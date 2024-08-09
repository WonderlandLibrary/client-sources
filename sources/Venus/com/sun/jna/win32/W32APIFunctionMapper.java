/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.win32;

import com.sun.jna.FunctionMapper;
import com.sun.jna.NativeLibrary;
import java.lang.reflect.Method;

public class W32APIFunctionMapper
implements FunctionMapper {
    public static final FunctionMapper UNICODE = new W32APIFunctionMapper(true);
    public static final FunctionMapper ASCII = new W32APIFunctionMapper(false);
    private final String suffix;

    protected W32APIFunctionMapper(boolean bl) {
        this.suffix = bl ? "W" : "A";
    }

    @Override
    public String getFunctionName(NativeLibrary nativeLibrary, Method method) {
        String string = method.getName();
        if (!string.endsWith("W") && !string.endsWith("A")) {
            try {
                string = nativeLibrary.getFunction(string + this.suffix, 63).getName();
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                // empty catch block
            }
        }
        return string;
    }
}

