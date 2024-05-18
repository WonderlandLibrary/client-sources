package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import com.google.common.base.*;
import java.io.*;
import java.net.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;

public class GuiScreenAddServer extends GuiScreen
{
    private final GuiScreen parentScreen;
    private final ServerData serverData;
    private Predicate<String> field_181032_r;
    private GuiTextField serverNameField;
    private GuiTextField serverIPField;
    private static final String[] I;
    private GuiButton serverResourcePacks;
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.serverNameField.textboxKeyTyped(c, n);
        this.serverIPField.textboxKeyTyped(c, n);
        if (n == (0x69 ^ 0x66)) {
            final GuiTextField serverNameField = this.serverNameField;
            int focused;
            if (this.serverNameField.isFocused()) {
                focused = "".length();
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else {
                focused = " ".length();
            }
            serverNameField.setFocused(focused != 0);
            final GuiTextField serverIPField = this.serverIPField;
            int focused2;
            if (this.serverIPField.isFocused()) {
                focused2 = "".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                focused2 = " ".length();
            }
            serverIPField.setFocused(focused2 != 0);
        }
        if (n == (0x94 ^ 0x88) || n == 115 + 53 - 139 + 127) {
            this.actionPerformed(this.buttonList.get("".length()));
        }
        final GuiButton guiButton = this.buttonList.get("".length());
        int enabled;
        if (this.serverIPField.getText().length() > 0 && this.serverIPField.getText().split(GuiScreenAddServer.I[0x24 ^ 0x23]).length > 0 && this.serverNameField.getText().length() > 0) {
            enabled = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        guiButton.enabled = (enabled != 0);
    }
    
    public GuiScreenAddServer(final GuiScreen parentScreen, final ServerData serverData) {
        this.field_181032_r = (Predicate<String>)new Predicate<String>() {
            private static final String[] I;
            final GuiScreenAddServer this$0;
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("W", "mfuZO");
            }
            
            public boolean apply(final Object o) {
                return this.apply((String)o);
            }
            
            public boolean apply(final String s) {
                if (s.length() == 0) {
                    return " ".length() != 0;
                }
                final String[] split = s.split(GuiScreenAddServer$1.I["".length()]);
                if (split.length == 0) {
                    return " ".length() != 0;
                }
                try {
                    IDN.toASCII(split["".length()]);
                    return " ".length() != 0;
                }
                catch (IllegalArgumentException ex) {
                    return "".length() != 0;
                }
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
                    if (2 != 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        this.parentScreen = parentScreen;
        this.serverData = serverData;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiScreenAddServer.I[0xA9 ^ 0xA1], new Object["".length()]), this.width / "  ".length(), 0x5A ^ 0x4B, 8620918 + 13710789 - 5910974 + 356482);
        this.drawString(this.fontRendererObj, I18n.format(GuiScreenAddServer.I[0x95 ^ 0x9C], new Object["".length()]), this.width / "  ".length() - (0x7 ^ 0x63), 0xA7 ^ 0x92, 6415681 + 5550609 - 9805455 + 8366045);
        this.drawString(this.fontRendererObj, I18n.format(GuiScreenAddServer.I[0x64 ^ 0x6E], new Object["".length()]), this.width / "  ".length() - (0x6C ^ 0x8), 0xEE ^ 0xB0, 8270495 + 4453781 - 8937498 + 6740102);
        this.serverNameField.drawTextBox();
        this.serverIPField.drawTextBox();
        super.drawScreen(n, n2, n3);
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateScreen() {
        this.serverNameField.updateCursorCounter();
        this.serverIPField.updateCursorCounter();
    }
    
    static {
        I();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == "  ".length()) {
                this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + " ".length()) % ServerData.ServerResourceMode.values().length]);
                this.serverResourcePacks.displayString = String.valueOf(I18n.format(GuiScreenAddServer.I[0xC3 ^ 0xC6], new Object["".length()])) + GuiScreenAddServer.I[0x49 ^ 0x4F] + this.serverData.getResourceMode().getMotd().getFormattedText();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else if (guiButton.id == " ".length()) {
                this.parentScreen.confirmClicked("".length() != 0, "".length());
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (guiButton.id == 0) {
                this.serverData.serverName = this.serverNameField.getText();
                this.serverData.serverIP = this.serverIPField.getText();
                this.parentScreen.confirmClicked(" ".length() != 0, "".length());
            }
        }
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.buttonList.clear();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0xCC ^ 0xA8), this.height / (0x8 ^ 0xC) + (0x9 ^ 0x69) + (0x7C ^ 0x6E), I18n.format(GuiScreenAddServer.I["".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0xE0 ^ 0x84), this.height / (0xB0 ^ 0xB4) + (0xFA ^ 0x82) + (0x2 ^ 0x10), I18n.format(GuiScreenAddServer.I[" ".length()], new Object["".length()])));
        this.buttonList.add(this.serverResourcePacks = new GuiButton("  ".length(), this.width / "  ".length() - (0xD8 ^ 0xBC), this.height / (0x87 ^ 0x83) + (0x4E ^ 0x6), String.valueOf(I18n.format(GuiScreenAddServer.I["  ".length()], new Object["".length()])) + GuiScreenAddServer.I["   ".length()] + this.serverData.getResourceMode().getMotd().getFormattedText()));
        (this.serverNameField = new GuiTextField("".length(), this.fontRendererObj, this.width / "  ".length() - (0x33 ^ 0x57), 0x5E ^ 0x1C, 159 + 168 - 206 + 79, 0x1B ^ 0xF)).setFocused(" ".length() != 0);
        this.serverNameField.setText(this.serverData.serverName);
        (this.serverIPField = new GuiTextField(" ".length(), this.fontRendererObj, this.width / "  ".length() - (0xEB ^ 0x8F), 0xDB ^ 0xB1, 92 + 170 - 74 + 12, 0x1A ^ 0xE)).setMaxStringLength(16 + 14 + 57 + 41);
        this.serverIPField.setText(this.serverData.serverIP);
        this.serverIPField.func_175205_a(this.field_181032_r);
        final GuiButton guiButton = this.buttonList.get("".length());
        int enabled;
        if (this.serverIPField.getText().length() > 0 && this.serverIPField.getText().split(GuiScreenAddServer.I[0x52 ^ 0x56]).length > 0 && this.serverNameField.getText().length() > 0) {
            enabled = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        guiButton.enabled = (enabled != 0);
    }
    
    private static void I() {
        (I = new String[0x34 ^ 0x3F])["".length()] = I("\u000b<\f2)\u0018.\r\u0013b\u000b<\f", "jXhaL");
        GuiScreenAddServer.I[" ".length()] = I("\u000f \bl\u000e\t;\u0002'\u0001", "hUaBm");
        GuiScreenAddServer.I["  ".length()] = I("06\u00061\u001f#$\u0007\u0010T#7\u0011\r\u000f#1\u00072\u001b29", "QRbbz");
        GuiScreenAddServer.I["   ".length()] = I("Qw", "kWXdq");
        GuiScreenAddServer.I[0x1D ^ 0x19] = I("_", "eKBDX");
        GuiScreenAddServer.I[0x13 ^ 0x16] = I("\u0010=\"\"\t\u0003/#\u0003B\u0003<5\u001e\u0019\u0003:#!\r\u00122", "qYFql");
        GuiScreenAddServer.I[0x4E ^ 0x48] = I("wb", "MBiZc");
        GuiScreenAddServer.I[0x21 ^ 0x26] = I("X", "buPAg");
        GuiScreenAddServer.I[0x7C ^ 0x74] = I("\u0002\u0000\"\u0015\u0017\u0011\u0012#4\\\u0017\r2*\u0017", "cdFFr");
        GuiScreenAddServer.I[0x99 ^ 0x90] = I("9<!\u0018\u0006*. 9M=61.\u0011\u00169(.", "XXEKc");
        GuiScreenAddServer.I[0x73 ^ 0x79] = I("%5\u001d\u0010,6'\u001c1g!?\r&;\r!", "DQyCI");
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.serverIPField.mouseClicked(n, n2, n3);
        this.serverNameField.mouseClicked(n, n2, n3);
    }
}
