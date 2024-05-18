package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.collect.Lists;
import java.util.List;

public class MapGenNetherBridge extends MapGenStructure
{
    private List Âµá€;
    private static final String Ó = "CL_00000451";
    
    public MapGenNetherBridge() {
        (this.Âµá€ = Lists.newArrayList()).add(new BiomeGenBase.Â(EntityBlaze.class, 10, 2, 3));
        this.Âµá€.add(new BiomeGenBase.Â(EntityPigZombie.class, 5, 4, 4));
        this.Âµá€.add(new BiomeGenBase.Â(EntitySkeleton.class, 10, 4, 4));
        this.Âµá€.add(new BiomeGenBase.Â(EntityMagmaCube.class, 3, 4, 4));
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Fortress";
    }
    
    public List A_() {
        return this.Âµá€;
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final int p_75047_1_, final int p_75047_2_) {
        final int var3 = p_75047_1_ >> 4;
        final int var4 = p_75047_2_ >> 4;
        this.Â.setSeed(var3 ^ var4 << 4 ^ this.Ý.Æ());
        this.Â.nextInt();
        return this.Â.nextInt(3) == 0 && p_75047_1_ == (var3 << 4) + 4 + this.Â.nextInt(8) && p_75047_2_ == (var4 << 4) + 4 + this.Â.nextInt(8);
    }
    
    @Override
    protected StructureStart Â(final int p_75049_1_, final int p_75049_2_) {
        return new HorizonCode_Horizon_È(this.Ý, this.Â, p_75049_1_, p_75049_2_);
    }
    
    public static class HorizonCode_Horizon_È extends StructureStart
    {
        private static final String Ý = "CL_00000452";
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final World worldIn, final Random p_i2040_2_, final int p_i2040_3_, final int p_i2040_4_) {
            super(p_i2040_3_, p_i2040_4_);
            final StructureNetherBridgePieces.Å var5 = new StructureNetherBridgePieces.Å(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
            this.HorizonCode_Horizon_È.add(var5);
            var5.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_i2040_2_);
            final List var6 = var5.Ø;
            while (!var6.isEmpty()) {
                final int var7 = p_i2040_2_.nextInt(var6.size());
                final StructureComponent var8 = var6.remove(var7);
                var8.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_i2040_2_);
            }
            this.Ø­áŒŠá();
            this.HorizonCode_Horizon_È(worldIn, p_i2040_2_, 48, 70);
        }
    }
}
