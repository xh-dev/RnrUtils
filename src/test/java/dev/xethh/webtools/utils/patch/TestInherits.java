package dev.xethh.webtools.utils.patch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.xethh.webtools.utils.patch.annotation.SkipPatch;
import dev.xethh.webtools.utils.patch.deserializer.PartialEntityDeserializer;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestInherits {
    public static ObjectMapper om = new ObjectMapper();
    static {
        SimpleModule sm = PartialEntityDeserializer.MODULE_SUPPLIER.get();
        sm.addDeserializer(PartialEntity.class, new PartialEntityDeserializer());
        om.registerModule(sm);
    }

    @Data
    public static class Supper1{
        @SkipPatch
        private UUID id;
    }
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Data1 extends Supper1{
        @SkipPatch
        private String username;
        private String email;
    }

    @Data
    public static class Supper2{
        private UUID id;
    }
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Data2 extends Supper2{
        @SkipPatch
        private String username;
        private String email;
    }

    @Test
    public void test() throws JsonProcessingException {

        Data1 data1 = Optional.of(new Data1()).map(it -> {
                    it.setId(UUID.fromString("8dadeb09-22e6-469b-aefe-f6ffb5a4a281"));
                    it.setUsername("ABC");
                    it.setEmail("b@b.com");
                    return it;
                }).get();
        String jsonStr = "{\"id\":null,\"username\":\"xxx\",\"email\":\"c@c.com\"}";
        PartialObjectEntity p = om.readValue(jsonStr, PartialEntity.class).asObjectEntity();

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data1.getId().toString());
        assertEquals("ABC",data1.username);
        assertEquals("b@b.com",data1.email);

        PartialEntityHelper.merge(data1, p);

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data1.getId().toString());
        assertEquals("ABC",data1.username);
        assertEquals("c@c.com",data1.email);

        Data2 data2 = Optional.of(new Data2()).map(it -> {
            it.setId(UUID.fromString("8dadeb09-22e6-469b-aefe-f6ffb5a4a281"));
            it.setUsername("ABC");
            it.setEmail("b@b.com");
            return it;
        }).get();
        p = om.readValue(jsonStr, PartialEntity.class).asObjectEntity();

        assertEquals("8dadeb09-22e6-469b-aefe-f6ffb5a4a281",data2.getId().toString());
        assertEquals("ABC",data2.username);
        assertEquals("b@b.com",data2.email);

        PartialEntityHelper.merge(data2, p);

        assertNull(data2.getId());
        assertEquals("ABC",data2.username);
        assertEquals("c@c.com",data2.email);
    }
}
