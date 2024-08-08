package com.example.editme.modules.combat;

import com.example.editme.events.EventPlayerUpdate;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.PlayerUtil;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

@Module.Info(
   name = "OffHandGapple",
   category = Module.Category.COMBAT,
   description = "Lets you crystal offhand"
)
public class OffhandGapple extends Module {
   int crystals;
   boolean moving;
   private Setting ecpMode;
   Item item;
   private Setting crystalCheck = this.register(SettingsManager.b("Enemy Crystal Predictor [BETA]", false));
   private Setting disableTotem = this.register(SettingsManager.b("Disable Totem", true));
   boolean returnI;
   private int cooldown;
   private Setting totemOnElytra;
   private Setting fallDistance = this.register(SettingsManager.floatBuilder("Fall Distance").withMinimum(0.0F).withMaximum(100.0F).withValue((Number)30.0F));
   @EventHandler
   private Listener OnPlayerUpdate;
   private Setting health = this.register(SettingsManager.integerBuilder("Health").withMinimum(0).withMaximum(20).withValue((int)8).build());
   public int totems;
   private Setting cumulative = this.register(SettingsManager.b("Cumulative DMG Check", false));
   private Setting crystalCooldown;

   private boolean crystalCheck() {
      boolean var1 = true;
      boolean var2 = false;
      float var3 = 0.0F;
      ArrayList var4 = new ArrayList();
      var4.add(this.calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 0)));
      var4.add(this.calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 0)));
      var4.add(this.calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 1)));
      var4.add(this.calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -1)));
      var4.add(this.calculateDamageAABB(mc.field_71439_g.func_180425_c()));
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         float var6 = (Float)var5.next();
         var3 += var6;
         if (this.ecpMode.getValue() == OffhandGapple.ECPMode.TOTEMATHEALTH) {
            var2 = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - var6 <= (float)(Integer)this.health.getValue();
         } else if (this.ecpMode.getValue() == OffhandGapple.ECPMode.TOTEMATONE) {
            var2 = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - var6 <= 1.0F;
         } else if (this.ecpMode.getValue() == OffhandGapple.ECPMode.TOTEMATZERO) {
            var2 = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - var6 < 1.0F;
         }

         if (var2) {
            var1 = false;
         }
      }

      if (!var1) {
         return false;
      } else if ((Boolean)this.cumulative.getValue() && mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - var3 <= (float)(Integer)this.health.getValue()) {
         return false;
      } else {
         return true;
      }
   }

   private static float getDamageMultiplied(float var0) {
      int var1 = mc.field_71441_e.func_175659_aa().func_151525_a();
      return var0 * (var1 == 0 ? 0.0F : (var1 == 2 ? 1.0F : (var1 == 1 ? 0.5F : 1.5F)));
   }

   protected void onEnable() {
      if ((Boolean)this.disableTotem.getValue()) {
         ModuleManager.disableModule("AutoTotem");
      }

   }

   private float calculateDamageAABB(BlockPos var1) {
      List var2 = (List)mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(var1)).stream().filter(OffhandGapple::lambda$calculateDamageAABB$1).collect(Collectors.toList());
      float var3 = 0.0F;

      Entity var5;
      for(Iterator var4 = var2.iterator(); var4.hasNext(); var3 += calculateDamage(var5.field_70165_t, var5.field_70163_u, var5.field_70161_v, mc.field_71439_g)) {
         var5 = (Entity)var4.next();
      }

      return var3;
   }

   public static float getBlastReduction(EntityLivingBase var0, float var1, Explosion var2) {
      if (var0 instanceof EntityPlayer) {
         EntityPlayer var3 = (EntityPlayer)var0;
         DamageSource var4 = DamageSource.func_94539_a(var2);
         var1 = CombatRules.func_189427_a(var1, (float)var3.func_70658_aO(), (float)var3.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         int var5 = EnchantmentHelper.func_77508_a(var3.func_184193_aE(), var4);
         float var6 = MathHelper.func_76131_a((float)var5, 0.0F, 20.0F);
         var1 *= 1.0F - var6 / 25.0F;
         if (var0.func_70644_a(Potion.func_188412_a(11))) {
            var1 -= var1 / 4.0F;
         }

         return var1;
      } else {
         var1 = CombatRules.func_189427_a(var1, (float)var0.func_70658_aO(), (float)var0.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         return var1;
      }
   }

   private void SwitchOffHandIfNeed(Item var1) {
      Item var3 = Items.field_151153_ao;
      if (mc.field_71439_g.func_184592_cb().func_77973_b() != var1) {
         int var4 = PlayerUtil.GetItemSlot(var1);
         if (var4 == -1 && var1 != var3 && mc.field_71439_g.func_184592_cb().func_77973_b() != var3) {
            var4 = PlayerUtil.GetItemSlot(var3);
            if (var4 == -1 && var3 != Items.field_190929_cY) {
               var3 = Items.field_190929_cY;
               if (var1 != var3 && mc.field_71439_g.func_184592_cb().func_77973_b() != var3) {
                  var4 = PlayerUtil.GetItemSlot(var3);
               }
            }
         }

         if (var4 != -1) {
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, var4, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 45, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, var4, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_78765_e();
         }
      }

   }

   private static boolean lambda$calculateDamageAABB$1(Entity var0) {
      return var0 instanceof EntityEnderCrystal;
   }

   public static float calculateDamage(double var0, double var2, double var4, Entity var6) {
      float var7 = 12.0F;
      double var8 = var6.func_70011_f(var0, var2, var4) / 12.0D;
      Vec3d var10 = new Vec3d(var0, var2, var4);
      double var11 = (double)var6.field_70170_p.func_72842_a(var10, var6.func_174813_aQ());
      double var13 = (1.0D - var8) * var11;
      float var15 = (float)((int)((var13 * var13 + var13) / 2.0D * 7.0D * 12.0D + 1.0D));
      double var16 = 1.0D;
      if (var6 instanceof EntityLivingBase) {
         var16 = (double)getBlastReduction((EntityLivingBase)var6, getDamageMultiplied(var15), new Explosion(mc.field_71441_e, (Entity)null, var0, var2, var4, 6.0F, false, true));
      }

      return (float)var16;
   }

   protected void onDisable() {
      if ((Boolean)this.disableTotem.getValue()) {
         ModuleManager.enableModule("AutoTotem");
      }

   }

   private void lambda$new$0(EventPlayerUpdate var1) {
      if (!(mc.field_71462_r instanceof GuiContainer)) {
         if (this.shouldTotem() || (Boolean)this.totemOnElytra.getValue() && mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_70143_R >= (Float)this.fallDistance.getValue() && !mc.field_71439_g.func_184613_cA()) {
            this.SwitchOffHandIfNeed(Items.field_190929_cY);
         } else if (this.cooldown > 0) {
            --this.cooldown;
         } else {
            this.SwitchOffHandIfNeed(Items.field_151153_ao);
            this.cooldown = (Integer)this.crystalCooldown.getValue();
         }
      }
   }

   private boolean shouldTotem() {
      boolean var1 = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() <= (float)(Integer)this.health.getValue();
      boolean var2 = this.crystalCheck();
      if (!(Boolean)this.crystalCheck.getValue()) {
         return var1;
      } else {
         return var1 || !var2;
      }
   }

   public OffhandGapple() {
      this.ecpMode = this.register(SettingsManager.e("ECP Mode", OffhandGapple.ECPMode.TOTEMATHEALTH));
      this.totemOnElytra = this.register(SettingsManager.b("Force Totem on Elytra", true));
      this.crystalCooldown = this.register(SettingsManager.i("Return to Gapple Delay", 5));
      this.moving = false;
      this.cooldown = (Integer)this.crystalCooldown.getValue();
      this.OnPlayerUpdate = new Listener(this::lambda$new$0, new Predicate[0]);
   }

   private static enum ECPMode {
      private static final OffhandGapple.ECPMode[] $VALUES = new OffhandGapple.ECPMode[]{TOTEMATONE, TOTEMATZERO, TOTEMATHEALTH};
      TOTEMATZERO,
      TOTEMATHEALTH,
      TOTEMATONE;
   }
}
