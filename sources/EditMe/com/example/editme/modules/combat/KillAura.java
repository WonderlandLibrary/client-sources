package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.modules.misc.KillStreak;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Friends;
import com.example.editme.util.client.LagCompensator;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

@Module.Info(
   name = "Aura",
   category = Module.Category.COMBAT,
   description = "Hits entities around you"
)
public class KillAura extends Module {
   private Setting autoSword = this.register(SettingsManager.b("AutoSword", false));
   private Setting waitTick;
   private Setting Criticals = this.register(SettingsManager.b("Criticals", true));
   private Setting attackPlayers = this.register(SettingsManager.b("Players", true));
   private Setting swordOnly = this.register(SettingsManager.b("SwordOnly", false));
   private Setting autoAxeHealth = this.register(SettingsManager.integerBuilder("Auto Axe Armor %").withValue((int)20).withVisibility(this::lambda$new$0));
   private Setting cacheck = this.register(SettingsManager.b("CADisable", true));
   private Setting autoAxe = this.register(SettingsManager.b("Auto Axe", false));
   private Setting waitMode;
   private int waitCounter;
   private Setting ignoreWalls = this.register(SettingsManager.b("Ignore Walls", true));
   private Setting hitRange = this.register(SettingsManager.d("Hit Range", 4.5D));
   private Setting axe = this.register(SettingsManager.b("Axe", false));
   private Setting TpsSync = this.register(SettingsManager.b("TpsSync", true));
   private Setting attackAnimals = this.register(SettingsManager.b("Animals", false));
   private Setting attackMobs = this.register(SettingsManager.b("Mobs", false));

   public static int reverseNumber(int var0, int var1, int var2) {
      return var2 + var1 - var0;
   }

   private boolean canEntityFeetBeSeen(Entity var1) {
      return mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(var1.field_70165_t, var1.field_70163_u, var1.field_70161_v), false, true, false) == null;
   }

   private float getLagComp() {
      return ((KillAura.WaitMode)this.waitMode.getValue()).equals(KillAura.WaitMode.DYNAMIC) ? -(20.0F - LagCompensator.INSTANCE.getTickRate()) : 0.0F;
   }

   private static void equip(int var0) {
      mc.field_71439_g.field_71071_by.field_70461_c = var0;
      mc.field_71442_b.func_78750_j();
   }

   private boolean lambda$new$0(Integer var1) {
      return (Boolean)this.autoAxe.getValue();
   }

   public KillAura() {
      this.waitMode = this.register(SettingsManager.e("Mode", KillAura.WaitMode.DYNAMIC));
      this.waitTick = this.register(SettingsManager.integerBuilder("Tick Delay").withMinimum(0).withValue((int)3).withVisibility(this::lambda$new$1).build());
   }

   private boolean shouldAxe(Entity var1) {
      if ((Boolean)this.axe.getValue()) {
         return true;
      } else {
         if ((Boolean)this.autoAxe.getValue() && var1 instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var1;
            if (var2 != mc.func_175606_aa() && var2.func_70089_S()) {
               for(int var3 = 3; var3 >= 0; --var3) {
                  ItemStack var4 = (ItemStack)var2.field_71071_by.field_70460_b.get(var3);
                  if (var4.func_77973_b() instanceof ItemArmor && var4 != null && 100 * var4.func_77952_i() / var4.func_77958_k() > reverseNumber((Integer)this.autoAxeHealth.getValue(), 1, 100)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public void autoEquipWeapon(Entity var1) {
      int var2 = -1;

      for(int var3 = 0; var3 < 9; ++var3) {
         ItemStack var4 = mc.field_71439_g.field_71071_by.func_70301_a(var3);
         if (!var4.field_190928_g) {
            if (var4.func_77973_b() instanceof ItemAxe && this.shouldAxe(var1)) {
               var2 = var3;
               break;
            }

            if (var4.func_77973_b() instanceof ItemSword && (Boolean)this.autoSword.getValue()) {
               var2 = var3;
            }
         }
      }

      if (var2 != -1) {
         equip(var2);
      }

   }

   public void onUpdate() {
      if (!mc.field_71439_g.field_70128_L) {
         if (((KillAura.WaitMode)this.waitMode.getValue()).equals(KillAura.WaitMode.DYNAMIC)) {
            if (mc.field_71439_g.func_184825_o(this.getLagComp()) < 1.0F) {
               return;
            }

            if (mc.field_71439_g.field_70173_aa % 2 != 0) {
               return;
            }
         }

         if (((KillAura.WaitMode)this.waitMode.getValue()).equals(KillAura.WaitMode.STATIC) && (Integer)this.waitTick.getValue() > 0) {
            if (this.waitCounter < (Integer)this.waitTick.getValue()) {
               ++this.waitCounter;
               return;
            }

            this.waitCounter = 0;
         }

         Iterator var1 = Minecraft.func_71410_x().field_71441_e.field_72996_f.iterator();

         Entity var2;
         while(true) {
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var1.hasNext()) {
                                 return;
                              }

                              var2 = (Entity)var1.next();
                           } while(!EntityUtil.isLiving(var2));
                        } while(var2 == mc.field_71439_g);
                     } while((double)mc.field_71439_g.func_70032_d(var2) > (Double)this.hitRange.getValue());
                  } while(((EntityLivingBase)var2).func_110143_aJ() <= 0.0F);
               } while(((KillAura.WaitMode)this.waitMode.getValue()).equals(KillAura.WaitMode.DYNAMIC) && ((EntityLivingBase)var2).field_70737_aN != 0);
            } while(!(Boolean)this.ignoreWalls.getValue() && !mc.field_71439_g.func_70685_l(var2) && !this.canEntityFeetBeSeen(var2));

            if ((Boolean)this.attackPlayers.getValue() && var2 instanceof EntityPlayer && !Friends.isFriend(var2.func_70005_c_())) {
               this.autoEquipWeapon(var2);
               this.attack(var2);
               return;
            }

            if (EntityUtil.isPassive(var2)) {
               if ((Boolean)this.attackAnimals.getValue()) {
                  break;
               }
            } else if (EntityUtil.isMobAggressive(var2) && (Boolean)this.attackMobs.getValue()) {
               break;
            }
         }

         this.attack(var2);
      }
   }

   private void attack(Entity var1) {
      boolean var2 = false;
      if (this.checkSharpness(mc.field_71439_g.func_184614_ca())) {
         var2 = true;
      }

      if (!(Boolean)this.swordOnly.getValue() || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
         if (!(Boolean)this.cacheck.getValue() || !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).isEnabled()) {
            int var3 = -1;

            for(int var4 = 0; var4 < 9; ++var4) {
               ItemStack var5 = mc.field_71439_g.field_71071_by.func_70301_a(var4);
               if (var5 != ItemStack.field_190927_a && this.checkSharpness(var5)) {
                  var3 = var4;
                  break;
               }
            }

            if (var3 != -1) {
               mc.field_71439_g.field_71071_by.field_70461_c = var3;
               var2 = true;
            }

            if (var1 instanceof EntityPlayer) {
               KillStreak.addTargetedPlayer(var1.func_70005_c_());
            }

            mc.field_71442_b.func_78764_a(mc.field_71439_g, var1);
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
         }
      }
   }

   private boolean checkSharpness(ItemStack var1) {
      if (var1.func_77978_p() == null) {
         return false;
      } else {
         NBTTagList var2 = (NBTTagList)var1.func_77978_p().func_74781_a("ench");
         if (var2 == null) {
            return false;
         } else {
            for(int var3 = 0; var3 < var2.func_74745_c(); ++var3) {
               NBTTagCompound var4 = var2.func_150305_b(var3);
               if (var4.func_74762_e("id") == 16) {
                  int var5 = var4.func_74762_e("lvl");
                  if (var5 >= 42) {
                     return true;
                  }
                  break;
               }
            }

            return false;
         }
      }
   }

   private boolean lambda$new$1(Integer var1) {
      return ((KillAura.WaitMode)this.waitMode.getValue()).equals(KillAura.WaitMode.STATIC);
   }

   private static enum WaitMode {
      STATIC;

      private static final KillAura.WaitMode[] $VALUES = new KillAura.WaitMode[]{DYNAMIC, STATIC};
      DYNAMIC;
   }
}
