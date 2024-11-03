package net.augustus.modules.player;

import java.awt.Color;

import net.augustus.Augustus;
import net.augustus.events.*;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.ChatUtil;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.skid.azura.CustomVec3;
import net.augustus.utils.skid.azura.Path;
import net.augustus.utils.skid.azura.PathUtil;
import net.augustus.utils.skid.azura.RaytraceUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Teleport extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   public StringValue mode = new StringValue(0, "Mode", this, "Vulcan", new String[]{"DEV", "Karhu", "Vulcan", "Vanilla", "SpartanB453"});
   public BooleanValue autoDisable = new BooleanValue(1, "AutoDisable", this, true);
   private double[] xyz = new double[3];
   private boolean shouldTeleport;
   private boolean teleported = false;

   public Teleport() {
      super("Teleport", new Color(141, 232, 5), Categorys.PLAYER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.xyz = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
      this.shouldTeleport = false;
      this.teleported = false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.xyz = new double[3];
      this.shouldTeleport = false;
   }
   @EventTarget
   public void onMove(EventPreMotion e) {
      this.setDisplayName(super.getName() + " §8" + this.mode.getSelected());
      if (mc.gameSettings.keyBindAttack.isKeyDown()
              && mc.objectMouseOver != null
              && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
              && this.xyz[0] == Double.POSITIVE_INFINITY) {
         BlockPos blockPos = mc.objectMouseOver.getBlockPos();
         Block block = mc.theWorld.getBlockState(blockPos).getBlock();
         this.xyz = new double[]{
                 (double)mc.objectMouseOver.getBlockPos().getX() + 0.5,
                 (double)mc.objectMouseOver.getBlockPos().getY() + block.getBlockBoundsMaxY(),
                 (double)mc.objectMouseOver.getBlockPos().getZ() + 0.5
         };
         this.shouldTeleport = true;
         this.timeHelper.reset();
      }

      if (this.shouldTeleport) {
         String var4 = this.mode.getSelected();
         switch(var4) {
            case "SpartanB453": {
               final MovingObjectPosition position = RaytraceUtil.rayTrace(200, e.getYaw(), e.getPitch());
               if (mc.gameSettings.keyBindAttack.pressed && position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !mc.theWorld.isAirBlock(position.getBlockPos())) {
                  mc.gameSettings.keyBindAttack.pressed = false;
                  if (!mc.thePlayer.onGround) {
                     ChatUtil.sendChat("Please stand on ground when selecting...");
                     return;
                  }
                  final CustomVec3 target = new CustomVec3(position.getBlockPos());
                  target.setY(mc.thePlayer.posY);
                  Path path = PathUtil.findPath(new CustomVec3(mc.thePlayer), target, 0.15);
                  for (CustomVec3 v : path.getAdditionalVectors())
                     mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer.C04PacketPlayerPosition(e.x = v.getX(), e.y = v.getY(), e.z = v.getZ(), true));
                  mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer.C04PacketPlayerPosition(e.x, e.y + 5000, e.z, true));
                  mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer.C04PacketPlayerPosition(e.x, e.y, e.z, true));
               }
               break;
            }
         }
      }

      if (this.autoDisable.getBoolean() && this.teleported) {
         this.toggle();
      }
   }

   @EventTarget
   public void onEventTick(EventUpdate eventUpdate) {
      this.setDisplayName(super.getName() + " §8" + this.mode.getSelected());
      if (mc.gameSettings.keyBindAttack.isKeyDown()
         && mc.objectMouseOver != null
         && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
         && this.xyz[0] == Double.POSITIVE_INFINITY) {
         BlockPos blockPos = mc.objectMouseOver.getBlockPos();
         Block block = mc.theWorld.getBlockState(blockPos).getBlock();
         this.xyz = new double[]{
            (double)mc.objectMouseOver.getBlockPos().getX() + 0.5,
            (double)mc.objectMouseOver.getBlockPos().getY() + block.getBlockBoundsMaxY(),
            (double)mc.objectMouseOver.getBlockPos().getZ() + 0.5
         };
         this.shouldTeleport = true;
         this.timeHelper.reset();
      }

      if (this.shouldTeleport) {
         String var4 = this.mode.getSelected();
         switch(var4) {
            case "Karhu":
               if (!mc.thePlayer.onGround) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.xyz[0], this.xyz[1] + 0.3, this.xyz[2], true));
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.xyz[0], this.xyz[1] + 0.3, this.xyz[2], false));
                  this.shouldTeleport = false;
                  this.teleported = true;
                  this.xyz = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
               }

               if (mc.thePlayer.onGround) {
                  mc.thePlayer.jump();
               }
               break;
            case "Vanilla":
               mc.thePlayer.setPosition(this.xyz[0], this.xyz[1] + 0.3, this.xyz[2]);
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.xyz[0], this.xyz[1] + 0.3, this.xyz[2], false));
               this.shouldTeleport = false;
               this.teleported = true;
         }
      }

      if (this.autoDisable.getBoolean() && this.teleported) {
         this.toggle();
      }
   }

   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      String var3 = this.mode.getSelected();
      byte var4 = -1;
      switch(var3.hashCode()) {
         case -1721492669:
            if (var3.equals("Vulcan")) {
               var4 = 0;
            }
         default:
            switch(var4) {
               case 0:
                  if (packet instanceof S08PacketPlayerPosLook && this.shouldTeleport) {
                     S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook)packet;
                     if (s08PacketPlayerPosLook.getX() == this.xyz[0]
                        && s08PacketPlayerPosLook.getY() == this.xyz[1]
                        && s08PacketPlayerPosLook.getZ() == this.xyz[2]) {
                        this.shouldTeleport = false;
                        this.teleported = true;
                        this.xyz = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
                     }
                  }
            }
      }
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      String var3 = this.mode.getSelected();
      byte var4 = -1;
      switch(var3.hashCode()) {
         case -1721492669:
            if (var3.equals("Vulcan")) {
               var4 = 0;
            }
         default:
            switch(var4) {
               case 0:
                  if (packet instanceof C03PacketPlayer && this.shouldTeleport) {
                     C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)packet;
                     c03PacketPlayer.setX(this.xyz[0]);
                     c03PacketPlayer.setY(this.xyz[1]);
                     c03PacketPlayer.setZ(this.xyz[2]);
                     mc.thePlayer.setPosition(this.xyz[0], this.xyz[1], this.xyz[2]);
                     mc.thePlayer.onGround = true;
                     if (this.timeHelper.reached(1000L)) {
                        this.shouldTeleport = false;
                        this.teleported = true;
                        this.xyz = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
                     }
                  }
            }
      }
   }
}
