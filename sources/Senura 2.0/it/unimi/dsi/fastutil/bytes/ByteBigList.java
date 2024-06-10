package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface ByteBigList extends BigList<Byte>, ByteCollection, Size64, Comparable<BigList<? extends Byte>> {
  ByteBigListIterator iterator();
  
  ByteBigListIterator listIterator();
  
  ByteBigListIterator listIterator(long paramLong);
  
  ByteBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, byte[][] paramArrayOfbyte, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, byte[][] paramArrayOfbyte);
  
  void addElements(long paramLong1, byte[][] paramArrayOfbyte, long paramLong2, long paramLong3);
  
  void add(long paramLong, byte paramByte);
  
  boolean addAll(long paramLong, ByteCollection paramByteCollection);
  
  boolean addAll(long paramLong, ByteBigList paramByteBigList);
  
  boolean addAll(ByteBigList paramByteBigList);
  
  byte getByte(long paramLong);
  
  byte removeByte(long paramLong);
  
  byte set(long paramLong, byte paramByte);
  
  long indexOf(byte paramByte);
  
  long lastIndexOf(byte paramByte);
  
  @Deprecated
  void add(long paramLong, Byte paramByte);
  
  @Deprecated
  Byte get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Byte remove(long paramLong);
  
  @Deprecated
  Byte set(long paramLong, Byte paramByte);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */