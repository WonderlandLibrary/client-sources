package exhibition.module.impl.movement;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventSystem;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMove;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.impl.player.Scaffold;
import exhibition.util.MathUtils;
import exhibition.util.PlayerUtil;

public class LongJump extends Module {
   private String OFF = "TOGGLE";
   private String AUTISM = "AUTISM";
   private String NIGGERBOOST = "AUT-BOOST";
   private boolean wasOnGround;
   private int stupidAutisticTickCounting;
   private double speed;
   private boolean onGroundLastTick;
   private double distance;
   private final String BOOST = "BOOST";

   public LongJump(ModuleData data) {
      super(data);
      this.settings.put(this.OFF, new Setting(this.OFF, true, "Toggles off on landing."));
      this.settings.put("BOOST", new Setting("BOOST", Integer.valueOf(3), "Boost speed.", 0.1D, 3.0D, 5.0D));
      this.settings.put(this.NIGGERBOOST, new Setting(this.NIGGERBOOST, Integer.valueOf(1), "Incase you want your chromosomes to dissipate faster.", 0.5D, 1.0D, 2.5D));
      this.settings.put(this.AUTISM, new Setting(this.AUTISM, false, "\"Wow arithmo i like how flokcks go ZOOM make me ZOOOM.\" - Brain dead Kid"));
      this.speed = 0.27999999999999997D;
      this.onGroundLastTick = false;
      this.distance = 0.0D;
   }

   public void onDisable() {
      if (mc.thePlayer != null) {
         mc.timer.timerSpeed = 1.0F;
      }
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         this.stupidAutisticTickCounting = -1;
         mc.timer.timerSpeed = 1.0F;
         this.speed = 0.27999999999999997D;
         this.onGroundLastTick = false;
         this.distance = 0.0D;
         this.wasOnGround = false;
         Module[] modules = new Module[]{(Module)Client.getModuleManager().get(Fly.class), (Module)Client.getModuleManager().get(Speed.class), (Module)Client.getModuleManager().get(Scaffold.class)};
         Module[] var2 = modules;
         int var3 = modules.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Module module = var2[var4];
            if (module.isEnabled()) {
               module.toggle();
               Notifications.getManager().post("Movement Check", "Disabled extra modules.", 250L, Notifications.Type.NOTIFY);
            }
         }

      }
   }

   @RegisterEvent(
      events = {EventMove.class, EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      boolean autism = ((Boolean)((Setting)this.settings.get(this.AUTISM)).getValue()).booleanValue();
      if (event instanceof EventMove) {
         EventMove em = (EventMove)event;
         if (autism) {
            em.setY(mc.thePlayer.motionY = 0.0D);
            float boost = (float)MathUtils.getIncremental((double)((Number)((Setting)this.settings.get(this.NIGGERBOOST)).getValue()).floatValue(), 0.5D);
            ++this.stupidAutisticTickCounting;
            mc.timer.timerSpeed = (float)this.stupidAutisticTickCounting + boost;
            if (this.stupidAutisticTickCounting == 1) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 9.38142345431554E-13D, mc.thePlayer.posZ);
            }

            if (this.stupidAutisticTickCounting == 2) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 9.34867523765344E-13D, mc.thePlayer.posZ);
            }

            if (this.stupidAutisticTickCounting == 3) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.8730098691969E-12D, mc.thePlayer.posZ);
               this.stupidAutisticTickCounting = 0;
            }
         } else {
            double boost = ((Number)((Setting)this.settings.get("BOOST")).getValue()).doubleValue();
            if (mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F || mc.theWorld == null || PlayerUtil.isOnLiquid() || PlayerUtil.isInLiquid()) {
               this.speed = 0.27999999999999997D;
               return;
            }

            if (mc.thePlayer.onGround) {
               if (!this.onGroundLastTick && mc.thePlayer.motionY >= -0.3D) {
                  this.speed = boost * 0.27999999999999997D;
               } else {
                  this.speed *= 2.15D - 1.0D / Math.pow(10.0D, 5.0D);
                  em.setY(mc.thePlayer.motionY = 0.41999998688697815D);
                  mc.thePlayer.onGround = true;
               }
            } else if (this.onGroundLastTick) {
               if (this.distance < 2.147D) {
                  this.distance = 2.147D;
               }

               this.speed = this.distance - 0.66D * (this.distance - 0.27999999999999997D);
            } else {
               this.speed = this.distance - this.distance / 159.0D;
            }

            this.onGroundLastTick = mc.thePlayer.onGround;
            this.speed = Math.max(this.speed, 0.27999999999999997D);
            em.setX(-(Math.sin((double)mc.thePlayer.getDirection()) * this.speed));
            em.setZ(Math.cos((double)mc.thePlayer.getDirection()) * this.speed);
         }
      } else if (event instanceof EventMotionUpdate && !autism) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            this.distance = Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ);
         }
      }

      if (((Boolean)((Setting)this.settings.get(this.OFF)).getValue()).booleanValue() && !autism) {
         if (!this.onGroundLastTick && mc.thePlayer.isCollidedVertically && this.wasOnGround && this.isEnabled()) {
            this.toggle();
            EventSystem.unregister(this);
         }

         if (!this.wasOnGround && PlayerUtil.isMoving()) {
            this.wasOnGround = true;
         }
      }

   }
}
