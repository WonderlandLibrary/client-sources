package me.satisfactory.base.setting;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BooleanSetting {
    String name();
    
    boolean booleanValue();
}
