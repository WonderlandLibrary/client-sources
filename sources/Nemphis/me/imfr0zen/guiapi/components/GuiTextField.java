/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.components;

import java.util.ArrayList;
import me.imfr0zen.guiapi.Colors;
import me.imfr0zen.guiapi.KeyUtils;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.GuiComponent;
import me.imfr0zen.guiapi.listeners.TextListener;

public class GuiTextField
implements GuiComponent {
    private boolean marked;
    private int blinkState;
    private int posX;
    private int posY;
    private String text;
    private String desc;
    private ArrayList<TextListener> textlisteners = new ArrayList();

    public GuiTextField(String desc, String input) {
        this.desc = desc;
        this.text = input;
        this.blinkState = -1;
    }

    public GuiTextField(String desc) {
        this(desc, "");
    }

    public void addTextListener(TextListener listener) {
        this.textlisteners.add(listener);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        int w = RenderUtil.getWidth(this.desc);
        int w0 = RenderUtil.getWidth(this.text);
        int w1 = w0 < 50 ? 50 : w0;
        int h = 11;
        RenderUtil.drawRect(posX + w + 8, posY, posX + w + w1 + 11, posY + 11, this.blinkState == -1 ? Colors.buttonColor : (this.marked ? Colors.buttonColorLight : Colors.buttonColorDark));
        RenderUtil.drawHorizontalLine(posX + w + 7, posX + w + w1 + 11, posY, -13224394);
        RenderUtil.drawHorizontalLine(posX + w + 7, posX + w + w1 + 11, posY + 11, -13224394);
        RenderUtil.drawVerticalLine(posX + w + 7, posY, posY + 11, -13224394);
        RenderUtil.drawVerticalLine(posX + w + w1 + 11, posY, posY + 11, -13224394);
        RenderUtil.drawString(this.desc, posX + 3, posY + 2, 13158600);
        if (!this.text.isEmpty()) {
            RenderUtil.drawString(this.text, posX + 10 + w, posY + 2, 13158600);
        }
        if (this.blinkState != -1) {
            ++this.blinkState;
            if (this.blinkState < 20 && !this.text.isEmpty()) {
                RenderUtil.drawVerticalLine(posX + w + RenderUtil.getWidth(this.text) + 9, posY + 1, posY + 10, -3618616);
            } else if (this.blinkState > 40) {
                this.blinkState = 0;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int w1;
        int w = RenderUtil.getWidth(this.desc);
        int w0 = RenderUtil.getWidth(this.text);
        int n = w1 = w0 < 50 ? 50 : w0;
        if (RenderUtil.isHovered(this.posX + w + 8, this.posY, w1 + 4, 11, mouseX, mouseY)) {
            boolean b = true;
            if (this.blinkState == -1) {
                this.blinkState = 11;
                b = false;
            }
            if (this.blinkState != -1 && b) {
                this.blinkState = -1;
                for (TextListener listener : this.textlisteners) {
                    listener.stringEntered(this.text);
                }
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (this.blinkState != -1) {
            if (keyCode == 14 && !this.text.isEmpty()) {
                if (!this.text.isEmpty()) {
                    if (!this.marked) {
                        this.text = this.text.substring(0, this.text.length() - 1);
                        for (TextListener listener : this.textlisteners) {
                            listener.keyTyped(typedChar, this.text);
                        }
                    } else {
                        this.text = "";
                        this.marked = false;
                    }
                }
            } else if (keyCode == 28) {
                this.blinkState = -1;
                for (TextListener listener2 : this.textlisteners) {
                    listener2.stringEntered(this.text);
                }
                for (TextListener listener2 : this.textlisteners) {
                    listener2.stringEntered(this.text);
                }
            } else if (typedChar != '\u0000' && keyCode != 14 && !KeyUtils.isCtrlKeyDown()) {
                if (!this.marked) {
                    this.text = String.valueOf(this.text) + typedChar;
                } else {
                    this.text = Character.toString(typedChar);
                    this.marked = false;
                }
                for (TextListener listener : this.textlisteners) {
                    listener.keyTyped(typedChar, this.text);
                }
            } else if (KeyUtils.isKeyComboCtrlC(keyCode) && this.marked) {
                KeyUtils.setClipboardString(this.text);
            } else if (KeyUtils.isKeyComboCtrlV(keyCode) && KeyUtils.getClipboardString() != null && !KeyUtils.getClipboardString().isEmpty()) {
                if (this.marked) {
                    this.text = KeyUtils.getClipboardString();
                    this.marked = false;
                } else {
                    this.text = String.valueOf(this.text) + KeyUtils.getClipboardString();
                }
                for (TextListener listener : this.textlisteners) {
                    listener.keyTyped(typedChar, this.text);
                }
            } else if (KeyUtils.isKeyComboCtrlA(keyCode)) {
                this.marked = true;
            } else if (this.marked && keyCode == 211) {
                this.text = "";
                this.marked = false;
            } else if (KeyUtils.isKeyComboCtrlX(keyCode) && this.marked) {
                KeyUtils.setClipboardString(this.text);
                this.text = "";
                this.marked = false;
            } else if (this.marked && !KeyUtils.isShiftKeyDown() && !KeyUtils.isKeyComboCtrlV(keyCode) && !KeyUtils.isKeyComboCtrlX(keyCode)) {
                this.marked = false;
            }
        }
    }

    @Override
    public int getWidth() {
        int w;
        return RenderUtil.getWidth(this.desc) + ((w = RenderUtil.getWidth(this.text)) < 50 ? 50 : w) + 15;
    }

    @Override
    public int getHeight() {
        return 14;
    }
}

