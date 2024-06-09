package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.RotationUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;

public class BowAimbot extends Module {
   boolean send;
   boolean isFiring;

   public BowAimbot(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            EntityLivingBase target = this.getTarg();
            if (mc.thePlayer.isUsingItem() && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && target != mc.thePlayer && target != null) {
               float[] rotations = RotationUtils.getBowAngles(target);
               em.setYaw(mc.thePlayer.rotationYaw = rotations[0]);
               em.setPitch(mc.thePlayer.rotationPitch = rotations[1]);
            }
         }
      }

   }

   private EntityLivingBase getTarg() {
      List<EntityLivingBase> loaded = new ArrayList();
      Iterator var2 = mc.theWorld.getLoadedEntityList().iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)o;
            if (ent instanceof EntityPlayer && mc.thePlayer.canEntityBeSeen(ent) && !FriendManager.isFriend(ent.getName())) {
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
         loaded.sort((o1, o2) -> {
            float[] rot1 = RotationUtils.getRotations(o1);
            float[] rot2 = RotationUtils.getRotations(o2);
            return (int)(RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot1[0]) + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot1[1]) - (RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot2[0]) + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot2[1])));
         });
         EntityLivingBase target = (EntityLivingBase)loaded.get(0);
         return target;
      }
   }
}
