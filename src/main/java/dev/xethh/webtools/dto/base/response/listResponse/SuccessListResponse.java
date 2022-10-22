package dev.xethh.webtools.dto.base.response.listResponse;

import dev.xethh.webtools.dto.base.response.itemResponse.SuccessItemResponse;

import java.util.List;

public class SuccessListResponse<Payload> extends SuccessItemResponse<List<Payload>> implements ListResponse<Payload>{
    public SuccessListResponse(List<Payload> payload) {
        super(payload);
    }
}
