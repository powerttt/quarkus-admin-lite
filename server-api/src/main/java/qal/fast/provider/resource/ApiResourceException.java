package qal.fast.provider.resource;

import qal.fast.utils.ResourceResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResourceException extends RuntimeException {

    private Integer code = 500;

    private String msg;

    public ApiResourceException() {
    }

    public ApiResourceException(String msg) {
        this.msg = msg;
    }

    public static ApiResourceException error(String msg) {
        ApiResourceException apiResourceException = new ApiResourceException();
        apiResourceException.setMsg(msg);
        return apiResourceException;
    }

    public static ApiResourceException error(ResourceResult resourceResult) {
        ApiResourceException apiResourceException = new ApiResourceException();
        apiResourceException.setMsg(resourceResult.getMsg());
        apiResourceException.setCode(resourceResult.getCode());
        return apiResourceException;
    }

    public static ApiResourceException error(Integer code, String msg) {
        ApiResourceException apiResourceException = new ApiResourceException();
        apiResourceException.setCode(code);
        apiResourceException.setMsg(msg);
        return apiResourceException;
    }
}
