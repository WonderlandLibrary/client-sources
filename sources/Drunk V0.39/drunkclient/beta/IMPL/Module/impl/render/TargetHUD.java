/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender2D;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.combat.Killaura;
import drunkclient.beta.IMPL.Module.impl.render.HUD;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.GuiUtils;
import drunkclient.beta.UTILS.render.RenderUtil;
import drunkclient.beta.UTILS.render.color.ColorUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TargetHUD
extends Module {
    private static double lastP = 0.0;
    private float animated = 20.0f;
    private final Dimension screenSize;
    private static double a = 0.0;
    public Mode<Enum> modes = new Mode("Mode", "Mode", (Enum[])Modes.values(), (Enum)Modes.Novoline);

    public TargetHUD() {
        super("TargetHUD", new String[0], Type.RENDER, "Rendering target info while attacking");
        this.addValues(this.modes);
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    @EventHandler
    public void e(EventRender2D e) {
        EntityLivingBase target = Killaura.target;
        ScaledResolution sr = new ScaledResolution(mc);
        switch (this.modes.getModeAsString()) {
            case "AstolfoEdit": {
                if (target == null) return;
                float sw = sr.getScaledWidth();
                float sh = sr.getScaledHeight();
                float y = sh / 2.0f + 35.0f;
                float x = sw / 2.0f - 77.5f;
                int color = HUD.color;
                this.drawRectB(x - 1.0f, y + 2.0f, 155.0f, 57.0f, new Color(-1459157241, true));
                TargetHUD.mc.fontRendererObj.drawStringWithShadow(Killaura.target.getName(), x + 31.0f, y + 6.0f, -1);
                GL11.glPushMatrix();
                GlStateManager.translate(x, y, 1.0f);
                GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
                GlStateManager.translate(-x, -y, 1.0f);
                TargetHUD.mc.fontRendererObj.drawStringWithShadow((double)Math.round((double)(Killaura.target.getHealth() / 2.0f) * 10.0) / 10.0 + " \u2764", x + 16.0f, y + 13.0f, new Color(color).darker().getRGB());
                GL11.glPopMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GuiInventory.drawEntityOnScreen((int)x + 16, (int)y + 55, 25, Killaura.target.rotationYaw, -Killaura.target.rotationPitch, Killaura.target);
                int xHealthbar = 30;
                int yHealthbar = 46;
                float add = 120.0f;
                this.drawRectB(x + 30.0f, y + 46.0f, 120.0f, 8.0f, new Color(color).darker().darker().darker());
                this.drawRectB(x + 30.0f, y + 46.0f, Killaura.target.getHealth() / Killaura.target.getMaxHealth() * 120.0f, 8.0f, new Color(color));
                double addX = x + 30.0f + Killaura.target.getHealth() / Killaura.target.getMaxHealth() * 120.0f;
                this.drawRectB((float)(addX - 3.0), y + 46.0f, 3.0f, 8.0f, new Color(-1979711488, true));
                int index = 1;
                while (index < 5) {
                    if (Killaura.target.getEquipmentInSlot(index) == null) {
                        // empty if block
                    }
                    ++index;
                }
                return;
            }
            case "Novoline": {
                if (target == null) return;
                if (!(target instanceof EntityPlayer)) return;
                float var28 = target.getHealth() + target.getAbsorptionAmount();
                float var32 = target.getMaxHealth() + target.getAbsorptionAmount() - 0.05f;
                float var37 = 35 + TargetHUD.mc.fontRendererObj.getStringWidth(target.getName()) + 40;
                float var42 = (float)((double)Math.round((double)var28 * 100.0) / 100.0);
                if (var42 > var32) {
                    var42 *= var32 / var42;
                }
                float var46 = 100.0f / var32;
                float var48 = var42 * var46;
                float var51 = (var37 - 50.0f) / 100.0f;
                float var53 = (float)(this.screenSize.getWidth() / 4.0);
                float var57 = (float)(this.screenSize.getHeight() / 4.0);
                if ((double)var48 < lastP) {
                    a = lastP - (double)var48;
                }
                lastP = var48;
                if (a > 0.0) {
                    a += (0.0 - a) * (double)0.05f;
                }
                a = MathHelper.clamp_double(a, 0.0, 100.0f - var48);
                RenderUtil.drawBorderedRect((double)var53, (double)(var57 - 1.5f), (double)(var53 + var37 - 6.0f), (double)var57 + 37.5, 1.0f, new Color(0, 0, 0, 50).getRGB(), new Color(29, 29, 29, 255).getRGB());
                Gui.drawRect(var53 + 1.0f, var57, var53 + var37 - 7.0f, var57 + 36.0f, new Color(40, 40, 40, 255).getRGB());
                Gui.drawRect(var53 + 40.0f, (float)((double)var57 + 16.5), var53 + 40.0f + 100.0f * var51, (float)((double)var57 + 27.3), new Color(0, 0, 0, 50).getRGB());
                Gui.drawRect(var53 + 40.0f, (float)((double)var57 + 16.5), var53 + 40.0f + var48 * var51, (float)((double)var57 + 27.3), HUD.color);
                Gui.drawRect(var53 + 40.0f + var48 * var51, (float)((double)var57 + 16.5), (float)((double)(var53 + 40.0f + var48 * var51) + a * (double)var51), (float)((double)var57 + 27.3), new Color(HUD.color).darker().getRGB());
                String healthString = String.format("%.1f", Float.valueOf(var48)) + "%";
                TargetHUD.mc.fontRendererObj.drawStringWithShadow(healthString, var53 + 40.0f + 50.0f * var51 - (float)(TargetHUD.mc.fontRendererObj.getStringWidth(healthString) / 2), var57 + 18.0f, -1);
                FontLoaders.arial22.drawStringWithShadow(target.getName(), var53 + 40.0f, var57 + 4.0f, -1);
                this.drawFace((int)((double)var53 + 0.3409090909090909), (int)((double)var57 + 0.045454545454545456), 8.0f, 8.0f, 8, 8, 38, 36, 64.0f, 64.0f, (AbstractClientPlayer)target);
                return;
            }
            case "DrunkClient": {
                if (target == null) return;
                float sw = sr.getScaledWidth();
                float sh = sr.getScaledHeight();
                if (Killaura.target == null) return;
                if (!(Killaura.target instanceof EntityPlayer)) return;
                String name = target.getName();
                int percent = (int)Killaura.target.getHealth() / 2;
                String uni = "";
                String healthColor = "";
                uni = "\u2764 ";
                healthColor = "\u00a77";
                float xSpeed = 133.0f / ((float)Minecraft.getDebugFPS() * 1.05f);
                float desiredWidth = (float)(FontLoaders.Comfortaa18.getStringWidth(name) + 45) / Killaura.target.getMaxHealth() * Math.min(Killaura.target.getHealth(), Killaura.target.getMaxHealth());
                if (desiredWidth < this.animated || desiredWidth > this.animated) {
                    this.animated = Math.abs(desiredWidth - this.animated) <= xSpeed ? desiredWidth : (this.animated += this.animated < desiredWidth ? xSpeed * 3.0f : -xSpeed);
                }
                GuiUtils.drawRoundedRect(sw / 2.0f + 10.0f, sh / 2.0f - 30.0f, (float)(FontLoaders.Comfortaa18.getStringWidth(name) - 35) + sw / 2.0f + 110.0f, sh / 2.0f + 20.0f, -1879048192, -1879048192);
                this.drawFace((int)sw / 2 + 12, (int)sh / 2 - 28, 8.0f, 8.0f, 8, 8, 28, 28, 64.0f, 64.0f, (AbstractClientPlayer)target);
                Minecraft.getMinecraft().fontRendererObj.drawString(uni, (int)sw / 2 + 12, (int)sh / 2, new Color(244, 102, 101).getRGB());
                GuiUtils.drawRoundedRect1(sw / 2.0f + 21.0f, sh / 2.0f + 3.5f, FontLoaders.Comfortaa18.getStringWidth(name) + 45, 0.1f, 0x900000, -1879048192);
                GuiUtils.drawRoundedRect1(sw / 2.0f + 21.0f, sh / 2.0f + 3.5f, this.animated, 0.1f, 0x900000, this.getHealthColorTest(target).getRGB());
                FontLoaders.Comfortaa18.drawString(name, sw / 2.0f + 45.0f, sh / 2.0f - 20.0f, -1);
                FontLoaders.Tahoma12.drawString("Health: " + String.format("%.1f", Float.valueOf(target.getHealth())), sw / 2.0f + 20.0f, sh / 2.0f + 10.0f, -1);
                return;
            }
        }
    }

    public void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        try {
            ResourceLocation skin = target.getLocationSkin();
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glDisable((int)3042);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Color getHealthColorTest(EntityLivingBase entityLivingBase) {
        Color color;
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        if (health >= 0.0f) {
            color = ColorUtils.blendColors(fractions, colors, progress).brighter();
            return color;
        }
        color = colors[0];
        return color;
    }

    public void drawRectB(float x, float y, float w, float h, Color color) {
        Gui.drawRect(x, y, x + w, y + h, color.getRGB());
    }

    static enum Modes {
        AstolfoEdit,
        Novoline;

    }
}

