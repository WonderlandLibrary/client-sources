package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Pair;
import com.example.editme.util.setting.SettingsManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "HotbarReplenish",
   description = "Replenishes hotbar, may cause desync",
   category = Module.Category.COMBAT
)
public class HotbarReplenish extends Module {
   private int delayStep = 0;
   private Setting threshold = this.register(SettingsManager.integerBuilder("Threshold").withMinimum(1).withValue((int)32).withMaximum(63).build());
   private Setting tickDelay = this.register(SettingsManager.integerBuilder("Delay").withMinimum(1).withValue((int)2).withMaximum(10).build());

   private int findCompatibleInventorySlot(ItemStack var1) {
      int var2 = -1;
      int var3 = 999;
      Iterator var4 = getInventory().entrySet().iterator();

      while(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         ItemStack var6 = (ItemStack)var5.getValue();
         if (!var6.field_190928_g && var6.func_77973_b() != Items.field_190931_a && this.isCompatibleStacks(var1, var6)) {
            int var7 = ((ItemStack)mc.field_71439_g.field_71069_bz.func_75138_a().get((Integer)var5.getKey())).field_77994_a;
            if (var3 > var7) {
               var3 = var7;
               var2 = (Integer)var5.getKey();
            }
         }
      }

      return var2;
   }

   private static Map getInventorySlots(int var0, int var1) {
      HashMap var2;
      for(var2 = new HashMap(); var0 <= var1; ++var0) {
         var2.put(var0, mc.field_71439_g.field_71069_bz.func_75138_a().get(var0));
      }

      return var2;
   }

   private boolean isCompatibleStacks(ItemStack var1, ItemStack var2) {
      if (!var1.func_77973_b().equals(var2.func_77973_b())) {
         return false;
      } else {
         if (var1.func_77973_b() instanceof ItemBlock && var2.func_77973_b() instanceof ItemBlock) {
            Block var3 = ((ItemBlock)var1.func_77973_b()).func_179223_d();
            Block var4 = ((ItemBlock)var2.func_77973_b()).func_179223_d();
            if (!var3.field_149764_J.equals(var4.field_149764_J)) {
               return false;
            }
         }

         if (!var1.func_82833_r().equals(var2.func_82833_r())) {
            return false;
         } else {
            return var1.func_77952_i() == var2.func_77952_i();
         }
      }
   }

   private Pair findReplenishableHotbarSlot() {
      Pair var1 = null;
      Iterator var2 = getHotbar().entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         ItemStack var4 = (ItemStack)var3.getValue();
         if (!var4.field_190928_g && var4.func_77973_b() != Items.field_190931_a && var4.func_77985_e() && var4.field_77994_a < var4.func_77976_d() && var4.field_77994_a <= (Integer)this.threshold.getValue()) {
            int var5 = this.findCompatibleInventorySlot(var4);
            if (var5 != -1) {
               var1 = new Pair(var5, var3.getKey());
            }
         }
      }

      return var1;
   }

   private static Map getInventory() {
      return getInventorySlots(9, 35);
   }

   private static Map getHotbar() {
      return getInventorySlots(36, 44);
   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         if (!(mc.field_71462_r instanceof GuiContainer)) {
            if (this.delayStep < (Integer)this.tickDelay.getValue()) {
               ++this.delayStep;
            } else {
               this.delayStep = 0;
               Pair var1 = this.findReplenishableHotbarSlot();
               if (var1 != null) {
                  int var2 = (Integer)var1.getKey();
                  int var3 = (Integer)var1.getValue();
                  mc.field_71442_b.func_187098_a(0, var2, 0, ClickType.PICKUP, mc.field_71439_g);
                  mc.field_71442_b.func_187098_a(0, var3, 0, ClickType.PICKUP, mc.field_71439_g);
                  mc.field_71442_b.func_187098_a(0, var2, 0, ClickType.PICKUP, mc.field_71439_g);
               }
            }
         }
      }
   }
}
