package com.lukflug.panelstudio.settings;

public class SimpleToggleable implements Toggleable {
   private boolean value;

   public boolean isOn() {
      return this.value;
   }

   public SimpleToggleable(boolean var1) {
      this.value = var1;
   }

   public void toggle() {
      this.value = !this.value;
   }
}
