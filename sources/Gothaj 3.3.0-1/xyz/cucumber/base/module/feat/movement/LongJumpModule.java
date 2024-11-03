package xyz.cucumber.base.module.feat.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you jump further",
   name = "Long Jump",
   key = 0
)
public class LongJumpModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Intave Boat", "Intave", "Polar"});
   public int jumps = 0;

   public LongJumpModule() {
      this.addSettings(new ModuleSettings[]{this.mode});
   }

   @Override
   public void onEnable() {
      if (this.mc.thePlayer != null && this.mc.theWorld != null) {
         this.jumps = 0;
      }
   }

   @EventListener
   public void onMotion(EventMotion e) {
      if (e.getType() == EventType.PRE) {
         this.setInfo(this.mode.getMode());
         String var2;
         switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1525012097:
               if (var2.equals("intave boat") && this.mc.thePlayer.isRiding()) {
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(
                        new C03PacketPlayer.C04PacketPlayerPosition(
                           this.mc.thePlayer.posX - Math.sin(Math.toRadians((double)this.mc.thePlayer.rotationYaw)) * 3.0,
                           this.mc.thePlayer.posY + 2.0,
                           this.mc.thePlayer.posZ + Math.cos(Math.toRadians((double)this.mc.thePlayer.rotationYaw)) * 3.0,
                           false
                        )
                     );
                  this.toggle();
               }
               break;
            case -1183766399:
               if (var2.equals("intave") && this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
                  this.mc.thePlayer.jump();
                  this.toggle();
               }
               break;
            case 106848062:
               if (var2.equals("polar") && this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
                  this.mc.thePlayer.jump();
                  this.toggle();
               }
         }
      }
   }
}
