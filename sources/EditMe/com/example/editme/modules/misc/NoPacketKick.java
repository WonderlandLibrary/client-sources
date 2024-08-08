package com.example.editme.modules.misc;

import com.example.editme.modules.Module;

@Module.Info(
   name = "NoPacketKick",
   category = Module.Category.MISC,
   description = "Prevent large packets from kicking you"
)
public class NoPacketKick {
   private static NoPacketKick INSTANCE;

   public NoPacketKick() {
      INSTANCE = this;
   }

   public static boolean isEnabled() {
      NoPacketKick var10000 = INSTANCE;
      return isEnabled();
   }
}
