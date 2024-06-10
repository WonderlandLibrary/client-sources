package it.unimi.dsi.fastutil.objects;

import java.util.Collection;
import java.util.Iterator;

public interface ObjectCollection<K> extends Collection<K>, ObjectIterable<K> {
  ObjectIterator<K> iterator();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */