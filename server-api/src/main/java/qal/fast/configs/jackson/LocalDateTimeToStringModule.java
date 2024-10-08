package qal.fast.configs.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;

public class LocalDateTimeToStringModule extends SimpleModule {

    public LocalDateTimeToStringModule() {
        super("LocalDateTimeToStringModule", new Version(1, 0, 0, null, null, null));
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer0());
    }
}
