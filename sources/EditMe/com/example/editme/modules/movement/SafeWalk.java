package com.example.editme.modules.movement;

import com.example.editme.modules.Module;

@Module.Info(
   name = "SafeWalk",
   category = Module.Category.MOVEMENT,
   description = "Keeps you from walking off edges"
)
public class SafeWalk extends Module {
   private static SafeWalk INSTANCE;

   public static boolean shouldSafewalk() {
      return INSTANCE.isEnabled();
   }

   public SafeWalk() {
      INSTANCE = this;
   }
}
