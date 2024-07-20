/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiTextField
extends Gui {
    private final int id;
    private final FontRenderer fontRendererInstance;
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

    public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        this.id = componentId;
        this.fontRendererInstance = fontrendererObj;
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

    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, 5.0f, 1.0f, ColorUtils.getColor(40, 80, 225), ColorUtils.getColor(40, 80, 225), ColorUtils.getColor(40, 80, 225), ColorUtils.getColor(40, 80, 225), true, true, true);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.xPosition + 2, this.yPosition + 2, this.xPosition + this.width - 2, this.yPosition + this.height - 2, 4.0f, 1.0f, -16777216, -16777216, -16777216, -16777216, false, true, true);
            }
            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
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
                j1 = this.fontRendererInstance.drawStringWithShadow(s1, (float)l + 2.0f, i1, i);
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
                j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, i1, i);
            }
            if (flag1) {
                if (flag2) {
                    Gui.drawRect(k1, (double)(i1 - 1), (double)(k1 + 1), (double)(i1 + 1 + this.fontRendererInstance.FONT_HEIGHT), -3092272);
                } else {
                    this.fontRendererInstance.drawStringWithShadow("_", k1, i1, i);
                }
            }
            if (k != j) {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }

    public void drawTextBox(boolean censured) {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                RenderUtils.smoothAngleRect(this.xPosition - 1, this.yPosition + 4, this.xPosition + this.width + 1, this.yPosition + this.height - 4, ColorUtils.getColor(40, 80, 225));
                RenderUtils.smoothAngleRect(this.xPosition, this.yPosition + 5, this.xPosition + this.width, this.yPosition + this.height - 5, -16777216);
            }
            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            String censStart = "";
            String censEnd = "";
            if (censured) {
                s = s.replace("\u0445\u0443\u0439", censStart + "\u0445*\u0439" + censEnd);
                s = s.replace("\u0445\u0443\u0438", censStart + "\u0445*\u0438" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440", censStart + "\u043f\u0438*\u043e\u0440" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440\u044b", censStart + "\u043f\u0438*\u043e\u0440\u044b" + censEnd);
                s = s.replace("\u0445\u0443\u0439\u043b\u043e", censStart + "\u0445**\u043b\u043e" + censEnd);
                s = s.replace("\u0431\u043b\u044f\u0442\u044c", censStart + "\u0431\u043b*\u0442\u044c" + censEnd);
                s = s.replace("\u0431\u043b\u044f\u0434\u044c", censStart + "\u0431\u043b*\u0434\u044c" + censEnd);
                s = s.replace("\u0431\u043b\u044f\u0434\u0438", censStart + "\u0431\u043b*\u0434\u0438" + censEnd);
                s = s.replace("\u0431\u043b\u0442\u044c", censStart + "\u0431*\u0442\u044c" + censEnd);
                s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0451\u0431", censStart + "\u0434\u0430\u043b\u0431**\u0431" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0430\u0431\u043e\u043b", censStart + "\u043f\u0438***\u0431\u043e\u043b" + censEnd);
                s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0451\u0431\u044b", censStart + "\u0434\u0430\u043b\u0431**\u0431\u044b" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0430\u0431\u043e\u043b\u044b", censStart + "\u043f\u0438***\u0431\u043e\u043b\u044b" + censEnd);
                s = s.replace("\u0434\u0430\u0443\u043d", censStart + "\u0434*\u0443\u043d" + censEnd);
                s = s.replace("\u0434\u0430\u0443\u043d\u044b", censStart + "\u0434*\u0443\u043d\u044b" + censEnd);
                s = s.replace("\u0433\u0430\u043d\u0434\u043e\u043d", censStart + "\u0433\u0430**\u043e\u043d" + censEnd);
                s = s.replace("\u0433\u0430\u043d\u0434\u043e\u043d\u044b", censStart + "\u0433\u0430**\u043e\u043d\u044b" + censEnd);
                s = s.replace("\u0447\u043c\u043e", censStart + "\u0447*\u043e" + censEnd);
                s = s.replace("\u0445\u0443\u0435\u0441\u043e\u0441", censStart + "\u0445**\u0441\u043e\u0441" + censEnd);
                s = s.replace("\u0432\u044b\u0431\u043b\u044f\u0434\u043e\u043a", censStart + "\u0432\u044b\u0431\u043b**\u043e\u043a" + censEnd);
                s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
                s = s.replace("\u0441\u043e\u0441\u0438", censStart + "\u0441\u043e*\u0438" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0435\u0446", censStart + "\u043f\u0438\u0437*\u0435\u0446" + censEnd);
                s = s.replace("\u0445\u0443\u0439\u043d\u044f", censStart + "\u0445**\u043d\u044f" + censEnd);
                s = s.replace("\u0445\u0435\u0440\u043d\u044f", censStart + "\u0445**\u043d\u044f" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0438", censStart + "\u043f**\u0434\u0438" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0451\u0436", censStart + "\u043f\u0438**\u0404\u0436" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0435\u0436", censStart + "\u043f\u0438**\u0435\u0436" + censEnd);
                s = s.replace("\u0437\u0430\u043b\u0443\u043f\u0430", censStart + "\u0437\u0430\u043b*\u043f\u0430" + censEnd);
                s = s.replace("\u0437\u0430\u043b\u0443\u043f\u043e\u0439", censStart + "\u0437\u0430\u043b*\u043f\u043e\u0439" + censEnd);
                s = s.replace("\u0437\u0430\u043b\u0443\u043f\u0430\u043c\u0438", censStart + "\u0437\u0430\u043b*\u043f\u0430\u043c\u0438" + censEnd);
                s = s.replace("\u0433\u0430\u0432\u043d\u043e", censStart + "\u0433\u0430\u0432*\u043d\u043e" + censEnd);
                s = s.replace("\u043c\u0443\u0434\u0430\u043a", censStart + "\u043c\u0443\u0434*\u043a" + censEnd);
                s = s.replace("\u043c\u0443\u0434\u0438\u043b\u0430", censStart + "\u043c\u0443\u0434*\u043b\u0430" + censEnd);
                s = s.replace("\u043c\u0443\u0434\u0438\u043b\u043e", censStart + "\u043c\u0443\u0434*\u043b\u043e" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440\u044b", censStart + "\u043f\u0438\u0434*\u0440\u044b" + censEnd);
                s = s.replace("\u043f\u0435\u043d\u0438\u0441", censStart + "\u043f\u0435\u043d*\u0441" + censEnd);
                s = s.replace("\u0443\u0435\u0431\u0430\u043d", censStart + "\u0443\u0435*\u0430\u043d" + censEnd);
                s = s.replace("\u0443\u0435\u0431\u043e\u043a", censStart + "\u0443\u0435*\u043e\u043a" + censEnd);
                s = s.replace("\u0443\u0404\u0431\u043e\u043a", censStart + "\u0443\u0404*\u043e\u043a" + censEnd);
                s = s.replace("\u0443\u0404\u0431\u0438\u0449\u0435", censStart + "\u0443\u0404*\u0438\u0449\u0435" + censEnd);
                s = s.replace("\u0443\u0435\u0431\u0438\u0449\u0435", censStart + "\u0443\u0435*\u0438\u0449\u0435" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043b\u043e", censStart + "\u0435\u0431*\u043b\u043e" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u043e", censStart + "\u0435*\u043b\u043e" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u0438\u0449\u0435", censStart + "\u0435**\u0438\u0449\u0435" + censEnd);
                s = s.replace("\u0451\u0431\u0430\u043d\u044b\u0439", censStart + "\u0404**\u043d\u044b\u0439" + censEnd);
                s = s.replace("\u0451\u0431\u0430\u043d\u044b\u0435", censStart + "\u0404**\u043d\u044b\u0435" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043d\u044b\u0439", censStart + "\u0435**\u043d\u044b\u0439" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043d\u044b\u0435", censStart + "\u0435**\u043d\u044b\u0435" + censEnd);
                s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0438\u0441\u044c", censStart + "\u043e\u0442\u0431**\u0438\u0441\u044c" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u0442\u044c", censStart + "\u0435**\u0442\u044c" + censEnd);
                s = s.replace("\u0430\u0445\u0443\u0435\u0442\u044c", censStart + "\u0430\u0445**\u0442\u044c" + censEnd);
                s = s.replace("\u0432\u0430\u0445\u0443\u0435", censStart + "\u0432\u0430*\u0443\u0435" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0435\u0446", censStart + "\u043f\u0438**\u0435\u0446" + censEnd);
                s = s.replace("\u0434\u043e\u0435\u0431\u0430\u043b\u0441\u044f", censStart + "\u0434\u043e\u0435**\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0404\u0431\u043d\u0443\u043b", censStart + "\u0404*\u043d\u0443\u043b" + censEnd);
                s = s.replace("\u0435\u0431\u043d\u0443\u043b", censStart + "\u0435*\u043d\u0443\u043b" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043d\u0443\u043b", censStart + "\u0435**\u043d\u0443\u043b" + censEnd);
                s = s.replace("\u0437\u0430\u0435\u0431\u0430\u043b", censStart + "\u0437\u0430**\u0430\u043b" + censEnd);
                s = s.replace("\u0435\u043b\u0434\u0430", censStart + "\u0435\u043b*\u0430" + censEnd);
                s = s.replace("\u0435\u043b\u0434\u0438\u043d\u0430", censStart + "\u0435\u043b*\u0438\u043d\u0430" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0430", censStart + "\u043f*\u0437\u0434\u0430" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0430\u0431\u043e\u043b", censStart + "\u043f\u0438\u0437\u0434*\u0431\u043e\u043b" + censEnd);
                s = s.replace("\u043f\u043e\u043f\u0438\u0437\u0434\u0438", censStart + "\u043f\u043e\u043f**\u0434\u0438" + censEnd);
                s = s.replace("\u043b\u043e\u0445", censStart + "\u043b*\u0445" + censEnd);
                s = s.replace("\u043b\u043e\u043e\u0445", censStart + "\u043b**\u0445" + censEnd);
                s = s.replace("\u043b\u043e\u043e\u043e\u0445", censStart + "\u043b***\u0445" + censEnd);
                s = s.replace("\u043b\u043e\u043e\u043e\u043e\u0445", censStart + "\u043b****\u0445" + censEnd);
                s = s.replace("\u043b\u043e\u043e\u043e\u043e\u043e\u0445", censStart + "\u043b*****\u0445" + censEnd);
                s = s.replace("\u043b\u043e\u043e\u043e\u043e\u043e\u043e\u0445", censStart + "\u043b******\u0445" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0443\u0439", censStart + "\u043f\u0438\u0437**\u0439" + censEnd);
                s = s.replace("\u0445\u0443\u0451\u0432", censStart + "\u0445*\u0404\u0432" + censEnd);
                s = s.replace("\u0445\u0443\u0435\u0432", censStart + "\u0445*\u0435\u0432" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u043c\u0438", censStart + "\u0445**\u043c\u0438" + censEnd);
                s = s.replace("\u0445\u0443\u0451\u0432\u044b\u0439", censStart + "\u0445**\u0432\u044b\u0439" + censEnd);
                s = s.replace("\u043d\u0430\u0445\u0443\u0439", censStart + "\u043d\u0430**\u0439" + censEnd);
                s = s.replace("\u0445\u0443\u044e", censStart + "\u0445*\u044e" + censEnd);
                s = s.replace("\u0445\u0443\u0435", censStart + "\u0445*\u0435" + censEnd);
                s = s.replace("\u0441\u043e\u0441\u0404\u0448\u044c", censStart + "\u0441\u043e\u0441*\u0448\u044c" + censEnd);
                s = s.replace("\u0441\u043e\u0441\u0435\u0448\u044c", censStart + "\u0441\u043e\u0441*\u0448\u044c" + censEnd);
                s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
                s = s.replace("\u043e\u0442\u0445\u0443\u044f\u0440\u044e", censStart + "\u043e\u0442\u0445*\u044f\u0440\u044e" + censEnd);
                s = s.replace("\u043e\u0442\u043f\u0438\u0437\u0434\u0438\u043b", censStart + "\u043e\u0442\u043f**\u0434\u0438\u043b" + censEnd);
                s = s.replace("\u043e\u0442\u043c\u0443\u0434\u043e\u0445\u0430\u043b", censStart + "\u043e\u0442\u043c**\u043e\u0445\u0430\u043b" + censEnd);
                s = s.replace("\u0437\u0430\u0445\u0443\u044f\u0440\u0438\u043b", censStart + "\u0437\u0430\u0445**\u0440\u0438\u043b" + censEnd);
                s = s.replace("\u043e\u0442\u0445\u0443\u044f\u0440\u0438\u043b", censStart + "\u043e\u0442\u0445**\u0440\u0438\u043b" + censEnd);
                s = s.replace("\u043e\u0442\u0445\u0443\u044f\u0440\u044e", censStart + "\u043e\u0442\u0445**\u0440\u044e" + censEnd);
                s = s.replace("\u043d\u0438\u0445\u0443\u044f", censStart + "\u043d\u0438\u0445*\u044f" + censEnd);
                s = s.replace("\u0445\u0443\u0435\u0433\u043b\u043e\u0442", censStart + "\u0445\u0443*\u0433\u043b\u043e\u0442" + censEnd);
                s = s.replace("\u0445\u0443\u0435\u0433\u0440\u044b\u0437", censStart + "\u0445**\u0433\u0440\u044b\u0437" + censEnd);
                s = s.replace("\u043e\u0442\u0441\u043e\u0441", censStart + "\u043e\u0442\u0441*\u0441" + censEnd);
                s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043b", censStart + "\u0435\u0431*\u043b" + censEnd);
                s = s.replace("\u0447\u043c\u043e\u0448\u043d\u0438\u043a", censStart + "\u0447\u043c**\u043d\u0438\u043a" + censEnd);
                s = s.replace("\u043d\u0438\u0445\u0435\u0440\u0430", censStart + "\u043d\u0438\u0445*\u0440\u0430" + censEnd);
                s = s.replace("\u0448\u043b\u044e\u0445\u0430", censStart + "\u0448\u043b*\u0445\u0430" + censEnd);
                s = s.replace("\u0433\u043d\u0438\u0434\u0430", censStart + "\u0433\u043d*\u0434\u0430" + censEnd);
                s = s.replace("\u0445\u0443\u0435\u043f\u043b\u0404\u0442", censStart + "\u0445**\u043f\u043b\u0404\u0442" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0443\u0439", censStart + "\u043f**\u0434\u0443\u0439" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u0440", censStart + "\u043f*\u0434\u0440" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u0442\u044c\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0442\u044c\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u0435\u0448\u044c\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0435\u0448\u044c\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u0442\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0442\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u0435\u0448\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0435\u0448\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u043d\u0443\u043b\u0441\u044f", censStart + "\u0432\u044b**\u043d\u0443\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u044e\u0441\u044c", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0441\u044c" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u044e\u0441\u044c", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0441\u044c" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u044e\u0442\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0442\u0441\u044f" + censEnd);
                s = s.replace("\u0434\u043e\u043f\u0438\u0437\u0434\u0435\u043b\u0441\u044f", censStart + "\u0434\u043e\u043f**\u0434\u0435\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0434\u043e\u043f\u0438\u0437\u0434\u0435\u043b\u0438\u0441\u044c", censStart + "\u0434\u043e\u043f**\u0434\u0435\u043b\u0438\u0441\u044c" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u043c\u0438", censStart + "\u0445*\u044f\u043c\u0438" + censEnd);
                s = s.replace("\u0435\u0431\u0443", censStart + "\u0435*\u0443" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043d\u044b\u0439", censStart + "\u0435\u0431**\u044b\u0439" + censEnd);
                s = s.replace("\u0435\u0431\u0443\u0447\u0438\u0439", censStart + "\u0435**\u0447\u0438\u0439" + censEnd);
                s = s.replace("\u0435\u0431\u0443\u0447\u0438\u0435", censStart + "\u0435**\u0447\u0438\u0435" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u0430\u043d", censStart + "\u0435*\u043b\u0430\u043d" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u0430\u043d\u043e\u0438\u0434", censStart + "\u0435*\u043b\u0430\u043d\u043e\u0438\u0434" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u0430\u043d\u044b", censStart + "\u0435*\u043b\u0430\u043d\u044b" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u0430\u043d\u043e\u0438\u0434\u044b", censStart + "\u0435*\u043b\u0430\u043d\u043e\u0438\u0434\u044b" + censEnd);
                s = s.replace("\u043e\u0431\u043e\u0441\u0441\u0430\u043b", censStart + "\u043e\u0431\u043e\u0441\u0441*\u043b" + censEnd);
                s = s.replace("\u043d\u0430\u0435\u0431\u043d\u0443\u043b\u0441\u044f", censStart + "\u043d\u0430**\u043d\u0443\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u043d\u0430\u0435\u0431\u043d\u0443\u043b\u0438\u0441\u044c", censStart + "\u043d\u0430**\u043d\u0443\u043b\u0438\u0441\u044c" + censEnd);
                s = s.replace("\u043d\u0430\u0435\u0431\u043d\u0443\u043b", censStart + "\u043d\u0430**\u043d\u0443\u043b" + censEnd);
                s = s.replace("sosi", censStart + "s*si" + censEnd);
                s = s.replace("otsosi", censStart + "ots*si" + censEnd);
                s = s.replace("otsos", censStart + "ots*s" + censEnd);
                s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
                s = s.replace("\u043e\u0442\u0441\u043e\u0441", censStart + "\u043e\u0442\u0441*\u0441" + censEnd);
                s = s.replace("\u043f\u0435\u0440\u0435\u0435\u0431\u0443", censStart + "\u043f\u0435\u0440\u0435*\u0431\u0443" + censEnd);
                s = s.replace("\u043f\u0435\u0440\u0435\u0435\u0431\u0430\u043b", censStart + "\u043f\u0435\u0440\u0435*\u0431\u0430\u043b" + censEnd);
                s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0430\u043b", censStart + "\u043e\u0442\u044c**\u0430\u043b" + censEnd);
                s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0443", censStart + "\u043e\u0442\u044c**\u0443" + censEnd);
                s = s.replace("\u043b\u043e\u0448\u0430\u0440\u0430", censStart + "\u043b*\u0448\u0430\u0440\u0430" + censEnd);
                s = s.replace("\u043f\u0440\u043e\u0435\u0431\u0430\u043b", censStart + "\u043f\u0440\u043e**\u0430\u043b" + censEnd);
                s = s.replace("\u0443\u0435\u0431\u0430\u043b", censStart + "\u0443**\u0430\u043b" + censEnd);
                s = s.replace("\u0443\u0435\u0431\u0430\u043b\u0441\u044f", censStart + "\u0443**\u0430\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0443\u0441\u0440\u0438\u0441\u044c", censStart + "\u0443\u0441\u0440*\u0441\u044c" + censEnd);
                s = s.replace("\u0443\u0441\u0440\u0430\u043b\u0441\u044f", censStart + "\u0443\u0441\u0440*\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0443\u0441\u0440\u0438\u0440\u0430\u044e\u0442\u0441\u044f", censStart + "\u0443\u0441\u0440*\u0440\u0430\u044e\u0442\u0441\u044f" + censEnd);
                s = s.replace("\u043e\u0431\u043e\u0441\u0440\u0430\u043b", censStart + "\u043e\u0431\u043e\u0441*\u0430\u043b" + censEnd);
                s = s.replace("\u043e\u0431\u043e\u0441\u0440\u0430\u043b\u0441\u044f", censStart + "\u043e\u0431\u043e\u0441*\u0430\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0441\u0435\u0440", censStart + "\u0432\u044b\u0441*\u0440" + censEnd);
                s = s.replace("\u0448\u0430\u043b\u0430\u0432\u0430", censStart + "\u0448\u0430\u043b*\u0432\u0430" + censEnd);
                s = s.replace("\u0433\u0430\u0432\u043d\u043e\u044e\u0437\u0435\u0440", censStart + "\u0442\u044b \u043b\u0443\u0447\u0448\u0438\u0439" + censEnd);
                s = s.replace("\u0433\u043e\u0432\u043d\u043e\u044e\u0437\u0435\u0440", censStart + "\u0442\u044b \u043b\u0443\u0447\u0448\u0438\u0439" + censEnd);
                s = s.replace("\u043f\u043e\u0435\u0431\u0435\u043d\u044c", censStart + "\u043f\u043e**\u0435\u043d\u044c" + censEnd);
                s = s.replace("\u0432\u044b\u0435\u0431\u0430\u043d", censStart + "\u0432\u044b**\u0430\u043d" + censEnd);
                s = s.replace("\u0432\u044b\u0435\u0431\u0430\u043b", censStart + "\u0432\u044b**\u0430\u043b" + censEnd);
                s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0430\u043b", censStart + "\u043e\u0442\u044c**\u0430\u043b" + censEnd);
                s = s.replace("\u0435\u0431\u043b\u044f", censStart + "\u0435*\u043b\u044f" + censEnd);
                s = s.replace("\u0435\u0431\u0438", censStart + "\u0435*\u0438" + censEnd);
                s = s.replace("\u0437\u0430\u0435\u0431\u0438\u0441\u044c", censStart + "\u0437\u0430**\u0438\u0441\u044c" + censEnd);
                s = s.replace("\u0437\u0430\u0435\u0431\u043e\u043a", censStart + "\u0437\u0430**\u043e\u043a" + censEnd);
                s = s.replace("\u0445\u0443\u0439\u043d\u0438", censStart + "\u0445**\u043d\u0438" + censEnd);
                s = s.replace("\u0448\u0430\u043b\u0430\u0432\u044b", censStart + "\u0448\u0430\u043b**\u044b" + censEnd);
                s = s.replace("\u0440\u0430\u0437\u044c\u0451\u0431", censStart + "\u0440\u0430\u0437\u044c*\u0431" + censEnd);
                s = s.replace("\u0440\u0430\u0437\u044c\u0435\u0431", censStart + "\u0440\u0430\u0437\u044c*\u0431" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441", censStart + "\u043f\u0438\u0434**\u0430\u0441" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441", censStart + "\u043f\u0438\u0434**\u0430\u0441" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u044b" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u044b" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u0438\u043d\u0430", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u0430" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u0438\u043d\u0430", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u0430" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u0438\u043d\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u044b" + censEnd);
                s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u0438\u043d\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u044b" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043d\u0443\u0442\u044c\u0441\u044f", censStart + "\u0435**\u043d\u0443\u0442\u044c\u0441\u044f" + censEnd);
                s = s.replace("\u0451\u0431\u043d\u0443\u0442\u044c\u0441\u044f", censStart + "\u0404*\u043d\u0443\u0442\u044c\u0441\u044f" + censEnd);
                s = s.replace("\u0451\u0431\u043d\u0443\u043b\u0441\u044f", censStart + "\u0404*\u043d\u0443\u043b\u0441\u044f" + censEnd);
                s = s.replace("\u0441\u0443\u043a\u0430", censStart + "\u0441*\u043a\u0430" + censEnd);
                s = s.replace("\u0441\u0443\u043a\u0438", censStart + "\u0441*\u043a\u0438" + censEnd);
                s = s.replace("\u0441\u0443\u0447\u043a\u0430", censStart + "\u0441*\u0447\u043a\u0430" + censEnd);
                s = s.replace("\u0441\u0443\u0447\u043a\u0438", censStart + "\u0441*\u0447\u043a\u0438" + censEnd);
                s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0404\u0431\u044b", censStart + "\u0434\u0430\u043b\u0431\u043e**\u044b" + censEnd);
                s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0435\u0431\u044b", censStart + "\u0434\u0430\u043b\u0431\u043e**\u044b" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u0442\u044c\u0441\u044f", censStart + "\u0435**\u0442\u044c\u0441\u044f" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u0442\u0441\u044f", censStart + "\u0435**\u0442\u0441\u044f" + censEnd);
                s = s.replace("\u0435\u0431\u0443\u0441\u044c", censStart + "\u0435**\u0441\u044c" + censEnd);
                s = s.replace("\u0432\u044c\u0435\u0431\u0443", censStart + "\u0432\u044c*\u0431\u0443" + censEnd);
                s = s.replace("\u0432\u044b\u0435\u0431\u0443", censStart + "\u0432\u044b*\u0431\u0443" + censEnd);
                s = s.replace("\u0431\u043b\u044f\u0434\u0438", censStart + "\u0431**\u0434\u0438" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u0439\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0439\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u044e\u0442\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0442\u0441\u044f" + censEnd);
                s = s.replace("\u0432\u044b\u0441\u0438\u0440\u0430\u0435\u0442", censStart + "\u0432\u044b\u0441*\u0440\u0430\u0435\u0442" + censEnd);
                s = s.replace("\u0432\u044b\u0441\u0435\u0440\u0430\u0435\u0442", censStart + "\u0432\u044b\u0441*\u0440\u0430\u0435\u0442" + censEnd);
                s = s.replace("\u0432\u044b\u0441\u0440\u0430\u043b", censStart + "\u0432\u044b\u0441\u0440*\u043b" + censEnd);
                s = s.replace("\u0433\u043e\u0432\u043d\u0438\u0449\u0435", censStart + "\u0433\u043e\u0432\u043d*\u0449\u0435" + censEnd);
                s = s.replace("\u0433\u043e\u0432\u043d\u0430", censStart + "\u0433\u043e\u0432*\u0430" + censEnd);
                s = s.replace("\u0430\u0443\u0442\u0438\u0441\u0442", censStart + "\u0430\u0443\u0442*\u0441\u0442" + censEnd);
                s = s.replace("\u0435\u0431\u0430\u043d\u0430\u0442", censStart + "\u0435*\u0430\u043d\u0430\u0442" + censEnd);
                s = s.replace("\u0434\u0443\u0440\u0430", censStart + "\u0434*\u0440\u0430" + censEnd);
                s = s.replace("\u0448\u043b\u044e\u0448\u043a\u0438", censStart + "\u0448\u043b*\u0448\u043a\u0438" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0438\u0442", censStart + "\u043f\u0438\u0437*\u0438\u0442" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u044f\u0442", censStart + "\u043f\u0438\u0437*\u044f\u0442" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0438\u0448\u044c", censStart + "\u043f\u0438\u0437*\u0438\u0448\u044c" + censEnd);
                s = s.replace("\u043f\u0438\u0437\u0434\u0438\u0448", censStart + "\u043f\u0438\u0437*\u0438\u0448" + censEnd);
                s = s.replace("\u0432\u0438\u0431\u043b\u044f\u0434\u043a\u0430", censStart + "\u0432\u0438**\u044f\u0434\u043a\u0430" + censEnd);
                s = s.replace("\u0432\u0438\u0431\u043b\u044f\u0434\u043a\u0438", censStart + "\u0432\u0438**\u044f\u0434\u043a\u0438" + censEnd);
                s = s.replace("\u0432\u044b\u0431\u043b\u044f\u0434\u043a\u0430", censStart + "\u0432\u044b**\u044f\u0434\u043a\u0430" + censEnd);
                s = s.replace("\u0435\u0431\u0443\u0447\u0430\u044f", censStart + "\u0435\u0431*\u0447\u0430\u044f" + censEnd);
                s = s.replace("\u0435\u0431\u0443\u0447\u0435\u0435", censStart + "\u0435\u0431*\u0447\u0435\u0435" + censEnd);
                s = s.replace("\u043a\u043e\u043d\u0447\u0438\u043b", censStart + "\u043a\u043e\u043d\u0447*\u043b" + censEnd);
                s = s.replace("\u043a\u043e\u043d\u0447\u0430\u043b", censStart + "\u043a\u043e\u043d\u0447*\u043b" + censEnd);
                s = s.replace("\u043a\u043e\u043d\u0447\u0430", censStart + "\u043a\u043e\u043d*\u0430" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u0440\u0438\u043b", censStart + "\u0445\u0443*\u0440\u0438\u043b" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u0447\u0438\u043b", censStart + "\u0445\u0443*\u0447\u0438\u043b" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u0440\u044e", censStart + "\u0445\u0443*\u0440\u044e" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u0447\u044e", censStart + "\u0445\u0443*\u0447\u044e" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u0440\u0443", censStart + "\u0445\u0443*\u0440\u0443" + censEnd);
                s = s.replace("\u0445\u0443\u044f\u0447\u0443", censStart + "\u0445\u0443*\u0447\u0443" + censEnd);
                s = s.replace("\u0441\u0432\u0438\u043d\u044c\u044f", censStart + "\u0441\u0432\u0438\u043d\u044e\u0448\u043a\u0430" + censEnd);
                s = s.replace("\u0447\u0443\u0440\u043a\u0430", censStart + "\u0447*\u0440\u043a\u0430" + censEnd);
                s = s.replace("\u0447\u0443\u0440\u043a\u0438", censStart + "\u0447*\u0440\u043a\u0438" + censEnd);
                s = s.replace("Penis", censStart + "Pen*s" + censEnd);
                s = s.replace("penis", censStart + "pen*s" + censEnd);
                s = s.replace("\u0446\u0435\u043b\u0435\u0441\u0442\u0438\u0430\u043b", censStart + "\u0446\u0435\u043b\u0435\u043f\u0443\u043a\u0441\u0442\u0438\u0430\u043b" + censEnd);
                s = s.replace("\u0430\u043a\u0440\u0438\u0435\u043d", censStart + "\u0430\u043a\u0440\u0438\u043f\u0443\u043a" + censEnd);
                s = s.replace("\u043d\u0443\u0440\u0438\u043a", censStart + "\u043d\u0443\u0440\u0438\u043f\u0443\u043a" + censEnd);
                s = s.replace("\u043d\u0443\u0440\u0441\u0443\u043b\u0442\u0430\u043d", censStart + "\u043d\u0443\u0440\u0441\u0443\u043b\u043f\u0443\u043a" + censEnd);
                s = s.replace("\u0440\u0438\u0447", censStart + "\u0441\u0440\u0438\u0447" + censEnd);
                s = s.replace("rich", censStart + "srich" + censEnd);
                s = s.replace("celestial", censStart + "celepukstial" + censEnd);
                s = s.replace("\u043d\u0435\u0432\u0435\u0440\u0445\u0443\u043a", censStart + "\u043d\u0435\u0432\u0435\u0440\u0445\u0440\u044e\u043a" + censEnd);
                s = s.replace("neverhook", censStart + "neverpuk" + censEnd);
                s = s.replace("\u044f \u043b\u044e\u0431\u043b\u044e \u044d\u0442\u043e\u0442 \u0447\u0438\u0442\u0435\u0440\u0441\u043a\u0438\u0439 \u0441\u0435\u0440\u0432\u0435\u0440 StormHVH", censStart + "\u044f \u0434\u0443\u0440\u0430\u043a" + censEnd);
                s = s.replace("YT", censStart + "LOH" + censEnd);
                s = s.replace("akrien", censStart + "akripuk" + censEnd);
                s = s.replace("Celka", censStart + "Dirka" + censEnd);
                s = s.replace("\u0435\u043a\u043f\u0435\u043d\u0441\u0438\u0432", censStart + "\u0435\u043a\u0441\u043f\u0443\u043a\u0441\u0438\u0432" + censEnd);
                s = s.replace("expensive", censStart + "expuksive" + censEnd);
                s = s.replace("\u0445\u0435\u0432\u0435\u043d", censStart + "\u0445\u0443\u0435\u0432\u0435\u043d" + censEnd);
                s = s.replace("\u0446\u0435\u043b\u0435\u0441\u0442\u0438\u0430\u043b", censStart + "\u0446\u0435\u043b\u0435\u043f\u0443\u043a\u0441\u0442\u0438\u0430\u043b" + censEnd);
                s = s.replace("heaven", censStart + "hueven" + censEnd);
                s = s.replace("\u0434\u0435\u0434\u043a\u043e\u0434", censStart + "\u0434\u0435\u0434\u043f\u0443\u043a" + censEnd);
                s = s.replace("deadcode", censStart + "deadpuk" + censEnd);
                s = s.replace("\u0432\u0435\u043a\u0441", censStart + "\u0432\u0435\u043f\u0443\u043a\u0441" + censEnd);
                s = s.replace("\u0432\u0435\u043a\u0441\u0430\u0439\u0434", censStart + "\u0432\u0435\u043f\u0443\u043a\u0441\u0430\u0439\u0434" + censEnd);
                s = s.replace("wexside", censStart + "pukside" + censEnd);
            }
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
                j1 = this.fontRendererInstance.drawStringWithShadow(s1, (float)l + 2.0f, i1, i);
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
                j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, i1, i);
            }
            if (flag1) {
                if (flag2) {
                    Gui.drawRect(k1, (double)(i1 - 1), (double)(k1 + 1), (double)(i1 + 1 + this.fontRendererInstance.FONT_HEIGHT), -3092272);
                } else {
                    this.fontRendererInstance.drawStringWithShadow("_", k1, i1, i);
                }
            }
            if (k != j) {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
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

