package dev.xethh.webtools.utils.patch.partialEntity.impl;


import dev.xethh.webtools.utils.patch.partialEntity.AbstractPartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;

import java.util.List;

public class PartialArrayEntityImpl extends AbstractPartialEntity implements PartialArrayEntity {
    public PartialArrayEntityImpl(List<Object> obj) {
        super(obj);
    }
}

