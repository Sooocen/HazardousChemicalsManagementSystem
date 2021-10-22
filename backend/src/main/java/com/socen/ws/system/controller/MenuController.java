package com.socen.ws.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.controller.BaseController;
import com.socen.ws.common.domain.router.VueRouter;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.system.domain.Menu;
import com.socen.ws.system.manager.UserManager;
import com.socen.ws.system.service.MenuService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单管理",description = "菜单管理")
public class MenuController extends BaseController {

    private String message;

    @Autowired
    private UserManager userManager;
    @Autowired
    private MenuService menuService;

    @GetMapping("/{username}")
    @ApiOperation("通过用户名获取路由")
    public ArrayList<VueRouter<Menu>> getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userManager.getUserRouters(username);
    }

    @GetMapping
    @RequiresPermissions("menu:view")
    @ApiOperation("获取所有菜单")
    public Map<String, Object> menuList(Menu menu) {
        return this.menuService.findMenus(menu);
    }

    @Log("新增菜单/按钮")
    @PostMapping
    @RequiresPermissions("menu:add")
    @ApiOperation("添加菜单，按钮")
    public void addMenu(@Valid Menu menu) throws WsException {
        try {
            this.menuService.createMenu(menu);
        } catch (Exception e) {
            message = "新增菜单/按钮失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @Log("删除菜单/按钮")
    @DeleteMapping("/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ApiOperation("删除菜单/按钮")
    public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) throws WsException {
        try {
            String[] ids = menuIds.split(StringPool.COMMA);
            this.menuService.deleteMeuns(ids);
        } catch (Exception e) {
            message = "删除菜单/按钮失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @Log("修改菜单/按钮")
    @PutMapping
    @RequiresPermissions("menu:update")
    @ApiOperation("修改菜单/按钮")
    public void updateMenu(@Valid Menu menu) throws WsException {
        try {
            this.menuService.updateMenu(menu);
        } catch (Exception e) {
            message = "修改菜单/按钮失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("menu:export")
    @ApiOperation("导出Excel")
    public void export(Menu menu, HttpServletResponse response) throws WsException {
        try {
            List<Menu> menus = this.menuService.findMenuList(menu);
            ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}
