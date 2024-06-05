package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.google.common.collect.*;

public static class ChestCorridor extends Stronghold
{
    private static final List strongholdChestContents;
    private boolean hasMadeChest;
    
    public ChestCorridor() {
    }
    
    public ChestCorridor(final int p_i45582_1_, final Random p_i45582_2_, final StructureBoundingBox p_i45582_3_, final EnumFacing p_i45582_4_) {
        super(p_i45582_1_);
        this.coordBaseMode = p_i45582_4_;
        this.field_143013_d = this.getRandomDoor(p_i45582_2_);
        this.boundingBox = p_i45582_3_;
    }
    
    public static ChestCorridor func_175868_a(final List p_175868_0_, final Random p_175868_1_, final int p_175868_2_, final int p_175868_3_, final int p_175868_4_, final EnumFacing p_175868_5_, final int p_175868_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175868_0_, var7) == null) ? new ChestCorridor(p_175868_6_, p_175868_1_, var7, p_175868_5_) : null;
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
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 6);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 1, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 2, 4, p_74875_3_);
        for (int var4 = 2; var4 <= 4; ++var4) {
            this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 2, 1, var4, p_74875_3_);
        }
        if (!this.hasMadeChest && p_74875_3_.func_175898_b(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
            this.hasMadeChest = true;
            this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 2, 3, WeightedRandomChestContent.func_177629_a(ChestCorridor.strongholdChestContents, Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 2 + p_74875_2_.nextInt(2));
        }
        return true;
    }
    
    static {
        strongholdChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
    }
}
