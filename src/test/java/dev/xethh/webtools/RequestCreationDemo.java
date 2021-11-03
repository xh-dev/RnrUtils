package dev.xethh.webtools;

import dev.xethh.webtool.dto.base.request.PaginatedRequest;
import dev.xethh.webtool.dto.base.request.Request;

public class RequestCreationDemo {
    /**
     * Simple Request, just extends {@link Request}
     */
    public static class SomeRequest extends Request{
        private String fieldA;

        public String getFieldA() {
            return fieldA;
        }

        public void setFieldA(String fieldA) {
            this.fieldA = fieldA;
        }
    }

    /**
     * Paginated Request, just extends {@link PaginatedRequest}
     */
    public static class SomePaginatedRequest extends PaginatedRequest {
        private String fieldB;

        public String getFieldB() {
            return fieldB;
        }

        public void setFieldB(String fieldB) {
            this.fieldB = fieldB;
        }
    }

    public static void main(String[] args){
    }
}
