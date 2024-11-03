package xyz.cucumber.base.module.feat.combat;

import java.util.Random;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.MovingObjectPosition;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventRotation;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Automatically click for you",
   name = "Auto Clicker",
   key = 0,
   priority = ArrayPriority.HIGH
)
public class AutoClickerModule extends Mod {
   public double leftDelay;
   public double rightDelay;
   public boolean shouldLeftClick;
   public boolean shouldRightClick;
   public BooleanSettings left = new BooleanSettings("Left", true);
   public BooleanSettings right = new BooleanSettings("Right", true);
   public NumberSettings minLeftCps = new NumberSettings("Min Left CPS", 10.0, 1.0, 20.0, 1.0);
   public NumberSettings maxLeftCps = new NumberSettings("Max Left CPS", 10.0, 1.0, 20.0, 1.0);
   public NumberSettings minRightCps = new NumberSettings("Min Right CPS", 10.0, 1.0, 20.0, 1.0);
   public NumberSettings maxRightCps = new NumberSettings("Max Right CPS", 10.0, 1.0, 20.0, 1.0);
   public BooleanSettings instantRightClick = new BooleanSettings("instant Right Click", false);
   public BooleanSettings onlyBlock = new BooleanSettings("Only Blocks", true);
   public Timer leftTimer = new Timer();
   public Timer rightTimer = new Timer();
   public Timer timer = new Timer();

   public AutoClickerModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.left, this.right, this.minLeftCps, this.maxLeftCps, this.minRightCps, this.maxRightCps, this.instantRightClick, this.onlyBlock
         }
      );
   }

   @EventListener
   public void onTick(EventTick e) {
      if (this.shouldLeftClick && this.left.isEnabled() && this.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
         this.shouldLeftClick = false;
         this.mc.clickMouseEvent();
      }

      if (this.shouldRightClick && this.right.isEnabled()) {
         this.shouldRightClick = false;
         if (this.onlyBlock.isEnabled()) {
            if (this.mc.thePlayer.getHeldItem() != null
               && this.mc.thePlayer.getHeldItem().getItem() != null
               && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock
               && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
               && this.mc.objectMouseOver.getBlockPos() != null) {
               this.mc.rightClickMouse();
            }
         } else {
            this.mc.rightClickMouse();
         }
      }
   }

   @EventListener
   public void onRotation(EventRotation e) {
      if (this.instantRightClick.isEnabled() && this.mc.gameSettings.keyBindUseItem.pressed) {
         if (this.onlyBlock.isEnabled()) {
            if (this.mc.thePlayer.getHeldItem() != null
               && this.mc.thePlayer.getHeldItem().getItem() != null
               && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock
               && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
               && this.mc.objectMouseOver.getBlockPos() != null) {
               this.mc.rightClickMouse();
            }
         } else {
            this.mc.rightClickMouse();
         }
      }
   }

   @EventListener
   public void onGameLoop(EventGameLoop e) {
      double leftMinValue = this.minLeftCps.getValue();
      double leftMaxValue = this.maxLeftCps.getValue();
      if (this.timer.hasTimeElapsed(100.0, false)) {
         this.leftDelay = leftMinValue + new Random().nextDouble() * (leftMaxValue - leftMinValue);
      }

      if (this.leftDelay < leftMinValue) {
         this.leftDelay = leftMinValue;
      }

      if (this.leftDelay > leftMaxValue) {
         this.leftDelay = leftMaxValue;
      }

      double finalLeftDelay = 1000.0 / this.leftDelay;
      finalLeftDelay -= 5.0;
      if (this.mc.gameSettings.keyBindAttack.pressed && this.leftTimer.hasTimeElapsed(finalLeftDelay, true)) {
         this.shouldLeftClick = true;
      }

      double rightMinValue = this.minRightCps.getValue();
      double rightMaxValue = this.maxRightCps.getValue();
      if (this.timer.hasTimeElapsed(100.0, true)) {
         this.rightDelay = rightMinValue + new Random().nextDouble() * (rightMaxValue - rightMinValue);
      }

      if (this.rightDelay < rightMinValue) {
         this.rightDelay = rightMinValue;
      }

      if (this.rightDelay > rightMaxValue) {
         this.rightDelay = rightMaxValue;
      }

      double finalRightDelay = 1000.0 / this.rightDelay;
      finalRightDelay -= 5.0;
      if (this.mc.gameSettings.keyBindUseItem.pressed && this.rightTimer.hasTimeElapsed(finalRightDelay, true)) {
         this.shouldRightClick = true;
      }
   }
}
