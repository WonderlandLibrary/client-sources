package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;

public class KeybindComponent extends ATextComponent {
   private final String keybind;
   private Function<String, String> translator = s -> s;

   public KeybindComponent(String keybind) {
      this.keybind = keybind;
   }

   public KeybindComponent(String keybind, @Nonnull Function<String, String> translator) {
      this.keybind = keybind;
      this.translator = translator;
   }

   public String getKeybind() {
      return this.keybind;
   }

   public KeybindComponent setTranslator(@Nonnull Function<String, String> translator) {
      this.translator = translator;
      return this;
   }

   @Override
   public String asSingleString() {
      return this.translator.apply(this.keybind);
   }

   @Override
   public ATextComponent copy() {
      KeybindComponent copy = new KeybindComponent(this.keybind);
      copy.translator = this.translator;
      return this.putMetaCopy(copy);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         KeybindComponent that = (KeybindComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings())
            && Objects.equals(this.getStyle(), that.getStyle())
            && Objects.equals(this.keybind, that.keybind)
            && Objects.equals(this.translator, that.translator);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSiblings(), this.getStyle(), this.keybind, this.translator);
   }

   @Override
   public String toString() {
      return "KeybindComponent{siblings="
         + this.getSiblings()
         + ", style="
         + this.getStyle()
         + ", keybind='"
         + this.keybind
         + '\''
         + ", translator="
         + this.translator
         + '}';
   }
}
