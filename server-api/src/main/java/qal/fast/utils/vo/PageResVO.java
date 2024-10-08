package qal.fast.utils.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
public class PageResVO<T> {
    public Integer page;
    public Integer size;
    public Long total;
    public List<T> list;

}
