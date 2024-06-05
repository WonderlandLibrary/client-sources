package net.minecraft.src;

class StructureStrongholdPieceWeight
{
    public Class pieceClass;
    public final int pieceWeight;
    public int instancesSpawned;
    public int instancesLimit;
    
    public StructureStrongholdPieceWeight(final Class par1Class, final int par2, final int par3) {
        this.pieceClass = par1Class;
        this.pieceWeight = par2;
        this.instancesLimit = par3;
    }
    
    public boolean canSpawnMoreStructuresOfType(final int par1) {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }
    
    public boolean canSpawnMoreStructures() {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }
}
