/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.awt.Dimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.optifine.player.PlayerItemRenderer;

public class PlayerItemModel {
    private Dimension textureSize = null;
    private boolean usePlayerTexture = false;
    private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
    private ResourceLocation textureLocation = null;
    private NativeImage textureImage = null;
    private DynamicTexture texture = null;
    private ResourceLocation locationMissing = new ResourceLocation("textures/block/red_wool.png");
    public static final int ATTACH_BODY = 0;
    public static final int ATTACH_HEAD = 1;
    public static final int ATTACH_LEFT_ARM = 2;
    public static final int ATTACH_RIGHT_ARM = 3;
    public static final int ATTACH_LEFT_LEG = 4;
    public static final int ATTACH_RIGHT_LEG = 5;
    public static final int ATTACH_CAPE = 6;

    public PlayerItemModel(Dimension dimension, boolean bl, PlayerItemRenderer[] playerItemRendererArray) {
        this.textureSize = dimension;
        this.usePlayerTexture = bl;
        this.modelRenderers = playerItemRendererArray;
    }

    public void render(BipedModel bipedModel, AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        ResourceLocation resourceLocation = this.locationMissing;
        if (this.usePlayerTexture) {
            resourceLocation = abstractClientPlayerEntity.getLocationSkin();
        } else if (this.textureLocation != null) {
            if (this.texture == null && this.textureImage != null) {
                this.texture = new DynamicTexture(this.textureImage);
                Minecraft.getInstance().getTextureManager().loadTexture(this.textureLocation, this.texture);
            }
            resourceLocation = this.textureLocation;
        } else {
            resourceLocation = this.locationMissing;
        }
        for (int i = 0; i < this.modelRenderers.length; ++i) {
            PlayerItemRenderer playerItemRenderer = this.modelRenderers[i];
            matrixStack.push();
            RenderType renderType = RenderType.getEntityCutoutNoCull(resourceLocation);
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(renderType);
            playerItemRenderer.render(bipedModel, matrixStack, iVertexBuilder, n, n2);
            matrixStack.pop();
        }
    }

    public static ModelRenderer getAttachModel(BipedModel bipedModel, int n) {
        switch (n) {
            case 0: {
                return bipedModel.bipedBody;
            }
            case 1: {
                return bipedModel.bipedHead;
            }
            case 2: {
                return bipedModel.bipedLeftArm;
            }
            case 3: {
                return bipedModel.bipedRightArm;
            }
            case 4: {
                return bipedModel.bipedLeftLeg;
            }
            case 5: {
                return bipedModel.bipedRightLeg;
            }
        }
        return null;
    }

    public NativeImage getTextureImage() {
        return this.textureImage;
    }

    public void setTextureImage(NativeImage nativeImage) {
        this.textureImage = nativeImage;
    }

    public DynamicTexture getTexture() {
        return this.texture;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public void setTextureLocation(ResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
    }

    public boolean isUsePlayerTexture() {
        return this.usePlayerTexture;
    }
}

