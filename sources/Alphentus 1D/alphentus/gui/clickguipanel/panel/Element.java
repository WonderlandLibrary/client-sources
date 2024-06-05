package alphentus.gui.clickguipanel.panel;

import alphentus.init.Init;
import alphentus.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.io.IOException;

public class Element {

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr = Init.getInstance().fontManager.getFont("arial", 16);
    public int x, y, width, height;
    public boolean extended;
    public Setting setting;

    public Element() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void setValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}