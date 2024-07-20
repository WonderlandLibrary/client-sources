/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.ReflectionAccessFilterHelper;

public interface ReflectionAccessFilter {
    public static final ReflectionAccessFilter BLOCK_INACCESSIBLE_JAVA = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> rawClass) {
            return ReflectionAccessFilterHelper.isJavaType(rawClass) ? FilterResult.BLOCK_INACCESSIBLE : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_JAVA = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> rawClass) {
            return ReflectionAccessFilterHelper.isJavaType(rawClass) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_ANDROID = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> rawClass) {
            return ReflectionAccessFilterHelper.isAndroidType(rawClass) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_PLATFORM = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> rawClass) {
            return ReflectionAccessFilterHelper.isAnyPlatformType(rawClass) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };

    public FilterResult check(Class<?> var1);

    public static enum FilterResult {
        ALLOW,
        INDECISIVE,
        BLOCK_INACCESSIBLE,
        BLOCK_ALL;

    }
}

