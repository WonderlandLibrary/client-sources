package optfine;

import net.minecraft.world.biome.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class ConnectedProperties
{
    public static final int FACE_SIDES;
    public int sumAllWeights;
    public static final int FACE_SOUTH;
    public static final int CONNECT_TILE;
    public int[] sumWeights;
    public static final int CONNECT_MATERIAL;
    public int height;
    public static final int SYMMETRY_OPPOSITE;
    public static final int FACE_WEST;
    public int minHeight;
    public static final int METHOD_VERTICAL_HORIZONTAL;
    public static final int METHOD_NONE;
    public String[] tiles;
    public int faces;
    public static final int SYMMETRY_ALL;
    public static final int FACE_TOP;
    public static final int FACE_NORTH;
    public static final int METHOD_VERTICAL;
    public static final int FACE_UNKNOWN;
    public static final int METHOD_REPEAT;
    public static final int METHOD_HORIZONTAL;
    public static final int CONNECT_NONE;
    public static final int METHOD_RANDOM;
    public static final int SYMMETRY_NONE;
    public String name;
    public int connect;
    public static final int FACE_EAST;
    public boolean innerSeams;
    public TextureAtlasSprite[] matchTileIcons;
    public static final int CONNECT_UNKNOWN;
    public int[] weights;
    public static final int METHOD_FIXED;
    public int[] metadatas;
    public static final int CONNECT_BLOCK;
    public static final int METHOD_TOP;
    public static final int METHOD_HORIZONTAL_VERTICAL;
    public static final int FACE_BOTTOM;
    public int renderPass;
    public int[] matchBlocks;
    public static final int FACE_ALL;
    public int method;
    public int width;
    public BiomeGenBase[] biomes;
    public String basePath;
    public static final int METHOD_CTM;
    public String[] matchTiles;
    public int maxHeight;
    private static final String[] I;
    public static final int SYMMETRY_UNKNOWN;
    public TextureAtlasSprite[] tileIcons;
    public int symmetry;
    
    private static void I() {
        (I = new String[104 + 41 - 90 + 87])["".length()] = I("\u0018\b\u0015\u000627\u0005\u000e\u00061\u0006", "uiaeZ");
        ConnectedProperties.I[" ".length()] = I("\u0003)1.!\u000f8$", "nLEOE");
        ConnectedProperties.I["  ".length()] = I("\u001e\t;\u0005\f'\u0001#\u0003\u0017", "shOfd");
        ConnectedProperties.I["   ".length()] = I("\u0000\n\u0007' \t", "mosOO");
        ConnectedProperties.I[0x57 ^ 0x53] = I("\u0000\u0004\u0002?=", "tmnZN");
        ConnectedProperties.I[0x2C ^ 0x29] = I("6\u000e;\u0004+6\u0015", "UaUjN");
        ConnectedProperties.I[0x1E ^ 0x18] = I(">(\u001a4:", "XIyQI");
        ConnectedProperties.I[0x1E ^ 0x19] = I("\u001a\u0006\u00164\u001d\u000b", "xoyYx");
        ConnectedProperties.I[0x57 ^ 0x5F] = I("8\u0013\f,<<\u001d\n\u0010", "UzbdY");
        ConnectedProperties.I[0x94 ^ 0x9D] = I("\u0002\u0003)-?\u0006\u00059\u0011", "obQeZ");
        ConnectedProperties.I[0xCA ^ 0xC0] = I(";\n:'-;?50;", "IoTCH");
        ConnectedProperties.I[0x73 ^ 0x78] = I(":\u0017\u0005\t\u001c\u0000\u001c\n\u0001\u001d", "Sykln");
        ConnectedProperties.I[0x92 ^ 0x9E] = I(":\u0005\u00071)", "MlcEA");
        ConnectedProperties.I[0x4F ^ 0x42] = I("9<\u0013\u0016.%", "QYzqF");
        ConnectedProperties.I[0x4D ^ 0x43] = I("'\u0012\u0005=\n$\u0004", "PwlZb");
        ConnectedProperties.I[0x44 ^ 0x4B] = I("\u0005\r\u0018$\u0012\u0002\u0006\f", "vtuIw");
        ConnectedProperties.I[0x74 ^ 0x64] = I("n", "Ndenq");
        ConnectedProperties.I[0x35 ^ 0x24] = I("o\u0019;\u0017", "AiUpg");
        ConnectedProperties.I[0x82 ^ 0x90] = I("", "lEGtW");
        ConnectedProperties.I[0xAC ^ 0xBF] = I("M", "mtmQg");
        ConnectedProperties.I[0x20 ^ 0x34] = I("$9\b\u00192F>\b\u0000w\u0000?\u0012\u001a3\\p", "fPgtW");
        ConnectedProperties.I[0x78 ^ 0x6D] = I("P", "pasmt");
        ConnectedProperties.I[0x17 ^ 0x1] = I("", "EggZz");
        ConnectedProperties.I[0x3D ^ 0x2A] = I("On", "oBAhO");
        ConnectedProperties.I[0x3C ^ 0x24] = I("`", "MBMTG");
        ConnectedProperties.I[0x32 ^ 0x2B] = I("h", "EJNvl");
        ConnectedProperties.I[0x66 ^ 0x7C] = I("\u0001\u00179\"\u0006!\u001do*\u0004<\u001c=5\u000b$Co", "HyOCj");
        ConnectedProperties.I[0xAE ^ 0xB5] = I("Xn\u0005 \u0013\u001an\u0002)\u0004\u0007'\u001c/LT", "tNrHv");
        ConnectedProperties.I[0x54 ^ 0x48] = I("\u00167(-\u0014\u00107#v", "bRPYa");
        ConnectedProperties.I[0xBB ^ 0xA6] = I("$\t\u001a\u0019\u0015*\u0002\u000f\nN", "Ijjxa");
        ConnectedProperties.I[0xBA ^ 0xA4] = I("@", "oIBWE");
        ConnectedProperties.I[0xAA ^ 0xB5] = I("O!\u0007\u001f", "aQixC");
        ConnectedProperties.I[0x9A ^ 0xBA] = I("\u001a?\u0019\u0012\u0013\u001c?\u0012I\u0004\u00025\u0002\r\u0015A", "nZaff");
        ConnectedProperties.I[0xE6 ^ 0xC7] = I("j", "Enmjg");
        ConnectedProperties.I[0x19 ^ 0x3B] = I("\u001a\u001b\u0007'%:\u0011Q(<>\u0017\u00144ss", "SuqFI");
        ConnectedProperties.I[0x62 ^ 0x41] = I("\u001e\n\f\u000b\u0003>\u0000Z\u0004\u001a:\u0006\u001f\u0018Uw", "Wdzjo");
        ConnectedProperties.I[0x37 ^ 0x13] = I(":%96", "NWLSW");
        ConnectedProperties.I[0xB9 ^ 0x9C] = I("\f\u001f\u00167\u001e\n\u001b\u0003", "cofXm");
        ConnectedProperties.I[0x50 ^ 0x76] = I("\t<\u0014", "hPxDT");
        ConnectedProperties.I[0x79 ^ 0x5E] = I("\u001c;\r\u0016\u0016>;F\u000b\u0000$8\u0003\f\u000b0oF", "IUfxy");
        ConnectedProperties.I[0xB9 ^ 0x91] = I("Xm", "xAUWe");
        ConnectedProperties.I[0x70 ^ 0x59] = I(":.5=\u00175", "XAAIx");
        ConnectedProperties.I[0x1F ^ 0x35] = I("\u0001\u0002\u0006\r", "emqcW");
        ConnectedProperties.I[0x7D ^ 0x56] = I("\"9#", "VVSwy");
        ConnectedProperties.I[0x20 ^ 0xC] = I("$\u0015", "QeGCW");
        ConnectedProperties.I[0x15 ^ 0x38] = I("\u000f\u0007?'*", "ahMSB");
        ConnectedProperties.I[0x2B ^ 0x5] = I("\u001f\f;>1", "lcNJY");
        ConnectedProperties.I[0xF ^ 0x20] = I("\u001c\r\u000b$", "ylxPQ");
        ConnectedProperties.I[0x96 ^ 0xA6] = I("\u001d*\u0018\f", "jOkxA");
        ConnectedProperties.I[0x98 ^ 0xA9] = I("\u001c\n0(\u0018", "ocTMk");
        ConnectedProperties.I[0xAE ^ 0x9C] = I("#%?", "BIScI");
        ConnectedProperties.I[0x33 ^ 0x0] = I("0\r\t#>\u0012\rB+0\u0006\u0006Xm", "ecbMQ");
        ConnectedProperties.I[0x2B ^ 0x1F] = I("2\u000e\u0001\u0017-", "PbntF");
        ConnectedProperties.I[0x1 ^ 0x34] = I("::\u001e\u0007", "NSrbc");
        ConnectedProperties.I[0x5F ^ 0x69] = I("\u0015\n'!(\u0011\n?", "xkSDZ");
        ConnectedProperties.I[0x78 ^ 0x4F] = I("\u001d)\u001f%8?)T(8&)\u0011(#rg", "HGtKW");
        ConnectedProperties.I[0x7A ^ 0x42] = I("XF", "xjChd");
        ConnectedProperties.I[0x9A ^ 0xA3] = I("]", "pTCdh");
        ConnectedProperties.I[0xAF ^ 0x95] = I("E", "hUDlm");
        ConnectedProperties.I[0xFE ^ 0xC5] = I("\u0001\t\u0011\u000f\u001c!\u0003G\u0007\u001e<\u0002\u0015\u0018\u0011$]G", "Hggnp");
        ConnectedProperties.I[0x84 ^ 0xB8] = I("@r\u001603\u0002r\u00119$\u001f;\u000f?lL", "lRaXV");
        ConnectedProperties.I[0x80 ^ 0xBD] = I("/!\u0007 .\u000f+Q(,\u0012*\u00037#\nuQ", "fOqAB");
        ConnectedProperties.I[0xA4 ^ 0x9A] = I("KD4?5\tD36\"\u0014\r-0jG", "gdCWP");
        ConnectedProperties.I[0x4 ^ 0x3B] = I("\u0003\b \u0014.#\u0002v\u001b7'\u00043\u0007xj", "JfVuB");
        ConnectedProperties.I[0xFF ^ 0xBF] = I("hb\u0006\u001e\u001d*b\u0001\u0017\n7+\u001f\u0011Bd", "DBqvx");
        ConnectedProperties.I[0x7C ^ 0x3D] = I("NG", "nkjdR");
        ConnectedProperties.I[0x3C ^ 0x7E] = I("@", "mIPuZ");
        ConnectedProperties.I[0x3E ^ 0x7D] = I("U", "xkzaz");
        ConnectedProperties.I[0x32 ^ 0x76] = I("3=\u001b\u00066\u00137M\u000e4\u000e6\u001f\u0011;\u0016iM", "zSmgZ");
        ConnectedProperties.I[0x1C ^ 0x59] = I("Im'\u0000\u0006\u000bm \t\u0011\u0016$>\u000fYE", "eMPhc");
        ConnectedProperties.I[0x1A ^ 0x5C] = I(";\u0019\u0005;;\u001b\u0013S39\u0006\u0012\u0001,6\u001eMS", "rwsZW");
        ConnectedProperties.I[0xCB ^ 0x8C] = I("{w1\u000229w6\u000b%$>(\rmw", "WWFjW");
        ConnectedProperties.I[0x88 ^ 0xC0] = I("</\u0018\u0013-\u001c%N\u0010-\u001a\"\u0005R\b1{N", "uAnrA");
        ConnectedProperties.I[0x17 ^ 0x5E] = I("Ch\u0019\n2\u0001h\u001e\u0003%\u001c!\u0000\u0005mO", "oHnbW");
        ConnectedProperties.I[0xDD ^ 0x97] = I("|", "FRjyj");
        ConnectedProperties.I[0x69 ^ 0x22] = I("r", "HAWSA");
        ConnectedProperties.I[0x7E ^ 0x32] = I("T", "iuAoG");
        ConnectedProperties.I[0x44 ^ 0x9] = I("M", "oOtFL");
        ConnectedProperties.I[0x42 ^ 0xC] = I("!\u0016\u0017\u001a C", "czxyK");
        ConnectedProperties.I[0xF0 ^ 0xBF] = I("A--\u0018x\u000f*l\u001b*\u000e5)\u0019,\u0018e", "aELkX");
        ConnectedProperties.I[0x6B ^ 0x3B] = I("\u001a\u001e\u0001+(:\u0014W<%?\u0005\u0012pd", "SpwJD");
        ConnectedProperties.I[0xF0 ^ 0xA1] = I("ti.\u001f\u001bx9:\u001f\u0019=;<\tSx", "XIHpi");
        ConnectedProperties.I[0x51 ^ 0x3] = I("=8-1'K02d,\u0004-a\u0007-\u0006) 6#\t5$~b", "kYADB");
        ConnectedProperties.I[0xC3 ^ 0x90] = I("kt*\"\bg$>\"\n\"&84@g", "GTLMz");
        ConnectedProperties.I[0x43 ^ 0x17] = I("\u0015\">", "vVSqo");
        ConnectedProperties.I[0x35 ^ 0x60] = I("\u000e9\u000b\u0000\u0015", "iUjsf");
        ConnectedProperties.I[0x7 ^ 0x51] = I("\u0007\r\u0002\u00041\u0000\f\u0004\f'", "obpmK");
        ConnectedProperties.I[0x48 ^ 0x1F] = I("\b'\u00169\u0016\u0002-\u00154", "jHyRe");
        ConnectedProperties.I[0x10 ^ 0x48] = I("\u0004!\u00037\u0007\u0011%\u001d", "rDqCn");
        ConnectedProperties.I[0x1E ^ 0x47] = I("\u000e6\u0007", "zYwBc");
        ConnectedProperties.I[0x44 ^ 0x1E] = I("\u0018\r/\u0005\u0019\u0007", "jlAav");
        ConnectedProperties.I[0x66 ^ 0x3D] = I(":\f\u0003\u00024<", "HisgU");
        ConnectedProperties.I[0xDD ^ 0x81] = I("3\u001b;?1", "UrCZU");
        ConnectedProperties.I[0x1C ^ 0x41] = I("\u001b\u0001%,.\u001c\u0000#$8X\u001827 \u001a\r6)", "snWET");
        ConnectedProperties.I[0x28 ^ 0x76] = I("\tJ\u0013", "aaewB");
        ConnectedProperties.I[0xCB ^ 0x94] = I("\u0018\n%\u000e\u0002\r\u000e;Q\u0003\u0001\u001d>\u0000\u0004\u0000\u001b6\u0016", "noWzk");
        ConnectedProperties.I[0xA0 ^ 0xC0] = I("5\u007f\u000e", "CTfvq");
        ConnectedProperties.I[0x9 ^ 0x68] = I("\u0000\u001e\u0002&9\"\u001eI%3!\u0018\u0006,lu", "UpiHV");
        ConnectedProperties.I[0xCC ^ 0xAE] = I("\u0001\u001bF$0<\u0011F60;\u001cF >:\u001a\u0002|q", "OtfFQ");
        ConnectedProperties.I[0x31 ^ 0x52] = I("\n7y\u000b\u00030;1$\u000e+;2\u0015B+*y\u000b\u00030;12\u000b(=*F\u00114=:\u000f\u0004-==\\B", "DXYfb");
        ConnectedProperties.I[0xC4 ^ 0xA0] = I(";\u001eJ\u0000+\u0001\u0019\u0005\ttU", "uqjmN");
        ConnectedProperties.I[0xF9 ^ 0x9C] = I("$:\u000f\b\u0006\u00040Y\n\u0005\u0003:\u001c\n\u001eM=\u0017SJ", "mTyij");
        ConnectedProperties.I[0x7A ^ 0x1C] = I("\u00133\n \u00063v\u0014%\u00102v\n+\u0017a%\u00114\u0013.$\u0010!\u0007{v", "AVdDc");
        ConnectedProperties.I[0xF0 ^ 0x97] = I(".\u00050\t8\u000e\u000ff\u000e5\u0004\u000e5H=\tQf", "gkFhT");
        ConnectedProperties.I[0x0 ^ 0x68] = I("<8!0\u0019\u001c2w\"\f\u0018;2%\u0007\fv>?OU", "uVWQu");
        ConnectedProperties.I[0x63 ^ 0xA] = I("\u0014\u0005*%\u00006\u0005a&\n5\u0003./Ua", "AkAKo");
        ConnectedProperties.I[0x1 ^ 0x6B] = I("\u001a\ni\u0006%8\u0000:R?$\u0000*\u001b*=\u0000-Hl", "TeIrL");
        ConnectedProperties.I[0x7C ^ 0x17] = I("/$C&4\f.C.:\u0014%\u0007ru", "aKcHU");
        ConnectedProperties.I[0x30 ^ 0x5C] = I("\u000b9:\u00011", "iUUbZ");
        ConnectedProperties.I[0xAC ^ 0xC1] = I("\u0011 9\"/", "sLVAD");
        ConnectedProperties.I[0x44 ^ 0x2A] = I("\b\u001e\u001b\u0006-\u0019]", "jrteF");
        ConnectedProperties.I[0x2C ^ 0x43] = I("BxaDTCc}GCRfbX@AudMYGm", "rUPut");
        ConnectedProperties.I[0x35 ^ 0x45] = I("\u001e\u0019!\u0004\n>\u0013w\u0011\u000f;\u0012$IF:\u0002$\u0011F5\u0012w\u0004\u0012w\u001b2\u0004\u0015#WcR\\w", "WwWef");
        ConnectedProperties.I[0x5E ^ 0x2F] = I("b@fhb", "SrKYW");
        ConnectedProperties.I[0x2 ^ 0x70] = I("\u001e4\u00119\u0005>>G,\u0000;?\u0014tI:/\u0014,I5?G=\u001169\u00134\u0010wn]x", "WZgXi");
        ConnectedProperties.I[0xE9 ^ 0x9A] = I("\n:d &(07t+!3-:* u\";=d#!&;-6%8ud", "DUDTO");
        ConnectedProperties.I[0x7C ^ 0x8] = I("\u000e'%7\u0002.-s\"\u0007+, zN*< \"N%,s3\u0016&*':\u0017g}iv", "GISVn");
        ConnectedProperties.I[0x67 ^ 0x12] = I(" \u0002Y'\u001b\u0002\b\ns\u0016\u000b\u000b\u0010=\u0017\nM\u001f<\u0000N\u0005\u0016!\u001b\u0014\u0002\u0017'\u0013\u0002F\u000f6\u0000\u001a\u0004\u001a2\u001eTM", "nmySr");
        ConnectedProperties.I[0x21 ^ 0x57] = I("\b\r$\u0007\u0007(\u0007r\u0012\u0002-\u0006!JK,\u0016!\u0012K#\u0006r\u0003\u0013 \u0000&\n\u0012aThF", "AcRfk");
        ConnectedProperties.I[0x62 ^ 0x15] = I("\u0007\nV8\b%\u0000\u0005l\u0005,\u0003\u001f\"\u0004-E\u0010#\u0013i\u0013\u0013>\u0015 \u0006\u0017 J!\n\u0004%\u001b&\u000b\u0002-\rsE", "IevLa");
        ConnectedProperties.I[0xD0 ^ 0xA8] = I("\u0011(1'\u001a1\"g2\u001f4#4jV5342V:#g#\u000e9%3*\u000fxq}f", "XFGFv");
        ConnectedProperties.I[0x52 ^ 0x2B] = I("\u001e9&?R$3==\u001a'%t>\u00175?:?\u0016s\"<;\u001cs\"=6\u0017 zt.\u0000:;93\u001c4v#?\u001b4> )Hs", "SVTZr");
        ConnectedProperties.I[0x68 ^ 0x12] = I("\u0002\u0000\u00151R9\u0000\u000f%\u001a:\u0016F&\u0017(\f\b'\u0016n\u0011\u000e#\u001cn\u0011\u000f.\u0017=IF'\n>\u0004\b&\u001b \u0002F5\u0017'\u0002\u000e6\u0001tE", "NefBr");
        ConnectedProperties.I[0x7A ^ 0x1] = I("\u0013/<;\u00023%j)\u001b7a%<N;-&z\u0019?(-2\u001a){j", "ZAJZn");
        ConnectedProperties.I[0x6C ^ 0x10] = I("\u0010'5\u0007:d 6\u0016i +?\u000b'!*cB", "DNYbI");
        ConnectedProperties.I[0x40 ^ 0x3D] = I(",\u00188\u0000\u0017X\u001f;\u0011D\u001c\u00142\f\n\u001d\u0015nE", "xqTed");
        ConnectedProperties.I[0xFD ^ 0x83] = I("\u0006\u000f\u001f\n*:Z\u001d\u000eo<\u0013\u001e\r<h\u001e\u001d\r<h\u0014\u001d\u001co-\u000b\u0007\t#h\r\u001b\f; Z\nH'-\u0013\u0015\u0000;rZ", "HzrhO");
        ConnectedProperties.I[99 + 22 - 94 + 100] = I("/\u0004\u0000\u0016&\u000f\u000eV\u001f/\u000f\r\u001e\u0003pF", "fjvwJ");
        ConnectedProperties.I[91 + 64 - 43 + 16] = I("3\u0001\u0004.(\u0013\u000bR8-\u001e\u001b\u001aud", "zorOD");
        ConnectedProperties.I[113 + 26 - 24 + 14] = I("!\u0010<44U\u0017?%g\u0011\u001c68)\u0010\u001djq", "uyPQG");
        ConnectedProperties.I[80 + 46 - 34 + 38] = I("448\u0000\u0002\ba:\u0004G\u000e(9\u0007\u0014Z2=\r\u0012\u0016%u\u0000\u0002Zpu\u0004\b\ba8\u0007\u0013\u0012.1XG\u001c(-\u0007\u0003T", "zAUbg");
        ConnectedProperties.I[118 + 14 - 39 + 38] = I("AD", "wrjel");
        ConnectedProperties.I[4 + 73 - 39 + 94] = I("8\u0017%-\u0003\u0018\u001ds8\u0006\u001d\u001c `O\u001c\f 8O\u0013\u001cs)\u0017\u0010\u001a' \u0016QHil", "qySLo");
        ConnectedProperties.I[35 + 51 + 45 + 2] = I("H", "gukhL");
        ConnectedProperties.I[35 + 33 - 23 + 89] = I("!098\u0003'02c\u00149:\"'\u0005z", "UUALv");
        ConnectedProperties.I[56 + 27 - 65 + 117] = I("a\u0004\b\t", "Otfnv");
        ConnectedProperties.I[85 + 11 - 49 + 89] = I("\n\u00104#K\"\u0016,f\r#\f6\"Ql", "LyXFk");
        ConnectedProperties.I[61 + 24 - 40 + 92] = I(";*>\u0012\u0016=*5I", "OOFfc");
        ConnectedProperties.I[9 + 109 - 88 + 108] = I("\u0007?\u0019C\u0017%\u00061YY", "DkTcy");
        ConnectedProperties.I[26 + 72 - 25 + 66] = I("[k\u0011\u0018\u0011\u0012\u001b\u0012\r\nMk", "wKsyb");
        ConnectedProperties.I[66 + 11 - 37 + 100] = I("nQ/\u0016\u001a!\u0019\u0000\u001b\u0001!\u001a1MN", "BqBwn");
        ConnectedProperties.I[35 + 75 - 29 + 60] = I("ZH+\u0014%\u0015\u0000\u0012\u001c=\u0013\u001b|U", "vhFuQ");
    }
    
    private static BiomeGenBase[] parseBiomes(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0x9F ^ 0x8C]);
        final ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            final BiomeGenBase biome = findBiome(s2);
            if (biome == null) {
                Config.warn(ConnectedProperties.I[0x7 ^ 0x13] + s2);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                list.add(biome);
            }
            ++i;
        }
        return list.toArray(new BiomeGenBase[list.size()]);
    }
    
    private static BiomeGenBase findBiome(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        final BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < biomeGenArray.length) {
            final BiomeGenBase biomeGenBase = biomeGenArray[i];
            if (biomeGenBase != null && biomeGenBase.biomeName.replace(ConnectedProperties.I[0x3E ^ 0x2B], ConnectedProperties.I[0x17 ^ 0x1]).toLowerCase().equals(lowerCase)) {
                return biomeGenBase;
            }
            ++i;
        }
        return null;
    }
    
    private static int parseFaces(final String s) {
        if (s == null) {
            return 0x26 ^ 0x19;
        }
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0x32 ^ 0x1A]);
        int length = "".length();
        int i = "".length();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (i < tokenize.length) {
            length |= parseFace(tokenize[i]);
            ++i;
        }
        return length;
    }
    
    private static int parseBlockId(final String s) {
        final int int1 = Config.parseInt(s, -" ".length());
        if (int1 >= 0) {
            return int1;
        }
        final Block blockFromName = Block.getBlockFromName(s);
        int idFromBlock;
        if (blockFromName != null) {
            idFromBlock = Block.getIdFromBlock(blockFromName);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            idFromBlock = -" ".length();
        }
        return idFromBlock;
    }
    
    public static IProperty getProperty(final String s, final Collection collection) {
        final Iterator<IProperty> iterator = collection.iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IProperty next = iterator.next();
            if (s.equals(next.getName())) {
                return next;
            }
        }
        return null;
    }
    
    private String[] parseMatchTiles(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0x49 ^ 0x59]);
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < tokenize.length) {
            String substring = tokenize[i];
            if (substring.endsWith(ConnectedProperties.I[0x92 ^ 0x83])) {
                substring = substring.substring("".length(), substring.length() - (0x5C ^ 0x58));
            }
            tokenize[i] = TextureUtils.fixResourcePath(substring, this.basePath);
            ++i;
        }
        return tokenize;
    }
    
    private static TextureAtlasSprite getIcon(final String s) {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite spriteSafe = textureMapBlocks.getSpriteSafe(s);
        if (spriteSafe != null) {
            return spriteSafe;
        }
        return textureMapBlocks.getSpriteSafe(ConnectedProperties.I[0x1B ^ 0x75] + s);
    }
    
    private boolean isValidCtm(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames(ConnectedProperties.I[0x9 ^ 0x66]);
        }
        if (this.tiles.length < (0xEF ^ 0xC0)) {
            Config.warn(ConnectedProperties.I[0x26 ^ 0x56] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public ConnectedProperties(final Properties properties, final String s) {
        this.name = null;
        this.basePath = null;
        this.matchBlocks = null;
        this.matchTiles = null;
        this.method = "".length();
        this.tiles = null;
        this.connect = "".length();
        this.faces = (0x3D ^ 0x2);
        this.metadatas = null;
        this.biomes = null;
        this.minHeight = "".length();
        this.maxHeight = 780 + 905 - 685 + 24;
        this.renderPass = "".length();
        this.innerSeams = ("".length() != 0);
        this.width = "".length();
        this.height = "".length();
        this.weights = null;
        this.symmetry = " ".length();
        this.sumWeights = null;
        this.sumAllWeights = " ".length();
        this.matchTileIcons = null;
        this.tileIcons = null;
        this.name = parseName(s);
        this.basePath = parseBasePath(s);
        final String property = properties.getProperty(ConnectedProperties.I["".length()]);
        final IBlockState blockState = this.parseBlockState(property);
        if (blockState != null) {
            final int[] matchBlocks = new int[" ".length()];
            matchBlocks["".length()] = Block.getIdFromBlock(blockState.getBlock());
            this.matchBlocks = matchBlocks;
            final int[] metadatas = new int[" ".length()];
            metadatas["".length()] = blockState.getBlock().getMetaFromState(blockState);
            this.metadatas = metadatas;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = parseBlockIds(property);
        }
        if (this.metadatas == null) {
            this.metadatas = parseInts(properties.getProperty(ConnectedProperties.I[" ".length()]));
        }
        this.matchTiles = this.parseMatchTiles(properties.getProperty(ConnectedProperties.I["  ".length()]));
        this.method = parseMethod(properties.getProperty(ConnectedProperties.I["   ".length()]));
        this.tiles = this.parseTileNames(properties.getProperty(ConnectedProperties.I[0xB7 ^ 0xB3]));
        this.connect = parseConnect(properties.getProperty(ConnectedProperties.I[0x2B ^ 0x2E]));
        this.faces = parseFaces(properties.getProperty(ConnectedProperties.I[0x5D ^ 0x5B]));
        this.biomes = parseBiomes(properties.getProperty(ConnectedProperties.I[0x3B ^ 0x3C]));
        this.minHeight = parseInt(properties.getProperty(ConnectedProperties.I[0x15 ^ 0x1D]), -" ".length());
        this.maxHeight = parseInt(properties.getProperty(ConnectedProperties.I[0x88 ^ 0x81]), 128 + 551 - 617 + 962);
        this.renderPass = parseInt(properties.getProperty(ConnectedProperties.I[0x1F ^ 0x15]));
        this.innerSeams = parseBoolean(properties.getProperty(ConnectedProperties.I[0x18 ^ 0x13]));
        this.width = parseInt(properties.getProperty(ConnectedProperties.I[0xA ^ 0x6]));
        this.height = parseInt(properties.getProperty(ConnectedProperties.I[0x0 ^ 0xD]));
        this.weights = parseInts(properties.getProperty(ConnectedProperties.I[0xA9 ^ 0xA7]));
        this.symmetry = parseSymmetry(properties.getProperty(ConnectedProperties.I[0x68 ^ 0x67]));
    }
    
    public boolean isValid(final String s) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn(ConnectedProperties.I[0x7C ^ 0x17] + s);
            return "".length() != 0;
        }
        if (this.basePath == null) {
            Config.warn(ConnectedProperties.I[0xF3 ^ 0x91] + s);
            return "".length() != 0;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }
        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }
        if (this.matchBlocks == null && this.matchTiles == null) {
            Config.warn(ConnectedProperties.I[0x5A ^ 0x39] + s);
            return "".length() != 0;
        }
        if (this.method == 0) {
            Config.warn(ConnectedProperties.I[0x24 ^ 0x40] + s);
            return "".length() != 0;
        }
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.warn(ConnectedProperties.I[0xEC ^ 0x86] + s);
            return "".length() != 0;
        }
        if (this.connect == 0) {
            this.connect = this.detectConnect();
        }
        if (this.connect == 105 + 98 - 153 + 78) {
            Config.warn(ConnectedProperties.I[0x6A ^ 0xF] + s);
            return "".length() != 0;
        }
        if (this.renderPass > 0) {
            Config.warn(ConnectedProperties.I[0xEE ^ 0x88] + this.renderPass);
            return "".length() != 0;
        }
        if ((this.faces & 109 + 2 - 70 + 87) != 0x0) {
            Config.warn(ConnectedProperties.I[0x65 ^ 0x2] + s);
            return "".length() != 0;
        }
        if ((this.symmetry & 118 + 56 - 105 + 59) != 0x0) {
            Config.warn(ConnectedProperties.I[0x3 ^ 0x6B] + s);
            return "".length() != 0;
        }
        switch (this.method) {
            case 1: {
                return this.isValidCtm(s);
            }
            case 2: {
                return this.isValidHorizontal(s);
            }
            case 3: {
                return this.isValidTop(s);
            }
            case 4: {
                return this.isValidRandom(s);
            }
            case 5: {
                return this.isValidRepeat(s);
            }
            case 6: {
                return this.isValidVertical(s);
            }
            case 7: {
                return this.isValidFixed(s);
            }
            case 8: {
                return this.isValidHorizontalVertical(s);
            }
            case 9: {
                return this.isValidVerticalHorizontal(s);
            }
            default: {
                Config.warn(ConnectedProperties.I[0xAB ^ 0xC2] + s);
                return "".length() != 0;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static boolean parseBoolean(final String s) {
        int n;
        if (s == null) {
            n = "".length();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            n = (s.toLowerCase().equals(ConnectedProperties.I[0xA9 ^ 0x8D]) ? 1 : 0);
        }
        return n != 0;
    }
    
    private boolean isValidFixed(final String s) {
        if (this.tiles == null) {
            Config.warn(ConnectedProperties.I[18 + 73 - 48 + 86] + s);
            return "".length() != 0;
        }
        if (this.tiles.length != " ".length()) {
            Config.warn(ConnectedProperties.I[101 + 50 - 21 + 0]);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static int parseFace(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals(ConnectedProperties.I[0x79 ^ 0x50]) || lowerCase.equals(ConnectedProperties.I[0xB0 ^ 0x9A])) {
            return " ".length();
        }
        if (lowerCase.equals(ConnectedProperties.I[0x48 ^ 0x63]) || lowerCase.equals(ConnectedProperties.I[0x60 ^ 0x4C])) {
            return "  ".length();
        }
        if (lowerCase.equals(ConnectedProperties.I[0x24 ^ 0x9])) {
            return 0x38 ^ 0x3C;
        }
        if (lowerCase.equals(ConnectedProperties.I[0x91 ^ 0xBF])) {
            return 0x54 ^ 0x5C;
        }
        if (lowerCase.equals(ConnectedProperties.I[0x7E ^ 0x51])) {
            return 0x5B ^ 0x7B;
        }
        if (lowerCase.equals(ConnectedProperties.I[0x9F ^ 0xAF])) {
            return 0x9A ^ 0x8A;
        }
        if (lowerCase.equals(ConnectedProperties.I[0x4 ^ 0x35])) {
            return 0x94 ^ 0xA8;
        }
        if (lowerCase.equals(ConnectedProperties.I[0x3B ^ 0x9])) {
            return 0xBF ^ 0x80;
        }
        Config.warn(ConnectedProperties.I[0x6D ^ 0x5E] + lowerCase);
        return 98 + 19 - 81 + 92;
    }
    
    private boolean isValidRepeat(final String s) {
        if (this.tiles == null) {
            Config.warn(ConnectedProperties.I[0x20 ^ 0x5D] + s);
            return "".length() != 0;
        }
        if (this.width <= 0 || this.width > (0xB6 ^ 0xA6)) {
            Config.warn(ConnectedProperties.I[43 + 13 + 35 + 37] + s);
            return "".length() != 0;
        }
        if (this.height <= 0 || this.height > (0x23 ^ 0x33)) {
            Config.warn(ConnectedProperties.I[51 + 73 - 67 + 70] + s);
            return "".length() != 0;
        }
        if (this.tiles.length != this.width * this.height) {
            Config.warn(ConnectedProperties.I[0x2 ^ 0x7C] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static int parseConnect(final String s) {
        if (s == null) {
            return "".length();
        }
        if (s.equals(ConnectedProperties.I[0xD ^ 0x39])) {
            return " ".length();
        }
        if (s.equals(ConnectedProperties.I[0x9F ^ 0xAA])) {
            return "  ".length();
        }
        if (s.equals(ConnectedProperties.I[0x5F ^ 0x69])) {
            return "   ".length();
        }
        Config.warn(ConnectedProperties.I[0x66 ^ 0x51] + s);
        return 41 + 106 - 77 + 58;
    }
    
    private static int parseSymmetry(final String s) {
        if (s == null) {
            return " ".length();
        }
        if (s.equals(ConnectedProperties.I[0xE7 ^ 0xC2])) {
            return "  ".length();
        }
        if (s.equals(ConnectedProperties.I[0x35 ^ 0x13])) {
            return 0x22 ^ 0x24;
        }
        Config.warn(ConnectedProperties.I[0x23 ^ 0x4] + s);
        return " ".length();
    }
    
    private static int[] parseInts(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList<Integer> list = new ArrayList<Integer>();
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0x27 ^ 0x1F]);
        int i = "".length();
        "".length();
        if (4 < 1) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            if (s2.contains(ConnectedProperties.I[0xB0 ^ 0x89])) {
                final String[] tokenize2 = Config.tokenize(s2, ConnectedProperties.I[0xB0 ^ 0x8A]);
                if (tokenize2.length != "  ".length()) {
                    Config.warn(ConnectedProperties.I[0x66 ^ 0x5D] + s2 + ConnectedProperties.I[0xE ^ 0x32] + s);
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else {
                    final int int1 = Config.parseInt(tokenize2["".length()], -" ".length());
                    final int int2 = Config.parseInt(tokenize2[" ".length()], -" ".length());
                    if (int1 >= 0 && int2 >= 0 && int1 <= int2) {
                        int j = int1;
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                        while (j <= int2) {
                            list.add(j);
                            ++j;
                        }
                        "".length();
                        if (true != true) {
                            throw null;
                        }
                    }
                    else {
                        Config.warn(ConnectedProperties.I[0xB8 ^ 0x85] + s2 + ConnectedProperties.I[0x23 ^ 0x1D] + s);
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                }
            }
            else {
                final int int3 = Config.parseInt(s2, -" ".length());
                if (int3 < 0) {
                    Config.warn(ConnectedProperties.I[0x5C ^ 0x63] + s2 + ConnectedProperties.I[0xFE ^ 0xBE] + s);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    list.add(int3);
                }
            }
            ++i;
        }
        final int[] array = new int[list.size()];
        int k = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (k < array.length) {
            array[k] = (int)list.get(k);
            ++k;
        }
        return array;
    }
    
    public void updateIcons(final TextureMap textureMap) {
        if (this.matchTiles != null) {
            this.matchTileIcons = registerIcons(this.matchTiles, textureMap);
        }
        if (this.tiles != null) {
            this.tileIcons = registerIcons(this.tiles, textureMap);
        }
    }
    
    private static int parseMethod(final String s) {
        if (s == null) {
            return " ".length();
        }
        if (s.equals(ConnectedProperties.I[0xD1 ^ 0x85]) || s.equals(ConnectedProperties.I[0xC7 ^ 0x92])) {
            return " ".length();
        }
        if (s.equals(ConnectedProperties.I[0x3C ^ 0x6A]) || s.equals(ConnectedProperties.I[0xD4 ^ 0x83])) {
            return "  ".length();
        }
        if (s.equals(ConnectedProperties.I[0xEF ^ 0xB7])) {
            return 0x3C ^ 0x3A;
        }
        if (s.equals(ConnectedProperties.I[0x6A ^ 0x33])) {
            return "   ".length();
        }
        if (s.equals(ConnectedProperties.I[0x4D ^ 0x17])) {
            return 0xF ^ 0xB;
        }
        if (s.equals(ConnectedProperties.I[0xE1 ^ 0xBA])) {
            return 0xB7 ^ 0xB2;
        }
        if (s.equals(ConnectedProperties.I[0x9 ^ 0x55])) {
            return 0xBC ^ 0xBB;
        }
        if (s.equals(ConnectedProperties.I[0x7F ^ 0x22]) || s.equals(ConnectedProperties.I[0x1C ^ 0x42])) {
            return 0x73 ^ 0x7B;
        }
        if (!s.equals(ConnectedProperties.I[0x33 ^ 0x6C]) && !s.equals(ConnectedProperties.I[0x72 ^ 0x12])) {
            Config.warn(ConnectedProperties.I[0xD2 ^ 0xB3] + s);
            return "".length();
        }
        return 0x4A ^ 0x43;
    }
    
    private boolean isValidRandom(final String s) {
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null) {
                if (this.weights.length > this.tiles.length) {
                    Config.warn(ConnectedProperties.I[0xB9 ^ 0xC0] + s);
                    final int[] weights = new int[this.tiles.length];
                    System.arraycopy(this.weights, "".length(), weights, "".length(), weights.length);
                    this.weights = weights;
                }
                if (this.weights.length < this.tiles.length) {
                    Config.warn(ConnectedProperties.I[0xD9 ^ 0xA3] + s);
                    final int[] weights2 = new int[this.tiles.length];
                    System.arraycopy(this.weights, "".length(), weights2, "".length(), this.weights.length);
                    final int average = ConnectedUtils.getAverage(this.weights);
                    int i = this.weights.length;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    while (i < weights2.length) {
                        weights2[i] = average;
                        ++i;
                    }
                    this.weights = weights2;
                }
                this.sumWeights = new int[this.weights.length];
                int length = "".length();
                int j = "".length();
                "".length();
                if (3 == 0) {
                    throw null;
                }
                while (j < this.weights.length) {
                    length += this.weights[j];
                    this.sumWeights[j] = length;
                    ++j;
                }
                this.sumAllWeights = length;
                if (this.sumAllWeights <= 0) {
                    Config.warn(ConnectedProperties.I[0xCE ^ 0xB5] + length);
                    this.sumAllWeights = " ".length();
                }
            }
            return " ".length() != 0;
        }
        Config.warn(ConnectedProperties.I[0xCF ^ 0xB3] + s);
        return "".length() != 0;
    }
    
    private int[] detectMatchBlocks() {
        if (!this.name.startsWith(ConnectedProperties.I[0x67 ^ 0xB])) {
            return null;
        }
        int i;
        final int n = i = ConnectedProperties.I[0x49 ^ 0x24].length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < this.name.length()) {
            final char char1 = this.name.charAt(i);
            if (char1 < (0x91 ^ 0xA1)) {
                break;
            }
            if (char1 > (0xF9 ^ 0xC0)) {
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        if (i == n) {
            return null;
        }
        final int int1 = Config.parseInt(this.name.substring(n, i), -" ".length());
        int[] array;
        if (int1 < 0) {
            array = null;
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            (array = new int[" ".length()])["".length()] = int1;
        }
        return array;
    }
    
    private boolean isValidHorizontal(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames(ConnectedProperties.I[0x2B ^ 0x5A]);
        }
        if (this.tiles.length != (0x60 ^ 0x64)) {
            Config.warn(ConnectedProperties.I[0x2B ^ 0x59] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
        CONNECT_TILE = "  ".length();
        FACE_NORTH = (0xF ^ 0xB);
        FACE_UNKNOWN = 1 + 86 - 1 + 42;
        METHOD_HORIZONTAL = "  ".length();
        FACE_SIDES = (0x58 ^ 0x64);
        METHOD_CTM = " ".length();
        CONNECT_BLOCK = " ".length();
        CONNECT_UNKNOWN = 117 + 9 - 124 + 126;
        SYMMETRY_UNKNOWN = 6 + 114 - 112 + 120;
        METHOD_VERTICAL = (0x21 ^ 0x27);
        FACE_BOTTOM = " ".length();
        CONNECT_MATERIAL = "   ".length();
        SYMMETRY_NONE = " ".length();
        FACE_ALL = (0x8B ^ 0xB4);
        METHOD_TOP = "   ".length();
        METHOD_FIXED = (0x6 ^ 0x1);
        METHOD_VERTICAL_HORIZONTAL = (0x92 ^ 0x9B);
        FACE_SOUTH = (0x67 ^ 0x6F);
        FACE_EAST = (0x37 ^ 0x17);
        SYMMETRY_OPPOSITE = "  ".length();
        METHOD_HORIZONTAL_VERTICAL = (0x36 ^ 0x3E);
        FACE_WEST = (0x27 ^ 0x37);
        METHOD_NONE = "".length();
        CONNECT_NONE = "".length();
        METHOD_RANDOM = (0xBC ^ 0xB8);
        FACE_TOP = "  ".length();
        METHOD_REPEAT = (0x5 ^ 0x0);
        SYMMETRY_ALL = (0x53 ^ 0x55);
    }
    
    private static int[] parseBlockIds(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList<Integer> list = new ArrayList<Integer>();
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0x4 ^ 0x45]);
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            if (s2.contains(ConnectedProperties.I[0x74 ^ 0x36])) {
                final String[] tokenize2 = Config.tokenize(s2, ConnectedProperties.I[0x2F ^ 0x6C]);
                if (tokenize2.length != "  ".length()) {
                    Config.warn(ConnectedProperties.I[0x3F ^ 0x7B] + s2 + ConnectedProperties.I[0x47 ^ 0x2] + s);
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else {
                    final int blockId = parseBlockId(tokenize2["".length()]);
                    final int blockId2 = parseBlockId(tokenize2[" ".length()]);
                    if (blockId >= 0 && blockId2 >= 0 && blockId <= blockId2) {
                        int j = blockId;
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                        while (j <= blockId2) {
                            list.add(j);
                            ++j;
                        }
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        Config.warn(ConnectedProperties.I[0x9 ^ 0x4F] + s2 + ConnectedProperties.I[0x21 ^ 0x66] + s);
                        "".length();
                        if (3 == 1) {
                            throw null;
                        }
                    }
                }
            }
            else {
                final int blockId3 = parseBlockId(s2);
                if (blockId3 < 0) {
                    Config.warn(ConnectedProperties.I[0xF5 ^ 0xBD] + s2 + ConnectedProperties.I[0x3C ^ 0x75] + s);
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    list.add(blockId3);
                }
            }
            ++i;
        }
        final int[] array = new int[list.size()];
        int k = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (k < array.length) {
            array[k] = (int)list.get(k);
            ++k;
        }
        return array;
    }
    
    private static String parseName(final String s) {
        String s2 = s;
        final int lastIndex = s.lastIndexOf(0x1 ^ 0x2E);
        if (lastIndex >= 0) {
            s2 = s.substring(lastIndex + " ".length());
        }
        final int lastIndex2 = s2.lastIndexOf(0x57 ^ 0x79);
        if (lastIndex2 >= 0) {
            s2 = s2.substring("".length(), lastIndex2);
        }
        return s2;
    }
    
    private boolean isValidHorizontalVertical(final String s) {
        if (this.tiles == null) {
            Config.warn(ConnectedProperties.I[0xF4 ^ 0x81] + s);
            return "".length() != 0;
        }
        if (this.tiles.length != (0x47 ^ 0x40)) {
            Config.warn(ConnectedProperties.I[0xEF ^ 0x99] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public boolean matchesIcon(final TextureAtlasSprite textureAtlasSprite) {
        if (this.matchTileIcons == null || this.matchTileIcons.length <= 0) {
            return " ".length() != 0;
        }
        int i = "".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (i < this.matchTileIcons.length) {
            if (this.matchTileIcons[i] == textureAtlasSprite) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    private boolean isValidVertical(final String s) {
        if (this.tiles == null) {
            Config.warn(ConnectedProperties.I[0xFD ^ 0x8E] + s);
            return "".length() != 0;
        }
        if (this.tiles.length != (0x17 ^ 0x13)) {
            Config.warn(ConnectedProperties.I[0x43 ^ 0x37] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private boolean isValidTop(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames(ConnectedProperties.I[20 + 42 + 45 + 24]);
        }
        if (this.tiles.length != " ".length()) {
            Config.warn(ConnectedProperties.I[0 + 122 - 100 + 110] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private int detectConnect() {
        int n;
        if (this.matchBlocks != null) {
            n = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (this.matchTiles != null) {
            n = "  ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = 90 + 48 - 44 + 34;
        }
        return n;
    }
    
    private String[] parseTileNames(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList<String> list = new ArrayList<String>();
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0x83 ^ 0x94]);
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            Label_0276: {
                if (s2.contains(ConnectedProperties.I[0x96 ^ 0x8E])) {
                    final String[] tokenize2 = Config.tokenize(s2, ConnectedProperties.I[0xB7 ^ 0xAE]);
                    if (tokenize2.length == "  ".length()) {
                        final int int1 = Config.parseInt(tokenize2["".length()], -" ".length());
                        final int int2 = Config.parseInt(tokenize2[" ".length()], -" ".length());
                        if (int1 >= 0 && int2 >= 0) {
                            if (int1 > int2) {
                                Config.warn(ConnectedProperties.I[0x52 ^ 0x48] + s2 + ConnectedProperties.I[0x47 ^ 0x5C] + s);
                                "".length();
                                if (false) {
                                    throw null;
                                }
                                break Label_0276;
                            }
                            else {
                                int j = int1;
                                while (j <= int2) {
                                    list.add(String.valueOf(j));
                                    ++j;
                                    "".length();
                                    if (2 == 1) {
                                        throw null;
                                    }
                                }
                                "".length();
                                if (4 <= -1) {
                                    throw null;
                                }
                                break Label_0276;
                            }
                        }
                    }
                }
                list.add(s2);
            }
            ++i;
        }
        final String[] array = list.toArray(new String[list.size()]);
        int k = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (k < array.length) {
            String s3 = TextureUtils.fixResourcePath(array[k], this.basePath);
            if (!s3.startsWith(this.basePath) && !s3.startsWith(ConnectedProperties.I[0x3B ^ 0x27]) && !s3.startsWith(ConnectedProperties.I[0x57 ^ 0x4A])) {
                s3 = String.valueOf(this.basePath) + ConnectedProperties.I[0xD9 ^ 0xC7] + s3;
            }
            if (s3.endsWith(ConnectedProperties.I[0x90 ^ 0x8F])) {
                s3 = s3.substring("".length(), s3.length() - (0xB9 ^ 0xBD));
            }
            final String s4 = ConnectedProperties.I[0x2D ^ 0xD];
            if (s3.startsWith(s4)) {
                s3 = s3.substring(s4.length());
            }
            if (s3.startsWith(ConnectedProperties.I[0x6 ^ 0x27])) {
                s3 = s3.substring(" ".length());
            }
            array[k] = s3;
            ++k;
        }
        return array;
    }
    
    private static TextureAtlasSprite[] registerIcons(final String[] array, final TextureMap textureMap) {
        if (array == null) {
            return null;
        }
        final ArrayList<TextureAtlasSprite> list = new ArrayList<TextureAtlasSprite>();
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < array.length) {
            final ResourceLocation resourceLocation = new ResourceLocation(array[i]);
            final String resourceDomain = resourceLocation.getResourceDomain();
            String s = resourceLocation.getResourcePath();
            if (!s.contains(ConnectedProperties.I[18 + 131 - 41 + 25])) {
                s = ConnectedProperties.I[105 + 75 - 156 + 110] + s;
            }
            final String string = String.valueOf(s) + ConnectedProperties.I[90 + 31 - 80 + 94];
            if (!Config.hasResource(new ResourceLocation(resourceDomain, string))) {
                Config.warn(ConnectedProperties.I[111 + 24 - 16 + 17] + string);
            }
            final String s2 = ConnectedProperties.I[20 + 133 - 108 + 92];
            String substring = s;
            if (s.startsWith(s2)) {
                substring = s.substring(s2.length());
            }
            list.add(textureMap.registerSprite(new ResourceLocation(resourceDomain, substring)));
            ++i;
        }
        return list.toArray(new TextureAtlasSprite[list.size()]);
    }
    
    public boolean matchesBlock(final int n) {
        if (this.matchBlocks == null || this.matchBlocks.length <= 0) {
            return " ".length() != 0;
        }
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.matchBlocks.length) {
            if (this.matchBlocks[i] == n) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    private String[] detectMatchTiles() {
        String[] array;
        if (getIcon(this.name) == null) {
            array = null;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            (array = new String[" ".length()])["".length()] = this.name;
        }
        return array;
    }
    
    private static int parseInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        final int int1 = Config.parseInt(s, -" ".length());
        if (int1 < 0) {
            Config.warn(ConnectedProperties.I[0x5D ^ 0x7E] + s);
            return n;
        }
        return int1;
    }
    
    @Override
    public String toString() {
        return ConnectedProperties.I[124 + 0 - 85 + 99] + this.name + ConnectedProperties.I[138 + 97 - 111 + 15] + this.basePath + ConnectedProperties.I[35 + 12 - 0 + 93] + Config.arrayToString(this.matchBlocks) + ConnectedProperties.I[110 + 117 - 158 + 72] + Config.arrayToString(this.matchTiles);
    }
    
    private IBlockState parseBlockState(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, ConnectedProperties.I[0xF8 ^ 0xB2]);
        if (tokenize.length < "  ".length()) {
            return null;
        }
        final String string = String.valueOf(tokenize["".length()]) + ConnectedProperties.I[0x4E ^ 0x5] + tokenize[" ".length()];
        final Block blockFromName = Block.getBlockFromName(string);
        if (blockFromName == null) {
            return null;
        }
        int n = -" ".length();
        IBlockState blockState = null;
        int i = "  ".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            if (s2.length() >= " ".length()) {
                if (Character.isDigit(s2.charAt("".length()))) {
                    if (s2.indexOf(0x41 ^ 0x6C) < 0 && s2.indexOf(0x29 ^ 0x5) < 0) {
                        final int int1 = Config.parseInt(s2, -" ".length());
                        if (int1 >= 0) {
                            n = int1;
                            "".length();
                            if (2 == 0) {
                                throw null;
                            }
                        }
                    }
                }
                else {
                    final String[] tokenize2 = Config.tokenize(s2, ConnectedProperties.I[0xDD ^ 0x91]);
                    if (tokenize2.length >= "  ".length()) {
                        final String s3 = tokenize2["".length()];
                        final String s4 = tokenize2[" ".length()];
                        if (s4.indexOf(0xA0 ^ 0x8C) < 0) {
                            if (blockState == null) {
                                blockState = blockFromName.getDefaultState();
                            }
                            final IProperty property = getProperty(s3, blockState.getPropertyNames());
                            if (property == null) {
                                final String s5 = ConnectedProperties.I[0x6B ^ 0x26];
                                Config.warn(ConnectedProperties.I[0x54 ^ 0x1A] + s5 + string + s5 + ConnectedProperties.I[0x7D ^ 0x32] + s5 + s3 + s5);
                                "".length();
                                if (2 < 1) {
                                    throw null;
                                }
                            }
                            else {
                                Comparable comparable = ConnectedParser.parseValue(s4, property.getValueClass());
                                if (comparable == null) {
                                    comparable = ConnectedParser.getPropertyValue(s4, property.getAllowedValues());
                                }
                                if (comparable == null) {
                                    Config.warn(ConnectedProperties.I[0x65 ^ 0x35] + s4 + ConnectedProperties.I[0x76 ^ 0x27] + property);
                                    "".length();
                                    if (-1 >= 4) {
                                        throw null;
                                    }
                                }
                                else if (!(comparable instanceof Comparable)) {
                                    Config.warn(ConnectedProperties.I[0xC7 ^ 0x95] + s4 + ConnectedProperties.I[0xCD ^ 0x9E] + property);
                                    "".length();
                                    if (2 < -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    blockState = blockState.withProperty((IProperty<Comparable>)property, (Comparable)comparable);
                                }
                            }
                        }
                    }
                }
            }
            ++i;
        }
        if (blockState != null) {
            return blockState;
        }
        if (n < 0) {
            return null;
        }
        return blockFromName.getStateFromMeta(n);
    }
    
    private static String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(0x15 ^ 0x3A);
        String substring;
        if (lastIndex < 0) {
            substring = ConnectedProperties.I[0x13 ^ 0x1];
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), lastIndex);
        }
        return substring;
    }
    
    private static int parseInt(final String s) {
        if (s == null) {
            return -" ".length();
        }
        final int int1 = Config.parseInt(s, -" ".length());
        if (int1 < 0) {
            Config.warn(ConnectedProperties.I[0x50 ^ 0x72] + s);
        }
        return int1;
    }
    
    private boolean isValidVerticalHorizontal(final String s) {
        if (this.tiles == null) {
            Config.warn(ConnectedProperties.I[0x2F ^ 0x58] + s);
            return "".length() != 0;
        }
        if (this.tiles.length != (0x79 ^ 0x7E)) {
            Config.warn(ConnectedProperties.I[0xFB ^ 0x83] + s);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
}
