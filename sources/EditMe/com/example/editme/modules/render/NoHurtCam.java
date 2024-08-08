package com.example.editme.modules.render;

import com.example.editme.modules.Module;

@Module.Info(
   name = "NoHurtCam",
   category = Module.Category.RENDER,
   description = "Disables the 'hurt' camera effect"
)
public class NoHurtCam extends Module {
   private static NoHurtCam INSTANCE;

   public static boolean shouldDisable() {
      return INSTANCE != null && INSTANCE.isEnabled();
   }

   public NoHurtCam() {
      INSTANCE = this;
   }
}
