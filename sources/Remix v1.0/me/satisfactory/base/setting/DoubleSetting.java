package me.satisfactory.base.setting;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleSetting {
    String name();
    
    double currentValue();
    
    double minValue();
    
    double maxValue();
    
    boolean onlyInt();
}
