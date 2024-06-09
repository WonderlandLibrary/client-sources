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

public class NCPFall
extends Mod {
    public NCPFall() {
        super("NCPFall", "NCPFall", 0, Category.EXPLOITS);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (NCPFall.mc.thePlayer.fallDistance >= 2.0f) {
            NCPFall.mc.thePlayer.motionY = 0.0;
            this.toggle();
        }
    }
}

