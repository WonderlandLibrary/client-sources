package com.alan.clients.component.impl.render.espcomponent.impl;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.component.impl.render.espcomponent.api.ESP;
import com.alan.clients.component.impl.render.espcomponent.api.ESPColor;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.render.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

import static com.alan.clients.layer.Layers.BLOOM;

public class PlayerGlow extends ESP implements Accessor {

    public PlayerGlow(ESPColor espColor) {
        super(espColor);
    }

    @Override
    public void render3D() {
        getLayer(BLOOM).add(() -> {
            final float partialTicks = mc.timer.renderPartialTicks;
            for (final Entity player : TargetComponent.getTargets()) {
                final Render<EntityPlayer> render = mc.getRenderManager().getEntityRenderObject(player);

                if (mc.getRenderManager() == null || !(player instanceof EntityPlayer) || render == null || !RenderUtil.isInViewFrustrum(player)) {
                    continue;
                }

                final Color color = ((EntityPlayer) player).hurtTime > 0 ? Color.RED : this.getColor((EntityPlayer) player);

                if (color.getAlpha() <= 0) {
                    continue;
                }

                final double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
                final double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
                final double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
                final float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;

                boolean invisible = player.isInvisible();
                player.setInvisible(false);
                RendererLivingEntity.setShaderBrightness(color);
                render.doRender((EntityPlayer) player, x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ, yaw, partialTicks);
                RendererLivingEntity.unsetShaderBrightness();
                player.setInvisible(invisible);
            }

            RenderHelper.disableStandardItemLighting();
            mc.entityRenderer.disableLightmap();
        });
    }
}
