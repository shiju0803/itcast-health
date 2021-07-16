package cn.itcast.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回结果
 * {flag:true/false ,message:"xxx",data:""}
 */
@Data
public class Result implements Serializable{
    //执行结果，true为执行成功 false为执行失败
    private boolean flag;
    //返回结果信息，主要用于页面提示信息
    private String message;
    //返回数据
    private Object data;

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }
}
