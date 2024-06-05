/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;

public class FastPlace
extends Module {
    public FastPlace() {
        super("FastPlace", new String[0], Type.MISC, "Allow's to placing block's faster");
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        FastPlace.mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        FastPlace.mc.rightClickDelayTimer = 6;
        super.onDisable();
    }
}

