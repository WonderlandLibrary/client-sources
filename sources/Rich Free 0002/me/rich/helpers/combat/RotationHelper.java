package me.rich.helpers.combat;

import org.apache.commons.lang3.RandomUtils;

import me.rich.Main;
import me.rich.helpers.other.CountHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationHelper {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static float[] getRatations(Entity e) {
		double diffX = e.posX - mc.player.posX;
		double diffZ = e.posZ - mc.player.posZ;
		double diffY;

		if (e instanceof EntityLivingBase) {
			diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.4;
		} else {
			diffY = (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + mc.player.getEyeHeight());
		}

		double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + CountHelper.nextFloat(-2, 2);
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + CountHelper.nextFloat(-2, 2);
		yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
		pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
		pitch = MathHelper.clamp(pitch, -90F, 90F);
		return new float[] { yaw, pitch };
	}

	public static float[] getRotations(Entity ent) {
		double x = ent.posX;
		double z = ent.posZ;
		double y = ent.posY + ent.getEyeHeight() / 2.0F;
		return getRotationFromPosition(x, z, y);
	}
	
	

	public static float[] getRotationFromPosition(double x, double z, double y) {
		double xDiff = x - Minecraft.getMinecraft().player.posX;
		double zDiff = z - Minecraft.getMinecraft().player.posZ;
		double yDiff = y - Minecraft.getMinecraft().player.posY - 1.7;

		double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
		return new float[] { yaw, pitch };
	}
	
    public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
        Minecraft.getMinecraft();
        double diffX = entityLiving.posX - Minecraft.player.posX;
        Minecraft.getMinecraft();
        double diffZ = entityLiving.posZ - Minecraft.player.posZ;
        float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double d = newYaw;
        Minecraft.getMinecraft();
        double difference = angleDifference(d, Minecraft.player.rotationYaw);
        return difference <= (double)scope;
    }
    
    public static double angleDifference(double a, double b) {
        float yaw360 = (float)(Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }
    
    public static boolean isLookingAtEntity(Entity e) {
        return isLookingAt(e.getPositionEyes(mc.timer.elapsedPartialTicks));
    }
    
    public static boolean isLookingAt(Vec3d vec) {
        Float[] targetangles = getLookAngles(vec);
        targetangles = getLookAngles(vec);
        float change = Math.abs(MathHelper.wrapDegrees(targetangles[0].floatValue()  / Main.instance.settingsManager.getSettingByName("RayTrace Box").getValFloat()));
        return change < 20.0f;
    }
    
    public static Float[] getLookAngles(Vec3d vec) {
        Float[] angles = new Float[2];
        Minecraft mc = Minecraft.getMinecraft();
        angles[0] = Float.valueOf((float)(Math.atan2(Minecraft.player.posZ - vec.zCoord, Minecraft.player.posX - vec.xCoord) / Math.PI * 180.0) + 90.0f);
        float heightdiff = (float)(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - vec.yCoord);
        float distance = (float)Math.sqrt((Minecraft.player.posZ - vec.zCoord) * (Minecraft.player.posZ - vec.zCoord) + (Minecraft.player.posX - vec.xCoord) * (Minecraft.player.posX - vec.xCoord));
        angles[1] = Float.valueOf((float)(Math.atan2(heightdiff, distance) / Math.PI * 180.0));
        return angles;
    }
}
