package net.minecraft.src;

public abstract class McoOption
{
    public abstract Object func_98155_a();
    
    public static McoOptionSome func_98153_a(final Object par0Obj) {
        return new McoOptionSome(par0Obj);
    }
    
    public static McoOptionNone func_98154_b() {
        return new McoOptionNone();
    }
}
