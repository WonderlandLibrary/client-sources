package net.minecraft.world.chunk.storage;

public class NibbleArrayReader
{
    private final int depthBits;
    public final byte[] data;
    private final int depthBitsPlusFour;
    
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int get(final int n, final int n2, final int n3) {
        final int n4 = n << this.depthBitsPlusFour | n3 << this.depthBits | n2;
        final int n5 = n4 >> " ".length();
        int n6;
        if ((n4 & " ".length()) == 0x0) {
            n6 = (this.data[n5] & (0x9E ^ 0x91));
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            n6 = (this.data[n5] >> (0x33 ^ 0x37) & (0x4D ^ 0x42));
        }
        return n6;
    }
    
    public NibbleArrayReader(final byte[] data, final int depthBits) {
        this.data = data;
        this.depthBits = depthBits;
        this.depthBitsPlusFour = depthBits + (0xB9 ^ 0xBD);
    }
}
