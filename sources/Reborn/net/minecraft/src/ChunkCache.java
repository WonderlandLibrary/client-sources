package net.minecraft.src;

public class ChunkCache implements IBlockAccess
{
    private int chunkX;
    private int chunkZ;
    private Chunk[][] chunkArray;
    private boolean hasExtendedLevels;
    private World worldObj;
    
    public ChunkCache(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8) {
        this.worldObj = par1World;
        this.chunkX = par2 - par8 >> 4;
        this.chunkZ = par4 - par8 >> 4;
        final int var9 = par5 + par8 >> 4;
        final int var10 = par7 + par8 >> 4;
        this.chunkArray = new Chunk[var9 - this.chunkX + 1][var10 - this.chunkZ + 1];
        this.hasExtendedLevels = true;
        for (int var11 = this.chunkX; var11 <= var9; ++var11) {
            for (int var12 = this.chunkZ; var12 <= var10; ++var12) {
                final Chunk var13 = par1World.getChunkFromChunkCoords(var11, var12);
                if (var13 != null) {
                    this.chunkArray[var11 - this.chunkX][var12 - this.chunkZ] = var13;
                }
            }
        }
        for (int var11 = par2 >> 4; var11 <= par5 >> 4; ++var11) {
            for (int var12 = par4 >> 4; var12 <= par7 >> 4; ++var12) {
                final Chunk var13 = this.chunkArray[var11 - this.chunkX][var12 - this.chunkZ];
                if (var13 != null && !var13.getAreLevelsEmpty(par3, par6)) {
                    this.hasExtendedLevels = false;
                }
            }
        }
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return this.hasExtendedLevels;
    }
    
    @Override
    public int getBlockId(final int par1, final int par2, final int par3) {
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            return 0;
        }
        final int var4 = (par1 >> 4) - this.chunkX;
        final int var5 = (par3 >> 4) - this.chunkZ;
        if (var4 >= 0 && var4 < this.chunkArray.length && var5 >= 0 && var5 < this.chunkArray[var4].length) {
            final Chunk var6 = this.chunkArray[var4][var5];
            return (var6 == null) ? 0 : var6.getBlockID(par1 & 0xF, par2, par3 & 0xF);
        }
        return 0;
    }
    
    @Override
    public TileEntity getBlockTileEntity(final int par1, final int par2, final int par3) {
        final int var4 = (par1 >> 4) - this.chunkX;
        final int var5 = (par3 >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].getChunkBlockTileEntity(par1 & 0xF, par2, par3 & 0xF);
    }
    
    @Override
    public float getBrightness(final int par1, final int par2, final int par3, final int par4) {
        int var5 = this.getLightValue(par1, par2, par3);
        if (var5 < par4) {
            var5 = par4;
        }
        return this.worldObj.provider.lightBrightnessTable[var5];
    }
    
    @Override
    public int getLightBrightnessForSkyBlocks(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
        int var6 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
        if (var6 < par4) {
            var6 = par4;
        }
        return var5 << 20 | var6 << 4;
    }
    
    @Override
    public float getLightBrightness(final int par1, final int par2, final int par3) {
        return this.worldObj.provider.lightBrightnessTable[this.getLightValue(par1, par2, par3)];
    }
    
    public int getLightValue(final int par1, final int par2, final int par3) {
        return this.getLightValueExt(par1, par2, par3, true);
    }
    
    public int getLightValueExt(final int par1, final int par2, final int par3, final boolean par4) {
        if (par1 < -30000000 || par3 < -30000000 || par1 >= 30000000 || par3 > 30000000) {
            return 15;
        }
        if (par4) {
            final int var5 = this.getBlockId(par1, par2, par3);
            if (var5 == Block.stoneSingleSlab.blockID || var5 == Block.woodSingleSlab.blockID || var5 == Block.tilledField.blockID || var5 == Block.stairsWoodOak.blockID || var5 == Block.stairsCobblestone.blockID) {
                int var6 = this.getLightValueExt(par1, par2 + 1, par3, false);
                final int var7 = this.getLightValueExt(par1 + 1, par2, par3, false);
                final int var8 = this.getLightValueExt(par1 - 1, par2, par3, false);
                final int var9 = this.getLightValueExt(par1, par2, par3 + 1, false);
                final int var10 = this.getLightValueExt(par1, par2, par3 - 1, false);
                if (var7 > var6) {
                    var6 = var7;
                }
                if (var8 > var6) {
                    var6 = var8;
                }
                if (var9 > var6) {
                    var6 = var9;
                }
                if (var10 > var6) {
                    var6 = var10;
                }
                return var6;
            }
        }
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            int var5 = 15 - this.worldObj.skylightSubtracted;
            if (var5 < 0) {
                var5 = 0;
            }
            return var5;
        }
        int var5 = (par1 >> 4) - this.chunkX;
        int var6 = (par3 >> 4) - this.chunkZ;
        return this.chunkArray[var5][var6].getBlockLightValue(par1 & 0xF, par2, par3 & 0xF, this.worldObj.skylightSubtracted);
    }
    
    @Override
    public int getBlockMetadata(final int par1, final int par2, final int par3) {
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            return 0;
        }
        final int var4 = (par1 >> 4) - this.chunkX;
        final int var5 = (par3 >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].getBlockMetadata(par1 & 0xF, par2, par3 & 0xF);
    }
    
    @Override
    public Material getBlockMaterial(final int par1, final int par2, final int par3) {
        final int var4 = this.getBlockId(par1, par2, par3);
        return (var4 == 0) ? Material.air : Block.blocksList[var4].blockMaterial;
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final int par1, final int par2) {
        return this.worldObj.getBiomeGenForCoords(par1, par2);
    }
    
    @Override
    public boolean isBlockOpaqueCube(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.getBlockId(par1, par2, par3)];
        return var4 != null && var4.isOpaqueCube();
    }
    
    @Override
    public boolean isBlockNormalCube(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.getBlockId(par1, par2, par3)];
        return var4 != null && (var4.blockMaterial.blocksMovement() && var4.renderAsNormalBlock());
    }
    
    @Override
    public boolean doesBlockHaveSolidTopSurface(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.getBlockId(par1, par2, par3)];
        return this.worldObj.isBlockTopFacingSurfaceSolid(var4, this.getBlockMetadata(par1, par2, par3));
    }
    
    @Override
    public Vec3Pool getWorldVec3Pool() {
        return this.worldObj.getWorldVec3Pool();
    }
    
    @Override
    public boolean isAirBlock(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.getBlockId(par1, par2, par3)];
        return var4 == null;
    }
    
    public int getSkyBlockTypeBrightness(final EnumSkyBlock par1EnumSkyBlock, final int par2, int par3, final int par4) {
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            par3 = 255;
        }
        if (par3 < 0 || par3 >= 256 || par2 < -30000000 || par4 < -30000000 || par2 >= 30000000 || par4 > 30000000) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        if (par1EnumSkyBlock == EnumSkyBlock.Sky && this.worldObj.provider.hasNoSky) {
            return 0;
        }
        if (Block.useNeighborBrightness[this.getBlockId(par2, par3, par4)]) {
            int var5 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3 + 1, par4);
            final int var6 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2 + 1, par3, par4);
            final int var7 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2 - 1, par3, par4);
            final int var8 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 + 1);
            final int var9 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 - 1);
            if (var6 > var5) {
                var5 = var6;
            }
            if (var7 > var5) {
                var5 = var7;
            }
            if (var8 > var5) {
                var5 = var8;
            }
            if (var9 > var5) {
                var5 = var9;
            }
            return var5;
        }
        int var5 = (par2 >> 4) - this.chunkX;
        final int var6 = (par4 >> 4) - this.chunkZ;
        return this.chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
    }
    
    public int getSpecialBlockBrightness(final EnumSkyBlock par1EnumSkyBlock, final int par2, int par3, final int par4) {
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            par3 = 255;
        }
        if (par3 >= 0 && par3 < 256 && par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 <= 30000000) {
            final int var5 = (par2 >> 4) - this.chunkX;
            final int var6 = (par4 >> 4) - this.chunkZ;
            return this.chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
        }
        return par1EnumSkyBlock.defaultLightValue;
    }
    
    @Override
    public int getHeight() {
        return 256;
    }
    
    @Override
    public int isBlockProvidingPowerTo(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = this.getBlockId(par1, par2, par3);
        return (var5 == 0) ? 0 : Block.blocksList[var5].isProvidingStrongPower(this, par1, par2, par3, par4);
    }
}
