package dev.xethh.webtools.utils.patch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.xethh.webtools.utils.patch.annotation.SkipPatch;
import dev.xethh.webtools.utils.patch.deserializer.PartialEntityDeserializer;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestSkipPatch {
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

        Data data = Optional.of(new Data()).map(it -> {
                    it.setId(UUID.fromString("8dadeb09-22e6-469b-aefe-f6ffb5a4a281"));
                    it.setUsername("ABC");
                    it.setEmail("b@b.com");
                    return it;
                }).get();
        String jsonStr = "{\"id\":null,\"username\":\"xxx\",\"email\":\"c@c.com\"}";
        PartialObjectEntity p = om.readValue(jsonStr, PartialEntity.class).asObjectEntity();

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data.id.toString());
        assertEquals("ABC",data.username);
        assertEquals("b@b.com",data.email);

        PartialEntityUtils.patch(data, p);

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data.id.toString());
        assertEquals("ABC",data.username);
        assertEquals("c@c.com",data.email);
    }
}
