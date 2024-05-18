package net.minecraft.src;

public class RegistryDefaulted extends RegistrySimple
{
    private final Object defaultObject;
    
    public RegistryDefaulted(final Object par1Obj) {
        this.defaultObject = par1Obj;
    }
    
    @Override
    public Object func_82594_a(final Object par1Obj) {
        final Object var2 = super.func_82594_a(par1Obj);
        return (var2 == null) ? this.defaultObject : var2;
    }
}
