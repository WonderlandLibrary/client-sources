package vestige.module.impl.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.util.Timer;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.PostStepEvent;
import vestige.event.impl.PreStepEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.network.PacketUtil;
import vestige.util.player.MovementUtil;
import vestige.util.util.Utils;

public class Step extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", new String[]{"Vanilla", "NCP", "Hypixel", "None"});
   private final DoubleSetting height = new DoubleSetting("Height", () -> {
      return this.mode.is("Vanilla");
   }, 1.0D, 1.0D, 9.0D, 0.5D);
   private final DoubleSetting timer = new DoubleSetting("Timer", 1.0D, 0.1D, 1.0D, 0.05D);
   public boolean prevOffGround;
   private int offGroundTicks = -1;
   private boolean stepping = false;
   private long lastStep = -1L;
   private boolean timerTick;

   public Step() {
      super("Step", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.mode, this.height, this.timer});
   }

   public boolean onDisable() {
      this.prevOffGround = false;
      Timer var10000 = mc.timer;
      var10000.timerSpeed = 1.0F;
      mc.thePlayer.stepHeight = 0.6F;
      return false;
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.onGround) {
         this.offGroundTicks = 0;
      } else if (this.offGroundTicks != -1) {
         ++this.offGroundTicks;
      }

      long time = System.currentTimeMillis();
      if (mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally && MovementUtil.isMoving() && time - this.lastStep >= 0L) {
         this.stepping = true;
         this.lastStep = time;
      }

      if (MovementUtil.isMoving() && !Utils.jumpDown() && (mc.thePlayer.isCollidedHorizontally || this.offGroundTicks <= 4)) {
         String var4 = this.mode.getMode();
         byte var5 = -1;
         switch(var4.hashCode()) {
         case -1248403467:
            if (var4.equals("Hypixel")) {
               var5 = 2;
            }
            break;
         case 77115:
            if (var4.equals("NCP")) {
               var5 = 1;
            }
            break;
         case 1897755483:
            if (var4.equals("Vanilla")) {
               var5 = 0;
            }
         }

         switch(var5) {
         case 0:
            mc.thePlayer.stepHeight = (float)this.height.getValue();
            break;
         case 1:
            mc.thePlayer.stepHeight = 1.0F;
            break;
         case 2:
            if (this.stepping) {
               mc.thePlayer.stepHeight = 1.0F;
            }
         }

         if (this.timerTick) {
            Timer var10000 = mc.timer;
            var10000.timerSpeed = 1.0F;
            this.timerTick = false;
         }

      } else {
         this.stepping = false;
      }
   }

   public String getInfo() {
      return this.mode.getMode();
   }

   @Listener
   public void onPreStep(PreStepEvent event) {
      if (!this.mode.is("Vanilla") && !this.mode.is("Hypixel") && mc.thePlayer.onGround && this.prevOffGround && (double)event.getHeight() > 0.6D) {
         event.setHeight(0.6F);
      }

   }

   @Listener
   public void onSend(PacketSendEvent e) {
      if (this.mode.is("Hypixel") && mc.thePlayer.isCollidedHorizontally) {
         Packet<?> packet = e.getPacket();
         if (packet.getClass().getSimpleName().startsWith("S")) {
            return;
         }

         if (packet instanceof C00Handshake || packet instanceof C00PacketLoginStart || packet instanceof C00PacketServerQuery || packet instanceof C01PacketEncryptionResponse || packet instanceof C01PacketChatMessage) {
            return;
         }

         e.setCancelled(true);
      }

   }

   @Listener
   public void onPostStep(PostStepEvent event) {
      if (event.getHeight() > 0.6F) {
         if (this.timer.getValue() < 1.0D) {
            Timer var10000 = mc.timer;
            var10000.timerSpeed = (float)this.timer.getValue();
            this.timerTick = true;
         }

         String var2 = this.mode.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case 77115:
            if (var2.equals("NCP")) {
               var3 = 0;
            }
         default:
            switch(var3) {
            case 0:
               PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42D, mc.thePlayer.posZ, false));
               PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.75D, mc.thePlayer.posZ, false));
            }
         }
      }

   }

   @Listener
   public void onMotion(MotionEvent event) {
      this.prevOffGround = !event.isOnGround();
   }
}
