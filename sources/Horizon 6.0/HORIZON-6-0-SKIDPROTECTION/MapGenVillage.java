package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class MapGenVillage extends MapGenStructure
{
    public static final List Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private static final String áŒŠÆ = "CL_00000514";
    
    static {
        Âµá€ = Arrays.asList(BiomeGenBase.µà, BiomeGenBase.ˆà, BiomeGenBase.Ï­à);
    }
    
    public MapGenVillage() {
        this.à = 32;
        this.Ø = 8;
    }
    
    public MapGenVillage(final Map p_i2093_1_) {
        this();
        for (final Map.Entry var3 : p_i2093_1_.entrySet()) {
            if (var3.getKey().equals("size")) {
                this.Ó = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.Ó, 0);
            }
            else {
                if (!var3.getKey().equals("distance")) {
                    continue;
                }
                this.à = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.à, this.Ø + 1);
            }
        }
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Village";
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
        final Random var7 = this.Ý.Â(var5, var6, 10387312);
        var5 *= this.à;
        var6 *= this.à;
        var5 += var7.nextInt(this.à - this.Ø);
        var6 += var7.nextInt(this.à - this.Ø);
        if (var3 == var5 && var4 == var6) {
            final boolean var8 = this.Ý.áŒŠÆ().HorizonCode_Horizon_È(var3 * 16 + 8, var4 * 16 + 8, 0, MapGenVillage.Âµá€);
            if (var8) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected StructureStart Â(final int p_75049_1_, final int p_75049_2_) {
        return new HorizonCode_Horizon_È(this.Ý, this.Â, p_75049_1_, p_75049_2_, this.Ó);
    }
    
    public static class HorizonCode_Horizon_È extends StructureStart
    {
        private boolean Ý;
        private static final String Ø­áŒŠá = "CL_00000515";
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final World worldIn, final Random p_i2092_2_, final int p_i2092_3_, final int p_i2092_4_, final int p_i2092_5_) {
            super(p_i2092_3_, p_i2092_4_);
            final List var6 = StructureVillagePieces.HorizonCode_Horizon_È(p_i2092_2_, p_i2092_5_);
            final StructureVillagePieces.á var7 = new StructureVillagePieces.á(worldIn.áŒŠÆ(), 0, p_i2092_2_, (p_i2092_3_ << 4) + 2, (p_i2092_4_ << 4) + 2, var6, p_i2092_5_);
            this.HorizonCode_Horizon_È.add(var7);
            var7.HorizonCode_Horizon_È(var7, this.HorizonCode_Horizon_È, p_i2092_2_);
            final List var8 = var7.áˆºÑ¢Õ;
            final List var9 = var7.áŒŠÆ;
            while (!var8.isEmpty() || !var9.isEmpty()) {
                if (var8.isEmpty()) {
                    final int var10 = p_i2092_2_.nextInt(var9.size());
                    final StructureComponent var11 = var9.remove(var10);
                    var11.HorizonCode_Horizon_È(var7, this.HorizonCode_Horizon_È, p_i2092_2_);
                }
                else {
                    final int var10 = p_i2092_2_.nextInt(var8.size());
                    final StructureComponent var11 = var8.remove(var10);
                    var11.HorizonCode_Horizon_È(var7, this.HorizonCode_Horizon_È, p_i2092_2_);
                }
            }
            this.Ø­áŒŠá();
            int var10 = 0;
            for (final StructureComponent var13 : this.HorizonCode_Horizon_È) {
                if (!(var13 instanceof StructureVillagePieces.ÂµÈ)) {
                    ++var10;
                }
            }
            this.Ý = (var10 > 2);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Ý;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final NBTTagCompound p_143022_1_) {
            super.HorizonCode_Horizon_È(p_143022_1_);
            p_143022_1_.HorizonCode_Horizon_È("Valid", this.Ý);
        }
        
        @Override
        public void Â(final NBTTagCompound p_143017_1_) {
            super.Â(p_143017_1_);
            this.Ý = p_143017_1_.£á("Valid");
        }
    }
}
