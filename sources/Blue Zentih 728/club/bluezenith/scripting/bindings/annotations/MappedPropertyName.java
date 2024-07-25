package club.bluezenith.scripting.bindings.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MappedPropertyName {
    String value();
}
