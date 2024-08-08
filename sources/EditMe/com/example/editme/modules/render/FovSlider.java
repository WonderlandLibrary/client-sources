package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;

@Module.Info(
   name = "FovSlider",
   description = "KUAKE PRO",
   category = Module.Category.RENDER
)
public class FovSlider extends Module {
   private Setting fov = this.register(SettingsManager.integerBuilder("FOV").withMinimum(0).withValue((int)120).withMaximum(180).build());
   public float old_fov;
   public float new_fov;

   public void onDisable() {
      mc.field_71474_y.field_74334_X = this.old_fov;
   }

   public void onUpdate() {
      this.new_fov = (float)(Integer)this.fov.getValue();
      mc.field_71474_y.field_74334_X = this.new_fov;
   }
}
