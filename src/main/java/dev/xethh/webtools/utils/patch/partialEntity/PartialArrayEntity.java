package dev.xethh.webtools.utils.patch.partialEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface PartialArrayEntity extends PartialEntity<List<PartialObjectEntity>> {
    List<PartialObjectEntity> get();
    default <T> List<T> getField(Function<Map<String,Object>, T> function){
        return get().stream().map(it->it.getFields(function)).collect(Collectors.toList());
    }
}
