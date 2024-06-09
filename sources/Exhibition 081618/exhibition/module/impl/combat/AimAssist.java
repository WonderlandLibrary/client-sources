package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RotationUtils;
import exhibition.util.misc.ChatUtil;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class AimAssist extends Module {
   private EntityLivingBase target;
   private String HEIGHT = "HEIGHT";
   private String WEAPON = "WEAPON";
   private String RANGE = "RANGE";
   private String HORIZONTAL = "SPEED-H";
   private String VERTICAL = "SPEED-V";
   private String FOVYAW = "FOVYAW";
   private String FOVPITCH = "FOVPITCH";
   private String X = "RANDOM-XZ";
   private String Y = "RANDOM-Y";

   public AimAssist(ModuleData data) {
      super(data);
      this.settings.put(this.WEAPON, new Setting(this.WEAPON, true, "Checks if you have a sword in hand."));
      this.settings.put(this.X, new Setting(this.X, Integer.valueOf(0), "Randomization on XZ axis.", 0.1D, 0.0D, 1.5D));
      this.settings.put(this.Y, new Setting(this.Y, Integer.valueOf(0), "Randomization on Y axis.", 0.1D, 0.0D, 1.5D));
      this.settings.put(this.RANGE, new Setting(this.RANGE, 4.5D, "The distance in which an entity is valid to attack.", 0.1D, 1.0D, 10.0D));
      this.settings.put(this.HORIZONTAL, new Setting(this.HORIZONTAL, Integer.valueOf(20), "Horizontal speed.", 0.25D, 0.0D, 10.0D));
      this.settings.put(this.VERTICAL, new Setting(this.VERTICAL, Integer.valueOf(15), "Vertical speed.", 0.25D, 0.0D, 10.0D));
      this.settings.put(this.FOVYAW, new Setting(this.FOVYAW, Integer.valueOf(45), "Yaw FOV check.", 1.0D, 5.0D, 50.0D));
      this.settings.put(this.FOVPITCH, new Setting(this.FOVPITCH, Integer.valueOf(25), "Vertical FOV check.", 1.0D, 5.0D, 50.0D));
      this.settings.put(this.HEIGHT, new Setting(this.HEIGHT, Integer.valueOf(0), "Adjust aim height.", 0.1D, -1.0D, 1.0D));
   }

   private int randomNumber() {
      return -100 + (int)(Math.random() * 201.0D);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            this.target = this.getBestEntity();
         } else if (em.isPost() && mc.currentScreen == null) {
            if (mc.thePlayer.getHeldItem() == null || mc.thePlayer.getHeldItem().getItem() == null) {
               return;
            }

            Item heldItem = mc.thePlayer.getHeldItem().getItem();
            if (((Boolean)((Setting)this.settings.get(this.WEAPON)).getValue()).booleanValue() && heldItem != null && !(heldItem instanceof ItemSword)) {
               return;
            }

            if (this.target != null && !FriendManager.isFriend(this.target.getName()) && mc.thePlayer.isEntityAlive()) {
               this.stepAngle();
            }
         }
      }

   }

   private void stepAngle() {
      float yawFactor = ((Number)((Setting)this.settings.get(this.HORIZONTAL)).getValue()).floatValue();
      float pitchFactor = ((Number)((Setting)this.settings.get(this.VERTICAL)).getValue()).floatValue();
      double xz = ((Number)((Setting)this.settings.get(this.X)).getValue()).doubleValue();
      double y = ((Number)((Setting)this.settings.get(this.Y)).getValue()).doubleValue();
      double yOff = ((Number)((Setting)this.settings.get(this.HEIGHT)).getValue()).doubleValue();
      float targetYaw = RotationUtils.getYawChange(this.target.posX + xz * (double)this.randomNumber() / 100.0D, this.target.posZ + xz * (double)this.randomNumber() / 100.0D);
      if (targetYaw > 0.0F && targetYaw > yawFactor) {
         mc.thePlayer.rotationYaw += yawFactor;
      } else if (targetYaw < 0.0F && targetYaw < -yawFactor) {
         mc.thePlayer.rotationYaw -= yawFactor;
      } else {
         mc.thePlayer.rotationYaw += targetYaw;
      }

      float targetPitch = RotationUtils.getPitchChange(this.target, this.target.posY + yOff + y * (double)this.randomNumber() / 100.0D);
      if (targetPitch > 0.0F && targetPitch > pitchFactor) {
         mc.thePlayer.rotationPitch += pitchFactor;
      } else if (targetPitch < 0.0F && targetPitch < -pitchFactor) {
         mc.thePlayer.rotationPitch -= pitchFactor;
      } else {
         mc.thePlayer.rotationPitch += targetPitch;
      }

   }

   private EntityLivingBase getBestEntity() {
      List<EntityLivingBase> loaded = new CopyOnWriteArrayList();
      Iterator var2 = mc.theWorld.getLoadedEntityList().iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)o;
            if (ent.isEntityAlive() && ent instanceof EntityPlayer && ent.getDistanceToEntity(mc.thePlayer) < ((Number)((Setting)this.settings.get(this.RANGE)).getValue()).floatValue() && this.fovCheck(ent)) {
               if (ent == Killaura.vip) {
                  return ent;
               }

               loaded.add(ent);
            }
         }
      }

      if (loaded.isEmpty()) {
         return null;
      } else {
         try {
            loaded.sort((o1, o2) -> {
               float[] rot1 = RotationUtils.getRotations(o1);
               float[] rot2 = RotationUtils.getRotations(o2);
               return (int)(RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot1[0]) + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot1[1]) - (RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot2[0]) + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot2[1])));
            });
         } catch (Exception var5) {
            ChatUtil.printChat("Exception with TM: " + var5.getMessage());
         }

         return (EntityLivingBase)loaded.get(0);
      }
   }

   private boolean fovCheck(EntityLivingBase ent) {
      float[] rotations = RotationUtils.getRotations(ent);
      float dist = mc.thePlayer.getDistanceToEntity(ent);
      if (dist == 0.0F) {
         dist = 1.0F;
      }

      float yawDist = RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rotations[0]);
      float pitchDist = RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rotations[1]);
      float fovYaw = ((Number)((Setting)this.settings.get(this.FOVYAW)).getValue()).floatValue() * 3.0F / dist;
      float fovPitch = ((Number)((Setting)this.settings.get(this.FOVPITCH)).getValue()).floatValue() * 3.0F / dist;
      return yawDist < fovYaw && pitchDist < fovPitch;
   }
}
