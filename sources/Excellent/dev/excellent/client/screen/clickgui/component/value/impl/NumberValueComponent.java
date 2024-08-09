package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class NumberValueComponent extends ValueComponent {
    public boolean grabbed;
    private float percentage;
    private float current;
    private boolean hover;

    public NumberValueComponent(Value<?> value, float width) {
        super(value, width - 10);
        NumberValue numberValue = (NumberValue) value;
        percentage = (-numberValue.getMin().floatValue() + numberValue.getValue().floatValue()) / (-numberValue.getMin().floatValue() + numberValue.getMax().floatValue());
    }

    @Override
    public void init() {
        NumberValue numberValue = (NumberValue) value;
        percentage = (-numberValue.getMin().floatValue() + numberValue.getValue().floatValue()) / (-numberValue.getMin().floatValue() + numberValue.getMax().floatValue());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        NumberValue numberValue = (NumberValue) this.value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        numberValue.setValue(Mathf.clamp(numberValue.getMin().floatValue(), numberValue.getMax().floatValue(), numberValue.getValue().floatValue()));

        float padding = 5;
        float x = position.x;
        float y = position.y;

        String minvalue = String.valueOf(numberValue.getMin().floatValue());
        if (minvalue.endsWith(".0")) {
            minvalue = minvalue.replace(".0", "");
        }
        String maxvalue = String.valueOf(numberValue.getMax().floatValue());
        if (maxvalue.endsWith(".0")) {
            maxvalue = maxvalue.replace(".0", "");
        }
        String value = String.valueOf(numberValue.getValue().floatValue());
        if (value.endsWith(".0")) {
            value = value.replace(".0", "");
        }

        hover = isHover(mouseX, mouseY, x + padding, y + padding + font12.getHeight(), width, padding + 2 + padding);

        font12.draw(matrixStack, numberValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        font12.drawRight(matrixStack, value, x + width + padding, y + padding, ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        font12.draw(matrixStack, minvalue, x + padding, y + (padding + font12.getHeight()) + (padding + 2) + (padding / 2F), ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 220))));
        font12.drawRight(matrixStack, maxvalue, x + width + padding, y + (padding + font12.getHeight()) + (padding + 2) + (padding / 2F), ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 220))));

        if (grabbed) {
            percentage = mouseX - (x + padding);
            percentage /= width;
            percentage = Math.max(Math.min(percentage, 1), 0);

            numberValue.setValue((numberValue.getMin().floatValue() + (numberValue.getMax().floatValue() - numberValue.getMin().floatValue()) * percentage));
            numberValue.setValue(Mathf.step(numberValue.getValue().floatValue(), numberValue.getDecimalPlaces().floatValue()));
        }

        current = Interpolator.lerp(current, percentage, 0.5F);
        float sliderWidth = current * width;
        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, y + padding + font12.getHeight() + padding, x + padding + width, y + padding + font12.getHeight() + padding + 2, 1, 1, ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, y + padding + font12.getHeight() + padding, x + padding + sliderWidth, y + padding + font12.getHeight() + padding + 2, 1, 1, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + sliderWidth - 2, y + padding + font12.getHeight() + padding - 1, x + padding + sliderWidth - 2 + 4, y + padding + font12.getHeight() + padding - 1 + 4, 2, 1, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), false, true, true, true);

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, x + padding, y + padding + font12.getHeight() + padding, width, 2) || grabbed) {
                if (!hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.RESIZEH);
                    hovered = true;
                }
            } else {
                if (hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                    hovered = false;
                }
            }
        }

        height = padding + font12.getHeight() + padding + 2 + (padding / 2F) + font12.getHeight() + padding;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isLClick(button)) {
            if (hover) {
                grabbed = true;
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        grabbed = false;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }

    @Override
    public void onClose() {
        grabbed = false;
    }
}
