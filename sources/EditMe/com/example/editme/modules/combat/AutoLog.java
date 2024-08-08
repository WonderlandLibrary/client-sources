package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "AutoLog",
   category = Module.Category.COMBAT
)
public class AutoLog extends Module {
   private Setting onTotem = this.register(SettingsManager.b("Only when no totems", false));
   private Setting health = this.register(SettingsManager.integerBuilder("Health").withMinimum(1).withMaximum(20).withValue((int)4).build());
   private Setting onRender = this.register(SettingsManager.b("On Render", false));

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         if (!(Boolean)this.onTotem.getValue() || mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(AutoLog::lambda$onUpdate$0).mapToInt(ItemStack::func_190916_E).sum() + mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(AutoLog::lambda$onUpdate$1).mapToInt(ItemStack::func_190916_E).sum() <= 0) {
            if (mc.field_71439_g.func_110143_aJ() < (float)(Integer)this.health.getValue()) {
               mc.field_71441_e.func_72882_A();
               mc.func_147108_a(new GuiMainMenu());
               this.disable();
            }

            if ((Boolean)this.onRender.getValue()) {
               Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

               while(var1.hasNext()) {
                  EntityPlayer var2 = (EntityPlayer)var1.next();
                  if (var2 != mc.field_71439_g) {
                     mc.field_71441_e.func_72882_A();
                     mc.func_147108_a(new GuiMainMenu());
                     this.disable();
                  }
               }
            }

         }
      }
   }

   private static boolean lambda$onUpdate$0(ItemStack var0) {
      return var0.func_77973_b() == Items.field_190929_cY;
   }

   private static boolean lambda$onUpdate$1(ItemStack var0) {
      return var0.func_77973_b() == Items.field_190929_cY;
   }
}
