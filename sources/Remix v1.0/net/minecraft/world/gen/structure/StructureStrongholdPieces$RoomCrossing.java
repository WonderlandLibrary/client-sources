package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public static class RoomCrossing extends Stronghold
{
    private static final List strongholdRoomCrossingChestContents;
    protected int roomType;
    
    public RoomCrossing() {
    }
    
    public RoomCrossing(final int p_i45575_1_, final Random p_i45575_2_, final StructureBoundingBox p_i45575_3_, final EnumFacing p_i45575_4_) {
        super(p_i45575_1_);
        this.coordBaseMode = p_i45575_4_;
        this.field_143013_d = this.getRandomDoor(p_i45575_2_);
        this.boundingBox = p_i45575_3_;
        this.roomType = p_i45575_2_.nextInt(5);
    }
    
    public static RoomCrossing func_175859_a(final List p_175859_0_, final Random p_175859_1_, final int p_175859_2_, final int p_175859_3_, final int p_175859_4_, final EnumFacing p_175859_5_, final int p_175859_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175859_0_, var7) == null) ? new RoomCrossing(p_175859_6_, p_175859_1_, var7, p_175859_5_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setInteger("Type", this.roomType);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.roomType = p_143011_1_.getInteger("Type");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
        this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
        this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 10, 6, 10, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 1, 0);
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        switch (this.roomType) {
            case 0: {
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 4, 3, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 6, 3, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 5, 3, 4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 5, 3, 6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 6, p_74875_3_);
                break;
            }
            case 1: {
                for (int var4 = 0; var4 < 5; ++var4) {
                    this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + var4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + var4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3 + var4, 1, 3, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3 + var4, 1, 7, p_74875_3_);
                }
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.flowing_water.getDefaultState(), 5, 4, 5, p_74875_3_);
                break;
            }
            case 2: {
                for (int var4 = 1; var4 <= 9; ++var4) {
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 1, 3, var4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 9, 3, var4, p_74875_3_);
                }
                for (int var4 = 1; var4 <= 9; ++var4) {
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), var4, 3, 1, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), var4, 3, 9, p_74875_3_);
                }
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, 3, 5, p_74875_3_);
                for (int var4 = 1; var4 <= 3; ++var4) {
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, var4, 4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, var4, 4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, var4, 6, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, var4, 6, p_74875_3_);
                }
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 5, 3, 5, p_74875_3_);
                for (int var4 = 2; var4 <= 8; ++var4) {
                    this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 2, 3, var4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 3, 3, var4, p_74875_3_);
                    if (var4 <= 3 || var4 >= 7) {
                        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 4, 3, var4, p_74875_3_);
                        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 5, 3, var4, p_74875_3_);
                        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 6, 3, var4, p_74875_3_);
                    }
                    this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 7, 3, var4, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 3, var4, p_74875_3_);
                }
                this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 1, 3, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 2, 3, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 3, 3, p_74875_3_);
                this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 4, 8, WeightedRandomChestContent.func_177629_a(RoomCrossing.strongholdRoomCrossingChestContents, Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 1 + p_74875_2_.nextInt(4));
                break;
            }
        }
        return true;
    }
    
    static {
        strongholdRoomCrossingChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) });
    }
}
