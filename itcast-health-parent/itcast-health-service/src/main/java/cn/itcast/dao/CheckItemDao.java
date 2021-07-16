package cn.itcast.dao;


import cn.itcast.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author 黑马程序员
 */
public interface CheckItemDao {

    /**
     * 新增检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 根据条件查询检查项
     *
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询该检查项是否被检查项组引用
     *
     * @param id
     * @return
     */
    int selectCheckItemReferencedCount(Integer id);

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
    void update(CheckItem checkItem);

    /**
     * 查询所有检查项信息
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 根据检查组id , 查询其关联的检查项信息
     * @param checkgroupId
     * @return
     */
    List<CheckItem> findByCheckGroupId(Integer checkgroupId);

}
