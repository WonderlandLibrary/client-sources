package net.minecraft.block;

import net.minecraft.util.*;

public enum DirtType implements IStringSerializable
{
    DIRT("DIRT", 0, 0, "dirt", "default"), 
    COARSE_DIRT("COARSE_DIRT", 1, 1, "coarse_dirt", "coarse"), 
    PODZOL("PODZOL", 2, 2, "podzol");
    
    private static final DirtType[] METADATA_LOOKUP;
    private static final DirtType[] $VALUES;
    private final int metadata;
    private final String name;
    private final String unlocalizedName;
    
    private DirtType(final String p_i45727_1_, final int p_i45727_2_, final int metadata, final String name) {
        this(p_i45727_1_, p_i45727_2_, metadata, name, name);
    }
    
    private DirtType(final String p_i45728_1_, final int p_i45728_2_, final int metadata, final String name, final String unlocalizedName) {
        this.metadata = metadata;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
    }
    
    public static DirtType byMetadata(int metadata) {
        if (metadata < 0 || metadata >= DirtType.METADATA_LOOKUP.length) {
            metadata = 0;
        }
        return DirtType.METADATA_LOOKUP[metadata];
    }
    
    public int getMetadata() {
        return this.metadata;
    }
    
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    static {
        METADATA_LOOKUP = new DirtType[values().length];
        $VALUES = new DirtType[] { DirtType.DIRT, DirtType.COARSE_DIRT, DirtType.PODZOL };
        for (final DirtType var4 : values()) {
            DirtType.METADATA_LOOKUP[var4.getMetadata()] = var4;
        }
    }
}
