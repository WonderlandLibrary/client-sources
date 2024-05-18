package net.minecraft.src;

import java.util.*;

public abstract class StructureComponent
{
    protected StructureBoundingBox boundingBox;
    protected int coordBaseMode;
    protected int componentType;
    
    protected StructureComponent(final int par1) {
        this.componentType = par1;
        this.coordBaseMode = -1;
    }
    
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
    }
    
    public abstract boolean addComponentParts(final World p0, final Random p1, final StructureBoundingBox p2);
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    public int getComponentType() {
        return this.componentType;
    }
    
    public static StructureComponent findIntersecting(final List par0List, final StructureBoundingBox par1StructureBoundingBox) {
        for (final StructureComponent var3 : par0List) {
            if (var3.getBoundingBox() != null && var3.getBoundingBox().intersectsWith(par1StructureBoundingBox)) {
                return var3;
            }
        }
        return null;
    }
    
    public ChunkPosition getCenter() {
        return new ChunkPosition(this.boundingBox.getCenterX(), this.boundingBox.getCenterY(), this.boundingBox.getCenterZ());
    }
    
    protected boolean isLiquidInStructureBoundingBox(final World par1World, final StructureBoundingBox par2StructureBoundingBox) {
        final int var3 = Math.max(this.boundingBox.minX - 1, par2StructureBoundingBox.minX);
        final int var4 = Math.max(this.boundingBox.minY - 1, par2StructureBoundingBox.minY);
        final int var5 = Math.max(this.boundingBox.minZ - 1, par2StructureBoundingBox.minZ);
        final int var6 = Math.min(this.boundingBox.maxX + 1, par2StructureBoundingBox.maxX);
        final int var7 = Math.min(this.boundingBox.maxY + 1, par2StructureBoundingBox.maxY);
        final int var8 = Math.min(this.boundingBox.maxZ + 1, par2StructureBoundingBox.maxZ);
        for (int var9 = var3; var9 <= var6; ++var9) {
            for (int var10 = var5; var10 <= var8; ++var10) {
                int var11 = par1World.getBlockId(var9, var4, var10);
                if (var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) {
                    return true;
                }
                var11 = par1World.getBlockId(var9, var7, var10);
                if (var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) {
                    return true;
                }
            }
        }
        for (int var9 = var3; var9 <= var6; ++var9) {
            for (int var10 = var4; var10 <= var7; ++var10) {
                int var11 = par1World.getBlockId(var9, var10, var5);
                if (var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) {
                    return true;
                }
                var11 = par1World.getBlockId(var9, var10, var8);
                if (var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) {
                    return true;
                }
            }
        }
        for (int var9 = var5; var9 <= var8; ++var9) {
            for (int var10 = var4; var10 <= var7; ++var10) {
                int var11 = par1World.getBlockId(var3, var10, var9);
                if (var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) {
                    return true;
                }
                var11 = par1World.getBlockId(var6, var10, var9);
                if (var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected int getXWithOffset(final int par1, final int par2) {
        switch (this.coordBaseMode) {
            case 0:
            case 2: {
                return this.boundingBox.minX + par1;
            }
            case 1: {
                return this.boundingBox.maxX - par2;
            }
            case 3: {
                return this.boundingBox.minX + par2;
            }
            default: {
                return par1;
            }
        }
    }
    
    protected int getYWithOffset(final int par1) {
        return (this.coordBaseMode == -1) ? par1 : (par1 + this.boundingBox.minY);
    }
    
    protected int getZWithOffset(final int par1, final int par2) {
        switch (this.coordBaseMode) {
            case 0: {
                return this.boundingBox.minZ + par2;
            }
            case 1:
            case 3: {
                return this.boundingBox.minZ + par1;
            }
            case 2: {
                return this.boundingBox.maxZ - par2;
            }
            default: {
                return par2;
            }
        }
    }
    
    protected int getMetadataWithOffset(final int par1, final int par2) {
        if (par1 == Block.rail.blockID) {
            if (this.coordBaseMode == 1 || this.coordBaseMode == 3) {
                if (par2 == 1) {
                    return 0;
                }
                return 1;
            }
        }
        else if (par1 != Block.doorWood.blockID && par1 != Block.doorIron.blockID) {
            if (par1 != Block.stairsCobblestone.blockID && par1 != Block.stairsWoodOak.blockID && par1 != Block.stairsNetherBrick.blockID && par1 != Block.stairsStoneBrick.blockID && par1 != Block.stairsSandStone.blockID) {
                if (par1 == Block.ladder.blockID) {
                    if (this.coordBaseMode == 0) {
                        if (par2 == 2) {
                            return 3;
                        }
                        if (par2 == 3) {
                            return 2;
                        }
                    }
                    else if (this.coordBaseMode == 1) {
                        if (par2 == 2) {
                            return 4;
                        }
                        if (par2 == 3) {
                            return 5;
                        }
                        if (par2 == 4) {
                            return 2;
                        }
                        if (par2 == 5) {
                            return 3;
                        }
                    }
                    else if (this.coordBaseMode == 3) {
                        if (par2 == 2) {
                            return 5;
                        }
                        if (par2 == 3) {
                            return 4;
                        }
                        if (par2 == 4) {
                            return 2;
                        }
                        if (par2 == 5) {
                            return 3;
                        }
                    }
                }
                else if (par1 == Block.stoneButton.blockID) {
                    if (this.coordBaseMode == 0) {
                        if (par2 == 3) {
                            return 4;
                        }
                        if (par2 == 4) {
                            return 3;
                        }
                    }
                    else if (this.coordBaseMode == 1) {
                        if (par2 == 3) {
                            return 1;
                        }
                        if (par2 == 4) {
                            return 2;
                        }
                        if (par2 == 2) {
                            return 3;
                        }
                        if (par2 == 1) {
                            return 4;
                        }
                    }
                    else if (this.coordBaseMode == 3) {
                        if (par2 == 3) {
                            return 2;
                        }
                        if (par2 == 4) {
                            return 1;
                        }
                        if (par2 == 2) {
                            return 3;
                        }
                        if (par2 == 1) {
                            return 4;
                        }
                    }
                }
                else if (par1 != Block.tripWireSource.blockID && (Block.blocksList[par1] == null || !(Block.blocksList[par1] instanceof BlockDirectional))) {
                    if (par1 == Block.pistonBase.blockID || par1 == Block.pistonStickyBase.blockID || par1 == Block.lever.blockID || par1 == Block.dispenser.blockID) {
                        if (this.coordBaseMode == 0) {
                            if (par2 == 2 || par2 == 3) {
                                return Facing.oppositeSide[par2];
                            }
                        }
                        else if (this.coordBaseMode == 1) {
                            if (par2 == 2) {
                                return 4;
                            }
                            if (par2 == 3) {
                                return 5;
                            }
                            if (par2 == 4) {
                                return 2;
                            }
                            if (par2 == 5) {
                                return 3;
                            }
                        }
                        else if (this.coordBaseMode == 3) {
                            if (par2 == 2) {
                                return 5;
                            }
                            if (par2 == 3) {
                                return 4;
                            }
                            if (par2 == 4) {
                                return 2;
                            }
                            if (par2 == 5) {
                                return 3;
                            }
                        }
                    }
                }
                else if (this.coordBaseMode == 0) {
                    if (par2 == 0 || par2 == 2) {
                        return Direction.rotateOpposite[par2];
                    }
                }
                else if (this.coordBaseMode == 1) {
                    if (par2 == 2) {
                        return 1;
                    }
                    if (par2 == 0) {
                        return 3;
                    }
                    if (par2 == 1) {
                        return 2;
                    }
                    if (par2 == 3) {
                        return 0;
                    }
                }
                else if (this.coordBaseMode == 3) {
                    if (par2 == 2) {
                        return 3;
                    }
                    if (par2 == 0) {
                        return 1;
                    }
                    if (par2 == 1) {
                        return 2;
                    }
                    if (par2 == 3) {
                        return 0;
                    }
                }
            }
            else if (this.coordBaseMode == 0) {
                if (par2 == 2) {
                    return 3;
                }
                if (par2 == 3) {
                    return 2;
                }
            }
            else if (this.coordBaseMode == 1) {
                if (par2 == 0) {
                    return 2;
                }
                if (par2 == 1) {
                    return 3;
                }
                if (par2 == 2) {
                    return 0;
                }
                if (par2 == 3) {
                    return 1;
                }
            }
            else if (this.coordBaseMode == 3) {
                if (par2 == 0) {
                    return 2;
                }
                if (par2 == 1) {
                    return 3;
                }
                if (par2 == 2) {
                    return 1;
                }
                if (par2 == 3) {
                    return 0;
                }
            }
        }
        else if (this.coordBaseMode == 0) {
            if (par2 == 0) {
                return 2;
            }
            if (par2 == 2) {
                return 0;
            }
        }
        else {
            if (this.coordBaseMode == 1) {
                return par2 + 1 & 0x3;
            }
            if (this.coordBaseMode == 3) {
                return par2 + 3 & 0x3;
            }
        }
        return par2;
    }
    
    protected void placeBlockAtCurrentPosition(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final StructureBoundingBox par7StructureBoundingBox) {
        final int var8 = this.getXWithOffset(par4, par6);
        final int var9 = this.getYWithOffset(par5);
        final int var10 = this.getZWithOffset(par4, par6);
        if (par7StructureBoundingBox.isVecInside(var8, var9, var10)) {
            par1World.setBlock(var8, var9, var10, par2, par3, 2);
        }
    }
    
    protected int getBlockIdAtCurrentPosition(final World par1World, final int par2, final int par3, final int par4, final StructureBoundingBox par5StructureBoundingBox) {
        final int var6 = this.getXWithOffset(par2, par4);
        final int var7 = this.getYWithOffset(par3);
        final int var8 = this.getZWithOffset(par2, par4);
        return par5StructureBoundingBox.isVecInside(var6, var7, var8) ? par1World.getBlockId(var6, var7, var8) : 0;
    }
    
    protected void fillWithAir(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8) {
        for (int var9 = par4; var9 <= par7; ++var9) {
            for (int var10 = par3; var10 <= par6; ++var10) {
                for (int var11 = par5; var11 <= par8; ++var11) {
                    this.placeBlockAtCurrentPosition(par1World, 0, 0, var10, var9, var11, par2StructureBoundingBox);
                }
            }
        }
    }
    
    protected void fillWithBlocks(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final int par9, final int par10, final boolean par11) {
        for (int var12 = par4; var12 <= par7; ++var12) {
            for (int var13 = par3; var13 <= par6; ++var13) {
                for (int var14 = par5; var14 <= par8; ++var14) {
                    if (!par11 || this.getBlockIdAtCurrentPosition(par1World, var13, var12, var14, par2StructureBoundingBox) != 0) {
                        if (var12 != par4 && var12 != par7 && var13 != par3 && var13 != par6 && var14 != par5 && var14 != par8) {
                            this.placeBlockAtCurrentPosition(par1World, par10, 0, var13, var12, var14, par2StructureBoundingBox);
                        }
                        else {
                            this.placeBlockAtCurrentPosition(par1World, par9, 0, var13, var12, var14, par2StructureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void fillWithMetadataBlocks(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final int par9, final int par10, final int par11, final int par12, final boolean par13) {
        for (int var14 = par4; var14 <= par7; ++var14) {
            for (int var15 = par3; var15 <= par6; ++var15) {
                for (int var16 = par5; var16 <= par8; ++var16) {
                    if (!par13 || this.getBlockIdAtCurrentPosition(par1World, var15, var14, var16, par2StructureBoundingBox) != 0) {
                        if (var14 != par4 && var14 != par7 && var15 != par3 && var15 != par6 && var16 != par5 && var16 != par8) {
                            this.placeBlockAtCurrentPosition(par1World, par11, par12, var15, var14, var16, par2StructureBoundingBox);
                        }
                        else {
                            this.placeBlockAtCurrentPosition(par1World, par9, par10, var15, var14, var16, par2StructureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void fillWithRandomizedBlocks(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final boolean par9, final Random par10Random, final StructurePieceBlockSelector par11StructurePieceBlockSelector) {
        for (int var12 = par4; var12 <= par7; ++var12) {
            for (int var13 = par3; var13 <= par6; ++var13) {
                for (int var14 = par5; var14 <= par8; ++var14) {
                    if (!par9 || this.getBlockIdAtCurrentPosition(par1World, var13, var12, var14, par2StructureBoundingBox) != 0) {
                        par11StructurePieceBlockSelector.selectBlocks(par10Random, var13, var12, var14, var12 == par4 || var12 == par7 || var13 == par3 || var13 == par6 || var14 == par5 || var14 == par8);
                        this.placeBlockAtCurrentPosition(par1World, par11StructurePieceBlockSelector.getSelectedBlockId(), par11StructurePieceBlockSelector.getSelectedBlockMetaData(), var13, var12, var14, par2StructureBoundingBox);
                    }
                }
            }
        }
    }
    
    protected void randomlyFillWithBlocks(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final Random par3Random, final float par4, final int par5, final int par6, final int par7, final int par8, final int par9, final int par10, final int par11, final int par12, final boolean par13) {
        for (int var14 = par6; var14 <= par9; ++var14) {
            for (int var15 = par5; var15 <= par8; ++var15) {
                for (int var16 = par7; var16 <= par10; ++var16) {
                    if (par3Random.nextFloat() <= par4 && (!par13 || this.getBlockIdAtCurrentPosition(par1World, var15, var14, var16, par2StructureBoundingBox) != 0)) {
                        if (var14 != par6 && var14 != par9 && var15 != par5 && var15 != par8 && var16 != par7 && var16 != par10) {
                            this.placeBlockAtCurrentPosition(par1World, par12, 0, var15, var14, var16, par2StructureBoundingBox);
                        }
                        else {
                            this.placeBlockAtCurrentPosition(par1World, par11, 0, var15, var14, var16, par2StructureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void randomlyPlaceBlock(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final Random par3Random, final float par4, final int par5, final int par6, final int par7, final int par8, final int par9) {
        if (par3Random.nextFloat() < par4) {
            this.placeBlockAtCurrentPosition(par1World, par8, par9, par5, par6, par7, par2StructureBoundingBox);
        }
    }
    
    protected void randomlyRareFillWithBlocks(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final int par9, final boolean par10) {
        final float var11 = par6 - par3 + 1;
        final float var12 = par7 - par4 + 1;
        final float var13 = par8 - par5 + 1;
        final float var14 = par3 + var11 / 2.0f;
        final float var15 = par5 + var13 / 2.0f;
        for (int var16 = par4; var16 <= par7; ++var16) {
            final float var17 = (var16 - par4) / var12;
            for (int var18 = par3; var18 <= par6; ++var18) {
                final float var19 = (var18 - var14) / (var11 * 0.5f);
                for (int var20 = par5; var20 <= par8; ++var20) {
                    final float var21 = (var20 - var15) / (var13 * 0.5f);
                    if (!par10 || this.getBlockIdAtCurrentPosition(par1World, var18, var16, var20, par2StructureBoundingBox) != 0) {
                        final float var22 = var19 * var19 + var17 * var17 + var21 * var21;
                        if (var22 <= 1.05f) {
                            this.placeBlockAtCurrentPosition(par1World, par9, 0, var18, var16, var20, par2StructureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void clearCurrentPositionBlocksUpwards(final World par1World, final int par2, final int par3, final int par4, final StructureBoundingBox par5StructureBoundingBox) {
        final int var6 = this.getXWithOffset(par2, par4);
        int var7 = this.getYWithOffset(par3);
        final int var8 = this.getZWithOffset(par2, par4);
        if (par5StructureBoundingBox.isVecInside(var6, var7, var8)) {
            while (!par1World.isAirBlock(var6, var7, var8) && var7 < 255) {
                par1World.setBlock(var6, var7, var8, 0, 0, 2);
                ++var7;
            }
        }
    }
    
    protected void fillCurrentPositionBlocksDownwards(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final StructureBoundingBox par7StructureBoundingBox) {
        final int var8 = this.getXWithOffset(par4, par6);
        int var9 = this.getYWithOffset(par5);
        final int var10 = this.getZWithOffset(par4, par6);
        if (par7StructureBoundingBox.isVecInside(var8, var9, var10)) {
            while ((par1World.isAirBlock(var8, var9, var10) || par1World.getBlockMaterial(var8, var9, var10).isLiquid()) && var9 > 1) {
                par1World.setBlock(var8, var9, var10, par2, par3, 2);
                --var9;
            }
        }
    }
    
    protected boolean generateStructureChestContents(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final Random par3Random, final int par4, final int par5, final int par6, final WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, final int par8) {
        final int var9 = this.getXWithOffset(par4, par6);
        final int var10 = this.getYWithOffset(par5);
        final int var11 = this.getZWithOffset(par4, par6);
        if (par2StructureBoundingBox.isVecInside(var9, var10, var11) && par1World.getBlockId(var9, var10, var11) != Block.chest.blockID) {
            par1World.setBlock(var9, var10, var11, Block.chest.blockID, 0, 2);
            final TileEntityChest var12 = (TileEntityChest)par1World.getBlockTileEntity(var9, var10, var11);
            if (var12 != null) {
                WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var12, par8);
            }
            return true;
        }
        return false;
    }
    
    protected boolean generateStructureDispenserContents(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final Random par3Random, final int par4, final int par5, final int par6, final int par7, final WeightedRandomChestContent[] par8ArrayOfWeightedRandomChestContent, final int par9) {
        final int var10 = this.getXWithOffset(par4, par6);
        final int var11 = this.getYWithOffset(par5);
        final int var12 = this.getZWithOffset(par4, par6);
        if (par2StructureBoundingBox.isVecInside(var10, var11, var12) && par1World.getBlockId(var10, var11, var12) != Block.dispenser.blockID) {
            par1World.setBlock(var10, var11, var12, Block.dispenser.blockID, this.getMetadataWithOffset(Block.dispenser.blockID, par7), 2);
            final TileEntityDispenser var13 = (TileEntityDispenser)par1World.getBlockTileEntity(var10, var11, var12);
            if (var13 != null) {
                WeightedRandomChestContent.generateDispenserContents(par3Random, par8ArrayOfWeightedRandomChestContent, var13, par9);
            }
            return true;
        }
        return false;
    }
    
    protected void placeDoorAtCurrentPosition(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final Random par3Random, final int par4, final int par5, final int par6, final int par7) {
        final int var8 = this.getXWithOffset(par4, par6);
        final int var9 = this.getYWithOffset(par5);
        final int var10 = this.getZWithOffset(par4, par6);
        if (par2StructureBoundingBox.isVecInside(var8, var9, var10)) {
            ItemDoor.placeDoorBlock(par1World, var8, var9, var10, par7, Block.doorWood);
        }
    }
}
