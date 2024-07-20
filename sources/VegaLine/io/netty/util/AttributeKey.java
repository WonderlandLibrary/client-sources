/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.AbstractConstant;
import io.netty.util.ConstantPool;

public final class AttributeKey<T>
extends AbstractConstant<AttributeKey<T>> {
    private static final ConstantPool<AttributeKey<Object>> pool = new ConstantPool<AttributeKey<Object>>(){

        @Override
        protected AttributeKey<Object> newConstant(int id, String name) {
            return new AttributeKey<Object>(id, name);
        }
    };

    public static <T> AttributeKey<T> valueOf(String name) {
        return pool.valueOf(name);
    }

    public static boolean exists(String name) {
        return pool.exists(name);
    }

    public static <T> AttributeKey<T> newInstance(String name) {
        return pool.newInstance(name);
    }

    public static <T> AttributeKey<T> valueOf(Class<?> firstNameComponent, String secondNameComponent) {
        return pool.valueOf(firstNameComponent, secondNameComponent);
    }

    private AttributeKey(int id, String name) {
        super(id, name);
    }
}

