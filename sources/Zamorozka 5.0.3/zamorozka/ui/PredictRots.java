package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PredictRots {

	private static final Minecraft MC = Minecraft.getMinecraft();
	
	public static float serverYaw, serverPitch;
	
	public static final float[] getRotations(Entity entity, boolean predict, double predictionFactor) {
		final Vec3d playerPos = new Vec3d(MC.player.posX + (predict ? MC.player.motionX * predictionFactor : 0), MC.player.posY + (entity instanceof EntityLivingBase ? MC.player.getEyeHeight() : 0) + (predict ? MC.player.motionY * predictionFactor : 0), MC.player.posZ + (predict ? MC.player.motionZ * predictionFactor : 0));
		final Vec3d entityPos = new Vec3d(entity.posX + (predict ? (entity.posX - entity.prevPosX) * predictionFactor : 0), entity.posY + (predict ? (entity.posY - entity.prevPosY) * predictionFactor : 0), entity.posZ + (predict ? (entity.posZ - entity.prevPosZ) * predictionFactor : 0));
		
		final double diffX = entityPos.xCoord - playerPos.xCoord;
		final double diffY = (entity instanceof EntityLivingBase ? entityPos.yCoord + ((EntityLivingBase) entity).getEyeHeight() - playerPos.yCoord : entityPos.yCoord - playerPos.yCoord);
		final double diffZ = entityPos.zCoord - playerPos.zCoord;
		
		final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		
		final double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0;
		final double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
		
		return new float[] {(float) yaw, (float) pitch};
	}
	
    public static final float[] getRotations(Vec3d pos, boolean predict, double predictionFactor) {
    	final Vec3d playerPos = new Vec3d(MC.player.posX + (predict ? MC.player.motionX * predictionFactor : 0), MC.player.posY+ (predict ? MC.player.motionY * predictionFactor : 0), MC.player.posZ + (predict ? MC.player.motionZ * predictionFactor : 0));
		
    	final double diffX = pos.xCoord + 0.5 - playerPos.xCoord;
    	final double diffY = pos.yCoord + 0.5 - (playerPos.yCoord + MC.player.getEyeHeight());
    	final double diffZ = pos.zCoord + 0.5 - playerPos.zCoord;
        
    	final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = Math.toDegrees (Math.atan2(diffZ, diffX)) - 90.0f;
        double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
        yaw = MC.player.rotationYaw + MathHelper.wrapAngleTo180_float((float) (yaw - MC.player.rotationYaw));
        pitch = MC.player.rotationPitch + MathHelper.wrapAngleTo180_float((float) (pitch - MC.player.rotationPitch));
        return new float[] { (float) yaw, (float) pitch };
    }
    
    public static final Vec3d getVectorForRotation(float yaw, float pitch)
    {
    	final double f = Math.cos(Math.toRadians(-yaw) - Math.PI);
    	final double f1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
    	final double f2 = -Math.cos(Math.toRadians(-pitch));
    	final double f3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
    
    public static final float getDifference(float a, float b) {
        float r = (float) ((a - b) % 360.0);
        
        if (r < -180.0) {
        	r += 360.0;
        }
        
        if (r >= 180.0) {
        	r -= 360.0;
        }
        
        return r;
    }
    
    public static final double getRotationDifference(float[] clientRotations, float[] serverRotations) {
    	return Math.hypot(getDifference(clientRotations[0], serverRotations[0]), clientRotations[1] - serverRotations[1]);
    }
    
    public static final double getRotationDifference(Entity entity) {
    	final float[] rotations = getRotations(entity, false, 1);
    	return getRotationDifference(rotations, new float[] {MC.player.rotationYaw, MC.player.rotationPitch});
    }
    
    public static final float[] smoothRotation(float[] currentRotations, float[] neededRotations, float rotationSpeed) {
    	final float yawDiff = getDifference(neededRotations[0], currentRotations[0]);
    	final float pitchDiff = getDifference(neededRotations[1], currentRotations[1]);
    	
    	float rotationSpeedYaw = rotationSpeed;
    	
    	if (yawDiff > rotationSpeed) {
    		rotationSpeedYaw = rotationSpeed;
    	} else {
    		rotationSpeedYaw = Math.max(yawDiff, -rotationSpeed);
    	}
    	
    	float rotationSpeedPitch = rotationSpeed;
    	
    	if (pitchDiff > rotationSpeed) {
    		rotationSpeedPitch = rotationSpeed;
    	} else {
    		rotationSpeedPitch = Math.max(pitchDiff, -rotationSpeed);
    	}
    	
    	final float newYaw = currentRotations[0] + rotationSpeedYaw;
    	final float newPitch = currentRotations[1] + rotationSpeedPitch;
    	
    	return new float[] { newYaw, newPitch };
    }

    public static final void setRotations(float yaw, float pitch) {
    	serverYaw = yaw;
    	serverPitch = pitch;
    }
    
    public static final void setRotations(float[] rotations) {
    	setRotations(rotations[0], rotations[1]);
    }
    
    public static final float[] getServerRotations() {
    	return new float[] {serverYaw, serverPitch};
    }
    
}