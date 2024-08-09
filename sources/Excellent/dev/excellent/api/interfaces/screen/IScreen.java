package dev.excellent.api.interfaces.screen;

import dev.excellent.api.interfaces.client.IAccess;
import net.mojang.blaze3d.matrix.MatrixStack;

public interface IScreen extends IAccess {

    void init();

    void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    boolean mouseClicked(double mouseX, double mouseY, int button);

    boolean mouseReleased(double mouseX, double mouseY, int button);

    boolean keyPressed(int keyCode, int scanCode, int modifiers);

    boolean keyReleased(int keyCode, int scanCode, int modifiers);

    boolean charTyped(char codePoint, int modifiers);

    void onClose();

}