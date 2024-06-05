// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();
    
    String renderName() default "";
    
    String[] aliases() default { "" };
    
    String description() default "This module doesn't have a description.";
    
    ModuleCategory category();
}
