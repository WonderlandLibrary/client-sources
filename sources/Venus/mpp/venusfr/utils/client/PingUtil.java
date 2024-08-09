/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class PingUtil
implements IMinecraft {
    public static String calculateBPS() {
        return String.format("%.2f", Math.hypot(PingUtil.mc.player.getPosX() - PingUtil.mc.player.prevPosX, PingUtil.mc.player.getPosZ() - PingUtil.mc.player.prevPosZ) * (double)PingUtil.mc.timer.timerSpeed * 20.0);
    }

    public static void drawItemStack(ItemStack itemStack, float f, float f2, boolean bl, boolean bl2, float f3) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(f, f2, 0.0f);
        if (bl2) {
            GL11.glScaled(f3, f3, f3);
        }
        mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, 0, 0);
        if (bl) {
            mc.getItemRenderer().renderItemOverlays(PingUtil.mc.fontRenderer, itemStack, 0, 0);
        }
        RenderSystem.popMatrix();
    }

    public static int calculatePing() {
        return PingUtil.mc.player.connection.getPlayerInfo(PingUtil.mc.player.getUniqueID()) != null ? PingUtil.mc.player.connection.getPlayerInfo(PingUtil.mc.player.getUniqueID()).getResponseTime() : 0;
    }

    public static String serverIP() {
        return mc.getCurrentServerData() != null && PingUtil.mc.getCurrentServerData().serverIP != null && !mc.isSingleplayer() ? PingUtil.mc.getCurrentServerData().serverIP : "";
    }
}

