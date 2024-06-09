package HORIZON-6-0-SKIDPROTECTION;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ModInfo {
    String HorizonCode_Horizon_È();
    
    String Â();
    
    int Ý();
    
    Category Ø­áŒŠá();
}
