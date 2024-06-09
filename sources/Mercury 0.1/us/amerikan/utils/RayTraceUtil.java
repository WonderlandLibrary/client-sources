/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

public class RayTraceUtil {
    protected Minecraft mc = Minecraft.getMinecraft();
    private float startX;
    private float startY;
    private float startZ;
    private float endX;
    private float endY;
    private float endZ;
    private static final float MAX_STEP = 0.1f;
    private ArrayList positions = new ArrayList();
    private EntityLivingBase entity;

    public RayTraceUtil(EntityLivingBase entity) {
        this.startX = (float)Minecraft.thePlayer.posX;
        this.startY = (float)Minecraft.thePlayer.posY + 1.0f;
        this.startZ = (float)Minecraft.thePlayer.posZ;
        this.endX = (float)entity.posX;
        this.endY = (float)entity.posY + entity.height / 2.0f;
        this.endZ = (float)entity.posZ;
        this.entity = entity;
        this.positions.clear();
        this.addPositions();
    }

    private void addPositions() {
        float diffX = this.endX - this.startX;
        float diffY = this.endY - this.startY;
        float diffZ = this.endZ - this.startZ;
        float currentX = 0.0f;
        float currentY = 1.0f;
        float currentZ = 0.0f;
        int steps = (int)Math.max(Math.abs(diffX) / 0.1f, Math.max(Math.abs(diffY) / 0.1f, Math.abs(diffZ) / 0.1f));
        for (int i2 = 0; i2 <= steps; ++i2) {
            this.positions.add(new Vector3f(currentX, currentY, currentZ));
            currentX += diffX / (float)steps;
            currentY += diffY / (float)steps;
            currentZ += diffZ / (float)steps;
        }
    }

    private boolean isInBox(Vector3f point, EntityLivingBase target) {
        boolean z2;
        AxisAlignedBB box2 = target.getEntityBoundingBox();
        double posX = Minecraft.thePlayer.posX + (double)point.x;
        double posY = Minecraft.thePlayer.posY + (double)point.y;
        double posZ = Minecraft.thePlayer.posZ + (double)point.z;
        boolean x2 = posX >= box2.minX - 0.25 && posX <= box2.maxX + 0.25;
        boolean y2 = posY >= box2.minY && posY <= box2.maxY;
        boolean bl2 = z2 = posZ >= box2.minZ - 0.25 && posZ <= box2.maxZ + 0.25;
        return x2 && z2 && y2;
    }

    public ArrayList getPositions() {
        return this.positions;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public EntityLivingBase getEntity() {
        dist = Minecraft.thePlayer.getDistanceToEntity(this.entity);
        entity = this.entity;
        var6 = this.mc.theWorld.loadedEntityList.iterator();
        block0 : do lbl-1000: // 3 sources:
        {
            if (!var6.hasNext()) {
                return entity;
            }
            o = var6.next();
            if (!(o instanceof EntityLivingBase) || !((double)Minecraft.thePlayer.getDistanceToEntity(e = (EntityLivingBase)o) < dist) || Minecraft.thePlayer == e) ** GOTO lbl-1000
            var9 = this.getPositions().iterator();
            do {
                if (!var9.hasNext()) continue block0;
                vec = (Vector3f)var9.next();
                if (!this.isInBox(vec, e) || !(Minecraft.thePlayer.getDistanceToEntity(e) < Minecraft.thePlayer.getDistanceToEntity(entity))) continue;
                entity = e;
            } while (true);
            break;
        } while (true);
    }
}

