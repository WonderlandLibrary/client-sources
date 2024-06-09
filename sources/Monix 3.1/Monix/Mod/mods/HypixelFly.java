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
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class HypixelFly
extends Mod {
    public HypixelFly() {
        super("HypixelFly", "Fly \u00a77[Hypixel]", 35, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventPreMotionUpdates e) {
        if (HypixelFly.mc.thePlayer.isSneaking()) {
            HypixelFly.mc.thePlayer.motionY = -0.4;
        } else if (HypixelFly.mc.gameSettings.keyBindJump.pressed) {
            HypixelFly.mc.thePlayer.motionY = 0.4;
        } else if (HypixelFly.mc.gameSettings.keyBindForward.pressed || HypixelFly.mc.gameSettings.keyBindBack.pressed || HypixelFly.mc.gameSettings.keyBindLeft.pressed || HypixelFly.mc.gameSettings.keyBindRight.pressed) {
            HypixelFly.mc.thePlayer.motionY = 0.0;
            HypixelFly.mc.thePlayer.setSprinting(true);
        } else {
            HypixelFly.mc.thePlayer.motionY = 0.0;
        }
    }
}

