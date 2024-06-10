package it.unimi.dsi.fastutil;

import java.util.Iterator;

public interface BidirectionalIterator<K> extends Iterator<K> {
  K previous();
  
  boolean hasPrevious();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\BidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */