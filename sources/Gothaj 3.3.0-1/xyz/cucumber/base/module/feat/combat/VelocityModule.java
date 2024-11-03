package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventKnockBack;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventMoveForward;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventUpdate;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to take lower knockback",
   name = "Velocity",
   priority = ArrayPriority.HIGH
)
public class VelocityModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Vulcan", "Hypixel", "Intave", "AAC", "Matrix", "Grim 1.8", "Grim", "Jump"});
   public ModeSettings hypixelMode = new ModeSettings(
      "Hypixel mode", () -> this.mode.getMode().equalsIgnoreCase("Hypixel"), new String[]{"Full", "Cancel horizontal", "0 100"}
   );
   public NumberSettings horizontal = new NumberSettings("Horizontal", () -> this.mode.getMode().equalsIgnoreCase("Vanilla"), 0.0, -100.0, 100.0, 1.0);
   public NumberSettings vertical = new NumberSettings("Vertical", () -> this.mode.getMode().equalsIgnoreCase("Vanilla"), 0.0, -100.0, 100.0, 1.0);
   public boolean blockVelocity;
   public boolean isWorking;
   public ConcurrentLinkedQueue<Short> transactions = new ConcurrentLinkedQueue<>();
   public ArrayList<Packet> packets = new ArrayList<>();
   public Timer timer = new Timer();
   public int ticks;
   public int updates;

   public VelocityModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.hypixelMode, this.horizontal, this.vertical});
   }

   @Override
   public void onEnable() {
      this.updates = 0;
      this.blockVelocity = false;
      this.isWorking = false;
      this.packets.clear();
      this.transactions.clear();
      this.ticks = 0;
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case 3181391:
            if (var2.equals("grim")) {
               this.updates = 8;
               this.ticks = 0;
               this.blockVelocity = false;
               this.transactions.clear();
            }
            break;
         case 324769130:
            if (var2.equals("grim 1.8")) {
               this.updates = 8;
               this.blockVelocity = false;
               this.transactions.clear();
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onUpdate(EventUpdate e) {
      this.info = this.mode.getMode();
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave")) {
               this.blockVelocity = true;
            }
            break;
         case -1081239615:
            if (var2.equals("matrix") && this.mc.thePlayer.hurtTime > 0) {
               this.mc.thePlayer.motionX *= 0.6;
               this.mc.thePlayer.motionZ *= 0.6;
            }
            break;
         case 96323:
            if (var2.equals("aac") && this.mc.thePlayer.hurtTime == 7) {
               this.mc.thePlayer.motionX *= 0.25;
               this.mc.thePlayer.motionZ *= 0.25;
            }
            break;
         case 3181391:
            if (var2.equals("grim")) {
               this.updates--;
               if (this.ticks > 0) {
                  if (this.blockVelocity) {
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(this.mc.thePlayer.onGround));
                     this.blockVelocity = false;
                  }

                  this.ticks--;
                  this.mc
                     .thePlayer
                     .sendQueue
                     .addToSendQueue(
                        new C07PacketPlayerDigging(
                           C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                           new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ),
                           EnumFacing.DOWN
                        )
                     );
               }
            }
            break;
         case 3273774:
            if (var2.equals("jump") && this.mc.thePlayer.onGround && this.mc.thePlayer.hurtTime >= 5) {
               this.mc.thePlayer.jump();
            }
            break;
         case 102851513:
            if (var2.equals("legit") && this.mc.objectMouseOver.entityHit != null && this.mc.thePlayer.hurtTime == 9 && !this.mc.thePlayer.isBurning()) {
               this.mc.thePlayer.movementInput.jump = true;
               this.ticks++;
            }
            break;
         case 324769130:
            if (var2.equals("grim 1.8")) {
               this.updates--;
               if (this.transactions.isEmpty() && this.blockVelocity) {
                  this.blockVelocity = false;
               }
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onReceivePacket(EventReceivePacket e) {
      Packet p = e.getPacket();
      String var3;
      switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var3.equals("intave") && p instanceof S12PacketEntityVelocity) {
               S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
               if (packet.getEntityID() == this.mc.thePlayer.getEntityId()) {
                  this.isWorking = true;
                  return;
               }
            }

            return;
         case -805359837:
            if (!var3.equals("vulcan")) {
               return;
            }
            break;
         case 3181391:
            if (var3.equals("grim")) {
               if (p instanceof S08PacketPlayerPosLook) {
                  S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)p;
                  if (packet.x == this.mc.thePlayer.posX && packet.y == this.mc.thePlayer.posY && packet.z == this.mc.thePlayer.posZ
                     || packet.x == this.mc.thePlayer.lastReportedPosX
                        && packet.y == this.mc.thePlayer.lastReportedPosY
                        && packet.z == this.mc.thePlayer.lastReportedPosZ) {
                     return;
                  }

                  this.updates = 8;
               }

               if (this.updates > 0) {
                  this.blockVelocity = false;
                  this.ticks = 0;
                  return;
               }

               if (p instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)p).getEntityID() == this.mc.thePlayer.getEntityId()) {
                  this.blockVelocity = true;
                  this.ticks = 1;
                  e.setCancelled(true);
                  return;
               }
            }

            return;
         case 233102203:
            if (!var3.equals("vanilla")) {
               return;
            }
            break;
         case 324769130:
            if (var3.equals("grim 1.8")) {
               if (p instanceof S08PacketPlayerPosLook) {
                  S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)p;
                  if (packet.x == this.mc.thePlayer.posX && packet.y == this.mc.thePlayer.posY && packet.z == this.mc.thePlayer.posZ
                     || packet.x == this.mc.thePlayer.lastReportedPosX
                        && packet.y == this.mc.thePlayer.lastReportedPosY
                        && packet.z == this.mc.thePlayer.lastReportedPosZ) {
                     return;
                  }

                  this.updates = 8;
               }

               if (this.updates > 0) {
                  this.transactions.clear();
                  this.blockVelocity = false;
                  return;
               }

               if (p instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)p).getEntityID() == this.mc.thePlayer.getEntityId()) {
                  e.setCancelled(true);
                  this.blockVelocity = true;
               } else if (p instanceof S32PacketConfirmTransaction) {
                  if (!this.blockVelocity) {
                     return;
                  }

                  S32PacketConfirmTransaction packet = (S32PacketConfirmTransaction)p;
                  this.transactions.add(packet.getActionNumber());
                  e.setCancelled(true);
                  return;
               }

               return;
            }

            return;
         case 1381910549:
            if (var3.equals("hypixel") && p instanceof S12PacketEntityVelocity) {
               S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
               if (packet.getEntityID() == this.mc.thePlayer.getEntityId()) {
                  String var5;
                  switch ((var5 = this.hypixelMode.getMode().toLowerCase()).hashCode()) {
                     case -420362870:
                        if (var5.equals("cancel horizontal")) {
                           if (!this.mc.thePlayer.onGround) {
                              e.setCancelled(true);
                           } else {
                              packet.motionX = (int)(this.mc.thePlayer.motionX * 8000.0);
                              packet.motionZ = (int)(this.mc.thePlayer.motionZ * 8000.0);
                           }
                        }
                        break;
                     case 3154575:
                        if (var5.equals("full")) {
                           e.setCancelled(true);
                        }
                        break;
                     case 45330945:
                        if (var5.equals("0 100")) {
                           if (!this.mc.thePlayer.onGround) {
                              e.setCancelled(true);
                           } else {
                              packet.motionX = 0;
                              packet.motionZ = 0;
                           }
                        }
                  }

                  this.ticks = 0;
                  return;
               }
            }

            return;
         default:
            return;
      }

      if (p instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)p).getEntityID() == this.mc.thePlayer.getEntityId()) {
         S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
         packet.motionX = (int)((double)(packet.getMotionX() / 100) * this.horizontal.getValue());
         packet.motionY = (int)((double)(packet.getMotionY() / 100) * this.vertical.getValue());
         packet.motionZ = (int)((double)(packet.getMotionZ() / 100) * this.horizontal.getValue());
         if (this.horizontal.getValue() == 0.0 && this.vertical.getValue() == 0.0) {
            e.setCancelled(true);
         }
      }
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -805359837:
            if (var2.equals("vulcan") && e.getPacket() instanceof C0FPacketConfirmTransaction && this.mc.thePlayer.hurtTime > 0) {
               e.setCancelled(true);
            }
            break;
         case 324769130:
            if (var2.equals("grim 1.8")) {
               if (this.updates > 0) {
                  this.transactions.clear();
                  this.blockVelocity = false;
                  return;
               }

               if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                  if (!this.blockVelocity || this.transactions.isEmpty()) {
                     return;
                  }

                  C0FPacketConfirmTransaction packet = (C0FPacketConfirmTransaction)e.getPacket();
                  if (this.transactions.remove(packet.getUid())) {
                     e.setCancelled(true);
                  }
               }
            }
            break;
         case 1381910549:
            if (var2.equals("hypixel")) {
               if (this.ticks <= 0) {
                  if (e.getPacket() instanceof C03PacketPlayer && !e.isCancelled()) {
                     e.setCancelled(true);
                     this.packets.add(e.getPacket());
                     this.ticks++;
                  }
               } else {
                  for (Packet packet : this.packets) {
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
                  }

                  this.packets.clear();
               }
            }
      }
   }

   @EventListener(EventPriority.LOWEST)
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveButton(EventMoveButton e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave") && this.mc.thePlayer.hurtTime > 0 && this.mc.objectMouseOver.entityHit != null) {
               e.forward = true;
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onKnockBack(EventKnockBack e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave")) {
               e.setReduceY(true);
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onAttack(EventAttack e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave") && this.mc.objectMouseOver.entityHit != null && this.mc.thePlayer.hurtTime > 0 && this.blockVelocity) {
               this.mc.thePlayer.setSprinting(false);
               this.mc.thePlayer.motionX *= 0.6;
               this.mc.thePlayer.motionZ *= 0.6;
               this.blockVelocity = false;
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveForward(EventMoveForward e) {
      if (e.getType() == EventType.POST) {
         this.mode.getMode().toLowerCase().hashCode();
      }
   }
}
