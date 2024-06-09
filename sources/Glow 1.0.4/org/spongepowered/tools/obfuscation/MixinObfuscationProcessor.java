package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.asm.mixin.*;
import java.lang.annotation.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.*;
import java.util.*;

public abstract class MixinObfuscationProcessor extends AbstractProcessor
{
    protected AnnotatedMixins mixins;
    
    public MixinObfuscationProcessor() {
        super();
    }
    
    @Override
    public synchronized void init(final ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.mixins = AnnotatedMixins.getMixinsForEnvironment(processingEnvironment);
    }
    
    protected void processMixins(final RoundEnvironment roundEnvironment) {
        this.mixins.onPassStarted();
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Mixin.class)) {
            if (element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.INTERFACE) {
                this.mixins.registerMixin((TypeElement)element);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Mixin annotation on an element which is not a class or interface", element);
            }
        }
    }
    
    protected void postProcess(final RoundEnvironment roundEnvironment) {
        this.mixins.onPassCompleted(roundEnvironment);
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        try {
            return SourceVersion.valueOf("RELEASE_8");
        }
        catch (IllegalArgumentException ex) {
            return super.getSupportedSourceVersion();
        }
    }
    
    @Override
    public Set<String> getSupportedOptions() {
        return SupportedOptions.getAllOptions();
    }
}
