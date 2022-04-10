package dev.xethh.webtools.utils.patch.partialEntity;


import java.util.Map;
import java.util.function.Function;

public interface PartialObjectEntity extends PartialEntity<Map<String,Object>> {
    Map<String, Object> map();
    default <T> T getFields(Function<Map<String, Object>, T> function){
        return function.apply(map());
    }
}
