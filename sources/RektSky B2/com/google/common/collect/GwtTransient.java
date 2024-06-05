package com.google.common.collect;

import java.lang.annotation.*;
import com.google.common.annotations.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@GwtCompatible
@interface GwtTransient {
}
