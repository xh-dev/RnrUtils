package dev.xethh.webtools.utils.patch.partialEntity.impl;


import dev.xethh.webtools.utils.patch.partialEntity.AbstractPartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;

import java.util.Map;

public class PartialObjectEntityImpl extends AbstractPartialEntity<Map<String,Object>> implements PartialObjectEntity {
    public PartialObjectEntityImpl(Map<String, Object> obj) {
        super(obj);
    }

    public Map<String, Object> map() {
        return obj;
    }

    @Override
    public PartialArrayEntity asArrayEntity() {
        throw new RuntimeException("Not support conversion");
    }

    @Override
    public PartialObjectEntity asObjectEntity() {
        return this;
    }
}
