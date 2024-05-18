package org.spongepowered.tools.obfuscation;

import java.util.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;

static class AliasedElementName
{
    protected final String originalName;
    private final List<String> aliases;
    private boolean caseSensitive;
    
    public AliasedElementName(final Element element, final AnnotationHandle annotationHandle) {
        super();
        this.originalName = element.getSimpleName().toString();
        this.aliases = annotationHandle.getList("aliases");
    }
    
    public AliasedElementName setCaseSensitive(final boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        return this;
    }
    
    public boolean isCaseSensitive() {
        return this.caseSensitive;
    }
    
    public boolean hasAliases() {
        return this.aliases.size() > 0;
    }
    
    public List<String> getAliases() {
        return this.aliases;
    }
    
    public String elementName() {
        return this.originalName;
    }
    
    public String baseName() {
        return this.originalName;
    }
    
    public boolean hasPrefix() {
        return false;
    }
}
