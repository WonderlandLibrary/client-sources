package com.client.glowclient.sponge.mixin;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ LayerArmorBase.class })
public abstract class MixinLayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase>
{
    @Shadow
    @Final
    protected static ResourceLocation field_177188_b;
    
    public MixinLayerArmorBase() {
        super();
    }
    
    @Overwrite
    public static void renderEnchantedGlint(final RenderLivingBase<?> renderLivingBase, final EntityLivingBase entityLivingBase, final ModelBase modelBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final float n8 = entityLivingBase.ticksExisted + n3;
        renderLivingBase.bindTexture(MixinLayerArmorBase.ENCHANTED_ITEM_GLINT_RES);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
            if (HookTranslator.m23()) {
                if (HookTranslator.m24()) {
                    GlStateManager.color(HookTranslator.m25() / 255.0f, HookTranslator.m26() / 255.0f, HookTranslator.m27() / 255.0f, 1.0f);
                }
                else if (HookTranslator.m34()) {
                    GlStateManager.color(HookTranslator.m28() / 255.0f, HookTranslator.m29() / 255.0f, HookTranslator.m30() / 255.0f, 1.0f);
                }
                else if (HookTranslator.m35()) {
                    GlStateManager.color(HookTranslator.m31() / 255.0f, HookTranslator.m32() / 255.0f, HookTranslator.m33() / 255.0f, 1.0f);
                }
            }
            else {
                GlStateManager.color(0.38f, 0.19f, 0.608f, 1.0f);
            }
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.scale(0.33333334f, 0.33333334f, 0.33333334f);
            GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, n8 * (0.001f + i * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(5888);
            modelBase.render((Entity)entityLivingBase, n, n2, n4, n5, n6, n7);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
    }
}
