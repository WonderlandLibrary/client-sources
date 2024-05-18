// 
// Decompiled by Procyon v0.5.30
// 

package org.reflections.scanners;

import org.reflections.vfs.Vfs;

public class ResourcesScanner extends AbstractScanner
{
    public boolean acceptsInput(final String file) {
        return !file.endsWith(".class");
    }
    
    public Object scan(final Vfs.File file, final Object classObject) {
        this.getStore().put((Object)file.getName(), (Object)file.getRelativePath());
        return classObject;
    }
    
    public void scan(final Object cls) {
        throw new UnsupportedOperationException();
    }
}
