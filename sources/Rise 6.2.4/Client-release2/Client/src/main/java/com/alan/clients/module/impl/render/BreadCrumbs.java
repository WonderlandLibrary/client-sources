package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ModuleInfo(aliases = {"module.render.breadcrumbs.name"}, description = "module.render.breadcrumbs.description", category = Category.RENDER)
public class BreadCrumbs extends Module {
    private final List<TimedVec3> path = new ArrayList<>();
    private final NumberValue timeout = new NumberValue("Time", this, 15, 1, 150, 0.1);

    @EventLink
    public final Listener<PreMotionEvent> eventListener = event -> {
        if (mc.thePlayer.lastTickPosX != mc.thePlayer.posX || mc.thePlayer.lastTickPosY != mc.thePlayer.posY || mc.thePlayer.lastTickPosZ != mc.thePlayer.posZ) {
            path.add(new TimedVec3(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), System.currentTimeMillis()));
        }

        long currentTime = System.currentTimeMillis();
        path.removeIf(timedVec -> currentTime - timedVec.time > (timeout.getValue().intValue() * 100L));
    };

    @EventLink
    public final Listener<Render3DEvent> render3DEventListener = event -> {
        renderBreadCrumbs(path);
    };

    @Override
    public void onEnable() {
        path.clear();
    }

    public void renderBreadCrumbs(List<TimedVec3> vec3s) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glLineWidth(2.0f);

        long currentTime = System.currentTimeMillis();

        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < vec3s.size(); i++) {
            TimedVec3 timedVec = vec3s.get(i);
            Vec3 v = timedVec.vec;

            double x = v.xCoord - RenderManager.renderPosX;
            double y = v.yCoord - RenderManager.renderPosY;
            double z = v.zCoord - RenderManager.renderPosZ;

            float alpha = 1.0f - (float) (currentTime - timedVec.time) / (float) (timeout.getValue().intValue() * 100);
            alpha = Math.max(0.0f, Math.min(1.0f, alpha));

            Color color = getTheme().getAccentColor();
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha);

            GL11.glVertex3d(x, y, z);
        }
        GL11.glEnd();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private static class TimedVec3 {
        public Vec3 vec;
        public long time;

        public TimedVec3(Vec3 vec, long time) {
            this.vec = vec;
            this.time = time;
        }
    }
}