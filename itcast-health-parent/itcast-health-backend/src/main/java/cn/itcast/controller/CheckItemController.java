package cn.itcast.controller;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.QueryPageBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 黑马程序员
 */
@RestController
@RequestMapping(path = "/checkitem")
@CrossOrigin
public class CheckItemController {


    @Reference
    private CheckItemService checkItemService; // com.itheima.service.CheckItemService

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     */
    @PostMapping(path = "/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 分页查询检查项数据
     *
     * @param queryPageBean
     * @return
     */
    @GetMapping(path = "/findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkItemService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id删除检查项
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result deleteById(@PathVariable Integer id) {
        try {
            //删除检查项
            checkItemService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id查询数据详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findById(@PathVariable int id) {
        try {
            //查询检查项
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 确定编辑, 更新数据
     *
     * @param checkItem
     * @return
     */
    @PutMapping(path = "/edit")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询所有检查项数据
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findAll() {
        try {
            List<CheckItem> items = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, items);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
