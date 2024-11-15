package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;

public class AimWarning extends Module {

   public AimWarning(ModuleData data) {
      super(data);
   }

   @RegisterEvent(events = { EventMotionUpdate.class })
   @Override
   public void onEvent(Event event) {
	   if(event instanceof EventMotionUpdate) {
		   for(Object o : mc.theWorld.loadedEntityList){
   			Entity en = (Entity)o;
   				if(isAimAtMe(en)) {
   	   				ChatUtil.printChat(en.getName() + "正在看你");
   	   			}
		   }
	   }   
   }
   
   public static boolean isAimAtMe(Entity entity) {
       float entityYaw = getNormalizedYaw(entity.rotationYaw);
       float entityPitch = entity.rotationPitch;
       double pMinX = mc.thePlayer.getEntityBoundingBox().minX;
       double pMaxX = mc.thePlayer.getEntityBoundingBox().maxX;
       double pMaxY = mc.thePlayer.posY + (double)mc.thePlayer.height;
       double pMinY = mc.thePlayer.getEntityBoundingBox().minY;
       double pMaxZ = mc.thePlayer.getEntityBoundingBox().maxZ;
       double pMinZ = mc.thePlayer.getEntityBoundingBox().minZ;
       double eX = entity.posX;
       double eY = entity.posY + (double)(entity.height / 2.0f);
       double eZ = entity.posZ;
       double dMaxX = pMaxX - eX;
       double dMaxY = pMaxY - eY;
       double dMaxZ = pMaxZ - eZ;
       double dMinX = pMinX - eX;
       double dMinY = pMinY - eY;
       double dMinZ = pMinZ - eZ;
       double dMinH = Math.sqrt(Math.pow(dMinX, 2.0) + Math.pow(dMinZ, 2.0));
       double dMaxH = Math.sqrt(Math.pow(dMaxX, 2.0) + Math.pow(dMaxZ, 2.0));
       double maxPitch = 90 - Math.toDegrees(Math.atan2(dMaxH, dMaxY));
       double minPitch = 90 -Math.toDegrees(Math.atan2(dMinH, dMinY));
       boolean yawAt = Math.abs(getNormalizedYaw(getYawToEntity(entity,mc.thePlayer)) - entityYaw)
               <= (16 - mc.thePlayer.getDistanceToEntity(entity) / 2 + mc.thePlayer.hurtResistantTime / 4);
       boolean pitchAt = (maxPitch >= entityPitch && entityPitch >= minPitch)
               || (minPitch >= entityPitch && entityPitch >= maxPitch);
       return yawAt && pitchAt;
   }
   
   public static float getYawToEntity(Entity mainEntity,Entity targetEntity){
       double pX = mainEntity.posX;
       double pZ = mainEntity.posZ;
       double eX = targetEntity.posX;
       double eZ = targetEntity.posZ;
       double dX = pX - eX;
       double dZ = pZ - eZ;
       double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
       return (float)yaw;
   }
   
   public static float getNormalizedYaw(float yaw){
       float yawStageFirst = yaw % 360;
       if(yawStageFirst > 180){
           yawStageFirst -= 360;
           return yawStageFirst;
       }
       if(yawStageFirst < -180){
           yawStageFirst += 360;
           return yawStageFirst;
       }
       return yawStageFirst;
   }
}
