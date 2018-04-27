package cn.wolfcode.wms.util;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class PageResult {

    public static final PageResult EMPTY_PAGE = new
            PageResult(1,1,0, Collections.EMPTY_LIST);

    private int pageSize;
    private int currentPage;

    private Integer totalCount;
    private List<?> data;

    private int endPage;
    private int prevPage;
    private int nextPage;

    public PageResult(int pageSize, int currentPage, Integer totalCount, List<?> data) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.data = data;

        if (totalCount <= pageSize) {
            endPage = 1;
            prevPage = 1;
            nextPage = 1;
            return;
        }

        endPage = (totalCount + pageSize - 1) / pageSize;

        prevPage = currentPage > 1 ? currentPage - 1 : 1;

        nextPage = currentPage < endPage ? currentPage + 1 : endPage;
    }
}
