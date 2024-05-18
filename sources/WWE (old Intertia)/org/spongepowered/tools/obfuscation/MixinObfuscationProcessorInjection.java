package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.util.*;

@SupportedAnnotationTypes({ "org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At" })
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor
{
    public MixinObfuscationProcessorInjection() {
        super();
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            this.postProcess(roundEnvironment);
            return true;
        }
        this.processMixins(roundEnvironment);
        this.processInjectors(roundEnvironment, Inject.class);
        this.processInjectors(roundEnvironment, ModifyArg.class);
        this.processInjectors(roundEnvironment, ModifyArgs.class);
        this.processInjectors(roundEnvironment, Redirect.class);
        this.processInjectors(roundEnvironment, ModifyVariable.class);
        this.processInjectors(roundEnvironment, ModifyConstant.class);
        this.postProcess(roundEnvironment);
        return true;
    }
    
    @Override
    protected void postProcess(final RoundEnvironment roundEnvironment) {
        super.postProcess(roundEnvironment);
        try {
            this.mixins.writeReferences();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void processInjectors(final RoundEnvironment roundEnvironment, final Class<? extends Annotation> clazz) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(clazz)) {
            final Element enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                throw new IllegalStateException("@" + clazz.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(enclosingElement));
            }
            final AnnotationHandle of = AnnotationHandle.of(element, clazz);
            if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerInjector((TypeElement)enclosingElement, (ExecutableElement)element, of);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.WARNING, "Found an @" + clazz.getSimpleName() + " annotation on an element which is not a method: " + element.toString());
            }
        }
    }
}
