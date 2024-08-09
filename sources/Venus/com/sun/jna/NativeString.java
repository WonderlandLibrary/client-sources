/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import java.nio.CharBuffer;

class NativeString
implements CharSequence,
Comparable {
    static final String WIDE_STRING = "--WIDE-STRING--";
    private Pointer pointer;
    private String encoding;

    public NativeString(String string) {
        this(string, Native.getDefaultStringEncoding());
    }

    public NativeString(String string, boolean bl) {
        this(string, bl ? WIDE_STRING : Native.getDefaultStringEncoding());
    }

    public NativeString(WString wString) {
        this(wString.toString(), WIDE_STRING);
    }

    public NativeString(String string, String string2) {
        if (string == null) {
            throw new NullPointerException("String must not be null");
        }
        this.encoding = string2;
        if (WIDE_STRING.equals(this.encoding)) {
            int n = (string.length() + 1) * Native.WCHAR_SIZE;
            this.pointer = new StringMemory(this, n);
            this.pointer.setWideString(0L, string);
        } else {
            byte[] byArray = Native.getBytes(string, string2);
            this.pointer = new StringMemory(this, byArray.length + 1);
            this.pointer.write(0L, byArray, 0, byArray.length);
            this.pointer.setByte(byArray.length, (byte)0);
        }
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof CharSequence) {
            return this.compareTo(object) == 0;
        }
        return true;
    }

    @Override
    public String toString() {
        boolean bl = WIDE_STRING.equals(this.encoding);
        String string = bl ? "const wchar_t*" : "const char*";
        string = string + "(" + (bl ? this.pointer.getWideString(0L) : this.pointer.getString(0L, this.encoding)) + ")";
        return string;
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    @Override
    public char charAt(int n) {
        return this.toString().charAt(n);
    }

    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return CharBuffer.wrap(this.toString()).subSequence(n, n2);
    }

    public int compareTo(Object object) {
        if (object == null) {
            return 0;
        }
        return this.toString().compareTo(object.toString());
    }

    private class StringMemory
    extends Memory {
        final NativeString this$0;

        public StringMemory(NativeString nativeString, long l) {
            this.this$0 = nativeString;
            super(l);
        }

        @Override
        public String toString() {
            return this.this$0.toString();
        }
    }
}

