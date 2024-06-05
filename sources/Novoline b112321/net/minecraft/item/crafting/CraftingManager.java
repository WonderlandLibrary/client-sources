package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.iD;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.BlockStone$EnumType;
import net.minecraft.block.BlockStoneSlab$EnumType;
import net.minecraft.block.BlockStoneSlabNew$EnumType;
import net.minecraft.block.BlockWall$EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCloning;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraft.item.crafting.RecipeRepairItem;
import net.minecraft.item.crafting.RecipesArmor;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.item.crafting.RecipesCrafting;
import net.minecraft.item.crafting.RecipesDyes;
import net.minecraft.item.crafting.RecipesFood;
import net.minecraft.item.crafting.RecipesIngots;
import net.minecraft.item.crafting.RecipesMapCloning;
import net.minecraft.item.crafting.RecipesMapExtending;
import net.minecraft.item.crafting.RecipesTools;
import net.minecraft.item.crafting.RecipesWeapons;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class CraftingManager {
   private static final CraftingManager instance = new CraftingManager();
   private final List recipes = Lists.newArrayList();

   public static CraftingManager getInstance() {
      return instance;
   }

   private CraftingManager() {
      (new RecipesTools()).addRecipes(this);
      (new RecipesWeapons()).addRecipes(this);
      (new RecipesIngots()).addRecipes(this);
      (new RecipesFood()).addRecipes(this);
      (new RecipesCrafting()).addRecipes(this);
      (new RecipesArmor()).addRecipes(this);
      (new RecipesDyes()).addRecipes(this);
      this.recipes.add(new RecipesArmorDyes());
      this.recipes.add(new RecipeBookCloning());
      this.recipes.add(new RecipesMapCloning());
      this.recipes.add(new RecipesMapExtending());
      this.recipes.add(new RecipeFireworks());
      this.recipes.add(new RecipeRepairItem());
      (new iD()).a(this);
      this.addRecipe(new ItemStack(Items.paper, 3), new Object[]{"###", Character.valueOf('#'), Items.reeds});
      this.addShapelessRecipe(new ItemStack(Items.book, 1), new Object[]{Items.paper, Items.paper, Items.paper, Items.leather});
      this.addShapelessRecipe(new ItemStack(Items.writable_book, 1), new Object[]{Items.book, new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), Items.feather});
      this.addRecipe(new ItemStack(Blocks.oak_fence, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.OAK.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.birch_fence, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.BIRCH.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.spruce_fence, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.SPRUCE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.jungle_fence, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.JUNGLE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.acacia_fence, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.ACACIA.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.dark_oak_fence, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall$EnumType.NORMAL.getMetadata()), new Object[]{"###", "###", Character.valueOf('#'), Blocks.cobblestone});
      this.addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall$EnumType.MOSSY.getMetadata()), new Object[]{"###", "###", Character.valueOf('#'), Blocks.mossy_cobblestone});
      this.addRecipe(new ItemStack(Blocks.nether_brick_fence, 6), new Object[]{"###", "###", Character.valueOf('#'), Blocks.nether_brick});
      this.addRecipe(new ItemStack(Blocks.oak_fence_gate, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.OAK.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.birch_fence_gate, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.BIRCH.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.spruce_fence_gate, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.SPRUCE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.jungle_fence_gate, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.JUNGLE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.acacia_fence_gate, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.ACACIA.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.dark_oak_fence_gate, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.jukebox, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.diamond});
      this.addRecipe(new ItemStack(Items.lead, 2), new Object[]{"~~ ", "~O ", "  ~", Character.valueOf('~'), Items.string, Character.valueOf('O'), Items.slime_ball});
      this.addRecipe(new ItemStack(Blocks.noteblock, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.redstone});
      this.addRecipe(new ItemStack(Blocks.bookshelf, 1), new Object[]{"###", "XXX", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.book});
      this.addRecipe(new ItemStack(Blocks.snow, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.snowball});
      this.addRecipe(new ItemStack(Blocks.snow_layer, 6), new Object[]{"###", Character.valueOf('#'), Blocks.snow});
      this.addRecipe(new ItemStack(Blocks.clay, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.clay_ball});
      this.addRecipe(new ItemStack(Blocks.brick_block, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.brick});
      this.addRecipe(new ItemStack(Blocks.glowstone, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.glowstone_dust});
      this.addRecipe(new ItemStack(Blocks.quartz_block, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.quartz});
      this.addRecipe(new ItemStack(Blocks.wool, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.string});
      this.addRecipe(new ItemStack(Blocks.tnt, 1), new Object[]{"X#X", "#X#", "X#X", Character.valueOf('X'), Items.gunpowder, Character.valueOf('#'), Blocks.sand});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.COBBLESTONE.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.cobblestone});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.STONE.getMetadata()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.stone, BlockStone$EnumType.STONE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.SAND.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.sandstone});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.BRICK.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.brick_block});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.SMOOTHBRICK.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.stonebrick});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.NETHERBRICK.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.nether_brick});
      this.addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab$EnumType.QUARTZ.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.quartz_block});
      this.addRecipe(new ItemStack(Blocks.stone_slab2, 6, BlockStoneSlabNew$EnumType.RED_SANDSTONE.getMetadata()), new Object[]{"###", Character.valueOf('#'), Blocks.red_sandstone});
      this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 0), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.OAK.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, BlockPlanks$EnumType.BIRCH.getMetadata()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.BIRCH.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, BlockPlanks$EnumType.SPRUCE.getMetadata()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.SPRUCE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, BlockPlanks$EnumType.JUNGLE.getMetadata()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.JUNGLE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 4 + BlockPlanks$EnumType.ACACIA.getMetadata() - 4), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.ACACIA.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 4 + BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.ladder, 3), new Object[]{"# #", "###", "# #", Character.valueOf('#'), Items.stick});
      this.addRecipe(new ItemStack(Items.oak_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.OAK.getMetadata())});
      this.addRecipe(new ItemStack(Items.spruce_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.SPRUCE.getMetadata())});
      this.addRecipe(new ItemStack(Items.birch_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.BIRCH.getMetadata())});
      this.addRecipe(new ItemStack(Items.jungle_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.JUNGLE.getMetadata())});
      this.addRecipe(new ItemStack(Items.acacia_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.ACACIA.getMetadata())});
      this.addRecipe(new ItemStack(Items.dark_oak_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.DARK_OAK.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.trapdoor, 2), new Object[]{"###", "###", Character.valueOf('#'), Blocks.planks});
      this.addRecipe(new ItemStack(Items.iron_door, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Blocks.iron_trapdoor, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Items.sign, 3), new Object[]{"###", "###", " X ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.stick});
      this.addRecipe(new ItemStack(Items.cake, 1), new Object[]{"AAA", "BEB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), Items.sugar, Character.valueOf('C'), Items.wheat, Character.valueOf('E'), Items.egg});
      this.addRecipe(new ItemStack(Items.sugar, 1), new Object[]{"#", Character.valueOf('#'), Items.reeds});
      this.addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks$EnumType.OAK.getMetadata()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks$EnumType.OAK.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks$EnumType.SPRUCE.getMetadata()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks$EnumType.SPRUCE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks$EnumType.BIRCH.getMetadata()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks$EnumType.BIRCH.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks$EnumType.JUNGLE.getMetadata()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks$EnumType.JUNGLE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks$EnumType.ACACIA.getMetadata() - 4), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, BlockPlanks$EnumType.ACACIA.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Items.stick, 4), new Object[]{"#", "#", Character.valueOf('#'), Blocks.planks});
      this.addRecipe(new ItemStack(Blocks.torch, 4), new Object[]{"X", "#", Character.valueOf('X'), Items.coal, Character.valueOf('#'), Items.stick});
      this.addRecipe(new ItemStack(Blocks.torch, 4), new Object[]{"X", "#", Character.valueOf('X'), new ItemStack(Items.coal, 1, 1), Character.valueOf('#'), Items.stick});
      this.addRecipe(new ItemStack(Items.bowl, 4), new Object[]{"# #", " # ", Character.valueOf('#'), Blocks.planks});
      this.addRecipe(new ItemStack(Items.glass_bottle, 3), new Object[]{"# #", " # ", Character.valueOf('#'), Blocks.glass});
      this.addRecipe(new ItemStack(Blocks.rail, 16), new Object[]{"X X", "X#X", "X X", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Items.stick});
      this.addRecipe(new ItemStack(Blocks.golden_rail, 6), new Object[]{"X X", "X#X", "XRX", Character.valueOf('X'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Items.stick});
      this.addRecipe(new ItemStack(Blocks.activator_rail, 6), new Object[]{"XSX", "X#X", "XSX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('S'), Items.stick});
      this.addRecipe(new ItemStack(Blocks.detector_rail, 6), new Object[]{"X X", "X#X", "XRX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Blocks.stone_pressure_plate});
      this.addRecipe(new ItemStack(Items.minecart, 1), new Object[]{"# #", "###", Character.valueOf('#'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Items.cauldron, 1), new Object[]{"# #", "# #", "###", Character.valueOf('#'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Items.brewing_stand, 1), new Object[]{" B ", "###", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('B'), Items.blaze_rod});
      this.addRecipe(new ItemStack(Blocks.lit_pumpkin, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.pumpkin, Character.valueOf('B'), Blocks.torch});
      this.addRecipe(new ItemStack(Items.chest_minecart, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.chest, Character.valueOf('B'), Items.minecart});
      this.addRecipe(new ItemStack(Items.furnace_minecart, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.furnace, Character.valueOf('B'), Items.minecart});
      this.addRecipe(new ItemStack(Items.tnt_minecart, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.tnt, Character.valueOf('B'), Items.minecart});
      this.addRecipe(new ItemStack(Items.hopper_minecart, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.hopper, Character.valueOf('B'), Items.minecart});
      this.addRecipe(new ItemStack(Items.boat, 1), new Object[]{"# #", "###", Character.valueOf('#'), Blocks.planks});
      this.addRecipe(new ItemStack(Items.bucket, 1), new Object[]{"# #", " # ", Character.valueOf('#'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Items.flower_pot, 1), new Object[]{"# #", " # ", Character.valueOf('#'), Items.brick});
      this.addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1), new Object[]{new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.flint, 1)});
      this.addRecipe(new ItemStack(Items.bread, 1), new Object[]{"###", Character.valueOf('#'), Items.wheat});
      this.addRecipe(new ItemStack(Blocks.oak_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.OAK.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.birch_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.BIRCH.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.spruce_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.SPRUCE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.jungle_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks$EnumType.JUNGLE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.acacia_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.ACACIA.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Blocks.dark_oak_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks$EnumType.DARK_OAK.getMetadata() - 4)});
      this.addRecipe(new ItemStack(Items.fishing_rod, 1), new Object[]{"  #", " #X", "# X", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.string});
      this.addRecipe(new ItemStack(Items.carrot_on_a_stick, 1), new Object[]{"# ", " X", Character.valueOf('#'), Items.fishing_rod, Character.valueOf('X'), Items.carrot});
      this.addRecipe(new ItemStack(Blocks.stone_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.cobblestone});
      this.addRecipe(new ItemStack(Blocks.brick_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.brick_block});
      this.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.stonebrick});
      this.addRecipe(new ItemStack(Blocks.nether_brick_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.nether_brick});
      this.addRecipe(new ItemStack(Blocks.sandstone_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.sandstone});
      this.addRecipe(new ItemStack(Blocks.red_sandstone_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.red_sandstone});
      this.addRecipe(new ItemStack(Blocks.quartz_stairs, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.quartz_block});
      this.addRecipe(new ItemStack(Items.painting, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Blocks.wool});
      this.addRecipe(new ItemStack(Items.item_frame, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.leather});
      this.addRecipe(new ItemStack(Items.golden_apple, 1, 0), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.apple});
      this.addRecipe(new ItemStack(Items.golden_apple, 1, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Blocks.gold_block, Character.valueOf('X'), Items.apple});
      this.addRecipe(new ItemStack(Items.golden_carrot, 1, 0), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.carrot});
      this.addRecipe(new ItemStack(Items.speckled_melon, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.melon});
      this.addRecipe(new ItemStack(Blocks.lever, 1), new Object[]{"X", "#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.stick});
      this.addRecipe(new ItemStack(Blocks.tripwire_hook, 2), new Object[]{"I", "S", "#", Character.valueOf('#'), Blocks.planks, Character.valueOf('S'), Items.stick, Character.valueOf('I'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Blocks.redstone_torch, 1), new Object[]{"X", "#", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.redstone});
      this.addRecipe(new ItemStack(Items.repeater, 1), new Object[]{"#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.redstone, Character.valueOf('I'), new ItemStack(Blocks.stone, 1, BlockStone$EnumType.STONE.getMetadata())});
      this.addRecipe(new ItemStack(Items.comparator, 1), new Object[]{" # ", "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.quartz, Character.valueOf('I'), new ItemStack(Blocks.stone, 1, BlockStone$EnumType.STONE.getMetadata())});
      this.addRecipe(new ItemStack(Items.clock, 1), new Object[]{" # ", "#X#", " # ", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.redstone});
      this.addRecipe(new ItemStack(Items.compass, 1), new Object[]{" # ", "#X#", " # ", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.redstone});
      this.addRecipe(new ItemStack(Items.map, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.paper, Character.valueOf('X'), Items.compass});
      this.addRecipe(new ItemStack(Blocks.stone_button, 1), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone$EnumType.STONE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[]{"#", Character.valueOf('#'), Blocks.planks});
      this.addRecipe(new ItemStack(Blocks.stone_pressure_plate, 1), new Object[]{"##", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone$EnumType.STONE.getMetadata())});
      this.addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), new Object[]{"##", Character.valueOf('#'), Blocks.planks});
      this.addRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), new Object[]{"##", Character.valueOf('#'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1), new Object[]{"##", Character.valueOf('#'), Items.gold_ingot});
      this.addRecipe(new ItemStack(Blocks.dispenser, 1), new Object[]{"###", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.bow, Character.valueOf('R'), Items.redstone});
      this.addRecipe(new ItemStack(Blocks.dropper, 1), new Object[]{"###", "# #", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('R'), Items.redstone});
      this.addRecipe(new ItemStack(Blocks.piston, 1), new Object[]{"TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('T'), Blocks.planks});
      this.addRecipe(new ItemStack(Blocks.sticky_piston, 1), new Object[]{"S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), Blocks.piston});
      this.addRecipe(new ItemStack(Items.bed, 1), new Object[]{"###", "XXX", Character.valueOf('#'), Blocks.wool, Character.valueOf('X'), Blocks.planks});
      this.addRecipe(new ItemStack(Blocks.enchanting_table, 1), new Object[]{" B ", "D#D", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('B'), Items.book, Character.valueOf('D'), Items.diamond});
      this.addRecipe(new ItemStack(Blocks.anvil, 1), new Object[]{"III", " i ", "iii", Character.valueOf('I'), Blocks.iron_block, Character.valueOf('i'), Items.iron_ingot});
      this.addRecipe(new ItemStack(Items.leather), new Object[]{"##", "##", Character.valueOf('#'), Items.rabbit_hide});
      this.addShapelessRecipe(new ItemStack(Items.ender_eye, 1), new Object[]{Items.ender_pearl, Items.blaze_powder});
      this.addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[]{Items.gunpowder, Items.blaze_powder, Items.coal});
      this.addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[]{Items.gunpowder, Items.blaze_powder, new ItemStack(Items.coal, 1, 1)});
      this.addRecipe(new ItemStack(Blocks.daylight_detector), new Object[]{"GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.glass, Character.valueOf('Q'), Items.quartz, Character.valueOf('W'), Blocks.wooden_slab});
      this.addRecipe(new ItemStack(Blocks.hopper), new Object[]{"I I", "ICI", " I ", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest});
      this.addRecipe(new ItemStack(Items.bU, 1), new Object[]{"///", " / ", "/_/", Character.valueOf('/'), Items.stick, Character.valueOf('_'), new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab$EnumType.STONE.getMetadata())});
      this.recipes.sort(CraftingManager::lambda$new$0);
   }

   public ShapedRecipes addRecipe(ItemStack var1, Object... var2) {
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      if(var2[var4] instanceof String[]) {
         String[] var12 = (String[])((String[])var2[var4++]);

         for(String var11 : var12) {
            ++var6;
            var5 = var11.length();
            var3.append(var11);
         }
      } else {
         while(var2[var4] instanceof String) {
            String var7 = (String)var2[var4++];
            ++var6;
            var5 = var7.length();
            var3.append(var7);
         }
      }

      HashMap var13;
      for(var13 = Maps.newHashMap(); var4 < var2.length; var4 += 2) {
         Character var14 = (Character)var2[var4];
         ItemStack var16 = null;
         if(var2[var4 + 1] instanceof Item) {
            var16 = new ItemStack((Item)var2[var4 + 1]);
         } else if(var2[var4 + 1] instanceof Block) {
            var16 = new ItemStack((Block)var2[var4 + 1], 1, 32767);
         } else if(var2[var4 + 1] instanceof ItemStack) {
            var16 = (ItemStack)var2[var4 + 1];
         }

         var13.put(var14, var16);
      }

      ItemStack[] var15 = new ItemStack[var5 * var6];

      for(int var17 = 0; var17 < var5 * var6; ++var17) {
         char var19 = var3.charAt(var17);
         if(var13.containsKey(Character.valueOf(var19))) {
            var15[var17] = ((ItemStack)var13.get(Character.valueOf(var19))).copy();
         } else {
            var15[var17] = null;
         }
      }

      ShapedRecipes var18 = new ShapedRecipes(var5, var6, var15, var1);
      this.recipes.add(var18);
      return var18;
   }

   public void addShapelessRecipe(ItemStack var1, Object... var2) {
      ArrayList var3 = Lists.newArrayList();

      for(Object var7 : var2) {
         if(var7 instanceof ItemStack) {
            var3.add(((ItemStack)var7).copy());
         } else if(var7 instanceof Item) {
            var3.add(new ItemStack((Item)var7));
         } else {
            if(!(var7 instanceof Block)) {
               throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + var7.getClass().getName() + "!");
            }

            var3.add(new ItemStack((Block)var7));
         }
      }

      this.recipes.add(new ShapelessRecipes(var1, var3));
   }

   public void addRecipe(IRecipe var1) {
      this.recipes.add(var1);
   }

   public ItemStack findMatchingRecipe(InventoryCrafting var1, World var2) {
      for(IRecipe var4 : this.recipes) {
         if(var4.matches(var1, var2)) {
            return var4.getCraftingResult(var1);
         }
      }

      return null;
   }

   public ItemStack[] func_180303_b(InventoryCrafting var1, World var2) {
      for(IRecipe var4 : this.recipes) {
         if(var4.matches(var1, var2)) {
            return var4.getRemainingItems(var1);
         }
      }

      ItemStack[] var5 = new ItemStack[var1.getSizeInventory()];

      for(int var6 = 0; var6 < var5.length; ++var6) {
         var5[var6] = var1.getStackInSlot(var6);
      }

      return var5;
   }

   public List getRecipeList() {
      return this.recipes;
   }

   private static int lambda$new$0(IRecipe var0, IRecipe var1) {
      return Integer.compare(var1.getRecipeSize(), var0.getRecipeSize());
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
