package com.blue.server.service.impl;

import com.blue.server.PO.Menu;
import com.blue.server.mapper.MenuMapper;
import com.blue.server.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
