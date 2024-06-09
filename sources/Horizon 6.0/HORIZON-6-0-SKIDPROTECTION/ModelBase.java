package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.List;

public abstract class ModelBase
{
    public float Âµá€;
    public boolean Ó;
    public boolean à;
    public List Ø;
    private Map HorizonCode_Horizon_È;
    public int áŒŠÆ;
    public int áˆºÑ¢Õ;
    private static final String Â = "CL_00000845";
    
    public ModelBase() {
        this.à = true;
        this.Ø = Lists.newArrayList();
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.áŒŠÆ = 64;
        this.áˆºÑ¢Õ = 32;
    }
    
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
    }
    
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
    }
    
    public ModelRenderer HorizonCode_Horizon_È(final Random p_85181_1_) {
        return this.Ø.get(p_85181_1_.nextInt(this.Ø.size()));
    }
    
    protected void HorizonCode_Horizon_È(final String p_78085_1_, final int p_78085_2_, final int p_78085_3_) {
        this.HorizonCode_Horizon_È.put(p_78085_1_, new TextureOffset(p_78085_2_, p_78085_3_));
    }
    
    public TextureOffset HorizonCode_Horizon_È(final String p_78084_1_) {
        return this.HorizonCode_Horizon_È.get(p_78084_1_);
    }
    
    public static void HorizonCode_Horizon_È(final ModelRenderer p_178685_0_, final ModelRenderer p_178685_1_) {
        p_178685_1_.Ó = p_178685_0_.Ó;
        p_178685_1_.à = p_178685_0_.à;
        p_178685_1_.Ø = p_178685_0_.Ø;
        p_178685_1_.Ý = p_178685_0_.Ý;
        p_178685_1_.Ø­áŒŠá = p_178685_0_.Ø­áŒŠá;
        p_178685_1_.Âµá€ = p_178685_0_.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final ModelBase p_178686_1_) {
        this.Âµá€ = p_178686_1_.Âµá€;
        this.Ó = p_178686_1_.Ó;
        this.à = p_178686_1_.à;
    }
}
