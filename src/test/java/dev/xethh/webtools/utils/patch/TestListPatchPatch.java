package dev.xethh.webtools.utils.patch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.xethh.webtools.utils.patch.annotation.SkipPatch;
import dev.xethh.webtools.utils.patch.deserializer.PartialEntityDeserializer;
import dev.xethh.webtools.utils.patch.partialEntity.PartialArrayEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestListPatchPatch {
    public static ObjectMapper om = new ObjectMapper();
    static {
        SimpleModule sm = new SimpleModule("PartialEntityDeserializer");
        sm.addDeserializer(PartialEntity.class, new PartialEntityDeserializer());
        om.registerModule(sm);
    }
    @lombok.Data
    public static class Data{
        @SkipPatch
        private UUID id;
        @SkipPatch
        private String username;
        private String email;
    }


    @Test
    public void test() throws JsonProcessingException {

        List<Data> dataList = Optional.of(new Data()).map(it -> {
                    it.setId(UUID.fromString("8dadeb09-22e6-469b-aefe-f6ffb5a4a281"));
                    it.setUsername("ABC");
                    it.setEmail("b@b.com");
                    return it;
                }).map(Arrays::asList).get();
        String jsonStr = "[{\"id\":null,\"username\":\"xxx\",\"email\":\"c@c.com\"}]";
        PartialEntity p = om.readValue(jsonStr, PartialEntity.class);
        Data data = dataList.get(0);

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data.id.toString());
        assertEquals("ABC",data.username);
        assertEquals("b@b.com",data.email);

        PartialArrayEntity arrayEntity = p.asArrayEntity();
        dataList = PartialEntityUtils.patchList(dataList, arrayEntity);
        data = dataList.get(0);

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data.id.toString());
        assertEquals("ABC",data.username);
        assertEquals("c@c.com",data.email);
    }
}
