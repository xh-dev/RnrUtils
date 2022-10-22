package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.response.ResponseUtils;

import java.util.Arrays;

public class ResponseCreationDemo {
    public static void main(String[] args){
        //Create Response without payload
        ResponseUtils.success();
        //Create Response of error
        ResponseUtils.failed("Trace-ID", "Error message");

        //Create Response with single item payload
        ResponseUtils.itemSuccess("data");
        //Create Response of error for item response
        ResponseUtils.itemFailed("Trace-ID", "Error message");

        //Create Response with list of item as payload
        ResponseUtils.listSuccess(Arrays.asList("data1","data2"));
        ResponseUtils.arraySuccess(new String[]{"data1","data2"});

        //Create Response of error for list response
        ResponseUtils.listFailed("Trace-ID", "Error message");

        //Create Response with page of item as payload
        ResponseUtils.pageSuccess(1, 10, Arrays.asList("data1","data2"));

        //Create Response of error for page response
        ResponseUtils.pageFailed(1, 10, "Trace-ID", "Error message");
    }
}
