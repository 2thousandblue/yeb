package com.blue.server.service.impl;

import com.blue.server.PO.Oplog;
import com.blue.server.mapper.OplogMapper;
import com.blue.server.service.IOplogService;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
