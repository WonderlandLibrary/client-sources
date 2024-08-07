package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 16/12/2017.
 * Updated 24 November 2019 by hub
 */
public class KamiSettingsPanelUI extends AbstractComponentUI<SettingsPanel> {

    private GuiManager guiManager = KamiMod.getInstance().guiManager;

    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
//        glLineWidth(2);
//        glColor3f(.59f,.05f,.11f);
//        glBegin(GL_LINES);
//        {
//            glVertex2d(0,component.getHeight());
//            glVertex2d(component.getWidth(),component.getHeight());
//        }
//        glEnd();

        glLineWidth(2f);
        glColor4f(.17f, .17f, .18f, .9f);
        RenderHelper.drawFilledRectangle(0, 0, component.getWidth(), component.getHeight());
        glColor3f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());
        glLineWidth(1.5f);
        RenderHelper.drawRectangle(0, 0, component.getWidth(), component.getHeight());
    }
}
