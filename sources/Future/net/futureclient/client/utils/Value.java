package net.futureclient.client.utils;

public class Value<T>
{
    public T D;
    private String[] k;
    
    public Value(final T d, final String... k) {
        super();
        this.D = d;
        this.k = k;
    }
    
    public void M(final T d) {
        this.D = d;
    }
    
    public static String M(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        final int n = string.length() - 1;
        final int n2 = 26;
        final int n3 = 97;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
    
    public String[] M() {
        return this.k;
    }
    
    public T M() {
        return this.D;
    }
}
