package com.socen.ws.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.socen.ws.common.domain.Tree;
import com.socen.ws.common.domain.WsConstant;
import com.socen.ws.common.utils.TreeUtil;
import com.socen.ws.system.dao.MenuMapper;
import com.socen.ws.system.domain.Menu;
import com.socen.ws.system.manager.UserManager;
import com.socen.ws.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserManager userManager;

    @Override
    public List<Menu> findUserPermissions(String username) {
        return this.baseMapper.findUserPermissions(username);
    }

    @Override
    public List<Menu> findUserMenus(String username) {
        return this.baseMapper.findUserMenus(username);
    }

    @Override
    public Map<String, Object> findMenus(Menu menu) {
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            findMenuCondition(queryWrapper, menu);
            List<Menu> menus = baseMapper.selectList(queryWrapper);

            List<Tree<Menu>> trees = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            buildTrees(trees, menus, ids);

            result.put("ids", ids);
            if (StringUtils.equals(menu.getType(), WsConstant.TYPE_BUTTON)) {
                result.put("rows", trees);
            } else {
                Tree<Menu> menuTree = TreeUtil.build(trees);
                result.put("rows", menuTree);
            }

            result.put("total", menus.size());
        } catch (NumberFormatException e) {
            log.error("??????????????????", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }


    @Override
    public List<Menu> findMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        findMenuCondition(queryWrapper, menu);
        queryWrapper.orderByAsc(Menu::getMenuId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createMenu(Menu menu) {
        menu.setCreateTime(new Date());
        setMenu(menu);
        this.save(menu);
    }

    @Override
    @Transactional
    public void updateMenu(Menu menu) throws Exception {
        menu.setModifyTime(new Date());
        setMenu(menu);
        baseMapper.updateById(menu);

        // ?????????????????????/?????????????????????
        List<String> userIds = this.baseMapper.findUserIdsByMenuId(String.valueOf(menu.getMenuId()));
        // ???????????????????????????????????????????????? Redis???
        this.userManager.loadUserPermissionRoleRedisCache(userIds);
    }

    @Override
    @Transactional
    public void deleteMeuns(String[] menuIds) throws Exception {
        this.delete(Arrays.asList(menuIds));
        for (String menuId : menuIds) {
            // ?????????????????????/?????????????????????
            List<String> userIds = this.baseMapper.findUserIdsByMenuId(String.valueOf(menuId));
            // ???????????????????????????????????????????????? Redis???
            this.userManager.loadUserPermissionRoleRedisCache(userIds);
        }
    }

    private void buildTrees(List<Tree<Menu>> trees, List<Menu> menus, List<String> ids) {
        menus.forEach(menu -> {
            ids.add(menu.getMenuId().toString());
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            tree.setTitle(tree.getText());
            tree.setIcon(menu.getIcon());
            tree.setComponent(menu.getComponent());
            tree.setCreateTime(menu.getCreateTime());
            tree.setModifyTime(menu.getModifyTime());
            tree.setPath(menu.getPath());
            tree.setOrder(menu.getOrderNum());
            tree.setPermission(menu.getPerms());
            tree.setType(menu.getType());
            trees.add(tree);
        });
    }

    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setPath(null);
            menu.setIcon(null);
            menu.setComponent(null);
        }
    }

    private void findMenuCondition(LambdaQueryWrapper<Menu> queryWrapper, Menu menu) {
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.eq(Menu::getMenuName, menu.getMenuName());
        }
        if (StringUtils.isNotBlank(menu.getType())) {
            queryWrapper.eq(Menu::getType, menu.getType());
        }
        if (StringUtils.isNotBlank(menu.getCreateTimeFrom()) && StringUtils.isNotBlank(menu.getCreateTimeTo())) {
            queryWrapper
                    .ge(Menu::getCreateTime, menu.getCreateTimeFrom())
                    .le(Menu::getCreateTime, menu.getCreateTimeTo());
        }
    }


    private void delete(List<String> menuIds) {
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            this.delete(menuIdList);
        }
    }

}
