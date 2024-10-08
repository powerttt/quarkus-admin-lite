package qal.fast.resource.vo.admin.member;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import qal.fast.utils.vo.PageReqVO;

@Getter
@Setter
public class AdminMemberUserPageReqVO extends PageReqVO {

    @QueryParam("id")
    public Long id;

    @QueryParam("email_like")
    public String email;

    @QueryParam("email_like")
    public String mobile;

    @QueryParam("nickname_like")
    public String nickname;

    @QueryParam("loginIp_like")
    public String loginIp;

    @QueryParam("refCode")
    public String refCode;

    @QueryParam("parentId")
    public Long parentId;

    @QueryParam("status")
    public String status;

    @QueryParam("roles")
    public String roles;

}
