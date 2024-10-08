package qal.fast.resource.vo.member;

import lombok.Data;

import java.util.List;

@Data
public class MemberInfoResVO {

    /**
     * 邀请码
     */
    public String refCode;

    public Long id;
    public String nickname;
    // 加密
    public String mobile;
    public String avatar;

    // token
    public Long exp;
    public Long iat;
    public Long xp;
    public String token;
    public List<String> roles;
}
