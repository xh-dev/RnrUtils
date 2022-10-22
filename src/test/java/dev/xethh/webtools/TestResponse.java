package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.response.ResponseUtils;
import dev.xethh.webtools.dto.base.response.simpleResponse.FailedSimpleResponse;
import dev.xethh.webtools.dto.base.response.simpleResponse.SuccessSimpleResponse;
import dev.xethh.webtools.exception.EmptyPayload;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

public class TestResponse {
    @Test
    public void TestErrorResponse(){
        var response = ResponseUtils.failed("ABCD", "error");
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertTrue(response.isFailed());
        assertEquals("error", response.asFailedResponse().getMessage());
        assertEquals("ABCD", response.asFailedResponse().getTraceId());
    }

    @Test
    public void TestSuccessResponse(){
        var response = ResponseUtils.success();
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertSame(response, SuccessSimpleResponse.instance());
        assertEquals(response, SuccessSimpleResponse.instance());
    }

    @Test
    public void testItemResponse(){
        var response = ResponseUtils.itemSuccess(true);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getPayload());
        assertNotEquals(response, ResponseUtils.itemSuccess(true));

        assertThrows(EmptyPayload.class, ()-> ResponseUtils.itemSuccess(null));
    }

    @Test
    public void testFailedItemResponse(){
        var response = ResponseUtils.<Boolean>itemFailed("trace-id","msg");
        assertNotNull(response);
        assertTrue(response.isFailed());
        assertNull(response.getPayload());
        assertEquals("trace-id", response.asFailedResponse().getTraceId());
        assertEquals("msg", response.asFailedResponse().getMessage());
    }

    @Test
    public void testFailedListResponse(){
        var response = ResponseUtils.<Boolean>listFailed("trace-id","msg");
        assertNotNull(response);
        assertTrue(response.isFailed());
        assertNull(response.getPayload());
        assertEquals("trace-id", response.asFailedResponse().getTraceId());
        assertEquals("msg", response.asFailedResponse().getMessage());
    }

    @Test
    public void testFailedPageResponse(){
        var response = ResponseUtils.<Boolean>pageFailed(2, 10, "trace-id","msg");
        assertNotNull(response);
        assertTrue(response.isFailed());
        assertNull(response.getPayload());
        assertEquals(2, response.page());
        assertEquals(10, response.pageSize());
        assertEquals(0, response.itemCount());
        assertEquals("trace-id", response.asFailedResponse().getTraceId());
        assertEquals("msg", response.asFailedResponse().getMessage());
    }

    @Test
    public void testSuccessPageResponse(){
        var response = ResponseUtils.<Boolean>pageSuccess(2, 10, List.of(true, false, true));
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(3, response.itemCount());
        assertEquals(2, response.page());
        assertEquals(10, response.pageSize());
    }

    @Test
    public void testArrayResponse(){
        var response = ResponseUtils.arraySuccess(new Integer[]{1,2,3});
        assertNotNull(response);
        assertTrue(response.isSuccess());
        var testIndex = 1;
        for(int index : response.getPayload()){
            assertEquals(testIndex, index);
            testIndex++;
        }

        assertThrows(EmptyPayload.class, ()-> ResponseUtils.arraySuccess(null));

    }
    @Test
    public void testListResponse(){
        var response = ResponseUtils.listSuccess(Arrays.asList(1,2,3));
        assertNotNull(response);
        assertTrue(response.isSuccess());
        var testIndex = 1;
        for(int index : response.getPayload()){
            assertEquals(testIndex, index);
            testIndex++;
        }
        assertThrows(EmptyPayload.class, ()-> ResponseUtils.listSuccess(null));

    }
}
