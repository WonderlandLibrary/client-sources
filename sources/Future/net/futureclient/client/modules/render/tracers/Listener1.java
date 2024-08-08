package net.futureclient.client.modules.render.tracers;

import java.awt.Color;
import java.util.Iterator;
import net.futureclient.client.ed;
import net.futureclient.client.modules.render.ESP;
import net.minecraft.util.math.Vec3d;
import net.futureclient.loader.mixin.common.render.entity.wrapper.IEntityRenderer;
import net.futureclient.client.dH;
import org.lwjgl.opengl.GL11;
import net.futureclient.client.pg;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.futureclient.client.wb;
import net.futureclient.client.BC;
import net.futureclient.client.xG;
import net.minecraft.entity.Entity;
import net.futureclient.client.ZG;
import net.futureclient.client.events.EventType;
import net.futureclient.client.events.EventRender;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Tracers;
import net.futureclient.client.KD;
import net.futureclient.client.n;

public class Listener1 extends n<KD>
{
    public final Tracers k;
    
    public Listener1(final Tracers k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventRender)event);
    }
    
    public void M(final EventRender eventRender) {
        if (!eventRender.getType().equals(EventType.POST)) {
            return;
        }
        final Iterator<Entity> iterator2;
        Iterator<Entity> iterator = iterator2 = ZG.e().iterator();
        while (iterator.hasNext()) {
            final Entity entity = iterator2.next();
            if (!Tracers.M(this.k, entity)) {
                iterator = iterator2;
            }
            else {
                final Vec3d m = xG.M(entity);
                final double x = m.x;
                final double y = m.y;
                final double z = m.z;
                AxisAlignedBB axisAlignedBB = null;
                switch (BC.D[((wb.qd)Tracers.M(this.k).M()).ordinal()]) {
                    case 1:
                        axisAlignedBB = new AxisAlignedBB(x - 0.0, y + entity.height - 1.697596633E-314, z - 0.0, x + 0.0, y + entity.height + 1.612716801E-314, z + 0.0);
                        break;
                    default:
                        axisAlignedBB = new AxisAlignedBB(x - 1.273197475E-314, y, z - 1.273197475E-314, x + 1.273197475E-314, y + entity.height + 9.33678148E-315, z + 1.273197475E-314);
                        break;
                }
                xG.B();
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                if (pg.M().M().M(entity.getName())) {
                    GL11.glColor4f(0.33333334f, 0.78431374f, 0.78431374f, 0.55f);
                }
                else {
                    final float distance = Tracers.getMinecraft5().player.getDistance(entity);
                    float n;
                    if (distance >= 60.0f) {
                        n = 120.0f;
                    }
                    else {
                        final float n2 = distance;
                        n = n2 + n2;
                    }
                    final Color e;
                    GL11.glColor4f((e = new dH(n, 100.0f, 50.0f, 0.55f).e()).getRed() / 255.0f, e.getGreen() / 255.0f, e.getBlue() / 255.0f, e.getAlpha() / 255.0f);
                }
                final boolean viewBobbing = Tracers.getMinecraft7().gameSettings.viewBobbing;
                Tracers.getMinecraft().gameSettings.viewBobbing = false;
                ((IEntityRenderer)Tracers.getMinecraft6().entityRenderer).orientCameraWrapper(eventRender.M());
                GL11.glLineWidth(Tracers.M(this.k).B().floatValue());
                final double n3 = 0.0;
                final Vec3d rotateYaw = new Vec3d(n3, n3, 1.0).rotatePitch(-(float)Math.toRadians(Tracers.getMinecraft1().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Tracers.getMinecraft2().player.rotationYaw));
                GL11.glBegin(1);
                final ESP esp = (ESP)pg.M().M().M((Class)ed.class);
                if (((wb.CC)Tracers.e(this.k).M()).equals((Object)wb.CC.a) && esp != null && !esp.M()) {
                    GL11.glVertex3d(x, y, z);
                    GL11.glVertex3d(x, Tracers.getMinecraft3().player.getEyeHeight() + y, z);
                }
                if (Tracers.M(this.k).M()) {
                    GL11.glVertex3d(rotateYaw.x, Tracers.getMinecraft4().player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
                    switch (BC.D[((wb.qd)Tracers.M(this.k).M()).ordinal()]) {
                        case 1:
                            GL11.glVertex3d(x, y + entity.height - 9.33678148E-315, z);
                            break;
                        case 2:
                            GL11.glVertex3d(x, y + entity.height / 2.0f, z);
                            break;
                        case 3:
                            GL11.glVertex3d(x, y, z);
                            break;
                    }
                    GL11.glEnd();
                }
                GL11.glTranslated(x, y, z);
                GL11.glTranslated(-x, -y, -z);
                switch (BC.k[((wb.CC)Tracers.e(this.k).M()).ordinal()]) {
                    case 1:
                        xG.B(axisAlignedBB);
                        break;
                    case 2:
                        xG.M(axisAlignedBB);
                        break;
                }
                Tracers.getMinecraft8().gameSettings.viewBobbing = viewBobbing;
                GlStateManager.popMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                xG.C();
                iterator = iterator2;
            }
        }
    }
}
