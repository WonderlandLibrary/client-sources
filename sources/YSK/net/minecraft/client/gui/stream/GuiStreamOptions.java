package net.minecraft.client.gui.stream;

import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

public class GuiStreamOptions extends GuiScreen
{
    private static final GameSettings.Options[] field_152316_f;
    private final GameSettings field_152318_h;
    private static final GameSettings.Options[] field_152312_a;
    private static final String[] I;
    private String field_152319_i;
    private final GuiScreen parentScreen;
    private boolean field_152315_t;
    private int field_152314_s;
    private String field_152313_r;
    
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiStreamOptions(final GuiScreen parentScreen, final GameSettings field_152318_h) {
        this.field_152315_t = ("".length() != 0);
        this.parentScreen = parentScreen;
        this.field_152318_h = field_152318_h;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < (0xF5 ^ 0x91) && guiButton instanceof GuiOptionButton) {
                final GameSettings.Options returnEnumOptions = ((GuiOptionButton)guiButton).returnEnumOptions();
                this.field_152318_h.setOptionValue(returnEnumOptions, " ".length());
                guiButton.displayString = this.field_152318_h.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
                if (this.mc.getTwitchStream().isBroadcasting() && returnEnumOptions != GameSettings.Options.STREAM_CHAT_ENABLED && returnEnumOptions != GameSettings.Options.STREAM_CHAT_USER_FILTER) {
                    this.field_152315_t = (" ".length() != 0);
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
            }
            else if (guiButton instanceof GuiOptionSlider) {
                if (guiButton.id == GameSettings.Options.STREAM_VOLUME_MIC.returnEnumOrdinal()) {
                    this.mc.getTwitchStream().updateStreamVolume();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (guiButton.id == GameSettings.Options.STREAM_VOLUME_SYSTEM.returnEnumOrdinal()) {
                    this.mc.getTwitchStream().updateStreamVolume();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (this.mc.getTwitchStream().isBroadcasting()) {
                    this.field_152315_t = (" ".length() != 0);
                }
            }
            if (guiButton.id == 168 + 159 - 151 + 24) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (guiButton.id == 86 + 40 - 123 + 198) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiIngestServers(this));
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_152319_i, this.width / "  ".length(), 0x8 ^ 0x1C, 6981468 + 848974 - 7072752 + 16019525);
        this.drawCenteredString(this.fontRendererObj, this.field_152313_r, this.width / "  ".length(), this.field_152314_s, 16775563 + 16068556 - 31903156 + 15836252);
        if (this.field_152315_t) {
            this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + I18n.format(GuiStreamOptions.I[0x52 ^ 0x56], new Object["".length()]), this.width / "  ".length(), (0x57 ^ 0x43) + this.fontRendererObj.FONT_HEIGHT, 15254521 + 12750894 - 25559875 + 14331675);
        }
        super.drawScreen(n, n2, n3);
    }
    
    static {
        I();
        final GameSettings.Options[] field_152312_a2 = new GameSettings.Options[0x72 ^ 0x7A];
        field_152312_a2["".length()] = GameSettings.Options.STREAM_BYTES_PER_PIXEL;
        field_152312_a2[" ".length()] = GameSettings.Options.STREAM_FPS;
        field_152312_a2["  ".length()] = GameSettings.Options.STREAM_KBPS;
        field_152312_a2["   ".length()] = GameSettings.Options.STREAM_SEND_METADATA;
        field_152312_a2[0x69 ^ 0x6D] = GameSettings.Options.STREAM_VOLUME_MIC;
        field_152312_a2[0x68 ^ 0x6D] = GameSettings.Options.STREAM_VOLUME_SYSTEM;
        field_152312_a2[0x5F ^ 0x59] = GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR;
        field_152312_a2[0x2E ^ 0x29] = GameSettings.Options.STREAM_COMPRESSION;
        field_152312_a = field_152312_a2;
        final GameSettings.Options[] field_152316_f2 = new GameSettings.Options["  ".length()];
        field_152316_f2["".length()] = GameSettings.Options.STREAM_CHAT_ENABLED;
        field_152316_f2[" ".length()] = GameSettings.Options.STREAM_CHAT_USER_FILTER;
        field_152316_f = field_152316_f2;
    }
    
    private static void I() {
        (I = new String[0xBE ^ 0xBB])["".length()] = I("\t2\u0004\u0000+\b1^\u001a0\u0014'\u0011\u0004j\u0012+\u0004\u0005!", "fBpiD");
        GuiStreamOptions.I[" ".length()] = I("\u0004\u0005\u0004\r\u0015\u0005\u0006^\u0017\u000e\u0019\u0010\u0011\tT\b\u001d\u0011\u0010T\u001f\u001c\u0004\b\u001f", "kupdz");
        GuiStreamOptions.I["  ".length()] = I("\u0011\u0005\u001c^\u0017\u0019\u001e\u0010", "vpups");
        GuiStreamOptions.I["   ".length()] = I("7\u001d (\u00066\u001ez2\u001d*\b5,G1\u00033$\u001a,>1-\f;\u0019=.\u0007", "XmTAi");
        GuiStreamOptions.I[0x81 ^ 0x85] = I("\u000338/\u001b\u00020b5\u0000\u001e&-+Z\u000f+-(\u0013\t0", "lCLFt");
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        this.field_152319_i = I18n.format(GuiStreamOptions.I["".length()], new Object["".length()]);
        this.field_152313_r = I18n.format(GuiStreamOptions.I[" ".length()], new Object["".length()]);
        final GameSettings.Options[] field_152312_a;
        final int length2 = (field_152312_a = GuiStreamOptions.field_152312_a).length;
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = field_152312_a[i];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), this.width / "  ".length() - (79 + 55 - 12 + 33) + length % "  ".length() * (58 + 48 + 5 + 49), this.height / (0xA9 ^ 0xAF) + (0x59 ^ 0x41) * (length >> " ".length()), options));
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), this.width / "  ".length() - (5 + 20 + 13 + 117) + length % "  ".length() * (54 + 41 - 27 + 92), this.height / (0x4C ^ 0x4A) + (0x5A ^ 0x42) * (length >> " ".length()), options, this.field_152318_h.getKeyBinding(options)));
            }
            ++length;
            ++i;
        }
        if (length % "  ".length() == " ".length()) {
            ++length;
        }
        this.field_152314_s = this.height / (0xB1 ^ 0xB7) + (0x2C ^ 0x34) * (length >> " ".length()) + (0x49 ^ 0x4F);
        length += 2;
        final GameSettings.Options[] field_152316_f;
        final int length3 = (field_152316_f = GuiStreamOptions.field_152316_f).length;
        int j = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (j < length3) {
            final GameSettings.Options options2 = field_152316_f[j];
            if (options2.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options2.returnEnumOrdinal(), this.width / "  ".length() - (92 + 146 - 112 + 29) + length % "  ".length() * (148 + 139 - 286 + 159), this.height / (0x91 ^ 0x97) + (0x9C ^ 0x84) * (length >> " ".length()), options2));
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionButton(options2.returnEnumOrdinal(), this.width / "  ".length() - (85 + 1 + 32 + 37) + length % "  ".length() * (122 + 59 - 129 + 108), this.height / (0x23 ^ 0x25) + (0x79 ^ 0x61) * (length >> " ".length()), options2, this.field_152318_h.getKeyBinding(options2)));
            }
            ++length;
            ++j;
        }
        this.buttonList.add(new GuiButton(171 + 8 - 133 + 154, this.width / "  ".length() - (152 + 55 - 179 + 127), this.height / (0x84 ^ 0x82) + (65 + 160 - 159 + 102), 17 + 87 + 5 + 41, 0xB5 ^ 0xA1, I18n.format(GuiStreamOptions.I["  ".length()], new Object["".length()])));
        final GuiButton guiButton = new GuiButton(166 + 159 - 199 + 75, this.width / "  ".length() + (0x56 ^ 0x53), this.height / (0xB9 ^ 0xBF) + (129 + 71 - 91 + 59), 51 + 126 - 51 + 24, 0x5F ^ 0x4B, I18n.format(GuiStreamOptions.I["   ".length()], new Object["".length()]));
        int enabled;
        if ((!this.mc.getTwitchStream().isReadyToBroadcast() || this.mc.getTwitchStream().func_152925_v().length <= 0) && !this.mc.getTwitchStream().func_152908_z()) {
            enabled = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            enabled = " ".length();
        }
        guiButton.enabled = (enabled != 0);
        this.buttonList.add(guiButton);
    }
}
