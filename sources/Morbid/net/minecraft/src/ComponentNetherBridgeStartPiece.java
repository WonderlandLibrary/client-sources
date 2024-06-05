package net.minecraft.src;

import java.util.*;

public class ComponentNetherBridgeStartPiece extends ComponentNetherBridgeCrossing3
{
    public StructureNetherBridgePieceWeight theNetherBridgePieceWeight;
    public List primaryWeights;
    public List secondaryWeights;
    public ArrayList field_74967_d;
    
    public ComponentNetherBridgeStartPiece(final Random par1Random, final int par2, final int par3) {
        super(par1Random, par2, par3);
        this.primaryWeights = new ArrayList();
        this.field_74967_d = new ArrayList();
        for (final StructureNetherBridgePieceWeight var7 : StructureNetherBridgePieces.getPrimaryComponents()) {
            var7.field_78827_c = 0;
            this.primaryWeights.add(var7);
        }
        this.secondaryWeights = new ArrayList();
        for (final StructureNetherBridgePieceWeight var7 : StructureNetherBridgePieces.getSecondaryComponents()) {
            var7.field_78827_c = 0;
            this.secondaryWeights.add(var7);
        }
    }
}
