package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public static class Path extends Road
{
    private int averageGroundLevel;
    
    public Path() {
    }
    
    public Path(final Start p_i45562_1_, final int p_i45562_2_, final Random p_i45562_3_, final StructureBoundingBox p_i45562_4_, final EnumFacing p_i45562_5_) {
        super(p_i45562_1_, p_i45562_2_);
        this.coordBaseMode = p_i45562_5_;
        this.boundingBox = p_i45562_4_;
        this.averageGroundLevel = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
    }
    
    public static StructureBoundingBox func_175848_a(final Start p_175848_0_, final List p_175848_1_, final Random p_175848_2_, final int p_175848_3_, final int p_175848_4_, final int p_175848_5_, final EnumFacing p_175848_6_) {
        for (int var7 = 7 * MathHelper.getRandomIntegerInRange(p_175848_2_, 3, 5); var7 >= 7; var7 -= 7) {
            final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, var7, p_175848_6_);
            if (StructureComponent.findIntersecting(p_175848_1_, var8) == null) {
                return var8;
            }
        }
        return null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setInteger("Length", this.averageGroundLevel);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.averageGroundLevel = p_143011_1_.getInteger("Length");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        boolean var4 = false;
        for (int var5 = p_74861_3_.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + p_74861_3_.nextInt(5)) {
            final StructureComponent var6 = this.getNextComponentNN((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
            if (var6 != null) {
                var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                var4 = true;
            }
        }
        for (int var5 = p_74861_3_.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + p_74861_3_.nextInt(5)) {
            final StructureComponent var6 = this.getNextComponentPP((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
            if (var6 != null) {
                var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                var4 = true;
            }
        }
        if (var4 && p_74861_3_.nextInt(3) > 0 && this.coordBaseMode != null) {
            switch (StructureVillagePieces.SwitchEnumFacing.field_176064_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                    break;
                }
                case 2: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
                    break;
                }
                case 3: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    break;
                }
                case 4: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    break;
                }
            }
        }
        if (var4 && p_74861_3_.nextInt(3) > 0 && this.coordBaseMode != null) {
            switch (StructureVillagePieces.SwitchEnumFacing.field_176064_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                    break;
                }
                case 2: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
                    break;
                }
                case 3: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    break;
                }
                case 4: {
                    StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final IBlockState var4 = this.func_175847_a(Blocks.gravel.getDefaultState());
        final IBlockState var5 = this.func_175847_a(Blocks.cobblestone.getDefaultState());
        for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6) {
            for (int var7 = this.boundingBox.minZ; var7 <= this.boundingBox.maxZ; ++var7) {
                BlockPos var8 = new BlockPos(var6, 64, var7);
                if (p_74875_3_.func_175898_b(var8)) {
                    var8 = worldIn.func_175672_r(var8).offsetDown();
                    worldIn.setBlockState(var8, var4, 2);
                    worldIn.setBlockState(var8.offsetDown(), var5, 2);
                }
            }
        }
        return true;
    }
}
