package com.nfcat.cloud.sql.service.impl;

import com.nfcat.cloud.sql.entity.NfConfig;
import com.nfcat.cloud.sql.mapper.NfConfigMapper;
import com.nfcat.cloud.sql.service.INfConfigService;
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
public class NfConfigServiceImpl extends ServiceImpl<NfConfigMapper, NfConfig> implements INfConfigService {

}
