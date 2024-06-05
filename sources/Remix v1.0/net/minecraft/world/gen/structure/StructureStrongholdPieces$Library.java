package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.google.common.collect.*;

public static class Library extends Stronghold
{
    private static final List strongholdLibraryChestContents;
    private boolean isLargeRoom;
    
    public Library() {
    }
    
    public Library(final int p_i45578_1_, final Random p_i45578_2_, final StructureBoundingBox p_i45578_3_, final EnumFacing p_i45578_4_) {
        super(p_i45578_1_);
        this.coordBaseMode = p_i45578_4_;
        this.field_143013_d = this.getRandomDoor(p_i45578_2_);
        this.boundingBox = p_i45578_3_;
        this.isLargeRoom = (p_i45578_3_.getYSize() > 6);
    }
    
    public static Library func_175864_a(final List p_175864_0_, final Random p_175864_1_, final int p_175864_2_, final int p_175864_3_, final int p_175864_4_, final EnumFacing p_175864_5_, final int p_175864_6_) {
        StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
        if (!Stronghold.canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_175864_0_, var7) != null) {
            var7 = StructureBoundingBox.func_175897_a(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
            if (!Stronghold.canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_175864_0_, var7) != null) {
                return null;
            }
        }
        return new Library(p_175864_6_, p_175864_1_, var7, p_175864_5_);
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Tall", this.isLargeRoom);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.isLargeRoom = p_143011_1_.getBoolean("Tall");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        byte var4 = 11;
        if (!this.isLargeRoom) {
            var4 = 6;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 13, var4 - 1, 14, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 1, 0);
        this.func_175805_a(worldIn, p_74875_3_, p_74875_2_, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false);
        final boolean var5 = true;
        final boolean var6 = true;
        for (int var7 = 1; var7 <= 13; ++var7) {
            if ((var7 - 1) % 4 == 0) {
                this.func_175804_a(worldIn, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 2, 3, var7, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 11, 3, var7, p_74875_3_);
                if (this.isLargeRoom) {
                    this.func_175804_a(worldIn, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
                    this.func_175804_a(worldIn, p_74875_3_, 12, 6, var7, 12, 9, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
                }
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                if (this.isLargeRoom) {
                    this.func_175804_a(worldIn, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                    this.func_175804_a(worldIn, p_74875_3_, 12, 6, var7, 12, 9, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                }
            }
        }
        for (int var7 = 3; var7 < 12; var7 += 2) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, var7, 4, 3, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, var7, 7, 3, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 1, var7, 10, 3, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
        }
        if (this.isLargeRoom) {
            this.func_175804_a(worldIn, p_74875_3_, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 9, 5, 11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 5, 11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 9, 5, 10, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 8, 6, 11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 10, p_74875_3_);
            final int var7 = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 1, 13, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 2, 13, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 3, 13, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 4, 13, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 5, 13, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 6, 13, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 7, 13, p_74875_3_);
            final byte var8 = 7;
            final byte var9 = 7;
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 9, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 9, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 8, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 8, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 7, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 2, 7, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 + 1, 7, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9 - 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9 + 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 7, var9 - 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 7, var9 + 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 - 2, 8, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 + 1, 8, var9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 - 1, 8, var9 - 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 - 1, 8, var9 + 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8, 8, var9 - 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8, 8, var9 + 1, p_74875_3_);
        }
        this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 3, 5, WeightedRandomChestContent.func_177629_a(Library.strongholdLibraryChestContents, Items.enchanted_book.func_92112_a(p_74875_2_, 1, 5, 2)), 1 + p_74875_2_.nextInt(4));
        if (this.isLargeRoom) {
            this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, 9, 1, p_74875_3_);
            this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 12, 8, 1, WeightedRandomChestContent.func_177629_a(Library.strongholdLibraryChestContents, Items.enchanted_book.func_92112_a(p_74875_2_, 1, 5, 2)), 1 + p_74875_2_.nextInt(4));
        }
        return true;
    }
    
    static {
        strongholdLibraryChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent(Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) });
    }
}
