package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import optifine.*;
import java.util.*;

public class BlockLeavesBase extends Block
{
    private static Map mapOriginalOpacity;
    protected boolean field_150121_P;
    
    protected BlockLeavesBase(final Material materialIn, final boolean fancyGraphics) {
        super(materialIn);
        this.field_150121_P = fancyGraphics;
    }
    
    public static void setLightOpacity(final Block block, final int opacity) {
        if (!BlockLeavesBase.mapOriginalOpacity.containsKey(block)) {
            BlockLeavesBase.mapOriginalOpacity.put(block, block.getLightOpacity());
        }
        block.setLightOpacity(opacity);
    }
    
    public static void restoreLightOpacity(final Block block) {
        if (BlockLeavesBase.mapOriginalOpacity.containsKey(block)) {
            final int opacity = BlockLeavesBase.mapOriginalOpacity.get(block);
            setLightOpacity(block, opacity);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return (!Config.isCullFacesLeaves() || worldIn.getBlockState(pos).getBlock() != this) && super.shouldSideBeRendered(worldIn, pos, side);
    }
    
    static {
        BlockLeavesBase.mapOriginalOpacity = new IdentityHashMap();
    }
}
