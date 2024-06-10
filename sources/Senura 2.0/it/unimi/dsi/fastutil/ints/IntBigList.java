package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface IntBigList extends BigList<Integer>, IntCollection, Size64, Comparable<BigList<? extends Integer>> {
  IntBigListIterator iterator();
  
  IntBigListIterator listIterator();
  
  IntBigListIterator listIterator(long paramLong);
  
  IntBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, int[][] paramArrayOfint, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, int[][] paramArrayOfint);
  
  void addElements(long paramLong1, int[][] paramArrayOfint, long paramLong2, long paramLong3);
  
  void add(long paramLong, int paramInt);
  
  boolean addAll(long paramLong, IntCollection paramIntCollection);
  
  boolean addAll(long paramLong, IntBigList paramIntBigList);
  
  boolean addAll(IntBigList paramIntBigList);
  
  int getInt(long paramLong);
  
  int removeInt(long paramLong);
  
  int set(long paramLong, int paramInt);
  
  long indexOf(int paramInt);
  
  long lastIndexOf(int paramInt);
  
  @Deprecated
  void add(long paramLong, Integer paramInteger);
  
  @Deprecated
  Integer get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Integer remove(long paramLong);
  
  @Deprecated
  Integer set(long paramLong, Integer paramInteger);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */