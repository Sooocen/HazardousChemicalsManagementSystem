package com.socen.ws.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.socen.ws.system.domain.LoginLog;

public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog(LoginLog loginLog);
}
