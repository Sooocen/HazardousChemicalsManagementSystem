package com.socen.ws.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.socen.ws.common.utils.AddressUtil;
import com.socen.ws.common.utils.HttpContextUtil;
import com.socen.ws.common.utils.IPUtil;
import com.socen.ws.system.dao.LoginLogMapper;
import com.socen.ws.system.domain.LoginLog;
import com.socen.ws.system.service.LoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service("loginLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }
}
