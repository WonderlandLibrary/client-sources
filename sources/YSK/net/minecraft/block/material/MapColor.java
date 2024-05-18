package net.minecraft.block.material;

public class MapColor
{
    public static final MapColor adobeColor;
    public static final MapColor brownColor;
    public static final MapColor iceColor;
    public static final MapColor clothColor;
    public static final MapColor magentaColor;
    public static final MapColor sandColor;
    public static final MapColor goldColor;
    public static final MapColor stoneColor;
    public static final MapColor snowColor;
    public static final MapColor yellowColor;
    public static final MapColor redColor;
    public static final MapColor ironColor;
    public static final MapColor diamondColor;
    public static final MapColor blackColor;
    public static final MapColor grassColor;
    public static final MapColor greenColor;
    public static final MapColor woodColor;
    public final int colorValue;
    public static final MapColor netherrackColor;
    private static final String[] I;
    public static final MapColor emeraldColor;
    public static final MapColor quartzColor;
    public static final MapColor lightBlueColor;
    public static final MapColor blueColor;
    public static final MapColor purpleColor;
    public static final MapColor pinkColor;
    public static final MapColor obsidianColor;
    public static final MapColor airColor;
    public static final MapColor[] mapColorArray;
    public static final MapColor clayColor;
    public static final MapColor waterColor;
    public static final MapColor dirtColor;
    public static final MapColor cyanColor;
    public static final MapColor grayColor;
    public static final MapColor lapisColor;
    public static final MapColor foliageColor;
    public static final MapColor silverColor;
    public final int colorIndex;
    public static final MapColor tntColor;
    public static final MapColor limeColor;
    
    private MapColor(final int colorIndex, final int colorValue) {
        if (colorIndex < 0 || colorIndex > (0xFA ^ 0xC5)) {
            throw new IndexOutOfBoundsException(MapColor.I["".length()]);
        }
        this.colorIndex = colorIndex;
        this.colorValue = colorValue;
        MapColor.mapColorArray[colorIndex] = this;
        "".length();
        if (false) {
            throw null;
        }
    }
    
    public int func_151643_b(final int n) {
        int n2 = 144 + 98 - 116 + 94;
        if (n == "   ".length()) {
            n2 = 9 + 84 - 8 + 50;
        }
        if (n == "  ".length()) {
            n2 = 62 + 220 - 238 + 211;
        }
        if (n == " ".length()) {
            n2 = 147 + 142 - 256 + 187;
        }
        if (n == 0) {
            n2 = 85 + 153 - 225 + 167;
        }
        return -(9313293 + 12084817 - 13186330 + 8565436) | (this.colorValue >> (0x34 ^ 0x24) & 57 + 95 - 27 + 130) * n2 / (114 + 164 - 46 + 23) << (0x5B ^ 0x4B) | (this.colorValue >> (0x48 ^ 0x40) & 244 + 213 - 303 + 101) * n2 / (77 + 48 - 9 + 139) << (0x3D ^ 0x35) | (this.colorValue & 127 + 105 - 13 + 36) * n2 / (209 + 146 - 314 + 214);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("<\b7U,\u001e\u0005(\u0000=Q \u0003U\"\u0004\u001a3U-\u0014I%\u0010;\u0006\f\"\u001boAI&\u001b+Q_tUg\u0018\u0007$\u0019:\u0002\u00001\u0010f", "qiGuO");
    }
    
    static {
        I();
        mapColorArray = new MapColor[0x2B ^ 0x6B];
        airColor = new MapColor("".length(), "".length());
        grassColor = new MapColor(" ".length(), 5515929 + 6729405 - 7156094 + 3279456);
        sandColor = new MapColor("  ".length(), 12881867 + 246383 + 1097813 + 2021140);
        clothColor = new MapColor("   ".length(), 7516221 + 12056100 - 8580331 + 2100817);
        tntColor = new MapColor(0x15 ^ 0x11, 8483459 + 12195592 - 20511082 + 16543711);
        iceColor = new MapColor(0x3E ^ 0x3B, 10304091 + 10347277 - 17156694 + 7032301);
        ironColor = new MapColor(0x32 ^ 0x34, 7602613 + 2332909 - 7944963 + 8996872);
        foliageColor = new MapColor(0xA8 ^ 0xAF, 13935 + 11283 - 2492 + 9018);
        snowColor = new MapColor(0x6C ^ 0x64, 15252691 + 6428069 - 6453811 + 1550266);
        clayColor = new MapColor(0x75 ^ 0x7C, 422715 + 9426213 - 5302328 + 6244496);
        dirtColor = new MapColor(0x3A ^ 0x30, 3027721 + 1263689 + 1454904 + 4177603);
        stoneColor = new MapColor(0x60 ^ 0x6B, 2696631 + 3667457 - 5675266 + 6679994);
        waterColor = new MapColor(0x35 ^ 0x39, 2369263 + 48387 + 320430 + 1472863);
        woodColor = new MapColor(0x12 ^ 0x1F, 1028517 + 4931185 + 1686421 + 1756061);
        quartzColor = new MapColor(0x47 ^ 0x49, 2390838 + 7408636 + 3812112 + 3164851);
        adobeColor = new MapColor(0x5D ^ 0x52, 12164272 + 11321694 - 20897841 + 11600214);
        magentaColor = new MapColor(0x66 ^ 0x76, 5998454 + 10999824 - 8690133 + 3376935);
        lightBlueColor = new MapColor(0x1D ^ 0xC, 1135847 + 2460044 - 810176 + 3938341);
        yellowColor = new MapColor(0x2D ^ 0x3F, 5203965 + 13177996 - 11855745 + 8540203);
        limeColor = new MapColor(0x27 ^ 0x34, 2059467 + 3892809 - 2609369 + 5032414);
        pinkColor = new MapColor(0x71 ^ 0x65, 6951928 + 8096958 - 9432607 + 10276110);
        grayColor = new MapColor(0x99 ^ 0x8C, 4644277 + 2334547 - 3117737 + 1139181);
        silverColor = new MapColor(0x4D ^ 0x5B, 9945455 + 7053886 - 9760354 + 2827342);
        cyanColor = new MapColor(0x7 ^ 0x10, 3346500 + 316539 - 3644032 + 4994394);
        purpleColor = new MapColor(0x1F ^ 0x7, 5570631 + 6742157 - 10801128 + 6827718);
        blueColor = new MapColor(0x56 ^ 0x4F, 1993906 + 2688039 - 2494142 + 1174167);
        brownColor = new MapColor(0x4C ^ 0x56, 1396475 + 1918879 + 2900212 + 488613);
        greenColor = new MapColor(0x53 ^ 0x48, 766619 + 2157738 - 1629234 + 5422112);
        redColor = new MapColor(0x8F ^ 0x93, 2788336 + 8880538 - 11656969 + 10028210);
        blackColor = new MapColor(0xB ^ 0x16, 234288 + 313308 - 132508 + 1229737);
        goldColor = new MapColor(0x36 ^ 0x28, 14188606 + 9568094 - 12904540 + 5592845);
        diamondColor = new MapColor(0xBB ^ 0xA4, 1416133 + 1095034 - 911051 + 4485473);
        lapisColor = new MapColor(0xB2 ^ 0x92, 4366986 + 3763028 - 5860741 + 2613414);
        emeraldColor = new MapColor(0xB0 ^ 0x91, 40191 + 55487 - 67514 + 27446);
        obsidianColor = new MapColor(0x5B ^ 0x79, 6647978 + 1484113 - 6137096 + 6481214);
        netherrackColor = new MapColor(0x8E ^ 0xAD, 5309748 + 1891414 - 7083518 + 7222900);
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
