package net.minecraft.src;

public enum EnumEntitySize
{
    SIZE_1("SIZE_1", 0), 
    SIZE_2("SIZE_2", 1), 
    SIZE_3("SIZE_3", 2), 
    SIZE_4("SIZE_4", 3), 
    SIZE_5("SIZE_5", 4), 
    SIZE_6("SIZE_6", 5);
    
    private EnumEntitySize(final String s, final int n) {
    }
    
    public int multiplyBy32AndRound(final double par1) {
        final double var3 = par1 - (MathHelper.floor_double(par1) + 0.5);
        switch (EnumEntitySizeHelper.field_96565_a[this.ordinal()]) {
            case 1: {
                if (var3 < 0.0) {
                    if (var3 < -0.3125) {
                        return MathHelper.ceiling_double_int(par1 * 32.0);
                    }
                }
                else if (var3 < 0.3125) {
                    return MathHelper.ceiling_double_int(par1 * 32.0);
                }
                return MathHelper.floor_double(par1 * 32.0);
            }
            case 2: {
                if (var3 < 0.0) {
                    if (var3 < -0.3125) {
                        return MathHelper.floor_double(par1 * 32.0);
                    }
                }
                else if (var3 < 0.3125) {
                    return MathHelper.floor_double(par1 * 32.0);
                }
                return MathHelper.ceiling_double_int(par1 * 32.0);
            }
            case 3: {
                if (var3 > 0.0) {
                    return MathHelper.floor_double(par1 * 32.0);
                }
                return MathHelper.ceiling_double_int(par1 * 32.0);
            }
            case 4: {
                if (var3 < 0.0) {
                    if (var3 < -0.1875) {
                        return MathHelper.ceiling_double_int(par1 * 32.0);
                    }
                }
                else if (var3 < 0.1875) {
                    return MathHelper.ceiling_double_int(par1 * 32.0);
                }
                return MathHelper.floor_double(par1 * 32.0);
            }
            case 5: {
                if (var3 < 0.0) {
                    if (var3 < -0.1875) {
                        return MathHelper.floor_double(par1 * 32.0);
                    }
                }
                else if (var3 < 0.1875) {
                    return MathHelper.floor_double(par1 * 32.0);
                }
                return MathHelper.ceiling_double_int(par1 * 32.0);
            }
            default: {
                if (var3 > 0.0) {
                    return MathHelper.ceiling_double_int(par1 * 32.0);
                }
                return MathHelper.floor_double(par1 * 32.0);
            }
        }
    }
}
