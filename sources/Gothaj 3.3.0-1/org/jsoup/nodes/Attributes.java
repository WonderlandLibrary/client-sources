package org.jsoup.nodes;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;

public class Attributes implements Iterable<Attribute>, Cloneable {
   protected static final String dataPrefix = "data-";
   static final char InternalPrefix = '/';
   private static final int InitialCapacity = 3;
   private static final int GrowthFactor = 2;
   static final int NotFound = -1;
   private static final String EmptyString = "";
   private int size = 0;
   String[] keys = new String[3];
   Object[] vals = new Object[3];

   private void checkCapacity(int minNewSize) {
      Validate.isTrue(minNewSize >= this.size);
      int curCap = this.keys.length;
      if (curCap < minNewSize) {
         int newCap = curCap >= 3 ? this.size * 2 : 3;
         if (minNewSize > newCap) {
            newCap = minNewSize;
         }

         this.keys = Arrays.copyOf(this.keys, newCap);
         this.vals = Arrays.copyOf(this.vals, newCap);
      }
   }

   int indexOfKey(String key) {
      Validate.notNull(key);

      for (int i = 0; i < this.size; i++) {
         if (key.equals(this.keys[i])) {
            return i;
         }
      }

      return -1;
   }

   private int indexOfKeyIgnoreCase(String key) {
      Validate.notNull(key);

      for (int i = 0; i < this.size; i++) {
         if (key.equalsIgnoreCase(this.keys[i])) {
            return i;
         }
      }

      return -1;
   }

   static String checkNotNull(@Nullable Object val) {
      return val == null ? "" : (String)val;
   }

   public String get(String key) {
      int i = this.indexOfKey(key);
      return i == -1 ? "" : checkNotNull(this.vals[i]);
   }

   public String getIgnoreCase(String key) {
      int i = this.indexOfKeyIgnoreCase(key);
      return i == -1 ? "" : checkNotNull(this.vals[i]);
   }

   @Nullable
   Object getUserData(String key) {
      Validate.notNull(key);
      if (!this.isInternalKey(key)) {
         key = internalKey(key);
      }

      int i = this.indexOfKeyIgnoreCase(key);
      return i == -1 ? null : this.vals[i];
   }

   public Attributes add(String key, @Nullable String value) {
      this.addObject(key, value);
      return this;
   }

   private void addObject(String key, @Nullable Object value) {
      this.checkCapacity(this.size + 1);
      this.keys[this.size] = key;
      this.vals[this.size] = value;
      this.size++;
   }

   public Attributes put(String key, @Nullable String value) {
      Validate.notNull(key);
      int i = this.indexOfKey(key);
      if (i != -1) {
         this.vals[i] = value;
      } else {
         this.add(key, value);
      }

      return this;
   }

   Attributes putUserData(String key, Object value) {
      Validate.notNull(key);
      if (!this.isInternalKey(key)) {
         key = internalKey(key);
      }

      Validate.notNull(value);
      int i = this.indexOfKey(key);
      if (i != -1) {
         this.vals[i] = value;
      } else {
         this.addObject(key, value);
      }

      return this;
   }

   void putIgnoreCase(String key, @Nullable String value) {
      int i = this.indexOfKeyIgnoreCase(key);
      if (i != -1) {
         this.vals[i] = value;
         if (!this.keys[i].equals(key)) {
            this.keys[i] = key;
         }
      } else {
         this.add(key, value);
      }
   }

   public Attributes put(String key, boolean value) {
      if (value) {
         this.putIgnoreCase(key, null);
      } else {
         this.remove(key);
      }

      return this;
   }

   public Attributes put(Attribute attribute) {
      Validate.notNull(attribute);
      this.put(attribute.getKey(), attribute.getValue());
      attribute.parent = this;
      return this;
   }

   private void remove(int index) {
      Validate.isFalse(index >= this.size);
      int shifted = this.size - index - 1;
      if (shifted > 0) {
         System.arraycopy(this.keys, index + 1, this.keys, index, shifted);
         System.arraycopy(this.vals, index + 1, this.vals, index, shifted);
      }

      this.size--;
      this.keys[this.size] = null;
      this.vals[this.size] = null;
   }

   public void remove(String key) {
      int i = this.indexOfKey(key);
      if (i != -1) {
         this.remove(i);
      }
   }

   public void removeIgnoreCase(String key) {
      int i = this.indexOfKeyIgnoreCase(key);
      if (i != -1) {
         this.remove(i);
      }
   }

   public boolean hasKey(String key) {
      return this.indexOfKey(key) != -1;
   }

   public boolean hasKeyIgnoreCase(String key) {
      return this.indexOfKeyIgnoreCase(key) != -1;
   }

   public boolean hasDeclaredValueForKey(String key) {
      int i = this.indexOfKey(key);
      return i != -1 && this.vals[i] != null;
   }

   public boolean hasDeclaredValueForKeyIgnoreCase(String key) {
      int i = this.indexOfKeyIgnoreCase(key);
      return i != -1 && this.vals[i] != null;
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public void addAll(Attributes incoming) {
      if (incoming.size() != 0) {
         this.checkCapacity(this.size + incoming.size);
         boolean needsPut = this.size != 0;

         for (Attribute attr : incoming) {
            if (needsPut) {
               this.put(attr);
            } else {
               this.add(attr.getKey(), attr.getValue());
            }
         }
      }
   }

   @Override
   public Iterator<Attribute> iterator() {
      return new Iterator<Attribute>() {
         int i = 0;

         @Override
         public boolean hasNext() {
            while (this.i < Attributes.this.size && Attributes.this.isInternalKey(Attributes.this.keys[this.i])) {
               this.i++;
            }

            return this.i < Attributes.this.size;
         }

         public Attribute next() {
            Attribute attr = new Attribute(Attributes.this.keys[this.i], (String)Attributes.this.vals[this.i], Attributes.this);
            this.i++;
            return attr;
         }

         @Override
         public void remove() {
            Attributes.this.remove(--this.i);
         }
      };
   }

   public List<Attribute> asList() {
      ArrayList<Attribute> list = new ArrayList<>(this.size);

      for (int i = 0; i < this.size; i++) {
         if (!this.isInternalKey(this.keys[i])) {
            Attribute attr = new Attribute(this.keys[i], (String)this.vals[i], this);
            list.add(attr);
         }
      }

      return Collections.unmodifiableList(list);
   }

   public Map<String, String> dataset() {
      return new Attributes.Dataset(this);
   }

   public String html() {
      StringBuilder sb = StringUtil.borrowBuilder();

      try {
         this.html(sb, new Document("").outputSettings());
      } catch (IOException var3) {
         throw new SerializationException(var3);
      }

      return StringUtil.releaseBuilder(sb);
   }

   final void html(Appendable accum, Document.OutputSettings out) throws IOException {
      int sz = this.size;

      for (int i = 0; i < sz; i++) {
         if (!this.isInternalKey(this.keys[i])) {
            String key = Attribute.getValidKey(this.keys[i], out.syntax());
            if (key != null) {
               Attribute.htmlNoValidate(key, (String)this.vals[i], accum.append(' '), out);
            }
         }
      }
   }

   @Override
   public String toString() {
      return this.html();
   }

   @Override
   public boolean equals(@Nullable Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Attributes that = (Attributes)o;
         if (this.size != that.size) {
            return false;
         } else {
            for (int i = 0; i < this.size; i++) {
               String key = this.keys[i];
               int thatI = that.indexOfKey(key);
               if (thatI == -1) {
                  return false;
               }

               Object val = this.vals[i];
               Object thatVal = that.vals[thatI];
               if (val == null) {
                  if (thatVal != null) {
                     return false;
                  }
               } else if (!val.equals(thatVal)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.size;
      result = 31 * result + Arrays.hashCode((Object[])this.keys);
      return 31 * result + Arrays.hashCode(this.vals);
   }

   public Attributes clone() {
      Attributes clone;
      try {
         clone = (Attributes)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new RuntimeException(var3);
      }

      clone.size = this.size;
      clone.keys = Arrays.copyOf(this.keys, this.size);
      clone.vals = Arrays.copyOf(this.vals, this.size);
      return clone;
   }

   public void normalize() {
      for (int i = 0; i < this.size; i++) {
         this.keys[i] = Normalizer.lowerCase(this.keys[i]);
      }
   }

   public int deduplicate(ParseSettings settings) {
      if (this.isEmpty()) {
         return 0;
      } else {
         boolean preserve = settings.preserveAttributeCase();
         int dupes = 0;

         for (int i = 0; i < this.keys.length; i++) {
            for (int j = i + 1; j < this.keys.length && this.keys[j] != null; j++) {
               if (preserve && this.keys[i].equals(this.keys[j]) || !preserve && this.keys[i].equalsIgnoreCase(this.keys[j])) {
                  dupes++;
                  this.remove(j);
                  j--;
               }
            }
         }

         return dupes;
      }
   }

   private static String dataKey(String key) {
      return "data-" + key;
   }

   static String internalKey(String key) {
      return '/' + key;
   }

   private boolean isInternalKey(String key) {
      return key != null && key.length() > 1 && key.charAt(0) == '/';
   }

   private static class Dataset extends AbstractMap<String, String> {
      private final Attributes attributes;

      private Dataset(Attributes attributes) {
         this.attributes = attributes;
      }

      @Override
      public Set<Entry<String, String>> entrySet() {
         return new Attributes.Dataset.EntrySet();
      }

      public String put(String key, String value) {
         String dataKey = Attributes.dataKey(key);
         String oldValue = this.attributes.hasKey(dataKey) ? this.attributes.get(dataKey) : null;
         this.attributes.put(dataKey, value);
         return oldValue;
      }

      private class DatasetIterator implements Iterator<Entry<String, String>> {
         private Iterator<Attribute> attrIter = Dataset.this.attributes.iterator();
         private Attribute attr;

         private DatasetIterator() {
         }

         @Override
         public boolean hasNext() {
            while (this.attrIter.hasNext()) {
               this.attr = this.attrIter.next();
               if (this.attr.isDataAttribute()) {
                  return true;
               }
            }

            return false;
         }

         public Entry<String, String> next() {
            return new Attribute(this.attr.getKey().substring("data-".length()), this.attr.getValue());
         }

         @Override
         public void remove() {
            Dataset.this.attributes.remove(this.attr.getKey());
         }
      }

      private class EntrySet extends AbstractSet<Entry<String, String>> {
         private EntrySet() {
         }

         @Override
         public Iterator<Entry<String, String>> iterator() {
            return Dataset.this.new DatasetIterator();
         }

         @Override
         public int size() {
            int count = 0;
            Iterator iter = Dataset.this.new DatasetIterator();

            while (iter.hasNext()) {
               count++;
            }

            return count;
         }
      }
   }
}
