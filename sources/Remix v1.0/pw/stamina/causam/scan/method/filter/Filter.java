package pw.stamina.causam.scan.method.filter;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {
    Class<? extends FilterFactory<?>> value();
}
