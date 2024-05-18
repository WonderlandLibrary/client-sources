package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class GuiQualitySettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    private long mouseStillTime;
    protected String title;
    private static final String[] I;
    private int lastMouseY;
    private GuiScreen prevScreen;
    private int lastMouseX;
    private GameSettings settings;
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < this.buttonList.size()) {
            final GuiButton guiButton = this.buttonList.get(i);
            final int buttonWidth = GuiVideoSettings.getButtonWidth(guiButton);
            final int buttonHeight = GuiVideoSettings.getButtonHeight(guiButton);
            int n3;
            if (n >= guiButton.xPosition && n2 >= guiButton.yPosition && n < guiButton.xPosition + buttonWidth && n2 < guiButton.yPosition + buttonHeight) {
                n3 = " ".length();
                "".length();
                if (0 >= 1) {
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
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(0x61 ^ 0x5B);
        String substring;
        if (index < 0) {
            substring = s;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), index);
        }
        return substring;
    }
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / "  ".length(), 0x27 ^ 0x33, 3610734 + 6920557 - 560566 + 6806490);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= (0x99 ^ 0x9C) && Math.abs(lastMouseY - this.lastMouseY) <= (0x16 ^ 0x13)) {
            if (System.currentTimeMillis() >= this.mouseStillTime + (435 + 71 - 40 + 234)) {
                final int n2 = this.width / "  ".length() - (131 + 4 - 48 + 63);
                int n3 = this.height / (0xA ^ 0xC) - (0x58 ^ 0x5D);
                if (lastMouseY <= n3 + (0x57 ^ 0x35)) {
                    n3 += 105;
                }
                final int n4 = n2 + (30 + 27 + 12 + 81) + (54 + 106 - 143 + 133);
                final int n5 = n3 + (0x37 ^ 0x63) + (0x62 ^ 0x68);
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY);
                if (selectedButton != null) {
                    final String[] tooltipLines = this.getTooltipLines(this.getButtonName(selectedButton.displayString));
                    if (tooltipLines == null) {
                        return;
                    }
                    this.drawGradientRect(n2, n3, n4, n5, -(152893953 + 533694490 - 491096521 + 341378990), -(440585633 + 247969904 - 258882459 + 107197834));
                    int i = "".length();
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                    while (i < tooltipLines.length) {
                        final String s = tooltipLines[i];
                        int n6 = 14460233 + 3498703 - 12317825 + 8899142;
                        if (s.endsWith(GuiQualitySettingsOF.I["  ".length()])) {
                            n6 = 2700307 + 9543575 - 5625178 + 10101200;
                        }
                        this.fontRendererObj.drawStringWithShadow(s, n2 + (0xB0 ^ 0xB5), n3 + (0xE ^ 0xB) + i * (0x5A ^ 0x51), n6);
                        ++i;
                    }
                    "".length();
                    if (-1 >= 3) {
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 172 + 198 - 278 + 108 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 139 + 166 - 250 + 145) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    public GuiQualitySettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = GuiQualitySettingsOF.I["".length()];
        this.lastMouseX = "".length();
        this.lastMouseY = "".length();
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        final GameSettings.Options[] enumOptions;
        final int length2 = (enumOptions = GuiQualitySettingsOF.enumOptions).length;
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = enumOptions[i];
            final int n = this.width / "  ".length() - (83 + 38 - 8 + 42) + length % "  ".length() * (65 + 126 - 81 + 50);
            final int n2 = this.height / (0xBA ^ 0xBC) + (0xA ^ 0x1F) * (length / "  ".length()) - (0xA7 ^ 0xAD);
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), n, n2, options));
            }
            ++length;
            ++i;
        }
        this.buttonList.add(new GuiButton(137 + 18 - 10 + 55, this.width / "  ".length() - (0x57 ^ 0x33), this.height / (0x2C ^ 0x2A) + (9 + 156 - 58 + 61) + (0xBD ^ 0xB6), I18n.format(GuiQualitySettingsOF.I[" ".length()], new Object["".length()])));
    }
    
    private String[] getTooltipLines(final String s) {
        String[] array2;
        if (s.equals(GuiQualitySettingsOF.I["   ".length()])) {
            final String[] array = array2 = new String[0xA3 ^ 0xA5];
            array["".length()] = GuiQualitySettingsOF.I[0xA6 ^ 0xA2];
            array[" ".length()] = GuiQualitySettingsOF.I[0x33 ^ 0x36];
            array["  ".length()] = GuiQualitySettingsOF.I[0x54 ^ 0x52];
            array["   ".length()] = GuiQualitySettingsOF.I[0x64 ^ 0x63];
            array[0x26 ^ 0x22] = GuiQualitySettingsOF.I[0x21 ^ 0x29];
            array[0x74 ^ 0x71] = GuiQualitySettingsOF.I[0xAD ^ 0xA4];
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x9D ^ 0x97])) {
            final String[] array3 = array2 = new String[0x4 ^ 0x2];
            array3["".length()] = GuiQualitySettingsOF.I[0x25 ^ 0x2E];
            array3[" ".length()] = GuiQualitySettingsOF.I[0xA6 ^ 0xAA];
            array3["  ".length()] = GuiQualitySettingsOF.I[0x76 ^ 0x7B];
            array3["   ".length()] = GuiQualitySettingsOF.I[0x79 ^ 0x77];
            array3[0x5A ^ 0x5E] = GuiQualitySettingsOF.I[0x7D ^ 0x72];
            array3[0x11 ^ 0x14] = GuiQualitySettingsOF.I[0x2 ^ 0x12];
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xBF ^ 0xAE])) {
            final String[] array4 = array2 = new String[0x69 ^ 0x6F];
            array4["".length()] = GuiQualitySettingsOF.I[0x68 ^ 0x7A];
            array4[" ".length()] = GuiQualitySettingsOF.I[0x8F ^ 0x9C];
            array4["  ".length()] = GuiQualitySettingsOF.I[0xBF ^ 0xAB];
            array4["   ".length()] = GuiQualitySettingsOF.I[0x65 ^ 0x70];
            array4[0x8 ^ 0xC] = GuiQualitySettingsOF.I[0x3C ^ 0x2A];
            array4[0x9D ^ 0x98] = GuiQualitySettingsOF.I[0x47 ^ 0x50];
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xDB ^ 0xC3])) {
            final String[] array5 = array2 = new String[0x9 ^ 0x1];
            array5["".length()] = GuiQualitySettingsOF.I[0x63 ^ 0x7A];
            array5[" ".length()] = GuiQualitySettingsOF.I[0xAD ^ 0xB7];
            array5["  ".length()] = GuiQualitySettingsOF.I[0xB4 ^ 0xAF];
            array5["   ".length()] = GuiQualitySettingsOF.I[0x8E ^ 0x92];
            array5[0x3A ^ 0x3E] = GuiQualitySettingsOF.I[0x1C ^ 0x1];
            array5[0x30 ^ 0x35] = GuiQualitySettingsOF.I[0xBF ^ 0xA1];
            array5[0x9C ^ 0x9A] = GuiQualitySettingsOF.I[0x64 ^ 0x7B];
            array5[0x1D ^ 0x1A] = GuiQualitySettingsOF.I[0x10 ^ 0x30];
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x41 ^ 0x60])) {
            final String[] array6 = array2 = new String["   ".length()];
            array6["".length()] = GuiQualitySettingsOF.I[0x84 ^ 0xA6];
            array6[" ".length()] = GuiQualitySettingsOF.I[0x22 ^ 0x1];
            array6["  ".length()] = GuiQualitySettingsOF.I[0x87 ^ 0xA3];
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xB3 ^ 0x96])) {
            final String[] array7 = array2 = new String[0x86 ^ 0x82];
            array7["".length()] = GuiQualitySettingsOF.I[0xE ^ 0x28];
            array7[" ".length()] = GuiQualitySettingsOF.I[0x8F ^ 0xA8];
            array7["  ".length()] = GuiQualitySettingsOF.I[0x6B ^ 0x43];
            array7["   ".length()] = GuiQualitySettingsOF.I[0x39 ^ 0x10];
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xAB ^ 0x81])) {
            final String[] array8 = array2 = new String[0x0 ^ 0x5];
            array8["".length()] = GuiQualitySettingsOF.I[0x67 ^ 0x4C];
            array8[" ".length()] = GuiQualitySettingsOF.I[0x28 ^ 0x4];
            array8["  ".length()] = GuiQualitySettingsOF.I[0x69 ^ 0x44];
            array8["   ".length()] = GuiQualitySettingsOF.I[0x43 ^ 0x6D];
            array8[0x58 ^ 0x5C] = GuiQualitySettingsOF.I[0x5A ^ 0x75];
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x62 ^ 0x52])) {
            final String[] array9 = array2 = new String[0xBD ^ 0xB8];
            array9["".length()] = GuiQualitySettingsOF.I[0x6A ^ 0x5B];
            array9[" ".length()] = GuiQualitySettingsOF.I[0x18 ^ 0x2A];
            array9["  ".length()] = GuiQualitySettingsOF.I[0x7 ^ 0x34];
            array9["   ".length()] = GuiQualitySettingsOF.I[0x82 ^ 0xB6];
            array9[0x89 ^ 0x8D] = GuiQualitySettingsOF.I[0xF7 ^ 0xC2];
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xC ^ 0x3A])) {
            final String[] array10 = array2 = new String[0xC ^ 0x8];
            array10["".length()] = GuiQualitySettingsOF.I[0x3C ^ 0xB];
            array10[" ".length()] = GuiQualitySettingsOF.I[0x8 ^ 0x30];
            array10["  ".length()] = GuiQualitySettingsOF.I[0x2E ^ 0x17];
            array10["   ".length()] = GuiQualitySettingsOF.I[0x96 ^ 0xAC];
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x4C ^ 0x77])) {
            final String[] array11 = array2 = new String[0xA4 ^ 0xA2];
            array11["".length()] = GuiQualitySettingsOF.I[0x5 ^ 0x39];
            array11[" ".length()] = GuiQualitySettingsOF.I[0xE ^ 0x33];
            array11["  ".length()] = GuiQualitySettingsOF.I[0x56 ^ 0x68];
            array11["   ".length()] = GuiQualitySettingsOF.I[0x93 ^ 0xAC];
            array11[0xAB ^ 0xAF] = GuiQualitySettingsOF.I[0x70 ^ 0x30];
            array11[0x3B ^ 0x3E] = GuiQualitySettingsOF.I[0x5F ^ 0x1E];
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x36 ^ 0x74])) {
            final String[] array12 = array2 = new String[0x2A ^ 0x2F];
            array12["".length()] = GuiQualitySettingsOF.I[0xCC ^ 0x8F];
            array12[" ".length()] = GuiQualitySettingsOF.I[0xF2 ^ 0xB6];
            array12["  ".length()] = GuiQualitySettingsOF.I[0x6D ^ 0x28];
            array12["   ".length()] = GuiQualitySettingsOF.I[0x19 ^ 0x5F];
            array12[0x6C ^ 0x68] = GuiQualitySettingsOF.I[0x3C ^ 0x7B];
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xFD ^ 0xB5])) {
            final String[] array13 = array2 = new String[0x88 ^ 0x8D];
            array13["".length()] = GuiQualitySettingsOF.I[0x1E ^ 0x57];
            array13[" ".length()] = GuiQualitySettingsOF.I[0xA ^ 0x40];
            array13["  ".length()] = GuiQualitySettingsOF.I[0xD ^ 0x46];
            array13["   ".length()] = GuiQualitySettingsOF.I[0x3 ^ 0x4F];
            array13[0x1 ^ 0x5] = GuiQualitySettingsOF.I[0xFC ^ 0xB1];
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x42 ^ 0xC])) {
            final String[] array14 = array2 = new String["   ".length()];
            array14["".length()] = GuiQualitySettingsOF.I[0x5 ^ 0x4A];
            array14[" ".length()] = GuiQualitySettingsOF.I[0x31 ^ 0x61];
            array14["  ".length()] = GuiQualitySettingsOF.I[0xCF ^ 0x9E];
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0xC0 ^ 0x92])) {
            final String[] array15 = array2 = new String[0x99 ^ 0x91];
            array15["".length()] = GuiQualitySettingsOF.I[0xE3 ^ 0xB0];
            array15[" ".length()] = GuiQualitySettingsOF.I[0x3D ^ 0x69];
            array15["  ".length()] = GuiQualitySettingsOF.I[0x6D ^ 0x38];
            array15["   ".length()] = GuiQualitySettingsOF.I[0xDD ^ 0x8B];
            array15[0x4C ^ 0x48] = GuiQualitySettingsOF.I[0xEB ^ 0xBC];
            array15[0xB3 ^ 0xB6] = GuiQualitySettingsOF.I[0xF ^ 0x57];
            array15[0x6D ^ 0x6B] = GuiQualitySettingsOF.I[0x55 ^ 0xC];
            array15[0xA9 ^ 0xAE] = GuiQualitySettingsOF.I[0xDC ^ 0x86];
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x72 ^ 0x29])) {
            final String[] array16 = array2 = new String[0x8B ^ 0x8C];
            array16["".length()] = GuiQualitySettingsOF.I[0x78 ^ 0x24];
            array16[" ".length()] = GuiQualitySettingsOF.I[0xE1 ^ 0xBC];
            array16["  ".length()] = GuiQualitySettingsOF.I[0x19 ^ 0x47];
            array16["   ".length()] = GuiQualitySettingsOF.I[0x6F ^ 0x30];
            array16[0x7D ^ 0x79] = GuiQualitySettingsOF.I[0x5 ^ 0x65];
            array16[0x91 ^ 0x94] = GuiQualitySettingsOF.I[0xF6 ^ 0x97];
            array16[0xA4 ^ 0xA2] = GuiQualitySettingsOF.I[0xEB ^ 0x89];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x62 ^ 0x1])) {
            final String[] array17 = array2 = new String[0x73 ^ 0x7B];
            array17["".length()] = GuiQualitySettingsOF.I[0x33 ^ 0x57];
            array17[" ".length()] = GuiQualitySettingsOF.I[0xE6 ^ 0x83];
            array17["  ".length()] = GuiQualitySettingsOF.I[0x1E ^ 0x78];
            array17["   ".length()] = GuiQualitySettingsOF.I[0x6 ^ 0x61];
            array17[0x4C ^ 0x48] = GuiQualitySettingsOF.I[0x2C ^ 0x44];
            array17[0xB8 ^ 0xBD] = GuiQualitySettingsOF.I[0x26 ^ 0x4F];
            array17[0x7C ^ 0x7A] = GuiQualitySettingsOF.I[0x2C ^ 0x46];
            array17[0x55 ^ 0x52] = GuiQualitySettingsOF.I[0x6C ^ 0x7];
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (s.equals(GuiQualitySettingsOF.I[0x43 ^ 0x2F])) {
            final String[] array18 = array2 = new String[0x5D ^ 0x58];
            array18["".length()] = GuiQualitySettingsOF.I[0x6A ^ 0x7];
            array18[" ".length()] = GuiQualitySettingsOF.I[0x51 ^ 0x3F];
            array18["  ".length()] = GuiQualitySettingsOF.I[0x4B ^ 0x24];
            array18["   ".length()] = GuiQualitySettingsOF.I[0xCD ^ 0xBD];
            array18[0xB2 ^ 0xB6] = GuiQualitySettingsOF.I[0x20 ^ 0x51];
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    private static void I() {
        (I = new String[0x3 ^ 0x71])["".length()] = I("'=#=\u0004\u00021b\u0002\b\u0002<+?\n\u0005", "vHBQm");
        GuiQualitySettingsOF.I[" ".length()] = I("\u0010=\u0007z\u0012\u0018&\u000b", "wHnTv");
        GuiQualitySettingsOF.I["  ".length()] = I("c", "BRmJi");
        GuiQualitySettingsOF.I["   ".length()] = I(">\u0007>\u0018*\u0003N\u0002\u0010=\u0016\u0002=", "snNuK");
        GuiQualitySettingsOF.I[0x41 ^ 0x45] = I("1#\u0005?$\u000bj\u0013,#\u0002)\u0002j2\u000f#\u0015\"e\n+\u001d/6G.\u001f91\u0006$\u0002j*\u0005 \u0013)1\u0014j\u001a%*\fj\u0014/1\u0013/\u0004", "gJvJE");
        GuiQualitySettingsOF.I[0xBB ^ 0xBE] = I("\u0017(H\u0012\u0018\u001a>\u001c\t\u001c\u001b6H\u0015\u001d\u0010q\u001c\u0004\r\u0001$\u001a\u0004U\u00114\u001c\u0000\u001c\u0019\"", "uQhau");
        GuiQualitySettingsOF.I[0x37 ^ 0x31] = I("sP96\ns]V\u001e#s\u0003\u001b\u001f#'\u0018\u001f\u001e+", "SpvpL");
        GuiQualitySettingsOF.I[0xBD ^ 0xBA] = I("sAUU^s\f\r\u001b\u001a>\u0014\tU\u0000>\u000e\u000b\u0001\u001b:\u000f\u0003", "Sadus");
        GuiQualitySettingsOF.I[0x44 ^ 0x4C] = I("HOMB@H\u0002\u0018\u001a\u0004\u0005\u001a\u0014B\u001e\u0005\u0000\u0016\u0016\u0005\u0001\u0001\u001e", "hoybm");
        GuiQualitySettingsOF.I[0x7B ^ 0x72] = I("0<\u0011\u001aM\u000b$\f\u0000\u0002\nt\r\u001a\u0018\u00058\u0014\u0010M\u0000;\u001d\u001aM\n;\fI\f\u00022\u001d\n\u0019D \u0010\fM\u00141\n\u000f\u0002\u00169\u0019\u0007\u000e\u0001z", "dTxim");
        GuiQualitySettingsOF.I[0xA0 ^ 0xAA] = I("#1\u0016\u0019\u0000\u001ex2\r\u0011\u000b", "nXfta");
        GuiQualitySettingsOF.I[0x6D ^ 0x66] = I("\u0004\u0002&\u0016\u0012>K0\u0005\u00157\b!C\u0004:\u00026\u000bS?\n>\u0006\u0000r\u000f<\u0010\u00073\u0005!C\u001c0\u00010\u0000\u0007!K9\f\u001c9K7\u0006\u0007&\u000e'", "RkUcs");
        GuiQualitySettingsOF.I[0x52 ^ 0x5E] = I("+!e+\u0006&710\u0002'?e,\u0003,x1=\u0013=-7=K-=19\u0002%+", "IXEXk");
        GuiQualitySettingsOF.I[0x61 ^ 0x6C] = I("gE?\u000b\u00165\u0000\u0002\u001aWjE\u0003\u0001\u0002 \rQ\u001d\u001a(\n\u0005\u0006\u001e)\u0002QF\u0011&\u0016\u0005\u000b\u00043L", "Geqnw");
        GuiQualitySettingsOF.I[0x6E ^ 0x60] = I("RS\u001f8\u0016\u0017\u0012!qUR\u001d<#\u0015\u0013\u001fs\"\u0015\u001d\u001c'9\u0011\u001c\u0014", "rsSQx");
        GuiQualitySettingsOF.I[0xC8 ^ 0xC7] = I("yP$\b\u00050\u001e\u0003\u0000\u001by]F\u0007\u00007\u0015F\u0012\u00046\u001f\u0012\t\u00007\u0017", "Ypfai");
        GuiQualitySettingsOF.I[0x84 ^ 0x94] = I("NN5\u0004;\u0002\u0007\u000f\u00133\u001cNLV4\u0007\u0000\u0004\u0005&N\u001d\f\u0019=\u001a\u0006\b\u00185NF\u0012\u001a=\u0019\u000b\u0012\u0002{", "nnavR");
        GuiQualitySettingsOF.I[0x8C ^ 0x9D] = I("\u0010 \u001b\u0006&%<\u001d\u0005 2n4\u001c%%+\u0000\u001c'6", "QNruI");
        GuiQualitySettingsOF.I[0x41 ^ 0x53] = I("+>\u001a&\n\u001e\"\u001c%\f\tp5<\t\u001e5\u0001<\u000b\r", "jPsUe");
        GuiQualitySettingsOF.I[0xD3 ^ 0xC0] = I("m\u0017\"3W`xL\u0011\u0012+9\u0011\u0019\u0003dx\u0017\u0001\u0016#<\u0005\u0007\u0013m,\u0001\r\u00038*\u0001U\u0013(,\u0005\u001c\u001bmp\u0002\u0014\u00049=\u0016\\", "MXduw");
        GuiQualitySettingsOF.I[0x99 ^ 0x8D] = I("ltYRFlkT\u0005\u0019\"#\u0006C\u0014)2\u0015\n\u001c?f\u001d\rP!/\u0004\u000e\u0011<6\u0011\u0007P8#\f\u0017\u0005>#\u0007CX?*\u001b\u0014\u0015>o", "LFtcp");
        GuiQualitySettingsOF.I[0x2F ^ 0x3A] = I("\u001a\u0011\u0000N\u0014 \u0010\u0016\u0001!<\u0016\u0015\u00076n?\f\u0002!+\u000b\f\u00002n\u000b\u0000\u001d!!\u000b\u0000\u001du*\u001c\u0011\u000f<\"\nE\u0007;", "NyenU");
        GuiQualitySettingsOF.I[0x3C ^ 0x2A] = I(" \u001e\u001b*\u0002=\u0007\u000e#C9\u0012\u00133\u0016?\u0012\u0018i", "MwkGc");
        GuiQualitySettingsOF.I[0x7D ^ 0x6A] = I("2\u0002.\u0000s\u0000\u0004*\f?\u0000\u000ek\u0007'E\u0007*\u0017s\u0016\u001f)\u001d'\u0004\u0004?\u00072\t\u00062N7\u0000\t9\u000b2\u0016\u000fk\u001a;\u0000J\r>\u0000K", "ejKnS");
        GuiQualitySettingsOF.I[0x92 ^ 0x8A] = I("\u00119\u0011:3<>\u0004 ;>0", "PWeSR");
        GuiQualitySettingsOF.I[0x46 ^ 0x5F] = I("9\u00169\u000e4\u0014\u0011,\u0014<\u0016\u001f", "xxMgU");
        GuiQualitySettingsOF.I[0x45 ^ 0x5F] = I("F\u001c\u0010+KKs~\t\u000e\u00002#\u0001\u001fOs8\u0002K\u0007=\"\u0004\n\n:7\u001e\u0002\b4vE\r\u0007 \"\b\u0019O", "fSVmk");
        GuiQualitySettingsOF.I[0xDC ^ 0xC7] = I("FW{hnFHv86\u0012\f751\u0007\u00163=x\n\f8<+F\u00048=x\u0003\u00011<+FM%57\u0011\u0000$p", "feVYX");
        GuiQualitySettingsOF.I[0xA9 ^ 0xB5] = I("\u0019$ J\u000b#8,\u000b&$-6\u0003$*l6\u0007%\"8-\u0019j'-\"\r/)l)\u0003$(?e\u000b$)l", "MLEjJ");
        GuiQualitySettingsOF.I[0xB7 ^ 0xAA] = I(")=78\u0003z69&\u001c(u\"8\u00124&?>\u001a5;%d", "ZUVJs");
        GuiQualitySettingsOF.I[0x47 ^ 0x59] = I("\u0006\u000e\u0010\u001an4\b\u0014\u0016\"4\u0002U\u001d:q\u000b\u0014\rn\"\u0013\u0017\u0007:0\b\u0001\u001d/=\n\fT*4\u0005\u0007\u0011/\"\u0003U\u0000&4F3$\u001d\u007f", "QfutN");
        GuiQualitySettingsOF.I[0x55 ^ 0x4A] = I("\r\u0019\u0002R\u000f/\u001aV\u001e\u000b5\u0013\u001a\u0001N\"\u0004\u0013R\u001d6\u0006\u0006\u001d\u001c7\u0013\u0012R\f:V\u0017\u001e\u0002c\u0011\u0004\u0013\u001e+\u001f\u0015\u0001N \u0017\u0004\u0016\u001dm", "Cvvrn");
        GuiQualitySettingsOF.I[0x4A ^ 0x6A] = I(",,3'\u0010\u001d##'S\b,!'\u0001I+u\u00106:\u001e\u0014\u0010'H", "iJUBs");
        GuiQualitySettingsOF.I[0x4B ^ 0x6A] = I(";\u001f\u0003.\u0002X$\u0007;\u0015\n", "xsfOp");
        GuiQualitySettingsOF.I[0x39 ^ 0x1B] = I("\r\t\u0007\r:n2\u0003\u0018-<", "NeblH");
        GuiQualitySettingsOF.I[0xA ^ 0x29] = I("Un?\u0007oXn\u0013%*\u0014<\\i;\u0007/\u001e:?\u0014<\u0015';U9\u0011=*\u0007", "uNpIO");
        GuiQualitySettingsOF.I[0xBA ^ 0x9E] = I("xD#7!xIL\u0015\u0002>\u0005\u0019\u001d\u0013x\u0013\r\u0005\u0002*", "Xdlqg");
        GuiQualitySettingsOF.I[0xAF ^ 0x8A] = I("\u0012$\f7\u0014\"a?1\u0010#2", "PAxCq");
        GuiQualitySettingsOF.I[0x4C ^ 0x6A] = I("\r\u0002\u0018.\u000b=G+(\u000f<\u0014", "OglZn");
        GuiQualitySettingsOF.I[0x4E ^ 0x69] = I("gU.3\u0001gXA\u0011\"!\u0014\u0014\u00193g\u0006\b\u0011\"g\u0012\u0013\u001444U\u0015\u0010?3\u0000\u0013\u0010kg\u0013\u0000\u00063\"\u0006\u0015", "GuauG");
        GuiQualitySettingsOF.I[0x24 ^ 0xC] = I("Tj\n\u0013\u0016\u0000jaR\u0003\u0001& R\u0016\u001d.)R\u0002\u0006+?\u0001E\u0000/4\u0006\u0010\u0006/`R\u0016\u0018%;\u0017\u0017", "tJLre");
        GuiQualitySettingsOF.I[0xEC ^ 0xC5] = I("zF3\u0011\"9\u001fU]l>\u001f\u001b\u0011!3\u0005U\u0003%>\u0003U\u0017>;\u0015\u0006P8?\u001e\u0001\u0005>?JU\u0003 5\u0011\u0010\u00038", "ZfupL");
        GuiQualitySettingsOF.I[0x56 ^ 0x7C] = I("\u0015)\u0005\u0015\u0012%l\"\u000f\u0018 ", "WLqaw");
        GuiQualitySettingsOF.I[0x87 ^ 0xAC] = I("\u0011(\u001e\u0005\t!m9\u001f\u0003$", "SMjql");
        GuiQualitySettingsOF.I[0x4A ^ 0x66] = I("Ue:\u0013\u0015UhU16\u0013$\u00009'U6\u001b:$Ye\u00134 \u0001 \u0007", "uEuUS");
        GuiQualitySettingsOF.I[0x22 ^ 0xF] = I("mo\u001a\u0005a`o7.59*'k2# \"ga>#:<$?", "MOUKA");
        GuiQualitySettingsOF.I[0x9D ^ 0xB3] = I("\u00011\u001a\u000f;r*\u001b\u0017?r,\u001b\u001c- y\u0001\n)<*\u0005\u0019:77\u0001X*>6\u0016\u0013;rq\u0013\u001d&1<YX<35\u0019X/ 8\u0006\u000ba", "RYuxH");
        GuiQualitySettingsOF.I[0x76 ^ 0x59] = I("\u0016\u000b!!d\u0003\f6+!\u0013\n*(d\u0016\n0'd\u0012\r+8d\u0003\u000f+,/\u0012", "acDOD");
        GuiQualitySettingsOF.I[0x11 ^ 0x21] = I("*\r>,.\u0015L\u001d'#\u000b", "xlPHA");
        GuiQualitySettingsOF.I[0x7F ^ 0x4E] = I("\u0019\u0006'(\u0004&G\u0004#\t8", "KgILk");
        GuiQualitySettingsOF.I[0xBA ^ 0x88] = I("fz)(\bfwF\u0000!f(\u0007\u0000*)7F\u0003!$)JN(')\u0012\u000b<", "FZfnN");
        GuiQualitySettingsOF.I[0x1A ^ 0x29] = I("cb\b>Rnb5\u0011\u001c'-*P\u001f, 4\\R0.(\u0007\u00171", "CBGpr");
        GuiQualitySettingsOF.I[0x3D ^ 0x9] = I("\u001f%(<\u000b d+7\u0006>d3+\u0001>d49\n)++x\u0010(<2-\u0016(7f>\u000b?d20\u0001m#'5\u0001m'4=\u0005914=\u0017c", "MDFXd");
        GuiQualitySettingsOF.I[0x21 ^ 0x14] = I("=5F( \u0011%\u0015f$T5\u0003>1\u00013\u0003f5\u0015\"\rf2\u001c(\u0005.e\u001c \u0015f(\u0001-\u0012/5\u0018$F+*\u0016a\u0012#=\u00004\u0014#6Z", "tAfFE");
        GuiQualitySettingsOF.I[0x23 ^ 0x15] = I("\u0003\u0000\u0016\u000e;p4\u0018\u000f$\"\u0004", "PwwcK");
        GuiQualitySettingsOF.I[0x16 ^ 0x21] = I("\u00148\u0014\b\u001ag\f\u001a\t\u00055<", "GOuej");
        GuiQualitySettingsOF.I[0x53 ^ 0x6B] = I("GN\u001b\fLJN!1\tG\u001d##\u0001\u0017N7-\u0000\b\u001c'bD\u0003\u000b2#\u0019\u000b\u001a}nL\u0014\u0002;5\t\u0015", "gnTBl");
        GuiQualitySettingsOF.I[0x9B ^ 0xA2] = I("VP\u0017.\u000eV]x\f'V\u001e7\u001ch\u0003\u0003=H;\u0001\u00115\u0018h\u0015\u001f4\u0007:\u0005\\x\u000e)\u0005\u0004=\u001a", "vpXhH");
        GuiQualitySettingsOF.I[0x81 ^ 0xBB] = I("9,0O\u0015\u001a%8\u001fF\u000e+9\u0000\u0014\u001ed4\t\u0000\b'!O\u0001\u001f%&\u001cJM(0\u000e\u0010\b7yO\u0010\u0004*0\u001cF\f*1O\u0011\f00\u001dH", "mDUof");
        GuiQualitySettingsOF.I[0x9A ^ 0xA1] = I(" \n\u001f&0\u001bG2 +\u001e\u0002\u0003", "sgpID");
        GuiQualitySettingsOF.I[0x9E ^ 0xA2] = I("?\u0003?\u0006\u0001\u0004N\u0012\u0000\u001a\u0001\u000b#", "lnPiu");
        GuiQualitySettingsOF.I[0x5F ^ 0x62] = I("Gj\u0006\u001cCJj:?\f\b>!;\r\u0000j&4C\u0005#&?\u0006G(& \u0007\u00028:rK\u0003//3\u0016\u000b>`~C\u0014&&%\u0006\u0015", "gJIRc");
        GuiQualitySettingsOF.I[0x64 ^ 0x5A] = I("uL'6\u0013uAH\u001e:u\u001f\u0005\u001f:!\u0004\u0001\u001e2u\u0003\u000eP7<\u0003\u0005\u0015u7\u0003\u001a\u00140'\u001fDP34\u001f\u001c\u0015'", "UlhpU");
        GuiQualitySettingsOF.I[0xBF ^ 0x80] = I("8\u0003!P\u001d\u0001\u0004+\u0004\u0006\u0005\u0005#P\u0001\nK&\u0019\u0001\u0001\u000ed\u0012\u0001\u001e\u000f!\u0002\u001dL\u00027P\n\u0003\u0005!P\f\u0015K7\u0011\u0003\u001c\u0007-\u001e\tL\n*\u0014", "lkDpn");
        GuiQualitySettingsOF.I[0x80 ^ 0xC0] = I("1\u0018\u0007\u0001\u00147\u0007\f\u0014U$\u0006\u0007S\u0016?\u0002\r\u0001U?\bB\u0012\u0019<N\u0011\u0006\u0007\"\u0001\u0017\u001d\u00119\u0000\u0005S\u0017<\u0001\u0001\u0018\u0006~", "Pnbsu");
        GuiQualitySettingsOF.I[0xC8 ^ 0x89] = I("(<-,9\u001d?/i;\u001b?k.(\b)8ez\u0005?*??\u001avk?3\u0007?8i;\u0007>k>;\u001d?9g", "iZKIZ");
        GuiQualitySettingsOF.I[0x4D ^ 0xF] = I("\r#\u0004\u0000\u0000#v1\u001b\u0001:%", "NVwto");
        GuiQualitySettingsOF.I[0x3A ^ 0x79] = I("'\u001e\"\u0015\u000b\tK\u0017\u000e\n\u0010\u0018", "dkQad");
        GuiQualitySettingsOF.I[0x12 ^ 0x56] = I("xl\u000e<Yul4\u0001\u001c+l\"\u0007\n,#,R\u001f7\"5\u0001Yp($\u0014\u0018- 5[Ux?-\u001d\u000e=>", "XLAry");
        GuiQualitySettingsOF.I[0xA ^ 0x4F] = I("Sk#\u0003\nSfL0?\u00168L!)\u0015*\u0019)8S-\u0003+8_k\n$?\u0007.\u001e", "sKlEL");
        GuiQualitySettingsOF.I[0x49 ^ 0xF] = I("\u000e\u0018\u0010c4/\u0003\u0001,:z\u0016\u001a-#)P\u001412z\u0003\u00003'6\u0019\u0010'w8\tU7??P\u00166%(\u0015\u001b7", "ZpuCW");
        GuiQualitySettingsOF.I[0x31 ^ 0x76] = I("<\"\"\u001c\u0013:\"z\u0018\u0007+,", "HGZhf");
        GuiQualitySettingsOF.I[0x50 ^ 0x18] = I("\u00074\u0018\u0007\u001a)a(\u001c\u0019+3\u0018", "DAksu");
        GuiQualitySettingsOF.I[0x34 ^ 0x7D] = I("\r\u00026\u0004$#W\u0006\u001f'!\u00056", "NwEpK");
        GuiQualitySettingsOF.I[0x38 ^ 0x72] = I("Us>\u000bfXs\u00046#\u0006s\u001205\u0001<\u001ce%\u001a?\u001e75U{\u0015  \u0014&\u001d1oYs\u0002))\u00026\u0003", "uSqEF");
        GuiQualitySettingsOF.I[0x1C ^ 0x57] = I("CK\u0015$\"CFz\u0017\u0017\u0006\u0018z\u0006\u0001\u0005\n/\u000e\u0010C\b5\u000e\u000b\u0011\u0018vB\u0002\u0002\u0018.\u0007\u0016", "ckZbd");
        GuiQualitySettingsOF.I[0x53 ^ 0x1F] = I(" !\"I7\u0001:3\u00069T*(\u0005;\u0006:g\b&\u0011i4\u001c$\u0004%.\f0T+>I \u001c,g\n!\u0006;\"\u0007 ", "tIGiT");
        GuiQualitySettingsOF.I[0x4 ^ 0x49] = I("\u001b7;5:\u001d7c1.\f9", "oRCAO");
        GuiQualitySettingsOF.I[0xD ^ 0x43] = I("\u0017\u001b!\u0018T\u0007\u0012>\n\u0007", "DsNot");
        GuiQualitySettingsOF.I[0x40 ^ 0xF] = I(">\n\u0019\u0019W.\u0003\u0006\u000b\u0004", "mbvnw");
        GuiQualitySettingsOF.I[0x52 ^ 0x2] = I("UP'#eXP\u001b\u0005*\u0002P\u0018\u0001$\f\u0015\u001aM&\u0014\u0000\r\u001ee]\u0014\r\u000b$\u0000\u001c\u001cD", "uphmE");
        GuiQualitySettingsOF.I[0x58 ^ 0x9] = I("Iu>\n\u000bIxQ(\"I;\u001e8m\u001a=\u001e;m\u00199\u00105(\u001bu\u0012-=\f&", "iUqLM");
        GuiQualitySettingsOF.I[0x46 ^ 0x14] = I("\t$:\u0006!)?1\fd\u001e.,\u001c18.'", "JKThD");
        GuiQualitySettingsOF.I[0xDF ^ 0x8C] = I(":-\u0019?\u000b\u001a6\u00125N-'\u000f%\u001b\u000b'\u0004", "yBwQn");
        GuiQualitySettingsOF.I[0x63 ^ 0x37] = I("Wf7\u0010\"WkX8\u000bW%\u00178\n\u0012%\f3\u0000W2\u001d.\u0010\u00024\u001d%D_\"\u001d0\u0005\u0002*\f\u007f", "wFxVd");
        GuiQualitySettingsOF.I[0x1F ^ 0x4A] = I("Fl\u0015$>\u0012l~e+\u0007?'e.\t\"= .\u0012)7e9\u00034'0?\u0003?", "fLSEM");
        GuiQualitySettingsOF.I[0x58 ^ 0xE] = I("xy0\u00066; VJx>8\u0018\u0004!x:\u0019\t6=:\u0002\u0002<x-\u0013\u001f,-+\u0013\u0014", "XYvgX");
        GuiQualitySettingsOF.I[0x96 ^ 0xC1] = I("\u0006\u001d)\b.&\u0006\"\u0002k1\u0017?\u0012>7\u00174F!*\u001b)\u0015k1\u001a\"F? \n3\u00139 \u0001g\t-e\u0015+\u000786^", "ErGfK");
        GuiQualitySettingsOF.I[0x7A ^ 0x22] = I("\u0016;)4\u0002\u00115)5Q\u00044#p\u0013\n5,#\u0019\u0000615\u0002E-/5\u001fE*+1\u0012\u0000>g>\u0014\u001d.g$\u001e", "eZGPq");
        GuiQualitySettingsOF.I[0x5A ^ 0x3] = I("\u0010,!\u0004O\u001a9*\t\u001d[m\u0016\u0004\nU.-\u0002\u0001\u0010.6\t\u000bU9'\u0014\u001b\u0000?'\u001fO\u0014?'L\u001c\u0000=2\u0000\u0006\u0010)", "uMBlo");
        GuiQualitySettingsOF.I[0xF7 ^ 0xAD] = I("$7g\u0017&#n$\u0016<4+)\u0017n2+?\u0017;4+g\u0013/%%i", "FNGcN");
        GuiQualitySettingsOF.I[0xC2 ^ 0x99] = I(".\u00166W>\u0001\u00123", "hwDwh");
        GuiQualitySettingsOF.I[0xC4 ^ 0x98] = I("0\u000b\u000bL$\u001f\u000f\u000e", "vjylr");
        GuiQualitySettingsOF.I[0x3E ^ 0x63] = I("j5#\u0010kgZM2.,\u001b\u0010:?cZ\u0016\"*$\u001e\u0004$/j\f\f3<j\u001e\f%?+\u0014\u00063", "JzeVK");
        GuiQualitySettingsOF.I[0xE4 ^ 0xBA] = I("r9>dXrE\bd\u0003;\u0013\u0007d\u0011;\u0005\u0004%\u001b1\u0013", "RvpDu");
        GuiQualitySettingsOF.I[0x1D ^ 0x42] = I("0\"'o\u0015\u001f&\"o*\u0005c#*1\u000fc'*0\u00196',&V'0\"\"\u0018'<!$W", "vCUOC");
        GuiQualitySettingsOF.I[0xFE ^ 0x9E] = I("|?E?\u0000*0E-\u0000<3\u0004'\n*gXwIv?E*\u0001:)\u000e:I;(E+\fo+\n(\r*#EtWo\u00015\u001aI`g\\", "OGeIi");
        GuiQualitySettingsOF.I[0x55 ^ 0x34] = I("6\u001d\u0015-#\u0004\u001b\u0010c1\f\f\u0003c#\f\u001a\u0000\")\u0006\f\u0007ygV[XcqQETru]ETqrS", "eitCG");
        GuiQualitySettingsOF.I[0x51 ^ 0x33] = I("3'\u0006z\u0010\u001c#\u0003z\u0002\u001c5\u0000;\b\u0016#\u0007`FLpXzWLtXzUMrXzSDt", "uFtZf");
        GuiQualitySettingsOF.I[0x1F ^ 0x7C] = I("<5\u0011\u0018?\u00138E9(\n \u0010\u001f(\u0001", "rTemM");
        GuiQualitySettingsOF.I[0x6 ^ 0x62] = I("\u0017\u0011\u0015!48\u001cA\u0000#!\u0004\u0014&#*", "YpaTF");
        GuiQualitySettingsOF.I[0x7F ^ 0x1A] = I("Kd#\u001e0KiL6\u0019K*\r,\u0003\u0019%\u0000x\u0002\u000e<\u0018-\u0004\u000e7Lp\u0012\u000e\"\r-\u001a\u001fm", "kDlXv");
        GuiQualitySettingsOF.I[0x2B ^ 0x4D] = I("BL7 yOL\r\u001d<B\u0002\u0019\u001a,\u0010\r\u0014N-\u0007\u0014\f\u001b+\u0007\u001f", "blxnY");
        GuiQualitySettingsOF.I[0xE2 ^ 0x85] = I("9$\u0017#\u0011\u0016)C\"\u0006\u000f1\u0016$\u0006\u0004e\u00113\u000e\u00183\u0006v\u0017\u001f C1\u0011\u001e!\u000f?\b\u0012e\u00137\u0017\u0003 \u00118", "wEcVc");
        GuiQualitySettingsOF.I[0xCB ^ 0xA3] = I("\u0013\u00162\u0002\u001d\u0015\u0000w\u0001\u0010P\u00162\u0013\f\u0011\u0010>\r\u000eP\u0006;\f\n\u001b\u0017w\f\u000fP\u0010?\u0006I\u0003\u0005:\u0006I\u0004\u001d'\u0006G", "pdWci");
        GuiQualitySettingsOF.I[0xE5 ^ 0x8C] = I("\u0003<Q:\u001c/;Q=\u0000>)\u0005*\u000bj)\u001f+O,$\u0018?\u001f/,Q9\u000e8!\u0010!\u001b9h\u001e)O> \u0014o\r+;\u0014", "JHqOo");
        GuiQualitySettingsOF.I[0x43 ^ 0x29] = I("\u0011-8\u0004'S52\u001f8\u000632Il')2G/\u001c/1\u000e+\u000636\u0013%\u001c/w\u0001#\u0001a#\u000f)S/6\u00139\u0001 ;", "sAWgL");
        GuiQualitySettingsOF.I[0xE9 ^ 0x82] = I("\u0006\u00044\u0012>\u0000\u0004?F\"\u0001A?\u0013;\u0002\r%\u0003/R\u00035F?\u001a\u0004l\u0005>\u0000\u0013)\b?R\u0015)\u001e?\u0007\u0013)F;\u0013\u0002'", "raLfK");
        GuiQualitySettingsOF.I[0x69 ^ 0x5] = I("27$-\u0003\u001cb\u00042\u0015", "qBWYl");
        GuiQualitySettingsOF.I[0xB ^ 0x66] = I("\u0013\u0003\u000396=V#& ", "PvpMY");
        GuiQualitySettingsOF.I[0xAB ^ 0xC5] = I("XF(\u000bxUF\u00040+\f\t\ne+\u0013\u001fG1=\u0000\u0012\u00127=\u000bFO!=\u001e\u0007\u0012),QJG64\u0017\u0011", "xfgEX");
        GuiQualitySettingsOF.I[0x5C ^ 0x33] = I("rA\u0017\u0003*rLx!\t4\u0000-)\u0018r\u00123<@r\u000796\u00187\u0013", "RaXEl");
        GuiQualitySettingsOF.I[0xC ^ 0x7C] = I("#<\rF)\u0002'\u001c\t'W'\u0003\u001fj\u00031\u0010\u0012?\u00051\u001bF+\u00051H\u0015?\u0007$\u0004\u000f/\u0013t\n\u001fj\u0003<\rF)\u0002&\u001a\u0003$\u0003", "wThfJ");
        GuiQualitySettingsOF.I[0x61 ^ 0x10] = I("\u001d)7\u001e\u0017\u001b)o\u001a\u0003\n'", "iLOjb");
    }
    
    static {
        I();
        final GameSettings.Options[] enumOptions = new GameSettings.Options[0x71 ^ 0x7E];
        enumOptions["".length()] = GameSettings.Options.MIPMAP_LEVELS;
        enumOptions[" ".length()] = GameSettings.Options.MIPMAP_TYPE;
        enumOptions["  ".length()] = GameSettings.Options.AF_LEVEL;
        enumOptions["   ".length()] = GameSettings.Options.AA_LEVEL;
        enumOptions[0x9F ^ 0x9B] = GameSettings.Options.CLEAR_WATER;
        enumOptions[0x65 ^ 0x60] = GameSettings.Options.RANDOM_MOBS;
        enumOptions[0x78 ^ 0x7E] = GameSettings.Options.BETTER_GRASS;
        enumOptions[0x68 ^ 0x6F] = GameSettings.Options.BETTER_SNOW;
        enumOptions[0x4F ^ 0x47] = GameSettings.Options.CUSTOM_FONTS;
        enumOptions[0x6B ^ 0x62] = GameSettings.Options.CUSTOM_COLORS;
        enumOptions[0x48 ^ 0x42] = GameSettings.Options.SWAMP_COLORS;
        enumOptions[0x5 ^ 0xE] = GameSettings.Options.SMOOTH_BIOMES;
        enumOptions[0xBA ^ 0xB6] = GameSettings.Options.CONNECTED_TEXTURES;
        enumOptions[0x9C ^ 0x91] = GameSettings.Options.NATURAL_TEXTURES;
        enumOptions[0xAB ^ 0xA5] = GameSettings.Options.CUSTOM_SKY;
        GuiQualitySettingsOF.enumOptions = enumOptions;
    }
}
