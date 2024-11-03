package vestige.module.impl.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.PreMotionEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Killaura;
import vestige.module.impl.world.ScaffoldV2;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.network.PacketUtil;
import vestige.util.player.PlayerUtil;
import vestige.util.util.Utils;
import vestige.util.world.BlockUtils;

public class Nofall extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Spoof", new String[]{"Packet", "Spoof", "Timer", "Blink"});
   private final BooleanSetting suspendAll = new BooleanSetting("Suspend all packets", () -> {
      return this.mode.is("Blink");
   }, true);
   private final BooleanSetting ignorevoid = new BooleanSetting("Ignore void", true);
   private double lastY;
   public double fallDistance;
   public double fallDistancee;
   private boolean prevOnGround = false;
   private boolean timed = false;
   private int ticks;
   private boolean blinking;

   public Nofall() {
      super("Nofall", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.mode, this.suspendAll, this.ignorevoid});
   }

   public void onEnable() {
      this.ticks = 0;
      this.lastY = mc.thePlayer.posY;
      this.fallDistance = (double)mc.thePlayer.fallDistance;
   }

   public String getInfo() {
      return this.mode.getMode();
   }

   public boolean onDisable() {
      Flap.instance.getPacketBlinkHandler().stopAll();
      return false;
   }

   @Listener
   public void onPremotion(PreMotionEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 64274236:
         if (var2.equals("Blink")) {
            var3 = 1;
         }
         break;
      case 80811813:
         if (var2.equals("Timer")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         if (!this.ignorevoid.isEnabled() || !Utils.overVoid()) {
            if (mc.thePlayer.onGround) {
               this.fallDistancee = 0.0D;
            } else {
               this.fallDistancee += (double)((float)Math.max(mc.thePlayer.lastTickPosY - event.getPosY(), 0.0D));
            }

            this.fallDistance -= PlayerUtil.predictedMotion(mc.thePlayer.motionY, 1);
            net.minecraft.util.Timer var10000;
            if (this.fallDistancee >= 3.0D && ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget() == null && !((ScaffoldV2)Flap.instance.getModuleManager().getModule(ScaffoldV2.class)).isEnabled()) {
               var10000 = mc.timer;
               var10000.timerSpeed = 0.5F;
               mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
               this.fallDistancee = 0.0D;
               this.timed = true;
            } else if (this.timed) {
               var10000 = mc.timer;
               var10000.timerSpeed = 1.0F;
               this.timed = false;
            }
         }
         break;
      case 1:
         if (!mc.thePlayer.onGround && !this.noAction()) {
            if (this.prevOnGround) {
               if (this.shouldBlink()) {
                  ((Blink)Flap.instance.getModuleManager().getModule(Blink.class)).toggle();
                  this.blinking = true;
               }

               this.prevOnGround = false;
            } else if (BlockUtils.isBlockUnder() && ((Blink)Flap.instance.getModuleManager().getModule(Blink.class)).isEnabled() && this.fallDistance - mc.thePlayer.motionY >= 3.0D) {
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
               this.fallDistance = 0.0D;
            }
         } else {
            if (this.blinking) {
               ((Blink)Flap.instance.getModuleManager().getModule(Blink.class)).toggle();
               this.blinking = false;
            }

            this.prevOnGround = mc.thePlayer.onGround;
         }
      }

   }

   @Listener
   public void onMotion(MotionEvent event) throws IllegalAccessException {
      double y = event.getY();
      double motionY = y - this.lastY;
      if (mc.thePlayer.onGround) {
         this.fallDistance = 0.0D;
      } else if (motionY < 0.0D) {
         this.fallDistance -= motionY;
      }

      String var6 = this.mode.getMode();
      byte var7 = -1;
      switch(var6.hashCode()) {
      case -1911998296:
         if (var6.equals("Packet")) {
            var7 = 0;
         }
         break;
      case 80099049:
         if (var6.equals("Spoof")) {
            var7 = 1;
         }
      }

      switch(var7) {
      case 0:
         if (this.fallDistance >= 3.0D) {
            PacketUtil.sendPacket(new C03PacketPlayer(true));
            this.fallDistance = 0.0D;
         }
         break;
      case 1:
         if (this.fallDistance >= 3.0D) {
            event.setOnGround(true);
            this.fallDistance = 0.0D;
         }
      }

      this.lastY = event.getY();
   }

   public boolean noAction() {
      return this.ignorevoid.isEnabled() && Utils.overVoid();
   }

   private boolean shouldBlink() {
      return !mc.thePlayer.onGround && !BlockUtils.isBlockUnder((int)Math.floor(3.0D)) && BlockUtils.isBlockUnder() && !((ScaffoldV2)Flap.instance.getModuleManager().getModule(ScaffoldV2.class)).isEnabled();
   }
}
