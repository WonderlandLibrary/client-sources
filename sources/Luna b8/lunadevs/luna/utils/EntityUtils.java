package lunadevs.luna.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
public class EntityUtils {

   public static boolean lookChanged;
   public static float yaw;
   public static float pitch;


   public static float[] getRotationsNeeded(Entity entity) {
      if(entity == null) {
         return null;
      } else {
         Minecraft.getMinecraft();
		double diffX = entity.posX - Minecraft.thePlayer.posX;
         double diffY;
         if(entity instanceof EntityLivingBase) {
            EntityLivingBase diffZ = (EntityLivingBase)entity;
            Minecraft.getMinecraft();
			Minecraft.getMinecraft();
			diffY = diffZ.posY + (double)diffZ.getEyeHeight() * 0.9D - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
         } else {
            Minecraft.getMinecraft();
			Minecraft.getMinecraft();
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
         }

         Minecraft.getMinecraft();
		double diffZ1 = entity.posZ - Minecraft.thePlayer.posZ;
         double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ1 * diffZ1);
         float yaw = (float)(Math.atan2(diffZ1, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
         float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
         Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		return new float[]{(float) (Minecraft.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_double(yaw - Minecraft.thePlayer.rotationYaw)), (float) (Minecraft.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_double(pitch - Minecraft.thePlayer.rotationPitch))};
      }
   }

   public static void faceEntity(EntityLivingBase entity) {
      float[] rotations = RotationUtils.getRotations(entity);
      if(rotations != null) {
         Minecraft.getMinecraft();
		Minecraft.thePlayer.rotationYaw = rotations[0];
         Minecraft.getMinecraft();
		Minecraft.thePlayer.rotationPitch = rotations[1] - 8.0F;
      }

   }

   public static int getDistanceFromMouse(Entity entity) {
      float[] neededRotations = getRotationsNeeded(entity);
      if(neededRotations != null) {
         Minecraft.getMinecraft();
		float neededYaw = Minecraft.thePlayer.rotationYaw - neededRotations[0];
         Minecraft.getMinecraft();
		float neededPitch = Minecraft.thePlayer.rotationPitch - neededRotations[1];
         float distanceFromMouse = MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
         return (int)distanceFromMouse;
      } else {
         return -1;
      }
   }

   public static float getDistanceFromMouseTrig(EntityLivingBase entity) {
      float[] neededRotations = getRotationsNeeded(entity);
      if(neededRotations != null) {
         Minecraft.getMinecraft();
		float neededYaw = Minecraft.thePlayer.rotationYaw - neededRotations[0];
         Minecraft.getMinecraft();
		float neededPitch = Minecraft.thePlayer.rotationPitch - neededRotations[1] + 8.0F;
         MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
         if(neededYaw > -10.0F && neededYaw < 10.0F && neededPitch > 3.0F && neededPitch < 44.0F) {
            return 999999.0F;
         }
      }

      return -1.0F;
   }

   public static float getDistanceFromMouseSafe(EntityLivingBase entity) {
      float[] neededRotations = getRotationsNeeded(entity);
      if(neededRotations != null) {
         Minecraft.getMinecraft();
		float neededYaw = Minecraft.thePlayer.rotationYaw - neededRotations[0];
         Minecraft.getMinecraft();
		float neededPitch = Minecraft.thePlayer.rotationPitch - neededRotations[1] + 8.0F;
         MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
         if(neededYaw > -40.0F && neededYaw < 40.0F && neededPitch > -5.0F && neededPitch < 40.0F) {
            return 999999.0F;
         }
      }

      return -1.0F;
   }

   public static final float limitAngleChange(float current, float intended, float maxChange) {
      float change = intended - current;
      if(change > maxChange) {
         change = maxChange;
      } else if(change < -maxChange) {
         change = -maxChange;
      }

      return current + change;
   }

   public static EntityLivingBase getClosestEntity() {
      EntityLivingBase closestEntity = null;
      Iterator<?> var2 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		if(!(o instanceof EntityPlayerSP) && !entityplayer.isDead && entityplayer.getHealth() > 0.0F && Minecraft.thePlayer.canEntityBeSeen(entityplayer) && !entityplayer.getName().equals(Minecraft.thePlayer.getName()) && (closestEntity == null || Minecraft.thePlayer.getDistanceToEntity(entityplayer) < Minecraft.thePlayer.getDistanceToEntity(closestEntity))) {
            closestEntity = entityplayer;
         }
      }

      return closestEntity;
   }
   
   public static EntityPlayer getClosestEntity1() {
	      EntityPlayer closestEntityP = null;
	      Iterator<?> var2 = Minecraft.getMinecraft().theWorld.playerEntities.iterator();

	      while(var2.hasNext()) {
	         Object o = var2.next();
	         EntityPlayer entityplayerP = (EntityPlayer)o;
	         Minecraft.getMinecraft();
			Minecraft.getMinecraft();
			Minecraft.getMinecraft();
			Minecraft.getMinecraft();
			if(!(o instanceof EntityPlayerSP) && !entityplayerP.isDead && entityplayerP.getHealth() > 0.0F && Minecraft.thePlayer.canEntityBeSeen(entityplayerP) && !entityplayerP.getName().equals(Minecraft.thePlayer.getName()) && (closestEntityP == null || Minecraft.thePlayer.getDistanceToEntity(entityplayerP) < Minecraft.thePlayer.getDistanceToEntity(closestEntityP))) {
	            closestEntityP = entityplayerP;
	         }
	      }

	      return closestEntityP;
	   }

   public static ArrayList<EntityLivingBase> getCloseEntities(boolean ignoreFriends, float range) {
      ArrayList<EntityLivingBase> closeEntities = new ArrayList<EntityLivingBase>();
      Iterator<?> var4 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var4.hasNext()) {
         Object o = var4.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		if(!(o instanceof EntityPlayerSP) && !entityplayer.isDead && entityplayer.getHealth() > 0.0F && Minecraft.thePlayer.canEntityBeSeen(entityplayer) && !entityplayer.getName().equals(Minecraft.thePlayer.getName()) && Minecraft.thePlayer.getDistanceToEntity(entityplayer) <= range) {
            closeEntities.add(entityplayer);
         }
      }

      return closeEntities;
   }

   public static EntityLivingBase getClosestEntityRaw(boolean ignoreFriends) {
      EntityLivingBase closestEntity = null;
      Iterator<?> var3 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		if(!(o instanceof EntityPlayerSP) && !entityplayer.isDead && entityplayer.getHealth() > 0.0F && (closestEntity == null || Minecraft.thePlayer.getDistanceToEntity(entityplayer) < Minecraft.thePlayer.getDistanceToEntity(closestEntity))) {
            closestEntity = entityplayer;
         }
      }

      return closestEntity;
   }

   public static EntityLivingBase getClosestEnemy(EntityLivingBase friend) {
      EntityLivingBase closestEnemy = null;
      Iterator<?> var3 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		if(!(o instanceof EntityPlayerSP) && o != friend && !entityplayer.isDead && entityplayer.getHealth() > 0.0F && Minecraft.thePlayer.canEntityBeSeen(entityplayer) && (closestEnemy == null || Minecraft.thePlayer.getDistanceToEntity(entityplayer) < Minecraft.thePlayer.getDistanceToEntity(closestEnemy))) {
            closestEnemy = entityplayer;
         }
      }

      return closestEnemy;
   }

   public static EntityLivingBase searchEntityByIdRaw(UUID ID) {
      EntityLivingBase newEntity = null;
      Iterator<?> var3 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         if(!(o instanceof EntityPlayerSP) && !entityplayer.isDead && newEntity == null && entityplayer.getUniqueID().equals(ID)) {
            newEntity = entityplayer;
         }
      }

      return newEntity;
   }

   public static EntityLivingBase searchEntityByName(String name) {
      EntityLivingBase newEntity = null;
      Iterator<?> var3 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         Minecraft.getMinecraft();
		if(!(o instanceof EntityPlayerSP) && !entityplayer.isDead && Minecraft.thePlayer.canEntityBeSeen(entityplayer) && newEntity == null && entityplayer.getName().equals(name)) {
            newEntity = entityplayer;
         }
      }

      return newEntity;
   }

   public static EntityLivingBase searchEntityByNameRaw(String name) {
      EntityLivingBase newEntity = null;
      Iterator<?> var3 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         EntityLivingBase entityplayer = (EntityLivingBase)o;
         if(!(o instanceof EntityPlayerSP) && !entityplayer.isDead && newEntity == null && entityplayer.getName().equals(name)) {
            newEntity = entityplayer;
         }
      }

      return newEntity;
   }
   private static boolean set = false;
   private static EntityPlayer reference;
   
   
}
