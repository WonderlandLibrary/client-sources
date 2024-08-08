package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "FastUse",
   category = Module.Category.COMBAT
)
public class FastUse extends Module {
   private Setting crystal = this.register(SettingsManager.b("Crystals", true));
   private Setting blocks = this.register(SettingsManager.b("Blocks", false));
   private Setting xpDelay = this.register(SettingsManager.integerBuilder("XP Delay").withMinimum(0).withMaximum(10).withValue((int)0).build());
   private int bow_delay = 0;
   private Setting exp = this.register(SettingsManager.b("Exp Bottles", true));
   private Setting bowDelay = this.register(SettingsManager.integerBuilder("Bow Delay").withMinimum(0).withMaximum(10).withValue((int)0).build());
   private int xp_delay = 0;
   private Setting other = this.register(SettingsManager.b("Other", false));
   private Setting selfBow = this.register(SettingsManager.b("Self Bow Spam", false));
   private Setting bow = this.register(SettingsManager.b("BowSpam", true));

   public void onUpdate() {
      if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle) {
         if ((Boolean)this.exp.getValue()) {
            if (this.xp_delay < 1) {
               mc.field_71467_ac = 0;
               this.xp_delay = (Integer)this.xpDelay.getValue();
            } else {
               --this.xp_delay;
            }
         }
      } else if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemEndCrystal) {
         if ((Boolean)this.crystal.getValue()) {
            mc.field_71467_ac = 0;
         }
      } else if (Block.func_149634_a(mc.field_71439_g.func_184614_ca().func_77973_b()).func_176223_P().func_185913_b()) {
         if ((Boolean)this.blocks.getValue()) {
            mc.field_71467_ac = 0;
         }
      } else if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBow) {
         if ((Boolean)this.bow.getValue()) {
            Minecraft var1 = Minecraft.func_71410_x();
            if (var1.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBow && var1.field_71439_g.func_184587_cr() && var1.field_71439_g.func_184612_cw() >= 3) {
               if (this.bow_delay < 1) {
                  if ((Boolean)this.selfBow.getValue()) {
                     var1.field_71439_g.field_70125_A = -90.0F;
                  }

                  var1.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, var1.field_71439_g.func_174811_aO()));
                  var1.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(var1.field_71439_g.func_184600_cs()));
                  var1.field_71439_g.func_184597_cx();
                  this.bow_delay = (Integer)this.bowDelay.getValue();
               } else if (this.bow_delay == 1 && (Boolean)this.selfBow.getValue()) {
                  var1.field_71439_g.field_70125_A = -90.0F;
                  --this.bow_delay;
               } else {
                  --this.bow_delay;
               }
            }
         } else if ((Boolean)this.other.getValue() && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBlock)) {
            mc.field_71467_ac = 0;
         }
      }

   }
}
