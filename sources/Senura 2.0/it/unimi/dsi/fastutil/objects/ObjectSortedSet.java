package it.unimi.dsi.fastutil.objects;

import java.util.Iterator;
import java.util.SortedSet;

public interface ObjectSortedSet<K> extends ObjectSet<K>, SortedSet<K>, ObjectBidirectionalIterable<K> {
  ObjectBidirectionalIterator<K> iterator(K paramK);
  
  ObjectBidirectionalIterator<K> iterator();
  
  ObjectSortedSet<K> subSet(K paramK1, K paramK2);
  
  ObjectSortedSet<K> headSet(K paramK);
  
  ObjectSortedSet<K> tailSet(K paramK);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectSortedSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */