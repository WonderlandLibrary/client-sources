package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * Created by 086 on 6/08/2017.
 * Updated 24 November 2019 by hub
 */
public class RootSettingsPanelUI extends AbstractComponentUI<SettingsPanel> {

    private GuiManager guiManager = KamiMod.getInstance().guiManager;

    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        glColor3f(guiManager.getGuiRed(), guiManager.getGuiGreen(), guiManager.getGuiBlue());
        RenderHelper.drawOutlinedRoundedRectangle(0, 0, component.getWidth(), component.getHeight(), 6f, 0.14f, 0.14f, 0.14f, component.getOpacity(), 1f);
    }

}
