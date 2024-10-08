package qal.fast.utils.vo;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageReqVO {

    @QueryParam("_page")
    public Integer _page = 1;

    @QueryParam("_size")
    public Integer _size = 10;

    @QueryParam("_sort")
    public String _sort = "id";

    @QueryParam("_order")
    public String _order = "desc";
    // search keys
    @QueryParam("_q")
    public String _q;
}
