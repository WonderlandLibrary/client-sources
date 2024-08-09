package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render3DPosedLastEvent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.AntiBot;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.mojang.blaze3d.systems.RenderSystem;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(name = "Tracers", description = "Отображает линии до сущностей", category = Category.RENDER)
public class Tracers extends Module implements IRenderAccess {
    private final BooleanValue ignoreNaked = new BooleanValue("Игнорировать голых", this, true);

    private final Listener<Render3DPosedLastEvent> onRender3D = event -> {
        glPushMatrix();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glEnable(GL_LINE_SMOOTH);

        glLineWidth(1);

        Vector3d cam = new Vector3d(0, 0, 150)
                .rotatePitch((float) -(Math.toRadians(mc.getRenderManager().info.getPitch())))
                .rotateYaw((float) -Math.toRadians(mc.getRenderManager().info.getYaw()));

        for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;
            if (!player.isAlive()
                    || AntiBot.contains(player)
                    || player.getTotalArmorValue() == 0.0f && ignoreNaked.getValue()) continue;

            Vector3d pos = getInterpolatedPositionVec(player).subtract(mc.getRenderManager().info.getProjectedView());

            float[] rgba = ColorUtil.getRGBAf(excellent.getFriendManager().isFriend(player.getGameProfile().getName()) ? ColorUtil.getColor(25, 227, 142) : getTheme().getClientColor(0));
            RenderSystem.color4f(rgba[0], rgba[1], rgba[2], rgba[3]);
            BUFFER.begin(1, DefaultVertexFormats.POSITION);

            BUFFER.pos(cam.x, cam.y, cam.z).endVertex();
            BUFFER.pos(pos.x, pos.y, pos.z).endVertex();


            TESSELLATOR.draw();
        }

        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        glPopMatrix();
    };

    public Vector3d getPrevPositionVec(final Entity entity) {
        return new Vector3d(
                entity.prevPosX,
                entity.prevPosY,
                entity.prevPosZ
        );
    }

    public Vector3d getInterpolatedPositionVec(final Entity entity) {
        final Vector3d prev = getPrevPositionVec(entity);
        return prev.add(entity.getPositionVec()
                .subtract(prev)
                .scale(mc.getRenderPartialTicks())
        );
    }
}
