package cn.itcast.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private Integer id;
    private String code;
    private String name;
    private String address;
    private String detail;
    private String coordinate;
    private Integer enable;

}
