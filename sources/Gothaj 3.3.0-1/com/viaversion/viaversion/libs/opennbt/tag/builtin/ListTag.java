package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class ListTag extends Tag implements Iterable<Tag> {
   public static final int ID = 9;
   private List<Tag> value;
   private Class<? extends Tag> type;

   public ListTag() {
      this.value = new ArrayList<>();
   }

   public ListTag(@Nullable Class<? extends Tag> type) {
      this.type = type;
      this.value = new ArrayList<>();
   }

   public ListTag(List<Tag> value) throws IllegalArgumentException {
      this.setValue(value);
   }

   public static ListTag read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
      tagLimiter.checkLevel(nestingLevel);
      tagLimiter.countBytes(5);
      int id = in.readByte();
      Class<? extends Tag> type = null;
      if (id != 0) {
         type = TagRegistry.getClassFor(id);
         if (type == null) {
            throw new IOException("Unknown tag ID in ListTag: " + id);
         }
      }

      ListTag listTag = new ListTag(type);
      int count = in.readInt();
      int newNestingLevel = nestingLevel + 1;

      for (int index = 0; index < count; index++) {
         Tag tag;
         try {
            tag = TagRegistry.read(id, in, tagLimiter, newNestingLevel);
         } catch (IllegalArgumentException var11) {
            throw new IOException("Failed to create tag.", var11);
         }

         listTag.add(tag);
      }

      return listTag;
   }

   public List<Tag> getValue() {
      return this.value;
   }

   @Override
   public String asRawString() {
      return this.value.toString();
   }

   public void setValue(List<Tag> value) throws IllegalArgumentException {
      this.value = new ArrayList<>(value);
      if (!value.isEmpty()) {
         this.type = (Class<? extends Tag>)value.get(0).getClass();

         for (int i = 1; i < value.size(); i++) {
            this.checkType(value.get(i));
         }
      } else {
         this.type = null;
      }
   }

   @Nullable
   public Class<? extends Tag> getElementType() {
      return this.type;
   }

   public boolean add(Tag tag) throws IllegalArgumentException {
      if (this.type == null) {
         this.type = (Class<? extends Tag>)tag.getClass();
      } else if (tag.getClass() != this.type) {
         this.checkType(tag);
      }

      return this.value.add(tag);
   }

   private void checkType(Tag tag) throws IllegalArgumentException {
      if (tag.getClass() != this.type) {
         throw new IllegalArgumentException("Tag type " + tag.getClass().getSimpleName() + " differs from list type " + this.type.getSimpleName());
      }
   }

   public boolean remove(Tag tag) {
      return this.value.remove(tag);
   }

   public <T extends Tag> T get(int index) {
      return (T)this.value.get(index);
   }

   public int size() {
      return this.value.size();
   }

   public boolean isEmpty() {
      return this.value.isEmpty();
   }

   @Override
   public Iterator<Tag> iterator() {
      return this.value.iterator();
   }

   @Override
   public void write(DataOutput out) throws IOException {
      if (this.type == null) {
         out.writeByte(0);
      } else {
         int id = TagRegistry.getIdFor(this.type);
         if (id == -1) {
            throw new IOException("ListTag contains unregistered tag class.");
         }

         out.writeByte(id);
      }

      out.writeInt(this.value.size());

      for (Tag tag : this.value) {
         tag.write(out);
      }
   }

   public ListTag copy() {
      List<Tag> newList = new ArrayList<>();

      for (Tag value : this.value) {
         newList.add(value.copy());
      }

      return new ListTag(newList);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ListTag tags = (ListTag)o;
         return !Objects.equals(this.type, tags.type) ? false : this.value.equals(tags.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.type != null ? this.type.hashCode() : 0;
      return 31 * result + this.value.hashCode();
   }

   @Override
   public int getTagId() {
      return 9;
   }
}
