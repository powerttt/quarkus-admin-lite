package qal.fast.resource.admin;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import qal.fast.entity.member.MemberUser;
import qal.fast.resource.vo.admin.member.*;
import qal.fast.service.MemberUserService;
import qal.fast.utils.ResourceResult;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import qal.fast.utils.vo.PageResVO;

import java.util.List;

@Path("admin-007fgVT0S/memberUser")
@RequestScoped
@Produces("application/json")
@Consumes("application/json")
public class AdminMemberUserResource {

    @Inject
    @Claim(standard = Claims.exp)
    Long exp;

    @Inject
    @Claim(standard = Claims.sub)
    Long userId;

    @Inject
    MemberUserService memberUserService;

    @Inject
    io.vertx.core.http.HttpServerRequest httpServerRequest;

    // create mock member user
    @POST
    @Path("login")
    public ResourceResult<AdminUserLoginResVO> login(@Valid @NotNull(message = "参数不完整") AdminUserLoginReqVO loginReqVO) {
        return memberUserService.adminLogin(loginReqVO, httpServerRequest);
    }


    @GET
    @Path("/")
    @RolesAllowed({"Admin"})
    public ResourceResult<PageResVO<?>> getList(@BeanParam AdminMemberUserPageReqVO vo) {


        PageResVO<AdminMemberUserPageResVO> list = memberUserService.getList(vo, userId);
        return ResourceResult.success(list);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Admin"})
    public ResourceResult<?> getById(@PathParam("id") Long id) {
        return ResourceResult.success(memberUserService.getById(id));
    }


    @POST
    @Path("/")
    @RolesAllowed({"Admin"})
    public ResourceResult<?> save(AdminMemberUserReqVO vo) {
        return memberUserService.save(vo, userId);
    }

    @PATCH
    @Path("{id}")
    @RolesAllowed({"Admin"})
    public ResourceResult<?> update(@PathParam("id") Long id, AdminMemberUserReqVO vo) {
        return memberUserService.update(vo, userId);
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"Admin"})
    public ResourceResult<?> delete(@PathParam("id") Long id) {
        return memberUserService.delete(id);
    }


}
