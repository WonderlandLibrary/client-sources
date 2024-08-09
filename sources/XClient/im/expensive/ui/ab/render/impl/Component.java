package im.expensive.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;

public abstract class Component implements IComponent {

    public float x,y,width,height;


    public boolean isHovered(int mouseX, int mouseY, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public boolean isHovered(double mouseX, double mouseY, float x,float y, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public boolean isHovered(double mouseX, double mouseY, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public abstract void mouseScrolled(double mouseX, double mouseY, double delta);
    @Override
    public abstract void mouseClicked(double mouseX, double mouseY, int mouseButton);

    @Override
    public abstract void mouseReleased(double mouseX, double mouseY, int mouseButton);

    @Override
    public abstract void keyTyped(int keyCode, int scanCode, int modifiers);

    @Override
    public abstract void charTyped(char codePoint, int modifiers);
    @Override
    public abstract void render(MatrixStack stack, int mouseX, int mouseY);
}