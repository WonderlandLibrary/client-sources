/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.collect.Multimap;
import java.lang.annotation.Inherited;
import java.util.List;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;

public class TypeAnnotationsScanner
extends AbstractScanner {
    public void scan(Object cls) {
        String className = this.getMetadataAdapter().getClassName(cls);
        for (String annotationType : this.getMetadataAdapter().getClassAnnotationNames(cls)) {
            if (!this.acceptResult(annotationType) && !annotationType.equals(Inherited.class.getName())) continue;
            this.getStore().put((Object)annotationType, (Object)className);
        }
    }
}

