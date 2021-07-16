package cn.itcast.service.impl;

import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Setmeal;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.itcast.dao.SetmealDao;
import cn.itcast.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //保存体检套餐基础数据
        setmealDao.add(setmeal);
        //保存体检套餐关联体检组数据
        if (checkgroupIds != null) {
            setCheckGroupAssociation(setmeal.getId(), checkgroupIds);
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Integer[] selectedCheckGroupIds(Integer setMealId) {
        return setmealDao.findCheckGroupAssociationIds(setMealId);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Transactional
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新体检套餐基础信息
        setmealDao.update(setmeal);
        //删除之前体检套餐与体检组的关联信息
        setmealDao.deleteCheckGroupAssociation(setmeal.getId());
        //保存新的体检套餐与体检组的关联信息
        if (checkgroupIds != null) {
            setCheckGroupAssociation(setmeal.getId(), checkgroupIds);
        }
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        //删除体检套餐与体检组的关联数据
        setmealDao.deleteCheckGroupAssociation(id);
        //删除体检套餐数据
        setmealDao.deleteById(id);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findSetmealDetail(Integer id) {
        return setmealDao.findDetailsById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealOrderedCount() {
        return setmealDao.findSetmealOrderedCount();
    }

    /**
     * 保存检查组和检查项关联信息
     *
     * @param setmealId
     * @param checkGroupIds
     */
    private void setCheckGroupAssociation(Integer setmealId, Integer[] checkGroupIds) {
        Stream.of(checkGroupIds).forEach(checkGroupId -> setmealDao.setCheckGroupAssociation(setmealId, checkGroupId));
    }

}
