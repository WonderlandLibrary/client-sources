// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import com.google.common.base.Predicates;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.client.gui.GuiScreen;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.minecraft.client.gui.FontRenderer;
import com.google.common.base.Predicate;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;

@SideOnly(Side.CLIENT)
public class GuiTextField extends Gui
{
    private /* synthetic */ GuiPageButtonList.GuiResponder guiResponder;
    private static final /* synthetic */ String[] lIIl;
    private /* synthetic */ int enabledColor;
    public /* synthetic */ int y;
    private static final /* synthetic */ int[] lIll;
    private /* synthetic */ int cursorPosition;
    private /* synthetic */ Predicate<String> validator;
    private /* synthetic */ boolean isEnabled;
    public /* synthetic */ int width;
    private /* synthetic */ boolean isFocused;
    private /* synthetic */ String text;
    private /* synthetic */ boolean enableBackgroundDrawing;
    private final /* synthetic */ int id;
    private /* synthetic */ int cursorCounter;
    public /* synthetic */ int height;
    private final /* synthetic */ FontRenderer fontRenderer;
    private /* synthetic */ boolean visible;
    private /* synthetic */ int disabledColor;
    private /* synthetic */ int selectionEnd;
    public /* synthetic */ int x;
    private /* synthetic */ int maxStringLength;
    private /* synthetic */ boolean canLoseFocus;
    private /* synthetic */ int lineScrollOffset;
    
    public void setGuiResponder(final GuiPageButtonList.GuiResponder llllllIIIIIlIIl) {
        this.guiResponder = llllllIIIIIlIIl;
    }
    
    private static boolean lIIllI(final int lllllIIIlllIlll) {
        return lllllIIIlllIlll == 0;
    }
    
    private static boolean lIllII(final int lllllIIIlllIIIl) {
        return lllllIIIlllIIIl > 0;
    }
    
    public void setDisabledTextColour(final int lllllIIlllIlllI) {
        this.disabledColor = lllllIIlllIlllI;
    }
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    private static String lIllI(String lllllIIlIlIIllI, final String lllllIIlIlIlIlI) {
        lllllIIlIlIIllI = (byte)new String(Base64.getDecoder().decode(((String)lllllIIlIlIIllI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lllllIIlIlIlIIl = new StringBuilder();
        final char[] lllllIIlIlIlIII = lllllIIlIlIlIlI.toCharArray();
        int lllllIIlIlIIlll = GuiTextField.lIll[0];
        final float lllllIIlIlIIIIl = (Object)((String)lllllIIlIlIIllI).toCharArray();
        final char lllllIIlIlIIIII = (char)lllllIIlIlIIIIl.length;
        int lllllIIlIIlllll = GuiTextField.lIll[0];
        while (lIIlIl(lllllIIlIIlllll, lllllIIlIlIIIII)) {
            final char lllllIIlIlIllII = lllllIIlIlIIIIl[lllllIIlIIlllll];
            lllllIIlIlIlIIl.append((char)(lllllIIlIlIllII ^ lllllIIlIlIlIII[lllllIIlIlIIlll % lllllIIlIlIlIII.length]));
            "".length();
            ++lllllIIlIlIIlll;
            ++lllllIIlIIlllll;
            "".length();
            if (-(0xB0 ^ 0xB4) > 0) {
                return null;
            }
        }
        return String.valueOf(lllllIIlIlIlIIl);
    }
    
    public boolean mouseClicked(final int lllllIlIlIlllII, final int lllllIlIlIllIll, final int lllllIlIlIllIlI) {
        int n;
        if (lIllIl(lllllIlIlIlllII, this.x) && lIIlIl(lllllIlIlIlllII, this.x + this.width) && lIllIl(lllllIlIlIllIll, this.y) && lIIlIl(lllllIlIlIllIll, this.y + this.height)) {
            n = GuiTextField.lIll[2];
            "".length();
            if (null != null) {
                return ((0xDA ^ 0xC6 ^ (0xC3 ^ 0x93)) & (0x23 ^ 0x58 ^ (0x3B ^ 0xC) ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = GuiTextField.lIll[0];
        }
        final boolean lllllIlIlIllIIl = n != 0;
        if (lIIIlI(this.canLoseFocus ? 1 : 0)) {
            this.setFocused(lllllIlIlIllIIl);
        }
        if (lIIIlI(this.isFocused ? 1 : 0) && lIIIlI(lllllIlIlIllIIl ? 1 : 0) && lIIllI(lllllIlIlIllIlI)) {
            int lllllIlIlIlllll = lllllIlIlIlllII - this.x;
            if (lIIIlI(this.enableBackgroundDrawing ? 1 : 0)) {
                lllllIlIlIlllll -= 4;
            }
            final String lllllIlIlIllllI = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(this.fontRenderer.trimStringToWidth(lllllIlIlIllllI, lllllIlIlIlllll).length() + this.lineScrollOffset);
            return GuiTextField.lIll[2] != 0;
        }
        return GuiTextField.lIll[0] != 0;
    }
    
    public int getCursorPosition() {
        return this.cursorPosition;
    }
    
    private static boolean lIlIIl(final int lllllIIIlllIIll) {
        return lllllIIIlllIIll < 0;
    }
    
    public void setEnabled(final boolean lllllIIllIlllIl) {
        this.isEnabled = lllllIIllIlllIl;
    }
    
    public void setCanLoseFocus(final boolean lllllIIllIIIIIl) {
        this.canLoseFocus = lllllIIllIIIIIl;
    }
    
    public boolean textboxKeyTyped(final char lllllIlIllIlIII, final int lllllIlIllIlIlI) {
        if (lIIllI(this.isFocused ? 1 : 0)) {
            return GuiTextField.lIll[0] != 0;
        }
        if (lIIIlI(GuiScreen.isKeyComboCtrlA(lllllIlIllIlIlI) ? 1 : 0)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(GuiTextField.lIll[0]);
            return GuiTextField.lIll[2] != 0;
        }
        if (lIIIlI(GuiScreen.isKeyComboCtrlC(lllllIlIllIlIlI) ? 1 : 0)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return GuiTextField.lIll[2] != 0;
        }
        if (lIIIlI(GuiScreen.isKeyComboCtrlV(lllllIlIllIlIlI) ? 1 : 0)) {
            if (lIIIlI(this.isEnabled ? 1 : 0)) {
                this.writeText(GuiScreen.getClipboardString());
            }
            return GuiTextField.lIll[2] != 0;
        }
        if (lIIIlI(GuiScreen.isKeyComboCtrlX(lllllIlIllIlIlI) ? 1 : 0)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            if (lIIIlI(this.isEnabled ? 1 : 0)) {
                this.writeText(GuiTextField.lIIl[GuiTextField.lIll[9]]);
            }
            return GuiTextField.lIll[2] != 0;
        }
        switch (lllllIlIllIlIlI) {
            case 14: {
                if (lIIIlI(GuiScreen.isCtrlKeyDown() ? 1 : 0)) {
                    if (lIIIlI(this.isEnabled ? 1 : 0)) {
                        this.deleteWords(GuiTextField.lIll[8]);
                        "".length();
                        if (-" ".length() > "  ".length()) {
                            return ((0xFB ^ 0xB8 ^ (0x73 ^ 0x34)) & (44 + 75 + 18 + 1 ^ 35 + 70 + 17 + 20 ^ -" ".length())) != 0x0;
                        }
                    }
                }
                else if (lIIIlI(this.isEnabled ? 1 : 0)) {
                    this.deleteFromCursor(GuiTextField.lIll[8]);
                }
                return GuiTextField.lIll[2] != 0;
            }
            case 199: {
                if (lIIIlI(GuiScreen.isShiftKeyDown() ? 1 : 0)) {
                    this.setSelectionPos(GuiTextField.lIll[0]);
                    "".length();
                    if ("   ".length() != "   ".length()) {
                        return ((0x50 ^ 0x4B ^ (0x47 ^ 0x3F)) & (0x80 ^ 0xB0 ^ (0xF8 ^ 0xAB) ^ -" ".length())) != 0x0;
                    }
                }
                else {
                    this.setCursorPositionZero();
                }
                return GuiTextField.lIll[2] != 0;
            }
            case 203: {
                if (lIIIlI(GuiScreen.isShiftKeyDown() ? 1 : 0)) {
                    if (lIIIlI(GuiScreen.isCtrlKeyDown() ? 1 : 0)) {
                        this.setSelectionPos(this.getNthWordFromPos(GuiTextField.lIll[8], this.getSelectionEnd()));
                        "".length();
                        if (null != null) {
                            return ((141 + 110 - 79 + 4 ^ 7 + 116 + 21 + 10) & (21 + 83 - 50 + 86 ^ 142 + 154 - 283 + 153 ^ -" ".length())) != 0x0;
                        }
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() - GuiTextField.lIll[2]);
                        "".length();
                        if ((48 + 103 - 108 + 93 ^ 30 + 38 - 9 + 81) != (0x5A ^ 0x51 ^ (0xB ^ 0x4))) {
                            return ((30 + 31 - 17 + 90 ^ 111 + 133 - 235 + 134) & (0xD4 ^ 0xC6 ^ (0xA9 ^ 0xB2) ^ -" ".length())) != 0x0;
                        }
                    }
                }
                else if (lIIIlI(GuiScreen.isCtrlKeyDown() ? 1 : 0)) {
                    this.setCursorPosition(this.getNthWordFromCursor(GuiTextField.lIll[8]));
                    "".length();
                    if (null != null) {
                        return ((0xF3 ^ 0xAC) & ~(0xE5 ^ 0xBA)) != 0x0;
                    }
                }
                else {
                    this.moveCursorBy(GuiTextField.lIll[8]);
                }
                return GuiTextField.lIll[2] != 0;
            }
            case 205: {
                if (lIIIlI(GuiScreen.isShiftKeyDown() ? 1 : 0)) {
                    if (lIIIlI(GuiScreen.isCtrlKeyDown() ? 1 : 0)) {
                        this.setSelectionPos(this.getNthWordFromPos(GuiTextField.lIll[2], this.getSelectionEnd()));
                        "".length();
                        if (-" ".length() > 0) {
                            return (" ".length() & (" ".length() ^ -" ".length())) != 0x0;
                        }
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() + GuiTextField.lIll[2]);
                        "".length();
                        if (-" ".length() > "  ".length()) {
                            return ((0x27 ^ 0x3A) & ~(0x5E ^ 0x43)) != 0x0;
                        }
                    }
                }
                else if (lIIIlI(GuiScreen.isCtrlKeyDown() ? 1 : 0)) {
                    this.setCursorPosition(this.getNthWordFromCursor(GuiTextField.lIll[2]));
                    "".length();
                    if (-(158 + 85 - 68 + 3 ^ 39 + 46 - 79 + 176) >= 0) {
                        return ((0x7D ^ 0x4 ^ (0xE1 ^ 0x8B)) & (0xD2 ^ 0xAB ^ (0x32 ^ 0x58) ^ -" ".length())) != 0x0;
                    }
                }
                else {
                    this.moveCursorBy(GuiTextField.lIll[2]);
                }
                return GuiTextField.lIll[2] != 0;
            }
            case 207: {
                if (lIIIlI(GuiScreen.isShiftKeyDown() ? 1 : 0)) {
                    this.setSelectionPos(this.text.length());
                    "".length();
                    if (((0xA ^ 0x7C ^ (0x65 ^ 0x1D)) & (0xF7 ^ 0x9F ^ (0x54 ^ 0x32) ^ -" ".length())) != ((0x43 ^ 0x30 ^ (0x25 ^ 0x19)) & (0x59 ^ 0x18 ^ (0x71 ^ 0x7F) ^ -" ".length()))) {
                        return ((0xBA ^ 0x89 ^ (0xE7 ^ 0xB4)) & (181 + 120 - 286 + 220 ^ 13 + 3 + 16 + 107 ^ -" ".length())) != 0x0;
                    }
                }
                else {
                    this.setCursorPositionEnd();
                }
                return GuiTextField.lIll[2] != 0;
            }
            case 211: {
                if (lIIIlI(GuiScreen.isCtrlKeyDown() ? 1 : 0)) {
                    if (lIIIlI(this.isEnabled ? 1 : 0)) {
                        this.deleteWords(GuiTextField.lIll[2]);
                        "".length();
                        if (((0x4F ^ 0x11) & ~(0x9F ^ 0xC1) & ~((0x3E ^ 0x32) & ~(0x48 ^ 0x44))) != 0x0) {
                            return ((0x16 ^ 0x27) & ~(0x3C ^ 0xD)) != 0x0;
                        }
                    }
                }
                else if (lIIIlI(this.isEnabled ? 1 : 0)) {
                    this.deleteFromCursor(GuiTextField.lIll[2]);
                }
                return GuiTextField.lIll[2] != 0;
            }
            default: {
                if (lIIIlI(ChatAllowedCharacters.isAllowedCharacter(lllllIlIllIlIII) ? 1 : 0)) {
                    if (lIIIlI(this.isEnabled ? 1 : 0)) {
                        this.writeText(Character.toString(lllllIlIllIlIII));
                    }
                    return GuiTextField.lIll[2] != 0;
                }
                return GuiTextField.lIll[0] != 0;
            }
        }
    }
    
    public void setValidator(final Predicate<String> lllllIllllIllII) {
        this.validator = lllllIllllIllII;
    }
    
    public void setFocused(final boolean lllllIIlllIIllI) {
        if (lIIIlI(lllllIIlllIIllI ? 1 : 0) && lIIllI(this.isFocused ? 1 : 0)) {
            this.cursorCounter = GuiTextField.lIll[0];
        }
        this.isFocused = lllllIIlllIIllI;
        if (lIIlll(Minecraft.getMinecraft().currentScreen)) {
            Minecraft.getMinecraft().currentScreen.setFocused(lllllIIlllIIllI);
        }
    }
    
    public void setSelectionPos(int lllllIIllIIlIIl) {
        final int lllllIIllIIlIll = this.text.length();
        if (lIIlII(lllllIIllIIlIIl, lllllIIllIIlIll)) {
            lllllIIllIIlIIl = lllllIIllIIlIll;
        }
        if (lIlIIl(lllllIIllIIlIIl)) {
            lllllIIllIIlIIl = GuiTextField.lIll[0];
        }
        this.selectionEnd = lllllIIllIIlIIl;
        if (lIIlll(this.fontRenderer)) {
            if (lIIlII(this.lineScrollOffset, lllllIIllIIlIll)) {
                this.lineScrollOffset = lllllIIllIIlIll;
            }
            final int lllllIIllIlIIII = this.getWidth();
            final String lllllIIllIIllll = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), lllllIIllIlIIII);
            final int lllllIIllIIlllI = lllllIIllIIllll.length() + this.lineScrollOffset;
            if (lIlIll(lllllIIllIIlIIl, this.lineScrollOffset)) {
                this.lineScrollOffset -= this.fontRenderer.trimStringToWidth(this.text, lllllIIllIlIIII, (boolean)(GuiTextField.lIll[2] != 0)).length();
            }
            if (lIIlII(lllllIIllIIlIIl, lllllIIllIIlllI)) {
                this.lineScrollOffset += lllllIIllIIlIIl - lllllIIllIIlllI;
                "".length();
                if ("  ".length() < -" ".length()) {
                    return;
                }
            }
            else if (lIlllI(lllllIIllIIlIIl, this.lineScrollOffset)) {
                this.lineScrollOffset -= this.lineScrollOffset - lllllIIllIIlIIl;
            }
            this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, GuiTextField.lIll[0], lllllIIllIIlIll);
        }
    }
    
    public void setTextColor(final int lllllIIllllIIlI) {
        this.enabledColor = lllllIIllllIIlI;
    }
    
    private static boolean lIlIII(final int lllllIIIllIlllI, final int lllllIIIllIllIl) {
        return lllllIIIllIlllI != lllllIIIllIllIl;
    }
    
    public void setCursorPosition(final int lllllIlIllllIlI) {
        this.cursorPosition = lllllIlIllllIlI;
        final int lllllIlIllllIIl = this.text.length();
        this.cursorPosition = MathHelper.clamp(this.cursorPosition, GuiTextField.lIll[0], lllllIlIllllIIl);
        this.setSelectionPos(this.cursorPosition);
    }
    
    private static boolean lIllIl(final int lllllIIlIIIlIlI, final int lllllIIlIIIlIIl) {
        return lllllIIlIIIlIlI >= lllllIIlIIIlIIl;
    }
    
    public int getNthWordFromPosWS(final int lllllIllIIIllII, final int lllllIllIIlIIlI, final boolean lllllIllIIIlIlI) {
        int lllllIllIIlIIII = lllllIllIIlIIlI;
        int n;
        if (lIlIIl(lllllIllIIIllII)) {
            n = GuiTextField.lIll[2];
            "".length();
            if (-" ".length() != -" ".length()) {
                return (0x6F ^ 0x21 ^ (0xCC ^ 0xB5)) & (230 + 73 - 219 + 162 ^ 10 + 82 + 15 + 86 ^ -" ".length());
            }
        }
        else {
            n = GuiTextField.lIll[0];
        }
        final boolean lllllIllIIIllll = n != 0;
        final int lllllIllIIIlllI = Math.abs(lllllIllIIIllII);
        int lllllIllIIlIlIl = GuiTextField.lIll[0];
        while (lIIlIl(lllllIllIIlIlIl, lllllIllIIIlllI)) {
            if (lIIllI(lllllIllIIIllll ? 1 : 0)) {
                final int lllllIllIIlIllI = this.text.length();
                lllllIllIIlIIII = this.text.indexOf(GuiTextField.lIll[1], lllllIllIIlIIII);
                if (lIlIll(lllllIllIIlIIII, GuiTextField.lIll[8])) {
                    lllllIllIIlIIII = lllllIllIIlIllI;
                    "".length();
                    if (null != null) {
                        return (0xBB ^ 0xBC) & ~(0xBC ^ 0xBB);
                    }
                }
                else {
                    while (lIIIlI(lllllIllIIIlIlI ? 1 : 0) && lIIlIl(lllllIllIIlIIII, lllllIllIIlIllI) && lIlIll(this.text.charAt(lllllIllIIlIIII), GuiTextField.lIll[1])) {
                        ++lllllIllIIlIIII;
                        "".length();
                        if (((0xB0 ^ 0x87 ^ (0x3E ^ 0x19)) & (0xF ^ 0x3F ^ (0x5B ^ 0x7B) ^ -" ".length())) != 0x0) {
                            return (0x6 ^ 0x4E ^ (0x71 ^ 0x69)) & (220 + 118 - 85 + 1 ^ 7 + 98 - 37 + 106 ^ -" ".length());
                        }
                    }
                }
                "".length();
                if ("   ".length() <= 0) {
                    return (0x65 ^ 0x30 ^ (0xD4 ^ 0xA1)) & (0xD5 ^ 0xB3 ^ (0x11 ^ 0x57) ^ -" ".length());
                }
            }
            else {
                while (lIIIlI(lllllIllIIIlIlI ? 1 : 0) && lIllII(lllllIllIIlIIII) && lIlIll(this.text.charAt(lllllIllIIlIIII - GuiTextField.lIll[2]), GuiTextField.lIll[1])) {
                    --lllllIllIIlIIII;
                    "".length();
                    if ("  ".length() < -" ".length()) {
                        return (0xC ^ 0x24 ^ (0x4B ^ 0x7F)) & (136 + 58 - 90 + 57 ^ 175 + 168 - 336 + 182 ^ -" ".length());
                    }
                }
                while (lIllII(lllllIllIIlIIII) && lIlIII(this.text.charAt(lllllIllIIlIIII - GuiTextField.lIll[2]), GuiTextField.lIll[1])) {
                    --lllllIllIIlIIII;
                    "".length();
                    if (((0xE ^ 0x3F) & ~(0x22 ^ 0x13)) != 0x0) {
                        return (0xBE ^ 0xB6) & ~(0x47 ^ 0x4F);
                    }
                }
            }
            ++lllllIllIIlIlIl;
            "".length();
            if (null != null) {
                return (0xB8 ^ 0x9E ^ (0x70 ^ 0x1E)) & (0x40 ^ 0x79 ^ (0x20 ^ 0x51) ^ -" ".length());
            }
        }
        return lllllIllIIlIIII;
    }
    
    private static boolean lIIlIl(final int lllllIIlIIIIllI, final int lllllIIlIIIIlIl) {
        return lllllIIlIIIIllI < lllllIIlIIIIlIl;
    }
    
    public void drawTextBox(final int lllllIlIIllIIIl, final int lllllIlIIllIIll) {
        if (lIIIlI(this.getVisible() ? 1 : 0)) {
            if (lIIIlI(this.getEnableBackgroundDrawing() ? 1 : 0)) {
                drawRect(this.x - GuiTextField.lIll[2], this.y - GuiTextField.lIll[2], this.x + this.width + GuiTextField.lIll[2], this.y + this.height + GuiTextField.lIll[2], lllllIlIIllIIIl);
                drawRect(this.x, this.y, this.x + this.width, this.y + this.height, lllllIlIIllIIll);
            }
            int n;
            if (lIIIlI(this.isEnabled ? 1 : 0)) {
                n = this.enabledColor;
                "".length();
                if (-" ".length() >= "  ".length()) {
                    return;
                }
            }
            else {
                n = this.disabledColor;
            }
            final int lllllIlIlIIIIII = n;
            final int lllllIlIIllllll = this.cursorPosition - this.lineScrollOffset;
            int lllllIlIIlllllI = this.selectionEnd - this.lineScrollOffset;
            final String lllllIlIIllllIl = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            int n2;
            if (lIlIlI(lllllIlIIllllll) && lIlllI(lllllIlIIllllll, lllllIlIIllllIl.length())) {
                n2 = GuiTextField.lIll[2];
                "".length();
                if ((0x75 ^ 0x71) <= 0) {
                    return;
                }
            }
            else {
                n2 = GuiTextField.lIll[0];
            }
            final boolean lllllIlIIllllII = n2 != 0;
            int n3;
            if (lIIIlI(this.isFocused ? 1 : 0) && lIIllI(this.cursorCounter / GuiTextField.lIll[10] % GuiTextField.lIll[5]) && lIIIlI(lllllIlIIllllII ? 1 : 0)) {
                n3 = GuiTextField.lIll[2];
                "".length();
                if (-(0x4E ^ 0x4A) >= 0) {
                    return;
                }
            }
            else {
                n3 = GuiTextField.lIll[0];
            }
            final boolean lllllIlIIlllIll = n3 != 0;
            int x;
            if (lIIIlI(this.enableBackgroundDrawing ? 1 : 0)) {
                x = this.x + GuiTextField.lIll[7];
                "".length();
                if ((0xC7 ^ 0xC3) < 0) {
                    return;
                }
            }
            else {
                x = this.x;
            }
            final int lllllIlIIlllIlI = x;
            int y;
            if (lIIIlI(this.enableBackgroundDrawing ? 1 : 0)) {
                y = this.y + (this.height - GuiTextField.lIll[11]) / GuiTextField.lIll[5];
                "".length();
                if ((0xBB ^ 0x9A ^ (0x7B ^ 0x5E)) > (118 + 46 - 90 + 103 ^ 110 + 47 - 107 + 131)) {
                    return;
                }
            }
            else {
                y = this.y;
            }
            final int lllllIlIIlllIIl = y;
            int lllllIlIIlllIII = lllllIlIIlllIlI;
            if (lIIlII(lllllIlIIlllllI, lllllIlIIllllIl.length())) {
                lllllIlIIlllllI = lllllIlIIllllIl.length();
            }
            if (lIIllI(lllllIlIIllllIl.isEmpty() ? 1 : 0)) {
                String substring;
                if (lIIIlI(lllllIlIIllllII ? 1 : 0)) {
                    substring = lllllIlIIllllIl.substring(GuiTextField.lIll[0], lllllIlIIllllll);
                    "".length();
                    if ("  ".length() == ((0x9D ^ 0xBF ^ (0x42 ^ 0x77)) & (0xB2 ^ 0x93 ^ (0x28 ^ 0x1E) ^ -" ".length()))) {
                        return;
                    }
                }
                else {
                    substring = lllllIlIIllllIl;
                }
                final String lllllIlIlIIIIlI = substring;
                lllllIlIIlllIII = this.fontRenderer.drawStringWithShadow(lllllIlIlIIIIlI, (float)lllllIlIIlllIlI, (float)lllllIlIIlllIIl, lllllIlIlIIIIII);
            }
            int n4;
            if (!lIllIl(this.cursorPosition, this.text.length()) || lIllIl(this.text.length(), this.getMaxStringLength())) {
                n4 = GuiTextField.lIll[2];
                "".length();
                if (null != null) {
                    return;
                }
            }
            else {
                n4 = GuiTextField.lIll[0];
            }
            final boolean lllllIlIIllIlll = n4 != 0;
            int lllllIlIIllIllI = lllllIlIIlllIII;
            if (lIIllI(lllllIlIIllllII ? 1 : 0)) {
                int n5;
                if (lIllII(lllllIlIIllllll)) {
                    n5 = lllllIlIIlllIlI + this.width;
                    "".length();
                    if ("   ".length() < "   ".length()) {
                        return;
                    }
                }
                else {
                    n5 = lllllIlIIlllIlI;
                }
                lllllIlIIllIllI = n5;
                "".length();
                if ((0x14 ^ 0x10) < -" ".length()) {
                    return;
                }
            }
            else if (lIIIlI(lllllIlIIllIlll ? 1 : 0)) {
                lllllIlIIllIllI = lllllIlIIlllIII - GuiTextField.lIll[2];
                --lllllIlIIlllIII;
            }
            if (lIIllI(lllllIlIIllllIl.isEmpty() ? 1 : 0) && lIIIlI(lllllIlIIllllII ? 1 : 0) && lIIlIl(lllllIlIIllllll, lllllIlIIllllIl.length())) {
                lllllIlIIlllIII = this.fontRenderer.drawStringWithShadow(lllllIlIIllllIl.substring(lllllIlIIllllll), (float)lllllIlIIlllIII, (float)lllllIlIIlllIIl, lllllIlIlIIIIII);
            }
            if (lIIIlI(lllllIlIIlllIll ? 1 : 0)) {
                if (lIIIlI(lllllIlIIllIlll ? 1 : 0)) {
                    Gui.drawRect(lllllIlIIllIllI, lllllIlIIlllIIl - GuiTextField.lIll[2], lllllIlIIllIllI + GuiTextField.lIll[2], lllllIlIIlllIIl + GuiTextField.lIll[2] + this.fontRenderer.FONT_HEIGHT, GuiTextField.lIll[12]);
                    "".length();
                    if ("   ".length() <= " ".length()) {
                        return;
                    }
                }
                else {
                    this.fontRenderer.drawStringWithShadow(GuiTextField.lIIl[GuiTextField.lIll[10]], (float)lllllIlIIllIllI, (float)lllllIlIIlllIIl, lllllIlIlIIIIII);
                    "".length();
                }
            }
            if (lIlIII(lllllIlIIlllllI, lllllIlIIllllll)) {
                final int lllllIlIlIIIIIl = lllllIlIIlllIlI + this.fontRenderer.getStringWidth(lllllIlIIllllIl.substring(GuiTextField.lIll[0], lllllIlIIlllllI));
                this.drawSelectionBox(lllllIlIIllIllI, lllllIlIIlllIIl - GuiTextField.lIll[2], lllllIlIlIIIIIl - GuiTextField.lIll[2], lllllIlIIlllIIl + GuiTextField.lIll[2] + this.fontRenderer.FONT_HEIGHT);
            }
        }
    }
    
    public void setMaxStringLength(final int lllllIlIIIIlIIl) {
        this.maxStringLength = lllllIlIIIIlIIl;
        if (lIIlII(this.text.length(), lllllIlIIIIlIIl)) {
            this.text = this.text.substring(GuiTextField.lIll[0], lllllIlIIIIlIIl);
        }
    }
    
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }
    
    public int getSelectionEnd() {
        return this.selectionEnd;
    }
    
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }
    
    public void setEnableBackgroundDrawing(final boolean lllllIIlllllIlI) {
        this.enableBackgroundDrawing = lllllIIlllllIlI;
    }
    
    public void setCursorPositionZero() {
        this.setCursorPosition(GuiTextField.lIll[0]);
    }
    
    private static boolean lIlIll(final int lllllIIlIIIlllI, final int lllllIIlIIIllIl) {
        return lllllIIlIIIlllI == lllllIIlIIIllIl;
    }
    
    public GuiTextField(final int llllllIIIIlIIlI, final FontRenderer llllllIIIIlIIIl, final int llllllIIIIlIlll, final int llllllIIIIlIllI, final int llllllIIIIIlllI, final int llllllIIIIIllIl) {
        this.text = GuiTextField.lIIl[GuiTextField.lIll[0]];
        this.maxStringLength = GuiTextField.lIll[1];
        this.enableBackgroundDrawing = (GuiTextField.lIll[2] != 0);
        this.canLoseFocus = (GuiTextField.lIll[2] != 0);
        this.isEnabled = (GuiTextField.lIll[2] != 0);
        this.enabledColor = GuiTextField.lIll[3];
        this.disabledColor = GuiTextField.lIll[4];
        this.visible = (GuiTextField.lIll[2] != 0);
        this.validator = (Predicate<String>)Predicates.alwaysTrue();
        this.id = llllllIIIIlIIlI;
        this.fontRenderer = llllllIIIIlIIIl;
        this.x = llllllIIIIlIlll;
        this.y = llllllIIIIlIllI;
        this.width = llllllIIIIIlllI;
        this.height = llllllIIIIIllIl;
    }
    
    public int getId() {
        return this.id;
    }
    
    private static void lIIIII() {
        (lIll = new int[14])[0] = ((0x7A ^ 0x2D) & ~(0x9 ^ 0x5E));
        GuiTextField.lIll[1] = (0xE5 ^ 0xB8 ^ (0xE ^ 0x73));
        GuiTextField.lIll[2] = " ".length();
        GuiTextField.lIll[3] = (-(0xFFFF971C & 0x79F3) & (0xFFFFF5FF & 0xE0FBEF));
        GuiTextField.lIll[4] = (-(0xFFFFCFAF & 0x3DDF) & (0xFFFFFFFE & 0x707DFF));
        GuiTextField.lIll[5] = "  ".length();
        GuiTextField.lIll[6] = "   ".length();
        GuiTextField.lIll[7] = (0x95 ^ 0x91);
        GuiTextField.lIll[8] = -" ".length();
        GuiTextField.lIll[9] = (0x3A ^ 0x15 ^ (0x55 ^ 0x7F));
        GuiTextField.lIll[10] = (123 + 61 - 103 + 62 ^ 123 + 133 - 224 + 105);
        GuiTextField.lIll[11] = (0x9 ^ 0x1);
        GuiTextField.lIll[12] = -(0xFFFFEFFB & 0x2F3F34);
        GuiTextField.lIll[13] = (0x3B ^ 0x3C);
    }
    
    public int getWidth() {
        int width;
        if (lIIIlI(this.getEnableBackgroundDrawing() ? 1 : 0)) {
            width = this.width - GuiTextField.lIll[11];
            "".length();
            if ("  ".length() != "  ".length()) {
                return (26 + 93 - 109 + 160 ^ 50 + 84 - 64 + 114) & (0x24 ^ 0x75 ^ (0x11 ^ 0x52) ^ -" ".length());
            }
        }
        else {
            width = this.width;
        }
        return width;
    }
    
    public int getNthWordFromPos(final int lllllIllIlIIlII, final int lllllIllIlIIIII) {
        return this.getNthWordFromPosWS(lllllIllIlIIlII, lllllIllIlIIIII, (boolean)(GuiTextField.lIll[2] != 0));
    }
    
    private static boolean lIlIlI(final int lllllIIIlllIlIl) {
        return lllllIIIlllIlIl >= 0;
    }
    
    private static boolean lIIlII(final int lllllIIIllllllI, final int lllllIIIlllllIl) {
        return lllllIIIllllllI > lllllIIIlllllIl;
    }
    
    private static void lIlll() {
        (lIIl = new String[GuiTextField.lIll[13]])[GuiTextField.lIll[0]] = lIlIl("4dBMbWCD/Q0=", "wrUYJ");
        GuiTextField.lIIl[GuiTextField.lIll[2]] = lIlIl("7S5iM4JBp4A=", "RqOlp");
        GuiTextField.lIIl[GuiTextField.lIll[5]] = lIllI("", "iyRAz");
        GuiTextField.lIIl[GuiTextField.lIll[6]] = lIllI("", "yxTRs");
        GuiTextField.lIIl[GuiTextField.lIll[7]] = lIllI("", "WNVJA");
        GuiTextField.lIIl[GuiTextField.lIll[9]] = lIlIl("REuS+zVgVBU=", "ujmfK");
        GuiTextField.lIIl[GuiTextField.lIll[10]] = lIllI("FQ==", "JMoPF");
    }
    
    public void deleteWords(final int lllllIlllIIIllI) {
        if (lIIllI(this.text.isEmpty() ? 1 : 0)) {
            if (lIlIII(this.selectionEnd, this.cursorPosition)) {
                this.writeText(GuiTextField.lIIl[GuiTextField.lIll[5]]);
                "".length();
                if (((0xF9 ^ 0xC4) & ~(0x48 ^ 0x75)) != 0x0) {
                    return;
                }
            }
            else {
                this.deleteFromCursor(this.getNthWordFromCursor(lllllIlllIIIllI) - this.cursorPosition);
            }
        }
    }
    
    private void drawSelectionBox(int lllllIlIIIlIIlI, int lllllIlIIIlIIIl, int lllllIlIIIlIIII, int lllllIlIIIIllll) {
        if (lIIlIl(lllllIlIIIlIIlI, lllllIlIIIlIIII)) {
            final int lllllIlIIIlllII = lllllIlIIIlIIlI;
            lllllIlIIIlIIlI = lllllIlIIIlIIII;
            lllllIlIIIlIIII = lllllIlIIIlllII;
        }
        if (lIIlIl(lllllIlIIIlIIIl, (int)lllllIlIIIIllll)) {
            final int lllllIlIIIllIll = lllllIlIIIlIIIl;
            lllllIlIIIlIIIl = (int)lllllIlIIIIllll;
            lllllIlIIIIllll = lllllIlIIIllIll;
        }
        if (lIIlII(lllllIlIIIlIIII, this.x + this.width)) {
            lllllIlIIIlIIII = this.x + this.width;
        }
        if (lIIlII(lllllIlIIIlIIlI, this.x + this.width)) {
            lllllIlIIIlIIlI = this.x + this.width;
        }
        final Tessellator lllllIlIIIlIlIl = Tessellator.getInstance();
        final BufferBuilder lllllIlIIIlIlII = lllllIlIIIlIlIl.getBuffer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        lllllIlIIIlIlII.begin(GuiTextField.lIll[13], DefaultVertexFormats.POSITION);
        lllllIlIIIlIlII.pos((double)lllllIlIIIlIIlI, (double)lllllIlIIIIllll, 0.0).endVertex();
        lllllIlIIIlIlII.pos((double)lllllIlIIIlIIII, (double)lllllIlIIIIllll, 0.0).endVertex();
        lllllIlIIIlIlII.pos((double)lllllIlIIIlIIII, (double)lllllIlIIIlIIIl, 0.0).endVertex();
        lllllIlIIIlIlII.pos((double)lllllIlIIIlIIlI, (double)lllllIlIIIlIIIl, 0.0).endVertex();
        lllllIlIIIlIlIl.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
    
    public boolean getVisible() {
        return this.visible;
    }
    
    private static boolean lIIIlI(final int lllllIIIllllIIl) {
        return lllllIIIllllIIl != 0;
    }
    
    private static String lIlIl(final String lllllIIlIIlIlII, final String lllllIIlIIlIIll) {
        try {
            final SecretKeySpec lllllIIlIIllIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllIIlIIlIIll.getBytes(StandardCharsets.UTF_8)), GuiTextField.lIll[11]), "DES");
            final Cipher lllllIIlIIllIII = Cipher.getInstance("DES");
            lllllIIlIIllIII.init(GuiTextField.lIll[5], lllllIIlIIllIIl);
            return new String(lllllIIlIIllIII.doFinal(Base64.getDecoder().decode(lllllIIlIIlIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIIlIIlIlll) {
            lllllIIlIIlIlll.printStackTrace();
            return null;
        }
    }
    
    public void setVisible(final boolean lllllIIlIllIllI) {
        this.visible = lllllIIlIllIllI;
    }
    
    public void setResponderEntryValue(final int lllllIlllIIlllI, final String lllllIlllIIlIlI) {
        if (lIIlll(this.guiResponder)) {
            this.guiResponder.setEntryValue(lllllIlllIIlllI, lllllIlllIIlIlI);
        }
    }
    
    static {
        lIIIII();
        lIlll();
    }
    
    public void setText(final String llllllIIIIIIIII) {
        if (lIIIlI(this.validator.apply((Object)llllllIIIIIIIII) ? 1 : 0)) {
            if (lIIlII(llllllIIIIIIIII.length(), this.maxStringLength)) {
                this.text = llllllIIIIIIIII.substring(GuiTextField.lIll[0], this.maxStringLength);
                "".length();
                if (((0x74 ^ 0x20 ^ (0xF2 ^ 0x85)) & (0x4C ^ 0x42 ^ (0xEB ^ 0xC6) ^ -" ".length())) != 0x0) {
                    return;
                }
            }
            else {
                this.text = llllllIIIIIIIII;
            }
            this.setCursorPositionEnd();
        }
    }
    
    public void updateCursorCounter() {
        this.cursorCounter += GuiTextField.lIll[2];
    }
    
    public void deleteFromCursor(final int lllllIllIlllIII) {
        if (lIIllI(this.text.isEmpty() ? 1 : 0)) {
            if (lIlIII(this.selectionEnd, this.cursorPosition)) {
                this.writeText(GuiTextField.lIIl[GuiTextField.lIll[6]]);
                "".length();
                if (" ".length() != " ".length()) {
                    return;
                }
            }
            else {
                int n;
                if (lIlIIl(lllllIllIlllIII)) {
                    n = GuiTextField.lIll[2];
                    "".length();
                    if ("  ".length() < 0) {
                        return;
                    }
                }
                else {
                    n = GuiTextField.lIll[0];
                }
                final boolean lllllIllIllllIl = n != 0;
                int cursorPosition;
                if (lIIIlI(lllllIllIllllIl ? 1 : 0)) {
                    cursorPosition = this.cursorPosition + lllllIllIlllIII;
                    "".length();
                    if ((0x1E ^ 0x7E ^ (0x42 ^ 0x26)) <= " ".length()) {
                        return;
                    }
                }
                else {
                    cursorPosition = this.cursorPosition;
                }
                final int lllllIllIllllII = cursorPosition;
                int cursorPosition2;
                if (lIIIlI(lllllIllIllllIl ? 1 : 0)) {
                    cursorPosition2 = this.cursorPosition;
                    "".length();
                    if (-" ".length() > (0xAF ^ 0xAB)) {
                        return;
                    }
                }
                else {
                    cursorPosition2 = this.cursorPosition + lllllIllIlllIII;
                }
                final int lllllIllIlllIll = cursorPosition2;
                String lllllIllIlllIlI = GuiTextField.lIIl[GuiTextField.lIll[7]];
                if (lIlIlI(lllllIllIllllII)) {
                    lllllIllIlllIlI = this.text.substring(GuiTextField.lIll[0], lllllIllIllllII);
                }
                if (lIIlIl(lllllIllIlllIll, this.text.length())) {
                    lllllIllIlllIlI = String.valueOf(new StringBuilder().append(lllllIllIlllIlI).append(this.text.substring(lllllIllIlllIll)));
                }
                if (lIIIlI(this.validator.apply((Object)lllllIllIlllIlI) ? 1 : 0)) {
                    this.text = lllllIllIlllIlI;
                    if (lIIIlI(lllllIllIllllIl ? 1 : 0)) {
                        this.moveCursorBy(lllllIllIlllIII);
                    }
                    this.setResponderEntryValue(this.id, this.text);
                }
            }
        }
    }
    
    public String getSelectedText() {
        int n;
        if (lIIlIl(this.cursorPosition, this.selectionEnd)) {
            n = this.cursorPosition;
            "".length();
            if (-" ".length() >= " ".length()) {
                return null;
            }
        }
        else {
            n = this.selectionEnd;
        }
        final int lllllIlllllIllI = n;
        int n2;
        if (lIIlIl(this.cursorPosition, this.selectionEnd)) {
            n2 = this.selectionEnd;
            "".length();
            if ("   ".length() != "   ".length()) {
                return null;
            }
        }
        else {
            n2 = this.cursorPosition;
        }
        final int lllllIlllllIlIl = n2;
        return this.text.substring(lllllIlllllIllI, lllllIlllllIlIl);
    }
    
    public String getText() {
        return this.text;
    }
    
    private static boolean lIlllI(final int lllllIIlIIIIIlI, final int lllllIIlIIIIIIl) {
        return lllllIIlIIIIIlI <= lllllIIlIIIIIIl;
    }
    
    public void moveCursorBy(final int lllllIllIIIIIIl) {
        this.setCursorPosition(this.selectionEnd + lllllIllIIIIIIl);
    }
    
    public void writeText(final String lllllIllllIIIIl) {
        String lllllIllllIIIII = GuiTextField.lIIl[GuiTextField.lIll[2]];
        final String lllllIlllIlllll = ChatAllowedCharacters.filterAllowedCharacters(lllllIllllIIIIl);
        int n;
        if (lIIlIl(this.cursorPosition, this.selectionEnd)) {
            n = this.cursorPosition;
            "".length();
            if ((0xBD ^ 0xB9) <= -" ".length()) {
                return;
            }
        }
        else {
            n = this.selectionEnd;
        }
        final int lllllIlllIllllI = n;
        int n2;
        if (lIIlIl(this.cursorPosition, this.selectionEnd)) {
            n2 = this.selectionEnd;
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        }
        else {
            n2 = this.cursorPosition;
        }
        final int lllllIlllIlllIl = n2;
        final int lllllIlllIlllII = this.maxStringLength - this.text.length() - (lllllIlllIllllI - lllllIlllIlllIl);
        if (lIIllI(this.text.isEmpty() ? 1 : 0)) {
            lllllIllllIIIII = String.valueOf(new StringBuilder().append(lllllIllllIIIII).append(this.text.substring(GuiTextField.lIll[0], lllllIlllIllllI)));
        }
        int lllllIlllIllIll;
        if (lIIlIl(lllllIlllIlllII, lllllIlllIlllll.length())) {
            lllllIllllIIIII = String.valueOf(new StringBuilder().append(lllllIllllIIIII).append(lllllIlllIlllll.substring(GuiTextField.lIll[0], lllllIlllIlllII)));
            final int lllllIllllIIIll = lllllIlllIlllII;
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        }
        else {
            lllllIllllIIIII = String.valueOf(new StringBuilder().append(lllllIllllIIIII).append(lllllIlllIlllll));
            lllllIlllIllIll = lllllIlllIlllll.length();
        }
        if (lIIllI(this.text.isEmpty() ? 1 : 0) && lIIlIl(lllllIlllIlllIl, this.text.length())) {
            lllllIllllIIIII = String.valueOf(new StringBuilder().append(lllllIllllIIIII).append(this.text.substring(lllllIlllIlllIl)));
        }
        if (lIIIlI(this.validator.apply((Object)lllllIllllIIIII) ? 1 : 0)) {
            this.text = lllllIllllIIIII;
            this.moveCursorBy(lllllIlllIllllI - this.selectionEnd + lllllIlllIllIll);
            this.setResponderEntryValue(this.id, this.text);
        }
    }
    
    public int getNthWordFromCursor(final int lllllIllIlIlIIl) {
        return this.getNthWordFromPos(lllllIllIlIlIIl, this.getCursorPosition());
    }
    
    private static boolean lIIlll(final Object lllllIIIllllIll) {
        return lllllIIIllllIll != null;
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
}
