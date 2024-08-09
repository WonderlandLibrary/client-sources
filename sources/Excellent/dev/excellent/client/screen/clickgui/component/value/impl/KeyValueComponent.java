package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.client.module.impl.render.ClickGui;
import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.KeyValue;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;

@Setter
@Getter
public class KeyValueComponent extends ValueComponent {
    public KeyValueComponent(Value<?> value, float width) {
        super(value, width - 10);
    }

    private boolean binding = false;

    @Override
    public void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (position == null) return;
        KeyValue keyValue = (KeyValue) value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        float padding = 5;
        float x = position.x;
        float y = position.y;

        font12.draw(matrixStack, keyValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        String keyName = isBinding() ? "Бинд.." : Keyboard.keyName(keyValue.getValue());
        float keyWidth = font12.getWidth(keyName);
        float height = 8;

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, x + width - keyWidth, y + padding, keyWidth + padding, height)) {
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

        int color = ColorUtil.replAlpha(getTheme().getClientColor().darker().hashCode(), (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255)));
        RectUtil.drawRoundedRectShadowed(matrixStack, x + width - keyWidth, y + padding, x + width - keyWidth + keyWidth + padding, y + padding + height, 1.5F, 0.5F, color, color, color, color, false, false, true, true);
        font12.drawRight(matrixStack, keyName, x + padding / 2F + width, y + padding + (height / 2F) - (font12.getHeight() / 2F), ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        if (keyValue.getParent().getKeyCode() == keyValue.getValue()) {
            keyValue.setValue(Keyboard.KEY_NONE.keyCode);
        }

        this.height = padding + height + padding;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (position == null) return;
        KeyValue keyValue = (KeyValue) value;

        float padding = 5;
        float x = position.x;
        float y = position.y;

        String keyName = Keyboard.keyName(keyValue.getValue());
        float keyWidth = font12.getWidth(keyName);

        boolean valid = button != Keyboard.MOUSE_MIDDLE.keyCode
                && button != Keyboard.MOUSE_RIGHT.keyCode
                && button != Keyboard.MOUSE_LEFT.keyCode;

        float height = 8;
        if (isHover(mouseX, mouseY, x + width - keyWidth, y + padding, keyWidth + padding, height) && !valid) {
            setBinding(!isBinding());
        } else {
            if (isBinding()) {
                if (valid) {
                    keyValue.setValue(button);
                    setBinding(false);
                }
            } else {
                setBinding(false);
            }
        }

    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (position == null) return;
        KeyValue keyValue = (KeyValue) value;
        boolean valid = keyCode != Keyboard.KEY_DELETE.keyCode
                && keyCode != Keyboard.KEY_ESCAPE.keyCode
                && keyCode != ClickGui.singleton.get().getKeyCode()
                && keyCode != keyValue.getParent().getKeyCode();
        if (isBinding() && valid) {
            keyValue.setValue(keyCode);
            setBinding(false);
        } else if (isBinding() && (keyCode == Keyboard.KEY_ESCAPE.keyCode || keyCode == Keyboard.KEY_DELETE.keyCode)) {
            keyValue.setValue(Keyboard.KEY_NONE.keyCode);
            setBinding(false);
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }

    @Override
    public void onClose() {
        setBinding(false);
    }
}
