package cn.itcast.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */

@Data
public class PageResult implements Serializable{
    //总记录数
    private Long total;
    //当前页结果
    private List rows;
    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
}
