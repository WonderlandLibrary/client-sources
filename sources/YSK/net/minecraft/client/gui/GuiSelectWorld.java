package net.minecraft.client.gui;

import org.apache.commons.lang3.*;
import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;
import java.text.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.*;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback
{
    private boolean field_146643_x;
    protected GuiScreen parentScreen;
    private java.util.List<SaveFormatComparator> field_146639_s;
    private String field_146636_v;
    private GuiButton selectButton;
    private String[] field_146635_w;
    private GuiButton renameButton;
    private GuiButton deleteButton;
    private GuiButton recreateButton;
    private String field_146637_u;
    private static final Logger logger;
    private int field_146640_r;
    private static final String[] I;
    private List field_146638_t;
    private final DateFormat field_146633_h;
    protected String field_146628_f;
    private boolean field_146634_i;
    
    protected String func_146614_d(final int n) {
        String s = this.field_146639_s.get(n).getDisplayName();
        if (StringUtils.isEmpty((CharSequence)s)) {
            s = String.valueOf(I18n.format(GuiSelectWorld.I[0xC8 ^ 0xC2], new Object["".length()])) + GuiSelectWorld.I[0x6C ^ 0x67] + (n + " ".length());
        }
        return s;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private void func_146627_h() throws AnvilConverterException {
        Collections.sort(this.field_146639_s = this.mc.getSaveLoader().getSaveList());
        this.field_146640_r = -" ".length();
    }
    
    public void func_146618_g() {
        this.buttonList.add(this.selectButton = new GuiButton(" ".length(), this.width / "  ".length() - (111 + 146 - 211 + 108), this.height - (0x90 ^ 0xA4), 131 + 39 - 95 + 75, 0x7A ^ 0x6E, I18n.format(GuiSelectWorld.I[0x15 ^ 0x19], new Object["".length()])));
        this.buttonList.add(new GuiButton("   ".length(), this.width / "  ".length() + (0x73 ^ 0x77), this.height - (0x50 ^ 0x64), 109 + 14 - 112 + 139, 0xA4 ^ 0xB0, I18n.format(GuiSelectWorld.I[0x37 ^ 0x3A], new Object["".length()])));
        this.buttonList.add(this.renameButton = new GuiButton(0x25 ^ 0x23, this.width / "  ".length() - (143 + 113 - 186 + 84), this.height - (0xA5 ^ 0xB9), 0x74 ^ 0x3C, 0x75 ^ 0x61, I18n.format(GuiSelectWorld.I[0x63 ^ 0x6D], new Object["".length()])));
        this.buttonList.add(this.deleteButton = new GuiButton("  ".length(), this.width / "  ".length() - (0x48 ^ 0x4), this.height - (0x6E ^ 0x72), 0x9 ^ 0x41, 0x20 ^ 0x34, I18n.format(GuiSelectWorld.I[0x38 ^ 0x37], new Object["".length()])));
        this.buttonList.add(this.recreateButton = new GuiButton(0x95 ^ 0x92, this.width / "  ".length() + (0x8E ^ 0x8A), this.height - (0x15 ^ 0x9), 0xCF ^ 0x87, 0xF ^ 0x1B, I18n.format(GuiSelectWorld.I[0xA9 ^ 0xB9], new Object["".length()])));
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() + (0xE2 ^ 0xB0), this.height - (0x2C ^ 0x30), 0xEF ^ 0xA7, 0xAA ^ 0xBE, I18n.format(GuiSelectWorld.I[0x2B ^ 0x3A], new Object["".length()])));
        this.selectButton.enabled = ("".length() != 0);
        this.deleteButton.enabled = ("".length() != 0);
        this.renameButton.enabled = ("".length() != 0);
        this.recreateButton.enabled = ("".length() != 0);
    }
    
    static String access$9(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146636_v;
    }
    
    private static void I() {
        (I = new String[0x25 ^ 0x3E])["".length()] = I("\u0019\u0001\u001d\u0016\u0013>D\u0006\u001c\u0002&\u0000", "Jdqsp");
        GuiSelectWorld.I[" ".length()] = I("4\u0006\u0002\u0003634\u0001\u00149#M\u001a\u000f!+\u0006", "GcnfU");
        GuiSelectWorld.I["  ".length()] = I(".'\u0010%1\u0003o\u0011i9\u0002)\u0001i9\b>\u0000%u\u0001!\u0016=", "mHeIU");
        GuiSelectWorld.I["   ".length()] = I("-\u001c.)\b\u001dR;$D\u0014\u001d./D\u000f\u001d='\u0000\u000b", "xrOKd");
        GuiSelectWorld.I[0x8 ^ 0xC] = I("0\u001f5/'7-68('T.%6/\u001e", "CzYJD");
        GuiSelectWorld.I[0xB5 ^ 0xB0] = I("#\u0001\u0015++$3\u0016<$4J\u001a!&&\u0001\u000b=!?\n", "PdyNH");
        GuiSelectWorld.I[0x9C ^ 0x9A] = I("\u0000&\u0019!\u0002\b#\u0011j<\u00125\u0002-9\u0006+", "gGtDO");
        GuiSelectWorld.I[0x2F ^ 0x28] = I("\"\u0000\u0004\u000f *\u0005\fD\u000e7\u0004\b\u001e\u00043\u0004", "Eaijm");
        GuiSelectWorld.I[0x3D ^ 0x35] = I("\u0014\u0004)$\u001d\u001c\u0001!o1\u0017\u0013!/$\u0006\u0017!", "seDAP");
        GuiSelectWorld.I[0x17 ^ 0x1E] = I("*\u0019\f6\u000f\"\u001c\u0004}1=\u001d\u0002'#9\u0017\u0013", "MxaSB");
        GuiSelectWorld.I[0x9E ^ 0x94] = I("\u0010\u0013:)4\u0017!9>;\u0007X!#%\u000f\u0012", "cvVLW");
        GuiSelectWorld.I[0x87 ^ 0x8C] = I("w", "WpjDm");
        GuiSelectWorld.I[0x7D ^ 0x71] = I("\t1+\u0013\u001b\u000e\u0003(\u0004\u0014\u001ez4\u0013\u0014\u001f73", "zTGvx");
        GuiSelectWorld.I[0x77 ^ 0x7A] = I("$=&\n/#\u000f%\u001d 3v)\u001d)6,/", "WXJoL");
        GuiSelectWorld.I[0x34 ^ 0x3A] = I("!\u0007\u00001;&5\u0003&46L\u001e163\u000f\t", "RblTX");
        GuiSelectWorld.I[0x33 ^ 0x3C] = I("%1*.,\"\u0003)9#2z\".#3 #", "VTFKO");
        GuiSelectWorld.I[0x15 ^ 0x5] = I("4 6\u001d\u00043\u00125\n\u000b#k(\u001d\u00045 ;\f\u0002", "GEZxg");
        GuiSelectWorld.I[0x1B ^ 0xA] = I("0\u0018,[\b6\u0003&\u0010\u0007", "WmEuk");
        GuiSelectWorld.I[0xB1 ^ 0xA3] = I("\r\b%$4", "ZgWHP");
        GuiSelectWorld.I[0x2E ^ 0x3D] = I("<\u0015$.%", "kzVBA");
        GuiSelectWorld.I[0x6 ^ 0x12] = I("\u001a9\u0007 \r7q\u0006l\u000567\u0016l\u0005< \u0017 I5?\u00018", "YVrLi");
        GuiSelectWorld.I[0x5C ^ 0x49] = I("=<\u001f/\u0017:\u000e\u001c8\u0018*w\u0017/\u0018+-\u0016\u001b\u0001+*\u0007#\u001b ", "NYsJt");
        GuiSelectWorld.I[0x29 ^ 0x3F] = I("P", "wKDNC");
        GuiSelectWorld.I[0xD5 ^ 0xC2] = I("UM", "rmITP");
        GuiSelectWorld.I[0x3F ^ 0x27] = I("\u0004+8\u0017\u0007\u0003\u0019;\u0000\b\u0013`0\u0017\b\u0012:1%\u0005\u0005 =\u001c\u0003", "wNTrd");
        GuiSelectWorld.I[0x49 ^ 0x50] = I(">150\u00139\u00036'\u001c)z=0\u001c( <\u0017\u00059 6;", "MTYUp");
        GuiSelectWorld.I[0x7C ^ 0x66] = I("-6\u001d]9+-\u0017\u00166", "JCtsZ");
    }
    
    static GuiButton access$4(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.deleteButton;
    }
    
    static GuiButton access$5(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.renameButton;
    }
    
    static GuiButton access$6(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.recreateButton;
    }
    
    static void access$1(final GuiSelectWorld guiSelectWorld, final int field_146640_r) {
        guiSelectWorld.field_146640_r = field_146640_r;
    }
    
    public GuiSelectWorld(final GuiScreen parentScreen) {
        this.field_146633_h = new SimpleDateFormat();
        this.field_146628_f = GuiSelectWorld.I["".length()];
        this.field_146635_w = new String[0x6C ^ 0x68];
        this.parentScreen = parentScreen;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == "  ".length()) {
                final String func_146614_d = this.func_146614_d(this.field_146640_r);
                if (func_146614_d != null) {
                    this.field_146643_x = (" ".length() != 0);
                    this.mc.displayGuiScreen(func_152129_a(this, func_146614_d, this.field_146640_r));
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                }
            }
            else if (guiButton.id == " ".length()) {
                this.func_146615_e(this.field_146640_r);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else if (guiButton.id == "   ".length()) {
                this.mc.displayGuiScreen(new GuiCreateWorld(this));
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x84 ^ 0x82)) {
                this.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else if (guiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x2C ^ 0x2B)) {
                final GuiCreateWorld guiCreateWorld = new GuiCreateWorld(this);
                final ISaveHandler saveLoader = this.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), "".length() != 0);
                final WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
                saveLoader.flush();
                guiCreateWorld.func_146318_a(loadWorldInfo);
                this.mc.displayGuiScreen(guiCreateWorld);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                this.field_146638_t.actionPerformed(guiButton);
            }
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146638_t.handleMouseInput();
    }
    
    static GuiButton access$3(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.selectButton;
    }
    
    @Override
    public void initGui() {
        this.field_146628_f = I18n.format(GuiSelectWorld.I[" ".length()], new Object["".length()]);
        try {
            this.func_146627_h();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (AnvilConverterException ex) {
            GuiSelectWorld.logger.error(GuiSelectWorld.I["  ".length()], (Throwable)ex);
            this.mc.displayGuiScreen(new GuiErrorScreen(GuiSelectWorld.I["   ".length()], ex.getMessage()));
            return;
        }
        this.field_146637_u = I18n.format(GuiSelectWorld.I[0x84 ^ 0x80], new Object["".length()]);
        this.field_146636_v = I18n.format(GuiSelectWorld.I[0x6C ^ 0x69], new Object["".length()]);
        this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format(GuiSelectWorld.I[0xB7 ^ 0xB1], new Object["".length()]);
        this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format(GuiSelectWorld.I[0x74 ^ 0x73], new Object["".length()]);
        this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format(GuiSelectWorld.I[0x23 ^ 0x2B], new Object["".length()]);
        this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format(GuiSelectWorld.I[0x15 ^ 0x1C], new Object["".length()]);
        (this.field_146638_t = new List(this.mc)).registerScrollButtons(0x87 ^ 0x83, 0x8D ^ 0x88);
        this.func_146618_g();
    }
    
    public void func_146615_e(final int n) {
        this.mc.displayGuiScreen(null);
        if (!this.field_146634_i) {
            this.field_146634_i = (" ".length() != 0);
            String s = this.func_146621_a(n);
            if (s == null) {
                s = GuiSelectWorld.I[0x35 ^ 0x27] + n;
            }
            String s2 = this.func_146614_d(n);
            if (s2 == null) {
                s2 = GuiSelectWorld.I[0x6E ^ 0x7D] + n;
            }
            if (this.mc.getSaveLoader().canLoadWorld(s)) {
                this.mc.launchIntegratedServer(s, s2, null);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_146638_t.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_146628_f, this.width / "  ".length(), 0x98 ^ 0x8C, 9858373 + 4342269 - 7877144 + 10453717);
        super.drawScreen(n, n2, n3);
    }
    
    static int access$2(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146640_r;
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
    
    static DateFormat access$8(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146633_h;
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (this.field_146643_x) {
            this.field_146643_x = ("".length() != 0);
            if (b) {
                final ISaveFormat saveLoader = this.mc.getSaveLoader();
                saveLoader.flushCache();
                saveLoader.deleteWorldDirectory(this.func_146621_a(n));
                try {
                    this.func_146627_h();
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
                catch (AnvilConverterException ex) {
                    GuiSelectWorld.logger.error(GuiSelectWorld.I[0x8F ^ 0x9B], (Throwable)ex);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    public static GuiYesNo func_152129_a(final GuiYesNoCallback guiYesNoCallback, final String s, final int n) {
        return new GuiYesNo(guiYesNoCallback, I18n.format(GuiSelectWorld.I[0x4A ^ 0x5F], new Object["".length()]), GuiSelectWorld.I[0x4 ^ 0x12] + s + GuiSelectWorld.I[0x29 ^ 0x3E] + I18n.format(GuiSelectWorld.I[0x19 ^ 0x1], new Object["".length()]), I18n.format(GuiSelectWorld.I[0x1D ^ 0x4], new Object["".length()]), I18n.format(GuiSelectWorld.I[0x51 ^ 0x4B], new Object["".length()]), n);
    }
    
    static String[] access$10(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146635_w;
    }
    
    static String access$7(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146637_u;
    }
    
    protected String func_146621_a(final int n) {
        return this.field_146639_s.get(n).getFileName();
    }
    
    static java.util.List access$0(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146639_s;
    }
    
    class List extends GuiSlot
    {
        private static final String[] I;
        final GuiSelectWorld this$0;
        
        @Override
        protected int getContentHeight() {
            return GuiSelectWorld.access$0(this.this$0).size() * (0x51 ^ 0x75);
        }
        
        static {
            I();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            if (n == GuiSelectWorld.access$2(this.this$0)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            GuiSelectWorld.access$1(this.this$0, n);
            int n4;
            if (GuiSelectWorld.access$2(this.this$0) >= 0 && GuiSelectWorld.access$2(this.this$0) < this.getSize()) {
                n4 = " ".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            final int n5 = n4;
            GuiSelectWorld.access$3(this.this$0).enabled = (n5 != 0);
            GuiSelectWorld.access$4(this.this$0).enabled = (n5 != 0);
            GuiSelectWorld.access$5(this.this$0).enabled = (n5 != 0);
            GuiSelectWorld.access$6(this.this$0).enabled = (n5 != 0);
            if (b && n5) {
                this.this$0.func_146615_e(n);
            }
        }
        
        @Override
        protected int getSize() {
            return GuiSelectWorld.access$0(this.this$0).size();
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        public List(final GuiSelectWorld this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, this$0.width, this$0.height, 0x2C ^ 0xC, this$0.height - (0xED ^ 0xAD), 0x5E ^ 0x7A);
        }
        
        private static void I() {
            (I = new String[0x85 ^ 0x8D])["".length()] = I("V", "vAWom");
            List.I[" ".length()] = I("JY", "jqzoM");
            List.I["  ".length()] = I("O", "fHZwZ");
            List.I["   ".length()] = I("", "PaPvk");
            List.I[0x4A ^ 0x4E] = I("c", "CvYjN");
            List.I[0x3 ^ 0x6] = I("%1\u000b6;-4\u0003}\u001e#\"\u00020\u001905", "BPfSv");
            List.I[0x64 ^ 0x62] = I("Ke", "gEeJf");
            List.I[0x5C ^ 0x5B] = I("\u001e\u000f\u000602\u0019=\u0005'=\tD\t=4\f\u001e\u0019", "mjjUQ");
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final SaveFormatComparator saveFormatComparator = GuiSelectWorld.access$0(this.this$0).get(n);
            String s = saveFormatComparator.getDisplayName();
            if (StringUtils.isEmpty((CharSequence)s)) {
                s = String.valueOf(GuiSelectWorld.access$7(this.this$0)) + List.I["".length()] + (n + " ".length());
            }
            final String string = String.valueOf(new StringBuilder(String.valueOf(saveFormatComparator.getFileName())).append(List.I[" ".length()]).append(GuiSelectWorld.access$8(this.this$0).format(new Date(saveFormatComparator.getLastTimePlayed()))).toString()) + List.I["  ".length()];
            final String s2 = List.I["   ".length()];
            String s3;
            if (saveFormatComparator.requiresConversion()) {
                s3 = String.valueOf(GuiSelectWorld.access$9(this.this$0)) + List.I[0x2E ^ 0x2A] + s2;
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                s3 = GuiSelectWorld.access$10(this.this$0)[saveFormatComparator.getEnumGameType().getID()];
                if (saveFormatComparator.isHardcoreModeEnabled()) {
                    s3 = EnumChatFormatting.DARK_RED + I18n.format(List.I[0x54 ^ 0x51], new Object["".length()]) + EnumChatFormatting.RESET;
                }
                if (saveFormatComparator.getCheatsEnabled()) {
                    s3 = String.valueOf(s3) + List.I[0x18 ^ 0x1E] + I18n.format(List.I[0xB1 ^ 0xB6], new Object["".length()]);
                }
            }
            this.this$0.drawString(this.this$0.fontRendererObj, s, n2 + "  ".length(), n3 + " ".length(), 8085886 + 3903878 + 710120 + 4077331);
            this.this$0.drawString(this.this$0.fontRendererObj, string, n2 + "  ".length(), n3 + (0xA9 ^ 0xA5), 5293155 + 3094704 - 1213293 + 1246938);
            this.this$0.drawString(this.this$0.fontRendererObj, s3, n2 + "  ".length(), n3 + (0x43 ^ 0x4F) + (0x1B ^ 0x11), 291499 + 371732 + 5561643 + 2196630);
        }
    }
}
