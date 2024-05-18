package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.base.Predicate;

public class BlockPattern
{
    private final Predicate[][][] HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private final int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002024";
    
    public BlockPattern(final Predicate[][][] p_i45657_1_) {
        this.HorizonCode_Horizon_È = p_i45657_1_;
        this.Â = p_i45657_1_.length;
        if (this.Â > 0) {
            this.Ý = p_i45657_1_[0].length;
            if (this.Ý > 0) {
                this.Ø­áŒŠá = p_i45657_1_[0][0].length;
            }
            else {
                this.Ø­áŒŠá = 0;
            }
        }
        else {
            this.Ý = 0;
            this.Ø­áŒŠá = 0;
        }
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    private Â HorizonCode_Horizon_È(final BlockPos p_177682_1_, final EnumFacing p_177682_2_, final EnumFacing p_177682_3_, final LoadingCache p_177682_4_) {
        for (int var5 = 0; var5 < this.Ø­áŒŠá; ++var5) {
            for (int var6 = 0; var6 < this.Ý; ++var6) {
                for (int var7 = 0; var7 < this.Â; ++var7) {
                    if (!this.HorizonCode_Horizon_È[var7][var6][var5].apply(p_177682_4_.getUnchecked((Object)HorizonCode_Horizon_È(p_177682_1_, p_177682_2_, p_177682_3_, var5, var6, var7)))) {
                        return null;
                    }
                }
            }
        }
        return new Â(p_177682_1_, p_177682_2_, p_177682_3_, p_177682_4_);
    }
    
    public Â HorizonCode_Horizon_È(final World worldIn, final BlockPos p_177681_2_) {
        final LoadingCache var3 = CacheBuilder.newBuilder().build((CacheLoader)new HorizonCode_Horizon_È(worldIn));
        final int var4 = Math.max(Math.max(this.Ø­áŒŠá, this.Ý), this.Â);
        for (final BlockPos var6 : BlockPos.Â(p_177681_2_, p_177681_2_.Â(var4 - 1, var4 - 1, var4 - 1))) {
            for (final EnumFacing var10 : EnumFacing.values()) {
                for (final EnumFacing var14 : EnumFacing.values()) {
                    if (var14 != var10 && var14 != var10.Âµá€()) {
                        final Â var15 = this.HorizonCode_Horizon_È(var6, var10, var14, var3);
                        if (var15 != null) {
                            return var15;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    protected static BlockPos HorizonCode_Horizon_È(final BlockPos p_177683_0_, final EnumFacing p_177683_1_, final EnumFacing p_177683_2_, final int p_177683_3_, final int p_177683_4_, final int p_177683_5_) {
        if (p_177683_1_ != p_177683_2_ && p_177683_1_ != p_177683_2_.Âµá€()) {
            final Vec3i var6 = new Vec3i(p_177683_1_.Ø(), p_177683_1_.áŒŠÆ(), p_177683_1_.áˆºÑ¢Õ());
            final Vec3i var7 = new Vec3i(p_177683_2_.Ø(), p_177683_2_.áŒŠÆ(), p_177683_2_.áˆºÑ¢Õ());
            final Vec3i var8 = var6.Ø­áŒŠá(var7);
            return p_177683_0_.Â(var7.HorizonCode_Horizon_È() * -p_177683_4_ + var8.HorizonCode_Horizon_È() * p_177683_3_ + var6.HorizonCode_Horizon_È() * p_177683_5_, var7.Â() * -p_177683_4_ + var8.Â() * p_177683_3_ + var6.Â() * p_177683_5_, var7.Ý() * -p_177683_4_ + var8.Ý() * p_177683_3_ + var6.Ý() * p_177683_5_);
        }
        throw new IllegalArgumentException("Invalid forwards & up combination");
    }
    
    static class HorizonCode_Horizon_È extends CacheLoader
    {
        private final World HorizonCode_Horizon_È;
        private static final String Â = "CL_00002023";
        
        public HorizonCode_Horizon_È(final World worldIn) {
            this.HorizonCode_Horizon_È = worldIn;
        }
        
        public BlockWorldState HorizonCode_Horizon_È(final BlockPos p_177679_1_) {
            return new BlockWorldState(this.HorizonCode_Horizon_È, p_177679_1_);
        }
        
        public Object load(final Object p_load_1_) {
            return this.HorizonCode_Horizon_È((BlockPos)p_load_1_);
        }
    }
    
    public static class Â
    {
        private final BlockPos HorizonCode_Horizon_È;
        private final EnumFacing Â;
        private final EnumFacing Ý;
        private final LoadingCache Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002022";
        
        public Â(final BlockPos p_i45655_1_, final EnumFacing p_i45655_2_, final EnumFacing p_i45655_3_, final LoadingCache p_i45655_4_) {
            this.HorizonCode_Horizon_È = p_i45655_1_;
            this.Â = p_i45655_2_;
            this.Ý = p_i45655_3_;
            this.Ø­áŒŠá = p_i45655_4_;
        }
        
        public EnumFacing HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public EnumFacing Â() {
            return this.Ý;
        }
        
        public BlockWorldState HorizonCode_Horizon_È(final int p_177670_1_, final int p_177670_2_, final int p_177670_3_) {
            return (BlockWorldState)this.Ø­áŒŠá.getUnchecked((Object)BlockPattern.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È(), this.Â(), p_177670_1_, p_177670_2_, p_177670_3_));
        }
    }
}
