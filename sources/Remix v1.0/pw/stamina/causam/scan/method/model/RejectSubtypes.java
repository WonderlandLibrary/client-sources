package pw.stamina.causam.scan.method.model;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RejectSubtypes {
}
