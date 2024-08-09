/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.Constant;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ConstantPool<T extends Constant<T>> {
    private final ConcurrentMap<String, T> constants = PlatformDependent.newConcurrentHashMap();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public T valueOf(Class<?> clazz, String string) {
        if (clazz == null) {
            throw new NullPointerException("firstNameComponent");
        }
        if (string == null) {
            throw new NullPointerException("secondNameComponent");
        }
        return this.valueOf(clazz.getName() + '#' + string);
    }

    public T valueOf(String string) {
        ConstantPool.checkNotNullAndNotEmpty(string);
        return this.getOrCreate(string);
    }

    private T getOrCreate(String string) {
        T t;
        Constant constant = (Constant)this.constants.get(string);
        if (constant == null && (constant = (Constant)this.constants.putIfAbsent(string, t = this.newConstant(this.nextId(), string))) == null) {
            return t;
        }
        return (T)constant;
    }

    public boolean exists(String string) {
        ConstantPool.checkNotNullAndNotEmpty(string);
        return this.constants.containsKey(string);
    }

    public T newInstance(String string) {
        ConstantPool.checkNotNullAndNotEmpty(string);
        return this.createOrThrow(string);
    }

    private T createOrThrow(String string) {
        T t;
        Constant constant = (Constant)this.constants.get(string);
        if (constant == null && (constant = (Constant)this.constants.putIfAbsent(string, t = this.newConstant(this.nextId(), string))) == null) {
            return t;
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", string));
    }

    private static String checkNotNullAndNotEmpty(String string) {
        ObjectUtil.checkNotNull(string, "name");
        if (string.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        return string;
    }

    protected abstract T newConstant(int var1, String var2);

    @Deprecated
    public final int nextId() {
        return this.nextId.getAndIncrement();
    }
}

