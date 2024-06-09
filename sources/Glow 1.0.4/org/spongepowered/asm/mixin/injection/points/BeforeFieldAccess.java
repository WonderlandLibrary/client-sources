package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import java.util.*;

@AtCode("FIELD")
public class BeforeFieldAccess extends BeforeInvoke
{
    private static final String ARRAY_GET = "get";
    private static final String ARRAY_SET = "set";
    private static final String ARRAY_LENGTH = "length";
    public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
    private final int opcode;
    private final int arrOpcode;
    private final int fuzzFactor;
    
    public BeforeFieldAccess(final InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.opcode = injectionPointData.getOpcode(-1, 180, 181, 178, 179, -1);
        final String value = injectionPointData.get("array", "");
        this.arrOpcode = ("get".equalsIgnoreCase(value) ? 46 : ("set".equalsIgnoreCase(value) ? 79 : ("length".equalsIgnoreCase(value) ? 190 : 0)));
        this.fuzzFactor = Math.min(Math.max(injectionPointData.get("fuzz", 8), 1), 32);
    }
    
    public int getFuzzFactor() {
        return this.fuzzFactor;
    }
    
    public int getArrayOpcode() {
        return this.arrOpcode;
    }
    
    private int getArrayOpcode(final String s) {
        if (this.arrOpcode != 190) {
            return Type.getType(s).getElementType().getOpcode(this.arrOpcode);
        }
        return this.arrOpcode;
    }
    
    @Override
    protected boolean matchesInsn(final AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode instanceof FieldInsnNode && (((FieldInsnNode)abstractInsnNode).getOpcode() == this.opcode || this.opcode == -1) && (this.arrOpcode == 0 || ((abstractInsnNode.getOpcode() == 178 || abstractInsnNode.getOpcode() == 180) && Type.getType(((FieldInsnNode)abstractInsnNode).desc).getSort() == 9));
    }
    
    @Override
    protected boolean addInsn(final InsnList list, final Collection<AbstractInsnNode> collection, final AbstractInsnNode abstractInsnNode) {
        if (this.arrOpcode > 0) {
            final FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
            final int arrayOpcode = this.getArrayOpcode(fieldInsnNode.desc);
            this.log("{} > > > > searching for array access opcode {} fuzz={}", this.className, Bytecode.getOpcodeName(arrayOpcode), this.fuzzFactor);
            if (findArrayNode(list, fieldInsnNode, arrayOpcode, this.fuzzFactor) == null) {
                this.log("{} > > > > > failed to locate matching insn", this.className);
                return false;
            }
        }
        this.log("{} > > > > > adding matching insn", this.className);
        return super.addInsn(list, collection, abstractInsnNode);
    }
    
    public static AbstractInsnNode findArrayNode(final InsnList list, final FieldInsnNode fieldInsnNode, final int n, final int n2) {
        int n3 = 0;
        final ListIterator<AbstractInsnNode> iterator = list.iterator(list.indexOf(fieldInsnNode) + 1);
        while (iterator.hasNext()) {
            final AbstractInsnNode abstractInsnNode = iterator.next();
            if (abstractInsnNode.getOpcode() == n) {
                return abstractInsnNode;
            }
            if (abstractInsnNode.getOpcode() == 190 && n3 == 0) {
                return null;
            }
            if (abstractInsnNode instanceof FieldInsnNode) {
                final FieldInsnNode fieldInsnNode2 = (FieldInsnNode)abstractInsnNode;
                if (fieldInsnNode2.desc.equals(fieldInsnNode.desc) && fieldInsnNode2.name.equals(fieldInsnNode.name) && fieldInsnNode2.owner.equals(fieldInsnNode.owner)) {
                    return null;
                }
            }
            if (n3++ > n2) {
                return null;
            }
        }
        return null;
    }
}
