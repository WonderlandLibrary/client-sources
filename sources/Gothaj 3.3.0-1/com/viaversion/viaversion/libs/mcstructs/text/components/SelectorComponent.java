package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import javax.annotation.Nullable;

public class SelectorComponent extends ATextComponent {
   private final String selector;
   private final ATextComponent separator;

   public SelectorComponent(String selector) {
      this(selector, null);
   }

   public SelectorComponent(String selector, @Nullable ATextComponent separator) {
      this.selector = selector;
      this.separator = separator;
   }

   public String getSelector() {
      return this.selector;
   }

   @Nullable
   public ATextComponent getSeparator() {
      return this.separator;
   }

   @Override
   public String asSingleString() {
      return this.selector;
   }

   @Override
   public ATextComponent copy() {
      return this.separator == null
         ? this.putMetaCopy(new SelectorComponent(this.selector, null))
         : this.putMetaCopy(new SelectorComponent(this.selector, this.separator.copy()));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SelectorComponent that = (SelectorComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings())
            && Objects.equals(this.getStyle(), that.getStyle())
            && Objects.equals(this.selector, that.selector)
            && Objects.equals(this.separator, that.separator);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSiblings(), this.getStyle(), this.selector, this.separator);
   }

   @Override
   public String toString() {
      return "SelectorComponent{siblings="
         + this.getSiblings()
         + ", style="
         + this.getStyle()
         + ", selector='"
         + this.selector
         + '\''
         + ", separator="
         + this.separator
         + '}';
   }
}
