package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import java.lang.annotation.*;
import org.spongepowered.asm.util.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;

public class SyntheticBridgeException extends MixinException
{
    private static final long serialVersionUID = 1L;
    private final Problem problem;
    private final String name;
    private final String desc;
    private final int index;
    private final AbstractInsnNode a;
    private final AbstractInsnNode b;
    
    public SyntheticBridgeException(final Problem problem, final String name, final String desc, final int index, final AbstractInsnNode a, final AbstractInsnNode b) {
        super(problem.getMessage(name, desc, index, a, b));
        this.problem = problem;
        this.name = name;
        this.desc = desc;
        this.index = index;
        this.a = a;
        this.b = b;
    }
    
    public void printAnalysis(final IMixinContext mixinContext, final MethodNode methodNode, final MethodNode methodNode2) {
        final PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.addWrapped(100, this.getMessage(), new Object[0]).hr();
        prettyPrinter.add().kv("Method", (Object)(this.name + this.desc)).kv("Problem Type", this.problem).add().hr();
        final String s = Annotations.getValue(Annotations.getVisible(methodNode, MixinMerged.class), "mixin");
        this.printMethod(prettyPrinter.add("Existing method").add().kv("Owner", (Object)((s != null) ? s : mixinContext.getTargetClassRef().replace('/', '.'))).add(), methodNode).hr();
        this.printMethod(prettyPrinter.add("Incoming method").add().kv("Owner", (Object)mixinContext.getClassRef().replace('/', '.')).add(), methodNode2).hr();
        this.printProblem(prettyPrinter, mixinContext, methodNode, methodNode2).print(System.err);
    }
    
    private PrettyPrinter printMethod(final PrettyPrinter prettyPrinter, final MethodNode methodNode) {
        int n = 0;
        final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
        while (iterator.hasNext()) {
            prettyPrinter.kv((n == this.index) ? ">>>>" : "", (Object)Bytecode.describeNode((AbstractInsnNode)iterator.next()));
            ++n;
        }
        return prettyPrinter.add();
    }
    
    private PrettyPrinter printProblem(final PrettyPrinter prettyPrinter, final IMixinContext mixinContext, final MethodNode methodNode, final MethodNode methodNode2) {
        final Type objectType = Type.getObjectType(mixinContext.getTargetClassRef());
        prettyPrinter.add("Analysis").add();
        switch (this.problem) {
            case BAD_INSN: {
                prettyPrinter.add("The bridge methods are not compatible because they contain incompatible opcodes");
                prettyPrinter.add("at index " + this.index + ":").add();
                prettyPrinter.kv("Existing opcode: %s", (Object)Bytecode.getOpcodeName(this.a));
                prettyPrinter.kv("Incoming opcode: %s", (Object)Bytecode.getOpcodeName(this.b)).add();
                prettyPrinter.add("This implies that the bridge methods are from different interfaces. This problem");
                prettyPrinter.add("may not be resolvable without changing the base interfaces.").add();
                break;
            }
            case BAD_LOAD: {
                prettyPrinter.add("The bridge methods are not compatible because they contain different variables at");
                prettyPrinter.add("opcode index " + this.index + ".").add();
                final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                final ListIterator<AbstractInsnNode> iterator2 = methodNode2.instructions.iterator();
                final Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
                final Type[] argumentTypes2 = Type.getArgumentTypes(methodNode2.desc);
                int n = 0;
                while (iterator.hasNext() && iterator2.hasNext()) {
                    final AbstractInsnNode abstractInsnNode = iterator.next();
                    final AbstractInsnNode abstractInsnNode2 = iterator2.next();
                    if (abstractInsnNode instanceof VarInsnNode && abstractInsnNode2 instanceof VarInsnNode) {
                        final VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode;
                        final VarInsnNode varInsnNode2 = (VarInsnNode)abstractInsnNode2;
                        final Type type = (varInsnNode.var > 0) ? argumentTypes[varInsnNode.var - 1] : objectType;
                        final Type type2 = (varInsnNode2.var > 0) ? argumentTypes2[varInsnNode2.var - 1] : objectType;
                        prettyPrinter.kv("Target " + n, "%8s %-2d %s", Bytecode.getOpcodeName(varInsnNode), varInsnNode.var, type);
                        prettyPrinter.kv("Incoming " + n, "%8s %-2d %s", Bytecode.getOpcodeName(varInsnNode2), varInsnNode2.var, type2);
                        if (type.equals(type2)) {
                            prettyPrinter.kv("", "Types match: %s", type);
                        }
                        else if (type.getSort() != type2.getSort()) {
                            prettyPrinter.kv("", (Object)"Types are incompatible");
                        }
                        else if (type.getSort() == 10) {
                            prettyPrinter.kv("", "Common supertype: %s", ClassInfo.getCommonSuperClassOrInterface(type, type2));
                        }
                        prettyPrinter.add();
                    }
                    ++n;
                }
                prettyPrinter.add("Since this probably means that the methods come from different interfaces, you");
                prettyPrinter.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
                prettyPrinter.add("both root interfaces");
                break;
            }
            case BAD_CAST: {
                prettyPrinter.add("Incompatible CHECKCAST encountered at opcode " + this.index + ", this could indicate that the bridge");
                prettyPrinter.add("is casting down for contravariant generic types. It may be possible to coalesce the");
                prettyPrinter.add("bridges by adjusting the types in the target method.").add();
                final Type objectType2 = Type.getObjectType(((TypeInsnNode)this.a).desc);
                final Type objectType3 = Type.getObjectType(((TypeInsnNode)this.b).desc);
                prettyPrinter.kv("Target type", objectType2);
                prettyPrinter.kv("Incoming type", objectType3);
                prettyPrinter.kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(objectType2, objectType3)).add();
                break;
            }
            case BAD_INVOKE_NAME: {
                prettyPrinter.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
                prettyPrinter.add("and implies that a remapping transformer has incorrectly remapped a method. This");
                prettyPrinter.add("is an unrecoverable error.");
                break;
            }
            case BAD_INVOKE_DESC: {
                final MethodInsnNode methodInsnNode = (MethodInsnNode)this.a;
                final MethodInsnNode methodInsnNode2 = (MethodInsnNode)this.b;
                final Type[] argumentTypes3 = Type.getArgumentTypes(methodInsnNode.desc);
                final Type[] argumentTypes4 = Type.getArgumentTypes(methodInsnNode2.desc);
                if (argumentTypes3.length != argumentTypes4.length) {
                    final int length = Type.getArgumentTypes(methodNode.desc).length;
                    final String s = (argumentTypes3.length == length) ? "The TARGET" : ((argumentTypes4.length == length) ? " The INCOMING" : "NEITHER");
                    prettyPrinter.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
                    prettyPrinter.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
                    prettyPrinter.add("Overlapping bridge methods should always have the same number of arguments, yet");
                    prettyPrinter.add("the target method has %d arguments, the incoming method has %d. This is an", argumentTypes3.length, argumentTypes4.length);
                    prettyPrinter.add("unrecoverable error. %s method has the expected arg count of %d", s, length);
                    break;
                }
                final Type returnType = Type.getReturnType(methodInsnNode.desc);
                final Type returnType2 = Type.getReturnType(methodInsnNode2.desc);
                prettyPrinter.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
                prettyPrinter.add("types are incompatible over one or more generic superclasses or interfaces. It may");
                prettyPrinter.add("be possible to adjust the generic types on implemented members to rectify this");
                prettyPrinter.add("problem by coalescing the appropriate generic types.").add();
                this.printTypeComparison(prettyPrinter, "return type", returnType, returnType2);
                for (int i = 0; i < argumentTypes3.length; ++i) {
                    this.printTypeComparison(prettyPrinter, "arg " + i, argumentTypes3[i], argumentTypes4[i]);
                }
                break;
            }
            case BAD_LENGTH: {
                prettyPrinter.add("Mismatched bridge method length implies the bridge methods are incompatible");
                prettyPrinter.add("and may originate from different superinterfaces. This is an unrecoverable");
                prettyPrinter.add("error.").add();
                break;
            }
        }
        return prettyPrinter;
    }
    
    private PrettyPrinter printTypeComparison(final PrettyPrinter prettyPrinter, final String s, final Type type, final Type type2) {
        prettyPrinter.kv("Target " + s, "%s", type);
        prettyPrinter.kv("Incoming " + s, "%s", type2);
        if (type.equals(type2)) {
            prettyPrinter.kv("Analysis", "Types match: %s", type);
        }
        else if (type.getSort() != type2.getSort()) {
            prettyPrinter.kv("Analysis", (Object)"Types are incompatible");
        }
        else if (type.getSort() == 10) {
            prettyPrinter.kv("Analysis", "Common supertype: L%s;", ClassInfo.getCommonSuperClassOrInterface(type, type2));
        }
        return prettyPrinter.add();
    }
    
    public enum Problem
    {
        BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"), 
        BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"), 
        BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"), 
        BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"), 
        BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"), 
        BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");
        
        private final String message;
        private static final Problem[] $VALUES;
        
        public static Problem[] values() {
            return Problem.$VALUES.clone();
        }
        
        public static Problem valueOf(final String s) {
            return Enum.valueOf(Problem.class, s);
        }
        
        private Problem(final String message) {
            this.message = message;
        }
        
        String getMessage(final String s, final String s2, final int n, final AbstractInsnNode abstractInsnNode, final AbstractInsnNode abstractInsnNode2) {
            return String.format(this.message, s, s2, n, Bytecode.getOpcodeName(abstractInsnNode), Bytecode.getOpcodeName(abstractInsnNode), getInsnName(abstractInsnNode), getInsnName(abstractInsnNode2), getInsnDesc(abstractInsnNode), getInsnDesc(abstractInsnNode2));
        }
        
        private static String getInsnName(final AbstractInsnNode abstractInsnNode) {
            return (abstractInsnNode instanceof MethodInsnNode) ? ((MethodInsnNode)abstractInsnNode).name : "";
        }
        
        private static String getInsnDesc(final AbstractInsnNode abstractInsnNode) {
            return (abstractInsnNode instanceof MethodInsnNode) ? ((MethodInsnNode)abstractInsnNode).desc : "";
        }
        
        static {
            $VALUES = new Problem[] { Problem.BAD_INSN, Problem.BAD_LOAD, Problem.BAD_CAST, Problem.BAD_INVOKE_NAME, Problem.BAD_INVOKE_DESC, Problem.BAD_LENGTH };
        }
    }
}
