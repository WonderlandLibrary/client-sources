/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson;

import com.google.gson.internal.ReflectionAccessFilterHelper;

public interface ReflectionAccessFilter {
    public static final ReflectionAccessFilter BLOCK_INACCESSIBLE_JAVA = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> clazz) {
            return ReflectionAccessFilterHelper.isJavaType(clazz) ? FilterResult.BLOCK_INACCESSIBLE : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_JAVA = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> clazz) {
            return ReflectionAccessFilterHelper.isJavaType(clazz) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_ANDROID = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> clazz) {
            return ReflectionAccessFilterHelper.isAndroidType(clazz) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_PLATFORM = new ReflectionAccessFilter(){

        @Override
        public FilterResult check(Class<?> clazz) {
            return ReflectionAccessFilterHelper.isAnyPlatformType(clazz) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
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

