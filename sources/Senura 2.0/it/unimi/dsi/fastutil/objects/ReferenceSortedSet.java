package it.unimi.dsi.fastutil.objects;

import java.util.Iterator;
import java.util.SortedSet;

public interface ReferenceSortedSet<K> extends ReferenceSet<K>, SortedSet<K>, ObjectBidirectionalIterable<K> {
  ObjectBidirectionalIterator<K> iterator(K paramK);
  
  ObjectBidirectionalIterator<K> iterator();
  
  ReferenceSortedSet<K> subSet(K paramK1, K paramK2);
  
  ReferenceSortedSet<K> headSet(K paramK);
  
  ReferenceSortedSet<K> tailSet(K paramK);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceSortedSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */