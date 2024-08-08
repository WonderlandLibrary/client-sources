package com.example.editme.modules.combat;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.setting.SettingsManager;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "AutoMend",
   category = Module.Category.COMBAT
)
public class AutoMend extends Module {
   private Setting autoAim = this.register(SettingsManager.b("Auto Aim", false));
   private Setting autoSwitch = this.register(SettingsManager.b("AutoSwitch", true));
   private Setting safetyRange = this.register(SettingsManager.integerBuilder("Safety Range").withMinimum(1).withMaximum(100).withValue((int)10));
   private Setting threshold = this.register(SettingsManager.integerBuilder("Repair %").withMinimum(1).withMaximum(100).withValue((int)75));
   private Setting safety = this.register(SettingsManager.booleanBuilder("Safety").withValue(false).build());
   private Setting autoDisable = this.register(SettingsManager.booleanBuilder("Auto Disable").withValue(false).withVisibility(this::lambda$new$0).build());
   private Setting autoThrow = this.register(SettingsManager.b("Throw", true));
   private int initHotbarSlot = -1;
   @EventHandler
   private Listener receiveListener = new Listener(AutoMend::lambda$new$1, new Predicate[0]);

   private static Float lambda$shouldMend$4(EntityEnderCrystal var0) {
      return mc.field_71439_g.func_70032_d(var0);
   }

   protected void onDisable() {
      if (mc.field_71439_g != null) {
         if ((Boolean)this.autoSwitch.getValue() && this.initHotbarSlot != -1 && this.initHotbarSlot != mc.field_71439_g.field_71071_by.field_70461_c) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.initHotbarSlot;
         }

      }
   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         if (this.shouldMend(0) || this.shouldMend(1) || this.shouldMend(2) || this.shouldMend(3)) {
            if ((Boolean)this.autoSwitch.getValue() && mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151062_by) {
               int var1 = this.findXpPots();
               if (var1 == -1) {
                  if ((Boolean)this.autoDisable.getValue()) {
                     this.disable();
                  }

                  return;
               }

               mc.field_71439_g.field_71071_by.field_70461_c = var1;
            }

            if ((Boolean)this.autoThrow.getValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
               if ((Boolean)this.autoAim.getValue()) {
                  mc.field_71439_g.field_70125_A = 90.0F;
               }

               mc.func_147121_ag();
            }
         }

      }
   }

   private static EntityEnderCrystal lambda$shouldMend$3(Entity var0) {
      return (EntityEnderCrystal)var0;
   }

   private int findXpPots() {
      int var1 = -1;

      for(int var2 = 0; var2 < 9; ++var2) {
         if (mc.field_71439_g.field_71071_by.func_70301_a(var2).func_77973_b() == Items.field_151062_by) {
            var1 = var2;
            break;
         }
      }

      return var1;
   }

   private boolean shouldMend(int var1) {
      if ((Boolean)this.safety.getValue()) {
         Iterator var2 = Minecraft.func_71410_x().field_71441_e.field_72996_f.iterator();

         while(var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if (var3 instanceof EntityPlayer && var3 != mc.field_71439_g && !Friends.isFriend(var3.func_70005_c_()) && var3.func_70032_d(mc.field_71439_g) < (float)(Integer)this.safetyRange.getValue()) {
               return false;
            }
         }

         EntityEnderCrystal var4 = (EntityEnderCrystal)mc.field_71441_e.field_72996_f.stream().filter(this::lambda$shouldMend$2).map(AutoMend::lambda$shouldMend$3).min(Comparator.comparing(AutoMend::lambda$shouldMend$4)).orElse((Object)null);
         if (var4 != null) {
            return false;
         }
      }

      if (((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b.get(var1)).func_77958_k() == 0) {
         return false;
      } else {
         return 100 * ((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b.get(var1)).func_77952_i() / ((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b.get(var1)).func_77958_k() > reverseNumber((Integer)this.threshold.getValue(), 1, 100);
      }
   }

   private static void lambda$new$1(PacketEvent.Receive var0) {
      if (mc.field_71439_g != null && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
         mc.field_71467_ac = 0;
      }

   }

   public static int reverseNumber(int var0, int var1, int var2) {
      return var2 + var1 - var0;
   }

   protected void onEnable() {
      if (mc.field_71439_g != null) {
         if ((Boolean)this.autoSwitch.getValue()) {
            this.initHotbarSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         }

      }
   }

   private boolean lambda$new$0(Boolean var1) {
      return (Boolean)this.autoSwitch.getValue();
   }

   private boolean lambda$shouldMend$2(Entity var1) {
      return var1 instanceof EntityEnderCrystal && mc.field_71439_g.func_70032_d(var1) <= (float)(Integer)this.safetyRange.getValue();
   }
}
