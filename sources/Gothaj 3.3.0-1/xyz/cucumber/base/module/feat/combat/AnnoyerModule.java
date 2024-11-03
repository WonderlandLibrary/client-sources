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
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Automatically blocks player with blocks",
   name = "Annoyer",
   key = 0
)
public class AnnoyerModule extends Mod {
   public EntityLivingBase target;
   public boolean cancel;
   public KillAuraModule ka;
   public Timer timer = new Timer();
   public NumberSettings delay = new NumberSettings("Delay", 1000.0, 500.0, 5000.0, 250.0);

   public AnnoyerModule() {
      this.addSettings(new ModuleSettings[]{this.delay});
   }

   @Override
   public void onEnable() {
      this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
   }

   @Override
   public void onDisable() {
      RotationUtils.customRots = false;
   }

   @EventListener
   public void onRotation(EventRotation e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         int newSlot = InventoryUtils.getBlockSlot(false);
         if (newSlot == -1) {
            this.cancel = false;
         } else {
            this.target = EntityUtils.getTarget(
               5.0,
               "Players",
               "Off",
               400,
               Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
               this.ka.TroughWalls.isEnabled(),
               this.ka.attackInvisible.isEnabled(),
               this.ka.attackDead.isEnabled()
            );
            if (this.target == null) {
               this.cancel = false;
            } else if (EntityUtils.getDistanceToEntityBox(this.target) <= 3.0) {
               this.target = null;
               this.cancel = false;
            } else if (!this.ka.isEnabled()) {
               this.target = null;
               this.cancel = false;
            } else if (!this.timer.hasTimeElapsed(this.delay.getValue(), false)) {
               if (this.cancel) {
                  this.cancel = false;
                  RotationUtils.customRots = false;
               }
            } else {
               float[] rots = RotationUtils.getRotationFromPosition(this.target.posX, this.target.posY - 1.6, this.target.posZ);
               RotationUtils.customRots = true;
               RotationUtils.serverYaw = rots[0];
               RotationUtils.serverPitch = rots[1];
               this.mc.thePlayer.inventory.currentItem = newSlot;
               this.cancel = true;
            }
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            if (e.getType() == EventType.PRE) {
               e.setYaw(RotationUtils.serverYaw);
               e.setPitch(RotationUtils.serverPitch);
            } else {
               if (this.mc.thePlayer.inventory.currentItem == InventoryUtils.getBlockSlot(false)) {
                  this.mc.rightClickMouse();
               }

               this.timer.reset();
            }
         }
      }
   }

   @EventListener
   public void onMove(EventMoveFlying e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            e.setYaw(RotationUtils.serverYaw);
         }
      }
   }

   @EventListener
   public void onJump(EventJump e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            e.setYaw(RotationUtils.serverYaw);
         }
      }
   }

   @EventListener
   public void onLook(EventLook e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
         }
      }
   }

   @EventListener
   public void onRotationRender(EventRenderRotation e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.cancel) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
         }
      }
   }
}
