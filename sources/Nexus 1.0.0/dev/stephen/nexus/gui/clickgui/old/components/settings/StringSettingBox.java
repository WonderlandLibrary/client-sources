package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.StringSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class StringSettingBox extends RenderableSetting {
    public final StringSetting setting;
    private String currentText;
    private boolean isFocused;
    private final int boxWidth = 240;
    private final int boxHeight = 20;
    private final Set<Integer> keysPressed = new HashSet<>();
    private boolean shiftPressed = false;

    public StringSettingBox(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (StringSetting) setting;
        this.currentText = ((StringSetting) setting).getValue();
        this.isFocused = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int x = parentX();
        int y = parentY() + parentOffset() + offset;

        context.fill(x, y, x + boxWidth, y + boxHeight, new Color(0, 0, 0, 120).getRGB());

        MinecraftClient client = MinecraftClient.getInstance();
        context.drawText(client.textRenderer, currentText, x + 5, y + 5, 0xFFFFFFFF, true);

        if (isFocused) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime / 500) % 2 == 0) {
                context.fill(x + client.textRenderer.getWidth(currentText) + 6, y + 5, x + client.textRenderer.getWidth(currentText) + 6 + 1, y + 5 + boxHeight - 10, 0xFFFFFFFF);
            }
        }

        isFocused = mouseX >= x && mouseX <= x + boxWidth && mouseY >= y && mouseY <= y + boxHeight;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);

        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            shiftPressed = true;
            return;
        }

        if (isFocused) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!currentText.isEmpty()) {
                    currentText = currentText.substring(0, currentText.length() - 1);
                }
            } else if (keyCode == GLFW.GLFW_KEY_ENTER) {
                updateSettingValue();
                isFocused = false;
            } else if (keyCode >= GLFW.GLFW_KEY_SPACE && keyCode <= GLFW.GLFW_KEY_LAST) {
                char character = getCharFromKeyCode(keyCode);
                if (shiftPressed) {
                    character = Character.toUpperCase(character);
                }
                currentText += character;
            }
        }
    }

    @Override
    public void keyReleased(int keyCode, int scanCode, int modifiers) {
        super.keyReleased(keyCode, scanCode, modifiers);
        keysPressed.remove(keyCode);

        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            shiftPressed = false;
        }
    }

    private char getCharFromKeyCode(int keyCode) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_A:
                return 'a';
            case GLFW.GLFW_KEY_B:
                return 'b';
            case GLFW.GLFW_KEY_C:
                return 'c';
            case GLFW.GLFW_KEY_D:
                return 'd';
            case GLFW.GLFW_KEY_E:
                return 'e';
            case GLFW.GLFW_KEY_F:
                return 'f';
            case GLFW.GLFW_KEY_G:
                return 'g';
            case GLFW.GLFW_KEY_H:
                return 'h';
            case GLFW.GLFW_KEY_I:
                return 'i';
            case GLFW.GLFW_KEY_J:
                return 'j';
            case GLFW.GLFW_KEY_K:
                return 'k';
            case GLFW.GLFW_KEY_L:
                return 'l';
            case GLFW.GLFW_KEY_M:
                return 'm';
            case GLFW.GLFW_KEY_N:
                return 'n';
            case GLFW.GLFW_KEY_O:
                return 'o';
            case GLFW.GLFW_KEY_P:
                return 'p';
            case GLFW.GLFW_KEY_Q:
                return 'q';
            case GLFW.GLFW_KEY_R:
                return 'r';
            case GLFW.GLFW_KEY_S:
                return 's';
            case GLFW.GLFW_KEY_T:
                return 't';
            case GLFW.GLFW_KEY_U:
                return 'u';
            case GLFW.GLFW_KEY_V:
                return 'v';
            case GLFW.GLFW_KEY_W:
                return 'w';
            case GLFW.GLFW_KEY_X:
                return 'x';
            case GLFW.GLFW_KEY_Y:
                return 'y';
            case GLFW.GLFW_KEY_Z:
                return 'z';
            case GLFW.GLFW_KEY_0:
                return '0';
            case GLFW.GLFW_KEY_1:
                return '1';
            case GLFW.GLFW_KEY_2:
                return '2';
            case GLFW.GLFW_KEY_3:
                return '3';
            case GLFW.GLFW_KEY_4:
                return '4';
            case GLFW.GLFW_KEY_5:
                return '5';
            case GLFW.GLFW_KEY_6:
                return '6';
            case GLFW.GLFW_KEY_7:
                return '7';
            case GLFW.GLFW_KEY_8:
                return '8';
            case GLFW.GLFW_KEY_9:
                return '9';
            case GLFW.GLFW_KEY_SPACE:
                return ' ';
            default:
                return (char) keyCode;
        }
    }

    private void updateSettingValue() {
        setting.setValue(currentText);
    }
}