package dev.xethh.webtools.dto.base.response;

import dev.xethh.webtools.dto.base.response.itemResponse.FailItemResponse;
import dev.xethh.webtools.dto.base.response.itemResponse.ItemResponse;
import dev.xethh.webtools.dto.base.response.itemResponse.SuccessItemResponse;
import dev.xethh.webtools.dto.base.response.listResponse.FailListResponse;
import dev.xethh.webtools.dto.base.response.listResponse.ListResponse;
import dev.xethh.webtools.dto.base.response.listResponse.SuccessListResponse;
import dev.xethh.webtools.dto.base.response.pageResponse.FailedPageResponse;
import dev.xethh.webtools.dto.base.response.pageResponse.PageResponse;
import dev.xethh.webtools.dto.base.response.pageResponse.SuccessPageResponse;
import dev.xethh.webtools.dto.base.response.simpleResponse.FailedSimpleResponse;
import dev.xethh.webtools.dto.base.response.simpleResponse.SimpleResponse;
import dev.xethh.webtools.dto.base.response.simpleResponse.SuccessSimpleResponse;
import dev.xethh.webtools.exception.EmptyPayload;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResponseUtils {
    public static SimpleResponse success() {
        return SuccessSimpleResponse.instance();
    }

    public static SimpleResponse failed(String errorId, String message) {
        return new FailedSimpleResponse(errorId, message);
    }

    public static <Payload> ItemResponse<Payload> itemSuccess(Payload payload) {
        if (Objects.isNull(payload))
            throw new EmptyPayload();
        return new SuccessItemResponse<>(payload);
    }

    public static <Payload> ItemResponse<Payload> itemFailed(String errorId, String message) {
        return new FailItemResponse<>(errorId, message);
    }

    public static <PayloadItem> ListResponse<PayloadItem> listSuccess(List<PayloadItem> payload) {
        if (Objects.isNull(payload))
            throw new EmptyPayload();
        return new SuccessListResponse<>(payload);
    }
    public static <Payload> ListResponse<Payload> arraySuccess(Payload[] item) {
        if (Objects.isNull(item))
            throw new EmptyPayload();
        return new SuccessListResponse<>(Arrays.asList(item));
    }
    public static <PayloadItem> ListResponse<PayloadItem> listFailed(String errorId, String message) {
        return new FailListResponse<>(errorId, message);
    }

    public static <PayloadItem> PageResponse<PayloadItem> pageSuccess(int page, int pageSize, List<PayloadItem> payload) {
        return new SuccessPageResponse<>(page, pageSize, payload);
    }

    public static <PayloadItem> PageResponse<PayloadItem> pageFailed(int page, int pageSize, String errorId, String message) {
        return new FailedPageResponse<>(page, pageSize, errorId, message);
    }

//    public static <Payload> SuccessItemResponse<Payload> optionalPayload(Optional<Payload> op) {
//        return Try.ofSupplier(() -> op).map(it -> {
//                    if (it.isPresent()) {
//                        return it.get();
//                    } else {
//                        throw new EmptyPayload();
//                    }
//                }
//        ).map(ResponseUtils::itemSuccess).get();
//    }
//
//    public static <Payload> SuccessItemResponse<Payload> supplyPayload(Supplier<Payload> op) {
//        return Try.ofSupplier(op).map(ResponseUtils::payload).get();
//    }
//
//    public static <Payload> ListResponse<Payload> supplyArray(Supplier<Payload[]> op) {
//        return Try.ofSupplier(op).map(ResponseUtils::arraySuccess).get();
//    }
//
//    public static <Payload> SuccessListResponse<Payload> supplyList(Supplier<List<Payload>> op) {
//        return Try.ofSupplier(op).map(ResponseUtils::list).get();
//    }
//
}
