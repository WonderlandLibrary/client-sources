package net.minecraft.src;

import java.awt.image.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class MapItemRenderer
{
    private int[] intArray;
    private int bufferedImage;
    private GameSettings gameSettings;
    private FontRenderer fontRenderer;
    
    public MapItemRenderer(final FontRenderer par1FontRenderer, final GameSettings par2GameSettings, final RenderEngine par3RenderEngine) {
        this.intArray = new int[16384];
        this.gameSettings = par2GameSettings;
        this.fontRenderer = par1FontRenderer;
        this.bufferedImage = par3RenderEngine.allocateAndSetupTexture(new BufferedImage(128, 128, 2));
        for (int var4 = 0; var4 < 16384; ++var4) {
            this.intArray[var4] = 0;
        }
    }
    
    public void renderMap(final EntityPlayer par1EntityPlayer, final RenderEngine par2RenderEngine, final MapData par3MapData) {
        for (int var4 = 0; var4 < 16384; ++var4) {
            final byte var5 = par3MapData.colors[var4];
            if (var5 / 4 == 0) {
                this.intArray[var4] = (var4 + var4 / 128 & 0x1) * 8 + 16 << 24;
            }
            else {
                final int var6 = MapColor.mapColorArray[var5 / 4].colorValue;
                final int var7 = var5 & 0x3;
                short var8 = 220;
                if (var7 == 2) {
                    var8 = 255;
                }
                if (var7 == 0) {
                    var8 = 180;
                }
                int var9 = (var6 >> 16 & 0xFF) * var8 / 255;
                int var10 = (var6 >> 8 & 0xFF) * var8 / 255;
                int var11 = (var6 & 0xFF) * var8 / 255;
                if (this.gameSettings.anaglyph) {
                    final int var12 = (var9 * 30 + var10 * 59 + var11 * 11) / 100;
                    final int var13 = (var9 * 30 + var10 * 70) / 100;
                    final int var14 = (var9 * 30 + var11 * 70) / 100;
                    var9 = var12;
                    var10 = var13;
                    var11 = var14;
                }
                this.intArray[var4] = (0xFF000000 | var9 << 16 | var10 << 8 | var11);
            }
        }
        par2RenderEngine.createTextureFromBytes(this.intArray, 128, 128, this.bufferedImage);
        final byte var15 = 0;
        final byte var16 = 0;
        final Tessellator var17 = Tessellator.instance;
        final float var18 = 0.0f;
        GL11.glBindTexture(3553, this.bufferedImage);
        GL11.glEnable(3042);
        GL11.glBlendFunc(1, 771);
        GL11.glDisable(3008);
        var17.startDrawingQuads();
        var17.addVertexWithUV(var15 + 0 + var18, var16 + 128 - var18, -0.009999999776482582, 0.0, 1.0);
        var17.addVertexWithUV(var15 + 128 - var18, var16 + 128 - var18, -0.009999999776482582, 1.0, 1.0);
        var17.addVertexWithUV(var15 + 128 - var18, var16 + 0 + var18, -0.009999999776482582, 1.0, 0.0);
        var17.addVertexWithUV(var15 + 0 + var18, var16 + 0 + var18, -0.009999999776482582, 0.0, 0.0);
        var17.draw();
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        par2RenderEngine.resetBoundTexture();
        par2RenderEngine.bindTexture("/misc/mapicons.png");
        int var19 = 0;
        for (final MapCoord var21 : par3MapData.playersVisibleOnMap.values()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(var15 + var21.centerX / 2.0f + 64.0f, var16 + var21.centerZ / 2.0f + 64.0f, -0.02f);
            GL11.glRotatef(var21.iconRotation * 360 / 16.0f, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(4.0f, 4.0f, 3.0f);
            GL11.glTranslatef(-0.125f, 0.125f, 0.0f);
            final float var22 = (var21.iconSize % 4 + 0) / 4.0f;
            final float var23 = (var21.iconSize / 4 + 0) / 4.0f;
            final float var24 = (var21.iconSize % 4 + 1) / 4.0f;
            final float var25 = (var21.iconSize / 4 + 1) / 4.0f;
            var17.startDrawingQuads();
            var17.addVertexWithUV(-1.0, 1.0, var19 * 0.001f, var22, var23);
            var17.addVertexWithUV(1.0, 1.0, var19 * 0.001f, var24, var23);
            var17.addVertexWithUV(1.0, -1.0, var19 * 0.001f, var24, var25);
            var17.addVertexWithUV(-1.0, -1.0, var19 * 0.001f, var22, var25);
            var17.draw();
            GL11.glPopMatrix();
            ++var19;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, -0.04f);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
