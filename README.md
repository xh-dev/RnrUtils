## Dependency

maven
```xml
    <dependency>
        <groupId>dev.xethh.webtools</groupId>
        <artifactId>RNRUtils</artifactId>
        <version>0.0.5</version>
    </dependency>
```

## Demo

### Request creation

```java
package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.request.PaginatedRequest;
import dev.xethh.webtools.dto.base.request.Request;

public class RequestCreationDemo {
    /**
     * Simple Request, just extends {@link Request}
     */
    public static class SomeRequest extends Request {
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

    public static void main(String[] args) {
    }
}
```

### Paginated Request

```java
package dev.xethh.webtools;

import dev.xethh.webtools.dto.base.request.PaginatedRequest;

public class DemoSorting {
    public static void main(String[] args) {
        var request = new PaginatedRequest();
        request.setSorting("+b,c,-d");
        // Optional.empty
        System.out.println(request.asSortingOpt());

        request.setSorting(",-b,c,-d");
        //Optional[
        //  Sorting{
        //    items=[
        //      SortItem{field='b', direction=Desc}, 
        //      SortItem{field='c', direction=Asc}, 
        //      SortItem{field='d', direction=Desc}
        //    ]
        //  }
        //]
        System.out.println(request.asSortingOpt());

        request.setSorting("a,-b,c,-d");
        // Optional[
        //  Sorting{
        //    items=[
        //        SortItem{field='a', direction=Asc},
        //        SortItem{field='b', direction=Desc},
        //        SortItem{field='c', direction=Asc},
        //        SortItem{field='d', direction=Desc}
        //    ]
        //  }
        //]
        System.out.println(request.asSortingOpt());
    }
}

```

### Response creation

```java
import dev.xethh.webtools.dto.base.response.FailResponse;
import dev.xethh.webtools.dto.base.response.SuccessResponse;

import java.util.Arrays;
import java.util.Optional;

public class Demo {
    public static void main(String[] args) {
        //Create Response without payload
        SuccessResponse.noPayload();

        //Create Response with single item payload
        SuccessResponse.payload("data");
        SuccessResponse.supplyPayload(() -> "data");
        SuccessResponse.optionalPayload(Optional.of( "data"));

        //Create Response with list of item as payload
        SuccessResponse.list(Arrays.asList("data1", "data2"));
        SuccessResponse.supplyList(()->Arrays.asList("data1","data2"));
        SuccessResponse.array(new String[]{"data1", "data2"});
        SuccessResponse.supplyArray(()->Arrays.asList("data1","data2"));

        //Create Response of error
        FailResponse.error("Trace Id", "Error message");
    }
}
```
