package net.optifine.player;

import i.gishreloaded.protection.annotation.Native;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.optifine.Config;

import java.util.Map;
import java.util.Set;

public class PlayerItemsLayer extends LayerRenderer {
    private PlayerRenderer renderPlayer = null;

    public PlayerItemsLayer(PlayerRenderer renderPlayer) {
        super(renderPlayer);
        this.renderPlayer = renderPlayer;
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Entity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderEquippedItems(entitylivingbaseIn, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    protected void renderEquippedItems(Entity entityLiving, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, int packedOverlayIn) {
        if (Config.isShowCapes()) {
            if (!entityLiving.isInvisible()) {
                if (entityLiving instanceof AbstractClientPlayerEntity abstractclientplayerentity) {
                    BipedModel bipedmodel = this.renderPlayer.getEntityModel();
                    PlayerConfigurations.renderPlayerItems(bipedmodel, abstractclientplayerentity, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                }
            }
        }
    }

    @Native
    public static void register(Map renderPlayerMap) {
        Set set = renderPlayerMap.keySet();
        boolean flag = false;

        for (Object object : set) {
            Object object1 = renderPlayerMap.get(object);

            if (object1 instanceof PlayerRenderer playerrenderer) {
                playerrenderer.addLayer(new PlayerItemsLayer(playerrenderer));
                flag = true;
            }
        }

        if (!flag) {
            Config.warn("PlayerItemsLayer not registered");
        }
    }
}
