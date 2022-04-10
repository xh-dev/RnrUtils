package dev.xethh.webtools.utils.patch;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.xethh.webtools.utils.patch.annotation.ListContent;
import dev.xethh.webtools.utils.patch.annotation.SkipPatch;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;
import dev.xethh.webtools.utils.patch.partialEntity.impl.PartialArrayEntityImpl;
import dev.xethh.webtools.utils.patch.partialEntity.impl.PartialBaseEntityImpl;
import dev.xethh.webtools.utils.patch.partialEntity.impl.PartialEmptyEntityImpl;
import dev.xethh.webtools.utils.patch.partialEntity.impl.PartialObjectEntityImpl;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PartialEntityHelper {
    private static final Logger logger = LoggerFactory.getLogger(PartialEntityHelper.class);
    public static ObjectMapper objectMapper;

    public static void setObjectMapper(ObjectMapper objectMapper) {
        PartialEntityHelper.objectMapper = objectMapper;
    }

    public static PartialEntity entityOf(Object obj) {
        if (obj == null) {
            return new PartialEmptyEntityImpl();
        } else {
            if (obj instanceof Map) {
                return new PartialObjectEntityImpl((Map<String, Object>) obj);
            } else if (obj instanceof List) {
                List<PartialObjectEntity> list = ((List<Map<String, Object>>) obj)
                        .stream()
                        .map(PartialObjectEntityImpl::new)
                        .map(it->(PartialObjectEntity)it)
                        .collect(Collectors.toList());
                return new PartialArrayEntityImpl(list);
            } else {
                return new PartialBaseEntityImpl(obj);
            }
        }
    }

    public static <T> T merge(T t, PartialEntity deltaChange) {
        // if(t instanceof List){
        //     return objectMapper.readValue(deltaChange.toJson(), Array);
        // } else {
        return (T) internalMerge(t, deltaChange);
        // }
    }

    public static <T> List<T> mergeList(List<T> list, PartialArrayEntity deltaChange) {
        if(list.size() != deltaChange.get().size()){
            throw new RuntimeException("Not match");
        }
        List<T> newList = new ArrayList<>(list);
        for(int i=0;i<list.size();i++){
            newList.add(i,merge(list.get(i), deltaChange.get().get(i)));
        }
        return newList;
    }

    private static List<Field> getAllDeclaredFields(Class<?> clazz){
        List<Field> list = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        if(clazz.getSuperclass() != Object.class){
            list.addAll(getAllDeclaredFields(clazz.getSuperclass()));
        }
        return list;
    }

    protected static Object internalMerge(Object t, PartialEntity deltaChange) {
        Map<String, Field> fields = getAllDeclaredFields(t.getClass()).stream().filter(it -> it.getAnnotation(SkipPatch.class) != null).collect(Collectors.toMap(i -> i.getName().toLowerCase(), i -> i));
        Try<Map<String, Method>> result = Try.of(() -> t.getClass().getMethods())
                .filter(it -> deltaChange instanceof PartialObjectEntity) // Only PartialObjectEntity could patch with instance
                // extract setters from instance
                .map(it ->
                        Arrays.stream(it)
                                .filter(i -> i.getName().startsWith("set"))
                                // cast to map of method name and reflection method instance
                                .collect(Collectors.toMap(i -> i.getName().substring(3).toLowerCase(), i -> i))
                )
                .mapTry(fieldNameAndSetterMethod -> {
                            // loop through list of delta change
                            for (Map.Entry<String, Object> deltaChangeEntry : ((PartialObjectEntity) deltaChange).map().entrySet()) {
                                final String fieldName = deltaChangeEntry.getKey().toLowerCase();

                                if (fieldNameAndSetterMethod.containsKey(fieldName)) { // if the map contains key,
                                    if (fields.containsKey(fieldName) || fieldNameAndSetterMethod.get(fieldName).getAnnotation(SkipPatch.class)!=null) {
                                        logger.info("skipped: " + fieldName);
                                        continue;
                                    }
                                    logger.info("processing: " + fieldName);
                                    Method setterMethod = fieldNameAndSetterMethod.get(fieldName);
                                    Class<?> setterParameterType = setterMethod.getParameterTypes()[0];
                                    Object value = deltaChangeEntry.getValue();
                                    String key = deltaChangeEntry.getKey();
                                    if (value == null) { // set to null ignore the typing
                                        if (setterParameterType.isPrimitive()) {
                                            if (setterParameterType == int.class) {
                                                int x = 0;
                                                logger.info(String.format("Field[%s] update to  " + x, key));
                                                setterMethod.invoke(t, x);
                                            } else if (setterParameterType == long.class) {
                                                long x = 0L;
                                                logger.info(String.format("Field[%s] update to  " + x, key));
                                                setterMethod.invoke(t, x);
                                            } else if (setterParameterType == float.class) {
                                                float x = 0f;
                                                logger.info(String.format("Field[%s] update to  " + x, key));
                                                setterMethod.invoke(t, x);
                                            } else if (setterParameterType == double.class) {
                                                double x = 0d;
                                                logger.info(String.format("Field[%s] update to  " + x, key));
                                                setterMethod.invoke(t, x);
                                            } else if (setterParameterType == boolean.class) {
                                                boolean x = false; //java bool default value is false
                                                logger.info(String.format("Field[%s] update to  " + x, key));
                                                setterMethod.invoke(t, x);
                                            }

                                        } else {
                                            logger.info(String.format("Field[%s] update to  null", key));
                                            setterMethod.invoke(t, (Object) null);
                                        }
                                    } else if (value instanceof String) {
                                        if (setterParameterType == String.class) {
                                            logger.info(String.format("Field[%s] update to  %s", key, value));
                                            setterMethod.invoke(t, value);
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof Long) {
                                        if (setterParameterType == Long.class || setterParameterType == long.class) {
                                            logger.info(String.format("Field[%s] update to  %d", key, value));
                                            setterMethod.invoke(t, value);
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof Integer) {
                                        if (setterParameterType == Long.class || setterParameterType == long.class) {
                                            logger.info(String.format("Field[%s] update to  %d", key, value));
                                            setterMethod.invoke(t, ((Integer) value).longValue());
                                        } else if (setterParameterType == Integer.class || setterParameterType == int.class) {
                                            logger.info(String.format("Field[%s] update to  %d", key, value));
                                            setterMethod.invoke(t, value);
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof Double) {
                                        if (setterParameterType.isAssignableFrom(BigDecimal.class)) {
                                            logger.info(String.format("Field[%s] update to  %f", key, value));
                                            setterMethod.invoke(t, BigDecimal.valueOf((Double) value));
                                        } else if (setterParameterType == Double.class || setterParameterType == double.class) {
                                            logger.info(String.format("Field[%s] update to  %f", key, value));
                                            setterMethod.invoke(t, value);
                                        } else if (setterParameterType == Float.class || setterParameterType == float.class) {
                                            logger.info(String.format("Field[%s] update to  %f", key, value));
                                            setterMethod.invoke(t, ((Double) value).floatValue());
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof BigDecimal) {
                                        if (setterParameterType.isAssignableFrom(BigDecimal.class)) {
                                            logger.info(String.format("Field[%s] update to  %f", key, value));
                                            setterMethod.invoke(t, deltaChangeEntry.getValue());
                                        } else if (setterParameterType == Double.class || setterParameterType == double.class) {
                                            logger.info(String.format("Field[%s] update to  %f", key, value));
                                            setterMethod.invoke(t, ((BigDecimal) value).doubleValue());
                                        } else if (setterParameterType == Float.class || setterParameterType == float.class) {
                                            logger.info(String.format("Field[%s] update to  %f", key, deltaChangeEntry.getValue()));
                                            setterMethod.invoke(t, ((Double) deltaChangeEntry.getValue()).floatValue());
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof Boolean) {
                                        if (setterParameterType == Boolean.class || setterParameterType == boolean.class) {
                                            logger.info(String.format("Field[%s] update to  %B", key, value));
                                            setterMethod.invoke(t, value);
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof List) {
                                        if (setterParameterType == List.class) {
                                            // logger.info(String.format("Field[%s] update to  list", key));
                                            // setterMethod.invoke(t, value);
                                            processList(t, (List<Map<String, Object>>) value, setterMethod, setterParameterType, key);
                                        } else if (setterParameterType.isArray()) {
                                            // logger.info(String.format("Field[%s] update to  list", key));
                                            // setterMethod.invoke(t, value);
                                            processArray(t, (List<Map<String, Object>>) value, setterMethod, setterParameterType, key);
                                        } else throw new RuntimeException("Setting typing not match");
                                    } else if (value instanceof Map) {
                                        if (setterMethod.getParameterTypes()[0].isAssignableFrom(Map.class)) {
                                            logger.info(String.format("Field[%s] update to  map", key));
                                            setterMethod.invoke(t, value);
                                        } else {
                                            logger.info("Update sub object: " + key);
                                            Object subObject = t.getClass().getMethod("g" + setterMethod.getName().substring(1)).invoke(t);
                                            PartialEntityHelper.internalMerge(subObject, PartialEntityHelper.entityOf(value));
                                        }
                                    } else throw new RuntimeException("No matching found");
                                } else throw new RuntimeException("unexpected field contained in the upload data");
                            }
                            return fieldNameAndSetterMethod;
                        }
                );

        if (result.isFailure()) {
            logger.error("Fail to process merge", result.getCause());
            throw new RuntimeException(result.toEither().getLeft());
        }

        return t;
    }

    private static void processArray(Object target, List<Map<String, Object>> value, Method setter, Class<?> setterClass, String fieldName) throws InvocationTargetException, IllegalAccessException {
        if (objectMapper == null) {
            throw new RuntimeException("Object mapper not set for merging list data");
        }
         if (setterClass.isArray()) {
            Class<?> typeOfArray = setterClass.getComponentType();
             logger.info(String.format("Field[%s] update to  array", fieldName));
             Object[] array = value.stream().map(it -> objectMapper.convertValue(it, typeOfArray)).toArray();
             Object[] arrays = (Object[]) Array.newInstance(typeOfArray, array.length);
             System.arraycopy(array, 0, arrays, 0, array.length);
            setter.invoke(target, (Object) arrays);
        } else throw new RuntimeException("Setting typing not match");
    }
    private static void processList(Object target, List<Map<String, Object>> value, Method setter, Class<?> setterClass, String fieldName) throws InvocationTargetException, IllegalAccessException {
        if (objectMapper == null) {
            throw new RuntimeException("Object mapper not set for merging list data");
        }
        if (setterClass == List.class) {
            ListContent content = setter.getAnnotation(ListContent.class);

            if (content != null && content.value() != null) {
                List<?> listItem = value.stream().map(it -> objectMapper.convertValue(it, content.value())).collect(Collectors.toList());
                logger.info(String.format("Field[%s] update to  list", fieldName));
                setter.invoke(target, listItem);
            } else throw new RuntimeException("Missing list content class information");
        } else throw new RuntimeException("Setting typing not match");
    }
}
