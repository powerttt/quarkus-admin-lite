package qal.fast.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import qal.fast.configs.generator.CustomPanacheEntity;
import qal.fast.enums.MemberUserStatusEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class MemberUser extends CustomPanacheEntity {

    /**
     * 用户昵称
     */
    public String nickname;
    /**
     * 用户头像
     */
    public String avatar;
    /**
     * 帐号状态
     * <p>
     * 枚举 {@link MemberUserStatusEnum}
     */
    @ColumnDefault("0")
    public Integer status;
    /**
     * mobile
     */
    @Column(unique = true)
    public String mobile;
    /**
     * email
     */
    @Column(unique = true)
    public String email;

    /**
     * 邀请码
     */
    @Column(unique = true)
    public String refCode;
    /**
     * 上级邀请ID
     */
    @ColumnDefault("0")
    public Long parentId;

    /**
     * 加密后的密码
     */
    public String password;
    /**
     * 注册 IP
     */
    public String registerIp;
    public LocalDateTime registerAt;
    /**
     * 最后登录IP
     */
    public String loginIp;
    /**
     * 最后登录时间
     */
    public LocalDateTime loginAt;

    /**
     * 逗号分隔: Member,Admin
     */
    public String roles;


    /**
     * 创建时间
     */
    public LocalDateTime updateAt;
    public Long updater;
}
