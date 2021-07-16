package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    /**
     * 新增检查组成功
     *
     * @param checkGroup
     * @param checkItems
     */
    void add(CheckGroup checkGroup, Integer[] checkItems);

    /**
     * 分页查询检查组信息
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据ID查询检查组信息
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 查询检查组关联的检查项ID
     *
     * @param checkGroupId
     * @return
     */
    Integer[] selectedCheckItemsId(Integer checkGroupId);

    /**
     * 更新检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组数据
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有检查组数据
     *
     * @return
     */
    List<CheckGroup> findAll();
}
