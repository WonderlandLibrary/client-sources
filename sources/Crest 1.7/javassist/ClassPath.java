// 
// Decompiled by Procyon v0.5.30
// 

package javassist;

import java.net.URL;
import java.io.InputStream;

public interface ClassPath
{
    InputStream openClassfile(final String p0) throws NotFoundException;
    
    URL find(final String p0);
    
    void close();
}
