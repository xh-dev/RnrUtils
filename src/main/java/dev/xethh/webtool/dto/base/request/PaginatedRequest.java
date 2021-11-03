package dev.xethh.webtool.dto.base.request;

import dev.xethh.webtool.utils.Sorting;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PaginatedRequest extends Request {
    private Integer page, pageSize = -1;
    private String sorting;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    protected Optional<String> isSortingValid(String s) {
        if (s == null) return Optional.empty();
        else if (s.equals("")) return Optional.empty();
        else {
            var matchPredicate = Pattern.compile("^[-]?[a-zA-Z._]+$").asMatchPredicate();
            var processedString = Arrays.stream(s.split(",")).map(String::trim).collect(Collectors.toList());
            return processedString.stream().allMatch(matchPredicate) ? Optional.of(String.join(",", processedString)) : Optional.empty();
        }
    }

    protected boolean hasPagination() {
        return page > -1 && pageSize > -1;
    }

    public Optional<Sorting> asSortingOpt() {
        return isSortingValid(sorting).
                map(it -> {
                    Set<Sorting.SortItem> sortItems = new HashSet<>();
                    for (String s : it.split(",")) {
                        var sortDirection = s.startsWith("-") ? Sorting.SortDirection.Desc : Sorting.SortDirection.Asc;
                        var name = sortDirection == Sorting.SortDirection.Asc ? s : s.substring(1);
                        var item = new Sorting.SortItem(name, sortDirection);
                        sortItems.add(item);
                    }
                    return new Sorting(new ArrayList<>(sortItems));
                });
    }
}
