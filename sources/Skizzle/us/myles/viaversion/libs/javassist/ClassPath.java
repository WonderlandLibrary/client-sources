/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist;

import java.io.InputStream;
import java.net.URL;
import us.myles.viaversion.libs.javassist.NotFoundException;

public interface ClassPath {
    public InputStream openClassfile(String var1) throws NotFoundException;

    public URL find(String var1);
}

