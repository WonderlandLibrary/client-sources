/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventCancelOverlay;
import mpp.venusfr.venusfr;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.apache.commons.lang3.tuple.Pair;

public class OverlayRenderer {
    private static final ResourceLocation TEXTURE_UNDERWATER = new ResourceLocation("textures/misc/underwater.png");

    public static void renderOverlays(Minecraft minecraft, MatrixStack matrixStack) {
        RenderSystem.disableAlphaTest();
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
        if (!clientPlayerEntity.noClip) {
            if (Reflector.ForgeEventFactory_renderBlockOverlay.exists() && Reflector.ForgeBlockModelShapes_getTexture3.exists()) {
                Object object;
                Pair<BlockState, BlockPos> pair = OverlayRenderer.getOverlayBlock(clientPlayerEntity);
                if (pair != null && !Reflector.ForgeEventFactory_renderBlockOverlay.callBoolean(clientPlayerEntity, matrixStack, object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK), pair.getLeft(), pair.getRight())) {
                    TextureAtlasSprite textureAtlasSprite = (TextureAtlasSprite)Reflector.call(minecraft.getBlockRendererDispatcher().getBlockModelShapes(), Reflector.ForgeBlockModelShapes_getTexture3, pair.getLeft(), minecraft.world, pair.getRight());
                    OverlayRenderer.renderTexture(minecraft, textureAtlasSprite, matrixStack);
                }
            } else {
                BlockState blockState = OverlayRenderer.getViewBlockingState(clientPlayerEntity);
                if (blockState != null) {
                    OverlayRenderer.renderTexture(minecraft, minecraft.getBlockRendererDispatcher().getBlockModelShapes().getTexture(blockState), matrixStack);
                }
            }
        }
        if (!minecraft.player.isSpectator()) {
            if (minecraft.player.areEyesInFluid(FluidTags.WATER) && !Reflector.ForgeEventFactory_renderWaterOverlay.callBoolean(clientPlayerEntity, matrixStack)) {
                OverlayRenderer.renderUnderwater(minecraft, matrixStack);
            }
            if (minecraft.player.isBurning() && !Reflector.ForgeEventFactory_renderFireOverlay.callBoolean(clientPlayerEntity, matrixStack)) {
                OverlayRenderer.renderFire(minecraft, matrixStack);
            }
        }
        RenderSystem.enableAlphaTest();
    }

    @Nullable
    private static BlockState getViewBlockingState(PlayerEntity playerEntity) {
        Pair<BlockState, BlockPos> pair = OverlayRenderer.getOverlayBlock(playerEntity);
        return pair == null ? null : pair.getLeft();
    }

    private static Pair<BlockState, BlockPos> getOverlayBlock(PlayerEntity playerEntity) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 8; ++i) {
            double d = playerEntity.getPosX() + (double)(((float)((i >> 0) % 2) - 0.5f) * playerEntity.getWidth() * 0.8f);
            double d2 = playerEntity.getPosYEye() + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
            double d3 = playerEntity.getPosZ() + (double)(((float)((i >> 2) % 2) - 0.5f) * playerEntity.getWidth() * 0.8f);
            mutable.setPos(d, d2, d3);
            BlockState blockState = playerEntity.world.getBlockState(mutable);
            if (blockState.getRenderType() == BlockRenderType.INVISIBLE || !blockState.causesSuffocation(playerEntity.world, mutable)) continue;
            return Pair.of(blockState, mutable.toImmutable());
        }
        return null;
    }

    private static void renderTexture(Minecraft minecraft, TextureAtlasSprite textureAtlasSprite, MatrixStack matrixStack) {
        if (SmartAnimations.isActive()) {
            SmartAnimations.spriteRendered(textureAtlasSprite);
        }
        minecraft.getTextureManager().bindTexture(textureAtlasSprite.getAtlasTexture().getTextureLocation());
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float f = 0.1f;
        float f2 = -1.0f;
        float f3 = 1.0f;
        float f4 = -1.0f;
        float f5 = 1.0f;
        float f6 = -0.5f;
        float f7 = textureAtlasSprite.getMinU();
        float f8 = textureAtlasSprite.getMaxU();
        float f9 = textureAtlasSprite.getMinV();
        float f10 = textureAtlasSprite.getMaxV();
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferBuilder.pos(matrix4f, -1.0f, -1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).tex(f8, f10).endVertex();
        bufferBuilder.pos(matrix4f, 1.0f, -1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).tex(f7, f10).endVertex();
        bufferBuilder.pos(matrix4f, 1.0f, 1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).tex(f7, f9).endVertex();
        bufferBuilder.pos(matrix4f, -1.0f, 1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).tex(f8, f9).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
    }

    private static void renderUnderwater(Minecraft minecraft, MatrixStack matrixStack) {
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            RenderSystem.enableTexture();
            minecraft.getTextureManager().bindTexture(TEXTURE_UNDERWATER);
            if (SmartAnimations.isActive()) {
                SmartAnimations.textureRendered(minecraft.getTextureManager().getTexture(TEXTURE_UNDERWATER).getGlTextureId());
            }
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            float f = minecraft.player.getBrightness();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float f2 = 4.0f;
            float f3 = -1.0f;
            float f4 = 1.0f;
            float f5 = -1.0f;
            float f6 = 1.0f;
            float f7 = -0.5f;
            float f8 = -minecraft.player.rotationYaw / 64.0f;
            float f9 = minecraft.player.rotationPitch / 64.0f;
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            bufferBuilder.pos(matrix4f, -1.0f, -1.0f, -0.5f).color(f, f, f, 0.1f).tex(4.0f + f8, 4.0f + f9).endVertex();
            bufferBuilder.pos(matrix4f, 1.0f, -1.0f, -0.5f).color(f, f, f, 0.1f).tex(0.0f + f8, 4.0f + f9).endVertex();
            bufferBuilder.pos(matrix4f, 1.0f, 1.0f, -0.5f).color(f, f, f, 0.1f).tex(0.0f + f8, 0.0f + f9).endVertex();
            bufferBuilder.pos(matrix4f, -1.0f, 1.0f, -0.5f).color(f, f, f, 0.1f).tex(4.0f + f8, 0.0f + f9).endVertex();
            bufferBuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferBuilder);
            RenderSystem.disableBlend();
        }
    }

    private static void renderFire(Minecraft minecraft, MatrixStack matrixStack) {
        EventCancelOverlay eventCancelOverlay = new EventCancelOverlay(EventCancelOverlay.Overlays.FIRE_OVERLAY);
        venusfr.getInstance().getEventBus().post(eventCancelOverlay);
        if (eventCancelOverlay.isCancel()) {
            eventCancelOverlay.open();
            return;
        }
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();
        TextureAtlasSprite textureAtlasSprite = ModelBakery.LOCATION_FIRE_1.getSprite();
        if (SmartAnimations.isActive()) {
            SmartAnimations.spriteRendered(textureAtlasSprite);
        }
        minecraft.getTextureManager().bindTexture(textureAtlasSprite.getAtlasTexture().getTextureLocation());
        float f = textureAtlasSprite.getMinU();
        float f2 = textureAtlasSprite.getMaxU();
        float f3 = (f + f2) / 2.0f;
        float f4 = textureAtlasSprite.getMinV();
        float f5 = textureAtlasSprite.getMaxV();
        float f6 = (f4 + f5) / 2.0f;
        float f7 = textureAtlasSprite.getUvShrinkRatio();
        float f8 = MathHelper.lerp(f7, f, f3);
        float f9 = MathHelper.lerp(f7, f2, f3);
        float f10 = MathHelper.lerp(f7, f4, f6);
        float f11 = MathHelper.lerp(f7, f5, f6);
        float f12 = 1.0f;
        for (int i = 0; i < 2; ++i) {
            matrixStack.push();
            float f13 = -0.5f;
            float f14 = 0.5f;
            float f15 = -0.5f;
            float f16 = 0.5f;
            float f17 = -0.5f;
            matrixStack.translate((float)(-(i * 2 - 1)) * 0.24f, -0.3f, 0.0);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)(i * 2 - 1) * 10.0f));
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            bufferBuilder.pos(matrix4f, -0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).tex(f9, f11).endVertex();
            bufferBuilder.pos(matrix4f, 0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).tex(f8, f11).endVertex();
            bufferBuilder.pos(matrix4f, 0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).tex(f8, f10).endVertex();
            bufferBuilder.pos(matrix4f, -0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).tex(f9, f10).endVertex();
            bufferBuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferBuilder);
            matrixStack.pop();
        }
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
    }
}

