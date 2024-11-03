package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatTag extends NumberTag {
   public static final int ID = 5;
   private float value;

   public FloatTag() {
      this(0.0F);
   }

   public FloatTag(float value) {
      this.value = value;
   }

   public static FloatTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countFloat();
      return new FloatTag(in.readFloat());
   }

   @Deprecated
   public Float getValue() {
      return this.value;
   }

   @Override
   public String asRawString() {
      return Float.toString(this.value);
   }

   public void setValue(float value) {
      this.value = value;
   }

   @Override
   public void write(DataOutput out) throws IOException {
      out.writeFloat(this.value);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FloatTag floatTag = (FloatTag)o;
         return this.value == floatTag.value;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Float.hashCode(this.value);
   }

   public FloatTag copy() {
      return new FloatTag(this.value);
   }

   @Override
   public byte asByte() {
      return (byte)((int)this.value);
   }

   @Override
   public short asShort() {
      return (short)((int)this.value);
   }

   @Override
   public int asInt() {
      return (int)this.value;
   }

   @Override
   public long asLong() {
      return (long)this.value;
   }

   @Override
   public float asFloat() {
      return this.value;
   }

   @Override
   public double asDouble() {
      return (double)this.value;
   }

   @Override
   public int getTagId() {
      return 5;
   }
}
