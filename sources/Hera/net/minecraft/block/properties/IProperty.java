package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty<T extends Comparable<T>> {
  String getName();
  
  Collection<T> getAllowedValues();
  
  Class<T> getValueClass();
  
  String getName(T paramT);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\properties\IProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */