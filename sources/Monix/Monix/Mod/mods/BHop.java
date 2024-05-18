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
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class BHop
extends Mod {
    public BHop() {
        super("AutoJump", "AutoJump \u00a77[Move]", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if ((BHop.mc.gameSettings.keyBindForward.pressed || BHop.mc.gameSettings.keyBindBack.pressed || BHop.mc.gameSettings.keyBindLeft.pressed || BHop.mc.gameSettings.keyBindRight.pressed) && BHop.mc.thePlayer.onGround) {
            BHop.mc.thePlayer.jump();
            BHop.mc.thePlayer.setSprinting(true);
        }
    }
}

