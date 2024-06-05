package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Animation;

public final class AnimatedToggleable implements Toggleable {
   private final Toggleable toggle;
   private final Animation animation;

   public AnimatedToggleable(Toggleable var1, Animation var2) {
      this.toggle = var1;
      this.animation = var2;
      if (var1.isOn()) {
         var2.initValue(1.0D);
      } else {
         var2.initValue(0.0D);
      }

   }

   public void toggle() {
      this.toggle.toggle();
      if (this.toggle.isOn()) {
         this.animation.setValue(1.0D);
      } else {
         this.animation.setValue(0.0D);
      }

   }

   public boolean isOn() {
      return this.toggle.isOn();
   }

   public double getValue() {
      if (this.animation.getTarget() != (double)(this.toggle.isOn() ? 1 : 0)) {
         if (this.toggle.isOn()) {
            this.animation.setValue(1.0D);
         } else {
            this.animation.setValue(0.0D);
         }
      }

      return this.animation.getValue();
   }
}
