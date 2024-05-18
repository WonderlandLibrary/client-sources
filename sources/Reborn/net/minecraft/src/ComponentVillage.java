package net.minecraft.src;

import java.util.*;

abstract class ComponentVillage extends StructureComponent
{
    private int villagersSpawned;
    protected ComponentVillageStartPiece startPiece;
    
    protected ComponentVillage(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final int par2) {
        super(par2);
        this.startPiece = par1ComponentVillageStartPiece;
    }
    
    protected StructureComponent getNextComponentNN(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final List par2List, final Random par3Random, final int par4, final int par5) {
        switch (this.coordBaseMode) {
            case 0: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
            }
            case 1: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
            }
            case 2: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
            }
            case 3: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructureComponent getNextComponentPP(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final List par2List, final Random par3Random, final int par4, final int par5) {
        switch (this.coordBaseMode) {
            case 0: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
            }
            case 1: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }
            case 2: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
            }
            case 3: {
                return StructureVillagePieces.getNextStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }
            default: {
                return null;
            }
        }
    }
    
    protected int getAverageGroundLevel(final World par1World, final StructureBoundingBox par2StructureBoundingBox) {
        int var3 = 0;
        int var4 = 0;
        for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5) {
            for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6) {
                if (par2StructureBoundingBox.isVecInside(var6, 64, var5)) {
                    var3 += Math.max(par1World.getTopSolidOrLiquidBlock(var6, var5), par1World.provider.getAverageGroundLevel());
                    ++var4;
                }
            }
        }
        if (var4 == 0) {
            return -1;
        }
        return var3 / var4;
    }
    
    protected static boolean canVillageGoDeeper(final StructureBoundingBox par0StructureBoundingBox) {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
    }
    
    protected void spawnVillagers(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6) {
        if (this.villagersSpawned < par6) {
            for (int var7 = this.villagersSpawned; var7 < par6; ++var7) {
                final int var8 = this.getXWithOffset(par3 + var7, par5);
                final int var9 = this.getYWithOffset(par4);
                final int var10 = this.getZWithOffset(par3 + var7, par5);
                if (!par2StructureBoundingBox.isVecInside(var8, var9, var10)) {
                    break;
                }
                ++this.villagersSpawned;
                final EntityVillager var11 = new EntityVillager(par1World, this.getVillagerType(var7));
                var11.setLocationAndAngles(var8 + 0.5, var9, var10 + 0.5, 0.0f, 0.0f);
                par1World.spawnEntityInWorld(var11);
            }
        }
    }
    
    protected int getVillagerType(final int par1) {
        return 0;
    }
    
    protected int getBiomeSpecificBlock(final int par1, final int par2) {
        if (this.startPiece.inDesert) {
            if (par1 == Block.wood.blockID) {
                return Block.sandStone.blockID;
            }
            if (par1 == Block.cobblestone.blockID) {
                return Block.sandStone.blockID;
            }
            if (par1 == Block.planks.blockID) {
                return Block.sandStone.blockID;
            }
            if (par1 == Block.stairsWoodOak.blockID) {
                return Block.stairsSandStone.blockID;
            }
            if (par1 == Block.stairsCobblestone.blockID) {
                return Block.stairsSandStone.blockID;
            }
            if (par1 == Block.gravel.blockID) {
                return Block.sandStone.blockID;
            }
        }
        return par1;
    }
    
    protected int getBiomeSpecificBlockMetadata(final int par1, final int par2) {
        if (this.startPiece.inDesert) {
            if (par1 == Block.wood.blockID) {
                return 0;
            }
            if (par1 == Block.cobblestone.blockID) {
                return 0;
            }
            if (par1 == Block.planks.blockID) {
                return 2;
            }
        }
        return par2;
    }
    
    @Override
    protected void placeBlockAtCurrentPosition(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final StructureBoundingBox par7StructureBoundingBox) {
        final int var8 = this.getBiomeSpecificBlock(par2, par3);
        final int var9 = this.getBiomeSpecificBlockMetadata(par2, par3);
        super.placeBlockAtCurrentPosition(par1World, var8, var9, par4, par5, par6, par7StructureBoundingBox);
    }
    
    @Override
    protected void fillWithBlocks(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final int par9, final int par10, final boolean par11) {
        final int var12 = this.getBiomeSpecificBlock(par9, 0);
        final int var13 = this.getBiomeSpecificBlockMetadata(par9, 0);
        final int var14 = this.getBiomeSpecificBlock(par10, 0);
        final int var15 = this.getBiomeSpecificBlockMetadata(par10, 0);
        super.fillWithMetadataBlocks(par1World, par2StructureBoundingBox, par3, par4, par5, par6, par7, par8, var12, var13, var14, var15, par11);
    }
    
    @Override
    protected void fillCurrentPositionBlocksDownwards(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final StructureBoundingBox par7StructureBoundingBox) {
        final int var8 = this.getBiomeSpecificBlock(par2, par3);
        final int var9 = this.getBiomeSpecificBlockMetadata(par2, par3);
        super.fillCurrentPositionBlocksDownwards(par1World, var8, var9, par4, par5, par6, par7StructureBoundingBox);
    }
}
