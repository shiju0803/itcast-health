package cn.itcast.service;



import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    /**
     * 新增体检套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询套餐数据
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 查询检查套餐关联的检查组数据
     *
     * @param id
     * @return
     */
    Integer[] selectedCheckGroupIds(Integer id);

    /**
     * 根据id查询体检套餐基本信息
     *
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 更新体检套餐数据
     *
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据id删除体检套餐数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有套餐数据
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情 -- 套餐包含的检查组  -- 检查组包含的检查项
     * @param id
     * @return
     */
    Setmeal findSetmealDetail(Integer id);

    /**
     * 查询每个体检套餐的预约数量
     * @return
     */
    List<Map<String, Object>> findSetmealOrderedCount();

}
