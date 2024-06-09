package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public abstract class LayerArmorBase implements LayerRenderer
{
    protected static final ResourceLocation_1975012498 Â;
    protected ModelBase Ý;
    protected ModelBase Ø­áŒŠá;
    private final RendererLivingEntity HorizonCode_Horizon_È;
    private float Âµá€;
    private float Ó;
    private float à;
    private float Ø;
    private boolean áŒŠÆ;
    private static final Map áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00002428";
    
    static {
        Â = new ResourceLocation_1975012498("textures/misc/enchanted_item_glint.png");
        áˆºÑ¢Õ = Maps.newHashMap();
    }
    
    public LayerArmorBase(final RendererLivingEntity p_i46125_1_) {
        this.Âµá€ = 1.0f;
        this.Ó = 1.0f;
        this.à = 1.0f;
        this.Ø = 1.0f;
        this.HorizonCode_Horizon_È = p_i46125_1_;
        this.HorizonCode_Horizon_È();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, 4);
        this.HorizonCode_Horizon_È(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, 3);
        this.HorizonCode_Horizon_È(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, 2);
        this.HorizonCode_Horizon_È(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, 1);
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    private void HorizonCode_Horizon_È(final EntityLivingBase p_177182_1_, final float p_177182_2_, final float p_177182_3_, final float p_177182_4_, final float p_177182_5_, final float p_177182_6_, final float p_177182_7_, final float p_177182_8_, final int p_177182_9_) {
        final ItemStack var10 = this.HorizonCode_Horizon_È(p_177182_1_, p_177182_9_);
        if (var10 != null && var10.HorizonCode_Horizon_È() instanceof ItemArmor) {
            final ItemArmor var11 = (ItemArmor)var10.HorizonCode_Horizon_È();
            final ModelBase var12 = this.HorizonCode_Horizon_È(p_177182_9_);
            var12.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Â());
            var12.HorizonCode_Horizon_È(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_4_);
            this.HorizonCode_Horizon_È(var12, p_177182_9_);
            final boolean var13 = this.Â(p_177182_9_);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(var11, var13));
            switch (LayerArmorBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var11.ˆà().ordinal()]) {
                case 1: {
                    final int var14 = var11.á(var10);
                    final float var15 = (var14 >> 16 & 0xFF) / 255.0f;
                    final float var16 = (var14 >> 8 & 0xFF) / 255.0f;
                    final float var17 = (var14 & 0xFF) / 255.0f;
                    GlStateManager.Ý(this.Ó * var15, this.à * var16, this.Ø * var17, this.Âµá€);
                    var12.HorizonCode_Horizon_È(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(var11, var13, "overlay"));
                }
                case 2:
                case 3:
                case 4:
                case 5: {
                    GlStateManager.Ý(this.Ó, this.à, this.Ø, this.Âµá€);
                    var12.HorizonCode_Horizon_È(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                    break;
                }
            }
            if (!this.áŒŠÆ && var10.Šáƒ()) {
                this.HorizonCode_Horizon_È(p_177182_1_, var12, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
            }
        }
    }
    
    public ItemStack HorizonCode_Horizon_È(final EntityLivingBase p_177176_1_, final int p_177176_2_) {
        return p_177176_1_.ÂµÈ(p_177176_2_ - 1);
    }
    
    public ModelBase HorizonCode_Horizon_È(final int p_177175_1_) {
        return this.Â(p_177175_1_) ? this.Ý : this.Ø­áŒŠá;
    }
    
    private boolean Â(final int p_177180_1_) {
        return p_177180_1_ == 2;
    }
    
    private void HorizonCode_Horizon_È(final EntityLivingBase p_177183_1_, final ModelBase p_177183_2_, final float p_177183_3_, final float p_177183_4_, final float p_177183_5_, final float p_177183_6_, final float p_177183_7_, final float p_177183_8_, final float p_177183_9_) {
        final float var10 = p_177183_1_.Œ + p_177183_5_;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(LayerArmorBase.Â);
        GlStateManager.á();
        GlStateManager.Ý(514);
        GlStateManager.HorizonCode_Horizon_È(false);
        final float var11 = 0.5f;
        GlStateManager.Ý(var11, var11, var11, 1.0f);
        for (int var12 = 0; var12 < 2; ++var12) {
            GlStateManager.Ó();
            GlStateManager.Â(768, 1);
            final float var13 = 0.76f;
            GlStateManager.Ý(0.5f * var13, 0.25f * var13, 0.8f * var13, 1.0f);
            GlStateManager.á(5890);
            GlStateManager.ŒÏ();
            final float var14 = 0.33333334f;
            GlStateManager.HorizonCode_Horizon_È(var14, var14, var14);
            GlStateManager.Â(30.0f - var12 * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.Â(0.0f, var10 * (0.001f + var12 * 0.003f) * 20.0f, 0.0f);
            GlStateManager.á(5888);
            p_177183_2_.HorizonCode_Horizon_È(p_177183_1_, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_, p_177183_8_, p_177183_9_);
        }
        GlStateManager.á(5890);
        GlStateManager.ŒÏ();
        GlStateManager.á(5888);
        GlStateManager.Âµá€();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.Ý(515);
        GlStateManager.ÂµÈ();
    }
    
    private ResourceLocation_1975012498 HorizonCode_Horizon_È(final ItemArmor p_177181_1_, final boolean p_177181_2_) {
        return this.HorizonCode_Horizon_È(p_177181_1_, p_177181_2_, null);
    }
    
    private ResourceLocation_1975012498 HorizonCode_Horizon_È(final ItemArmor p_177178_1_, final boolean p_177178_2_, final String p_177178_3_) {
        final String var4 = String.format("textures/models/armor/%s_layer_%d%s.png", p_177178_1_.ˆà().Ý(), p_177178_2_ ? 2 : 1, (p_177178_3_ == null) ? "" : String.format("_%s", p_177178_3_));
        ResourceLocation_1975012498 var5 = LayerArmorBase.áˆºÑ¢Õ.get(var4);
        if (var5 == null) {
            var5 = new ResourceLocation_1975012498(var4);
            LayerArmorBase.áˆºÑ¢Õ.put(var4, var5);
        }
        return var5;
    }
    
    protected abstract void HorizonCode_Horizon_È();
    
    protected abstract void HorizonCode_Horizon_È(final ModelBase p0, final int p1);
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002427";
        
        static {
            HorizonCode_Horizon_È = new int[ItemArmor.HorizonCode_Horizon_È.values().length];
            try {
                LayerArmorBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                LayerArmorBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemArmor.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                LayerArmorBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemArmor.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                LayerArmorBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemArmor.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                LayerArmorBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemArmor.HorizonCode_Horizon_È.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
