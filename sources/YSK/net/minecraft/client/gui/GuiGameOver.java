package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback
{
    private boolean field_146346_f;
    private static final String[] I;
    private int enableButtonsTimer;
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(new GuiMainMenu());
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen(null);
        }
    }
    
    private static void I() {
        (I = new String[0x64 ^ 0x69])["".length()] = I("\u001c3\u0004-\u0010+5\u0017<\u001d\u0016x\u0001<\u0014\u001d\"\u0000\u000e\u0017\n:\u0001", "xVeYx");
        GuiGameOver.I[" ".length()] = I("\u001d\b*\f\r*\u000e9\u001d\u0000\u0017C'\u001d\u0004\u000f\b\u0018\u001d\u0017\u000f\b9", "ymKxe");
        GuiGameOver.I["  ".length()] = I("2\t4\u0017\u0006\u0005\u000f'\u0006\u000b8B'\u0006\u001d&\r\"\r", "VlUcn");
        GuiGameOver.I["   ".length()] = I("!+\u000e\u001b!\u0016-\u001d\n,+`\u001b\u0006=)+<\f; +\u0001", "ENooI");
        GuiGameOver.I[0xC0 ^ 0xC4] = I("\n+\b#\u0001=-\u001b2\f\u0000`\u0018\"\u0000\u001a`\n8\u0007\b'\u001b:", "nNiWi");
        GuiGameOver.I[0x71 ^ 0x74] = I("", "feFEn");
        GuiGameOver.I[0x86 ^ 0x80] = I("\u0015\"-&&\"$>7+\u001fi8;:\u001d\"\u001f1<\u0014\"\"", "qGLRN");
        GuiGameOver.I[0x59 ^ 0x5E] = I("\u001d4\u0015\u00128*2\u0006\u00035\u0017\u007f\u0006\u0003#\t0\u0003\b", "yQtfP");
        GuiGameOver.I[0x43 ^ 0x4B] = I("\u0013/7\u001e\u001b$)$\u000f\u0016\u0019d\"\u0003\u0007\u001b/x\u0002\u0012\u0005.5\u0005\u0001\u0012", "wJVjs");
        GuiGameOver.I[0x8A ^ 0x83] = I("\u000e'2\r?9!!\u001c2\u0004l'\u0010#\u0006'", "jBSyW");
        GuiGameOver.I[0xC9 ^ 0xC3] = I("0\u0002$</\u0007\u00047-\":I-)50\u0004*:\"\u001d\t#'", "TgEHG");
        GuiGameOver.I[0xD ^ 0x6] = I("\u000b\u0015)\u00010<\u0013:\u0010=\u0001^;\u00167\u001d\u0015", "opHuX");
        GuiGameOver.I[0x89 ^ 0x85] = I("mm", "WMIIE");
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.enableButtonsTimer += " ".length();
        if (this.enableButtonsTimer == (0x5B ^ 0x4F)) {
            final Iterator<GuiButton> iterator = this.buttonList.iterator();
            "".length();
            if (!true) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().enabled = (" ".length() != 0);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    static {
        I();
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return "".length() != 0;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
            if (this.mc.isIntegratedServerRunning()) {
                this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0xED ^ 0x89), this.height / (0x1F ^ 0x1B) + (0xA6 ^ 0xC6), I18n.format(GuiGameOver.I["".length()], new Object["".length()])));
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0x34 ^ 0x50), this.height / (0x37 ^ 0x33) + (0x4E ^ 0x2E), I18n.format(GuiGameOver.I[" ".length()], new Object["".length()])));
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
        }
        else {
            this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0x54 ^ 0x30), this.height / (0x1 ^ 0x5) + (0xE2 ^ 0xAA), I18n.format(GuiGameOver.I["  ".length()], new Object["".length()])));
            this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0xD0 ^ 0xB4), this.height / (0x36 ^ 0x32) + (0x59 ^ 0x39), I18n.format(GuiGameOver.I["   ".length()], new Object["".length()])));
            if (this.mc.getSession() == null) {
                this.buttonList.get(" ".length()).enabled = ("".length() != 0);
            }
        }
        final Iterator<GuiButton> iterator = this.buttonList.iterator();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().enabled = ("".length() != 0);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                this.mc.thePlayer.respawnPlayer();
                this.mc.displayGuiScreen(null);
                "".length();
                if (3 == 4) {
                    throw null;
                }
                break;
            }
            case 1: {
                if (!this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                    final GuiYesNo guiYesNo = new GuiYesNo(this, I18n.format(GuiGameOver.I[0xD ^ 0x9], new Object["".length()]), GuiGameOver.I[0x8 ^ 0xD], I18n.format(GuiGameOver.I[0x2C ^ 0x2A], new Object["".length()]), I18n.format(GuiGameOver.I[0xA ^ 0xD], new Object["".length()]), "".length());
                    this.mc.displayGuiScreen(guiYesNo);
                    guiYesNo.setButtonDelay(0x8D ^ 0x99);
                    break;
                }
                this.mc.displayGuiScreen(new GuiMainMenu());
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
        }
    }
    
    public GuiGameOver() {
        this.field_146346_f = ("".length() != 0);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawGradientRect("".length(), "".length(), this.width, this.height, 41296774 + 1099850691 + 473515862 + 1192289, -(1262779664 + 1138208288 - 895040913 + 96264753));
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        final boolean hardcoreModeEnabled = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
        String s;
        if (hardcoreModeEnabled) {
            s = I18n.format(GuiGameOver.I[0x3F ^ 0x37], new Object["".length()]);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            s = I18n.format(GuiGameOver.I[0x91 ^ 0x98], new Object["".length()]);
        }
        this.drawCenteredString(this.fontRendererObj, s, this.width / "  ".length() / "  ".length(), 0x36 ^ 0x28, 13146921 + 50093 - 3575305 + 7155506);
        GlStateManager.popMatrix();
        if (hardcoreModeEnabled) {
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiGameOver.I[0xAB ^ 0xA1], new Object["".length()]), this.width / "  ".length(), 79 + 23 + 7 + 35, 13566583 + 9249269 - 9188094 + 3149457);
        }
        this.drawCenteredString(this.fontRendererObj, String.valueOf(I18n.format(GuiGameOver.I[0xB1 ^ 0xBA], new Object["".length()])) + GuiGameOver.I[0xD ^ 0x1] + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / "  ".length(), 0x33 ^ 0x57, 4801376 + 5068499 + 2354067 + 4553273);
        super.drawScreen(n, n2, n3);
    }
}
