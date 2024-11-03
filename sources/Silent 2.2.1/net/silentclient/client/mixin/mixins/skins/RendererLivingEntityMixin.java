package net.silentclient.client.mixin.mixins.skins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.accessors.skins.PlayerEntityModelAccessor;
import net.silentclient.client.mods.render.skins.SkinsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public class RendererLivingEntityMixin<T extends EntityLivingBase> {

    @Inject(method = "renderModel", at = @At("TAIL"))
    protected void renderModelLayers(T p_renderModel_1_, float p_renderModel_2_, float p_renderModel_3_,
            float p_renderModel_4_, float p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, CallbackInfo info) {
        if(!Client.getInstance().getModInstances().getModByClass(SkinsMod.class).isEnabled()) {
            return;
        }
        if(!(this instanceof PlayerEntityModelAccessor)) {
            return;
        }
        boolean flag = !p_renderModel_1_.isInvisible();
        boolean flag1 = (!flag && !p_renderModel_1_.isInvisibleToPlayer((Minecraft.getMinecraft()).thePlayer));
        if (flag || flag1) {
            PlayerEntityModelAccessor playerRenderer = (PlayerEntityModelAccessor) this;
            if (flag1) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569F);
            }
            playerRenderer.client$getHeadLayer().doRenderLayer((AbstractClientPlayer) p_renderModel_1_, p_renderModel_2_, 0f, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
            playerRenderer.client$getBodyLayer().doRenderLayer((AbstractClientPlayer) p_renderModel_1_, p_renderModel_2_, 0f, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
            if (flag1) {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }
    }
    
    
}
