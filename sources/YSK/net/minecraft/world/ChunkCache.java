package net.minecraft.world;

import net.minecraft.world.chunk.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.biome.*;
import net.minecraft.init.*;

public class ChunkCache implements IBlockAccess
{
    protected boolean hasExtendedLevels;
    protected int chunkX;
    protected Chunk[][] chunkArray;
    protected int chunkZ;
    protected World worldObj;
    
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ChunkCache(final World worldObj, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        this.worldObj = worldObj;
        this.chunkX = blockPos.getX() - n >> (0x71 ^ 0x75);
        this.chunkZ = blockPos.getZ() - n >> (0xA8 ^ 0xAC);
        final int n2 = blockPos2.getX() + n >> (0xB7 ^ 0xB3);
        final int n3 = blockPos2.getZ() + n >> (0xC1 ^ 0xC5);
        this.chunkArray = new Chunk[n2 - this.chunkX + " ".length()][n3 - this.chunkZ + " ".length()];
        this.hasExtendedLevels = (" ".length() != 0);
        int i = this.chunkX;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i <= n2) {
            int j = this.chunkZ;
            "".length();
            if (true != true) {
                throw null;
            }
            while (j <= n3) {
                this.chunkArray[i - this.chunkX][j - this.chunkZ] = worldObj.getChunkFromChunkCoords(i, j);
                ++j;
            }
            ++i;
        }
        int k = blockPos.getX() >> (0x8C ^ 0x88);
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (k <= blockPos2.getX() >> (0xAD ^ 0xA9)) {
            int l = blockPos.getZ() >> (0x67 ^ 0x63);
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (l <= blockPos2.getZ() >> (0x3A ^ 0x3E)) {
                final Chunk chunk = this.chunkArray[k - this.chunkX][l - this.chunkZ];
                if (chunk != null && !chunk.getAreLevelsEmpty(blockPos.getY(), blockPos2.getY())) {
                    this.hasExtendedLevels = ("".length() != 0);
                }
                ++l;
            }
            ++k;
        }
    }
    
    @Override
    public WorldType getWorldType() {
        return this.worldObj.getWorldType();
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        return this.chunkArray[(blockPos.getX() >> (0x22 ^ 0x26)) - this.chunkX][(blockPos.getZ() >> (0xA6 ^ 0xA2)) - this.chunkZ].getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return this.hasExtendedLevels;
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 94 + 49 + 45 + 68) {
            return this.chunkArray[(blockPos.getX() >> (0xAD ^ 0xA9)) - this.chunkX][(blockPos.getZ() >> (0x6B ^ 0x6F)) - this.chunkZ].getLightFor(enumSkyBlock, blockPos);
        }
        return enumSkyBlock.defaultLightValue;
    }
    
    @Override
    public int getStrongPower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().getStrongPower(this, blockPos, blockState, enumFacing);
    }
    
    @Override
    public boolean isAirBlock(final BlockPos blockPos) {
        if (this.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final BlockPos blockPos) {
        return this.worldObj.getBiomeGenForCoords(blockPos);
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 37 + 133 - 44 + 130) {
            final int n = (blockPos.getX() >> (0xB8 ^ 0xBC)) - this.chunkX;
            final int n2 = (blockPos.getZ() >> (0x60 ^ 0x64)) - this.chunkZ;
            if (n >= 0 && n < this.chunkArray.length && n2 >= 0 && n2 < this.chunkArray[n].length) {
                final Chunk chunk = this.chunkArray[n][n2];
                if (chunk != null) {
                    return chunk.getBlockState(blockPos);
                }
            }
        }
        return Blocks.air.getDefaultState();
    }
    
    private int getLightForExt(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (enumSkyBlock == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
            return "".length();
        }
        if (blockPos.getY() < 0 || blockPos.getY() >= 218 + 33 - 231 + 236) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            return this.chunkArray[(blockPos.getX() >> (0x8B ^ 0x8F)) - this.chunkX][(blockPos.getZ() >> (0x28 ^ 0x2C)) - this.chunkZ].getLightFor(enumSkyBlock, blockPos);
        }
        int length = "".length();
        final EnumFacing[] values;
        final int length2 = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i < length2) {
            final int light = this.getLightFor(enumSkyBlock, blockPos.offset(values[i]));
            if (light > length) {
                length = light;
            }
            if (length >= (0x64 ^ 0x6B)) {
                return length;
            }
            ++i;
        }
        return length;
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int lightForExt = this.getLightForExt(EnumSkyBlock.SKY, blockPos);
        int lightForExt2 = this.getLightForExt(EnumSkyBlock.BLOCK, blockPos);
        if (lightForExt2 < n) {
            lightForExt2 = n;
        }
        return lightForExt << (0x44 ^ 0x50) | lightForExt2 << (0xA9 ^ 0xAD);
    }
}
