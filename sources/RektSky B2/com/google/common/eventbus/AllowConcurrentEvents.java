package com.google.common.eventbus;

import java.lang.annotation.*;
import com.google.common.annotations.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Beta
public @interface AllowConcurrentEvents {
}
