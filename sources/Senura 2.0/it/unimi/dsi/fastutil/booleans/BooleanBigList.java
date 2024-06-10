package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface BooleanBigList extends BigList<Boolean>, BooleanCollection, Size64, Comparable<BigList<? extends Boolean>> {
  BooleanBigListIterator iterator();
  
  BooleanBigListIterator listIterator();
  
  BooleanBigListIterator listIterator(long paramLong);
  
  BooleanBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, boolean[][] paramArrayOfboolean, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, boolean[][] paramArrayOfboolean);
  
  void addElements(long paramLong1, boolean[][] paramArrayOfboolean, long paramLong2, long paramLong3);
  
  void add(long paramLong, boolean paramBoolean);
  
  boolean addAll(long paramLong, BooleanCollection paramBooleanCollection);
  
  boolean addAll(long paramLong, BooleanBigList paramBooleanBigList);
  
  boolean addAll(BooleanBigList paramBooleanBigList);
  
  boolean getBoolean(long paramLong);
  
  boolean removeBoolean(long paramLong);
  
  boolean set(long paramLong, boolean paramBoolean);
  
  long indexOf(boolean paramBoolean);
  
  long lastIndexOf(boolean paramBoolean);
  
  @Deprecated
  void add(long paramLong, Boolean paramBoolean);
  
  @Deprecated
  Boolean get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Boolean remove(long paramLong);
  
  @Deprecated
  Boolean set(long paramLong, Boolean paramBoolean);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */