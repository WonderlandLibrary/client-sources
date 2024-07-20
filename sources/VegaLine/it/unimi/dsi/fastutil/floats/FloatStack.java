/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Stack;

public interface FloatStack
extends Stack<Float> {
    @Override
    public void push(float var1);

    public float popFloat();

    public float topFloat();

    public float peekFloat(int var1);
}

