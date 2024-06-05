package me.vaziak.sensation.client.api.gui.menu.components;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.FontRenderer;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.gui.Gui;

public class TextField extends Gui {
    private FontRenderer fontRenderer;

    private int posX;
    private int posY;
    private int width;
    private int height;

    private String defaultContent;
    private String typedContent;

    private int defaultTextColor;
    private int typedTextColor;

    private int backgroundColor;
    private int borderColor;
    private int focusedBackgroundColor;
    private int focusedBorderColor;
    private double borderWidth;

    private int charLimit;
    private FieldType type;

    private boolean focused;

    private char character;
    private int key;
    private int ticksKeyDown;
    private TimerUtil keyStopwatch;

    private String replaceAll;

    private int startIndex;

    private boolean hidden;

    public TextField(FontRenderer fontRenderer, int posX, int posY, int width, int height) {
        this.fontRenderer = fontRenderer;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        this.defaultContent = "";
        this.typedContent = "";

        this.defaultTextColor = ColorCreator.create(180, 180, 180);
        this.typedTextColor = ColorCreator.create(230, 230, 230);

        this.backgroundColor = ColorCreator.create(15, 15, 15, 255);
        this.borderColor = ColorCreator.create(10, 10, 10, 255);
        this.focusedBackgroundColor = ColorCreator.create(10, 10, 10, 255);
        this.focusedBorderColor = new Color(255, 75,60).getRGB();
        this.borderWidth = 1;

        this.charLimit = -1;
        this.type = FieldType.ANY;

        this.focused = false;

        key = 0;
        ticksKeyDown = 0;
        keyStopwatch = new TimerUtil();

        replaceAll = "";

        startIndex = 0;
        hidden = false;
    }

    public void updateTextField() {
        if (key != 0) {
            if (Keyboard.isKeyDown(key))
                ticksKeyDown++;
            else {
                key = 0;
                ticksKeyDown = 0;
                return;
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!focused)
            return;

        if (keyCode == Keyboard.KEY_ESCAPE) {
            focused = false;
            return;
        }

        if (keyCode != key) {
            character = typedChar;
            key = keyCode;
            ticksKeyDown = 0;
            keyStopwatch.reset();
        }

        if (keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE) {
            backspace();
            return;
        }

        if (keyCode == Keyboard.KEY_V && (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))) {
            String clipboardContent = readClipboard();

            typedContent += clipboardContent;
            return;
        }

        String charAsString = Character.toString(typedChar);

        if (typedChar != ' ') {
            switch (type) {
                case LETTERS: {
                    if (!Character.isLetter(typedChar))
                        return;
                    break;
                }
                case NUMBERS: {
                    if (!Character.isDigit(typedChar))
                        return;
                    break;
                }
                case NUMBER_LETTERS: {
                    if (!Character.isLetter(typedChar)
                            && !Character.isDigit(typedChar))
                        return;
                    break;
                }
                case NUMBERS_LETTERS_SPECIAL: {
                    boolean specialChar = false;
                    String specialChars = "-/*!@#$%^&*()\"{}_[]|\\?/<>,.";
                    for (int i = 0; i < specialChars.length(); i++) {
                        char special = specialChars.charAt(i);
                        if (typedChar == special)
                            specialChar = true;
                    }

                    if (!Character.isLetter(typedChar)
                            && !Character.isDigit(typedChar)
                            && !specialChar)
                        return;
                    break;
                }
                case ANY:
                    break;
                default:
                    break;
            }
        }

        if (charLimit != -1 && typedContent.length() >= charLimit)
            return;

        typedContent += charAsString;

        if (fontRenderer.getStringWidthCust(typedContent) >= width - 4)
            startIndex++;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseX >= posX && mouseY >= posY && mouseX <= posX + width && mouseY <= posY + height)
            focused = true;
        else
            focused = false;
    }

    public void drawTextField() {
        if (hidden)
            return;

        Draw.drawRectangle(posX, posY, posX + width, posY + height, backgroundColor);
//        Draw.drawRectangle(posX, posY + height, posX + width, posY + height + 1, focused ? new Color(255, 71, 71).getRGB() : backgroundColor);
        Draw.drawRectangle(posX - 1.1, posY + height, posX + width + 1.1, posY + height + .3, focused ? new Color(255, 71, 71).getRGB() : backgroundColor);

        String content = getReplacedContent().substring(startIndex);

        fontRenderer.drawString(typedContent.isEmpty() ? defaultContent : (replaceAll != "" ? content.replaceAll(".", replaceAll) : content),
                posX + 1, posY + height / 2 - fontRenderer.getHeight() / 2,
                typedContent.isEmpty() ? defaultTextColor : typedTextColor);

        if (focused) {
            Draw.drawRectangle(
                    typedContent.isEmpty() ? posX + 2 : posX + 2 + fontRenderer.getStringWidth(content),
                    posY + height / 2 + fontRenderer.getHeight() / 2,
                    typedContent.isEmpty() ? posX + 5 : posX + 5 + fontRenderer.getStringWidth(content),
                    posY + height / 2 + fontRenderer.getHeight() / 2 + 1,
                    0xffffffff);
        }

        //in draw so it can go faster than 20/s lol
        if (ticksKeyDown == 10) {
            keyStopwatch.reset();
        } else if (ticksKeyDown > 5 && ticksKeyDown < 25) {
            if (keyStopwatch.hasPassed(100)) {
                try {
                    keyTyped(character, key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                keyStopwatch.reset();
            }
        } else if (ticksKeyDown > 25) {
            if (keyStopwatch.hasPassed(25)) {
                try {
                    keyTyped(character, key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                keyStopwatch.reset();
            }
        }
    }

    public void backspace() {
        if (!typedContent.isEmpty()) {
            typedContent = typedContent.substring(0, typedContent.length() - 1);
            if (startIndex > 0)
                startIndex--;
        }
    }

    public String getReplacedContent() {
        return replaceAll != "" ? typedContent.replaceAll(".", replaceAll) : typedContent;
    }

    public String getTypedContentFormatted() {
        return trimStringToWidth(getReplacedContent(), width - 2);
    }

    public String trimStringToWidth(String string, int width) {
        if (fontRenderer.getStringWidth(string) > width) {
            return trimStringToWidth(string.substring(0, string.length() - 1), width);
        }

        return string;
    }

    public String getTypedContent() {
        return typedContent;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public String getReplaceAll() {
        return replaceAll;
    }

    public void setReplaceAll(String replaceAll) {
        this.replaceAll = replaceAll;
    }

    public void setTypedContent(String typedContent) {

        this.typedContent = typedContent;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public static String readClipboard() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable contents = clipboard.getContents(null);
        DataFlavor dfRTF = new DataFlavor("text/rtf", "Rich Formatted Text");
        DataFlavor dfTxt = DataFlavor.stringFlavor;

        boolean hasTransferableRTFText = (contents != null)
                && contents.isDataFlavorSupported(dfRTF);

        boolean hasTransferableTxtText = (contents != null)
                && contents.isDataFlavorSupported(dfTxt);

        if (hasTransferableRTFText) {
            try {
                result = streamToString((InputStream) contents.getTransferData(dfRTF));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (hasTransferableTxtText) {
            try {
                result = (String) contents.getTransferData(dfTxt);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private static String streamToString(InputStream transferData) {
        return slurp(transferData, 1024);
    }

    public static String slurp(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try {
            final Reader in = new InputStreamReader(is, "UTF-8");
            try {
                for (; ; ) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
            } finally {
                in.close();
            }
        } catch (Exception ex) {
        }
        return out.toString();
    }

    public static void writeToClipboard(String content) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
    }
}
