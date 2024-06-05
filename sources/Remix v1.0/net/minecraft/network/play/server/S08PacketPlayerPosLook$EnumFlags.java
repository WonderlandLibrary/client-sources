package net.minecraft.network.play.server;

import java.util.*;

public enum EnumFlags
{
    X("X", 0, 0), 
    Y("Y", 1, 1), 
    Z("Z", 2, 2), 
    Y_ROT("Y_ROT", 3, 3), 
    X_ROT("X_ROT", 4, 4);
    
    private static final EnumFlags[] $VALUES;
    private int field_180058_f;
    
    private EnumFlags(final String p_i45992_1_, final int p_i45992_2_, final int p_i45992_3_) {
        this.field_180058_f = p_i45992_3_;
    }
    
    public static Set func_180053_a(final int p_180053_0_) {
        final EnumSet var1 = EnumSet.noneOf(EnumFlags.class);
        for (final EnumFlags var5 : values()) {
            if (var5.func_180054_b(p_180053_0_)) {
                var1.add(var5);
            }
        }
        return var1;
    }
    
    public static int func_180056_a(final Set p_180056_0_) {
        int var1 = 0;
        for (final EnumFlags var3 : p_180056_0_) {
            var1 |= var3.func_180055_a();
        }
        return var1;
    }
    
    private int func_180055_a() {
        return 1 << this.field_180058_f;
    }
    
    private boolean func_180054_b(final int p_180054_1_) {
        return (p_180054_1_ & this.func_180055_a()) == this.func_180055_a();
    }
    
    static {
        $VALUES = new EnumFlags[] { EnumFlags.X, EnumFlags.Y, EnumFlags.Z, EnumFlags.Y_ROT, EnumFlags.X_ROT };
    }
}
