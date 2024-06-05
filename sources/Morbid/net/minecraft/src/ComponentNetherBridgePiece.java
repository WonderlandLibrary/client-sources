package net.minecraft.src;

import java.util.*;

abstract class ComponentNetherBridgePiece extends StructureComponent
{
    protected ComponentNetherBridgePiece(final int par1) {
        super(par1);
    }
    
    private int getTotalWeight(final List par1List) {
        boolean var2 = false;
        int var3 = 0;
        for (final StructureNetherBridgePieceWeight var5 : par1List) {
            if (var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d) {
                var2 = true;
            }
            var3 += var5.field_78826_b;
        }
        return var2 ? var3 : -1;
    }
    
    private ComponentNetherBridgePiece getNextComponent(final ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, final List par2List, final List par3List, final Random par4Random, final int par5, final int par6, final int par7, final int par8, final int par9) {
        final int var10 = this.getTotalWeight(par2List);
        final boolean var11 = var10 > 0 && par9 <= 30;
        int var12 = 0;
        while (var12 < 5 && var11) {
            ++var12;
            int var13 = par4Random.nextInt(var10);
            for (final StructureNetherBridgePieceWeight var15 : par2List) {
                var13 -= var15.field_78826_b;
                if (var13 < 0) {
                    if (!var15.func_78822_a(par9)) {
                        break;
                    }
                    if (var15 == par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight && !var15.field_78825_e) {
                        break;
                    }
                    final ComponentNetherBridgePiece var16 = StructureNetherBridgePieces.createNextComponent(var15, par3List, par4Random, par5, par6, par7, par8, par9);
                    if (var16 != null) {
                        final StructureNetherBridgePieceWeight structureNetherBridgePieceWeight = var15;
                        ++structureNetherBridgePieceWeight.field_78827_c;
                        par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight = var15;
                        if (!var15.func_78823_a()) {
                            par2List.remove(var15);
                        }
                        return var16;
                    }
                    continue;
                }
            }
        }
        return ComponentNetherBridgeEnd.func_74971_a(par3List, par4Random, par5, par6, par7, par8, par9);
    }
    
    private StructureComponent getNextComponent(final ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, final List par2List, final Random par3Random, final int par4, final int par5, final int par6, final int par7, final int par8, final boolean par9) {
        if (Math.abs(par4 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par6 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minZ) <= 112) {
            List var10 = par1ComponentNetherBridgeStartPiece.primaryWeights;
            if (par9) {
                var10 = par1ComponentNetherBridgeStartPiece.secondaryWeights;
            }
            final ComponentNetherBridgePiece var11 = this.getNextComponent(par1ComponentNetherBridgeStartPiece, var10, par2List, par3Random, par4, par5, par6, par7, par8 + 1);
            if (var11 != null) {
                par2List.add(var11);
                par1ComponentNetherBridgeStartPiece.field_74967_d.add(var11);
            }
            return var11;
        }
        return ComponentNetherBridgeEnd.func_74971_a(par2List, par3Random, par4, par5, par6, par7, par8);
    }
    
    protected StructureComponent getNextComponentNormal(final ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, final List par2List, final Random par3Random, final int par4, final int par5, final boolean par6) {
        switch (this.coordBaseMode) {
            case 0: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType(), par6);
            }
            case 1: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType(), par6);
            }
            case 2: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType(), par6);
            }
            case 3: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType(), par6);
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructureComponent getNextComponentX(final ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, final List par2List, final Random par3Random, final int par4, final int par5, final boolean par6) {
        switch (this.coordBaseMode) {
            case 0: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType(), par6);
            }
            case 1: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType(), par6);
            }
            case 2: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType(), par6);
            }
            case 3: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType(), par6);
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructureComponent getNextComponentZ(final ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, final List par2List, final Random par3Random, final int par4, final int par5, final boolean par6) {
        switch (this.coordBaseMode) {
            case 0: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType(), par6);
            }
            case 1: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType(), par6);
            }
            case 2: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType(), par6);
            }
            case 3: {
                return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType(), par6);
            }
            default: {
                return null;
            }
        }
    }
    
    protected static boolean isAboveGround(final StructureBoundingBox par0StructureBoundingBox) {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
    }
}
