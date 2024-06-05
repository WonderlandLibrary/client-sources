package net.minecraft.block.properties;

import java.util.*;

public interface IProperty
{
    String getName();
    
    Collection getAllowedValues();
    
    Class getValueClass();
    
    String getName(final Comparable p0);
}
