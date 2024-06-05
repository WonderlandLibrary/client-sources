package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.google.common.collect.*;

public static class House2 extends Village
{
    private static final List villageBlacksmithChestContents;
    private boolean hasMadeChest;
    
    public House2() {
    }
    
    public House2(final Start p_i45563_1_, final int p_i45563_2_, final Random p_i45563_3_, final StructureBoundingBox p_i45563_4_, final EnumFacing p_i45563_5_) {
        super(p_i45563_1_, p_i45563_2_);
        this.coordBaseMode = p_i45563_5_;
        this.boundingBox = p_i45563_4_;
    }
    
    public static House2 func_175855_a(final Start p_175855_0_, final List p_175855_1_, final Random p_175855_2_, final int p_175855_3_, final int p_175855_4_, final int p_175855_5_, final EnumFacing p_175855_6_, final int p_175855_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, p_175855_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175855_1_, var8) == null) ? new House2(p_175855_0_, p_175855_7_, p_175855_2_, var8, p_175855_6_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Chest", this.hasMadeChest);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.hasMadeChest = p_143011_1_.getBoolean("Chest");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 3, 3, 1, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 4, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.furnace.getDefaultState(), 6, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.furnace.getDefaultState(), 6, 3, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 1, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, p_74875_3_);
        if (!this.hasMadeChest && p_74875_3_.func_175898_b(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5)))) {
            this.hasMadeChest = true;
            this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 5, 1, 5, House2.villageBlacksmithChestContents, 3 + p_74875_2_.nextInt(6));
        }
        for (int var4 = 6; var4 <= 8; ++var4) {
            if (this.func_175807_a(worldIn, var4, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, var4, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
                this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), var4, 0, -1, p_74875_3_);
            }
        }
        for (int var4 = 0; var4 < 7; ++var4) {
            for (int var5 = 0; var5 < 10; ++var5) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var5, 6, var4, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var5, -1, var4, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 7, 1, 1, 1);
        return true;
    }
    
    @Override
    protected int func_180779_c(final int p_180779_1_, final int p_180779_2_) {
        return 3;
    }
    
    static {
        villageBlacksmithChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
    }
}
