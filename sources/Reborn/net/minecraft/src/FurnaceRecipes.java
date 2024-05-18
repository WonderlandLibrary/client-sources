package net.minecraft.src;

import java.util.*;

public class FurnaceRecipes
{
    private static final FurnaceRecipes smeltingBase;
    private Map smeltingList;
    private Map experienceList;
    
    static {
        smeltingBase = new FurnaceRecipes();
    }
    
    public static final FurnaceRecipes smelting() {
        return FurnaceRecipes.smeltingBase;
    }
    
    private FurnaceRecipes() {
        this.smeltingList = new HashMap();
        this.experienceList = new HashMap();
        this.addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron), 0.7f);
        this.addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold), 1.0f);
        this.addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond), 1.0f);
        this.addSmelting(Block.sand.blockID, new ItemStack(Block.glass), 0.1f);
        this.addSmelting(Item.porkRaw.itemID, new ItemStack(Item.porkCooked), 0.35f);
        this.addSmelting(Item.beefRaw.itemID, new ItemStack(Item.beefCooked), 0.35f);
        this.addSmelting(Item.chickenRaw.itemID, new ItemStack(Item.chickenCooked), 0.35f);
        this.addSmelting(Item.fishRaw.itemID, new ItemStack(Item.fishCooked), 0.35f);
        this.addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone), 0.1f);
        this.addSmelting(Item.clay.itemID, new ItemStack(Item.brick), 0.3f);
        this.addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2), 0.2f);
        this.addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1), 0.15f);
        this.addSmelting(Block.oreEmerald.blockID, new ItemStack(Item.emerald), 1.0f);
        this.addSmelting(Item.potato.itemID, new ItemStack(Item.bakedPotato), 0.35f);
        this.addSmelting(Block.netherrack.blockID, new ItemStack(Item.netherrackBrick), 0.1f);
        this.addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal), 0.1f);
        this.addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone), 0.7f);
        this.addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 1, 4), 0.2f);
        this.addSmelting(Block.oreNetherQuartz.blockID, new ItemStack(Item.netherQuartz), 0.2f);
    }
    
    public void addSmelting(final int par1, final ItemStack par2ItemStack, final float par3) {
        this.smeltingList.put(par1, par2ItemStack);
        this.experienceList.put(par2ItemStack.itemID, par3);
    }
    
    public ItemStack getSmeltingResult(final int par1) {
        return this.smeltingList.get(par1);
    }
    
    public Map getSmeltingList() {
        return this.smeltingList;
    }
    
    public float getExperience(final int par1) {
        return this.experienceList.containsKey(par1) ? this.experienceList.get(par1) : 0.0f;
    }
}
