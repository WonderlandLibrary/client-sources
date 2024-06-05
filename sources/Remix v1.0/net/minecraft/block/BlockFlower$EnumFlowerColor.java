package net.minecraft.block;

import net.minecraft.init.*;

public enum EnumFlowerColor
{
    YELLOW("YELLOW", 0), 
    RED("RED", 1);
    
    private static final EnumFlowerColor[] $VALUES;
    
    private EnumFlowerColor(final String p_i45716_1_, final int p_i45716_2_) {
    }
    
    public BlockFlower func_180346_a() {
        return (this == EnumFlowerColor.YELLOW) ? Blocks.yellow_flower : Blocks.red_flower;
    }
    
    static {
        $VALUES = new EnumFlowerColor[] { EnumFlowerColor.YELLOW, EnumFlowerColor.RED };
    }
}
