package com.sun.jna.win32;

import com.sun.jna.*;
import java.lang.reflect.*;

public class W32APIFunctionMapper implements FunctionMapper
{
    public static final FunctionMapper UNICODE;
    public static final FunctionMapper ASCII;
    private final String suffix;
    
    protected W32APIFunctionMapper(final boolean unicode) {
        this.suffix = (unicode ? "W" : "A");
    }
    
    @Override
    public String getFunctionName(final NativeLibrary library, final Method method) {
        String name = method.getName();
        if (!name.endsWith("W") && !name.endsWith("A")) {
            try {
                name = library.getFunction(name + this.suffix, 63).getName();
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
        }
        return name;
    }
    
    static {
        UNICODE = new W32APIFunctionMapper(true);
        ASCII = new W32APIFunctionMapper(false);
    }
}
