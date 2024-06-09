package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.base.*;

public class SignaturePrinter
{
    private final String name;
    private final Type returnType;
    private final Type[] argTypes;
    private final String[] argNames;
    private String modifiers;
    private boolean fullyQualified;
    
    public SignaturePrinter(final MethodNode modifiers) {
        this(modifiers.name, Type.VOID_TYPE, Type.getArgumentTypes(modifiers.desc));
        this.setModifiers(modifiers);
    }
    
    public SignaturePrinter(final MethodNode modifiers, final String[] array) {
        this(modifiers.name, Type.VOID_TYPE, Type.getArgumentTypes(modifiers.desc), array);
        this.setModifiers(modifiers);
    }
    
    public SignaturePrinter(final MemberInfo memberInfo) {
        this(memberInfo.name, memberInfo.desc);
    }
    
    public SignaturePrinter(final String s, final String s2) {
        this(s, Type.getReturnType(s2), Type.getArgumentTypes(s2));
    }
    
    public SignaturePrinter(final String name, final Type returnType, final Type[] array) {
        super();
        this.modifiers = "private void";
        this.name = name;
        this.returnType = returnType;
        this.argTypes = new Type[array.length];
        this.argNames = new String[array.length];
        int i = 0;
        int n = 0;
        while (i < array.length) {
            if (array[i] != null) {
                this.argTypes[i] = array[i];
                this.argNames[i] = "var" + n++;
            }
            ++i;
        }
    }
    
    public SignaturePrinter(final String name, final Type returnType, final LocalVariableNode[] array) {
        super();
        this.modifiers = "private void";
        this.name = name;
        this.returnType = returnType;
        this.argTypes = new Type[array.length];
        this.argNames = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                this.argTypes[i] = Type.getType(array[i].desc);
                this.argNames[i] = array[i].name;
            }
        }
    }
    
    public SignaturePrinter(final String name, final Type returnType, final Type[] argTypes, final String[] argNames) {
        super();
        this.modifiers = "private void";
        this.name = name;
        this.returnType = returnType;
        this.argTypes = argTypes;
        this.argNames = argNames;
        if (this.argTypes.length > this.argNames.length) {
            throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", this.argNames.length, this.argTypes.length));
        }
    }
    
    public String getFormattedArgs() {
        return this.appendArgs(new StringBuilder(), true, true).toString();
    }
    
    public String getReturnType() {
        return getTypeName(this.returnType, false, this.fullyQualified);
    }
    
    public void setModifiers(final MethodNode methodNode) {
        final String typeName = getTypeName(Type.getReturnType(methodNode.desc), false, this.fullyQualified);
        if ((methodNode.access & 0x1) != 0x0) {
            this.setModifiers("public " + typeName);
        }
        else if ((methodNode.access & 0x4) != 0x0) {
            this.setModifiers("protected " + typeName);
        }
        else if ((methodNode.access & 0x2) != 0x0) {
            this.setModifiers("private " + typeName);
        }
        else {
            this.setModifiers(typeName);
        }
    }
    
    public SignaturePrinter setModifiers(final String s) {
        this.modifiers = s.replace("${returnType}", this.getReturnType());
        return this;
    }
    
    public SignaturePrinter setFullyQualified(final boolean fullyQualified) {
        this.fullyQualified = fullyQualified;
        return this;
    }
    
    public boolean isFullyQualified() {
        return this.fullyQualified;
    }
    
    @Override
    public String toString() {
        return this.appendArgs(new StringBuilder().append(this.modifiers).append(" ").append(this.name), false, true).toString();
    }
    
    public String toDescriptor() {
        return this.appendArgs(new StringBuilder(), true, false).append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
    }
    
    private StringBuilder appendArgs(final StringBuilder sb, final boolean b, final boolean b2) {
        sb.append('(');
        for (int i = 0; i < this.argTypes.length; ++i) {
            if (this.argTypes[i] != null) {
                if (i > 0) {
                    sb.append(',');
                    if (b2) {
                        sb.append(' ');
                    }
                }
                try {
                    this.appendType(sb, this.argTypes[i], b ? null : (Strings.isNullOrEmpty(this.argNames[i]) ? ("unnamed" + i) : this.argNames[i]));
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return sb.append(")");
    }
    
    private StringBuilder appendType(final StringBuilder sb, final Type type, final String s) {
        switch (type.getSort()) {
            case 9: {
                return appendArraySuffix(this.appendType(sb, type.getElementType(), s), type);
            }
            case 10: {
                return this.appendType(sb, type.getClassName(), s);
            }
            default: {
                sb.append(getTypeName(type, false, this.fullyQualified));
                if (s != null) {
                    sb.append(' ').append(s);
                }
                return sb;
            }
        }
    }
    
    private StringBuilder appendType(final StringBuilder sb, String substring, final String s) {
        if (!this.fullyQualified) {
            substring = substring.substring(substring.lastIndexOf(46) + 1);
        }
        sb.append(substring);
        if (substring.endsWith("CallbackInfoReturnable")) {
            sb.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
        }
        if (s != null) {
            sb.append(' ').append(s);
        }
        return sb;
    }
    
    public static String getTypeName(final Type type, final boolean b) {
        return getTypeName(type, b, false);
    }
    
    public static String getTypeName(final Type type, final boolean b, final boolean b2) {
        switch (type.getSort()) {
            case 0: {
                return b ? "Void" : "void";
            }
            case 1: {
                return b ? "Boolean" : "boolean";
            }
            case 2: {
                return b ? "Character" : "char";
            }
            case 3: {
                return b ? "Byte" : "byte";
            }
            case 4: {
                return b ? "Short" : "short";
            }
            case 5: {
                return b ? "Integer" : "int";
            }
            case 6: {
                return b ? "Float" : "float";
            }
            case 7: {
                return b ? "Long" : "long";
            }
            case 8: {
                return b ? "Double" : "double";
            }
            case 9: {
                return getTypeName(type.getElementType(), b, b2) + arraySuffix(type);
            }
            case 10: {
                String s = type.getClassName();
                if (!b2) {
                    s = s.substring(s.lastIndexOf(46) + 1);
                }
                return s;
            }
            default: {
                return "Object";
            }
        }
    }
    
    private static String arraySuffix(final Type type) {
        return Strings.repeat("[]", type.getDimensions());
    }
    
    private static StringBuilder appendArraySuffix(final StringBuilder sb, final Type type) {
        for (int i = 0; i < type.getDimensions(); ++i) {
            sb.append("[]");
        }
        return sb;
    }
}
