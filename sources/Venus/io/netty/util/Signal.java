/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.AbstractConstant;
import io.netty.util.Constant;
import io.netty.util.ConstantPool;

public final class Signal
extends Error
implements Constant<Signal> {
    private static final long serialVersionUID = -221145131122459977L;
    private static final ConstantPool<Signal> pool = new ConstantPool<Signal>(){

        @Override
        protected Signal newConstant(int n, String string) {
            return new Signal(n, string, null);
        }

        @Override
        protected Constant newConstant(int n, String string) {
            return this.newConstant(n, string);
        }
    };
    private final SignalConstant constant;

    public static Signal valueOf(String string) {
        return pool.valueOf(string);
    }

    public static Signal valueOf(Class<?> clazz, String string) {
        return pool.valueOf(clazz, string);
    }

    private Signal(int n, String string) {
        this.constant = new SignalConstant(n, string);
    }

    public void expect(Signal signal) {
        if (this != signal) {
            throw new IllegalStateException("unexpected signal: " + signal);
        }
    }

    @Override
    public Throwable initCause(Throwable throwable) {
        return this;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public int id() {
        return this.constant.id();
    }

    @Override
    public String name() {
        return this.constant.name();
    }

    public boolean equals(Object object) {
        return this == object;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public int compareTo(Signal signal) {
        if (this == signal) {
            return 1;
        }
        return this.constant.compareTo(signal.constant);
    }

    @Override
    public String toString() {
        return this.name();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Signal)object);
    }

    Signal(int n, String string, 1 var3_3) {
        this(n, string);
    }

    private static final class SignalConstant
    extends AbstractConstant<SignalConstant> {
        SignalConstant(int n, String string) {
            super(n, string);
        }
    }
}

