package com.client.glowclient.sponge.mixin;

import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.client.renderer.texture.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ RenderItem.class })
public abstract class MixinRenderItem
{
    @Shadow
    private TextureManager field_175057_n;
    @Shadow
    private static final ResourceLocation field_110798_h;
    
    public MixinRenderItem() {
        super();
    }
    
    @Shadow
    private void renderModel(final IBakedModel bakedModel, final int n) {
    }
    
    @Overwrite
    private void renderEffect(final IBakedModel bakedModel) {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        this.textureManager.bindTexture(MixinRenderItem.RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        GlStateManager.translate(Minecraft.getSystemTime() % 3000L / 3000.0f / 8.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f);
        if (HookTranslator.m23()) {
            if (HookTranslator.m24()) {
                this.renderModel(bakedModel, HookTranslator.m46());
            }
            else if (HookTranslator.m34()) {
                this.renderModel(bakedModel, HookTranslator.m47());
            }
            else if (HookTranslator.m35()) {
                this.renderModel(bakedModel, HookTranslator.m48());
            }
        }
        else {
            this.renderModel(bakedModel, -8372020);
        }
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        GlStateManager.translate(-(Minecraft.getSystemTime() % 4873L / 4873.0f / 8.0f), 0.0f, 0.0f);
        GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f);
        if (HookTranslator.m23()) {
            if (HookTranslator.m24()) {
                this.renderModel(bakedModel, HookTranslator.m46());
            }
            else if (HookTranslator.m34()) {
                this.renderModel(bakedModel, HookTranslator.m47());
            }
            else if (HookTranslator.m35()) {
                this.renderModel(bakedModel, HookTranslator.m48());
            }
        }
        else {
            this.renderModel(bakedModel, -8372020);
        }
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }
    
    static {
        MixinRenderItem.RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
