package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;

public class DeathClip extends Module {
   public static String DIST = "DIST";
   public static String CLIP = "CLIP";
   public static String MESSAGE = "MESSAGE";
   boolean dead;
   public int waitTicks = 0;

   public DeathClip(ModuleData data) {
      super(data);
      this.settings.put(CLIP, new Setting(CLIP, true, "Vertical Clip."));
      this.settings.put(DIST, new Setting(DIST, 2.0D, "Distance to clip.", 1.0D, -10.0D, 10.0D));
      this.settings.put(MESSAGE, new Setting(MESSAGE, "/sethome", "Command to execute after clipping."));
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      EventMotionUpdate em = (EventMotionUpdate)event;
      boolean vclip = ((Boolean)((Setting)this.settings.get(CLIP)).getValue()).booleanValue();
      float distance = ((Number)((Setting)this.settings.get(DIST)).getValue()).floatValue();
      if (em.isPre()) {
         if (vclip && mc.thePlayer.getHealth() == 0.0F && !this.dead) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + (double)distance, mc.thePlayer.posZ);
            ++this.waitTicks;
            this.dead = true;
         } else if (mc.thePlayer.getHealth() == 0.0F && !this.dead) {
            float yaw = mc.thePlayer.rotationYaw;
            mc.thePlayer.setPosition(mc.thePlayer.posX + (double)(distance * 2.0F) * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + 0.0D * Math.sin(Math.toRadians((double)(yaw + 90.0F))), mc.thePlayer.posY + 0.0010000000474974513D, mc.thePlayer.posZ + ((double)(distance * 2.0F) * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - 0.0D * Math.cos(Math.toRadians((double)(yaw + 90.0F)))));
            ++this.waitTicks;
            this.dead = true;
         }

         if (this.waitTicks > 0) {
            ++this.waitTicks;
            if (this.waitTicks >= 4) {
               ChatUtil.sendChat(((String)((Setting)this.settings.get(MESSAGE)).getValue()).toString());
               this.waitTicks = 0;
            }
         }

         if (mc.thePlayer.getHealth() > 0.0F) {
            this.dead = false;
         }
      }

   }
}
