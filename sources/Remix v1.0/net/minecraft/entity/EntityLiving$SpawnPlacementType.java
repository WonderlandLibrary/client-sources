package net.minecraft.entity;

public enum SpawnPlacementType
{
    ON_GROUND("ON_GROUND", 0, "ON_GROUND", 0), 
    IN_AIR("IN_AIR", 1, "IN_AIR", 1), 
    IN_WATER("IN_WATER", 2, "IN_WATER", 2);
    
    private static final SpawnPlacementType[] $VALUES;
    
    private SpawnPlacementType(final String p_i46393_1_, final int p_i46393_2_, final String p_i45893_1_, final int p_i45893_2_) {
    }
    
    static {
        $VALUES = new SpawnPlacementType[] { SpawnPlacementType.ON_GROUND, SpawnPlacementType.IN_AIR, SpawnPlacementType.IN_WATER };
    }
}
