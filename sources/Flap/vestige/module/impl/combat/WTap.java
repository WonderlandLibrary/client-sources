package vestige.module.impl.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.EntityActionEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.MovementUtil;

public class WTap extends Module {
   private boolean taped;
   private boolean stoppedLastTick;
   private final ModeSetting mode = new ModeSetting("Mode", "W-Tap", new String[]{"W-Tap", "S-Tap", "Sneak", "No Stop"});
   private final DoubleSetting delay = new DoubleSetting("Delay", 10.0D, 2.0D, 10.0D, 1.0D);
   private Killaura killauraModule;
   private EntityLivingBase lastCursorTarget;
   private int cursorTargetTicks;

   public WTap() {
      super("WTap", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.mode, this.delay});
   }

   public void onEnable() {
      this.taped = false;
      this.stoppedLastTick = false;
   }

   public void onClientStarted() {
      this.killauraModule = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
   }

   @Listener(3)
   public void onTick(TickEvent event) {
      EntityLivingBase target;
      if (this.mode.is("W-Tap")) {
         if (this.stoppedLastTick) {
            mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
            this.stoppedLastTick = false;
            return;
         }

         target = this.getCurrentTarget();
         if (target != null) {
            if ((double)target.hurtTime >= this.delay.getValue() && mc.thePlayer.onGround && mc.thePlayer.isSprinting()) {
               if (!this.taped) {
                  mc.gameSettings.keyBindSprint.pressed = false;
                  mc.gameSettings.keyBindForward.pressed = false;
                  this.stoppedLastTick = true;
                  this.taped = true;
               }
            } else {
               this.taped = false;
            }
         } else {
            this.taped = false;
         }
      }

      if (this.mode.is("S-Tap")) {
         target = this.getCurrentTarget();
         if (target != null) {
            if ((double)target.hurtTime >= this.delay.getValue() && mc.thePlayer.onGround && MovementUtil.isMoving()) {
               if (!this.taped) {
                  mc.gameSettings.keyBindSprint.pressed = false;
                  mc.gameSettings.keyBindBack.pressed = true;
                  this.taped = true;
                  this.stoppedLastTick = true;
               }
            } else if (this.stoppedLastTick) {
               mc.gameSettings.keyBindBack.pressed = false;
               mc.gameSettings.keyBindForward.pressed = true;
               mc.gameSettings.keyBindSprint.pressed = true;
               this.taped = false;
               this.stoppedLastTick = false;
            }
         } else {
            mc.gameSettings.keyBindBack.pressed = false;
            this.taped = false;
         }
      }

      if (this.mode.is("S-Tap")) {
         target = this.getCurrentTarget();
         if (target != null) {
            if ((double)target.hurtTime >= this.delay.getValue() && mc.thePlayer.onGround && MovementUtil.isMoving()) {
               if (!this.taped) {
                  mc.gameSettings.keyBindSprint.pressed = false;
                  mc.gameSettings.keyBindBack.pressed = true;
                  this.taped = true;
                  this.stoppedLastTick = true;
               }
            } else if (this.stoppedLastTick) {
               mc.gameSettings.keyBindBack.pressed = false;
               mc.gameSettings.keyBindForward.pressed = true;
               mc.gameSettings.keyBindSprint.pressed = true;
               this.taped = false;
               this.stoppedLastTick = false;
            }
         } else {
            mc.gameSettings.keyBindBack.pressed = false;
            this.taped = false;
         }
      }

      if (this.mode.is("Sneak")) {
         target = this.getCurrentTarget();
         if (target != null) {
            if ((double)target.hurtTime >= this.delay.getValue() && mc.thePlayer.onGround && MovementUtil.isMoving()) {
               if (!this.taped) {
                  mc.gameSettings.keyBindSprint.pressed = false;
                  mc.gameSettings.keyBindSneak.pressed = true;
                  this.taped = true;
                  this.stoppedLastTick = true;
               }
            } else if (this.stoppedLastTick) {
               mc.gameSettings.keyBindSneak.pressed = false;
               mc.gameSettings.keyBindSprint.pressed = true;
               this.taped = false;
               this.stoppedLastTick = false;
            }
         } else {
            mc.gameSettings.keyBindSneak.pressed = false;
            this.taped = false;
         }
      }

   }

   @Listener(3)
   public void onEntityAction(EntityActionEvent event) {
      if (this.mode.is("No Stop")) {
         EntityLivingBase target = this.getCurrentTarget();
         if (target != null) {
            if ((double)target.hurtTime >= this.delay.getValue() && mc.thePlayer.onGround && mc.thePlayer.isSprinting()) {
               if (!this.taped) {
                  event.setSprinting(false);
                  this.taped = true;
               }
            } else {
               this.taped = false;
            }
         } else {
            this.taped = false;
         }
      }

   }

   public EntityLivingBase getCurrentTarget() {
      if (this.killauraModule == null) {
         this.killauraModule = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
      }

      if (this.killauraModule.isEnabled() && this.killauraModule.getTarget() != null) {
         return this.killauraModule.getTarget();
      } else if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
         this.lastCursorTarget = (EntityLivingBase)mc.objectMouseOver.entityHit;
         return (EntityLivingBase)mc.objectMouseOver.entityHit;
      } else {
         if (this.lastCursorTarget != null) {
            if (++this.cursorTargetTicks <= 10) {
               return this.lastCursorTarget;
            }

            this.lastCursorTarget = null;
         }

         return null;
      }
   }
}
