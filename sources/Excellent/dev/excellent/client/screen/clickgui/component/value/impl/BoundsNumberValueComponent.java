package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.BoundsNumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class BoundsNumberValueComponent extends ValueComponent {
    private boolean grabbed1, grabbed2;
    private float percentage1;
    private float percentage2;
    private float current1, current2;
    private boolean hover;

    public BoundsNumberValueComponent(Value<?> value, float width) {
        super(value, width - 10);
        BoundsNumberValue boundsNumberValue = (BoundsNumberValue) value;
        float percentage1 = (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getValue().floatValue()) / (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getMax().floatValue());
        float percentage2 = (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getSecondValue().floatValue()) / (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getMax().floatValue());
        this.percentage1 = percentage1;
        this.percentage2 = percentage2;
    }

    @Override
    public void init() {
        BoundsNumberValue boundsNumberValue = (BoundsNumberValue) value;
        percentage1 = (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getValue().floatValue()) / (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getMax().floatValue());
        percentage2 = (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getSecondValue().floatValue()) / (-boundsNumberValue.getMin().floatValue() + boundsNumberValue.getMax().floatValue());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        BoundsNumberValue boundsNumberValue = (BoundsNumberValue) value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        boundsNumberValue.setValue(Mathf.clamp(boundsNumberValue.getMin().floatValue(), boundsNumberValue.getMax().floatValue(), boundsNumberValue.getValue().floatValue()));
        boundsNumberValue.setSecondValue(Mathf.clamp(boundsNumberValue.getMin().floatValue(), boundsNumberValue.getMax().floatValue(), boundsNumberValue.getSecondValue().floatValue()));

        float padding = 5;
        float x = position.x;
        float y = position.y;

        String minvalue = String.valueOf(boundsNumberValue.getMin().floatValue());
        if (minvalue.endsWith(".0")) {
            minvalue = minvalue.replace(".0", "");
        }
        String maxvalue = String.valueOf(boundsNumberValue.getMax().floatValue());
        if (maxvalue.endsWith(".0")) {
            maxvalue = maxvalue.replace(".0", "");
        }
        String firstValue = String.valueOf(boundsNumberValue.getValue().floatValue());
        String secondValue = String.valueOf(boundsNumberValue.getSecondValue().floatValue());
        if (firstValue.endsWith(".0")) {
            firstValue = firstValue.replace(".0", "");
        }
        if (secondValue.endsWith(".0")) {
            secondValue = secondValue.replace(".0", "");
        }

        String value = TextFormatting.WHITE + firstValue + TextFormatting.GRAY + "/" + TextFormatting.WHITE + secondValue;

        hover = isHover(mouseX, mouseY, x + padding, y + padding + font12.getHeight(), width, padding + 2 + padding);

        font12.draw(matrixStack, boundsNumberValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        font12.drawRight(matrixStack, value, x + width + padding, y + padding, ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        font12.draw(matrixStack, minvalue, x + padding, y + (padding + font12.getHeight()) + (padding + 2) + (padding / 2F), ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 220))));
        font12.drawRight(matrixStack, maxvalue, x + width + padding, y + (padding + font12.getHeight()) + (padding + 2) + (padding / 2F), ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 220))));

        if (grabbed1) {
            percentage1 = mouseX - (x + padding);
            percentage1 /= width;
            percentage1 = Math.max(Math.min(percentage1, 1), 0);

            boundsNumberValue.setValue((boundsNumberValue.getMin().floatValue() + (boundsNumberValue.getMax().floatValue() - boundsNumberValue.getMin().floatValue()) * percentage1));
            boundsNumberValue.setValue(Mathf.step(boundsNumberValue.getValue().floatValue(), boundsNumberValue.getDecimalPlaces().floatValue()));

            if (percentage1 > percentage2) {
                percentage2 = percentage1;
                boundsNumberValue.setSecondValue((boundsNumberValue.getMin().floatValue() + (boundsNumberValue.getMax().floatValue() - boundsNumberValue.getMin().floatValue()) * percentage2));
                boundsNumberValue.setSecondValue(Mathf.step(boundsNumberValue.getSecondValue().floatValue(), boundsNumberValue.getDecimalPlaces().floatValue()));
            }
        } else if (grabbed2) {
            percentage2 = mouseX - (x + padding);
            percentage2 /= width;
            percentage2 = Math.max(Math.min(percentage2, 1), 0);

            boundsNumberValue.setSecondValue((boundsNumberValue.getMin().floatValue() + (boundsNumberValue.getMax().floatValue() - boundsNumberValue.getMin().floatValue()) * percentage2));
            boundsNumberValue.setSecondValue(Mathf.step(boundsNumberValue.getSecondValue().floatValue(), boundsNumberValue.getDecimalPlaces().floatValue()));

            if (percentage2 < percentage1) {
                percentage1 = percentage2;
                boundsNumberValue.setValue((boundsNumberValue.getMin().floatValue() + (boundsNumberValue.getMax().floatValue() - boundsNumberValue.getMin().floatValue()) * percentage1));
                boundsNumberValue.setValue(Mathf.step(boundsNumberValue.getValue().floatValue(), boundsNumberValue.getDecimalPlaces().floatValue()));
            }
        }

        current1 = Interpolator.lerp(current1, percentage1, 0.5F);
        current2 = Interpolator.lerp(current2, percentage2, 0.5F);

        float startPositionX = current1 * width;
        float endPositionX = current2 * width;
        float boundsWidth = endPositionX - startPositionX;

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, x + padding, y + padding + font12.getHeight() + padding, width, 2) || (grabbed1 || grabbed2)) {
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

        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, y + padding + font12.getHeight() + padding, x + padding + width, y + padding + font12.getHeight() + padding + 2, 1, 1, ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.getColor(40, 40, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), true, true, true, true);

        if (percentage1 != percentage2) {
            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + startPositionX, y + padding + font12.getHeight() + padding, x + padding + startPositionX + boundsWidth, y + padding + font12.getHeight() + padding + 2, 1, 1, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 220))), true, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + startPositionX - 2, y + padding + font12.getHeight() + padding - 1, x + padding + startPositionX - 2 + 4, y + padding + font12.getHeight() + padding - 1 + 4, 2, 1, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), false, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + startPositionX + boundsWidth - 2, y + padding + font12.getHeight() + padding - 1, x + padding + startPositionX + boundsWidth - 2 + 4, y + padding + font12.getHeight() + padding - 1 + 4, 2, 1, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), false, true, true, true);
        } else if (!firstValue.equals(minvalue) && !secondValue.equals(minvalue) && !firstValue.equals(maxvalue) && !secondValue.equals(maxvalue)) {
            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + startPositionX - 2, y + padding + font12.getHeight() + padding - 1, x + padding + startPositionX - 2 + 4, y + padding + font12.getHeight() + padding - 1 + 4, 2, 1, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255))), false, true, true, true);
        }

        height = padding + font12.getHeight() + padding + 2 + (padding / 2F) + font12.getHeight() + padding;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (position == null) return;

        if (isLClick(button)) {
            if (hover) {
                float distance1 = (float) Math.abs(mouseX - (position.x + current1 * width));
                float distance2 = (float) Math.abs(mouseX - (position.x + current2 * width));

                if (distance1 < distance2) {
                    grabbed1 = true;
                } else {
                    grabbed2 = true;
                }
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        grabbed1 = grabbed2 = false;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }

    @Override
    public void onClose() {
        grabbed1 = grabbed2 = false;
    }
}
