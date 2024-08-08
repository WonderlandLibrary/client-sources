package com.example.editme.modules.movement;

import com.example.editme.events.EditmeEvent;
import com.example.editme.events.EventPlayerMotionUpdate;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.math.AxisAlignedBB;

@Module.Info(
   name = "Step",
   category = Module.Category.MOVEMENT
)
public class Step extends Module {
   private Setting height = this.register(SettingsManager.f("Height", 1.0F));
   @EventHandler
   private Listener onMotionUpdate = new Listener(Step::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(EventPlayerMotionUpdate var0) {
      if (var0.getEra() == EditmeEvent.Era.PRE) {
         if (mc.field_71439_g.field_70123_F && mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70143_R == 0.0F && !mc.field_71439_g.field_70134_J && !mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.field_71158_b.field_78901_c) {
            AxisAlignedBB var1 = mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, 0.05D, 0.0D).func_186662_g(0.05D);
            if (!mc.field_71441_e.func_184144_a(mc.field_71439_g, var1.func_72317_d(0.0D, 1.0D, 0.0D)).isEmpty()) {
               return;
            }

            double var2 = -1.0D;
            Iterator var4 = mc.field_71441_e.func_184144_a(mc.field_71439_g, var1).iterator();

            while(true) {
               if (!var4.hasNext()) {
                  var2 -= mc.field_71439_g.field_70163_u;
                  if (var2 < 0.0D || var2 > 1.0D) {
                     return;
                  }

                  mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.42D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
                  mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.75D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
                  mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0D, mc.field_71439_g.field_70161_v);
                  break;
               }

               AxisAlignedBB var5 = (AxisAlignedBB)var4.next();
               if (var5.field_72337_e > var2) {
                  var2 = var5.field_72337_e;
               }
            }
         }

      }
   }
}
