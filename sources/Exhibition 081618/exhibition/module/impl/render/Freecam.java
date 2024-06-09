package exhibition.module.impl.render;

import com.mojang.authlib.GameProfile;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventBlockBounds;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMove;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventPushBlock;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class Freecam extends Module {
   public static final String SPEED = "SPEED";
   private EntityOtherPlayerMP freecamEntity;

   public Freecam(ModuleData data) {
      super(data);
      this.settings.put("SPEED", new Setting("SPEED", 1.0D, "Movement speed."));
   }

   public void onDisable() {
      mc.thePlayer.setPositionAndRotation(this.freecamEntity.posX, this.freecamEntity.posY, this.freecamEntity.posZ, this.freecamEntity.rotationYaw, this.freecamEntity.rotationPitch);
      mc.theWorld.removeEntityFromWorld(this.freecamEntity.getEntityId());
      mc.renderGlobal.loadRenderers();
      mc.thePlayer.noClip = false;
      super.onDisable();
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         this.freecamEntity = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(new UUID(69L, 96L), "XDDD"));
         this.freecamEntity.inventory = mc.thePlayer.inventory;
         this.freecamEntity.inventoryContainer = mc.thePlayer.inventoryContainer;
         this.freecamEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
         this.freecamEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
         mc.theWorld.addEntityToWorld(this.freecamEntity.getEntityId(), this.freecamEntity);
         mc.renderGlobal.loadRenderers();
         super.onEnable();
      }
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
         int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class, EventBlockBounds.class, EventMove.class, EventPushBlock.class}
   )
   public void onEvent(Event event) {
      float speed = ((Number)((Setting)this.settings.get("SPEED")).getValue()).floatValue();
      if (event instanceof EventMotionUpdate) {
         mc.thePlayer.noClip = true;
      }

      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event;
         if (ep.isOutgoing()) {
            if (ep.getPacket() instanceof C03PacketPlayer) {
               C03PacketPlayer packet = (C03PacketPlayer)ep.getPacket();
               packet.yaw = this.freecamEntity.rotationYaw;
               packet.pitch = this.freecamEntity.rotationPitch;
               packet.x = this.freecamEntity.posX;
               packet.y = this.freecamEntity.posY;
               packet.z = this.freecamEntity.posZ;
               packet.onGround = this.freecamEntity.onGround;
            } else if (ep.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
               C03PacketPlayer.C04PacketPlayerPosition packet = (C03PacketPlayer.C04PacketPlayerPosition)ep.getPacket();
               packet.yaw = this.freecamEntity.rotationYaw;
               packet.pitch = this.freecamEntity.rotationPitch;
               packet.x = this.freecamEntity.posX;
               packet.y = this.freecamEntity.posY;
               packet.z = this.freecamEntity.posZ;
               packet.onGround = this.freecamEntity.onGround;
            } else if (ep.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
               C03PacketPlayer.C05PacketPlayerLook packet = (C03PacketPlayer.C05PacketPlayerLook)ep.getPacket();
               packet.yaw = this.freecamEntity.rotationYaw;
               packet.pitch = this.freecamEntity.rotationPitch;
               packet.x = this.freecamEntity.posX;
               packet.y = this.freecamEntity.posY;
               packet.z = this.freecamEntity.posZ;
               packet.onGround = this.freecamEntity.onGround;
            }
         }
      }

      if (event instanceof EventBlockBounds) {
         EventBlockBounds ebb = (EventBlockBounds)event;
         ebb.setBounds((AxisAlignedBB)null);
      }

      if (event instanceof EventMove) {
         EventMove em = (EventMove)event;
         if (mc.thePlayer.movementInput.jump) {
            em.setY(mc.thePlayer.motionY = (double)speed);
         } else if (mc.thePlayer.movementInput.sneak) {
            em.setY(mc.thePlayer.motionY = (double)(-speed));
         } else {
            em.setY(mc.thePlayer.motionY = 0.0D);
         }

         speed = (float)Math.max((double)speed, getBaseMoveSpeed());
         double forward = (double)mc.thePlayer.movementInput.moveForward;
         double strafe = (double)mc.thePlayer.movementInput.moveStrafe;
         float yaw = mc.thePlayer.rotationYaw;
         if (forward == 0.0D && strafe == 0.0D) {
            em.setX(0.0D);
            em.setZ(0.0D);
         } else {
            if (forward != 0.0D) {
               if (strafe > 0.0D) {
                  strafe = 1.0D;
                  yaw += (float)(forward > 0.0D ? -45 : 45);
               } else if (strafe < 0.0D) {
                  yaw += (float)(forward > 0.0D ? 45 : -45);
               }

               strafe = 0.0D;
               if (forward > 0.0D) {
                  forward = 1.0D;
               } else {
                  forward = -1.0D;
               }
            }

            em.setX(forward * (double)speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * (double)speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
            em.setZ(forward * (double)speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * (double)speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
         }
      }

      if (event instanceof EventPushBlock) {
         EventPushBlock ebp = (EventPushBlock)event;
         ebp.setCancelled(true);
      }

   }
}
