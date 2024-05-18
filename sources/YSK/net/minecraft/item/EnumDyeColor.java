package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.block.material.*;

public enum EnumDyeColor implements IStringSerializable
{
    YELLOW(EnumDyeColor.I[0xA4 ^ 0xA8], 0x5B ^ 0x5F, 0x7B ^ 0x7F, 0x8D ^ 0x86, EnumDyeColor.I[0x13 ^ 0x1E], EnumDyeColor.I[0x5D ^ 0x53], MapColor.yellowColor, EnumChatFormatting.YELLOW);
    
    private final String name;
    private final int dyeDamage;
    
    GREEN(EnumDyeColor.I[0xB ^ 0x2C], 0x13 ^ 0x1E, 0x76 ^ 0x7B, "  ".length(), EnumDyeColor.I[0x23 ^ 0xB], EnumDyeColor.I[0xAA ^ 0x83], MapColor.greenColor, EnumChatFormatting.DARK_GREEN), 
    BLACK(EnumDyeColor.I[0xB ^ 0x26], 0x9D ^ 0x92, 0x7E ^ 0x71, "".length(), EnumDyeColor.I[0x9D ^ 0xB3], EnumDyeColor.I[0x6 ^ 0x29], MapColor.blackColor, EnumChatFormatting.BLACK);
    
    private final String unlocalizedName;
    private final int meta;
    private final EnumChatFormatting chatColor;
    private static final String[] I;
    
    SILVER(EnumDyeColor.I[0x91 ^ 0x89], 0x2B ^ 0x23, 0x47 ^ 0x4F, 0x98 ^ 0x9F, EnumDyeColor.I[0x4B ^ 0x52], EnumDyeColor.I[0xAA ^ 0xB0], MapColor.silverColor, EnumChatFormatting.GRAY), 
    GRAY(EnumDyeColor.I[0x64 ^ 0x71], 0x17 ^ 0x10, 0x23 ^ 0x24, 0xBC ^ 0xB4, EnumDyeColor.I[0xD6 ^ 0xC0], EnumDyeColor.I[0x1C ^ 0xB], MapColor.grayColor, EnumChatFormatting.DARK_GRAY), 
    LIGHT_BLUE(EnumDyeColor.I[0x4F ^ 0x46], "   ".length(), "   ".length(), 0x99 ^ 0x95, EnumDyeColor.I[0xC9 ^ 0xC3], EnumDyeColor.I[0x80 ^ 0x8B], MapColor.lightBlueColor, EnumChatFormatting.BLUE), 
    CYAN(EnumDyeColor.I[0xB3 ^ 0xA8], 0xF ^ 0x6, 0x8B ^ 0x82, 0x84 ^ 0x82, EnumDyeColor.I[0xD9 ^ 0xC5], EnumDyeColor.I[0x7F ^ 0x62], MapColor.cyanColor, EnumChatFormatting.DARK_AQUA);
    
    private static final EnumDyeColor[] DYE_DMG_LOOKUP;
    private static final EnumDyeColor[] META_LOOKUP;
    
    MAGENTA(EnumDyeColor.I[0x66 ^ 0x60], "  ".length(), "  ".length(), 0xB1 ^ 0xBC, EnumDyeColor.I[0x5D ^ 0x5A], EnumDyeColor.I[0x37 ^ 0x3F], MapColor.magentaColor, EnumChatFormatting.AQUA), 
    WHITE(EnumDyeColor.I["".length()], "".length(), "".length(), 0x93 ^ 0x9C, EnumDyeColor.I[" ".length()], EnumDyeColor.I["  ".length()], MapColor.snowColor, EnumChatFormatting.WHITE), 
    PURPLE(EnumDyeColor.I[0x14 ^ 0xA], 0xCF ^ 0xC5, 0xA6 ^ 0xAC, 0x3F ^ 0x3A, EnumDyeColor.I[0x8A ^ 0x95], EnumDyeColor.I[0x95 ^ 0xB5], MapColor.purpleColor, EnumChatFormatting.DARK_PURPLE), 
    PINK(EnumDyeColor.I[0x75 ^ 0x67], 0xC0 ^ 0xC6, 0x36 ^ 0x30, 0x75 ^ 0x7C, EnumDyeColor.I[0x8E ^ 0x9D], EnumDyeColor.I[0x96 ^ 0x82], MapColor.pinkColor, EnumChatFormatting.LIGHT_PURPLE), 
    ORANGE(EnumDyeColor.I["   ".length()], " ".length(), " ".length(), 0x68 ^ 0x66, EnumDyeColor.I[0x62 ^ 0x66], EnumDyeColor.I[0x42 ^ 0x47], MapColor.adobeColor, EnumChatFormatting.GOLD), 
    BROWN(EnumDyeColor.I[0x5B ^ 0x7F], 0x8F ^ 0x83, 0x1 ^ 0xD, "   ".length(), EnumDyeColor.I[0xA0 ^ 0x85], EnumDyeColor.I[0x5E ^ 0x78], MapColor.brownColor, EnumChatFormatting.GOLD);
    
    private static final EnumDyeColor[] ENUM$VALUES;
    
    RED(EnumDyeColor.I[0x82 ^ 0xA8], 0x7E ^ 0x70, 0x86 ^ 0x88, " ".length(), EnumDyeColor.I[0x2D ^ 0x6], EnumDyeColor.I[0x63 ^ 0x4F], MapColor.redColor, EnumChatFormatting.DARK_RED);
    
    private final MapColor mapColor;
    
    BLUE(EnumDyeColor.I[0xE2 ^ 0xC3], 0x3A ^ 0x31, 0xB6 ^ 0xBD, 0x5D ^ 0x59, EnumDyeColor.I[0xA6 ^ 0x84], EnumDyeColor.I[0x2A ^ 0x9], MapColor.blueColor, EnumChatFormatting.DARK_BLUE), 
    LIME(EnumDyeColor.I[0x78 ^ 0x77], 0x70 ^ 0x75, 0x4F ^ 0x4A, 0x2C ^ 0x26, EnumDyeColor.I[0x22 ^ 0x32], EnumDyeColor.I[0x60 ^ 0x71], MapColor.limeColor, EnumChatFormatting.GREEN);
    
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    private EnumDyeColor(final String s, final int n, final int meta, final int dyeDamage, final String name, final String unlocalizedName, final MapColor mapColor, final EnumChatFormatting chatColor) {
        this.meta = meta;
        this.dyeDamage = dyeDamage;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        this.mapColor = mapColor;
        this.chatColor = chatColor;
    }
    
    @Override
    public String toString() {
        return this.unlocalizedName;
    }
    
    public int getMetadata() {
        return this.meta;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MapColor getMapColor() {
        return this.mapColor;
    }
    
    public static EnumDyeColor byMetadata(int length) {
        if (length < 0 || length >= EnumDyeColor.META_LOOKUP.length) {
            length = "".length();
        }
        return EnumDyeColor.META_LOOKUP[length];
    }
    
    static {
        I();
        final EnumDyeColor[] enum$VALUES = new EnumDyeColor[0x25 ^ 0x35];
        enum$VALUES["".length()] = EnumDyeColor.WHITE;
        enum$VALUES[" ".length()] = EnumDyeColor.ORANGE;
        enum$VALUES["  ".length()] = EnumDyeColor.MAGENTA;
        enum$VALUES["   ".length()] = EnumDyeColor.LIGHT_BLUE;
        enum$VALUES[0x77 ^ 0x73] = EnumDyeColor.YELLOW;
        enum$VALUES[0xC4 ^ 0xC1] = EnumDyeColor.LIME;
        enum$VALUES[0x49 ^ 0x4F] = EnumDyeColor.PINK;
        enum$VALUES[0x76 ^ 0x71] = EnumDyeColor.GRAY;
        enum$VALUES[0x69 ^ 0x61] = EnumDyeColor.SILVER;
        enum$VALUES[0x98 ^ 0x91] = EnumDyeColor.CYAN;
        enum$VALUES[0x97 ^ 0x9D] = EnumDyeColor.PURPLE;
        enum$VALUES[0x63 ^ 0x68] = EnumDyeColor.BLUE;
        enum$VALUES[0xBE ^ 0xB2] = EnumDyeColor.BROWN;
        enum$VALUES[0x6E ^ 0x63] = EnumDyeColor.GREEN;
        enum$VALUES[0x40 ^ 0x4E] = EnumDyeColor.RED;
        enum$VALUES[0x7F ^ 0x70] = EnumDyeColor.BLACK;
        ENUM$VALUES = enum$VALUES;
        META_LOOKUP = new EnumDyeColor[values().length];
        DYE_DMG_LOOKUP = new EnumDyeColor[values().length];
        final EnumDyeColor[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < length) {
            final EnumDyeColor enumDyeColor = values[i];
            EnumDyeColor.META_LOOKUP[enumDyeColor.getMetadata()] = enumDyeColor;
            EnumDyeColor.DYE_DMG_LOOKUP[enumDyeColor.getDyeDamage()] = enumDyeColor;
            ++i;
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public static EnumDyeColor byDyeDamage(int length) {
        if (length < 0 || length >= EnumDyeColor.DYE_DMG_LOOKUP.length) {
            length = "".length();
        }
        return EnumDyeColor.DYE_DMG_LOOKUP[length];
    }
    
    public int getDyeDamage() {
        return this.dyeDamage;
    }
    
    private static void I() {
        (I = new String[0xB9 ^ 0x89])["".length()] = I("/\f\u000f%4", "xDFqq");
        EnumDyeColor.I[" ".length()] = I("#9'\u00056", "TQNqS");
        EnumDyeColor.I["  ".length()] = I("\u0011!\u0010 &", "fIyTC");
        EnumDyeColor.I["   ".length()] = I("7\u0001\u0013\b7=", "xSRFp");
        EnumDyeColor.I[0x3F ^ 0x3B] = I("\r\u001d\u0013\u0002*\u0007", "borlM");
        EnumDyeColor.I[0x93 ^ 0x96] = I("+\n3\r=!", "DxRcZ");
        EnumDyeColor.I[0x10 ^ 0x16] = I(">7%/\u0006'7", "svbjH");
        EnumDyeColor.I[0xB6 ^ 0xB1] = I("/9\u0016<\r69", "BXqYc");
        EnumDyeColor.I[0x26 ^ 0x2E] = I("7*\u0002\u0000\u001a.*", "ZKeet");
        EnumDyeColor.I[0x44 ^ 0x4D] = I("\u0007\u000e$\u0004<\u0014\u0005/\u0019-", "KGcLh");
        EnumDyeColor.I[0x5A ^ 0x50] = I("?\r>\u001d\u0019\f\u00065\u0000\b", "SdYum");
        EnumDyeColor.I[0x98 ^ 0x93] = I("$\u0005\";!\n\u000006", "HlESU");
        EnumDyeColor.I[0xB4 ^ 0xB8] = I("3.(9\u000e=", "jkduA");
        EnumDyeColor.I[0x2D ^ 0x20] = I("\u000b\u0001\u0006\u001c\u0003\u0005", "rdjpl");
        EnumDyeColor.I[0x2A ^ 0x24] = I(")\u001f\b\u0003\u001a'", "Pzdou");
        EnumDyeColor.I[0x65 ^ 0x6A] = I("\u0015?\u001d*", "YvPoc");
        EnumDyeColor.I[0x74 ^ 0x64] = I("\u0004*')", "hCJLF");
        EnumDyeColor.I[0x40 ^ 0x51] = I("\n/4,", "fFYID");
        EnumDyeColor.I[0x60 ^ 0x72] = I("\u001e\u0003&\u0003", "NJhHe");
        EnumDyeColor.I[0x2B ^ 0x38] = I(" \u0006>\u0002", "PoPiV");
        EnumDyeColor.I[0x3 ^ 0x17] = I("\u0018(\u0018 ", "hAvKh");
        EnumDyeColor.I[0x7B ^ 0x6E] = I(">#\n\f", "yqKUl");
        EnumDyeColor.I[0x74 ^ 0x62] = I("\u000f0$\u001c", "hBEeG");
        EnumDyeColor.I[0x96 ^ 0x81] = I("\n\u001e\f\u000e", "mlmwW");
        EnumDyeColor.I[0x41 ^ 0x59] = I("68\u0014\u001017", "eqXFt");
        EnumDyeColor.I[0x2E ^ 0x37] = I("\u001b\u000e=\u0014=\u001a", "hgQbX");
        EnumDyeColor.I[0x96 ^ 0x8C] = I("+\u001c\u000e/\"*", "XubYG");
        EnumDyeColor.I[0x7E ^ 0x65] = I("3\u001b 8", "pBavY");
        EnumDyeColor.I[0x7D ^ 0x61] = I("\u0005+7=", "fRVSg");
        EnumDyeColor.I[0x4C ^ 0x51] = I("7\u0012\"<", "TkCRA");
        EnumDyeColor.I[0x7D ^ 0x63] = I(":8:\u0019?/", "jmhIs");
        EnumDyeColor.I[0x23 ^ 0x3C] = I("8/;\u001a\u001e-", "HZIjr");
        EnumDyeColor.I[0xA4 ^ 0x84] = I(":1\u0018;\u0019/", "JDjKu");
        EnumDyeColor.I[0x81 ^ 0xA0] = I(".\u0007\u0011\u0017", "lKDRT");
        EnumDyeColor.I[0x56 ^ 0x74] = I("\u00114?\b", "sXJmo");
        EnumDyeColor.I[0xA2 ^ 0x81] = I("\r\u0004\u000f!", "ohzDn");
        EnumDyeColor.I[0x8D ^ 0xA9] = I("\b\u0003:\u000e\u001e", "JQuYP");
        EnumDyeColor.I[0x34 ^ 0x11] = I("\u00059\"% ", "gKMRN");
        EnumDyeColor.I[0x37 ^ 0x11] = I("\u00000#\u0005!", "bBLrO");
        EnumDyeColor.I[0xAE ^ 0x89] = I("\u0014< \u0003\"", "SneFl");
        EnumDyeColor.I[0x9C ^ 0xB4] = I("\u00067?\u001c\u001e", "aEZyp");
        EnumDyeColor.I[0x4C ^ 0x65] = I("5\u00171\u0016\u0005", "ReTsk");
        EnumDyeColor.I[0x3E ^ 0x14] = I("%(7", "wmsbl");
        EnumDyeColor.I[0x2E ^ 0x5] = I("75!", "EPEjC");
        EnumDyeColor.I[0xB9 ^ 0x95] = I("\u0010\f\u0003", "bigin");
        EnumDyeColor.I[0xA4 ^ 0x89] = I("\n(\u000f\u0010\u0007", "HdNSL");
        EnumDyeColor.I[0x4 ^ 0x2A] = I(",\u000e\u0010\u00053", "NbqfX");
        EnumDyeColor.I[0x5 ^ 0x2A] = I("0&;\u0019\n", "RJZza");
    }
}
