package net.augustus.modules.combat;

import java.awt.Color;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class MoreKB extends Module {
   public StringValue mode = new StringValue(1, "Modes", this, "Legit", new String[]{"Legit", "LessPacket", "Packet", "DoublePacket", "MorePacket"});
   public DoubleValue idkWhatToCallThisLmao = new DoubleValue(42069, "Packets", this, 5, 3, 10, 0);
   public BooleanValue intelligent = new BooleanValue(1, "Intelligent", this, false);
   private boolean shouldSprintReset = false;

   public MoreKB() {
      super("MoreKB", new Color(252, 73, 3), Categorys.COMBAT);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      EntityLivingBase entity = null;
      if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
         entity = (EntityLivingBase)mc.objectMouseOver.entityHit;
      }

      if (mm.killAura.isToggled() && mm.killAura.target != null) {
         entity = mm.killAura.target;
      }

      if (entity != null) {
         double x = mc.thePlayer.posX - entity.posX;
         double z = mc.thePlayer.posZ - entity.posZ;
         float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
         float diffY = Math.abs(MathHelper.wrapAngleTo180_float(calcYaw - entity.rotationYawHead));
         if (!this.intelligent.getBoolean() || !(diffY > 120.0F)) {
            String var9 = this.mode.getSelected();
            switch(var9) {
               case "MorePacket": {
                  if (entity.hurtTime == 10) {
                     for (int i = 0; i < idkWhatToCallThisLmao.getValue(); i++) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                     }
                     mc.thePlayer.setServerSprintState(true);
                  }
                  break;
               }
               case "Packet":
                  if (entity.hurtTime == 10) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                     mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                     mc.thePlayer.setServerSprintState(true);
                  }
                  break;
               case "DoublePacket":
                  if (entity.hurtTime == 10) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                     mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                     mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                     mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                     mc.thePlayer.setServerSprintState(true);
                  }
                  break;
               case "Legit":
                  if (entity.hurtTime == 10) {
                     this.shouldSprintReset = true;
                     mc.thePlayer.reSprint = 2;
                     this.shouldSprintReset = false;
                     System.out.println("Resprint   " + entity.hurtTime);
                  }
                  break;
               case "LessPacket":
                  if (entity.hurtTime == 10) {
                     if (mc.thePlayer.isSprinting()) {
                        mc.thePlayer.setSprinting(false);
                     }

                     mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                     mc.thePlayer.setServerSprintState(true);
                  }
            }
         }
      }
   }
}
