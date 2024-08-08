package net.futureclient.client;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import javax.crypto.Cipher;

public class QH
{
    private byte[] k;
    
    public QH(final String s) {
        int i = 0;
        final int n = 16;
        super();
        this.k = new byte[n];
        int n2 = 0;
        while (i < 16) {
            try {
                final byte[] k = this.k;
                final byte[] bytes = s.getBytes();
                final int n3 = n2;
                k[n3] = bytes[n3];
            }
            catch (Exception ex) {}
            i = ++n2;
        }
    }
    
    public byte[] e(final byte[] array) throws Exception {
        final Key m = this.M();
        final Cipher instance = Cipher.getInstance("AES");
        instance.init(2, m);
        return instance.doFinal(array);
    }
    
    public byte[] M(final byte[] array) throws Exception {
        final Key m = this.M();
        final Cipher instance = Cipher.getInstance("AES");
        instance.init(1, m);
        return instance.doFinal(array);
    }
    
    private Key M() throws Exception {
        return new SecretKeySpec(this.k, "AES");
    }
    
    public static String M(String s) {
        final char c = '\t';
        final char c2 = 'r';
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n;
        int i = n = length - 1;
        final char[] array2 = array;
        final char c3 = c2;
        final char c4 = c;
        while (i >= 0) {
            final char[] array3 = array2;
            final String s2 = s;
            final int n2 = n;
            final char char1 = s2.charAt(n2);
            --n;
            array3[n2] = (char)(char1 ^ c4);
            if (n < 0) {
                break;
            }
            final char[] array4 = array2;
            final String s3 = s;
            final int n3 = n--;
            array4[n3] = (char)(s3.charAt(n3) ^ c3);
            i = n;
        }
        return new String(array2);
    }
}
