package gnu.trove.impl.unmodifiable;

import gnu.trove.TByteCollection;
import gnu.trove.TCollections;
import gnu.trove.function.TByteFunction;
import gnu.trove.iterator.TLongByteIterator;
import gnu.trove.map.TLongByteMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TLongByteProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.set.TLongSet;
import java.io.Serializable;
import java.util.Map;

public class TUnmodifiableLongByteMap implements TLongByteMap, Serializable {
   private static final long serialVersionUID = -1034234728574286014L;
   private final TLongByteMap m;
   private transient TLongSet keySet = null;
   private transient TByteCollection values = null;

   public TUnmodifiableLongByteMap(TLongByteMap var1) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.m = var1;
      }
   }

   public int size() {
      return this.m.size();
   }

   public boolean isEmpty() {
      return this.m.isEmpty();
   }

   public boolean containsKey(long var1) {
      return this.m.containsKey(var1);
   }

   public boolean containsValue(byte var1) {
      return this.m.containsValue(var1);
   }

   public byte get(long var1) {
      return this.m.get(var1);
   }

   public byte put(long var1, byte var3) {
      throw new UnsupportedOperationException();
   }

   public byte remove(long var1) {
      throw new UnsupportedOperationException();
   }

   public void putAll(TLongByteMap var1) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map var1) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public TLongSet keySet() {
      if (this.keySet == null) {
         this.keySet = TCollections.unmodifiableSet(this.m.keySet());
      }

      return this.keySet;
   }

   public long[] keys() {
      return this.m.keys();
   }

   public long[] keys(long[] var1) {
      return this.m.keys(var1);
   }

   public TByteCollection valueCollection() {
      if (this.values == null) {
         this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
      }

      return this.values;
   }

   public byte[] values() {
      return this.m.values();
   }

   public byte[] values(byte[] var1) {
      return this.m.values(var1);
   }

   public boolean equals(Object var1) {
      return var1 == this || this.m.equals(var1);
   }

   public int hashCode() {
      return this.m.hashCode();
   }

   public String toString() {
      return this.m.toString();
   }

   public long getNoEntryKey() {
      return this.m.getNoEntryKey();
   }

   public byte getNoEntryValue() {
      return this.m.getNoEntryValue();
   }

   public boolean forEachKey(TLongProcedure var1) {
      return this.m.forEachKey(var1);
   }

   public boolean forEachValue(TByteProcedure var1) {
      return this.m.forEachValue(var1);
   }

   public boolean forEachEntry(TLongByteProcedure var1) {
      return this.m.forEachEntry(var1);
   }

   public TLongByteIterator iterator() {
      return new TLongByteIterator(this) {
         TLongByteIterator iter;
         final TUnmodifiableLongByteMap this$0;

         {
            this.this$0 = var1;
            this.iter = TUnmodifiableLongByteMap.access$000(this.this$0).iterator();
         }

         public long key() {
            return this.iter.key();
         }

         public byte value() {
            return this.iter.value();
         }

         public void advance() {
            this.iter.advance();
         }

         public boolean hasNext() {
            return this.iter.hasNext();
         }

         public byte setValue(byte var1) {
            throw new UnsupportedOperationException();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public byte putIfAbsent(long var1, byte var3) {
      throw new UnsupportedOperationException();
   }

   public void transformValues(TByteFunction var1) {
      throw new UnsupportedOperationException();
   }

   public boolean retainEntries(TLongByteProcedure var1) {
      throw new UnsupportedOperationException();
   }

   public boolean increment(long var1) {
      throw new UnsupportedOperationException();
   }

   public boolean adjustValue(long var1, byte var3) {
      throw new UnsupportedOperationException();
   }

   public byte adjustOrPutValue(long var1, byte var3, byte var4) {
      throw new UnsupportedOperationException();
   }

   static TLongByteMap access$000(TUnmodifiableLongByteMap var0) {
      return var0.m;
   }
}
