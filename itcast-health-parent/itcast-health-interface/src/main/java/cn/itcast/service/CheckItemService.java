package cn.itcast.service;



import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.CheckItem;

import java.util.List;

/**
 * @Author 黑马程序员
 */
public interface CheckItemService {

    /**
     * 薪资检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项信息
     *
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 更新检查项数据
     *
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 查询所有检查项信息
     *
     * @return
     */
    List<CheckItem> findAll();

}
