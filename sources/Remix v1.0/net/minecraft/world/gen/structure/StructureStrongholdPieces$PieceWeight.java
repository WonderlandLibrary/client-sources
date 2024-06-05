package net.minecraft.world.gen.structure;

static class PieceWeight
{
    public final int pieceWeight;
    public Class pieceClass;
    public int instancesSpawned;
    public int instancesLimit;
    
    public PieceWeight(final Class p_i2076_1_, final int p_i2076_2_, final int p_i2076_3_) {
        this.pieceClass = p_i2076_1_;
        this.pieceWeight = p_i2076_2_;
        this.instancesLimit = p_i2076_3_;
    }
    
    public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }
    
    public boolean canSpawnMoreStructures() {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }
}
