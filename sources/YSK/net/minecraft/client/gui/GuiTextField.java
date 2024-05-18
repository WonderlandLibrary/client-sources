package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GuiTextField extends Gui
{
    private Predicate<String> field_175209_y;
    private final int width;
    private int maxStringLength;
    private boolean visible;
    private boolean isFocused;
    private boolean isEnabled;
    private int cursorPosition;
    private boolean enableBackgroundDrawing;
    private int lineScrollOffset;
    private int disabledColor;
    private int selectionEnd;
    private final FontRenderer fontRendererInstance;
    private final int height;
    private final int id;
    private int cursorCounter;
    private int enabledColor;
    private static final String[] I;
    public int yPosition;
    private String text;
    public int xPosition;
    private boolean canLoseFocus;
    private GuiPageButtonList.GuiResponder field_175210_x;
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    public int getCursorPosition() {
        return this.cursorPosition;
    }
    
    public void func_175205_a(final Predicate<String> field_175209_y) {
        this.field_175209_y = field_175209_y;
    }
    
    public String getText() {
        return this.text;
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
    
    public void moveCursorBy(final int n) {
        this.setCursorPosition(this.selectionEnd + n);
    }
    
    public void setFocused(final boolean isFocused) {
        if (isFocused && !this.isFocused) {
            this.cursorCounter = "".length();
        }
        this.isFocused = isFocused;
    }
    
    public void setEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    public void deleteWords(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText(GuiTextField.I["  ".length()]);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursorPosition);
            }
        }
    }
    
    public void updateCursorCounter() {
        this.cursorCounter += " ".length();
    }
    
    public void setEnableBackgroundDrawing(final boolean enableBackgroundDrawing) {
        this.enableBackgroundDrawing = enableBackgroundDrawing;
    }
    
    public int getNthWordFromPos(final int n, final int n2) {
        return this.func_146197_a(n, n2, " ".length() != 0);
    }
    
    public GuiTextField(final int id, final FontRenderer fontRendererInstance, final int xPosition, final int yPosition, final int width, final int height) {
        this.text = GuiTextField.I["".length()];
        this.maxStringLength = (0x2C ^ 0xC);
        this.enableBackgroundDrawing = (" ".length() != 0);
        this.canLoseFocus = (" ".length() != 0);
        this.isEnabled = (" ".length() != 0);
        this.enabledColor = 517511 + 2282207 + 5478528 + 6459386;
        this.disabledColor = 2640041 + 6718787 - 6335738 + 4345726;
        this.visible = (" ".length() != 0);
        this.field_175209_y = (Predicate<String>)Predicates.alwaysTrue();
        this.id = id;
        this.fontRendererInstance = fontRendererInstance;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
    }
    
    public void setText(final String text) {
        if (this.field_175209_y.apply((Object)text)) {
            if (text.length() > this.maxStringLength) {
                this.text = text.substring("".length(), this.maxStringLength);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                this.text = text;
            }
            this.setCursorPositionEnd();
        }
    }
    
    public void writeText(final String s) {
        String string = GuiTextField.I[" ".length()];
        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(s);
        int n;
        if (this.cursorPosition < this.selectionEnd) {
            n = this.cursorPosition;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            n = this.selectionEnd;
        }
        final int n2 = n;
        int n3;
        if (this.cursorPosition < this.selectionEnd) {
            n3 = this.selectionEnd;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n3 = this.cursorPosition;
        }
        final int n4 = n3;
        final int n5 = this.maxStringLength - this.text.length() - (n2 - n4);
        "".length();
        if (this.text.length() > 0) {
            string = String.valueOf(string) + this.text.substring("".length(), n2);
        }
        String text;
        int length;
        if (n5 < filterAllowedCharacters.length()) {
            text = String.valueOf(string) + filterAllowedCharacters.substring("".length(), n5);
            length = n5;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            text = String.valueOf(string) + filterAllowedCharacters;
            length = filterAllowedCharacters.length();
        }
        if (this.text.length() > 0 && n4 < this.text.length()) {
            text = String.valueOf(text) + this.text.substring(n4);
        }
        if (this.field_175209_y.apply((Object)text)) {
            this.text = text;
            this.moveCursorBy(n2 - this.selectionEnd + length);
            if (this.field_175210_x != null) {
                this.field_175210_x.func_175319_a(this.id, this.text);
            }
        }
    }
    
    public boolean getVisible() {
        return this.visible;
    }
    
    public void deleteFromCursor(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText(GuiTextField.I["   ".length()]);
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else {
                int n2;
                if (n < 0) {
                    n2 = " ".length();
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                final int n3 = n2;
                int cursorPosition;
                if (n3 != 0) {
                    cursorPosition = this.cursorPosition + n;
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                }
                else {
                    cursorPosition = this.cursorPosition;
                }
                final int n4 = cursorPosition;
                int cursorPosition2;
                if (n3 != 0) {
                    cursorPosition2 = this.cursorPosition;
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else {
                    cursorPosition2 = this.cursorPosition + n;
                }
                final int n5 = cursorPosition2;
                String text = GuiTextField.I[0xC3 ^ 0xC7];
                if (n4 >= 0) {
                    text = this.text.substring("".length(), n4);
                }
                if (n5 < this.text.length()) {
                    text = String.valueOf(text) + this.text.substring(n5);
                }
                if (this.field_175209_y.apply((Object)text)) {
                    this.text = text;
                    if (n3 != 0) {
                        this.moveCursorBy(n);
                    }
                    if (this.field_175210_x != null) {
                        this.field_175210_x.func_175319_a(this.id, this.text);
                    }
                }
            }
        }
    }
    
    public void setCursorPosition(final int cursorPosition) {
        this.cursorPosition = cursorPosition;
        this.setSelectionPos(this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, "".length(), this.text.length()));
    }
    
    public void setSelectionPos(int length) {
        final int length2 = this.text.length();
        if (length > length2) {
            length = length2;
        }
        if (length < 0) {
            length = "".length();
        }
        this.selectionEnd = length;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > length2) {
                this.lineScrollOffset = length2;
            }
            final int width = this.getWidth();
            final int n = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), width).length() + this.lineScrollOffset;
            if (length == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, width, " ".length() != 0).length();
            }
            if (length > n) {
                this.lineScrollOffset += length - n;
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else if (length <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - length;
            }
            this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, "".length(), length2);
        }
    }
    
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }
    
    public int getSelectionEnd() {
        return this.selectionEnd;
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
        if (n3 > this.xPosition + this.width) {
            n3 = this.xPosition + this.width;
        }
        if (n > this.xPosition + this.width) {
            n = this.xPosition + this.width;
        }
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(1067 + 3089 - 3042 + 4273);
        worldRenderer.begin(0x9C ^ 0x9B, DefaultVertexFormats.POSITION);
        worldRenderer.pos(n, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n2, 0.0).endVertex();
        worldRenderer.pos(n, n2, 0.0).endVertex();
        instance.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
    
    public void setTextColor(final int enabledColor) {
        this.enabledColor = enabledColor;
    }
    
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                Gui.drawRect(this.xPosition - " ".length(), this.yPosition - " ".length(), this.xPosition + this.width + " ".length(), this.yPosition + this.height + " ".length(), -(2097958 + 446457 - 1762871 + 5468792));
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -(5097073 + 16411775 - 21238168 + 16506536));
            }
            int n;
            if (this.isEnabled) {
                n = this.enabledColor;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = this.disabledColor;
            }
            final int n2 = n;
            final int n3 = this.cursorPosition - this.lineScrollOffset;
            int length = this.selectionEnd - this.lineScrollOffset;
            final String trimStringToWidth = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            int n4;
            if (n3 >= 0 && n3 <= trimStringToWidth.length()) {
                n4 = " ".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            final int n5 = n4;
            int n6;
            if (this.isFocused && this.cursorCounter / (0x2B ^ 0x2D) % "  ".length() == 0 && n5 != 0) {
                n6 = " ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n6 = "".length();
            }
            final int n7 = n6;
            int xPosition;
            if (this.enableBackgroundDrawing) {
                xPosition = this.xPosition + (0x5B ^ 0x5F);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                xPosition = this.xPosition;
            }
            final int n8 = xPosition;
            int yPosition;
            if (this.enableBackgroundDrawing) {
                yPosition = this.yPosition + (this.height - (0xB ^ 0x3)) / "  ".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                yPosition = this.yPosition;
            }
            final int n9 = yPosition;
            int drawStringWithShadow = n8;
            if (length > trimStringToWidth.length()) {
                length = trimStringToWidth.length();
            }
            if (trimStringToWidth.length() > 0) {
                String substring;
                if (n5 != 0) {
                    substring = trimStringToWidth.substring("".length(), n3);
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else {
                    substring = trimStringToWidth;
                }
                drawStringWithShadow = this.fontRendererInstance.drawStringWithShadow(substring, n8, n9, n2);
            }
            int n10;
            if (this.cursorPosition >= this.text.length() && this.text.length() < this.getMaxStringLength()) {
                n10 = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                n10 = " ".length();
            }
            final int n11 = n10;
            int n12 = drawStringWithShadow;
            if (n5 == 0) {
                int n13;
                if (n3 > 0) {
                    n13 = n8 + this.width;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n13 = n8;
                }
                n12 = n13;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else if (n11 != 0) {
                n12 = drawStringWithShadow - " ".length();
                --drawStringWithShadow;
            }
            if (trimStringToWidth.length() > 0 && n5 != 0 && n3 < trimStringToWidth.length()) {
                this.fontRendererInstance.drawStringWithShadow(trimStringToWidth.substring(n3), drawStringWithShadow, n9, n2);
            }
            if (n7 != 0) {
                if (n11 != 0) {
                    Gui.drawRect(n12, n9 - " ".length(), n12 + " ".length(), n9 + " ".length() + this.fontRendererInstance.FONT_HEIGHT, -(501847 + 1171870 - 1639095 + 3057650));
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                else {
                    this.fontRendererInstance.drawStringWithShadow(GuiTextField.I[0x10 ^ 0x16], n12, n9, n2);
                }
            }
            if (length != n3) {
                this.drawCursorVertical(n12, n9 - " ".length(), n8 + this.fontRendererInstance.getStringWidth(trimStringToWidth.substring("".length(), length)) - " ".length(), n9 + " ".length() + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }
    
    public void setMaxStringLength(final int maxStringLength) {
        this.maxStringLength = maxStringLength;
        if (this.text.length() > maxStringLength) {
            this.text = this.text.substring("".length(), maxStringLength);
        }
    }
    
    public void setDisabledTextColour(final int disabledColor) {
        this.disabledColor = disabledColor;
    }
    
    public void setCanLoseFocus(final boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }
    
    public int func_146197_a(final int n, final int n2, final boolean b) {
        int index = n2;
        int n3;
        if (n < 0) {
            n3 = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final int n4 = n3;
        final int abs = Math.abs(n);
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < abs) {
            if (n4 == 0) {
                final int length = this.text.length();
                index = this.text.indexOf(0xE2 ^ 0xC2, index);
                if (index == -" ".length()) {
                    index = length;
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else {
                    while (b && index < length) {
                        if (this.text.charAt(index) != (0x3E ^ 0x1E)) {
                            "".length();
                            if (2 < 1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++index;
                        }
                    }
                }
            }
            else {
                while (b && index > 0) {
                    if (this.text.charAt(index - " ".length()) != (0x91 ^ 0xB1)) {
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        --index;
                    }
                }
                while (index > 0 && this.text.charAt(index - " ".length()) != (0x35 ^ 0x15)) {
                    --index;
                }
            }
            ++i;
        }
        return index;
    }
    
    public void func_175207_a(final GuiPageButtonList.GuiResponder field_175210_x) {
        this.field_175210_x = field_175210_x;
    }
    
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }
    
    private static void I() {
        (I = new String[0x82 ^ 0x85])["".length()] = I("", "gTneU");
        GuiTextField.I[" ".length()] = I("", "tDWNo");
        GuiTextField.I["  ".length()] = I("", "AGjgJ");
        GuiTextField.I["   ".length()] = I("", "FMUpN");
        GuiTextField.I[0x82 ^ 0x86] = I("", "wkHYY");
        GuiTextField.I[0xA2 ^ 0xA7] = I("", "BpBvm");
        GuiTextField.I[0x91 ^ 0x97] = I("&", "ypFQm");
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean textboxKeyTyped(final char c, final int n) {
        if (!this.isFocused) {
            return "".length() != 0;
        }
        if (GuiScreen.isKeyComboCtrlA(n)) {
            this.setCursorPositionEnd();
            this.setSelectionPos("".length());
            return " ".length() != 0;
        }
        if (GuiScreen.isKeyComboCtrlC(n)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return " ".length() != 0;
        }
        if (GuiScreen.isKeyComboCtrlV(n)) {
            if (this.isEnabled) {
                this.writeText(GuiScreen.getClipboardString());
            }
            return " ".length() != 0;
        }
        if (GuiScreen.isKeyComboCtrlX(n)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText(GuiTextField.I[0x6 ^ 0x3]);
            }
            return " ".length() != 0;
        }
        switch (n) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(-" ".length());
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                }
                else if (this.isEnabled) {
                    this.deleteFromCursor(-" ".length());
                }
                return " ".length() != 0;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos("".length());
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else {
                    this.setCursorPositionZero();
                }
                return " ".length() != 0;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(-" ".length(), this.getSelectionEnd()));
                        "".length();
                        if (2 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() - " ".length());
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                    }
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-" ".length()));
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    this.moveCursorBy(-" ".length());
                }
                return " ".length() != 0;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(" ".length(), this.getSelectionEnd()));
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() + " ".length());
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                    }
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(" ".length()));
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    this.moveCursorBy(" ".length());
                }
                return " ".length() != 0;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(this.text.length());
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                }
                else {
                    this.setCursorPositionEnd();
                }
                return " ".length() != 0;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(" ".length());
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                }
                else if (this.isEnabled) {
                    this.deleteFromCursor(" ".length());
                }
                return " ".length() != 0;
            }
            default: {
                if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                    if (this.isEnabled) {
                        this.writeText(Character.toString(c));
                    }
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        }
    }
    
    public String getSelectedText() {
        int n;
        if (this.cursorPosition < this.selectionEnd) {
            n = this.cursorPosition;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n = this.selectionEnd;
        }
        final int n2 = n;
        int n3;
        if (this.cursorPosition < this.selectionEnd) {
            n3 = this.selectionEnd;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n3 = this.cursorPosition;
        }
        return this.text.substring(n2, n3);
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getNthWordFromCursor(final int n) {
        return this.getNthWordFromPos(n, this.getCursorPosition());
    }
    
    static {
        I();
    }
    
    public int getWidth() {
        int width;
        if (this.getEnableBackgroundDrawing()) {
            width = this.width - (0x1D ^ 0x15);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            width = this.width;
        }
        return width;
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        int n4;
        if (n >= this.xPosition && n < this.xPosition + this.width && n2 >= this.yPosition && n2 < this.yPosition + this.height) {
            n4 = " ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int focused = n4;
        if (this.canLoseFocus) {
            this.setFocused(focused != 0);
        }
        if (this.isFocused && focused && n3 == 0) {
            int n5 = n - this.xPosition;
            if (this.enableBackgroundDrawing) {
                n5 -= 4;
            }
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth()), n5).length() + this.lineScrollOffset);
        }
    }
    
    public void setCursorPositionZero() {
        this.setCursorPosition("".length());
    }
}
