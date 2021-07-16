package cn.itcast.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 */
@Data
public class Menu implements Serializable{
    private Integer id;
    private String name; // 菜单名称
    private String linkUrl; // 访问路径
    private Integer priority; // 优先级（用于排序）
    private String icon;//图标
    private String description; // 描述
    private Integer parentMenuId;//父菜单id
    private Integer level ;
    private Set<Role> roles = new HashSet<Role>();//角色集合
    private List<Menu> children = new ArrayList<>();//子菜单集合

}
