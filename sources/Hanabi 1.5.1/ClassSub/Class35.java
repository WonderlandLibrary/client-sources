package ClassSub;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Class35 extends Gui
{
    private final FontRenderer fontRenderer;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private String text;
    private int maxStringLength;
    private int cursorCounter;
    private boolean enableBackgroundDrawing;
    private boolean canLoseFocus;
    public boolean isFocused;
    private boolean isEnabled;
    private int field_73816_n;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor;
    private int disabledColor;
    private boolean field_73823_s;
    
    
    public Class35(final FontRenderer fontRenderer, final int xPos, final int yPos, final int width, final int height) {
        this.text = "";
        this.maxStringLength = 50;
        this.enableBackgroundDrawing = true;
        this.canLoseFocus = true;
        this.isFocused = false;
        this.isEnabled = true;
        this.field_73816_n = 0;
        this.cursorPosition = 0;
        this.selectionEnd = 0;
        this.enabledColor = 14737632;
        this.disabledColor = 7368816;
        this.field_73823_s = true;
        this.fontRenderer = fontRenderer;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }
    
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }
    
    public void setText(final String text) {
        if (text.length() > this.maxStringLength) {
            this.text = text.substring(0, this.maxStringLength);
        }
        else {
            this.text = text;
        }
        this.setCursorPositionEnd();
    }
    
    public String getText() {
        return this.text.replaceAll(" ", "");
    }
    
    public String getSelectedtext() {
        return this.text.substring((this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd, (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition);
    }
    
    public void writeText(final String s) {
        String string = "";
        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(s);
        final int n = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
        final int n2 = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
        final int n3 = this.maxStringLength - this.text.length() - (n - this.selectionEnd);
        if (this.text.length() > 0) {
            string += this.text.substring(0, n);
        }
        String s2;
        int length;
        if (n3 < filterAllowedCharacters.length()) {
            s2 = string + filterAllowedCharacters.substring(0, n3);
            length = n3;
        }
        else {
            s2 = string + filterAllowedCharacters;
            length = filterAllowedCharacters.length();
        }
        if (this.text.length() > 0 && n2 < this.text.length()) {
            s2 += this.text.substring(n2);
        }
        this.text = s2.replaceAll(" ", "");
        this.func_73784_d(n - this.selectionEnd + length);
    }
    
    public void func_73779_a(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursorPosition);
            }
        }
    }
    
    public void deleteFromCursor(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                final boolean b = n < 0;
                final int n2 = b ? (this.cursorPosition + n) : this.cursorPosition;
                final int n3 = b ? this.cursorPosition : (this.cursorPosition + n);
                String text = "";
                if (n2 >= 0) {
                    text = this.text.substring(0, n2);
                }
                if (n3 < this.text.length()) {
                    text += this.text.substring(n3);
                }
                this.text = text;
                if (b) {
                    this.func_73784_d(n);
                }
            }
        }
    }
    
    public int getNthWordFromCursor(final int n) {
        return this.getNthWordFromPos(n, this.getCursorPosition());
    }
    
    public int getNthWordFromPos(final int n, final int n2) {
        return this.func_73798_a(n, this.getCursorPosition(), true);
    }
    
    public int func_73798_a(final int n, final int n2, final boolean b) {
        int index = n2;
        final boolean b2 = n < 0;
        for (int abs = Math.abs(n), i = 0; i < abs; ++i) {
            if (!b2) {
                final int length = this.text.length();
                index = this.text.indexOf(32, index);
                if (index == -1) {
                    index = length;
                }
                else {
                    while (b && index < length && this.text.charAt(index) == ' ') {
                        ++index;
                    }
                }
            }
            else {
                while (b && index > 0 && this.text.charAt(index - 1) == ' ') {
                    --index;
                }
                while (index > 0 && this.text.charAt(index - 1) != ' ') {
                    --index;
                }
            }
        }
        return index;
    }
    
    public void func_73784_d(final int n) {
        this.setCursorPosition(this.selectionEnd + n);
    }
    
    public void setCursorPosition(final int cursorPosition) {
        this.cursorPosition = cursorPosition;
        final int length = this.text.length();
        if (this.cursorPosition < 0) {
            this.cursorPosition = 0;
        }
        if (this.cursorPosition > length) {
            this.cursorPosition = length;
        }
        this.func_73800_i(this.cursorPosition);
    }
    
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }
    
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }
    
    public boolean textboxKeyTyped(final char c, final int n) {
        if (!this.isEnabled || !this.isFocused) {
            return false;
        }
        switch (c) {
            case '\u0001': {
                this.setCursorPositionEnd();
                this.func_73800_i(0);
                return true;
            }
            case '\u0003': {
                GuiScreen.setClipboardString(this.getSelectedtext());
                return true;
            }
            case '\u0016': {
                this.writeText(GuiScreen.getClipboardString());
                return true;
            }
            case '\u0018': {
                GuiScreen.setClipboardString(this.getSelectedtext());
                this.writeText("");
                return true;
            }
            default: {
                switch (n) {
                    case 14: {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.func_73779_a(-1);
                        }
                        else {
                            this.deleteFromCursor(-1);
                        }
                        return true;
                    }
                    case 199: {
                        if (GuiScreen.isShiftKeyDown()) {
                            this.func_73800_i(0);
                        }
                        else {
                            this.setCursorPositionZero();
                        }
                        return true;
                    }
                    case 203: {
                        if (GuiScreen.isShiftKeyDown()) {
                            if (GuiScreen.isCtrlKeyDown()) {
                                this.func_73800_i(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                            }
                            else {
                                this.func_73800_i(this.getSelectionEnd() - 1);
                            }
                        }
                        else if (GuiScreen.isCtrlKeyDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(-1));
                        }
                        else {
                            this.func_73784_d(-1);
                        }
                        return true;
                    }
                    case 205: {
                        if (GuiScreen.isShiftKeyDown()) {
                            if (GuiScreen.isCtrlKeyDown()) {
                                this.func_73800_i(this.getNthWordFromPos(1, this.getSelectionEnd()));
                            }
                            else {
                                this.func_73800_i(this.getSelectionEnd() + 1);
                            }
                        }
                        else if (GuiScreen.isCtrlKeyDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(1));
                        }
                        else {
                            this.func_73784_d(1);
                        }
                        return true;
                    }
                    case 207: {
                        if (GuiScreen.isShiftKeyDown()) {
                            this.func_73800_i(this.text.length());
                        }
                        else {
                            this.setCursorPositionEnd();
                        }
                        return true;
                    }
                    case 211: {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.func_73779_a(1);
                        }
                        else {
                            this.deleteFromCursor(1);
                        }
                        return true;
                    }
                    default: {
                        if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                            this.writeText(Character.toString(c));
                            return true;
                        }
                        return false;
                    }
                }
                break;
            }
        }
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        final boolean b = n >= this.xPos && n < this.xPos + this.width && n2 >= this.yPos && n2 < this.yPos + this.height;
        if (this.canLoseFocus) {
            this.setFocused(this.isEnabled && b);
        }
        if (this.isFocused && n3 == 0) {
            int n4 = n - this.xPos;
            if (this.enableBackgroundDrawing) {
                n4 -= 4;
            }
            this.setCursorPosition(this.fontRenderer.trimStringToWidth(this.fontRenderer.trimStringToWidth(this.text.substring(this.field_73816_n), this.getWidth()), n4).length() + this.field_73816_n);
        }
    }
    
    public void drawTextBox() {
        if (this.func_73778_q()) {
            if (this.getEnableBackgroundDrawing()) {
                Gui.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
                Gui.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
            }
            final int n = this.isEnabled ? this.enabledColor : this.disabledColor;
            final int n2 = this.cursorPosition - this.field_73816_n;
            int length = this.selectionEnd - this.field_73816_n;
            final String trimStringToWidth = this.fontRenderer.trimStringToWidth(this.text.substring(this.field_73816_n), this.getWidth());
            final boolean b = n2 >= 0 && n2 <= trimStringToWidth.length();
            final boolean b2 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && b;
            final int n3 = this.enableBackgroundDrawing ? (this.xPos + 4) : this.xPos;
            final int n4 = this.enableBackgroundDrawing ? (this.yPos + (this.height - 8) / 2) : this.yPos;
            int drawStringWithShadow = n3;
            if (length > trimStringToWidth.length()) {
                length = trimStringToWidth.length();
            }
            if (trimStringToWidth.length() > 0) {
                if (b) {
                    trimStringToWidth.substring(0, n2);
                }
                drawStringWithShadow = Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.text.replaceAll("(?s).", "*"), (float)n3, (float)n4, n);
            }
            final boolean b3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int n5 = drawStringWithShadow;
            if (!b) {
                n5 = ((n2 > 0) ? (n3 + this.width) : n3);
            }
            else if (b3) {
                n5 = drawStringWithShadow - 1;
                --drawStringWithShadow;
            }
            if (trimStringToWidth.length() > 0 && b && n2 < trimStringToWidth.length()) {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(trimStringToWidth.substring(n2), (float)drawStringWithShadow, (float)n4, n);
            }
            if (b2) {
                if (b3) {
                    Gui.drawRect(n5, n4 - 1, n5 + 1, n4 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
                }
                else {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("_", (float)n5, (float)n4, n);
                }
            }
            if (length != n2) {
                this.drawCursorVertical(n5, n4 - 1, n3 + this.fontRenderer.getStringWidth(trimStringToWidth.substring(0, length)) - 1, n4 + 1 + this.fontRenderer.FONT_HEIGHT);
            }
        }
    }
    
    private void drawCursorVertical(int n, int n2, int n3, int n4) {
        if (n < n3) {
            final int n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            final int n6 = n2;
            n2 = n4;
            n4 = n6;
        }
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        GL11.glColor4f(0.0f, 0.0f, 255.0f, 255.0f);
        GL11.glDisable(3553);
        GL11.glEnable(3058);
        GL11.glLogicOp(5387);
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos((double)n, (double)n4, 0.0);
        getWorldRenderer.pos((double)n3, (double)n4, 0.0);
        getWorldRenderer.pos((double)n3, (double)n2, 0.0);
        getWorldRenderer.pos((double)n, (double)n2, 0.0);
        getInstance.draw();
        GL11.glDisable(3058);
        GL11.glEnable(3553);
    }
    
    public void setMaxStringLength(final int maxStringLength) {
        this.maxStringLength = maxStringLength;
        if (this.text.length() > maxStringLength) {
            this.text = this.text.substring(0, maxStringLength);
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
    
    public void setEnableBackgroundDrawing(final boolean enableBackgroundDrawing) {
        this.enableBackgroundDrawing = enableBackgroundDrawing;
    }
    
    public void func_73794_g(final int enabledColor) {
        this.enabledColor = enabledColor;
    }
    
    public void setFocused(final boolean isFocused) {
        if (isFocused && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = isFocused;
    }
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    public int getSelectionEnd() {
        return this.selectionEnd;
    }
    
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
    }
    
    public void func_73800_i(int selectionEnd) {
        final int length = this.text.length();
        if (selectionEnd > length) {
            selectionEnd = length;
        }
        if (selectionEnd < 0) {
            selectionEnd = 0;
        }
        this.selectionEnd = selectionEnd;
        if (this.fontRenderer != null) {
            if (this.field_73816_n > length) {
                this.field_73816_n = length;
            }
            final int width = this.getWidth();
            final int n = this.fontRenderer.trimStringToWidth(this.text.substring(this.field_73816_n), width).length() + this.field_73816_n;
            if (selectionEnd == this.field_73816_n) {
                this.field_73816_n -= this.fontRenderer.trimStringToWidth(this.text, width, true).length();
            }
            if (selectionEnd > n) {
                this.field_73816_n += selectionEnd - n;
            }
            else if (selectionEnd <= this.field_73816_n) {
                this.field_73816_n -= this.field_73816_n - selectionEnd;
            }
            if (this.field_73816_n < 0) {
                this.field_73816_n = 0;
            }
            if (this.field_73816_n > length) {
                this.field_73816_n = length;
            }
        }
    }
    
    public void setCanLoseFocus(final boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }
    
    public boolean func_73778_q() {
        return this.field_73823_s;
    }
    
    public void func_73790_e(final boolean field_73823_s) {
        this.field_73823_s = field_73823_s;
    }
}
