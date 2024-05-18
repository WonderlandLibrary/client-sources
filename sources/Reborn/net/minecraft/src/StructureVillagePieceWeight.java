package net.minecraft.src;

public class StructureVillagePieceWeight
{
    public Class villagePieceClass;
    public final int villagePieceWeight;
    public int villagePiecesSpawned;
    public int villagePiecesLimit;
    
    public StructureVillagePieceWeight(final Class par1Class, final int par2, final int par3) {
        this.villagePieceClass = par1Class;
        this.villagePieceWeight = par2;
        this.villagePiecesLimit = par3;
    }
    
    public boolean canSpawnMoreVillagePiecesOfType(final int par1) {
        return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
    }
    
    public boolean canSpawnMoreVillagePieces() {
        return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
    }
}
