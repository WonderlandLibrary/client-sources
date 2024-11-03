package vestige.module.impl.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.PostMotionEvent;
import vestige.event.impl.PostVelocityEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.movement.Longjump;
import vestige.module.impl.movement.Speed;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.KeyboardUtil;
import vestige.util.player.MovementUtil;

public class Velocity extends Module {
   public final ModeSetting mode = new ModeSetting("Mode", "Legit", new String[]{"Packet", "Hypixel", "Cancel", "Legit"});
   public final ModeSetting submode = new ModeSetting("Submode", () -> {
      return this.mode.is("Hypixel");
   }, "Custom", new String[]{"Air", "Motion"});
   private final IntegerSetting horizontal = new IntegerSetting("Horizontal", () -> {
      return this.mode.is("Packet");
   }, 0, -100, 100, 2);
   private final IntegerSetting vertical = new IntegerSetting("Vertical", () -> {
      return this.mode.is("Packet");
   }, 0, -100, 100, 2);
   private boolean reducing;
   private boolean pendingVelocity;
   private double motionY;
   private int unReduceTimes = 0;
   private int ticks;
   private int offGroundTicks;
   private boolean wasVelocityEffective;
   private Longjump longjumpModule;
   private Speed speedModule;

   public Velocity() {
      super("Velocity", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.mode, this.submode, this.horizontal, this.vertical});
   }

   public void onEnable() {
      this.reducing = false;
      this.offGroundTicks = 0;
      this.wasVelocityEffective = false;
   }

   public boolean onDisable() {
      return false;
   }

   public String getInfo() {
      return this.mode.getMode();
   }

   public void onClientStarted() {
      this.longjumpModule = (Longjump)Flap.instance.getModuleManager().getModule(Longjump.class);
      this.speedModule = (Speed)Flap.instance.getModuleManager().getModule(Speed.class);
   }

   public boolean receivehud(PacketReceiveEvent event) {
      return event.getPacket() instanceof S12PacketEntityVelocity;
   }

   @Listener
   public void onReceive(PacketReceiveEvent event) {
      if (this.canEditVelocity() && event.getPacket() instanceof S12PacketEntityVelocity) {
         S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
         if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
            String var3 = this.mode.getMode();
            byte var4 = -1;
            switch(var3.hashCode()) {
            case -1911998296:
               if (var3.equals("Packet")) {
                  var4 = 0;
               }
               break;
            case -1248403467:
               if (var3.equals("Hypixel")) {
                  var4 = 1;
               }
               break;
            case 73298841:
               if (var3.equals("Legit")) {
                  var4 = 3;
               }
               break;
            case 2011110042:
               if (var3.equals("Cancel")) {
                  var4 = 2;
               }
            }

            switch(var4) {
            case 0:
               double horizontalMult = (double)this.horizontal.getValue() / 100.0D;
               double verticalMult = (double)this.vertical.getValue() / 100.0D;
               if (horizontalMult == 0.0D) {
                  event.setCancelled(true);
                  if (verticalMult > 0.0D) {
                     mc.thePlayer.motionY = (double)packet.getMotionY() * verticalMult / 8000.0D;
                  }
               } else {
                  packet.setMotionX((int)((double)packet.getMotionX() * horizontalMult));
                  packet.setMotionZ((int)((double)packet.getMotionZ() * horizontalMult));
                  packet.setMotionY((int)((double)packet.getMotionY() * verticalMult));
               }
               break;
            case 1:
               String var9 = this.submode.getMode();
               byte var10 = -1;
               switch(var9.hashCode()) {
               case -1984451626:
                  if (var9.equals("Motion")) {
                     var10 = 1;
                  }
                  break;
               case 65834:
                  if (var9.equals("Air")) {
                     var10 = 0;
                  }
               }

               switch(var10) {
               case 0:
                  if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                     event.setCancelled(true);
                     if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = (double)(packet.getMotionY() * 100 / 100) / 8000.0D;
                        return;
                     }
                  }

                  return;
               case 1:
                  packet.setMotionY(0);
                  event.setCancelled(true);
                  MovementUtil.customStrafeStrength(70.0D);
                  return;
               default:
                  return;
               }
            case 2:
               event.setCancelled(true);
               this.pendingVelocity = true;
               break;
            case 3:
               if (mc.currentScreen == null) {
                  mc.gameSettings.keyBindSprint.pressed = true;
                  mc.gameSettings.keyBindForward.pressed = true;
                  mc.gameSettings.keyBindJump.pressed = true;
                  mc.gameSettings.keyBindBack.pressed = false;
                  this.reducing = true;
               }
            }
         }
      }

   }

   private boolean canEditVelocity() {
      boolean usingSelfDamageLongjump = this.longjumpModule.isEnabled() && this.longjumpModule.mode.is("Self damage");
      return !usingSelfDamageLongjump;
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.onGround) {
         this.offGroundTicks = 0;
      } else {
         ++this.offGroundTicks;
      }

   }

   @Listener
   public void onPostMotion(PostMotionEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 191094248:
         if (var2.equals("Legit Reset")) {
            var3 = 0;
         }
      default:
         switch(var3) {
         case 0:
            if (this.reducing) {
               if (mc.currentScreen == null) {
                  KeyboardUtil.resetKeybindings(mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindBack);
               }

               this.reducing = false;
            }
         default:
         }
      }
   }

   public String getSuffix() {
      return this.mode.getMode();
   }

   @Listener
   public void onPostVelocity(PostVelocityEvent event) {
      this.unReduceTimes = 1;
   }
}
