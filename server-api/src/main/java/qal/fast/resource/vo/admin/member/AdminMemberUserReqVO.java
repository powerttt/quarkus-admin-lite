package qal.fast.resource.vo.admin.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import qal.fast.enums.MemberUserStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AdminMemberUserReqVO {
    public Long id;
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
    public Integer status;
    /**
     * mobile
     */
    public String mobile;
    /**
     * email
     */
    public String email;
    /**
     * 注册 IP
     */
    public String registerIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime registerAt;
    /**
     * 最后登录IP
     */
    public String loginIp;
    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime loginAt;

    /**
     * 备注
     */
    public String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updateAt;
    /**
     * 创建人
     */
    public Long creator;
    public Long updater;

    public Long parentId;
    public String refCode;
    public List<String> roles;

}
