package com.viaversion.viaversion.libs.opennbt.tag.builtin;

public abstract class NumberTag extends Tag {
   public abstract Number getValue();

   public abstract byte asByte();

   public abstract short asShort();

   public abstract int asInt();

   public abstract long asLong();

   public abstract float asFloat();

   public abstract double asDouble();

   public boolean asBoolean() {
      return this.asByte() != 0;
   }
}
