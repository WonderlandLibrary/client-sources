package net.minecraft.client.gui;

import java.io.*;
import net.minecraft.client.resources.*;

public class GuiConfirmOpenLink extends GuiYesNo
{
    private final String openLinkWarning;
    private boolean showSecurityWarning;
    private final String copyLinkButtonText;
    private static final String[] I;
    private final String linkText;
    
    static {
        I();
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0x9F ^ 0xAD) - (0xF ^ 0x66), this.height / (0xA5 ^ 0xA3) + (0xD8 ^ 0xB8), 0x3F ^ 0x5B, 0xAD ^ 0xB9, this.confirmButtonText));
        this.buttonList.add(new GuiButton("  ".length(), this.width / "  ".length() - (0x22 ^ 0x10), this.height / (0xA2 ^ 0xA4) + (0x52 ^ 0x32), 0x76 ^ 0x12, 0xBA ^ 0xAE, this.copyLinkButtonText));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0x6B ^ 0x59) + (0x46 ^ 0x2F), this.height / (0x8A ^ 0x8C) + (0x45 ^ 0x25), 0xB ^ 0x6F, 0x34 ^ 0x20, this.cancelButtonText));
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == "  ".length()) {
            this.copyLinkToClipboard();
        }
        final GuiYesNoCallback parentScreen = this.parentScreen;
        int n;
        if (guiButton.id == 0) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        parentScreen.confirmClicked(n != 0, this.parentButtonClickedId);
    }
    
    public void copyLinkToClipboard() {
        GuiScreen.setClipboardString(this.linkText);
    }
    
    public void disableSecurityWarning() {
        this.showSecurityWarning = ("".length() != 0);
    }
    
    private static void I() {
        (I = new String[0xC9 ^ 0xC1])["".length()] = I("\u0014\u0002-0T\u001b\u0003\"/T\u0014\u0005\"\"\u0013\u0005\u0007\u00186\u000f\u0004\u001e) ", "wjLDz");
        GuiConfirmOpenLink.I[" ".length()] = I("%/)\u000eH*.&\u0011H%(&\u001c\u000f4*", "FGHzf");
        GuiConfirmOpenLink.I["  ".length()] = I("\b\f2\u001bj\u0007\r=\u0004j\u0004\u00146\u0001", "kdSoD");
        GuiConfirmOpenLink.I["   ".length()] = I("\u0013\u0007\u0005X/\u0011\u0001", "trlvV");
        GuiConfirmOpenLink.I[0x43 ^ 0x47] = I("!\u001e\u0003E7'\u0005\t\u000e8", "FkjkT");
        GuiConfirmOpenLink.I[0x82 ^ 0x87] = I("&\r\u001ex\u0016.", "AxwVx");
        GuiConfirmOpenLink.I[0x78 ^ 0x7E] = I("(\u0001\t%W(\u0006\u0018(", "KihQy");
        GuiConfirmOpenLink.I[0xBE ^ 0xB9] = I("\u0006\u001c %G\t\u001d/:G\u0012\u00153?\u0000\u000b\u0013", "etAQi");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        if (this.showSecurityWarning) {
            this.drawCenteredString(this.fontRendererObj, this.openLinkWarning, this.width / "  ".length(), 0xAF ^ 0xC1, 10281467 + 14078848 - 22040837 + 14444630);
        }
    }
    
    public GuiConfirmOpenLink(final GuiYesNoCallback guiYesNoCallback, final String linkText, final int n, final boolean b) {
        String s;
        if (b) {
            s = GuiConfirmOpenLink.I["".length()];
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            s = GuiConfirmOpenLink.I[" ".length()];
        }
        super(guiYesNoCallback, I18n.format(s, new Object["".length()]), linkText, n);
        this.showSecurityWarning = (" ".length() != 0);
        String s2;
        if (b) {
            s2 = GuiConfirmOpenLink.I["  ".length()];
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            s2 = GuiConfirmOpenLink.I["   ".length()];
        }
        this.confirmButtonText = I18n.format(s2, new Object["".length()]);
        String s3;
        if (b) {
            s3 = GuiConfirmOpenLink.I[0x8 ^ 0xC];
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            s3 = GuiConfirmOpenLink.I[0x75 ^ 0x70];
        }
        this.cancelButtonText = I18n.format(s3, new Object["".length()]);
        this.copyLinkButtonText = I18n.format(GuiConfirmOpenLink.I[0x99 ^ 0x9F], new Object["".length()]);
        this.openLinkWarning = I18n.format(GuiConfirmOpenLink.I[0x1B ^ 0x1C], new Object["".length()]);
        this.linkText = linkText;
    }
}
