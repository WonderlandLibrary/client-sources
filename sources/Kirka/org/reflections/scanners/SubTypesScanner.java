/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import java.util.List;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;
import org.reflections.scanners.Scanner;
import org.reflections.util.FilterBuilder;

public class SubTypesScanner
extends AbstractScanner {
    public SubTypesScanner() {
        this(true);
    }

    public SubTypesScanner(boolean excludeObjectClass) {
        if (excludeObjectClass) {
            this.filterResultsBy(new FilterBuilder().exclude(Object.class.getName()));
        }
    }

    public void scan(Object cls) {
        String className = this.getMetadataAdapter().getClassName(cls);
        String superclass = this.getMetadataAdapter().getSuperclassName(cls);
        if (this.acceptResult(superclass)) {
            this.getStore().put((Object)superclass, (Object)className);
        }
        for (String anInterface : this.getMetadataAdapter().getInterfacesNames(cls)) {
            if (!this.acceptResult(anInterface)) continue;
            this.getStore().put((Object)anInterface, (Object)className);
        }
    }
}

