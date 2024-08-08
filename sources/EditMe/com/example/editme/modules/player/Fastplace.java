package com.example.editme.modules.player;

import com.example.editme.modules.Module;

@Module.Info(
   name = "Fastplace",
   category = Module.Category.PLAYER,
   description = "Nullifies block place delay"
)
public class Fastplace extends Module {
   public void onUpdate() {
      mc.field_71467_ac = 0;
   }
}
