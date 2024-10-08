package qal.fast.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import qal.fast.entity.member.MemberUser;
import qal.fast.enums.MemberRoleEnum;
import qal.fast.enums.MemberUserStatusEnum;
import qal.fast.mapper.MemberUserMapper;
import qal.fast.resource.vo.admin.member.*;
import qal.fast.resource.vo.member.MemberInfoResVO;
import qal.fast.utils.ResourceResult;
import qal.fast.utils.auth.PBKDF2Encoder;
import qal.fast.utils.auth.TokenUtils;
import qal.fast.utils.vo.PageResVO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MemberUserService {

    @ConfigProperty(name = "quarkusjwt.jwt.duration")
    Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    MemberUserMapper memberUserMapper;

    public ResourceResult<AdminUserLoginResVO> adminLogin(AdminUserLoginReqVO loginReqVO, HttpServerRequest httpServerRequest) {

        MemberUser memberUser = MemberUser.find("email", loginReqVO.email).firstResult();
        // 判断是否禁止登录
        if (memberUser != null && !memberUser.status.equals(MemberUserStatusEnum.ENABLE.getStatus())) {
            return ResourceResult.error("Account is disabled");
        }
        if (memberUser != null && memberUser.password.equals(passwordEncoder.encode(loginReqVO.password))) {
            try {
                MemberInfoResVO memberInfoResVO = wrapperVO(memberUser);
                AdminUserLoginResVO resVO = memberUserMapper.toAdminUserLoginResVO(memberInfoResVO);

                return ResourceResult.success(resVO);
            } catch (Exception e) {
                Log.warn(e.getMessage());
                return ResourceResult.error("账号或密码错误");
            }
        }

        // 账号或密码错误
        return ResourceResult.error("账号或密码错误");
    }


    /**
     * 包装用户信息, 生成token
     *
     * @param memberUser 用户
     *                   expAt      已签发的过期时间,null 则使用当前时间戳; 已经颁发的+24H
     */
    private MemberInfoResVO wrapperVO(MemberUser memberUser) {
        // expAt 过期了,null,还有24H过期
        long epochSecond = Instant.now().getEpochSecond();
//        boolean generatorToken = expAt == null || expAt <= epochSecond || expAt <= epochSecond + 86400 * 2;

        MemberInfoResVO memberInfoResVO = memberUserMapper.toMemberInfoResVO(memberUser);

        if (StringUtils.isBlank(memberUser.roles)) {
            memberUser.roles = MemberRoleEnum.MEMBER.getRole();
        }
        try {
//            if (generatorToken) {
            // 当前时间戳
            Long tokenIat = Instant.now().getEpochSecond();
            Long tokenExp = tokenIat + duration;
            // token
            memberInfoResVO.setExp(tokenExp);
            memberInfoResVO.setIat(tokenIat);


            String jwt = TokenUtils.generateToken(memberUser.id.toString(), memberUser.roles, tokenIat, tokenExp, issuer);
            memberInfoResVO.setToken(jwt);
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memberInfoResVO;
    }


    String[] SORT_FILED_LIST = new String[]{"id", "payAt"};

    public String checkSortFiled(String sortFiled) {
        if (Arrays.asList(SORT_FILED_LIST).contains(sortFiled)) {
            return sortFiled;
        }
        return "id";
    }

    public PageResVO<AdminMemberUserPageResVO> getList(AdminMemberUserPageReqVO vo, Long userId) {
        // 装配条件
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (vo.id != null) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("id = :id");
            params.put("id", vo.id);
        }

        if (StringUtils.isNotBlank(vo.email)) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("email like :email");
            params.put("email", "%" + vo.email + "%");
        }
        if (StringUtils.isNotBlank(vo.mobile)) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("mobile like :mobile");
            params.put("mobile", "%" + vo.mobile + "%");
        }
        if (StringUtils.isNotBlank(vo.nickname)) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("nickname like :nickname");
            params.put("nickname", "%" + vo.nickname + "%");
        }
        if (StringUtils.isNotBlank(vo.refCode)) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("refCode like :refCode");
            params.put("refCode", "%" + vo.refCode + "%");
        }
        if (StringUtils.isNotBlank(vo.roles)) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("roles like :roles");
            params.put("roles", "%" + vo.roles + "%");
        }
        if (StringUtils.isNotBlank(vo.loginIp)) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("loginIp like :loginIp");
            params.put("loginIp", "%" + vo.loginIp + "%");
        }


        if (vo.status != null) {
            if (!stringBuilder.isEmpty()) stringBuilder.append(" and ");
            stringBuilder.append("status = :status");
            params.put("status", vo.status);
        }


        Sort sort = Sort.by(checkSortFiled(vo._sort), vo._order.equals("asc") ? Sort.Direction.Ascending : Sort.Direction.Descending);

        PanacheQuery<MemberUser> result = MemberUser.find(stringBuilder.toString(), sort, params);


        List<MemberUser> list = result.page(Page.of(vo._page, vo._size)).list();
        List<AdminMemberUserPageResVO> resList = memberUserMapper.toAdminPageResVO(list);

        long count = result.count();

        PageResVO<AdminMemberUserPageResVO> resVO = new PageResVO<>();
        resVO.setPage(vo._page).setSize(vo._size).setTotal(count).setList(resList);
        return resVO;


    }

    public AdminMemberUserResVO getById(Long id) {
        MemberUser byId = MemberUser.findById(id);
        return memberUserMapper.toAdminResVO(byId);
    }

    @Transactional(rollbackOn = Exception.class)
    public ResourceResult<?> save(AdminMemberUserReqVO vo, Long userId) {
        // 查询存在
        if (StringUtils.isBlank(vo.getEmail()) || MemberUser.find("email", vo.getEmail()).count() > 0) {
            return ResourceResult.error("Email 已存在");
        }
        if (StringUtils.isBlank(vo.getMobile()) || MemberUser.find("mobile", vo.getMobile()).count() > 0) {
            return ResourceResult.error("Mobile 已存在");
        }

        MemberUser entity = new MemberUser();
        memberUserMapper.updateAdminMemberUserReqVO(vo, entity);

        LocalDateTime now = LocalDateTime.now();

        entity.registerAt = now;
        entity.updateAt = now;
        entity.updater = userId;

        entity.persist();

        return ResourceResult.success();
    }

    // update
    @Transactional(rollbackOn = Exception.class)
    public ResourceResult<?> update(AdminMemberUserReqVO vo, Long userId) {
        MemberUser entity = MemberUser.findById(vo.getId());

        if (entity == null) {
            return ResourceResult.error("ID不存在");
        }
        // 判断email号是否重复
        if (StringUtils.isNotBlank(entity.email) && !entity.email.equals(vo.email)) {
            MemberUser emailMember = MemberUser.find("email", vo.email).firstResult();
            if (emailMember != null && !emailMember.id.equals(entity.id)) {
                return ResourceResult.error("Email 已存在");
            }
        }
        // 判断email号是否 mobile
        if (StringUtils.isNotBlank(entity.mobile) && !entity.mobile.equals(vo.mobile)) {
            MemberUser mobileMember = MemberUser.find("mobile", vo.mobile).firstResult();
            if (mobileMember != null && !mobileMember.id.equals(entity.id)) {
                return ResourceResult.error("Mobile 已存在");
            }
        }

        // 交换字段
        memberUserMapper.updateAdminMemberUserReqVO(vo, entity);

        entity.updateAt = LocalDateTime.now();
        entity.updater = userId;

        entity.persist();
        return ResourceResult.success();
    }

    /**
     * del
     */
    @Transactional(rollbackOn = Exception.class)
    public ResourceResult<?> delete(Long id) {
        MemberUser entity = MemberUser.findById(id);
        if (entity == null) {
            return ResourceResult.error("ID 不存在");
        }
        entity.delete();
        return ResourceResult.success();
    }

}
