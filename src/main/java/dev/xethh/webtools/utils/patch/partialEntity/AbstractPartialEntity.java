package dev.xethh.webtools.utils.patch.partialEntity;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.List;
import java.util.Map;

public abstract class AbstractPartialEntity<A> implements PartialEntity {
    final protected A obj;

    protected AbstractPartialEntity(A obj) {
        this.obj = obj;
    }

    @Override
    public boolean isArray() {
        return isNotEmpty() && obj instanceof List;
    }

    @Override
    public boolean isObject() {
        return isNotEmpty() && obj instanceof Map;
    }

    @Override
    public boolean isEmpty() {
        return obj == null;
    }

    @Override
    public boolean isBaseValue() {
        return isNotEmpty() && isNotArray() && isNotObject();
    }

    @Override
    public String toJson() {
        return Try.ofSupplier(ObjectMapper::new)
                .mapTry(objectMapper -> objectMapper.writeValueAsString(obj))
                .get();
    }

    @Override
    public String toString() {
        return toJson();
    }
}
