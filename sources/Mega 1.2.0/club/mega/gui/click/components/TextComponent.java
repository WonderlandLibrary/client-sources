package club.mega.gui.click.components;

import club.mega.Mega;
import club.mega.gui.click.Component;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.setting.impl.TextSetting;
import club.mega.util.ChatUtil;
import club.mega.util.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TextComponent extends Component implements MinecraftInterface {

    private final TextSetting setting;
    private boolean typing, selectedAll;

    public TextComponent(TextSetting setting, double width, double height) {
        super(setting, width, height);
        this.setting = setting;
    }

    @Override
    public void drawComponent(final int mouseX, final int mouseY) {
        super.drawComponent(mouseX, mouseY);
        RenderUtil.drawRect(x + 3, y + 3, width - 5, height - 6, new Color(1,1,1,180));
        Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString(setting.getRawText().isEmpty() && !typing ? setting.getName() : setting.getText() + (typing ? "_" : ""), x + 5, y + 5, setting.getRawText().isEmpty() && !typing ? new Color(255,255,255, 180) : Color.WHITE);
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY) && mouseButton == 0)
            typing = true;
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        selectedAll = false;
        if (!isInside(mouseX, mouseY) && mouseButton == 0)
            typing = false;
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == Keyboard.KEY_RETURN) typing = false;
        if (!typing) return;
        if (keyCode == Keyboard.KEY_BACK || selectedAll) {
            if (selectedAll) {
                setting.setText(getKey(keyCode, typedChar));
                selectedAll = false;
            } else
                setting.remove();
        } else
            setting.setText(setting.getRawText() + getKey(keyCode, typedChar));
    }

    private String getKey(final int key, final char typedChar) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            switch (key)
            {
                case Keyboard.KEY_0:
                    return "=";
                case Keyboard.KEY_1:
                    return "!";
                case Keyboard.KEY_2:
                    return "''";
                case Keyboard.KEY_3:
                    return "§";
                case Keyboard.KEY_4:
                    return "$";
                case Keyboard.KEY_5:
                    return "%";
                case Keyboard.KEY_6:
                    return "&";
                case Keyboard.KEY_7:
                    return "/";
                case Keyboard.KEY_8:
                    return "(";
                case Keyboard.KEY_9:
                    return ")";
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
            switch (key)
            {
                case Keyboard.KEY_E:
                    return "€";
                case Keyboard.KEY_Q:
                    return "@";
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && key == Keyboard.KEY_A) {
            selectedAll = true;
            return "";
        }
        return isLetterOrDigit(typedChar) || key == Keyboard.KEY_SPACE ? String.valueOf(typedChar) : "";
    }

    private boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

}
