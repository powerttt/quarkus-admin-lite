package qal.fast.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MemberUserStatusEnum {

    ENABLE(0, "开启"),
    DISABLE(1, "关闭"),

    ;

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    MemberUserStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

}
