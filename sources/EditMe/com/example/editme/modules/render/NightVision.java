package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import net.minecraft.util.ResourceLocation;

@Module.Info(
   name = "NightVision",
   category = Module.Category.RENDER
)
public class NightVision extends Module {
   public void onEnable() {
      if (mc.field_71439_g != null) {
         if (mc.field_71460_t.func_147702_a()) {
            mc.field_71460_t.func_181022_b();
         }

         mc.field_71460_t.func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
      } else {
         this.disable();
      }

   }

   public void onDisable() {
      if (mc.field_71439_g != null) {
         mc.field_71460_t.func_181022_b();
      }

   }
}
