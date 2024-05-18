package optfine;

public class RangeInt
{
    private int min;
    private static final String[] I;
    private int max;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\n*=LR", "gCSvr");
        RangeInt.I[" ".length()] = I("xk927nk", "TKTSO");
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RangeInt(final int n, final int n2) {
        this.min = Math.min(n, n2);
        this.max = Math.max(n, n2);
    }
    
    @Override
    public String toString() {
        return RangeInt.I["".length()] + this.min + RangeInt.I[" ".length()] + this.max;
    }
    
    public boolean isInRange(final int n) {
        int n2;
        if (n < this.min) {
            n2 = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (n <= this.max) {
            n2 = " ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    static {
        I();
    }
}
