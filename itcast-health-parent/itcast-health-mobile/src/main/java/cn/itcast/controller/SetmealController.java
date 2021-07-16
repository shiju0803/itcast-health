package cn.itcast.controller;

import cn.itcast.entity.Result;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.itcast.constant.MessageConstant;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有套餐
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    public Result findAll() {
        try {
            //查询所有套餐数据
            List<Setmeal> setmeals = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeals);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 查询套餐详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            //根据id查询套餐详情
            Setmeal setmeal = setmealService.findSetmealDetail(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
