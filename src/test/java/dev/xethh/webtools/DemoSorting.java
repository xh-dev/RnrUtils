package dev.xethh.webtools;

import dev.xethh.webtool.dto.base.request.PaginatedRequest;

public class DemoSorting {
    public static void main(String[] args){
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
