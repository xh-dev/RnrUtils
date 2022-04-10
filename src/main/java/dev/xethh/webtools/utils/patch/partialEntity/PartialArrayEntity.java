package dev.xethh.webtools.utils.patch.partialEntity;

import java.util.List;

public interface PartialArrayEntity extends PartialEntity {
    List<PartialObjectEntity> get();
}
