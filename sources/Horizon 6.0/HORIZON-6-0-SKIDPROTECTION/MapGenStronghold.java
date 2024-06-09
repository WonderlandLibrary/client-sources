package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import com.google.common.collect.Lists;
import java.util.List;

public class MapGenStronghold extends MapGenStructure
{
    private List Âµá€;
    private boolean Ó;
    private ChunkCoordIntPair[] à;
    private double Ø;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000481";
    
    public MapGenStronghold() {
        this.à = new ChunkCoordIntPair[3];
        this.Ø = 32.0;
        this.áŒŠÆ = 3;
        this.Âµá€ = Lists.newArrayList();
        for (final BiomeGenBase var4 : BiomeGenBase.£á()) {
            if (var4 != null && var4.ˆÐƒØ­à > 0.0f) {
                this.Âµá€.add(var4);
            }
        }
    }
    
    public MapGenStronghold(final Map p_i2068_1_) {
        this();
        for (final Map.Entry var3 : p_i2068_1_.entrySet()) {
            if (var3.getKey().equals("distance")) {
                this.Ø = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.Ø, 1.0);
            }
            else if (var3.getKey().equals("count")) {
                this.à = new ChunkCoordIntPair[MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.à.length, 1)];
            }
            else {
                if (!var3.getKey().equals("spread")) {
                    continue;
                }
                this.áŒŠÆ = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.áŒŠÆ, 1);
            }
        }
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Stronghold";
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final int p_75047_1_, final int p_75047_2_) {
        if (!this.Ó) {
            final Random var3 = new Random();
            var3.setSeed(this.Ý.Æ());
            double var4 = var3.nextDouble() * 3.141592653589793 * 2.0;
            int var5 = 1;
            for (int var6 = 0; var6 < this.à.length; ++var6) {
                final double var7 = (1.25 * var5 + var3.nextDouble()) * this.Ø * var5;
                int var8 = (int)Math.round(Math.cos(var4) * var7);
                int var9 = (int)Math.round(Math.sin(var4) * var7);
                final BlockPos var10 = this.Ý.áŒŠÆ().HorizonCode_Horizon_È((var8 << 4) + 8, (var9 << 4) + 8, 112, this.Âµá€, var3);
                if (var10 != null) {
                    var8 = var10.HorizonCode_Horizon_È() >> 4;
                    var9 = var10.Ý() >> 4;
                }
                this.à[var6] = new ChunkCoordIntPair(var8, var9);
                var4 += 6.283185307179586 * var5 / this.áŒŠÆ;
                if (var6 == this.áŒŠÆ) {
                    var5 += 2 + var3.nextInt(5);
                    this.áŒŠÆ += 1 + var3.nextInt(2);
                }
            }
            this.Ó = true;
        }
        for (final ChunkCoordIntPair var14 : this.à) {
            if (p_75047_1_ == var14.HorizonCode_Horizon_È && p_75047_2_ == var14.Â) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected List Â() {
        final ArrayList var1 = Lists.newArrayList();
        for (final ChunkCoordIntPair var5 : this.à) {
            if (var5 != null) {
                var1.add(var5.HorizonCode_Horizon_È(64));
            }
        }
        return var1;
    }
    
    @Override
    protected StructureStart Â(final int p_75049_1_, final int p_75049_2_) {
        HorizonCode_Horizon_È var3;
        for (var3 = new HorizonCode_Horizon_È(this.Ý, this.Â, p_75049_1_, p_75049_2_); var3.Ý().isEmpty() || var3.Ý().get(0).Â == null; var3 = new HorizonCode_Horizon_È(this.Ý, this.Â, p_75049_1_, p_75049_2_)) {}
        return var3;
    }
    
    public static class HorizonCode_Horizon_È extends StructureStart
    {
        private static final String Ý = "CL_00000482";
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final World worldIn, final Random p_i2067_2_, final int p_i2067_3_, final int p_i2067_4_) {
            super(p_i2067_3_, p_i2067_4_);
            StructureStrongholdPieces.Â();
            final StructureStrongholdPieces.á var5 = new StructureStrongholdPieces.á(0, p_i2067_2_, (p_i2067_3_ << 4) + 2, (p_i2067_4_ << 4) + 2);
            this.HorizonCode_Horizon_È.add(var5);
            var5.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_i2067_2_);
            final List var6 = var5.Ý;
            while (!var6.isEmpty()) {
                final int var7 = p_i2067_2_.nextInt(var6.size());
                final StructureComponent var8 = var6.remove(var7);
                var8.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_i2067_2_);
            }
            this.Ø­áŒŠá();
            this.HorizonCode_Horizon_È(worldIn, p_i2067_2_, 10);
        }
    }
}
