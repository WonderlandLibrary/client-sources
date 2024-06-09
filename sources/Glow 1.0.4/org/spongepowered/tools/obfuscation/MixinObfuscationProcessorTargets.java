package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import java.lang.annotation.*;
import javax.tools.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.*;

@SupportedAnnotationTypes({ "org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements" })
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor
{
    public MixinObfuscationProcessorTargets() {
        super();
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            this.postProcess(roundEnvironment);
            return true;
        }
        this.processMixins(roundEnvironment);
        this.processShadows(roundEnvironment);
        this.processOverwrites(roundEnvironment);
        this.processAccessors(roundEnvironment);
        this.processInvokers(roundEnvironment);
        this.processImplements(roundEnvironment);
        this.postProcess(roundEnvironment);
        return true;
    }
    
    @Override
    protected void postProcess(final RoundEnvironment roundEnvironment) {
        super.postProcess(roundEnvironment);
        try {
            this.mixins.writeReferences();
            this.mixins.writeMappings();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void processShadows(final RoundEnvironment roundEnvironment) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Shadow.class)) {
            final Element enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            }
            else {
                final AnnotationHandle of = AnnotationHandle.of(element, Shadow.class);
                if (element.getKind() == ElementKind.FIELD) {
                    this.mixins.registerShadow((TypeElement)enclosingElement, (VariableElement)element, of);
                }
                else if (element.getKind() == ElementKind.METHOD) {
                    this.mixins.registerShadow((TypeElement)enclosingElement, (ExecutableElement)element, of);
                }
                else {
                    this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method or field", element);
                }
            }
        }
    }
    
    private void processOverwrites(final RoundEnvironment roundEnvironment) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Overwrite.class)) {
            final Element enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            }
            else if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerOverwrite((TypeElement)enclosingElement, (ExecutableElement)element);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", element);
            }
        }
    }
    
    private void processAccessors(final RoundEnvironment roundEnvironment) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Accessor.class)) {
            final Element enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            }
            else if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerAccessor((TypeElement)enclosingElement, (ExecutableElement)element);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", element);
            }
        }
    }
    
    private void processInvokers(final RoundEnvironment roundEnvironment) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Invoker.class)) {
            final Element enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            }
            else if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerInvoker((TypeElement)enclosingElement, (ExecutableElement)element);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", element);
            }
        }
    }
    
    private void processImplements(final RoundEnvironment roundEnvironment) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Implements.class)) {
            if (element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.INTERFACE) {
                this.mixins.registerSoftImplements((TypeElement)element, AnnotationHandle.of(element, Implements.class));
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", element);
            }
        }
    }
}
