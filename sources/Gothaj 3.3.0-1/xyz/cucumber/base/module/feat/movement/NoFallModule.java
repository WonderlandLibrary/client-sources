package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAirCollide;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to not take fall damage",
   name = "No Fall",
   key = 0
)
public class NoFallModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Clutch", "Verus", "Vulcan", "Hypixel Timer", "Spoof", "Grim"});
   public Timer timer = new Timer();
   public boolean canWork;
   public boolean pickup;

   public NoFallModule() {
      this.addSettings(new ModuleSettings[]{this.mode});
   }

   @EventListener(EventPriority.HIGHEST)
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onLook(EventLook e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1357340883:
            if (var2.equals("clutch")) {
               if (this.mc.thePlayer.fallDistance > 2.9F) {
                  int item = InventoryUtils.getBucketSlot();
                  if (item == -1) {
                     item = InventoryUtils.getCobwebSlot();
                  }

                  if (item == -1) {
                     if (this.canWork && !this.pickup) {
                        RotationUtils.customRots = false;
                        RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                        RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                        this.canWork = false;
                        this.pickup = false;
                        return;
                     }
                  } else {
                     this.mc.thePlayer.inventory.currentItem = item;
                     RotationUtils.customRots = true;
                     RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                     RotationUtils.serverPitch = 90.0F;
                     e.setYaw(RotationUtils.serverYaw);
                     e.setPitch(RotationUtils.serverPitch);
                     this.canWork = true;
                     if (!this.mc.thePlayer.isInWater()
                        && !this.mc.thePlayer.isInWeb
                        && !this.pickup
                        && this.mc
                              .theWorld
                              .getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0, this.mc.thePlayer.posZ))
                              .getBlock()
                           != Blocks.water
                        && this.mc
                              .theWorld
                              .getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0, this.mc.thePlayer.posZ))
                              .getBlock()
                           != Blocks.air) {
                        this.mc.rightClickMouse();
                        this.pickup = true;
                        this.timer.reset();
                     }
                  }
               } else if (this.canWork) {
                  if (this.mc.thePlayer.isInWater() && this.pickup) {
                     this.mc.rightClickMouse();
                     this.pickup = false;
                  } else {
                     RotationUtils.customRots = false;
                     RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                     RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                     this.canWork = false;
                     this.pickup = false;
                  }

                  if (this.timer.hasTimeElapsed(150.0, false)) {
                     RotationUtils.customRots = false;
                     RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                     RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                     this.canWork = false;
                     this.pickup = false;
                  }
               }
            }
      }
   }

   @EventListener(EventPriority.HIGHEST)
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      if (e.getType() == EventType.PRE) {
         this.setInfo(this.mode.getMode());
         String var2;
         switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1357340883:
               if (var2.equals("clutch") && this.canWork) {
                  e.setYaw(RotationUtils.serverYaw);
                  e.setPitch(RotationUtils.serverPitch);
               }
               break;
            case -805359837:
               if (var2.equals("vulcan") && (double)this.mc.thePlayer.fallDistance > 2.9) {
                  this.mc.thePlayer.motionY = -0.11;
                  e.setOnGround(true);
                  this.mc.thePlayer.fallDistance = 0.0F;
               }
               break;
            case -392544390:
               if (var2.equals("hypixel timer")) {
                  if ((double)this.mc.thePlayer.fallDistance - this.mc.thePlayer.motionY > 3.0) {
                     this.mc.timer.timerSpeed = 0.5F;
                     this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                     this.mc.thePlayer.fallDistance = 0.0F;
                  } else {
                     this.mc.timer.timerSpeed = 1.0F;
                  }
               }
               break;
            case 3181391:
               if (var2.equals("grim") && this.mc.thePlayer.fallDistance >= 3.0F) {
                  this.mc.thePlayer.motionX *= 0.2;
                  this.mc.thePlayer.motionZ *= 0.2;
                  float distance = this.mc.thePlayer.fallDistance;
                  if (MovementUtils.isOnGround(2.0)) {
                     if (distance > 2.0F) {
                        MovementUtils.strafe(0.19F);
                     }

                     if (distance > 3.0F && MovementUtils.getSpeed() < 0.2) {
                        e.setOnGround(true);
                        distance = 0.0F;
                     }
                  }

                  this.mc.thePlayer.fallDistance = distance;
               }
               break;
            case 109651721:
               if (var2.equals("spoof") && (double)this.mc.thePlayer.fallDistance > 2.9) {
                  e.setOnGround(true);
                  this.mc.thePlayer.fallDistance = 0.0F;
               }
               break;
            case 112097665:
               if (var2.equals("verus") && (double)this.mc.thePlayer.fallDistance > 2.9) {
                  this.mc.thePlayer.motionX *= 0.6;
                  this.mc.thePlayer.motionZ *= 0.6;
                  this.mc.thePlayer.motionY = 0.0;
                  e.setOnGround(true);
                  this.mc.thePlayer.fallDistance = 0.0F;
               }
         }
      }
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case 3181391:
            if (var2.equals("grim") && e.getPacket() instanceof S08PacketPlayerPosLook) {
               this.canWork = true;
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onAirCollide(EventAirCollide e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case 3181391:
            if (var2.equals("grim")
               && this.mc.thePlayer.fallDistance >= 3.0F
               && !this.canWork
               && this.mc.theWorld.getBlockState(e.getPos()).getBlock() instanceof BlockAir
               && !this.mc.thePlayer.isSneaking()) {
               double x = (double)e.getPos().getX();
               double y = (double)e.getPos().getY();
               double z = (double)e.getPos().getZ();
               if (y < this.mc.thePlayer.posY) {
                  e.setReturnValue(AxisAlignedBB.fromBounds(-15.0, -1.0, -15.0, 15.0, 1.0, 15.0).offset(x, y, z));
               }
            }
      }
   }
}
