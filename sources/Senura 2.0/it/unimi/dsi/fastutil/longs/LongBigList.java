package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface LongBigList extends BigList<Long>, LongCollection, Size64, Comparable<BigList<? extends Long>> {
  LongBigListIterator iterator();
  
  LongBigListIterator listIterator();
  
  LongBigListIterator listIterator(long paramLong);
  
  LongBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, long[][] paramArrayOflong, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, long[][] paramArrayOflong);
  
  void addElements(long paramLong1, long[][] paramArrayOflong, long paramLong2, long paramLong3);
  
  void add(long paramLong1, long paramLong2);
  
  boolean addAll(long paramLong, LongCollection paramLongCollection);
  
  boolean addAll(long paramLong, LongBigList paramLongBigList);
  
  boolean addAll(LongBigList paramLongBigList);
  
  long getLong(long paramLong);
  
  long removeLong(long paramLong);
  
  long set(long paramLong1, long paramLong2);
  
  long indexOf(long paramLong);
  
  long lastIndexOf(long paramLong);
  
  @Deprecated
  void add(long paramLong, Long paramLong1);
  
  @Deprecated
  Long get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Long remove(long paramLong);
  
  @Deprecated
  Long set(long paramLong, Long paramLong1);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */