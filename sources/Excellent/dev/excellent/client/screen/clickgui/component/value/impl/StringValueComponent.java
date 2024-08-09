package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.util.render.text.TextAlign;
import dev.excellent.impl.util.render.text.TextBox;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.StringValue;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class StringValueComponent extends ValueComponent {
    private final TextBox textBox = new TextBox(new Vector2f(), font12, ColorUtil.getColor(255, 255, 255), TextAlign.LEFT, "Введите что-нибудь..", 0);

    public StringValueComponent(Value<?> value, float width) {
        super(value, width - 10);
        StringValue stringValue = (StringValue) value;
        textBox.setText(stringValue.getValue());
        textBox.setCursor(stringValue.getValue().length());
    }

    @Override
    public void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        StringValue stringValue = (StringValue) this.value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        float padding = 5;
        float x = position.x;
        float y = position.y;

        font12.draw(matrixStack, stringValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, y + padding + font12.getHeight() + padding, x + padding + width, y + padding + font12.getHeight() + padding + padding + font12.getHeight() + padding, 2, 0.5F, ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 64))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 64))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 64))), ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 64))), false, false, true, true);

        position = new Vector2f(x + (padding * 2F), y + padding + font12.getHeight() + padding + padding);
        textBox.setColor(ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
        textBox.setPosition(position);
        textBox.setWidth(width - (padding * 2F));
        textBox.draw(matrixStack);
        stringValue.setValue(textBox.getText());

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, x + padding, y + padding + font12.getHeight() + padding, width, padding + font12.getHeight() + padding)) {
                if (!hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.IBEAM);
                    hovered = true;
                }
            } else {
                if (hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                    hovered = false;
                }
            }
        }

        height = (padding + font12.getHeight() + padding) * 2F + padding;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (position == null) return;
        if (isLClick(button))
            textBox.mouse(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (position == null) return;
        textBox.keyPressed(keyCode);
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        if (position == null) return;

        textBox.charTyped(codePoint);
    }

    @Override
    public void onClose() {
        textBox.selected = false;
    }
}
