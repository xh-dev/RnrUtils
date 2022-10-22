package dev.xethh.webtools.dto.base.response.itemResponse;

import dev.xethh.webtools.dto.base.response.simpleResponse.SimpleResponse;

public interface ItemResponse<Payload> extends SimpleResponse {
    Payload getPayload();
}
