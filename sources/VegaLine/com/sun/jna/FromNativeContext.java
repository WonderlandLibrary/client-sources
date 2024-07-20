/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class FromNativeContext {
    private Class<?> type;

    FromNativeContext(Class<?> javaType) {
        this.type = javaType;
    }

    public Class<?> getTargetType() {
        return this.type;
    }
}

