package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.response.FailResponse;
import dev.xethh.webtools.dto.base.response.SuccessResponse;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

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
        var response = SuccessResponse.item(true);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getPayload());
        assertNotEquals(response, SuccessResponse.item(true));
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
    }
}
