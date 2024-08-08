package com.example.editme.modules.player;

import com.example.editme.modules.Module;

@Module.Info(
   name = "TpsSync",
   description = "Synchronizes some actions with the server TPS",
   category = Module.Category.PLAYER
)
public class TpsSync extends Module {
   private static TpsSync INSTANCE;

   public TpsSync() {
      INSTANCE = this;
   }

   public static boolean isSync() {
      return INSTANCE.isEnabled();
   }
}
