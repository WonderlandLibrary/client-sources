package com.example.editme.util.client;

import com.example.editme.util.color.ColorTextFormatting;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class InfoUtil {
   private static Minecraft mc = Minecraft.func_71410_x();

   private static boolean lambda$getItems$1(Item var0, ItemStack var1) {
      return var1.func_77973_b() == var0;
   }

   public static ArrayList infoContents() {
      ArrayList var0 = new ArrayList();
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append("editme").append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" ").append("r5")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append("Welcome").append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" ").append(mc.func_110432_I().func_111285_a())));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(InfoCalculator.tps()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" tps")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(Minecraft.field_71470_ab).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" fps")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(InfoCalculator.speed(false, mc, 2)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" bps")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(InfoCalculator.ping(mc)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" ms")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(InfoCalculator.dura(mc)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" Durability")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(getItems(Items.field_190929_cY)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" Totems")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(getItems(Items.field_185158_cP)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" Crystals")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(getItems(Items.field_151062_by)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" EXP Bottles")));
      var0.add(String.valueOf((new StringBuilder()).append(getStringColour(setToText(ColorTextFormatting.ColourCode.RED))).append(getItems(Items.field_151153_ao)).append(getStringColour(setToText(ColorTextFormatting.ColourCode.WHITE))).append(" Gapples")));
      return var0;
   }

   public static String getStringColour(TextFormatting var0) {
      return var0.toString();
   }

   private static boolean lambda$getItems$0(Item var0, ItemStack var1) {
      return var1.func_77973_b() == var0;
   }

   public static ArrayList infoFrameContents() {
      ArrayList var0 = new ArrayList();

      try {
         var0.add("editme r5");
         var0.add(String.valueOf((new StringBuilder()).append("Welcome ").append(mc.func_110432_I().func_111285_a())));
         var0.add(String.valueOf((new StringBuilder()).append(InfoCalculator.tps()).append(" tps")));
         var0.add(String.valueOf((new StringBuilder()).append(Minecraft.field_71470_ab).append(" fps")));
         var0.add(String.valueOf((new StringBuilder()).append(InfoCalculator.speed(false, mc, 2)).append(" bps")));
         var0.add(String.valueOf((new StringBuilder()).append(InfoCalculator.ping(mc)).append(" ms")));
         var0.add(String.valueOf((new StringBuilder()).append(InfoCalculator.dura(mc)).append(" Durability")));
         var0.add(String.valueOf((new StringBuilder()).append(getItems(Items.field_190929_cY)).append(" Totems")));
         var0.add(String.valueOf((new StringBuilder()).append(getItems(Items.field_185158_cP)).append(" Crystals")));
         var0.add(String.valueOf((new StringBuilder()).append(getItems(Items.field_151062_by)).append(" EXP Bottles")));
         var0.add(String.valueOf((new StringBuilder()).append(getItems(Items.field_151153_ao)).append(" Gapples")));
      } catch (Exception var2) {
         var0.add("Currently not in game");
      }

      return var0;
   }

   private static TextFormatting setToText(ColorTextFormatting.ColourCode var0) {
      return (TextFormatting)ColorTextFormatting.toTextMap.get(var0);
   }

   public static int getItems(Item var0) {
      return mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(InfoUtil::lambda$getItems$0).mapToInt(ItemStack::func_190916_E).sum() + mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(InfoUtil::lambda$getItems$1).mapToInt(ItemStack::func_190916_E).sum();
   }
}
