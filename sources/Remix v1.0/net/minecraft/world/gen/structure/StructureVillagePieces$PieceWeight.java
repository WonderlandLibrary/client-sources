package net.minecraft.world.gen.structure;

public static class PieceWeight
{
    public final int villagePieceWeight;
    public Class villagePieceClass;
    public int villagePiecesSpawned;
    public int villagePiecesLimit;
    
    public PieceWeight(final Class p_i2098_1_, final int p_i2098_2_, final int p_i2098_3_) {
        this.villagePieceClass = p_i2098_1_;
        this.villagePieceWeight = p_i2098_2_;
        this.villagePiecesLimit = p_i2098_3_;
    }
    
    public boolean canSpawnMoreVillagePiecesOfType(final int p_75085_1_) {
        return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
    }
    
    public boolean canSpawnMoreVillagePieces() {
        return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
    }
}
