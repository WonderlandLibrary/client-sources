package com.example.editme.modules.player;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import net.minecraft.util.math.MathHelper;

@Module.Info(
   name = "PitchLock",
   category = Module.Category.PLAYER
)
public class PitchLock extends Module {
   private Setting pitch = this.register(SettingsManager.f("Pitch", 180.0F));
   private Setting auto = this.register(SettingsManager.b("Auto", true));
   private Setting slice = this.register(SettingsManager.i("Slice", 8));

   public void onUpdate() {
      if ((Integer)this.slice.getValue() != 0) {
         if ((Boolean)this.auto.getValue()) {
            int var1 = 360 / (Integer)this.slice.getValue();
            float var2 = mc.field_71439_g.field_70125_A;
            var2 = (float)(Math.round(var2 / (float)var1) * var1);
            mc.field_71439_g.field_70125_A = var2;
            if (mc.field_71439_g.func_184218_aH()) {
               mc.field_71439_g.func_184187_bx().field_70125_A = var2;
            }
         } else {
            mc.field_71439_g.field_70125_A = MathHelper.func_76131_a((Float)this.pitch.getValue() - 180.0F, -180.0F, 180.0F);
         }

      }
   }
}
