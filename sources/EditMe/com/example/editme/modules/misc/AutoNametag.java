package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

@Module.Info(
   name = "AutoNametag",
   category = Module.Category.MISC
)
public class AutoNametag extends Module {
   private Setting modeSetting;
   private Setting range;
   private String currentName;
   private int currentSlot;

   public AutoNametag() {
      this.modeSetting = this.register(SettingsManager.e("Mode", AutoNametag.Mode.WITHER));
      this.range = this.register(SettingsManager.floatBuilder("Range").withMinimum(2.0F).withValue((Number)3.5F).withMaximum(10.0F).build());
      this.currentName = "";
      this.currentSlot = -1;
   }

   private void selectNameTags() {
      if (this.currentSlot != -1 && this.isNametag(this.currentSlot)) {
         mc.field_71439_g.field_71071_by.field_70461_c = this.currentSlot;
      } else {
         this.disable();
      }
   }

   private void findNameTags() {
      for(int var1 = 0; var1 < 9; ++var1) {
         ItemStack var2 = mc.field_71439_g.field_71071_by.func_70301_a(var1);
         if (var2 != ItemStack.field_190927_a && !(var2.func_77973_b() instanceof ItemBlock) && this.isNametag(var1)) {
            this.currentName = var2.func_82833_r();
            this.currentSlot = var1;
         }
      }

   }

   public void onUpdate() {
      this.findNameTags();
      this.useNameTag();
   }

   private boolean isNametag(int var1) {
      ItemStack var2 = mc.field_71439_g.field_71071_by.func_70301_a(var1);
      Item var3 = var2.func_77973_b();
      return var3 instanceof ItemNameTag && !var2.func_82833_r().equals("Name Tag");
   }

   private void useNameTag() {
      int var1 = mc.field_71439_g.field_71071_by.field_70461_c;
      Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

      while(true) {
         Entity var3;
         label41:
         do {
            while(var2.hasNext()) {
               var3 = (Entity)var2.next();
               switch((AutoNametag.Mode)this.modeSetting.getValue()) {
               case WITHER:
                  if (var3 instanceof EntityWither && !var3.func_145748_c_().func_150260_c().equals(this.currentName) && mc.field_71439_g.func_70032_d(var3) <= (Float)this.range.getValue()) {
                     this.selectNameTags();
                     mc.field_71442_b.func_187097_a(mc.field_71439_g, var3, EnumHand.MAIN_HAND);
                  }
                  break;
               case ANY:
                  continue label41;
               }
            }

            mc.field_71439_g.field_71071_by.field_70461_c = var1;
            return;
         } while(!(var3 instanceof EntityMob) && !(var3 instanceof EntityAnimal));

         if (!var3.func_145748_c_().func_150260_c().equals(this.currentName) && mc.field_71439_g.func_70032_d(var3) <= (Float)this.range.getValue()) {
            this.selectNameTags();
            mc.field_71442_b.func_187097_a(mc.field_71439_g, var3, EnumHand.MAIN_HAND);
         }
      }
   }

   private static enum Mode {
      private static final AutoNametag.Mode[] $VALUES = new AutoNametag.Mode[]{WITHER, ANY};
      WITHER,
      ANY;
   }
}
