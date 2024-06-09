package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.*;

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
