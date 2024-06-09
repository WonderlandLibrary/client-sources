/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventPreMotionUpdates;
import Monix.Mod.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Glide
extends Mod {
    public Glide() {
        super("Glide", "Glide", 34, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventPreMotionUpdates e) {
        Glide.mc.thePlayer.motionY = -0.2;
    }
}

