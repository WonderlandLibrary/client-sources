/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class NativeLong
extends IntegerType {
    private static final long serialVersionUID = 1L;
    public static final int SIZE = Native.LONG_SIZE;

    public NativeLong() {
        this(0L);
    }

    public NativeLong(long value) {
        this(value, false);
    }

    public NativeLong(long value, boolean unsigned) {
        super(SIZE, value, unsigned);
    }
}

