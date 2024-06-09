package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;

public class MapGenScatteredFeature extends MapGenStructure
{
    private static final List Âµá€;
    private List Ó;
    private int à;
    private int Ø;
    private static final String áŒŠÆ = "CL_00000471";
    
    static {
        Âµá€ = Arrays.asList(BiomeGenBase.ˆà, BiomeGenBase.ÇŽÕ, BiomeGenBase.Õ, BiomeGenBase.à¢, BiomeGenBase.Æ);
    }
    
    public MapGenScatteredFeature() {
        this.Ó = Lists.newArrayList();
        this.à = 32;
        this.Ø = 8;
        this.Ó.add(new BiomeGenBase.Â(EntityWitch.class, 1, 1, 1));
    }
    
    public MapGenScatteredFeature(final Map p_i2061_1_) {
        this();
        for (final Map.Entry var3 : p_i2061_1_.entrySet()) {
            if (var3.getKey().equals("distance")) {
                this.à = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.à, this.Ø + 1);
            }
        }
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Temple";
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(int p_75047_1_, int p_75047_2_) {
        final int var3 = p_75047_1_;
        final int var4 = p_75047_2_;
        if (p_75047_1_ < 0) {
            p_75047_1_ -= this.à - 1;
        }
        if (p_75047_2_ < 0) {
            p_75047_2_ -= this.à - 1;
        }
        int var5 = p_75047_1_ / this.à;
        int var6 = p_75047_2_ / this.à;
        final Random var7 = this.Ý.Â(var5, var6, 14357617);
        var5 *= this.à;
        var6 *= this.à;
        var5 += var7.nextInt(this.à - this.Ø);
        var6 += var7.nextInt(this.à - this.Ø);
        if (var3 == var5 && var4 == var6) {
            final BiomeGenBase var8 = this.Ý.áŒŠÆ().HorizonCode_Horizon_È(new BlockPos(var3 * 16 + 8, 0, var4 * 16 + 8));
            if (var8 == null) {
                return false;
            }
            for (final BiomeGenBase var10 : MapGenScatteredFeature.Âµá€) {
                if (var8 == var10) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected StructureStart Â(final int p_75049_1_, final int p_75049_2_) {
        return new HorizonCode_Horizon_È(this.Ý, this.Â, p_75049_1_, p_75049_2_);
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_175798_1_) {
        final StructureStart var2 = this.Ý(p_175798_1_);
        if (var2 != null && var2 instanceof HorizonCode_Horizon_È && !var2.HorizonCode_Horizon_È.isEmpty()) {
            final StructureComponent var3 = var2.HorizonCode_Horizon_È.getFirst();
            return var3 instanceof ComponentScatteredFeaturePieces.Ø­áŒŠá;
        }
        return false;
    }
    
    public List B_() {
        return this.Ó;
    }
    
    public static class HorizonCode_Horizon_È extends StructureStart
    {
        private static final String Ý = "CL_00000472";
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final World worldIn, final Random p_i2060_2_, final int p_i2060_3_, final int p_i2060_4_) {
            super(p_i2060_3_, p_i2060_4_);
            final BiomeGenBase var5 = worldIn.Ý(new BlockPos(p_i2060_3_ * 16 + 8, 0, p_i2060_4_ * 16 + 8));
            if (var5 != BiomeGenBase.Õ && var5 != BiomeGenBase.à¢) {
                if (var5 == BiomeGenBase.Æ) {
                    final ComponentScatteredFeaturePieces.Ø­áŒŠá var6 = new ComponentScatteredFeaturePieces.Ø­áŒŠá(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                    this.HorizonCode_Horizon_È.add(var6);
                }
                else if (var5 == BiomeGenBase.ˆà || var5 == BiomeGenBase.ÇŽÕ) {
                    final ComponentScatteredFeaturePieces.HorizonCode_Horizon_È var7 = new ComponentScatteredFeaturePieces.HorizonCode_Horizon_È(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                    this.HorizonCode_Horizon_È.add(var7);
                }
            }
            else {
                final ComponentScatteredFeaturePieces.Ý var8 = new ComponentScatteredFeaturePieces.Ý(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                this.HorizonCode_Horizon_È.add(var8);
            }
            this.Ø­áŒŠá();
        }
    }
}
