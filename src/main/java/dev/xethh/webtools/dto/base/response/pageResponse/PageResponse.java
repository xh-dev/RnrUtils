package dev.xethh.webtools.dto.base.response.pageResponse;

import dev.xethh.webtools.dto.base.response.listResponse.ListResponse;

public interface PageResponse<PayloadItem> extends ListResponse<PayloadItem> {
    long page();
    long pageSize();

    default long itemCount(){
        return isSuccess()?getPayload().size():0;
    };

    default long offset(){
        return (page()-1) * pageSize();
    }

    default long endIndex(){
        return offset()+pageSize();
    }
}
