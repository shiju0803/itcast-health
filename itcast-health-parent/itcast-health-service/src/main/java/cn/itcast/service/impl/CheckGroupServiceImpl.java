package cn.itcast.service.impl;

import cn.itcast.dao.CheckGroupDao;
import cn.itcast.entity.PageResult;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItems) {
        //保存检查组基本信息
        checkGroupDao.add(checkGroup);
        //关联保存检查项信息
        if (checkItems != null) {
            Integer checkGroupId = checkGroup.getId();
            setCheckGroupAndCheckItem(checkGroupId, checkItems);
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public Integer[] selectedCheckItemsId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemsAssociationIds(checkGroupId);
    }

    @Transactional
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组基本信息
        checkGroupDao.update(checkGroup);
        //删除检查组关联检查项信息
        checkGroupDao.deleteCheckItemsAssociation(checkGroup.getId());
        //设置检查组关联检查项信息
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        //删除关联信息
        checkGroupDao.deleteCheckItemsAssociation(id);
        //删除检查组基本信息
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAl();
    }

    /**
     * 设置检查组和检查项的关联信息
     *
     * @param checkGroupId
     * @param checkItemIds
     */
    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkItemIds) {
        Stream.of(checkItemIds).forEach(checkItemId -> checkGroupDao.setCheckItemsAssociation(checkGroupId, checkItemId));

        //for (Integer checkItemId : checkItemIds) {
        //    checkGroupDao.setCheckItemsAssociation(checkGroupId, checkItemId);
        //}
    }


}
