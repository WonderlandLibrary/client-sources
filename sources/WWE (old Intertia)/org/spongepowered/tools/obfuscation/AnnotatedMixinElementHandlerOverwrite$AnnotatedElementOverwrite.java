package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;

static class AnnotatedElementOverwrite extends AnnotatedElement<ExecutableElement>
{
    private final boolean shouldRemap;
    
    public AnnotatedElementOverwrite(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean shouldRemap) {
        super(executableElement, annotationHandle);
        this.shouldRemap = shouldRemap;
    }
    
    public boolean shouldRemap() {
        return this.shouldRemap;
    }
}
