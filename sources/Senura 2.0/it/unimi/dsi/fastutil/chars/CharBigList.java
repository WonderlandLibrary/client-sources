package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import java.util.Iterator;

public interface CharBigList extends BigList<Character>, CharCollection, Size64, Comparable<BigList<? extends Character>> {
  CharBigListIterator iterator();
  
  CharBigListIterator listIterator();
  
  CharBigListIterator listIterator(long paramLong);
  
  CharBigList subList(long paramLong1, long paramLong2);
  
  void getElements(long paramLong1, char[][] paramArrayOfchar, long paramLong2, long paramLong3);
  
  void removeElements(long paramLong1, long paramLong2);
  
  void addElements(long paramLong, char[][] paramArrayOfchar);
  
  void addElements(long paramLong1, char[][] paramArrayOfchar, long paramLong2, long paramLong3);
  
  void add(long paramLong, char paramChar);
  
  boolean addAll(long paramLong, CharCollection paramCharCollection);
  
  boolean addAll(long paramLong, CharBigList paramCharBigList);
  
  boolean addAll(CharBigList paramCharBigList);
  
  char getChar(long paramLong);
  
  char removeChar(long paramLong);
  
  char set(long paramLong, char paramChar);
  
  long indexOf(char paramChar);
  
  long lastIndexOf(char paramChar);
  
  @Deprecated
  void add(long paramLong, Character paramCharacter);
  
  @Deprecated
  Character get(long paramLong);
  
  @Deprecated
  long indexOf(Object paramObject);
  
  @Deprecated
  long lastIndexOf(Object paramObject);
  
  @Deprecated
  Character remove(long paramLong);
  
  @Deprecated
  Character set(long paramLong, Character paramCharacter);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */