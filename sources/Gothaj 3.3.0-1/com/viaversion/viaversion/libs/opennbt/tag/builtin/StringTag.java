package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringTag extends Tag {
   public static final int ID = 8;
   private String value;

   public StringTag() {
      this("");
   }

   public StringTag(String value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public static StringTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      String value = in.readUTF();
      tagLimiter.countBytes(2 * value.length());
      return new StringTag(value);
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public String asRawString() {
      return this.value;
   }

   public void setValue(String value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   @Override
   public void write(DataOutput out) throws IOException {
      out.writeUTF(this.value);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         StringTag stringTag = (StringTag)o;
         return this.value.equals(stringTag.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   public StringTag copy() {
      return new StringTag(this.value);
   }

   @Override
   public int getTagId() {
      return 8;
   }
}
