/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Component;

public interface Rumbler {
    public void rumble(float var1);

    public String getAxisName();

    public Component.Identifier getAxisIdentifier();
}

