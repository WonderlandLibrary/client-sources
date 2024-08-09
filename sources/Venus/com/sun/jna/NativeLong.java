/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

public class NativeLong
extends IntegerType {
    private static final long serialVersionUID = 1L;
    public static final int SIZE = Native.LONG_SIZE;

    public NativeLong() {
        this(0L);
    }

    public NativeLong(long l) {
        this(l, false);
    }

    public NativeLong(long l, boolean bl) {
        super(SIZE, l, bl);
    }
}

