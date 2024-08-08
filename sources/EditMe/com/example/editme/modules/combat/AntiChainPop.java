package com.example.editme.modules.combat;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.module.ModuleManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;

@Module.Info(
   name = "AntiChainPop",
   description = "Enables Surround when popping a totem",
   category = Module.Category.COMBAT
)
public class AntiChainPop extends Module {
   private int totems = 0;
   @EventHandler
   public Listener selfPopListener = new Listener(AntiChainPop::lambda$new$0, new Predicate[0]);

   public void onDisable() {
      this.totems = 0;
   }

   private static void lambda$new$0(PacketEvent.Receive var0) {
      if (mc.field_71439_g != null) {
         if (var0.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus var1 = (SPacketEntityStatus)var0.getPacket();
            if (var1.func_149160_c() == 35) {
               Entity var2 = var1.func_149161_a(mc.field_71441_e);
               if (var2.func_145748_c_().equals(mc.field_71439_g.func_145748_c_())) {
                  ModuleManager.enableModule("Surround");
               }
            }
         }

      }
   }

   public void onEnable() {
      this.totems = 0;
   }
}
