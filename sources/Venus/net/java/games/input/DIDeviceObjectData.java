/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class DIDeviceObjectData {
    private int format_offset;
    private int data;
    private int millis;
    private int sequence;

    DIDeviceObjectData() {
    }

    public final void set(int n, int n2, int n3, int n4) {
        this.format_offset = n;
        this.data = n2;
        this.millis = n3;
        this.sequence = n4;
    }

    public final void set(DIDeviceObjectData dIDeviceObjectData) {
        this.set(dIDeviceObjectData.format_offset, dIDeviceObjectData.data, dIDeviceObjectData.millis, dIDeviceObjectData.sequence);
    }

    public final int getData() {
        return this.data;
    }

    public final int getFormatOffset() {
        return this.format_offset;
    }

    public final long getNanos() {
        return (long)this.millis * 1000000L;
    }
}

