package dev.xethh.webtools.dto.base.response.itemResponse;

import dev.xethh.webtools.dto.base.response.simpleResponse.FailedSimpleResponse;

public class FailItemResponse<Payload> extends FailedSimpleResponse implements ItemResponse<Payload> {
    public FailItemResponse(String traceId, String message) {
        super(traceId, message);
    }

    public Payload getPayload() {
        return null;
    }

}
