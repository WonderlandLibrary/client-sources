/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Sprint
extends Mod {
    public Sprint() {
        super("Sprint", "Sprint", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (Sprint.mc.thePlayer.moveForward > 0.0f) {
            Sprint.mc.thePlayer.setSprinting(true);
        }
    }
}

