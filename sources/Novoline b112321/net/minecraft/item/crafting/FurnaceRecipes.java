package net.minecraft.item.crafting;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood$FishType;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes {
   private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
   private Map smeltingList = Maps.newHashMap();
   private Map experienceList = Maps.newHashMap();

   public static FurnaceRecipes instance() {
      return smeltingBase;
   }

   private FurnaceRecipes() {
      this.addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
      this.addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
      this.addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
      this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
      this.addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
      this.addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
      this.addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
      this.addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35F);
      this.addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35F);
      this.addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
      this.addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1F);
      this.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
      this.addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
      this.addSmeltingRecipeForBlock(Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
      this.addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
      this.addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
      this.addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
      this.addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
      this.addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
      this.addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15F);

      for(ItemFishFood$FishType var4 : ItemFishFood$FishType.values()) {
         if(var4.canCook()) {
            this.addSmeltingRecipe(new ItemStack(Items.fish, 1, var4.getMetadata()), new ItemStack(Items.cooked_fish, 1, var4.getMetadata()), 0.35F);
         }
      }

      this.addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
      this.addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
      this.addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
      this.addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
   }

   public void addSmeltingRecipeForBlock(Block var1, ItemStack var2, float var3) {
      this.addSmelting(Item.getItemFromBlock(var1), var2, var3);
   }

   public void addSmelting(Item var1, ItemStack var2, float var3) {
      this.addSmeltingRecipe(new ItemStack(var1, 1, 32767), var2, var3);
   }

   public void addSmeltingRecipe(ItemStack var1, ItemStack var2, float var3) {
      this.smeltingList.put(var1, var2);
      this.experienceList.put(var2, Float.valueOf(var3));
   }

   public ItemStack getSmeltingResult(ItemStack var1) {
      for(Entry var3 : this.smeltingList.entrySet()) {
         if(this.compareItemStacks(var1, (ItemStack)var3.getKey())) {
            return (ItemStack)var3.getValue();
         }
      }

      return null;
   }

   private boolean compareItemStacks(ItemStack var1, ItemStack var2) {
      return var2.getItem() == var1.getItem() && (var2.getMetadata() == 32767 || var2.getMetadata() == var1.getMetadata());
   }

   public Map getSmeltingList() {
      return this.smeltingList;
   }

   public float getSmeltingExperience(ItemStack var1) {
      for(Entry var3 : this.experienceList.entrySet()) {
         if(this.compareItemStacks(var1, (ItemStack)var3.getKey())) {
            return ((Float)var3.getValue()).floatValue();
         }
      }

      return 0.0F;
   }
}
