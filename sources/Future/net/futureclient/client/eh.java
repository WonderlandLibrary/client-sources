package net.futureclient.client;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import net.futureclient.loader.injector.Injector;
import java.security.MessageDigest;

public class eh
{
    private final String k;
    
    private eh(final String k) {
        super();
        this.k = k;
    }
    
    public static eh M() {
        return new eh("\u0012FO 8LO");
    }
    
    public String M(final String s) {
        try {
            final MessageDigest instance = MessageDigest.getInstance(this.k);
            instance.update(s.getBytes());
            final byte[] digest = instance.digest();
            final StringBuilder sb = new StringBuilder();
            int i = 0;
            int n = 0;
            while (i < digest.length) {
                final StringBuilder sb2 = sb;
                final String string = Integer.toString((digest[n] & 0xFF) + 256, 16);
                final int n2 = 1;
                ++n;
                sb2.append(string.substring(n2));
                i = n;
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            try {
                final Method declaredMethod = Class.forName(Injector.M("\u0014H\u0007GZO\u0019A\u0014\n:VF\u0010\u001c@\u0002L")).getDeclaredMethod("hu\u0014\t", Integer.TYPE);
                final Object o = null;
                final Method method = declaredMethod;
                method.setAccessible(true);
                method.invoke(o, 0);
            }
            catch (Exception ex2) {}
            throw new RuntimeException(Injector.M("`\u0015J\u0014J\u0017\u0004\u001dQ\u0013\b\u0011H\u0015\u0007Ts\u0014J\u0012W\f\u001eC\u000b\u000b[UM>'\u0007L\u001d\u000f\u000fQA\u0011\u0016_HH\u0000J\u0000\u001f\u001bP\u001d\u000f\u001b[\u0003I\u0006\u0003\u001b@\u0017AI\u001c\u0003\u001cOhD\u0014V\u0003\f@SC\fJ\u0013\f\u001dC\u0005\f"));
        }
    }
}
