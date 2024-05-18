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

public class NCPSpeed2
extends Mod {
    public NCPSpeed2() {
        super("NCPSpeed2", "NCPSpeed \u00a77[New]", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (!NCPSpeed2.mc.thePlayer.isInWater() && (mc.gameSettings.keyBindForward.getIsKeyPressed() || mc.gameSettings.keyBindLeft.getIsKeyPressed() || mc.gameSettings.keyBindRight.getIsKeyPressed() || mc.gameSettings.keyBindBack.getIsKeyPressed())) {
        if (!NCPSpeed2.mc.thePlayer.isInWater()) {
            if (NCPSpeed2.mc.thePlayer.onGround) {
                NCPSpeed2.mc.thePlayer.jump();
            }
            else {
                NCPSpeed2.mc.thePlayer.motionY = -1.0;
            }
        }
    }
}

