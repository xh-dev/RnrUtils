package dev.xethh.webtools.dto.base.response.itemResponse;

import dev.xethh.webtools.dto.base.response.simpleResponse.SuccessSimpleResponse;

public class SuccessItemResponse<Payload> extends SuccessSimpleResponse implements ItemResponse<Payload> {
    private Payload payload;

    public SuccessItemResponse(Payload payload) {
        super();
        this.payload = payload;
    }

    public Payload getPayload() {
        return payload;
    }

}
