package dev.xethh.webtools.dto.base.response.pageResponse;

import dev.xethh.webtools.dto.base.response.listResponse.FailListResponse;

public class FailedPageResponse<PayloadItem> extends FailListResponse<PayloadItem> implements PageResponse<PayloadItem> {
    private final int page;
    private final int pageSize;

    public FailedPageResponse(int page, int pageSize, String traceId, String message) {
        super(traceId, message);
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public long page() {
        return page;
    }

    @Override
    public long pageSize() {
        return pageSize;
    }

    public PageResponse<PayloadItem> of(int page, int pageSize, String traceId, String message){
        return new FailedPageResponse<>(page, pageSize, traceId, message);
    }

}
