package dev.xethh.webtools.utils.patch.partialEntity.impl;


import dev.xethh.webtools.utils.patch.partialEntity.AbstractPartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEmptyEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;

public class PartialEmptyEntityImpl extends AbstractPartialEntity implements PartialEmptyEntity {

    public PartialEmptyEntityImpl() {
        super(null);
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
