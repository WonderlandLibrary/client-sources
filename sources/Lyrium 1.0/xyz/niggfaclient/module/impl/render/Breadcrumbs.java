// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import xyz.niggfaclient.utils.render.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import java.awt.Color;
import java.util.ArrayList;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.Render3DEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import net.minecraft.util.Vec3;
import java.util.List;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Breadcrumbs", description = "Shows u the last position's by drawing a line", cat = Category.RENDER)
public class Breadcrumbs extends Module
{
    private final List<Vec3> breadcrumbs;
    private final DoubleProperty size;
    private final DoubleProperty length;
    private final Property<Integer> color;
    @EventLink
    private final Listener<Render3DEvent> render3DEventListener;
    
    public Breadcrumbs() {
        this.breadcrumbs = new ArrayList<Vec3>();
        this.size = new DoubleProperty("Size", 1.0, 1.0, 4.0, 1.0);
        this.length = new DoubleProperty("Length", 200.0, 100.0, 1000.0, 10.0);
        this.color = new Property<Integer>("Color", new Color(255, 255, 255).getRGB());
        final double renderX;
        final double renderY;
        final double renderZ;
        final double x;
        final double y;
        final double z;
        Vec3 lastCrumb;
        final Iterator<Vec3> iterator;
        Vec3 breadcrumb;
        this.render3DEventListener = (e -> {
            renderX = RenderManager.renderPosX;
            renderY = RenderManager.renderPosY;
            renderZ = RenderManager.renderPosZ;
            if (this.breadcrumbs.size() >= this.length.getValue()) {
                this.breadcrumbs.remove(0);
            }
            x = RenderUtils.interpolate(this.mc.thePlayer.prevPosX, this.mc.thePlayer.posX, e.getPartialTicks());
            y = RenderUtils.interpolate(this.mc.thePlayer.prevPosY, this.mc.thePlayer.posY, e.getPartialTicks());
            z = RenderUtils.interpolate(this.mc.thePlayer.prevPosZ, this.mc.thePlayer.posZ, e.getPartialTicks());
            this.breadcrumbs.add(new Vec3(x, y, z));
            GL11.glTranslated(-renderX, -renderY, -renderZ);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            RenderUtils.startBlending();
            GL11.glEnable(2848);
            GL11.glDepthMask(false);
            GL11.glLineWidth(this.size.getValue().floatValue());
            RenderUtils.color(this.color.getValue());
            GL11.glBegin(3);
            lastCrumb = null;
            this.breadcrumbs.iterator();
            while (iterator.hasNext()) {
                breadcrumb = iterator.next();
                if (lastCrumb != null && lastCrumb.distanceTo(breadcrumb) > Math.sqrt(3.0)) {
                    GL11.glEnd();
                    GL11.glBegin(3);
                }
                GL11.glVertex3d(breadcrumb.xCoord, breadcrumb.yCoord, breadcrumb.zCoord);
                lastCrumb = breadcrumb;
            }
            GL11.glEnd();
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            RenderUtils.endBlending();
            GL11.glDepthMask(true);
            GL11.glTranslated(renderX, renderY, renderZ);
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.breadcrumbs.clear();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.breadcrumbs.clear();
    }
}
