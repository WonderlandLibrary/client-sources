package xyz.cucumber.base.module.feat.player;

import java.util.concurrent.ThreadLocalRandom;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Allows you to rotate back smoother",
   name = "Smooth Rotation"
)
public class SmoothRotationModule extends Mod {
   public KillAuraModule killAura;
   public ScaffoldModule scaffold;
   public boolean wasKillAura;
   public boolean wasScaffold;
   public BooleanSettings ka = new BooleanSettings("Kill Aura", true);
   public BooleanSettings sc = new BooleanSettings("Scaffold", true);
   public NumberSettings turnSpeed = new NumberSettings("Turn Speed", 3.0, 15.0, 60.0, 1.0);
   public BooleanSettings smooth = new BooleanSettings("Smooth", false);

   public SmoothRotationModule() {
      this.addSettings(new ModuleSettings[]{this.ka, this.sc, this.turnSpeed, this.smooth});
   }

   @Override
   public void onEnable() {
      this.wasKillAura = false;
      this.wasScaffold = false;
      this.killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      this.scaffold = (ScaffoldModule)Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class);
   }

   @EventListener
   public void onRotation(EventTick e) {
      this.setInfo(String.valueOf(this.turnSpeed.getValue()));
      if (this.killAura.isEnabled() && this.killAura.allowedToWork && this.ka.isEnabled()) {
         this.wasKillAura = true;
      }

      float random = (float)(this.turnSpeed.getValue() * ThreadLocalRandom.current().nextDouble(0.8, 1.2));
      int toStop = 1;
      if (this.wasKillAura && (!this.killAura.isEnabled() || this.killAura.isEnabled() && !this.killAura.allowedToWork)) {
         if (!(RotationUtils.getRotationDifference(RotationUtils.serverYaw, this.mc.thePlayer.rotationYaw) > (float)toStop)
            && !(RotationUtils.getRotationDifference(RotationUtils.serverPitch, this.mc.thePlayer.rotationPitch) > (float)toStop)) {
            if (this.wasKillAura) {
               RotationUtils.customRots = false;
            }

            this.wasKillAura = false;
         } else {
            RotationUtils.customRots = true;
            float lastYaw = RotationUtils.serverYaw;
            float lastPitch = RotationUtils.serverPitch;
            RotationUtils.serverYaw = RotationUtils.updateRotation(RotationUtils.serverYaw, this.mc.thePlayer.rotationYaw, random);
            RotationUtils.serverPitch = RotationUtils.updateRotation(RotationUtils.serverPitch, this.mc.thePlayer.rotationPitch, random);
            RotationUtils.serverYaw = (float)((double)RotationUtils.serverYaw + (Math.random() * 2.0 - Math.random() * 2.0));
            RotationUtils.serverPitch = (float)((double)RotationUtils.serverPitch + (Math.random() * 2.0 - Math.random() * 2.0));
            if (this.smooth.isEnabled()) {
               float smoothness = 1.2F;
               RotationUtils.serverYaw = (RotationUtils.serverYaw * smoothness + (RotationUtils.serverYaw - RotationUtils.serverYaw)) / smoothness;
               RotationUtils.serverPitch = (RotationUtils.serverPitch * smoothness + (RotationUtils.serverPitch - RotationUtils.serverPitch)) / smoothness;
            }

            float[] rots = RotationUtils.getFixedRotation(new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}, new float[]{lastYaw, lastPitch});
            RotationUtils.serverYaw = rots[0];
            RotationUtils.serverPitch = rots[1];
         }
      }

      if (this.scaffold.isEnabled() && InventoryUtils.getBlockSlot(false) != -1 && this.sc.isEnabled()) {
         this.wasScaffold = true;
      }

      if (this.wasScaffold && this.scaffold.enabledTicks < 10) {
         if (!(RotationUtils.getRotationDifference(RotationUtils.serverYaw, this.scaffold.lastYaw) > (float)toStop)
            && !(RotationUtils.getRotationDifference(RotationUtils.serverPitch, this.scaffold.lastPitch) > (float)toStop)) {
            this.scaffold.enabledTicks = 11;
         } else {
            RotationUtils.customRots = true;
            float lastYaw = RotationUtils.serverYaw;
            float lastPitch = RotationUtils.serverPitch;
            RotationUtils.serverYaw = RotationUtils.updateRotation(RotationUtils.serverYaw, this.mc.thePlayer.rotationYaw - 180.0F, random);
            RotationUtils.serverPitch = RotationUtils.updateRotation(RotationUtils.serverPitch, 80.0F, random);
            RotationUtils.serverYaw = (float)((double)RotationUtils.serverYaw + (Math.random() * 2.0 - Math.random() * 2.0));
            RotationUtils.serverPitch = (float)((double)RotationUtils.serverPitch + (Math.random() * 2.0 - Math.random() * 2.0));
            if (this.smooth.isEnabled()) {
               float smoothness = 1.2F;
               RotationUtils.serverYaw = (RotationUtils.serverYaw * smoothness + (RotationUtils.serverYaw - RotationUtils.serverYaw)) / smoothness;
               RotationUtils.serverPitch = (RotationUtils.serverPitch * smoothness + (RotationUtils.serverPitch - RotationUtils.serverPitch)) / smoothness;
            }

            float[] rots = RotationUtils.getFixedRotation(new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}, new float[]{lastYaw, lastPitch});
            RotationUtils.serverYaw = rots[0];
            RotationUtils.serverPitch = rots[1];
         }
      }

      if (this.wasScaffold && !this.scaffold.isEnabled()) {
         if (!(RotationUtils.getRotationDifference(RotationUtils.serverYaw, this.mc.thePlayer.rotationYaw) > (float)toStop)
            && !(RotationUtils.getRotationDifference(RotationUtils.serverPitch, this.mc.thePlayer.rotationPitch) > (float)toStop)) {
            if (this.wasScaffold) {
               RotationUtils.customRots = false;
            }

            this.wasScaffold = false;
         } else {
            RotationUtils.customRots = true;
            float lastYaw = RotationUtils.serverYaw;
            float lastPitch = RotationUtils.serverPitch;
            RotationUtils.serverYaw = RotationUtils.updateRotation(RotationUtils.serverYaw, this.mc.thePlayer.rotationYaw, random);
            RotationUtils.serverPitch = RotationUtils.updateRotation(RotationUtils.serverPitch, this.mc.thePlayer.rotationPitch, random);
            RotationUtils.serverYaw = (float)((double)RotationUtils.serverYaw + (Math.random() * 2.0 - Math.random() * 2.0));
            RotationUtils.serverPitch = (float)((double)RotationUtils.serverPitch + (Math.random() * 2.0 - Math.random() * 2.0));
            if (this.smooth.isEnabled()) {
               float smoothness = 1.2F;
               RotationUtils.serverYaw = (RotationUtils.serverYaw * smoothness + (RotationUtils.serverYaw - RotationUtils.serverYaw)) / smoothness;
               RotationUtils.serverPitch = (RotationUtils.serverPitch * smoothness + (RotationUtils.serverPitch - RotationUtils.serverPitch)) / smoothness;
            }

            float[] rots = RotationUtils.getFixedRotation(new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}, new float[]{lastYaw, lastPitch});
            RotationUtils.serverYaw = rots[0];
            RotationUtils.serverPitch = rots[1];
         }
      }

      if (RotationUtils.serverPitch > 90.0F) {
         RotationUtils.serverPitch = 90.0F;
      }

      if (RotationUtils.serverPitch < -90.0F) {
         RotationUtils.serverPitch = -90.0F;
      }
   }

   @EventListener
   public void onMotion(EventMotion e) {
      if (e.getType() != EventType.POST) {
         if (RotationUtils.customRots && (this.wasKillAura || this.wasScaffold)) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
         }
      }
   }

   @EventListener
   public void onLook(EventLook event) {
      if (event.getType() != EventType.POST) {
         if (RotationUtils.customRots && (this.wasKillAura || this.wasScaffold)) {
            event.setYaw(RotationUtils.serverYaw);
            event.setPitch(RotationUtils.serverPitch);
         }
      }
   }

   @EventListener
   public void onRenderRotation(EventRenderRotation event) {
      if (event.getType() != EventType.POST) {
         if (RotationUtils.customRots && (this.wasKillAura || this.wasScaffold)) {
            event.setYaw(RotationUtils.serverYaw);
            event.setPitch(RotationUtils.serverPitch);
         }
      }
   }

   @EventListener
   public void onMoveFlying(EventMoveFlying e) {
      if (RotationUtils.customRots
         && (this.wasKillAura || this.wasScaffold)
         && !this.mc.isSingleplayer()
         && !this.killAura.allowedToWork
         && !this.scaffold.isEnabled()) {
         e.setCancelled(true);
         MovementUtils.silentMoveFix(e);
      }
   }

   @EventListener
   public void onJump(EventJump e) {
      if (RotationUtils.customRots && this.wasKillAura) {
         e.setYaw(RotationUtils.serverYaw);
      }
   }
}
