package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.realms.*;
import net.minecraft.client.gui.achievement.*;
import java.io.*;

public class GuiIngameMenu extends GuiScreen
{
    private int field_146445_a;
    private static final String[] I;
    private int field_146444_f;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiIngameMenu.I[0xA8 ^ 0xAF], new Object["".length()]), this.width / "  ".length(), 0x41 ^ 0x69, 15858015 + 858078 - 4866909 + 4928031);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.field_146444_f += " ".length();
    }
    
    @Override
    public void initGui() {
        this.field_146445_a = "".length();
        this.buttonList.clear();
        final int n = -(0x7A ^ 0x6A);
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0xF ^ 0x6B), this.height / (0xBF ^ 0xBB) + (0x70 ^ 0x8) + n, I18n.format(GuiIngameMenu.I["".length()], new Object["".length()])));
        if (!this.mc.isIntegratedServerRunning()) {
            this.buttonList.get("".length()).displayString = I18n.format(GuiIngameMenu.I[" ".length()], new Object["".length()]);
        }
        this.buttonList.add(new GuiButton(0xAC ^ 0xA8, this.width / "  ".length() - (0x51 ^ 0x35), this.height / (0x43 ^ 0x47) + (0x58 ^ 0x40) + n, I18n.format(GuiIngameMenu.I["  ".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0x6A ^ 0xE), this.height / (0xA7 ^ 0xA3) + (0x17 ^ 0x77) + n, 0x6B ^ 0x9, 0xA9 ^ 0xBD, I18n.format(GuiIngameMenu.I["   ".length()], new Object["".length()])));
        final GuiButton guiButton;
        this.buttonList.add(guiButton = new GuiButton(0x7F ^ 0x78, this.width / "  ".length() + "  ".length(), this.height / (0x1C ^ 0x18) + (0x72 ^ 0x12) + n, 0xC ^ 0x6E, 0x9 ^ 0x1D, I18n.format(GuiIngameMenu.I[0xA5 ^ 0xA1], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x42 ^ 0x47, this.width / "  ".length() - (0x1 ^ 0x65), this.height / (0x68 ^ 0x6C) + (0x96 ^ 0xA6) + n, 0xDD ^ 0xBF, 0x23 ^ 0x37, I18n.format(GuiIngameMenu.I[0x12 ^ 0x17], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x4B ^ 0x4D, this.width / "  ".length() + "  ".length(), this.height / (0xBE ^ 0xBA) + (0x98 ^ 0xA8) + n, 0x7D ^ 0x1F, 0x48 ^ 0x5C, I18n.format(GuiIngameMenu.I[0xAE ^ 0xA8], new Object["".length()])));
        final GuiButton guiButton2 = guiButton;
        int enabled;
        if (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic()) {
            enabled = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        guiButton2.enabled = (enabled != 0);
    }
    
    private static void I() {
        (I = new String[0x82 ^ 0x8A])["".length()] = I("\u000b.\u001f\u001fv\u0014.\u0005\u001f*\b\u001f\u001e'=\b>", "fKqjX");
        GuiIngameMenu.I[" ".length()] = I("<\u0015-\u001ax5\u00190\f9?\u001e&\f\"", "QpCoV");
        GuiIngameMenu.I["  ".length()] = I("<\u0015&\u0013I#\u0015<\u0013\u0015?$'!\u0006<\u0015", "QpHfg");
        GuiIngameMenu.I["   ".length()] = I("\u0005\u0014\t\u001cI\u0007\u0001\u0013\u0000\b\u0006\u0002", "hqgig");
        GuiIngameMenu.I[0x64 ^ 0x60] = I("+\n\u0014\u001bb5\u0007\u001b\u001c)\u0012\u00006\u000f\"", "FoznL");
        GuiIngameMenu.I[0x6C ^ 0x69] = I("\u0003&?M7\u0007;?\u0006 \u0001>3\r\"\u0017", "dSVcV");
        GuiIngameMenu.I[0xBF ^ 0xB9] = I("\u0013\u0013-}\u0011\u0000\u00070 ", "tfDSb");
        GuiIngameMenu.I[0x14 ^ 0x13] = I("\u0015\r\n$b\u001f\t\t4", "xhdQL");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                "".length();
                if (3 == 0) {
                    throw null;
                }
                return;
            }
            case 1: {
                final boolean integratedServerRunning = this.mc.isIntegratedServerRunning();
                final boolean func_181540_al = this.mc.func_181540_al();
                guiButton.enabled = ("".length() != 0);
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                if (integratedServerRunning) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    return;
                }
                else {
                    if (!func_181540_al) {
                        this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                        break;
                    }
                    new RealmsBridge().switchToRealms(new GuiMainMenu());
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    return;
                }
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                "".length();
                if (2 == 4) {
                    throw null;
                }
                return;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                return;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                return;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                return;
            }
        }
        "".length();
        if (2 < 1) {
            throw null;
        }
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
