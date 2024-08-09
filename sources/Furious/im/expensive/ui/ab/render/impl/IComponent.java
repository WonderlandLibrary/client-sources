package im.expensive.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IComponent {

    void render(MatrixStack stack, int mouseX, int mouseY);
    void mouseClicked(double mouseX, double mouseY, int mouseButton);

    void mouseReleased(double mouseX, double mouseY, int mouseButton);
    void mouseScrolled(double mouseX, double mouseY, double delta);

    void keyTyped(int keyCode, int scanCode, int modifiers);

    void charTyped(char codePoint, int modifiers);
}
