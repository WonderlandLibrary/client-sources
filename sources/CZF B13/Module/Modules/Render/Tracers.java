package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Combat.AntiBot;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Tracers extends Module {
    public Tracers() {
        super("Tracers", new String[]{"lines", "tracer"}, ModuleType.Render);
        this.setColor(new Color(60, 136, 166).getRGB());
    }

    @EventHandler
    private void on3DRender(EventRender3D e) {
        for (Object o : mc.theWorld.loadedEntityList) {
            double[] arrd;
            Entity entity = (Entity) o;
            double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) e.getPartialTicks()
                    - RenderManager.renderPosX;
            double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) e.getPartialTicks()
                    - RenderManager.renderPosY;
            double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) e.getPartialTicks()
                    - RenderManager.renderPosZ;
            boolean old = mc.gameSettings.viewBobbing;
            RenderUtil.startDrawing();
            mc.gameSettings.viewBobbing = false;
            mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
            mc.gameSettings.viewBobbing = old;
            float color = (float) Math.round(255.0 - mc.thePlayer.getDistanceSqToEntity(entity) * 255.0
                    / MathUtil.square((double) mc.gameSettings.renderDistanceChunks * 2.5)) / 255.0f;
            if (FriendManager.isFriend(entity.getName())) {
                double[] arrd2 = new double[3];
                arrd2[0] = 0.0;
                arrd2[1] = 1.0;
                arrd = arrd2;
                arrd2[2] = 1.0;
            } else {
                double[] arrd3 = new double[3];
                arrd3[0] = color;
                arrd3[1] = 1.0f - color;
                arrd = arrd3;
                arrd3[2] = 0.0;
            }
            this.drawLine(entity, arrd, posX, posY, posZ);
            RenderUtil.stopDrawing();
        }
    }

    private void drawLine(Entity entity, double[] color, double x, double y, double z) {
        if (AntiBot.isServerBot(entity) || !(entity instanceof EntityPlayer) || entity == mc.thePlayer) {
            return;
        }
        float distance = mc.thePlayer.getDistanceToEntity(entity);
        float xD = distance / 48.0f;
        if (xD >= 1.0f) {
            xD = 1.0f;
        }
        boolean entityesp = false;
        GL11.glEnable(2848);
        if (color.length >= 4) {
            if (color[3] <= 0.1) {
                return;
            }
            GL11.glColor4d(color[0], color[1], color[2], color[3]);
        } else {
            GL11.glColor3d(color[0], color[1], color[2]);
        }
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0, mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(2848);
    }
}
