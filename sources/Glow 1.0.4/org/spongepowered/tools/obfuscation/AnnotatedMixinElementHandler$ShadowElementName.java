package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.obfuscation.mapping.*;

static class ShadowElementName extends AliasedElementName
{
    private final boolean hasPrefix;
    private final String prefix;
    private final String baseName;
    private String obfuscated;
    
    ShadowElementName(final Element element, final AnnotationHandle annotationHandle) {
        super(element, annotationHandle);
        this.prefix = annotationHandle.getValue("prefix", "shadow$");
        boolean hasPrefix = false;
        String s = this.originalName;
        if (s.startsWith(this.prefix)) {
            hasPrefix = true;
            s = s.substring(this.prefix.length());
        }
        this.hasPrefix = hasPrefix;
        final String s2 = s;
        this.baseName = s2;
        this.obfuscated = s2;
    }
    
    @Override
    public String toString() {
        return this.baseName;
    }
    
    @Override
    public String baseName() {
        return this.baseName;
    }
    
    public ShadowElementName setObfuscatedName(final IMapping<?> mapping) {
        this.obfuscated = mapping.getName();
        return this;
    }
    
    public ShadowElementName setObfuscatedName(final String obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }
    
    @Override
    public boolean hasPrefix() {
        return this.hasPrefix;
    }
    
    public String prefix() {
        return this.hasPrefix ? this.prefix : "";
    }
    
    public String name() {
        return this.prefix(this.baseName);
    }
    
    public String obfuscated() {
        return this.prefix(this.obfuscated);
    }
    
    public String prefix(final String s) {
        return this.hasPrefix ? (this.prefix + s) : s;
    }
}
