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
@TableName("nf_order")
public class NfOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private String uid;

    /**
     * 商品信息(json)
     */
    @TableField("shop_info")
    private String shopInfo;

    /**
     * 价格(json)
     */
    @TableField("price_json")
    private String priceJson;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 完成时间
     */
    @TableField("finish_time")
    private LocalDateTime finishTime;

    /**
     * 关闭时间
     */
    @TableField("destroy_time")
    private LocalDateTime destroyTime;

    /**
     * 状态
     */
    @TableField("state")
    private Integer state;

    /**
     * 其他数据(json)
     */
    @TableField("info")
    private String info;


}
