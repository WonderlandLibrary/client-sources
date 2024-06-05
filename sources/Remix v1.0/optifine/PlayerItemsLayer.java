package optifine;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;

public class PlayerItemsLayer implements LayerRenderer
{
    private RenderPlayer renderPlayer;
    
    public PlayerItemsLayer(final RenderPlayer renderPlayer) {
        this.renderPlayer = null;
        this.renderPlayer = renderPlayer;
    }
    
    public static void register(final Map renderPlayerMap) {
        final Set keys = renderPlayerMap.keySet();
        boolean registered = false;
        for (final Object key : keys) {
            final Object renderer = renderPlayerMap.get(key);
            if (renderer instanceof RenderPlayer) {
                final RenderPlayer renderPlayer = (RenderPlayer)renderer;
                renderPlayer.addLayer(new PlayerItemsLayer(renderPlayer));
                registered = true;
            }
        }
        if (!registered) {
            Config.warn("PlayerItemsLayer not registered");
        }
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLiving, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ticksExisted, final float headYaw, final float rotationPitch, final float scale) {
        this.renderEquippedItems(entityLiving, scale, partialTicks);
    }
    
    protected void renderEquippedItems(final EntityLivingBase entityLiving, final float scale, final float partialTicks) {
        if (Config.isShowCapes() && entityLiving instanceof AbstractClientPlayer) {
            final AbstractClientPlayer player = (AbstractClientPlayer)entityLiving;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableRescaleNormal();
            final ModelBiped modelBipedMain = (ModelBiped)this.renderPlayer.getMainModel();
            PlayerConfigurations.renderPlayerItems(modelBipedMain, player, scale, partialTicks);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
