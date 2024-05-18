package optfine;

public class RangeListInt
{
    private static final String[] I;
    private RangeInt[] ranges;
    
    public RangeListInt() {
        this.ranges = new RangeInt["".length()];
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(".", "uqdLb");
        RangeListInt.I[" ".length()] = I("Fk", "jKVHL");
        RangeListInt.I["  ".length()] = I("\u000f", "RegUL");
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(RangeListInt.I["".length()]);
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < this.ranges.length) {
            final RangeInt rangeInt = this.ranges[i];
            if (i > 0) {
                sb.append(RangeListInt.I[" ".length()]);
            }
            sb.append(rangeInt.toString());
            ++i;
        }
        sb.append(RangeListInt.I["  ".length()]);
        return sb.toString();
    }
    
    public void addRange(final RangeInt rangeInt) {
        this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, rangeInt);
    }
    
    public boolean isInRange(final int n) {
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < this.ranges.length) {
            if (this.ranges[i].isInRange(n)) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
}
