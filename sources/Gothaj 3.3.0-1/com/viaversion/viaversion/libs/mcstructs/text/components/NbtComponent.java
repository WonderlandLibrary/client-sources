package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import javax.annotation.Nullable;

public abstract class NbtComponent extends ATextComponent {
   private final String component;
   private final boolean resolve;
   private final ATextComponent separator;

   public NbtComponent(String component, boolean resolve) {
      this(component, resolve, null);
   }

   public NbtComponent(String component, boolean resolve, @Nullable ATextComponent separator) {
      this.component = component;
      this.resolve = resolve;
      this.separator = separator;
   }

   public String getComponent() {
      return this.component;
   }

   public boolean isResolve() {
      return this.resolve;
   }

   @Nullable
   public ATextComponent getSeparator() {
      return this.separator;
   }

   @Override
   public String asSingleString() {
      return "";
   }
}
