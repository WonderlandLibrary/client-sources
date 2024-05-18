package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class RenderZombie extends RenderBiped
{
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private final ModelBiped á;
    private final ModelZombieVillager ˆÏ­;
    private final List £á;
    private final List Å;
    private static final String £à = "CL_00001037";
    
    static {
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/zombie/zombie.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/zombie/zombie_villager.png");
    }
    
    public RenderZombie(final RenderManager p_i46127_1_) {
        super(p_i46127_1_, new ModelZombie(), 0.5f, 1.0f);
        final LayerRenderer var2 = this.Ø.get(0);
        this.á = this.HorizonCode_Horizon_È;
        this.ˆÏ­ = new ModelZombieVillager();
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
        final LayerBipedArmor var3 = new LayerBipedArmor(this) {
            private static final String Âµá€ = "CL_00002429";
            
            @Override
            protected void HorizonCode_Horizon_È() {
                this.Ý = new ModelZombie(0.5f, true);
                this.Ø­áŒŠá = new ModelZombie(1.0f, true);
            }
        };
        this.HorizonCode_Horizon_È(var3);
        this.Å = Lists.newArrayList((Iterable)this.Ø);
        if (var2 instanceof LayerCustomHead) {
            this.Â(var2);
            this.HorizonCode_Horizon_È(new LayerCustomHead(this.ˆÏ­.ÂµÈ));
        }
        this.Â(var3);
        this.HorizonCode_Horizon_È(new LayerVillagerArmor(this));
        this.£á = Lists.newArrayList((Iterable)this.Ø);
    }
    
    public void HorizonCode_Horizon_È(final EntityZombie p_180579_1_, final double p_180579_2_, final double p_180579_4_, final double p_180579_6_, final float p_180579_8_, final float p_180579_9_) {
        this.Â(p_180579_1_);
        super.HorizonCode_Horizon_È(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityZombie p_180578_1_) {
        return p_180578_1_.ÐƒÇŽà() ? RenderZombie.ÂµÈ : RenderZombie.áˆºÑ¢Õ;
    }
    
    private void Â(final EntityZombie p_82427_1_) {
        if (p_82427_1_.ÐƒÇŽà()) {
            this.Ó = this.ˆÏ­;
            this.Ø = this.£á;
        }
        else {
            this.Ó = this.á;
            this.Ø = this.Å;
        }
        this.HorizonCode_Horizon_È = (ModelBiped)this.Ó;
    }
    
    protected void HorizonCode_Horizon_È(final EntityZombie p_77043_1_, final float p_77043_2_, float p_77043_3_, final float p_77043_4_) {
        if (p_77043_1_.¥Ê()) {
            p_77043_3_ += (float)(Math.cos(p_77043_1_.Œ * 3.25) * 3.141592653589793 * 0.25);
        }
        super.HorizonCode_Horizon_È(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityLiving p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityZombie)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((EntityZombie)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityZombie)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
