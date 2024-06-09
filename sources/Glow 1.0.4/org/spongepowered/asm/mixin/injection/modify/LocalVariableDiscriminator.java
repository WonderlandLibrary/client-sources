package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;

public class LocalVariableDiscriminator
{
    private final boolean argsOnly;
    private final int ordinal;
    private final int index;
    private final Set<String> names;
    private final boolean print;
    
    public LocalVariableDiscriminator(final boolean argsOnly, final int ordinal, final int index, final Set<String> set, final boolean print) {
        super();
        this.argsOnly = argsOnly;
        this.ordinal = ordinal;
        this.index = index;
        this.names = Collections.unmodifiableSet((Set<? extends String>)set);
        this.print = print;
    }
    
    public boolean isArgsOnly() {
        return this.argsOnly;
    }
    
    public int getOrdinal() {
        return this.ordinal;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public Set<String> getNames() {
        return this.names;
    }
    
    public boolean hasNames() {
        return !this.names.isEmpty();
    }
    
    public boolean printLVT() {
        return this.print;
    }
    
    protected boolean isImplicit(final Context context) {
        return this.ordinal < 0 && this.index < context.baseArgIndex && this.names.isEmpty();
    }
    
    public int findLocal(final Type type, final boolean b, final Target target, final AbstractInsnNode abstractInsnNode) {
        try {
            return this.findLocal(new Context(type, b, target, abstractInsnNode));
        }
        catch (InvalidImplicitDiscriminatorException ex) {
            return -2;
        }
    }
    
    public int findLocal(final Context context) {
        if (this.isImplicit(context)) {
            return this.findImplicitLocal(context);
        }
        return this.findExplicitLocal(context);
    }
    
    private int findImplicitLocal(final Context context) {
        int n = 0;
        int n2 = 0;
        for (int i = context.baseArgIndex; i < context.locals.length; ++i) {
            final Context.Local local = context.locals[i];
            if (local != null) {
                if (local.type.equals(context.returnType)) {
                    ++n2;
                    n = i;
                }
            }
        }
        if (n2 == 1) {
            return n;
        }
        throw new InvalidImplicitDiscriminatorException("Found " + n2 + " candidate variables but exactly 1 is required.");
    }
    
    private int findExplicitLocal(final Context context) {
        for (int i = context.baseArgIndex; i < context.locals.length; ++i) {
            final Context.Local local = context.locals[i];
            if (local != null) {
                if (local.type.equals(context.returnType)) {
                    if (this.ordinal > -1) {
                        if (this.ordinal == local.ord) {
                            return i;
                        }
                    }
                    else if (this.index >= context.baseArgIndex) {
                        if (this.index == i) {
                            return i;
                        }
                    }
                    else if (this.names.contains(local.name)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static LocalVariableDiscriminator parse(final AnnotationNode annotationNode) {
        final boolean booleanValue = Annotations.getValue(annotationNode, "argsOnly", Boolean.FALSE);
        final int intValue = Annotations.getValue(annotationNode, "ordinal", -1);
        final int intValue2 = Annotations.getValue(annotationNode, "index", -1);
        final boolean booleanValue2 = Annotations.getValue(annotationNode, "print", Boolean.FALSE);
        final HashSet<Object> set = new HashSet<Object>();
        final List<? extends String> list = Annotations.getValue(annotationNode, "name", (List<? extends String>)null);
        if (list != null) {
            set.addAll(list);
        }
        return new LocalVariableDiscriminator(booleanValue, intValue, intValue2, (Set<String>)set, booleanValue2);
    }
    
    public static class Context implements PrettyPrinter.IPrettyPrintable
    {
        final Target target;
        final Type returnType;
        final AbstractInsnNode node;
        final int baseArgIndex;
        final Local[] locals;
        private final boolean isStatic;
        
        public Context(final Type returnType, final boolean b, final Target target, final AbstractInsnNode node) {
            super();
            this.isStatic = Bytecode.methodIsStatic(target.method);
            this.returnType = returnType;
            this.target = target;
            this.node = node;
            this.baseArgIndex = (this.isStatic ? 0 : 1);
            this.locals = this.initLocals(target, b, node);
            this.initOrdinals();
        }
        
        private Local[] initLocals(final Target target, final boolean b, final AbstractInsnNode abstractInsnNode) {
            if (!b) {
                final LocalVariableNode[] locals = Locals.getLocalsAt(target.classNode, target.method, abstractInsnNode);
                if (locals != null) {
                    final Local[] array = new Local[locals.length];
                    for (int i = 0; i < locals.length; ++i) {
                        if (locals[i] != null) {
                            array[i] = new Local(locals[i].name, Type.getType(locals[i].desc));
                        }
                    }
                    return array;
                }
            }
            final Local[] array2 = new Local[this.baseArgIndex + target.arguments.length];
            if (!this.isStatic) {
                array2[0] = new Local("this", Type.getType(target.classNode.name));
            }
            for (int j = this.baseArgIndex; j < array2.length; ++j) {
                array2[j] = new Local("arg" + j, target.arguments[j - this.baseArgIndex]);
            }
            return array2;
        }
        
        private void initOrdinals() {
            final HashMap<Type, Integer> hashMap = (HashMap<Type, Integer>)new HashMap<Object, Integer>();
            for (int i = 0; i < this.locals.length; ++i) {
                0;
                if (this.locals[i] != null) {
                    final Integer n = hashMap.get(this.locals[i].type);
                    final Integer value;
                    hashMap.put(this.locals[i].type, value = ((n == null) ? 0 : (n + 1)));
                    this.locals[i].ord = value;
                }
            }
        }
        
        @Override
        public void print(final PrettyPrinter prettyPrinter) {
            prettyPrinter.add("%5s  %7s  %30s  %-50s  %s", "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE");
            for (int i = this.baseArgIndex; i < this.locals.length; ++i) {
                final Local local = this.locals[i];
                if (local != null) {
                    final Type type = local.type;
                    prettyPrinter.add("[%3d]    [%3d]  %30s  %-50s  %s", i, local.ord, SignaturePrinter.getTypeName(type, false), local.name, this.returnType.equals(type) ? "YES" : "-");
                }
                else if (i > 0) {
                    final Local local2 = this.locals[i - 1];
                    prettyPrinter.add("[%3d]           %30s", i, (local2 != null && local2.type != null && local2.type.getSize() > 1) ? "<top>" : "-");
                }
            }
        }
        
        public class Local
        {
            int ord;
            String name;
            Type type;
            final Context this$0;
            
            public Local(final Context this$0, final String name, final Type type) {
                this.this$0 = this$0;
                super();
                this.ord = 0;
                this.name = name;
                this.type = type;
            }
            
            @Override
            public String toString() {
                return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
            }
        }
    }
}
