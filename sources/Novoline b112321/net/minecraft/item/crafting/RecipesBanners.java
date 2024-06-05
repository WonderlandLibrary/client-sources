package net.minecraft.item.crafting;

import net.H;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.RecipesBanners$RecipeDuplicatePattern;

public class RecipesBanners {
   void addRecipes(CraftingManager var1) {
      for(EnumDyeColor var5 : EnumDyeColor.values()) {
         var1.addRecipe(new ItemStack(Items.banner, 1, var5.getDyeDamage()), new Object[]{"###", "###", " | ", Character.valueOf('#'), new ItemStack(Blocks.wool, 1, var5.getMetadata()), Character.valueOf('|'), Items.stick});
      }

      var1.addRecipe(new RecipesBanners$RecipeDuplicatePattern());
      var1.addRecipe(new H());
   }
}
