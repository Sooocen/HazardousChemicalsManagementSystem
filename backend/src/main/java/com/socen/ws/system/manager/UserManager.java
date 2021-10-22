package com.socen.ws.system.manager;

import com.socen.ws.common.domain.router.RouterMeta;
import com.socen.ws.common.domain.router.VueRouter;
import com.socen.ws.common.service.CacheService;
import com.socen.ws.common.utils.TreeUtil;
import com.socen.ws.common.utils.WsUtil;
import com.socen.ws.gen.entity.Company;
import com.socen.ws.gen.entity.Zone;
import com.socen.ws.gen.service.ICompanyService;
import com.socen.ws.gen.service.IZoneService;
import com.socen.ws.system.domain.Menu;
import com.socen.ws.system.domain.Role;
import com.socen.ws.system.domain.User;
import com.socen.ws.system.domain.UserConfig;
import com.socen.ws.system.service.MenuService;
import com.socen.ws.system.service.RoleService;
import com.socen.ws.system.service.UserConfigService;
import com.socen.ws.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 封装一些和 User相关的业务操作
 */
@Service
public class UserManager {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private IZoneService zoneService;
    @Autowired
    private ICompanyService companyService;


    /**
     * 通过用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    public User getUser(String username) {
        return WsUtil.selectCacheByTemplate(
                () -> this.cacheService.getUser(username),
                () -> this.userService.findByName(username));
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    public Set<String> getUserRoles(String username) {
        List<Role> roleList = WsUtil.selectCacheByTemplate(
                () -> this.cacheService.getRoles(username),
                () -> this.roleService.findUserRole(username));
        return roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    public Set<String> getUserPermissions(String username) {
        List<Menu> permissionList = WsUtil.selectCacheByTemplate(
                () -> this.cacheService.getPermissions(username),
                () -> this.menuService.findUserPermissions(username));
        return permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    /**
     * 通过用户名构建 Vue路由
     *
     * @param username 用户名
     * @return 路由集合
     */
    public ArrayList<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.menuService.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setIcon(menu.getIcon());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(true, null));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    /**
     * 通过用户名构建安监局角色Vue路由
     * @param username
     * @return
     */
    public ArrayList<VueRouter<Menu>> getOfficeUserRouters(String username){
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = new ArrayList<>();
        List<Menu> userMenus = this.menuService.findUserMenus(username);
        List<Zone> zones = zoneService.findZoneByUsername(username);
        zones.forEach(zone -> {
            menus.add(setZoneMenu(zone));
            List<Company> companies = companyService.findCompanyByZoneId(zone.getZoneId().toString());
            companies.forEach(company -> {
                menus.add(setCompanyMenu(company));
                userMenus.forEach(userMenu ->{
                   // userMenu.setParentId();
                });
            });
        });
        return null;
    }

    private Menu setZoneMenu(Zone zone){
        Menu menu = new Menu();
        menu.setMenuId(zone.getZoneId());
        menu.setParentId(0L);
        menu.setMenuName(zone.getZoneName());
        menu.setPath("/"+zone.getZoneId());
        menu.setComponent("PageView");
        menu.setPerms("");
        menu.setIcon("global");
        menu.setType("1");
        return menu;
    }

    private Menu setCompanyMenu(Company company){
        Menu menu = new Menu();
        menu.setMenuId(company.getCompanyId());
        menu.setParentId(company.getZoneId());
        menu.setMenuName(company.getCompanyName());
        menu.setPath("/"+company.getZoneId()+"/"+company.getCompanyId());
        menu.setComponent("PageView");
        menu.setPerms("");
        menu.setIcon("home");
        menu.setType("1");
        return menu;
    }

    /**
     * 通过用户 ID获取前端系统个性化配置
     *
     * @param userId 用户 ID
     * @return 前端系统个性化配置
     */
    public UserConfig getUserConfig(String userId) {
        return WsUtil.selectCacheByTemplate(
                () -> this.cacheService.getUserConfig(userId),
                () -> this.userConfigService.findByUserId(userId));
    }

    /**
     * 将用户相关信息添加到 Redis缓存中
     *
     * @param user user
     */
    public void loadUserRedisCache(User user) throws Exception {
        // 缓存用户
        cacheService.saveUser(user.getUsername());
        // 缓存用户角色
        cacheService.saveRoles(user.getUsername());
        // 缓存用户权限
        cacheService.savePermissions(user.getUsername());
        // 缓存用户个性化配置
        cacheService.saveUserConfigs(String.valueOf(user.getUserId()));
    }

    /**
     * 将用户角色和权限添加到 Redis缓存中
     *
     * @param userIds userIds
     */
    public void loadUserPermissionRoleRedisCache(List<String> userIds) throws Exception {
        for (String userId : userIds) {
            User user = userService.getById(userId);
            // 缓存用户角色
            cacheService.saveRoles(user.getUsername());
            // 缓存用户权限
            cacheService.savePermissions(user.getUsername());
        }
    }

    /**
     * 通过用户 id集合批量删除用户 Redis缓存
     *
     * @param userIds userIds
     */
    public void deleteUserRedisCache(String... userIds) throws Exception {
        for (String userId : userIds) {
            User user = userService.getById(userId);
            if (user != null) {
                cacheService.deleteUser(user.getUsername());
                cacheService.deleteRoles(user.getUsername());
                cacheService.deletePermissions(user.getUsername());
            }
            cacheService.deleteUserConfigs(userId);
        }
    }

}

