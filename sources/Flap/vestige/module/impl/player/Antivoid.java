package vestige.module.impl.player;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.movement.Fly;
import vestige.module.impl.movement.Longjump;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.misc.LogUtil;
import vestige.util.world.WorldUtil;

public class Antivoid extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Flag", new String[]{"Flag", "Collision flag", "Blink", "Bounce"});
   private final BooleanSetting stopHorizontalMove = new BooleanSetting("Stop horizontal move", () -> {
      return this.mode.is("Blink");
   }, false);
   private final DoubleSetting bounceMotion = new DoubleSetting("Bounce motion", () -> {
      return this.mode.is("Bounce");
   }, 1.5D, 0.4D, 3.0D, 0.1D);
   private final DoubleSetting minFallDist = new DoubleSetting("Min fall dist", 3.5D, 2.0D, 10.0D, 0.25D);
   private Antivoid.PlayerInfo lastSafePos;
   private BlockPos collisionBlock;
   private boolean blinking;
   private Fly flyModule;
   private Longjump longjumpModule;
   private boolean receivedLagback;

   public Antivoid() {
      super("Antivoid", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.mode, this.stopHorizontalMove, this.bounceMotion, this.minFallDist});
   }

   public void onEnable() {
      this.collisionBlock = null;
      this.lastSafePos = new Antivoid.PlayerInfo(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround, mc.thePlayer.fallDistance, mc.thePlayer.inventory.currentItem);
   }

   public boolean onDisable() {
      if (this.blinking) {
         Flap.instance.getPacketBlinkHandler().stopAll();
         LogUtil.addChatMessage("Stopped blinking");
         this.blinking = false;
      }

      this.receivedLagback = false;
      return false;
   }

   public void onClientStarted() {
      this.flyModule = (Fly)Flap.instance.getModuleManager().getModule(Fly.class);
      this.longjumpModule = (Longjump)Flap.instance.getModuleManager().getModule(Longjump.class);
   }

   @Listener(0)
   public void onTick(TickEvent event) {
      if (mc.thePlayer.ticksExisted < 10) {
         this.collisionBlock = null;
      } else {
         String var2 = this.mode.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1392696390:
            if (var2.equals("Collision flag")) {
               var3 = 1;
            }
            break;
         case 64274236:
            if (var2.equals("Blink")) {
               var3 = 2;
            }
            break;
         case 1995629224:
            if (var2.equals("Bounce")) {
               var3 = 0;
            }
         }

         switch(var3) {
         case 0:
            if (this.shouldSetback() && mc.thePlayer.motionY < -0.1D) {
               mc.thePlayer.motionY = this.bounceMotion.getValue();
            }
            break;
         case 1:
            if (this.shouldSetback()) {
               if (this.collisionBlock != null) {
                  mc.theWorld.setBlockToAir(this.collisionBlock);
               }

               this.collisionBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
               mc.theWorld.setBlockState(this.collisionBlock, Blocks.barrier.getDefaultState());
            } else if (this.collisionBlock != null) {
               mc.theWorld.setBlockToAir(this.collisionBlock);
               this.collisionBlock = null;
            }
            break;
         case 2:
            if (this.isSafe()) {
               this.lastSafePos = new Antivoid.PlayerInfo(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround, mc.thePlayer.fallDistance, mc.thePlayer.inventory.currentItem);
               this.receivedLagback = false;
               if (this.blinking) {
                  Flap.instance.getPacketBlinkHandler().stopAll();
                  this.blinking = false;
               }
            } else if (!this.receivedLagback) {
               if (this.shouldSetback()) {
                  if (this.blinking) {
                     mc.thePlayer.setPosition(this.lastSafePos.x, this.lastSafePos.y, this.lastSafePos.z);
                     if (this.stopHorizontalMove.isEnabled()) {
                        mc.thePlayer.motionX = 0.0D;
                        mc.thePlayer.motionZ = 0.0D;
                     } else {
                        mc.thePlayer.motionX = this.lastSafePos.motionX;
                        mc.thePlayer.motionZ = this.lastSafePos.motionZ;
                     }

                     mc.thePlayer.motionY = this.lastSafePos.motionY;
                     mc.thePlayer.rotationYaw = this.lastSafePos.yaw;
                     mc.thePlayer.rotationPitch = this.lastSafePos.pitch;
                     mc.thePlayer.onGround = this.lastSafePos.onGround;
                     mc.thePlayer.fallDistance = this.lastSafePos.fallDist;
                     mc.thePlayer.inventory.currentItem = this.lastSafePos.itemSlot;
                     mc.playerController.currentPlayerItem = this.lastSafePos.itemSlot;
                     Flap.instance.getPacketBlinkHandler().releasePing();
                     Flap.instance.getPacketBlinkHandler().clearMove();
                     Flap.instance.getPacketBlinkHandler().clearOther();
                  }
               } else if (!this.blinking) {
                  Flap.instance.getPacketBlinkHandler().startBlinkingAll();
                  this.blinking = true;
               }
            }
         }

      }
   }

   @Listener(4)
   public void onMotion(MotionEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 2192268:
         if (var2.equals("Flag")) {
            var3 = 0;
         }
      default:
         switch(var3) {
         case 0:
            if (this.shouldSetback()) {
               event.setY(event.getY() + 8.0D + Math.random());
            }
         default:
         }
      }
   }

   @Listener
   public void onReceive(PacketReceiveEvent event) {
      if (event.getPacket() instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
         if (this.mode.is("Blink") && this.blinking) {
            mc.thePlayer.onGround = false;
            mc.thePlayer.fallDistance = this.lastSafePos.fallDist;
            mc.thePlayer.inventory.currentItem = this.lastSafePos.itemSlot;
            mc.playerController.currentPlayerItem = this.lastSafePos.itemSlot;
            Flap.instance.getPacketBlinkHandler().releasePing();
            Flap.instance.getPacketBlinkHandler().clearMove();
            Flap.instance.getPacketBlinkHandler().clearOther();
            Flap.instance.getPacketBlinkHandler().stopAll();
            this.lastSafePos = new Antivoid.PlayerInfo(packet.getX(), packet.getY(), packet.getZ(), 0.0D, 0.0D, 0.0D, packet.getYaw(), packet.getPitch(), false, mc.thePlayer.fallDistance, mc.thePlayer.inventory.currentItem);
            this.blinking = false;
            this.receivedLagback = true;
         }
      }

   }

   private boolean shouldSetback() {
      return (double)mc.thePlayer.fallDistance >= this.minFallDist.getValue() && !WorldUtil.isBlockUnder() && mc.thePlayer.ticksExisted >= 100;
   }

   private boolean isSafe() {
      return WorldUtil.isBlockUnder() || !mc.getNetHandler().doneLoadingTerrain || mc.thePlayer.ticksExisted < 100 || this.flyModule.isEnabled() || this.longjumpModule.isEnabled();
   }

   public boolean isBlinking() {
      return this.blinking;
   }

   private class PlayerInfo {
      private final double x;
      private final double y;
      private final double z;
      private final double motionX;
      private final double motionY;
      private final double motionZ;
      private final float yaw;
      private final float pitch;
      private final boolean onGround;
      private final float fallDist;
      private final int itemSlot;

      private PlayerInfo(double param2, double param4, double param6, double param8, double param10, double param12, float param14, float param15, boolean param16, float param17, int param18) {
         this.x = param2;
         this.y = param4;
         this.z =param6;
         this.motionX = param8;
         this.motionY = param10;
         this.motionZ = param12;
         this.yaw = param14;
         this.pitch = param15;
         this.onGround = param16;
         this.fallDist = param17;
         this.itemSlot = param18;
      }

      // $FF: synthetic method
      PlayerInfo(double x1, double x2, double x3, double x4, double x5, double x6, float x7, float x8, boolean x9, float x10, int x11, Object x12) {
         this(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11);
      }
   }
}
