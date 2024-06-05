package net.minecraft.client.renderer;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;

public class RegionRenderCache extends ChunkCache
{
    private static final IBlockState DEFAULT_STATE;
    private final BlockPos position;
    private int[] combinedLights;
    private IBlockState[] blockStates;
    
    public RegionRenderCache(final World worldIn, final BlockPos posFromIn, final BlockPos posToIn, final int subIn) {
        super(worldIn, posFromIn, posToIn, subIn);
        this.position = posFromIn.subtract(new Vec3i(subIn, subIn, subIn));
        final int i = 8000;
        this.combinedLights = new int[8000];
        Arrays.fill(this.combinedLights, -1);
        this.blockStates = new IBlockState[8000];
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos pos) {
        final int i = (pos.getX() >> 4) - this.chunkX;
        final int j = (pos.getZ() >> 4) - this.chunkZ;
        return this.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.QUEUED);
    }
    
    @Override
    public int getCombinedLight(final BlockPos pos, final int lightValue) {
        final int i = this.getPositionIndex(pos);
        int j = this.combinedLights[i];
        if (j == -1) {
            j = super.getCombinedLight(pos, lightValue);
            this.combinedLights[i] = j;
        }
        return j;
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos pos) {
        final int i = this.getPositionIndex(pos);
        IBlockState iblockstate = this.blockStates[i];
        if (iblockstate == null) {
            iblockstate = this.getBlockStateRaw(pos);
            this.blockStates[i] = iblockstate;
        }
        return iblockstate;
    }
    
    private IBlockState getBlockStateRaw(final BlockPos pos) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            final int i = (pos.getX() >> 4) - this.chunkX;
            final int j = (pos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[i][j].getBlockState(pos);
        }
        return RegionRenderCache.DEFAULT_STATE;
    }
    
    private int getPositionIndex(final BlockPos p_175630_1_) {
        final int i = p_175630_1_.getX() - this.position.getX();
        final int j = p_175630_1_.getY() - this.position.getY();
        final int k = p_175630_1_.getZ() - this.position.getZ();
        return i * 400 + k * 20 + j;
    }
    
    static {
        DEFAULT_STATE = Blocks.air.getDefaultState();
    }
}
