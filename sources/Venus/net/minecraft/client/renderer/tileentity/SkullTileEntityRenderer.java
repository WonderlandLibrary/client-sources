/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.model.DragonHeadModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class SkullTileEntityRenderer
extends TileEntityRenderer<SkullTileEntity> {
    private static final Map<SkullBlock.ISkullType, GenericHeadModel> MODELS = Util.make(Maps.newHashMap(), SkullTileEntityRenderer::lambda$static$0);
    private static final Map<SkullBlock.ISkullType, ResourceLocation> SKINS = Util.make(Maps.newHashMap(), SkullTileEntityRenderer::lambda$static$1);

    public SkullTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(SkullTileEntity skullTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        float f2 = skullTileEntity.getAnimationProgress(f);
        BlockState blockState = skullTileEntity.getBlockState();
        boolean bl = blockState.getBlock() instanceof WallSkullBlock;
        Direction direction = bl ? blockState.get(WallSkullBlock.FACING) : null;
        float f3 = 22.5f * (float)(bl ? (2 + direction.getHorizontalIndex()) * 4 : blockState.get(SkullBlock.ROTATION));
        SkullTileEntityRenderer.render(direction, f3, ((AbstractSkullBlock)blockState.getBlock()).getSkullType(), skullTileEntity.getPlayerProfile(), f2, matrixStack, iRenderTypeBuffer, n);
    }

    public static void render(@Nullable Direction direction, float f, SkullBlock.ISkullType iSkullType, @Nullable GameProfile gameProfile, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        GenericHeadModel genericHeadModel = MODELS.get(iSkullType);
        matrixStack.push();
        if (direction == null) {
            matrixStack.translate(0.5, 0.0, 0.5);
        } else {
            float f3 = 0.25f;
            matrixStack.translate(0.5f - (float)direction.getXOffset() * 0.25f, 0.25, 0.5f - (float)direction.getZOffset() * 0.25f);
        }
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(SkullTileEntityRenderer.getRenderType(iSkullType, gameProfile));
        genericHeadModel.func_225603_a_(f2, f, 0.0f);
        genericHeadModel.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

    private static RenderType getRenderType(SkullBlock.ISkullType iSkullType, @Nullable GameProfile gameProfile) {
        ResourceLocation resourceLocation = SKINS.get(iSkullType);
        if (iSkullType == SkullBlock.Types.PLAYER && gameProfile != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(gameProfile);
            return map.containsKey((Object)MinecraftProfileTexture.Type.SKIN) ? RenderType.getEntityTranslucent(minecraft.getSkinManager().loadSkin(map.get((Object)MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN)) : RenderType.getEntityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(gameProfile)));
        }
        return RenderType.getEntityCutoutNoCullZOffset(resourceLocation);
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((SkullTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }

    private static void lambda$static$1(HashMap hashMap) {
        hashMap.put(SkullBlock.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
        hashMap.put(SkullBlock.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
        hashMap.put(SkullBlock.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
        hashMap.put(SkullBlock.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
        hashMap.put(SkullBlock.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
        hashMap.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultSkinLegacy());
    }

    private static void lambda$static$0(HashMap hashMap) {
        GenericHeadModel genericHeadModel = new GenericHeadModel(0, 0, 64, 32);
        HumanoidHeadModel humanoidHeadModel = new HumanoidHeadModel();
        DragonHeadModel dragonHeadModel = new DragonHeadModel(0.0f);
        hashMap.put(SkullBlock.Types.SKELETON, genericHeadModel);
        hashMap.put(SkullBlock.Types.WITHER_SKELETON, genericHeadModel);
        hashMap.put(SkullBlock.Types.PLAYER, humanoidHeadModel);
        hashMap.put(SkullBlock.Types.ZOMBIE, humanoidHeadModel);
        hashMap.put(SkullBlock.Types.CREEPER, genericHeadModel);
        hashMap.put(SkullBlock.Types.DRAGON, dragonHeadModel);
    }
}

