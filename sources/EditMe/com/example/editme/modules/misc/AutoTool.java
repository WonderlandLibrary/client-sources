package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.util.module.ModuleManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;

@Module.Info(
   name = "AutoTool",
   description = "Automatically switch to the best tools or weapons",
   category = Module.Category.MISC
)
public class AutoTool extends Module {
   @EventHandler
   private Listener leftClickListener = new Listener(this::lambda$new$0, new Predicate[0]);
   @EventHandler
   private Listener attackListener = new Listener(AutoTool::lambda$new$1, new Predicate[0]);

   private static void equip(int var0) {
      mc.field_71439_g.field_71071_by.field_70461_c = var0;
      mc.field_71442_b.func_78750_j();
   }

   private void equipBestTool(IBlockState var1) {
      int var2 = -1;
      double var3 = 0.0D;

      for(int var5 = 0; var5 < 9; ++var5) {
         ItemStack var6 = mc.field_71439_g.field_71071_by.func_70301_a(var5);
         if (!var6.field_190928_g) {
            float var7 = var6.func_150997_a(var1);
            if (var7 > 1.0F) {
               int var8;
               var7 = (float)((double)var7 + ((var8 = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, var6)) > 0 ? Math.pow((double)var8, 2.0D) + 1.0D : 0.0D));
               if ((double)var7 > var3) {
                  var3 = (double)var7;
                  var2 = var5;
               }
            }
         }
      }

      if (var2 != -1) {
         equip(var2);
      }

   }

   public static void equipBestWeapon() {
      int var0 = -1;
      double var1 = 0.0D;

      for(int var3 = 0; var3 < 9; ++var3) {
         ItemStack var4 = mc.field_71439_g.field_71071_by.func_70301_a(var3);
         if (!var4.field_190928_g) {
            double var5;
            if (var4.func_77973_b() instanceof ItemTool) {
               var5 = (double)((ItemTool)var4.func_77973_b()).field_77865_bY + (double)EnchantmentHelper.func_152377_a(var4, EnumCreatureAttribute.UNDEFINED);
               if (var5 > var1) {
                  var1 = var5;
                  var0 = var3;
               }
            } else if (var4.func_77973_b() instanceof ItemSword) {
               var5 = (double)((ItemSword)var4.func_77973_b()).func_150931_i() + (double)EnchantmentHelper.func_152377_a(var4, EnumCreatureAttribute.UNDEFINED);
               if (var5 > var1) {
                  var1 = var5;
                  var0 = var3;
               }
            }
         }
      }

      if (var0 != -1) {
         equip(var0);
      }

   }

   private void lambda$new$0(LeftClickBlock var1) {
      this.equipBestTool(mc.field_71441_e.func_180495_p(var1.getPos()));
   }

   private static void lambda$new$1(AttackEntityEvent var0) {
      if (!ModuleManager.isModuleEnabled("AutoCrystal")) {
         equipBestWeapon();
      }

   }
}
