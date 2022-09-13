package cn.guanjm.common.vo;

import lombok.Data;

import java.util.List;

// vo view object 专门视图对象
@Data
public class PageResult<T> {
    private Long total;
    private Integer totalPage; // 总页数
    private List<T> items; // 当前页的数据

    public PageResult(){
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
