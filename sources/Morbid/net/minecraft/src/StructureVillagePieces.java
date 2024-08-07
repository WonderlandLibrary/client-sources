package net.minecraft.src;

import java.util.*;

public class StructureVillagePieces
{
    public static ArrayList getStructureVillageWeightedPieceList(final Random par0Random, final int par1) {
        final ArrayList var2 = new ArrayList();
        var2.add(new StructureVillagePieceWeight(ComponentVillageHouse4_Garden.class, 4, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageChurch.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 1 + par1)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageHouse1.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageWoodHut.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 5 + par1 * 3)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageHall.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageField.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 4 + par1)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageField2.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageHouse2.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0, 1 + par1)));
        var2.add(new StructureVillagePieceWeight(ComponentVillageHouse3.class, 8, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 3 + par1 * 2)));
        final Iterator var3 = var2.iterator();
        while (var3.hasNext()) {
            if (var3.next().villagePiecesLimit == 0) {
                var3.remove();
            }
        }
        return var2;
    }
    
    private static int func_75079_a(final List par0List) {
        boolean var1 = false;
        int var2 = 0;
        for (final StructureVillagePieceWeight var4 : par0List) {
            if (var4.villagePiecesLimit > 0 && var4.villagePiecesSpawned < var4.villagePiecesLimit) {
                var1 = true;
            }
            var2 += var4.villagePieceWeight;
        }
        return var1 ? var2 : -1;
    }
    
    private static ComponentVillage func_75083_a(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final StructureVillagePieceWeight par1StructureVillagePieceWeight, final List par2List, final Random par3Random, final int par4, final int par5, final int par6, final int par7, final int par8) {
        final Class var9 = par1StructureVillagePieceWeight.villagePieceClass;
        Object var10 = null;
        if (var9 == ComponentVillageHouse4_Garden.class) {
            var10 = ComponentVillageHouse4_Garden.func_74912_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageChurch.class) {
            var10 = ComponentVillageChurch.func_74919_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageHouse1.class) {
            var10 = ComponentVillageHouse1.func_74898_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageWoodHut.class) {
            var10 = ComponentVillageWoodHut.func_74908_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageHall.class) {
            var10 = ComponentVillageHall.func_74906_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageField.class) {
            var10 = ComponentVillageField.func_74900_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageField2.class) {
            var10 = ComponentVillageField2.func_74902_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageHouse2.class) {
            var10 = ComponentVillageHouse2.func_74915_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == ComponentVillageHouse3.class) {
            var10 = ComponentVillageHouse3.func_74921_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        return (ComponentVillage)var10;
    }
    
    private static ComponentVillage getNextVillageComponent(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final int var8 = func_75079_a(par0ComponentVillageStartPiece.structureVillageWeightedPieceList);
        if (var8 <= 0) {
            return null;
        }
        int var9 = 0;
        while (var9 < 5) {
            ++var9;
            int var10 = par2Random.nextInt(var8);
            for (final StructureVillagePieceWeight var12 : par0ComponentVillageStartPiece.structureVillageWeightedPieceList) {
                var10 -= var12.villagePieceWeight;
                if (var10 < 0) {
                    if (!var12.canSpawnMoreVillagePiecesOfType(par7)) {
                        break;
                    }
                    if (var12 == par0ComponentVillageStartPiece.structVillagePieceWeight && par0ComponentVillageStartPiece.structureVillageWeightedPieceList.size() > 1) {
                        break;
                    }
                    final ComponentVillage var13 = func_75083_a(par0ComponentVillageStartPiece, var12, par1List, par2Random, par3, par4, par5, par6, par7);
                    if (var13 != null) {
                        final StructureVillagePieceWeight structureVillagePieceWeight = var12;
                        ++structureVillagePieceWeight.villagePiecesSpawned;
                        par0ComponentVillageStartPiece.structVillagePieceWeight = var12;
                        if (!var12.canSpawnMoreVillagePieces()) {
                            par0ComponentVillageStartPiece.structureVillageWeightedPieceList.remove(var12);
                        }
                        return var13;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox var14 = ComponentVillageTorch.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);
        if (var14 != null) {
            return new ComponentVillageTorch(par0ComponentVillageStartPiece, par7, par2Random, var14, par6);
        }
        return null;
    }
    
    private static StructureComponent getNextVillageStructureComponent(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par7 > 50) {
            return null;
        }
        if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
            final ComponentVillage var8 = getNextVillageComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);
            if (var8 != null) {
                final int var9 = (var8.boundingBox.minX + var8.boundingBox.maxX) / 2;
                final int var10 = (var8.boundingBox.minZ + var8.boundingBox.maxZ) / 2;
                final int var11 = var8.boundingBox.maxX - var8.boundingBox.minX;
                final int var12 = var8.boundingBox.maxZ - var8.boundingBox.minZ;
                final int var13 = (var11 > var12) ? var11 : var12;
                if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(var9, var10, var13 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    par1List.add(var8);
                    par0ComponentVillageStartPiece.field_74932_i.add(var8);
                    return var8;
                }
            }
            return null;
        }
        return null;
    }
    
    private static StructureComponent getNextComponentVillagePath(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par7 > 3 + par0ComponentVillageStartPiece.terrainType) {
            return null;
        }
        if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
            final StructureBoundingBox var8 = ComponentVillagePathGen.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);
            if (var8 != null && var8.minY > 10) {
                final ComponentVillagePathGen var9 = new ComponentVillagePathGen(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
                final int var10 = (var9.boundingBox.minX + var9.boundingBox.maxX) / 2;
                final int var11 = (var9.boundingBox.minZ + var9.boundingBox.maxZ) / 2;
                final int var12 = var9.boundingBox.maxX - var9.boundingBox.minX;
                final int var13 = var9.boundingBox.maxZ - var9.boundingBox.minZ;
                final int var14 = (var12 > var13) ? var12 : var13;
                if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(var10, var11, var14 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    par1List.add(var9);
                    par0ComponentVillageStartPiece.field_74930_j.add(var9);
                    return var9;
                }
            }
            return null;
        }
        return null;
    }
    
    static StructureComponent getNextStructureComponent(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        return getNextVillageStructureComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }
    
    static StructureComponent getNextStructureComponentVillagePath(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        return getNextComponentVillagePath(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }
}
