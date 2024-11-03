package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShortTag extends NumberTag {
   public static final int ID = 2;
   private short value;

   public ShortTag() {
      this((short)0);
   }

   public ShortTag(short value) {
      this.value = value;
   }

   public static ShortTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countShort();
      return new ShortTag(in.readShort());
   }

   @Deprecated
   public Short getValue() {
      return this.value;
   }

   @Override
   public String asRawString() {
      return Short.toString(this.value);
   }

   public void setValue(short value) {
      this.value = value;
   }

   @Override
   public void write(DataOutput out) throws IOException {
      out.writeShort(this.value);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ShortTag shortTag = (ShortTag)o;
         return this.value == shortTag.value;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.value;
   }

   public ShortTag copy() {
      return new ShortTag(this.value);
   }

   @Override
   public byte asByte() {
      return (byte)this.value;
   }

   @Override
   public short asShort() {
      return this.value;
   }

   @Override
   public int asInt() {
      return this.value;
   }

   @Override
   public long asLong() {
      return (long)this.value;
   }

   @Override
   public float asFloat() {
      return (float)this.value;
   }

   @Override
   public double asDouble() {
      return (double)this.value;
   }

   @Override
   public int getTagId() {
      return 2;
   }
}
