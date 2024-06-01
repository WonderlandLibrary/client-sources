package com.polarware.module.impl.render;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.Render3DEvent;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

import java.awt.*;

@ModuleInfo(name = "module.render.tracers.name", description = "module.render.tracers.description", category = Category.RENDER)
public final class TracersModule extends Module {

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (mc.gameSettings.hideGUI) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);

        for (final Entity player : Client.INSTANCE.getTargetComponent()) {
            if (player == mc.thePlayer || player.isDead || Client.INSTANCE.getBotComponent().contains(player)) {
                continue;
            }

            final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
            final double y = (player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks()) + 1.62F;
            final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

            final Color color = ColorUtil.withAlpha(
                    ColorUtil.mixColors(getTheme().getSecondColor(), getTheme().getFirstColor(), Math.min(1, mc.thePlayer.getDistanceToEntity(player) / 50)),
                128);

            RenderUtil.drawLine(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY + mc.thePlayer.getEyeHeight(), mc.getRenderManager().renderPosZ, x, y, z, color, 1.5F);
        }

        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    };
}