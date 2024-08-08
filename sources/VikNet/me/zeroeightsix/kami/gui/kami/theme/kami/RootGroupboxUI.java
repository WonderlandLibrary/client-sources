package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Groupbox;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * Created by 086 on 26/06/2017.
 * Updated 24 November 2019 by hub
 */
public class RootGroupboxUI extends AbstractComponentUI<Groupbox> {

    private GuiManager guiManager = KamiMod.getInstance().guiManager;

    @Override
    public void renderComponent(Groupbox component, FontRenderer fontRenderer) {
        GL11.glLineWidth(1f);
        fontRenderer.drawString(1, 1, component.getName());

        glColor3f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex2d(0, 0);
            GL11.glVertex2d(component.getWidth(), 0);

            GL11.glVertex2d(component.getWidth(), 0);
            GL11.glVertex2d(component.getWidth(), component.getHeight());

            GL11.glVertex2d(component.getWidth(), component.getHeight());
            GL11.glVertex2d(0, component.getHeight());

            GL11.glVertex2d(0, component.getHeight());
            GL11.glVertex2d(0, 0);
        }
        GL11.glEnd();
    }

    @Override
    public void handleMouseDown(Groupbox component, int x, int y, int button) {
    }

    @Override
    public void handleAddComponent(Groupbox component, Container container) {
        component.setWidth(100);
        component.setHeight(100);
        component.setOriginOffsetY(component.getTheme().getFontRenderer().getFontHeight() + 3);
    }
}
