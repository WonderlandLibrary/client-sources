package net.minecraft.src;

import java.util.*;

public class StructureNetherBridgePieces
{
    private static final StructureNetherBridgePieceWeight[] primaryComponents;
    private static final StructureNetherBridgePieceWeight[] secondaryComponents;
    
    static {
        primaryComponents = new StructureNetherBridgePieceWeight[] { new StructureNetherBridgePieceWeight(ComponentNetherBridgeStraight.class, 30, 0, true), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCrossing3.class, 10, 4), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCrossing.class, 10, 4), new StructureNetherBridgePieceWeight(ComponentNetherBridgeStairs.class, 10, 3), new StructureNetherBridgePieceWeight(ComponentNetherBridgeThrone.class, 5, 2), new StructureNetherBridgePieceWeight(ComponentNetherBridgeEntrance.class, 5, 1) };
        secondaryComponents = new StructureNetherBridgePieceWeight[] { new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor5.class, 25, 0, true), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCrossing2.class, 15, 5), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor2.class, 5, 10), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor.class, 5, 10), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor3.class, 10, 3, true), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor4.class, 7, 2), new StructureNetherBridgePieceWeight(ComponentNetherBridgeNetherStalkRoom.class, 5, 2) };
    }
    
    private static ComponentNetherBridgePiece createNextComponentRandom(final StructureNetherBridgePieceWeight par0StructureNetherBridgePieceWeight, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final Class var8 = par0StructureNetherBridgePieceWeight.weightClass;
        Object var9 = null;
        if (var8 == ComponentNetherBridgeStraight.class) {
            var9 = ComponentNetherBridgeStraight.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCrossing3.class) {
            var9 = ComponentNetherBridgeCrossing3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCrossing.class) {
            var9 = ComponentNetherBridgeCrossing.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeStairs.class) {
            var9 = ComponentNetherBridgeStairs.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeThrone.class) {
            var9 = ComponentNetherBridgeThrone.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeEntrance.class) {
            var9 = ComponentNetherBridgeEntrance.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCorridor5.class) {
            var9 = ComponentNetherBridgeCorridor5.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCorridor2.class) {
            var9 = ComponentNetherBridgeCorridor2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCorridor.class) {
            var9 = ComponentNetherBridgeCorridor.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCorridor3.class) {
            var9 = ComponentNetherBridgeCorridor3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCorridor4.class) {
            var9 = ComponentNetherBridgeCorridor4.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeCrossing2.class) {
            var9 = ComponentNetherBridgeCrossing2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == ComponentNetherBridgeNetherStalkRoom.class) {
            var9 = ComponentNetherBridgeNetherStalkRoom.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        return (ComponentNetherBridgePiece)var9;
    }
    
    static ComponentNetherBridgePiece createNextComponent(final StructureNetherBridgePieceWeight par0StructureNetherBridgePieceWeight, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        return createNextComponentRandom(par0StructureNetherBridgePieceWeight, par1List, par2Random, par3, par4, par5, par6, par7);
    }
    
    static StructureNetherBridgePieceWeight[] getPrimaryComponents() {
        return StructureNetherBridgePieces.primaryComponents;
    }
    
    static StructureNetherBridgePieceWeight[] getSecondaryComponents() {
        return StructureNetherBridgePieces.secondaryComponents;
    }
}
