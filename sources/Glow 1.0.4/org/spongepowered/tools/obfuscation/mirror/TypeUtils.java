package org.spongepowered.tools.obfuscation.mirror;

import java.util.*;
import org.spongepowered.asm.util.*;
import javax.lang.model.type.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

public abstract class TypeUtils
{
    private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
    private static final String OBJECT_SIG = "java.lang.Object";
    private static final String OBJECT_REF = "java/lang/Object";
    
    private TypeUtils() {
        super();
    }
    
    public static PackageElement getPackage(final TypeMirror typeMirror) {
        if (!(typeMirror instanceof DeclaredType)) {
            return null;
        }
        return getPackage((TypeElement)((DeclaredType)typeMirror).asElement());
    }
    
    public static PackageElement getPackage(final TypeElement typeElement) {
        Element element;
        for (element = typeElement.getEnclosingElement(); element != null && !(element instanceof PackageElement); element = element.getEnclosingElement()) {}
        return (PackageElement)element;
    }
    
    public static String getElementType(final Element element) {
        if (element instanceof TypeElement) {
            return "TypeElement";
        }
        if (element instanceof ExecutableElement) {
            return "ExecutableElement";
        }
        if (element instanceof VariableElement) {
            return "VariableElement";
        }
        if (element instanceof PackageElement) {
            return "PackageElement";
        }
        if (element instanceof TypeParameterElement) {
            return "TypeParameterElement";
        }
        return element.getClass().getSimpleName();
    }
    
    public static String stripGenerics(final String s) {
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        int n = 0;
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == '<') {
                ++n;
            }
            if (n == 0) {
                sb.append(char1);
            }
            else if (char1 == '>') {
                --n;
            }
            ++i;
        }
        return sb.toString();
    }
    
    public static String getName(final VariableElement variableElement) {
        return (variableElement != null) ? variableElement.getSimpleName().toString() : null;
    }
    
    public static String getName(final ExecutableElement executableElement) {
        return (executableElement != null) ? executableElement.getSimpleName().toString() : null;
    }
    
    public static String getJavaSignature(final Element element) {
        if (element instanceof ExecutableElement) {
            final ExecutableElement executableElement = (ExecutableElement)element;
            final StringBuilder append = new StringBuilder().append("(");
            int n = 0;
            for (final VariableElement variableElement : executableElement.getParameters()) {
                if (n != 0) {
                    append.append(',');
                }
                append.append(getTypeName(variableElement.asType()));
                n = 1;
            }
            append.append(')').append(getTypeName(executableElement.getReturnType()));
            return append.toString();
        }
        return getTypeName(element.asType());
    }
    
    public static String getJavaSignature(final String s) {
        return new SignaturePrinter("", s).setFullyQualified(true).toDescriptor();
    }
    
    public static String getTypeName(final TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case ARRAY: {
                return getTypeName(((ArrayType)typeMirror).getComponentType()) + "[]";
            }
            case DECLARED: {
                return getTypeName((DeclaredType)typeMirror);
            }
            case TYPEVAR: {
                return getTypeName(getUpperBound(typeMirror));
            }
            case ERROR: {
                return "java.lang.Object";
            }
            default: {
                return typeMirror.toString();
            }
        }
    }
    
    public static String getTypeName(final DeclaredType declaredType) {
        if (declaredType == null) {
            return "java.lang.Object";
        }
        return getInternalName((TypeElement)declaredType.asElement()).replace('/', '.');
    }
    
    public static String getDescriptor(final Element element) {
        if (element instanceof ExecutableElement) {
            return getDescriptor((ExecutableElement)element);
        }
        if (element instanceof VariableElement) {
            return getInternalName((VariableElement)element);
        }
        return getInternalName(element.asType());
    }
    
    public static String getDescriptor(final ExecutableElement executableElement) {
        if (executableElement == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final Iterator<? extends VariableElement> iterator = executableElement.getParameters().iterator();
        while (iterator.hasNext()) {
            sb.append(getInternalName((VariableElement)iterator.next()));
        }
        return String.format("(%s)%s", sb, getInternalName(executableElement.getReturnType()));
    }
    
    public static String getInternalName(final VariableElement variableElement) {
        if (variableElement == null) {
            return null;
        }
        return getInternalName(variableElement.asType());
    }
    
    public static String getInternalName(final TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case ARRAY: {
                return "[" + getInternalName(((ArrayType)typeMirror).getComponentType());
            }
            case DECLARED: {
                return "L" + getInternalName((DeclaredType)typeMirror) + ";";
            }
            case TYPEVAR: {
                return "L" + getInternalName(getUpperBound(typeMirror)) + ";";
            }
            case BOOLEAN: {
                return "Z";
            }
            case BYTE: {
                return "B";
            }
            case CHAR: {
                return "C";
            }
            case DOUBLE: {
                return "D";
            }
            case FLOAT: {
                return "F";
            }
            case INT: {
                return "I";
            }
            case LONG: {
                return "J";
            }
            case SHORT: {
                return "S";
            }
            case VOID: {
                return "V";
            }
            case ERROR: {
                return "Ljava/lang/Object;";
            }
            default: {
                throw new IllegalArgumentException("Unable to parse type symbol " + typeMirror + " with " + typeMirror.getKind() + " to equivalent bytecode type");
            }
        }
    }
    
    public static String getInternalName(final DeclaredType declaredType) {
        if (declaredType == null) {
            return "java/lang/Object";
        }
        return getInternalName((TypeElement)declaredType.asElement());
    }
    
    public static String getInternalName(final TypeElement typeElement) {
        if (typeElement == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(typeElement.getSimpleName());
        for (Element element = typeElement.getEnclosingElement(); element != null; element = element.getEnclosingElement()) {
            if (element instanceof TypeElement) {
                sb.insert(0, "$").insert(0, element.getSimpleName());
            }
            else if (element instanceof PackageElement) {
                sb.insert(0, "/").insert(0, ((PackageElement)element).getQualifiedName().toString().replace('.', '/'));
            }
        }
        return sb.toString();
    }
    
    private static DeclaredType getUpperBound(final TypeMirror typeMirror) {
        try {
            return getUpperBound0(typeMirror, 5);
        }
        catch (IllegalStateException ex) {
            throw new IllegalArgumentException("Type symbol \"" + typeMirror + "\" is too complex", ex);
        }
        catch (IllegalArgumentException ex2) {
            throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + typeMirror, ex2);
        }
    }
    
    private static DeclaredType getUpperBound0(final TypeMirror typeMirror, int n) {
        if (n == 0) {
            throw new IllegalStateException("Generic symbol \"" + typeMirror + "\" is too complex, exceeded " + 5 + " iterations attempting to determine upper bound");
        }
        if (typeMirror instanceof DeclaredType) {
            return (DeclaredType)typeMirror;
        }
        if (typeMirror instanceof TypeVariable) {
            try {
                return getUpperBound0(((TypeVariable)typeMirror).getUpperBound(), --n);
            }
            catch (IllegalStateException ex) {
                throw ex;
            }
            catch (IllegalArgumentException ex2) {
                throw ex2;
            }
            catch (Exception ex3) {
                throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + typeMirror);
            }
        }
        return null;
    }
    
    public static boolean isAssignable(final ProcessingEnvironment processingEnvironment, final TypeMirror typeMirror, final TypeMirror typeMirror2) {
        final boolean assignable = processingEnvironment.getTypeUtils().isAssignable(typeMirror, typeMirror2);
        if (!assignable && typeMirror instanceof DeclaredType && typeMirror2 instanceof DeclaredType) {
            return processingEnvironment.getTypeUtils().isAssignable(toRawType(processingEnvironment, (DeclaredType)typeMirror), toRawType(processingEnvironment, (DeclaredType)typeMirror2));
        }
        return assignable;
    }
    
    private static TypeMirror toRawType(final ProcessingEnvironment processingEnvironment, final DeclaredType declaredType) {
        return processingEnvironment.getElementUtils().getTypeElement(((TypeElement)declaredType.asElement()).getQualifiedName()).asType();
    }
    
    public static Visibility getVisibility(final Element element) {
        if (element == null) {
            return null;
        }
        final Iterator<Modifier> iterator = element.getModifiers().iterator();
        while (iterator.hasNext()) {
            switch (iterator.next()) {
                case PUBLIC: {
                    return Visibility.PUBLIC;
                }
                case PROTECTED: {
                    return Visibility.PROTECTED;
                }
                case PRIVATE: {
                    return Visibility.PRIVATE;
                }
                default: {
                    continue;
                }
            }
        }
        return Visibility.PACKAGE;
    }
}
