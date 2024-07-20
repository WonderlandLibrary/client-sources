/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class LinuxJoystickEvent {
    private long nanos;
    private int value;
    private int type;
    private int number;

    LinuxJoystickEvent() {
    }

    public final void set(long millis, int value, int type2, int number) {
        this.nanos = millis * 1000000L;
        this.value = value;
        this.type = type2;
        this.number = number;
    }

    public final int getValue() {
        return this.value;
    }

    public final int getType() {
        return this.type;
    }

    public final int getNumber() {
        return this.number;
    }

    public final long getNanos() {
        return this.nanos;
    }
}

