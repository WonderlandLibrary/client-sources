package net.minecraft.src;

import java.util.*;

public class StructureStrongholdPieces
{
    private static final StructureStrongholdPieceWeight[] pieceWeightArray;
    private static List structurePieceList;
    private static Class strongComponentType;
    static int totalWeight;
    private static final StructureStrongholdStones strongholdStones;
    
    static {
        pieceWeightArray = new StructureStrongholdPieceWeight[] { new StructureStrongholdPieceWeight(ComponentStrongholdStraight.class, 40, 0), new StructureStrongholdPieceWeight(ComponentStrongholdPrison.class, 5, 5), new StructureStrongholdPieceWeight(ComponentStrongholdLeftTurn.class, 20, 0), new StructureStrongholdPieceWeight(ComponentStrongholdRightTurn.class, 20, 0), new StructureStrongholdPieceWeight(ComponentStrongholdRoomCrossing.class, 10, 6), new StructureStrongholdPieceWeight(ComponentStrongholdStairsStraight.class, 5, 5), new StructureStrongholdPieceWeight(ComponentStrongholdStairs.class, 5, 5), new StructureStrongholdPieceWeight(ComponentStrongholdCrossing.class, 5, 4), new StructureStrongholdPieceWeight(ComponentStrongholdChestCorridor.class, 5, 4), new StructureStrongholdPieceWeight2(ComponentStrongholdLibrary.class, 10, 2), new StructureStrongholdPieceWeight3(ComponentStrongholdPortalRoom.class, 20, 1) };
        StructureStrongholdPieces.totalWeight = 0;
        strongholdStones = new StructureStrongholdStones(null);
    }
    
    public static void prepareStructurePieces() {
        StructureStrongholdPieces.structurePieceList = new ArrayList();
        for (final StructureStrongholdPieceWeight var4 : StructureStrongholdPieces.pieceWeightArray) {
            var4.instancesSpawned = 0;
            StructureStrongholdPieces.structurePieceList.add(var4);
        }
        StructureStrongholdPieces.strongComponentType = null;
    }
    
    private static boolean canAddStructurePieces() {
        boolean var0 = false;
        StructureStrongholdPieces.totalWeight = 0;
        for (final StructureStrongholdPieceWeight var3 : StructureStrongholdPieces.structurePieceList) {
            if (var3.instancesLimit > 0 && var3.instancesSpawned < var3.instancesLimit) {
                var0 = true;
            }
            StructureStrongholdPieces.totalWeight += var3.pieceWeight;
        }
        return var0;
    }
    
    private static ComponentStronghold getStrongholdComponentFromWeightedPiece(final Class par0Class, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        Object var8 = null;
        if (par0Class == ComponentStrongholdStraight.class) {
            var8 = ComponentStrongholdStraight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdPrison.class) {
            var8 = ComponentStrongholdPrison.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdLeftTurn.class) {
            var8 = ComponentStrongholdLeftTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdRightTurn.class) {
            var8 = ComponentStrongholdLeftTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdRoomCrossing.class) {
            var8 = ComponentStrongholdRoomCrossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdStairsStraight.class) {
            var8 = ComponentStrongholdStairsStraight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdStairs.class) {
            var8 = ComponentStrongholdStairs.getStrongholdStairsComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdCrossing.class) {
            var8 = ComponentStrongholdCrossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdChestCorridor.class) {
            var8 = ComponentStrongholdChestCorridor.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdLibrary.class) {
            var8 = ComponentStrongholdLibrary.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == ComponentStrongholdPortalRoom.class) {
            var8 = ComponentStrongholdPortalRoom.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        return (ComponentStronghold)var8;
    }
    
    private static ComponentStronghold getNextComponent(final ComponentStrongholdStairs2 par0ComponentStrongholdStairs2, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (!canAddStructurePieces()) {
            return null;
        }
        if (StructureStrongholdPieces.strongComponentType != null) {
            final ComponentStronghold var8 = getStrongholdComponentFromWeightedPiece(StructureStrongholdPieces.strongComponentType, par1List, par2Random, par3, par4, par5, par6, par7);
            StructureStrongholdPieces.strongComponentType = null;
            if (var8 != null) {
                return var8;
            }
        }
        int var9 = 0;
        while (var9 < 5) {
            ++var9;
            int var10 = par2Random.nextInt(StructureStrongholdPieces.totalWeight);
            for (final StructureStrongholdPieceWeight var12 : StructureStrongholdPieces.structurePieceList) {
                var10 -= var12.pieceWeight;
                if (var10 < 0) {
                    if (!var12.canSpawnMoreStructuresOfType(par7)) {
                        break;
                    }
                    if (var12 == par0ComponentStrongholdStairs2.strongholdPieceWeight) {
                        break;
                    }
                    final ComponentStronghold var13 = getStrongholdComponentFromWeightedPiece(var12.pieceClass, par1List, par2Random, par3, par4, par5, par6, par7);
                    if (var13 != null) {
                        final StructureStrongholdPieceWeight structureStrongholdPieceWeight = var12;
                        ++structureStrongholdPieceWeight.instancesSpawned;
                        par0ComponentStrongholdStairs2.strongholdPieceWeight = var12;
                        if (!var12.canSpawnMoreStructures()) {
                            StructureStrongholdPieces.structurePieceList.remove(var12);
                        }
                        return var13;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox var14 = ComponentStrongholdCorridor.func_74992_a(par1List, par2Random, par3, par4, par5, par6);
        if (var14 != null && var14.minY > 1) {
            return new ComponentStrongholdCorridor(par7, par2Random, var14, par6);
        }
        return null;
    }
    
    private static StructureComponent getNextValidComponent(final ComponentStrongholdStairs2 par0ComponentStrongholdStairs2, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par7 > 50) {
            return null;
        }
        if (Math.abs(par3 - par0ComponentStrongholdStairs2.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentStrongholdStairs2.getBoundingBox().minZ) <= 112) {
            final ComponentStronghold var8 = getNextComponent(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7 + 1);
            if (var8 != null) {
                par1List.add(var8);
                par0ComponentStrongholdStairs2.field_75026_c.add(var8);
            }
            return var8;
        }
        return null;
    }
    
    static StructureComponent getNextValidComponentAccess(final ComponentStrongholdStairs2 par0ComponentStrongholdStairs2, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        return getNextValidComponent(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7);
    }
    
    static Class setComponentType(final Class par0Class) {
        return StructureStrongholdPieces.strongComponentType = par0Class;
    }
    
    static StructureStrongholdStones getStrongholdStones() {
        return StructureStrongholdPieces.strongholdStones;
    }
}
