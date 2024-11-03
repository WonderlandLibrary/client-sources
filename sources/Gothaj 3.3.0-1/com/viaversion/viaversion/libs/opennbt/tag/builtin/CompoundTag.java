package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class CompoundTag extends Tag implements Iterable<Entry<String, Tag>> {
   public static final int ID = 10;
   private Map<String, Tag> value;

   public CompoundTag() {
      this(new LinkedHashMap<>());
   }

   public CompoundTag(Map<String, Tag> value) {
      this.value = new LinkedHashMap<>(value);
   }

   public CompoundTag(LinkedHashMap<String, Tag> value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public static CompoundTag read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
      tagLimiter.checkLevel(nestingLevel);
      int newNestingLevel = nestingLevel + 1;
      CompoundTag compoundTag = new CompoundTag();

      while (true) {
         tagLimiter.countByte();
         int id = in.readByte();
         if (id == 0) {
            return compoundTag;
         }

         String name = in.readUTF();
         tagLimiter.countBytes(2 * name.length());

         Tag tag;
         try {
            tag = TagRegistry.read(id, in, tagLimiter, newNestingLevel);
         } catch (IllegalArgumentException var9) {
            throw new IOException("Failed to create tag.", var9);
         }

         compoundTag.value.put(name, tag);
      }
   }

   public Map<String, Tag> getValue() {
      return this.value;
   }

   @Override
   public String asRawString() {
      return this.value.toString();
   }

   public void setValue(Map<String, Tag> value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = new LinkedHashMap<>(value);
      }
   }

   public void setValue(LinkedHashMap<String, Tag> value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public boolean isEmpty() {
      return this.value.isEmpty();
   }

   public boolean contains(String tagName) {
      return this.value.containsKey(tagName);
   }

   @Nullable
   public <T extends Tag> T get(String tagName) {
      return (T)this.value.get(tagName);
   }

   @Nullable
   public <T extends Tag> T put(String tagName, T tag) {
      return (T)this.value.put(tagName, tag);
   }

   public void putString(String tagName, String value) {
      this.value.put(tagName, new StringTag(value));
   }

   public void putByte(String tagName, byte value) {
      this.value.put(tagName, new ByteTag(value));
   }

   public void putInt(String tagName, int value) {
      this.value.put(tagName, new IntTag(value));
   }

   public void putShort(String tagName, short value) {
      this.value.put(tagName, new ShortTag(value));
   }

   public void putLong(String tagName, long value) {
      this.value.put(tagName, new LongTag(value));
   }

   public void putFloat(String tagName, float value) {
      this.value.put(tagName, new FloatTag(value));
   }

   public void putDouble(String tagName, double value) {
      this.value.put(tagName, new DoubleTag(value));
   }

   public void putBoolean(String tagName, boolean value) {
      this.value.put(tagName, new ByteTag((byte)(value ? 1 : 0)));
   }

   public void putAll(CompoundTag compoundTag) {
      this.value.putAll(compoundTag.value);
   }

   @Nullable
   public <T extends Tag> T remove(String tagName) {
      return (T)this.value.remove(tagName);
   }

   public Set<String> keySet() {
      return this.value.keySet();
   }

   public Collection<Tag> values() {
      return this.value.values();
   }

   public Set<Entry<String, Tag>> entrySet() {
      return this.value.entrySet();
   }

   public int size() {
      return this.value.size();
   }

   public void clear() {
      this.value.clear();
   }

   @Override
   public Iterator<Entry<String, Tag>> iterator() {
      return this.value.entrySet().iterator();
   }

   @Override
   public void write(DataOutput out) throws IOException {
      for (Entry<String, Tag> entry : this.value.entrySet()) {
         Tag tag = entry.getValue();
         out.writeByte(tag.getTagId());
         out.writeUTF(entry.getKey());
         tag.write(out);
      }

      out.writeByte(0);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         CompoundTag tags = (CompoundTag)o;
         return this.value.equals(tags.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   public CompoundTag copy() {
      LinkedHashMap<String, Tag> newMap = new LinkedHashMap<>();

      for (Entry<String, Tag> entry : this.value.entrySet()) {
         newMap.put(entry.getKey(), entry.getValue().copy());
      }

      return new CompoundTag(newMap);
   }

   @Override
   public int getTagId() {
      return 10;
   }
}
