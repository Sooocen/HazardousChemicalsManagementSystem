package com.socen.ws.system.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socen.ws.system.domain.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

	List<Role> findUserRole(String userName);

}
