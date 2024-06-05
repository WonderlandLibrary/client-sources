package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.google.common.collect.*;

public static class JunglePyramid extends Feature
{
    private static final List field_175816_i;
    private static final List field_175815_j;
    private static Stones junglePyramidsRandomScatteredStones;
    private boolean field_74947_h;
    private boolean field_74948_i;
    private boolean field_74945_j;
    private boolean field_74946_k;
    
    public JunglePyramid() {
    }
    
    public JunglePyramid(final Random p_i2064_1_, final int p_i2064_2_, final int p_i2064_3_) {
        super(p_i2064_1_, p_i2064_2_, 64, p_i2064_3_, 12, 10, 15);
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("placedMainChest", this.field_74947_h);
        p_143012_1_.setBoolean("placedHiddenChest", this.field_74948_i);
        p_143012_1_.setBoolean("placedTrap1", this.field_74945_j);
        p_143012_1_.setBoolean("placedTrap2", this.field_74946_k);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.field_74947_h = p_143011_1_.getBoolean("placedMainChest");
        this.field_74948_i = p_143011_1_.getBoolean("placedHiddenChest");
        this.field_74945_j = p_143011_1_.getBoolean("placedTrap1");
        this.field_74946_k = p_143011_1_.getBoolean("placedTrap2");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (!this.func_74935_a(worldIn, p_74875_3_, 0)) {
            return false;
        }
        final int var4 = this.getMetadataWithOffset(Blocks.stone_stairs, 3);
        final int var5 = this.getMetadataWithOffset(Blocks.stone_stairs, 2);
        final int var6 = this.getMetadataWithOffset(Blocks.stone_stairs, 0);
        final int var7 = this.getMetadataWithOffset(Blocks.stone_stairs, 1);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 1, 2, 9, 2, 2, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 1, 12, 9, 2, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 1, 3, 2, 2, 11, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 1, 3, 9, 2, 11, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 1, 10, 6, 1, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 13, 10, 6, 13, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 2, 1, 6, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 10, 3, 2, 10, 6, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 3, 2, 9, 3, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 6, 2, 9, 6, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 3, 7, 3, 8, 7, 11, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 8, 4, 7, 8, 10, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithAir(worldIn, p_74875_3_, 3, 1, 3, 8, 2, 11);
        this.fillWithAir(worldIn, p_74875_3_, 4, 3, 6, 7, 3, 9);
        this.fillWithAir(worldIn, p_74875_3_, 2, 4, 2, 9, 5, 12);
        this.fillWithAir(worldIn, p_74875_3_, 4, 6, 5, 7, 6, 9);
        this.fillWithAir(worldIn, p_74875_3_, 5, 7, 6, 6, 7, 8);
        this.fillWithAir(worldIn, p_74875_3_, 5, 1, 2, 6, 2, 2);
        this.fillWithAir(worldIn, p_74875_3_, 5, 2, 12, 6, 2, 12);
        this.fillWithAir(worldIn, p_74875_3_, 5, 5, 1, 6, 5, 1);
        this.fillWithAir(worldIn, p_74875_3_, 5, 5, 13, 6, 5, 13);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 5, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, 5, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 5, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, 5, 9, p_74875_3_);
        for (int var8 = 0; var8 <= 14; var8 += 14) {
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 4, var8, 2, 5, var8, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 4, var8, 4, 5, var8, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 4, var8, 7, 5, var8, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 4, var8, 9, 5, var8, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 6, 0, 6, 6, 0, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        for (int var8 = 0; var8 <= 11; var8 += 11) {
            for (int var9 = 2; var9 <= 12; var9 += 2) {
                this.fillWithRandomizedBlocks(worldIn, p_74875_3_, var8, 4, var9, var8, 5, var9, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
            }
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, var8, 6, 5, var8, 6, 5, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, var8, 6, 9, var8, 6, 9, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 7, 2, 2, 9, 2, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 7, 2, 9, 9, 2, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 7, 12, 2, 9, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 7, 12, 9, 9, 12, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 9, 4, 4, 9, 4, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 9, 4, 7, 9, 4, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 9, 10, 4, 9, 10, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 9, 10, 7, 9, 10, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 9, 7, 6, 9, 7, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 5, 9, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 6, 9, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 5, 9, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 6, 9, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 0, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 5, 0, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 6, 0, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 0, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 1, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 2, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 3, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 1, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 2, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 3, 10, p_74875_3_);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 9, 4, 1, 9, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 1, 9, 7, 1, 9, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 10, 7, 2, 10, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 4, 5, 6, 4, 5, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var6), 4, 4, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var7), 7, 4, 5, p_74875_3_);
        for (int var8 = 0; var8 < 4; ++var8) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 5, 0 - var8, 6 + var8, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 6, 0 - var8, 6 + var8, p_74875_3_);
            this.fillWithAir(worldIn, p_74875_3_, 5, 0 - var8, 7 + var8, 6, 0 - var8, 9 + var8);
        }
        this.fillWithAir(worldIn, p_74875_3_, 1, -3, 12, 10, -1, 13);
        this.fillWithAir(worldIn, p_74875_3_, 1, -3, 1, 3, -1, 13);
        this.fillWithAir(worldIn, p_74875_3_, 1, -3, 1, 9, -1, 5);
        for (int var8 = 1; var8 <= 13; var8 += 2) {
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, -3, var8, 1, -2, var8, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        }
        for (int var8 = 2; var8 <= 12; var8 += 2) {
            this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, -1, var8, 3, -1, var8, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, -2, 1, 5, -2, 1, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, -2, 1, 9, -2, 1, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 6, -3, 1, 6, -3, 1, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 6, -1, 1, 6, -1, 1, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.func_175811_a(worldIn, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.EAST.getHorizontalIndex())).withProperty(BlockTripWireHook.field_176265_M, true), 1, -3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.WEST.getHorizontalIndex())).withProperty(BlockTripWireHook.field_176265_M, true), 4, -3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 2, -3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 3, -3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 4, -3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 3, -3, 1, p_74875_3_);
        if (!this.field_74945_j) {
            this.field_74945_j = this.func_175806_a(worldIn, p_74875_3_, p_74875_2_, 3, -2, 1, EnumFacing.NORTH.getIndex(), JunglePyramid.field_175815_j, 2);
        }
        this.func_175811_a(worldIn, Blocks.vine.getStateFromMeta(15), 3, -2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.NORTH.getHorizontalIndex())).withProperty(BlockTripWireHook.field_176265_M, true), 7, -3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.SOUTH.getHorizontalIndex())).withProperty(BlockTripWireHook.field_176265_M, true), 7, -3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 7, -3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 7, -3, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 7, -3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 8, -3, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 9, -3, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 9, -3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 9, -2, 4, p_74875_3_);
        if (!this.field_74946_k) {
            this.field_74946_k = this.func_175806_a(worldIn, p_74875_3_, p_74875_2_, 9, -2, 3, EnumFacing.WEST.getIndex(), JunglePyramid.field_175815_j, 2);
        }
        this.func_175811_a(worldIn, Blocks.vine.getStateFromMeta(15), 8, -1, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.vine.getStateFromMeta(15), 8, -2, 3, p_74875_3_);
        if (!this.field_74947_h) {
            this.field_74947_h = this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 8, -3, 3, WeightedRandomChestContent.func_177629_a(JunglePyramid.field_175816_i, Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 2 + p_74875_2_.nextInt(5));
        }
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 4, -3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 5, -2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 5, -1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 6, -3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 7, -2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 7, -1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 5, p_74875_3_);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, -1, 1, 9, -1, 5, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithAir(worldIn, p_74875_3_, 8, -3, 8, 10, -1, 10);
        this.func_175811_a(worldIn, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 8, -2, 11, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 9, -2, 11, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 10, -2, 11, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.lever.getStateFromMeta(BlockLever.func_176357_a(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))), 8, -2, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.lever.getStateFromMeta(BlockLever.func_176357_a(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))), 9, -2, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.lever.getStateFromMeta(BlockLever.func_176357_a(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))), 10, -2, 12, p_74875_3_);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 8, -3, 8, 8, -3, 10, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 10, -3, 8, 10, -3, 10, false, p_74875_2_, JunglePyramid.junglePyramidsRandomScatteredStones);
        this.func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 10, -2, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 8, -2, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 8, -2, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 10, -1, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sticky_piston.getStateFromMeta(EnumFacing.UP.getIndex()), 9, -2, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sticky_piston.getStateFromMeta(this.getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())), 10, -2, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sticky_piston.getStateFromMeta(this.getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())), 10, -1, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.unpowered_repeater.getStateFromMeta(this.getMetadataWithOffset(Blocks.unpowered_repeater, EnumFacing.NORTH.getHorizontalIndex())), 10, -2, 10, p_74875_3_);
        if (!this.field_74948_i) {
            this.field_74948_i = this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 9, -3, 10, WeightedRandomChestContent.func_177629_a(JunglePyramid.field_175816_i, Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 2 + p_74875_2_.nextInt(5));
        }
        return true;
    }
    
    static {
        field_175816_i = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
        field_175815_j = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30) });
        JunglePyramid.junglePyramidsRandomScatteredStones = new Stones(null);
    }
    
    static class Stones extends BlockSelector
    {
        private Stones() {
        }
        
        Stones(final ComponentScatteredFeaturePieces.SwitchEnumFacing p_i45583_1_) {
            this();
        }
        
        @Override
        public void selectBlocks(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_, final int p_75062_4_, final boolean p_75062_5_) {
            if (p_75062_1_.nextFloat() < 0.4f) {
                this.field_151562_a = Blocks.cobblestone.getDefaultState();
            }
            else {
                this.field_151562_a = Blocks.mossy_cobblestone.getDefaultState();
            }
        }
    }
}
