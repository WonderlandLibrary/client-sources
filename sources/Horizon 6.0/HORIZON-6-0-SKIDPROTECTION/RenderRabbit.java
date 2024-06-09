package HORIZON-6-0-SKIDPROTECTION;

public class RenderRabbit extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private static final ResourceLocation_1975012498 á;
    private static final ResourceLocation_1975012498 ˆÏ­;
    private static final ResourceLocation_1975012498 £á;
    private static final ResourceLocation_1975012498 Å;
    private static final String £à = "CL_00002432";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/rabbit/brown.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/rabbit/white.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/rabbit/black.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/rabbit/gold.png");
        á = new ResourceLocation_1975012498("textures/entity/rabbit/salt.png");
        ˆÏ­ = new ResourceLocation_1975012498("textures/entity/rabbit/white_splotched.png");
        £á = new ResourceLocation_1975012498("textures/entity/rabbit/toast.png");
        Å = new ResourceLocation_1975012498("textures/entity/rabbit/caerbannog.png");
    }
    
    public RenderRabbit(final RenderManager p_i46146_1_, final ModelBase p_i46146_2_, final float p_i46146_3_) {
        super(p_i46146_1_, p_i46146_2_, p_i46146_3_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityRabbit p_177125_1_) {
        final String var2 = EnumChatFormatting.HorizonCode_Horizon_È(p_177125_1_.v_());
        if (var2 != null && var2.equals("Toast")) {
            return RenderRabbit.£á;
        }
        switch (p_177125_1_.ÐƒÓ()) {
            default: {
                return RenderRabbit.HorizonCode_Horizon_È;
            }
            case 1: {
                return RenderRabbit.Âµá€;
            }
            case 2: {
                return RenderRabbit.áˆºÑ¢Õ;
            }
            case 3: {
                return RenderRabbit.ˆÏ­;
            }
            case 4: {
                return RenderRabbit.ÂµÈ;
            }
            case 5: {
                return RenderRabbit.á;
            }
            case 99: {
                return RenderRabbit.Å;
            }
        }
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityRabbit)p_110775_1_);
    }
}
