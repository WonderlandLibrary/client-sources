package dev.excellent.client.module.impl.misc.autobuy.screen;

import dev.excellent.api.interfaces.client.IAccess;
import net.minecraft.util.math.vector.Vector4f;
import net.mojang.blaze3d.matrix.MatrixStack;

public interface IAutoBuyPage extends IAccess {

    Vector4f position();


    void init();

    void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    boolean mouseClicked(double mouseX, double mouseY, int button);

    boolean mouseReleased(double mouseX, double mouseY, int button);

    boolean keyPressed(int keyCode, int scanCode, int modifiers);

    boolean keyReleased(int keyCode, int scanCode, int modifiers);

    boolean charTyped(char codePoint, int modifiers);

    void onClose();

}