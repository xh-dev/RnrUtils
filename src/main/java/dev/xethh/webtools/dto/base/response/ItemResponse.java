package dev.xethh.webtools.dto.base.response;

public class ItemResponse<Payload> extends SuccessResponse {
    private Payload payload;

    protected ItemResponse(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return payload;
    }
}
