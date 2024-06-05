package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class PlayerHelper {

	private static Minecraft mc = Minecraft.getMinecraft();
	public static BlockPos getPlayerPos() {
		return new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
	}

	public static void motionXZ(double motion) {
		mc.player.motionZ *= motion;
		mc.player.motionX *= motion;
	}

	public static Entity raycast(Entity e, double range) {
		Vec3d vec = e.getPositionVector().add(new Vec3d(0, e.getEyeHeight(), 0));
		Vec3d vec1 = mc.player.getPositionVector().add(new Vec3d(0, mc.player.getEyeHeight(), 0));
		AxisAlignedBB axis = mc.player.getEntityBoundingBox()
				.addCoord(vec.xCoord - vec1.xCoord, vec.yCoord - vec1.yCoord, vec.zCoord - vec1.zCoord).expand(1, 1, 1);

		Entity nearst = null;
		Vec3d vec2;

		for (Object obj : mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, axis)) {
			Entity en = (Entity) obj;

			if (en.canBeCollidedWith() && en instanceof EntityLivingBase) {
				float size = en.getCollisionBorderSize();
				AxisAlignedBB axis1 = en.getEntityBoundingBox().expand(size, size, size);
				RayTraceResult mop = axis1.calculateIntercept(vec1, vec);

				if (axis1.isVecInside(vec1)) {
					if (range >= 0) {
						nearst = en;
						vec2 = mop == null ? vec1 : mop.hitVec;
						range = 0;
					}
				} else if (mop != null) {
					double dist = vec1.distanceTo(mop.hitVec);

					if (range == 0 || dist < range) {
						nearst = en;
						vec2 = mop.hitVec;
						range = dist;
					}
				}
			}
		}

		return nearst;

	}
	
	public static float wrapAngleTo180(float angle)
    {
        angle %= 360f;

        if (angle >= 180f)
        {
            angle -= 360f;
        }

        if (angle < -180f)
        {
            angle += 360f;
        }

        return angle;
    }

}