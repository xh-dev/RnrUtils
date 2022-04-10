package dev.xethh.webtools.utils.patch.partialEntity.impl;


import dev.xethh.webtools.utils.patch.partialEntity.AbstractPartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;

import java.util.List;

public class PartialArrayEntityImpl extends AbstractPartialEntity implements PartialArrayEntity {
    public PartialArrayEntityImpl(List<PartialObjectEntity> obj) {
        super(obj);
    }

    @Override
    public List<PartialObjectEntity> get() {
        return (List<PartialObjectEntity>) obj;
    }

    @Override
    public PartialArrayEntity asArrayEntity() {
        return this;
    }

    @Override
    public PartialObjectEntity asObjectEntity() {
        throw new RuntimeException("Not support conversion");
    }
}

