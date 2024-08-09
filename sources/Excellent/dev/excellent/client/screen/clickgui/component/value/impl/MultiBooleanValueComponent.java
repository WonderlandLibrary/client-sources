package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class MultiBooleanValueComponent extends ValueComponent {
    public MultiBooleanValueComponent(Value<?> value, float width) {
        super(value, width - 10);
    }

    @Override
    public void init() {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        float padding = 5;
        float x = position.x;
        float y = position.y;

        font12.draw(matrixStack, multiBooleanValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        float size = 16;

        float offset = 0;
        for (BooleanValue booleanValue : multiBooleanValue.getValues()) {
            boolean selected = multiBooleanValue.get(booleanValue.getName()).getValue().equals(true);
            booleanValue.animation.run(selected ? 1 : 0);
            float yPos = y + padding + font12.getHeight() + padding + offset;
            int enabledColor = ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));
            int disabledColor = ColorUtil.replAlpha(getTheme().getClientColor().darker().darker().darker().darker().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));

            float bheight = 8;

            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, yPos, x + padding + size, yPos + bheight, bheight / 2F, 1, disabledColor, disabledColor, disabledColor, disabledColor, true, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + 1 + (float) (booleanValue.animation.getValue() * (size / 2F)), yPos + 1, (float) (x + padding + 1 + (booleanValue.animation.getValue() * (size / 2F))) + bheight - 2, yPos + 1 + bheight - 2, (bheight - 2) / 2F, 1, enabledColor, enabledColor, enabledColor, enabledColor, true, true, true, true);

            font12.draw(matrixStack, booleanValue.getName(), x + padding + size + 2.5F, yPos + 0.5F, selected ? ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))) : ColorUtil.getColor(200, 200, 200, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
            offset += bheight + 4F;
        }

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, x + 5, y + padding + font12.getHeight() + padding, size, offset - 4F)) {
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

        height = padding + font12.getHeight() + padding + offset + padding - 4F;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (position == null) return;
        MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;

        float padding = 5;
        float x = position.x;
        float y = position.y;

        float offset = 0;
        for (BooleanValue booleanValue : multiBooleanValue.getValues()) {
            float yPos = y + padding + font12.getHeight() + padding + offset;
            float size = 8;
            if (isLClick(button) && isHover(mouseX, mouseY, x + padding, yPos - (size / 4F), width, size + (size / 2F))) {
                multiBooleanValue.get(booleanValue.getName()).setValue(!multiBooleanValue.get(booleanValue.getName()).getValue());
            }
            offset += size + 4F;
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
