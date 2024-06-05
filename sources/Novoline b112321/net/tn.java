package net;

import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeData$Recipe;

public class tn {
   public static String h(RecipeData$Recipe var0) {
      return var0.e();
   }

   public static String e(RecipeData$Recipe var0) {
      return var0.i();
   }

   public static Item[][] i(RecipeData$Recipe var0) {
      return var0.getIngredients();
   }

   public static Item a(RecipeData$Recipe var0) {
      return var0.getResult();
   }

   public static int d(RecipeData$Recipe var0) {
      return var0.d();
   }

   public static int c(RecipeData$Recipe var0) {
      return var0.b();
   }

   public static Item[] f(RecipeData$Recipe var0) {
      return var0.getIngredient();
   }

   public static float b(RecipeData$Recipe var0) {
      return var0.getExperience();
   }

   public static int g(RecipeData$Recipe var0) {
      return var0.f();
   }
}
