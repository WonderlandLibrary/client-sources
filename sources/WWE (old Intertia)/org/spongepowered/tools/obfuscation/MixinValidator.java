package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.type.*;

public abstract class MixinValidator implements IMixinValidator
{
    protected final ProcessingEnvironment processingEnv;
    protected final Messager messager;
    protected final IOptionProvider options;
    protected final ValidationPass pass;
    
    public MixinValidator(final IMixinAnnotationProcessor mixinAnnotationProcessor, final ValidationPass pass) {
        super();
        this.processingEnv = mixinAnnotationProcessor.getProcessingEnvironment();
        this.messager = mixinAnnotationProcessor;
        this.options = mixinAnnotationProcessor;
        this.pass = pass;
    }
    
    @Override
    public final boolean validate(final ValidationPass validationPass, final TypeElement typeElement, final AnnotationHandle annotationHandle, final Collection<TypeHandle> collection) {
        return validationPass != this.pass || this.validate(typeElement, annotationHandle, collection);
    }
    
    protected abstract boolean validate(final TypeElement p0, final AnnotationHandle p1, final Collection<TypeHandle> p2);
    
    protected final void note(final String s, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, s, element);
    }
    
    protected final void warning(final String s, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.WARNING, s, element);
    }
    
    protected final void error(final String s, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, s, element);
    }
    
    protected final Collection<TypeMirror> getMixinsTargeting(final TypeMirror typeMirror) {
        return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(typeMirror);
    }
}
