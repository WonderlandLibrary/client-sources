package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public static class Torch extends Village
{
    public Torch() {
    }
    
    public Torch(final Start p_i45568_1_, final int p_i45568_2_, final Random p_i45568_3_, final StructureBoundingBox p_i45568_4_, final EnumFacing p_i45568_5_) {
        super(p_i45568_1_, p_i45568_2_);
        this.coordBaseMode = p_i45568_5_;
        this.boundingBox = p_i45568_4_;
    }
    
    public static StructureBoundingBox func_175856_a(final Start p_175856_0_, final List p_175856_1_, final Random p_175856_2_, final int p_175856_3_, final int p_175856_4_, final int p_175856_5_, final EnumFacing p_175856_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, p_175856_6_);
        return (StructureComponent.findIntersecting(p_175856_1_, var7) != null) ? null : var7;
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 0, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeColorDamage()), 1, 3, 0, p_74875_3_);
        final boolean var4 = this.coordBaseMode == EnumFacing.EAST || this.coordBaseMode == EnumFacing.NORTH;
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateY()), var4 ? 2 : 0, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode), 1, 3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateYCCW()), var4 ? 0 : 2, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.getOpposite()), 1, 3, -1, p_74875_3_);
        return true;
    }
}
