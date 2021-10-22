package com.socen.ws.system.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socen.ws.system.domain.UserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author lzx
     * @date 2019年03月04日17:46:49
     */
    Boolean deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据角色Id删除该角色的用户关系
     *
     * @param roleId 角色ID
     * @return boolean
     * @author lzx
     * @date 2019年03月04日17:47:16
     */
    Boolean deleteByRoleId(@Param("roleId") Long roleId);
}
