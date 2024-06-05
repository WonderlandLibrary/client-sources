package net.minecraft.entity.item;

import java.util.*;
import com.google.common.collect.*;

public enum EnumMinecartType
{
    RIDEABLE("RIDEABLE", 0, 0, "MinecartRideable"), 
    CHEST("CHEST", 1, 1, "MinecartChest"), 
    FURNACE("FURNACE", 2, 2, "MinecartFurnace"), 
    TNT("TNT", 3, 3, "MinecartTNT"), 
    SPAWNER("SPAWNER", 4, 4, "MinecartSpawner"), 
    HOPPER("HOPPER", 5, 5, "MinecartHopper"), 
    COMMAND_BLOCK("COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
    
    private static final Map field_180051_h;
    private static final EnumMinecartType[] $VALUES;
    private final int field_180052_i;
    private final String field_180049_j;
    
    private EnumMinecartType(final String p_i45847_1_, final int p_i45847_2_, final int p_i45847_3_, final String p_i45847_4_) {
        this.field_180052_i = p_i45847_3_;
        this.field_180049_j = p_i45847_4_;
    }
    
    public static EnumMinecartType func_180038_a(final int p_180038_0_) {
        final EnumMinecartType var1 = EnumMinecartType.field_180051_h.get(p_180038_0_);
        return (var1 == null) ? EnumMinecartType.RIDEABLE : var1;
    }
    
    public int func_180039_a() {
        return this.field_180052_i;
    }
    
    public String func_180040_b() {
        return this.field_180049_j;
    }
    
    static {
        field_180051_h = Maps.newHashMap();
        $VALUES = new EnumMinecartType[] { EnumMinecartType.RIDEABLE, EnumMinecartType.CHEST, EnumMinecartType.FURNACE, EnumMinecartType.TNT, EnumMinecartType.SPAWNER, EnumMinecartType.HOPPER, EnumMinecartType.COMMAND_BLOCK };
        for (final EnumMinecartType var4 : values()) {
            EnumMinecartType.field_180051_h.put(var4.func_180039_a(), var4);
        }
    }
}
