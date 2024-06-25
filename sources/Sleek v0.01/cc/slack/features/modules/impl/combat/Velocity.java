package cc.slack.features.modules.impl.combat;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@ModuleInfo(
   name = "Velocity",
   category = Category.COMBAT
)
public class Velocity extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Cancel", "Motion", "Tick", "Reverse", "Hypixel", "Hypixel Damage Strafe", "Intave"});
   private final NumberValue<Integer> vertical = new NumberValue("Vertical", 100, 0, 100, 1);
   private final NumberValue<Integer> horizontal = new NumberValue("Horizontal", 0, 0, 100, 1);
   private final NumberValue<Integer> velocityTick = new NumberValue("Velocity Tick", 5, 0, 20, 1);
   private final BooleanValue onlyground = new BooleanValue("OnlyGround", false);
   private final BooleanValue noFire = new BooleanValue("NoFire", false);
   int jumped = 0;

   public Velocity() {
      this.addSettings(new Value[]{this.mode, this.vertical, this.horizontal, this.velocityTick, this.onlyground, this.noFire});
   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (mc.getPlayer() != null && mc.getWorld() != null) {
         if (!(Boolean)this.noFire.getValue() || !mc.getPlayer().isBurning()) {
            if (!(Boolean)this.onlyground.getValue() || mc.getPlayer().onGround) {
               if (event.getPacket() instanceof S12PacketEntityVelocity) {
                  S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
                  if (packet.getEntityID() == mc.getPlayer().getEntityId()) {
                     String var3 = ((String)this.mode.getValue()).toLowerCase();
                     byte var4 = -1;
                     switch(var3.hashCode()) {
                     case -1367724422:
                        if (var3.equals("cancel")) {
                           var4 = 1;
                        }
                        break;
                     case -1068318794:
                        if (var3.equals("motion")) {
                           var4 = 0;
                        }
                        break;
                     case 1099846370:
                        if (var3.equals("reverse")) {
                           var4 = 3;
                        }
                        break;
                     case 1381910549:
                        if (var3.equals("hypixel")) {
                           var4 = 2;
                        }
                     }

                     switch(var4) {
                     case 0:
                        if ((Integer)this.horizontal.getValue() == 0) {
                           event.cancel();
                           mc.getPlayer().motionY = (double)packet.getMotionY() * ((Integer)this.vertical.getValue()).doubleValue() / 100.0D / 8000.0D;
                        } else if ((Integer)this.vertical.getValue() == 0) {
                           event.cancel();
                           mc.getPlayer().motionX = (double)packet.getMotionX() * ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D / 8000.0D;
                           mc.getPlayer().motionZ = (double)packet.getMotionZ() * ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D / 8000.0D;
                        } else {
                           packet.setMotionX(packet.getMotionX() * ((Integer)this.horizontal.getValue() / 100));
                           packet.setMotionY(packet.getMotionY() * ((Integer)this.vertical.getValue() / 100));
                           packet.setMotionZ(packet.getMotionZ() * ((Integer)this.horizontal.getValue() / 100));
                        }
                        break;
                     case 1:
                        event.cancel();
                        break;
                     case 2:
                        event.cancel();
                        mc.getPlayer().motionY = (double)packet.getMotionY() * ((Integer)this.vertical.getValue()).doubleValue() / 100.0D / 8000.0D;
                        break;
                     case 3:
                        event.cancel();
                        mc.getPlayer().motionY = (double)packet.getMotionY() / 8000.0D;
                        mc.getPlayer().motionX = (double)packet.getMotionX() / 8000.0D;
                        mc.getPlayer().motionZ = (double)packet.getMotionZ() / 8000.0D;
                        MovementUtil.strafe();
                     }
                  }
               }

            }
         }
      }
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (!mc.getPlayer().isInWater() && !mc.getPlayer().isInLava() && !mc.getPlayer().isInWeb) {
         String var2 = ((String)this.mode.getValue()).toLowerCase();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1183766399:
            if (var2.equals("intave")) {
               var3 = 0;
            }
            break;
         case 3559837:
            if (var2.equals("tick")) {
               var3 = 2;
            }
            break;
         case 1009214997:
            if (var2.equals("hypixel damage strafe")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            if (mc.getPlayer().hurtTime == 9) {
               if (++this.jumped % 2 == 0 && mc.getPlayer().onGround && mc.getPlayer().isSprinting() && mc.getCurrentScreen() == null) {
                  mc.getGameSettings().keyBindJump.pressed = true;
                  this.jumped = 0;
               }
            } else {
               mc.getGameSettings().keyBindJump.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindJump);
            }
            break;
         case 1:
            if (mc.getPlayer().hurtTime == 8) {
               MovementUtil.strafe((float)MovementUtil.getSpeed() * 0.75F);
            }
            break;
         case 2:
            if (mc.getPlayer().ticksSinceLastDamage == (Integer)this.velocityTick.getValue()) {
               EntityPlayerSP var10000 = mc.getPlayer();
               var10000.motionX *= ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D;
               var10000 = mc.getPlayer();
               var10000.motionY *= ((Integer)this.vertical.getValue()).doubleValue() / 100.0D;
               var10000 = mc.getPlayer();
               var10000.motionZ *= ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D;
            }
         }

      }
   }
}
