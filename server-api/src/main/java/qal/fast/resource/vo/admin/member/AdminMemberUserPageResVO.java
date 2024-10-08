package qal.fast.resource.vo.admin.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import qal.fast.enums.MemberUserStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AdminMemberUserPageResVO {

    public Long id;
    /**
     * 用户昵称
     */
    public String nickname;
    /**
     * 用户昵称
     */
    public List<String> roles;
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


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updateAt;
    public Long updater;
    public Long parentId;
    public String refCode;


}
