package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 4/08/2017.
 * Updated 24 November 2019 by hub
 */
public class RootCheckButtonUI<T extends CheckButton> extends AbstractComponentUI<CheckButton> {

    private GuiManager guiManager = KamiMod.getInstance().guiManager;

    protected Color idleColourNormal = new Color(200, 200, 200);
    protected Color downColourNormal = new Color(190, 190, 190);

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {


        glColor4f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue(), component.getOpacity());
        if (component.isToggled()) {
            glColor3f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());
        }
        if (component.isHovered() || component.isPressed()) {
            glColor4f(1, 1, 1, component.getOpacity());
        }

        Color color = new Color(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());

        String text = component.getName();
        int c = component.isPressed() ? 0xaaaaaa : component.isToggled() ? color.getRGB() : 0xdddddd;
        if (component.isHovered()) {
            c = color.brighter().getRGB();
        }

        glColor3f(1, 1, 1);
        glEnable(GL_TEXTURE_2D);
        KamiGUI.fontRenderer.drawString(component.getWidth() / 2 - KamiGUI.fontRenderer.getStringWidth(text) / 2, KamiGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KamiGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KamiGUI.fontRenderer.getFontHeight() + 2);
    }
}
