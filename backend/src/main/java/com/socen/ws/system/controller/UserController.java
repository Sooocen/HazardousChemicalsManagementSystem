package com.socen.ws.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.controller.BaseController;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.common.utils.MD5Util;
import com.socen.ws.system.domain.Role;
import com.socen.ws.system.domain.User;
import com.socen.ws.system.domain.UserConfig;
import com.socen.ws.system.service.RoleService;
import com.socen.ws.system.service.UserConfigService;
import com.socen.ws.system.service.UserService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("user")
@Api(tags = "用户管理",description = "用户管理")
public class UserController extends BaseController {

    private String message;

    @Autowired
    private UserService userService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private RoleService roleService;

    @ApiOperation("判断用户名是否存在")
    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @ApiOperation("用户信息")
    @GetMapping("/{username}")
    public User detail(@NotBlank(message = "{required}") @PathVariable String username) {
        User user=this.userService.findByName(username);
        //修复用户修改自己的个人信息第二次提示roleId不能为空
        List<Role> roles=roleService.findUserRole(username);
        List<Long> roleIds=roles.stream().map(role ->role.getRoleId()).collect(Collectors.toList());
        String roleIdStr=StringUtils.join(roleIds.toArray(new Long[roleIds.size()]),",");
        user.setRoleId(roleIdStr);
        return user;
    }

    @GetMapping
    @RequiresPermissions("user:view")
    @ApiOperation("获取用户列表")
    public Map<String, Object> userList(QueryRequest queryRequest, User user) {
        return getDataTable(userService.findUserDetail(user, queryRequest));
    }

    @Log("新增用户")
    @PostMapping
    @RequiresPermissions("user:add")
    @ApiOperation("添加用户")
    public void addUser(@Valid User user) throws WsException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            message = "新增用户失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @Log("修改用户")
    @PutMapping
    @RequiresPermissions("user:update")
    @ApiOperation("修改用户")
    public void updateUser(@Valid User user) throws WsException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            message = "修改用户失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @Log("删除用户")
    @DeleteMapping("/{userIds}")
    @RequiresPermissions("user:delete")
    @ApiOperation("删除用户")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws WsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            message = "删除用户失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PutMapping("profile")
    @ApiOperation("修改个人信息")
    public void updateProfile(@Valid User user) throws WsException {
        try {
            this.userService.updateProfile(user);
        } catch (Exception e) {
            message = "修改个人信息失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PutMapping("avatar")
    @ApiOperation("修改头像")
    public void updateAvatar(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String avatar) throws WsException {
        try {
            this.userService.updateAvatar(username, avatar);
        } catch (Exception e) {
            message = "修改头像失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PutMapping("userconfig")
    @ApiOperation("修改个性化配置")
    public void updateUserConfig(@Valid UserConfig userConfig) throws WsException {
        try {
            this.userConfigService.update(userConfig);
        } catch (Exception e) {
            message = "修改个性化配置失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @GetMapping("password/check")
    @ApiOperation("检查密码")
    public boolean checkPassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) {
        String encryptPassword = MD5Util.encrypt(username, password);
        User user = userService.findByName(username);
        if (user != null) {
            return StringUtils.equals(user.getPassword(), encryptPassword);
        }
        else {
            return false;
        }
    }

    @PutMapping("password")
    @ApiOperation("修改密码")
    public void updatePassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws WsException {
        try {
            userService.updatePassword(username, password);
        } catch (Exception e) {
            message = "修改密码失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PutMapping("password/reset")
    @RequiresPermissions("user:reset")
    @ApiOperation("重置用户密码")
    public void resetPassword(@NotBlank(message = "{required}") String usernames) throws WsException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr);
        } catch (Exception e) {
            message = "重置用户密码失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("user:export")
    @ApiOperation("导出Excel")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws WsException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}
