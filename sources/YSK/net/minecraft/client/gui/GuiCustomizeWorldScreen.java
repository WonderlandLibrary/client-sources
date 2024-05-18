package net.minecraft.client.gui;

import com.google.common.base.*;
import java.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.biome.*;
import java.io.*;
import com.google.common.primitives.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder
{
    private Predicate<String> field_175332_D;
    private static final String[] I;
    private Random random;
    private GuiButton field_175347_t;
    private ChunkProviderSettings.Factory field_175336_F;
    private boolean field_175340_C;
    private int field_175339_B;
    private ChunkProviderSettings.Factory field_175334_E;
    private GuiCreateWorld field_175343_i;
    private GuiButton field_175352_x;
    protected String field_175335_g;
    private GuiButton field_175346_u;
    private GuiPageButtonList field_175349_r;
    protected String field_175333_f;
    private GuiButton field_175348_s;
    protected String[] field_175342_h;
    private GuiButton field_175345_v;
    private GuiButton field_175351_y;
    private GuiButton field_175344_w;
    private boolean field_175338_A;
    private GuiButton field_175350_z;
    protected String field_175341_a;
    
    private String func_175330_b(final int n, final float n2) {
        switch (n) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 107:
            case 108:
            case 110:
            case 111:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 139:
            case 140:
            case 142:
            case 143: {
                final String s = GuiCustomizeWorldScreen.I[149 + 65 - 111 + 50];
                final Object[] array = new Object[" ".length()];
                array["".length()] = n2;
                return String.format(s, array);
            }
            case 105:
            case 106:
            case 109:
            case 112:
            case 113:
            case 114:
            case 115:
            case 137:
            case 138:
            case 141:
            case 144:
            case 145:
            case 146:
            case 147: {
                final String s2 = GuiCustomizeWorldScreen.I[105 + 129 - 92 + 12];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = n2;
                return String.format(s2, array2);
            }
            default: {
                final String s3 = GuiCustomizeWorldScreen.I[61 + 87 - 95 + 102];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = (int)n2;
                return String.format(s3, array3);
            }
            case 162: {
                if (n2 < 0.0f) {
                    return I18n.format(GuiCustomizeWorldScreen.I[86 + 31 - 90 + 129], new Object["".length()]);
                }
                if ((int)n2 >= BiomeGenBase.hell.biomeID) {
                    final BiomeGenBase biomeGenBase = BiomeGenBase.getBiomeGenArray()[(int)n2 + "  ".length()];
                    String biomeName;
                    if (biomeGenBase != null) {
                        biomeName = biomeGenBase.biomeName;
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        biomeName = GuiCustomizeWorldScreen.I[41 + 50 - 82 + 148];
                    }
                    return biomeName;
                }
                final BiomeGenBase biomeGenBase2 = BiomeGenBase.getBiomeGenArray()[(int)n2];
                String biomeName2;
                if (biomeGenBase2 != null) {
                    biomeName2 = biomeGenBase2.biomeName;
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
                else {
                    biomeName2 = GuiCustomizeWorldScreen.I[55 + 41 - 48 + 110];
                }
                return biomeName2;
            }
        }
    }
    
    public void func_175324_a(final String s) {
        if (s != null && s.length() != 0) {
            this.field_175336_F = ChunkProviderSettings.Factory.jsonToFactory(s);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.field_175336_F = new ChunkProviderSettings.Factory();
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        if (this.field_175340_C) {
            this.field_175340_C = ("".length() != 0);
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else if (this.field_175339_B == 0) {
            this.field_175349_r.mouseReleased(n, n2, n3);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.field_175339_B == 0 && !this.field_175340_C) {
            this.field_175349_r.mouseClicked(n, n2, n3);
        }
    }
    
    private static void I() {
        (I = new String[108 + 68 - 31 + 18])["".length()] = I("\u000e-12* 18#e\u001a70*!m\u000b'21$6%5", "MXBFE");
        GuiCustomizeWorldScreen.I[" ".length()] = I("(;44TIz<7TK", "xZSQt");
        GuiCustomizeWorldScreen.I["  ".length()] = I("\f6)\u001c\bn\u0004?\u0001\u001f'9=\u0006", "NWZuk");
        GuiCustomizeWorldScreen.I["   ".length()] = I("\u0018\u0007\u0012\u000f\u000b\u0019\u0004H\u0005\u0011\u0004\u0003\t\u000b\r\r\u00122\u000f\u0010\u001b\u0012", "wwffd");
        GuiCustomizeWorldScreen.I[0x10 ^ 0x14] = I("%\u001d+\t1#8!\u001a)\"A-\u001d62\u0000#\u0001?#A-\u001d62\u0000#F54\n8", "FoNhE");
        GuiCustomizeWorldScreen.I[0x2A ^ 0x2F] = I("/;\u000e0!)\u001e\u0004#9(g\b$&8&\u00068/)g\b$&8&\u0006\u007f;)1\u001f", "LIkQU");
        GuiCustomizeWorldScreen.I[0x5E ^ 0x58] = I("\u0013\u0018\u001d#3\u0015=\u00170+\u0014D\u001b74\u0004\u0005\u0015+=\u0015D\u001b74\u0004\u0005\u0015l#\u0015\f\u00197+\u0004\u0019", "pjxBG");
        GuiCustomizeWorldScreen.I[0x27 ^ 0x20] = I("\u0005\u0013\u0012\"$\u00036\u00181<\u0002O\u00146#\u0012\u000e\u001a**\u0003O\u00146#\u0012\u000e\u001am\"\u0007\u000f\u0013,=\u000f\u001b\u0012", "fawCP");
        GuiCustomizeWorldScreen.I[0x2C ^ 0x24] = I("\u0010:7\u0003\u0019\u0016\u001f=\u0010\u0001\u0017f1\u0017\u001e\u0007'?\u000b\u0017\u0016f1\u0017\u001e\u0007'?L\u001d\u0001-!\u0007\u0019\u0000", "sHRbm");
        GuiCustomizeWorldScreen.I[0x82 ^ 0x8B] = I("!\u0000\b^\u0005)\u001b\u0004", "Fuapa");
        GuiCustomizeWorldScreen.I[0x38 ^ 0x32] = I("\b\"\u0006m\u0015\n$", "oWoCl");
        GuiCustomizeWorldScreen.I[0xAA ^ 0xA1] = I(".&0m,&", "ISYCB");
        GuiCustomizeWorldScreen.I[0x8F ^ 0x83] = I("*=\u00069\u0002,\u0018\f*\u001a-a\u0000-\u0005= \u000e1\f,a\u0000-\u0005= \u000ev\u0005,./=\u0000,#", "IOcXv");
        GuiCustomizeWorldScreen.I[0x1B ^ 0x16] = I(":*4&\u0018<\u000f>5\u0000=v22\u001f-7<.\u0016<v22\u001f-7<i\u0019*=\u0012&\u001a<+", "YXQGl");
        GuiCustomizeWorldScreen.I[0x1C ^ 0x12] = I("$$\u0001\u0017\u0005\"\u0001\u000b\u0004\u001d#x\u0007\u0003\u000239\t\u001f\u000b\"x\u0007\u0003\u000239\tX\u0004437\u0002\u0003(8\u0003\u001e\u001e+2\u0017", "GVdvq");
        GuiCustomizeWorldScreen.I[0x12 ^ 0x1D] = I("(\u000b!,\u0019..+?\u0001/W'8\u001e?\u0016)$\u0017.W'8\u001e?\u0016)c\u00188\u001c\u0012$\u0001'\u0018#(\u001e", "KyDMm");
        GuiCustomizeWorldScreen.I[0x22 ^ 0x32] = I("\u001b4&+\u0002\u001d\u0011,8\u001a\u001ch ?\u0005\f).#\f\u001dh ?\u0005\f).d\u0003\u000b#\u000e#\u0018\u001d\u0015++\u0010\f5", "xFCJv");
        GuiCustomizeWorldScreen.I[0x96 ^ 0x87] = I("\u0006\u001f3-1\u0000:9>)\u0001C596\u0011\u0002;%?\u0000C596\u0011\u0002;b0\u0016\b\u0002)(\u0015\u00013?", "emVLE");
        GuiCustomizeWorldScreen.I[0x69 ^ 0x7B] = I(".4&\u0002>(\u0011,\u0011&)h \u001699).\n0(h \u001699).M?>#\u000e\f$8+&\r>>", "MFCcJ");
        GuiCustomizeWorldScreen.I[0xB1 ^ 0xA2] = I("/<\u0012\u0005\u001d)\u0019\u0018\u0016\u0005(`\u0014\u0011\u001a8!\u001a\r\u0013)`\u0014\u0011\u001a8!\u001aJ\u001c?+%\u0005\u001f% \u0012\u0017", "LNwdi");
        GuiCustomizeWorldScreen.I[0x33 ^ 0x27] = I("\u001b\b#4\u001a\u001d-)'\u0002\u001cT% \u001d\f\u0015+<\u0014\u001dT% \u001d\f\u0015+{\u001b\u000b\u001f\u0002 \u0000\u001f\u001f);\u001d", "xzFUn");
        GuiCustomizeWorldScreen.I[0x24 ^ 0x31] = I("\u00140/\u0018\u001a\u0012\u0015%\u000b\u0002\u0013l)\f\u001d\u0003-'\u0010\u0014\u0012l)\f\u001d\u0003-'W\n\u0002,-\u001c\u0001\u0019\u0001\"\u0018\u0000\u0014'", "wBJyn");
        GuiCustomizeWorldScreen.I[0x38 ^ 0x2E] = I("(*\u00045\u0003.\u000f\u000e&\u001b/v\u0002!\u0004?7\f=\r.v\u0002!\u0004?7\fz\u00028=65\u0003.*-5\u001c.+", "KXaTw");
        GuiCustomizeWorldScreen.I[0x72 ^ 0x65] = I("3:\u0017\u0016\r5\u001f\u001d\u0005\u00154f\u0011\u0002\n$'\u001f\u001e\u00035f\u0011\u0002\n$'\u001fY\u000e1<\u0017\u000551#\u00174\u00111&\u0011\u0012", "PHrwy");
        GuiCustomizeWorldScreen.I[0xA9 ^ 0xB1] = I("3+#/\u001c5\u000e)<\u00044w%;\u001b$6+'\u00125w%;\u001b$6+`\u001d#<\n/\u001e1\u0015'%\r#", "PYFNh");
        GuiCustomizeWorldScreen.I[0x46 ^ 0x5F] = I("/0!\u000b\")\u0015+\u0018:(l'\u001f%8-)\u0003,)l'\u001f%8-)D:-4%&7''\u0007\u00027\"!!", "LBDjV");
        GuiCustomizeWorldScreen.I[0x46 ^ 0x5C] = I("\n\u0001\u0017\u0003\u0016\f$\u001d\u0010\u000e\r]\u0011\u0017\u0011\u001d\u001c\u001f\u000b\u0018\f]\u0011\u0017\u0011\u001d\u001c\u001fL\u0017\u001a\u0016>\u0003\u0014\b<\u0011\u0007\u0003\u0007\u0000", "isrbb");
        GuiCustomizeWorldScreen.I[0xBF ^ 0xA4] = I("'1\u0012;#!\u0014\u0018(; m\u0014/$0,\u001a3-!m\u0014/$0,\u001at1-;\u0012>\u0015-,\u001a?", "DCwZW");
        GuiCustomizeWorldScreen.I[0x84 ^ 0x98] = I("$!0\b\r\"\u0004:\u001b\u0015#}6\u001c\n3<8\u0000\u0003\"}6\u001c\n3<8G\u001b.<8\f*.)0", "GSUiy");
        GuiCustomizeWorldScreen.I[0x38 ^ 0x25] = I("\"\"\u0004(\u000e$\u0007\u000e;\u0016%~\u0002<\t5?\f \u0000$~\u0002<\t5?\fg\b(&\u0004;)(*\u0004", "APaIz");
        GuiCustomizeWorldScreen.I[0x75 ^ 0x6B] = I(",+'+B<+9:B6#&+", "XBKNl");
        GuiCustomizeWorldScreen.I[0x8A ^ 0x95] = I("\u0010%)\u0000\r\u0016\u0000#\u0013\u0015\u0017y/\u0014\n\u00078!\b\u0003\u0016y/\u0014\n\u00078!O\n\u001a-)", "sWLay");
        GuiCustomizeWorldScreen.I[0x2E ^ 0xE] = I("\u0017\u0000\u00116\u0002\u0011%\u001b%\u001a\u0010\\\u0017\"\u0005\u0000\u001d\u0019>\f\u0011\\\u0017\"\u0005\u0000\u001d\u0019y\u0015\u001b\u0007\u001a#", "trtWv");
        GuiCustomizeWorldScreen.I[0xB8 ^ 0x99] = I("\u0014:5\u0018'\u0012\u001f?\u000b?\u0013f3\f \u0003'=\u0010)\u0012f3\f \u0003'=W>\u001e&\u0018\u001c:\u0010 $", "wHPyS");
        GuiCustomizeWorldScreen.I[0x78 ^ 0x5A] = I("\u0015\u0011\u00111\u0018\u00134\u001b\"\u0000\u0012M\u0017%\u001f\u0002\f\u00199\u0016\u0013M\u0017%\u001f\u0002\f\u0019~\u0001\u0017\u001b<5\u0005\u0011\u000b\u0000", "vctPl");
        GuiCustomizeWorldScreen.I[0x26 ^ 0x5] = I("089\rM##4\u001e\u0006(\u007f;\t\u000e!", "DQUhc");
        GuiCustomizeWorldScreen.I[0x82 ^ 0xA6] = I("51\u000b\u0017\u00043\u0014\u0001\u0004\u001c2m\r\u0003\u0003\",\u0003\u001f\n3m\r\u0003\u0003\",\u0003X\u0003?9\u000b", "VCnvp");
        GuiCustomizeWorldScreen.I[0xAC ^ 0x89] = I(")\u00062'\u001e/#84\u0006.Z43\u0019>\u001b:/\u0010/Z43\u0019>\u001b:h\t%\u000192", "JtWFj");
        GuiCustomizeWorldScreen.I[0x7C ^ 0x5A] = I("51\u0002\u0000\u00063\u0014\b\u0013\u001e2m\u0004\u0014\u0001\",\n\b\b3m\u0004\u0014\u0001\",\nO\u001f?-/\u0004\u001b1+\u0013", "VCgar");
        GuiCustomizeWorldScreen.I[0x5D ^ 0x7A] = I("\u000b\u0010\u0013\u0017\u0003\r5\u0019\u0004\u001b\fL\u0015\u0003\u0004\u001c\r\u001b\u001f\r\rL\u0015\u0003\u0004\u001c\r\u001bX\u001a\t\u001a>\u0013\u001e\u000f\n\u0002", "hbvvw");
        GuiCustomizeWorldScreen.I[0xA0 ^ 0x88] = I("\u0013\u0018\u001d5E\u0014\u0005\u001e>\u000eI\u0016\u00031\u0005\u000e\u0005\u0014~\u0005\u0006\u001c\u0014", "gqqPk");
        GuiCustomizeWorldScreen.I[0xEF ^ 0xC6] = I("+\u0014\u0014\u0006--1\u001e\u00155,H\u0012\u0012*<\t\u001c\u000e#-H\u0012\u0012*<\t\u001cI*!\u001c\u0014", "HfqgY");
        GuiCustomizeWorldScreen.I[0x89 ^ 0xA3] = I("(\u001a=/\u001e.?7<\u0006/F;;\u0019?\u00075'\u0010.F;;\u0019?\u00075`\t$\u001d6:", "KhXNj");
        GuiCustomizeWorldScreen.I[0x6A ^ 0x41] = I(".\u0017\u001c\u0016-(2\u0016\u00055)K\u001a\u0002*9\n\u0014\u001e#(K\u001a\u0002*9\n\u0014Y4$\u000b1\u00120*\r\r", "MeywY");
        GuiCustomizeWorldScreen.I[0x2A ^ 0x6] = I("5\u0010\u00147\r35\u001e$\u00152L\u0012#\n\"\r\u001c?\u00033L\u0012#\n\"\r\u001cx\u00147\u001a93\u00101\n\u0005", "VbqVy");
        GuiCustomizeWorldScreen.I[0x68 ^ 0x45] = I("\u0005\u0013-(\\\u0002\u000e.#\u0017_\u001e(\"\u0000\u0018\u000e$c\u001c\u0010\u0017$", "qzAMr");
        GuiCustomizeWorldScreen.I[0xAE ^ 0x80] = I("0\u000b\u00034'6.\t'?7W\u0005  '\u0016\u000b<)6W\u0005  '\u0016\u000b{ :\u0003\u0003", "SyfUS");
        GuiCustomizeWorldScreen.I[0x5B ^ 0x74] = I("\u0014=/,,\u0012\u0018%?4\u0013a)8+\u0003 '$\"\u0012a)8+\u0003 'c;\u0018:$9", "wOJMX");
        GuiCustomizeWorldScreen.I[0x86 ^ 0xB6] = I("4\u000b\u00121\u00002.\u0018\"\u00183W\u0014%\u0007#\u0016\u001a9\u000e2W\u0014%\u0007#\u0016\u001a~\u0019>\u0017?5\u001d0\u0011\u0003", "WywPt");
        GuiCustomizeWorldScreen.I[0xF7 ^ 0xC6] = I("*$ %;,\u0001*6#-x&1<=9(-5,x&1<=9(j\"(.\r!&.>1", "IVEDO");
        GuiCustomizeWorldScreen.I[0x7A ^ 0x48] = I("?\u0002\n\u000eZ8\u001f\t\u0005\u0011e\n\b\u000f\u00118\u0002\u0012\u000eZ%\n\u000b\u000e", "Kkfkt");
        GuiCustomizeWorldScreen.I[0x58 ^ 0x6B] = I("\b\u00065&0\u000e#?5(\u000fZ327\u001f\u001b=.>\u000eZ327\u001f\u001b=i7\u0002\u000e5", "ktPGD");
        GuiCustomizeWorldScreen.I[0x27 ^ 0x13] = I("-(1\t\u000e+\r;\u001a\u0016*t7\u001d\t:59\u0001\u0000+t7\u001d\t:59F\u0019!/:\u001c", "NZThz");
        GuiCustomizeWorldScreen.I[0x33 ^ 0x6] = I(":\u0015\u0000\u00127<0\n\u0001/=I\u0006\u00060-\b\b\u001a9<I\u0006\u00060-\b\b].0\t-\u0016*>\u000f\u0011", "YgesC");
        GuiCustomizeWorldScreen.I[0x9C ^ 0xAA] = I("\u0006 \u0014-\u001d\u0000\u0005\u001e>\u0005\u0001|\u00129\u001a\u0011=\u001c%\u0013\u0000|\u00129\u001a\u0011=\u001cb\u0004\u0004*9)\u0000\u0002:\u0005", "eRqLi");
        GuiCustomizeWorldScreen.I[0xBC ^ 0x8B] = I("\u0013##)y\b8*\u000f8\u0006&a\"6\n/", "gJOLW");
        GuiCustomizeWorldScreen.I[0xBA ^ 0x82] = I("\u0005>,\u0002\u0019\u0003\u001b&\u0011\u0001\u0002b*\u0016\u001e\u0012#$\n\u0017\u0003b*\u0016\u001e\u0012#$M\u001e\u000f6,", "fLIcm");
        GuiCustomizeWorldScreen.I[0x1E ^ 0x27] = I("\n\n\u001f\n6\f/\u0015\u0019.\rV\u0019\u001e1\u001d\u0017\u0017\u00028\fV\u0019\u001e1\u001d\u0017\u0017E!\u0006\r\u0014\u001f", "ixzkB");
        GuiCustomizeWorldScreen.I[0x8E ^ 0xB4] = I("\u001a* \n\u000e\u001c\u000f*\u0019\u0016\u001dv&\u001e\t\r7(\u0002\u0000\u001cv&\u001e\t\r7(E\u0017\u00106\r\u000e\u0013\u001e01", "yXEkz");
        GuiCustomizeWorldScreen.I[0x2F ^ 0x14] = I("&\u00196\u001b& <<\b>!E0\u000f!1\u0004>\u0013( E0\u000f!1\u0004>T?$\u0013\u001b\u001f;\"\u0003'", "EkSzR");
        GuiCustomizeWorldScreen.I[0x3 ^ 0x3F] = I("\r\u0001\u000e\u0015J\u0016\u001a\u00079\u0016\u0016\u0006L\u001e\u0005\u0014\r", "yhbpd");
        GuiCustomizeWorldScreen.I[0x2D ^ 0x10] = I("/\u001a\u00138\u0006)?\u0019+\u001e(F\u0015,\u00018\u0007\u001b0\b)F\u0015,\u00018\u0007\u001bw\u0001%\u0012\u0013", "LhvYr");
        GuiCustomizeWorldScreen.I[0x7E ^ 0x40] = I(" >\u001c%\u0001&\u001b\u00166\u0019'b\u001a1\u00067#\u0014-\u000f&b\u001a1\u00067#\u0014j\u0016,9\u00170", "CLyDu");
        GuiCustomizeWorldScreen.I[0x6A ^ 0x55] = I("::\u000e(2<\u001f\u0004;*=f\b<5-'\u0006 <<f\b<5-'\u0006g+0&#,/> \u001f", "YHkIF");
        GuiCustomizeWorldScreen.I[0x12 ^ 0x52] = I("+4\u0000-2-\u0011\n>*,h\u000695<)\b%<-h\u000695<)\bb+)>-)//.\u0011", "HFeLF");
        GuiCustomizeWorldScreen.I[0xEE ^ 0xAF] = I("\u0018!\u000f\u0014i\u0003:\u00066(\u0000,M\u001f&\u0001-", "lHcqG");
        GuiCustomizeWorldScreen.I[0x4E ^ 0xC] = I("'\u001e\u0002\u000b-!;\b\u00185 B\u0004\u001f*0\u0003\n\u0003#!B\u0004\u001f*0\u0003\nD*-\u0016\u0002", "DlgjY");
        GuiCustomizeWorldScreen.I[0xC9 ^ 0x8A] = I("7<*521\u0019 &*0`,!5 !\"=<1`,!5 !\"z%;;! ", "TNOTF");
        GuiCustomizeWorldScreen.I[0xC6 ^ 0x82] = I("-4)1\f+\u0011#\"\u0014*h/%\u000b:)!9\u0002+h/%\u000b:)!~\u0015'(\u00045\u0011).8", "NFLPx");
        GuiCustomizeWorldScreen.I[0xF7 ^ 0xB2] = I("\b9.\u0000?\u000e\u001c$\u0013'\u000fe(\u00148\u001f$&\b1\u000ee(\u00148\u001f$&O&\n3\u0003\u0004\"\f#?", "kKKaK");
        GuiCustomizeWorldScreen.I[0x82 ^ 0xC4] = I(".-'\rz56.:1>7?\u0007:?j%\t9?", "ZDKhT");
        GuiCustomizeWorldScreen.I[0x1A ^ 0x5D] = I("'!=\u0015\u0018!\u00047\u0006\u0000 };\u0001\u001f0<5\u001d\u0016!};\u0001\u001f0<5Z\u001f-)=", "DSXtl");
        GuiCustomizeWorldScreen.I[0x7B ^ 0x33] = I(")\u0005#\u000f&/ )\u001c>.Y%\u001b!>\u0018+\u0007(/Y%\u001b!>\u0018+@1%\u0002(\u001a", "JwFnR");
        GuiCustomizeWorldScreen.I[0x24 ^ 0x6D] = I("+4\u001f\n\u0000-\u0011\u0015\u0019\u0018,h\u0019\u001e\u0007<)\u0017\u0002\u000e-h\u0019\u001e\u0007<)\u0017E\u0019!(2\u000e\u001d/.\u000e", "HFzkt");
        GuiCustomizeWorldScreen.I[0x2C ^ 0x66] = I("\u0014'\u0004+!\u0012\u0002\u000e89\u0013{\u0002?&\u0003:\f#/\u0012{\u0002?&\u0003:\fd8\u0016-)/<\u0010=\u0015", "wUaJU");
        GuiCustomizeWorldScreen.I[0xC8 ^ 0x83] = I("\u0019\u0004\u0004\u0017{\u0002\u001f\r6<\f\u0000\u0007\u001c1C\u0003\t\u001f0", "mmhrU");
        GuiCustomizeWorldScreen.I[0x6B ^ 0x27] = I("+5!&\u001c-\u0010+5\u0004,i'2\u001b<().\u0012-i'2\u001b<()i\u001b!=!", "HGDGh");
        GuiCustomizeWorldScreen.I[0x58 ^ 0x15] = I("\u000e\u0005\u0013+\u001a\b \u00198\u0002\tY\u0015?\u001d\u0019\u0018\u001b#\u0014\bY\u0015?\u001d\u0019\u0018\u001bd\r\u0002\u0002\u0018>", "mwvJn");
        GuiCustomizeWorldScreen.I[0x5C ^ 0x12] = I("4\u0014*\u0019\u001c21 \n\u00043H,\r\u001b#\t\"\u0011\u00122H,\r\u001b#\t\"V\u0005>\b\u0007\u001d\u00010\u000e;", "WfOxh");
        GuiCustomizeWorldScreen.I[0xFA ^ 0xB5] = I("\u001a%\u0010\"9\u001c\u0000\u001a1!\u001dy\u00166>\r8\u0018*7\u001cy\u00166>\r8\u0018m \u0018/=&$\u001e?\u0001", "yWuCM");
        GuiCustomizeWorldScreen.I[0xD7 ^ 0x87] = I("\u0010\"\u00026A\u000b9\u000b\u001f\u000e\u0014\"\u001d}\u0001\u0005&\u000b", "dKnSo");
        GuiCustomizeWorldScreen.I[0xCA ^ 0x9B] = I("%1\u0002\u000f\u0010#\u0014\b\u001c\b\"m\u0004\u001b\u00172,\n\u0007\u001e#m\u0004\u001b\u00172,\n@\u0017/9\u0002", "FCgnd");
        GuiCustomizeWorldScreen.I[0x96 ^ 0xC4] = I("\n\u0002'\t\u0003\f'-\u001a\u001b\r^!\u001d\u0004\u001d\u001f/\u0001\r\f^!\u001d\u0004\u001d\u001f/F\u0014\u0006\u0005,\u001c", "ipBhw");
        GuiCustomizeWorldScreen.I[0x4D ^ 0x1E] = I("7\u001e\u0002\u0010\u00131;\b\u0003\u000b0B\u0004\u0004\u0014 \u0003\n\u0018\u001d1B\u0004\u0004\u0014 \u0003\n_\u00041\u0002\u0013\u0014\u0015", "Tlgqg");
        GuiCustomizeWorldScreen.I[0x10 ^ 0x44] = I("\u000e(.7 \b\r$$8\tt(#'\u00195&?.\bt(#'\u00195&x'\u001d(.70", "mZKVT");
        GuiCustomizeWorldScreen.I[0x28 ^ 0x7D] = I("\u0019=\u00136=\u001f\u0018\u0019%%\u001ea\u0015\":\u000e \u001b>3\u001fa\u0015\":\u000e \u001by$\u001b&\u0018\u0019&\u0013<\u0013\u0004*\u001b#\u0013\u000f", "zOvWI");
        GuiCustomizeWorldScreen.I[0x29 ^ 0x7F] = I("';\u0012\u0004\u0017!\u001e\u0018\u0017\u000f g\u0014\u0010\u00100&\u001a\f\u0019!g\u0014\u0010\u00100&\u001aK\u000e% \u0019+\f-:\u00126\u0000%%\u0012<", "DIwec");
        GuiCustomizeWorldScreen.I[0xD2 ^ 0x85] = I(";:?79=\u001f5$!<f9#>,'7?7=f9#>,'7x 9!4\u0018\"1;?\u0005.9$?\f", "XHZVM");
        GuiCustomizeWorldScreen.I[0xEE ^ 0xB6] = I("\n1+&:\f\u0014!5\"\rm-2=\u001d,#.4\fm-2=\u001d,#i*\f3:/\u0000\u0006*=\"\u001d\n\"\"\"\u0016", "iCNGN");
        GuiCustomizeWorldScreen.I[0x27 ^ 0x7E] = I("4\u0007\u0015\u0011\u001b2\"\u001f\u0002\u00033[\u0013\u0005\u001c#\u001a\u001d\u0019\u00152[\u0013\u0005\u001c#\u001a\u001d^\u000b2\u0005\u0004\u0018!8\u001c\u0003\u0015<4\u0014\u001c\u00155", "Wuppo");
        GuiCustomizeWorldScreen.I[0x3A ^ 0x60] = I(".#\u000f(\u001c(\u0006\u0005;\u0004)\u007f\t<\u001b9>\u0007 \u0012(\u007f\t<\u001b9>\u0007g\f(!\u001e!&\"8\u0019,;.0\u0006,-5!\u0005'\r#%", "MQjIh");
        GuiCustomizeWorldScreen.I[0x63 ^ 0x38] = I(":\u0013\u0013\u0002\u0007<6\u0019\u0011\u001f=O\u0015\u0016\u0000-\u000e\u001b\n\t<O\u0015\u0016\u0000-\u000e\u001bM\u00118\u0012\u00130\u001a#\u0004", "Yavcs");
        GuiCustomizeWorldScreen.I[0x6B ^ 0x37] = I("\u0006\u0019='>\u0000<74&\u0001E;39\u0011\u00045/0\u0000E;39\u0011\u00045h)\n\u0004*\"#\u000b\n,#\u0019\u0006\n4#", "ekXFJ");
        GuiCustomizeWorldScreen.I[0x23 ^ 0x7E] = I("\u001b\u0005\t\f\u001b\u001d \u0003\u001f\u0003\u001cY\u000f\u0018\u001c\f\u0018\u0001\u0004\u0015\u001dY\u000f\u0018\u001c\f\u0018\u0001C\u0007\u001d\u001e\u000b\u0005\u001b+\u0014\r\u0001\n", "xwlmo");
        GuiCustomizeWorldScreen.I[0xE2 ^ 0xBC] = I(")\u0000/%5/%%6-.\\)12>\u001d'-;/\\)12>\u001d'j2>\u0000/0\"\"+", "JrJDA");
        GuiCustomizeWorldScreen.I[0x7 ^ 0x58] = I(" !2*\u0005&\u000489\u001d'}4>\u00027<:\"\u000b&}4>\u00027<:e\u00043#29=*>>?\" 2;.", "CSWKq");
        GuiCustomizeWorldScreen.I[0x25 ^ 0x45] = I("\f7*\f3\n\u0012 \u001f+\u000bk,\u00184\u001b*\"\u0004=\nk,\u00184\u001b*\"C+\u00002*\u001f\u000b\u0006(&\u0019\u0014\f$#\b", "oEOmG");
        GuiCustomizeWorldScreen.I[0x4D ^ 0x2C] = I("\f\u001c4\u00186\n9>\u000b.\u000b@2\f1\u001b\u0001<\u00108\n@2\f1\u001b\u0001<W \u0006\u0001<\u001c\u0006\n\u001e%\u0011\u0015\n\u00076\u00116", "onQyB");
        GuiCustomizeWorldScreen.I[0x2D ^ 0x4F] = I("+\b\u0010\u0010\u0007--\u001a\u0003\u001f,T\u0016\u0004\u0000<\u0015\u0018\u0018\t-T\u0016\u0004\u0000<\u0015\u0018_\u0011!\u0015\u0018\u00147-\n\u0001\u0019<.\u001c\u0006\u0014\u0007", "Hzuqs");
        GuiCustomizeWorldScreen.I[0x67 ^ 0x4] = I("\u0001\u001a\u0012\f#\u0007?\u0018\u001f;\u0006F\u0014\u0018$\u0016\u0007\u001a\u0004-\u0007F\u0014\u0018$\u0016\u0007\u001aC5\u000b\u0007\u001a\b\u0004\u0001\t\u001b\b\u0000\u0007\u0001\u0010\u0005#", "bhwmW");
        GuiCustomizeWorldScreen.I[0x4F ^ 0x2B] = I("\u0002*\u001c# \u0004\u000f\u001608\u0005v\u001a7'\u00157\u0014+.\u0004v\u001a7'\u00157\u0014l6\b7\u0014'\u0007\u00029\u0015'\u001b\u0007>\n' ", "aXyBT");
        GuiCustomizeWorldScreen.I[0x5A ^ 0x3F] = I("\u00194#\u00119\u001f\u0011)\u0002!\u001eh%\u0005>\u000e)+\u00197\u001fh%\u0005>\u000e)+^ \u001b/(>\"\u00135##.\u001b*#(", "zFFpM");
        GuiCustomizeWorldScreen.I[0xEC ^ 0x8A] = I("s", "IQQZl");
        GuiCustomizeWorldScreen.I[0x21 ^ 0x46] = I("_aWr\n", "zTyAl");
        GuiCustomizeWorldScreen.I[0xE4 ^ 0x8C] = I("7\u00060\u001b.1#:\b60Z6\u000f) \u001b8\u0013 1Z6\u000f) \u001b8T75\u001d;45=\u00070)95\u00180#", "TtUzZ");
        GuiCustomizeWorldScreen.I[0x65 ^ 0xC] = I("\\", "ftYCJ");
        GuiCustomizeWorldScreen.I[0xDA ^ 0xB0] = I("rgiu\u0004", "WRGFb");
        GuiCustomizeWorldScreen.I[0xD9 ^ 0xB2] = I("\u001b\n\u0012-,\u001d/\u0018>4\u001cV\u00149+\f\u0017\u001a%\"\u001dV\u00149+\f\u0017\u001ab5\u0019\u0011\u0019\u00027\u0011\u000b\u0012\u001f;\u0019\u0014\u0012\u0016", "xxwLX");
        GuiCustomizeWorldScreen.I[0x6F ^ 0x3] = I("[", "atEFY");
        GuiCustomizeWorldScreen.I[0x27 ^ 0x4A] = I("lRBT\u0017", "Iglgq");
        GuiCustomizeWorldScreen.I[0xC3 ^ 0xAD] = I("/&\u0014,\u0006)\u0003\u001e?\u001e(z\u00128\u00018;\u001c$\b)z\u00128\u00018;\u001cc\u0016)$\u0005%<#=\u0002(!/5\u001d(*", "LTqMr");
        GuiCustomizeWorldScreen.I[0xCB ^ 0xA4] = I("`", "ZZTRa");
        GuiCustomizeWorldScreen.I[0x31 ^ 0x41] = I("Hrt]\b", "mGZnn");
        GuiCustomizeWorldScreen.I[0x68 ^ 0x19] = I("35\r'65\u0010\u00074.4i\u000b31$(\u0005/85i\u000b31$(\u0005h&57\u001c.\f?.\u001b#\u00113&\u0004#\u0018", "PGhFB");
        GuiCustomizeWorldScreen.I[0x36 ^ 0x44] = I("X", "bVAEu");
        GuiCustomizeWorldScreen.I[0x5B ^ 0x28] = I("Agbb\u0000", "dRLQf");
        GuiCustomizeWorldScreen.I[0x67 ^ 0x13] = I("\u0011\u0000\u0010\u0019\u0010\u0017%\u001a\n\b\u0016\\\u0016\r\u0017\u0006\u001d\u0018\u0011\u001e\u0017\\\u0016\r\u0017\u0006\u001d\u0018V\u0000\u0017\u0002\u0001\u0010*\u001d\u001b\u0006\u001d7\u0011\u0013\u0019\u001d!\n\u0002\u001a\u0016\u0001\u001c\u0006", "rruxd");
        GuiCustomizeWorldScreen.I[0x4C ^ 0x39] = I("\u007f", "EGLrZ");
        GuiCustomizeWorldScreen.I[0xFD ^ 0x8B] = I("PHky\u001f", "uzEJy");
        GuiCustomizeWorldScreen.I[0x32 ^ 0x45] = I("\u00124#+ \u0014\u0011)88\u0015h%?'\u0005)+#.\u0014h%?'\u0005)+d6\u00105#\u0019=\u000b#", "qFFJT");
        GuiCustomizeWorldScreen.I[0xB8 ^ 0xC0] = I("o", "UCjpo");
        GuiCustomizeWorldScreen.I[0xD6 ^ 0xAF] = I("q}Gi\t", "TOiZo");
        GuiCustomizeWorldScreen.I[0xDF ^ 0xA5] = I("\u0000\u00064\u0005,\u0006#>\u00164\u0007Z2\u0011+\u0017\u001b<\r\"\u0006Z2\u0011+\u0017\u001b<J;\f\u001b#\u00001\r\u0015%\u0001\u000b\u0000\u0015=\u0001", "ctQdX");
        GuiCustomizeWorldScreen.I[0xD8 ^ 0xA3] = I("]", "gKeoX");
        GuiCustomizeWorldScreen.I[0x30 ^ 0x4C] = I("f\\mD\r", "CiCwk");
        GuiCustomizeWorldScreen.I[0x7F ^ 0x2] = I("&\u000b<\u0003; .6\u0010#!W:\u0017<1\u00164\u000b5 W:\u0017<1\u00164L' \u0010>\n;\u0016\u001a8\u000e*", "EyYbO");
        GuiCustomizeWorldScreen.I[0xCA ^ 0xB4] = I("C", "yIvvr");
        GuiCustomizeWorldScreen.I[99 + 66 - 91 + 53] = I("v\\KP5", "SiecS");
        GuiCustomizeWorldScreen.I[59 + 70 - 30 + 29] = I(", \u0012(\f*\u0005\u0018;\u0014+|\u0014<\u000b;=\u001a \u0002*|\u0014<\u000b;=\u001ag\u000b; \u0012=\u001b'\u000b", "ORwIx");
        GuiCustomizeWorldScreen.I[52 + 82 - 23 + 18] = I("K", "qLADz");
        GuiCustomizeWorldScreen.I[76 + 94 - 157 + 117] = I("bJk_-", "GxElK");
        GuiCustomizeWorldScreen.I[101 + 63 - 81 + 48] = I("\f5+0!\n\u0010!#9\u000bi-$&\u001b(#8/\ni-$&\u001b(#\u007f \u001f7+#\u0019\u0006*'%\u0006\f&\"4", "oGNQU");
        GuiCustomizeWorldScreen.I[66 + 65 - 88 + 89] = I("K", "qebIF");
        GuiCustomizeWorldScreen.I[5 + 35 + 23 + 70] = I("Rody\u0017", "wZJJq");
        GuiCustomizeWorldScreen.I[11 + 35 + 39 + 49] = I("/\u001f\u0007\n\u0004):\r\u0019\u001c(C\u0001\u001e\u00038\u0002\u000f\u0002\n)C\u0001\u001e\u00038\u0002\u000fE\u001c#\u001a\u0007\u0019<%\u0000\u000b\u001f#/\f\u000e\u000e", "Lmbkp");
        GuiCustomizeWorldScreen.I[4 + 17 - 19 + 133] = I("\\", "fArft");
        GuiCustomizeWorldScreen.I[86 + 106 - 66 + 10] = I("vEy\u007f\u0012", "SpWLt");
        GuiCustomizeWorldScreen.I[59 + 73 - 47 + 52] = I(",\u001f&*\r*:,9\u0015+C >\n;\u0002.\"\u0003*C >\n;\u0002.e\u001b&\u0002..=*\u001d7#.*\u0004$#\r", "OmCKy");
        GuiCustomizeWorldScreen.I[120 + 43 - 28 + 3] = I("J", "pDSWR");
        GuiCustomizeWorldScreen.I[114 + 124 - 210 + 111] = I("iaxj\r", "LSVYk");
        GuiCustomizeWorldScreen.I[92 + 49 - 74 + 73] = I("\n:/2\u0002\f\u001f%!\u001a\rf)&\u0005\u001d'':\f\ff)&\u0005\u001d''}\u0014\u0000''62\f8>;9\u000f.96\u0002", "iHJSv");
        GuiCustomizeWorldScreen.I[129 + 129 - 172 + 55] = I("r", "HSGdj");
        GuiCustomizeWorldScreen.I[60 + 93 - 13 + 2] = I("M[ad3", "hiOWU");
        GuiCustomizeWorldScreen.I[14 + 47 + 14 + 68] = I("/\b\u0013\f\f)-\u0019\u001f\u0014(T\u0015\u0018\u000b8\u0015\u001b\u0004\u0002)T\u0015\u0018\u000b8\u0015\u001bC\u001a%\u0015\u001b\b+/\u001b\u001a\b/)\u0013\u0011\u0005\f", "Lzvmx");
        GuiCustomizeWorldScreen.I[14 + 32 + 7 + 91] = I("t", "NNPcP");
        GuiCustomizeWorldScreen.I[141 + 15 - 146 + 135] = I("v{M^%", "SIcmC");
        GuiCustomizeWorldScreen.I[77 + 100 - 108 + 77] = I("\u0007\u0007' \u0003\u0001\"-3\u001b\u0000[!4\u0004\u0010\u001a/(\r\u0001[!4\u0004\u0010\u001a/o\u0015\r\u001a/$$\u0007\u0014.$8\u0002\u00131$\u0003", "duBAw");
        GuiCustomizeWorldScreen.I[133 + 137 - 196 + 73] = I("[", "aIAch");
        GuiCustomizeWorldScreen.I[53 + 19 + 45 + 31] = I("U\\Oj#", "pnaYE");
        GuiCustomizeWorldScreen.I[107 + 65 - 29 + 6] = I("\">\n4\u0015$\u001b\u0000'\r%b\f \u00125#\u0002<\u001b$b\f \u00125#\u0002{\u0011 +\n", "ALoUa");
        GuiCustomizeWorldScreen.I[87 + 133 - 186 + 116] = I("s", "yiRgR");
        GuiCustomizeWorldScreen.I[14 + 96 - 2 + 43] = I("", "rMZkM");
        GuiCustomizeWorldScreen.I[30 + 88 - 95 + 129] = I("xh", "BHKXe");
        GuiCustomizeWorldScreen.I[141 + 105 - 152 + 59] = I("]fxF\u000f", "xSVui");
        GuiCustomizeWorldScreen.I[106 + 73 - 116 + 91] = I("DhwR%", "aZYaC");
        GuiCustomizeWorldScreen.I[126 + 118 - 141 + 52] = I("a\u0005", "Datjr");
        GuiCustomizeWorldScreen.I[152 + 100 - 136 + 40] = I("\u0017\u0013\u0004`4\u001c\n", "pfmNU");
        GuiCustomizeWorldScreen.I[65 + 148 - 204 + 148] = I("Z", "egIBD");
        GuiCustomizeWorldScreen.I[5 + 62 - 47 + 138] = I("O", "prYMn");
        GuiCustomizeWorldScreen.I[158 + 36 - 165 + 130] = I("\u0004\f+1Z\u0016\u0002#?=\b\u0007-9\u0015\u0012\f6", "fcDZt");
        GuiCustomizeWorldScreen.I[70 + 146 - 156 + 100] = I("'\u0001\u000e7\u001d!$\u0004$\u0005 ]\b#\u001a0\u001c\u0006?\u0013!]\b#\u001a0\u001c\u0006x\n+\u001d\r?\u001b)'\u0002\"\u0005!", "DskVi");
        GuiCustomizeWorldScreen.I[40 + 16 - 23 + 128] = I("\u0017 1\u0010\u0016\u0011\u0005;\u0003\u000e\u0010|7\u0004\u0011\u0000=9\u0018\u0018\u0011|7\u0004\u0011\u0000=9_\u0001\u001b<2\u0018\u0010\u0019c", "tRTqb");
        GuiCustomizeWorldScreen.I[104 + 60 - 111 + 109] = I("2\u001f+\u001734:!\u0004+5C-\u00034%\u0002#\u001f=4C-\u00034%\u0002#X$>\u0003(\u001f5<_", "QmNvG");
    }
    
    public String func_175323_a() {
        return this.field_175336_F.toString().replace(GuiCustomizeWorldScreen.I[72 + 96 - 89 + 71], GuiCustomizeWorldScreen.I[65 + 27 - 87 + 146]);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (this.field_175339_B == 0) {
            switch (n) {
                case 200: {
                    this.func_175327_a(1.0f);
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                    break;
                }
                case 208: {
                    this.func_175327_a(-1.0f);
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.field_175349_r.func_178062_a(c, n);
                    break;
                }
            }
        }
    }
    
    private void func_175327_a(final float n) {
        final Gui func_178056_g = this.field_175349_r.func_178056_g();
        if (func_178056_g instanceof GuiTextField) {
            float n2 = n;
            if (GuiScreen.isShiftKeyDown()) {
                n2 = n * 0.1f;
                if (GuiScreen.isCtrlKeyDown()) {
                    n2 *= 0.1f;
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
            }
            else if (GuiScreen.isCtrlKeyDown()) {
                n2 = n * 10.0f;
                if (GuiScreen.isAltKeyDown()) {
                    n2 *= 10.0f;
                }
            }
            final GuiTextField guiTextField = (GuiTextField)func_178056_g;
            final Float tryParse = Floats.tryParse(guiTextField.getText());
            if (tryParse != null) {
                final Float value = tryParse + n2;
                final int id = guiTextField.getId();
                final String func_175330_b = this.func_175330_b(guiTextField.getId(), value);
                guiTextField.setText(func_175330_b);
                this.func_175319_a(id, func_175330_b);
            }
        }
    }
    
    static {
        I();
    }
    
    @Override
    public void func_175321_a(final int n, final boolean useMonuments) {
        switch (n) {
            case 148: {
                this.field_175336_F.useCaves = useMonuments;
                "".length();
                if (3 < 1) {
                    throw null;
                }
                break;
            }
            case 149: {
                this.field_175336_F.useDungeons = useMonuments;
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                break;
            }
            case 150: {
                this.field_175336_F.useStrongholds = useMonuments;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 151: {
                this.field_175336_F.useVillages = useMonuments;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                break;
            }
            case 152: {
                this.field_175336_F.useMineShafts = useMonuments;
                "".length();
                if (2 < 1) {
                    throw null;
                }
                break;
            }
            case 153: {
                this.field_175336_F.useTemples = useMonuments;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                break;
            }
            case 154: {
                this.field_175336_F.useRavines = useMonuments;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 155: {
                this.field_175336_F.useWaterLakes = useMonuments;
                "".length();
                if (3 < 1) {
                    throw null;
                }
                break;
            }
            case 156: {
                this.field_175336_F.useLavaLakes = useMonuments;
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 161: {
                this.field_175336_F.useLavaOceans = useMonuments;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
            case 210: {
                this.field_175336_F.useMonuments = useMonuments;
                break;
            }
        }
        if (!this.field_175336_F.equals(this.field_175334_E)) {
            this.func_181031_a(" ".length() != 0);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 300: {
                    this.field_175343_i.chunkProviderSettingsJson = this.field_175336_F.toString();
                    this.mc.displayGuiScreen(this.field_175343_i);
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    break;
                }
                case 301: {
                    int i = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (i < this.field_175349_r.getSize()) {
                        final GuiPageButtonList.GuiEntry listEntry = this.field_175349_r.getListEntry(i);
                        final Gui func_178022_a = listEntry.func_178022_a();
                        if (func_178022_a instanceof GuiButton) {
                            final GuiButton guiButton2 = (GuiButton)func_178022_a;
                            if (guiButton2 instanceof GuiSlider) {
                                ((GuiSlider)guiButton2).func_175219_a(MathHelper.clamp_float(((GuiSlider)guiButton2).func_175217_d() * (0.75f + this.random.nextFloat() * 0.5f) + (this.random.nextFloat() * 0.1f - 0.05f), 0.0f, 1.0f));
                                "".length();
                                if (4 < 2) {
                                    throw null;
                                }
                            }
                            else if (guiButton2 instanceof GuiListButton) {
                                ((GuiListButton)guiButton2).func_175212_b(this.random.nextBoolean());
                            }
                        }
                        final Gui func_178021_b = listEntry.func_178021_b();
                        if (func_178021_b instanceof GuiButton) {
                            final GuiButton guiButton3 = (GuiButton)func_178021_b;
                            if (guiButton3 instanceof GuiSlider) {
                                ((GuiSlider)guiButton3).func_175219_a(MathHelper.clamp_float(((GuiSlider)guiButton3).func_175217_d() * (0.75f + this.random.nextFloat() * 0.5f) + (this.random.nextFloat() * 0.1f - 0.05f), 0.0f, 1.0f));
                                "".length();
                                if (0 >= 2) {
                                    throw null;
                                }
                            }
                            else if (guiButton3 instanceof GuiListButton) {
                                ((GuiListButton)guiButton3).func_175212_b(this.random.nextBoolean());
                            }
                        }
                        ++i;
                    }
                }
                case 302: {
                    this.field_175349_r.func_178071_h();
                    this.func_175328_i();
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                    break;
                }
                case 303: {
                    this.field_175349_r.func_178064_i();
                    this.func_175328_i();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    break;
                }
                case 304: {
                    if (!this.field_175338_A) {
                        break;
                    }
                    this.func_175322_b(195 + 139 - 239 + 209);
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                    break;
                }
                case 305: {
                    this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                    break;
                }
                case 306: {
                    this.func_175331_h();
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 307: {
                    this.field_175339_B = "".length();
                    this.func_175331_h();
                    break;
                }
            }
        }
    }
    
    private void func_175329_a(final boolean b) {
        this.field_175352_x.visible = b;
        this.field_175351_y.visible = b;
        final GuiButton field_175347_t = this.field_175347_t;
        int enabled;
        if (b) {
            enabled = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            enabled = " ".length();
        }
        field_175347_t.enabled = (enabled != 0);
        final GuiButton field_175348_s = this.field_175348_s;
        int enabled2;
        if (b) {
            enabled2 = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            enabled2 = " ".length();
        }
        field_175348_s.enabled = (enabled2 != 0);
        final GuiButton field_175345_v = this.field_175345_v;
        int enabled3;
        if (b) {
            enabled3 = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            enabled3 = " ".length();
        }
        field_175345_v.enabled = (enabled3 != 0);
        final GuiButton field_175344_w = this.field_175344_w;
        int enabled4;
        if (b) {
            enabled4 = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            enabled4 = " ".length();
        }
        field_175344_w.enabled = (enabled4 != 0);
        final GuiButton field_175346_u = this.field_175346_u;
        int enabled5;
        if (this.field_175338_A && !b) {
            enabled5 = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            enabled5 = "".length();
        }
        field_175346_u.enabled = (enabled5 != 0);
        final GuiButton field_175350_z = this.field_175350_z;
        int enabled6;
        if (b) {
            enabled6 = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            enabled6 = " ".length();
        }
        field_175350_z.enabled = (enabled6 != 0);
        final GuiPageButtonList field_175349_r = this.field_175349_r;
        int n;
        if (b) {
            n = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        field_175349_r.func_181155_a(n != 0);
    }
    
    @Override
    public void onTick(final int n, final float n2) {
        Label_1901: {
            switch (n) {
                case 100: {
                    this.field_175336_F.mainNoiseScaleX = n2;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 101: {
                    this.field_175336_F.mainNoiseScaleY = n2;
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 102: {
                    this.field_175336_F.mainNoiseScaleZ = n2;
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 103: {
                    this.field_175336_F.depthNoiseScaleX = n2;
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 104: {
                    this.field_175336_F.depthNoiseScaleZ = n2;
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 105: {
                    this.field_175336_F.depthNoiseScaleExponent = n2;
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 106: {
                    this.field_175336_F.baseSize = n2;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 107: {
                    this.field_175336_F.coordinateScale = n2;
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 108: {
                    this.field_175336_F.heightScale = n2;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 109: {
                    this.field_175336_F.stretchY = n2;
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 110: {
                    this.field_175336_F.upperLimitScale = n2;
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 111: {
                    this.field_175336_F.lowerLimitScale = n2;
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 112: {
                    this.field_175336_F.biomeDepthWeight = n2;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 113: {
                    this.field_175336_F.biomeDepthOffset = n2;
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 114: {
                    this.field_175336_F.biomeScaleWeight = n2;
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 115: {
                    this.field_175336_F.biomeScaleOffset = n2;
                    break;
                }
                case 157: {
                    this.field_175336_F.dungeonChance = (int)n2;
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 158: {
                    this.field_175336_F.waterLakeChance = (int)n2;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 159: {
                    this.field_175336_F.lavaLakeChance = (int)n2;
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 160: {
                    this.field_175336_F.seaLevel = (int)n2;
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 162: {
                    this.field_175336_F.fixedBiome = (int)n2;
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 163: {
                    this.field_175336_F.biomeSize = (int)n2;
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 164: {
                    this.field_175336_F.riverSize = (int)n2;
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 165: {
                    this.field_175336_F.dirtSize = (int)n2;
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 166: {
                    this.field_175336_F.dirtCount = (int)n2;
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 167: {
                    this.field_175336_F.dirtMinHeight = (int)n2;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 168: {
                    this.field_175336_F.dirtMaxHeight = (int)n2;
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 169: {
                    this.field_175336_F.gravelSize = (int)n2;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 170: {
                    this.field_175336_F.gravelCount = (int)n2;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 171: {
                    this.field_175336_F.gravelMinHeight = (int)n2;
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 172: {
                    this.field_175336_F.gravelMaxHeight = (int)n2;
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 173: {
                    this.field_175336_F.graniteSize = (int)n2;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 174: {
                    this.field_175336_F.graniteCount = (int)n2;
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 175: {
                    this.field_175336_F.graniteMinHeight = (int)n2;
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 176: {
                    this.field_175336_F.graniteMaxHeight = (int)n2;
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 177: {
                    this.field_175336_F.dioriteSize = (int)n2;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 178: {
                    this.field_175336_F.dioriteCount = (int)n2;
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 179: {
                    this.field_175336_F.dioriteMinHeight = (int)n2;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 180: {
                    this.field_175336_F.dioriteMaxHeight = (int)n2;
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 181: {
                    this.field_175336_F.andesiteSize = (int)n2;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 182: {
                    this.field_175336_F.andesiteCount = (int)n2;
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 183: {
                    this.field_175336_F.andesiteMinHeight = (int)n2;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 184: {
                    this.field_175336_F.andesiteMaxHeight = (int)n2;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 185: {
                    this.field_175336_F.coalSize = (int)n2;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 186: {
                    this.field_175336_F.coalCount = (int)n2;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 187: {
                    this.field_175336_F.coalMinHeight = (int)n2;
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 189: {
                    this.field_175336_F.coalMaxHeight = (int)n2;
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 190: {
                    this.field_175336_F.ironSize = (int)n2;
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 191: {
                    this.field_175336_F.ironCount = (int)n2;
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 192: {
                    this.field_175336_F.ironMinHeight = (int)n2;
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 193: {
                    this.field_175336_F.ironMaxHeight = (int)n2;
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 194: {
                    this.field_175336_F.goldSize = (int)n2;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 195: {
                    this.field_175336_F.goldCount = (int)n2;
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 196: {
                    this.field_175336_F.goldMinHeight = (int)n2;
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 197: {
                    this.field_175336_F.goldMaxHeight = (int)n2;
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 198: {
                    this.field_175336_F.redstoneSize = (int)n2;
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 199: {
                    this.field_175336_F.redstoneCount = (int)n2;
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 200: {
                    this.field_175336_F.redstoneMinHeight = (int)n2;
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 201: {
                    this.field_175336_F.redstoneMaxHeight = (int)n2;
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 202: {
                    this.field_175336_F.diamondSize = (int)n2;
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 203: {
                    this.field_175336_F.diamondCount = (int)n2;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 204: {
                    this.field_175336_F.diamondMinHeight = (int)n2;
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 205: {
                    this.field_175336_F.diamondMaxHeight = (int)n2;
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 206: {
                    this.field_175336_F.lapisSize = (int)n2;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 207: {
                    this.field_175336_F.lapisCount = (int)n2;
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 208: {
                    this.field_175336_F.lapisCenterHeight = (int)n2;
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    break Label_1901;
                }
                case 209: {
                    this.field_175336_F.lapisSpread = (int)n2;
                    break Label_1901;
                }
            }
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        if (n >= (0x71 ^ 0x15) && n < (0x51 ^ 0x25)) {
            final Gui func_178061_c = this.field_175349_r.func_178061_c(n - (0xEB ^ 0x8F) + (61 + 120 - 180 + 131));
            if (func_178061_c != null) {
                ((GuiTextField)func_178061_c).setText(this.func_175330_b(n, n2));
            }
        }
        if (!this.field_175336_F.equals(this.field_175334_E)) {
            this.func_181031_a(" ".length() != 0);
        }
    }
    
    @Override
    public String getText(final int n, final String s, final float n2) {
        return String.valueOf(s) + GuiCustomizeWorldScreen.I[75 + 123 - 176 + 130] + this.func_175330_b(n, n2);
    }
    
    private void func_175326_g() {
        this.field_175336_F.func_177863_a();
        this.func_175325_f();
        this.func_181031_a("".length() != 0);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_175349_r.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_175341_a, this.width / "  ".length(), "  ".length(), 16137213 + 12478014 - 24023318 + 12185306);
        this.drawCenteredString(this.fontRendererObj, this.field_175333_f, this.width / "  ".length(), 0x27 ^ 0x2B, 640739 + 5709086 + 9586363 + 841027);
        this.drawCenteredString(this.fontRendererObj, this.field_175335_g, this.width / "  ".length(), 0xB9 ^ 0xAF, 2000292 + 3379869 + 10723 + 11386331);
        super.drawScreen(n, n2, n3);
        if (this.field_175339_B != 0) {
            Gui.drawRect("".length(), "".length(), this.width, this.height, -"".length());
            this.drawHorizontalLine(this.width / "  ".length() - (0x6 ^ 0x5D), this.width / "  ".length() + (0x1F ^ 0x45), 0x7 ^ 0x64, -(1401218 + 1588081 - 2444235 + 1494520));
            this.drawHorizontalLine(this.width / "  ".length() - (0x76 ^ 0x2D), this.width / "  ".length() + (0x2C ^ 0x76), 3 + 35 + 134 + 13, -(623470 + 3070999 + 261524 + 2294343));
            this.drawVerticalLine(this.width / "  ".length() - (0x58 ^ 0x3), 0x6F ^ 0xC, 32 + 163 - 51 + 41, -(63537 + 104414 + 199702 + 1671931));
            this.drawVerticalLine(this.width / "  ".length() + (0x38 ^ 0x62), 0x73 ^ 0x10, 156 + 167 - 287 + 149, -(829841 + 6126856 - 3868364 + 3162003));
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(GuiCustomizeWorldScreen.optionsBackground);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            worldRenderer.begin(0x92 ^ 0x95, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.width / "  ".length() - (0xF7 ^ 0xAD), 185.0, 0.0).tex(0.0, 2.65625).color(0x2 ^ 0x42, 0x6E ^ 0x2E, 0xE9 ^ 0xA9, 0xD3 ^ 0x93).endVertex();
            worldRenderer.pos(this.width / "  ".length() + (0x5 ^ 0x5F), 185.0, 0.0).tex(5.625, 2.65625).color(0x1C ^ 0x5C, 0x17 ^ 0x57, 0x3E ^ 0x7E, 0xF2 ^ 0xB2).endVertex();
            worldRenderer.pos(this.width / "  ".length() + (0x37 ^ 0x6D), 100.0, 0.0).tex(5.625, 0.0).color(0x14 ^ 0x54, 0x6B ^ 0x2B, 0x3F ^ 0x7F, 0xF1 ^ 0xB1).endVertex();
            worldRenderer.pos(this.width / "  ".length() - (0xEB ^ 0xB1), 100.0, 0.0).tex(0.0, 0.0).color(0xDA ^ 0x9A, 0xF3 ^ 0xB3, 0x6 ^ 0x46, 0x78 ^ 0x38).endVertex();
            instance.draw();
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiCustomizeWorldScreen.I[152 + 23 - 35 + 20], new Object["".length()]), this.width / "  ".length(), 0x15 ^ 0x7C, 13531935 + 10368418 - 8986056 + 1862918);
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiCustomizeWorldScreen.I[33 + 9 + 55 + 64], new Object["".length()]), this.width / "  ".length(), 0xBE ^ 0xC3, 9622250 + 15991007 - 17288229 + 8452187);
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiCustomizeWorldScreen.I[53 + 97 - 8 + 20], new Object["".length()]), this.width / "  ".length(), 28 + 130 - 74 + 51, 1767271 + 16457828 - 10954876 + 9506992);
            this.field_175352_x.drawButton(this.mc, n, n2);
            this.field_175351_y.drawButton(this.mc, n, n2);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_175349_r.handleMouseInput();
    }
    
    private void func_175325_f() {
        final GuiPageButtonList.GuiListEntry[] array = new GuiPageButtonList.GuiListEntry[0x94 ^ 0x86];
        array["".length()] = new GuiPageButtonList.GuiSlideEntry(45 + 78 - 75 + 112, I18n.format(GuiCustomizeWorldScreen.I[0x38 ^ 0x34], new Object["".length()]), " ".length() != 0, this, 1.0f, 255.0f, this.field_175336_F.seaLevel);
        array[" ".length()] = new GuiPageButtonList.GuiButtonEntry(110 + 85 - 51 + 4, I18n.format(GuiCustomizeWorldScreen.I[0x1E ^ 0x13], new Object["".length()]), " ".length() != 0, this.field_175336_F.useCaves);
        array["  ".length()] = new GuiPageButtonList.GuiButtonEntry(28 + 44 + 13 + 65, I18n.format(GuiCustomizeWorldScreen.I[0x26 ^ 0x28], new Object["".length()]), " ".length() != 0, this.field_175336_F.useStrongholds);
        array["   ".length()] = new GuiPageButtonList.GuiButtonEntry(103 + 88 - 60 + 20, I18n.format(GuiCustomizeWorldScreen.I[0x35 ^ 0x3A], new Object["".length()]), " ".length() != 0, this.field_175336_F.useVillages);
        array[0x36 ^ 0x32] = new GuiPageButtonList.GuiButtonEntry(124 + 53 - 59 + 34, I18n.format(GuiCustomizeWorldScreen.I[0x80 ^ 0x90], new Object["".length()]), " ".length() != 0, this.field_175336_F.useMineShafts);
        array[0x2 ^ 0x7] = new GuiPageButtonList.GuiButtonEntry(151 + 19 - 153 + 136, I18n.format(GuiCustomizeWorldScreen.I[0xAE ^ 0xBF], new Object["".length()]), " ".length() != 0, this.field_175336_F.useTemples);
        array[0x83 ^ 0x85] = new GuiPageButtonList.GuiButtonEntry(186 + 126 - 190 + 88, I18n.format(GuiCustomizeWorldScreen.I[0x40 ^ 0x52], new Object["".length()]), " ".length() != 0, this.field_175336_F.useMonuments);
        array[0x1F ^ 0x18] = new GuiPageButtonList.GuiButtonEntry(151 + 73 - 155 + 85, I18n.format(GuiCustomizeWorldScreen.I[0x2 ^ 0x11], new Object["".length()]), " ".length() != 0, this.field_175336_F.useRavines);
        array[0x83 ^ 0x8B] = new GuiPageButtonList.GuiButtonEntry(129 + 47 - 133 + 106, I18n.format(GuiCustomizeWorldScreen.I[0x39 ^ 0x2D], new Object["".length()]), " ".length() != 0, this.field_175336_F.useDungeons);
        array[0x5C ^ 0x55] = new GuiPageButtonList.GuiSlideEntry(51 + 2 - 5 + 109, I18n.format(GuiCustomizeWorldScreen.I[0xBB ^ 0xAE], new Object["".length()]), " ".length() != 0, this, 1.0f, 100.0f, this.field_175336_F.dungeonChance);
        array[0x31 ^ 0x3B] = new GuiPageButtonList.GuiButtonEntry(87 + 50 - 14 + 32, I18n.format(GuiCustomizeWorldScreen.I[0x96 ^ 0x80], new Object["".length()]), " ".length() != 0, this.field_175336_F.useWaterLakes);
        array[0x20 ^ 0x2B] = new GuiPageButtonList.GuiSlideEntry(50 + 20 + 53 + 35, I18n.format(GuiCustomizeWorldScreen.I[0xB ^ 0x1C], new Object["".length()]), " ".length() != 0, this, 1.0f, 100.0f, this.field_175336_F.waterLakeChance);
        array[0x6 ^ 0xA] = new GuiPageButtonList.GuiButtonEntry(105 + 90 - 73 + 34, I18n.format(GuiCustomizeWorldScreen.I[0x26 ^ 0x3E], new Object["".length()]), " ".length() != 0, this.field_175336_F.useLavaLakes);
        array[0x26 ^ 0x2B] = new GuiPageButtonList.GuiSlideEntry(33 + 59 - 87 + 154, I18n.format(GuiCustomizeWorldScreen.I[0x2A ^ 0x33], new Object["".length()]), " ".length() != 0, this, 10.0f, 100.0f, this.field_175336_F.lavaLakeChance);
        array[0x1E ^ 0x10] = new GuiPageButtonList.GuiButtonEntry(113 + 103 - 181 + 126, I18n.format(GuiCustomizeWorldScreen.I[0x99 ^ 0x83], new Object["".length()]), " ".length() != 0, this.field_175336_F.useLavaOceans);
        array[0x3 ^ 0xC] = new GuiPageButtonList.GuiSlideEntry(97 + 98 - 50 + 17, I18n.format(GuiCustomizeWorldScreen.I[0xB2 ^ 0xA9], new Object["".length()]), " ".length() != 0, this, -1.0f, 37.0f, this.field_175336_F.fixedBiome);
        array[0x10 ^ 0x0] = new GuiPageButtonList.GuiSlideEntry(127 + 26 - 57 + 67, I18n.format(GuiCustomizeWorldScreen.I[0x57 ^ 0x4B], new Object["".length()]), " ".length() != 0, this, 1.0f, 8.0f, this.field_175336_F.biomeSize);
        array[0x8 ^ 0x19] = new GuiPageButtonList.GuiSlideEntry(87 + 78 - 12 + 11, I18n.format(GuiCustomizeWorldScreen.I[0xA4 ^ 0xB9], new Object["".length()]), " ".length() != 0, this, 1.0f, 5.0f, this.field_175336_F.riverSize);
        final GuiPageButtonList.GuiListEntry[] array2 = array;
        final GuiPageButtonList.GuiListEntry[] array3 = new GuiPageButtonList.GuiListEntry[0x30 ^ 0x72];
        array3["".length()] = new GuiPageButtonList.GuiLabelEntry(86 + 40 + 277 + 13, I18n.format(GuiCustomizeWorldScreen.I[0x15 ^ 0xB], new Object["".length()]), "".length() != 0);
        array3["  ".length()] = new GuiPageButtonList.GuiSlideEntry(11 + 138 - 104 + 120, I18n.format(GuiCustomizeWorldScreen.I[0x13 ^ 0xC], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.dirtSize);
        array3["   ".length()] = new GuiPageButtonList.GuiSlideEntry(162 + 16 - 99 + 87, I18n.format(GuiCustomizeWorldScreen.I[0xA2 ^ 0x82], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.dirtCount);
        array3[0x2F ^ 0x2B] = new GuiPageButtonList.GuiSlideEntry(60 + 71 - 55 + 91, I18n.format(GuiCustomizeWorldScreen.I[0xA5 ^ 0x84], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.dirtMinHeight);
        array3[0x1F ^ 0x1A] = new GuiPageButtonList.GuiSlideEntry(58 + 149 - 178 + 139, I18n.format(GuiCustomizeWorldScreen.I[0x33 ^ 0x11], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.dirtMaxHeight);
        array3[0x8E ^ 0x88] = new GuiPageButtonList.GuiLabelEntry(128 + 257 - 248 + 280, I18n.format(GuiCustomizeWorldScreen.I[0xA5 ^ 0x86], new Object["".length()]), "".length() != 0);
        array3[0xB5 ^ 0xBD] = new GuiPageButtonList.GuiSlideEntry(16 + 18 + 52 + 83, I18n.format(GuiCustomizeWorldScreen.I[0x81 ^ 0xA5], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.gravelSize);
        array3[0x23 ^ 0x2A] = new GuiPageButtonList.GuiSlideEntry(11 + 136 - 52 + 75, I18n.format(GuiCustomizeWorldScreen.I[0xA6 ^ 0x83], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.gravelCount);
        array3[0x1 ^ 0xB] = new GuiPageButtonList.GuiSlideEntry(131 + 67 - 57 + 30, I18n.format(GuiCustomizeWorldScreen.I[0x1 ^ 0x27], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.gravelMinHeight);
        array3[0x49 ^ 0x42] = new GuiPageButtonList.GuiSlideEntry(154 + 28 - 74 + 64, I18n.format(GuiCustomizeWorldScreen.I[0xAA ^ 0x8D], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.gravelMaxHeight);
        array3[0xCD ^ 0xC1] = new GuiPageButtonList.GuiLabelEntry(356 + 287 - 228 + 3, I18n.format(GuiCustomizeWorldScreen.I[0x3C ^ 0x14], new Object["".length()]), "".length() != 0);
        array3[0x7 ^ 0x9] = new GuiPageButtonList.GuiSlideEntry(14 + 95 - 1 + 65, I18n.format(GuiCustomizeWorldScreen.I[0x6F ^ 0x46], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.graniteSize);
        array3[0x21 ^ 0x2E] = new GuiPageButtonList.GuiSlideEntry(16 + 95 - 90 + 153, I18n.format(GuiCustomizeWorldScreen.I[0xB ^ 0x21], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.graniteCount);
        array3[0x8 ^ 0x18] = new GuiPageButtonList.GuiSlideEntry(66 + 3 - 63 + 169, I18n.format(GuiCustomizeWorldScreen.I[0x9A ^ 0xB1], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.graniteMinHeight);
        array3[0x8A ^ 0x9B] = new GuiPageButtonList.GuiSlideEntry(95 + 145 - 215 + 151, I18n.format(GuiCustomizeWorldScreen.I[0xBE ^ 0x92], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.graniteMaxHeight);
        array3[0x48 ^ 0x5A] = new GuiPageButtonList.GuiLabelEntry(125 + 20 + 44 + 230, I18n.format(GuiCustomizeWorldScreen.I[0x85 ^ 0xA8], new Object["".length()]), "".length() != 0);
        array3[0x88 ^ 0x9C] = new GuiPageButtonList.GuiSlideEntry(31 + 9 - 25 + 162, I18n.format(GuiCustomizeWorldScreen.I[0xA3 ^ 0x8D], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.dioriteSize);
        array3[0x60 ^ 0x75] = new GuiPageButtonList.GuiSlideEntry(31 + 57 + 14 + 76, I18n.format(GuiCustomizeWorldScreen.I[0x84 ^ 0xAB], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.dioriteCount);
        array3[0x39 ^ 0x2F] = new GuiPageButtonList.GuiSlideEntry(34 + 53 - 12 + 104, I18n.format(GuiCustomizeWorldScreen.I[0x59 ^ 0x69], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.dioriteMinHeight);
        array3[0x3D ^ 0x2A] = new GuiPageButtonList.GuiSlideEntry(109 + 138 - 218 + 151, I18n.format(GuiCustomizeWorldScreen.I[0xA7 ^ 0x96], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.dioriteMaxHeight);
        array3[0x26 ^ 0x3E] = new GuiPageButtonList.GuiLabelEntry(162 + 366 - 392 + 284, I18n.format(GuiCustomizeWorldScreen.I[0x4 ^ 0x36], new Object["".length()]), "".length() != 0);
        array3[0x1C ^ 0x6] = new GuiPageButtonList.GuiSlideEntry(11 + 26 + 67 + 77, I18n.format(GuiCustomizeWorldScreen.I[0xAF ^ 0x9C], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.andesiteSize);
        array3[0x91 ^ 0x8A] = new GuiPageButtonList.GuiSlideEntry(59 + 118 - 2 + 7, I18n.format(GuiCustomizeWorldScreen.I[0x1C ^ 0x28], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.andesiteCount);
        array3[0x4B ^ 0x57] = new GuiPageButtonList.GuiSlideEntry(66 + 88 + 10 + 19, I18n.format(GuiCustomizeWorldScreen.I[0x2C ^ 0x19], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.andesiteMinHeight);
        array3[0x54 ^ 0x49] = new GuiPageButtonList.GuiSlideEntry(38 + 156 - 172 + 162, I18n.format(GuiCustomizeWorldScreen.I[0x2C ^ 0x1A], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.andesiteMaxHeight);
        array3[0x9F ^ 0x81] = new GuiPageButtonList.GuiLabelEntry(280 + 352 - 515 + 304, I18n.format(GuiCustomizeWorldScreen.I[0xBC ^ 0x8B], new Object["".length()]), "".length() != 0);
        array3[0x4E ^ 0x6E] = new GuiPageButtonList.GuiSlideEntry(165 + 167 - 208 + 61, I18n.format(GuiCustomizeWorldScreen.I[0xA6 ^ 0x9E], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.coalSize);
        array3[0xE6 ^ 0xC7] = new GuiPageButtonList.GuiSlideEntry(92 + 111 - 171 + 154, I18n.format(GuiCustomizeWorldScreen.I[0x6F ^ 0x56], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.coalCount);
        array3[0x4D ^ 0x6F] = new GuiPageButtonList.GuiSlideEntry(86 + 99 - 14 + 16, I18n.format(GuiCustomizeWorldScreen.I[0x4B ^ 0x71], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.coalMinHeight);
        array3[0x8 ^ 0x2B] = new GuiPageButtonList.GuiSlideEntry(178 + 126 - 118 + 3, I18n.format(GuiCustomizeWorldScreen.I[0xD ^ 0x36], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.coalMaxHeight);
        array3[0x8F ^ 0xAB] = new GuiPageButtonList.GuiLabelEntry(353 + 24 - 366 + 411, I18n.format(GuiCustomizeWorldScreen.I[0x73 ^ 0x4F], new Object["".length()]), "".length() != 0);
        array3[0xBA ^ 0x9C] = new GuiPageButtonList.GuiSlideEntry(15 + 103 - 56 + 128, I18n.format(GuiCustomizeWorldScreen.I[0x96 ^ 0xAB], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.ironSize);
        array3[0x97 ^ 0xB0] = new GuiPageButtonList.GuiSlideEntry(76 + 96 - 118 + 137, I18n.format(GuiCustomizeWorldScreen.I[0x46 ^ 0x78], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.ironCount);
        array3[0x24 ^ 0xC] = new GuiPageButtonList.GuiSlideEntry(70 + 85 - 92 + 129, I18n.format(GuiCustomizeWorldScreen.I[0x3A ^ 0x5], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.ironMinHeight);
        array3[0x78 ^ 0x51] = new GuiPageButtonList.GuiSlideEntry(62 + 42 + 15 + 74, I18n.format(GuiCustomizeWorldScreen.I[0x14 ^ 0x54], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.ironMaxHeight);
        array3[0x90 ^ 0xBA] = new GuiPageButtonList.GuiLabelEntry(256 + 379 - 472 + 260, I18n.format(GuiCustomizeWorldScreen.I[0xC9 ^ 0x88], new Object["".length()]), "".length() != 0);
        array3[0xA2 ^ 0x8E] = new GuiPageButtonList.GuiSlideEntry(11 + 155 + 2 + 26, I18n.format(GuiCustomizeWorldScreen.I[0x3F ^ 0x7D], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.goldSize);
        array3[0x67 ^ 0x4A] = new GuiPageButtonList.GuiSlideEntry(96 + 163 - 132 + 68, I18n.format(GuiCustomizeWorldScreen.I[0x56 ^ 0x15], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.goldCount);
        array3[0x85 ^ 0xAB] = new GuiPageButtonList.GuiSlideEntry(179 + 27 - 46 + 36, I18n.format(GuiCustomizeWorldScreen.I[0x4 ^ 0x40], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.goldMinHeight);
        array3[0x5 ^ 0x2A] = new GuiPageButtonList.GuiSlideEntry(19 + 157 - 129 + 150, I18n.format(GuiCustomizeWorldScreen.I[0xFD ^ 0xB8], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.goldMaxHeight);
        array3[0xBE ^ 0x8E] = new GuiPageButtonList.GuiLabelEntry(12 + 283 - 210 + 339, I18n.format(GuiCustomizeWorldScreen.I[0x2D ^ 0x6B], new Object["".length()]), "".length() != 0);
        array3[0x4 ^ 0x36] = new GuiPageButtonList.GuiSlideEntry(159 + 160 - 169 + 48, I18n.format(GuiCustomizeWorldScreen.I[0x50 ^ 0x17], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.redstoneSize);
        array3[0x45 ^ 0x76] = new GuiPageButtonList.GuiSlideEntry(145 + 0 - 93 + 147, I18n.format(GuiCustomizeWorldScreen.I[0x25 ^ 0x6D], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.redstoneCount);
        array3[0xB2 ^ 0x86] = new GuiPageButtonList.GuiSlideEntry(69 + 59 + 12 + 60, I18n.format(GuiCustomizeWorldScreen.I[0xE2 ^ 0xAB], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.redstoneMinHeight);
        array3[0x6 ^ 0x33] = new GuiPageButtonList.GuiSlideEntry(46 + 32 + 105 + 18, I18n.format(GuiCustomizeWorldScreen.I[0x4C ^ 0x6], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.redstoneMaxHeight);
        array3[0xB8 ^ 0x8E] = new GuiPageButtonList.GuiLabelEntry(79 + 346 - 79 + 79, I18n.format(GuiCustomizeWorldScreen.I[0xE6 ^ 0xAD], new Object["".length()]), "".length() != 0);
        array3[0x98 ^ 0xA0] = new GuiPageButtonList.GuiSlideEntry(151 + 71 - 178 + 158, I18n.format(GuiCustomizeWorldScreen.I[0xC1 ^ 0x8D], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.diamondSize);
        array3[0x31 ^ 0x8] = new GuiPageButtonList.GuiSlideEntry(85 + 47 - 15 + 86, I18n.format(GuiCustomizeWorldScreen.I[0x3D ^ 0x70], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.diamondCount);
        array3[0x47 ^ 0x7D] = new GuiPageButtonList.GuiSlideEntry(167 + 123 - 278 + 192, I18n.format(GuiCustomizeWorldScreen.I[0xFF ^ 0xB1], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.diamondMinHeight);
        array3[0x23 ^ 0x18] = new GuiPageButtonList.GuiSlideEntry(2 + 79 + 53 + 71, I18n.format(GuiCustomizeWorldScreen.I[0xC1 ^ 0x8E], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.diamondMaxHeight);
        array3[0x0 ^ 0x3C] = new GuiPageButtonList.GuiLabelEntry(183 + 91 - 104 + 256, I18n.format(GuiCustomizeWorldScreen.I[0x35 ^ 0x65], new Object["".length()]), "".length() != 0);
        array3[0x2C ^ 0x12] = new GuiPageButtonList.GuiSlideEntry(153 + 30 - 11 + 34, I18n.format(GuiCustomizeWorldScreen.I[0x5A ^ 0xB], new Object["".length()]), "".length() != 0, this, 1.0f, 50.0f, this.field_175336_F.lapisSize);
        array3[0x20 ^ 0x1F] = new GuiPageButtonList.GuiSlideEntry(159 + 103 - 55 + 0, I18n.format(GuiCustomizeWorldScreen.I[0x29 ^ 0x7B], new Object["".length()]), "".length() != 0, this, 0.0f, 40.0f, this.field_175336_F.lapisCount);
        array3[0x50 ^ 0x10] = new GuiPageButtonList.GuiSlideEntry(21 + 122 + 40 + 25, I18n.format(GuiCustomizeWorldScreen.I[0x4C ^ 0x1F], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.lapisCenterHeight);
        array3[0xEA ^ 0xAB] = new GuiPageButtonList.GuiSlideEntry(28 + 67 - 28 + 142, I18n.format(GuiCustomizeWorldScreen.I[0x61 ^ 0x35], new Object["".length()]), "".length() != 0, this, 0.0f, 255.0f, this.field_175336_F.lapisSpread);
        final GuiPageButtonList.GuiListEntry[] array4 = array3;
        final GuiPageButtonList.GuiListEntry[] array5 = new GuiPageButtonList.GuiListEntry[0x4 ^ 0x14];
        array5["".length()] = new GuiPageButtonList.GuiSlideEntry(0x46 ^ 0x22, I18n.format(GuiCustomizeWorldScreen.I[0x7B ^ 0x2E], new Object["".length()]), "".length() != 0, this, 1.0f, 5000.0f, this.field_175336_F.mainNoiseScaleX);
        array5[" ".length()] = new GuiPageButtonList.GuiSlideEntry(0xA4 ^ 0xC1, I18n.format(GuiCustomizeWorldScreen.I[0x5F ^ 0x9], new Object["".length()]), "".length() != 0, this, 1.0f, 5000.0f, this.field_175336_F.mainNoiseScaleY);
        array5["  ".length()] = new GuiPageButtonList.GuiSlideEntry(0xC1 ^ 0xA7, I18n.format(GuiCustomizeWorldScreen.I[0x58 ^ 0xF], new Object["".length()]), "".length() != 0, this, 1.0f, 5000.0f, this.field_175336_F.mainNoiseScaleZ);
        array5["   ".length()] = new GuiPageButtonList.GuiSlideEntry(0x1B ^ 0x7C, I18n.format(GuiCustomizeWorldScreen.I[0x43 ^ 0x1B], new Object["".length()]), "".length() != 0, this, 1.0f, 2000.0f, this.field_175336_F.depthNoiseScaleX);
        array5[0x65 ^ 0x61] = new GuiPageButtonList.GuiSlideEntry(0x6 ^ 0x6E, I18n.format(GuiCustomizeWorldScreen.I[0x19 ^ 0x40], new Object["".length()]), "".length() != 0, this, 1.0f, 2000.0f, this.field_175336_F.depthNoiseScaleZ);
        array5[0xA5 ^ 0xA0] = new GuiPageButtonList.GuiSlideEntry(0x4E ^ 0x27, I18n.format(GuiCustomizeWorldScreen.I[0xFF ^ 0xA5], new Object["".length()]), "".length() != 0, this, 0.01f, 20.0f, this.field_175336_F.depthNoiseScaleExponent);
        array5[0xB ^ 0xD] = new GuiPageButtonList.GuiSlideEntry(0xFE ^ 0x94, I18n.format(GuiCustomizeWorldScreen.I[0xE8 ^ 0xB3], new Object["".length()]), "".length() != 0, this, 1.0f, 25.0f, this.field_175336_F.baseSize);
        array5[0x22 ^ 0x25] = new GuiPageButtonList.GuiSlideEntry(0xDF ^ 0xB4, I18n.format(GuiCustomizeWorldScreen.I[0x69 ^ 0x35], new Object["".length()]), "".length() != 0, this, 1.0f, 6000.0f, this.field_175336_F.coordinateScale);
        array5[0x4C ^ 0x44] = new GuiPageButtonList.GuiSlideEntry(0x0 ^ 0x6C, I18n.format(GuiCustomizeWorldScreen.I[0x63 ^ 0x3E], new Object["".length()]), "".length() != 0, this, 1.0f, 6000.0f, this.field_175336_F.heightScale);
        array5[0x5 ^ 0xC] = new GuiPageButtonList.GuiSlideEntry(0x65 ^ 0x8, I18n.format(GuiCustomizeWorldScreen.I[0xC7 ^ 0x99], new Object["".length()]), "".length() != 0, this, 0.01f, 50.0f, this.field_175336_F.stretchY);
        array5[0xA8 ^ 0xA2] = new GuiPageButtonList.GuiSlideEntry(0x75 ^ 0x1B, I18n.format(GuiCustomizeWorldScreen.I[0x19 ^ 0x46], new Object["".length()]), "".length() != 0, this, 1.0f, 5000.0f, this.field_175336_F.upperLimitScale);
        array5[0x25 ^ 0x2E] = new GuiPageButtonList.GuiSlideEntry(0xF9 ^ 0x96, I18n.format(GuiCustomizeWorldScreen.I[0x5 ^ 0x65], new Object["".length()]), "".length() != 0, this, 1.0f, 5000.0f, this.field_175336_F.lowerLimitScale);
        array5[0x45 ^ 0x49] = new GuiPageButtonList.GuiSlideEntry(0xCB ^ 0xBB, I18n.format(GuiCustomizeWorldScreen.I[0xCC ^ 0xAD], new Object["".length()]), "".length() != 0, this, 1.0f, 20.0f, this.field_175336_F.biomeDepthWeight);
        array5[0x2B ^ 0x26] = new GuiPageButtonList.GuiSlideEntry(0x30 ^ 0x41, I18n.format(GuiCustomizeWorldScreen.I[0x51 ^ 0x33], new Object["".length()]), "".length() != 0, this, 0.0f, 20.0f, this.field_175336_F.biomeDepthOffset);
        array5[0x79 ^ 0x77] = new GuiPageButtonList.GuiSlideEntry(0x5B ^ 0x29, I18n.format(GuiCustomizeWorldScreen.I[0x1F ^ 0x7C], new Object["".length()]), "".length() != 0, this, 1.0f, 20.0f, this.field_175336_F.biomeScaleWeight);
        array5[0x32 ^ 0x3D] = new GuiPageButtonList.GuiSlideEntry(0x75 ^ 0x6, I18n.format(GuiCustomizeWorldScreen.I[0x4 ^ 0x60], new Object["".length()]), "".length() != 0, this, 0.0f, 20.0f, this.field_175336_F.biomeScaleOffset);
        final GuiPageButtonList.GuiListEntry[] array6 = array5;
        final GuiPageButtonList.GuiListEntry[] array7 = new GuiPageButtonList.GuiListEntry[0xA ^ 0x2A];
        array7["".length()] = new GuiPageButtonList.GuiLabelEntry(338 + 76 - 97 + 83, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x43 ^ 0x26], new Object["".length()])) + GuiCustomizeWorldScreen.I[0x7B ^ 0x1D], "".length() != 0);
        final int length = " ".length();
        final int n = 102 + 24 - 22 + 28;
        final String s = GuiCustomizeWorldScreen.I[0xE3 ^ 0x84];
        final Object[] array8 = new Object[" ".length()];
        array8["".length()] = this.field_175336_F.mainNoiseScaleX;
        array7[length] = new GuiPageButtonList.EditBoxEntry(n, String.format(s, array8), "".length() != 0, this.field_175332_D);
        array7["  ".length()] = new GuiPageButtonList.GuiLabelEntry(285 + 345 - 516 + 287, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x5A ^ 0x32], new Object["".length()])) + GuiCustomizeWorldScreen.I[0xD1 ^ 0xB8], "".length() != 0);
        final int length2 = "   ".length();
        final int n2 = 9 + 102 + 15 + 7;
        final String s2 = GuiCustomizeWorldScreen.I[0x7 ^ 0x6D];
        final Object[] array9 = new Object[" ".length()];
        array9["".length()] = this.field_175336_F.mainNoiseScaleY;
        array7[length2] = new GuiPageButtonList.EditBoxEntry(n2, String.format(s2, array9), "".length() != 0, this.field_175332_D);
        array7[0x34 ^ 0x30] = new GuiPageButtonList.GuiLabelEntry(293 + 31 + 8 + 70, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x6F ^ 0x4], new Object["".length()])) + GuiCustomizeWorldScreen.I[0x15 ^ 0x79], "".length() != 0);
        final int n3 = 0x20 ^ 0x25;
        final int n4 = 113 + 93 - 137 + 65;
        final String s3 = GuiCustomizeWorldScreen.I[0xD0 ^ 0xBD];
        final Object[] array10 = new Object[" ".length()];
        array10["".length()] = this.field_175336_F.mainNoiseScaleZ;
        array7[n3] = new GuiPageButtonList.EditBoxEntry(n4, String.format(s3, array10), "".length() != 0, this.field_175332_D);
        array7[0x72 ^ 0x74] = new GuiPageButtonList.GuiLabelEntry(97 + 231 - 263 + 338, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0xE2 ^ 0x8C], new Object["".length()])) + GuiCustomizeWorldScreen.I[0x79 ^ 0x16], "".length() != 0);
        final int n5 = 0x5B ^ 0x5C;
        final int n6 = 34 + 68 - 73 + 106;
        final String s4 = GuiCustomizeWorldScreen.I[0x74 ^ 0x4];
        final Object[] array11 = new Object[" ".length()];
        array11["".length()] = this.field_175336_F.depthNoiseScaleX;
        array7[n5] = new GuiPageButtonList.EditBoxEntry(n6, String.format(s4, array11), "".length() != 0, this.field_175332_D);
        array7[0x9C ^ 0x94] = new GuiPageButtonList.GuiLabelEntry(235 + 89 - 263 + 343, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x24 ^ 0x55], new Object["".length()])) + GuiCustomizeWorldScreen.I[0x15 ^ 0x67], "".length() != 0);
        final int n7 = 0x11 ^ 0x18;
        final int n8 = 26 + 26 - 9 + 93;
        final String s5 = GuiCustomizeWorldScreen.I[0x4B ^ 0x38];
        final Object[] array12 = new Object[" ".length()];
        array12["".length()] = this.field_175336_F.depthNoiseScaleZ;
        array7[n7] = new GuiPageButtonList.EditBoxEntry(n8, String.format(s5, array12), "".length() != 0, this.field_175332_D);
        array7[0xBD ^ 0xB7] = new GuiPageButtonList.GuiLabelEntry(341 + 377 - 679 + 366, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x1E ^ 0x6A], new Object["".length()])) + GuiCustomizeWorldScreen.I[0x24 ^ 0x51], "".length() != 0);
        final int n9 = 0x7D ^ 0x76;
        final int n10 = 63 + 18 - 76 + 132;
        final String s6 = GuiCustomizeWorldScreen.I[0x5B ^ 0x2D];
        final Object[] array13 = new Object[" ".length()];
        array13["".length()] = this.field_175336_F.depthNoiseScaleExponent;
        array7[n9] = new GuiPageButtonList.EditBoxEntry(n10, String.format(s6, array13), "".length() != 0, this.field_175332_D);
        array7[0x2D ^ 0x21] = new GuiPageButtonList.GuiLabelEntry(76 + 326 - 251 + 255, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0xF7 ^ 0x80], new Object["".length()])) + GuiCustomizeWorldScreen.I[0xDA ^ 0xA2], "".length() != 0);
        final int n11 = 0x76 ^ 0x7B;
        final int n12 = 13 + 121 - 64 + 68;
        final String s7 = GuiCustomizeWorldScreen.I[0x0 ^ 0x79];
        final Object[] array14 = new Object[" ".length()];
        array14["".length()] = this.field_175336_F.baseSize;
        array7[n11] = new GuiPageButtonList.EditBoxEntry(n12, String.format(s7, array14), "".length() != 0, this.field_175332_D);
        array7[0x58 ^ 0x56] = new GuiPageButtonList.GuiLabelEntry(330 + 340 - 510 + 247, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x1E ^ 0x64], new Object["".length()])) + GuiCustomizeWorldScreen.I[0xE9 ^ 0x92], "".length() != 0);
        final int n13 = 0x7A ^ 0x75;
        final int n14 = 75 + 101 - 109 + 72;
        final String s8 = GuiCustomizeWorldScreen.I[0x69 ^ 0x15];
        final Object[] array15 = new Object[" ".length()];
        array15["".length()] = this.field_175336_F.coordinateScale;
        array7[n13] = new GuiPageButtonList.EditBoxEntry(n14, String.format(s8, array15), "".length() != 0, this.field_175332_D);
        array7[0x47 ^ 0x57] = new GuiPageButtonList.GuiLabelEntry(240 + 382 - 295 + 81, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[0x56 ^ 0x2B], new Object["".length()])) + GuiCustomizeWorldScreen.I[0x44 ^ 0x3A], "".length() != 0);
        final int n15 = 0x21 ^ 0x30;
        final int n16 = 122 + 6 + 2 + 10;
        final String s9 = GuiCustomizeWorldScreen.I[52 + 34 - 4 + 45];
        final Object[] array16 = new Object[" ".length()];
        array16["".length()] = this.field_175336_F.heightScale;
        array7[n15] = new GuiPageButtonList.EditBoxEntry(n16, String.format(s9, array16), "".length() != 0, this.field_175332_D);
        array7[0x10 ^ 0x2] = new GuiPageButtonList.GuiLabelEntry(30 + 233 - 12 + 158, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[1 + 43 + 27 + 57], new Object["".length()])) + GuiCustomizeWorldScreen.I[44 + 115 - 142 + 112], "".length() != 0);
        final int n17 = 0x4C ^ 0x5F;
        final int n18 = 50 + 29 + 55 + 7;
        final String s10 = GuiCustomizeWorldScreen.I[92 + 121 - 180 + 97];
        final Object[] array17 = new Object[" ".length()];
        array17["".length()] = this.field_175336_F.stretchY;
        array7[n17] = new GuiPageButtonList.EditBoxEntry(n18, String.format(s10, array17), "".length() != 0, this.field_175332_D);
        array7[0xC ^ 0x18] = new GuiPageButtonList.GuiLabelEntry(337 + 135 - 292 + 230, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[33 + 89 - 17 + 26], new Object["".length()])) + GuiCustomizeWorldScreen.I[120 + 8 - 126 + 130], "".length() != 0);
        final int n19 = 0x45 ^ 0x50;
        final int n20 = 36 + 35 - 44 + 115;
        final String s11 = GuiCustomizeWorldScreen.I[18 + 103 - 82 + 94];
        final Object[] array18 = new Object[" ".length()];
        array18["".length()] = this.field_175336_F.upperLimitScale;
        array7[n19] = new GuiPageButtonList.EditBoxEntry(n20, String.format(s11, array18), "".length() != 0, this.field_175332_D);
        array7[0x5D ^ 0x4B] = new GuiPageButtonList.GuiLabelEntry(239 + 307 - 229 + 94, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[26 + 56 + 37 + 15], new Object["".length()])) + GuiCustomizeWorldScreen.I[83 + 16 - 45 + 81], "".length() != 0);
        final int n21 = 0x8A ^ 0x9D;
        final int n22 = 92 + 75 - 46 + 22;
        final String s12 = GuiCustomizeWorldScreen.I[68 + 97 - 140 + 111];
        final Object[] array19 = new Object[" ".length()];
        array19["".length()] = this.field_175336_F.lowerLimitScale;
        array7[n21] = new GuiPageButtonList.EditBoxEntry(n22, String.format(s12, array19), "".length() != 0, this.field_175332_D);
        array7[0x2A ^ 0x32] = new GuiPageButtonList.GuiLabelEntry(79 + 216 - 228 + 345, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[111 + 54 - 161 + 133], new Object["".length()])) + GuiCustomizeWorldScreen.I[2 + 60 - 22 + 98], "".length() != 0);
        final int n23 = 0x5E ^ 0x47;
        final int n24 = 110 + 6 - 45 + 73;
        final String s13 = GuiCustomizeWorldScreen.I[76 + 132 - 203 + 134];
        final Object[] array20 = new Object[" ".length()];
        array20["".length()] = this.field_175336_F.biomeDepthWeight;
        array7[n23] = new GuiPageButtonList.EditBoxEntry(n24, String.format(s13, array20), "".length() != 0, this.field_175332_D);
        array7[0xBE ^ 0xA4] = new GuiPageButtonList.GuiLabelEntry(106 + 188 + 48 + 71, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[88 + 109 - 121 + 64], new Object["".length()])) + GuiCustomizeWorldScreen.I[23 + 32 + 47 + 39], "".length() != 0);
        final int n25 = 0x4F ^ 0x54;
        final int n26 = 127 + 38 - 22 + 2;
        final String s14 = GuiCustomizeWorldScreen.I[9 + 25 + 80 + 28];
        final Object[] array21 = new Object[" ".length()];
        array21["".length()] = this.field_175336_F.biomeDepthOffset;
        array7[n25] = new GuiPageButtonList.EditBoxEntry(n26, String.format(s14, array21), "".length() != 0, this.field_175332_D);
        array7[0x25 ^ 0x39] = new GuiPageButtonList.GuiLabelEntry(397 + 80 - 278 + 215, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[83 + 117 - 114 + 57], new Object["".length()])) + GuiCustomizeWorldScreen.I[6 + 103 - 34 + 69], "".length() != 0);
        final int n27 = 0xB4 ^ 0xA9;
        final int n28 = 60 + 23 + 2 + 61;
        final String s15 = GuiCustomizeWorldScreen.I[46 + 135 - 111 + 75];
        final Object[] array22 = new Object[" ".length()];
        array22["".length()] = this.field_175336_F.biomeScaleWeight;
        array7[n27] = new GuiPageButtonList.EditBoxEntry(n28, String.format(s15, array22), "".length() != 0, this.field_175332_D);
        array7[0x6 ^ 0x18] = new GuiPageButtonList.GuiLabelEntry(63 + 177 + 113 + 62, String.valueOf(I18n.format(GuiCustomizeWorldScreen.I[27 + 0 + 74 + 45], new Object["".length()])) + GuiCustomizeWorldScreen.I[34 + 73 - 102 + 142], "".length() != 0);
        final int n29 = 0x63 ^ 0x7C;
        final int n30 = 19 + 52 + 38 + 38;
        final String s16 = GuiCustomizeWorldScreen.I[88 + 145 - 156 + 71];
        final Object[] array23 = new Object[" ".length()];
        array23["".length()] = this.field_175336_F.biomeScaleOffset;
        array7[n29] = new GuiPageButtonList.EditBoxEntry(n30, String.format(s16, array23), "".length() != 0, this.field_175332_D);
        final GuiPageButtonList.GuiListEntry[] array24 = array7;
        final Minecraft mc = this.mc;
        final int width = this.width;
        final int height = this.height;
        final int n31 = 0xA8 ^ 0x88;
        final int n32 = this.height - (0x4F ^ 0x6F);
        final int n33 = 0x6D ^ 0x74;
        final GuiPageButtonList.GuiListEntry[][] array25 = new GuiPageButtonList.GuiListEntry[0x1B ^ 0x1F][];
        array25["".length()] = array2;
        array25[" ".length()] = array4;
        array25["  ".length()] = array6;
        array25["   ".length()] = array24;
        this.field_175349_r = new GuiPageButtonList(mc, width, height, n31, n32, n33, this, array25);
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < (0x48 ^ 0x4C)) {
            this.field_175342_h[i] = I18n.format(GuiCustomizeWorldScreen.I[31 + 72 - 80 + 126] + i, new Object["".length()]);
            ++i;
        }
        this.func_175328_i();
    }
    
    @Override
    public void func_175319_a(final int n, final String s) {
        float float1 = 0.0f;
        try {
            float1 = Float.parseFloat(s);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        catch (NumberFormatException ex) {}
        float n2 = 0.0f;
        switch (n) {
            case 132: {
                final ChunkProviderSettings.Factory field_175336_F = this.field_175336_F;
                final float clamp_float = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F.mainNoiseScaleX = clamp_float;
                n2 = clamp_float;
                "".length();
                if (4 < 0) {
                    throw null;
                }
                break;
            }
            case 133: {
                final ChunkProviderSettings.Factory field_175336_F2 = this.field_175336_F;
                final float clamp_float2 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F2.mainNoiseScaleY = clamp_float2;
                n2 = clamp_float2;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 134: {
                final ChunkProviderSettings.Factory field_175336_F3 = this.field_175336_F;
                final float clamp_float3 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F3.mainNoiseScaleZ = clamp_float3;
                n2 = clamp_float3;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 135: {
                final ChunkProviderSettings.Factory field_175336_F4 = this.field_175336_F;
                final float clamp_float4 = MathHelper.clamp_float(float1, 1.0f, 2000.0f);
                field_175336_F4.depthNoiseScaleX = clamp_float4;
                n2 = clamp_float4;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                break;
            }
            case 136: {
                final ChunkProviderSettings.Factory field_175336_F5 = this.field_175336_F;
                final float clamp_float5 = MathHelper.clamp_float(float1, 1.0f, 2000.0f);
                field_175336_F5.depthNoiseScaleZ = clamp_float5;
                n2 = clamp_float5;
                "".length();
                if (1 == 4) {
                    throw null;
                }
                break;
            }
            case 137: {
                final ChunkProviderSettings.Factory field_175336_F6 = this.field_175336_F;
                final float clamp_float6 = MathHelper.clamp_float(float1, 0.01f, 20.0f);
                field_175336_F6.depthNoiseScaleExponent = clamp_float6;
                n2 = clamp_float6;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 138: {
                final ChunkProviderSettings.Factory field_175336_F7 = this.field_175336_F;
                final float clamp_float7 = MathHelper.clamp_float(float1, 1.0f, 25.0f);
                field_175336_F7.baseSize = clamp_float7;
                n2 = clamp_float7;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 139: {
                final ChunkProviderSettings.Factory field_175336_F8 = this.field_175336_F;
                final float clamp_float8 = MathHelper.clamp_float(float1, 1.0f, 6000.0f);
                field_175336_F8.coordinateScale = clamp_float8;
                n2 = clamp_float8;
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                break;
            }
            case 140: {
                final ChunkProviderSettings.Factory field_175336_F9 = this.field_175336_F;
                final float clamp_float9 = MathHelper.clamp_float(float1, 1.0f, 6000.0f);
                field_175336_F9.heightScale = clamp_float9;
                n2 = clamp_float9;
                "".length();
                if (2 < -1) {
                    throw null;
                }
                break;
            }
            case 141: {
                final ChunkProviderSettings.Factory field_175336_F10 = this.field_175336_F;
                final float clamp_float10 = MathHelper.clamp_float(float1, 0.01f, 50.0f);
                field_175336_F10.stretchY = clamp_float10;
                n2 = clamp_float10;
                "".length();
                if (4 < 1) {
                    throw null;
                }
                break;
            }
            case 142: {
                final ChunkProviderSettings.Factory field_175336_F11 = this.field_175336_F;
                final float clamp_float11 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F11.upperLimitScale = clamp_float11;
                n2 = clamp_float11;
                "".length();
                if (0 < -1) {
                    throw null;
                }
                break;
            }
            case 143: {
                final ChunkProviderSettings.Factory field_175336_F12 = this.field_175336_F;
                final float clamp_float12 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F12.lowerLimitScale = clamp_float12;
                n2 = clamp_float12;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 144: {
                final ChunkProviderSettings.Factory field_175336_F13 = this.field_175336_F;
                final float clamp_float13 = MathHelper.clamp_float(float1, 1.0f, 20.0f);
                field_175336_F13.biomeDepthWeight = clamp_float13;
                n2 = clamp_float13;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                break;
            }
            case 145: {
                final ChunkProviderSettings.Factory field_175336_F14 = this.field_175336_F;
                final float clamp_float14 = MathHelper.clamp_float(float1, 0.0f, 20.0f);
                field_175336_F14.biomeDepthOffset = clamp_float14;
                n2 = clamp_float14;
                "".length();
                if (4 == 1) {
                    throw null;
                }
                break;
            }
            case 146: {
                final ChunkProviderSettings.Factory field_175336_F15 = this.field_175336_F;
                final float clamp_float15 = MathHelper.clamp_float(float1, 1.0f, 20.0f);
                field_175336_F15.biomeScaleWeight = clamp_float15;
                n2 = clamp_float15;
                "".length();
                if (1 == 4) {
                    throw null;
                }
                break;
            }
            case 147: {
                final ChunkProviderSettings.Factory field_175336_F16 = this.field_175336_F;
                final float clamp_float16 = MathHelper.clamp_float(float1, 0.0f, 20.0f);
                field_175336_F16.biomeScaleOffset = clamp_float16;
                n2 = clamp_float16;
                break;
            }
        }
        if (n2 != float1 && float1 != 0.0f) {
            ((GuiTextField)this.field_175349_r.func_178061_c(n)).setText(this.func_175330_b(n, n2));
        }
        ((GuiSlider)this.field_175349_r.func_178061_c(n - (37 + 7 + 55 + 33) + (0x75 ^ 0x11))).func_175218_a(n2, "".length() != 0);
        if (!this.field_175336_F.equals(this.field_175334_E)) {
            this.func_181031_a(" ".length() != 0);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_181031_a(final boolean b) {
        this.field_175338_A = b;
        this.field_175346_u.enabled = b;
    }
    
    @Override
    public void initGui() {
        int n = "".length();
        int n2 = "".length();
        if (this.field_175349_r != null) {
            n = this.field_175349_r.func_178059_e();
            n2 = this.field_175349_r.getAmountScrolled();
        }
        this.field_175341_a = I18n.format(GuiCustomizeWorldScreen.I["   ".length()], new Object["".length()]);
        this.buttonList.clear();
        this.buttonList.add(this.field_175345_v = new GuiButton(93 + 238 - 134 + 105, 0x76 ^ 0x62, 0x84 ^ 0x81, 0xEC ^ 0xBC, 0x7E ^ 0x6A, I18n.format(GuiCustomizeWorldScreen.I[0x88 ^ 0x8C], new Object["".length()])));
        this.buttonList.add(this.field_175344_w = new GuiButton(171 + 123 - 110 + 119, this.width - (0x71 ^ 0x15), 0x70 ^ 0x75, 0x17 ^ 0x47, 0x27 ^ 0x33, I18n.format(GuiCustomizeWorldScreen.I[0x93 ^ 0x96], new Object["".length()])));
        this.buttonList.add(this.field_175346_u = new GuiButton(148 + 111 - 158 + 203, this.width / "  ".length() - (37 + 183 - 198 + 165), this.height - (0xDA ^ 0xC1), 0x59 ^ 0x3, 0x6D ^ 0x79, I18n.format(GuiCustomizeWorldScreen.I[0x2C ^ 0x2A], new Object["".length()])));
        this.buttonList.add(this.field_175347_t = new GuiButton(144 + 238 - 357 + 276, this.width / "  ".length() - (0x58 ^ 0x4), this.height - (0xB8 ^ 0xA3), 0xA ^ 0x50, 0x3C ^ 0x28, I18n.format(GuiCustomizeWorldScreen.I[0x8D ^ 0x8A], new Object["".length()])));
        this.buttonList.add(this.field_175350_z = new GuiButton(177 + 269 - 335 + 194, this.width / "  ".length() + "   ".length(), this.height - (0x9A ^ 0x81), 0x5B ^ 0x1, 0x10 ^ 0x4, I18n.format(GuiCustomizeWorldScreen.I[0xA2 ^ 0xAA], new Object["".length()])));
        this.buttonList.add(this.field_175348_s = new GuiButton(91 + 59 + 102 + 48, this.width / "  ".length() + (0xFE ^ 0x9C), this.height - (0xB9 ^ 0xA2), 0x57 ^ 0xD, 0xA3 ^ 0xB7, I18n.format(GuiCustomizeWorldScreen.I[0x6 ^ 0xF], new Object["".length()])));
        this.field_175346_u.enabled = this.field_175338_A;
        this.field_175352_x = new GuiButton(123 + 175 - 293 + 301, this.width / "  ".length() - (0xF0 ^ 0xC7), 122 + 2 - 9 + 45, 0xB ^ 0x39, 0x69 ^ 0x7D, I18n.format(GuiCustomizeWorldScreen.I[0x34 ^ 0x3E], new Object["".length()]));
        this.field_175352_x.visible = ("".length() != 0);
        this.buttonList.add(this.field_175352_x);
        this.field_175351_y = new GuiButton(18 + 66 - 27 + 250, this.width / "  ".length() + (0xB2 ^ 0xB7), 43 + 52 + 65 + 0, 0x23 ^ 0x11, 0x9A ^ 0x8E, I18n.format(GuiCustomizeWorldScreen.I[0xB ^ 0x0], new Object["".length()]));
        this.field_175351_y.visible = ("".length() != 0);
        this.buttonList.add(this.field_175351_y);
        if (this.field_175339_B != 0) {
            this.field_175352_x.visible = (" ".length() != 0);
            this.field_175351_y.visible = (" ".length() != 0);
        }
        this.func_175325_f();
        if (n != 0) {
            this.field_175349_r.func_181156_c(n);
            this.field_175349_r.scrollBy(n2);
            this.func_175328_i();
        }
    }
    
    private void func_175328_i() {
        final GuiButton field_175345_v = this.field_175345_v;
        int enabled;
        if (this.field_175349_r.func_178059_e() != 0) {
            enabled = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        field_175345_v.enabled = (enabled != 0);
        final GuiButton field_175344_w = this.field_175344_w;
        int enabled2;
        if (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - " ".length()) {
            enabled2 = " ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            enabled2 = "".length();
        }
        field_175344_w.enabled = (enabled2 != 0);
        final String s = GuiCustomizeWorldScreen.I[28 + 41 + 17 + 73];
        final Object[] array = new Object["  ".length()];
        array["".length()] = this.field_175349_r.func_178059_e() + " ".length();
        array[" ".length()] = this.field_175349_r.func_178057_f();
        this.field_175333_f = I18n.format(s, array);
        this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
        final GuiButton field_175347_t = this.field_175347_t;
        int enabled3;
        if (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - " ".length()) {
            enabled3 = " ".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            enabled3 = "".length();
        }
        field_175347_t.enabled = (enabled3 != 0);
    }
    
    private void func_175322_b(final int field_175339_B) {
        this.field_175339_B = field_175339_B;
        this.func_175329_a(" ".length() != 0);
    }
    
    public GuiCustomizeWorldScreen(final GuiScreen guiScreen, final String s) {
        this.field_175341_a = GuiCustomizeWorldScreen.I["".length()];
        this.field_175333_f = GuiCustomizeWorldScreen.I[" ".length()];
        this.field_175335_g = GuiCustomizeWorldScreen.I["  ".length()];
        this.field_175342_h = new String[0x5F ^ 0x5B];
        this.field_175338_A = ("".length() != 0);
        this.field_175339_B = "".length();
        this.field_175340_C = ("".length() != 0);
        this.field_175332_D = (Predicate<String>)new Predicate<String>() {
            final GuiCustomizeWorldScreen this$0;
            
            public boolean apply(final String s) {
                final Float tryParse = Floats.tryParse(s);
                if (s.length() != 0 && (tryParse == null || !Floats.isFinite((float)tryParse) || tryParse < 0.0f)) {
                    return "".length() != 0;
                }
                return " ".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((String)o);
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
                    if (3 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        this.field_175334_E = new ChunkProviderSettings.Factory();
        this.random = new Random();
        this.field_175343_i = (GuiCreateWorld)guiScreen;
        this.func_175324_a(s);
    }
    
    private void func_175331_h() throws IOException {
        switch (this.field_175339_B) {
            case 300: {
                this.actionPerformed((GuiButton)this.field_175349_r.func_178061_c(159 + 155 - 47 + 33));
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
            case 304: {
                this.func_175326_g();
                break;
            }
        }
        this.field_175339_B = "".length();
        this.field_175340_C = (" ".length() != 0);
        this.func_175329_a("".length() != 0);
    }
}
