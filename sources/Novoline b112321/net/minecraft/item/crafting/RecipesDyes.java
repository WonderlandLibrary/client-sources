package net.minecraft.item.crafting;

import net.minecraft.block.BlockDoublePlant$EnumPlantType;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class RecipesDyes {
   public void addRecipes(CraftingManager var1) {
      for(int var2 = 0; var2 < 16; ++var2) {
         var1.addShapelessRecipe(new ItemStack(Blocks.wool, 1, var2), new Object[]{new ItemStack(Items.dye, 1, 15 - var2), new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0)});
         var1.addRecipe(new ItemStack(Blocks.stained_hardened_clay, 8, 15 - var2), new Object[]{"###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.hardened_clay), Character.valueOf('X'), new ItemStack(Items.dye, 1, var2)});
         var1.addRecipe(new ItemStack(Blocks.stained_glass, 8, 15 - var2), new Object[]{"###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.glass), Character.valueOf('X'), new ItemStack(Items.dye, 1, var2)});
         var1.addRecipe(new ItemStack(Blocks.stained_glass_pane, 16, var2), new Object[]{"###", "###", Character.valueOf('#'), new ItemStack(Blocks.stained_glass, 1, var2)});
      }

      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeDamage()), new Object[]{new ItemStack(Blocks.yellow_flower, 1, BlockFlower$EnumFlowerType.DANDELION.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.POPPY.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.WHITE.getDyeDamage()), new Object[]{Items.bone});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.ORANGE.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIME.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.GRAY.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.SILVER.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.GRAY.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.SILVER.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.CYAN.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PURPLE.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.PURPLE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.MAGENTA.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 4, EnumDyeColor.MAGENTA.getDyeDamage()), new Object[]{new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.BLUE_ORCHID.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.MAGENTA.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.ALLIUM.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.HOUSTONIA.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.RED_TULIP.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.ORANGE.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.ORANGE_TULIP.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.WHITE_TULIP.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.PINK_TULIP.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeDamage()), new Object[]{new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.OXEYE_DAISY.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.YELLOW.getDyeDamage()), new Object[]{new ItemStack(Blocks.double_plant, 1, BlockDoublePlant$EnumPlantType.SUNFLOWER.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeDamage()), new Object[]{new ItemStack(Blocks.double_plant, 1, BlockDoublePlant$EnumPlantType.SYRINGA.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.RED.getDyeDamage()), new Object[]{new ItemStack(Blocks.double_plant, 1, BlockDoublePlant$EnumPlantType.ROSE.getMeta())});
      var1.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeDamage()), new Object[]{new ItemStack(Blocks.double_plant, 1, BlockDoublePlant$EnumPlantType.PAEONIA.getMeta())});

      for(int var3 = 0; var3 < 16; ++var3) {
         var1.addRecipe(new ItemStack(Blocks.carpet, 3, var3), new Object[]{"##", Character.valueOf('#'), new ItemStack(Blocks.wool, 1, var3)});
      }

   }
}
