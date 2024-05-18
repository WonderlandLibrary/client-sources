package net.minecraft.item.crafting;

import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.*;

public class FurnaceRecipes
{
    private Map<ItemStack, ItemStack> smeltingList;
    private static final FurnaceRecipes smeltingBase;
    private Map<ItemStack, Float> experienceList;
    
    public void addSmelting(final Item item, final ItemStack itemStack, final float n) {
        this.addSmeltingRecipe(new ItemStack(item, " ".length(), 1945 + 28883 - 12569 + 14508), itemStack, n);
    }
    
    public void addSmeltingRecipe(final ItemStack itemStack, final ItemStack itemStack2, final float n) {
        this.smeltingList.put(itemStack, itemStack2);
        this.experienceList.put(itemStack2, n);
    }
    
    private FurnaceRecipes() {
        this.smeltingList = (Map<ItemStack, ItemStack>)Maps.newHashMap();
        this.experienceList = (Map<ItemStack, Float>)Maps.newHashMap();
        this.addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7f);
        this.addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0f);
        this.addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0f);
        this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1f);
        this.addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35f);
        this.addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35f);
        this.addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35f);
        this.addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35f);
        this.addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1f);
        this.addSmeltingRecipe(new ItemStack(Blocks.stonebrick, " ".length(), BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, " ".length(), BlockStoneBrick.CRACKED_META), 0.1f);
        this.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3f);
        this.addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.cactus, new ItemStack(Items.dye, " ".length(), EnumDyeColor.GREEN.getDyeDamage()), 0.2f);
        this.addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, " ".length(), " ".length()), 0.15f);
        this.addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, " ".length(), " ".length()), 0.15f);
        this.addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0f);
        this.addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1f);
        this.addSmeltingRecipe(new ItemStack(Blocks.sponge, " ".length(), " ".length()), new ItemStack(Blocks.sponge, " ".length(), "".length()), 0.15f);
        final ItemFishFood.FishType[] values;
        final int length = (values = ItemFishFood.FishType.values()).length;
        int i = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (i < length) {
            final ItemFishFood.FishType fishType = values[i];
            if (fishType.canCook()) {
                this.addSmeltingRecipe(new ItemStack(Items.fish, " ".length(), fishType.getMetadata()), new ItemStack(Items.cooked_fish, " ".length(), fishType.getMetadata()), 0.35f);
            }
            ++i;
        }
        this.addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1f);
        this.addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7f);
        this.addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage()), 0.2f);
        this.addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2f);
    }
    
    static {
        smeltingBase = new FurnaceRecipes();
    }
    
    private boolean compareItemStacks(final ItemStack itemStack, final ItemStack itemStack2) {
        if (itemStack2.getItem() == itemStack.getItem() && (itemStack2.getMetadata() == 4866 + 25401 - 18432 + 20932 || itemStack2.getMetadata() == itemStack.getMetadata())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public float getSmeltingExperience(final ItemStack itemStack) {
        final Iterator<Map.Entry<ItemStack, Float>> iterator = this.experienceList.entrySet().iterator();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<ItemStack, Float> entry = iterator.next();
            if (this.compareItemStacks(itemStack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0.0f;
    }
    
    public ItemStack getSmeltingResult(final ItemStack itemStack) {
        final Iterator<Map.Entry<ItemStack, ItemStack>> iterator = this.smeltingList.entrySet().iterator();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<ItemStack, ItemStack> entry = iterator.next();
            if (this.compareItemStacks(itemStack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public void addSmeltingRecipeForBlock(final Block block, final ItemStack itemStack, final float n) {
        this.addSmelting(Item.getItemFromBlock(block), itemStack, n);
    }
    
    public static FurnaceRecipes instance() {
        return FurnaceRecipes.smeltingBase;
    }
    
    public Map<ItemStack, ItemStack> getSmeltingList() {
        return this.smeltingList;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
