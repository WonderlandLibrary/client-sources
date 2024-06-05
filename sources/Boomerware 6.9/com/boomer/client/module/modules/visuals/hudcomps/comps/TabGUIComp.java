package com.boomer.client.module.modules.visuals.hudcomps.comps;

import com.boomer.client.gui.tab.TabGUI;
import com.boomer.client.module.Module;
import com.boomer.client.module.modules.visuals.hudcomps.HudComp;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.value.impl.BooleanValue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * made by oHare for BoomerWare
 *
 * @since 7/13/2019
 **/
public class TabGUIComp extends HudComp {
    private ScaledResolution sr = RenderUtil.getResolution();
    private TabGUI tabGUI;
    public BooleanValue roundedTab = new BooleanValue("RoundedTab", false);
    public BooleanValue icons = new BooleanValue("Icons", false);

    public TabGUIComp() {
        super("TabGUIComp", 2, 26, 100, Module.Category.values().length * 12);
        addValues(roundedTab, icons);
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
        tabGUI = new TabGUI((float) getX(), (float) getY());
        tabGUI.init();
    }

    @Override
    public void onDisable() {
        tabGUI = null;
    }

    @Override
    public void onDrag() {
        super.onDrag();
        tabGUI.updatePosition();
    }

    @Override
    public void onKey(int key) {
        tabGUI.keypress(key);
    }

    @Override
    public void onRender(ScaledResolution sr) {
        super.onRender(sr);
        if (tabGUI == null) return;
        if (getX() != tabGUI.getX()) {
            tabGUI.setX((float) getX());
            tabGUI.updatePosition();
        }
        if (getY() != tabGUI.getY()) {
            tabGUI.setY((float) getY());
            tabGUI.updatePosition();
        }
        tabGUI.draw(this, sr);
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
