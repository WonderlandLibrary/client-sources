package com.google.common.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
@Documented
@GwtCompatible
public @interface GwtIncompatible {
    String value();
}
