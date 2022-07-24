package com.nfcat.cloud.sql.service.impl;

import com.nfcat.cloud.sql.entity.NfComment;
import com.nfcat.cloud.sql.mapper.NfCommentMapper;
import com.nfcat.cloud.sql.service.INfCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author nfcat
 * @since 2022-07-15
 */
@Service
public class NfCommentServiceImpl extends ServiceImpl<NfCommentMapper, NfComment> implements INfCommentService {

}
