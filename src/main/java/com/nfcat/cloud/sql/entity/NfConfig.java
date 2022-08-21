package com.nfcat.cloud.sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("nf_config")
public class NfConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("conf_name")
    private String confName;

    @TableField("conf_value")
    private String confValue;

    /**
     * 其他数据(json)
     */
    @TableField("info")
    private String info;


}
