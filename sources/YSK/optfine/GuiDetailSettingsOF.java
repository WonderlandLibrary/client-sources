package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiDetailSettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    private int lastMouseY;
    private GuiScreen prevScreen;
    private int lastMouseX;
    private long mouseStillTime;
    protected String title;
    private static final String[] I;
    private GameSettings settings;
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / "  ".length(), 0x74 ^ 0x60, 5318433 + 3264716 - 3740577 + 11934643);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= (0xC0 ^ 0xC5) && Math.abs(lastMouseY - this.lastMouseY) <= (0x24 ^ 0x21)) {
            if (System.currentTimeMillis() >= this.mouseStillTime + (612 + 355 - 542 + 275)) {
                final int n2 = this.width / "  ".length() - (7 + 2 + 124 + 17);
                int n3 = this.height / (0xBE ^ 0xB8) - (0x69 ^ 0x6C);
                if (lastMouseY <= n3 + (0x4B ^ 0x29)) {
                    n3 += 105;
                }
                final int n4 = n2 + (117 + 26 - 30 + 37) + (138 + 133 - 160 + 39);
                final int n5 = n3 + (0x41 ^ 0x15) + (0x86 ^ 0x8C);
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY);
                if (selectedButton != null) {
                    final String[] tooltipLines = this.getTooltipLines(this.getButtonName(selectedButton.displayString));
                    if (tooltipLines == null) {
                        return;
                    }
                    this.drawGradientRect(n2, n3, n4, n5, -(295067466 + 330881904 - 348869826 + 259791368), -(55687472 + 121450165 + 225301242 + 134432033));
                    int i = "".length();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                    while (i < tooltipLines.length) {
                        this.fontRendererObj.drawStringWithShadow(tooltipLines[i], n2 + (0x29 ^ 0x2C), n3 + (0x14 ^ 0x11) + i * (0xBB ^ 0xB0), 793616 + 6186502 + 7207329 + 352806);
                        ++i;
                    }
                    "".length();
                    if (2 != 2) {
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
            if (3 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x3E ^ 0x67])["".length()] = I("72\r\u0004\u0006\u001fw*\u0000\u001b\u0007>\u0017\u0002\u001c", "sWyeo");
        GuiDetailSettingsOF.I[" ".length()] = I("%\u0003/i\u0016-\u0018#", "BvFGr");
        GuiDetailSettingsOF.I["  ".length()] = I("5\u0018\u001a\u001d,\u0005", "vtuhH");
        GuiDetailSettingsOF.I["   ".length()] = I("%\u000f$\u0012>\u0015", "fcKgZ");
        GuiDetailSettingsOF.I[0x13 ^ 0x17] = I("pv\u000f671#''q}v* q#3?s3)v86%$?%4q\u0017$*#9958", "PVKSQ");
        GuiDetailSettingsOF.I[0xC6 ^ 0xC3] = I("iB34\u0014=BXu\u000b&\u0015\u0010'G8\u0017\u00149\u000e=\u001bYu\u0001(\u0011\u00010\u0015", "IbuUg");
        GuiDetailSettingsOF.I[0x66 ^ 0x60] = I("LS4 &\u000f\nRlh\u0004\u001a\u0015)-\u001eS\u00034)\u0000\u001a\u00068dL\u0000\u001e.?\t\u0001", "lsrAH");
        GuiDetailSettingsOF.I[0x84 ^ 0x83] = I("tQ\u001c\u0013\u000bt\\s;\"t\u0012?:80\u0002\u007fu+5\u0002'0> ", "TqSUM");
        GuiDetailSettingsOF.I[0x3E ^ 0x36] = I("\u001f\b\u0001'n:\u0005\u001d&**I\u0013!+y\u001b\u0017=*<\u001b\u00177nk-\\", "YirSN");
        GuiDetailSettingsOF.I[0x93 ^ 0x9A] = I("\u000b7 \"(m5\".$)%n #(v<$?)3<$5me\no", "MVNAQ");
        GuiDetailSettingsOF.I[0x62 ^ 0x68] = I("9\b#\u001d\u0017Z,)\u0001\u0014\u0012\u0010", "zdLhs");
        GuiDetailSettingsOF.I[0x5E ^ 0x55] = I("9\u0000\u0019$\tZ$\u00138\n\u0012\u0018", "zlvQm");
        GuiDetailSettingsOF.I[0x9A ^ 0x96] = I("me(1\u001fmhG\u0013<+$\u0012\u001b-m-\u0002\u001e>%1", "MEgwY");
        GuiDetailSettingsOF.I[0x47 ^ 0x4A] = I("xZRDR}ZNT\u0003:\u0015\u0015\u0011B/\u0015\u0011\u0018\u0006x\u0012\u0006\u001d\u00050\u000eC\u0018\u000b5\u0013\u0017", "Xzctb");
        GuiDetailSettingsOF.I[0x71 ^ 0x7F] = I("3+3\f8", "gYViK");
        GuiDetailSettingsOF.I[0x9D ^ 0x92] = I("\u001c1?/!", "HCZJR");
        GuiDetailSettingsOF.I[0xA5 ^ 0xB5] = I("pz2\u0017\u000e1/\u001a\u0006H}z\u0017\u0001H#?\u0002R\n)z\u0005\u0017\u001c$3\u0018\u0015H\u0017(\u0017\u0002\u000099\u0005", "PZvrh");
        GuiDetailSettingsOF.I[0x92 ^ 0x83] = I("Ka?\f\n\u001faTM\u0015\u00046\u001c\u001fY\u001a4\u0018\u0001\u0010\u001f8UM\u001f\n2\r\b\u000b", "kAymy");
        GuiDetailSettingsOF.I[0x6B ^ 0x79] = I("Yb\u0002\u0002=\u001a;dNs\u0011+#\u000b6\u000bb5\u00162\u0015+0\u001a\u007fY1(\f$\u001c0", "yBDcS");
        GuiDetailSettingsOF.I[0x10 ^ 0x3] = I("$%8%W\u00166.4\u0004B,*'\u0012B+;0\u0006\u0017!k=\u0012\u00032.\"Y", "bDKQw");
        GuiDetailSettingsOF.I[0x4 ^ 0x10] = I("\u00173\n*\u000eq&\u0016,\u0012\"r\f(\u00014r\u0010;\u0016?!\u0014(\u00054<\u0010i\u001b43\u0012,\u0004\u007f", "QRdIw");
        GuiDetailSettingsOF.I[0x86 ^ 0x93] = I("\u0002;\r\u0004\u0014", "EIlwg");
        GuiDetailSettingsOF.I[0x79 ^ 0x6F] = I("\b\u00066\"+", "OtWQX");
        GuiDetailSettingsOF.I[0x62 ^ 0x75] = I("Nw>)\r\u000f\"\u00168KCw\u001b?K\u001d2\u000el\t\u0017w\t)\u001f\u001a>\u0014+K)%\u001b<\u0003\u00074\t", "nWzLk");
        GuiDetailSettingsOF.I[0x53 ^ 0x4B] = I("nS60\u000b:S]q\u0014!\u0004\u0015#X?\u0006\u0011=\u0011:\n\\q\u001e/\u0000\u00044\n", "NspQx");
        GuiDetailSettingsOF.I[0x3C ^ 0x25] = I("lh\u0001$\u001f/1ghQ$! -\u0014>h60\u0010 !3<]l;+*\u0006):", "LHGEq");
        GuiDetailSettingsOF.I[0xB5 ^ 0xAF] = I(" /4\u001cI\u0001<&\u001b\u001aF;4\r\u001aF*\"\u000e\b\u0013\"3H\u001a\u000f*\"H\u001d\u000363\u001d\u001b\u0003`", "fNGhi");
        GuiDetailSettingsOF.I[0xAD ^ 0xB6] = I("-\b+-\u000eK\u000e7/\u0004\u0018I0=\u0012\u0018I''\u0018\u0006\fe=\u001e\u000f\fe:\u0012\u0013\u001d0<\u0012E", "kiENw");
        GuiDetailSettingsOF.I[0xA5 ^ 0xB9] = I("*=:\u001a2\u000b+u#6\u000b\"&", "nOUjB");
        GuiDetailSettingsOF.I[0x8E ^ 0x93] = I("<:\u0007\u0003)\u001d,H:-\u001d%\u001b", "xHhsY");
        GuiDetailSettingsOF.I[0x63 ^ 0x7D] = I("WW&6\u001e\u0016\u0002\u000e'XZW\u0003 X\u0004\u0012\u0016s\u001a\u000eW\u00116\f\u0003\u001e\f4X0\u0005\u0003#\u0010\u001e\u0014\u0011", "wwbSx");
        GuiDetailSettingsOF.I[0x25 ^ 0x3A] = I("Wo\u00168\u001c\u0003o}y]3o4+\u0000\u0007?5=O\u001e;54\u001c[o68\u001c\u0003*\"", "wOPYo");
        GuiDetailSettingsOF.I[0x39 ^ 0x19] = I("MQ%;(\u000e\bCwf^5C>4\u0002\u0001\u0013?\"M\u0018\u0017?+\u001e]C)*\u0002\u0006\u0006(", "mqcZF");
        GuiDetailSettingsOF.I[0x72 ^ 0x53] = I("\u00190%\u00134", "NQQvF");
        GuiDetailSettingsOF.I[0xE4 ^ 0xC6] = I(" \u0002\u0002+7", "wcvNE");
        GuiDetailSettingsOF.I[0x18 ^ 0x3B] = I("wS\u0006*\u000e6\u0006.;HzS#<H$\u00166o\n.S1*\u001c#\u001a,(H\u0010\u0001#?\u0000>\u00101", "WsBOh");
        GuiDetailSettingsOF.I[0x71 ^ 0x55] = I("Tu<*7\u0000uZfd\u0018:\r.6T$\u000f*(\u001d!\u0003gd\u00124\t?!\u0006", "tUzKD");
        GuiDetailSettingsOF.I[0xA1 ^ 0x84] = I("xg\u0003\u00186;>eTx0.\"\u0011=*g4\f94.1\u0000tx4)\u0016/=5", "XGEyX");
        GuiDetailSettingsOF.I[0x92 ^ 0xB4] = I("!\u0015\u00038d\u0010\u0015\u0004)6G\\Al4\u0006\u0007\u0003ed\u000f\u0015\u0003l7\b\u0019\u0015l2\u000e\u0007\u0005-(G\u0015\u00028-\u0001\u0015\u001387", "gtpLD");
        GuiDetailSettingsOF.I[0x50 ^ 0x77] = I("\u00044\u000f\u0012\u001db\"\u0000\u0005\u00010uICD24\u0012\u0002Mb=\u0000\u0002D,:A\u0007\r1 \u0000\u001dD#'\u0015\u0018\u0002#6\u0015\u0002", "BUaqd");
        GuiDetailSettingsOF.I[0x30 ^ 0x18] = I("\u0005-9<qql\u0003<> ", "WLPRQ");
        GuiDetailSettingsOF.I[0x93 ^ 0xBA] = I("\u0019\f\u0007\u001bKmM=\u001b\u0004<", "Kmnuk");
        GuiDetailSettingsOF.I[0x9 ^ 0x23] = I("Qg2\u0002\u0016\u00102\u001a\u0013P\\g\u0017\u0014P\u0002\"\u0002G\u0012\bg\u0005\u0002\u0004\u0005.\u0018\u0000P65\u0017\u0017\u0018\u0018$\u0005", "qGvgp");
        GuiDetailSettingsOF.I[0x70 ^ 0x5B] = I("gV\u0017-'3Vqat+\u001f6$ g\u00040%:h\u0005?##kV7-'3\u0013#", "GvQLT");
        GuiDetailSettingsOF.I[0x87 ^ 0xAB] = I("rB\u0013&\u00001\u001bujN:\u000741\u0017r\u00104.\u0000}\u0011;(\u0019~B&+\u0001%\u0007'", "RbUGn");
        GuiDetailSettingsOF.I[0x5A ^ 0x77] = I("ue\u00155.uhz\u001d\u0007u7;\u001a\u0006z64\u001c\u001fye<\u0012\u001b! )\u0007", "UEZsh");
        GuiDetailSettingsOF.I[0xB8 ^ 0x96] = I("\r\r,\bz(\u0004 \bz3\u0016i)\u001c\u001cE=\u000e?z\u00169\n;)\r,\u0015z;\u000b-F(;\f'F)5\u0010'\u0002)", "ZeIfZ");
        GuiDetailSettingsOF.I[0xB1 ^ 0x9E] = I("\f\u001c\u001db*\u0019\u0007\u0014.y\f\r\f+/\b@", "mnxBY");
        GuiDetailSettingsOF.I[0xB3 ^ 0x83] = I(")!?", "zJFNs");
        GuiDetailSettingsOF.I[0x6B ^ 0x5A] = I("2\u0013/", "axVcJ");
        GuiDetailSettingsOF.I[0x5 ^ 0x37] = I("MC\u001a\nN@C&/\u0017M\n&d\u0018\u0004\u0010<&\u0002\bOu7\u0002\u0002\u001406", "mcUDn");
        GuiDetailSettingsOF.I[0x2B ^ 0x18] = I("YY\"4-YY@R\u0018\u0012\u0000M\u001b\u0018Y\u0017\u0002\u0006K\u000f\u0010\u001e\u001b\t\u0015\u001cAR\r\u0018\n\u0019\u0017\u0019", "yymrk");
        GuiDetailSettingsOF.I[0x27 ^ 0x13] = I("\u000f\u001f4\u001cv+\u001c(R?+W\u001e4\u0010x\u00039\u0017v5\u0018>\u001cv9\u00195R%-\u0019q\u0013$=W\"\u0006?4\u001bq\u0004?+\u001e3\u001e3v", "XwQrV");
        GuiDetailSettingsOF.I[0x69 ^ 0x5C] = I("?\u0019/kPL!.$\u0018", "llAKv");
        GuiDetailSettingsOF.I[0x4 ^ 0x32] = I("'=$DIT\u0005%\u000b\u0001", "tHJdo");
        GuiDetailSettingsOF.I[0x16 ^ 0x21] = I("Eh\u000e\u000fMHh24\u0003E)/%M\b'./M\u0004:$a\u001b\f;(#\u0001\u0000hi%\b\u0003)4-\u0019L", "eHAAm");
        GuiDetailSettingsOF.I[0x62 ^ 0x5A] = I("Xr\u001556XrwS\u0003\r<z\u0012\u001e\u001cr7\u001c\u001f\u0016r;\u0001\u0015X<5\u0007P\u000e;)\u001a\u0012\u00147z[\u0016\u0019!.\u0016\u0002Q", "xRZsp");
        GuiDetailSettingsOF.I[0x2D ^ 0x14] = I("\u001c\u0000+&=", "OtJTN");
        GuiDetailSettingsOF.I[0xAE ^ 0x94] = I("\u001a\u001b%(\t", "IoDZz");
        GuiDetailSettingsOF.I[0x85 ^ 0xBE] = I("PK\u0002=V]K>\u0007\u0017\u0002\u0018m\u0012\u0004\u0015K;\u001a\u0005\u0019\t!\u0016ZP\u0018!\u001c\u0001\u0015\u0019", "pkMsv");
        GuiDetailSettingsOF.I[0x65 ^ 0x59] = I("Vw\u000b%#VwiC\u0016\u000266\u0010E\u0017%!C\u000b\u0019#d\u0015\f\u0005>&\u000f\u0000Zw\"\u0002\u0016\u000226", "vWDce");
        GuiDetailSettingsOF.I[0x90 ^ 0xAD] = I("\u00144\"1\u000bp\u0017=\"", "PQREc");
        GuiDetailSettingsOF.I[0x23 ^ 0x1D] = I("\u0012+$\u001f\u0018v\b;\f", "VNTkp");
        GuiDetailSettingsOF.I[0x41 ^ 0x7E] = I("dM\u0000$tiM)\u00053d\u0000 \u001c17M,\u0006;7\b=J50M-\u000f06\u0002,\u0001t(\b9\u000f87Mg\u000e1\"\f:\u0006 m", "DmOjT");
        GuiDetailSettingsOF.I[0xC3 ^ 0x83] = I("LG\u0000(<LJo\u001d\u001b\u0001\u0002o\b\u0015\u000bG.\u001aZ\r\u000b#N\u0016\t\u0011*\u0002\t", "lgOnz");
        GuiDetailSettingsOF.I[0x4C ^ 0xD] = I("\u00140\u0019\u0006X\u00049\u0006\u0014\u000b", "GXvqx");
        GuiDetailSettingsOF.I[0x15 ^ 0x57] = I("9\u000e=2k)\u0007\" 8", "jfREK");
        GuiDetailSettingsOF.I[0x51 ^ 0x12] = I("nZ<<UcZ\u0000\u001a\u001a9Z\u0003\u001e\u00147\u001f\u0001R\u0016/\n\u0016\u0001Uf\u001e\u0016\u0014\u0014;\u0016\u0007[", "Nzsru");
        GuiDetailSettingsOF.I[0xCD ^ 0x89] = I("yG\u0006\u000e\u0015yJi,<y\t&<s*\u000f&?s)\u000b(16+G*)#<\u0014", "YgIHS");
        GuiDetailSettingsOF.I[0xE3 ^ 0xA6] = I("2\u0007!\u0001Y3\u0016(\bY.\r\"\t\r\u0013\u0012>", "zbMey");
        GuiDetailSettingsOF.I[0x64 ^ 0x22] = I("\u0001\u001d\u0004,N \f\r%N=\u0017\u0007$\u001a \b\u001b", "IxhHn");
        GuiDetailSettingsOF.I[0x34 ^ 0x73] = I("Es? THs\u0003\u0006\u001b\u0012s\u0004\u0001\u001b\t'\u0019\u001e\u0007E5\u001f\u001cT\r6\u001c\nT\f'\u0015\u0003\u0007E{\u0014\u000b\u0012\u0004&\u001c\u001a]", "eSpnt");
        GuiDetailSettingsOF.I[0x41 ^ 0x9] = I("El\u000e0\u0017Eaa\u0012>E\".\u0002q\u0016$.\u0001q\u0011#.\u001a%\f<2V7\n>a\u001e4\t(a\u001f%\u0000!2", "eLAvQ");
        GuiDetailSettingsOF.I[0xE3 ^ 0xAA] = I("\u00045\u000e\u0018\u0011<2\f\u0013\f$g-\u001a\r3,\u001c", "PGovb");
        GuiDetailSettingsOF.I[0xD4 ^ 0x9E] = I("13/%\u0012\t4-.\u000f\u0011a\f'\u000e\u0006*=", "eANKa");
        GuiDetailSettingsOF.I[0xFD ^ 0xB6] = I("YQ\"+(\u001a\bDgf\u001a\u001e\u00168#\u001a\u0005D))\u0015\u001e\u0016j$\u0015\u0014\n./\u0017\u0016Db\"\u001c\u0017\u0005?*\rX", "yqdJF");
        GuiDetailSettingsOF.I[0x8E ^ 0xC2] = I("jc*\u0010+>cAQ>+0\u0018Q;%/\u0003\u0003x(/\t\u001f<#-\u000bQp,\"\u001f\u0005=8j", "JClqX");
        GuiDetailSettingsOF.I[0x32 ^ 0x7F] = I("98;'\u0016\u0015;&s\u0010\u00122u0\u000b\u00168's\u0006\u00162;7\r\u00140u<\u0002Z#'2\n\t; 0\u0001\u0014#u1\b\u00154> ", "zWUSd");
        GuiDetailSettingsOF.I[0xE2 ^ 0xAC] = I("2\u001b\u0001\u001eW!\u001b\u0013\u0010\u00127\u0017\u001b\u0002W&\u001d\u0019\u0019\u0005eZ\u0006\u0002\u0016,\u001c\u0010\u0012W\"\u001e\u0014\u0005\u0004iR\u0002\u0017\u0003 \u0000YV\u001e&\u0017\\", "Eruvw");
        GuiDetailSettingsOF.I[0x35 ^ 0x7A] = I("\u0018=\r7y\u001f9\t:<\u000bu\n<1\u0006;\fy<\u000e6\u0000y6\u001b=\r+y\u0018<\u001c1y\u000e<\u001ay;\n!\u001f<<\u0001u\u001c1<\u0002{", "oUhYY");
        GuiDetailSettingsOF.I[0xC7 ^ 0x97] = I("<3-;\r\u001e./", "jZJUh");
        GuiDetailSettingsOF.I[0x3B ^ 0x6A] = I("\u001b\u000f2&9!F$5>(\u00055s/%\u000f\";x>\n(409\n8s<,\u0014*66>F5;=m\u0015\"!=(\ba07?\b$!+", "MfASX");
        GuiDetailSettingsOF.I[0x90 ^ 0xC2] = I("OK7\u0000\u0002\u000e\u001e\u001f\u0011DBK\u0012\u0016D\u001c\u000e\u0007E\u0006\u0016K\u0007\r\u0001O\u0018\u0016\u0011\u0010\u0006\u0005\u0014E#\u001d\n\u0003\r\r\f\u0018SM\u0000\n\r\u0012\u0010\b\u001bB", "oksed");
        GuiDetailSettingsOF.I[0x59 ^ 0xA] = I("gk5\b\t3k^I\f.,\u001d\f\u000e3.S\r\u00134*\u0011\u0005\u001f#k[\u000f\u001b4?\u0016\u001bS", "GKsiz");
        GuiDetailSettingsOF.I[0x42 ^ 0x16] = I("lP\u0014\b+/\trDe:\u00195\u0007 8\u00047I \"\u00110\u0005 (Pz\u001a)#\u00077\u001bl", "LpRiE");
        GuiDetailSettingsOF.I[0x92 ^ 0xC7] = I("7\u001a\u000bw'\n\u0015\u00002%\u0017\u0017N:0\u001aR\u00066'\u0006R\u000fw\"\n\u0015\u0000>7\n\u0011\u000f9%C\u0017\b14\u0000\u0006N8?C\u0006\u00062q%\"={", "crnWQ");
        GuiDetailSettingsOF.I[0x57 ^ 0x1] = I("\u000f8\u0006\u00012\u0003*\u001a\b(J<\u001e\u0001?J;\u001a\u0005(\u0003%\u0011D7\u001f'\u001a\u00172\u0018.\u0013\n\u007f", "jKvdQ");
        GuiDetailSettingsOF.I[0xE1 ^ 0xB6] = I("9\u001e\u0015z0\u0004\u0011\u001e?2\u0019\u0013P? \u000b\u0013\u0013.f\u0004\u0005P,#\u001f\u000fP)3\u000f\u0002\u001c?f\f\u0018\u0014z%\f\u0018P)'\u000b\u0013\u001c#", "mvpZF");
        GuiDetailSettingsOF.I[0x4B ^ 0x13] = I("1\rm\u0001\r \t/\t\u00017", "ShMed");
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        final GameSettings.Options[] enumOptions;
        final int length2 = (enumOptions = GuiDetailSettingsOF.enumOptions).length;
        int i = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = enumOptions[i];
            final int n = this.width / "  ".length() - (130 + 66 - 83 + 42) + length % "  ".length() * (85 + 114 - 43 + 4);
            final int n2 = this.height / (0xC6 ^ 0xC0) + (0x45 ^ 0x50) * (length / "  ".length()) - (0x8D ^ 0x87);
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
        this.buttonList.add(new GuiButton(168 + 71 - 119 + 80, this.width / "  ".length() - (0xD5 ^ 0xB1), this.height / (0x81 ^ 0x87) + (89 + 50 - 74 + 103) + (0x55 ^ 0x5E), I18n.format(GuiDetailSettingsOF.I[" ".length()], new Object["".length()])));
    }
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(0xBA ^ 0x80);
        String substring;
        if (index < 0) {
            substring = s;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), index);
        }
        return substring;
    }
    
    private String[] getTooltipLines(final String s) {
        String[] array2;
        if (s.equals(GuiDetailSettingsOF.I["  ".length()])) {
            final String[] array = array2 = new String[0x65 ^ 0x62];
            array["".length()] = GuiDetailSettingsOF.I["   ".length()];
            array[" ".length()] = GuiDetailSettingsOF.I[0x88 ^ 0x8C];
            array["  ".length()] = GuiDetailSettingsOF.I[0x47 ^ 0x42];
            array["   ".length()] = GuiDetailSettingsOF.I[0x7 ^ 0x1];
            array[0x1E ^ 0x1A] = GuiDetailSettingsOF.I[0x8E ^ 0x89];
            array[0x10 ^ 0x15] = GuiDetailSettingsOF.I[0x56 ^ 0x5E];
            array[0x72 ^ 0x74] = GuiDetailSettingsOF.I[0x9C ^ 0x95];
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x52 ^ 0x58])) {
            final String[] array3 = array2 = new String["   ".length()];
            array3["".length()] = GuiDetailSettingsOF.I[0x2E ^ 0x25];
            array3[" ".length()] = GuiDetailSettingsOF.I[0x82 ^ 0x8E];
            array3["  ".length()] = GuiDetailSettingsOF.I[0xD ^ 0x0];
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x80 ^ 0x8E])) {
            final String[] array4 = array2 = new String[0xA6 ^ 0xA0];
            array4["".length()] = GuiDetailSettingsOF.I[0x35 ^ 0x3A];
            array4[" ".length()] = GuiDetailSettingsOF.I[0x8F ^ 0x9F];
            array4["  ".length()] = GuiDetailSettingsOF.I[0x4C ^ 0x5D];
            array4["   ".length()] = GuiDetailSettingsOF.I[0x61 ^ 0x73];
            array4[0x39 ^ 0x3D] = GuiDetailSettingsOF.I[0xBD ^ 0xAE];
            array4[0x2E ^ 0x2B] = GuiDetailSettingsOF.I[0x9F ^ 0x8B];
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0xA4 ^ 0xB1])) {
            final String[] array5 = array2 = new String[0x3A ^ 0x3C];
            array5["".length()] = GuiDetailSettingsOF.I[0x71 ^ 0x67];
            array5[" ".length()] = GuiDetailSettingsOF.I[0x2E ^ 0x39];
            array5["  ".length()] = GuiDetailSettingsOF.I[0xBB ^ 0xA3];
            array5["   ".length()] = GuiDetailSettingsOF.I[0x25 ^ 0x3C];
            array5[0x94 ^ 0x90] = GuiDetailSettingsOF.I[0x4F ^ 0x55];
            array5[0x9F ^ 0x9A] = GuiDetailSettingsOF.I[0xBF ^ 0xA4];
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0xA ^ 0x16])) {
            final String[] array6 = array2 = new String[0x9B ^ 0x9F];
            array6["".length()] = GuiDetailSettingsOF.I[0xB8 ^ 0xA5];
            array6[" ".length()] = GuiDetailSettingsOF.I[0xA0 ^ 0xBE];
            array6["  ".length()] = GuiDetailSettingsOF.I[0x54 ^ 0x4B];
            array6["   ".length()] = GuiDetailSettingsOF.I[0x99 ^ 0xB9];
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x6 ^ 0x27])) {
            final String[] array7 = array2 = new String[0x6A ^ 0x6C];
            array7["".length()] = GuiDetailSettingsOF.I[0xBC ^ 0x9E];
            array7[" ".length()] = GuiDetailSettingsOF.I[0x7A ^ 0x59];
            array7["  ".length()] = GuiDetailSettingsOF.I[0x1F ^ 0x3B];
            array7["   ".length()] = GuiDetailSettingsOF.I[0x25 ^ 0x0];
            array7[0x98 ^ 0x9C] = GuiDetailSettingsOF.I[0x8D ^ 0xAB];
            array7[0x50 ^ 0x55] = GuiDetailSettingsOF.I[0xE ^ 0x29];
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x73 ^ 0x5B])) {
            final String[] array8 = array2 = new String[0x74 ^ 0x73];
            array8["".length()] = GuiDetailSettingsOF.I[0xA5 ^ 0x8C];
            array8[" ".length()] = GuiDetailSettingsOF.I[0x10 ^ 0x3A];
            array8["  ".length()] = GuiDetailSettingsOF.I[0x87 ^ 0xAC];
            array8["   ".length()] = GuiDetailSettingsOF.I[0x33 ^ 0x1F];
            array8[0xC7 ^ 0xC3] = GuiDetailSettingsOF.I[0xB8 ^ 0x95];
            array8[0x39 ^ 0x3C] = GuiDetailSettingsOF.I[0x84 ^ 0xAA];
            array8[0x48 ^ 0x4E] = GuiDetailSettingsOF.I[0xEA ^ 0xC5];
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x97 ^ 0xA7])) {
            final String[] array9 = array2 = new String[0x39 ^ 0x3D];
            array9["".length()] = GuiDetailSettingsOF.I[0x2E ^ 0x1F];
            array9[" ".length()] = GuiDetailSettingsOF.I[0x93 ^ 0xA1];
            array9["  ".length()] = GuiDetailSettingsOF.I[0x29 ^ 0x1A];
            array9["   ".length()] = GuiDetailSettingsOF.I[0x35 ^ 0x1];
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x18 ^ 0x2D])) {
            final String[] array10 = array2 = new String["   ".length()];
            array10["".length()] = GuiDetailSettingsOF.I[0x1A ^ 0x2C];
            array10[" ".length()] = GuiDetailSettingsOF.I[0x2B ^ 0x1C];
            array10["  ".length()] = GuiDetailSettingsOF.I[0x68 ^ 0x50];
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x5C ^ 0x65])) {
            final String[] array11 = array2 = new String["   ".length()];
            array11["".length()] = GuiDetailSettingsOF.I[0x93 ^ 0xA9];
            array11[" ".length()] = GuiDetailSettingsOF.I[0x84 ^ 0xBF];
            array11["  ".length()] = GuiDetailSettingsOF.I[0xAD ^ 0x91];
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x5 ^ 0x38])) {
            final String[] array12 = array2 = new String["   ".length()];
            array12["".length()] = GuiDetailSettingsOF.I[0x5 ^ 0x3B];
            array12[" ".length()] = GuiDetailSettingsOF.I[0x46 ^ 0x79];
            array12["  ".length()] = GuiDetailSettingsOF.I[0x41 ^ 0x1];
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x20 ^ 0x61])) {
            final String[] array13 = array2 = new String["   ".length()];
            array13["".length()] = GuiDetailSettingsOF.I[0x21 ^ 0x63];
            array13[" ".length()] = GuiDetailSettingsOF.I[0x51 ^ 0x12];
            array13["  ".length()] = GuiDetailSettingsOF.I[0x83 ^ 0xC7];
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x64 ^ 0x21])) {
            final String[] array14 = array2 = new String["   ".length()];
            array14["".length()] = GuiDetailSettingsOF.I[0x1D ^ 0x5B];
            array14[" ".length()] = GuiDetailSettingsOF.I[0x66 ^ 0x21];
            array14["  ".length()] = GuiDetailSettingsOF.I[0xE8 ^ 0xA0];
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x8 ^ 0x41])) {
            final String[] array15 = array2 = new String[0x81 ^ 0x87];
            array15["".length()] = GuiDetailSettingsOF.I[0x5 ^ 0x4F];
            array15[" ".length()] = GuiDetailSettingsOF.I[0xE2 ^ 0xA9];
            array15["  ".length()] = GuiDetailSettingsOF.I[0x7 ^ 0x4B];
            array15["   ".length()] = GuiDetailSettingsOF.I[0xCE ^ 0x83];
            array15[0x6C ^ 0x68] = GuiDetailSettingsOF.I[0xF5 ^ 0xBB];
            array15[0x63 ^ 0x66] = GuiDetailSettingsOF.I[0x38 ^ 0x77];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (s.equals(GuiDetailSettingsOF.I[0x49 ^ 0x19])) {
            final String[] array16 = array2 = new String[0x99 ^ 0x91];
            array16["".length()] = GuiDetailSettingsOF.I[0x45 ^ 0x14];
            array16[" ".length()] = GuiDetailSettingsOF.I[0xD2 ^ 0x80];
            array16["  ".length()] = GuiDetailSettingsOF.I[0x69 ^ 0x3A];
            array16["   ".length()] = GuiDetailSettingsOF.I[0x21 ^ 0x75];
            array16[0x55 ^ 0x51] = GuiDetailSettingsOF.I[0x15 ^ 0x40];
            array16[0x5F ^ 0x5A] = GuiDetailSettingsOF.I[0xFE ^ 0xA8];
            array16[0x8C ^ 0x8A] = GuiDetailSettingsOF.I[0x4A ^ 0x1D];
            array16[0x66 ^ 0x61] = GuiDetailSettingsOF.I[0x30 ^ 0x68];
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    public GuiDetailSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = GuiDetailSettingsOF.I["".length()];
        this.lastMouseX = "".length();
        this.lastMouseY = "".length();
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 73 + 2 + 27 + 98 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 1 + 151 - 79 + 127) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        int i = "".length();
        "".length();
        if (1 >= 3) {
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
                if (true != true) {
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
    
    static {
        I();
        final GameSettings.Options[] enumOptions = new GameSettings.Options[0xBB ^ 0xB6];
        enumOptions["".length()] = GameSettings.Options.CLOUDS;
        enumOptions[" ".length()] = GameSettings.Options.CLOUD_HEIGHT;
        enumOptions["  ".length()] = GameSettings.Options.TREES;
        enumOptions["   ".length()] = GameSettings.Options.RAIN;
        enumOptions[0x2 ^ 0x6] = GameSettings.Options.SKY;
        enumOptions[0x8 ^ 0xD] = GameSettings.Options.STARS;
        enumOptions[0x74 ^ 0x72] = GameSettings.Options.SUN_MOON;
        enumOptions[0x96 ^ 0x91] = GameSettings.Options.SHOW_CAPES;
        enumOptions[0x13 ^ 0x1B] = GameSettings.Options.TRANSLUCENT_BLOCKS;
        enumOptions[0x1D ^ 0x14] = GameSettings.Options.HELD_ITEM_TOOLTIPS;
        enumOptions[0x6A ^ 0x60] = GameSettings.Options.DROPPED_ITEMS;
        enumOptions[0x80 ^ 0x8B] = GameSettings.Options.ENTITY_SHADOWS;
        enumOptions[0xB ^ 0x7] = GameSettings.Options.VIGNETTE;
        GuiDetailSettingsOF.enumOptions = enumOptions;
    }
}
