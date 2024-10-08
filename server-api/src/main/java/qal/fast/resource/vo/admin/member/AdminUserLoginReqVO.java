package qal.fast.resource.vo.admin.member;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminUserLoginReqVO {

    @NotNull(message = "邮箱不能为空")
    public String email;
    @NotNull(message = "密码不能为空")
    public String password;

}
