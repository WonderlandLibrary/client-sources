/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;

public final class Buffer {
    private static final int DEFAULT_CAPACITY = 64;
    private static final byte[] NOBYTES = new byte[0];
    private byte[] bytes;
    private int length;
    private int offset;
    private LuaValue value;

    public Buffer() {
        this(64);
    }

    public Buffer(int n) {
        this.bytes = new byte[n];
        this.length = 0;
        this.offset = 0;
        this.value = null;
    }

    public Buffer(LuaValue luaValue) {
        this.bytes = NOBYTES;
        this.offset = 0;
        this.length = 0;
        this.value = luaValue;
    }

    public LuaValue value() {
        return this.value != null ? this.value : this.tostring();
    }

    public Buffer setvalue(LuaValue luaValue) {
        this.bytes = NOBYTES;
        this.length = 0;
        this.offset = 0;
        this.value = luaValue;
        return this;
    }

    public LuaString tostring() {
        this.realloc(this.length, 0);
        return LuaString.valueOf(this.bytes, this.offset, this.length);
    }

    public String tojstring() {
        return this.value().tojstring();
    }

    public String toString() {
        return this.tojstring();
    }

    public Buffer append(byte by) {
        this.makeroom(0, 1);
        this.bytes[this.offset + this.length++] = by;
        return this;
    }

    public Buffer append(LuaValue luaValue) {
        this.append(luaValue.strvalue());
        return this;
    }

    public Buffer append(LuaString luaString) {
        int n = luaString.m_length;
        this.makeroom(0, n);
        luaString.copyInto(0, this.bytes, this.offset + this.length, n);
        this.length += n;
        return this;
    }

    public Buffer append(String string) {
        char[] cArray = string.toCharArray();
        int n = LuaString.lengthAsUtf8(cArray);
        this.makeroom(0, n);
        LuaString.encodeToUtf8(cArray, cArray.length, this.bytes, this.offset + this.length);
        this.length += n;
        return this;
    }

    public Buffer concatTo(LuaValue luaValue) {
        return this.setvalue(luaValue.concat(this.value()));
    }

    public Buffer concatTo(LuaString luaString) {
        return this.value != null && !this.value.isstring() ? this.setvalue(luaString.concat(this.value)) : this.prepend(luaString);
    }

    public Buffer concatTo(LuaNumber luaNumber) {
        return this.value != null && !this.value.isstring() ? this.setvalue(luaNumber.concat(this.value)) : this.prepend(luaNumber.strvalue());
    }

    public Buffer prepend(LuaString luaString) {
        int n = luaString.m_length;
        this.makeroom(n, 0);
        System.arraycopy(luaString.m_bytes, luaString.m_offset, this.bytes, this.offset - n, n);
        this.offset -= n;
        this.length += n;
        this.value = null;
        return this;
    }

    public void makeroom(int n, int n2) {
        if (this.value != null) {
            LuaString luaString = this.value.strvalue();
            this.value = null;
            this.length = luaString.m_length;
            this.offset = n;
            this.bytes = new byte[n + this.length + n2];
            System.arraycopy(luaString.m_bytes, luaString.m_offset, this.bytes, this.offset, this.length);
        } else if (this.offset + this.length + n2 > this.bytes.length || this.offset < n) {
            int n3 = n + this.length + n2;
            int n4 = n3 < 32 ? 32 : (n3 < this.length * 2 ? this.length * 2 : n3);
            this.realloc(n4, n == 0 ? 0 : n4 - this.length - n2);
        }
    }

    private void realloc(int n, int n2) {
        if (n != this.bytes.length) {
            byte[] byArray = new byte[n];
            System.arraycopy(this.bytes, this.offset, byArray, n2, this.length);
            this.bytes = byArray;
            this.offset = n2;
        }
    }
}

