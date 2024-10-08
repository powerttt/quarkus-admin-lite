package qal.fast.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ResourceResult<T> implements Serializable {

    /**
     * 错误码
     */
    public Integer code;
    /**
     * 返回数据
     */
    public T data;
    /**
     * 错误提示，用户可阅读
     */
    public String msg;

    public static ResourceResult<Object> success() {
        return success(null);
    }

    public static <T> ResourceResult<T> success(T data) {
        ResourceResult<T> result = new ResourceResult<>();
        result.code = 0;
        result.data = data;
        result.msg = "";
        return result;
    }

    public static <T> ResourceResult<T> error(Integer code, String message) {
        ResourceResult<T> result = new ResourceResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> ResourceResult<T> error(String message) {
        ResourceResult<T> result = new ResourceResult<>();
        result.code = 500;
        result.msg = message;
        return result;
    }

}
