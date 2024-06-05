package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class RotationUtils2 {
	static Minecraft mc = Minecraft.getMinecraft();
    public static float[] getRotations(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }
    public static float[] getPredictedRotations(EntityLivingBase ent) {
        double x = ent.posX + (ent.posX - ent.lastTickPosX);
        double z = ent.posZ + (ent.posZ - ent.lastTickPosZ);
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getRotationsToEnt(Entity ent, EntityPlayerSP playerSP) {
        final double differenceX = ent.posX - playerSP.posX;
        final double differenceY = (ent.posY + ent.height) - (playerSP.posY + playerSP.height);
        final double differenceZ = ent.posZ - playerSP.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, playerSP.getDistanceToEntity(ent)) * 180.0D / Math.PI);
        final float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
        final float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
        return new float[]{finishedYaw, -finishedPitch};
    }
    
    public static float[] getRotations(double posX, double posY, double posZ) {
        final EntityPlayerSP player = mc.player;
        double x = posX - player.posX;
        double y = posY - (player.posY + player.getEyeHeight());
        double z = posZ - player.posZ;

        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float getDistanceToEntity(EntityLivingBase entityLivingBase) {
        return mc.player.getDistanceToEntity(entityLivingBase);
    }

    public static boolean isOnSameTeam(EntityLivingBase entity) {
        if (entity.getTeam() != null && mc.player.getTeam() != null) {
            char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            char c2 = mc.player.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }

    public static float[] getRotationsEntity(final EntityLivingBase entity) {
        // Hypixel typically flags your rotations making it so you cannot hit people for a bit if they flag their pattern check.
        if (mc.player.isMoving()) {
            return getRotations(entity.posX + MathUtils.randomNumber(0.03, -0.03), entity.posY + entity.getEyeHeight() - 0.4D + MathUtils.randomNumber(0.07, -0.07), entity.posZ + MathUtils.randomNumber(0.03, -0.03));
        }
        return getRotations(entity.posX, entity.posY + entity.getEyeHeight() - 0.4D, entity.posZ);
    }

    //nef tutorial rotations :flushed:
    public static float[] getRotations3(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.player.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.player.posY + mc.player.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.player.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)) + (float) (Math.random() * 2) - 1,
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance)) + (float) (Math.random() * 2) - 1;

        final double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + v);
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + v);
        }
        return new float[]{yaw, pitch};
    }

    public static void drawEntityOnScreen(double posX, double posY, double scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);

        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public static float[] getAverageRotations(List<EntityLivingBase> targetList) {
        double posX = 0.0D;
        double posY = 0.0D;
        double posZ = 0.0D;
        for (Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0D;
            posZ += ent.posZ;
        }
        posX /= targetList.size();
        posY /= targetList.size();
        posZ /= targetList.size();

        return new float[]{getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1]};
    }
    public static float getStraitYaw(){
    	float YAW = MathHelper.wrapDegrees(mc.player.rotationYaw);
    	if(YAW < 45 && YAW > -45){
			YAW = 0;
		}else if(YAW > 45 && YAW < 135){
			YAW = 90f;
		}else if(YAW > 135 || YAW < -135){
			YAW = 180;
		}else{
			YAW = -90f;
		}	 
    	return YAW;
    }
    
    public static Rotation getNeededRotations(Vec3d vec)
	{
		Vec3d eyesPos = getEyesPos();
		
		double diffX = vec.xCoord - eyesPos.xCoord;
		double diffY = vec.yCoord - eyesPos.yCoord;
		double diffZ = vec.zCoord - eyesPos.zCoord;
		
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		
		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
		
		return new Rotation(yaw, pitch);
	}
    
    public static float[] getBowAngles(final Entity entity) {
        final double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
        final double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
        double d = Minecraft.getMinecraft().player.getDistanceToEntity(entity);
        d -= d % 0.8;
        double xMulti = 1.0;
        double zMulti = 1.0;
        final boolean sprint = entity.isSprinting();
        xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
        zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
        final double x = entity.posX + xMulti - Minecraft.getMinecraft().player.posX;
        final double z = entity.posZ + zMulti - Minecraft.getMinecraft().player.posZ;
        final double y = Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight() - (entity.posY + entity.getEyeHeight());
        final double dist = Minecraft.getMinecraft().player.getDistanceToEntity(entity);
        final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
		double d1 = MathHelper.sqrt(x * x + z * z);
        final float pitch =  (float) - (Math.atan2(y, d1) * 180.0D / Math.PI) + (float)dist*0.11f;
        
        return new float[]{yaw, -pitch};
    }
    
    public static final class Rotation
	{
		private final float yaw;
		private final float pitch;
		
		public Rotation(float yaw, float pitch)
		{
			this.yaw = MathHelper.wrapDegrees(yaw);
			this.pitch = MathHelper.wrapDegrees(pitch);
		}
		
		public float getYaw()
		{
			return yaw;
		}
		
		public float getPitch()
		{
			return pitch;
		}
	}

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().player.posX;
        double zDiff = z - Minecraft.getMinecraft().player.posZ;
        double yDiff = y - Minecraft.getMinecraft().player.posY - 1.2;

        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g = 0.006F;
        float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0F * d1 * (velocity * velocity));
        return (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }

    public static float getYawChange(float yaw, double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().player.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().player.posZ;
        double yawToEntity = 0;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
        	if(deltaX != 0)
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
        	if(deltaX != 0)
            yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
        	if(deltaZ != 0)
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapDegrees(-(yaw- (float) yawToEntity));
    }

    public static float getPitchChange(float pitch, Entity entity, double posY) {
        double deltaX = entity.posX - Minecraft.getMinecraft().player.posX;
        double deltaZ = entity.posZ - Minecraft.getMinecraft().player.posZ;
        double deltaY = posY - 2.2D + entity.getEyeHeight() - Minecraft.getMinecraft().player.posY;
        double distanceXZ = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapDegrees(pitch - (float) pitchToEntity) - 2.5F;
    }


    public static float getNewAngle(float angle) {
        angle %= 360.0F;
        if (angle >= 180.0F) {
            angle -= 360.0F;
        }
        if (angle < -180.0F) {
            angle += 360.0F;
        }
        return angle;
    }

    public static boolean canEntityBeSeen(Entity e){
    	Vec3d vec1 = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(),mc.player.posZ);

    	AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3d vec2 = new Vec3d(e.posX, e.posY + (e.getEyeHeight()/1.32F),e.posZ);	
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY) ;
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see =  mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3d(maxx,miny,minz);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3d(minx,miny,minz);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	  
	    if(see)
	    	return true;
	    vec2 = new Vec3d(minx,miny,maxz);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3d(maxx,miny,maxz);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    
	    vec2 = new Vec3d(maxx, maxy,minz);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	  
	    if(see)
	    	return true;
	    vec2 = new Vec3d(minx, maxy,minz);	
	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3d(minx, maxy,maxz - 0.1);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3d(maxx, maxy,maxz);	
	    see = mc.world.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    
	
    	return false;
    }
    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle = Math.abs(angle1 - angle2) % 360.0F;
        if (angle > 180.0F) {
            angle = 360.0F - angle;
        }
        return angle;
    }
    
    public static Vec3d getEyesPos()
	{
		return new Vec3d(mc.player.posX,
				mc.player.posY + mc.player.getEyeHeight(),
				mc.player.posZ);
	}
    
    public static float[] getRotations1(final BlockPos pos, final EnumFacing facing) {
        return getRotations1(pos.getX(), pos.getY(), pos.getZ(), facing);
    }
    public static float[] getRotations1(final double x, final double y, final double z, final EnumFacing facing) {
        final EntityPig temp = new EntityPig(mc.world);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;

        temp.posX += facing.getDirectionVec().getX() * 0.5;
        temp.posY += facing.getDirectionVec().getY() * 0.5;
        temp.posZ += facing.getDirectionVec().getZ() * 0.5;

        return getRotations(temp);
    }

    public static float[] getRotations1(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + (double)(ent.getEyeHeight() / 2.0f);
        return RotationUtils2.getRotationFromPosition(x, z, y);
    }
}