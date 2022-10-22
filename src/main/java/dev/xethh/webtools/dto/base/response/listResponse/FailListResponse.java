package dev.xethh.webtools.dto.base.response.listResponse;

import dev.xethh.webtools.dto.base.response.itemResponse.FailItemResponse;

import java.util.List;

public class FailListResponse<Payload> extends FailItemResponse<List<Payload>> implements ListResponse<Payload>{
    public FailListResponse(String traceId, String message) {
        super(traceId, message);
    }
}
