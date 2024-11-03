package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.entity.EntityLivingBase;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventRotation;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Automatically throw projectiles into player",
   name = "Auto Rod",
   key = 0,
   priority = ArrayPriority.HIGH
)
public class AutoRodModule extends Mod {
   public EntityLivingBase target;
   public boolean cancel;
   public KillAuraModule ka;
   public Timer timer = new Timer();

   @Override
   public void onEnable() {
      this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
   }

   @Override
   public void onDisable() {
      RotationUtils.customRots = false;
   }

   @EventListener
   public void onRotation(EventRotation event) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         int newSlot = InventoryUtils.getProjectileSlot();
         if (newSlot == -1) {
            if (this.cancel) {
               this.cancel = false;
               RotationUtils.customRots = false;
            }
         } else {
            this.target = EntityUtils.getTarget(
               10.0,
               "Players",
               "Off",
               400,
               Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
               this.ka.TroughWalls.isEnabled(),
               this.ka.attackInvisible.isEnabled(),
               this.ka.attackDead.isEnabled()
            );
            if (this.target == null) {
               if (this.cancel) {
                  this.cancel = false;
                  RotationUtils.customRots = false;
               }
            } else if (this.ka.isEnabled()
               && !(EntityUtils.getDistanceToEntityBox(this.target) < Math.max(this.ka.Range.value, 4.0))
               && this.target.hurtTime < 3) {
               int delay = 350;
               if (this.mc.thePlayer.getDistanceToEntity(this.target) <= 8.0F) {
                  delay = 300;
               } else if (this.mc.thePlayer.getDistanceToEntity(this.target) <= 8.0F) {
                  delay = 250;
               } else if (this.mc.thePlayer.getDistanceToEntity(this.target) <= 7.0F) {
                  delay = 200;
               } else if (this.mc.thePlayer.getDistanceToEntity(this.target) <= 6.0F) {
                  delay = 150;
               } else if (this.mc.thePlayer.getDistanceToEntity(this.target) <= 5.0F) {
                  delay = 100;
               }

               if (!this.timer.hasTimeElapsed((double)delay, false)) {
                  if (this.cancel) {
                     this.cancel = false;
                     RotationUtils.customRots = false;
                  }
               } else {
                  double multiplier = (double)this.mc.thePlayer.getDistanceToEntity(this.target) / 1.25;
                  double deltaX = (this.target.posX - this.target.lastTickPosX) * multiplier;
                  double deltaZ = (this.target.posZ - this.target.lastTickPosZ) * multiplier;
                  double targetPosX = this.target.posX + deltaX;
                  double targetPosZ = this.target.posZ + deltaZ;
                  double targetPosY = this.target.posY + (double)this.target.getEyeHeight() - 0.4;
                  float[] rots = RotationUtils.getRotationFromPosition(targetPosX, targetPosY, targetPosZ);
                  RotationUtils.customRots = true;
                  RotationUtils.serverYaw = rots[0];
                  RotationUtils.serverPitch = rots[1];
                  this.mc.thePlayer.inventory.currentItem = newSlot;
                  this.cancel = true;
               }
            } else {
               if (this.cancel) {
                  this.cancel = false;
                  RotationUtils.customRots = false;
               }
            }
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion event) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            if (event.getType() == EventType.PRE) {
               event.setYaw(RotationUtils.serverYaw);
               event.setPitch(RotationUtils.serverPitch);
            } else {
               if (this.mc.thePlayer.inventory.currentItem == InventoryUtils.getProjectileSlot()) {
                  this.mc.rightClickMouse();
               }

               this.timer.reset();
            }
         }
      }
   }

   @EventListener
   public void onMove(EventMoveFlying event) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            event.setYaw(RotationUtils.serverYaw);
         }
      }
   }

   @EventListener
   public void onJump(EventJump event) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            event.setYaw(RotationUtils.serverYaw);
         }
      }
   }

   @EventListener
   public void onLook(EventLook event) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            event.setYaw(RotationUtils.serverYaw);
            event.setPitch(RotationUtils.serverPitch);
         }
      }
   }

   @EventListener
   public void onRotationRender(EventRenderRotation event) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            event.setYaw(RotationUtils.serverYaw);
            event.setPitch(RotationUtils.serverPitch);
         }
      }
   }
}
