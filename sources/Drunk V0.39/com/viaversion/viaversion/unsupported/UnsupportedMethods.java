/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.unsupported;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

public final class UnsupportedMethods {
    private final String className;
    private final Set<String> methodNames;

    public UnsupportedMethods(String className, Set<String> methodNames) {
        this.className = className;
        this.methodNames = Collections.unmodifiableSet(methodNames);
    }

    public String getClassName() {
        return this.className;
    }

    public final boolean findMatch() {
        try {
            Method[] methodArray = Class.forName(this.className).getDeclaredMethods();
            int n = methodArray.length;
            int n2 = 0;
            while (n2 < n) {
                Method method = methodArray[n2];
                if (this.methodNames.contains(method.getName())) {
                    return true;
                }
                ++n2;
            }
            return false;
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return false;
    }
}

