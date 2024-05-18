package net.minecraft.block.properties;

import java.util.*;

public interface IProperty<T extends Comparable<T>>
{
    String getName(final T p0);
    
    Class<T> getValueClass();
    
    String getName();
    
    Collection<T> getAllowedValues();
}
