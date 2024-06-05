package alphentus.gui.clickguipanel;

import alphentus.gui.clickguipanel.panel.DrawPanel;
import alphentus.init.Init;
import alphentus.mod.ModCategory;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends GuiScreen {

    ArrayList<DrawPanel> drawPanels = new ArrayList<>();
    FontRenderer fr = Init.getInstance().fontManager.getFont("arial", 18);
    int panelStartX = 100, panelStartY = 50, panelWidth = 95, panelHeight = 20;

    public ClickGUI() {
        for (ModCategory modCategory : ModCategory.values()) {
            drawPanels.add(new DrawPanel(modCategory, panelStartX, panelStartY, panelWidth, panelHeight));
            panelStartX += 100;
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
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (DrawPanel drawPanel : drawPanels) {
            drawPanel.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }
}