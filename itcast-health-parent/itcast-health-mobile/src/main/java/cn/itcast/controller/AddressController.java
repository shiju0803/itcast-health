package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Address;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    /**
     * 查询所有分院地址信息
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    public Result findAll() {
        try {
            List<Address> addressList = addressService.findAll();
            return new Result(true, MessageConstant.QUERY_ADDRESS_SUCCESS,addressList);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ADDRESS_FAIL);
        }
    }
}
