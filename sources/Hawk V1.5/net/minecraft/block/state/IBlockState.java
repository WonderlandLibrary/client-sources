package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public interface IBlockState {
   IBlockState cycleProperty(IProperty var1);

   ImmutableMap getProperties();

   Block getBlock();

   Comparable getValue(IProperty var1);

   Collection getPropertyNames();

   IBlockState withProperty(IProperty var1, Comparable var2);
}
