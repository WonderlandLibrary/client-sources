package lol.point.returnclient.ui.components;

import lol.point.Return;
import lol.point.returnclient.module.impl.client.Theme;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class UITextInput {

    private FastFontRenderer text = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");
    private FastFontRenderer icon = Return.INSTANCE.fontManager.getFont("ReturnNew-Icons 20");
    public String placeHolderText, inputText = "", inputIcon;
    private float width, height;
    private boolean clicked = false;
    private boolean ctrlA = false;

    public UITextInput(String placeHolderText, String buttonIcon, float width, float height) {
        this.placeHolderText = placeHolderText;
        this.inputIcon = buttonIcon;
        this.width = width;
        this.height = height;
    }

    public void drawButton(int mouseX, int mouseY, float x, float y, float iconX, float iconY) {
        Color gradient1, gradient2;
        gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
        gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

        boolean hover = RenderUtil.hovered(mouseX, mouseY, x, y, width, height);
        if (hover) {
            RenderUtil.gradientH(x, y, width, height, gradient1, gradient2);
        }

        ShaderUtil.renderGlow(() -> {
            if (hover) {
                RenderUtil.gradientH(x, y, width, height, gradient1, gradient2);
            }
        }, 2, 2, 0.86f);

        RenderUtil.rectangle(hover ? x + 1 : x, hover ? y + 1 : y, hover ? width - 2 : width, hover ? height - 2 : height, hover ? new Color(33, 33, 33).brighter() : new Color(33, 33, 33));
        boolean blankOrEmpty = inputText.isEmpty() || inputText.isBlank();
        if (ctrlA && !blankOrEmpty) {
            RenderUtil.rectangle(x + (width / 2) - (text.getWidth(inputText + "_") / 2), y + 6, text.getWidth(inputText), 8, new Color(0, 0, 255, 150));
        }

        if (clicked) {
            text.drawStringWithShadow(inputText + "_", x + (width / 2) - (text.getWidth(inputText + "_") / 2), y + 6, -1);
        } else {
            text.drawStringWithShadow(blankOrEmpty ? placeHolderText : inputText, x + (width / 2) - (text.getWidth(blankOrEmpty ? placeHolderText : inputText) / 2), y + 6, blankOrEmpty ? new Color(92, 92, 92).getRGB() : -1);
        }
        if (!inputIcon.isEmpty() || !inputIcon.isBlank()) {
            icon.drawStringWithShadow(inputIcon, iconX, iconY, -1);
        }
    }

    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY, float x, float y) {
        boolean hover = RenderUtil.hovered(mouseX, mouseY, x, y, width, height);
        if (mouseButton == 0) {
            ctrlA = false;
        }
        clicked = mouseButton == 0 && hover;
        return mouseButton == 0 && hover;
    }

    public void keyTyped(int keyCode, char keyChar) {
        if (clicked) {
            switch (keyCode) {
                case 1, 28 -> {
                    clicked = false;
                    return;
                }
                case 14 -> {
                    if (ctrlA) {
                        inputText = "";
                    }

                    if (!inputText.isEmpty()) {
                        inputText = inputText.substring(0, inputText.length() - 1);
                    }
                    return;
                }
                default -> {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && ctrlA) {
                        ctrlA = true;
                    } else {
                        if (keyCode == 14) {
                            ctrlA = true;
                        } else {
                            ctrlA = false;
                        }
                    }
                }
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                if (keyCode == Keyboard.KEY_A) {
                    ctrlA = true;
                    return;
                }
                if (keyCode == Keyboard.KEY_C && ctrlA) {
                    StringSelection stringSelection = new StringSelection(inputText);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                    return;
                }
                if (keyCode == Keyboard.KEY_V) {
                    Transferable clipboardContents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                    if (clipboardContents != null && clipboardContents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        try {
                            String clipboardText = (String) clipboardContents.getTransferData(DataFlavor.stringFlavor);
                            if (clipboardText != null) {
                                inputText += clipboardText;
                            }
                        } catch (UnsupportedFlavorException | IOException e) {
                            Return.LOGGER.error(e);
                        }
                    }
                    return;
                }
            }

            boolean shiftPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            if (shiftPressed) {
                keyChar = getShiftModifiedChar(keyChar);
            }

            if (isAllowedCharacter(keyChar)) {
                this.inputText += keyChar;
            }
        }
    }

    private char getShiftModifiedChar(char keyChar) {
        switch (keyChar) {
            case '1':
                return '!';
            case '2':
                return '@';
            case '3':
                return '#';
            case '4':
                return '$';
            case '5':
                return '%';
            case '6':
                return '^';
            case '7':
                return '&';
            case '8':
                return '*';
            case '9':
                return '(';
            case '0':
                return ')';
            case '-':
                return '_';
            case '=':
                return '+';
            case '[':
                return '{';
            case ']':
                return '}';
            case ';':
                return ':';
            case '\'':
                return '"';
            case ',':
                return '<';
            case '.':
                return '>';
            case '/':
                return '?';
            case '\\':
                return '|';
            default:
                return Character.toUpperCase(keyChar);
        }
    }

    private boolean isAllowedCharacter(char keyChar) {
        return Character.isLetterOrDigit(keyChar) || Character.isWhitespace(keyChar) || keyChar == '.' || keyChar == ',' ||
                keyChar == '-' || keyChar == '_' || keyChar == '!' || keyChar == '@' || keyChar == '#' || keyChar == '$' ||
                keyChar == '%' || keyChar == '^' || keyChar == '&' || keyChar == '*' || keyChar == '(' || keyChar == ')' ||
                keyChar == '+' || keyChar == '=' || keyChar == '{' || keyChar == '}' || keyChar == ':' || keyChar == '"' ||
                keyChar == '<' || keyChar == '>' || keyChar == '?' || keyChar == '|' || keyChar == '`' || keyChar == '~';
    }

}
