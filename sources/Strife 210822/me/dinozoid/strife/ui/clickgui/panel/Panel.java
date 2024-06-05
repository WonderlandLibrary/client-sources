package me.dinozoid.strife.ui.clickgui.panel;

public abstract class Panel {

    protected float x, y, width, height;

    public abstract void drawScreen(int mouseX, int mouseY);
    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);
    public abstract void keyTyped(char typedChar, int keyCode);
}
