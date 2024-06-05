package com.boomer.client.module.modules.visuals.hudcomps.comps;

import com.boomer.client.Client;
import com.boomer.client.module.modules.visuals.HUD;
import com.boomer.client.module.modules.visuals.hudcomps.HudComp;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * made by oHare for BoomerWare
 *
 * @since 5/31/2019
 **/
public class CoordinateComp extends HudComp {
    private ScaledResolution sr = RenderUtil.getResolution();

    public CoordinateComp() {
        super("CoordinateComp", 2, RenderUtil.getResolution().getScaledHeight() - 12, 100, Fonts.hudfont.getHeight());
    }

    @Override
    public void onEnable() {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (sr.getScaledWidth() < scaledResolution.getScaledWidth() && getX() > sr.getScaledWidth() - getWidth() - 20) {
            setX(scaledResolution.getScaledWidth() - getWidth() - 2);
        }
        if (sr.getScaledHeight() < scaledResolution.getScaledHeight() && getY() > sr.getScaledHeight() - getHeight() - 20) {
            setY(scaledResolution.getScaledHeight() - getHeight() - 2);
        }
        if (sr.getScaledHeight() != scaledResolution.getScaledHeight()) {
            sr = scaledResolution;
        }
        if (sr.getScaledWidth() != scaledResolution.getScaledWidth()) {
            sr = scaledResolution;
        }
    }

    @Override
    public void onRender(ScaledResolution scaledResolution) {
        HUD hud = (HUD) Client.INSTANCE.getModuleManager().getModule("hud");
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft() == null) return;
        Fonts.hudfont.drawStringWithShadow((int) Minecraft.getMinecraft().thePlayer.posX + ", " + (int) Minecraft.getMinecraft().thePlayer.posY + ", " + (int) Minecraft.getMinecraft().thePlayer.posZ, getX(), getY() - ((getY() + getHeight() > scaledResolution.getScaledHeight() - 10 && Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) ? 12 : 0), hud.staticRainbow.isEnabled() ? color(2, 100) : new Color(0x616161).getRGB());
        if (getWidth() != Fonts.hudfont.getStringWidth((int) Minecraft.getMinecraft().thePlayer.posX + ", " + (int) Minecraft.getMinecraft().thePlayer.posY + ", " + (int) Minecraft.getMinecraft().thePlayer.posZ)) {
            setWidth(Fonts.hudfont.getStringWidth((int) Minecraft.getMinecraft().thePlayer.posX + ", " + (int) Minecraft.getMinecraft().thePlayer.posY + ", " + (int) Minecraft.getMinecraft().thePlayer.posZ));
        }
        if (getX() + getWidth() > scaledResolution.getScaledWidth())
            setX(scaledResolution.getScaledWidth() - getWidth() - 2);
        if (getX() < 0) setX(0);
        if (getY() + getHeight() > scaledResolution.getScaledHeight())
            setY(scaledResolution.getScaledHeight() - getHeight() - 2);
        if (getY() < 0) setY(0);
    }

    @Override
    public void onResize(ScaledResolution scaledResolution) {
        if (sr.getScaledWidth() < scaledResolution.getScaledWidth() && getX() > sr.getScaledWidth() - getWidth() - 20) {
            setX(scaledResolution.getScaledWidth() - getWidth() - 2);
        }
        if (sr.getScaledHeight() < scaledResolution.getScaledHeight() && getY() > sr.getScaledHeight() - getHeight() - 20) {
            setY(scaledResolution.getScaledHeight() - getHeight() - 2);
        }
        if (sr.getScaledHeight() != scaledResolution.getScaledHeight()) {
            sr = scaledResolution;
        }
    }

    @Override
    public void onFullScreen(float w, float h) {
        if (sr.getScaledWidth() < w && getX() > sr.getScaledWidth() - getWidth() - 20) {
            setX(w - (sr.getScaledWidth() - getWidth()) - 2);
        }
        if (sr.getScaledHeight() < h && getY() > sr.getScaledHeight() - getHeight() - 20) {
            setY(h - (sr.getScaledHeight() - getHeight()) - 2);
        }
        if (sr.getScaledHeight() != new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight()) {
            sr = new ScaledResolution(Minecraft.getMinecraft());
        }
    }
}