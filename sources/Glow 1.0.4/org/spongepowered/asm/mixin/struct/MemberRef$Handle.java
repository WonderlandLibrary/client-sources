package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.util.*;

public static final class Handle extends MemberRef
{
    private org.spongepowered.asm.lib.Handle handle;
    
    public Handle(final org.spongepowered.asm.lib.Handle handle) {
        super();
        this.handle = handle;
    }
    
    public org.spongepowered.asm.lib.Handle getMethodHandle() {
        return this.handle;
    }
    
    @Override
    public boolean isField() {
        switch (this.handle.getTag()) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: {
                return false;
            }
            case 1:
            case 2:
            case 3:
            case 4: {
                return true;
            }
            default: {
                throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
            }
        }
    }
    
    @Override
    public int getOpcode() {
        final int opcodeFromTag = MemberRef.opcodeFromTag(this.handle.getTag());
        if (opcodeFromTag == 0) {
            throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
        }
        return opcodeFromTag;
    }
    
    @Override
    public void setOpcode(final int n) {
        final int tagFromOpcode = MemberRef.tagFromOpcode(n);
        if (tagFromOpcode == 0) {
            throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(n) + " for method handle " + this.handle + ".");
        }
        this.handle = new org.spongepowered.asm.lib.Handle(tagFromOpcode, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), tagFromOpcode == 9);
    }
    
    @Override
    public String getOwner() {
        return this.handle.getOwner();
    }
    
    @Override
    public void setOwner(final String s) {
        this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), s, this.handle.getName(), this.handle.getDesc(), this.handle.getTag() == 9);
    }
    
    @Override
    public String getName() {
        return this.handle.getName();
    }
    
    @Override
    public void setName(final String s) {
        this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), s, this.handle.getDesc(), this.handle.getTag() == 9);
    }
    
    @Override
    public String getDesc() {
        return this.handle.getDesc();
    }
    
    @Override
    public void setDesc(final String s) {
        this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), s, this.handle.getTag() == 9);
    }
}
