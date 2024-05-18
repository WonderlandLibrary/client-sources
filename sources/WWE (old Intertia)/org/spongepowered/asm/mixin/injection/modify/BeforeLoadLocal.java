package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("LOAD")
public class BeforeLoadLocal extends ModifyVariableInjector.ContextualInjectionPoint
{
    private final Type returnType;
    private final LocalVariableDiscriminator discriminator;
    private final int opcode;
    private final int ordinal;
    private boolean opcodeAfter;
    
    protected BeforeLoadLocal(final InjectionPointData injectionPointData) {
        this(injectionPointData, 21, false);
    }
    
    protected BeforeLoadLocal(final InjectionPointData injectionPointData, final int n, final boolean opcodeAfter) {
        super(injectionPointData.getContext());
        this.returnType = injectionPointData.getMethodReturnType();
        this.discriminator = injectionPointData.getLocalVariableDiscriminator();
        this.opcode = injectionPointData.getOpcode(this.returnType.getOpcode(n));
        this.ordinal = injectionPointData.getOrdinal();
        this.opcodeAfter = opcodeAfter;
    }
    
    @Override
    boolean find(final Target target, final Collection<AbstractInsnNode> collection) {
        final SearchState searchState = new SearchState(this.ordinal, this.discriminator.printLVT());
        for (final AbstractInsnNode abstractInsnNode : target.method.instructions) {
            if (searchState.isPendingCheck()) {
                searchState.check(collection, abstractInsnNode, this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, abstractInsnNode));
            }
            else {
                if (!(abstractInsnNode instanceof VarInsnNode) || abstractInsnNode.getOpcode() != this.opcode || (this.ordinal != -1 && searchState.success())) {
                    continue;
                }
                searchState.register((VarInsnNode)abstractInsnNode);
                if (this.opcodeAfter) {
                    searchState.setPendingCheck();
                }
                else {
                    searchState.check(collection, abstractInsnNode, this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, abstractInsnNode));
                }
            }
        }
        return searchState.success();
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection collection) {
        return super.find(s, list, collection);
    }
    
    static class SearchState
    {
        private final boolean print;
        private final int targetOrdinal;
        private int ordinal;
        private boolean pendingCheck;
        private boolean found;
        private VarInsnNode varNode;
        
        SearchState(final int targetOrdinal, final boolean print) {
            super();
            this.ordinal = 0;
            this.pendingCheck = false;
            this.found = false;
            this.targetOrdinal = targetOrdinal;
            this.print = print;
        }
        
        boolean success() {
            return this.found;
        }
        
        boolean isPendingCheck() {
            return this.pendingCheck;
        }
        
        void setPendingCheck() {
            this.pendingCheck = true;
        }
        
        void register(final VarInsnNode varNode) {
            this.varNode = varNode;
        }
        
        void check(final Collection<AbstractInsnNode> collection, final AbstractInsnNode abstractInsnNode, final int n) {
            this.pendingCheck = false;
            if (n != this.varNode.var && (n > -2 || !this.print)) {
                return;
            }
            if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
                collection.add(abstractInsnNode);
                this.found = true;
            }
            ++this.ordinal;
            this.varNode = null;
        }
    }
}
