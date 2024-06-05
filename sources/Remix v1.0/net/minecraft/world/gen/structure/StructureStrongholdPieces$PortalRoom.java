package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public static class PortalRoom extends Stronghold
{
    private boolean hasSpawner;
    
    public PortalRoom() {
    }
    
    public PortalRoom(final int p_i45577_1_, final Random p_i45577_2_, final StructureBoundingBox p_i45577_3_, final EnumFacing p_i45577_4_) {
        super(p_i45577_1_);
        this.coordBaseMode = p_i45577_4_;
        this.boundingBox = p_i45577_3_;
    }
    
    public static PortalRoom func_175865_a(final List p_175865_0_, final Random p_175865_1_, final int p_175865_2_, final int p_175865_3_, final int p_175865_4_, final EnumFacing p_175865_5_, final int p_175865_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175865_0_, var7) == null) ? new PortalRoom(p_175865_6_, p_175865_1_, var7, p_175865_5_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Mob", this.hasSpawner);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.hasSpawner = p_143011_1_.getBoolean("Mob");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        if (p_74861_1_ != null) {
            ((Stairs2)p_74861_1_).strongholdPortalRoom = this;
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 10, 7, 15, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, Door.GRATES, 4, 1, 0);
        final byte var4 = 6;
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, var4, 1, 1, var4, 14, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, var4, 1, 9, var4, 14, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, var4, 1, 8, var4, 2, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, var4, 14, 8, var4, 14, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 1, 1, 2, 1, 4, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 8, 1, 1, 9, 1, 4, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 3, 1, 8, 7, 1, 12, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
        for (int var5 = 3; var5 < 14; var5 += 2) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, var5, 0, 4, var5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 10, 3, var5, 10, 4, var5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
        }
        for (int var5 = 2; var5 < 9; var5 += 2) {
            this.func_175804_a(worldIn, p_74875_3_, var5, 3, 15, var5, 4, 15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
        }
        int var5 = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 5, 6, 1, 7, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 2, 6, 6, 2, 7, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 3, 7, 6, 3, 7, false, p_74875_2_, StructureStrongholdPieces.access$000());
        for (int var6 = 4; var6 <= 6; ++var6) {
            this.func_175811_a(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(var5), var6, 1, 4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(var5), var6, 2, 5, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(var5), var6, 3, 6, p_74875_3_);
        }
        int var6 = EnumFacing.NORTH.getHorizontalIndex();
        int var7 = EnumFacing.SOUTH.getHorizontalIndex();
        int var8 = EnumFacing.EAST.getHorizontalIndex();
        int var9 = EnumFacing.WEST.getHorizontalIndex();
        if (this.coordBaseMode != null) {
            switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[this.coordBaseMode.ordinal()]) {
                case 2: {
                    var6 = EnumFacing.SOUTH.getHorizontalIndex();
                    var7 = EnumFacing.NORTH.getHorizontalIndex();
                    break;
                }
                case 3: {
                    var6 = EnumFacing.WEST.getHorizontalIndex();
                    var7 = EnumFacing.EAST.getHorizontalIndex();
                    var8 = EnumFacing.SOUTH.getHorizontalIndex();
                    var9 = EnumFacing.NORTH.getHorizontalIndex();
                    break;
                }
                case 4: {
                    var6 = EnumFacing.EAST.getHorizontalIndex();
                    var7 = EnumFacing.WEST.getHorizontalIndex();
                    var8 = EnumFacing.SOUTH.getHorizontalIndex();
                    var9 = EnumFacing.NORTH.getHorizontalIndex();
                    break;
                }
            }
        }
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var6).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 4, 3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var6).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 5, 3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var6).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 6, 3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var7).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 4, 3, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var7).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 5, 3, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var7).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 6, 3, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var8).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 3, 3, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var8).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 3, 3, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var8).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 3, 3, 11, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var9).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 7, 3, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var9).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 7, 3, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var9).withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9f), 7, 3, 11, p_74875_3_);
        if (!this.hasSpawner) {
            final int var10 = this.getYWithOffset(3);
            final BlockPos var11 = new BlockPos(this.getXWithOffset(5, 6), var10, this.getZWithOffset(5, 6));
            if (p_74875_3_.func_175898_b(var11)) {
                this.hasSpawner = true;
                worldIn.setBlockState(var11, Blocks.mob_spawner.getDefaultState(), 2);
                final TileEntity var12 = worldIn.getTileEntity(var11);
                if (var12 instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner)var12).getSpawnerBaseLogic().setEntityName("Silverfish");
                }
            }
        }
        return true;
    }
}
