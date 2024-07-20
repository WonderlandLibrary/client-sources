/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;

public interface ErroredCallback
extends Callback {
    public void apply(int var1, String var2);
}

