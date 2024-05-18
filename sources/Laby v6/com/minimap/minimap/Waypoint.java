package com.minimap.minimap;

import net.minecraft.client.resources.*;
import com.minimap.*;
import net.minecraft.client.renderer.*;
import com.minimap.settings.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import com.minimap.interfaces.*;
import net.minecraft.client.gui.*;

public class Waypoint
{
    public int x;
    public int y;
    public int z;
    public String name;
    public String symbol;
    public int color;
    public boolean disabled;
    public int type;
    public boolean rotation;
    public int yaw;
    
    public Waypoint(final int x, final int y, final int z, final String name, final String symbol, final int color) {
        this(x, y, z, name, symbol, color, 0);
    }
    
    public Waypoint(final int x, final int y, final int z, final String name, final String symbol, final int color, final int type) {
        this.disabled = false;
        this.type = 0;
        this.rotation = false;
        this.yaw = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.symbol = symbol;
        this.color = color;
        this.type = type;
        this.name = name;
    }
    
    public double getDistanceSq(final double x, final double y, final double z) {
        double d3 = this.x - x;
        final double d4 = this.y - y;
        d3 = this.z - z;
        return d3 * d3 + d4 * d4 + d3 * d3;
    }
    
    public String getName() {
        return I18n.format(this.name, new Object[0]);
    }
    
    public void drawIconInWorld(final WorldRenderer worldrenderer, final Tessellator tessellator, final FontRenderer fontrenderer, String text, float textSize, final boolean background, final boolean showDistance) {
        GlStateManager.scale(XaeroMinimap.getSettings().waypointsScale, XaeroMinimap.getSettings().waypointsScale, 1.0f);
        if (this.type == 0) {
            final int c = ModSettings.COLORS[this.color];
            final float l = (c >> 16 & 0xFF) / 255.0f;
            final float i1 = (c >> 8 & 0xFF) / 255.0f;
            final float j1 = (c & 0xFF) / 255.0f;
            final int s = fontrenderer.getStringWidth(this.symbol) / 2;
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            GlStateManager.color(l, i1, j1, 0.4705882f);
            worldrenderer.pos(-5.0, -9.0, 0.0).endVertex();
            worldrenderer.pos(-5.0, 0.0, 0.0).endVertex();
            worldrenderer.pos(4.0, 0.0, 0.0).endVertex();
            worldrenderer.pos(4.0, -9.0, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(this.symbol, -s, -8, 553648127);
            fontrenderer.drawString(this.symbol, -s, -8, -1);
            GlStateManager.scale(textSize / 2.0f, textSize / 2.0f, 1.0f);
            final int t = fontrenderer.getStringWidth(text) / 2;
            if (background) {
                GlStateManager.disableTexture2D();
                GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2745098f);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION);
                worldrenderer.pos(-t - 1.0, 1.0, 0.0).endVertex();
                worldrenderer.pos(-t - 1.0, 11.0, 0.0).endVertex();
                worldrenderer.pos(t + 1.0, 11.0, 0.0).endVertex();
                worldrenderer.pos(t + 1.0, 1.0, 0.0).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
            }
            fontrenderer.drawString(text, -t, 2, 553648127);
            fontrenderer.drawString(text, -t, 2, -1);
        }
        else if (this.type == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(InterfaceHandler.guiTextures);
            final float f = 0.00390625f;
            final float f2 = 0.00390625f;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 0.7843137f);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-5.0, -9.0, 0.0).tex(0.0, 78.0f * f2).endVertex();
            worldrenderer.pos(-5.0, 0.0, 0.0).tex(0.0, 87.0f * f2).endVertex();
            worldrenderer.pos(4.0, 0.0, 0.0).tex(9.0f * f, 87.0f * f2).endVertex();
            worldrenderer.pos(4.0, -9.0, 0.0).tex(9.0f * f, 78.0f * f2).endVertex();
            tessellator.draw();
            if (!showDistance) {
                text = this.getName();
                textSize = 1.0f;
            }
            GlStateManager.scale(textSize / 2.0f, textSize / 2.0f, 1.0f);
            final int t2 = fontrenderer.getStringWidth(text) / 2;
            if (background) {
                GlStateManager.disableTexture2D();
                GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2745098f);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION);
                worldrenderer.pos(-t2 - 1.0, 1.0, 0.0).endVertex();
                worldrenderer.pos(-t2 - 1.0, 11.0, 0.0).endVertex();
                worldrenderer.pos(t2 + 1.0, 11.0, 0.0).endVertex();
                worldrenderer.pos(t2 + 1.0, 1.0, 0.0).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
            }
            fontrenderer.drawString(text, -t2, 2, 553648127);
            fontrenderer.drawString(text, -t2, 2, -1);
        }
    }
    
    public void drawIconOnGUI(final int drawX, final int drawY) {
        if (this.type == 0) {
            final int rectX2 = drawX + 9;
            final int rectY2 = drawY + 9;
            Gui.drawRect(drawX, drawY, rectX2, rectY2, ModSettings.COLORS[this.color]);
            final int j = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.symbol) / 2;
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.symbol, drawX + 5 - j, drawY + 1, Minimap.radarPlayers.hashCode());
        }
        else if (this.type == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(InterfaceHandler.guiTextures);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(drawX, drawY, 0, 78, 9, 9);
        }
    }
}
