package com.example.editme.modules.player;

import com.example.editme.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketVehicleMove;

@Module.Info(
   name = "Vanish",
   category = Module.Category.PLAYER
)
public class Vanish extends Module {
   private static Entity vehicle;

   public void onEnable() {
      if (mc != null && mc.field_71439_g != null) {
         if (mc.field_71439_g.func_184187_bx() != null) {
            mc.field_71438_f.func_72712_a();
            vehicle = mc.field_71439_g.func_184187_bx();
            mc.field_71439_g.func_184210_p();
            mc.field_71441_e.func_72900_e(vehicle);
         } else {
            this.toggle();
         }

      } else {
         vehicle = null;
         this.disable();
      }
   }

   public void onUpdate() {
      if (mc.field_71439_g.func_184187_bx() != null) {
         vehicle = null;
      }

      if (vehicle == null && mc.field_71439_g.func_184187_bx() != null) {
         vehicle = mc.field_71439_g.func_184187_bx();
         mc.field_71439_g.func_184210_p();
         mc.field_71441_e.func_72900_e(vehicle);
      }

      if (vehicle != null) {
         vehicle.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketVehicleMove(vehicle));
      }

   }

   public void onDisable() {
      if (vehicle != null) {
         vehicle.field_70128_L = false;
         mc.field_71441_e.field_72996_f.add(vehicle);
         mc.field_71439_g.func_184205_a(vehicle, true);
         vehicle = null;
      }

   }
}
