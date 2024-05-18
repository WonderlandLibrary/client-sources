package optfine;

public class CacheLocalByte
{
    private int maxY;
    private int lastDz;
    private int maxX;
    private int offsetY;
    private int offsetZ;
    private byte[] lastZs;
    private int offsetX;
    private int maxZ;
    private byte[][][] cache;
    
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setLast(final byte b) {
        try {
            this.lastZs[this.lastDz] = b;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setOffset(final int offsetX, final int offsetY, final int offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.resetCache();
    }
    
    public void resetCache() {
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < this.maxX) {
            final byte[][] array = this.cache[i];
            int j = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (j < this.maxY) {
                final byte[] array2 = array[j];
                int k = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (k < this.maxZ) {
                    array2[k] = (byte)(-" ".length());
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public byte get(final int n, final int n2, final int n3) {
        try {
            this.lastZs = this.cache[n - this.offsetX][n2 - this.offsetY];
            this.lastDz = n3 - this.offsetZ;
            return this.lastZs[this.lastDz];
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return (byte)(-" ".length());
        }
    }
    
    public CacheLocalByte(final int maxX, final int maxY, final int maxZ) {
        this.maxX = (0x70 ^ 0x62);
        this.maxY = 34 + 103 - 97 + 88;
        this.maxZ = (0x97 ^ 0x85);
        this.offsetX = "".length();
        this.offsetY = "".length();
        this.offsetZ = "".length();
        this.cache = null;
        this.lastZs = null;
        this.lastDz = "".length();
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.cache = new byte[maxX][maxY][maxZ];
        this.resetCache();
    }
}
