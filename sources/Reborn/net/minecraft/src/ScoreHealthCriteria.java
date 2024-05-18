package net.minecraft.src;

import java.util.*;

public class ScoreHealthCriteria extends ScoreDummyCriteria
{
    public ScoreHealthCriteria(final String par1Str) {
        super(par1Str);
    }
    
    @Override
    public int func_96635_a(final List par1List) {
        float var2 = 0.0f;
        for (final EntityPlayer var4 : par1List) {
            int var5 = var4.getHealth();
            final float var6 = var4.getMaxHealth();
            if (var5 < 0) {
                var5 = 0;
            }
            if (var5 > var6) {
                var5 = var4.getMaxHealth();
            }
            var2 += var5 / var6;
        }
        if (par1List.size() > 0) {
            var2 /= par1List.size();
        }
        return MathHelper.floor_float(var2 * 19.0f) + ((var2 > 0.0f) ? 1 : 0);
    }
    
    @Override
    public boolean isReadOnly() {
        return true;
    }
}
