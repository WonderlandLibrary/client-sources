package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.kami.RootSmallFontRenderer;
import me.zeroeightsix.kami.gui.kami.component.ColorizedCheckButton;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 8/08/2017.
 * Updated 24 November 2019 by hub
 */
public class RootColorizedCheckButtonUI extends RootCheckButtonUI<ColorizedCheckButton> {

    RootSmallFontRenderer ff = new RootSmallFontRenderer();

    private GuiManager guiManager = KamiMod.getInstance().guiManager;

    public RootColorizedCheckButtonUI() {
        downColourNormal = new Color(190, 190, 190);
    }

    @Override
    public void renderComponent(CheckButton component, FontRenderer aa) {
        glColor4f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue(), component.getOpacity());
        if (component.isHovered() || component.isPressed()) {
            glColor4f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue(), component.getOpacity());
        }
        if (component.isToggled()) {
            glColor3f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());
        }

//        RenderHelper.drawRoundedRectangle(0,0,component.getWidth(), component.getHeight(), 3f);
        glLineWidth(2.5f);
        glBegin(GL_LINES);
        {
            glVertex2d(0, component.getHeight());
            glVertex2d(component.getWidth(), component.getHeight());
        }
        glEnd();

        Color idleColour = component.isToggled() ? idleColourNormal.brighter() : idleColourNormal;
        Color downColour = component.isToggled() ? downColourNormal.brighter() : downColourNormal;

        glColor3f(1, 1, 1);
        glEnable(GL_TEXTURE_2D);
        ff.drawString(component.getWidth() / 2 - KamiGUI.fontRenderer.getStringWidth(component.getName()) / 2, 0, component.isPressed() ? downColour : idleColour, component.getName());
        glDisable(GL_TEXTURE_2D);
    }
}
