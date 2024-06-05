package client.metaware.impl.module.render;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.render.Render3DEvent;
import client.metaware.impl.module.combat.KillAura;
import client.metaware.impl.utils.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(name = "PredictMotion", renderName = "Predict Motion", category = Category.VISUALS)
public class PredictMotion extends Module {

    @EventHandler
    private final Listener<Render3DEvent> eventListener = event -> {
        KillAura killAura = Metaware.INSTANCE.getModuleManager().getModuleByClass(KillAura.class);
        if (killAura.target == null) {
            return;
        }
        final EntityLivingBase player = killAura.target;
        GL11.glPushMatrix();
        RenderUtil.pre3D();
        mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
        final double x = player.prevPosX + (player.posX - player.prevPosX) * event.getPartialTicks() - RenderManager.renderPosX;
        final double y = player.prevPosY + (player.posY - player.prevPosY) * event.getPartialTicks() - RenderManager.renderPosY;
        final double z = player.prevPosZ + (player.posZ - player.prevPosZ) * event.getPartialTicks() - RenderManager.renderPosZ;
        final double xDelta = player.posX - player.prevPosX;
        final double yDelta = player.posY - player.prevPosY;
        final double zDelta = player.posZ - player.prevPosZ;
        double yMotion = 0.0;
        final double initVel = mc.thePlayer.motionY;
        for (int i = 5; i < 6; ++i) {
            yMotion += initVel - 0.002 * i;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + xDelta * i, y + (yDelta + yMotion) * i, z + zDelta * i);
            RenderUtil.drawPlatform(player, new Color(0, 255, 88, 75));
            GlStateManager.popMatrix();
        }
        RenderUtil.post3D();
        GL11.glPopMatrix();  
    };
}
