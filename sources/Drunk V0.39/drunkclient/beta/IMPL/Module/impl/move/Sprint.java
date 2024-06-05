/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import net.minecraft.client.Minecraft;

public class Sprint
extends Module {
    public Sprint() {
        super("Sprint", new String[0], Type.MOVE, "Automaticly Sprinting");
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        if (Minecraft.thePlayer.moveForward > 0.0f) {
            Minecraft.thePlayer.setSprinting(true);
            return;
        }
        Minecraft.thePlayer.setSprinting(false);
    }
}

