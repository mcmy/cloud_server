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
@TableName("nf_shopping_cart")
public class NfShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private String uid;

    /**
     * 商品ID
     */
    @TableField("pid")
    private String pid;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 金币价格
     */
    @TableField("g_price")
    private Integer gPrice;

    /**
     * 积分价格
     */
    @TableField("p_price")
    private Integer pPrice;

    /**
     * CNY价格
     */
    @TableField("m_price")
    private Integer mPrice;

    /**
     * 组合价格(json)
     */
    @TableField("price_json")
    private String priceJson;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 其他数据(json)
     */
    @TableField("info")
    private String info;


}
