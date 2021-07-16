package cn.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/checkgroup")
@CrossOrigin
public class CheckGroupController {

    @Reference
    private CheckGroupService checkgroupService;

    /**
     * 保存检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping(path = "/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkgroupService.add(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询检查组信息
     *
     * @param queryPageBean
     * @return
     */
    @GetMapping(path = "/findPage")
    public Result findPage(QueryPageBean queryPageBean) {
        //分页查询检查组信息
        try {
            PageResult pageResult = checkgroupService.pageQuery(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据id查询检查项详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            CheckGroup checkGroup = checkgroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询检查组下的检查项ID集合
     *
     * @param checkGroupId
     * @return
     */
    @GetMapping(path = "/{id}/checkItems")
    public Result selectedCheckItemsId(@PathVariable("id") Integer checkGroupId) {
        try {
            Integer[] checkItemIds = checkgroupService.selectedCheckItemsId(checkGroupId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 更新检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PutMapping(path = "/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkgroupService.update(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 删除检查组信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        try {
            checkgroupService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查组信息
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> checkGroupList = checkgroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
