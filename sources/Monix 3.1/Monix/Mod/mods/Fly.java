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
import net.minecraft.entity.player.PlayerCapabilities;

public class Fly
extends Mod {
    public Fly() {
        super("Fly", "Fly", 33, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        Fly.mc.thePlayer.capabilities.isFlying = true;
        Fly.mc.thePlayer.capabilities.allowFlying = true;
        Fly.mc.thePlayer.capabilities.setFlySpeed(0.2f);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Fly.mc.thePlayer.capabilities.isFlying = false;
        Fly.mc.thePlayer.capabilities.allowFlying = false;
    }
}

