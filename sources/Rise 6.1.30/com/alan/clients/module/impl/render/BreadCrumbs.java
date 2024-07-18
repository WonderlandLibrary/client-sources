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
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(aliases = {"module.render.breadcrumbs.name"}, description = "module.render.breadcrumbs.description", category = Category.RENDER)
public class BreadCrumbs extends Module {

    List<Vec3> path = new ArrayList<>();
    private final NumberValue timeout = new NumberValue("Time", this, 15, 1, 150, 0.1);

    @Override
    public void onEnable() {
        path.clear();
    }

    @EventLink
    public final Listener<PreMotionEvent> eventListener = event -> {
        if (mc.thePlayer.lastTickPosX != mc.thePlayer.posX || mc.thePlayer.lastTickPosY != mc.thePlayer.posY || mc.thePlayer.lastTickPosZ != mc.thePlayer.posZ) {
            path.add(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
        }

        while (path.size() > 15) {
            path.remove(0);
        }
    };

    @EventLink
    public final Listener<Render3DEvent> render3DEventListener = event -> {
        for (final Vec3 v : path) {
            renderBreadCrumbs(path);
        }
    };

    public void renderBreadCrumbs(final List<Vec3> vec3s) {

        GlStateManager.disableDepth();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int i = 0;
        for (final Vec3 v : vec3s) {
            i++;
            boolean draw = true;

            final double x = v.xCoord - (mc.getRenderManager()).renderPosX;
            final double y = v.yCoord - (mc.getRenderManager()).renderPosY;
            final double z = v.zCoord - (mc.getRenderManager()).renderPosZ;

            final double distanceFromPlayer = mc.thePlayer.getDistance(v.xCoord, v.yCoord - 1, v.zCoord);

            if (i % 10 != 0 && distanceFromPlayer > 25) {
                draw = false;
            }

            if (i % 5 == 0 && distanceFromPlayer > 15) {
                draw = false;
            }

            if (draw) {

                GL11.glPushMatrix();
                GL11.glTranslated(x, y, z);

                final float scale = 0.04f;
                GL11.glScalef(-scale, -scale, -scale);

                GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);
                GL11.glRotated((mc.getRenderManager()).playerViewX, 1.0D, 0.0D, 0.0D);

                if (distanceFromPlayer < 20) {
                    RenderUtil.circle(0, 0, 3, getTheme().getAccentColor());
                }

                GL11.glScalef(0.8f, 0.8f, 0.8f);

                GL11.glPopMatrix();

            }
        }
    }
}

