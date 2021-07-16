package cn.itcast.dao;

import cn.itcast.pojo.Setmeal;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    /**
     * 保存体检套餐基础数据
     *
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 保存体检套餐与检查组关联信息
     *
     * @param setmealId
     * @param checkGroupId
     */
    void setCheckGroupAssociation(@Param("setmealId") Integer setmealId, @Param("checkGroupId") Integer checkGroupId);

    /**
     * 根据条件查询体检套餐数据
     *
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

    /**
     * 查询体检套餐关联的检查组数据
     *
     * @param setmealId
     * @return
     */
    Integer[] findCheckGroupAssociationIds(Integer setmealId);

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 更新体检套餐基础信息
     *
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除体检套餐与体检组的关联信息
     *
     * @param id
     */
    void deleteCheckGroupAssociation(Integer id);

    /**
     * 根据id删除指定体检套餐
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有套餐数据
     *
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情 -- 套餐包含的检查组  -- 检查组包含的检查项
     *
     * @param id
     * @return
     */
    Setmeal findDetailsById(Integer id);

    /**
     * 查询体检套餐的预约数量
     * @return
     */
    List<Map<String, Object>> findSetmealOrderedCount();

}
