package lunadevs.luna.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Documented
@TypeQualifierNickname
@Nonnull(when = When.UNKNOWN)
@Retention(RetentionPolicy.RUNTIME)
public @interface Nullable {

}