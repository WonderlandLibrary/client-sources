package com.example.editme.modules.combat;

import com.example.editme.events.EventPlayerUpdate;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.PlayerUtil;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

@Module.Info(
   name = "AutoTotem",
   category = Module.Category.COMBAT
)
public class AutoTotem extends Module {
   private Setting autoStrenthPotMinHealth = this.register(SettingsManager.floatBuilder("Autostrengthpot Min Health").withMinimum(0.0F).withMaximum(20.0F).withValue((Number)20.0F).withVisibility(this::lambda$new$2));
   private Setting fallDistance = this.register(SettingsManager.floatBuilder("Fall Distance").withMinimum(0.0F).withMaximum(100.0F).withValue((Number)30.0F));
   private Setting crystalRange = this.register(SettingsManager.integerBuilder("CrystalRange").withMinimum(0).withMaximum(100).withValue((int)10).withVisibility(this::lambda$new$0));
   private Setting offHandGapHealthValue = this.register(SettingsManager.floatBuilder("Offhand Gap Health").withMinimum(0.0F).withMaximum(20.0F).withValue((Number)8.0F).withVisibility(this::lambda$new$1));
   private Setting health = this.register(SettingsManager.floatBuilder("Health").withMinimum(0.0F).withMaximum(20.0F).withValue((Number)10.0F));
   private Setting autoStrengthPot = this.register(SettingsManager.b("Automatically offhand strength pot when you don't have strength", false));
   private Setting crystalTotem = this.register(SettingsManager.b("Totem when any crystals", false));
   @EventHandler
   private Listener OnPlayerUpdate = new Listener(this::lambda$new$3, new Predicate[0]);
   private Setting totemOnElytra = this.register(SettingsManager.b("Force Totem on Elytra", true));
   private Setting autoOffHandGap = this.register(SettingsManager.b("Offhand Gapple", false));

   private boolean lambda$new$1(Float var1) {
      return (Boolean)this.autoOffHandGap.getValue();
   }

   public Item GetItemFromModeVal(AutoTotem.AutoTotemMode var1) {
      switch(var1) {
      case Gap:
         return Items.field_151153_ao;
      case Strength:
         return Items.field_151068_bn;
      default:
         return Items.field_190929_cY;
      }
   }

   private boolean lambda$new$0(Integer var1) {
      return (Boolean)this.crystalTotem.getValue();
   }

   private void lambda$new$3(EventPlayerUpdate var1) {
      float var2 = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj();
      Iterator var3 = mc.field_71441_e.field_72996_f.iterator();

      while(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if (var4 instanceof EntityEnderCrystal && var4.func_70032_d(mc.field_71439_g) < (float)(Integer)this.crystalRange.getValue()) {
            var2 = 0.0F;
         }
      }

      if (!(mc.field_71462_r instanceof GuiInventory)) {
         if (!mc.field_71439_g.func_184614_ca().func_190926_b()) {
            if ((Float)this.autoStrenthPotMinHealth.getValue() <= var2 && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && (Boolean)this.autoStrengthPot.getValue() && !mc.field_71439_g.func_70644_a(MobEffects.field_76420_g)) {
               this.SwitchOffHandIfNeed(AutoTotem.AutoTotemMode.Strength);
               return;
            }

            if ((Float)this.offHandGapHealthValue.getValue() <= var2 && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && (Boolean)this.autoOffHandGap.getValue()) {
               this.SwitchOffHandIfNeed(AutoTotem.AutoTotemMode.Gap);
               return;
            }
         }

         if ((Float)this.health.getValue() > var2 || (Boolean)this.totemOnElytra.getValue() && mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_70143_R >= (Float)this.fallDistance.getValue() && !mc.field_71439_g.func_184613_cA()) {
            this.SwitchOffHandIfNeed(AutoTotem.AutoTotemMode.Totem);
         }
      }
   }

   private void SwitchOffHandIfNeed(AutoTotem.AutoTotemMode var1) {
      Item var2 = this.GetItemFromModeVal(var1);
      Item var3 = this.GetItemFromModeVal(AutoTotem.AutoTotemMode.Totem);
      if (mc.field_71439_g.func_184592_cb().func_77973_b() != var2) {
         int var4 = PlayerUtil.GetItemSlot(var2);
         if (var4 == -1 && var2 != var3 && mc.field_71439_g.func_184592_cb().func_77973_b() != var3) {
            var4 = PlayerUtil.GetItemSlot(var3);
            if (var4 == -1 && var3 != Items.field_190929_cY) {
               var3 = Items.field_190929_cY;
               if (var2 != var3 && mc.field_71439_g.func_184592_cb().func_77973_b() != var3) {
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

   private boolean lambda$new$2(Float var1) {
      return (Boolean)this.autoStrengthPot.getValue();
   }

   public static enum AutoTotemMode {
      Gap,
      Totem,
      Strength;

      private static final AutoTotem.AutoTotemMode[] $VALUES = new AutoTotem.AutoTotemMode[]{Totem, Gap, Strength};
   }
}
