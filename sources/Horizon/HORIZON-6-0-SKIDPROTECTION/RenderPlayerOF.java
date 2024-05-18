package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Field;
import java.util.Map;

public class RenderPlayerOF extends RenderPlayer
{
    public RenderPlayerOF(final RenderManager renderManager, final boolean slimArms) {
        super(renderManager, slimArms);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase entityLiving, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ticksExisted, final float headYaw, final float rotationPitch, final float scale) {
        super.HorizonCode_Horizon_È(entityLiving, limbSwing, limbSwingAmount, partialTicks, ticksExisted, headYaw, rotationPitch, scale);
        this.Â(entityLiving, scale, partialTicks);
    }
    
    protected void Â(final EntityLivingBase entityLiving, final float scale, final float partialTicks) {
        if (entityLiving instanceof AbstractClientPlayer) {
            final AbstractClientPlayer player = (AbstractClientPlayer)entityLiving;
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ñ¢á();
            final ModelBiped modelBipedMain = (ModelBiped)this.Ó;
            PlayerConfigurations.HorizonCode_Horizon_È(modelBipedMain, player, scale, partialTicks);
        }
    }
    
    public static void Ø() {
        final RenderManager rm = Config.È().ÇªÓ();
        final Map mapRenderTypes = HorizonCode_Horizon_È(rm);
        if (mapRenderTypes == null) {
            Config.Â("RenderPlayerOF init() failed: RenderManager.MapRenderTypes not found");
        }
        else {
            mapRenderTypes.put("default", new RenderPlayerOF(rm, false));
            mapRenderTypes.put("slim", new RenderPlayerOF(rm, true));
        }
    }
    
    private static Map HorizonCode_Horizon_È(final RenderManager rm) {
        try {
            final Field[] e = Reflector.Â(RenderManager.class, Map.class);
            for (int i = 0; i < e.length; ++i) {
                final Field field = e[i];
                final Map map = (Map)field.get(rm);
                if (map != null) {
                    final Object renderSteve = map.get("default");
                    if (renderSteve instanceof RenderPlayer) {
                        return map;
                    }
                }
            }
            return null;
        }
        catch (Exception var6) {
            Config.Â("Error getting RenderManager.mapRenderTypes");
            Config.Â(String.valueOf(var6.getClass().getName()) + ": " + var6.getMessage());
            return null;
        }
    }
}
