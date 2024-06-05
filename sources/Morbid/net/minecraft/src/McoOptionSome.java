package net.minecraft.src;

public final class McoOptionSome extends McoOption
{
    private final Object field_98156_a;
    
    public McoOptionSome(final Object par1Obj) {
        this.field_98156_a = par1Obj;
    }
    
    @Override
    public Object func_98155_a() {
        return this.field_98156_a;
    }
}
