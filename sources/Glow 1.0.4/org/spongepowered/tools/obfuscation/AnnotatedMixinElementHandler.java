package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.util.*;
import javax.tools.*;
import org.spongepowered.asm.util.throwables.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import javax.annotation.processing.*;
import java.util.*;

abstract class AnnotatedMixinElementHandler
{
    protected final AnnotatedMixin mixin;
    protected final String classRef;
    protected final IMixinAnnotationProcessor ap;
    protected final IObfuscationManager obf;
    private IMappingConsumer mappings;
    
    AnnotatedMixinElementHandler(final IMixinAnnotationProcessor ap, final AnnotatedMixin mixin) {
        super();
        this.ap = ap;
        this.mixin = mixin;
        this.classRef = mixin.getClassRef();
        this.obf = ap.getObfuscationManager();
    }
    
    private IMappingConsumer getMappings() {
        if (this.mappings == null) {
            final IMappingConsumer mappings = this.mixin.getMappings();
            if (mappings instanceof Mappings) {
                this.mappings = ((Mappings)mappings).asUnique();
            }
            else {
                this.mappings = mappings;
            }
        }
        return this.mappings;
    }
    
    protected final void addFieldMappings(final String s, final String s2, final ObfuscationData<MappingField> obfuscationData) {
        for (final ObfuscationType obfuscationType : obfuscationData) {
            final MappingField mappingField = obfuscationData.get(obfuscationType);
            this.addFieldMapping(obfuscationType, s, mappingField.getSimpleName(), s2, mappingField.getDesc());
        }
    }
    
    protected final void addFieldMapping(final ObfuscationType obfuscationType, final ShadowElementName shadowElementName, final String s, final String s2) {
        this.addFieldMapping(obfuscationType, shadowElementName.name(), shadowElementName.obfuscated(), s, s2);
    }
    
    protected final void addFieldMapping(final ObfuscationType obfuscationType, final String s, final String s2, final String s3, final String s4) {
        this.getMappings().addFieldMapping(obfuscationType, new MappingField(this.classRef, s, s3), new MappingField(this.classRef, s2, s4));
    }
    
    protected final void addMethodMappings(final String s, final String s2, final ObfuscationData<MappingMethod> obfuscationData) {
        for (final ObfuscationType obfuscationType : obfuscationData) {
            final MappingMethod mappingMethod = obfuscationData.get(obfuscationType);
            this.addMethodMapping(obfuscationType, s, mappingMethod.getSimpleName(), s2, mappingMethod.getDesc());
        }
    }
    
    protected final void addMethodMapping(final ObfuscationType obfuscationType, final ShadowElementName shadowElementName, final String s, final String s2) {
        this.addMethodMapping(obfuscationType, shadowElementName.name(), shadowElementName.obfuscated(), s, s2);
    }
    
    protected final void addMethodMapping(final ObfuscationType obfuscationType, final String s, final String s2, final String s3, final String s4) {
        this.getMappings().addMethodMapping(obfuscationType, new MappingMethod(this.classRef, s, s3), new MappingMethod(this.classRef, s2, s4));
    }
    
    protected final void checkConstraints(final ExecutableElement executableElement, final AnnotationHandle annotationHandle) {
        try {
            final ConstraintParser.Constraint parse = ConstraintParser.parse(annotationHandle.getValue("constraints"));
            try {
                parse.check(this.ap.getTokenProvider());
            }
            catch (ConstraintViolationException ex) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), executableElement, annotationHandle.asMirror());
            }
        }
        catch (InvalidConstraintException ex2) {
            this.ap.printMessage(Diagnostic.Kind.WARNING, ex2.getMessage(), executableElement, annotationHandle.asMirror());
        }
    }
    
    protected final void validateTarget(final Element element, final AnnotationHandle annotationHandle, final AliasedElementName aliasedElementName, final String s) {
        if (element instanceof ExecutableElement) {
            this.validateTargetMethod((ExecutableElement)element, annotationHandle, aliasedElementName, s, false, false);
        }
        else if (element instanceof VariableElement) {
            this.validateTargetField((VariableElement)element, annotationHandle, aliasedElementName, s);
        }
    }
    
    protected final void validateTargetMethod(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final AliasedElementName aliasedElementName, final String s, final boolean b, final boolean b2) {
        final String javaSignature = TypeUtils.getJavaSignature(executableElement);
        for (final TypeHandle typeHandle : this.mixin.getTargets()) {
            if (typeHandle.isImaginary()) {
                continue;
            }
            MethodHandle methodHandle = typeHandle.findMethod(executableElement);
            if (methodHandle == null && aliasedElementName.hasPrefix()) {
                methodHandle = typeHandle.findMethod(aliasedElementName.baseName(), javaSignature);
            }
            if (methodHandle == null && aliasedElementName.hasAliases()) {
                final Iterator<String> iterator2 = aliasedElementName.getAliases().iterator();
                while (iterator2.hasNext()) {
                    if ((methodHandle = typeHandle.findMethod(iterator2.next(), javaSignature)) != null) {
                        break;
                    }
                }
            }
            if (methodHandle != null) {
                if (!b) {
                    continue;
                }
                this.validateMethodVisibility(executableElement, annotationHandle, s, typeHandle, methodHandle);
            }
            else {
                if (b2) {
                    continue;
                }
                this.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + s + " method in " + typeHandle, executableElement, annotationHandle);
            }
        }
    }
    
    private void validateMethodVisibility(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final String s, final TypeHandle typeHandle, final MethodHandle methodHandle) {
        final Visibility visibility = methodHandle.getVisibility();
        if (visibility == null) {
            return;
        }
        final Visibility visibility2 = TypeUtils.getVisibility(executableElement);
        final String string = "visibility of " + visibility + " method in " + typeHandle;
        if (visibility.ordinal() > visibility2.ordinal()) {
            this.printMessage(Diagnostic.Kind.WARNING, visibility2 + " " + s + " method cannot reduce " + string, executableElement, annotationHandle);
        }
        else if (visibility == Visibility.PRIVATE && visibility2.ordinal() > visibility.ordinal()) {
            this.printMessage(Diagnostic.Kind.WARNING, visibility2 + " " + s + " method will upgrade " + string, executableElement, annotationHandle);
        }
    }
    
    protected final void validateTargetField(final VariableElement variableElement, final AnnotationHandle annotationHandle, final AliasedElementName aliasedElementName, final String s) {
        final String string = variableElement.asType().toString();
        for (final TypeHandle typeHandle : this.mixin.getTargets()) {
            if (typeHandle.isImaginary()) {
                continue;
            }
            FieldHandle fieldHandle = typeHandle.findField(variableElement);
            if (fieldHandle != null) {
                continue;
            }
            final Iterator<String> iterator2 = aliasedElementName.getAliases().iterator();
            while (iterator2.hasNext() && (fieldHandle = typeHandle.findField(iterator2.next(), string)) == null) {}
            if (fieldHandle != null) {
                continue;
            }
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + s + " field in " + typeHandle, variableElement, annotationHandle.asMirror());
        }
    }
    
    protected final void validateReferencedTarget(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final MemberInfo memberInfo, final String s) {
        final String descriptor = memberInfo.toDescriptor();
        for (final TypeHandle typeHandle : this.mixin.getTargets()) {
            if (typeHandle.isImaginary()) {
                continue;
            }
            if (typeHandle.findMethod(memberInfo.name, descriptor) != null) {
                continue;
            }
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + s + " in " + typeHandle, executableElement, annotationHandle.asMirror());
        }
    }
    
    private void printMessage(final Diagnostic.Kind kind, final String s, final Element element, final AnnotationHandle annotationHandle) {
        if (annotationHandle == null) {
            this.ap.printMessage(kind, s, element);
        }
        else {
            this.ap.printMessage(kind, s, element, annotationHandle.asMirror());
        }
    }
    
    protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(final ObfuscationData<T> obfuscationData) {
        final ObfuscationData<Object> obfuscationData2 = (ObfuscationData<Object>)new ObfuscationData<T>();
        for (final ObfuscationType obfuscationType : obfuscationData) {
            obfuscationData2.put(obfuscationType, obfuscationData.get(obfuscationType).move(null));
        }
        return (ObfuscationData<T>)obfuscationData2;
    }
    
    protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(final ObfuscationData<T> obfuscationData) {
        final ObfuscationData<Object> obfuscationData2 = (ObfuscationData<Object>)new ObfuscationData<T>();
        for (final ObfuscationType obfuscationType : obfuscationData) {
            obfuscationData2.put(obfuscationType, obfuscationData.get(obfuscationType).transform(null));
        }
        return (ObfuscationData<T>)obfuscationData2;
    }
    
    abstract static class AnnotatedElement<E extends Element>
    {
        protected final E element;
        protected final AnnotationHandle annotation;
        private final String desc;
        
        public AnnotatedElement(final E element, final AnnotationHandle annotation) {
            super();
            this.element = element;
            this.annotation = annotation;
            this.desc = TypeUtils.getDescriptor(element);
        }
        
        public E getElement() {
            return this.element;
        }
        
        public AnnotationHandle getAnnotation() {
            return this.annotation;
        }
        
        public String getSimpleName() {
            return this.getElement().getSimpleName().toString();
        }
        
        public String getDesc() {
            return this.desc;
        }
        
        public final void printMessage(final Messager messager, final Diagnostic.Kind kind, final CharSequence charSequence) {
            messager.printMessage(kind, charSequence, this.element, this.annotation.asMirror());
        }
    }
    
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
}
