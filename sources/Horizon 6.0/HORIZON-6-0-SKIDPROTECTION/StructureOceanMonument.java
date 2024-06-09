package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;

public class StructureOceanMonument extends MapGenStructure
{
    private int Ó;
    private int à;
    public static final List Âµá€;
    private static final List Ø;
    private static final String áŒŠÆ = "CL_00001996";
    
    static {
        Âµá€ = Arrays.asList(BiomeGenBase.£à, BiomeGenBase.¥à, BiomeGenBase.Šáƒ, BiomeGenBase.ŠÄ, BiomeGenBase.Ñ¢á);
        (Ø = Lists.newArrayList()).add(new BiomeGenBase.Â(EntityGuardian.class, 1, 2, 4));
    }
    
    public StructureOceanMonument() {
        this.Ó = 32;
        this.à = 5;
    }
    
    public StructureOceanMonument(final Map p_i45608_1_) {
        this();
        for (final Map.Entry var3 : p_i45608_1_.entrySet()) {
            if (var3.getKey().equals("spacing")) {
                this.Ó = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.Ó, 1);
            }
            else {
                if (!var3.getKey().equals("separation")) {
                    continue;
                }
                this.à = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.à, 1);
            }
        }
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Monument";
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(int p_75047_1_, int p_75047_2_) {
        final int var3 = p_75047_1_;
        final int var4 = p_75047_2_;
        if (p_75047_1_ < 0) {
            p_75047_1_ -= this.Ó - 1;
        }
        if (p_75047_2_ < 0) {
            p_75047_2_ -= this.Ó - 1;
        }
        int var5 = p_75047_1_ / this.Ó;
        int var6 = p_75047_2_ / this.Ó;
        final Random var7 = this.Ý.Â(var5, var6, 10387313);
        var5 *= this.Ó;
        var6 *= this.Ó;
        var5 += (var7.nextInt(this.Ó - this.à) + var7.nextInt(this.Ó - this.à)) / 2;
        var6 += (var7.nextInt(this.Ó - this.à) + var7.nextInt(this.Ó - this.à)) / 2;
        if (var3 == var5 && var4 == var6) {
            if (this.Ý.áŒŠÆ().HorizonCode_Horizon_È(new BlockPos(var3 * 16 + 8, 64, var4 * 16 + 8), null) != BiomeGenBase.¥à) {
                return false;
            }
            final boolean var8 = this.Ý.áŒŠÆ().HorizonCode_Horizon_È(var3 * 16 + 8, var4 * 16 + 8, 29, StructureOceanMonument.Âµá€);
            if (var8) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected StructureStart Â(final int p_75049_1_, final int p_75049_2_) {
        return new HorizonCode_Horizon_È(this.Ý, this.Â, p_75049_1_, p_75049_2_);
    }
    
    public List Ý() {
        return StructureOceanMonument.Ø;
    }
    
    public static class HorizonCode_Horizon_È extends StructureStart
    {
        private Set Ý;
        private boolean Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001995";
        
        public HorizonCode_Horizon_È() {
            this.Ý = Sets.newHashSet();
        }
        
        public HorizonCode_Horizon_È(final World worldIn, final Random p_i45607_2_, final int p_i45607_3_, final int p_i45607_4_) {
            super(p_i45607_3_, p_i45607_4_);
            this.Ý = Sets.newHashSet();
            this.Â(worldIn, p_i45607_2_, p_i45607_3_, p_i45607_4_);
        }
        
        private void Â(final World worldIn, final Random p_175789_2_, final int p_175789_3_, final int p_175789_4_) {
            p_175789_2_.setSeed(worldIn.Æ());
            final long var5 = p_175789_2_.nextLong();
            final long var6 = p_175789_2_.nextLong();
            final long var7 = p_175789_3_ * var5;
            final long var8 = p_175789_4_ * var6;
            p_175789_2_.setSeed(var7 ^ var8 ^ worldIn.Æ());
            final int var9 = p_175789_3_ * 16 + 8 - 29;
            final int var10 = p_175789_4_ * 16 + 8 - 29;
            final EnumFacing var11 = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_175789_2_);
            this.HorizonCode_Horizon_È.add(new StructureOceanMonumentPieces.áŒŠÆ(p_175789_2_, var9, var10, var11));
            this.Ø­áŒŠá();
            this.Ø­áŒŠá = true;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final World worldIn, final Random p_75068_2_, final StructureBoundingBox p_75068_3_) {
            if (!this.Ø­áŒŠá) {
                this.HorizonCode_Horizon_È.clear();
                this.Â(worldIn, p_75068_2_, this.Âµá€(), this.Ó());
            }
            super.HorizonCode_Horizon_È(worldIn, p_75068_2_, p_75068_3_);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final ChunkCoordIntPair p_175788_1_) {
            return !this.Ý.contains(p_175788_1_) && super.HorizonCode_Horizon_È(p_175788_1_);
        }
        
        @Override
        public void Â(final ChunkCoordIntPair p_175787_1_) {
            super.Â(p_175787_1_);
            this.Ý.add(p_175787_1_);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final NBTTagCompound p_143022_1_) {
            super.HorizonCode_Horizon_È(p_143022_1_);
            final NBTTagList var2 = new NBTTagList();
            for (final ChunkCoordIntPair var4 : this.Ý) {
                final NBTTagCompound var5 = new NBTTagCompound();
                var5.HorizonCode_Horizon_È("X", var4.HorizonCode_Horizon_È);
                var5.HorizonCode_Horizon_È("Z", var4.Â);
                var2.HorizonCode_Horizon_È(var5);
            }
            p_143022_1_.HorizonCode_Horizon_È("Processed", var2);
        }
        
        @Override
        public void Â(final NBTTagCompound p_143017_1_) {
            super.Â(p_143017_1_);
            if (p_143017_1_.Â("Processed", 9)) {
                final NBTTagList var2 = p_143017_1_.Ý("Processed", 10);
                for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                    final NBTTagCompound var4 = var2.Â(var3);
                    this.Ý.add(new ChunkCoordIntPair(var4.Ó("X"), var4.Ó("Z")));
                }
            }
        }
    }
}
