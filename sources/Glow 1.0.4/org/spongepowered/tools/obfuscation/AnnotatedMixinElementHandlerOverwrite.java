package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.tools.*;
import java.util.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerOverwrite(final IMixinAnnotationProcessor mixinAnnotationProcessor, final AnnotatedMixin annotatedMixin) {
        super(mixinAnnotationProcessor, annotatedMixin);
    }
    
    public void registerMerge(final ExecutableElement executableElement) {
        this.validateTargetMethod(executableElement, null, new AliasedElementName(executableElement, AnnotationHandle.MISSING), "overwrite", true, true);
    }
    
    public void registerOverwrite(final AnnotatedElementOverwrite annotatedElementOverwrite) {
        this.validateTargetMethod(annotatedElementOverwrite.getElement(), annotatedElementOverwrite.getAnnotation(), new AliasedElementName(((AnnotatedElement<Element>)annotatedElementOverwrite).getElement(), annotatedElementOverwrite.getAnnotation()), "@Overwrite", true, false);
        this.checkConstraints(annotatedElementOverwrite.getElement(), annotatedElementOverwrite.getAnnotation());
        if (annotatedElementOverwrite.shouldRemap()) {
            final Iterator<TypeHandle> iterator = this.mixin.getTargets().iterator();
            while (iterator.hasNext()) {
                if (!this.registerOverwriteForTarget(annotatedElementOverwrite, iterator.next())) {
                    return;
                }
            }
        }
        if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
            final Diagnostic.Kind kind = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            final String javadoc = this.ap.getJavadocProvider().getJavadoc(((AnnotatedElement<Element>)annotatedElementOverwrite).getElement());
            if (javadoc == null) {
                this.ap.printMessage(kind, "@Overwrite is missing javadoc comment", ((AnnotatedElement<Element>)annotatedElementOverwrite).getElement());
                return;
            }
            if (!javadoc.toLowerCase().contains("@author")) {
                this.ap.printMessage(kind, "@Overwrite is missing an @author tag", ((AnnotatedElement<Element>)annotatedElementOverwrite).getElement());
            }
            if (!javadoc.toLowerCase().contains("@reason")) {
                this.ap.printMessage(kind, "@Overwrite is missing an @reason tag", ((AnnotatedElement<Element>)annotatedElementOverwrite).getElement());
            }
        }
    }
    
    private boolean registerOverwriteForTarget(final AnnotatedElementOverwrite annotatedElementOverwrite, final TypeHandle typeHandle) {
        final ObfuscationData<MappingMethod> obfMethod = this.obf.getDataProvider().getObfMethod(typeHandle.getMappingMethod(annotatedElementOverwrite.getSimpleName(), annotatedElementOverwrite.getDesc()));
        if (obfMethod.isEmpty()) {
            Diagnostic.Kind kind = Diagnostic.Kind.ERROR;
            try {
                if (annotatedElementOverwrite.getElement().getClass().getMethod("isStatic", (Class<?>[])new Class[0]).invoke(((AnnotatedElement<Object>)annotatedElementOverwrite).getElement(), new Object[0])) {
                    kind = Diagnostic.Kind.WARNING;
                }
            }
            catch (Exception ex2) {}
            this.ap.printMessage(kind, "No obfuscation mapping for @Overwrite method", ((AnnotatedElement<Element>)annotatedElementOverwrite).getElement());
            return false;
        }
        try {
            this.addMethodMappings(annotatedElementOverwrite.getSimpleName(), annotatedElementOverwrite.getDesc(), obfMethod);
        }
        catch (Mappings.MappingConflictException ex) {
            annotatedElementOverwrite.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + ex.getNew().getSimpleName() + " for target " + typeHandle + " conflicts with existing mapping " + ex.getOld().getSimpleName());
            return false;
        }
        return true;
    }
    
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
}
