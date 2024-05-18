package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    protected String title;
    private GameSettings settings;
    private static final String[] I;
    private static GameSettings.Options[] enumOptions;
    private GuiScreen prevScreen;
    private int lastMouseY;
    private long mouseStillTime;
    private int lastMouseX;
    
    static {
        I();
        final GameSettings.Options[] enumOptions = new GameSettings.Options[0xA6 ^ 0xA1];
        enumOptions["".length()] = GameSettings.Options.SMOOTH_FPS;
        enumOptions[" ".length()] = GameSettings.Options.SMOOTH_WORLD;
        enumOptions["  ".length()] = GameSettings.Options.FAST_RENDER;
        enumOptions["   ".length()] = GameSettings.Options.FAST_MATH;
        enumOptions[0x4E ^ 0x4A] = GameSettings.Options.CHUNK_UPDATES;
        enumOptions[0xA2 ^ 0xA7] = GameSettings.Options.CHUNK_UPDATES_DYNAMIC;
        enumOptions[0x88 ^ 0x8E] = GameSettings.Options.LAZY_CHUNK_LOADING;
        GuiPerformanceSettingsOF.enumOptions = enumOptions;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 98 + 97 - 83 + 88 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 22 + 12 + 3 + 163) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    private String[] getTooltipLines(final String s) {
        String[] array2;
        if (s.equals(GuiPerformanceSettingsOF.I["  ".length()])) {
            final String[] array = array2 = new String[0x38 ^ 0x3D];
            array["".length()] = GuiPerformanceSettingsOF.I["   ".length()];
            array[" ".length()] = GuiPerformanceSettingsOF.I[0xC1 ^ 0xC5];
            array["  ".length()] = GuiPerformanceSettingsOF.I[0xB0 ^ 0xB5];
            array["   ".length()] = GuiPerformanceSettingsOF.I[0x5B ^ 0x5D];
            array[0x57 ^ 0x53] = GuiPerformanceSettingsOF.I[0x9 ^ 0xE];
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x39 ^ 0x31])) {
            final String[] array3 = array2 = new String[0xA7 ^ 0xA2];
            array3["".length()] = GuiPerformanceSettingsOF.I[0xB6 ^ 0xBF];
            array3[" ".length()] = GuiPerformanceSettingsOF.I[0x8B ^ 0x81];
            array3["  ".length()] = GuiPerformanceSettingsOF.I[0x1D ^ 0x16];
            array3["   ".length()] = GuiPerformanceSettingsOF.I[0x86 ^ 0x8A];
            array3[0xC0 ^ 0xC4] = GuiPerformanceSettingsOF.I[0x8C ^ 0x81];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x71 ^ 0x7F])) {
            final String[] array4 = array2 = new String[0x40 ^ 0x46];
            array4["".length()] = GuiPerformanceSettingsOF.I[0x62 ^ 0x6D];
            array4[" ".length()] = GuiPerformanceSettingsOF.I[0x3 ^ 0x13];
            array4["  ".length()] = GuiPerformanceSettingsOF.I[0x20 ^ 0x31];
            array4["   ".length()] = GuiPerformanceSettingsOF.I[0x1F ^ 0xD];
            array4[0x74 ^ 0x70] = GuiPerformanceSettingsOF.I[0x3E ^ 0x2D];
            array4[0xA8 ^ 0xAD] = GuiPerformanceSettingsOF.I[0xA7 ^ 0xB3];
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x6 ^ 0x13])) {
            final String[] array5 = array2 = new String[0x5E ^ 0x58];
            array5["".length()] = GuiPerformanceSettingsOF.I[0x6D ^ 0x7B];
            array5[" ".length()] = GuiPerformanceSettingsOF.I[0x30 ^ 0x27];
            array5["  ".length()] = GuiPerformanceSettingsOF.I[0xF ^ 0x17];
            array5["   ".length()] = GuiPerformanceSettingsOF.I[0x99 ^ 0x80];
            array5[0x6D ^ 0x69] = GuiPerformanceSettingsOF.I[0x64 ^ 0x7E];
            array5[0x68 ^ 0x6D] = GuiPerformanceSettingsOF.I[0x6F ^ 0x74];
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x63 ^ 0x7F])) {
            final String[] array6 = array2 = new String[0x9B ^ 0x9D];
            array6["".length()] = GuiPerformanceSettingsOF.I[0x64 ^ 0x79];
            array6[" ".length()] = GuiPerformanceSettingsOF.I[0x3F ^ 0x21];
            array6["  ".length()] = GuiPerformanceSettingsOF.I[0x7A ^ 0x65];
            array6["   ".length()] = GuiPerformanceSettingsOF.I[0x93 ^ 0xB3];
            array6[0x76 ^ 0x72] = GuiPerformanceSettingsOF.I[0xE0 ^ 0xC1];
            array6[0x19 ^ 0x1C] = GuiPerformanceSettingsOF.I[0x37 ^ 0x15];
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x99 ^ 0xBA])) {
            final String[] array7 = array2 = new String[0x95 ^ 0x90];
            array7["".length()] = GuiPerformanceSettingsOF.I[0x12 ^ 0x36];
            array7[" ".length()] = GuiPerformanceSettingsOF.I[0x21 ^ 0x4];
            array7["  ".length()] = GuiPerformanceSettingsOF.I[0xBE ^ 0x98];
            array7["   ".length()] = GuiPerformanceSettingsOF.I[0x8F ^ 0xA8];
            array7[0x58 ^ 0x5C] = GuiPerformanceSettingsOF.I[0x49 ^ 0x61];
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x81 ^ 0xA8])) {
            final String[] array8 = array2 = new String[0xBD ^ 0xBA];
            array8["".length()] = GuiPerformanceSettingsOF.I[0x88 ^ 0xA2];
            array8[" ".length()] = GuiPerformanceSettingsOF.I[0x13 ^ 0x38];
            array8["  ".length()] = GuiPerformanceSettingsOF.I[0x98 ^ 0xB4];
            array8["   ".length()] = GuiPerformanceSettingsOF.I[0x80 ^ 0xAD];
            array8[0xC ^ 0x8] = GuiPerformanceSettingsOF.I[0xA4 ^ 0x8A];
            array8[0x67 ^ 0x62] = GuiPerformanceSettingsOF.I[0x64 ^ 0x4B];
            array8[0x66 ^ 0x60] = GuiPerformanceSettingsOF.I[0xF4 ^ 0xC4];
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x78 ^ 0x49])) {
            final String[] array9 = array2 = new String[0x7B ^ 0x7E];
            array9["".length()] = GuiPerformanceSettingsOF.I[0x74 ^ 0x46];
            array9[" ".length()] = GuiPerformanceSettingsOF.I[0xB6 ^ 0x85];
            array9["  ".length()] = GuiPerformanceSettingsOF.I[0xC ^ 0x38];
            array9["   ".length()] = GuiPerformanceSettingsOF.I[0xAE ^ 0x9B];
            array9[0x3D ^ 0x39] = GuiPerformanceSettingsOF.I[0x2B ^ 0x1D];
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (s.equals(GuiPerformanceSettingsOF.I[0x2 ^ 0x35])) {
            final String[] array10 = array2 = new String[0xA9 ^ 0xAC];
            array10["".length()] = GuiPerformanceSettingsOF.I[0x5D ^ 0x65];
            array10[" ".length()] = GuiPerformanceSettingsOF.I[0x18 ^ 0x21];
            array10["  ".length()] = GuiPerformanceSettingsOF.I[0x7D ^ 0x47];
            array10["   ".length()] = GuiPerformanceSettingsOF.I[0x7A ^ 0x41];
            array10[0xA7 ^ 0xA3] = GuiPerformanceSettingsOF.I[0xFF ^ 0xC3];
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
        final int length2 = (enumOptions = GuiPerformanceSettingsOF.enumOptions).length;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = enumOptions[i];
            final int n = this.width / "  ".length() - (62 + 21 - 13 + 85) + length % "  ".length() * (122 + 119 - 146 + 65);
            final int n2 = this.height / (0xA4 ^ 0xA2) + (0x80 ^ 0x95) * (length / "  ".length()) - (0x1 ^ 0xB);
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), n, n2, options));
            }
            ++length;
            ++i;
        }
        this.buttonList.add(new GuiButton(121 + 16 - 56 + 119, this.width / "  ".length() - (0x47 ^ 0x23), this.height / (0x7A ^ 0x7C) + (45 + 156 - 80 + 47) + (0x13 ^ 0x18), I18n.format(GuiPerformanceSettingsOF.I[" ".length()], new Object["".length()])));
    }
    
    public GuiPerformanceSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = GuiPerformanceSettingsOF.I["".length()];
        this.lastMouseX = "".length();
        this.lastMouseY = "".length();
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(0x3B ^ 0x1);
        String substring;
        if (index < 0) {
            substring = s;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), index);
        }
        return substring;
    }
    
    private static void I() {
        (I = new String[0xFC ^ 0xC1])["".length()] = I("\u001255>\u001e0=&6\u0012'p\u0014=\u000569)?\u0002", "BPGXq");
        GuiPerformanceSettingsOF.I[" ".length()] = I("\t$,}!\u0001? ", "nQESE");
        GuiPerformanceSettingsOF.I["  ".length()] = I("*99\u000e6\u0011t\u00101\u0011", "yTVaB");
        GuiPerformanceSettingsOF.I["   ".length()] = I("\u0017\u001a\u0003\u0000>(\u0007\u0018\u0007$d(21w&\u0017B\u0004;1\u001d\n\u000b9#N\u0016\n2d\t\u0010\u0003',\u0007\u0001B36\u0007\u0014\u0007%d\f\u0017\u00041!\u001c\u0011", "DnbbW");
        GuiPerformanceSettingsOF.I[0x7A ^ 0x7E] = I("gY7)\u000egTX\u0001'g\n\f\u000e*.\u0015\u0011\u0015)3\u0010\u0017\u0001dg?(<h*\u0018\u0001O.+\f\u001b\u001b=&\r\u001d", "GyxoH");
        GuiPerformanceSettingsOF.I[0x12 ^ 0x17] = I("uL\r7wxL\u0004)\u0004u\u001f6\u00185<\u0000+\u00036!\u0005-\u0017", "UlByW");
        GuiPerformanceSettingsOF.I[0x52 ^ 0x54] = I("\u0005\u0000\u001b\u0006Z>\u0018\u0006\u001c\u0015?H\u001b\u0006Z6\u001a\u0013\u0005\u00128\u000b\u0001U\u001e#\u0001\u0004\u0010\bq\f\u0017\u0005\u001f?\f\u0013\u001b\u000eq\t\u001c\u0011Z8\u001c\u0001U\u001f7\u000e\u0017\u0016\u000e", "Qhruz");
        GuiPerformanceSettingsOF.I[0x5 ^ 0x2] = I("09L9--j\r;583\u001fw409\u00055.<", "YJlWB");
        GuiPerformanceSettingsOF.I[0xA1 ^ 0xA9] = I("\u001e\u0000\u0002+\r%M:+\u000b!\t", "MmmDy");
        GuiPerformanceSettingsOF.I[0x81 ^ 0x88] = I("\u00053\u001b\u001a\u00152%V\u0019\u00020v\u0005\u0005\n<3\u0005U\u00006#\u0005\u0010\u0007w4\u000fU\u0017?3V\u001c\r#3\u0004\u001b\u0002;v\u0005\u0010\u0011!3\u0004[", "WVvuc");
        GuiPerformanceSettingsOF.I[0x85 ^ 0x8F] = I("mA\t$\u0003mLf\f*m\u00122\u0003'$\r/\u0018$9\b)\fim'\u00161e \u0000?B#!\u0014%\u00160,\u0015#", "MaFbE");
        GuiPerformanceSettingsOF.I[0x13 ^ 0x18] = I("rC\u0004\u0001B\u007fC\r\u001f1r\u0010?.\u0000;\u000f\"5\u0003&\n$!", "RcKOb");
        GuiPerformanceSettingsOF.I[0x3E ^ 0x32] = I("$9\u00115\u0001\u001b$\n2\u001bW\u000b \u0004H\u00154P3\u0001\u00049\u0002>\n\u00029\u00199\u000fW9\u00182H\u001e#\u00042\u001a\u0019,\u001cw\u001b\u0012?\u00062\u001aW!\u001f6\fY", "wMpWh");
        GuiPerformanceSettingsOF.I[0x55 ^ 0x58] = I("5\u000f\u0002#\u0002\u0004\u0000\u0012#A\u001f\u0007\b?A\u0016\u0006\u0016f\r\u001f\n\u0005*A\u0007\u0006\u0016*\u0005\u0003IL5\b\u001e\u000e\b#A\u0000\u0005\u0005?\u0004\u0002@J", "pidFa");
        GuiPerformanceSettingsOF.I[0x5B ^ 0x55] = I("\u0004\u001a\u0016\u0000U\u000e\u0014\u0005", "Huwdu");
        GuiPerformanceSettingsOF.I[0x85 ^ 0x8A] = I("\u0004\u001f;\u0007'h\u00042\u0006t?\u001f(\u000f0h\u00132\u0016:#\u0003z\u0002 h\u00143\u0010 )\u001e9\u0006t\u000e\u0011(M", "HpZcT");
        GuiPerformanceSettingsOF.I[0x6 ^ 0x16] = I("\u0005\u0006:2&>\u0018=!e\"\u00196f73\u001f7#7v\u0015:517\u001f0#e2\u001e65e8\u001e'f&7\u0004 #e7\u001d?f&>\u0004=-6v", "VqSFE");
        GuiPerformanceSettingsOF.I[0x67 ^ 0x76] = I("-:E\n.y9\n\t/<1E\t,8<\u000bF", "YUehK");
        GuiPerformanceSettingsOF.I[0x4E ^ 0x5C] = I("zV\"+!z[M\u001a\b(\u001a\tM\u00042\u0003\u0003\u0006\u0014z\u001a\u0002\f\u0003?\u0012M\u0018\u0017z\u0002\u0002M\u0015?\u0018\t\b\u0015z\u0012\u0004\u001e\u0013;\u0018\u000e\b", "Zvmmg");
        GuiPerformanceSettingsOF.I[0x9B ^ 0x88] = I("bf\u0006\u0007Lof>&\u001e.\"i*\u00047(\":L.)(-\t&f(=L&/:=\r,%,i*#4ei\r.*&>\u001f", "BFIIl");
        GuiPerformanceSettingsOF.I[0xC ^ 0x18] = I("pSleNpS*$\u001d$S> \u00004\u0016>e\n9\u00008$\u00003\u0016l6\u00199\u0007/-\u0007>\u0014", "PsLEn");
        GuiPerformanceSettingsOF.I[0xAF ^ 0xBA] = I("\u0012\n'!:#\u001c')u\u0001\u00107#>1", "BxBMU");
        GuiPerformanceSettingsOF.I[0x5B ^ 0x4D] = I("\r-%\u0000',;c\b'i)1\f(i!-I>!! \u0001i''c\n!<&(\u001ai>!/\u0005i+-c\u0005&(,&\r", "IHCiI");
        GuiPerformanceSettingsOF.I[0x47 ^ 0x50] = I("Px\u0015\u001c,Puz;\f\u0004=(z_\u001dx4?\u001dP;2/\u0004\u001b+z-\u0003\u001c4z8\u000fP45;\u000e\u0015<", "pXZZj");
        GuiPerformanceSettingsOF.I[0x61 ^ 0x79] = I("NkJdzN*\u001e02\u001ckKv:Nk\u0016! N(\u001019\u00058X3>\u0002'X&2N'\u0017%3\u000b/", "nKxDW");
        GuiPerformanceSettingsOF.I[0x35 ^ 0x2C] = I("HvQM\\H7\u000f\u0019\u0014\u001avX_I\u0005v\u0007\b\u0006H5\u0001\u0018\u001f\u0003%I\u001a\u0018\u0004:I\u000f\u0014H:\u0006\f\u0015\r2", "hVimq");
        GuiPerformanceSettingsOF.I[0xA7 ^ 0xBD] = I("#\u0010\r:-\u0019Y\u001c3$\u001e\u001c\u0019r&\u000e\u001c\u000er%\u0004\u000b\u000fr<\u0002\u0014\u000fr<\u0004Y\u0006=)\u000fY\u000b>$K\r\u00027h\b\u0011\u001f<#\u0018", "kyjRH");
        GuiPerformanceSettingsOF.I[0x6A ^ 0x71] = I("\r/\u001cN \r8X\n(\u000f3\u001d\u000f>\ta\f\u0006(L\u0007(=c", "lAxnM");
        GuiPerformanceSettingsOF.I[0x31 ^ 0x2D] = I("/\u001c\u0007\u001a\fL!\u0002\u0010\u0006\u0018\u0011\u0001", "ltrtg");
        GuiPerformanceSettingsOF.I[0x96 ^ 0x8B] = I("\u001b\u0012!,\rx\u000f$&\u0007,\u001f'", "XzTBf");
        GuiPerformanceSettingsOF.I[0x31 ^ 0x2F] = I("RUyNZ\u0001\b6\u0014\u001f\u0000D.\f\b\u001e\u0000y\u000f\u0015\u0013\u00000\r\u001d^D1\n\u001d\u001a\u0001+C<\"7yK\u001e\u0017\u00028\u0016\u0016\u0006M", "rdYcz");
        GuiPerformanceSettingsOF.I[0x31 ^ 0x2E] = I("gBcGK!\u00100\u001e\u000e5Q4\u0005\u0019+\u0015c\u0006\u0004&\u0015*\u0004\fkQ/\u0005\u001c\"\u0003c,;\u0014", "GqCjk");
        GuiPerformanceSettingsOF.I[0x80 ^ 0xA0] = I("yRxDX?\u0006+\u001d\u001d*\u0013x\u001e\u0017+\u000b<I\u00146\u0006<\u0000\u0016>Kx\u0005\u0017.\u0002+\u001dX\u001f7\u000b", "YgXix");
        GuiPerformanceSettingsOF.I[0x48 ^ 0x69] = I("!;\u0019+\u001c\u001dn\u001b/Y\f&\u0001'\u0012O;\u0004-\u0018\u001b+\u0007i\t\n<T;\u001c\u0001*\u0011;\u001c\u000bn\u0012;\u0018\u0002+X", "oNtIy");
        GuiPerformanceSettingsOF.I[0x41 ^ 0x63] = I("*8\u0017/20q\u0006&;74\u0003g:#(P#21%\u0011%>.8\n\"w69\u0015g100\u001d\"%#%\u0015i", "BQpGW");
        GuiPerformanceSettingsOF.I[0x6F ^ 0x4C] = I("\t4\u0017\u000b\u0017$.Y?\n),\r\u000f\t", "MMyjz");
        GuiPerformanceSettingsOF.I[0x57 ^ 0x73] = I("\u0014\n65*9\u0010x7/%\u001d3t2 \u00179 \"#", "PsXTG");
        GuiPerformanceSettingsOF.I[0x3E ^ 0x1B] = I("v\u001c ?O{sN\u001d\n02\u0013\u0015\u001b\u007fs\u0015\r\u000e87\u0007\u000b\u000bv0\u000e\f\u0001=s\u0013\t\u000b7'\u0003\nO&6\u0014Y\t$2\u000b\u001c", "VSfyo");
        GuiPerformanceSettingsOF.I[0x29 ^ 0xF] = I("G9\u000bj`G\u001b*8(G\u00035.,\u0013\u00136j:\u000f\u001f)/m\u0013\u001e j=\u000b\u0017</?G\u001f6j>\u0013\u0017+.$\t\u0011e99\u000e\u001a)", "gvEJM");
        GuiPerformanceSettingsOF.I[0x0 ^ 0x27] = I("\f\u001d\n\u0010\u0004!\u0007D\u0004\u0019,\u0005\u0010\u0014\u001ah\u0002\u000b\u0003\n-D\t\u001e\u001b-D\u0007\u0019\u001c&\u000fD\u0004\u0019,\u0005\u0010\u0014\u001ah\u0013\f\u0018\u0005-", "Hddqi");
        GuiPerformanceSettingsOF.I[0xAF ^ 0x87] = I("\u0000\u001e5S \u0018\u0017)\u0016\"T\u001f#S#\u0000\u0017>\u00179\u001a\u0011p\u0000$\u001d\u001a<S$\u001bV<\u001c1\u0010V$\u001b5T\u0001?\u0001<\u0010V6\u0012#\u0000\u0013\"]", "tvPsP");
        GuiPerformanceSettingsOF.I[0xAE ^ 0x87] = I("5*\u00028J:#\r/\u0001Y\u0007\u0017 \u000e\u0010%\u001f", "yKxAj");
        GuiPerformanceSettingsOF.I[0x4B ^ 0x61] = I("\"\t(\u0018V-\u0000'\u000f\u001dN$=\u0000\u0012\u0007\u00065", "nhRav");
        GuiPerformanceSettingsOF.I[0x55 ^ 0x7E] = I("g !\u000eijO\u0003-/&\u001a\u000b<i4\n\u0015>,5O\u0004 <)\u0004G$&&\u000b\u000e&.", "GogHI");
        GuiPerformanceSettingsOF.I[0x9 ^ 0x25] = I("a\u0000!mca#\u000e77a<\n?8$=O.&4!\u0004m\"..\u000b$ &oG>#. \u001b%+3f", "AOoMN");
        GuiPerformanceSettingsOF.I[0x1B ^ 0x36] = I("\u001b\u001a\t<\u0018 \u0004F'\u0004-W\u000f=\u0018-\u0010\u00142\u0018-\u0013F \t:\u0001\u0003!L+\u001f\u0013=\u0007h\u001b\t2\b!\u0019\u0001s\u000e1", "HwfSl");
        GuiPerformanceSettingsOF.I[0x68 ^ 0x46] = I("\u0001!%7'\f*#7<\u000b/v7=\u0000h5+ \u000b#%c:\u0013-$c&\u0000>314\th\"*6\u000e;x", "eHVCU");
        GuiPerformanceSettingsOF.I[0xB0 ^ 0x9F] = I("\u001a\u0005(\u001bk'\u0004z:\r\bP3\u0013k>\u0011(\u00018n\u001f<U?&\u0015z\u0002$<\u001c>U/!P4\u001a?n\u001c5\u0014/n\u00135\u00079+\u0013.\u00192`", "NpZuK");
        GuiPerformanceSettingsOF.I[0xF ^ 0x3F] = I("'\u001f\u0010\u000e\u0015\u0016\u0010\u0000\u000eV\r\u0017\u001a\u0012V\u0004\u0016\u0004K\u001a\r\u001a\u0017\u0007V\u0015\u0016\u0004\u0007\u0012\u0011Y\u0017\u0005\u0012B\n\u001f\u0005\u0011\u000e\u001c[\b\u0019\u0010\u001cV(&7W", "byvkv");
        GuiPerformanceSettingsOF.I[0x40 ^ 0x71] = I("%0\u0014,Q.0\u00130", "cQgXq");
        GuiPerformanceSettingsOF.I[0x28 ^ 0x1A] = I(".5\u00123H%5\u0015/", "hTaGh");
        GuiPerformanceSettingsOF.I[0x86 ^ 0xB5] = I("Y%,\tpTJ\u0019;1\u0017\u000e\u000b=4Y\u0007\u000b;8YB\u000e*6\u0018\u001f\u0006;y", "yjjOP");
        GuiPerformanceSettingsOF.I[0x32 ^ 0x6] = I("b:*bKb\u0013\u00051\u0012'\u0007D/\u00076\u001d", "BudBf");
        GuiPerformanceSettingsOF.I[0xF2 ^ 0xC7] = I("2\u0000\u0002)Z\b\u0003\u00133\u0017\u000e\t\u0002>Z\u0014\u001a\trSG\u0012\t>Z\u0004\u001c\u0014rSG\u0015\u00124\u0019\u0013\u001a\b4\tG\u0004\u000f3\u0019\u000fS\u0004;\u0014", "gsgZz");
        GuiPerformanceSettingsOF.I[0x3B ^ 0xD] = I(" 6\u00103\u00010s\u00113\r.:\u001e\"D6;\u0001g'\u0012\u0006D$\u0005!;\u0001g\u0005,7D.\n!!\u0001&\u0017's\u0010/\u0001b\u00154\u0014J", "BSdGd");
        GuiPerformanceSettingsOF.I[0x41 ^ 0x76] = I("\u0001\u0011\n\u0001C\u0015\u0015\u0017\u0011\u00065", "Gpyuc");
        GuiPerformanceSettingsOF.I[0x5A ^ 0x62] = I("\r-)7L\u0019)4'\t9", "KLZCl");
        GuiPerformanceSettingsOF.I[0x35 ^ 0xC] = I("y\u0016\u0015\u0001Sty 3\u00127=25\u0017y+6)\u0017<+:)\u0014yq7\"\u00158,?3Z", "YYSGs");
        GuiPerformanceSettingsOF.I[0x6F ^ 0x55] = I("J5#I[J\u0015\u001d\u001d\u001f\u0007\u0013\u0017\f\u0012J\b\b\u0007\u0012\u000f\b\u0004\u0007\u0011JR\u000b\b\u0005\u001e\u001f\u001f@", "jzmiv");
        GuiPerformanceSettingsOF.I[0x2A ^ 0x11] = I("\u0006\u00150\u0004p<\u0016!\u001e=:\u001c0\u0013p!\u0003;\u00135!\u000f;\u0010p2\n2\u0018\":\u0012=\u001ap$\u000e<\u00148s\u00020\u0014\"6\u0007&\u0012#", "SfUwP");
        GuiPerformanceSettingsOF.I[0x23 ^ 0x1F] = I("\u001f)?c5;\u0014z/\u001d\n%z\"\u001c\u000fa7\"\u000bK2/!\u0001\u001f 47\u001b\u0004/;/\u001e\u0012a3-\u0011\u0019$;0\u0017K52&R-\u0011\tm", "kAZCr");
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        int i = "".length();
        "".length();
        if (-1 == 1) {
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
                if (1 <= -1) {
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
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / "  ".length(), 0xB5 ^ 0xA1, 1686477 + 15067983 - 7692092 + 7714847);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= (0x58 ^ 0x5D) && Math.abs(lastMouseY - this.lastMouseY) <= (0x0 ^ 0x5)) {
            if (System.currentTimeMillis() >= this.mouseStillTime + (470 + 120 - 410 + 520)) {
                final int n2 = this.width / "  ".length() - (46 + 50 + 40 + 14);
                int n3 = this.height / (0x44 ^ 0x42) - (0x93 ^ 0x96);
                if (lastMouseY <= n3 + (0x7 ^ 0x65)) {
                    n3 += 105;
                }
                final int n4 = n2 + (85 + 132 - 209 + 142) + (38 + 27 + 75 + 10);
                final int n5 = n3 + (0x47 ^ 0x13) + (0x7A ^ 0x70);
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY);
                if (selectedButton != null) {
                    final String[] tooltipLines = this.getTooltipLines(this.getButtonName(selectedButton.displayString));
                    if (tooltipLines == null) {
                        return;
                    }
                    this.drawGradientRect(n2, n3, n4, n5, -(453535056 + 19816045 - 187481164 + 251000975), -(504709047 + 458093682 - 824345798 + 398413981));
                    int i = "".length();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    while (i < tooltipLines.length) {
                        this.fontRendererObj.drawStringWithShadow(tooltipLines[i], n2 + (0x4B ^ 0x4E), n3 + (0xE ^ 0xB) + i * (0x8B ^ 0x80), 11529837 + 8301703 - 15168760 + 9877473);
                        ++i;
                    }
                    "".length();
                    if (0 >= 4) {
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
}
