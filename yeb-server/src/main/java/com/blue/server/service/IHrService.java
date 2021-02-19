package com.blue.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blue.server.PO.Hr;
import com.blue.server.PO.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blue
 * @since 2021-02-16
 */

public interface IHrService extends IService<Hr> {

    /**
     * 登录后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    Hr getHrByUsername(String username);
}
