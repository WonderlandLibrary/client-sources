package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class TraceUtil {
    private static final float MAX_STEP = 0.1f;
    private final float startX;
    private final float startY;
    private final float startZ;
    private final float endX;
    private final float endY;
    private final float endZ;
    private final ArrayList<Vector3f> positions = new ArrayList();
    private final EntityLivingBase entity;
    protected Minecraft mc = Minecraft.getMinecraft();

    public TraceUtil(EntityLivingBase entity) {
        this.startX = (float) Minecraft.player.posX;
        this.startY = (float) Minecraft.player.posY + 1.0f;
        this.startZ = (float) Minecraft.player.posZ;
        this.endX = (float) entity.posX;
        this.endY = (float) entity.posY + entity.height / 2.0f;
        this.endZ = (float) entity.posZ;
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
        int steps = (int) Math.max(Math.abs(diffX) / 0.1f, Math.max(Math.abs(diffY) / 0.1f, Math.abs(diffZ) / 0.1f));
        int i = 0;
        while (i <= steps) {
            this.positions.add(new Vector3f(currentX, currentY, currentZ));
            currentX += diffX / (float) steps;
            currentY += diffY / (float) steps;
            currentZ += diffZ / (float) steps;
            ++i;
        }
    }

    private boolean isInBox(Vector3f point, EntityLivingBase target) {
        boolean z;
        AxisAlignedBB box = target.getEntityBoundingBox();
        double posX = Minecraft.player.posX + (double) point.x;
        double posY = Minecraft.player.posY + (double) point.y;
        double posZ = Minecraft.player.posZ + (double) point.z;
        boolean x = posX >= box.minX - 0.25 && posX <= box.maxX + 0.25;
        boolean y = posY >= box.minY && posY <= box.maxY;
        boolean bl = z = posZ >= box.minZ - 0.25 && posZ <= box.maxZ + 0.25;
        return x && z && y;
    }

    public ArrayList<Vector3f> getPositions() {
        return this.positions;
    }

    public EntityLivingBase getEntity() {
        ArrayList possibleEntities = new ArrayList();
        double dist = Minecraft.player.getDistanceToEntity(this.entity);
        EntityLivingBase entity = this.entity;
        for (Object o : this.mc.world.loadedEntityList) {
            EntityLivingBase e;
            if (!(o instanceof EntityLivingBase) || (double) Minecraft.player.getDistanceToEntity(e = (EntityLivingBase) o) >= dist || Minecraft.player == e)
                continue;
            for (Vector3f vec : this.getPositions()) {
                if (!this.isInBox(vec, e) || Minecraft.player.getDistanceToEntity(e) >= Minecraft.player.getDistanceToEntity(entity))
                    continue;
                entity = e;
            }
        }
        return entity;
    }
}
