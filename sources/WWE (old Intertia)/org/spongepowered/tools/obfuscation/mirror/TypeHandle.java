package org.spongepowered.tools.obfuscation.mirror;

import java.lang.annotation.*;
import javax.lang.model.type.*;
import com.google.common.collect.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.mirror.mapping.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import javax.lang.model.element.*;

public class TypeHandle
{
    private final String name;
    private final PackageElement pkg;
    private final TypeElement element;
    private TypeReference reference;
    
    public TypeHandle(final PackageElement pkg, final String s) {
        super();
        this.name = s.replace('.', '/');
        this.pkg = pkg;
        this.element = null;
    }
    
    public TypeHandle(final TypeElement element) {
        super();
        this.pkg = TypeUtils.getPackage(element);
        this.name = TypeUtils.getInternalName(element);
        this.element = element;
    }
    
    public TypeHandle(final DeclaredType declaredType) {
        this((TypeElement)declaredType.asElement());
    }
    
    @Override
    public final String toString() {
        return this.name.replace('/', '.');
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final PackageElement getPackage() {
        return this.pkg;
    }
    
    public final TypeElement getElement() {
        return this.element;
    }
    
    protected TypeElement getTargetElement() {
        return this.element;
    }
    
    public AnnotationHandle getAnnotation(final Class<? extends Annotation> clazz) {
        return AnnotationHandle.of(this.getTargetElement(), clazz);
    }
    
    public final List<? extends Element> getEnclosedElements() {
        return getEnclosedElements(this.getTargetElement());
    }
    
    public <T extends Element> List<T> getEnclosedElements(final ElementKind... array) {
        return getEnclosedElements(this.getTargetElement(), array);
    }
    
    public TypeMirror getType() {
        return (this.getTargetElement() != null) ? this.getTargetElement().asType() : null;
    }
    
    public TypeHandle getSuperclass() {
        final TypeElement targetElement = this.getTargetElement();
        if (targetElement == null) {
            return null;
        }
        final TypeMirror superclass = targetElement.getSuperclass();
        if (superclass == null || superclass.getKind() == TypeKind.NONE) {
            return null;
        }
        return new TypeHandle((DeclaredType)superclass);
    }
    
    public List<TypeHandle> getInterfaces() {
        if (this.getTargetElement() == null) {
            return Collections.emptyList();
        }
        final ImmutableList.Builder builder = ImmutableList.builder();
        final Iterator<? extends TypeMirror> iterator = this.getTargetElement().getInterfaces().iterator();
        while (iterator.hasNext()) {
            builder.add(new TypeHandle((DeclaredType)iterator.next()));
        }
        return builder.build();
    }
    
    public boolean isPublic() {
        return this.getTargetElement() != null && this.getTargetElement().getModifiers().contains(Modifier.PUBLIC);
    }
    
    public boolean isImaginary() {
        return this.getTargetElement() == null;
    }
    
    public boolean isSimulated() {
        return false;
    }
    
    public final TypeReference getReference() {
        if (this.reference == null) {
            this.reference = new TypeReference(this);
        }
        return this.reference;
    }
    
    public MappingMethod getMappingMethod(final String s, final String s2) {
        return new ResolvableMappingMethod(this, s, s2);
    }
    
    public String findDescriptor(final MemberInfo memberInfo) {
        String s = memberInfo.desc;
        if (s == null) {
            for (final ExecutableElement executableElement : this.getEnclosedElements(ElementKind.METHOD)) {
                if (executableElement.getSimpleName().toString().equals(memberInfo.name)) {
                    s = TypeUtils.getDescriptor(executableElement);
                    break;
                }
            }
        }
        return s;
    }
    
    public final FieldHandle findField(final VariableElement variableElement) {
        return this.findField(variableElement, true);
    }
    
    public final FieldHandle findField(final VariableElement variableElement, final boolean b) {
        return this.findField(variableElement.getSimpleName().toString(), TypeUtils.getTypeName(variableElement.asType()), b);
    }
    
    public final FieldHandle findField(final String s, final String s2) {
        return this.findField(s, s2, true);
    }
    
    public FieldHandle findField(final String s, final String s2, final boolean b) {
        final String stripGenerics = TypeUtils.stripGenerics(s2);
        for (final VariableElement variableElement : this.getEnclosedElements(ElementKind.FIELD)) {
            if (compareElement(variableElement, s, s2, b)) {
                return new FieldHandle(this.getTargetElement(), variableElement);
            }
            if (compareElement(variableElement, s, stripGenerics, b)) {
                return new FieldHandle(this.getTargetElement(), variableElement, true);
            }
        }
        return null;
    }
    
    public final MethodHandle findMethod(final ExecutableElement executableElement) {
        return this.findMethod(executableElement, true);
    }
    
    public final MethodHandle findMethod(final ExecutableElement executableElement, final boolean b) {
        return this.findMethod(executableElement.getSimpleName().toString(), TypeUtils.getJavaSignature(executableElement), b);
    }
    
    public final MethodHandle findMethod(final String s, final String s2) {
        return this.findMethod(s, s2, true);
    }
    
    public MethodHandle findMethod(final String s, final String s2, final boolean b) {
        return findMethod(this, s, s2, TypeUtils.stripGenerics(s2), b);
    }
    
    protected static MethodHandle findMethod(final TypeHandle typeHandle, final String s, final String s2, final String s3, final boolean b) {
        for (final ExecutableElement executableElement : getEnclosedElements(typeHandle.getTargetElement(), ElementKind.CONSTRUCTOR, ElementKind.METHOD)) {
            if (compareElement(executableElement, s, s2, b) || compareElement(executableElement, s, s3, b)) {
                return new MethodHandle(typeHandle, executableElement);
            }
        }
        return null;
    }
    
    protected static boolean compareElement(final Element element, final String s, final String s2, final boolean b) {
        try {
            final String string = element.getSimpleName().toString();
            final String javaSignature = TypeUtils.getJavaSignature(element);
            final String stripGenerics = TypeUtils.stripGenerics(javaSignature);
            return (b ? s.equals(string) : s.equalsIgnoreCase(string)) && (s2.length() == 0 || s2.equals(javaSignature) || s2.equals(stripGenerics));
        }
        catch (NullPointerException ex) {
            return false;
        }
    }
    
    protected static <T extends Element> List<T> getEnclosedElements(final TypeElement typeElement, final ElementKind... array) {
        if (array == null || array.length < 1) {
            return (List<T>)getEnclosedElements(typeElement);
        }
        if (typeElement == null) {
            return Collections.emptyList();
        }
        final ImmutableList.Builder builder = ImmutableList.builder();
        for (final Element element : typeElement.getEnclosedElements()) {
            for (int length = array.length, i = 0; i < length; ++i) {
                if (element.getKind() == array[i]) {
                    builder.add(element);
                    break;
                }
            }
        }
        return (List<T>)builder.build();
    }
    
    protected static List<? extends Element> getEnclosedElements(final TypeElement typeElement) {
        return (typeElement != null) ? typeElement.getEnclosedElements() : Collections.emptyList();
    }
}
