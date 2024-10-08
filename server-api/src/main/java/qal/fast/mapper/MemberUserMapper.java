package qal.fast.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import qal.fast.entity.member.MemberUser;
import qal.fast.resource.vo.admin.member.AdminMemberUserPageResVO;
import qal.fast.resource.vo.admin.member.AdminMemberUserReqVO;
import qal.fast.resource.vo.admin.member.AdminMemberUserResVO;
import qal.fast.resource.vo.admin.member.AdminUserLoginResVO;
import qal.fast.resource.vo.member.MemberInfoResVO;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface MemberUserMapper extends MapperUtils {

    AdminUserLoginResVO toAdminUserLoginResVO(MemberInfoResVO memberInfoResVO);

    @Mapping(target = "roles", expression = "java(list2SplitStrings(vo.getRoles()))")
    void updateAdminMemberUserReqVO(AdminMemberUserReqVO vo, @MappingTarget MemberUser entity);

    List<AdminMemberUserPageResVO> toAdminPageResVO(List<MemberUser> list);

    @Mapping(target = "roles", expression = "java(splitStrings2List(memberUser.getRoles()))")
    AdminMemberUserPageResVO toAdminPageResVO(MemberUser memberUser);


    AdminMemberUserReqVO toAdminReqVO(MemberUser byId);

    @Mapping(target = "roles", expression = "java(splitStrings2List(memberUser.getRoles()))")
    MemberInfoResVO toMemberInfoResVO(MemberUser memberUser);


    @Mapping(target = "roles", expression = "java(splitStrings2List(memberUser.getRoles()))")
    AdminMemberUserResVO toAdminResVO(MemberUser memberUser);
}
