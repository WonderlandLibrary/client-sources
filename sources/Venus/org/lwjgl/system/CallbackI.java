/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import org.lwjgl.system.Callback;
import org.lwjgl.system.Pointer;

public interface CallbackI
extends Pointer {
    public String getSignature();

    @Override
    default public long address() {
        return Callback.create(this.getSignature(), this);
    }

    public static interface P
    extends CallbackI {
        public long callback(long var1);
    }

    public static interface D
    extends CallbackI {
        public double callback(long var1);
    }

    public static interface F
    extends CallbackI {
        public float callback(long var1);
    }

    public static interface J
    extends CallbackI {
        public long callback(long var1);
    }

    public static interface I
    extends CallbackI {
        public int callback(long var1);
    }

    public static interface S
    extends CallbackI {
        public short callback(long var1);
    }

    public static interface B
    extends CallbackI {
        public byte callback(long var1);
    }

    public static interface Z
    extends CallbackI {
        public boolean callback(long var1);
    }

    public static interface V
    extends CallbackI {
        public void callback(long var1);
    }
}

