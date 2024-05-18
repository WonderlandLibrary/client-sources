/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import java.util.List;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;

public class TypeElementsScanner
extends AbstractScanner {
    private boolean includeFields = true;
    private boolean includeMethods = true;
    private boolean includeAnnotations = true;
    private boolean publicOnly = true;

    public void scan(Object cls) {
        String className = this.getMetadataAdapter().getClassName(cls);
        if (!this.acceptResult(className)) {
            return;
        }
        this.getStore().put((Object)className, (Object)"");
        if (this.includeFields) {
            for (Object field : this.getMetadataAdapter().getFields(cls)) {
                String fieldName = this.getMetadataAdapter().getFieldName(field);
                this.getStore().put((Object)className, (Object)fieldName);
            }
        }
        if (this.includeMethods) {
            for (Object method : this.getMetadataAdapter().getMethods(cls)) {
                if (this.publicOnly && !this.getMetadataAdapter().isPublic(method)) continue;
                String methodKey = this.getMetadataAdapter().getMethodName(method) + "(" + Joiner.on((String)", ").join(this.getMetadataAdapter().getParameterNames(method)) + ")";
                this.getStore().put((Object)className, (Object)methodKey);
            }
        }
        if (this.includeAnnotations) {
            for (Object annotation : this.getMetadataAdapter().getClassAnnotationNames(cls)) {
                this.getStore().put((Object)className, (Object)("@" + annotation));
            }
        }
    }

    public TypeElementsScanner includeFields() {
        return this.includeFields(true);
    }

    public TypeElementsScanner includeFields(boolean include) {
        this.includeFields = include;
        return this;
    }

    public TypeElementsScanner includeMethods() {
        return this.includeMethods(true);
    }

    public TypeElementsScanner includeMethods(boolean include) {
        this.includeMethods = include;
        return this;
    }

    public TypeElementsScanner includeAnnotations() {
        return this.includeAnnotations(true);
    }

    public TypeElementsScanner includeAnnotations(boolean include) {
        this.includeAnnotations = include;
        return this;
    }

    public TypeElementsScanner publicOnly(boolean only) {
        this.publicOnly = only;
        return this;
    }

    public TypeElementsScanner publicOnly() {
        return this.publicOnly(true);
    }
}

