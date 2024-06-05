package optfine;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import java.util.*;
import java.lang.reflect.*;

public class RenderPlayerOF extends RenderPlayer
{
    public RenderPlayerOF(final RenderManager p_i62_1_, final boolean p_i62_2_) {
        super(p_i62_1_, p_i62_2_);
    }
    
    @Override
    protected void renderLayers(final AbstractClientPlayer entitylivingbaseIn, final float p_177093_2_, final float p_177093_3_, final float partialTicks, final float p_177093_5_, final float p_177093_6_, final float p_177093_7_, final float p_177093_8_) {
        super.renderLayers(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
        this.renderEquippedItems(entitylivingbaseIn, p_177093_8_, partialTicks);
    }
    
    protected void renderEquippedItems(final EntityLivingBase p_renderEquippedItems_1_, final float p_renderEquippedItems_2_, final float p_renderEquippedItems_3_) {
        if (p_renderEquippedItems_1_ instanceof AbstractClientPlayer) {
            final AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_renderEquippedItems_1_;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableRescaleNormal();
            final ModelBiped modelbiped = (ModelBiped)this.mainModel;
            PlayerConfigurations.renderPlayerItems(modelbiped, abstractclientplayer, p_renderEquippedItems_2_, p_renderEquippedItems_3_);
        }
    }
    
    public static void register() {
        final RenderManager rendermanager = Config.getMinecraft().getRenderManager();
        final Map map = getMapRenderTypes(rendermanager);
        if (map == null) {
            Config.warn("RenderPlayerOF init() failed: RenderManager.MapRenderTypes not found");
        }
        else {
            map.put("default", new RenderPlayerOF(rendermanager, false));
            map.put("slim", new RenderPlayerOF(rendermanager, true));
        }
    }
    
    private static Map getMapRenderTypes(final RenderManager p_getMapRenderTypes_0_) {
        try {
            final Field[] afield = Reflector.getFields(RenderManager.class, Map.class);
            for (int i = 0; i < afield.length; ++i) {
                final Field field = afield[i];
                final Map map = (Map)field.get(p_getMapRenderTypes_0_);
                if (map != null) {
                    final Object object = map.get("default");
                    if (object instanceof RenderPlayer) {
                        return map;
                    }
                }
            }
            return null;
        }
        catch (Exception exception) {
            Config.warn("Error getting RenderManager.mapRenderTypes");
            Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
            return null;
        }
    }
}
