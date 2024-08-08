package com.example.editme.modules.oldfag;

import com.example.editme.modules.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;

@Module.Info(
   name = "Ghast Notifier",
   description = "Says when entities enter visual range",
   category = Module.Category.OLDFAG
)
public class GhastNotifier extends Module {
   private List knownGhasts;

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         ArrayList var1 = new ArrayList();
         Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

         while(var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if (var3 instanceof EntityGhast) {
               var1.add(var3.func_110124_au());
            }
         }

         UUID var4;
         if (var1.size() > 0) {
            var2 = var1.iterator();

            while(var2.hasNext()) {
               var4 = (UUID)var2.next();
               if (!this.knownGhasts.contains(var4)) {
                  this.knownGhasts.add(var4);
                  this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("A Ghast has entered visual range")));
                  return;
               }
            }
         }

         if (this.knownGhasts.size() > 0) {
            var2 = this.knownGhasts.iterator();

            while(var2.hasNext()) {
               var4 = (UUID)var2.next();
               if (!var1.contains(var4)) {
                  this.knownGhasts.remove(var4);
                  return;
               }
            }
         }

      }
   }

   public void onEnable() {
      this.knownGhasts = new ArrayList();
   }
}
