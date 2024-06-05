package com.boomer.client.module.modules.visuals.hudcomps.comps;

import com.boomer.client.Client;
import com.boomer.client.module.modules.visuals.HUD;
import com.boomer.client.module.modules.visuals.hudcomps.HudComp;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.font.Fonts;
import com.boomer.client.utils.value.impl.StringValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Xen for BoomerWare
 * @since 8/6/2019
 **/
public class TimeComp extends HudComp {
    private ScaledResolution sr = RenderUtil.getResolution();

    private StringValue style = new StringValue("Style", "&7(%time)");

    public TimeComp() {
        super("TimeComp", 2, 16, 100, Fonts.hudfont.getHeight());
        addValues(style);
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
    public void onRender(ScaledResolution sr) {
        super.onRender(sr);
        HUD hud = (HUD) Client.INSTANCE.getModuleManager().getModule("hud");
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        String date = dateFormat.format(new Date());
        String styleString = style.getValue().replaceAll("&", "\247").replaceAll("%time", date);
        setWidth(Fonts.hudfont.getStringWidth(styleString));
        Fonts.hudfont.drawStringWithShadow(styleString, getX(), getY(), hud.staticRainbow.isEnabled() ? color(2, 100) : new Color(0x616161).getRGB());

        if (getX() + getWidth() > sr.getScaledWidth()) setX(sr.getScaledWidth() - getWidth() - 2);
        if (getX() < 0) setX(0);
        if (getY() + getHeight() > sr.getScaledHeight()) setY(sr.getScaledHeight() - getHeight() - 2);
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