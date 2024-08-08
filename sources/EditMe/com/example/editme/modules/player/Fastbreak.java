package com.example.editme.modules.player;

import com.example.editme.modules.Module;

@Module.Info(
   name = "Fastbreak",
   category = Module.Category.PLAYER,
   description = "Nullifies block hit delay"
)
public class Fastbreak extends Module {
   public void onUpdate() {
      mc.field_71442_b.field_78781_i = 0;
   }
}
