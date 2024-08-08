package com.example.editme.modules.render;

import com.example.editme.modules.Module;

@Module.Info(
   name = "AntiFog",
   description = "Disables or reduces fog",
   category = Module.Category.RENDER
)
public class AntiFog extends Module {
   private static AntiFog INSTANCE = new AntiFog();

   public static boolean enabled() {
      return INSTANCE.isEnabled();
   }

   public AntiFog() {
      INSTANCE = this;
   }
}
