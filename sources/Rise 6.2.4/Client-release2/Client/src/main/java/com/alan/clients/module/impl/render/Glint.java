package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.GlintEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.BoundsNumberValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.src.Config;
import net.optifine.CustomItems;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;

import java.awt.*;

@ModuleInfo(aliases = {"module.render.glint.name"}, description = "module.render.glint.description", category = Category.RENDER)
public final class Glint extends Module {

    private final BooleanValue glintWeapons = new BooleanValue("Glint Weapons", this, true);
    private final BoundsNumberValue hue = new BoundsNumberValue("Hue", this, 0, 360, 0, 360, 1);
    private final NumberValue layers = new NumberValue("Layers", this, 4, 1, 8, 1);
//    private final BooleanValue glow = new BooleanValue("Glow", this, true);

    @EventLink
    public final Listener<GlintEvent> onGlint = event -> {
        final ItemStack itemStack = event.getItemStack();
        final Item item = itemStack.getItem();

        if (this.glintWeapons.getValue() && (item instanceof ItemSword || item instanceof ItemAxe)) {
            event.setEnchanted(true);
        }

        event.setCancelled();

        if (event.isEnchanted() && event.isRender()) {
            this.renderEffect(event.getModel());
        }
    };

    public void renderEffect(final IBakedModel model) {
        if (RendererLivingEntity.SHADER_RENDERING) return;

        if (!Config.isCustomItems() || CustomItems.isUseGlint()) {
            if (!Config.isShaders() || !Shaders.isShadowPass) {
                GlStateManager.depthMask(false);
                GlStateManager.depthFunc(514);
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(768, 1);
                mc.getRenderItem().textureManager.bindTexture(RenderItem.RES_ITEM_GLINT);

                if (Config.isShaders() && !mc.getRenderItem().renderItemGui) {
                    ShadersRender.renderEnchantedGlintBegin();
                }

                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();

                GlStateManager.scale(8.0F, 8.0F, 8.0F);
                final float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
                GlStateManager.translate(f, 0.0F, 0.0F);

                for (int layer = 1; layer <= layers.getValue().intValue(); layer++) {
                    GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
                    mc.getRenderItem().renderModel(model,
                            new Color(Color.HSBtoRGB((hue.getValue().intValue() +
                                    Math.abs(this.hue.getSecondValue().intValue() -
                                            hue.getValue().intValue()) * (layer /
                                            layers.getValue().floatValue())) / 255, 1, 1)).
                                    hashCode());
                }

                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.enableLighting();
                GlStateManager.depthFunc(515);
                GlStateManager.depthMask(true);
                mc.getRenderItem().textureManager.bindTexture(TextureMap.locationBlocksTexture);

                if (Config.isShaders() && !mc.getRenderItem().renderItemGui) {
                    ShadersRender.renderEnchantedGlintEnd();
                }
            }
        }
    }

    @EventLink
    public final Listener<Render3DEvent> onRender3D = event -> {
        // Removed because idk how to fix
//        if (this.glow.getValue()) {
//
//            Runnable runnable = () -> {
//                RendererLivingEntity.setShaderBrightness(this.getTheme().getFirstColor());
//                mc.entityRenderer.renderHand(mc.timer.renderPartialTicks, 2, true, true, true);
//                RendererLivingEntity.unsetShaderBrightness();
//            };
//
//            getLayer(BASE_BLOOM).add(runnable);
//            NORMAL_OUTLINE_RUNNABLES.add(runnable);
//        }
    };
}
