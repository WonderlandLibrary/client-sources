package optfine;

public class IntArray
{
    private int position;
    private int[] array;
    private int limit;
    
    public void clear() {
        this.position = "".length();
        this.limit = "".length();
    }
    
    public void position(final int position) {
        this.position = position;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    public IntArray(final int n) {
        this.array = null;
        this.position = "".length();
        this.limit = "".length();
        this.array = new int[n];
    }
    
    public void put(final int[] array) {
        final int length = array.length;
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < length) {
            this.array[this.position] = array[i];
            this.position += " ".length();
            ++i;
        }
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }
    
    public void put(final int n) {
        this.array[this.position] = n;
        this.position += " ".length();
        if (this.limit < this.position) {
            this.limit = this.position;
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int get(final int n) {
        return this.array[n];
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public void put(final int limit, final int n) {
        this.array[limit] = n;
        if (this.limit < limit) {
            this.limit = limit;
        }
    }
    
    public int[] getArray() {
        return this.array;
    }
}
