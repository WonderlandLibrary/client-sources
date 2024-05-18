package wwe;

import net.minecraft.util.math.*;
import wwe.utils.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class PsqP
{
    public BlockPos maCT;
    public int SKIQ;
    public int lUAg;
    public float CPyB;
    
    public PsqP(final BlockPos maCT, final int luAg) {
        super();
        this.maCT = maCT;
        this.SKIQ = -1;
        this.lUAg = luAg;
        this.CPyB = (float)WRss(this.maCT);
    }
    
    public static int WRss(final BlockPos blockPos) {
        final IBlockState blockState = Wrapper.mc.world.getBlockState(blockPos.down());
        final Material material = blockState.getMaterial();
        int n = 0;
        if (material == Material.ROCK) {
            n = 1;
        }
        if (material == Material.SAND) {
            n = 2;
        }
        if (material == Material.GLASS) {
            n = 3;
        }
        if (material == Material.WOOD) {
            n = 4;
        }
        final Block block = blockState.getBlock();
        if (block == Blocks.CLAY) {
            n = 5;
        }
        if (block == Blocks.GOLD_BLOCK) {
            n = 6;
        }
        if (block == Blocks.WOOL) {
            n = 7;
        }
        if (block == Blocks.PACKED_ICE) {
            n = 8;
        }
        if (block == Blocks.BONE_BLOCK) {
            n = 9;
        }
        return n;
    }
}
