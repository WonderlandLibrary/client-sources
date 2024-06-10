package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface ShortBigList extends BigList<Short>, ShortCollection, Size64, Comparable<BigList<? extends Short>> {
  ShortBigListIterator iterator();
  
  ShortBigListIterator listIterator();
  
  ShortBigListIterator listIterator(long paramLong);
  
  ShortBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, short[][] paramArrayOfshort, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, short[][] paramArrayOfshort);
  
  void addElements(long paramLong1, short[][] paramArrayOfshort, long paramLong2, long paramLong3);
  
  void add(long paramLong, short paramShort);
  
  boolean addAll(long paramLong, ShortCollection paramShortCollection);
  
  boolean addAll(long paramLong, ShortBigList paramShortBigList);
  
  boolean addAll(ShortBigList paramShortBigList);
  
  short getShort(long paramLong);
  
  short removeShort(long paramLong);
  
  short set(long paramLong, short paramShort);
  
  long indexOf(short paramShort);
  
  long lastIndexOf(short paramShort);
  
  @Deprecated
  void add(long paramLong, Short paramShort);
  
  @Deprecated
  Short get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Short remove(long paramLong);
  
  @Deprecated
  Short set(long paramLong, Short paramShort);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */