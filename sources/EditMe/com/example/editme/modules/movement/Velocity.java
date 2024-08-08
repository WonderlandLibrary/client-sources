package com.example.editme.modules.movement;

import com.example.editme.events.EditmeEvent;
import com.example.editme.events.EntityEvent;
import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

@Module.Info(
   name = "Velocity",
   description = "Modify knockback impact",
   category = Module.Category.MOVEMENT
)
public class Velocity extends Module {
   private Setting horizontal = this.register(SettingsManager.f("Horizontal", 0.0F));
   @EventHandler
   private Listener packetEventListener = new Listener(this::lambda$new$0, new Predicate[0]);
   @EventHandler
   private Listener entityCollisionListener = new Listener(this::lambda$new$1, new Predicate[0]);
   private Setting vertical = this.register(SettingsManager.f("Vertical", 0.0F));

   private void lambda$new$0(PacketEvent.Receive var1) {
      if (var1.getEra() == EditmeEvent.Era.PRE) {
         if (var1.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity var2 = (SPacketEntityVelocity)var1.getPacket();
            if (var2.func_149412_c() == mc.field_71439_g.field_145783_c) {
               if ((Float)this.horizontal.getValue() == 0.0F && (Float)this.vertical.getValue() == 0.0F) {
                  var1.cancel();
               }

               var2.field_149415_b = (int)((float)var2.field_149415_b * (Float)this.horizontal.getValue());
               var2.field_149416_c = (int)((float)var2.field_149416_c * (Float)this.vertical.getValue());
               var2.field_149414_d = (int)((float)var2.field_149414_d * (Float)this.horizontal.getValue());
            }
         } else if (var1.getPacket() instanceof SPacketExplosion) {
            if ((Float)this.horizontal.getValue() == 0.0F && (Float)this.vertical.getValue() == 0.0F) {
               var1.cancel();
            }

            SPacketExplosion var3 = (SPacketExplosion)var1.getPacket();
            var3.field_149152_f *= (Float)this.horizontal.getValue();
            var3.field_149153_g *= (Float)this.vertical.getValue();
            var3.field_149159_h *= (Float)this.horizontal.getValue();
         }
      }

   }

   private void lambda$new$1(EntityEvent.EntityCollision var1) {
      if (var1.getEntity() == mc.field_71439_g) {
         if ((Float)this.horizontal.getValue() == 0.0F && (Float)this.vertical.getValue() == 0.0F) {
            var1.cancel();
            return;
         }

         var1.setX(-var1.getX() * (double)(Float)this.horizontal.getValue());
         var1.setY(0.0D);
         var1.setZ(-var1.getZ() * (double)(Float)this.horizontal.getValue());
      }

   }
}
