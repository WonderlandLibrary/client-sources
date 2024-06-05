/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.set;

import drunkclient.beta.IMPL.set.Value;

public class Color<T extends java.awt.Color>
extends Value<T> {
    private T value;

    public Color(String displayName, String name, T value) {
        super(displayName, name);
    }

    public T getARGB() {
        return this.value;
    }
}

