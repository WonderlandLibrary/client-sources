/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

public class ChatLine<T> {
    private final int updateCounterCreated;
    private final T lineString;
    private final int chatLineID;
    private boolean isClient;

    public ChatLine(int n, T t, int n2, boolean bl) {
        this.lineString = t;
        this.updateCounterCreated = n;
        this.chatLineID = n2;
        this.isClient = bl;
    }

    public T getLineString() {
        return this.lineString;
    }

    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }

    public int getChatLineID() {
        return this.chatLineID;
    }

    public boolean isClient() {
        return this.isClient;
    }
}

