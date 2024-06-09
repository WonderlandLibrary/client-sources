/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.util.List;

public abstract class ListManager<T> {
    protected List<T> contents;

    public final List<T> getContentList() {
        return this.contents;
    }

    public abstract void setup();
}

