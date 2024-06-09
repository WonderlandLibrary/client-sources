/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.InputStream;
import java.net.URL;
import javassist.NotFoundException;

public interface ClassPath {
    public InputStream openClassfile(String var1) throws NotFoundException;

    public URL find(String var1);

    public void close();
}

