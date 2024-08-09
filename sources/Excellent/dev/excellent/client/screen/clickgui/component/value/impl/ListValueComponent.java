package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.ListValue;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class ListValueComponent extends ValueComponent {
    public ListValueComponent(Value<?> value, float width) {
        super(value, width - 10);
    }

    @Override
    public void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        ListValue<?> listValue = (ListValue<?>) value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        float padding = 5;
        float x = position.x;
        float y = position.y;

        font12.draw(matrixStack, listValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        float size = 8;

        float offset = 0;
        for (Object list : listValue.getModes()) {
            boolean selected = list.toString().equals(listValue.getValue().toString());
            float yPos = y + padding + font12.getHeight() + padding + offset;

            int outColor = ColorUtil.replAlpha(getTheme().getClientColor().darker().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));

            int selectedIn = ColorUtil.replAlpha(-1, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));
            int unSelectedIn = ColorUtil.replAlpha(getTheme().getClientColor().darker().darker().darker().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));

            RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, yPos, x + padding + size, yPos + size, (size / 2F), 1, outColor, outColor, outColor, outColor, true, true, true, true);
            if (selected) {
                RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + (size / 4F) - 0.5F, yPos + (size / 4F) - 0.5F, x + padding + (size / 4F) - 0.5F + (size / 2F) + 1, yPos + (size / 4F) - 0.5F + (size / 2F) + 1, (size / 4F), 1, selectedIn, selectedIn, selectedIn, selectedIn, true, true, true, true);
            } else {
                RectUtil.drawRoundedRectShadowed(matrixStack, x + padding + (size / 4F) - 0.5F, yPos + (size / 4F) - 0.5F, x + padding + (size / 4F) - 0.5F + (size / 2F) + 1, yPos + (size / 4F) - 0.5F + (size / 2F) + 1, (size / 4F), 1, unSelectedIn, unSelectedIn, unSelectedIn, unSelectedIn, false, true, true, true);
            }

            font12.draw(matrixStack, list.toString(), x + padding + size + 2.5F, yPos + 0.5F, selected ? ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))) : ColorUtil.getColor(200, 200, 200, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
            offset += size + 4F;
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
        ListValue<?> listValue = (ListValue<?>) value;

        float padding = 5;
        float x = position.x;
        float y = position.y;

        float offset = 0;
        for (Object list : listValue.getModes()) {
            float yPos = y + padding + font12.getHeight() + padding + offset;
            float size = 8;
            if (isLClick(button) && isHover(mouseX, mouseY, x + padding, yPos - (size / 4F), width, size + (size / 2F))) {
                listValue.setValueAsObject(list);
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
