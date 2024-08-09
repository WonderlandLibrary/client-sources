package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.BooleanValue;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class BooleanValueComponent extends ValueComponent {
    public BooleanValueComponent(Value<?> value, float width) {
        super(value, width - 10);
    }


    @Override
    public void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (position == null) return;
        BooleanValue booleanValue = (BooleanValue) value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);
        animation.run(booleanValue.getValue() ? 1 : 0);

        float padding = 5;
        float x = position.x;
        float y = position.y;

        font12.draw(matrixStack, booleanValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        int enabledColor = ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));
        int disabledColor = ColorUtil.replAlpha(getTheme().getClientColor().darker().darker().darker().darker().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));

        float size = 16;
        float bheight = 8;

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, x + padding + width - size, y + padding, size, bheight)) {
                if (!hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                    hovered = true;
                }
            } else {
                if (hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                    hovered = false;
                }
            }
        }

        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + width - size, y + padding, x + padding + width - size + size, y + padding + bheight, bheight / 2F, 1, disabledColor, disabledColor, disabledColor, disabledColor, true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, (float) (x + padding + width - size + 1 + (animation.getValue() * (size / 2F))), y + padding + 1, (float) (x + padding + width - size + 1 + (animation.getValue() * (size / 2F))) + bheight - 2, y + padding + 1 + bheight - 2, (bheight - 2) / 2F, 1, enabledColor, enabledColor, enabledColor, enabledColor, true, true, true, true);


        height = padding + bheight + padding;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (position == null) return;
        BooleanValue booleanValue = (BooleanValue) value;

        float padding = 5;
        float x = position.x;
        float y = position.y;

        if (isHover(mouseX, mouseY, x + padding, y, width, height) && isLClick(button)) {
            booleanValue.setValue(!booleanValue.getValue());
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }

    @Override
    public void onClose() {

    }
}
