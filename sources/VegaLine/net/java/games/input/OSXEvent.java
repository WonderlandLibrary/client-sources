/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

class OSXEvent {
    private long type;
    private long cookie;
    private int value;
    private long nanos;

    OSXEvent() {
    }

    public void set(long type2, long cookie, int value, long nanos) {
        this.type = type2;
        this.cookie = cookie;
        this.value = value;
        this.nanos = nanos;
    }

    public long getType() {
        return this.type;
    }

    public long getCookie() {
        return this.cookie;
    }

    public int getValue() {
        return this.value;
    }

    public long getNanos() {
        return this.nanos;
    }
}

