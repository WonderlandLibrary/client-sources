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

public class Speed
extends Mod {
    public Speed() {
        super("Speed", "Speed", 50, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (Speed.mc.thePlayer.onGround) {
            Speed.mc.thePlayer.motionX *= 1.3;
            Speed.mc.thePlayer.motionZ *= 1.3;
        }
    }
}

