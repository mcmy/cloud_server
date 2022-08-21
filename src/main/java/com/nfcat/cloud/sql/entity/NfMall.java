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
@TableName("nf_mall")
public class NfMall implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布者ID
     */
    @TableField("uid")
    private String uid;

    /**
     * 商品类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 商品名
     */
    @TableField("name")
    private String name;

    /**
     * 销量
     */
    @TableField("sales_volume")
    private Integer salesVolume;

    /**
     * 金币购买价格
     */
    @TableField("g_price")
    private Integer gPrice;

    /**
     * 积分购买价格
     */
    @TableField("p_price")
    private Integer pPrice;

    /**
     * CNY购买价格
     */
    @TableField("m_price")
    private Integer mPrice;

    /**
     * 价格详情(json)
     */
    @TableField("price_json")
    private String priceJson;

    /**
     * 商品简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 商品详情(text,html,markdown)
     */
    @TableField("content")
    private String content;

    /**
     * 商品图片(json)
     */
    @TableField("images")
    private String images;

    /**
     * 商品其他数据(json)
     */
    @TableField("data")
    private String data;

    /**
     * 创建时间
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
