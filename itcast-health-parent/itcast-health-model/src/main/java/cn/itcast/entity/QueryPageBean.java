package cn.itcast.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装查询条件
 */
@Data
public class QueryPageBean implements Serializable {
    //页码
    protected Integer currentPage;
    //每页记录数
    protected Integer pageSize;
    //查询条件
    protected String queryString;

}