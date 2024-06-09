// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.entity.AbstractClientPlayer;
import intent.AquaDev.aqua.utils.StencilUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.Entity;
import intent.AquaDev.aqua.utils.RenderUtil;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiChat;
import events.listeners.EventRender2D;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;
import intent.AquaDev.aqua.modules.combat.Killaura;
import net.minecraft.client.gui.GuiNewChat;
import events.listeners.EventPostRender2D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class TargetHUD extends Module
{
    float lostHealthPercentage;
    float lastHealthPercentage;
    public int mouseX;
    public int mouseY;
    public int lastMouseX;
    public int lastMouseY;
    public boolean pressed;
    public boolean dragged;
    
    public TargetHUD() {
        super("TargetHUD", Type.Visual, "TargetHUD", 0, Category.Visual);
        this.lostHealthPercentage = 0.0f;
        this.lastHealthPercentage = 0.0f;
        Aqua.setmgr.register(new Setting("ClientColor", this, true));
        Aqua.setmgr.register(new Setting("CornerRadius", this, 4.0, 0.0, 12.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Glow", new String[] { "Glow", "Shadow" }));
        Aqua.setmgr.register(new Setting("RenderMode", this, "2D", new String[] { "2D", "Rotation" }));
        Aqua.setmgr.register(new Setting("TargetHUDMode", this, "Glow", new String[] { "1", "2", "NovolineOld", "Novoline", "Classic", "Hanabi", "Rise", "RiseGlow", "Rise6", "Tenacity", "Tenacity2" }));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPostRender2D) {
            if (GuiNewChat.animatedChatOpen && Killaura.target == null) {
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("2")) {
                    this.drawTargetHUD2(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity") || Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity2")) {
                    this.drawTencityHUD(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("NovolineOld")) {
                    if (Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDOldNovolineFollowing(TargetHUD.mc.thePlayer);
                    }
                    if (!Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDOldNovoline(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                    }
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Novoline")) {
                    this.drawTargetHUDNovoline(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise")) {
                    this.drawTargetHUDRise(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise6")) {
                    this.drawTargetHUDRise6(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("RiseGlow")) {
                    this.drawTargetHUDRiseGlow(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Hanabi")) {
                    this.drawTargetHUDHanabi(TargetHUD.mc.thePlayer);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("1")) {
                    this.drawTargetHUD(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Classic")) {
                    if (Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDClassicFollowing(TargetHUD.mc.thePlayer);
                    }
                    if (!Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDClassic(TargetHUD.mc.thePlayer);
                    }
                }
            }
            if (Killaura.target != null) {
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Classic")) {
                    if (Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDClassicFollowing(Killaura.target);
                    }
                    if (!Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDClassic(Killaura.target);
                    }
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("NovolineOld")) {
                    if (Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDOldNovolineFollowing(Killaura.target);
                    }
                    if (!Aqua.setmgr.getSetting("TargetHUDRenderMode").getCurrentMode().equalsIgnoreCase("Rotation")) {
                        this.drawTargetHUDOldNovoline(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                    }
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Novoline")) {
                    this.drawTargetHUDNovoline(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise")) {
                    this.drawTargetHUDRise(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise6")) {
                    this.drawTargetHUDRise6(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("RiseGlow")) {
                    this.drawTargetHUDRiseGlow(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Hanabi")) {
                    this.drawTargetHUDHanabi(Killaura.target);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("1")) {
                    this.drawTargetHUD(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("2")) {
                    this.drawTargetHUD2(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity") || Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity2")) {
                    this.drawTencityHUD(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
            }
        }
        if (e instanceof EventRender2D) {
            if (GuiNewChat.animatedChatOpen && Killaura.target == null) {
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("1")) {
                    this.drawTargetHUDShaders(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise") || Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("RiseGlow")) {
                    this.drawTargetHUDRiseShaders(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise6")) {
                    this.drawTargetHUDRise6Shaders(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity")) {
                    this.drawTencityHUDBlur(TargetHUD.mc.thePlayer, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("2")) {
                    this.drawTargetHUD2Blur(TargetHUD.mc.thePlayer);
                }
            }
            if (Killaura.target != null) {
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("1")) {
                    this.drawTargetHUDShaders(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise") || Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("RiseGlow")) {
                    this.drawTargetHUDRiseShaders(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Rise6")) {
                    this.drawTargetHUDRise6Shaders(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity")) {
                    this.drawTencityHUDBlur(Killaura.target, Mouse.getX() / 2, (Display.getHeight() - Mouse.getY()) / 2);
                }
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("2")) {
                    this.drawTargetHUD2Blur(Killaura.target);
                }
            }
        }
    }
    
    public void drawTencityHUD(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 40 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        final Color colorAlpha = RenderUtil.getColorAlpha(new Color(Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? Aqua.setmgr.getSetting("HUDColor").getColor() : Aqua.setmgr.getSetting("TargetHUDColor").getColor()).getRGB(), 90);
        if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity")) {
            RenderUtil.drawRoundedRect2Alpha(left - 7, top + 11, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 24.0f, 43.0, 4.0, new Color(20, 20, 20, 50));
        }
        else {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRectGradient(left - 7, top + 11, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 24.0f, 43.0, 4.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor())), false);
            RenderUtil.drawRoundedRectGradient(left - 7, top + 11, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 24.0f, 43.0, 4.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()));
        }
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity")) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(left + 35, bottom - 17, healthPos, 5.0, 2.0, -1), false);
        }
        RenderUtil.drawRoundedRect2Alpha(left + 35, bottom - 17, right4, 5.0, 2.0, new Color(20, 20, 20, 20));
        RenderUtil.drawRoundedRect(left + 35, bottom - 17, healthPos, 5.0, 2.0, -1);
        Aqua.INSTANCE.tenacityBig.drawString(target1.getName(), (float)(left + 50), (float)(top + 15), -1);
        final String distance = String.valueOf(Math.round(TargetHUD.mc.thePlayer.getDistanceToEntity(target1)));
        Aqua.INSTANCE.tenacityNormal.drawString(Math.round(target1.getHealth()) + "H - " + distance + ".0M", (float)(left + 50), (float)(top + 40), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {}
                StencilUtil.write(false);
                RenderUtil.drawCircle(left + 13.3, top + 32, 16.0, -1);
                StencilUtil.erase(true);
                if (Aqua.setmgr.getSetting("TargetHUDTargetHUDMode").getCurrentMode().equalsIgnoreCase("Tenacity")) {
                    final double offset = -(((AbstractClientPlayer)target1).hurtTime * 23);
                    RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
                }
                Gui.drawScaledCustomSizeModalRect(left - 3, top + 15, 8.0f, 8.0f, 8, 8, 34, 34, 64.0f, 66.0f);
                StencilUtil.dispose();
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTencityHUDBlur(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 40 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        final Color colorAlpha = RenderUtil.getColorAlpha(new Color(Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? Aqua.setmgr.getSetting("HUDColor").getColor() : Aqua.setmgr.getSetting("TargetHUDColor").getColor()).getRGB(), 90);
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(left - 7, top + 11, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 24.0f, 43.0, 4.0, colorAlpha), false);
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUDClassic(final EntityLivingBase target1) {
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = s.getScaledWidth() / 2 + 5 + 62;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 80 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = s.getScaledHeight() / 2 - 25 + 70;
        final int bottom = s.getScaledHeight() / 2 + 25 + 70;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left - 8, top + 9, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 20.0f, 52.0, 0.0, Color.black);
        RenderUtil.drawRoundedRect2Alpha(left - 7, top + 10, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 50.0, 0.0, new Color(30, 30, 30, 255));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left - 2, bottom + 2, healthPos, 6.0, 0.0, new Color(color[0], color[1], color[2]).getRGB());
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 50), (float)(top + 15), -1);
        TargetHUD.mc.fontRendererObj.drawString("\u2764", left + 50, top + 32, -1);
        Aqua.INSTANCE.comfortaa3.drawString("Health : " + Math.round(curTargetHealth) + ".0", (float)(left + 60), (float)(bottom - 19), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {
                    GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                }
                Gui.drawScaledCustomSizeModalRect(s.getScaledWidth() / 2 + 10 + 55, s.getScaledHeight() / 2 - 5 + 65, 8.0f, 8.0f, 8, 8, 34, 34, 64.0f, 66.0f);
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUDRiseShaders(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 16 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 35;
        final float curTargetHealth = target1.getHealth();
        final float maxTargetHealth = target1.getMaxHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final float nameWidth = 38.0f;
        final int scaleOffset = (int)(((EntityPlayer)target1).hurtTime * 0.35f);
        final float posX = (float)(left - 30);
        final float posY = (float)(top + 32);
        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX + 38.0f + 2.0f, posY - 34.0f, 129.0f + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2.0f - 18.0f, 40.0, 8.0, new Color(0, 0, 0, 0).getRGB()), false);
    }
    
    public void drawTargetHUDRise6Shaders(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 100;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 16 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 35;
        final float curTargetHealth = target1.getHealth();
        final float maxTargetHealth = target1.getMaxHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final float nameWidth = 38.0f;
        final int scaleOffset = (int)(((EntityPlayer)target1).hurtTime * 0.35f);
        final float posX = (float)(left - 30);
        final float posY = (float)(top + 32);
        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(posX + 38.0f + 2.0f, posY - 34.0f, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 40.0, 3.0, new Color(0, 0, 0, 70)), false);
    }
    
    public void drawTargetHUDRise(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 12 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 22;
        final int top = mouseY;
        final int bottom = mouseY + 35;
        final float curTargetHealth = target1.getHealth();
        final float maxTargetHealth = target1.getMaxHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final float nameWidth = 38.0f;
        final int scaleOffset = (int)(((EntityPlayer)target1).hurtTime * 0.35f);
        final float posX = (float)(left - 30);
        final float posY = (float)(top + 32);
        RenderUtil.drawRoundedRect2Alpha(posX + 38.0f + 2.0f, posY - 34.0f, 129.0f + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2.0f - 18.0f, 40.0, 8.0, new Color(0, 0, 0, 10));
        if (target1 instanceof AbstractClientPlayer) {
            StencilUtil.write(false);
            RenderUtil.circle(posX + 38.0f + 6.0f + scaleOffset / 2.0f, posY - 34.0f + 5.0f + scaleOffset / 2.0f, 30 - scaleOffset, Color.BLACK);
            StencilUtil.erase(true);
            final double offset = -(((AbstractClientPlayer)target1).hurtTime * 23);
            RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
            final EntityPlayer en = (EntityPlayer)target1;
            renderPlayerModelTexture(posX + 38.0f + 6.0f + scaleOffset / 2.0f, posY - 34.0f + 5.0f + scaleOffset / 2.0f, 3.0f, 3.0f, 3, 3, 30 - scaleOffset, 30 - scaleOffset, 24.0f, 24.5f, (AbstractClientPlayer)en);
            GlStateManager.resetColor();
            StencilUtil.dispose();
        }
        final float offset2 = 39.0f;
        final float drawBarPosX = posX + 38.0f;
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left + 50, bottom - 14, healthPos, 5.0, 2.0, new Color(color[0], color[1], color[2]).getRGB());
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 63), (float)(top + 5), -1);
        if (target1.getHealth() != 20.0f) {
            Aqua.INSTANCE.comfortaa4.drawString(String.valueOf(Math.round(Float.parseFloat(target1.getHealth() + ""))), left + healthPos + 52.0f, bottom - 16.5f, -1);
        }
    }
    
    public void drawTargetHUDRiseGlow(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 12 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 22;
        final int top = mouseY;
        final int bottom = mouseY + 35;
        final float curTargetHealth = target1.getHealth();
        final float maxTargetHealth = target1.getMaxHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final float nameWidth = 38.0f;
        final int scaleOffset = (int)(((EntityPlayer)target1).hurtTime * 0.35f);
        final float posX = (float)(left - 30);
        final float posY = (float)(top + 32);
        RenderUtil.drawRoundedRect2Alpha(posX + 38.0f + 2.0f, posY - 34.0f, 129.0f + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2.0f - 18.0f, 40.0, 8.0, new Color(0, 0, 0, 10));
        if (target1 instanceof AbstractClientPlayer) {
            StencilUtil.write(false);
            RenderUtil.circle(posX + 38.0f + 6.0f + scaleOffset / 2.0f, posY - 34.0f + 5.0f + scaleOffset / 2.0f, 30 - scaleOffset, Color.BLACK);
            StencilUtil.erase(true);
            final double offset = -(((AbstractClientPlayer)target1).hurtTime * 23);
            RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
            final EntityPlayer en = (EntityPlayer)target1;
            renderPlayerModelTexture(posX + 38.0f + 6.0f + scaleOffset / 2.0f, posY - 34.0f + 5.0f + scaleOffset / 2.0f, 3.0f, 3.0f, 3, 3, 30 - scaleOffset, 30 - scaleOffset, 24.0f, 24.5f, (AbstractClientPlayer)en);
            GlStateManager.resetColor();
            StencilUtil.dispose();
        }
        final float offset2 = 39.0f;
        final float drawBarPosX = posX + 38.0f;
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(posX + 38.0f + 2.0f, posY - 34.0f, 129.0f + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2.0f - 18.0f, 40.0, 8.0, new Color(0, 0, 0, 170)), false);
        RenderUtil.drawRoundedRect(left + 50, bottom - 14, healthPos, 5.0, 2.0, new Color(color[0], color[1], color[2]).getRGB());
        Shadow.drawGlow(() -> Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 63), (float)(top + 3), -1), false);
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 63), (float)(top + 3), -1);
        if (target1.getHealth() != 20.0f) {
            Aqua.INSTANCE.comfortaa4.drawString(String.valueOf(Math.round(Float.parseFloat(target1.getHealth() + ""))), left + healthPos + 52.0f, bottom - 16.5f, -1);
        }
    }
    
    public void drawTargetHUDRise6(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 100;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 12 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 22;
        final int top = mouseY;
        final int bottom = mouseY + 35;
        final float curTargetHealth = target1.getHealth();
        final float maxTargetHealth = target1.getMaxHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final float nameWidth = 38.0f;
        final int scaleOffset = (int)(((EntityPlayer)target1).hurtTime * 0.35f);
        final float posX = (float)(left - 30);
        final float posY = (float)(top + 32);
        ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha(posX + 38.0f + 2.0f, posY - 34.0f, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 40.0, 3.0, new Color(0, 0, 0, 180)), false);
        RenderUtil.drawRoundedRect2Alpha(posX + 38.0f + 2.0f, posY - 34.0f, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 40.0, 3.0, new Color(0, 0, 0, 50));
        if (target1 instanceof AbstractClientPlayer) {
            StencilUtil.write(false);
            RenderUtil.drawRoundedRect(left + 14, top + 2, 32.0, 32.0, 3.0, Color.white.getRGB());
            StencilUtil.erase(true);
            final double offset = -(((AbstractClientPlayer)target1).hurtTime * 3);
            RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
            final EntityPlayer en = (EntityPlayer)target1;
            renderPlayerModelTexture(posX + 38.0f + 6.0f, posY - 33.0f + 5.0f - 2.0f, 3.0f, 3.0f, 3, 3, 32, 32, 24.0f, 24.5f, (AbstractClientPlayer)en);
            GlStateManager.resetColor();
            StencilUtil.dispose();
        }
        final float offset2 = 39.0f;
        final float drawBarPosX = posX + 38.0f;
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        final int n;
        final int n2;
        final float n3;
        final Object o;
        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(n + 50, n2 - 14, n3, 5.0, 2.0, new Color(o[0], o[1], o[2]).getRGB()), false);
        RenderUtil.drawRoundedRect2Alpha(left + 50, bottom - 14, right4, 5.0, 2.0, new Color(40, 40, 40, 60));
        RenderUtil.drawRoundedRect(left + 50, bottom - 14, healthPos, 5.0, 2.0, new Color(color[0], color[1], color[2]).getRGB());
        Aqua.INSTANCE.comfortaa4.drawString("Name: ", (float)(left + 50), (float)(top + 5), Color.white.getRGB());
        Aqua.INSTANCE.comfortaa5.drawString(target1.getName(), (float)(left + 80), top + 5.5f, new Color(color[0], color[1], color[2]).getRGB());
        if (target1.getHealth() < 19.0f) {
            Aqua.INSTANCE.comfortaa4.drawString(String.valueOf(Math.round(Float.parseFloat(target1.getHealth() + ""))), left + healthPos + 52.0f, bottom - 16.5f, -1);
        }
    }
    
    public void drawTargetHUDClassicFollowing(final EntityLivingBase target1) {
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = (int)(s.getScaledWidth() / 2 - 140 - RenderUtil.interpolate(TargetHUD.mc.thePlayer.getRotationYawHead(), TargetHUD.mc.thePlayer.rotationYawHead, 1.0));
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 80 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = (int)(s.getScaledHeight() / 2 + RenderUtil.interpolate(TargetHUD.mc.thePlayer.getRotationPitchHead(), TargetHUD.mc.thePlayer.rotationPitchHead, 1.0) - 30.0);
        final int bottom = s.getScaledHeight() / 2 + 25 + 70;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left - 8, top + 9, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 20.0f, bottom / 6.0f - 6.0f, 0.0, Color.black);
        RenderUtil.drawRoundedRect2Alpha(left - 7, top + 10, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, bottom / 6.0f - 8.0f, 0.0, new Color(30, 30, 30, 255));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left - 2, top + 50, healthPos, bottom / 15.0f - 19.0f, 0.0, new Color(color[0], color[1], color[2]).getRGB());
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 50), (float)(top + 15), -1);
        TargetHUD.mc.fontRendererObj.drawString("\u2764", left + 50, top + 32, -1);
        Aqua.INSTANCE.comfortaa3.drawString("Health : " + Math.round(curTargetHealth) + ".0", (float)(left + 60), (float)(top + 31), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {
                    GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                }
                Gui.drawScaledCustomSizeModalRect((int)(s.getScaledWidth() / 2 - 142 - RenderUtil.interpolate(TargetHUD.mc.thePlayer.getRotationYawHead(), TargetHUD.mc.thePlayer.rotationYawHead, 1.0)), top + 14, 8.0f, 8.0f, 8, 8, 34, 34, 64.0f, 66.0f);
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUDOldNovoline(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left - 7, top + 12, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 62.0f, 44.0, 0.0, Color.BLACK);
        RenderUtil.drawRoundedRect2Alpha(left - 6, top + 13, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 64.0f, 42.0, 0.0, new Color(45, 45, 45, 255));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left + 40, bottom - 20, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 113.0f, 12.0, 0.0, Color.black.getRGB());
        RenderUtil.drawRoundedRect(left + 40, bottom - 20, healthPos, 12.0, 0.0, new Color(color[0], color[1], color[2]).getRGB());
        TargetHUD.mc.fontRendererObj.drawStringWithShadow(target1.getName(), (float)(left + 40), (float)(top + 17), -1);
        TargetHUD.mc.fontRendererObj.drawStringWithShadow(Math.round(curTargetHealth) + ".0 ", (float)(left + 41), (float)(bottom - 5), -1);
        TargetHUD.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(left + 62), (float)(bottom - 6), new Color(color[0], color[1], color[2]).getRGB());
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {}
                final double offset = -(((AbstractClientPlayer)target1).hurtTime * 23);
                RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
                Gui.drawScaledCustomSizeModalRect(left - 4, top + 14, 8.0f, 8.0f, 8, 8, 39, 40, 64.0f, 66.0f);
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUDNovoline(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(left - 7, top + 12, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 62.0f, 44.0, 2.0, new Color(0, 0, 0, 220)), false);
        RenderUtil.drawRoundedRect2Alpha(left - 7, top + 12, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 62.0f, 44.0, 2.0, new Color(0, 0, 0, 70));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left + 40, bottom - 20, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 113.0f, 12.0, 0.0, Color.black.getRGB());
        RenderUtil.drawRoundedRectGradient(left + 40, bottom - 20, healthPos, 12.0, 0.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()));
        TargetHUD.mc.fontRendererObj.drawStringWithShadow(target1.getName(), (float)(left + 40), (float)(top + 17), -1);
        TargetHUD.mc.fontRendererObj.drawStringWithShadow(Math.round(curTargetHealth) + ".0 ", (float)(left + 41), (float)(bottom - 5), -1);
        TargetHUD.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(left + 62), (float)(bottom - 6), new Color(color[0], color[1], color[2]).getRGB());
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {}
                final double offset = -(((AbstractClientPlayer)target1).hurtTime * 23);
                RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
                Gui.drawScaledCustomSizeModalRect(left - 4, top + 14, 8.0f, 8.0f, 8, 8, 39, 40, 64.0f, 66.0f);
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUDOldNovolineFollowing(final EntityLivingBase target1) {
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final float angle = getAngle(target1) % 360.0f + 180.0f;
        final int left = (int)(s.getScaledWidth() / 2 - 140 - RenderUtil.interpolate(TargetHUD.mc.thePlayer.getRotationYawHead(), TargetHUD.mc.thePlayer.rotationYawHead, 1.0));
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = (int)(s.getScaledHeight() / 2 + RenderUtil.interpolate(TargetHUD.mc.thePlayer.getRotationPitchHead(), TargetHUD.mc.thePlayer.rotationPitchHead, 1.0) - 30.0);
        final int bottom = s.getScaledHeight() / 2 + 25 + 60;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left - 6, top + 12, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 62.0f, bottom / 6.0f - 18.0f, 0.0, Color.BLACK);
        RenderUtil.drawRoundedRect2Alpha(left - 5, top + 13, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 64.0f, bottom / 6.0f - 20.0f, 0.0, new Color(45, 45, 45, 255));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left + 40, top + 30, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 113.0f, bottom / 15.0f - 11.0f, 0.0, Color.black.getRGB());
        RenderUtil.drawRoundedRect(left + 40, top + 30, healthPos, bottom / 15.0f - 11.0f, 0.0, new Color(color[0], color[1], color[2]).getRGB());
        TargetHUD.mc.fontRendererObj.drawStringWithShadow(target1.getName(), (float)(left + 40), (float)(top + 17), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {}
                Gui.drawScaledCustomSizeModalRect((int)(s.getScaledWidth() / 2 - 143 - RenderUtil.interpolate(TargetHUD.mc.thePlayer.getRotationYawHead(), TargetHUD.mc.thePlayer.rotationYawHead, 1.0)), top + 14, 8.0f, 8.0f, 8, 8, 35, 34, 64.0f, 66.0f);
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUD2(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        GL11.glPushMatrix();
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 80 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left - 7, top + 11, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 48.0, cornerRadius, new Color(20, 20, 20, 60));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        final int n;
        final int n2;
        final float n3;
        final Object o;
        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(n - 2, n2 + 2, n3, 4.0, 2.0, new Color(o[0], o[1], o[2]).getRGB()), false);
        RenderUtil.drawRoundedRect(left - 2, bottom + 2, healthPos, 4.0, 2.0, new Color(color[0], color[1], color[2]).getRGB());
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 50), (float)(top + 15), -1);
        TargetHUD.mc.fontRendererObj.drawString("\u2764", left + 50, top + 32, -1);
        Aqua.INSTANCE.comfortaa3.drawString("Health : " + Math.round(curTargetHealth) + ".0", (float)(left + 60), (float)(bottom - 19), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                GlStateManager.pushMatrix();
                if (target1.hurtTime != 0) {}
                StencilUtil.write(false);
                RenderUtil.drawRoundedRect(left - 2, top + 15, 34.0, 34.0, cornerRadius, Color.white.getRGB());
                StencilUtil.erase(true);
                final double offset = -(((AbstractClientPlayer)target1).hurtTime * 23);
                RenderUtil.color(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)));
                Gui.drawScaledCustomSizeModalRect(left - 2, top + 15, 8.0f, 8.0f, 8, 8, 34, 34, 64.0f, 66.0f);
                StencilUtil.dispose();
                GlStateManager.popMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawTargetHUD2Blur(final EntityLivingBase target1) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = this.mouseX;
            this.mouseY = this.mouseY;
        }
        else {
            this.mouseX = this.mouseX;
            this.mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = this.mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 80 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = this.mouseY;
        final int bottom = this.mouseY + 50;
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(left - 7, top + 11, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 48.0, cornerRadius, new Color(20, 20, 20, 90)), false);
    }
    
    public void drawTargetHUD(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 16 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left - 7, top + 10, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 53.0, cornerRadius, new Color(20, 20, 20, 90));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        RenderUtil.drawRoundedRect(left + 50, bottom, healthPos + 10.0f, 8.0, 2.0, new Color(color[0], color[1], color[2]).getRGB());
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 50), (float)(top + 15), -1);
        TargetHUD.mc.fontRendererObj.drawString("\u2764", left + 50, top + 32, -1);
        Aqua.INSTANCE.comfortaa3.drawString("Health : " + Math.round(curTargetHealth) + ".0", (float)(left + 60), (float)(bottom - 19), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                Gui.drawScaledCustomSizeModalRect(left, top + 15, 8.0f, 8.0f, 8, 8, 43, 43, 64.0f, 66.0f);
            }
        }
    }
    
    public void drawTargetHUDHanabi(final EntityLivingBase target1) {
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = s.getScaledWidth() / 2 + 5 + 62;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 159;
        final int top = s.getScaledHeight() / 2 - 25 + 70;
        final int bottom = s.getScaledHeight() / 2 + 25 + 70;
        final float curTargetHealth = target1.getHealth();
        final float maxTargetHealth = target1.getMaxHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2;
        final float healthPos = calculatedHealth * right4;
        RenderUtil.drawRoundedRect2Alpha(left + 2, top + 18, 159.0, 40.0, 0.0, new Color(0, 0, 0, 200));
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Aqua.INSTANCE.comfortaa3.drawString("" + Math.round(curTargetHealth) + ".0", (float)(left + 140), (float)(bottom - 4), -1);
        TargetHUD.mc.fontRendererObj.drawString("\u2764", left + 130, bottom - 3, -1);
        RenderUtil.drawRoundedRectGradient(left + 2, bottom + 8, healthPos, 5.0, 0.0, new Color(0, 216, 245), new Color(0, 55, 245));
        Aqua.INSTANCE.comfortaa3.drawString(target1.getName(), (float)(left + 44), (float)(top + 23), -1);
        Aqua.INSTANCE.comfortaa5.drawString("XYZ:" + Math.round(TargetHUD.mc.thePlayer.posX) + " " + Math.round(TargetHUD.mc.thePlayer.posY) + " " + Math.round(TargetHUD.mc.thePlayer.posZ) + " | Hurt: true", (float)(left + 44), (float)(top + 37), -1);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                Gui.drawScaledCustomSizeModalRect(s.getScaledWidth() / 2 + 10 + 62, s.getScaledHeight() / 2 - 5 + 70, 8.0f, 8.0f, 8, 8, 36, 36, 64.0f, 66.0f);
            }
        }
    }
    
    public void drawTargetHUDShaders(final EntityLivingBase target1, int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0) && TargetHUD.mc.currentScreen instanceof GuiChat) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        else {
            mouseX = this.mouseX;
            mouseY = this.mouseY;
        }
        final ScaledResolution s = new ScaledResolution(TargetHUD.mc);
        final float cornerRadius = (float)Aqua.setmgr.getSetting("TargetHUDCornerRadius").getCurrentNumber();
        final Color backgroundColor = new Color(0, 0, 0, 120);
        final Color emptyBarColor = new Color(59, 59, 59, 160);
        final Color healthBarColor = Color.green;
        final Color distBarColor = new Color(20, 81, 208);
        final int left = mouseX;
        final int right2 = 142;
        final int right3 = s.getScaledWidth() / 2 + right2;
        final int right4 = 16 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) + 29;
        final int top = mouseY;
        final int bottom = mouseY + 50;
        final float curTargetHealth = target1.getHealth();
        final float healthProcent = target1.getHealth() / target1.getMaxHealth();
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        final float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        final float calculatedHealth = finalHealthPercentage;
        final int rectRight = right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) / 2 - 5;
        final float healthPos = calculatedHealth * right4;
        final int[] color = Aqua.setmgr.getSetting("TargetHUDClientColor").isState() ? ESP.getRGB(this.getColor()) : ESP.getRGB(this.getColor2());
        if (Aqua.setmgr.getSetting("TargetHUDMode").getCurrentMode().equalsIgnoreCase("Glow")) {
            final int n;
            final int n2;
            final int n3;
            final ScaledResolution scaledResolution;
            final float n4;
            final Object o;
            Arraylist.drawGlowArray(() -> RenderUtil.drawRoundedRect(n - 7, n2 + 10, n3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - scaledResolution.getScaledWidth() / 2.0f - 22.0f, 53.0, n4, new Color(o[0], o[1], o[2]).getRGB()), false);
        }
        if (Aqua.setmgr.getSetting("TargetHUDMode").getCurrentMode().equalsIgnoreCase("Shadow")) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(left - 7, top + 10, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 53.0, cornerRadius, new Color(20, 20, 20, 255).getRGB()), false);
        }
        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(left - 7, top + 10, right3 + Aqua.INSTANCE.comfortaa3.getStringWidth(target1.getName()) - s.getScaledWidth() / 2.0f - 22.0f, 53.0, cornerRadius, new Color(20, 20, 20, 150).getRGB()), false);
        if (this.lastHealthPercentage != healthProcent) {
            this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
        }
        this.lastHealthPercentage = healthProcent;
        this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int n5;
        final int n6;
        final float n7;
        final Object o2;
        Arraylist.drawGlowArray(() -> RenderUtil.drawRoundedRect(n5 + 50, n6, n7 + 10.0f, 12.0, 2.0, new Color(o2[0], o2[1], o2[2]).getRGB()), false);
        final List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TargetHUD.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object nextObject : NetworkMoment) {
            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
            if (TargetHUD.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == target1) {
                GlStateManager.enableCull();
                TargetHUD.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
            }
        }
    }
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("TargetHUDColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public int getColor() {
        try {
            return Aqua.setmgr.getSetting("HUDColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public static float getAngle(final Entity entity) {
        final double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, 1.0) - RenderUtil.interpolate(TargetHUD.mc.thePlayer.posX, TargetHUD.mc.thePlayer.lastTickPosX, 1.0);
        final double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, 1.0) - RenderUtil.interpolate(TargetHUD.mc.thePlayer.posZ, TargetHUD.mc.thePlayer.lastTickPosZ, 1.0);
        final float yaw = (float)(-Math.toDegrees(Math.atan2(x, z)));
        return (float)(yaw - RenderUtil.interpolate(TargetHUD.mc.thePlayer.rotationYaw, TargetHUD.mc.thePlayer.prevRotationYaw, 1.0));
    }
    
    public static void renderPlayerModelTexture(final double x, final double y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight, final AbstractClientPlayer target) {
        final ResourceLocation skin = target.getLocationSkin();
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(3042);
    }
}
