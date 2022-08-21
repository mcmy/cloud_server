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
 * 
 * </p>
 *
 * @author nfcat
 * @since 2022-07-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("nf_article")
public class NfArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布者ID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 文章类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 文章标题
     */
    @TableField("title")
    private String title;

    /**
     * 文章简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 文章内容(text,html,markdown)
     */
    @TableField("content")
    private String content;

    /**
     * 文章略缩图(json)
     */
    @TableField("images")
    private String images;

    /**
     * 点赞
     */
    @TableField("love")
    private Integer love;

    /**
     * 收藏
     */
    @TableField("collection")
    private Integer collection;

    /**
     * 分享
     */
    @TableField("share")
    private Integer share;

    /**
     * 发布时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最新一次修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 其他数据(json)
     */
    @TableField("info")
    private String info;


}
