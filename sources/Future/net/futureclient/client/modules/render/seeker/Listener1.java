package net.futureclient.client.modules.render.seeker;

import java.util.Iterator;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.Vec3d;
import net.futureclient.loader.mixin.common.render.entity.wrapper.IEntityRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.futureclient.client.WG;
import java.awt.Color;
import net.minecraft.util.math.AxisAlignedBB;
import net.futureclient.client.pg;
import net.futureclient.client.ye;
import net.futureclient.client.xG;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.Entity;
import net.futureclient.client.events.EventType;
import net.futureclient.client.events.EventRender;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Seeker;
import net.futureclient.client.KD;
import net.futureclient.client.n;

public class Listener1 extends n<KD>
{
    public final Seeker k;
    
    public Listener1(final Seeker k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventRender)event);
    }
    
    public void M(final EventRender eventRender) {
        if (!eventRender.getType().equals(EventType.PRE)) {
            return;
        }
        final Iterator<Entity> iterator = (Iterator<Entity>)Seeker.getMinecraft().world.loadedEntityList.iterator();
        while (iterator.hasNext()) {
            final Entity entity;
            if ((entity = iterator.next()) != null && entity.isEntityAlive() && entity.getDistance((Entity)Seeker.getMinecraft5().player) > 0.0 && entity instanceof EntityAnimal && Seeker.M(this.k, (EntityAnimal)entity)) {
                final EntityAnimal entityAnimal = (EntityAnimal)entity;
                final Vec3d m = xG.M(entity);
                final double x = m.x;
                final double y = m.y;
                final double z = m.z;
                final Seeker k = this.k;
                final Seeker i = this.k;
                final EntityAnimal entityAnimal2 = entityAnimal;
                Seeker.M(k, (Entity)entityAnimal2, Seeker.M(i, entityAnimal2), x, y + 0.0, z, eventRender.M());
                final ye ye = (ye)pg.M().M().M((Class)ye.class);
                if (Seeker.M(this.k).M()) {
                    xG.B();
                    WG.M(new AxisAlignedBB(x - 0.0, y, z - 0.0, x + 0.0, y + 1.0, z + 0.0), 1.5f, new Color(ye.L.getRGB()));
                    xG.C();
                }
                if (Seeker.e(this.k).M()) {
                    xG.B();
                    WG.M(new AxisAlignedBB(x - 0.0, y, z - 0.0, x + 0.0, y + 1.0, z + 0.0), new Color(ye.L.getRed() / 255.0f, ye.L.getGreen() / 255.0f, ye.L.getBlue() / 255.0f, 0.2f));
                    xG.C();
                }
                if (Seeker.b(this.k).M()) {
                    xG.B();
                    GlStateManager.color(ye.L.getRed() / 255.0f, ye.L.getGreen() / 255.0f, ye.L.getBlue() / 255.0f, 1.0f);
                    GL11.glLoadIdentity();
                    GL11.glLineWidth(Seeker.M(this.k).B().floatValue());
                    ((IEntityRenderer)Seeker.getMinecraft6().entityRenderer).orientCameraWrapper(eventRender.M());
                    final double n = 0.0;
                    final Vec3d rotateYaw = new Vec3d(n, n, 1.0).rotatePitch(-(float)Math.toRadians(Seeker.getMinecraft1().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Seeker.getMinecraft2().player.rotationYaw));
                    GL11.glBegin(1);
                    final double n2 = x;
                    GL11.glVertex3d(rotateYaw.x, Seeker.getMinecraft4().player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
                    GL11.glVertex3d(n2, y, z);
                    final float n3 = 1.0f;
                    final int n4 = 1;
                    GL11.glColor4f((float)n4, (float)n4, n3, (float)n4);
                    GL11.glEnd();
                    xG.C();
                }
            }
            if (entity != null && entity.isEntityAlive() && entity instanceof EntityFallingBlock && entity.getDistance((Entity)Seeker.getMinecraft3().player) > 1.0f) {
                final EntityFallingBlock entityFallingBlock;
                final Vec3d j = xG.M((Entity)(entityFallingBlock = (EntityFallingBlock)entity));
                final double x2 = j.x;
                final double y2 = j.y;
                final double z2 = j.z;
                final Seeker l = this.k;
                final EntityFallingBlock entityFallingBlock2 = entityFallingBlock;
                String localizedName;
                double n5;
                if (entityFallingBlock2.getBlock() == null) {
                    localizedName = "Block";
                    n5 = x2;
                }
                else {
                    localizedName = entityFallingBlock.getBlock().getBlock().getLocalizedName();
                    n5 = x2;
                }
                Seeker.e(l, (Entity)entityFallingBlock2, localizedName, n5, y2 + 0.0, z2, eventRender.M());
            }
        }
    }
}
