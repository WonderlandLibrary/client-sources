// 
// Decompiled by Procyon v0.5.30
// 

package org.reflections.scanners;

import org.reflections.vfs.Vfs;

@Deprecated
public class TypesScanner extends AbstractScanner
{
    public Object scan(final Vfs.File file, Object classObject) {
        classObject = super.scan(file, classObject);
        final String className = this.getMetadataAdapter().getClassName(classObject);
        this.getStore().put((Object)className, (Object)className);
        return classObject;
    }
    
    public void scan(final Object cls) {
        throw new UnsupportedOperationException("should not get here");
    }
}
