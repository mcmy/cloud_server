package com.nfcat.cloud.sql.service.impl;

import com.nfcat.cloud.sql.entity.NfArticle;
import com.nfcat.cloud.sql.mapper.NfArticleMapper;
import com.nfcat.cloud.sql.service.INfArticleService;
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
public class NfArticleServiceImpl extends ServiceImpl<NfArticleMapper, NfArticle> implements INfArticleService {

}
