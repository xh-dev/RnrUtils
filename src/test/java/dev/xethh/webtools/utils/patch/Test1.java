package dev.xethh.webtools.utils.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.xethh.webtools.utils.patch.deserializer.PartialEntityDeserializer;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import lombok.Data;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class Test1
{
    @Data
    public static class Profile{
        private Long id;
        private String username;
        private String email;
        private Address address;

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", address=" + address +
                    '}';
        }
    }

    @Data
    public static class Address{
        private String line1;
        private String line2;
        private String line3;

        @Override
        public String toString() {
            return "{" +
                    "line1='" + line1 + '\'' +
                    ", line2='" + line2 + '\'' +
                    ", line3='" + line3 + '\'' +
                    '}';
        }
    }
    public static ObjectMapper om = new ObjectMapper();
    static {
        SimpleModule sm = PartialEntityDeserializer.MODULE_SUPPLIER.get();
        sm.addDeserializer(PartialEntity.class, new PartialEntityDeserializer());
        om.registerModule(sm);
    }

    @Test
    public void simpleTest() throws Throwable {
        PartialEntity partialData = om.readValue("{\"email\":null, \"address\":{\"line1\":\"updated line 1\", \"line2\":null}}", PartialEntity.class);

        Logger logger = LoggerFactory.getLogger(this.getClass());
        Profile profile = Optional.of(new Profile()).map(p->{
            p.setId(1L);
            p.setUsername("John");
            p.setEmail("a@a.com");
            p.setAddress(new Address());
            p.getAddress().setLine1("line 1");
            p.getAddress().setLine2("line 2");
            p.getAddress().setLine3("line 3");
            return p;
        }).get();

        logger.info("Delta change: "+partialData.toString());
        logger.info("Before partial update: ");
        logger.info(profile.toString());

        PartialEntityHelper.merge(profile, partialData);

        logger.info("After partial update: ");
        logger.info(profile.toString());


    }
}
