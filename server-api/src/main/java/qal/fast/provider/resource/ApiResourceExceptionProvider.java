package qal.fast.provider.resource;

import qal.fast.utils.ResourceResult;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider // 一定要加的注解，否则不生效
public class ApiResourceExceptionProvider implements ExceptionMapper<ApiResourceException> {

    @Override
    public Response toResponse(ApiResourceException e) {
        // 打印堆栈信息
        // Log.error(e.getMsg());

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(ResourceResult.error(e.getCode(), e.getMsg()))
                .build();

    }
}
