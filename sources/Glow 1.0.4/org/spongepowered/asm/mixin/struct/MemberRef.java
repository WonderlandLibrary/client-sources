package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

public abstract class MemberRef
{
    private static final int[] H_OPCODES;
    
    public MemberRef() {
        super();
    }
    
    public abstract boolean isField();
    
    public abstract int getOpcode();
    
    public abstract void setOpcode(final int p0);
    
    public abstract String getOwner();
    
    public abstract void setOwner(final String p0);
    
    public abstract String getName();
    
    public abstract void setName(final String p0);
    
    public abstract String getDesc();
    
    public abstract void setDesc(final String p0);
    
    @Override
    public String toString() {
        return String.format("%s for %s.%s%s%s", Bytecode.getOpcodeName(this.getOpcode()), this.getOwner(), this.getName(), this.isField() ? ":" : "", this.getDesc());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MemberRef)) {
            return false;
        }
        final MemberRef memberRef = (MemberRef)o;
        return this.getOpcode() == memberRef.getOpcode() && this.getOwner().equals(memberRef.getOwner()) && this.getName().equals(memberRef.getName()) && this.getDesc().equals(memberRef.getDesc());
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    static int opcodeFromTag(final int n) {
        return (n >= 0 && n < MemberRef.H_OPCODES.length) ? MemberRef.H_OPCODES[n] : 0;
    }
    
    static int tagFromOpcode(final int n) {
        for (int i = 1; i < MemberRef.H_OPCODES.length; ++i) {
            if (MemberRef.H_OPCODES[i] == n) {
                return i;
            }
        }
        return 0;
    }
    
    static {
        H_OPCODES = new int[] { 0, 180, 178, 181, 179, 182, 184, 183, 183, 185 };
    }
    
    public static final class Method extends MemberRef
    {
        private static final int OPCODES = 191;
        public final MethodInsnNode insn;
        
        public Method(final MethodInsnNode insn) {
            super();
            this.insn = insn;
        }
        
        @Override
        public boolean isField() {
            return false;
        }
        
        @Override
        public int getOpcode() {
            return this.insn.getOpcode();
        }
        
        @Override
        public void setOpcode(final int opcode) {
            if ((opcode & 0xBF) == 0x0) {
                throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(opcode));
            }
            this.insn.setOpcode(opcode);
        }
        
        @Override
        public String getOwner() {
            return this.insn.owner;
        }
        
        @Override
        public void setOwner(final String owner) {
            this.insn.owner = owner;
        }
        
        @Override
        public String getName() {
            return this.insn.name;
        }
        
        @Override
        public void setName(final String name) {
            this.insn.name = name;
        }
        
        @Override
        public String getDesc() {
            return this.insn.desc;
        }
        
        @Override
        public void setDesc(final String desc) {
            this.insn.desc = desc;
        }
    }
    
    public static final class Field extends MemberRef
    {
        private static final int OPCODES = 183;
        public final FieldInsnNode insn;
        
        public Field(final FieldInsnNode insn) {
            super();
            this.insn = insn;
        }
        
        @Override
        public boolean isField() {
            return true;
        }
        
        @Override
        public int getOpcode() {
            return this.insn.getOpcode();
        }
        
        @Override
        public void setOpcode(final int opcode) {
            if ((opcode & 0xB7) == 0x0) {
                throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(opcode));
            }
            this.insn.setOpcode(opcode);
        }
        
        @Override
        public String getOwner() {
            return this.insn.owner;
        }
        
        @Override
        public void setOwner(final String owner) {
            this.insn.owner = owner;
        }
        
        @Override
        public String getName() {
            return this.insn.name;
        }
        
        @Override
        public void setName(final String name) {
            this.insn.name = name;
        }
        
        @Override
        public String getDesc() {
            return this.insn.desc;
        }
        
        @Override
        public void setDesc(final String desc) {
            this.insn.desc = desc;
        }
    }
    
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
}
