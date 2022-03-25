package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.response.FailResponse;
import dev.xethh.webtools.dto.base.response.SuccessResponse;

import java.util.Arrays;

public class ResponseCreationDemo {
    public static void main(String[] args){
        //Create Response without payload
        SuccessResponse.noPayload();

        //Create Response with single item payload
        SuccessResponse.payload("data");

        //Create Response with list of item as payload
        SuccessResponse.list(Arrays.asList("data1","data2"));
        SuccessResponse.array(new String[]{"data1","data2"});

        //Create Response of error
        FailResponse.error("Trace-ID", "Error message");
    }
}
