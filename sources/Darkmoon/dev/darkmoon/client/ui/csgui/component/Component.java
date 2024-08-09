package dev.darkmoon.client.ui.csgui.component;

import lombok.Setter;

public class Component {
    @Setter
    public float x, y, width, height;
    public Component(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(int mouseX, int mouseY) {

    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {

    }

    public void mouseReleased(double mouseX, double mouseY, int state) {

    }

    public void keyTyped(int keyCode) {

    }

    public boolean isVisible() {
        return true;
    }
}
