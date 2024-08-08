package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "Welcome",
   category = Module.Category.HUD
)
public class Welcome extends Module {
   String currentString;
   int color;
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(Welcome::lambda$new$0).build());
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(Welcome::lambda$new$1).build());

   public int getWidth() {
      return this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Welcome, ").append(mc.func_110432_I().func_111285_a())));
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   public int getHeight() {
      return this.fontRenderer.getStringHeight("editme r5") + 5 + this.fontRenderer.getStringHeight(String.valueOf((new StringBuilder()).append("Welcome ").append(mc.func_110432_I().func_111285_a())));
   }

   private static boolean lambda$getItems$3(Item var0, ItemStack var1) {
      return var1.func_77973_b() == var0;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public static int getItems(Item var0) {
      return mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(Welcome::lambda$getItems$2).mapToInt(ItemStack::func_190916_E).sum() + mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(Welcome::lambda$getItems$3).mapToInt(ItemStack::func_190916_E).sum();
   }

   private static boolean lambda$getItems$2(Item var0, ItemStack var1) {
      return var1.func_77973_b() == var0;
   }

   public Welcome() {
      this.color = Color.WHITE.getRGB();
   }

   public void onRender() {
      int var1 = (Integer)this.x.getValue();
      int var2 = (Integer)this.y.getValue();
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      this.fontRenderer.drawStringWithShadow("editme r5", (double)var1, (double)var2, this.color);
      var2 += this.fontRenderer.getStringHeight("A") + 5;
      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Welcome, ").append(mc.func_110432_I().func_111285_a())), (double)var1, (double)var2, this.color);
   }
}
