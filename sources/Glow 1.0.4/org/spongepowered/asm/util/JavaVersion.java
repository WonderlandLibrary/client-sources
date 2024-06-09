package org.spongepowered.asm.util;

import java.util.regex.*;

public abstract class JavaVersion
{
    private static double current;
    
    private JavaVersion() {
        super();
    }
    
    public static double current() {
        if (JavaVersion.current == 0.0) {
            JavaVersion.current = resolveCurrentVersion();
        }
        return JavaVersion.current;
    }
    
    private static double resolveCurrentVersion() {
        final Matcher matcher = Pattern.compile("[0-9]+\\.[0-9]+").matcher(System.getProperty("java.version"));
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 1.6;
    }
    
    static {
        JavaVersion.current = 0.0;
    }
}
