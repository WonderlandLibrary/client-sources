/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class RawKeyboardEvent {
    private long millis;
    private int make_code;
    private int flags;
    private int vkey;
    private int message;
    private long extra_information;

    RawKeyboardEvent() {
    }

    public final void set(long l, int n, int n2, int n3, int n4, long l2) {
        this.millis = l;
        this.make_code = n;
        this.flags = n2;
        this.vkey = n3;
        this.message = n4;
        this.extra_information = l2;
    }

    public final void set(RawKeyboardEvent rawKeyboardEvent) {
        this.set(rawKeyboardEvent.millis, rawKeyboardEvent.make_code, rawKeyboardEvent.flags, rawKeyboardEvent.vkey, rawKeyboardEvent.message, rawKeyboardEvent.extra_information);
    }

    public final int getVKey() {
        return this.vkey;
    }

    public final int getMessage() {
        return this.message;
    }

    public final long getNanos() {
        return this.millis * 1000000L;
    }
}

