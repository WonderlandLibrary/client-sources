package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import javax.lang.model.type.*;
import javax.tools.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.struct.*;

class AnnotatedMixin
{
    private final AnnotationHandle annotation;
    private final Messager messager;
    private final ITypeHandleProvider typeProvider;
    private final IObfuscationManager obf;
    private final IMappingConsumer mappings;
    private final TypeElement mixin;
    private final List<ExecutableElement> methods;
    private final TypeHandle handle;
    private final List<TypeHandle> targets;
    private final TypeHandle primaryTarget;
    private final String classRef;
    private final boolean remap;
    private final boolean virtual;
    private final AnnotatedMixinElementHandlerOverwrite overwrites;
    private final AnnotatedMixinElementHandlerShadow shadows;
    private final AnnotatedMixinElementHandlerInjector injectors;
    private final AnnotatedMixinElementHandlerAccessor accessors;
    private final AnnotatedMixinElementHandlerSoftImplements softImplements;
    private boolean validated;
    
    public AnnotatedMixin(final IMixinAnnotationProcessor messager, final TypeElement mixin) {
        super();
        this.targets = new ArrayList<TypeHandle>();
        this.validated = false;
        this.typeProvider = messager.getTypeProvider();
        this.obf = messager.getObfuscationManager();
        this.mappings = this.obf.createMappingConsumer();
        this.messager = messager;
        this.mixin = mixin;
        this.handle = new TypeHandle(mixin);
        this.methods = new ArrayList<ExecutableElement>((Collection<? extends ExecutableElement>)this.handle.getEnclosedElements(ElementKind.METHOD));
        this.virtual = this.handle.getAnnotation(Pseudo.class).exists();
        this.annotation = this.handle.getAnnotation(Mixin.class);
        this.classRef = TypeUtils.getInternalName(mixin);
        this.primaryTarget = this.initTargets();
        this.remap = (this.annotation.getBoolean("remap", true) && this.targets.size() > 0);
        this.overwrites = new AnnotatedMixinElementHandlerOverwrite(messager, this);
        this.shadows = new AnnotatedMixinElementHandlerShadow(messager, this);
        this.injectors = new AnnotatedMixinElementHandlerInjector(messager, this);
        this.accessors = new AnnotatedMixinElementHandlerAccessor(messager, this);
        this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(messager, this);
    }
    
    AnnotatedMixin runValidators(final IMixinValidator.ValidationPass validationPass, final Collection<IMixinValidator> collection) {
        final Iterator<IMixinValidator> iterator = collection.iterator();
        while (iterator.hasNext() && iterator.next().validate(validationPass, this.mixin, this.annotation, this.targets)) {}
        if (validationPass == IMixinValidator.ValidationPass.FINAL && !this.validated) {
            this.validated = true;
            this.runFinalValidation();
        }
        return this;
    }
    
    private TypeHandle initTargets() {
        TypeHandle typeHandle = null;
        try {
            final Iterator<TypeMirror> iterator = this.annotation.getList().iterator();
            while (iterator.hasNext()) {
                final TypeHandle typeHandle2 = new TypeHandle((DeclaredType)iterator.next());
                if (this.targets.contains(typeHandle2)) {
                    continue;
                }
                this.addTarget(typeHandle2);
                if (typeHandle != null) {
                    continue;
                }
                typeHandle = typeHandle2;
            }
        }
        catch (Exception ex) {
            this.printMessage(Diagnostic.Kind.WARNING, "Error processing public targets: " + ex.getClass().getName() + ": " + ex.getMessage(), this);
        }
        try {
            for (final String s : this.annotation.getList("targets")) {
                TypeHandle typeHandle3 = this.typeProvider.getTypeHandle(s);
                if (this.targets.contains(typeHandle3)) {
                    continue;
                }
                if (this.virtual) {
                    typeHandle3 = this.typeProvider.getSimulatedHandle(s, this.mixin.asType());
                }
                else {
                    if (typeHandle3 == null) {
                        this.printMessage(Diagnostic.Kind.ERROR, "Mixin target " + s + " could not be found", this);
                        return null;
                    }
                    if (typeHandle3.isPublic()) {
                        this.printMessage(Diagnostic.Kind.WARNING, "Mixin target " + s + " is public and must be specified in value", this);
                        return null;
                    }
                }
                this.addSoftTarget(typeHandle3, s);
                if (typeHandle != null) {
                    continue;
                }
                typeHandle = typeHandle3;
            }
        }
        catch (Exception ex2) {
            this.printMessage(Diagnostic.Kind.WARNING, "Error processing private targets: " + ex2.getClass().getName() + ": " + ex2.getMessage(), this);
        }
        if (typeHandle == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Mixin has no targets", this);
        }
        return typeHandle;
    }
    
    private void printMessage(final Diagnostic.Kind kind, final CharSequence charSequence, final AnnotatedMixin annotatedMixin) {
        this.messager.printMessage(kind, charSequence, this.mixin, this.annotation.asMirror());
    }
    
    private void addSoftTarget(final TypeHandle typeHandle, final String s) {
        final ObfuscationData<String> obfClass = this.obf.getDataProvider().getObfClass(typeHandle);
        if (!obfClass.isEmpty()) {
            this.obf.getReferenceManager().addClassMapping(this.classRef, s, obfClass);
        }
        this.addTarget(typeHandle);
    }
    
    private void addTarget(final TypeHandle typeHandle) {
        this.targets.add(typeHandle);
    }
    
    @Override
    public String toString() {
        return this.mixin.getSimpleName().toString();
    }
    
    public AnnotationHandle getAnnotation() {
        return this.annotation;
    }
    
    public TypeElement getMixin() {
        return this.mixin;
    }
    
    public TypeHandle getHandle() {
        return this.handle;
    }
    
    public String getClassRef() {
        return this.classRef;
    }
    
    public boolean isInterface() {
        return this.mixin.getKind() == ElementKind.INTERFACE;
    }
    
    @Deprecated
    public TypeHandle getPrimaryTarget() {
        return this.primaryTarget;
    }
    
    public List<TypeHandle> getTargets() {
        return this.targets;
    }
    
    public boolean isMultiTarget() {
        return this.targets.size() > 1;
    }
    
    public boolean remap() {
        return this.remap;
    }
    
    public IMappingConsumer getMappings() {
        return this.mappings;
    }
    
    private void runFinalValidation() {
        final Iterator<ExecutableElement> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            this.overwrites.registerMerge(iterator.next());
        }
    }
    
    public void registerOverwrite(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean b) {
        this.methods.remove(executableElement);
        this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(executableElement, annotationHandle, b));
    }
    
    public void registerShadow(final VariableElement variableElement, final AnnotationHandle annotationHandle, final boolean b) {
        this.shadows.registerShadow(this.shadows.new AnnotatedElementShadowField(variableElement, annotationHandle, b));
    }
    
    public void registerShadow(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean b) {
        this.methods.remove(executableElement);
        this.shadows.registerShadow(this.shadows.new AnnotatedElementShadowMethod(executableElement, annotationHandle, b));
    }
    
    public void registerInjector(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final InjectorRemap injectorRemap) {
        this.methods.remove(executableElement);
        this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(executableElement, annotationHandle, injectorRemap));
        final Iterator<AnnotationHandle> iterator = annotationHandle.getAnnotationList("at").iterator();
        while (iterator.hasNext()) {
            this.registerInjectionPoint(executableElement, annotationHandle, iterator.next(), injectorRemap, "@At(%s)");
        }
        for (final AnnotationHandle annotationHandle2 : annotationHandle.getAnnotationList("slice")) {
            final String s = annotationHandle2.getValue("id", "");
            final AnnotationHandle annotation = annotationHandle2.getAnnotation("from");
            if (annotation != null) {
                this.registerInjectionPoint(executableElement, annotationHandle, annotation, injectorRemap, "@Slice[" + s + "](from=@At(%s))");
            }
            final AnnotationHandle annotation2 = annotationHandle2.getAnnotation("to");
            if (annotation2 != null) {
                this.registerInjectionPoint(executableElement, annotationHandle, annotation2, injectorRemap, "@Slice[" + s + "](to=@At(%s))");
            }
        }
    }
    
    public void registerInjectionPoint(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final AnnotationHandle annotationHandle2, final InjectorRemap injectorRemap, final String s) {
        this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(executableElement, annotationHandle, annotationHandle2, injectorRemap), s);
    }
    
    public void registerAccessor(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean b) {
        this.methods.remove(executableElement);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(executableElement, annotationHandle, b));
    }
    
    public void registerInvoker(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean b) {
        this.methods.remove(executableElement);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(executableElement, annotationHandle, b));
    }
    
    public void registerSoftImplements(final AnnotationHandle annotationHandle) {
        this.softImplements.process(annotationHandle);
    }
}
