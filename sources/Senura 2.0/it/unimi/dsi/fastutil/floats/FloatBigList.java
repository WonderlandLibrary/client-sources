package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface FloatBigList extends BigList<Float>, FloatCollection, Size64, Comparable<BigList<? extends Float>> {
  FloatBigListIterator iterator();
  
  FloatBigListIterator listIterator();
  
  FloatBigListIterator listIterator(long paramLong);
  
  FloatBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, float[][] paramArrayOffloat, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, float[][] paramArrayOffloat);
  
  void addElements(long paramLong1, float[][] paramArrayOffloat, long paramLong2, long paramLong3);
  
  void add(long paramLong, float paramFloat);
  
  boolean addAll(long paramLong, FloatCollection paramFloatCollection);
  
  boolean addAll(long paramLong, FloatBigList paramFloatBigList);
  
  boolean addAll(FloatBigList paramFloatBigList);
  
  float getFloat(long paramLong);
  
  float removeFloat(long paramLong);
  
  float set(long paramLong, float paramFloat);
  
  long indexOf(float paramFloat);
  
  long lastIndexOf(float paramFloat);
  
  @Deprecated
  void add(long paramLong, Float paramFloat);
  
  @Deprecated
  Float get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Float remove(long paramLong);
  
  @Deprecated
  Float set(long paramLong, Float paramFloat);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */