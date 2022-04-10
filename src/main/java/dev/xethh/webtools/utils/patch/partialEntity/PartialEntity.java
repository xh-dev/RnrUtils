package dev.xethh.webtools.utils.patch.partialEntity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.xethh.webtools.utils.patch.deserializer.PartialEntityDeserializer;

/**
 * The PartialEntity object provides functionality storing updating fields and value as key-value pair structure.
 * The PartialEntity object provides updating instruction for merging with the exiting entity to be updated.
 * The PartialEntity is deserialized using JsonPatchDataDeserializer.
 */
@JsonDeserialize(using = PartialEntityDeserializer.class)
public interface PartialEntity {
    boolean isArray();

    default boolean isNotArray() {
        return !isArray();
    }

    boolean isObject();

    default boolean isNotObject() {
        return !isObject();
    }

    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    boolean isBaseValue();

    default boolean isNotBaseValue() {
        return !isBaseValue();
    }

    PartialArrayEntity asArrayEntity();
    PartialObjectEntity asObjectEntity();

    String toJson();

}
