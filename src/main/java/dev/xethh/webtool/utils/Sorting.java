package dev.xethh.webtool.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sorting {
    private List<SortItem> items;

    public Sorting(List<SortItem> items) {
        this.items = items;
    }

    public List<SortItem> getItems() {
        return new ArrayList<>(items);
    }

    public static enum SortDirection {
        Asc, Desc
    }

    public static class SortItem {
        private String field;
        private SortDirection direction;

        public SortItem(String field, SortDirection direction) {
            this.field = field;
            this.direction = direction;
        }

        public String getField() {
            return field;
        }

        public SortDirection getDirection() {
            return direction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SortItem sortItem = (SortItem) o;
            return Objects.equals(field, sortItem.field);
        }

        @Override
        public int hashCode() {
            return Objects.hash(field);
        }

        @Override
        public String toString() {
            return "SortItem{" +
                    "field='" + field + '\'' +
                    ", direction=" + direction +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Sorting{" +
                "items=" + items +
                '}';
    }
}
