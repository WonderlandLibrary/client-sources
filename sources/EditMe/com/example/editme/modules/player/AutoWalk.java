package com.example.editme.modules.player;

import com.example.editme.modules.Module;
import com.example.editme.modules.render.Pathfind;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.pathfinding.PathPoint;
import net.minecraftforge.client.event.InputUpdateEvent;

@Module.Info(
   name = "AutoWalk",
   category = Module.Category.PLAYER
)
public class AutoWalk extends Module {
   private Setting mode;
   @EventHandler
   private Listener inputUpdateEventListener;

   public AutoWalk() {
      this.mode = this.register(SettingsManager.e("Mode", AutoWalk.AutoWalkMode.FORWARD));
      this.inputUpdateEventListener = new Listener(this::lambda$new$0, new Predicate[0]);
   }

   private void lambda$new$0(InputUpdateEvent var1) {
      switch((AutoWalk.AutoWalkMode)this.mode.getValue()) {
      case FORWARD:
         var1.getMovementInput().field_192832_b = 1.0F;
         break;
      case BACKWARDS:
         var1.getMovementInput().field_192832_b = -1.0F;
         break;
      case PATH:
         if (Pathfind.points.isEmpty()) {
            return;
         }

         var1.getMovementInput().field_192832_b = 1.0F;
         if (!mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab()) {
            if (mc.field_71439_g.field_70123_F && mc.field_71439_g.field_70122_E) {
               mc.field_71439_g.func_70664_aZ();
            }
         } else {
            mc.field_71439_g.field_71158_b.field_78901_c = true;
         }

         if (!ModuleManager.isModuleEnabled("Pathfind") || Pathfind.points.isEmpty()) {
            return;
         }

         PathPoint var2 = (PathPoint)Pathfind.points.get(0);
         this.lookAt(var2);
      }

   }

   private void lookAt(PathPoint var1) {
      double[] var2 = EntityUtil.calculateLookAt((double)((float)var1.field_75839_a + 0.5F), (double)var1.field_75837_b, (double)((float)var1.field_75838_c + 0.5F), mc.field_71439_g);
      mc.field_71439_g.field_70177_z = (float)var2[0];
      mc.field_71439_g.field_70125_A = (float)var2[1];
   }

   private static enum AutoWalkMode {
      FORWARD,
      PATH,
      BACKWARDS;

      private static final AutoWalk.AutoWalkMode[] $VALUES = new AutoWalk.AutoWalkMode[]{FORWARD, BACKWARDS, PATH};
   }
}
