package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    OAK("OAK", 0, 0, "oak"), 
    SPRUCE("SPRUCE", 1, 1, "spruce"), 
    BIRCH("BIRCH", 2, 2, "birch"), 
    JUNGLE("JUNGLE", 3, 3, "jungle"), 
    ACACIA("ACACIA", 4, 4, "acacia"), 
    DARK_OAK("DARK_OAK", 5, 5, "dark_oak", "big_oak");
    
    private static final EnumType[] field_176842_g;
    private static final EnumType[] $VALUES;
    private final int field_176850_h;
    private final String field_176851_i;
    private final String field_176848_j;
    
    private EnumType(final String p_i45695_1_, final int p_i45695_2_, final int p_i45695_3_, final String p_i45695_4_) {
        this(p_i45695_1_, p_i45695_2_, p_i45695_3_, p_i45695_4_, p_i45695_4_);
    }
    
    private EnumType(final String p_i45696_1_, final int p_i45696_2_, final int p_i45696_3_, final String p_i45696_4_, final String p_i45696_5_) {
        this.field_176850_h = p_i45696_3_;
        this.field_176851_i = p_i45696_4_;
        this.field_176848_j = p_i45696_5_;
    }
    
    public static EnumType func_176837_a(int p_176837_0_) {
        if (p_176837_0_ < 0 || p_176837_0_ >= EnumType.field_176842_g.length) {
            p_176837_0_ = 0;
        }
        return EnumType.field_176842_g[p_176837_0_];
    }
    
    public int func_176839_a() {
        return this.field_176850_h;
    }
    
    @Override
    public String toString() {
        return this.field_176851_i;
    }
    
    @Override
    public String getName() {
        return this.field_176851_i;
    }
    
    public String func_176840_c() {
        return this.field_176848_j;
    }
    
    static {
        field_176842_g = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.OAK, EnumType.SPRUCE, EnumType.BIRCH, EnumType.JUNGLE, EnumType.ACACIA, EnumType.DARK_OAK };
        for (final EnumType var4 : values()) {
            EnumType.field_176842_g[var4.func_176839_a()] = var4;
        }
    }
}
