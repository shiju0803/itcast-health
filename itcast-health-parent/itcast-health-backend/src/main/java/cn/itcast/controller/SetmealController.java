package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import cn.itcast.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/setmeal")
@CrossOrigin
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    /**
     * 上传体检套餐图片
     *
     * @param imgFile
     * @return
     */
    @PostMapping(path = "/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            //获取原始文件名称
            String fileName = imgFile.getOriginalFilename();
            //截取文件扩展名
            String extension = fileName.substring(fileName.lastIndexOf(".") - 1);
            //构建新文件名称
            fileName = UUID.randomUUID().toString() + extension;

            //执行文件上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);

            return new Result(true, MessageConstant.UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 保存体检套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping(path = "/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);

            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }


    /**
     * 分页查询检查项数据
     *
     * @param queryPageBean
     * @return
     */
    @GetMapping(path = "/findPage")
    public Result findPage(QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.pageQuery(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    public Result findAll() {
        try {
            List<Setmeal> setmeals = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeals);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 根据id查询套餐详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 查询检查项关联的检查组数据
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}/checkgroupIds")
    public Result selectedCheckGroupIds(@PathVariable("id") Integer id) {
        try {
            Integer[] ids = setmealService.selectedCheckGroupIds(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 更新体检套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PutMapping(path = "/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        //查询旧图片数据
        String img = setmealService.findById(setmeal.getId()).getImg();
        try {
            //更新体检套餐数据
            setmealService.update(setmeal, checkgroupIds);

            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    /**
     * 根据id删除体检套餐数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        //查询旧图片数据
        String img = setmealService.findById(id).getImg();
        try {
            setmealService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
}
