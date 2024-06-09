package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

@AtCode("RETURN")
public class BeforeReturn extends InjectionPoint
{
    private final int ordinal;
    
    public BeforeReturn(final InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.ordinal = injectionPointData.getOrdinal();
    }
    
    @Override
    public boolean checkPriority(final int n, final int n2) {
        return true;
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
        boolean b = false;
        final int opcode = Type.getReturnType(s).getOpcode(172);
        int n = 0;
        for (final AbstractInsnNode abstractInsnNode : list) {
            if (abstractInsnNode instanceof InsnNode && abstractInsnNode.getOpcode() == opcode) {
                if (this.ordinal == -1 || this.ordinal == n) {
                    collection.add(abstractInsnNode);
                    b = true;
                }
                ++n;
            }
        }
        return b;
    }
}
