package net.minecraft.util;

public interface IRegistry extends Iterable
{
    Object getObject(final Object p0);
    
    void putObject(final Object p0, final Object p1);
}
