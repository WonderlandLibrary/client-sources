package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;

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
