package net.minecraft.world.gen.layer;

public class GenLayerEdge extends GenLayer
{
    private final Mode field_151627_c;
    private static int[] $SWITCH_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode;
    
    private int[] getIntsCoolWarm(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - " ".length();
        final int n6 = n2 - " ".length();
        final int n7 = " ".length() + n3 + " ".length();
        final int[] ints = this.parent.getInts(n5, n6, n7, " ".length() + n4 + " ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                int length = ints[j + " ".length() + (i + " ".length()) * n7];
                if (length == " ".length()) {
                    final int n8 = ints[j + " ".length() + (i + " ".length() - " ".length()) * n7];
                    final int n9 = ints[j + " ".length() + " ".length() + (i + " ".length()) * n7];
                    final int n10 = ints[j + " ".length() - " ".length() + (i + " ".length()) * n7];
                    final int n11 = ints[j + " ".length() + (i + " ".length() + " ".length()) * n7];
                    int n12;
                    if (n8 != "   ".length() && n9 != "   ".length() && n10 != "   ".length() && n11 != "   ".length()) {
                        n12 = "".length();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        n12 = " ".length();
                    }
                    final int n13 = n12;
                    int n14;
                    if (n8 != (0x14 ^ 0x10) && n9 != (0x2E ^ 0x2A) && n10 != (0x7E ^ 0x7A) && n11 != (0x66 ^ 0x62)) {
                        n14 = "".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        n14 = " ".length();
                    }
                    final int n15 = n14;
                    if (n13 != 0 || n15 != 0) {
                        length = "  ".length();
                    }
                }
                intCache[j + i * n3] = length;
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode() {
        final int[] $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode = GenLayerEdge.$SWITCH_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode;
        if ($switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode != null) {
            return $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode;
        }
        final int[] $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode2 = new int[Mode.values().length];
        try {
            $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode2[Mode.COOL_WARM.ordinal()] = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode2[Mode.HEAT_ICE.ordinal()] = "  ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode2[Mode.SPECIAL.ordinal()] = "   ".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return GenLayerEdge.$SWITCH_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode = $switch_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode2;
    }
    
    public GenLayerEdge(final long n, final GenLayer parent, final Mode field_151627_c) {
        super(n);
        this.parent = parent;
        this.field_151627_c = field_151627_c;
    }
    
    private int[] getIntsSpecial(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                int n5 = ints[j + i * n3];
                if (n5 != 0 && this.nextInt(0xCC ^ 0xC1) == 0) {
                    n5 |= (" ".length() + this.nextInt(0x4E ^ 0x41) << (0x98 ^ 0x90) & 3182 + 1733 - 2675 + 1600);
                }
                intCache[j + i * n3] = n5;
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        switch ($SWITCH_TABLE$net$minecraft$world$gen$layer$GenLayerEdge$Mode()[this.field_151627_c.ordinal()]) {
            default: {
                return this.getIntsCoolWarm(n, n2, n3, n4);
            }
            case 2: {
                return this.getIntsHeatIce(n, n2, n3, n4);
            }
            case 3: {
                return this.getIntsSpecial(n, n2, n3, n4);
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
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private int[] getIntsHeatIce(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - " ".length();
        final int n6 = n2 - " ".length();
        final int n7 = " ".length() + n3 + " ".length();
        final int[] ints = this.parent.getInts(n5, n6, n7, " ".length() + n4 + " ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (j < n3) {
                int length = ints[j + " ".length() + (i + " ".length()) * n7];
                if (length == (0x30 ^ 0x34)) {
                    final int n8 = ints[j + " ".length() + (i + " ".length() - " ".length()) * n7];
                    final int n9 = ints[j + " ".length() + " ".length() + (i + " ".length()) * n7];
                    final int n10 = ints[j + " ".length() - " ".length() + (i + " ".length()) * n7];
                    final int n11 = ints[j + " ".length() + (i + " ".length() + " ".length()) * n7];
                    int n12;
                    if (n8 != "  ".length() && n9 != "  ".length() && n10 != "  ".length() && n11 != "  ".length()) {
                        n12 = "".length();
                        "".length();
                        if (4 < 3) {
                            throw null;
                        }
                    }
                    else {
                        n12 = " ".length();
                    }
                    final int n13 = n12;
                    int n14;
                    if (n8 != " ".length() && n9 != " ".length() && n10 != " ".length() && n11 != " ".length()) {
                        n14 = "".length();
                        "".length();
                        if (0 == 4) {
                            throw null;
                        }
                    }
                    else {
                        n14 = " ".length();
                    }
                    if (n14 != 0 || n13 != 0) {
                        length = "   ".length();
                    }
                }
                intCache[j + i * n3] = length;
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    public enum Mode
    {
        COOL_WARM(Mode.I["".length()], "".length()), 
        HEAT_ICE(Mode.I[" ".length()], " ".length()), 
        SPECIAL(Mode.I["  ".length()], "  ".length());
        
        private static final Mode[] ENUM$VALUES;
        private static final String[] I;
        
        static {
            I();
            final Mode[] enum$VALUES = new Mode["   ".length()];
            enum$VALUES["".length()] = Mode.COOL_WARM;
            enum$VALUES[" ".length()] = Mode.HEAT_ICE;
            enum$VALUES["  ".length()] = Mode.SPECIAL;
            ENUM$VALUES = enum$VALUES;
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
                if (4 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u0014\u00068 1\u0000\b%!", "WIwln");
            Mode.I[" ".length()] = I("'4&\u0004/&2\"", "oqgPp");
            Mode.I["  ".length()] = I("\u001c:\u0004\u000f=\u000e&", "OjALt");
        }
        
        private Mode(final String s, final int n) {
        }
    }
}
