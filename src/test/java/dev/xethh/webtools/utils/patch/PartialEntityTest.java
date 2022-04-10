package dev.xethh.webtools.utils.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.xethh.webtools.utils.patch.annotation.ListContent;
import dev.xethh.webtools.utils.patch.deserializer.PartialEntityDeserializer;
import dev.xethh.webtools.utils.patch.partialEntity.PartialEntity;
import dev.xethh.webtools.utils.patch.partialEntity.PartialObjectEntity;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class PartialEntityTest
{
    public static ObjectMapper om = new ObjectMapper();
    static {
        SimpleModule sm = PartialEntityDeserializer.MODULE_SUPPLIER.get();
        sm.addDeserializer(PartialEntity.class, new PartialEntityDeserializer());
        om.registerModule(sm);
    }

    @lombok.Data
    public static class TestData{
        private int int1;
        private int int2;
        private int int3;
        private Integer integer1;
        private Integer integer2;
        private Integer integer3;
        private long pLong1;
        private long pLong2;
        private long pLong3;
        private Long long1;
        private Long long2;
        private Long long3;
        private double pDouble1;
        private double pDouble2;
        private double pDouble3;
        private Double double1;
        private Double double2;
        private Double double3;
        private BigDecimal decimal1;
        private BigDecimal decimal2;
        private BigDecimal decimal3;
        private float pFloat1;
        private float pFloat2;
        private float pFloat3;
        private Float float1;
        private Float float2;
        private Float float3;
        private String string1;
        private String string2;
        private String string3;
        private boolean b1;
        private boolean b2;
        private boolean b3;
        private Boolean boolean1;
        private Boolean boolean2;
        private Boolean boolean3;
        private SubClass subClass;
        private UUID uuid1;
        private UUID uuid2;
        private UUID uuid3;

        private Map<String, String> map2;
        private Map<String, String> map3;

        private List<ContainedData> list2;

        // Generic type in list is erased in java, annotation to provide class information
        @ListContent(ContainedData.class)
        public void setList2(List<ContainedData> list2) {
            this.list2 = list2;
        }

        // Generic type in list is erased in java, annotation to provide class information
        @ListContent(ContainedData.class)
        public void setList3(List<ContainedData> list3) {
            this.list3 = list3;
        }

        @ListContent(ContainedData.class)
        private List<ContainedData> list3;

        private ContainedData[] array2;
        private ContainedData[] array3;
    }

    @lombok.Data
    public static class SubClass{
        private String line1;
        private String line2;
        private String line3;
    }

    @Data
    public static class ContainedData {
        private Integer int1;
        private Integer int2;
    }



    @Test
    public void simpleTest() throws Throwable {
        InputStream data = getClass().getClassLoader().getResourceAsStream("json");
        assert data != null;
        String jsonString = IOUtils.toString(data, StandardCharsets.UTF_8);
        PartialObjectEntity partialData = om.readValue(jsonString, PartialEntity.class).asObjectEntity();
        TestData testData = Optional.of(new TestData())
                .map(oTestData->{
                    oTestData.setInt1(1);
                    oTestData.setInt2(2);
                    oTestData.setInt3(3);
                    oTestData.setInteger1(1);
                    oTestData.setInteger2(2);
                    oTestData.setInteger3(3);
                    oTestData.setPLong1(1);
                    oTestData.setPLong2(2);
                    oTestData.setPLong3(3);
                    oTestData.setLong1(1L);
                    oTestData.setLong2(2L);
                    oTestData.setLong3(3L);
                    oTestData.setPDouble1(1.1);
                    oTestData.setPDouble2(1.2);
                    oTestData.setPDouble3(1.3);
                    oTestData.setDouble1(1.1);
                    oTestData.setDouble2(1.2);
                    oTestData.setDouble3(1.3);
                    oTestData.setDecimal1(new BigDecimal("1.1"));
                    oTestData.setDecimal2(new BigDecimal("1.2"));
                    oTestData.setDecimal3(new BigDecimal("1.3"));
                    oTestData.setPFloat1(1.1f);
                    oTestData.setPFloat2(1.2f);
                    oTestData.setPFloat3(1.3f);
                    oTestData.setFloat1(1.1f);
                    oTestData.setFloat2(1.2f);
                    oTestData.setFloat3(1.3f);
                    oTestData.setString1("1");
                    oTestData.setString2("2");
                    oTestData.setString3("3");
                    oTestData.setB1(true);
                    oTestData.setB2(true);
                    oTestData.setB3(true);
                    oTestData.setBoolean1(true);
                    oTestData.setBoolean2(true);
                    oTestData.setBoolean3(true);
                    oTestData.setUuid1(UUID.fromString("d4fc3a97-7954-41aa-b81b-9f94ca76a422"));
                    oTestData.setUuid2(UUID.fromString("0692aec2-925f-4130-9271-8a557e579ccb"));
                    oTestData.setUuid3(UUID.fromString("d67b776f-cf47-417e-8876-6f5749a4ed04"));
                    SubClass subClass = Optional.of(new SubClass())
                            .map(oSubClass -> {
                                oSubClass.setLine1("l1");
                                oSubClass.setLine2("l2");
                                oSubClass.setLine3("l3");
                                return oSubClass;
                            }).get();
                    oTestData.setSubClass(subClass);

                    HashMap<String, String> m2 = new HashMap<>();
                    m2.put("a","b");
                    oTestData.setMap2(m2);

                    HashMap<String, String> m3 = new HashMap<>();
                    m3.put("c","d");
                    oTestData.setMap3(m3);

                    oTestData.setList2(Arrays.asList(
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(100);
                                it.setInt2(100);
                                return it;
                            }).get(),
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(200);
                                it.setInt2(300);
                                return it;
                            }).get()
                    ));
                    oTestData.setList3(Arrays.asList(
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(100);
                                it.setInt2(100);
                                return it;
                            }).get(),
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(200);
                                it.setInt2(300);
                                return it;
                            }).get()
                    ));

                    oTestData.setArray2(new ContainedData[]{
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(1100);
                                it.setInt2(1100);
                                return it;
                            }).get(),
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(1200);
                                it.setInt2(1300);
                                return it;
                            }).get()

                    });

                    oTestData.setArray3(new ContainedData[]{
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(1100);
                                it.setInt2(1100);
                                return it;
                            }).get(),
                            Optional.of(new ContainedData()).map(it->{
                                it.setInt1(1200);
                                it.setInt2(1300);
                                return it;
                            }).get()

                    });

                    return oTestData;
                }).get()
                ;

        assertEquals(1,testData.getInt1());
        assertEquals(2,testData.getInt2());
        assertEquals(3,testData.getInt3());
        assertEquals(Integer.valueOf(1),testData.getInteger1());
        assertEquals(Integer.valueOf(2),testData.getInteger2());
        assertEquals(Integer.valueOf(3),testData.getInteger3());

        assertEquals(1L,testData.getPLong1());
        assertEquals(2L,testData.getPLong2());
        assertEquals(3L,testData.getPLong3());
        assertEquals(Long.valueOf(1),testData.getLong1());
        assertEquals(Long.valueOf(2),testData.getLong2());
        assertEquals(Long.valueOf(3),testData.getLong3());

        assertEquals(1.1,testData.getPDouble1(), 0.001);
        assertEquals(1.2,testData.getPDouble2(), 0.001);
        assertEquals(1.3,testData.getPDouble3(), 0.001);
        assertEquals(Double.valueOf(1.1),testData.getDouble1());
        assertEquals(Double.valueOf(1.2),testData.getDouble2());
        assertEquals(Double.valueOf(1.3),testData.getDouble3());

        assertEquals(new BigDecimal("1.1"),testData.getDecimal1());
        assertEquals(new BigDecimal("1.2"),testData.getDecimal2());
        assertEquals(new BigDecimal("1.3"),testData.getDecimal3());

        assertEquals(1.1f,testData.getPFloat1(), 0.001);
        assertEquals(1.2f,testData.getPFloat2(), 0.001);
        assertEquals(1.3f,testData.getPFloat3(), 0.001);
        assertEquals(Float.valueOf(1.1f),testData.getFloat1());
        assertEquals(Float.valueOf(1.2f),testData.getFloat2());
        assertEquals(Float.valueOf(1.3f),testData.getFloat3());

        assertEquals("1",testData.getString1());
        assertEquals("2",testData.getString2());
        assertEquals("3",testData.getString3());

        assertEquals(true,testData.getBoolean1());
        assertEquals(true,testData.getBoolean2());
        assertEquals(true,testData.getBoolean3());

        assertEquals(UUID.fromString("d4fc3a97-7954-41aa-b81b-9f94ca76a422"), testData.getUuid1());
        assertEquals(UUID.fromString("0692aec2-925f-4130-9271-8a557e579ccb"), testData.getUuid2());
        assertEquals(UUID.fromString("d67b776f-cf47-417e-8876-6f5749a4ed04"), testData.getUuid3());


        assertTrue(testData.isB1());
        assertTrue(testData.isB2());
        assertTrue(testData.isB3());
        assertEquals("l1",testData.getSubClass().getLine1());
        assertEquals("l2",testData.getSubClass().getLine2());
        assertEquals("l3",testData.getSubClass().getLine3());


        assertEquals(1, testData.getMap2().size());
        assertTrue(testData.getMap2().containsKey("a"));
        assertEquals("b", testData.getMap2().get("a"));

        assertEquals(1, testData.getMap3().size());
        assertTrue(testData.getMap3().containsKey("c"));
        assertEquals("d", testData.getMap3().get("c"));

        assertEquals(2, testData.getList2().size());
        assertEquals(Integer.valueOf(100), testData.getList2().get(0).getInt1());
        assertEquals(Integer.valueOf(100), testData.getList2().get(0).getInt2());
        assertEquals(Integer.valueOf(200), testData.getList2().get(1).getInt1());
        assertEquals(Integer.valueOf(300), testData.getList2().get(1).getInt2());

        assertEquals(2, testData.getList3().size());
        assertEquals(Integer.valueOf(100), testData.getList2().get(0).getInt1());
        assertEquals(Integer.valueOf(100), testData.getList2().get(0).getInt2());
        assertEquals(Integer.valueOf(200), testData.getList2().get(1).getInt1());
        assertEquals(Integer.valueOf(300), testData.getList2().get(1).getInt2());

        assertEquals(2, testData.getArray2().length);
        assertEquals(Integer.valueOf(1100), testData.getArray2()[0].getInt1());
        assertEquals(Integer.valueOf(1100), testData.getArray2()[0].getInt2());
        assertEquals(Integer.valueOf(1200), testData.getArray2()[1].getInt1());
        assertEquals(Integer.valueOf(1300), testData.getArray2()[1].getInt2());

        assertEquals(2, testData.getArray3().length);
        assertEquals(Integer.valueOf(1100), testData.getArray3()[0].getInt1());
        assertEquals(Integer.valueOf(1100), testData.getArray3()[0].getInt2());
        assertEquals(Integer.valueOf(1200), testData.getArray3()[1].getInt1());
        assertEquals(Integer.valueOf(1300), testData.getArray3()[1].getInt2());


        PartialEntityUtils.setObjectMapper(om);
        PartialEntityUtils.patch(testData, partialData);
        // Test after merge

        assertEquals(1,testData.getInt1());
        assertEquals(11,testData.getInt2());
        assertEquals(0,testData.getInt3());

        assertEquals(Integer.valueOf(1),testData.getInteger1());
        assertEquals(Integer.valueOf(22),testData.getInteger2());
        assertNull(testData.getInteger3());

        assertEquals(1L,testData.getPLong1());
        assertEquals(22L,testData.getPLong2());
        assertEquals(0L,testData.getPLong3());
        assertEquals(Long.valueOf(1),testData.getLong1());
        assertEquals(Long.valueOf(33),testData.getLong2());
        assertNull(testData.getLong3());

        assertEquals(1.1,testData.getPDouble1(), 0.001);
        assertEquals(1.3,testData.getPDouble2(), 0.001);
        assertEquals(0,testData.getPDouble3(), 0.001);
        assertEquals(Double.valueOf(1.1),testData.getDouble1());
        assertEquals(Double.valueOf(1.4),testData.getDouble2());
        assertNull(testData.getDouble3());

        assertEquals(new BigDecimal("1.1"),testData.getDecimal1());
        assertEquals(new BigDecimal("1.5"),testData.getDecimal2());
        assertNull(testData.getDecimal3());

        assertEquals(1.1f,testData.getPFloat1(), 0.001);
        assertEquals(2.2f,testData.getPFloat2(), 0.001);
        assertEquals(0f,testData.getPFloat3(), 0.001);
        assertEquals(Float.valueOf(1.1f),testData.getFloat1());
        assertEquals(Float.valueOf(3.2f),testData.getFloat2());
        assertNull(testData.getFloat3());

        assertEquals("1",testData.getString1());
        assertEquals("updated string",testData.getString2());
        assertNull(testData.getString3());

        assertEquals(true,testData.getBoolean1());
        assertEquals(false,testData.getBoolean2());
        assertNull(testData.getBoolean3());

        assertNull(testData.getUuid1());
        assertEquals(UUID.fromString("d67b776f-cf47-417e-8876-6f5749a4ed04"),testData.getUuid2());
        assertEquals(UUID.fromString("d67b776f-cf47-417e-8876-6f5749a4ed04"),testData.getUuid3());

        assertTrue(testData.isB1());
        assertFalse(testData.isB2());
        assertFalse(testData.isB3());
        assertEquals("l1",testData.getSubClass().getLine1());
        assertEquals("updated l2",testData.getSubClass().getLine2());
        assertNull(testData.getSubClass().getLine3());


        assertEquals(2, testData.getMap2().size());
        assertTrue(testData.getMap2().containsKey("x"));
        assertEquals("xx", testData.getMap2().get("x"));
        assertTrue(testData.getMap2().containsKey("aa"));
        assertEquals("bb", testData.getMap2().get("aa"));

        assertNull(testData.getMap3());

        assertEquals(3, testData.getList2().size());
        assertEquals(Integer.valueOf(1001), testData.getList2().get(0).getInt1());
        assertNull(testData.getList2().get(0).getInt2());
        assertEquals(Integer.valueOf(1002), testData.getList2().get(1).getInt1());
        assertEquals(Integer.valueOf(1003), testData.getList2().get(1).getInt2());
        assertEquals(Integer.valueOf(1005), testData.getList2().get(2).getInt1());
        assertEquals(Integer.valueOf(1006), testData.getList2().get(2).getInt2());

        assertNull(testData.getList3());

        assertEquals(3, testData.getArray2().length);
        assertEquals(Integer.valueOf(2001), testData.getArray2()[0].getInt1());
        assertNull(testData.getArray2()[0].getInt2());
        assertEquals(Integer.valueOf(2002), testData.getArray2()[1].getInt1());
        assertEquals(Integer.valueOf(3003), testData.getArray2()[1].getInt2());
        assertEquals(Integer.valueOf(3005), testData.getArray2()[2].getInt1());
        assertEquals(Integer.valueOf(4006), testData.getArray2()[2].getInt2());

        assertNull(testData.getArray3());
    }
}
