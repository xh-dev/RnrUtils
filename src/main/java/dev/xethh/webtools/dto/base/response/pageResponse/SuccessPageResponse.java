package dev.xethh.webtools.dto.base.response.pageResponse;

import dev.xethh.webtools.dto.base.response.listResponse.SuccessListResponse;

import java.util.List;

public class SuccessPageResponse<PayloadItem> extends SuccessListResponse<PayloadItem> implements PageResponse<PayloadItem> {
    private final int page;
    private final int pageSize;

    public SuccessPageResponse(int page, int pageSize, List<PayloadItem> payload) {
        super(payload);
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

    public PageResponse<PayloadItem> of(int page, int pageSize, List<PayloadItem> payload){
        return new SuccessPageResponse<>(page, pageSize, payload);
    }

}
