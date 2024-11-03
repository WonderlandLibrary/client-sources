package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteTag extends NumberTag {
   public static final int ID = 1;
   private byte value;

   public ByteTag() {
      this((byte)0);
   }

   public ByteTag(byte value) {
      this.value = value;
   }

   public static ByteTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countByte();
      return new ByteTag(in.readByte());
   }

   @Deprecated
   public Byte getValue() {
      return this.value;
   }

   @Override
   public String asRawString() {
      return Byte.toString(this.value);
   }

   public void setValue(byte value) {
      this.value = value;
   }

   @Override
   public void write(DataOutput out) throws IOException {
      out.writeByte(this.value);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ByteTag byteTag = (ByteTag)o;
         return this.value == byteTag.value;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.value;
   }

   public ByteTag copy() {
      return new ByteTag(this.value);
   }

   @Override
   public byte asByte() {
      return this.value;
   }

   @Override
   public short asShort() {
      return (short)this.value;
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
      return 1;
   }
}
