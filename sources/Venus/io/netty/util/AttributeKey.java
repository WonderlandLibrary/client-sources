/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.AbstractConstant;
import io.netty.util.Constant;
import io.netty.util.ConstantPool;

public final class AttributeKey<T>
extends AbstractConstant<AttributeKey<T>> {
    private static final ConstantPool<AttributeKey<Object>> pool = new ConstantPool<AttributeKey<Object>>(){

        @Override
        protected AttributeKey<Object> newConstant(int n, String string) {
            return new AttributeKey<Object>(n, string, null);
        }

        @Override
        protected Constant newConstant(int n, String string) {
            return this.newConstant(n, string);
        }
    };

    public static <T> AttributeKey<T> valueOf(String string) {
        return pool.valueOf(string);
    }

    public static boolean exists(String string) {
        return pool.exists(string);
    }

    public static <T> AttributeKey<T> newInstance(String string) {
        return pool.newInstance(string);
    }

    public static <T> AttributeKey<T> valueOf(Class<?> clazz, String string) {
        return pool.valueOf(clazz, string);
    }

    private AttributeKey(int n, String string) {
        super(n, string);
    }

    AttributeKey(int n, String string, 1 var3_3) {
        this(n, string);
    }
}

