package com.example.editme.modules.oldfag;

import com.example.editme.modules.Module;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@Module.Info(
   name = "LootNotifier",
   category = Module.Category.OLDFAG
)
public class LootNotifier extends Module {
   @EventHandler
   public Listener onWorldTick = new Listener(this::lambda$new$0, new Predicate[0]);

   private void lambda$new$0(WorldTickEvent var1) {
      if (!var1.world.field_72995_K) {
         World var2 = var1.world;
         EntityPlayer var3 = null;
         if (!var2.field_73010_i.isEmpty()) {
            var3 = (EntityPlayer)var2.field_73010_i.get(0);
            Iterator var4 = var2.field_147482_g.iterator();

            while(true) {
               TileEntityLockableLoot var6;
               int var7;
               ItemStack var8;
               do {
                  TileEntity var5;
                  do {
                     if (!var4.hasNext()) {
                        var4 = var2.field_72996_f.iterator();

                        while(true) {
                           EntityMinecartContainer var10;
                           do {
                              Entity var9;
                              do {
                                 if (!var4.hasNext()) {
                                    return;
                                 }

                                 var9 = (Entity)var4.next();
                              } while(!(var9 instanceof EntityMinecartContainer));

                              var10 = (EntityMinecartContainer)var9;
                           } while(var10.func_184276_b() == null);

                           var10.func_184288_f(var3);

                           for(var7 = 0; var7 < var10.itemHandler.getSlots(); ++var7) {
                              var8 = var10.itemHandler.getStackInSlot(var7);
                              if (var8.func_77973_b() == Items.field_151153_ao && var8.func_77952_i() == 1) {
                                 this.sendNotification(String.valueOf((new StringBuilder()).append("Found golden apple at ").append(var10.field_70165_t).append(" ").append(var10.field_70163_u).append(" ").append(var10.field_70161_v)));
                              }

                              if (var8.func_77973_b() == Items.field_151134_bR && EnchantmentHelper.func_77506_a(Enchantments.field_185296_A, var8) > 0) {
                                 this.sendNotification(String.valueOf((new StringBuilder()).append("Found enchanted mending book at ").append(var10.field_70165_t).append(" ").append(var10.field_70163_u).append(" ").append(var10.field_70161_v)));
                              }

                              if (var8.func_77973_b() == Items.field_151134_bR && EnchantmentHelper.func_77506_a(Enchantments.field_185307_s, var8) > 0) {
                                 this.sendNotification(String.valueOf((new StringBuilder()).append("Found enchanted unbreaking book at ").append(var10.field_70165_t).append(" ").append(var10.field_70163_u).append(" ").append(var10.field_70161_v)));
                              }

                              if (var8.func_77973_b() == Items.field_151134_bR && EnchantmentHelper.func_77506_a(Enchantments.field_180310_c, var8) > 0) {
                                 this.sendNotification(String.valueOf((new StringBuilder()).append("Found enchanted protection book at ").append(var10.field_70165_t).append(" ").append(var10.field_70163_u).append(" ").append(var10.field_70161_v)));
                              }
                           }
                        }
                     }

                     var5 = (TileEntity)var4.next();
                  } while(!(var5 instanceof TileEntityLockableLoot));

                  var6 = (TileEntityLockableLoot)var5;
               } while(var6.func_184276_b() == null);

               var6.func_184281_d(var3);

               for(var7 = 0; var7 < var6.func_70302_i_(); ++var7) {
                  var8 = var6.func_70301_a(var7);
                  if (var8.func_77973_b() == Items.field_151153_ao && var8.func_77952_i() == 1) {
                     this.sendNotification(String.valueOf((new StringBuilder()).append("Found golden apple at ").append(var6.func_174877_v().func_177958_n()).append(" ").append(var6.func_174877_v().func_177956_o()).append(" ").append(var6.func_174877_v().func_177952_p())));
                  }

                  if (var8.func_77973_b() == Items.field_151134_bR && EnchantmentHelper.func_77506_a(Enchantments.field_185296_A, var8) > 0) {
                     this.sendNotification(String.valueOf((new StringBuilder()).append("Found enchanted mending book at ").append(var6.func_174877_v().func_177958_n()).append(" ").append(var6.func_174877_v().func_177956_o()).append(" ").append(var6.func_174877_v().func_177952_p())));
                  }

                  if (var8.func_77973_b() == Items.field_151134_bR && EnchantmentHelper.func_77506_a(Enchantments.field_185307_s, var8) > 0) {
                     this.sendNotification(String.valueOf((new StringBuilder()).append("Found enchanted unbreaking book at ").append(var6.func_174877_v().func_177958_n()).append(" ").append(var6.func_174877_v().func_177956_o()).append(" ").append(var6.func_174877_v().func_177952_p())));
                  }

                  if (var8.func_77973_b() == Items.field_151134_bR && EnchantmentHelper.func_77506_a(Enchantments.field_180310_c, var8) > 0) {
                     this.sendNotification(String.valueOf((new StringBuilder()).append("Found enchanted protection book at ").append(var6.func_174877_v().func_177958_n()).append(" ").append(var6.func_174877_v().func_177956_o()).append(" ").append(var6.func_174877_v().func_177952_p())));
                  }
               }
            }
         }
      }
   }

   public void onEnable() {
      if (mc.field_71439_g == null) {
         this.disable();
      }

   }
}
