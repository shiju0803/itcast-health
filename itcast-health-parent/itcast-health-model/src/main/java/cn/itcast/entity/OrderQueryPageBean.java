package cn.itcast.entity;

import lombok.Data;

@Data
public class OrderQueryPageBean extends QueryPageBean {

    private String minTime;

    private String maxTime ;

    private String orderType ;

    private Integer  payStatus ;
}
