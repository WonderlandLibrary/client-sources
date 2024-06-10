package it.unimi.dsi.fastutil.objects;

import java.util.Iterator;
import java.util.Set;

public interface ObjectSet<K> extends ObjectCollection<K>, Set<K> {
  ObjectIterator<K> iterator();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */