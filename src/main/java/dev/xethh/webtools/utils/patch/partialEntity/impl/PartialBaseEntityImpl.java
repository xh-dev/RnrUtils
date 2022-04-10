package dev.xethh.webtools.utils.patch.partialEntity.impl;


import dev.xethh.webtools.utils.patch.partialEntity.AbstractPartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialBaseEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;

public class PartialBaseEntityImpl extends AbstractPartialEntity implements PartialBaseEntity {
    public PartialBaseEntityImpl(Object obj) {
        super(obj);
    }

    @Override
    public PartialArrayEntity asArrayEntity() {
        throw new RuntimeException("Not support conversion");
    }

    @Override
    public PartialObjectEntity asObjectEntity() {
        throw new RuntimeException("Not support conversion");
    }
}
