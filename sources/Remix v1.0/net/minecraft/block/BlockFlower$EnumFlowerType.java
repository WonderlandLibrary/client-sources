package net.minecraft.block;

import net.minecraft.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public enum EnumFlowerType implements IStringSerializable
{
    DANDELION("DANDELION", 0, EnumFlowerColor.YELLOW, 0, "dandelion"), 
    POPPY("POPPY", 1, EnumFlowerColor.RED, 0, "poppy"), 
    BLUE_ORCHID("BLUE_ORCHID", 2, EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"), 
    ALLIUM("ALLIUM", 3, EnumFlowerColor.RED, 2, "allium"), 
    HOUSTONIA("HOUSTONIA", 4, EnumFlowerColor.RED, 3, "houstonia"), 
    RED_TULIP("RED_TULIP", 5, EnumFlowerColor.RED, 4, "red_tulip", "tulipRed"), 
    ORANGE_TULIP("ORANGE_TULIP", 6, EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"), 
    WHITE_TULIP("WHITE_TULIP", 7, EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"), 
    PINK_TULIP("PINK_TULIP", 8, EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink"), 
    OXEYE_DAISY("OXEYE_DAISY", 9, EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");
    
    private static final EnumFlowerType[][] field_176981_k;
    private static final EnumFlowerType[] $VALUES;
    private final EnumFlowerColor field_176978_l;
    private final int field_176979_m;
    private final String field_176976_n;
    private final String field_176977_o;
    
    private EnumFlowerType(final String p_i45718_1_, final int p_i45718_2_, final EnumFlowerColor p_i45718_3_, final int p_i45718_4_, final String p_i45718_5_) {
        this(p_i45718_1_, p_i45718_2_, p_i45718_3_, p_i45718_4_, p_i45718_5_, p_i45718_5_);
    }
    
    private EnumFlowerType(final String p_i45719_1_, final int p_i45719_2_, final EnumFlowerColor p_i45719_3_, final int p_i45719_4_, final String p_i45719_5_, final String p_i45719_6_) {
        this.field_176978_l = p_i45719_3_;
        this.field_176979_m = p_i45719_4_;
        this.field_176976_n = p_i45719_5_;
        this.field_176977_o = p_i45719_6_;
    }
    
    public static EnumFlowerType func_176967_a(final EnumFlowerColor p_176967_0_, int p_176967_1_) {
        final EnumFlowerType[] var2 = EnumFlowerType.field_176981_k[p_176967_0_.ordinal()];
        if (p_176967_1_ < 0 || p_176967_1_ >= var2.length) {
            p_176967_1_ = 0;
        }
        return var2[p_176967_1_];
    }
    
    public static EnumFlowerType[] func_176966_a(final EnumFlowerColor p_176966_0_) {
        return EnumFlowerType.field_176981_k[p_176966_0_.ordinal()];
    }
    
    public EnumFlowerColor func_176964_a() {
        return this.field_176978_l;
    }
    
    public int func_176968_b() {
        return this.field_176979_m;
    }
    
    @Override
    public String toString() {
        return this.field_176976_n;
    }
    
    @Override
    public String getName() {
        return this.field_176976_n;
    }
    
    public String func_176963_d() {
        return this.field_176977_o;
    }
    
    static {
        field_176981_k = new EnumFlowerType[EnumFlowerColor.values().length][];
        $VALUES = new EnumFlowerType[] { EnumFlowerType.DANDELION, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY };
        for (final EnumFlowerColor var4 : EnumFlowerColor.values()) {
            final Collection var5 = Collections2.filter((Collection)Lists.newArrayList((Object[])values()), (Predicate)new Predicate() {
                public boolean func_180350_a(final EnumFlowerType p_180350_1_) {
                    return p_180350_1_.func_176964_a() == var4;
                }
                
                public boolean apply(final Object p_apply_1_) {
                    return this.func_180350_a((EnumFlowerType)p_apply_1_);
                }
            });
            EnumFlowerType.field_176981_k[var4.ordinal()] = var5.toArray(new EnumFlowerType[var5.size()]);
        }
    }
}
