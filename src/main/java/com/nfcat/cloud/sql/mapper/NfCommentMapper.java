package com.nfcat.cloud.sql.mapper;

import com.nfcat.cloud.sql.entity.NfComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author nfcat
 * @since 2022-07-15
 */
@Mapper
public interface NfCommentMapper extends BaseMapper<NfComment> {

}
