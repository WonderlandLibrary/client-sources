package com.example.editme.modules.movement;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook.EnumFlags;

@Module.Info(
   name = "NoRotate",
   category = Module.Category.MOVEMENT
)
public final class NoRotate extends Module {
   @EventHandler
   private Listener packetEvent = new Listener(NoRotate::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(PacketEvent var0) {
      if (var0.getPacket() instanceof SPacketPlayerPosLook && mc.field_71439_g != null && mc.field_71462_r == null) {
         var0.cancel();
         EntityPlayerSP var1 = mc.field_71439_g;
         SPacketPlayerPosLook var2 = (SPacketPlayerPosLook)var0.getPacket();
         double var3 = var2.func_148932_c();
         double var5 = var2.func_148928_d();
         double var7 = var2.func_148933_e();
         float var9 = var2.func_148931_f();
         float var10 = var2.func_148930_g();
         if (var2.func_179834_f().contains(EnumFlags.X)) {
            var3 += var1.field_70165_t;
         } else {
            var1.field_70159_w = 0.0D;
         }

         if (var2.func_179834_f().contains(EnumFlags.Y)) {
            var5 += var1.field_70163_u;
         } else {
            var1.field_70181_x = 0.0D;
         }

         if (var2.func_179834_f().contains(EnumFlags.Z)) {
            var7 += var1.field_70161_v;
         } else {
            var1.field_70179_y = 0.0D;
         }

         float var10000;
         if (var2.func_179834_f().contains(EnumFlags.X_ROT)) {
            var10000 = var10 + var1.field_70125_A;
         }

         if (var2.func_179834_f().contains(EnumFlags.Y_ROT)) {
            var10000 = var9 + var1.field_70177_z;
         }

         var1.func_70107_b(var3, var5, var7);
         mc.func_147114_u().func_147297_a(new CPacketConfirmTeleport(var2.func_186965_f()));
         mc.func_147114_u().func_147297_a(new PositionRotation(var1.field_70165_t, var1.func_174813_aQ().field_72338_b, var1.field_70161_v, var2.field_148936_d, var2.field_148937_e, false));
      }

   }
}
