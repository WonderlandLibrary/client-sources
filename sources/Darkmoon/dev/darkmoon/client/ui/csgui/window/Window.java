package dev.darkmoon.client.ui.csgui.window;

import lombok.Setter;
import net.minecraft.client.Minecraft;

public class Window {
    @Setter
    public float x, y, width, height;
    public final Minecraft mc = Minecraft.getMinecraft();

    public Window(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void init() {

    }

    public void render(int mouseX, int mouseY) {

    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {

    }

    public void mouseReleased(double mouseX, double mouseY, int state) {

    }

    public void keyTyped(int keyCode) {

    }
}
