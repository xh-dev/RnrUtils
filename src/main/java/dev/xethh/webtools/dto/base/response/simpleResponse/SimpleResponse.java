package dev.xethh.webtools.dto.base.response.simpleResponse;

import dev.xethh.webtools.dto.base.response.Response;

public interface SimpleResponse extends Response {
    FailResponse asFailedResponse();
}
