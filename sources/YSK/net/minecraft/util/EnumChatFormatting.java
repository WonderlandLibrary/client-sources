package net.minecraft.util;

import java.util.regex.*;
import java.util.*;
import com.google.common.collect.*;

public enum EnumChatFormatting
{
    LIGHT_PURPLE(EnumChatFormatting.I[0x48 ^ 0x52], 0x91 ^ 0x9C, EnumChatFormatting.I[0x2E ^ 0x35], (char)(0x36 ^ 0x52), 0xB ^ 0x6), 
    RESET(EnumChatFormatting.I[0x3F ^ 0x15], 0x1A ^ 0xF, EnumChatFormatting.I[0x11 ^ 0x3A], (char)(0xF9 ^ 0x8B), -" ".length()), 
    BLACK(EnumChatFormatting.I["".length()], "".length(), EnumChatFormatting.I[" ".length()], (char)(0xAB ^ 0x9B), "".length());
    
    private static final String[] I;
    
    DARK_GRAY(EnumChatFormatting.I[0x5 ^ 0x15], 0xB4 ^ 0xBC, EnumChatFormatting.I[0x5E ^ 0x4F], (char)(0x31 ^ 0x9), 0xB4 ^ 0xBC);
    
    private final String controlString;
    private final String name;
    
    GOLD(EnumChatFormatting.I[0xA8 ^ 0xA4], 0x4C ^ 0x4A, EnumChatFormatting.I[0x6C ^ 0x61], (char)(0x1 ^ 0x37), 0x65 ^ 0x63);
    
    private final int colorIndex;
    
    DARK_PURPLE(EnumChatFormatting.I[0x7 ^ 0xD], 0x83 ^ 0x86, EnumChatFormatting.I[0x64 ^ 0x6F], (char)(0x84 ^ 0xB1), 0x7D ^ 0x78), 
    STRIKETHROUGH(EnumChatFormatting.I[0x26 ^ 0x2], 0x48 ^ 0x5A, EnumChatFormatting.I[0x6C ^ 0x49], (char)(0x13 ^ 0x7E), (boolean)(" ".length() != 0)), 
    GREEN(EnumChatFormatting.I[0xAB ^ 0xBF], 0x6E ^ 0x64, EnumChatFormatting.I[0xD3 ^ 0xC6], (char)(0x51 ^ 0x30), 0x89 ^ 0x83), 
    ITALIC(EnumChatFormatting.I[0x46 ^ 0x6E], 0x85 ^ 0x91, EnumChatFormatting.I[0xB5 ^ 0x9C], (char)(0x12 ^ 0x7D), (boolean)(" ".length() != 0)), 
    DARK_BLUE(EnumChatFormatting.I["  ".length()], " ".length(), EnumChatFormatting.I["   ".length()], (char)(0xBE ^ 0x8F), " ".length()), 
    YELLOW(EnumChatFormatting.I[0x6D ^ 0x71], 0x3B ^ 0x35, EnumChatFormatting.I[0xA5 ^ 0xB8], (char)(0xA7 ^ 0xC2), 0x68 ^ 0x66), 
    UNDERLINE(EnumChatFormatting.I[0x4D ^ 0x6B], 0xAE ^ 0xBD, EnumChatFormatting.I[0xA0 ^ 0x87], (char)(0x4C ^ 0x22), (boolean)(" ".length() != 0)), 
    OBFUSCATED(EnumChatFormatting.I[0xB7 ^ 0x97], 0x24 ^ 0x34, EnumChatFormatting.I[0xC ^ 0x2D], (char)(0xF6 ^ 0x9D), (boolean)(" ".length() != 0)), 
    DARK_AQUA(EnumChatFormatting.I[0xA8 ^ 0xAE], "   ".length(), EnumChatFormatting.I[0x92 ^ 0x95], (char)(0x5F ^ 0x6C), "   ".length()), 
    DARK_GREEN(EnumChatFormatting.I[0x83 ^ 0x87], "  ".length(), EnumChatFormatting.I[0x95 ^ 0x90], (char)(0x2C ^ 0x1E), "  ".length()), 
    GRAY(EnumChatFormatting.I[0x77 ^ 0x79], 0xC2 ^ 0xC5, EnumChatFormatting.I[0x3D ^ 0x32], (char)(0xAC ^ 0x9B), 0x88 ^ 0x8F);
    
    private static final Pattern formattingCodePattern;
    
    WHITE(EnumChatFormatting.I[0xBA ^ 0xA4], 0xA5 ^ 0xAA, EnumChatFormatting.I[0x26 ^ 0x39], (char)(0x19 ^ 0x7F), 0xA5 ^ 0xAA), 
    AQUA(EnumChatFormatting.I[0xB2 ^ 0xA4], 0xA5 ^ 0xAE, EnumChatFormatting.I[0x95 ^ 0x82], (char)(0xC1 ^ 0xA3), 0x36 ^ 0x3D), 
    BOLD(EnumChatFormatting.I[0x32 ^ 0x10], 0x93 ^ 0x82, EnumChatFormatting.I[0xB3 ^ 0x90], (char)(0x1E ^ 0x72), (boolean)(" ".length() != 0)), 
    DARK_RED(EnumChatFormatting.I[0xAF ^ 0xA7], 0x85 ^ 0x81, EnumChatFormatting.I[0xC9 ^ 0xC0], (char)(0x99 ^ 0xAD), 0x1A ^ 0x1E);
    
    private static final EnumChatFormatting[] ENUM$VALUES;
    private final boolean fancyStyling;
    private final char formattingCode;
    private static final Map<String, EnumChatFormatting> nameMapping;
    
    RED(EnumChatFormatting.I[0x8A ^ 0x92], 0x1C ^ 0x10, EnumChatFormatting.I[0x5B ^ 0x42], (char)(0xD5 ^ 0xB6), 0xAE ^ 0xA2), 
    BLUE(EnumChatFormatting.I[0x6D ^ 0x7F], 0xD ^ 0x4, EnumChatFormatting.I[0x25 ^ 0x36], (char)(0x1A ^ 0x23), 0x7D ^ 0x74);
    
    private static String func_175745_c(final String s) {
        return s.toLowerCase().replaceAll(EnumChatFormatting.I[0x61 ^ 0x4F], EnumChatFormatting.I[0x8F ^ 0xA0]);
    }
    
    public static Collection<String> getValidValues(final boolean b, final boolean b2) {
        final ArrayList arrayList = Lists.newArrayList();
        final EnumChatFormatting[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < length) {
            final EnumChatFormatting enumChatFormatting = values[i];
            if ((!enumChatFormatting.isColor() || b) && (!enumChatFormatting.isFancyStyling() || b2)) {
                arrayList.add(enumChatFormatting.getFriendlyName());
            }
            ++i;
        }
        return (Collection<String>)arrayList;
    }
    
    public int getColorIndex() {
        return this.colorIndex;
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final char c, final boolean b) {
        this(s, n, s2, c, b, -" ".length());
    }
    
    private EnumChatFormatting(final String s, final int n, final String name, final char formattingCode, final boolean fancyStyling, final int colorIndex) {
        this.name = name;
        this.formattingCode = formattingCode;
        this.fancyStyling = fancyStyling;
        this.colorIndex = colorIndex;
        this.controlString = EnumChatFormatting.I[0x75 ^ 0x45] + formattingCode;
    }
    
    @Override
    public String toString() {
        return this.controlString;
    }
    
    static {
        I();
        final EnumChatFormatting[] enum$VALUES = new EnumChatFormatting[0x1C ^ 0xA];
        enum$VALUES["".length()] = EnumChatFormatting.BLACK;
        enum$VALUES[" ".length()] = EnumChatFormatting.DARK_BLUE;
        enum$VALUES["  ".length()] = EnumChatFormatting.DARK_GREEN;
        enum$VALUES["   ".length()] = EnumChatFormatting.DARK_AQUA;
        enum$VALUES[0x5A ^ 0x5E] = EnumChatFormatting.DARK_RED;
        enum$VALUES[0x45 ^ 0x40] = EnumChatFormatting.DARK_PURPLE;
        enum$VALUES[0x95 ^ 0x93] = EnumChatFormatting.GOLD;
        enum$VALUES[0x35 ^ 0x32] = EnumChatFormatting.GRAY;
        enum$VALUES[0x9 ^ 0x1] = EnumChatFormatting.DARK_GRAY;
        enum$VALUES[0xB2 ^ 0xBB] = EnumChatFormatting.BLUE;
        enum$VALUES[0xCD ^ 0xC7] = EnumChatFormatting.GREEN;
        enum$VALUES[0x85 ^ 0x8E] = EnumChatFormatting.AQUA;
        enum$VALUES[0x9F ^ 0x93] = EnumChatFormatting.RED;
        enum$VALUES[0x66 ^ 0x6B] = EnumChatFormatting.LIGHT_PURPLE;
        enum$VALUES[0x32 ^ 0x3C] = EnumChatFormatting.YELLOW;
        enum$VALUES[0x60 ^ 0x6F] = EnumChatFormatting.WHITE;
        enum$VALUES[0x73 ^ 0x63] = EnumChatFormatting.OBFUSCATED;
        enum$VALUES[0x92 ^ 0x83] = EnumChatFormatting.BOLD;
        enum$VALUES[0x83 ^ 0x91] = EnumChatFormatting.STRIKETHROUGH;
        enum$VALUES[0xBD ^ 0xAE] = EnumChatFormatting.UNDERLINE;
        enum$VALUES[0xBE ^ 0xAA] = EnumChatFormatting.ITALIC;
        enum$VALUES[0x81 ^ 0x94] = EnumChatFormatting.RESET;
        ENUM$VALUES = enum$VALUES;
        nameMapping = Maps.newHashMap();
        formattingCodePattern = Pattern.compile(EnumChatFormatting.I[0x9 ^ 0x25] + String.valueOf((char)(161 + 77 - 194 + 123)) + EnumChatFormatting.I[0x5C ^ 0x71]);
        final EnumChatFormatting[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < length) {
            final EnumChatFormatting enumChatFormatting = values[i];
            EnumChatFormatting.nameMapping.put(func_175745_c(enumChatFormatting.name), enumChatFormatting);
            ++i;
        }
    }
    
    public String getFriendlyName() {
        return this.name().toLowerCase();
    }
    
    public boolean isColor() {
        if (!this.fancyStyling && this != EnumChatFormatting.RESET) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isFancyStyling() {
        return this.fancyStyling;
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final char c, final int n2) {
        this(s, n, s2, c, "".length() != 0, n2);
    }
    
    public static EnumChatFormatting getValueByName(final String s) {
        EnumChatFormatting enumChatFormatting;
        if (s == null) {
            enumChatFormatting = null;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            enumChatFormatting = EnumChatFormatting.nameMapping.get(func_175745_c(s));
        }
        return enumChatFormatting;
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static EnumChatFormatting func_175744_a(final int n) {
        if (n < 0) {
            return EnumChatFormatting.RESET;
        }
        final EnumChatFormatting[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < length) {
            final EnumChatFormatting enumChatFormatting = values[i];
            if (enumChatFormatting.getColorIndex() == n) {
                return enumChatFormatting;
            }
            ++i;
        }
        return null;
    }
    
    private static void I() {
        (I = new String[0x2D ^ 0x1F])["".length()] = I("\u000b\u000e\u0014\b\u0007", "IBUKL");
        EnumChatFormatting.I[" ".length()] = I("6\u0007\u0014\"\u0007", "tKUaL");
        EnumChatFormatting.I["  ".length()] = I(")\u000b\u001e\u0019-/\u0006\u0019\u0017", "mJLRr");
        EnumChatFormatting.I["   ".length()] = I("!$<\b\u000b');\u0006", "eenCT");
        EnumChatFormatting.I[0x1D ^ 0x19] = I(")\u00118\u0002.*\u0002/\f?", "mPjIq");
        EnumChatFormatting.I[0x48 ^ 0x4D] = I("\u000f\u0005*1>\f\u0016=?/", "KDxza");
        EnumChatFormatting.I[0x93 ^ 0x95] = I("\u0007\u0014\u0019(,\u0002\u0004\u001e\"", "CUKcs");
        EnumChatFormatting.I[0x9 ^ 0xE] = I(". \n;\u0014+0\r1", "jaXpK");
        EnumChatFormatting.I[0x93 ^ 0x9B] = I("'\u00158<\r1\u0011.", "cTjwR");
        EnumChatFormatting.I[0xB1 ^ 0xB8] = I("\u0016\u000b4>8\u0000\u000f\"", "RJfug");
        EnumChatFormatting.I[0x1 ^ 0xB] = I("\u000b\u0003?#\u001c\u001f\u0017?8\u000f\n", "OBmhC");
        EnumChatFormatting.I[0xD ^ 0x6] = I("\f.\u001f\u0005<\u0018:\u001f\u001e/\r", "HoMNc");
        EnumChatFormatting.I[0xB7 ^ 0xBB] = I("\u000b\u0000\u000f\u0010", "LOCTB");
        EnumChatFormatting.I[0xA8 ^ 0xA5] = I("\u0002\u001a\u0003\u0001", "EUOEe");
        EnumChatFormatting.I[0x9F ^ 0x91] = I("\t$\u00005", "NvAlb");
        EnumChatFormatting.I[0xB2 ^ 0xBD] = I("#\u0005\n!", "dWKxc");
        EnumChatFormatting.I[0x4 ^ 0x14] = I("(\u0011\u0015>\u0017+\u0002\u0006,", "lPGuH");
        EnumChatFormatting.I[0x25 ^ 0x34] = I("<5\u0011)*?&\u0002;", "xtCbu");
        EnumChatFormatting.I[0xF ^ 0x1D] = I("\u0004\u0019\u0012 ", "FUGev");
        EnumChatFormatting.I[0x92 ^ 0x81] = I("\u0012\t\u001c4", "PEIqr");
        EnumChatFormatting.I[0x3 ^ 0x17] = I(".\u0010\u0011\u0002#", "iBTGm");
        EnumChatFormatting.I[0x5C ^ 0x49] = I(")\u0016\b?\u001c", "nDMzR");
        EnumChatFormatting.I[0x51 ^ 0x47] = I("\u0019\"$%", "XsqdP");
        EnumChatFormatting.I[0x20 ^ 0x37] = I("3\u000b9;", "rZlza");
        EnumChatFormatting.I[0x1E ^ 0x6] = I("%\u00125", "wWqsL");
        EnumChatFormatting.I[0x11 ^ 0x8] = I("\u0018*\u0001", "JoEYA");
        EnumChatFormatting.I[0xA5 ^ 0xBF] = I(":%28\u0017)< \"\u0013:)", "vlupC");
        EnumChatFormatting.I[0x9B ^ 0x80] = I(":%%\u00005)<7\u001a1:)", "vlbHa");
        EnumChatFormatting.I[0xE ^ 0x12] = I(".\u0016:*; ", "wSvft");
        EnumChatFormatting.I[0x9A ^ 0x87] = I("=-;\u0002\u001b3", "dhwNT");
        EnumChatFormatting.I[0x31 ^ 0x2F] = I("%\u001a\u0006\u0003\u001f", "rROWZ");
        EnumChatFormatting.I[0xE ^ 0x11] = I("'\u00129\u0015 ", "pZpAe");
        EnumChatFormatting.I[0x14 ^ 0x34] = I("\u001a!?\u0018\u0015\u0016\"-\b\u0002", "UcyMF");
        EnumChatFormatting.I[0x55 ^ 0x74] = I(":\u0015\"\u001f\n6\u00160\u000f\u001d", "uWdJY");
        EnumChatFormatting.I[0x5A ^ 0x78] = I("\u00019\u0006\u0016", "CvJRJ");
        EnumChatFormatting.I[0x16 ^ 0x35] = I(":\u000b*&", "xDfbU");
        EnumChatFormatting.I[0x24 ^ 0x0] = I("'\u0010\u0002\b.1\u0010\u0018\u0013*!\u0003\u0018", "tDPAe");
        EnumChatFormatting.I[0x6 ^ 0x23] = I("\",'\u000b\u000e4,=\u0010\n$?=", "qxuBE");
        EnumChatFormatting.I[0xA6 ^ 0x80] = I("\u001f\u001c\u0002\u000b5\u0006\u001b\b\u000b", "JRFNg");
        EnumChatFormatting.I[0x36 ^ 0x11] = I("0(\"3\u0005)/(3", "effvW");
        EnumChatFormatting.I[0x46 ^ 0x6E] = I("+\u0010\u0007\u001f\u001e!", "bDFSW");
        EnumChatFormatting.I[0x95 ^ 0xBC] = I("\u0003\u0003\u0012:+\t", "JWSvb");
        EnumChatFormatting.I[0x72 ^ 0x58] = I("7'#\u0001\u0010", "ebpDD");
        EnumChatFormatting.I[0x47 ^ 0x6C] = I("\u0003<\u0002\b3", "QyQMg");
        EnumChatFormatting.I[0x9E ^ 0xB2] = I("_o*Q", "wPCxV");
        EnumChatFormatting.I[0x3D ^ 0x10] = I("\u0014fJ[8b\u0010,O6\u001d\u000b", "OVgby");
        EnumChatFormatting.I[0xA2 ^ 0x8C] = I("\u001d\u0004*K#\u001b", "FZKfY");
        EnumChatFormatting.I[0xB7 ^ 0x98] = I("", "keIJy");
        EnumChatFormatting.I[0x34 ^ 0x4] = I("\u00d6", "qqFuX");
        EnumChatFormatting.I[0x7F ^ 0x4E] = I("", "tBXla");
    }
    
    public static String getTextWithoutFormattingCodes(final String s) {
        String replaceAll;
        if (s == null) {
            replaceAll = null;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            replaceAll = EnumChatFormatting.formattingCodePattern.matcher(s).replaceAll(EnumChatFormatting.I[0x35 ^ 0x4]);
        }
        return replaceAll;
    }
}
