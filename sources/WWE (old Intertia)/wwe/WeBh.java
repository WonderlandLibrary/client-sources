package wwe;

import wwe.utils.*;
import org.lwjgl.opengl.*;
import me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces.*;
import net.minecraft.client.gui.*;

public class WeBh extends GuiTextField
{
    public WeBh(final int n, final FontRenderer fontRenderer, final int n2, final int n3, final int n4, final int n5) {
        super(n, fontRenderer, n2, n3, n4, n5);
        this.setTextColor(-16777216);
    }
    
    public void func_146194_f() {
        if (this.getVisible()) {
            final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
            if (this.getEnableBackgroundDrawing()) {
                GL11.glPushMatrix();
                GL11.glScaled(1.0 / scaledResolution.getScaleFactor(), 1.0 / scaledResolution.getScaleFactor(), 1.0);
                gnNa.AvjN(this.x * scaledResolution.getScaleFactor(), this.y * scaledResolution.getScaleFactor(), (this.x + this.width) * scaledResolution.getScaleFactor(), (this.y + this.height) * scaledResolution.getScaleFactor(), 1, -4934476);
                GL11.glPopMatrix();
            }
            final InterfaceMixinGuiTextField interfaceMixinGuiTextField = (InterfaceMixinGuiTextField)this;
            if ((interfaceMixinGuiTextField.getText() == null || interfaceMixinGuiTextField.getText().isEmpty()) && !interfaceMixinGuiTextField.getIsFocused()) {
                GL11.glPushMatrix();
                final double n = 0.9;
                GL11.glScaled(n, n, 1.0);
                interfaceMixinGuiTextField.getFontRenderer().drawString("Search", (int)((this.x + 2) / n), (int)((this.y + 2) / n), -4934476);
                GL11.glPopMatrix();
            }
            int n2;
            if (interfaceMixinGuiTextField.getIsEnabled()) {
                n2 = interfaceMixinGuiTextField.getEnabledColor();
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                n2 = interfaceMixinGuiTextField.getDisabledColor();
            }
            final int n3 = n2;
            final int n4 = interfaceMixinGuiTextField.getCursorPosition() - interfaceMixinGuiTextField.getLineScrollOffset();
            int length = interfaceMixinGuiTextField.getSelectionEnd() - interfaceMixinGuiTextField.getLineScrollOffset();
            final String trimStringToWidth = interfaceMixinGuiTextField.getFontRenderer().trimStringToWidth(interfaceMixinGuiTextField.getText().substring(interfaceMixinGuiTextField.getLineScrollOffset()), this.getWidth());
            boolean b;
            if (n4 >= 0 && n4 <= trimStringToWidth.length()) {
                b = true;
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                b = false;
            }
            final boolean b2 = b;
            boolean b3;
            if (interfaceMixinGuiTextField.getIsFocused() && interfaceMixinGuiTextField.getCursorCounter() / 6 % 3 == 0 && b2) {
                b3 = true;
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                b3 = false;
            }
            final boolean b4 = b3;
            int x;
            if (interfaceMixinGuiTextField.getEnableBackgroundDrawing()) {
                x = this.x + 1;
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                x = this.x;
            }
            final int n5 = x;
            int y;
            if (interfaceMixinGuiTextField.getEnableBackgroundDrawing()) {
                y = this.y + (this.height - 8) / 2;
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                y = this.y;
            }
            final int n6 = y;
            int drawString = n5;
            if (length > trimStringToWidth.length()) {
                length = trimStringToWidth.length();
            }
            if (!trimStringToWidth.isEmpty()) {
                String substring;
                if (b2) {
                    substring = trimStringToWidth.substring(0, n4);
                    if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                        throw null;
                    }
                }
                else {
                    substring = trimStringToWidth;
                }
                drawString = interfaceMixinGuiTextField.getFontRenderer().drawString(substring, n5 + 1.0f, (float)n6, n3, false);
            }
            boolean b5;
            if (interfaceMixinGuiTextField.getCursorPosition() < interfaceMixinGuiTextField.getText().length() || interfaceMixinGuiTextField.getText().length() >= this.getMaxStringLength()) {
                b5 = true;
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                b5 = false;
            }
            final boolean b6 = b5;
            int n7;
            if (n4 > 0) {
                n7 = n5 + this.width;
                if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                    throw null;
                }
            }
            else {
                n7 = n5;
            }
            final int n8 = n7;
            if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                throw null;
            }
            if (!trimStringToWidth.isEmpty() && b2 && n4 < trimStringToWidth.length()) {
                interfaceMixinGuiTextField.getFontRenderer().drawString(trimStringToWidth.substring(n4), drawString + 1.0f, (float)n6, n3, false);
            }
            if (b4) {
                if (b6) {
                    Gui.drawRect(n8, n6 - 1, n8 + 1, n6 + 1 + interfaceMixinGuiTextField.getFontRenderer().FONT_HEIGHT, interfaceMixinGuiTextField.getEnabledColor());
                    if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                        throw null;
                    }
                }
                else {
                    interfaceMixinGuiTextField.getFontRenderer().drawString("|", (float)n8, n6 - 1.0f, n3, false);
                }
            }
            if (length != n4) {
                interfaceMixinGuiTextField.invokeDrawSelectionBox(n8, n6 - 1, n5 + interfaceMixinGuiTextField.getFontRenderer().getStringWidth(trimStringToWidth.substring(0, length)) - 1, n6 + 1 + interfaceMixinGuiTextField.getFontRenderer().FONT_HEIGHT);
            }
        }
    }
    
    public boolean func_146192_a(final int n, final int n2, final int n3) {
        boolean b;
        if (n >= this.x && n < this.x + this.width && n2 >= this.y && n2 < this.y + this.height) {
            b = true;
            if (WeBh.\u2005\u2005\u200c\u2001\u200a\u2001) {
                throw null;
            }
        }
        else {
            b = false;
        }
        final boolean focused = b;
        final InterfaceMixinGuiTextField interfaceMixinGuiTextField = (InterfaceMixinGuiTextField)this;
        if (interfaceMixinGuiTextField.getCanLoseFocus()) {
            this.setFocused(focused);
        }
        if (interfaceMixinGuiTextField.getIsFocused() && focused) {
            int n4 = n - this.x;
            if (interfaceMixinGuiTextField.getEnableBackgroundDrawing()) {
                --n4;
            }
            this.setCursorPosition(interfaceMixinGuiTextField.getFontRenderer().trimStringToWidth(interfaceMixinGuiTextField.getFontRenderer().trimStringToWidth(interfaceMixinGuiTextField.getText().substring(interfaceMixinGuiTextField.getLineScrollOffset()), this.getWidth()), n4).length() + interfaceMixinGuiTextField.getLineScrollOffset());
            return true;
        }
        return false;
    }
}
