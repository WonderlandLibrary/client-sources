/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.opengl.GL11
 *  org.newdawn.slick.particles.Particle
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.Render3DEvent;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.particles.Particle;

public class DamageParticles
extends Module {
    private final List<Particle> particles = new ArrayList<Particle>();

    public DamageParticles() {
        super("DamageParticles", "Sets the hit particle to the health reduced from the player", 0, Category.Render);
    }

    @Subscribe
    public void onRender(Render3DEvent e) {
        for (Entity entity : Minecraft.theWorld.getLoadedEntityList()) {
            if (!(entity instanceof EntityCrit2FX)) continue;
            EntityCritFX p = (EntityCritFX)entity;
            double x = p.getPosition().getX();
            double n = x - DamageParticles.mc.getRenderManager().viewerPosX;
            double y = p.getPosition().getY();
            double n2 = y - DamageParticles.mc.getRenderManager().viewerPosY;
            double z = p.getPosition().getZ();
            double n3 = z - DamageParticles.mc.getRenderManager().viewerPosZ;
            GlStateManager.pushMatrix();
            GlStateManager.enablePolygonOffset();
            GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
            GlStateManager.translate((float)n, (float)n2, (float)n3);
            GlStateManager.rotate(-DamageParticles.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            float textY = DamageParticles.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
            GlStateManager.rotate(DamageParticles.mc.getRenderManager().playerViewX, textY, 0.0f, 0.0f);
            double size = 0.03;
            GlStateManager.scale(-0.03, -0.03, 0.03);
            RenderUtils.enableGL2D();
            RenderUtils.disableGL2D();
            GL11.glDepthMask((boolean)false);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDepthMask((boolean)true);
            GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
            GlStateManager.disablePolygonOffset();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}

