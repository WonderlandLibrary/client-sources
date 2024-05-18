package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import optfine.*;
import java.io.*;
import net.minecraft.client.resources.*;

public class GuiVideoSettings extends GuiScreen
{
    private int lastMouseX;
    protected String screenTitle;
    private long mouseStillTime;
    private static GameSettings.Options[] videoOptions;
    private int lastMouseY;
    private static final String[] I;
    private GuiScreen parentGuiScreen;
    private GameSettings guiGameSettings;
    private boolean is64bit;
    private static final String __OBFID;
    
    public static int getButtonWidth(final GuiButton guiButton) {
        return guiButton.width;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            final int guiScale = this.guiGameSettings.guiScale;
            if (guiButton.id < 96 + 195 - 109 + 18 && guiButton instanceof GuiOptionButton) {
                this.guiGameSettings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 158 + 108 - 207 + 141) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.guiGameSettings.guiScale != guiScale) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
            if (guiButton.id == 172 + 16 - 149 + 162) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiDetailSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 23 + 122 - 20 + 77) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiQualitySettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 143 + 197 - 158 + 29) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiAnimationSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 49 + 159 - 21 + 25) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiPerformanceSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 166 + 40 - 199 + 215) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiOtherSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == GameSettings.Options.AO_LEVEL.ordinal()) {
                return;
            }
        }
    }
    
    private String[] getTooltipLines(final String s) {
        String[] array2;
        if (s.equals(GuiVideoSettings.I[0x9E ^ 0x93])) {
            final String[] array = array2 = new String[0x8 ^ 0xD];
            array["".length()] = GuiVideoSettings.I[0x39 ^ 0x37];
            array[" ".length()] = GuiVideoSettings.I[0x8 ^ 0x7];
            array["  ".length()] = GuiVideoSettings.I[0x92 ^ 0x82];
            array["   ".length()] = GuiVideoSettings.I[0x95 ^ 0x84];
            array[0xB9 ^ 0xBD] = GuiVideoSettings.I[0x1F ^ 0xD];
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x97 ^ 0x84])) {
            final String[] array3 = array2 = new String[0x47 ^ 0x4F];
            array3["".length()] = GuiVideoSettings.I[0xBF ^ 0xAB];
            array3[" ".length()] = GuiVideoSettings.I[0xBA ^ 0xAF];
            array3["  ".length()] = GuiVideoSettings.I[0x55 ^ 0x43];
            array3["   ".length()] = GuiVideoSettings.I[0xD1 ^ 0xC6];
            array3[0x40 ^ 0x44] = GuiVideoSettings.I[0x44 ^ 0x5C];
            array3[0x17 ^ 0x12] = GuiVideoSettings.I[0x23 ^ 0x3A];
            array3[0x22 ^ 0x24] = GuiVideoSettings.I[0xDE ^ 0xC4];
            array3[0x66 ^ 0x61] = GuiVideoSettings.I[0x4 ^ 0x1F];
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x87 ^ 0x9B])) {
            final String[] array4 = array2 = new String[0x81 ^ 0x85];
            array4["".length()] = GuiVideoSettings.I[0xDE ^ 0xC3];
            array4[" ".length()] = GuiVideoSettings.I[0x19 ^ 0x7];
            array4["  ".length()] = GuiVideoSettings.I[0xD8 ^ 0xC7];
            array4["   ".length()] = GuiVideoSettings.I[0x89 ^ 0xA9];
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0xBC ^ 0x9D])) {
            final String[] array5 = array2 = new String[0x78 ^ 0x7C];
            array5["".length()] = GuiVideoSettings.I[0x28 ^ 0xA];
            array5[" ".length()] = GuiVideoSettings.I[0x6F ^ 0x4C];
            array5["  ".length()] = GuiVideoSettings.I[0x82 ^ 0xA6];
            array5["   ".length()] = GuiVideoSettings.I[0x80 ^ 0xA5];
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x6B ^ 0x4D])) {
            final String[] array6 = array2 = new String[0xC7 ^ 0xC1];
            array6["".length()] = GuiVideoSettings.I[0x41 ^ 0x66];
            array6[" ".length()] = GuiVideoSettings.I[0xA ^ 0x22];
            array6["  ".length()] = GuiVideoSettings.I[0x9 ^ 0x20];
            array6["   ".length()] = GuiVideoSettings.I[0x99 ^ 0xB3];
            array6[0x70 ^ 0x74] = GuiVideoSettings.I[0x38 ^ 0x13];
            array6[0x7A ^ 0x7F] = GuiVideoSettings.I[0x75 ^ 0x59];
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0xB2 ^ 0x9F])) {
            final String[] array7 = array2 = new String["  ".length()];
            array7["".length()] = GuiVideoSettings.I[0x9C ^ 0xB2];
            array7[" ".length()] = GuiVideoSettings.I[0x9D ^ 0xB2];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x53 ^ 0x63])) {
            final String[] array8 = array2 = new String["  ".length()];
            array8["".length()] = GuiVideoSettings.I[0x70 ^ 0x41];
            array8[" ".length()] = GuiVideoSettings.I[0xBB ^ 0x89];
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0xA2 ^ 0x91])) {
            final String[] array9 = array2 = new String["  ".length()];
            array9["".length()] = GuiVideoSettings.I[0x5D ^ 0x69];
            array9[" ".length()] = GuiVideoSettings.I[0x16 ^ 0x23];
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x58 ^ 0x6E])) {
            final String[] array10 = array2 = new String[0x64 ^ 0x62];
            array10["".length()] = GuiVideoSettings.I[0x4 ^ 0x33];
            array10[" ".length()] = GuiVideoSettings.I[0x11 ^ 0x29];
            array10["  ".length()] = GuiVideoSettings.I[0xA ^ 0x33];
            array10["   ".length()] = GuiVideoSettings.I[0x4B ^ 0x71];
            array10[0xA7 ^ 0xA3] = GuiVideoSettings.I[0x2F ^ 0x14];
            array10[0xC ^ 0x9] = GuiVideoSettings.I[0x7E ^ 0x42];
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x72 ^ 0x4F])) {
            final String[] array11 = array2 = new String[0x5B ^ 0x5D];
            array11["".length()] = GuiVideoSettings.I[0xFB ^ 0xC5];
            array11[" ".length()] = GuiVideoSettings.I[0x41 ^ 0x7E];
            array11["  ".length()] = GuiVideoSettings.I[0xCA ^ 0x8A];
            array11["   ".length()] = GuiVideoSettings.I[0x3D ^ 0x7C];
            array11[0x8F ^ 0x8B] = GuiVideoSettings.I[0x22 ^ 0x60];
            array11[0x17 ^ 0x12] = GuiVideoSettings.I[0x65 ^ 0x26];
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x2B ^ 0x6F])) {
            final String[] array12 = array2 = new String[0x57 ^ 0x53];
            array12["".length()] = GuiVideoSettings.I[0x71 ^ 0x34];
            array12[" ".length()] = GuiVideoSettings.I[0x42 ^ 0x4];
            array12["  ".length()] = GuiVideoSettings.I[0x1D ^ 0x5A];
            array12["   ".length()] = GuiVideoSettings.I[0xD2 ^ 0x9A];
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x2A ^ 0x63])) {
            final String[] array13 = array2 = new String[0xAB ^ 0xAE];
            array13["".length()] = GuiVideoSettings.I[0xCB ^ 0x81];
            array13[" ".length()] = GuiVideoSettings.I[0x60 ^ 0x2B];
            array13["  ".length()] = GuiVideoSettings.I[0x2C ^ 0x60];
            array13["   ".length()] = GuiVideoSettings.I[0xE ^ 0x43];
            array13[0x6E ^ 0x6A] = GuiVideoSettings.I[0xE7 ^ 0xA9];
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x33 ^ 0x7C])) {
            final String[] array14 = array2 = new String[0xA3 ^ 0xAB];
            array14["".length()] = GuiVideoSettings.I[0x68 ^ 0x38];
            array14[" ".length()] = GuiVideoSettings.I[0x45 ^ 0x14];
            array14["  ".length()] = GuiVideoSettings.I[0x52 ^ 0x0];
            array14["   ".length()] = GuiVideoSettings.I[0x17 ^ 0x44];
            array14[0x8A ^ 0x8E] = GuiVideoSettings.I[0xDB ^ 0x8F];
            array14[0x68 ^ 0x6D] = GuiVideoSettings.I[0x73 ^ 0x26];
            array14[0x21 ^ 0x27] = GuiVideoSettings.I[0x10 ^ 0x46];
            array14[0x7F ^ 0x78] = GuiVideoSettings.I[0xE4 ^ 0xB3];
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x63 ^ 0x3B])) {
            final String[] array15 = array2 = new String["   ".length()];
            array15["".length()] = GuiVideoSettings.I[0xC3 ^ 0x9A];
            array15[" ".length()] = GuiVideoSettings.I[0xF7 ^ 0xAD];
            array15["  ".length()] = GuiVideoSettings.I[0xD6 ^ 0x8D];
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0xDA ^ 0x86])) {
            final String[] array16 = array2 = new String["   ".length()];
            array16["".length()] = GuiVideoSettings.I[0x5E ^ 0x3];
            array16[" ".length()] = GuiVideoSettings.I[0x1A ^ 0x44];
            array16["  ".length()] = GuiVideoSettings.I[0xF1 ^ 0xAE];
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (s.equals(GuiVideoSettings.I[0x6B ^ 0xB])) {
            final String[] array17 = array2 = new String[0x35 ^ 0x31];
            array17["".length()] = GuiVideoSettings.I[0xE8 ^ 0x89];
            array17[" ".length()] = GuiVideoSettings.I[0x44 ^ 0x26];
            array17["  ".length()] = GuiVideoSettings.I[0xCD ^ 0xAE];
            array17["   ".length()] = GuiVideoSettings.I[0xA2 ^ 0xC6];
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < this.buttonList.size()) {
            final GuiButton guiButton = this.buttonList.get(i);
            int n3;
            if (n >= guiButton.xPosition && n2 >= guiButton.yPosition && n < guiButton.xPosition + guiButton.width && n2 < guiButton.yPosition + guiButton.height) {
                n3 = " ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            if (n3 != 0) {
                return guiButton;
            }
            ++i;
        }
        return null;
    }
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        final FontRenderer fontRendererObj = this.fontRendererObj;
        final String screenTitle = this.screenTitle;
        final int n2 = this.width / "  ".length();
        int n3;
        if (this.is64bit) {
            n3 = (0x2A ^ 0x3E);
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            n3 = (0xA5 ^ 0xA0);
        }
        this.drawCenteredString(fontRendererObj, screenTitle, n2, n3, 4891480 + 5530798 + 2882965 + 3471972);
        if (this.is64bit || this.guiGameSettings.renderDistanceChunks > (0x8A ^ 0x82)) {}
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= (0xA ^ 0xF) && Math.abs(lastMouseY - this.lastMouseY) <= (0x4C ^ 0x49)) {
            if (System.currentTimeMillis() >= this.mouseStillTime + (202 + 86 - 10 + 422)) {
                final int n4 = this.width / "  ".length() - (51 + 26 - 7 + 80);
                int n5 = this.height / (0x51 ^ 0x57) - (0x11 ^ 0x14);
                if (lastMouseY <= n5 + (0xC0 ^ 0xA2)) {
                    n5 += 105;
                }
                final int n6 = n4 + (133 + 90 - 167 + 94) + (85 + 139 - 211 + 137);
                final int n7 = n5 + (0xFC ^ 0xA8) + (0x2F ^ 0x25);
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY);
                if (selectedButton != null) {
                    final String[] tooltipLines = this.getTooltipLines(this.getButtonName(selectedButton.displayString));
                    if (tooltipLines == null) {
                        return;
                    }
                    this.drawGradientRect(n4, n5, n6, n7, -(319823823 + 317641754 - 211431744 + 110837079), -(458558483 + 463397411 - 679852320 + 294767338));
                    int i = "".length();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    while (i < tooltipLines.length) {
                        this.fontRendererObj.drawStringWithShadow(tooltipLines[i], n4 + (0x9 ^ 0xC), n5 + (0xBB ^ 0xBE) + i * (0x15 ^ 0x1E), 5637431 + 14440388 - 12508538 + 6970972);
                        ++i;
                    }
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
            }
        }
        else {
            this.lastMouseX = lastMouseX;
            this.lastMouseY = lastMouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    public static int getButtonHeight(final GuiButton guiButton) {
        return guiButton.height;
    }
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(0x7F ^ 0x45);
        String substring;
        if (index < 0) {
            substring = s;
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), index);
        }
        return substring;
    }
    
    static {
        I();
        __OBFID = GuiVideoSettings.I["".length()];
        final GameSettings.Options[] videoOptions = new GameSettings.Options[0x77 ^ 0x7A];
        videoOptions["".length()] = GameSettings.Options.GRAPHICS;
        videoOptions[" ".length()] = GameSettings.Options.RENDER_DISTANCE;
        videoOptions["  ".length()] = GameSettings.Options.AMBIENT_OCCLUSION;
        videoOptions["   ".length()] = GameSettings.Options.FRAMERATE_LIMIT;
        videoOptions[0xAE ^ 0xAA] = GameSettings.Options.AO_LEVEL;
        videoOptions[0x68 ^ 0x6D] = GameSettings.Options.VIEW_BOBBING;
        videoOptions[0x43 ^ 0x45] = GameSettings.Options.GUI_SCALE;
        videoOptions[0x4F ^ 0x48] = GameSettings.Options.USE_VBO;
        videoOptions[0x4E ^ 0x46] = GameSettings.Options.GAMMA;
        videoOptions[0x48 ^ 0x41] = GameSettings.Options.BLOCK_ALTERNATIVES;
        videoOptions[0x71 ^ 0x7B] = GameSettings.Options.FOG_FANCY;
        videoOptions[0xB6 ^ 0xBD] = GameSettings.Options.FOG_START;
        videoOptions[0x6E ^ 0x62] = GameSettings.Options.ANAGLYPH;
        GuiVideoSettings.videoOptions = videoOptions;
    }
    
    public GuiVideoSettings(final GuiScreen parentGuiScreen, final GameSettings guiGameSettings) {
        this.screenTitle = GuiVideoSettings.I[" ".length()];
        this.lastMouseX = "".length();
        this.lastMouseY = "".length();
        this.mouseStillTime = 0L;
        this.parentGuiScreen = parentGuiScreen;
        this.guiGameSettings = guiGameSettings;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0xA0 ^ 0xC5])["".length()] = I("\"\u000b-wrQwBpsY", "aGrGB");
        GuiVideoSettings.I[" ".length()] = I("\u001e\r\u0006\u000b!h7\u0007\u001a:!\n\u0005\u001d", "HdbnN");
        GuiVideoSettings.I["  ".length()] = I(";\u0019>'\u0016:\u001ad8\u00100\f%\u001a\u0010 \u0005/", "TiJNy");
        GuiVideoSettings.I["   ".length()] = I("0\u0019\u001cI11\u000f\u001aI4\"\u0018\u0013I=,\b\u0017\u000b", "ClrgP");
        GuiVideoSettings.I[0x83 ^ 0x87] = I(";!\u0015e1:#V=5v,\u0011?57*\u001d", "XNxKX");
        GuiVideoSettings.I[0x86 ^ 0x83] = I("\u001c\u0000c\n8\u0010\u001b", "ssMkJ");
        GuiVideoSettings.I[0x84 ^ 0x82] = I("o`", "YTQKo");
        GuiVideoSettings.I[0x99 ^ 0x9E] = I("\u0006\u0005\t5.#\tFwi", "WphYG");
        GuiVideoSettings.I[0x73 ^ 0x7B] = I("'\"$\u0017%\u000f4~Xb", "cGPvL");
        GuiVideoSettings.I[0x77 ^ 0x7E] = I("\u001b3\u00165\u001b9;\u0005=\u0017.xJ}", "KVdSt");
        GuiVideoSettings.I[0xAE ^ 0xA4] = I("-': #\u0018 <#1Bg}", "lISMB");
        GuiVideoSettings.I[0x60 ^ 0x6B] = I("\u001c\u0004\u0007\u0004\b}^A", "Spoaz");
        GuiVideoSettings.I[0xC8 ^ 0xC4] = I("%\u001f-k\u0010-\u0004!", "BjDEt");
        GuiVideoSettings.I[0x8 ^ 0x5] = I("\u0012$+\u0012\u000e<59", "UVJbf");
        GuiVideoSettings.I[0x55 ^ 0x5B] = I(";;\u0017\u000f,\u0001r\u0015\u000f,\u0001;\u0010\u0003", "mRdzM");
        GuiVideoSettings.I[0x4 ^ 0xB] = I("ns\u00167\u000b:sp{X\"<'3\nn\"%7\u0014'')zX(2#\"\u001d<", "NSPVx");
        GuiVideoSettings.I[0x78 ^ 0x68] = I("Wu)\"#\u0014,Onm\u001f<\b+(\u0005u\u001e6,\u001b<\u001b:aW&\u0003,:\u0012'", "wUoCM");
        GuiVideoSettings.I[0xB8 ^ 0xA9] = I("\u0001\t7 4'\u0012v:;'A7>#'\u0000$/=!\u0004v!5b\u0002:!&&\u0012zn?'\u0000 + nA!/''\u0013z", "BaVNS");
        GuiVideoSettings.I[0xBF ^ 0xAD] = I("\u0005\u0004\u0014<)\u0001\u001fU9(\u0012L\u0012*'\u0005\u001fU+/\u0012\t\u0006v", "vluXF");
        GuiVideoSettings.I[0xA6 ^ 0xB5] = I("1 \u0006)0\u0011e,$&\u0017$\u0006.0", "cEhMU");
        GuiVideoSettings.I[0x23 ^ 0x37] = I("\u0011\u000f\u0015/4+\u0003F\"?4\u0012\u0007(5\"", "GffFV");
        GuiVideoSettings.I[0xD7 ^ 0xC2] = I("Uo\\Y\"\u001c!\u0017Y[U|\\\u0014V])\u000f\n\u0002\u0010<\u001aP", "uOnyv");
        GuiVideoSettings.I[0x97 ^ 0x81] = I("LTYl:\u0004\u001b\u001f8IAT[x\u0004L\\\u000b-\u001a\u0018\u0011\u001fe", "ltmLi");
        GuiVideoSettings.I[0x78 ^ 0x6F] = I("kWLy\f$\u0005\u00198.kZThps\u001a", "KwtYB");
        GuiVideoSettings.I[0x86 ^ 0x9E] = I("BXppq$\u00193f|BJtp<BP2*>\u0015\u001d3o", "bxAFQ");
        GuiVideoSettings.I[0x12 ^ 0xB] = I("DeBSC!=\u0005\u0013\u0006\t QLCQtC\fCL6\u001d\u000e\u0014\u00016\u0005@J", "dEqac");
        GuiVideoSettings.I[0xA7 ^ 0xBD] = I("\u0000\t#A\t,\u00154\u0004!1A0\b)#A\"\b? \u0000(\u0002)t\b5A:1\u0013?A>1\u0012)\u0014>7\u0004f\u0005)9\u0000(\u0005%:\u0006g", "TaFaL");
        GuiVideoSettings.I[0xB4 ^ 0xAF] = I("!8.\u0003\u0010\u0004y-\u0000\u0010\u0005ys@U180V\u0014\u0005<b\u0019\u001b\u001b b\u0013\u0013\u0011<!\u0002\u001c\u0001<b\u001f\u001bW5-\u0015\u0014\u001by5\u0019\u0007\u001b=1X", "wYBvu");
        GuiVideoSettings.I[0xDC ^ 0xC0] = I("7\u001f\u0017#\u0017\fR4%\u0004\f\u0006\u0011\"\u0004", "drxLc");
        GuiVideoSettings.I[0x8F ^ 0x92] = I("\u0003!7\u0019,8l4\u001f?881\u0018?", "PLXvX");
        GuiVideoSettings.I[0x46 ^ 0x58] = I("Zy#\u0010\"ZtL8\u000bZ*\u00019\u000b\u000e1L:\r\u001d1\u0018?\n\u001dyD0\u0005\t-\t$M", "zYlVd");
        GuiVideoSettings.I[0x82 ^ 0x9D] = I("JL\u0014\b\u0019\u0003\u0001,\fWGL*\b\u001a\u001a\u0000<A\u0004\u0007\u00036\u0015\u001fJ\u00000\u0006\u001f\u001e\u00057\u0006WB\u001f5\u000e\u0000\u000f\u001ep", "jlYaw");
        GuiVideoSettings.I[0x64 ^ 0x44] = I("ID\u0002\n3\u0000\t:\u0006kDD,\u0004&\u0019\b*\u0013k\u001a\t \u0004?\u0001D#\u0002,\u0001\u0010&\u0005,IL<\u0007$\u001e\u0001<\u001fb", "idOkK");
        GuiVideoSettings.I[0x36 ^ 0x17] = I("=.;\u0015\u0017\u0006c\u0018\u0013\u0004\u00067=\u0014\u0004N\u000f1\f\u0006\u0002", "nCTzc");
        GuiVideoSettings.I[0xE ^ 0x2C] = I("?\u001d(\u00193\u0004P+\u001f \u0004\u0004.\u0018 L\u001c\"\u0000\"\u0000", "lpGvG");
        GuiVideoSettings.I[0xAF ^ 0x8C] = I("wF:\n\twKU\" w\u0015\u001d-+8\u0011\u0006", "WfuLO");
        GuiVideoSettings.I[0xE ^ 0x2A] = I("EY|TMETi\b\u0001\u0002\u0011=D\u001b\r\u0018-\u000b\u001f\u0016", "eyIdh");
        GuiVideoSettings.I[0x20 ^ 0x5] = I("kLtYznLhI.*\u001e.I9#\r!\u0006=8", "KlEiJ");
        GuiVideoSettings.I[0x2A ^ 0xC] = I("<\u0011\"u\u0002\u0003\u0011706\u0010\u0004?", "qpZUD");
        GuiVideoSettings.I[0x2B ^ 0xC] = I("9%9r\u001f\u0006%,7\u000b\u00150$", "tDARy");
        GuiVideoSettings.I[0x87 ^ 0xAF] = I("EH\u000f7+\u000b\u000byIr\t\u00014\r&E\u001c6D?\n\u00060\u0010=\u0017H?\u00163\b\r+\u0005&\u0000HqRbIHjT~EZiM", "ehYdR");
        GuiVideoSettings.I[0x73 ^ 0x5A] = I("FMrbGSXgbU\u0010\f5&\u0014\u0004\u0001\"", "fmGOu");
        GuiVideoSettings.I[0x6 ^ 0x2C] = I("vl\u0011(.?!-2'2lif,9l(//?8dn$7?0#1\"e", "VLDFB");
        GuiVideoSettings.I[0x96 ^ 0xBD] = I("\r \rf/+)\u0005#;8<\rf%0%\u00012i=-\u000b4,8;\r5i- \rf\u000f\t\u001bH#?<&H//", "YHhFI");
        GuiVideoSettings.I[0x55 ^ 0x79] = I("\u001a&\u000eT;\u0007#\u0002\u0000w\u0018/\u0007\u00012N'\u0018T9\u0001:K\u00062\u000f-\u0003\u00113@", "nNktW");
        GuiVideoSettings.I[0x9A ^ 0xB7] = I(";,\u0010\u001fd/*\u0017\n-\u0003\"", "mEuhD");
        GuiVideoSettings.I[0x3 ^ 0x2D] = I("\u00158\"\u0014U*21\u001d\u001c+#9\u0012U58&\u0014\u0018=9$_", "XWPqu");
        GuiVideoSettings.I[0x96 ^ 0xB9] = I("\r:&*h/!**/z?*4%;\"0d;?&c-<z&,d\u0007\u001c\u0014c\"'(r!!;.r1!;/>77f", "ZRCDH");
        GuiVideoSettings.I[0x34 ^ 0x4] = I("\u0014\u001e\u000ef10*+#", "SKGFb");
        GuiVideoSettings.I[0xD ^ 0x3C] = I("\u001d6%w\u001d9\u0002\u00002", "ZclWN");
        GuiVideoSettings.I[0x5D ^ 0x6F] = I("\u0005:\n\u0018\u001b3%K3\"\u001fw\u0006\u001d\u0010>#K\u0016\u0012v1\n\u0007\u00033%", "VWktw");
        GuiVideoSettings.I[0x7D ^ 0x4E] = I("\u001e\u001d0\u0006\u0004?X\u0016\u0015\u00199\r0\u0015\u0012", "MxBpa");
        GuiVideoSettings.I[0x6E ^ 0x5A] = I("\u001633\u000e<7v5\u001d!1#3\u001d*", "EVAxY");
        GuiVideoSettings.I[0x99 ^ 0xAC] = I("\u0002\u0000\u0014u\u0011?\u0016Q'\u0000$\u001c\u0004'\u00062S\u00014\u0006<S\u00030\u00068\u001e\u001c0\u000b3\u0016\u0015u\u0007.S\u0005=\u0000w\u0000\u0014'\u00132\u0001", "WsqUe");
        GuiVideoSettings.I[0x37 ^ 0x1] = I("6\"5\u001b$\u0014#'Z\u0005\u0007#-=\u0006", "wFCzJ");
        GuiVideoSettings.I[0xA9 ^ 0x9E] = I(" 2\u0015$\t\u0010w\u0000/\u000eD%\u0004/\u000e\u0001%A.\u0004\b.A7\u0003\u0017>\u0003-\u000fD0\u0004.\u0007\u0001#\u00138", "dWaAj");
        GuiVideoSettings.I[0x49 ^ 0x71] = I("Ic '\bInO\u0000\"\u0005c\b\u0004!\u0004&\u001b\u00137I*\u001cA<\f-\u000b\u0004<\f'OI=\u0005,\u0018\u0004<@", "iCoaN");
        GuiVideoSettings.I[0xB6 ^ 0x8F] = I("Ot?-\u0000\u001btTl\u001c\u00018\u0000l\u0005\u0006'\u0010.\u001f\nt\u001e)\u001c\u00021\r>\nO=\nl\u0001\n:\u001d)\u0001\n0Yd\u0015\u000e'\r)\u0000\u001b}", "oTyLs");
        GuiVideoSettings.I[0x56 ^ 0x6C] = I("GU\u0017\t/\u0004\fqEa\u0004\u001a?\u001b$\u0015\u00030\u001c(\u0011\u0010}H \u0011\u001a8\f2G\u00038\u001b4\u0006\u0019q\t3\u0013\u001c7\t\"\u0013\u0006q@'\u0006\u0006%\r3N", "guQhA");
        GuiVideoSettings.I[0x65 ^ 0x5E] = I("\u0019\u0000\rT?=\u001c\u0001\u001b>m\u0001\u001bT1;\t\u0001\u00181/\u0004\rT?#\u0004\u0011T9+H\u0001\u0000p$\u001bH\u0007%=\u0018\u0007\u0006$(\fH\u0016)m\u001c\u0000\u0011p", "MhhtP");
        GuiVideoSettings.I[0xFA ^ 0xC6] = I(".$\u000e=\u0019 5O.\u0010;2A", "IVoMq");
        GuiVideoSettings.I[0x47 ^ 0x7A] = I("\u0013\u0001\u000e", "UnisE");
        GuiVideoSettings.I[0x2B ^ 0x15] = I("\"\u0000&v$\u001d\u001f$", "doAVP");
        GuiVideoSettings.I[0x87 ^ 0xB8] = I("AE\b\u0013\u0001\u0015EcR\u0014\u0000\u0016:\u0017\u0000A\u0003!\u0015", "aeNrr");
        GuiVideoSettings.I[0x77 ^ 0x37] = I("ty#3\n7 E\u007fD'5\n%\u0001&y\u0003=\u0003xy\t=\u000b?*E0\u0001 -\u0000 ", "TYeRd");
        GuiVideoSettings.I[0x41 ^ 0x0] = I("nt\u001e\u0000\u000bnyq(\"n2>!an2059+'%", "NTQFM");
        GuiVideoSettings.I[0xC8 ^ 0x8A] = I("\"2\bu\u0017\u00174\u000e,Q\u00105\nu\u0018\u0005z\f#\u0010\u001f6\f7\u001d\u0013z\u0002;\u001d\u000fz\u00043Q\u001f.M<\u0002V)\u0018%\u0001\u0019(\u00190\u0015V8\u0014u\u0005\u001e?M", "vZmUq");
        GuiVideoSettings.I[0x67 ^ 0x24] = I("&>31\u0018(/r\"\u00113(|", "ALRAp");
        GuiVideoSettings.I[0xE2 ^ 0xA6] = I("\"\u001a/F!\u0010\u0014:\u0012", "duHfr");
        GuiVideoSettings.I[0x46 ^ 0x3] = I("\u00039/d'17:0", "EVHDT");
        GuiVideoSettings.I[0x2A ^ 0x6C] = I("EV\u007fvaE[o,;\u0000V)74E\u0005;9!\u0011\u0005o66\u0004\u0004o,;\u0000V?42\u001c\u0013=", "evOXS");
        GuiVideoSettings.I[0xD5 ^ 0x92] = I("ihJJJieZ\u0010\u001a,h\u001c\u000b\u0015i;\u000e\u0005\u0000=;Z\u0002\u0013;h\u001c\u0016\u001d$h\u000e\f\u0017i8\u0016\u0005\u000b,:", "IHzdr");
        GuiVideoSettings.I[0x62 ^ 0x2A] = I("\u0001\u0001\u000b\u0004Y:\u0019\u0016\u001e\u0016;I\u0017\u0004\f4\u0005\u000e\u000eY1\u0006\u0007\u0004Y;\u0006\u0016W\u00183\u000f\u0007\u0014\ru\u001d\n\u0012Y%\f\u0010\u0011\u0016'\u0004\u0003\u0019\u001a0G", "Uibwy");
        GuiVideoSettings.I[0x3A ^ 0x73] = I("#\b\u0019=\u001d\u0015\u0014\u0015)\u0006", "azpZu");
        GuiVideoSettings.I[0x8 ^ 0x42] = I("\u001d\u0002\u000b\n=5\u001f\r\u000bx \u0004\rX:&\u0005\u000f\u0010,:\t\u001b\u000bx;\nH\u001c9&\u0007\r\nx;\u000e\u0002\u001d; \u001f", "TlhxX");
        GuiVideoSettings.I[0x4E ^ 0x5] = I("uV#\n\u0003u[L?14\u0018\b-71V\u000e>,2\u001e\u0018\" &\u0005", "UvlLE");
        GuiVideoSettings.I[0xEC ^ 0xA0] = I("ttyI^qteY\u00035,!\u0014\u001b9t*\u000b\u00073<<\u0017\u000b''h\u001f\u0001&t,\u0018\u001c?1:Y\u00016>-\u001a\u001a'", "TTHyn");
        GuiVideoSettings.I[0xD7 ^ 0x9A] = I("\u0019\u001d\u00079q\"\u0005\u001a#>#\u0006N.>(\u0006N$>9U\r\"0#\u0012\u000bj%%\u0010N(#$\u0012\u0006>?(\u0006\u001dj>+U", "MunJQ");
        GuiVideoSettings.I[0x20 ^ 0x6E] = I("\u000498\u000e\rB.8\u0003\u0017\tl;\u0000\u001e\u0007/ \u0011", "bLTbt");
        GuiVideoSettings.I[0x8B ^ 0xC4] = I("!\u000f/\u0019?B+5\u00160\u000b\t=", "bgZwT");
        GuiVideoSettings.I[0x1E ^ 0x4E] = I("\f\u000f\u00059\u0005o+\u001f6\n&\t\u0017", "OgpWn");
        GuiVideoSettings.I[0xE0 ^ 0xB1] = I("vx>\u0015\u00017-\u0016\u0004G{x\u000f\u001e\u0014\"9\u0018\u001c\u0002v\u001e*#G!0\u001f\u001eG:7\u001b\u0014\u000e8?Z\u0013\u000f#6\u0011\u0003", "VXzpg");
        GuiVideoSettings.I[0x93 ^ 0xC1] = I("rA8;\u001e=\u0015\u0003v\\r\u0012\u001f7\u0013>\u0004K\u0010!\u0001", "RakVq");
        GuiVideoSettings.I[0x91 ^ 0xC2] = I("Oo'-&\u001b&G\u001b%\u001d*Juj\u001c;\u000b:&\no,\b\u0019CoY j\t.\u0019,/\u001do\u001d78\u0003+J4%\u000e+\u00036-", "oOjXJ");
        GuiVideoSettings.I[0xCC ^ 0x98] = I("\u001d,'\u0006\"&a)\u00072n\f=\u0005\"'l\u000b\u0006$+a:\f;!7-I\"&$h\u001a\";5<\f$'//I7 %h", "NAHiV");
        GuiVideoSettings.I[0x96 ^ 0xC3] = I("7\u0005\u0004 \u00004\u0004A&\u001b$\u0004\u0004!Z3\u000eA&\u0012$\u0019\ne\u0016>\u0016\u0005,\u00146Y", "QwaEz");
        GuiVideoSettings.I[0x7F ^ 0x29] = I("4&=;*T\u0010>=&Y00!c\n#4*'Y&!op\u0001s%'&Y$>=/\u001ds= \"\u001d:?(c\u0018=5", "ySQOC");
        GuiVideoSettings.I[0x97 ^ 0xC0] = I("\f<\n\u0006\u0012\u0004!\fT15\u0001I\u0016\u000eE'\u001a\u001d\u0019\u0002r\bT\u0004\u00001\u0006\u001a\u0013E\u00119!W\u0006=\u001b\u0011Y", "eRitw");
        GuiVideoSettings.I[0xD4 ^ 0x8C] = I("\u0013$\u000e0'<)\u000e0u\u0010$\u00156>!", "RHzUU");
        GuiVideoSettings.I[0x3D ^ 0x64] = I("68\u001c  \u00195\u001c r58\u0007&9\u0004", "wThER");
        GuiVideoSettings.I[0x61 ^ 0x3B] = I("\u0007\u001e&\u0015t3\u00017\u0003&<\f7\u000f\"7M!\n;1\u0006c\u000b;6\b/\u0015t4\u00021F'=\u0000&F6>\u0002 \r'|", "RmCfT");
        GuiVideoSettings.I[0xCF ^ 0x94] = I("(\u0013\u001e\u0015!\b\u0005N\u001f!L\u0002\u0006\u0015o\u001f\u0013\u0002\u0015,\u0018\u0013\nP=\t\u0005\u0001\u0005=\u000f\u0013N\u0000.\u000f\u001d@", "lvnpO");
        GuiVideoSettings.I[0x63 ^ 0x3F] = I("\u001a\u001f\u0013o$\r#\u0005", "OlvOr");
        GuiVideoSettings.I[0x38 ^ 0x65] = I("\u001f\u0004:&)1A\n'*/\u0004:r\u0003+\u000b-18:", "IaHRL");
        GuiVideoSettings.I[0x9A ^ 0xC4] = I(">;\u0011\u0018a\n&T\n-\u001f-\u0006\u0005 \u001f!\u0002\u000ea\u0019-\u001a\u000f$\u0019!\u001a\fa\u0006'\u0010\u000e-K?\u001c\u0002\"\u0003h\u001d\u0018a\u001e;\u0001\n-\u00071", "kHtkA");
        GuiVideoSettings.I[0x1E ^ 0x41] = I("\f#)?3\u0018br~{[r\u007fbv\u001e*;%v\u001e*?k2\u000f$;>:\u001eb(.8\u000e'(\"8\rl", "jBZKV");
        GuiVideoSettings.I[0x3A ^ 0x5A] = I("r't7\u0004 \u00048\u000f\u001a)", "AcTvj");
        GuiVideoSettings.I[0x36 ^ 0x57] = I("j\u0013n\f\u001780\"4\t1", "YWNMy");
        GuiVideoSettings.I[0x3B ^ 0x59] = I("'\f\b\u0010\u0018\u0007\u0011I\u0013T\u0011\u0016\f\u0000\u0011\r\u0011\n\u001d\u0004\u000b\u0001IA0B\u0007\u000f\u0014\u0011\u0001\u0016I\u0007\u0007\u000b\f\u000eR\u0010\u000b\u0004\u000f\u0017\u0006\u0007\f\u001dR\u0017\r\u000e\u0006\u0000\u0007", "bbirt");
        GuiVideoSettings.I[0x29 ^ 0x4A] = I(">(\u0003q\u001d9$\u0019q\u001d!\"_", "XGqQx");
        GuiVideoSettings.I[0xCC ^ 0xA8] = I(">\"\u001a$8\u001e\"\u0018q#\t#F2(\r)K6=\r4\u00184\"L!\u0004#q\u001c5\u0004!4\u001eg\u001d84\u001b.\u00056\u007f", "lGkQQ");
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format(GuiVideoSettings.I["  ".length()], new Object["".length()]);
        this.buttonList.clear();
        this.is64bit = ("".length() != 0);
        final String[] array = new String["   ".length()];
        array["".length()] = GuiVideoSettings.I["   ".length()];
        array[" ".length()] = GuiVideoSettings.I[0x8A ^ 0x8E];
        array["  ".length()] = GuiVideoSettings.I[0x5 ^ 0x0];
        final String[] array2;
        final int length = (array2 = array).length;
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < length) {
            final String property = System.getProperty(array2[i]);
            if (property != null && property.contains(GuiVideoSettings.I[0x8F ^ 0x89])) {
                this.is64bit = (" ".length() != 0);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        "".length();
        if (this.is64bit) {
            "".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            " ".length();
        }
        final GameSettings.Options[] videoOptions = GuiVideoSettings.videoOptions;
        final int length2 = videoOptions.length;
        "".length();
        int j = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (j < length2) {
            final GameSettings.Options options = videoOptions[j];
            if (options != null) {
                final int n = this.width / "  ".length() - (60 + 32 - 0 + 63) + j % "  ".length() * (114 + 144 - 247 + 149);
                final int n2 = this.height / (0x0 ^ 0x6) + (0xA5 ^ 0xB0) * (j / "  ".length()) - (0x4F ^ 0x45);
                if (options.getEnumFloat()) {
                    this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), n, n2, options));
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                else {
                    this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), n, n2, options, this.guiGameSettings.getKeyBinding(options)));
                }
            }
            ++j;
        }
        int n3 = this.height / (0xB6 ^ 0xB0) + (0x3C ^ 0x29) * (j / "  ".length()) - (0x2A ^ 0x20);
        "".length();
        this.buttonList.add(new GuiOptionButton(102 + 11 - 80 + 169, this.width / "  ".length() - (57 + 15 + 21 + 62) + (12 + 28 + 56 + 64), n3, GuiVideoSettings.I[0xBB ^ 0xBC]));
        n3 += 21;
        this.buttonList.add(new GuiOptionButton(84 + 17 - 95 + 195, this.width / "  ".length() - (116 + 54 - 156 + 141) + "".length(), n3, GuiVideoSettings.I[0x69 ^ 0x61]));
        this.buttonList.add(new GuiOptionButton(194 + 148 - 168 + 38, this.width / "  ".length() - (116 + 130 - 178 + 87) + (52 + 23 + 47 + 38), n3, GuiVideoSettings.I[0x20 ^ 0x29]));
        n3 += 21;
        this.buttonList.add(new GuiOptionButton(3 + 66 - 62 + 204, this.width / "  ".length() - (102 + 53 - 102 + 102) + "".length(), n3, GuiVideoSettings.I[0x82 ^ 0x88]));
        this.buttonList.add(new GuiOptionButton(125 + 168 - 111 + 40, this.width / "  ".length() - (61 + 77 + 17 + 0) + (34 + 107 - 96 + 115), n3, GuiVideoSettings.I[0x16 ^ 0x1D]));
        this.buttonList.add(new GuiButton(17 + 32 + 34 + 117, this.width / "  ".length() - (0xDC ^ 0xB8), this.height / (0x8B ^ 0x8D) + (64 + 13 - 1 + 92) + (0x6E ^ 0x65), I18n.format(GuiVideoSettings.I[0x26 ^ 0x2A], new Object["".length()])));
    }
}
