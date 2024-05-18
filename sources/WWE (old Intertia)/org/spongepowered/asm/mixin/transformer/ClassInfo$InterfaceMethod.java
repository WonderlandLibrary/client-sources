package org.spongepowered.asm.mixin.transformer;

public class InterfaceMethod extends Method
{
    private final ClassInfo owner;
    final ClassInfo this$0;
    
    public InterfaceMethod(final ClassInfo this$0, final Member member) {
        this.this$0 = this$0.super(member);
        this.owner = member.getOwner();
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.owner;
    }
    
    @Override
    public ClassInfo getImplementor() {
        return this.this$0;
    }
}
