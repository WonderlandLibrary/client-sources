package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.ComparisonChain;
import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;

public class WeightedBakedModel implements IBakedModel
{
    private final int HorizonCode_Horizon_È;
    private final List Â;
    private final IBakedModel Ý;
    private static final String Ø­áŒŠá = "CL_00002384";
    
    public WeightedBakedModel(final List p_i46073_1_) {
        this.Â = p_i46073_1_;
        this.HorizonCode_Horizon_È = WeightedRandom.HorizonCode_Horizon_È(p_i46073_1_);
        this.Ý = p_i46073_1_.get(0).HorizonCode_Horizon_È;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumFacing p_177551_1_) {
        return this.Ý.HorizonCode_Horizon_È(p_177551_1_);
    }
    
    @Override
    public List HorizonCode_Horizon_È() {
        return this.Ý.HorizonCode_Horizon_È();
    }
    
    @Override
    public boolean Â() {
        return this.Ý.Â();
    }
    
    @Override
    public boolean Ý() {
        return this.Ý.Ý();
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return this.Ý.Ø­áŒŠá();
    }
    
    @Override
    public TextureAtlasSprite Âµá€() {
        return this.Ý.Âµá€();
    }
    
    @Override
    public ItemCameraTransforms Ó() {
        return this.Ý.Ó();
    }
    
    public IBakedModel HorizonCode_Horizon_È(final long p_177564_1_) {
        return ((Â)WeightedRandom.HorizonCode_Horizon_È(this.Â, Math.abs((int)p_177564_1_ >> 16) % this.HorizonCode_Horizon_È)).HorizonCode_Horizon_È;
    }
    
    public static class HorizonCode_Horizon_È
    {
        private List HorizonCode_Horizon_È;
        private static final String Â = "CL_00002383";
        
        public HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = Lists.newArrayList();
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final IBakedModel p_177677_1_, final int p_177677_2_) {
            this.HorizonCode_Horizon_È.add(new Â(p_177677_1_, p_177677_2_));
            return this;
        }
        
        public WeightedBakedModel HorizonCode_Horizon_È() {
            Collections.sort((List<Comparable>)this.HorizonCode_Horizon_È);
            return new WeightedBakedModel(this.HorizonCode_Horizon_È);
        }
        
        public IBakedModel Â() {
            return this.HorizonCode_Horizon_È.get(0).HorizonCode_Horizon_È;
        }
    }
    
    static class Â extends WeightedRandom.HorizonCode_Horizon_È implements Comparable
    {
        protected final IBakedModel HorizonCode_Horizon_È;
        private static final String Â = "CL_00002382";
        
        public Â(final IBakedModel p_i46072_1_, final int p_i46072_2_) {
            super(p_i46072_2_);
            this.HorizonCode_Horizon_È = p_i46072_1_;
        }
        
        public int HorizonCode_Horizon_È(final Â p_177634_1_) {
            return ComparisonChain.start().compare(p_177634_1_.Ý, this.Ý).compare(this.HorizonCode_Horizon_È(), p_177634_1_.HorizonCode_Horizon_È()).result();
        }
        
        protected int HorizonCode_Horizon_È() {
            int var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È().size();
            for (final EnumFacing var5 : EnumFacing.values()) {
                var1 += this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5).size();
            }
            return var1;
        }
        
        @Override
        public String toString() {
            return "MyWeighedRandomItem{weight=" + this.Ý + ", model=" + this.HorizonCode_Horizon_È + '}';
        }
        
        @Override
        public int compareTo(final Object p_compareTo_1_) {
            return this.HorizonCode_Horizon_È((Â)p_compareTo_1_);
        }
    }
}
