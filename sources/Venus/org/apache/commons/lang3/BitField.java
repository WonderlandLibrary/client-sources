/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

public class BitField {
    private final int _mask;
    private final int _shift_count;

    public BitField(int n) {
        this._mask = n;
        this._shift_count = n != 0 ? Integer.numberOfTrailingZeros(n) : 0;
    }

    public int getValue(int n) {
        return this.getRawValue(n) >> this._shift_count;
    }

    public short getShortValue(short s) {
        return (short)this.getValue(s);
    }

    public int getRawValue(int n) {
        return n & this._mask;
    }

    public short getShortRawValue(short s) {
        return (short)this.getRawValue(s);
    }

    public boolean isSet(int n) {
        return (n & this._mask) != 0;
    }

    public boolean isAllSet(int n) {
        return (n & this._mask) == this._mask;
    }

    public int setValue(int n, int n2) {
        return n & ~this._mask | n2 << this._shift_count & this._mask;
    }

    public short setShortValue(short s, short s2) {
        return (short)this.setValue(s, s2);
    }

    public int clear(int n) {
        return n & ~this._mask;
    }

    public short clearShort(short s) {
        return (short)this.clear(s);
    }

    public byte clearByte(byte by) {
        return (byte)this.clear(by);
    }

    public int set(int n) {
        return n | this._mask;
    }

    public short setShort(short s) {
        return (short)this.set(s);
    }

    public byte setByte(byte by) {
        return (byte)this.set(by);
    }

    public int setBoolean(int n, boolean bl) {
        return bl ? this.set(n) : this.clear(n);
    }

    public short setShortBoolean(short s, boolean bl) {
        return bl ? this.setShort(s) : this.clearShort(s);
    }

    public byte setByteBoolean(byte by, boolean bl) {
        return bl ? this.setByte(by) : this.clearByte(by);
    }
}

