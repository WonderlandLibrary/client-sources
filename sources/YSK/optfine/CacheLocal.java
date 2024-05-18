package optfine;

public class CacheLocal
{
    private int offsetY;
    private int offsetZ;
    private int[][][] cache;
    private int[] lastZs;
    private int maxZ;
    private int lastDz;
    private int maxY;
    private int offsetX;
    private int maxX;
    
    public void setOffset(final int offsetX, final int offsetY, final int offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.resetCache();
    }
    
    public void setLast(final int n) {
        try {
            this.lastZs[this.lastDz] = n;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void resetCache() {
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < this.maxX) {
            final int[][] array = this.cache[i];
            int j = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (j < this.maxY) {
                final int[] array2 = array[j];
                int k = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (k < this.maxZ) {
                    array2[k] = -" ".length();
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public CacheLocal(final int maxX, final int maxY, final int maxZ) {
        this.maxX = (0x8E ^ 0x9C);
        this.maxY = 69 + 108 - 64 + 15;
        this.maxZ = (0x4D ^ 0x5F);
        this.offsetX = "".length();
        this.offsetY = "".length();
        this.offsetZ = "".length();
        this.cache = null;
        this.lastZs = null;
        this.lastDz = "".length();
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.cache = new int[maxX][maxY][maxZ];
        this.resetCache();
    }
    
    public int get(final int n, final int n2, final int n3) {
        try {
            this.lastZs = this.cache[n - this.offsetX][n2 - this.offsetY];
            this.lastDz = n3 - this.offsetZ;
            return this.lastZs[this.lastDz];
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return -" ".length();
        }
    }
}
