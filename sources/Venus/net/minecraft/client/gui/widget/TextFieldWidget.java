/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.KawaseBlur;
import mpp.venusfr.utils.render.Stencil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class TextFieldWidget
extends Widget
implements IRenderable,
IGuiEventListener {
    private final FontRenderer fontRenderer;
    private String text = "";
    private int maxStringLength = 32;
    private int cursorCounter;
    private boolean enableBackgroundDrawing = true;
    private boolean canLoseFocus = true;
    private boolean isEnabled = true;
    private boolean field_212956_h;
    private int lineScrollOffset;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor = 0xE0E0E0;
    private int disabledColor = 0x707070;
    private String suggestion;
    private Consumer<String> guiResponder;
    private Predicate<String> validator = Objects::nonNull;
    private BiFunction<String, Integer, IReorderingProcessor> textFormatter = TextFieldWidget::lambda$new$0;
    String[] words = new String[]{"reg", "register", "l", "login", "call", "tpa", "warp"};

    public TextFieldWidget(FontRenderer fontRenderer, int n, int n2, int n3, int n4, ITextComponent iTextComponent) {
        this(fontRenderer, n, n2, n3, n4, null, iTextComponent);
    }

    public TextFieldWidget(FontRenderer fontRenderer, int n, int n2, int n3, int n4, @Nullable TextFieldWidget textFieldWidget, ITextComponent iTextComponent) {
        super(n, n2, n3, n4, iTextComponent);
        this.fontRenderer = fontRenderer;
        if (textFieldWidget != null) {
            this.setText(textFieldWidget.getText());
        }
    }

    public void setResponder(Consumer<String> consumer) {
        this.guiResponder = consumer;
    }

    public void setTextFormatter(BiFunction<String, Integer, IReorderingProcessor> biFunction) {
        this.textFormatter = biFunction;
    }

    public void tick() {
        ++this.cursorCounter;
    }

    @Override
    protected IFormattableTextComponent getNarrationMessage() {
        ITextComponent iTextComponent = this.getMessage();
        return new TranslationTextComponent("gui.narrate.editBox", iTextComponent, this.text);
    }

    public void setText(String string) {
        if (this.validator.test(string)) {
            this.text = string.length() > this.maxStringLength ? string.substring(0, this.maxStringLength) : string;
            this.setCursorPositionEnd();
            this.setSelectionPos(this.cursorPosition);
            this.onTextChanged(string);
        }
    }

    public String getText() {
        return this.text;
    }

    public String getSelectedText() {
        int n = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int n2 = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(n, n2);
    }

    public void setValidator(Predicate<String> predicate) {
        this.validator = predicate;
    }

    public void writeText(String string) {
        String string2;
        String string3;
        int n;
        int n2 = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int n3 = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int n4 = this.maxStringLength - this.text.length() - (n2 - n3);
        if (n4 < (n = (string3 = SharedConstants.filterAllowedCharacters(string)).length())) {
            string3 = string3.substring(0, n4);
            n = n4;
        }
        if (this.validator.test(string2 = new StringBuilder(this.text).replace(n2, n3, string3).toString())) {
            this.text = string2;
            this.clampCursorPosition(n2 + n);
            this.setSelectionPos(this.cursorPosition);
            this.onTextChanged(this.text);
        }
    }

    private void onTextChanged(String string) {
        if (this.guiResponder != null) {
            this.guiResponder.accept(string);
        }
        this.nextNarration = Util.milliTime() + 500L;
    }

    private void delete(int n) {
        if (Screen.hasControlDown()) {
            this.deleteWords(n);
        } else {
            this.deleteFromCursor(n);
        }
    }

    public void deleteWords(int n) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursorPosition);
            }
        }
    }

    public void deleteFromCursor(int n) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                String string;
                int n2;
                int n3 = this.func_238516_r_(n);
                int n4 = Math.min(n3, this.cursorPosition);
                if (n4 != (n2 = Math.max(n3, this.cursorPosition)) && this.validator.test(string = new StringBuilder(this.text).delete(n4, n2).toString())) {
                    this.text = string;
                    this.setCursorPosition(n4);
                }
            }
        }
    }

    public int getNthWordFromCursor(int n) {
        return this.getNthWordFromPos(n, this.getCursorPosition());
    }

    private int getNthWordFromPos(int n, int n2) {
        return this.getNthWordFromPosWS(n, n2, false);
    }

    private int getNthWordFromPosWS(int n, int n2, boolean bl) {
        int n3 = n2;
        boolean bl2 = n < 0;
        int n4 = Math.abs(n);
        for (int i = 0; i < n4; ++i) {
            if (!bl2) {
                int n5 = this.text.length();
                if ((n3 = this.text.indexOf(32, n3)) == -1) {
                    n3 = n5;
                    continue;
                }
                while (bl && n3 < n5 && this.text.charAt(n3) == ' ') {
                    ++n3;
                }
                continue;
            }
            while (bl && n3 > 0 && this.text.charAt(n3 - 1) == ' ') {
                --n3;
            }
            while (n3 > 0 && this.text.charAt(n3 - 1) != ' ') {
                --n3;
            }
        }
        return n3;
    }

    public void moveCursorBy(int n) {
        this.setCursorPosition(this.func_238516_r_(n));
    }

    private int func_238516_r_(int n) {
        return Util.func_240980_a_(this.text, this.cursorPosition, n);
    }

    public void setCursorPosition(int n) {
        this.clampCursorPosition(n);
        if (!this.field_212956_h) {
            this.setSelectionPos(this.cursorPosition);
        }
        this.onTextChanged(this.text);
    }

    public void clampCursorPosition(int n) {
        this.cursorPosition = MathHelper.clamp(n, 0, this.text.length());
    }

    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (!this.canWrite()) {
            return true;
        }
        this.field_212956_h = Screen.hasShiftDown();
        if (Screen.isSelectAll(n)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return false;
        }
        if (Screen.isCopy(n)) {
            Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
            return false;
        }
        if (Screen.isPaste(n)) {
            if (this.isEnabled) {
                this.writeText(Minecraft.getInstance().keyboardListener.getClipboardString());
            }
            return false;
        }
        if (Screen.isCut(n)) {
            Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText("");
            }
            return false;
        }
        switch (n) {
            case 259: {
                if (this.isEnabled) {
                    this.field_212956_h = false;
                    this.delete(-1);
                    this.field_212956_h = Screen.hasShiftDown();
                }
                return false;
            }
            default: {
                return true;
            }
            case 261: {
                if (this.isEnabled) {
                    this.field_212956_h = false;
                    this.delete(1);
                    this.field_212956_h = Screen.hasShiftDown();
                }
                return false;
            }
            case 262: {
                if (Screen.hasControlDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                } else {
                    this.moveCursorBy(1);
                }
                return false;
            }
            case 263: {
                if (Screen.hasControlDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                } else {
                    this.moveCursorBy(-1);
                }
                return false;
            }
            case 268: {
                this.setCursorPositionZero();
                return false;
            }
            case 269: 
        }
        this.setCursorPositionEnd();
        return false;
    }

    public boolean canWrite() {
        return this.getVisible() && this.isFocused() && this.isEnabled();
    }

    @Override
    public boolean charTyped(char c, int n) {
        if (!this.canWrite()) {
            return true;
        }
        if (SharedConstants.isAllowedCharacter(c)) {
            if (this.isEnabled) {
                this.writeText(Character.toString(c));
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        boolean bl;
        if (!this.getVisible()) {
            return true;
        }
        boolean bl2 = bl = d >= (double)this.x && d < (double)(this.x + this.width) && d2 >= (double)this.y && d2 < (double)(this.y + this.height);
        if (this.canLoseFocus) {
            this.setFocused2(bl);
        }
        if (this.isFocused() && bl && n == 0) {
            int n2 = MathHelper.floor(d) - this.x;
            if (this.enableBackgroundDrawing) {
                n2 -= 4;
            }
            String string = this.fontRenderer.func_238412_a_(this.text.substring(this.lineScrollOffset), this.getAdjustedWidth());
            this.setCursorPosition(this.fontRenderer.func_238412_a_(string, n2).length() + this.lineScrollOffset);
            return false;
        }
        return true;
    }

    public void setFocused2(boolean bl) {
        super.setFocused(bl);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.getVisible()) {
            int n3;
            int n4;
            if (this.getEnableBackgroundDrawing()) {
                n4 = this.isFocused() ? -1 : -6250336;
                TextFieldWidget.fill(matrixStack, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, n4);
                TextFieldWidget.fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
            }
            n4 = this.isEnabled ? this.enabledColor : this.disabledColor;
            int n5 = this.cursorPosition - this.lineScrollOffset;
            int n6 = this.selectionEnd - this.lineScrollOffset;
            String string = this.fontRenderer.func_238412_a_(this.text.substring(this.lineScrollOffset), this.getAdjustedWidth());
            boolean bl = n5 >= 0 && n5 <= string.length();
            boolean bl2 = this.isFocused() && this.cursorCounter / 6 % 2 == 0 && bl;
            int n7 = this.enableBackgroundDrawing ? this.x + 4 : this.x;
            int n8 = this.enableBackgroundDrawing ? this.y + (this.height - 8) / 2 : this.y;
            int n9 = n7;
            if (n6 > string.length()) {
                n6 = string.length();
            }
            if (!string.isEmpty()) {
                String string2 = bl ? string.substring(0, n5) : string;
                n9 = this.fontRenderer.func_238407_a_(matrixStack, this.textFormatter.apply(string2, this.lineScrollOffset), n7, n8, n4);
                n3 = 0;
                for (String string3 : this.words) {
                    String string4 = "/" + string3;
                    if (!string2.toLowerCase().startsWith(string4)) continue;
                    n3 = 1;
                    break;
                }
                if (n3 != 0 && ChatScreen.hide) {
                    MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
                    KawaseBlur.blur.updateBlur(3.0f, 3);
                    Stencil.initStencilToWrite();
                    DisplayUtils.drawRectW(2.0, mainWindow.getScaledHeight() - 14, this.fontRenderer.getStringWidth(string2) + 3, 12.0, -1);
                    Stencil.readStencilBuffer(1);
                    KawaseBlur.blur.BLURRED.draw();
                    Stencil.uninitStencilBuffer();
                }
            }
            boolean bl3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            n3 = n9;
            if (!bl) {
                n3 = n5 > 0 ? n7 + this.width : n7;
            } else if (bl3) {
                n3 = n9 - 1;
                --n9;
            }
            if (!string.isEmpty() && bl && n5 < string.length()) {
                this.fontRenderer.func_238407_a_(matrixStack, this.textFormatter.apply(string.substring(n5), this.cursorPosition), n9, n8, n4);
            }
            if (!bl3 && this.suggestion != null) {
                this.fontRenderer.drawStringWithShadow(matrixStack, this.suggestion, n3 - 1, n8, -8355712);
            }
            if (bl2) {
                if (bl3) {
                    AbstractGui.fill(matrixStack, n3, n8 - 1, n3 + 1, n8 + 1 + 9, -3092272);
                } else {
                    this.fontRenderer.drawStringWithShadow(matrixStack, "_", n3, n8, n4);
                }
            }
            if (n6 != n5) {
                int n10 = n7 + this.fontRenderer.getStringWidth(string.substring(0, n6));
                this.drawSelectionBox(n3, n8 - 1, n10 - 1, n8 + 1 + 9);
            }
        }
    }

    private void drawSelectionBox(int n, int n2, int n3, int n4) {
        int n5;
        if (n < n3) {
            n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        if (n3 > this.x + this.width) {
            n3 = this.x + this.width;
        }
        if (n > this.x + this.width) {
            n = this.x + this.width;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.color4f(0.0f, 0.0f, 255.0f, 255.0f);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(n, n4, 0.0).endVertex();
        bufferBuilder.pos(n3, n4, 0.0).endVertex();
        bufferBuilder.pos(n3, n2, 0.0).endVertex();
        bufferBuilder.pos(n, n2, 0.0).endVertex();
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    public void setMaxStringLength(int n) {
        this.maxStringLength = n;
        if (this.text.length() > n) {
            this.text = this.text.substring(0, n);
            this.onTextChanged(this.text);
        }
    }

    private int getMaxStringLength() {
        return this.maxStringLength;
    }

    public int getCursorPosition() {
        return this.cursorPosition;
    }

    private boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    public void setEnableBackgroundDrawing(boolean bl) {
        this.enableBackgroundDrawing = bl;
    }

    public void setTextColor(int n) {
        this.enabledColor = n;
    }

    public void setDisabledTextColour(int n) {
        this.disabledColor = n;
    }

    @Override
    public boolean changeFocus(boolean bl) {
        return this.visible && this.isEnabled ? super.changeFocus(bl) : false;
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return this.visible && d >= (double)this.x && d < (double)(this.x + this.width) && d2 >= (double)this.y && d2 < (double)(this.y + this.height);
    }

    @Override
    protected void onFocusedChanged(boolean bl) {
        if (bl) {
            this.cursorCounter = 0;
        }
    }

    private boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean bl) {
        this.isEnabled = bl;
    }

    public int getAdjustedWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    public void setSelectionPos(int n) {
        int n2 = this.text.length();
        this.selectionEnd = MathHelper.clamp(n, 0, n2);
        if (this.fontRenderer != null) {
            if (this.lineScrollOffset > n2) {
                this.lineScrollOffset = n2;
            }
            int n3 = this.getAdjustedWidth();
            String string = this.fontRenderer.func_238412_a_(this.text.substring(this.lineScrollOffset), n3);
            int n4 = string.length() + this.lineScrollOffset;
            if (this.selectionEnd == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRenderer.func_238413_a_(this.text, n3, false).length();
            }
            if (this.selectionEnd > n4) {
                this.lineScrollOffset += this.selectionEnd - n4;
            } else if (this.selectionEnd <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - this.selectionEnd;
            }
            this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, n2);
        }
    }

    public void setCanLoseFocus(boolean bl) {
        this.canLoseFocus = bl;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    public void setSuggestion(@Nullable String string) {
        this.suggestion = string;
    }

    public int func_195611_j(int n) {
        return n > this.text.length() ? this.x : this.x + this.fontRenderer.getStringWidth(this.text.substring(0, n));
    }

    public void setX(int n) {
        this.x = n;
    }

    private static IReorderingProcessor lambda$new$0(String string, Integer n) {
        return IReorderingProcessor.fromString(string, Style.EMPTY);
    }
}

