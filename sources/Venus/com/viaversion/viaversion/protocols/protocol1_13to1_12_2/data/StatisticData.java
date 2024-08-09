/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

public class StatisticData {
    private final int categoryId;
    private final int newId;
    private final int value;

    public StatisticData(int n, int n2, int n3) {
        this.categoryId = n;
        this.newId = n2;
        this.value = n3;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public int getNewId() {
        return this.newId;
    }

    public int getValue() {
        return this.value;
    }
}

