// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String name();
    
    String description();
    
    String usage();
    
    String[] aliases();
}
