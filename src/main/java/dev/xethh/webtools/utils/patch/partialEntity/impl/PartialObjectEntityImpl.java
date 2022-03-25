package dev.xethh.webtools.utils.patch.partialEntity.impl;


import dev.xethh.webtools.utils.patch.partialEntity.AbstractPartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;

import java.util.Map;

public class PartialObjectEntityImpl extends AbstractPartialEntity implements PartialObjectEntity {
    public PartialObjectEntityImpl(Map<String, Object> obj) {
        super(obj);
    }

    public Map<String, Object> map() {
        return ((Map<String, Object>) obj);
    }
}
