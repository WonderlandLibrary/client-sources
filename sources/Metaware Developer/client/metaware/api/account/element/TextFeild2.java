package client.metaware.api.account.element;

import client.metaware.Metaware;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.utils.MinecraftUtil;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.render.StringUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TextFeild2 implements MinecraftUtil {

    private float x, y, width, height;
    private String text, ghostText;

    private boolean focused, selected;

    private int cursorPosition = 1, cutPosition, cursorCounter;

    private CustomFontRenderer font;

    private int backgroundColor;

    public TextFeild2(int fontSize, float x, float y, float width, float height, int backgroundColor) {
        this("", fontSize, x, y, width, height, backgroundColor);
    }

    public TextFeild2(String ghostText, int fontSize, float x, float y, float width, float height, int backgroundColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        font = Metaware.INSTANCE.getFontManager().currentFont().size(fontSize);
    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawField(int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + height, backgroundColor);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.prepareScissorBox(x, y, x + width - 2, y + height);
        String character = "";
        if(focused && cursorCounter / 10 % 2 == 0) {
            if(text == null || cursorPosition == text.length()) character = "_";
            else character = "|";
        }
        if(text != null) {
            if(selected) {
                Gui.drawRect(x, y, x + font.getWidth(text), y + font.getHeight(text), new Color(255, 255, 255, 200).getRGB());
            }
            font.drawStringWithShadow(new StringBuilder(text).insert(cursorPosition, character).toString(), x + 1, y + height / 2 - font.getHeight(text) / 2, selected ? new Color(0, 0, 255).getRGB() : -1);
        } else {
            font.drawStringWithShadow(character, x + 1, y + height / 2 - font.getHeight(character) / 2, -1);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        focused = false;
        selected = false;
        if(RenderUtil.isHovered(x, y, width, height, mouseX, mouseY)) {
            focused = true;
        }
    }

    public void keyTyped(char character, int key) {
        if(focused) {
            if(!Keyboard.isRepeatEvent()) Keyboard.enableRepeatEvents(true);
            if(text != null) {
                if (key == Keyboard.KEY_BACK) {
                    if (text.length() > 0 && selected) {
                        text = null;
                    }
                }
            }
            selected = false;
            if(text != null) {
                if (key == Keyboard.KEY_BACK) {
                    if(text.length() > 0 && selected) {

                    }
                    if (text.length() > 0 && cursorPosition - 1 >= 0) {
                        text = new StringBuilder(text).deleteCharAt(cursorPosition - 1).toString();
                        cursorPosition--;
                        if(cutPosition - 1 >= 0) cutPosition--;
                        return;
                    }
                }
                if (key == Keyboard.KEY_DELETE) {
                    if (text.length() > 0 && cursorPosition >= 0 && getTextAfterCursor().length() > 0) {
                        text = new StringBuilder(text).deleteCharAt(cursorPosition).toString();
                        return;
                    }
                }
                if(key == Keyboard.KEY_RIGHT) {
                    if (cursorPosition < text.length()) {
                        cursorPosition++;
                        if(font.getWidth(text) > width) cutPosition++;
                    }
                }
                if(key == Keyboard.KEY_LEFT) {
                    if (text.length() > 0 && cursorPosition > 0) {
                        cursorPosition--;
                        if(cutPosition - 1 >= 0) cutPosition--;
                    }
                }
                if(GuiScreen.isKeyComboCtrlA(key) && focused) {
                    selected = true;
                    cutPosition = 0;
                    cursorPosition = 0;
                    return;
                }
                if(GuiScreen.isKeyComboCtrlV(key) && focused) {
                    text = StringUtils.getTrimmedClipboardContents();
                    return;
                }
            }
            if(ChatAllowedCharacters.isAllowedCharacter(character))
                addCharacter(character);
        }
    }

    private void addCharacter(char character) {
        if(text == null)
            text = String.valueOf(character);
        else {
            text = new StringBuilder(text).insert(cursorPosition, character).toString();
            cursorPosition++;
            if(font.getWidth(text) > width) cutPosition++;
        }
    }

    public void text(String text) {
        this.text = text;
    }
    public float x() {
        return x;
    }
    public float y() {
        return y;
    }
    public float width() {
        return width;
    }
    public float height() {
        return height;
    }
    public String text() {
        return text;
    }
    public boolean focused() {
        return focused;
    }
    public boolean selected() {
        return selected;
    }
    private String getTextBeforeCursor() {
        return cursorPosition == 0 ? "" : text.substring(0, cursorPosition);
    }
    private String getTextAfterCursor() {
        return text.substring(cursorPosition);
    }
    private String getCutText() {
        return cutPosition == 0 ? text : text.substring(cutPosition);
    }
    public void updateScreen() {
        cursorCounter++;
    }

}
