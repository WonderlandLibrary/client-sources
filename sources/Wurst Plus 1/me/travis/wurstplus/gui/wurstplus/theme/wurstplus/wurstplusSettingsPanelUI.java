package me.travis.wurstplus.gui.wurstplus.theme.wurstplus;

import me.travis.wurstplus.gui.wurstplus.RenderHelper;
import me.travis.wurstplus.gui.wurstplus.component.SettingsPanel;
import me.travis.wurstplus.gui.rgui.render.AbstractComponentUI;
import me.travis.wurstplus.gui.rgui.render.font.FontRenderer;

import static org.lwjgl.opengl.GL11.*;

public class wurstplusSettingsPanelUI extends AbstractComponentUI<SettingsPanel> {

    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
        glLineWidth(2f);
        glColor4f(.5f, .5f, .5f, .9f); // .66f,.33f,.0f,.9f
        RenderHelper.drawFilledRectangle(0,0,component.getWidth(),component.getHeight());
        glColor3f(.85f,.85f,.85f); // outline
        glLineWidth(2.5f);
        RenderHelper.drawRectangle(0,0,component.getWidth(),component.getHeight());
    }
}

// box that appears for settings
