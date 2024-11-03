package net.silentclient.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.theme.input.DefaultInputTheme;
import net.silentclient.client.gui.theme.input.IInputTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.NotificationUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.util.regex.Pattern;

public class Input {
    private boolean isFocused;
    private String value;
    private Pattern pattern;
    private String name;
    private int maxLength;
    private boolean keyValue;
    private int key;
    private boolean hovered = false;

    public Input(String name, String value, int key, Pattern pattern, int maxLength, boolean keyValue) {
        this.name = name;
        this.pattern = pattern;
        this.maxLength = maxLength;
        this.keyValue = keyValue;
        this.value = value;
        this.key = key;
    }

    public Input(String name, Pattern pattern, int maxLength) {
        this(name, "", -1, pattern, maxLength, false);
    }

    public Input(String name, String value) {
        this(name, value, -1, Pattern
                .compile("^[~`!@#$%^&*()_+=[\\\\]\\\\\\\\\\\\{\\\\}|;':\\\",.\\\\/<>?a-zA-Z0-9-\\s]+$"), 20, false);
    }

    public Input(String name, int key) {
        this(name , "", key, Pattern
                .compile("^[~`!@#$%^&*()_+=[\\\\]\\\\\\\\\\\\{\\\\}|;':\\\",.\\\\/<>?a-zA-Z0-9-\\s]+$"), 1, true);
    }

    public Input(String name, Pattern pattern) {
        this(name, pattern, 20);
    }

    public Input(String name) {
        this(name,  Pattern
                .compile("^[\\w,\\s-]{0,20}+$"), 20);
    }
    public void render(int mouseX, int mouseY, float x, float y, int width) {
    	this.render(mouseX, mouseY, x, y, width, false);
    }

    public void render(int mouseX, int mouseY, float x, float y, int width, boolean small) {
        this.render(mouseX, mouseY, x, y, width, small, new DefaultInputTheme());
    }

    public void render(int mouseX, int mouseY, float x, float y, int width, boolean small, IInputTheme theme) {
        this.render(mouseX, mouseY, x, y, width, small, theme, false);
    }

    public void render(int mouseX, int mouseY, float x, float y, int width, boolean small, IInputTheme theme, boolean center) {
        int borderColor = theme.getBorderColor().getRGB();
        if(MouseUtils.isInside(mouseX, mouseY, x, y, width, small ? 15 : 20)) {
            borderColor = theme.getHoveredBorderColor().getRGB();
            this.hovered = true;
        } else {
            this.hovered = false;
        }

        if(isFocused) {
            borderColor = theme.getFocusedBorderColor().getRGB();
        }

        RenderUtil.drawRoundedOutline(x, y, width, (small ? 15 : 20), 3, isFocused ? 2 : 1, borderColor);
        String renderText = name;
        ColorUtils.setColor(theme.getBorderColor().getRGB());
        if(value.length() != 0) {
            renderText = value;
        }
        if(keyValue) {
            if(key != -1) {
                renderText = Keyboard.getKeyName(key);
            } else {
                renderText = "NONE";
            }
            if(isFocused) {
                renderText = "...";
            }
        }
        ColorUtils.setColor(theme.getFocusedBorderColor().getRGB());
        float textX = x + 2;
        if(center) {
            textX = x + (width / 2) - (Client.getInstance().getSilentFontRenderer().getStringWidth(renderText, small ? 12 : 14, SilentFontRenderer.FontType.TITLE) / 2);
        }
        Client.getInstance().getSilentFontRenderer().drawString(EnumChatFormatting.getTextWithoutFormattingCodes(renderText), textX, y + (small ? 1 : 3), small ? 12 : 14, SilentFontRenderer.FontType.TITLE);
    }

    public boolean isHovered() {
        return hovered;
    }

    public boolean isFocused() {
		return isFocused;
	}
    
    public void onClick(int mouseX, int mouseY, int x, int y, int width) {
    	this.onClick(mouseX, mouseY, x, y, width, false);
    }

    public void onClick(int mouseX, int mouseY, int x, int y, int width, boolean small) {
        if(MouseUtils.isInside(mouseX, mouseY, x, y, width, small ? 15 : 20)) {
            this.isFocused = true;
        } else {
            this.isFocused = false;
        }
    }

    public void onKeyTyped(char typedChar, int keyCode) {
        if(isFocused) {
            if(keyValue) {
                if(keyCode == Keyboard.KEY_ESCAPE) {
                    key = -1;
                    isFocused = false;
                    return;
                }
                key = keyCode;
                isFocused = false;
                return;
            }
            if(keyCode == Keyboard.KEY_ESCAPE) {
                isFocused = false;
                return;
            }
            if(keyCode == Keyboard.KEY_BACK) {
                if (value != null || value.length() != 0 || value != "") {
                    this.value = StringUtils.chop(this.value);
                }
                return;
            }
            String test = "";
            if (GuiScreen.isKeyComboCtrlV(keyCode))
            {
                test += GuiScreen.getClipboardString();
                if(this.pattern.matcher(test).matches()) {
                    value += ChatAllowedCharacters.filterAllowedCharacters(test);
                }
                return;
            }

            if(value.length() > maxLength - 1) {
                NotificationUtils.showNotification("Error", "Maximum " + name + " size " + maxLength + " characters");
                return;
            }
            test += typedChar;

            if(this.pattern.matcher(test).matches()) {
                value += ChatAllowedCharacters.filterAllowedCharacters(test);
            }
        }
    }

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

    public boolean isKeyValue() {
        return keyValue;
    }

    public void setValue(String value) {
        this.value = value;
    }
}