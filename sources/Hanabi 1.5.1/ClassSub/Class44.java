package ClassSub;

import java.net.*;
import java.io.*;

public class Class44 implements Class217
{
    
    
    @Override
    public URL getResource(final String s) {
        return Class337.class.getClassLoader().getResource(s.replace('\\', '/'));
    }
    
    @Override
    public InputStream getResourceAsStream(final String s) {
        return Class337.class.getClassLoader().getResourceAsStream(s.replace('\\', '/'));
    }
}
