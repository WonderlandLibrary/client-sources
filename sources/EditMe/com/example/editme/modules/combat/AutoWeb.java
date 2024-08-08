package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(
   name = "AutoWeb",
   category = Module.Category.COMBAT
)
public class AutoWeb extends Module {
   public static List targets;
   BlockPos feet;
   public static float pitch;
   public static float yaw;
   int delay;
   private Setting doHead = this.register(SettingsManager.b("Do Head", true));
   BlockPos head;
   public static EntityPlayer target;

   public static boolean canBeClicked(BlockPos var0) {
      return mc.field_71441_e.func_180495_p(var0).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(var0), false);
   }

   public boolean isInBlockRange(Entity var1) {
      return var1.func_70032_d(mc.field_71439_g) <= 4.0F;
   }

   private boolean doesHotbarHaveObby() {
      for(int var1 = 36; var1 < 45; ++var1) {
         ItemStack var2 = mc.field_71439_g.field_71069_bz.func_75139_a(var1).func_75211_c();
         if (var2 != null && this.isStackObby(var2)) {
            return true;
         }
      }

      return false;
   }

   public static IBlockState getState(BlockPos var0) {
      return mc.field_71441_e.func_180495_p(var0);
   }

   public void onDisable() {
      this.delay = 0;
      yaw = mc.field_71439_g.field_70177_z;
      pitch = mc.field_71439_g.field_70125_A;
      target = null;
   }

   public static double roundToHalf(double var0) {
      return (double)Math.round(var0 * 2.0D) / 2.0D;
   }

   public void onUpdate() {
      if (!mc.field_71439_g.func_184587_cr()) {
         if (!this.isValid(target) || target == null) {
            this.updateTarget();
         }

         Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

         while(var1.hasNext()) {
            EntityPlayer var2 = (EntityPlayer)var1.next();
            if (!(var2 instanceof EntityPlayerSP) && this.isValid(var2) && var2.func_70032_d(mc.field_71439_g) < target.func_70032_d(mc.field_71439_g)) {
               target = var2;
               return;
            }
         }

         if (this.isValid(target) && mc.field_71439_g.func_70032_d(target) < 4.0F) {
            this.trap(target);
         } else {
            this.delay = 0;
         }

      }
   }

   public BlockPos getBlockPos(double var1, double var3, double var5) {
      return new BlockPos(var1, var3, var5);
   }

   public static Block getBlock(BlockPos var0) {
      return getState(var0).func_177230_c();
   }

   private boolean isStackObby(ItemStack var1) {
      return var1 != null && var1.func_77973_b() == Item.func_150899_d(30);
   }

   public void loadTargets() {
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (!(var2 instanceof EntityPlayerSP)) {
            if (this.isValid(var2)) {
               targets.add(var2);
            } else if (targets.contains(var2)) {
               targets.remove(var2);
            }
         }
      }

   }

   public void onEnable() {
      this.delay = 0;
   }

   public boolean isValid(EntityPlayer var1) {
      return var1 instanceof EntityPlayer && this.isInBlockRange(var1) && var1.func_110143_aJ() > 0.0F && !var1.field_70128_L && !var1.func_70005_c_().startsWith("Body #") && !Friends.isFriend(var1.func_70005_c_());
   }

   private void trap(EntityPlayer var1) {
      if ((double)var1.field_191988_bg == 0.0D && (double)var1.field_70702_br == 0.0D && (double)var1.field_70701_bs == 0.0D) {
         ++this.delay;
      }

      if ((double)var1.field_191988_bg != 0.0D || (double)var1.field_70702_br != 0.0D || (double)var1.field_70701_bs != 0.0D) {
         this.delay = 0;
      }

      if (!this.doesHotbarHaveObby()) {
         this.delay = 0;
      }

      if (this.delay == 2 && this.doesHotbarHaveObby()) {
         this.head = new BlockPos(var1.field_70165_t, var1.field_70163_u + 1.0D, var1.field_70161_v);
         this.feet = new BlockPos(var1.field_70165_t, var1.field_70163_u, var1.field_70161_v);

         for(int var2 = 36; var2 < 45; ++var2) {
            ItemStack var3 = mc.field_71439_g.field_71069_bz.func_75139_a(var2).func_75211_c();
            if (var3 != null && this.isStackObby(var3)) {
               int var4 = mc.field_71439_g.field_71071_by.field_70461_c;
               if (mc.field_71441_e.func_180495_p(this.head).func_185904_a().func_76222_j() || mc.field_71441_e.func_180495_p(this.feet).func_185904_a().func_76222_j()) {
                  mc.field_71439_g.field_71071_by.field_70461_c = var2 - 36;
                  if (mc.field_71441_e.func_180495_p(this.feet).func_185904_a().func_76222_j()) {
                     placeBlockLegit(this.feet);
                  }

                  if (mc.field_71441_e.func_180495_p(this.head).func_185904_a().func_76222_j() && (Boolean)this.doHead.getValue()) {
                     placeBlockLegit(this.head);
                  }

                  mc.field_71439_g.field_71071_by.field_70461_c = var4;
                  this.delay = 0;
                  break;
               }

               this.delay = 0;
            }

            this.delay = 0;
         }
      }

   }

   public void updateTarget() {
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (!(var2 instanceof EntityPlayerSP) && !(var2 instanceof EntityPlayerSP) && this.isValid(var2)) {
            target = var2;
         }
      }

   }

   public EnumFacing getEnumFacing(float var1, float var2, float var3) {
      return EnumFacing.func_176737_a(var1, var2, var3);
   }

   public static boolean placeBlockLegit(BlockPos var0) {
      Vec3d var1 = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
      Vec3d var2 = new Vec3d(var0);
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         BlockPos var7 = var0.func_177972_a(var6);
         if (canBeClicked(var7)) {
            Vec3d var8 = var2.func_178787_e((new Vec3d(var6.func_176730_m())).func_186678_a(0.5D));
            if (var1.func_72436_e(var8) <= 36.0D) {
               mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, var7, var6.func_176734_d(), var8, EnumHand.MAIN_HAND);
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);

               try {
                  TimeUnit.MILLISECONDS.sleep(10L);
               } catch (InterruptedException var10) {
                  var10.printStackTrace();
               }

               return true;
            }
         }
      }

      return false;
   }
}
