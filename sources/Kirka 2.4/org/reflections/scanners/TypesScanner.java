/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.collect.Multimap;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;
import org.reflections.vfs.Vfs;

@Deprecated
public class TypesScanner
extends AbstractScanner {
    public Object scan(Vfs.File file, Object classObject) {
        classObject = super.scan(file, classObject);
        String className = this.getMetadataAdapter().getClassName(classObject);
        this.getStore().put((Object)className, (Object)className);
        return classObject;
    }

    public void scan(Object cls) {
        throw new UnsupportedOperationException("should not get here");
    }
}

