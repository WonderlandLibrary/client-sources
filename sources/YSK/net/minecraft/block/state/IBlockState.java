package net.minecraft.block.state;

import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.block.*;

public interface IBlockState
{
     <T extends Comparable<T>> T getValue(final IProperty<T> p0);
    
     <T extends Comparable<T>, V extends T> IBlockState withProperty(final IProperty<T> p0, final V p1);
    
    ImmutableMap<IProperty, Comparable> getProperties();
    
    Collection<IProperty> getPropertyNames();
    
    Block getBlock();
    
     <T extends Comparable<T>> IBlockState cycleProperty(final IProperty<T> p0);
}
