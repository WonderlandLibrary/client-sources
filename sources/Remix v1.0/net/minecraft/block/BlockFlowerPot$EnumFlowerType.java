package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumFlowerType implements IStringSerializable
{
    EMPTY("EMPTY", 0, "empty"), 
    POPPY("POPPY", 1, "rose"), 
    BLUE_ORCHID("BLUE_ORCHID", 2, "blue_orchid"), 
    ALLIUM("ALLIUM", 3, "allium"), 
    HOUSTONIA("HOUSTONIA", 4, "houstonia"), 
    RED_TULIP("RED_TULIP", 5, "red_tulip"), 
    ORANGE_TULIP("ORANGE_TULIP", 6, "orange_tulip"), 
    WHITE_TULIP("WHITE_TULIP", 7, "white_tulip"), 
    PINK_TULIP("PINK_TULIP", 8, "pink_tulip"), 
    OXEYE_DAISY("OXEYE_DAISY", 9, "oxeye_daisy"), 
    DANDELION("DANDELION", 10, "dandelion"), 
    OAK_SAPLING("OAK_SAPLING", 11, "oak_sapling"), 
    SPRUCE_SAPLING("SPRUCE_SAPLING", 12, "spruce_sapling"), 
    BIRCH_SAPLING("BIRCH_SAPLING", 13, "birch_sapling"), 
    JUNGLE_SAPLING("JUNGLE_SAPLING", 14, "jungle_sapling"), 
    ACACIA_SAPLING("ACACIA_SAPLING", 15, "acacia_sapling"), 
    DARK_OAK_SAPLING("DARK_OAK_SAPLING", 16, "dark_oak_sapling"), 
    MUSHROOM_RED("MUSHROOM_RED", 17, "mushroom_red"), 
    MUSHROOM_BROWN("MUSHROOM_BROWN", 18, "mushroom_brown"), 
    DEAD_BUSH("DEAD_BUSH", 19, "dead_bush"), 
    FERN("FERN", 20, "fern"), 
    CACTUS("CACTUS", 21, "cactus");
    
    private static final EnumFlowerType[] $VALUES;
    private final String field_177006_w;
    
    private EnumFlowerType(final String p_i45715_1_, final int p_i45715_2_, final String p_i45715_3_) {
        this.field_177006_w = p_i45715_3_;
    }
    
    @Override
    public String toString() {
        return this.field_177006_w;
    }
    
    @Override
    public String getName() {
        return this.field_177006_w;
    }
    
    static {
        $VALUES = new EnumFlowerType[] { EnumFlowerType.EMPTY, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY, EnumFlowerType.DANDELION, EnumFlowerType.OAK_SAPLING, EnumFlowerType.SPRUCE_SAPLING, EnumFlowerType.BIRCH_SAPLING, EnumFlowerType.JUNGLE_SAPLING, EnumFlowerType.ACACIA_SAPLING, EnumFlowerType.DARK_OAK_SAPLING, EnumFlowerType.MUSHROOM_RED, EnumFlowerType.MUSHROOM_BROWN, EnumFlowerType.DEAD_BUSH, EnumFlowerType.FERN, EnumFlowerType.CACTUS };
    }
}
