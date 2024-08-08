package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.InfoCalculator;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

@Module.Info(
   name = "InvInfo",
   category = Module.Category.HUD
)
public class InvInfo extends Module {
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(InvInfo::lambda$new$1).build());
   String currentString;
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(InvInfo::lambda$new$0).build());
   int color;

   private static boolean lambda$getItems$3(Item var0, ItemStack var1) {
      return var1.func_77973_b() == var0;
   }

   public int getWidth() {
      int var1 = 0;

      try {
         if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemTool || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
            var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Durability: ").append(InfoCalculator.dura(mc)))), var1);
         }

         if (ModuleManager.isModuleEnabled("AutoTotem") || ModuleManager.isModuleEnabled("OffHandCrystal")) {
            var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Totems: ").append(getItems(Items.field_190929_cY)))), var1);
         }

         if (mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemEndCrystal || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemEndCrystal || ModuleManager.isModuleEnabled("AutoCrystal") || ModuleManager.isModuleEnabled("OffHandCrystal")) {
            var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Crystals: ").append(getItems(Items.field_185158_cP)))), var1);
         }

         var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("XP: ").append(getItems(Items.field_151062_by)))), var1);
         var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Gapples: ").append(getItems(Items.field_151153_ao)))), var1);
      } catch (Exception var3) {
      }

      return var1;
   }

   public void onRender() {
      int var1 = (Integer)this.x.getValue();
      int var2 = (Integer)this.y.getValue();
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemTool || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
         this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Durability: ").append(InfoCalculator.dura(mc))), (double)var1, (double)var2, this.color);
         var2 += this.fontRenderer.getStringHeight("A") + 5;
      }

      if (ModuleManager.isModuleEnabled("AutoTotem") || ModuleManager.isModuleEnabled("OffHandCrystal")) {
         this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Totems: ").append(getItems(Items.field_190929_cY))), (double)var1, (double)var2, this.color);
         var2 += this.fontRenderer.getStringHeight("A") + 5;
      }

      if (mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemEndCrystal || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemEndCrystal || ModuleManager.isModuleEnabled("AutoCrystal") || ModuleManager.isModuleEnabled("OffHandCrystal")) {
         this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Crystals: ").append(getItems(Items.field_185158_cP))), (double)var1, (double)var2, this.color);
         var2 += this.fontRenderer.getStringHeight("A") + 5;
      }

      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("XP: ").append(getItems(Items.field_151062_by))), (double)var1, (double)var2, this.color);
      var2 += this.fontRenderer.getStringHeight("A") + 5;
      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Gapples: ").append(getItems(Items.field_151153_ao))), (double)var1, (double)var2, this.color);
   }

   public static int getItems(Item var0) {
      return mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(InvInfo::lambda$getItems$2).mapToInt(ItemStack::func_190916_E).sum() + mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(InvInfo::lambda$getItems$3).mapToInt(ItemStack::func_190916_E).sum();
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public int getHeight() {
      int var1 = 0;

      try {
         if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemTool || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
            var1 += this.fontRenderer.getStringHeight(String.valueOf((new StringBuilder()).append("Durability: ").append(InfoCalculator.dura(mc))));
            var1 += 5;
         }

         if (ModuleManager.isModuleEnabled("AutoTotem") || ModuleManager.isModuleEnabled("OffHandCrystal")) {
            var1 += this.fontRenderer.getStringHeight(String.valueOf((new StringBuilder()).append("Totems: ").append(getItems(Items.field_190929_cY))));
            var1 += 5;
         }

         if (mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemEndCrystal || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemEndCrystal || ModuleManager.isModuleEnabled("AutoCrystal") || ModuleManager.isModuleEnabled("OffHandCrystal")) {
            var1 += this.fontRenderer.getStringHeight(String.valueOf((new StringBuilder()).append("Crystals: ").append(getItems(Items.field_185158_cP))));
            var1 += 5;
         }

         var1 += this.fontRenderer.getStringHeight(String.valueOf((new StringBuilder()).append("XP: ").append(getItems(Items.field_151062_by))));
         var1 += 5;
         var1 += this.fontRenderer.getStringHeight(String.valueOf((new StringBuilder()).append("Gapples: ").append(getItems(Items.field_151153_ao))));
      } catch (Exception var3) {
      }

      return var1;
   }

   private static boolean lambda$getItems$2(Item var0, ItemStack var1) {
      return var1.func_77973_b() == var0;
   }

   public InvInfo() {
      this.color = Color.WHITE.getRGB();
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }
}
