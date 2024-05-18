package net.minecraft.world.chunk;

public class NibbleArray
{
    private static final String[] I;
    private final byte[] data;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("0<\u000f'\u001f==\u0018+\u0018\u0016\u0015\b;\u0015\n'Z:\u001c\u001c!\u0016-T\u00111Z{DGlZ+\r\u00071\ti\u001a\u001c @i", "sTzIt");
    }
    
    private int getNibbleIndex(final int n) {
        return n >> " ".length();
    }
    
    private int getCoordinateIndex(final int n, final int n2, final int n3) {
        return n2 << (0x79 ^ 0x71) | n3 << (0x82 ^ 0x86) | n;
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NibbleArray(final byte[] data) {
        this.data = data;
        if (data.length != 187 + 531 + 293 + 1037) {
            throw new IllegalArgumentException(NibbleArray.I["".length()] + data.length);
        }
    }
    
    private boolean isLowerNibble(final int n) {
        if ((n & " ".length()) == 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int get(final int n, final int n2, final int n3) {
        return this.getFromIndex(this.getCoordinateIndex(n, n2, n3));
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.setIndex(this.getCoordinateIndex(n, n2, n3), n4);
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public int getFromIndex(final int n) {
        final int nibbleIndex = this.getNibbleIndex(n);
        int n2;
        if (this.isLowerNibble(n)) {
            n2 = (this.data[nibbleIndex] & (0x5F ^ 0x50));
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = (this.data[nibbleIndex] >> (0x4E ^ 0x4A) & (0x95 ^ 0x9A));
        }
        return n2;
    }
    
    public NibbleArray() {
        this.data = new byte[1545 + 1739 - 2200 + 964];
    }
    
    static {
        I();
    }
    
    public void setIndex(final int n, final int n2) {
        final int nibbleIndex = this.getNibbleIndex(n);
        if (this.isLowerNibble(n)) {
            this.data[nibbleIndex] = (byte)((this.data[nibbleIndex] & 42 + 37 + 51 + 110) | (n2 & (0x7E ^ 0x71)));
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            this.data[nibbleIndex] = (byte)((this.data[nibbleIndex] & (0xBC ^ 0xB3)) | (n2 & (0xCC ^ 0xC3)) << (0x73 ^ 0x77));
        }
    }
}
