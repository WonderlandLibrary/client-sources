package net.minecraft.block.state;

import java.util.*;
import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import net.minecraft.block.*;

public interface IBlockState
{
    Collection getPropertyNames();
    
    Comparable getValue(final IProperty p0);
    
    IBlockState withProperty(final IProperty p0, final Comparable p1);
    
    IBlockState cycleProperty(final IProperty p0);
    
    ImmutableMap getProperties();
    
    Block getBlock();
}
