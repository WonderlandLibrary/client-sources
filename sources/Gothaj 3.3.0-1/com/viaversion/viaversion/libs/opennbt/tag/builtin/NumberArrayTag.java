package com.viaversion.viaversion.libs.opennbt.tag.builtin;

public abstract class NumberArrayTag extends Tag {
   public abstract int length();

   public abstract ListTag toListTag();
}
