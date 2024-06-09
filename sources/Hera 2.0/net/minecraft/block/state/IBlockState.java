package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public interface IBlockState {
  Collection<IProperty> getPropertyNames();
  
  <T extends Comparable<T>> T getValue(IProperty<T> paramIProperty);
  
  <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> paramIProperty, V paramV);
  
  <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> paramIProperty);
  
  ImmutableMap<IProperty, Comparable> getProperties();
  
  Block getBlock();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\state\IBlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */