package com.nfcat.cloud.sql.service.impl;

import com.nfcat.cloud.sql.entity.NfUser;
import com.nfcat.cloud.sql.mapper.NfUserMapper;
import com.nfcat.cloud.sql.service.INfUserService;
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
public class NfUserServiceImpl extends ServiceImpl<NfUserMapper, NfUser> implements INfUserService {

}
