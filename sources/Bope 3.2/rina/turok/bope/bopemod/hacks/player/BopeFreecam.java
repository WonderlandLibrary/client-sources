package rina.turok.bope.bopemod.hacks.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventMove;
import rina.turok.bope.bopemod.events.BopeEventPacket;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilMath;

public class BopeFreecam extends BopeModule {
   BopeSetting speed_moveme = this.create("Speed Movement", "FreecamSpeedMovement", 1.0D, 1.0D, 4.0D);
   BopeSetting speed_updown = this.create("Speed Up/Down", "FreecamSpeedUpDown", 0.5D, 0.0D, 1.0D);
   double x;
   double y;
   double z;
   float pitch;
   float yaw;
   EntityOtherPlayerMP soul;
   Entity riding_entity;
   boolean is_riding;
   @EventHandler
   Listener<BopeEventMove> listener_move = new Listener<>(event -> {
      this.mc.player.noClip = true;
   });
   @EventHandler
   Listener<PlayerSPPushOutOfBlocksEvent> listener_push = new Listener<>(event -> {
      event.setCanceled(true);
   });
   @EventHandler
   Listener<BopeEventPacket.SendPacket> listener_packet = new Listener<>(event -> {
      if (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput) {
         event.cancel();
      }

   });

   public BopeFreecam() {
      super(BopeCategory.BOPE_PLAYER);
      this.name = "Freecam";
      this.tag = "Freecam";
      this.description = "Project Astral in Minecraft, a thing really insane.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void enable() {
      if (this.mc.player != null && this.mc.world != null) {
         this.is_riding = this.mc.player.getRidingEntity() != null;
         if (this.mc.player.getRidingEntity() == null) {
            this.x = this.mc.player.posX;
            this.y = this.mc.player.posY;
            this.z = this.mc.player.posZ;
         } else {
            this.riding_entity = this.mc.player.getRidingEntity();
            this.mc.player.dismountRidingEntity();
         }

         this.pitch = this.mc.player.rotationPitch;
         this.yaw = this.mc.player.rotationYaw;
         this.soul = new EntityOtherPlayerMP(this.mc.world, this.mc.getSession().getProfile());
         this.soul.copyLocationAndAnglesFrom(this.mc.player);
         this.soul.rotationYawHead = this.mc.player.rotationYawHead;
         this.mc.world.addEntityToWorld(-100, this.soul);
         this.mc.player.noClip = true;
      }

   }

   public void disable() {
      if (this.mc.player != null && this.mc.world != null) {
         this.mc.player.setPositionAndRotation(this.x, this.y, this.z, this.yaw, this.pitch);
         this.mc.world.removeEntityFromWorld(-100);
         this.soul = null;
         this.x = 0.0D;
         this.y = 0.0D;
         this.z = 0.0D;
         this.yaw = 0.0F;
         this.pitch = 0.0F;
         this.mc.player.motionX = this.mc.player.motionY = this.mc.player.motionZ = 0.0D;
         if (this.is_riding) {
            this.mc.player.startRiding(this.riding_entity, true);
         }
      }

   }

   public void update() {
      if (this.mc.player != null && this.mc.world != null) {
         this.mc.player.noClip = true;
         this.mc.player.setVelocity(0.0D, 0.0D, 0.0D);
         this.mc.player.renderArmPitch = 5000.0F;
         this.mc.player.jumpMovementFactor = 0.5F;
         double[] static_mov = BopeUtilMath.movement_speed(this.speed_moveme.get_value(1.0D) / 2.0D);
         if (this.mc.player.movementInput.moveStrafe == 0.0F && this.mc.player.movementInput.moveForward == 0.0F) {
            this.mc.player.motionX = 0.0D;
            this.mc.player.motionZ = 0.0D;
         } else {
            this.mc.player.motionX = static_mov[0];
            this.mc.player.motionZ = static_mov[1];
         }

         this.mc.player.setSprinting(false);
         EntityPlayerSP var10000;
         if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
            var10000 = this.mc.player;
            var10000.motionY += this.speed_updown.get_value(1.0D);
         }

         if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
            var10000 = this.mc.player;
            var10000.motionY -= this.speed_updown.get_value(1.0D);
         }
      }

   }
}
