package koks.gui.panelgui;

import koks.gui.panelgui.guistuff.DrawModule;
import koks.modules.Module;
import koks.utilities.CustomFont;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 10.09.2020 : 09:35
 */
public class PanelGUI extends GuiScreen {

    public final CustomFont arial20 = new CustomFont("fonts/arial.ttf", 20);
    public final CustomFont arial18 = new CustomFont("fonts/arial.ttf", 18);
    public final CustomFont arial16 = new CustomFont("fonts/arial.ttf", 16);
    private final ArrayList<DrawPanel> drawPanels = new ArrayList<>();
    private int x, y = 50, width = 100, height = 15;

    public PanelGUI() {
        x = 50;
        for (Module.Category category : Module.Category.values()) {
            drawPanels.add(new DrawPanel(category, x, y, width, height));
            x += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (DrawPanel drawPanel : drawPanels) {
            drawPanel.drawScreen(mouseX, mouseY, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (DrawPanel drawPanel : drawPanels) {
            drawPanel.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (DrawPanel drawPanel : drawPanels) {
            drawPanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (DrawPanel drawPanel : drawPanels) {
            drawPanel.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}