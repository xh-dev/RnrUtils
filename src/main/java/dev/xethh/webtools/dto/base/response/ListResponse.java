package dev.xethh.webtools.dto.base.response;

import java.util.List;

public class ListResponse<Payload> extends ItemResponse<List<Payload>> {
    protected ListResponse(List<Payload> payload) {
        super(payload);
    }
}
