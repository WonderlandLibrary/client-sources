package net.minecraft.util;

public interface IRegistry<K, V> extends Iterable<V> {
  V getObject(K paramK);
  
  void putObject(K paramK, V paramV);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\IRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */