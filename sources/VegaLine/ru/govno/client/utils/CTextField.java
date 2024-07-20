/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class CTextField
extends Gui {
    private final int id;
    private final CFontRenderer fontRendererInstance;
    public int xPosition;
    public int yPosition;
    private final int width;
    private final int height;
    private String text = "";
    private int maxStringLength = 32;
    private int cursorCounter;
    private boolean enableBackgroundDrawing = true;
    private boolean canLoseFocus = true;
    private boolean isFocused;
    private boolean isEnabled = true;
    private int lineScrollOffset;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor = 0xE0E0E0;
    private int disabledColor = 0x707070;
    private boolean visible = true;
    private GuiPageButtonList.GuiResponder guiResponder;
    private Predicate<String> validator = Predicates.alwaysTrue();

    public CTextField(int componentId, CFontRenderer cfontrenderer, int x, int y, int par5Width, int par6Height) {
        this.id = componentId;
        this.fontRendererInstance = cfontrenderer;
        this.xPosition = x;
        this.yPosition = y;
        this.width = par5Width;
        this.height = par6Height;
    }

    public void setGuiResponder(GuiPageButtonList.GuiResponder guiResponderIn) {
        this.guiResponder = guiResponderIn;
    }

    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    public void setText(String textIn) {
        if (this.validator.apply(textIn)) {
            this.text = textIn.length() > this.maxStringLength ? textIn.substring(0, this.maxStringLength) : textIn;
            this.setCursorPositionEnd();
        }
    }

    public String getText() {
        return this.text;
    }

    public String getSelectedText() {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }

    public void setValidator(Predicate<String> theValidator) {
        this.validator = theValidator;
    }

    public void writeText(String textToWrite) {
        int l;
        Object s = "";
        String s1 = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);
        if (!this.text.isEmpty()) {
            s = (String)s + this.text.substring(0, i);
        }
        if (k < s1.length()) {
            s = (String)s + s1.substring(0, k);
            l = k;
        } else {
            s = (String)s + s1;
            l = s1.length();
        }
        if (!this.text.isEmpty() && j < this.text.length()) {
            s = (String)s + this.text.substring(j);
        }
        if (this.validator.apply((String)s)) {
            this.text = s;
            this.moveCursorBy(i - this.selectionEnd + l);
            this.func_190516_a(this.id, this.text);
        }
    }

    public void func_190516_a(int p_190516_1_, String p_190516_2_) {
        if (this.guiResponder != null) {
            this.guiResponder.setEntryValue(p_190516_1_, p_190516_2_);
        }
    }

    public void deleteWords(int num) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(num) - this.cursorPosition);
            }
        }
    }

    public void deleteFromCursor(int num) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean flag = num < 0;
                int i = flag ? this.cursorPosition + num : this.cursorPosition;
                int j = flag ? this.cursorPosition : this.cursorPosition + num;
                Object s = "";
                if (i >= 0) {
                    s = this.text.substring(0, i);
                }
                if (j < this.text.length()) {
                    s = (String)s + this.text.substring(j);
                }
                if (this.validator.apply((String)s)) {
                    this.text = s;
                    if (flag) {
                        this.moveCursorBy(num);
                    }
                    this.func_190516_a(this.id, this.text);
                }
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public int getNthWordFromCursor(int numWords) {
        return this.getNthWordFromPos(numWords, this.getCursorPosition());
    }

    public int getNthWordFromPos(int n, int pos) {
        return this.getNthWordFromPosWS(n, pos, true);
    }

    public int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
        int i = pos;
        boolean flag = n < 0;
        int j = Math.abs(n);
        for (int k = 0; k < j; ++k) {
            if (!flag) {
                int l = this.text.length();
                if ((i = this.text.indexOf(32, i)) == -1) {
                    i = l;
                    continue;
                }
                while (skipWs && i < l && this.text.charAt(i) == ' ') {
                    ++i;
                }
                continue;
            }
            while (skipWs && i > 0 && this.text.charAt(i - 1) == ' ') {
                --i;
            }
            while (i > 0 && this.text.charAt(i - 1) != ' ') {
                --i;
            }
        }
        return i;
    }

    public void moveCursorBy(int num) {
        this.setCursorPosition(this.selectionEnd + num);
    }

    public void setCursorPosition(int pos) {
        this.cursorPosition = pos;
        int i = this.text.length();
        this.cursorPosition = MathHelper.clamp(this.cursorPosition, 0, i);
        this.setSelectionPos(this.cursorPosition);
    }

    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if (!this.isFocused) {
            return false;
        }
        if (GuiScreen.isKeyComboCtrlA(keyCode)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        if (GuiScreen.isKeyComboCtrlC(keyCode)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        }
        if (GuiScreen.isKeyComboCtrlV(keyCode)) {
            if (this.isEnabled) {
                this.writeText(GuiScreen.getClipboardString());
            }
            return true;
        }
        if (GuiScreen.isKeyComboCtrlX(keyCode)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText("");
            }
            return true;
        }
        switch (keyCode) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(-1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(0);
                } else {
                    this.setCursorPositionZero();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                    } else {
                        this.setSelectionPos(this.getSelectionEnd() - 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                } else {
                    this.moveCursorBy(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                    } else {
                        this.setSelectionPos(this.getSelectionEnd() + 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                } else {
                    this.moveCursorBy(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(this.text.length());
                } else {
                    this.setCursorPositionEnd();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(1);
                }
                return true;
            }
        }
        if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            if (this.isEnabled) {
                this.writeText(Character.toString(typedChar));
            }
            return true;
        }
        return false;
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean flag;
        boolean bl = flag = mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
        if (this.canLoseFocus) {
            this.setFocused(flag);
        }
        if (this.isFocused && flag && mouseButton == 0) {
            int i = mouseX - this.xPosition;
            if (this.enableBackgroundDrawing) {
                i -= 4;
            }
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
            return true;
        }
        return false;
    }

    public void drawTextBox(CFontRenderer font, float alphaPercent) {
        if (this.getVisible()) {
            int i = ColorUtils.swapAlpha(this.isEnabled ? this.enabledColor : this.disabledColor, 255.0f * alphaPercent);
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;
            if (k > s.length()) {
                k = s.length();
            }
            if (!s.isEmpty()) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = font.getStringWidth(s1) + l;
                font.drawStringWithShadow(s1, l, i1, i);
            }
            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;
            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }
            if (!s.isEmpty() && flag && j < s.length()) {
                font.drawStringWithShadow(s.substring(j), j1, i1, i);
            }
            if (flag1) {
                if (flag2) {
                    RenderUtils.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.getHeight(), -3092272);
                } else {
                    font.drawStringWithShadow("_", k1, i1, i);
                }
            }
            if (k != j) {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.getHeight());
            }
        }
    }

    private void drawCursorVertical(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        if (startY < endY) {
            int j = startY;
            startY = endY;
            endY = j;
        }
        if (endX > this.xPosition + this.width) {
            endX = this.xPosition + this.width;
        }
        if (startX > this.xPosition + this.width) {
            startX = this.xPosition + this.width;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(startX, endY, 0.0).endVertex();
        bufferbuilder.pos(endX, endY, 0.0).endVertex();
        bufferbuilder.pos(endX, startY, 0.0).endVertex();
        bufferbuilder.pos(startX, startY, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public void setMaxStringLength(int length) {
        this.maxStringLength = length;
        if (this.text.length() > length) {
            this.text = this.text.substring(0, length);
        }
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public int getCursorPosition() {
        return this.cursorPosition;
    }

    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn) {
        this.enableBackgroundDrawing = enableBackgroundDrawingIn;
    }

    public void setTextColor(int color) {
        this.enabledColor = color;
    }

    public void setDisabledTextColour(int color) {
        this.disabledColor = color;
    }

    public void setFocused(boolean isFocusedIn) {
        if (isFocusedIn && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = isFocusedIn;
        if (Minecraft.getMinecraft().currentScreen != null) {
            Minecraft.getMinecraft().currentScreen.func_193975_a(isFocusedIn);
        }
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    public void setSelectionPos(int position) {
        int i = this.text.length();
        if (position > i) {
            position = i;
        }
        if (position < 0) {
            position = 0;
        }
        this.selectionEnd = position;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > i) {
                this.lineScrollOffset = i;
            }
            int j = this.getWidth();
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
            int k = s.length() + this.lineScrollOffset;
            if (position == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
            }
            if (position > k) {
                this.lineScrollOffset += position - k;
            } else if (position <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - position;
            }
            this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
        }
    }

    public void setCanLoseFocus(boolean canLoseFocusIn) {
        this.canLoseFocus = canLoseFocusIn;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }
}

