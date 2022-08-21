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
@TableName("nf_files")
public class NfFiles implements Serializable {

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
    private Integer uid;

    /**
     * 文件ID
     */
    @TableField("fid")
    private String fid;

    /**
     * 文件名
     */
    @TableField("name")
    private String name;

    /**
     * 储存路径	
     */
    @TableField("path")
    private String path;

    /**
     * MD5
     */
    @TableField("name_md5")
    private String nameMd5;

    /**
     * 文件MD5
     */
    @TableField("md5")
    private String md5;

    /**
     * 储存位置
     */
    @TableField("location")
    private String location;

    /**
     * 文件大小
     */
    @TableField("size")
    private Long size;

    /**
     * 文件类型
     */
    @TableField("type")
    private String type;

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
