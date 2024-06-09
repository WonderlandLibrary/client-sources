package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.Set;
import java.util.BitSet;

public class VisGraph
{
    private static final int HorizonCode_Horizon_È;
    private static final int Â;
    private static final int Ý;
    private final BitSet Ø­áŒŠá;
    private static final int[] Âµá€;
    private int Ó;
    private static final String à = "CL_00002450";
    
    static {
        HorizonCode_Horizon_È = (int)Math.pow(16.0, 0.0);
        Â = (int)Math.pow(16.0, 1.0);
        Ý = (int)Math.pow(16.0, 2.0);
        Âµá€ = new int[1352];
        final boolean var0 = false;
        final boolean var2 = true;
        int var3 = 0;
        for (int var4 = 0; var4 < 16; ++var4) {
            for (int var5 = 0; var5 < 16; ++var5) {
                for (int var6 = 0; var6 < 16; ++var6) {
                    if (var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15 || var6 == 0 || var6 == 15) {
                        VisGraph.Âµá€[var3++] = HorizonCode_Horizon_È(var4, var5, var6);
                    }
                }
            }
        }
    }
    
    public VisGraph() {
        this.Ø­áŒŠá = new BitSet(4096);
        this.Ó = 4096;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_178606_1_) {
        this.Ø­áŒŠá.set(Ý(p_178606_1_), true);
        --this.Ó;
    }
    
    private static int Ý(final BlockPos p_178608_0_) {
        return HorizonCode_Horizon_È(p_178608_0_.HorizonCode_Horizon_È() & 0xF, p_178608_0_.Â() & 0xF, p_178608_0_.Ý() & 0xF);
    }
    
    private static int HorizonCode_Horizon_È(final int p_178605_0_, final int p_178605_1_, final int p_178605_2_) {
        return p_178605_0_ << 0 | p_178605_1_ << 8 | p_178605_2_ << 4;
    }
    
    public SetVisibility HorizonCode_Horizon_È() {
        final SetVisibility var1 = new SetVisibility();
        if (4096 - this.Ó < 256) {
            var1.HorizonCode_Horizon_È(true);
        }
        else if (this.Ó == 0) {
            var1.HorizonCode_Horizon_È(false);
        }
        else {
            for (final int var5 : VisGraph.Âµá€) {
                if (!this.Ø­áŒŠá.get(var5)) {
                    var1.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(var5));
                }
            }
        }
        return var1;
    }
    
    public Set Â(final BlockPos p_178609_1_) {
        return this.HorizonCode_Horizon_È(Ý(p_178609_1_));
    }
    
    private Set HorizonCode_Horizon_È(final int p_178604_1_) {
        final EnumSet var2 = EnumSet.noneOf(EnumFacing.class);
        final ArrayDeque var3 = new ArrayDeque(384);
        var3.add(IntegerCache.HorizonCode_Horizon_È(p_178604_1_));
        this.Ø­áŒŠá.set(p_178604_1_, true);
        while (!var3.isEmpty()) {
            final int var4 = var3.poll();
            this.HorizonCode_Horizon_È(var4, var2);
            for (final EnumFacing var8 : EnumFacing.à) {
                final int var9 = this.HorizonCode_Horizon_È(var4, var8);
                if (var9 >= 0 && !this.Ø­áŒŠá.get(var9)) {
                    this.Ø­áŒŠá.set(var9, true);
                    var3.add(IntegerCache.HorizonCode_Horizon_È(var9));
                }
            }
        }
        return var2;
    }
    
    private void HorizonCode_Horizon_È(final int p_178610_1_, final Set p_178610_2_) {
        final int var3 = p_178610_1_ >> 0 & 0xF;
        if (var3 == 0) {
            p_178610_2_.add(EnumFacing.Âµá€);
        }
        else if (var3 == 15) {
            p_178610_2_.add(EnumFacing.Ó);
        }
        final int var4 = p_178610_1_ >> 8 & 0xF;
        if (var4 == 0) {
            p_178610_2_.add(EnumFacing.HorizonCode_Horizon_È);
        }
        else if (var4 == 15) {
            p_178610_2_.add(EnumFacing.Â);
        }
        final int var5 = p_178610_1_ >> 4 & 0xF;
        if (var5 == 0) {
            p_178610_2_.add(EnumFacing.Ý);
        }
        else if (var5 == 15) {
            p_178610_2_.add(EnumFacing.Ø­áŒŠá);
        }
    }
    
    private int HorizonCode_Horizon_È(final int p_178603_1_, final EnumFacing p_178603_2_) {
        switch (VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_178603_2_.ordinal()]) {
            case 1: {
                if ((p_178603_1_ >> 8 & 0xF) == 0x0) {
                    return -1;
                }
                return p_178603_1_ - VisGraph.Ý;
            }
            case 2: {
                if ((p_178603_1_ >> 8 & 0xF) == 0xF) {
                    return -1;
                }
                return p_178603_1_ + VisGraph.Ý;
            }
            case 3: {
                if ((p_178603_1_ >> 4 & 0xF) == 0x0) {
                    return -1;
                }
                return p_178603_1_ - VisGraph.Â;
            }
            case 4: {
                if ((p_178603_1_ >> 4 & 0xF) == 0xF) {
                    return -1;
                }
                return p_178603_1_ + VisGraph.Â;
            }
            case 5: {
                if ((p_178603_1_ >> 0 & 0xF) == 0x0) {
                    return -1;
                }
                return p_178603_1_ - VisGraph.HorizonCode_Horizon_È;
            }
            case 6: {
                if ((p_178603_1_ >> 0 & 0xF) == 0xF) {
                    return -1;
                }
                return p_178603_1_ + VisGraph.HorizonCode_Horizon_È;
            }
            default: {
                return -1;
            }
        }
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002449";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                VisGraph.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
