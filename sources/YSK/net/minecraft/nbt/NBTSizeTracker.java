package net.minecraft.nbt;

public class NBTSizeTracker
{
    private static final String[] I;
    private final long max;
    public static final NBTSizeTracker INFINITE;
    private long read;
    
    public NBTSizeTracker(final long max) {
        this.max = max;
    }
    
    public void read(final long n) {
        this.read += n / 8L;
        if (this.read > this.max) {
            throw new RuntimeException(NBTSizeTracker.I["".length()] + this.read + NBTSizeTracker.I[" ".length()] + this.max);
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(".\u001b \n\tZ\u001d&O\u001f\u001f\b-O#8=i\u001b\f\u001dI=\u0007\f\u000eI>\u000e\u001eZ\u001d&\u0000M\u0018\u0000.TM\u000e\u001b \n\tZ\u001d&O\f\u0016\u0005&\f\f\u000e\fsO", "ziIom");
        NBTSizeTracker.I[" ".length()] = I("\u0018?!\u0013=Z1=\u0013<\u001ff8\u00176Z'9\u001a!\r#1Ln", "zFUvN");
    }
    
    static {
        I();
        INFINITE = new NBTSizeTracker() {
            @Override
            public void read(final long n) {
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
                    if (0 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
}
