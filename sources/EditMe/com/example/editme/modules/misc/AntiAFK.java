package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Random;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;

@Module.Info(
   name = "AntiAFK",
   category = Module.Category.MISC,
   description = "Moves in order not to get kicked. (May be invisible client-sided)"
)
public class AntiAFK extends Module {
   private Random random = new Random();
   private Setting swing = this.register(SettingsManager.b("Swing", true));
   private Setting turn = this.register(SettingsManager.b("Turn", true));

   public void onUpdate() {
      if (!mc.field_71442_b.func_181040_m()) {
         if (mc.field_71439_g.field_70173_aa % 40 == 0 && (Boolean)this.swing.getValue()) {
            mc.func_147114_u().func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
         }

         if (mc.field_71439_g.field_70173_aa % 15 == 0 && (Boolean)this.turn.getValue()) {
            mc.field_71439_g.field_70177_z = (float)(this.random.nextInt(360) - 180);
         }

         if (!(Boolean)this.swing.getValue() && !(Boolean)this.turn.getValue() && mc.field_71439_g.field_70173_aa % 80 == 0) {
            mc.field_71439_g.func_70664_aZ();
         }

      }
   }
}
