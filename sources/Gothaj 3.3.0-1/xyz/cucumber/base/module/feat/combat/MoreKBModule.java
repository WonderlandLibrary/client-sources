package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventMoveForward;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to deal more knockback",
   name = "More KB",
   key = 0,
   priority = ArrayPriority.HIGH
)
public class MoreKBModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Packet", "Packet legit", "WTap", "STap"});
   VelocityModule velocity;

   public MoreKBModule() {
      this.addSettings(new ModuleSettings[]{this.mode});
   }

   @Override
   public void onEnable() {
      this.velocity = (VelocityModule)Client.INSTANCE.getModuleManager().getModule(VelocityModule.class);
   }

   @EventListener
   public void onTick(EventTick e) {
      this.setInfo(this.mode.getMode());
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onAttack(EventAttack e) {
      if (!this.velocity.mode.getMode().equalsIgnoreCase("Polar")) {
         KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
         String var3;
         switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -995865464:
               if (var3.equals("packet")) {
                  if (this.mc.thePlayer.isSprinting()) {
                     this.mc.thePlayer.setSprinting(false);
                  }

                  this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                  this.mc.thePlayer.setSprinting(true);
               }
               break;
            case 1783053857:
               if (var3.equals("packet legit")) {
                  if (!this.mc.gameSettings.keyBindForward.pressed || this.mc.thePlayer.isSneaking()) {
                     return;
                  }

                  if (this.mc.thePlayer.isSprinting()) {
                     this.mc.thePlayer.setSprinting(false);
                     this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                     this.mc.thePlayer.serverSprintState = true;
                     this.mc.thePlayer.setSprinting(true);
                  }
               }
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveForward(EventMoveForward e) {
      if (!this.velocity.mode.getMode().equalsIgnoreCase("Polar")) {
         KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
         if (ka.target != null) {
            String var3;
            switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
               case 3659724:
                  if (var3.equals("wtap") && ka.target.hurtTime == 10) {
                     e.setReset(true);
                  }
            }
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveButton(EventMoveButton e) {
      if (!this.velocity.mode.getMode().equalsIgnoreCase("Polar")) {
         KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
         if (ka.target != null) {
            String var3;
            switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
               case 3540560:
                  if (var3.equals("stap")) {
                     if (ka.target.hurtTime == 10) {
                        e.backward = true;
                        e.forward = false;
                     }

                     if (ka.target.hurtTime == 9) {
                        e.backward = false;
                        e.forward = true;
                     }
                  }
            }
         }
      }
   }
}
