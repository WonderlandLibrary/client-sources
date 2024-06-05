package ClassSub;

import java.io.*;

public class Class227
{
    
    
    public static Class282 getTexture(final String s, final InputStream inputStream) throws IOException {
        return getTexture(s, inputStream, false, 9729);
    }
    
    public static Class282 getTexture(final String s, final InputStream inputStream, final boolean b) throws IOException {
        return getTexture(s, inputStream, b, 9729);
    }
    
    public static Class282 getTexture(final String s, final InputStream inputStream, final int n) throws IOException {
        return getTexture(s, inputStream, false, n);
    }
    
    public static Class282 getTexture(final String s, final InputStream inputStream, final boolean b, final int n) throws IOException {
        return Class144.get().getTexture(inputStream, inputStream.toString() + "." + s, b, n);
    }
}
