package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;

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
