package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.*;

public class VertexFormatElement
{
    private static final Logger LOGGER;
    private int index;
    private static final String[] I;
    private int elementCount;
    private final EnumUsage usage;
    private final EnumType type;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final VertexFormatElement vertexFormatElement = (VertexFormatElement)o;
            int n;
            if (this.elementCount != vertexFormatElement.elementCount) {
                n = "".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else if (this.index != vertexFormatElement.index) {
                n = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else if (this.type != vertexFormatElement.type) {
                n = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (this.usage == vertexFormatElement.usage) {
                n = " ".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        return "".length() != 0;
    }
    
    public final EnumUsage getUsage() {
        return this.usage;
    }
    
    @Override
    public int hashCode() {
        return (0x55 ^ 0x4A) * ((0x87 ^ 0x98) * ((0x1D ^ 0x2) * this.type.hashCode() + this.usage.hashCode()) + this.index) + this.elementCount;
    }
    
    public final EnumType getType() {
        return this.type;
    }
    
    public VertexFormatElement(final int index, final EnumType type, final EnumUsage usage, final int elementCount) {
        if (!this.func_177372_a(index, usage)) {
            VertexFormatElement.LOGGER.warn(VertexFormatElement.I["".length()]);
            this.usage = EnumUsage.UV;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            this.usage = usage;
        }
        this.type = type;
        this.index = index;
        this.elementCount = elementCount;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.elementCount) + VertexFormatElement.I[" ".length()] + this.usage.getDisplayName() + VertexFormatElement.I["  ".length()] + this.type.getDisplayName();
    }
    
    public final int getIndex() {
        return this.index;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0004\u001b:\u001f\u00119\u00023K\u000e,\u001c\"\u000e\u0000i\u000b:\u000e\u0015,\u0000\"\u0018X&\bv\u001f\u0010,N%\n\u0015,N\"\u0012\b,N9\u001f\u0010,\u001cv\u001f\u0010(\u0000v>.:N7\u0019\u001di\u00009\u001fX:\u001b&\u001b\u0017;\u001a3\u000fVi(9\u0019\u001b \u00001K\f0\u001e3K\f&N\u0003=V", "InVkx");
        VertexFormatElement.I[" ".length()] = I("i", "EdBey");
        VertexFormatElement.I["  ".length()] = I("N", "bFOIp");
    }
    
    public final boolean isPositionElement() {
        if (this.usage == EnumUsage.POSITION) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private final boolean func_177372_a(final int n, final EnumUsage enumUsage) {
        if (n != 0 && enumUsage != EnumUsage.UV) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public final int getElementCount() {
        return this.elementCount;
    }
    
    public final int getSize() {
        return this.type.getSize() * this.elementCount;
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    public enum EnumUsage
    {
        private final String displayName;
        
        COLOR(EnumUsage.I[0xC4 ^ 0xC0], "  ".length(), EnumUsage.I[0x7D ^ 0x78]), 
        NORMAL(EnumUsage.I["  ".length()], " ".length(), EnumUsage.I["   ".length()]), 
        MATRIX(EnumUsage.I[0x18 ^ 0x10], 0x96 ^ 0x92, EnumUsage.I[0x5 ^ 0xC]), 
        POSITION(EnumUsage.I["".length()], "".length(), EnumUsage.I[" ".length()]);
        
        private static final String[] I;
        
        BLEND_WEIGHT(EnumUsage.I[0x8B ^ 0x81], 0x8D ^ 0x88, EnumUsage.I[0x47 ^ 0x4C]);
        
        private static final EnumUsage[] ENUM$VALUES;
        
        PADDING(EnumUsage.I[0x1D ^ 0x11], 0x13 ^ 0x15, EnumUsage.I[0x53 ^ 0x5E]), 
        UV(EnumUsage.I[0x21 ^ 0x27], "   ".length(), EnumUsage.I[0x6C ^ 0x6B]);
        
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
                if (2 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final EnumUsage[] enum$VALUES = new EnumUsage[0x53 ^ 0x54];
            enum$VALUES["".length()] = EnumUsage.POSITION;
            enum$VALUES[" ".length()] = EnumUsage.NORMAL;
            enum$VALUES["  ".length()] = EnumUsage.COLOR;
            enum$VALUES["   ".length()] = EnumUsage.UV;
            enum$VALUES[0xA5 ^ 0xA1] = EnumUsage.MATRIX;
            enum$VALUES[0x14 ^ 0x11] = EnumUsage.BLEND_WEIGHT;
            enum$VALUES[0x77 ^ 0x71] = EnumUsage.PADDING;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x1B ^ 0x15])["".length()] = I("\u001f\u0000\u001e\u0011\u0005\u0006\u0000\u0003", "OOMXQ");
            EnumUsage.I[" ".length()] = I("\u001f\u0003>\u001c<&\u0003#", "OlMuH");
            EnumUsage.I["  ".length()] = I("!\u0019\u001e*\f#", "oVLgM");
            EnumUsage.I["   ".length()] = I("\u0003\u0003\u0006\b\r!", "Mltel");
            EnumUsage.I[0x4A ^ 0x4E] = I("\u001b85,7", "Xwyce");
            EnumUsage.I[0xB4 ^ 0xB1] = I("/\u0011;\r7\u0001T\n\u0016>\u0016\u0006", "ytIyR");
            EnumUsage.I[0xC ^ 0xA] = I("\u0019\u0002", "LTJmg");
            EnumUsage.I[0x63 ^ 0x64] = I(":8", "onwIL");
            EnumUsage.I[0x45 ^ 0x4D] = I("5\u0019 (\u0000 ", "xXtzI");
            EnumUsage.I[0x82 ^ 0x8B] = I("\u0007(\u0007/E\b&\u001d8\f=", "EGiJe");
            EnumUsage.I[0x10 ^ 0x1A] = I("'=\u001d/\u001e:&\u001d(\u001d-%", "eqXaZ");
            EnumUsage.I[0x10 ^ 0x1B] = I(",\u0018$\"\u000eN#$%\r\u0006\u0000", "ntALj");
            EnumUsage.I[0x5E ^ 0x52] = I("69\u0000,\u0001(?", "fxDhH");
            EnumUsage.I[0x1C ^ 0x11] = I("8(\u000e\t\u0010\u0006.", "hIjmy");
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        private EnumUsage(final String s, final int n, final String displayName) {
            this.displayName = displayName;
        }
    }
    
    public enum EnumType
    {
        SHORT(EnumType.I[0x28 ^ 0x20], 0x8C ^ 0x88, "  ".length(), EnumType.I[0x32 ^ 0x3B], 335 + 3898 - 3455 + 4344);
        
        private final int size;
        private static final String[] I;
        
        INT(EnumType.I[0x9F ^ 0x93], 0x71 ^ 0x77, 0x79 ^ 0x7D, EnumType.I[0x83 ^ 0x8E], 2912 + 1840 - 4423 + 4795), 
        UINT(EnumType.I[0x8F ^ 0x85], 0x4F ^ 0x4A, 0xA ^ 0xE, EnumType.I[0xA0 ^ 0xAB], 470 + 4602 - 4135 + 4188), 
        UBYTE(EnumType.I["  ".length()], " ".length(), " ".length(), EnumType.I["   ".length()], 4469 + 3685 - 5450 + 2417);
        
        private final String displayName;
        
        USHORT(EnumType.I[0xE ^ 0x8], "   ".length(), "  ".length(), EnumType.I[0x3E ^ 0x39], 4814 + 3519 - 3459 + 249);
        
        private final int glConstant;
        private static final EnumType[] ENUM$VALUES;
        
        FLOAT(EnumType.I["".length()], "".length(), 0x88 ^ 0x8C, EnumType.I[" ".length()], 4329 + 4058 - 3905 + 644), 
        BYTE(EnumType.I[0xAC ^ 0xA8], "  ".length(), " ".length(), EnumType.I[0x58 ^ 0x5D], 929 + 643 + 2971 + 577);
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public int getGlConstant() {
            return this.glConstant;
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
                if (1 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x15 ^ 0x1B])["".length()] = I("\b$\u001e&:", "NhQgn");
            EnumType.I[" ".length()] = I("\u000f+>\u0004%", "IGQeQ");
            EnumType.I["  ".length()] = I("0&!$\u0007", "edxpB");
            EnumType.I["   ".length()] = I(";\u0007\u0015\u001e0\u0000\f\u0002W\u0015\u0017\u001d\u0003", "nifwW");
            EnumType.I[0x2F ^ 0x2B] = I(",\u000f\u001c\u000b", "nVHNx");
            EnumType.I[0x42 ^ 0x47] = I("\u001b3\u0000(", "YJtMy");
            EnumType.I[0x12 ^ 0x14] = I("\u001a%\u000b!\u0002\u001b", "OvCnP");
            EnumType.I[0x84 ^ 0x83] = I("&\b\u001c\u0002\u001d\u001d\u0003\u000bK)\u001b\t\u001d\u001f", "sfokz");
            EnumType.I[0x29 ^ 0x21] = I("\u001d\u0004(>\u0007", "NLglS");
            EnumType.I[0x4D ^ 0x44] = I("%\f\u001c&\u001c", "vdsTh");
            EnumType.I[0x14 ^ 0x1E] = I("\u000f\u001a*8", "ZSdlr");
            EnumType.I[0x99 ^ 0x92] = I("\u001e\u00188,)%\u0013/e\u0007%\u0002", "KvKEN");
            EnumType.I[0x30 ^ 0x3C] = I("\u001a6%", "SxqTd");
            EnumType.I[0xB3 ^ 0xBE] = I("\u000b\".", "BLZnl");
        }
        
        public int getSize() {
            return this.size;
        }
        
        private EnumType(final String s, final int n, final int size, final String displayName, final int glConstant) {
            this.size = size;
            this.displayName = displayName;
            this.glConstant = glConstant;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0x67 ^ 0x60];
            enum$VALUES["".length()] = EnumType.FLOAT;
            enum$VALUES[" ".length()] = EnumType.UBYTE;
            enum$VALUES["  ".length()] = EnumType.BYTE;
            enum$VALUES["   ".length()] = EnumType.USHORT;
            enum$VALUES[0x75 ^ 0x71] = EnumType.SHORT;
            enum$VALUES[0x79 ^ 0x7C] = EnumType.UINT;
            enum$VALUES[0x10 ^ 0x16] = EnumType.INT;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
