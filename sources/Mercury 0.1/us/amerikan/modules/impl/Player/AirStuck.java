/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class AirStuck
extends Module {
    public AirStuck() {
        super("AirStuck", "AirStuck", 0, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        if (AirStuck.mc.theWorld != null) {
            if (Minecraft.thePlayer != null) {
                Minecraft.thePlayer.isDead = true;
            }
        }
    }

    @Override
    public void onDisable() {
        Minecraft.thePlayer.isDead = false;
    }
}

