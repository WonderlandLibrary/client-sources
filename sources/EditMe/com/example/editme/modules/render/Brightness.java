package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Stack;
import java.util.function.Function;

@Module.Info(
   name = "Brightness",
   description = "Makes everything brighter!",
   category = Module.Category.RENDER
)
public class Brightness extends Module {
   private Setting transition = this.register(SettingsManager.b("Transition", true));
   private Setting mode;
   private static boolean inTransition = false;
   private static float currentBrightness = 0.0F;
   private Stack transitionStack;
   private Setting seconds = this.register(SettingsManager.floatBuilder("Seconds").withMinimum(0.0F).withMaximum(10.0F).withValue((Number)1.0F).withVisibility(this::lambda$new$0).build());

   private boolean lambda$new$1(Object var1) {
      return (Boolean)this.transition.getValue();
   }

   private float sine(float var1) {
      return ((float)Math.sin(3.141592653589793D * (double)var1 - 1.5707963267948966D) + 1.0F) / 2.0F;
   }

   private boolean lambda$new$0(Float var1) {
      return (Boolean)this.transition.getValue();
   }

   public static boolean isInTransition() {
      return inTransition;
   }

   private static Float lambda$linear$2(Float var0) {
      return var0;
   }

   private void addTransition(boolean var1) {
      if ((Boolean)this.transition.getValue()) {
         int var2 = (int)((Float)this.seconds.getValue() * 20.0F);
         float[] var3;
         switch((Brightness.Transition)this.mode.getValue()) {
         case LINEAR:
            var3 = this.linear(var2, var1);
            break;
         case SINE:
            var3 = this.sine(var2, var1);
            break;
         default:
            var3 = new float[]{0.0F};
         }

         float[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            float var7 = var4[var6];
            this.transitionStack.add(var7);
         }

         inTransition = true;
      }

   }

   protected void onDisable() {
      this.setAlwaysListening(true);
      super.onDisable();
      this.addTransition(false);
   }

   private float[] sine(int var1, boolean var2) {
      return this.createTransition(var1, var2, this::sine);
   }

   public static float getCurrentBrightness() {
      return currentBrightness;
   }

   private float[] linear(int var1, boolean var2) {
      return this.createTransition(var1, var2, Brightness::lambda$linear$2);
   }

   private float[] createTransition(int var1, boolean var2, Function var3) {
      float[] var4 = new float[var1];

      for(int var5 = 0; var5 < var1; ++var5) {
         float var6 = (Float)var3.apply((float)var5 / (float)var1);
         if (var2) {
            var6 = 1.0F - var6;
         }

         var4[var5] = var6;
      }

      return var4;
   }

   public static boolean shouldBeActive() {
      return isInTransition() || currentBrightness == 1.0F;
   }

   public Brightness() {
      this.mode = this.register(SettingsManager.enumBuilder(Brightness.Transition.class).withName("Mode").withValue(Brightness.Transition.SINE).withVisibility(this::lambda$new$1).build());
      this.transitionStack = new Stack();
   }

   public void onUpdate() {
      if (inTransition) {
         if (this.transitionStack.isEmpty()) {
            inTransition = false;
            this.setAlwaysListening(false);
            currentBrightness = this.isEnabled() ? 1.0F : 0.0F;
         } else {
            currentBrightness = (Float)this.transitionStack.pop();
         }
      }

   }

   protected void onEnable() {
      super.onEnable();
      this.addTransition(true);
   }

   public static enum Transition {
      private static final Brightness.Transition[] $VALUES = new Brightness.Transition[]{LINEAR, SINE};
      SINE,
      LINEAR;
   }
}
