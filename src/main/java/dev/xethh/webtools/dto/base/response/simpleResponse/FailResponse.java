package dev.xethh.webtools.dto.base.response.simpleResponse;

import dev.xethh.webtools.dto.base.response.Response;

public interface FailResponse extends Response {
    String getTraceId();
    String getMessage();
    
    FailResponse asFailedResponse();
    
    
}
