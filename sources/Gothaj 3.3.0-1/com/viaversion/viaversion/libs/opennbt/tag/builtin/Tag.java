package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.stringified.SNBT;
import java.io.DataOutput;
import java.io.IOException;

public abstract class Tag {
   public abstract Object getValue();

   public abstract String asRawString();

   public <T> T value() {
      return (T)this.getValue();
   }

   public abstract void write(DataOutput var1) throws IOException;

   public abstract int getTagId();

   public abstract Tag copy();

   @Override
   public String toString() {
      return SNBT.serialize(this);
   }
}
