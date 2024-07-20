/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.ControllerEvent;

public interface ControllerListener {
    public void controllerRemoved(ControllerEvent var1);

    public void controllerAdded(ControllerEvent var1);
}

