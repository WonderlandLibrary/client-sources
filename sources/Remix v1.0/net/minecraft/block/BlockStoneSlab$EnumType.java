package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    STONE("STONE", 0, 0, "stone"), 
    SAND("SAND", 1, 1, "sandstone", "sand"), 
    WOOD("WOOD", 2, 2, "wood_old", "wood"), 
    COBBLESTONE("COBBLESTONE", 3, 3, "cobblestone", "cobble"), 
    BRICK("BRICK", 4, 4, "brick"), 
    SMOOTHBRICK("SMOOTHBRICK", 5, 5, "stone_brick", "smoothStoneBrick"), 
    NETHERBRICK("NETHERBRICK", 6, 6, "nether_brick", "netherBrick"), 
    QUARTZ("QUARTZ", 7, 7, "quartz");
    
    private static final EnumType[] field_176640_i;
    private static final EnumType[] $VALUES;
    private final int field_176637_j;
    private final String field_176638_k;
    private final String field_176635_l;
    
    private EnumType(final String p_i45677_1_, final int p_i45677_2_, final int p_i45677_3_, final String p_i45677_4_) {
        this(p_i45677_1_, p_i45677_2_, p_i45677_3_, p_i45677_4_, p_i45677_4_);
    }
    
    private EnumType(final String p_i45678_1_, final int p_i45678_2_, final int p_i45678_3_, final String p_i45678_4_, final String p_i45678_5_) {
        this.field_176637_j = p_i45678_3_;
        this.field_176638_k = p_i45678_4_;
        this.field_176635_l = p_i45678_5_;
    }
    
    public static EnumType func_176625_a(int p_176625_0_) {
        if (p_176625_0_ < 0 || p_176625_0_ >= EnumType.field_176640_i.length) {
            p_176625_0_ = 0;
        }
        return EnumType.field_176640_i[p_176625_0_];
    }
    
    public int func_176624_a() {
        return this.field_176637_j;
    }
    
    @Override
    public String toString() {
        return this.field_176638_k;
    }
    
    @Override
    public String getName() {
        return this.field_176638_k;
    }
    
    public String func_176627_c() {
        return this.field_176635_l;
    }
    
    static {
        field_176640_i = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.STONE, EnumType.SAND, EnumType.WOOD, EnumType.COBBLESTONE, EnumType.BRICK, EnumType.SMOOTHBRICK, EnumType.NETHERBRICK, EnumType.QUARTZ };
        for (final EnumType var4 : values()) {
            EnumType.field_176640_i[var4.func_176624_a()] = var4;
        }
    }
}
