package com.nfcat.cloud.sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author nfcat
 * @since 2022-07-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("nf_comment")
public class NfComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容对应ID
     */
    @TableField("cid")
    private Integer cid;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 其他数据(json)
     */
    @TableField("info")
    private String info;


}
