package client.metaware.impl.module.render;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.player.WorldLoadEvent;
import client.metaware.impl.event.impl.render.Render3DEvent;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.system.TimerUtil;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(name = "Breadcrumbs", renderName = "Breadcrumbs", category = Category.VISUALS)
public class BreadNiggers extends Module {

    private final DoubleProperty pointSizeProperty = new DoubleProperty("Point Size", 20, 10, 60, 1, Property.Representation.INT);
    private final EnumProperty<ColorMode> arrayListColor = new EnumProperty<>("Color", ColorMode.Rainbow);
    private final DoubleProperty timeProperty = new DoubleProperty("Time", 2, 1, 10, 1, Property.Representation.INT);
    private final Property<Boolean> timeoutProperty = new Property<>("Timeout", true);
    private final Property<Boolean> moreBreadcrumbsProperty = new Property<>("More Breadcrumbs", false);
    private final EnumProperty<BreadcrumbsMode> modeProperty = new EnumProperty<>("Mode", BreadcrumbsMode.DOTS);
    private final List<Breadcrumb> breadcrumbs = new ArrayList<>();
    private final TimerUtil timer = new TimerUtil();

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = event -> {
        if (breadcrumbs.size() > 0) {
            drawBreadcrumbs(event);
        }
    };

    @EventHandler
    private final Listener<WorldLoadEvent> worldLoadListener = event -> {
        breadcrumbs.clear();
    };


    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = event -> {
        if (event.getState() == UpdatePlayerEvent.EventState.PRE) {
            clearBreadcrumbs();
            updateBreadcrumbs();
        }
    };

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        breadcrumbs.clear();
    }

    public void drawBreadcrumbs(Render3DEvent event) {
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glEnable(GL_BLEND);
        if (modeProperty.getValue() == BreadcrumbsMode.DOTS)
            glEnable(GL_POINT_SMOOTH);
        glPushMatrix();
        int index = 0;
        for (Breadcrumb breadcrumb : breadcrumbs) {
            int color;
            color = 0;
            switch (arrayListColor.getValue()) {
                case Rainbow:
                    color = RenderUtil.getRainbowFelix(6000, (int) (index * 30), 0.636f);
                    break;
                case Felix:
                    color = RenderUtil.getGradientOffset(new Color(64, 85, 150), new Color(130, 191, 226), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (index / (50))).getRGB();
                    break;
                case Astolfo:
                    color = RenderUtil.getGradientOffset(new Color(0, 255, 255), new Color(255,105,180), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (index / (50))).getRGB();
                    break;
            }
            if (!breadcrumb.visible) {
                if (breadcrumb.opacity > 0) breadcrumb.opacity -= 1;
                if (breadcrumb.scale >= 0.1) breadcrumb.scale -= 0.1f;
            }
            double x = breadcrumb.x - RenderManager.viewerPosX;
            double y = breadcrumb.y - RenderManager.viewerPosY;
            double z = breadcrumb.z - RenderManager.viewerPosZ;
            switch (modeProperty.getValue()) {
                case DOTS: {
                    mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                    glPointSize(pointSizeProperty.getValue().floatValue());
                    glBegin(GL_POINTS);
                    RenderUtil.color(color, (int) breadcrumb.opacity);
                    glVertex3d(x, y, z);
                    glEnd();
                    glPointSize(pointSizeProperty.getValue().floatValue() * 1.75f);
                    glBegin(GL_POINTS);
                    RenderUtil.color(color, (int) breadcrumb.opacity / 3);
                    glVertex3d(x, y, z);
                    glEnd();
                    glPointSize(pointSizeProperty.getValue().floatValue() * 2.5f);
                    glBegin(GL_POINTS);
                    RenderUtil.color(color, (int) breadcrumb.opacity / 4);
                    glVertex3d(x, y, z);
                    glEnd();
                    break;
                }
                case LINES: {
                    glPushMatrix();
                    glTranslated(-x, -y, -z);
                    glScalef(breadcrumb.scale, breadcrumb.scale, breadcrumb.scale);
                    glTranslated(x, y, z);
                    glPopMatrix();
                    glBegin(GL_LINES);
                    RenderUtil.color(color, (int) breadcrumb.opacity);
                    glVertex3d(x, y, z);
                    glEnd();
                    break;
                }
            }
            index++;
        }
        glColor4f(1, 1, 1, 1);
        if (modeProperty.getValue() == BreadcrumbsMode.DOTS)
            glDisable(GL_POINT_SMOOTH);
        glDisable(GL_BLEND);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    private enum ColorMode {
        Custom, Rainbow, Felix, Astolfo;
    }

    public void clearBreadcrumbs() {
        breadcrumbs.removeIf(breadcrumb -> !breadcrumb.visible && breadcrumb.opacity == 0 && breadcrumb.scale == 0 || getDistanceToBreadcrumb(breadcrumb) > 30);
    }

    public void updateBreadcrumbs() {
        for (Breadcrumb breadcrumb : breadcrumbs) {
            if (System.currentTimeMillis() - breadcrumb.time > timeProperty.getValue() * 2000 && timeoutProperty.getValue()) {
                breadcrumb.visible = false;
            }
        }
        if (timer.delay(moreBreadcrumbsProperty.getValue() ? 25 : 50) && mc.thePlayer.isMoving()) {
            breadcrumbs.add(new Breadcrumb(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
            timer.reset();
        }
    }

    public double getDistanceToBreadcrumb(Breadcrumb breadcrumb) {
        double xDiff = Math.abs(breadcrumb.x - mc.thePlayer.posX);
        double yDiff = Math.abs(breadcrumb.y - mc.thePlayer.posY);
        double zDiff = Math.abs(breadcrumb.z - mc.thePlayer.posZ);
        return MathHelper.sqrt_double(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public enum BreadcrumbsMode {
        DOTS, LINES
    }

    private class Breadcrumb {

        private final double x;
        private final double y;
        private final double z;
        private float opacity = 255, scale = 1;
        private boolean visible = true;
        private final long time;

        public Breadcrumb(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            time = System.currentTimeMillis();
        }
    }
}