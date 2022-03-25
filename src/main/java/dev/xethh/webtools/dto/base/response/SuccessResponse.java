package dev.xethh.webtools.dto.base.response;

import dev.xethh.webtools.exception.EmptyPayload;
import io.vavr.control.Try;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SuccessResponse implements Response {
    final static SuccessResponse _instance = new SuccessResponse();

    SuccessResponse() {
    }

    public static SuccessResponse noPayload() {
        return SuccessResponse._instance;
    }

    public static <Payload> ItemResponse<Payload> optionalPayload(Optional<Payload> op) {
        return Try.ofSupplier(() -> op).map(it -> {
                    if (it.isPresent()) {
                        return it.get();
                    } else {
                        throw new EmptyPayload();
                    }
                }
        ).map(SuccessResponse::payload).get();
    }

    public static <Payload> ItemResponse<Payload> supplyPayload(Supplier<Payload> op) {
        return Try.ofSupplier(op).map(SuccessResponse::payload).get();
    }

    public static <Payload> ItemResponse<Payload> payload(Payload item) {
        if(item == null)
            throw new EmptyPayload();
        return new ItemResponse<>(item);
    }

    public static <Payload> ListResponse<Payload> supplyArray(Supplier<Payload[]> op) {
        return Try.ofSupplier(op).map(SuccessResponse::array).get();
    }

    public static <Payload> ListResponse<Payload> array(Payload[] item) {
        return new ListResponse<>(Arrays.asList(item));
    }

    public static <Payload> ListResponse<Payload> supplyList(Supplier<List<Payload>> op) {
        return Try.ofSupplier(op).map(SuccessResponse::list).get();
    }

    public static <Payload> ListResponse<Payload> list(List<Payload> item) {
        if(item == null) throw new NullPointerException();
        else return new ListResponse<>(item);
    }

    public boolean isSuccess() {
        return true;
    }
}
