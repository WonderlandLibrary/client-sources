/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.collect.Multimap;
import org.reflections.scanners.AbstractScanner;
import org.reflections.vfs.Vfs;

public class ResourcesScanner
extends AbstractScanner {
    public boolean acceptsInput(String file) {
        return !file.endsWith(".class");
    }

    public Object scan(Vfs.File file, Object classObject) {
        this.getStore().put((Object)file.getName(), (Object)file.getRelativePath());
        return classObject;
    }

    public void scan(Object cls) {
        throw new UnsupportedOperationException();
    }
}

