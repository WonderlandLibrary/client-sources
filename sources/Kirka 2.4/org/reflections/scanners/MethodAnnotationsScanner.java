/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.collect.Multimap;
import java.util.List;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;

public class MethodAnnotationsScanner
extends AbstractScanner {
    public void scan(Object cls) {
        for (Object method : this.getMetadataAdapter().getMethods(cls)) {
            for (String methodAnnotation : this.getMetadataAdapter().getMethodAnnotationNames(method)) {
                if (!this.acceptResult(methodAnnotation)) continue;
                this.getStore().put((Object)methodAnnotation, (Object)this.getMetadataAdapter().getMethodFullKey(cls, method));
            }
        }
    }
}

