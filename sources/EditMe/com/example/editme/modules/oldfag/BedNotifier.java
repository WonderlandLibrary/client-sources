package com.example.editme.modules.oldfag;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemEndCrystal;

@Module.Info(
   name = "BedNotifier",
   category = Module.Category.OLDFAG
)
public class BedNotifier extends Module {
   private List players = new ArrayList();
   private Setting crystals = this.register(SettingsManager.b("Crystals", true));

   public void onUpdate() {
      Iterator var1;
      EntityPlayer var2;
      if ((Boolean)this.crystals.getValue()) {
         var1 = mc.field_71441_e.field_73010_i.iterator();

         while(true) {
            while(var1.hasNext()) {
               var2 = (EntityPlayer)var1.next();
               if (!(var2.field_184831_bT.func_77973_b() instanceof ItemEndCrystal) && !(var2.field_184831_bT.func_77973_b() instanceof ItemBed) && !(var2.func_184592_cb().func_77973_b() instanceof ItemEndCrystal) && !(var2.func_184592_cb().func_77973_b() instanceof ItemBed)) {
                  if (!(var2.field_184831_bT.func_77973_b() instanceof ItemEndCrystal) && !(var2.field_184831_bT.func_77973_b() instanceof ItemBed) && !(var2.func_184592_cb().func_77973_b() instanceof ItemEndCrystal) && !(var2.func_184592_cb().func_77973_b() instanceof ItemBed)) {
                     this.players.remove(var2.func_70005_c_());
                  }
               } else if (!this.players.contains(var2.func_70005_c_())) {
                  this.sendNotification(String.valueOf((new StringBuilder()).append(var2.func_70005_c_()).append(" has a bed or crystal in their hand")));
                  this.players.add(var2.func_70005_c_());
               }
            }

            return;
         }
      } else {
         var1 = mc.field_71441_e.field_73010_i.iterator();

         while(true) {
            while(var1.hasNext()) {
               var2 = (EntityPlayer)var1.next();
               if (!(var2.field_184831_bT.func_77973_b() instanceof ItemBed) && !(var2.func_184592_cb().func_77973_b() instanceof ItemBed)) {
                  if (!(var2.field_184831_bT.func_77973_b() instanceof ItemBed) && !(var2.func_184592_cb().func_77973_b() instanceof ItemBed)) {
                     this.players.remove(var2.func_70005_c_());
                  }
               } else if (!this.players.contains(var2.func_70005_c_())) {
                  this.sendNotification(String.valueOf((new StringBuilder()).append(var2.func_70005_c_()).append(" has a bed in their hand")));
                  this.players.add(var2.func_70005_c_());
               }
            }

            return;
         }
      }
   }
}
