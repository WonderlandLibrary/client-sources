package it.unimi.dsi.fastutil.objects;

import java.util.Iterator;

public abstract class AbstractReferenceSortedSet<K> extends AbstractReferenceSet<K> implements ReferenceSortedSet<K> {
  public abstract ObjectBidirectionalIterator<K> iterator();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceSortedSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */