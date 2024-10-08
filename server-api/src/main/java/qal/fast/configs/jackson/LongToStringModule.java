package qal.fast.configs.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class LongToStringModule extends SimpleModule {

    public LongToStringModule() {
        super("LongToStringModule", new Version(1, 0, 0, null, null, null));
        addSerializer(Long.class, new LongToStringSerializer());
    }
}
