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

    public void set(long l, long l2, int n, long l3) {
        this.type = l;
        this.cookie = l2;
        this.value = n;
        this.nanos = l3;
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

