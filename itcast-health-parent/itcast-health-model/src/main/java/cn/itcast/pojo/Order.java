package cn.itcast.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约信息
 */
@Data
public class Order implements Serializable {
    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    private String id;
    private Integer memberId;//会员id
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date orderDate;//预约日期
    private String orderType;//预约类型 电话预约/微信预约
    private Integer orderStatus;//预约状态 0 : 未确定, 1 : 已确定
    private Integer setmealId;//体检套餐id
    private Integer arrival;  //是否到诊
    private String phoneNumber;
    private String name;
    private String idCard;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String sex;
    private Short married;
    private Integer addressId;
    private Integer payStatus ;

    private Member member; //预约客户信息

    private Setmeal setmeal; //预约套餐信息

    private Address address ; //预约体检机构

}
