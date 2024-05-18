package wwe;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface oqrr {
    byte Hmpo() default 2;
}
