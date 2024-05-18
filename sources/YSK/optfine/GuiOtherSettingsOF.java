package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private static GameSettings.Options[] enumOptions;
    private int lastMouseY;
    private static final String[] I;
    private GameSettings settings;
    private int lastMouseX;
    private GuiScreen prevScreen;
    private long mouseStillTime;
    protected String title;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 153 + 15 + 17 + 15 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 51 + 187 - 190 + 152) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id == 105 + 31 + 15 + 59) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiYesNo(this, GuiOtherSettingsOF.I["   ".length()], GuiOtherSettingsOF.I[0x4B ^ 0x4F], 6141 + 1153 - 4648 + 7353));
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(0xAA ^ 0x90);
        String substring;
        if (index < 0) {
            substring = s;
            "".length();
            if (0 == 3) {
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
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / "  ".length(), 0x79 ^ 0x6D, 3205558 + 1372189 - 320092 + 12519560);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= (0x10 ^ 0x15) && Math.abs(lastMouseY - this.lastMouseY) <= (0x58 ^ 0x5D)) {
            if (System.currentTimeMillis() >= this.mouseStillTime + (221 + 148 + 166 + 165)) {
                final int n2 = this.width / "  ".length() - (9 + 86 + 19 + 36);
                int n3 = this.height / (0x99 ^ 0x9F) - (0x5F ^ 0x5A);
                if (lastMouseY <= n3 + (0x50 ^ 0x32)) {
                    n3 += 105;
                }
                final int n4 = n2 + (34 + 144 - 159 + 131) + (2 + 127 - 120 + 141);
                final int n5 = n3 + (0x76 ^ 0x22) + (0x5 ^ 0xF);
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY);
                if (selectedButton != null) {
                    final String[] tooltipLines = this.getTooltipLines(this.getButtonName(selectedButton.displayString));
                    if (tooltipLines == null) {
                        return;
                    }
                    this.drawGradientRect(n2, n3, n4, n5, -(198707418 + 93547516 + 167991765 + 76624213), -(264693277 + 121306128 + 62653221 + 88218286));
                    int i = "".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    while (i < tooltipLines.length) {
                        this.fontRendererObj.drawStringWithShadow(tooltipLines[i], n2 + (0x61 ^ 0x64), n3 + (0x37 ^ 0x32) + i * (0xC9 ^ 0xC2), 6717352 + 14492605 - 16434832 + 9765128);
                        ++i;
                    }
                    "".length();
                    if (true != true) {
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
    
    private static void I() {
        (I = new String[0xAD ^ 0x97])["".length()] = I("<>\u0010\"\u0010S\u0019\u001d3\u0016\u001a$\u001f4", "sJxGb");
        GuiOtherSettingsOF.I[" ".length()] = I("<-\u0018,\u0010N\u001e\u0002-\u0001\u0001h8,\u0010\u001a!\u0005.\u0017@fE", "nHkId");
        GuiOtherSettingsOF.I["  ".length()] = I("\u000b\u001a+c\u0000\u0003\u0001'", "loBMd");
        GuiOtherSettingsOF.I["   ".length()] = I("\u001f\t&+#m\r9\"w;\u00051+8m\u001f0:#$\u00022=w9\u0003u:?(\u0005'n3(\n4;;9L#/;8\t&q", "MlUNW");
        GuiOtherSettingsOF.I[0xAA ^ 0xAE] = I("", "QziuU");
        GuiOtherSettingsOF.I[0x79 ^ 0x7C] = I("\u0007=\u0005$+'>\u0014", "FHqKX");
        GuiOtherSettingsOF.I[0x9 ^ 0xF] = I("\u0016'7\u001c\u001d6$&S\u00079&&\u0001\u00186>", "WRCsn");
        GuiOtherSettingsOF.I[0x5E ^ 0x59] = I("<\u000b0\u0014&\u0014\u001av\u0014&\f\u0001%\u0014%\u001dN?\u001b'\u001d\u001c \u0014?XFd\u0006zX\u0007%U\u001d7:v'\u0016;!\u001b8\u00166*\u00131}", "xnVuS");
        GuiOtherSettingsOF.I[0x74 ^ 0x7C] = I("#\u0004!'>\u0003\u00070h.\u0003\u0004&->B\u0005=-m\u0004\u00108'8\u0011Q\u0019)*B\"%!&\u0007Q:.m&\u00144<%L", "bqUHM");
        GuiOtherSettingsOF.I[0x92 ^ 0x9B] = I("/23%*\u0006'18", "cSTJG");
        GuiOtherSettingsOF.I[0xB0 ^ 0xBA] = I("\u001c?\u001d\u0018 o#\u001a\ns#6\u0015\u0000>*#\u0017\u001ds 9R\u001b;*w\u0016\n1:0R\u001c0=2\u0017\u0001sg\u0011AF}", "OWroS");
        GuiOtherSettingsOF.I[0xBA ^ 0xB1] = I("rW.626\u0010\u0004d~x:\u0004)<*\u000eA#2*\u0015\u0000#6x\u0014\u000e(?=\u0014\u0015-<6", "XwaDS");
        GuiOtherSettingsOF.I[0x1E ^ 0x12] = I("mN\u000b5\u000e)Nel;.\r#", "GnHLo");
        GuiOtherSettingsOF.I[0x90 ^ 0x9D] = I("^h6$\u0010\u0011hYh6\u0017 \u0011,\u0010\u0018-\u0010h\u0000\f-\u0017=\u0011\u0015*\u0018-\u0016", "tHtHe");
        GuiOtherSettingsOF.I[0x93 ^ 0x9D] = I("Fc\u0011\"5\u001c/$wjL\u0000)\")\u0007c4'+\u0003\"%", "lCAWG");
        GuiOtherSettingsOF.I[0x3A ^ 0x35] = I("Ix\u001b\u00036Cui%:\u00166\"F'\u0013<(\u00127\u0010", "cXIfR");
        GuiOtherSettingsOF.I[0xA5 ^ 0xB5] = I("\\J<,.\u001a\u0005\u0012ioV<\f:+\u0014\u0003\t 6\u000fJ\u0006!'\u0015\u0001", "vjeIB");
        GuiOtherSettingsOF.I[0x3D ^ 0x2C] = I("Fi=\u0004\u000b\t'Z[N>,\u0014\u0012\u000b\u001ei\u000e\u0013\u001c\u001e(\u0013\u0018", "lIzvn");
        GuiOtherSettingsOF.I[0x1B ^ 0x9] = I("&\f\r\u001a$B9\u001d\u0000%\u000b\u0005\n\u001d", "biooC");
        GuiOtherSettingsOF.I[0x25 ^ 0x36] = I("5<\r\u0019\u0005Q\t\u001d\u0003\u0004\u00185\n\u001e", "qYolb");
        GuiOtherSettingsOF.I[0x4B ^ 0x5F] = I("Zj%\u001eNWj\u000e5\f\u000f-J \u001c\u0015,\u0003<\u000b\bj\u0003#N\u001b)\u001e9\u0018\u001ffJ#\u0002\u0015=\u000f\"", "zJjPn");
        GuiOtherSettingsOF.I[0x90 ^ 0x85] = I("vr(\u0005-v\u007fG'\u000e4'\u0000c\u001b$=\u0001*\u00073 G*\u0018v<\b7K71\u0013*\u001d3~G%\n%&\u00021", "VRgCk");
        GuiOtherSettingsOF.I[0x2D ^ 0x3B] = I("-\f3n\u0015\u001c\u0006#)Q\t\u00169(\u0018\u0015\u0001$n\u0012\u0016\b:+\u0012\r\u0017v/\u001f\u001dD%&\u001e\u000e\u0017v*\u0014\u001b\u00111n\u0018\u0017\u00029<\u001c\u0018\u0010?!\u001f", "ydVNq");
        GuiOtherSettingsOF.I[0x24 ^ 0x33] = I(" \u000f5\u0019B#\u000f5W\u00062\u0005%\u0010B$\u0004\"\u0012\u00079G9\u0004B8\u00175\u0019B\u007f!c^", "WgPwb");
        GuiOtherSettingsOF.I[0x2F ^ 0x37] = I("\u00003\u0007&", "TZjCS");
        GuiOtherSettingsOF.I[0x1F ^ 0x6] = I("\u0018\u001d/\u0001", "LtBdh");
        GuiOtherSettingsOF.I[0xDD ^ 0xC7] = I("Z)\u0004\f\u0005\u000f\u0001\u0015JIZ\u0003\u000e\u0018\t\u001b\u0001A\u000e\u0005\u0003B\u000f\u0003\u0003\u0012\u0019A\t\u001d\u0019\u0001\u0004\u0019", "zmajd");
        GuiOtherSettingsOF.I[0x61 ^ 0x7A] = I("F\n\u000f\nj) \u0002\njKn\n\u00123F!\u0000\u001f3", "fNnsJ");
        GuiOtherSettingsOF.I[0x93 ^ 0x8F] = I("w,35/#B\u0015<+.Bwr)>\u00052&g8\f6+", "WbZRG");
        GuiOtherSettingsOF.I[0x9A ^ 0x87] = I("51\u0013e\u0005\b4\u0013e\u0002\u0004-\u0002,\u001f\u0006y\u001f6Q\u000e7\u001a<Q\u0004?\u0010 \u0012\u00150\u0000 Q\b7V\u0006#$\u0018\"\f'$y\u001b*\u0015\u0004", "aYvEq");
        GuiOtherSettingsOF.I[0x40 ^ 0x5E] = I("&6\u0006a/(*B-&$9\u000ea>(*\u000e%:i", "GXbAI");
        GuiOtherSettingsOF.I[0x26 ^ 0x39] = I(">\u000093\u0010\f\u0017", "ieXGx");
        GuiOtherSettingsOF.I[0xB2 ^ 0x92] = I("=$\u000b\u0001\u0004\u000f3", "jAjul");
        GuiOtherSettingsOF.I[0x92 ^ 0xB3] = I("ql\u0002\u0017y|l:<8%$(+y8?m8:%%;<uq?!6.4>", "QLMYY");
        GuiOtherSettingsOF.I[0x7B ^ 0x59] = I("OY5\"\u0001OTZ\u0013\"\u000e\r\u0012\u00015O\u0010\tD)\u0000\rZ\u0005$\u001b\u0010\f\u0001kO\u001f\u001b\u00173\n\u000b", "oyzdG");
        GuiOtherSettingsOF.I[0x60 ^ 0x43] = I("\u0000\u0011 N\u00191\u00181\u0006\u000b&Y&\u0001\u0000 \u000b*\u0002\u001dt\u000b$\u0007\u0000xY6\u0000\u0001#Y$\u0000\nt\r-\u001b\u00000\u001c7\u001d\u001a;\u000b(\u001d@", "TyEnn");
        GuiOtherSettingsOF.I[0xAC ^ 0x88] = I("\"+3\u0011\u000e\u0010<r\u0006\t\u001b: \n\nU'!E\t\u001b\"+E\u0016\u001a=!\f\u0004\u0019+r\u0003\t\u0007n>\n\u0005\u0014\"r\u0012\t\u0007\"6\u0016H", "uNRef");
        GuiOtherSettingsOF.I[0xA4 ^ 0x81] = I("56\u0002\")\u00101\u000b+4", "sCnNZ");
        GuiOtherSettingsOF.I[0x1F ^ 0x39] = I("\r\u0014#\u00041(\u0013*\r,", "KaOhB");
        GuiOtherSettingsOF.I[0x9 ^ 0x2E] = I("RZ\f;V_Z6\u0006\u0013R\u001c6\u0019\u001a\u0001\u00191\u0010\u0013\u001cZ.\u001a\u0012\u0017", "rzCuv");
        GuiOtherSettingsOF.I[0x8F ^ 0xA7] = I("ci\u0007\u0007\u0000cdh45&i?(('&?a+,--", "CIHAF");
        GuiOtherSettingsOF.I[0xEB ^ 0xC2] = I("$!\u0007\b\u000b\u0001&\u000e\u0001\u0016B9\u0004\u0000\u001dB9\n\u001dX\u00001K\u0002\u0019\u0011 \u000e\u0016X\r&K\u0017\u0014\r#\u000e\u0016X\u0016<\n\n", "bTkdx");
        GuiOtherSettingsOF.I[0x4F ^ 0x65] = I("\u000f\u0007\u000f\u0010\u0015\u000fN\f\u001b\u001e\u001dBA\u0010\u001f\b\u000b\u000f\u0010\u0013\u0016\tA\u001b\u0014X\u001a\t\u0011Z\u001f\u001c\u0000\u0004\u0012\u0011\r\u0012T\u0019\u0019\u001c\u0005Z", "xnatz");
        GuiOtherSettingsOF.I[0xAA ^ 0x81] = I("3!*\u001e\u0014\u0016&#\u0017\tU\u0019)\u0016\u0002", "uTFrg");
        GuiOtherSettingsOF.I[0x1B ^ 0x37] = I("'\u001a\b=\u0011\u0002\u001d\u00014\fA\u0002\u000b5\u0007", "aodQb");
        GuiOtherSettingsOF.I[0x22 ^ 0xF] = I("ei)3/$<\u0001\"ihi\u0018%,e-\b%\"1&\u001dv:&;\b3'e;\b%&)<\u0019?&+eM%%*>\b$", "EImVI");
        GuiOtherSettingsOF.I[0x11 ^ 0x3F] = I("yZ\u0010\u000f0yWg\u0002\u000b<Z$\u0002\u000b-\u0015*W\u000b:\b\"\u0012\u0016y\b\"\u0004\u00175\u000f3\u001e\u00177Vg\u001a\u0019 Z%\u0012X?\u001b4\u0003\u001d+", "YzGwx");
        GuiOtherSettingsOF.I[0x25 ^ 0xA] = I("0\u0006/f:\u0001\u0002/%=\u0001\nj4,\u0017\u0001&3=\r\u0001$f \u0017N?5,\u0000N#(i\u0002\u001b&*:\u0007\u001c/#'D\u0003%\",DF\fwxM@", "dnJFI");
        GuiOtherSettingsOF.I[0x72 ^ 0x42] = I("\u0001'<\u0004\u0015m:.\u0012\b!=?\b\b#;k\u0012\u000f\"='\u0005G*-%\u0004\u0015,$'\u0018G/-k\u0007\u0006><.\u0013I", "MHKag");
        GuiOtherSettingsOF.I[0xBA ^ 0x8B] = I("K1z'\u0019\u0019\u00126\u001f\u0007\u0010", "xuZfw");
        GuiOtherSettingsOF.I[0x6 ^ 0x34] = I("V\u0017A+6\u00016A3*\u00007A10\u0011;A4<\u0001~\u0002?8\u000bsR\u0002y\u0002?\u00005*\u0000 O", "eSaFY");
        GuiOtherSettingsOF.I[0x96 ^ 0xA5] = I("\u001f\u001b\u001a6s\n#&", "LsuAS");
        GuiOtherSettingsOF.I[0x53 ^ 0x67] = I("\u0007=\u001e\u0012\u0001t6\u001e\b\u000256\u0005E4\u0004\u0006Q\u0004\u001c0u\u0003\u0000\u001c00\u0003E\u001b:3\u001e\u0017\u001f5!\u0018\n\u001c", "TUqer");
        GuiOtherSettingsOF.I[0x16 ^ 0x23] = I("YL\u001auETL:'\u0010\u0017\u0007y=\u0000\u0017\b<=\u0000\u000b\u001f", "ylYOe");
        GuiOtherSettingsOF.I[0xBB ^ 0x8D] = I("So\u000bhc^o<7-\u0017*<7'S* &*\u0007&+!cXo,>,\u0010$n7-\u0007&:;&\u0000", "sONRC");
        GuiOtherSettingsOF.I[0x3E ^ 0x9] = I("ix\u0017Qydx!\u0003,'3b\u001e)-96\u000e*", "IXBkY");
        GuiOtherSettingsOF.I[0x2A ^ 0x12] = I("7\u0006\u0012J\u0013\f\u0003\u0007\u000b\u0013\u0017N1:#C\u0007\u0019\f\u001f\u0011\u0003\u0016\u001e\u0019\f\u0000W\u0003\u0003C\u0001\u0019\u0006\tC\u001d\u001f\u0005\u0007\rN\u0000\u0002\u0015\rN\u0003\u0002\u0015", "cnwjp");
        GuiOtherSettingsOF.I[0x3E ^ 0x7] = I("\u0013/\u0003\u0007-W9\u0002\u0000/\u0012$A\u001b9W$\u000e\u0006j\u0001#\u0012\u001b(\u001b/O", "wJarJ");
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        int i = "".length();
        "".length();
        if (3 >= 4) {
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
                if (1 < 1) {
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
    
    public GuiOtherSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = GuiOtherSettingsOF.I["".length()];
        this.lastMouseX = "".length();
        this.lastMouseY = "".length();
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    private String[] getTooltipLines(final String s) {
        String[] array2;
        if (s.equals(GuiOtherSettingsOF.I[0x84 ^ 0x81])) {
            final String[] array = array2 = new String["   ".length()];
            array["".length()] = GuiOtherSettingsOF.I[0x11 ^ 0x17];
            array[" ".length()] = GuiOtherSettingsOF.I[0x1B ^ 0x1C];
            array["  ".length()] = GuiOtherSettingsOF.I[0x4B ^ 0x43];
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x24 ^ 0x2D])) {
            final String[] array3 = array2 = new String[0xD ^ 0x5];
            array3["".length()] = GuiOtherSettingsOF.I[0x68 ^ 0x62];
            array3[" ".length()] = GuiOtherSettingsOF.I[0x40 ^ 0x4B];
            array3["  ".length()] = GuiOtherSettingsOF.I[0x98 ^ 0x94];
            array3["   ".length()] = GuiOtherSettingsOF.I[0x28 ^ 0x25];
            array3[0x33 ^ 0x37] = GuiOtherSettingsOF.I[0xF ^ 0x1];
            array3[0xB9 ^ 0xBC] = GuiOtherSettingsOF.I[0xCC ^ 0xC3];
            array3[0x4C ^ 0x4A] = GuiOtherSettingsOF.I[0x4C ^ 0x5C];
            array3[0x3E ^ 0x39] = GuiOtherSettingsOF.I[0x52 ^ 0x43];
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x69 ^ 0x7B])) {
            final String[] array4 = array2 = new String[0x4C ^ 0x49];
            array4["".length()] = GuiOtherSettingsOF.I[0xA0 ^ 0xB3];
            array4[" ".length()] = GuiOtherSettingsOF.I[0x25 ^ 0x31];
            array4["  ".length()] = GuiOtherSettingsOF.I[0x33 ^ 0x26];
            array4["   ".length()] = GuiOtherSettingsOF.I[0xB6 ^ 0xA0];
            array4[0x54 ^ 0x50] = GuiOtherSettingsOF.I[0x5B ^ 0x4C];
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x91 ^ 0x89])) {
            final String[] array5 = array2 = new String[0x82 ^ 0x84];
            array5["".length()] = GuiOtherSettingsOF.I[0x64 ^ 0x7D];
            array5[" ".length()] = GuiOtherSettingsOF.I[0x8B ^ 0x91];
            array5["  ".length()] = GuiOtherSettingsOF.I[0x17 ^ 0xC];
            array5["   ".length()] = GuiOtherSettingsOF.I[0x9D ^ 0x81];
            array5[0x21 ^ 0x25] = GuiOtherSettingsOF.I[0x36 ^ 0x2B];
            array5[0xA1 ^ 0xA4] = GuiOtherSettingsOF.I[0x83 ^ 0x9D];
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x34 ^ 0x2B])) {
            final String[] array6 = array2 = new String[0x75 ^ 0x70];
            array6["".length()] = GuiOtherSettingsOF.I[0xC ^ 0x2C];
            array6[" ".length()] = GuiOtherSettingsOF.I[0x81 ^ 0xA0];
            array6["  ".length()] = GuiOtherSettingsOF.I[0x78 ^ 0x5A];
            array6["   ".length()] = GuiOtherSettingsOF.I[0x65 ^ 0x46];
            array6[0x9E ^ 0x9A] = GuiOtherSettingsOF.I[0x4C ^ 0x68];
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x92 ^ 0xB7])) {
            final String[] array7 = array2 = new String[0x91 ^ 0x94];
            array7["".length()] = GuiOtherSettingsOF.I[0x37 ^ 0x11];
            array7[" ".length()] = GuiOtherSettingsOF.I[0xB2 ^ 0x95];
            array7["  ".length()] = GuiOtherSettingsOF.I[0x27 ^ 0xF];
            array7["   ".length()] = GuiOtherSettingsOF.I[0x37 ^ 0x1E];
            array7[0x13 ^ 0x17] = GuiOtherSettingsOF.I[0x56 ^ 0x7C];
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x69 ^ 0x42])) {
            final String[] array8 = array2 = new String[0xAC ^ 0xA9];
            array8["".length()] = GuiOtherSettingsOF.I[0xB9 ^ 0x95];
            array8[" ".length()] = GuiOtherSettingsOF.I[0x22 ^ 0xF];
            array8["  ".length()] = GuiOtherSettingsOF.I[0xB2 ^ 0x9C];
            array8["   ".length()] = GuiOtherSettingsOF.I[0x63 ^ 0x4C];
            array8[0x62 ^ 0x66] = GuiOtherSettingsOF.I[0x31 ^ 0x1];
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0x7D ^ 0x4C])) {
            (array2 = new String[" ".length()])["".length()] = GuiOtherSettingsOF.I[0x95 ^ 0xA7];
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (s.equals(GuiOtherSettingsOF.I[0xBD ^ 0x8E])) {
            final String[] array9 = array2 = new String[0x2E ^ 0x28];
            array9["".length()] = GuiOtherSettingsOF.I[0xBF ^ 0x8B];
            array9[" ".length()] = GuiOtherSettingsOF.I[0x98 ^ 0xAD];
            array9["  ".length()] = GuiOtherSettingsOF.I[0x77 ^ 0x41];
            array9["   ".length()] = GuiOtherSettingsOF.I[0x6C ^ 0x5B];
            array9[0x76 ^ 0x72] = GuiOtherSettingsOF.I[0x85 ^ 0xBD];
            array9[0xB ^ 0xE] = GuiOtherSettingsOF.I[0xA3 ^ 0x9A];
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        final GameSettings.Options[] enumOptions;
        final int length2 = (enumOptions = GuiOtherSettingsOF.enumOptions).length;
        int i = "".length();
        "".length();
        if (false == true) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = enumOptions[i];
            final int n = this.width / "  ".length() - (65 + 121 - 106 + 75) + length % "  ".length() * (72 + 133 - 167 + 122);
            final int n2 = this.height / (0x17 ^ 0x11) + (0x29 ^ 0x3C) * (length / "  ".length()) - (0x8A ^ 0x80);
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), n, n2, options));
            }
            ++length;
            ++i;
        }
        this.buttonList.add(new GuiButton(52 + 173 - 211 + 196, this.width / "  ".length() - (0xCC ^ 0xA8), this.height / (0xAF ^ 0xA9) + (98 + 106 - 72 + 36) + (0xB3 ^ 0xB8) - (0x80 ^ 0xAC), GuiOtherSettingsOF.I[" ".length()]));
        this.buttonList.add(new GuiButton(68 + 45 + 87 + 0, this.width / "  ".length() - (0xA3 ^ 0xC7), this.height / (0xE ^ 0x8) + (9 + 93 - 38 + 104) + (0x1 ^ 0xA), I18n.format(GuiOtherSettingsOF.I["  ".length()], new Object["".length()])));
    }
    
    static {
        I();
        final GameSettings.Options[] enumOptions = new GameSettings.Options[0x28 ^ 0x20];
        enumOptions["".length()] = GameSettings.Options.LAGOMETER;
        enumOptions[" ".length()] = GameSettings.Options.PROFILER;
        enumOptions["  ".length()] = GameSettings.Options.WEATHER;
        enumOptions["   ".length()] = GameSettings.Options.TIME;
        enumOptions[0x54 ^ 0x50] = GameSettings.Options.USE_FULLSCREEN;
        enumOptions[0x20 ^ 0x25] = GameSettings.Options.FULLSCREEN_MODE;
        enumOptions[0x39 ^ 0x3F] = GameSettings.Options.SHOW_FPS;
        enumOptions[0xC6 ^ 0xC1] = GameSettings.Options.AUTOSAVE_TICKS;
        GuiOtherSettingsOF.enumOptions = enumOptions;
    }
}
