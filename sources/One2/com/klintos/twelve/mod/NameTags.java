// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.entity.Entity;
import com.klintos.twelve.Twelve;
import net.minecraft.client.gui.FontRenderer;
import com.klintos.twelve.utils.GuiUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;

public class NameTags extends Mod
{
    public NameTags() {
        super("NameTags", 0, ModCategory.RENDER);
    }
    
    public static void renderNametag(final EntityLivingBase player, final double x, final double y, final double z) {
        GL11.glPushMatrix();
        final FontRenderer var13 = NameTags.mc.fontRendererObj;
        final String name = getNametagName(player);
        GL11.glTranslated((double)(float)x, (float)y + 2.5, (double)(float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-0.016666668f * getNametagSize(player), -0.016666668f * getNametagSize(player), 0.016666668f * getNametagSize(player));
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GL11.glDisable(2929);
        final int width = var13.getStringWidth(name) / 2;
        GuiUtils.drawBorderedRect(-width - 2, -2, width + 1, 9, -1073741824, Integer.MIN_VALUE);
        var13.func_175063_a(name, -width, 0, getNametagColor(player));
        GL11.glEnable(2929);
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GL11.glPopMatrix();
    }
    
    public static String getHealthColor(final int health) {
        if (health > 15) {
            return "a";
        }
        if (health > 10) {
            return "e";
        }
        if (health > 5) {
            return "6";
        }
        return "4";
    }
    
    public static int getNametagColor(final EntityLivingBase player) {
        if (Twelve.getInstance().getFriendHandler().isFriend(player.getName())) {
            return -43691;
        }
        if (player.isInvisible()) {
            return -13312;
        }
        if (player.isSneaking()) {
            return -1048576;
        }
        return -1;
    }
    
    public static String getNametagName(final EntityLivingBase player) {
        final String name = player.getDisplayName().getFormattedText();
        return String.valueOf(name) + " §" + getHealthColor((int)player.getHealth()) + (int)player.getHealth();
    }
    
    public static float getNametagSize(final EntityLivingBase player) {
        return (NameTags.mc.thePlayer.getDistanceToEntity((Entity)player) / 4.0f <= 2.0f) ? 2.0f : (NameTags.mc.thePlayer.getDistanceToEntity((Entity)player) / 4.0f);
    }
}
