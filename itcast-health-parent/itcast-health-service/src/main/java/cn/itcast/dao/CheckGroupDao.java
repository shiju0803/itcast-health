package cn.itcast.dao;

import cn.itcast.pojo.CheckGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    /**
     * 保存检查组基本信息
     *
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 保存检查组和检查项关联信息
     *
     * @param checkGroupId
     * @param checkItemId
     */
    void setCheckItemsAssociation(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    /**
     * 条件查询检查组信息
     *
     * @param queryString
     * @return
     */
    Page<CheckGroup> selectByCondition(String queryString);

    /**
     * 根据检查组id查询检查组信息
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 查询指定检查组关联的检查项id
     *
     * @param checkGroupId
     * @return
     */
    Integer[] findCheckItemsAssociationIds(Integer checkGroupId);

    /**
     * 根据Id更新检查组基本信息
     *
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项关联信息
     *
     * @param id
     */
    void deleteCheckItemsAssociation(Integer id);

    /**
     * 根据id删除检查项信息
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有检查组数据
     *
     * @return
     */
    List<CheckGroup> findAl();

    /**
     * 根据套餐ID查询该套餐下的检查组详细信息, 检查组中包含检查项信息
     *
     * @param setmealId
     * @return
     */
    List<CheckGroup> findBySetmealIdWithDetails(Integer setmealId);

}
