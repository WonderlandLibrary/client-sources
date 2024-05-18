package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import java.io.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;

public class GuiScreenServerList extends GuiScreen
{
    private final ServerData field_146301_f;
    private final GuiScreen field_146303_a;
    private static final String[] I;
    private GuiTextField field_146302_g;
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.field_146302_g.textboxKeyTyped(c, n)) {
            final GuiButton guiButton = this.buttonList.get("".length());
            int enabled;
            if (this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(GuiScreenServerList.I["   ".length()]).length > 0) {
                enabled = " ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                enabled = "".length();
            }
            guiButton.enabled = (enabled != 0);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n == (0x56 ^ 0x4A) || n == 127 + 76 - 163 + 116) {
            this.actionPerformed(this.buttonList.get("".length()));
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiScreenServerList.I[0xBA ^ 0xBE], new Object["".length()]), this.width / "  ".length(), 0x61 ^ 0x75, 803497 + 11784077 - 8590692 + 12780333);
        this.drawString(this.fontRendererObj, I18n.format(GuiScreenServerList.I[0x45 ^ 0x40], new Object["".length()]), this.width / "  ".length() - (0x4E ^ 0x2A), 0x34 ^ 0x50, 8066811 + 1724630 - 7366352 + 8101791);
        this.field_146302_g.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field_146302_g.mouseClicked(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x25 ^ 0x23])["".length()] = I("=\u0000\b';:6\u00010.+\u0017J1=\"\u0000\u00076", "NedBX");
        GuiScreenServerList.I[" ".length()] = I("7\u0002\u0003W01\u0019\t\u001c?", "PwjyS");
        GuiScreenServerList.I["  ".length()] = I("u", "ObllG");
        GuiScreenServerList.I["   ".length()] = I("S", "iYAiQ");
        GuiScreenServerList.I[0xA6 ^ 0xA2] = I("\u0006<<+-\u0001\n5<8\u0010+~*'\u0007<3:", "uYPNN");
        GuiScreenServerList.I[0x2E ^ 0x2B] = I("2\r*=7!\u001f+\u001c|6\u0007:\u000b \u001a\u0019", "SiNnR");
    }
    
    public GuiScreenServerList(final GuiScreen field_146303_a, final ServerData field_146301_f) {
        this.field_146303_a = field_146303_a;
        this.field_146301_f = field_146301_f;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
        this.mc.gameSettings.lastServer = this.field_146302_g.getText();
        this.mc.gameSettings.saveOptions();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.buttonList.clear();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0xFF ^ 0x9B), this.height / (0xAD ^ 0xA9) + (0xC ^ 0x6C) + (0x72 ^ 0x7E), I18n.format(GuiScreenServerList.I["".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0xC ^ 0x68), this.height / (0x63 ^ 0x67) + (0x49 ^ 0x31) + (0x53 ^ 0x5F), I18n.format(GuiScreenServerList.I[" ".length()], new Object["".length()])));
        (this.field_146302_g = new GuiTextField("  ".length(), this.fontRendererObj, this.width / "  ".length() - (0xD0 ^ 0xB4), 0x62 ^ 0x16, 42 + 103 - 12 + 67, 0x69 ^ 0x7D)).setMaxStringLength(6 + 117 - 10 + 15);
        this.field_146302_g.setFocused(" ".length() != 0);
        this.field_146302_g.setText(this.mc.gameSettings.lastServer);
        final GuiButton guiButton = this.buttonList.get("".length());
        int enabled;
        if (this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(GuiScreenServerList.I["  ".length()]).length > 0) {
            enabled = " ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        guiButton.enabled = (enabled != 0);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public void updateScreen() {
        this.field_146302_g.updateCursorCounter();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == " ".length()) {
                this.field_146303_a.confirmClicked("".length() != 0, "".length());
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (guiButton.id == 0) {
                this.field_146301_f.serverIP = this.field_146302_g.getText();
                this.field_146303_a.confirmClicked(" ".length() != 0, "".length());
            }
        }
    }
}
