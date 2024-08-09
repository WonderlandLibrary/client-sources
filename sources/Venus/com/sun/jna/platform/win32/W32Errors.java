/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.platform.win32.WinError;
import com.sun.jna.platform.win32.WinNT;

public abstract class W32Errors
implements WinError {
    public static final boolean SUCCEEDED(int n) {
        return n >= 0;
    }

    public static final boolean FAILED(int n) {
        return n < 0;
    }

    public static final int HRESULT_CODE(int n) {
        return n & 0xFFFF;
    }

    public static final int SCODE_CODE(int n) {
        return n & 0xFFFF;
    }

    public static final int HRESULT_FACILITY(int n) {
        return (n >>= 16) & 0x1FFF;
    }

    public static final int SCODE_FACILITY(short s) {
        s = (short)(s >> 16);
        return s & 0x1FFF;
    }

    public static short HRESULT_SEVERITY(int n) {
        return (short)((n >>= 31) & 1);
    }

    public static short SCODE_SEVERITY(short s) {
        s = (short)(s >> 31);
        return (short)(s & 1);
    }

    public static int MAKE_HRESULT(short s, short s2, short s3) {
        return s << 31 | s2 << 16 | s3;
    }

    public static final int MAKE_SCODE(short s, short s2, short s3) {
        return s << 31 | s2 << 16 | s3;
    }

    public static final WinNT.HRESULT HRESULT_FROM_WIN32(int n) {
        int n2 = 7;
        return new WinNT.HRESULT(n <= 0 ? n : n & 0xFFFF | (n2 <<= 16) | Integer.MIN_VALUE);
    }

    public static final int FILTER_HRESULT_FROM_FLT_NTSTATUS(int n) {
        int n2 = 31;
        return n & 0x8000FFFF | (n2 <<= 16);
    }
}

