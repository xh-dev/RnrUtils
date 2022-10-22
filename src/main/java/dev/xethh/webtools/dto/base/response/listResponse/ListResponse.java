package dev.xethh.webtools.dto.base.response.listResponse;

import dev.xethh.webtools.dto.base.response.itemResponse.ItemResponse;

import java.util.List;

public interface ListResponse<PayloadItem> extends ItemResponse<List<PayloadItem>> {
}
