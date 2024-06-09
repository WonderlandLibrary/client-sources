package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public class RenderHorse extends RenderLiving
{
    private static final Map HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private static final ResourceLocation_1975012498 á;
    private static final ResourceLocation_1975012498 ˆÏ­;
    private static final String £á = "CL_00001000";
    
    static {
        HorizonCode_Horizon_È = Maps.newHashMap();
        Âµá€ = new ResourceLocation_1975012498("textures/entity/horse/horse_white.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/horse/mule.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/horse/donkey.png");
        á = new ResourceLocation_1975012498("textures/entity/horse/horse_zombie.png");
        ˆÏ­ = new ResourceLocation_1975012498("textures/entity/horse/horse_skeleton.png");
    }
    
    public RenderHorse(final RenderManager p_i46170_1_, final ModelHorse p_i46170_2_, final float p_i46170_3_) {
        super(p_i46170_1_, p_i46170_2_, p_i46170_3_);
    }
    
    protected void HorizonCode_Horizon_È(final EntityHorse p_180580_1_, final float p_180580_2_) {
        float var3 = 1.0f;
        final int var4 = p_180580_1_.ÐƒÇŽà();
        if (var4 == 1) {
            var3 *= 0.87f;
        }
        else if (var4 == 2) {
            var3 *= 0.92f;
        }
        GlStateManager.HorizonCode_Horizon_È(var3, var3, var3);
        super.HorizonCode_Horizon_È(p_180580_1_, p_180580_2_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityHorse p_180581_1_) {
        if (p_180581_1_.ÇŽØ­à()) {
            return this.Â(p_180581_1_);
        }
        switch (p_180581_1_.ÐƒÇŽà()) {
            default: {
                return RenderHorse.Âµá€;
            }
            case 1: {
                return RenderHorse.ÂµÈ;
            }
            case 2: {
                return RenderHorse.áˆºÑ¢Õ;
            }
            case 3: {
                return RenderHorse.á;
            }
            case 4: {
                return RenderHorse.ˆÏ­;
            }
        }
    }
    
    private ResourceLocation_1975012498 Â(final EntityHorse p_110848_1_) {
        final String var2 = p_110848_1_.ÐƒáŒŠÂµÐƒÕ();
        if (!p_110848_1_.ÇªÇªÉ()) {
            return null;
        }
        ResourceLocation_1975012498 var3 = RenderHorse.HorizonCode_Horizon_È.get(var2);
        if (var3 == null) {
            var3 = new ResourceLocation_1975012498(var2);
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(var3, new LayeredTexture(p_110848_1_.Ø­áƒ()));
            RenderHorse.HorizonCode_Horizon_È.put(var2, var3);
        }
        return var3;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityHorse)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityHorse)p_110775_1_);
    }
}
