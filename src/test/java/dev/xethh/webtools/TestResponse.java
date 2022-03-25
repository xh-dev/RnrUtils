package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.response.FailResponse;
import dev.xethh.webtools.dto.base.response.SuccessResponse;
import dev.xethh.webtools.exception.EmptyPayload;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

public class TestResponse {
    @Test
    public void TestErrorResponse(){
        var response = FailResponse.error("ABCD", "error");
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("error", response.getMessage());
        assertEquals("ABCD", response.getTraceId());
    }

    @Test
    public void TestSuccessResponse(){
        var response = SuccessResponse.noPayload();
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertSame(response, SuccessResponse.noPayload());
        assertEquals(response, SuccessResponse.noPayload());
    }

    @Test
    public void testItemResponse(){
        var response = SuccessResponse.payload(true);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getPayload());
        assertNotEquals(response, SuccessResponse.payload(true));

        assertThrows(EmptyPayload.class, ()->SuccessResponse.payload(null));

        response = SuccessResponse.optionalPayload(Optional.of(true));
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getPayload());
        assertNotEquals(response, SuccessResponse.payload(true));

        assertThrows(EmptyPayload.class, ()->SuccessResponse.optionalPayload(Optional.empty()));

        response = SuccessResponse.supplyPayload(()->true);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getPayload());
        assertNotEquals(response, SuccessResponse.payload(true));

        assertThrows(EmptyPayload.class, ()->SuccessResponse.supplyPayload(()->{throw new EmptyPayload();}));
    }

    @Test
    public void testArrayResponse(){
        var response = SuccessResponse.array(new Integer[]{1,2,3});
        assertNotNull(response);
        assertTrue(response.isSuccess());
        var testIndex = 1;
        for(int index : response.getPayload()){
            assertEquals(testIndex, index);
            testIndex++;
        }

        assertThrows(NullPointerException.class, ()->SuccessResponse.array(null));

        response = SuccessResponse.supplyArray(()->new Integer[]{1,2,3});
        assertNotNull(response);
        assertTrue(response.isSuccess());
        testIndex = 1;
        for(int index : response.getPayload()){
            assertEquals(testIndex, index);
            testIndex++;
        }

        assertThrows(NullPointerException.class, ()->SuccessResponse.supplyArray(()->null));
    }
    @Test
    public void testListResponse(){
        var response = SuccessResponse.list(Arrays.asList(1,2,3));
        assertNotNull(response);
        assertTrue(response.isSuccess());
        var testIndex = 1;
        for(int index : response.getPayload()){
            assertEquals(testIndex, index);
            testIndex++;
        }
        assertThrows(NullPointerException.class, ()->SuccessResponse.list(null));

        response = SuccessResponse.supplyList(()->Arrays.asList(1,2,3));
        assertNotNull(response);
        assertTrue(response.isSuccess());
        testIndex = 1;
        for(int index : response.getPayload()){
            assertEquals(testIndex, index);
            testIndex++;
        }
        assertThrows(NullPointerException.class, ()->SuccessResponse.supplyList(null));
    }
}
