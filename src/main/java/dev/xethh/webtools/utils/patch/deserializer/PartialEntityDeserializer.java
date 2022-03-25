package dev.xethh.webtools.utils.patch.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.xethh.webtools.utils.patch.PartialEntityHelper;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Customized JSON deserializer for patching data.
 * The output Entity Partial.PartialEntity contains all to be updated field in key-value pair structure.
 */
public class PartialEntityDeserializer extends JsonDeserializer<PartialEntity> {
    public static String MODULE_NAME = "PartialEntityDeserializer";
    public static Supplier<SimpleModule> MODULE_SUPPLIER = ()->new SimpleModule(MODULE_NAME);
    @Override
    public PartialEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        switch (jsonParser.getCurrentToken()){
            case START_OBJECT:
                return PartialEntityHelper.entityOf(jsonParser.readValueAs(new TypeReference<Map<String, Object>>() {
                }));
            case VALUE_NULL:
                return PartialEntityHelper.entityOf(null);
            case VALUE_FALSE:
            case VALUE_TRUE:
                return PartialEntityHelper.entityOf(jsonParser.readValueAs(Boolean.class));
            case VALUE_STRING:
                return PartialEntityHelper.entityOf(jsonParser.readValueAs(String.class));
            case VALUE_NUMBER_FLOAT:
                return PartialEntityHelper.entityOf(jsonParser.getDecimalValue());
            case VALUE_NUMBER_INT:
                return PartialEntityHelper.entityOf(jsonParser.readValueAs(Long.class));
            case START_ARRAY:
                return PartialEntityHelper.entityOf(jsonParser.readValueAs(new TypeReference<List<Map<String,Object>>>() {
                }));
        }
        return null;
    }
}
