package com.nfcat.cloud.sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("nf_user")
public class NfUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("nano_id")
    private String nanoId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户头像
     */
    @TableField("header_img")
    private String headerImg;

    /**
     * 性别
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 微信开放ID
     */
    @TableField("wx_id")
    private String wxId;

    /**
     * 会员
     */
    @TableField("vip")
    private LocalDateTime vip;

    /**
     * 超级会员
     */
    @TableField("svip")
    private LocalDateTime svip;

    /**
     * 金币
     */
    @TableField("gold")
    private Long gold;

    /**
     * 积分
     */
    @TableField("membership_points")
    private Long membershipPoints;

    /**
     * CNY
     */
    @TableField("cny")
    private Integer cny;

    /**
     * 安全分
     */
    @TableField("safety_score")
    private Integer safetyScore;

    /**
     * 用户组
     */
    @TableField("user_group")
    private String userGroup;

    /**
     * 上级用户
     */
    @TableField("parent_user")
    private String parentUser;

    /**
     * 用户状态
     */
    @TableField("state")
    private Integer state;

    /**
     * 用户普通信息(json)
     */
    @TableField("user_info")
    private String userInfo;

    /**
     * 注册时间
     */
    @TableField("reg_time")
    private LocalDateTime regTime;

    /**
     * 其他数据(json)
     */
    @TableField("info")
    private String info;


}
