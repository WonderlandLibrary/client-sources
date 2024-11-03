package net.augustus.modules.combat;

import java.awt.Color;
import net.augustus.events.EventClick;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.notify.GeneralNotifyManager;
import net.augustus.notify.NotificationType;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public final DoubleValue minCPS = new DoubleValue(1, "MinCPS", this, 10.0, 0.0, 20.0, 0);
   public final DoubleValue maxCPS = new DoubleValue(5, "MaxCPS", this, 14.0, 0.0, 20.0, 0);
   public DoubleValue cpsCap;
   public DoubleValue cpsCapRMin;
   public DoubleValue cpsCapRMax;

   public BooleanValue spikes;
   public DoubleValue spikesDelayMin;
   public DoubleValue spikesDelayMax;
   public DoubleValue spikesDurationMin;
   public DoubleValue spikesDurationMax;
   public DoubleValue minSCPS;
   public DoubleValue maxSCPS;
   public BooleanValue drops;
   public DoubleValue dropsDelayMin;
   public DoubleValue dropsDelayMax;
   public DoubleValue dropsDurationMin;
   public DoubleValue dropsDurationMax;
   public DoubleValue minDCPS;
   public DoubleValue maxDCPS;

   public final BooleanValue smart = new BooleanValue(20, "Smart", this, false);
   public final BooleanValue onlyOnTarget = new BooleanValue(2, "OnlyOnTarget", this, false);
   private final TimeHelper timeHelper = new TimeHelper();
   private long randomDelay = 100L;

   private final TimeHelper spikesDelayTimeHelper = new TimeHelper();
   private final TimeHelper spikesDurationTimeHelper = new TimeHelper();
   private final TimeHelper dropsDelayTimeHelper = new TimeHelper();
   private final TimeHelper dropsDurationTimeHelper = new TimeHelper();

   public long nextSpikeDelay = 0, spikeDuration = 0;
   public long nextDropDelay = 0, dropDuration = 0;
   public boolean spikeCPS = false, dropCPS = false;

   public AutoClicker() {
      super("AutoClicker", new Color(82, 186, 74), Categorys.COMBAT);

      cpsCap = new DoubleValue(23465, "cpsCap", this, 18.0, 1.0, 40.0, 0);
      cpsCapRMin = new DoubleValue(3145672, "cpsCapRMin", this, 18.0, 1.0, 40.0, 0);
      cpsCapRMax = new DoubleValue(876534, "cpsCapRMax", this, 18.0, 1.0, 40.0, 0);
      spikes = new BooleanValue(43567563, "Spikes", this, false);
      spikesDelayMin = new DoubleValue(325436765, "SpikesDelayMin", this, 7000.0, 1.0, 10000.0, 0);
      spikesDelayMax = new DoubleValue(5347624, "SpikesDelayMax", this, 8500, 1.0, 10000.0, 0);
      spikesDurationMin = new DoubleValue(1321356785, "SpikesDurationMin", this, 2000.0, 1.0, 10000.0, 0);
      spikesDurationMax = new DoubleValue(23432543, "SpikesDurationMax", this, 3000.0, 1.0, 10000.0, 0);
      minSCPS = new DoubleValue(76234325, "MinSpikeCPS", this, 4.0, 1.0, 40.0, 0);
      maxSCPS = new DoubleValue(568346, "MaxSpikeCPS", this, 6.0, 1.0, 40.0, 0);
      drops = new BooleanValue(575686754, "Drops", this, false);
      dropsDelayMin = new DoubleValue(3456765, "DropsDelayMin", this, 7000.0, 1.0, 10000.0, 0);
      dropsDelayMax = new DoubleValue(756645367, "DropsDelayMax", this, 8500, 1.0, 10000.0, 0);
      dropsDurationMin = new DoubleValue(23564667, "DropsDurationMin", this, 2000.0, 1.0, 10000.0, 0);
      dropsDurationMax = new DoubleValue(875466, "DropsDurationMax", this, 3000.0, 1.0, 10000.0, 0);
      minDCPS = new DoubleValue(32453467, "MinDropCPS", this, 4.0, 1.0, 40.0, 0);
      maxDCPS = new DoubleValue(87656653, "MaxDropCPS", this, 6.0, 1.0, 40.0, 0);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.randomDelay = 0L;
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      // prevent flags on anticheats like antigamingchair
      if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air)
         return;

      if (Mouse.isButtonDown(0) && mc.currentScreen == null) {
         if (!mc.thePlayer.isUsingItem()) {
            if (this.attack()) {
               if (this.onlyOnTarget.getBoolean()) {
                  if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                     mc.clickMouse();
                     this.timeHelper.reset();
                     this.setRandomDelay();
                  }
               } else {
                  mc.clickMouse();
                  this.timeHelper.reset();
                  this.setRandomDelay();
               }
            }

            while(mc.gameSettings.keyBindUseItem.isPressed()) {
               mc.rightClickMouse();
            }
         } else {
            if (!mc.gameSettings.keyBindUseItem.isKeyDown()) {
               mc.playerController.onStoppedUsingItem(mc.thePlayer);
            }

            while(mc.gameSettings.keyBindAttack.isPressed()) {
            }

            while(mc.gameSettings.keyBindUseItem.isPressed()) {
            }

            while(mc.gameSettings.keyBindPickBlock.isPressed()) {
            }
         }

         if (mc.gameSettings.keyBindUseItem.isKeyDown() && mc.getRightClickDelayTimer() == 0 && !mc.thePlayer.isUsingItem()) {
            mc.rightClickMouse();
         }

         if (mc.currentScreen == null) {
         }

         mc.sendClickBlockToController(false);
         eventClick.setCanceled(true);
      }
   }

   private boolean attack() {
      if (this.smart.getBoolean()
         && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
         && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
         EntityLivingBase entity = (EntityLivingBase)mc.objectMouseOver.entityHit;
         if (entity.hurtTime == 0 || entity.hurtTime == 1) {
            return true;
         }
      }

      return this.timeHelper.reached(this.randomDelay);
   }

   private void setRandomDelay() {
      this.randomDelay = (long)RandomUtil.nextSecureInt((int)this.minCPS.getValue(), (int)this.maxCPS.getValue());

      if(this.spikes.getBoolean()) {
         if(spikesDelayTimeHelper.reached(nextSpikeDelay) || !spikesDurationTimeHelper.reached(spikeDuration) && !dropCPS) {
            long spikeCPS = 0;
            if(this.minSCPS.getValue() < this.maxSCPS.getValue()) {
               spikeCPS = (long)RandomUtil.nextSecureDouble((long)(this.minSCPS.getValue()), (long)(this.maxSCPS.getValue()));
            }else {
               GeneralNotifyManager.addNotification("Killaura CPS Spikes setting error.", NotificationType.Error);
               spikeCPS = (long)(this.minSCPS.getValue());
            }
            double expectedCPS = (int)(1000.0 / this.randomDelay) + spikeCPS;
            long processedDelay = (long) (1000.0 / expectedCPS);
            this.randomDelay = (long) (1000.0 / expectedCPS);
//    			  ChatUtil.sendChat("spike");
            this.spikeCPS = true;

            if(spikesDurationTimeHelper.reached(spikeDuration)) {
               //resetting the timer
               if(this.spikesDelayMin.getValue() < this.spikesDelayMax.getValue()) {
                  nextSpikeDelay = (long)RandomUtil.nextSecureDouble((long)(this.spikesDelayMin.getValue()), (long)(this.spikesDelayMax.getValue()));
               }else {
                  GeneralNotifyManager.addNotification("CPS Spikes setting error.", NotificationType.Error);
                  nextSpikeDelay = (long)this.spikesDelayMin.getValue();
               }
               if(this.spikesDurationMin.getValue() < this.spikesDurationMax.getValue()) {
                  spikeDuration = (long)RandomUtil.nextSecureDouble((long)(this.spikesDurationMin.getValue()), (long)(this.spikesDurationMax.getValue()));
               }else {
                  GeneralNotifyManager.addNotification("CPS Spikes setting error.", NotificationType.Error);
                  spikeDuration = (long)this.spikesDurationMin.getValue();
               }
               this.spikeCPS = false;
               spikesDurationTimeHelper.reset();
               spikesDelayTimeHelper.reset();
            }
         }
      }
      if(this.drops.getBoolean()) {
         if(dropsDelayTimeHelper.reached(nextDropDelay) || !dropsDurationTimeHelper.reached(dropDuration) && !spikeCPS) {
            long dropCPS = 0;
            if(this.minSCPS.getValue() < this.maxSCPS.getValue()) {
               dropCPS = (long)RandomUtil.nextSecureDouble((long)(this.minSCPS.getValue()), (long)(this.maxSCPS.getValue()));
            }else {
               GeneralNotifyManager.addNotification("CPS drops setting error.", NotificationType.Error);
               dropCPS = (long)(this.minSCPS.getValue());
            }
            double expectedCPS = (int)(1000.0 / this.randomDelay) - dropCPS;
            long processedDelay = (long) (1000.0 / expectedCPS);
            this.randomDelay = (long) (1000.0 / expectedCPS);
//    			  ChatUtil.sendChat("drop");
            this.dropCPS = true;

            if(dropsDurationTimeHelper.reached(dropDuration)) {
               //resetting the timer
               if(this.dropsDelayMin.getValue() < this.dropsDelayMax.getValue()) {
                  nextDropDelay = (long)RandomUtil.nextSecureDouble((long)(this.dropsDelayMin.getValue()), (long)(this.dropsDelayMax.getValue()));
               }else {
                  GeneralNotifyManager.addNotification("Killaura CPS Drops setting error.", NotificationType.Error);
                  nextDropDelay = (long)this.dropsDelayMin.getValue();
               }
               if(this.dropsDurationMin.getValue() < this.dropsDurationMax.getValue()) {
                  dropDuration = (long)RandomUtil.nextSecureDouble((long)(this.dropsDurationMin.getValue()), (long)(this.dropsDurationMax.getValue()));
               }else {
                  GeneralNotifyManager.addNotification("Killaura CPS Drops setting error.", NotificationType.Error);
                  dropDuration = (long)this.dropsDurationMin.getValue();
               }
               this.dropCPS = false;
               dropsDurationTimeHelper.reset();
               dropsDelayTimeHelper.reset();
            }
         }
      }

      if(this.cpsCap.getValue() > 0 && 1000.0 / this.randomDelay > this.cpsCap.getValue()) {
//			  ChatUtil.sendChat("cap");
         if(!(this.cpsCapRMin.getValue() > this.cpsCap.getValue() && this.cpsCapRMax.getValue() > this.cpsCap.getValue())) {
            this.randomDelay = (long)RandomUtil.nextSecureDouble((long)(1000.0 / this.cpsCapRMin.getValue()), (long)(1000.0 / this.cpsCapRMax.getValue()));
         }else {
            GeneralNotifyManager.addNotification("Killaura CPS CAP setting error.", NotificationType.Error);
            this.randomDelay = (long)(1000.0 / this.cpsCap.getValue());
         }
      }
   }
}
