package net.minecraft.util;

public class RegistryDefaulted extends RegistrySimple
{
    /**
     * Default object for this registry, returned when an object is not found.
     */
    private final Object defaultObject;


    public RegistryDefaulted(Object p_i1366_1_)
    {
        this.defaultObject = p_i1366_1_;
    }

    public Object getObject(Object p_82594_1_)
    {
        Object var2 = super.getObject(p_82594_1_);
        return var2 == null ? this.defaultObject : var2;
    }
}
