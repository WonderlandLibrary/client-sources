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

public class NCPSpeed
extends Mod {
    public NCPSpeed() {
        super("NCPSpeed", "NCPSpeed \u00a77[Old]", 49, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (NCPSpeed.mc.thePlayer.onGround) {
            NCPSpeed.mc.thePlayer.motionX *= 1.1;
            NCPSpeed.mc.thePlayer.motionZ *= 1.1;
            NCPSpeed.mc.thePlayer.motionY = 0.15;
        }
    }
}

