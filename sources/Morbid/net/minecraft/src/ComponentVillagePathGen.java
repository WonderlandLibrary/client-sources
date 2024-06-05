package net.minecraft.src;

import java.util.*;

public class ComponentVillagePathGen extends ComponentVillageRoadPiece
{
    private int averageGroundLevel;
    
    public ComponentVillagePathGen(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final int par2, final Random par3Random, final StructureBoundingBox par4StructureBoundingBox, final int par5) {
        super(par1ComponentVillageStartPiece, par2);
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
        this.averageGroundLevel = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        boolean var4 = false;
        for (int var5 = par3Random.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5)) {
            final StructureComponent var6 = this.getNextComponentNN((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, 0, var5);
            if (var6 != null) {
                var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                var4 = true;
            }
        }
        for (int var5 = par3Random.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5)) {
            final StructureComponent var6 = this.getNextComponentPP((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, 0, var5);
            if (var6 != null) {
                var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                var4 = true;
            }
        }
        if (var4 && par3Random.nextInt(3) > 0) {
            switch (this.coordBaseMode) {
                case 0: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                    break;
                }
                case 1: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    break;
                }
                case 2: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                    break;
                }
                case 3: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    break;
                }
            }
        }
        if (var4 && par3Random.nextInt(3) > 0) {
            switch (this.coordBaseMode) {
                case 0: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                    break;
                }
                case 1: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    break;
                }
                case 2: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                    break;
                }
                case 3: {
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    break;
                }
            }
        }
    }
    
    public static StructureBoundingBox func_74933_a(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6) {
        for (int var7 = 7 * MathHelper.getRandomIntegerInRange(par2Random, 3, 5); var7 >= 7; var7 -= 7) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 3, var7, par6);
            if (StructureComponent.findIntersecting(par1List, var8) == null) {
                return var8;
            }
        }
        return null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        final int var4 = this.getBiomeSpecificBlock(Block.gravel.blockID, 0);
        for (int var5 = this.boundingBox.minX; var5 <= this.boundingBox.maxX; ++var5) {
            for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; ++var6) {
                if (par3StructureBoundingBox.isVecInside(var5, 64, var6)) {
                    final int var7 = par1World.getTopSolidOrLiquidBlock(var5, var6) - 1;
                    par1World.setBlock(var5, var7, var6, var4, 0, 2);
                }
            }
        }
        return true;
    }
}
