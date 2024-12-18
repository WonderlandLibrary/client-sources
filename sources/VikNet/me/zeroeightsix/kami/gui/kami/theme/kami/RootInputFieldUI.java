package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.InputField;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * Created by 086 on 30/06/2017.
 * Updated 24 November 2019 by hub
 */
public class RootInputFieldUI<T extends InputField> extends AbstractComponentUI<InputField> {

    private GuiManager guiManager = KamiMod.getInstance().guiManager;

    @Override
    public void renderComponent(InputField component, FontRenderer fontRenderer) {
//        glColor3f(1,0.22f,0.22f);
//        RenderHelper.drawOutlinedRoundedRectangle(0,0,component.getWidth(),component.getHeight(),6f);
        GL11.glColor3f(0.33f, 0.22f, 0.22f);
        RenderHelper.drawFilledRectangle(0, 0, component.getWidth(), component.getHeight());
        GL11.glLineWidth(1.5f);
        glColor3f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());
        RenderHelper.drawRectangle(0, 0, component.getWidth(), component.getHeight());
    }

    @Override
    public void handleAddComponent(InputField component, Container container) {
        component.setWidth(200);
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight());
    }
}
