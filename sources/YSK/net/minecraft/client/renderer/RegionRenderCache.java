package net.minecraft.client.renderer;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.chunk.*;

public class RegionRenderCache extends ChunkCache
{
    private int[] combinedLights;
    private IBlockState[] blockStates;
    private final BlockPos position;
    private static final IBlockState DEFAULT_STATE;
    
    private int getPositionIndex(final BlockPos blockPos) {
        return (blockPos.getX() - this.position.getX()) * (296 + 188 - 345 + 261) + (blockPos.getZ() - this.position.getZ()) * (0x1B ^ 0xF) + (blockPos.getY() - this.position.getY());
    }
    
    static {
        DEFAULT_STATE = Blocks.air.getDefaultState();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RegionRenderCache(final World world, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        super(world, blockPos, blockPos2, n);
        this.position = blockPos.subtract(new Vec3i(n, n, n));
        Arrays.fill(this.combinedLights = new int[3581 + 2714 - 1478 + 3183], -" ".length());
        this.blockStates = new IBlockState[1685 + 42 + 4294 + 1979];
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        return this.chunkArray[(blockPos.getX() >> (0x23 ^ 0x27)) - this.chunkX][(blockPos.getZ() >> (0x1B ^ 0x1F)) - this.chunkZ].getTileEntity(blockPos, Chunk.EnumCreateEntityType.QUEUED);
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int positionIndex = this.getPositionIndex(blockPos);
        int combinedLight = this.combinedLights[positionIndex];
        if (combinedLight == -" ".length()) {
            combinedLight = super.getCombinedLight(blockPos, n);
            this.combinedLights[positionIndex] = combinedLight;
        }
        return combinedLight;
    }
    
    private IBlockState getBlockStateRaw(final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 62 + 241 - 124 + 77) {
            return this.chunkArray[(blockPos.getX() >> (0xBD ^ 0xB9)) - this.chunkX][(blockPos.getZ() >> (0x89 ^ 0x8D)) - this.chunkZ].getBlockState(blockPos);
        }
        return RegionRenderCache.DEFAULT_STATE;
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        final int positionIndex = this.getPositionIndex(blockPos);
        IBlockState blockStateRaw = this.blockStates[positionIndex];
        if (blockStateRaw == null) {
            blockStateRaw = this.getBlockStateRaw(blockPos);
            this.blockStates[positionIndex] = blockStateRaw;
        }
        return blockStateRaw;
    }
}
