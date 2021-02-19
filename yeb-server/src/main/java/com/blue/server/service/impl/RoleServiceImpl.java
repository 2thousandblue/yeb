package com.blue.server.service.impl;

import com.blue.server.PO.Role;
import com.blue.server.mapper.RoleMapper;
import com.blue.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-02-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
