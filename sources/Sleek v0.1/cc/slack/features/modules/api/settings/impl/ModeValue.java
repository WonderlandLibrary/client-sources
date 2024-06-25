package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;
import java.util.Arrays;
import java.util.List;

public class ModeValue<T> extends Value<T> {
   private final T[] modes;

   public ModeValue(String name, T[] modes) {
      super(name, modes[0], (Value.VisibilityCheck)null);
      this.modes = modes;
   }

   public ModeValue(T[] modes) {
      super((String)null, modes[0], (Value.VisibilityCheck)null);
      this.modes = modes;
   }

   public T increment() {
      List<T> choices = Arrays.asList(this.modes);
      int currentIndex = choices.indexOf(this.getValue());
      ++currentIndex;
      if (currentIndex >= choices.size()) {
         currentIndex = 0;
      }

      this.setValue(choices.get(currentIndex));
      return choices.get(currentIndex);
   }

   public T decrement() {
      List<T> choices = Arrays.asList(this.modes);
      int currentIndex = choices.indexOf(this.getValue());
      --currentIndex;
      if (currentIndex < 0) {
         currentIndex = choices.size() - 1;
      }

      this.setValue(choices.get(currentIndex));
      return choices.get(currentIndex);
   }

   public int getIndex() {
      List<T> choices = Arrays.asList(this.modes);
      return choices.indexOf(this.getValue());
   }

   public void setIndex(Integer index) {
      List<T> choices = Arrays.asList(this.modes);
      this.setValue(choices.get(index));
   }

   public boolean is(T mode) {
      Object[] var2 = this.modes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         T m = var2[var4];
         if (m instanceof String && ((String)m).toLowerCase() == mode) {
            return true;
         }

         if (m == mode) {
            return true;
         }
      }

      return false;
   }

   public void setValueFromString(String value) {
      Object[] var2 = this.modes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         T choice = var2[var4];
         if (choice.toString().equalsIgnoreCase(value)) {
            this.setValue(choice);
         }
      }

   }

   public T[] getModes() {
      return this.modes;
   }
}
