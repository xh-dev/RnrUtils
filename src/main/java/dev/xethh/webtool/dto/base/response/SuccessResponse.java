package dev.xethh.webtool.dto.base.response;

import java.util.Arrays;
import java.util.List;

public class SuccessResponse implements Response {
    final static SuccessResponse _instance = new SuccessResponse();

    SuccessResponse() {
    }

    public static SuccessResponse noPayload() {
        return SuccessResponse._instance;
    }

    public static <Payload> ItemResponse<Payload> item(Payload item) {
        return new ItemResponse<>(item);
    }

    public static <Payload> ListResponse<Payload> array(Payload[] item) {
        return new ListResponse<>(Arrays.asList(item));
    }

    public static <Payload> ListResponse<Payload> list(List<Payload> item) {
        return new ListResponse<>(item);
    }

    public boolean isSuccess() {
        return true;
    }
}
