package com.nfcat.cloud.sql.service.impl;

import com.nfcat.cloud.sql.entity.NfOrder;
import com.nfcat.cloud.sql.mapper.NfOrderMapper;
import com.nfcat.cloud.sql.service.INfOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nfcat
 * @since 2022-07-15
 */
@Service
public class NfOrderServiceImpl extends ServiceImpl<NfOrderMapper, NfOrder> implements INfOrderService {

}
