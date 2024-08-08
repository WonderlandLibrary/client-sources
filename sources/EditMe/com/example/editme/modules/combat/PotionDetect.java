package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

@Module.Info(
   name = "PotionDetect",
   category = Module.Category.COMBAT
)
public class PotionDetect extends Module {
   private List weaknessList = new ArrayList();
   private List strengthList = new ArrayList();

   public void onUpdate() {
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(true) {
         while(var1.hasNext()) {
            Entity var2 = (Entity)var1.next();
            EntityPlayer var3 = (EntityPlayer)var2;
            PotionEffect var4 = var3.func_70660_b(MobEffects.field_76420_g);
            PotionEffect var5 = var3.func_70660_b(MobEffects.field_76437_t);
            if (var4 != null && var3.func_70644_a(MobEffects.field_76420_g)) {
               if (!this.strengthList.contains(var3.func_70005_c_())) {
                  this.strengthList.add(var3.func_70005_c_());
                  this.sendNotification(String.valueOf((new StringBuilder()).append(var3.func_70005_c_()).append(" has strength!")));
               }
            } else if (this.strengthList.contains(var3.func_70005_c_())) {
               this.strengthList.remove(var3.func_70005_c_());
               this.sendNotification(String.valueOf((new StringBuilder()).append(var3.func_70005_c_()).append(" no longer has strength!")));
            }

            if (var5 != null && var3.func_70644_a(MobEffects.field_76437_t)) {
               if (!this.weaknessList.contains(var3.func_70005_c_())) {
                  this.weaknessList.add(var3.func_70005_c_());
                  this.sendNotification(String.valueOf((new StringBuilder()).append(var3.func_70005_c_()).append(" has weakness!")));
               }
            } else if (this.weaknessList.contains(var3.func_70005_c_())) {
               this.weaknessList.remove(var3.func_70005_c_());
               this.sendNotification(String.valueOf((new StringBuilder()).append(var3.func_70005_c_()).append(" no longer has weakness!")));
            }
         }

         return;
      }
   }
}
