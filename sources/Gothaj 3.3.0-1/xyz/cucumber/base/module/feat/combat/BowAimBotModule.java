package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventUpdate;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Automatically aims at player with bow",
   name = "Bow Aim Bot",
   key = 0,
   priority = ArrayPriority.HIGH
)
public class BowAimBotModule extends Mod {
   public KillAuraModule ka;
   public Timer timer = new Timer();
   public EntityLivingBase target;
   public boolean cancel;
   public float velocity;

   @Override
   public void onEnable() {
      this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
   }

   @Override
   public void onDisable() {
      RotationUtils.customRots = false;
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onUpdate(EventUpdate e) {
      this.target = EntityUtils.getTarget(
         80.0,
         this.ka.Targets.getMode(),
         "Off",
         400,
         Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
         this.ka.TroughWalls.isEnabled(),
         this.ka.attackInvisible.isEnabled(),
         this.ka.attackDead.isEnabled()
      );
      if (this.mc.thePlayer.getHeldItem() == null) {
         this.target = null;
         if (this.cancel) {
            RotationUtils.customRots = false;
            this.cancel = false;
         }
      } else if (this.mc.thePlayer.getHeldItem().getItem() == null) {
         this.target = null;
         if (this.cancel) {
            RotationUtils.customRots = false;
            this.cancel = false;
         }
      } else if (!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow)) {
         this.target = null;
         if (this.cancel) {
            RotationUtils.customRots = false;
            this.cancel = false;
         }
      } else if (!this.mc.thePlayer.isUsingItem()) {
         this.target = null;
         if (this.cancel) {
            RotationUtils.customRots = false;
            this.cancel = false;
         }
      } else if (this.mc.thePlayer.getItemInUseDuration() <= 1) {
         this.target = null;
         if (this.cancel) {
            RotationUtils.customRots = false;
            this.cancel = false;
         }
      } else if (this.target != null) {
         this.cancel = true;
         float[] rots = this.getBowRotations(this.target);
         RotationUtils.customRots = true;
         RotationUtils.serverYaw = rots[0];
         RotationUtils.serverPitch = rots[1];
      }
   }

   @EventListener
   public void onMotion(EventMotion e) {
      if (e.getType() == EventType.PRE) {
         if (this.target != null) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
         }
      }
   }

   @EventListener
   public void onMove(EventMoveFlying e) {
      if (this.target != null) {
         e.setYaw(RotationUtils.serverYaw);
      }
   }

   @EventListener
   public void onJump(EventJump e) {
      if (this.target != null) {
         e.setYaw(RotationUtils.serverYaw);
      }
   }

   @EventListener
   public void onLook(EventLook e) {
      if (this.target != null) {
         e.setYaw(RotationUtils.serverYaw);
         e.setPitch(RotationUtils.serverPitch);
      }
   }

   @EventListener
   public void onRotation(EventRenderRotation e) {
      if (this.target != null) {
         e.setYaw(RotationUtils.serverYaw);
         e.setPitch(RotationUtils.serverPitch);
      }
   }

   public float[] getBowRotations(EntityLivingBase entity) {
      this.velocity = (float)(72000 - this.mc.thePlayer.getItemInUseCount()) / 20.0F;
      this.velocity = (this.velocity * this.velocity + this.velocity * 2.0F) / 3.0F;
      if (this.velocity > 1.0F) {
         this.velocity = 1.0F;
      }

      double d = (double)this.mc.thePlayer.getDistanceToEntity(entity) / 2.5;
      double posX = this.target.posX + (this.target.posX - this.target.prevPosX) * d - this.mc.thePlayer.posX;
      double posY = this.target.posY
         + (this.target.posY - this.target.prevPosY) * 1.0
         + (double)this.target.height * 0.5
         - this.mc.thePlayer.posY
         - (double)this.mc.thePlayer.getEyeHeight();
      double posZ = this.target.posZ + (this.target.posZ - this.target.prevPosZ) * d - this.mc.thePlayer.posZ;
      float yaw = 0.0F;
      float pitch = 0.0F;
      yaw = (float)Math.toDegrees(Math.atan2(posZ, posX)) - 90.0F;
      double hDistance = Math.sqrt(posX * posX + posZ * posZ);
      double hDistanceSq = hDistance * hDistance;
      float g = 0.006F;
      float velocitySq = this.velocity * this.velocity;
      float velocityPow4 = velocitySq * velocitySq;
      float neededPitch = (float)(
         -Math.toDegrees(
            Math.atan(
               ((double)velocitySq - Math.sqrt((double)velocityPow4 - (double)g * ((double)g * hDistanceSq + 2.0 * posY * (double)velocitySq)))
                  / ((double)g * hDistance)
            )
         )
      );
      return Float.isNaN(neededPitch)
         ? RotationUtils.getInstantTargetRotation(entity, entity.posX, entity.posY, entity.posZ, false)
         : new float[]{yaw, neededPitch};
   }
}
