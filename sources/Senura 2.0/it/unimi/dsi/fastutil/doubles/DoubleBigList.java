package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface DoubleBigList extends BigList<Double>, DoubleCollection, Size64, Comparable<BigList<? extends Double>> {
  DoubleBigListIterator iterator();
  
  DoubleBigListIterator listIterator();
  
  DoubleBigListIterator listIterator(long paramLong);
  
  DoubleBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, double[][] paramArrayOfdouble, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, double[][] paramArrayOfdouble);
  
  void addElements(long paramLong1, double[][] paramArrayOfdouble, long paramLong2, long paramLong3);
  
  void add(long paramLong, double paramDouble);
  
  boolean addAll(long paramLong, DoubleCollection paramDoubleCollection);
  
  boolean addAll(long paramLong, DoubleBigList paramDoubleBigList);
  
  boolean addAll(DoubleBigList paramDoubleBigList);
  
  double getDouble(long paramLong);
  
  double removeDouble(long paramLong);
  
  double set(long paramLong, double paramDouble);
  
  long indexOf(double paramDouble);
  
  long lastIndexOf(double paramDouble);
  
  @Deprecated
  void add(long paramLong, Double paramDouble);
  
  @Deprecated
  Double get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Double remove(long paramLong);
  
  @Deprecated
  Double set(long paramLong, Double paramDouble);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */