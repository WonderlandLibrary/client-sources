package xyz.vergoclient.util.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {

	public static float getSensitivityMultiplier() {
		float SENSITIVITY = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
		return (SENSITIVITY * SENSITIVITY * SENSITIVITY * 8.0F) * 0.15F;
	}

	public static float smoothRotation(float from, float to, float speed) {
		float f = MathHelper.wrapAngleTo180_float(to - from);

		if (f > speed) {
			f = speed;
		}

		if (f < -speed) {
			f = -speed;
		}

		return from + f;
	}

	public static float updateRotation(float current, float intended, float factor) {
		float var4 = MathHelper.wrapAngleTo180_float(intended - current);

		if (var4 > factor) {
			var4 = factor;
		}

		if (var4 < -factor) {
			var4 = -factor;
		}

		return current + var4;
	}

	public static float getYaw(Vec3 to) {
		float x = (float) (to.xCoord - mc.thePlayer.posX);
		float z = (float) (to.zCoord - mc.thePlayer.posZ);
		float var1 = (float) (StrictMath.atan2(z, x) * 180.0D / StrictMath.PI) - 90.0F;
		float rotationYaw = mc.thePlayer.rotationYaw;
		return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
	}
	
	public static float getRotationChange(float current, float intended) {
		float var4 = MathHelper.wrapAngleTo180_float(intended - current);
		
		return var4;
	}
	
	public static float getRotationPercent(float starting, float current, float intended) {
		float currentToIntended = MathHelper.wrapAngleTo180_float(intended - current);
		float startingToIntended = MathHelper.wrapAngleTo180_float(intended - starting);
		
		if (currentToIntended == 0 || startingToIntended == 0) {
			return 1.0f;
		}
		
		if (currentToIntended < 0) {
			currentToIntended *= -1;
		}
		
		if (startingToIntended < 0) {
			startingToIntended *= -1;
		}
		
		if (currentToIntended > startingToIntended) {
			float temp = startingToIntended;
			startingToIntended = currentToIntended;
			currentToIntended = temp;
		}
		
		return 1.0f - (currentToIntended / startingToIntended);
	}
	
	public static float updateRotationWithPercent(float current, float intended, float percent) {
		float factor = (intended - current) * percent;
		return current + factor;
	}
	
    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY -1.2;

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }
    
    public static float[] getRotationFromPosition(double x1, double z1, double y1, double x2, double z2, double y2) {
        double xDiff = x2 - x1;
        double zDiff = z2 - z1;
        double yDiff = y2 - y1;

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

    protected static Minecraft mc = Minecraft.getMinecraft();
    
    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + (ent.getEyeHeight() / 2.0F);
        return getRotationFromPosition(x, z, y);
    }

    // New rotations that make the old
	// ones fucking dogwater and obsolete.
	public static float[] getRotations(final double posX, final double posY, final double posZ) {
		final EntityPlayerSP player = mc.thePlayer;
		final double x = posX - player.posX;
		final double y = posY - (player.posY + player.getEyeHeight());
		final double z = posZ - player.posZ;
		final double dist = MathHelper.sqrt_double(x * x + z * z);
		final float yaw = (float) (Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
		return new float[]{yaw, pitch};
	}

	public static float[] getRotation(Entity a1) {
		double v1 = a1.posX - mc.thePlayer.posX;
		double v3 = a1.posY + (double) a1.getEyeHeight() * 0.9 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
		double v5 = a1.posZ - mc.thePlayer.posZ;

		double v7 = MathHelper.ceiling_float_int((float) (v1 * v1 + v5 * v5));
		float v9 = (float) (Math.atan2(v5, v1) * 180.0 / 3.141592653589793) - 90.0f;
		float v10 = (float) (-(Math.atan2(v3, v7) * 180.0 / 3.141592653589793));
		return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(v9 - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(v10 - mc.thePlayer.rotationPitch)};
	}

	//Best method
	public static float[] getRotationToEntity(final EntityLivingBase entity) {
		double xDiff = entity.posX - mc.thePlayer.posX;
		double zDiff = entity.posZ - mc.thePlayer.posZ;
		double yDiff = (entity.posY + entity.getEyeHeight() * 0.9) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());

		double distance = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);

		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (-(Math.atan2(yDiff, distance) * 180.0D / Math.PI));

		return new float[]{yaw, pitch};
	}
    
    public static Vec3 getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
    
    public static float getBowVelocity() {
    	try {
            int i = Minecraft.getMinecraft().thePlayer.getItemInUse().getMaxItemUseDuration() - Minecraft.getMinecraft().thePlayer.itemInUseCount;
            float f = (float)i / 20.0F;
            return f * 2.0F;
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return 0;
    }
    
}
