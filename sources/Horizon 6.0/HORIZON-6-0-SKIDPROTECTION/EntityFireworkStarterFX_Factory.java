package HORIZON-6-0-SKIDPROTECTION;

public class EntityFireworkStarterFX_Factory implements IParticleFactory
{
    private static final String HorizonCode_Horizon_È = "CL_00002603";
    
    @Override
    public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
        final EntityFireworkSparkFX var16 = new EntityFireworkSparkFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, Minecraft.áŒŠà().Å);
        var16.Âµá€(0.99f);
        return var16;
    }
}
