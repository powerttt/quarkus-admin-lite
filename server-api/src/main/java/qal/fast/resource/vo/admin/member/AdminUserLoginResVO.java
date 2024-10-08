package qal.fast.resource.vo.admin.member;

import lombok.Data;

import java.util.List;

@Data
public class AdminUserLoginResVO {

    public Long uid;
    public String nickname;
    public String avatar;
    public List<String> roles;

    // token
    public Long exp;
    public Long iat;
    public Long xp;
    public String token;
}
