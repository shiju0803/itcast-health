package cn.itcast.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PayLog implements Serializable {

    private String orderId;
    private String transactionId;
    private Double money;
    private Integer payStatus;
    private Date payTime;
    private Date createTime;
}
