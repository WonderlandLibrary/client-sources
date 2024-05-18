package net.minecraft.util;

public class Tuple<A, B>
{
    private B b;
    private A a;
    
    public Tuple(final A a, final B b) {
        this.a = a;
        this.b = b;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public A getFirst() {
        return this.a;
    }
    
    public B getSecond() {
        return this.b;
    }
}
