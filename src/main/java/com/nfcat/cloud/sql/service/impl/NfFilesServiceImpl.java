package com.nfcat.cloud.sql.service.impl;

import com.nfcat.cloud.sql.entity.NfFiles;
import com.nfcat.cloud.sql.mapper.NfFilesMapper;
import com.nfcat.cloud.sql.service.INfFilesService;
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
public class NfFilesServiceImpl extends ServiceImpl<NfFilesMapper, NfFiles> implements INfFilesService {

}
