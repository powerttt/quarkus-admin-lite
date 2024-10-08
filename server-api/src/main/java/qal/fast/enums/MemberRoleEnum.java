package qal.fast.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum MemberRoleEnum {

    ADMIN("Admin", 0),
    MEMBER("Member", 1),
    ;

    private final String role;
    private final Integer type;

    MemberRoleEnum(String role, Integer type) {
        this.role = role;
        this.type = type;
    }

    // type convert to enum
    public static MemberRoleEnum getEnumByValue(Integer value) {
        return Stream.of(MemberRoleEnum.values()).filter(e -> e.type.equals(value)).findFirst().orElse(null);
    }

}
