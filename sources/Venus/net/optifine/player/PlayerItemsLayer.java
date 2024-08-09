/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.optifine.Config;
import net.optifine.player.PlayerConfigurations;

public class PlayerItemsLayer
extends LayerRenderer {
    private PlayerRenderer renderPlayer = null;

    public PlayerItemsLayer(PlayerRenderer playerRenderer) {
        super(playerRenderer);
        this.renderPlayer = playerRenderer;
    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.renderEquippedItems(entity2, matrixStack, iRenderTypeBuffer, n, OverlayTexture.NO_OVERLAY);
    }

    protected void renderEquippedItems(Entity entity2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        if (Config.isShowCapes() && !entity2.isInvisible() && entity2 instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)entity2;
            BipedModel bipedModel = (BipedModel)this.renderPlayer.getEntityModel();
            PlayerConfigurations.renderPlayerItems(bipedModel, abstractClientPlayerEntity, matrixStack, iRenderTypeBuffer, n, n2);
        }
    }

    public static void register(Map map) {
        Set set = map.keySet();
        boolean bl = false;
        for (Object k : set) {
            Object v = map.get(k);
            if (!(v instanceof PlayerRenderer)) continue;
            PlayerRenderer playerRenderer = (PlayerRenderer)v;
            playerRenderer.addLayer(new PlayerItemsLayer(playerRenderer));
            bl = true;
        }
        if (!bl) {
            Config.warn("PlayerItemsLayer not registered");
        }
    }
}

