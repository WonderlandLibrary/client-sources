package net.augustus.clickgui;

import net.augustus.events.EventClickGui;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.interfaces.SM;
import net.lenni0451.eventapi.reflection.EventTarget;

public class SettingSorter implements MC, MM, SM {
   @EventTarget
   public void onEventClickGui(EventClickGui eventClickGui) {
      split1();
      if (mm.killAura.mode.getSelected().equals("Advanced")) {
         split2();
         split3();
      } else {
         split4();
      }
      split5();
      split6();
      split7();
      split8();
      split9();
      split10();
   }

   private void split9() {
      mm.spinBot.spinSpeed.setVisible(mm.spinBot.mode.getSelected().equals("Spin"));
      mm.blockFly.latestPlace.setVisible(mm.blockFly.latestRotate.getBoolean() && mm.blockFly.rayCast.getBoolean());
      mm.blockFly.playerYaw.setVisible(mm.blockFly.rayCast.getBoolean());
      mm.blockFly.moonWalk.setVisible(mm.blockFly.playerYaw.isVisible() && mm.blockFly.playerYaw.getBoolean() && !mm.blockFly.godBridge.getBoolean());
      mm.blockFly.godBridge.setVisible(mm.blockFly.playerYaw.isVisible() && mm.blockFly.playerYaw.getBoolean() && !mm.blockFly.moonWalk.getBoolean());
      mm.blockFly.rotateToBlock.setVisible(!mm.blockFly.rayCast.getBoolean());
      mm.blockFly.correctSide.setVisible(mm.blockFly.rotateToBlock.isVisible() && mm.blockFly.rotateToBlock.getBoolean());
      mm.blockFly.sneakDelayBool.setVisible(mm.blockFly.sneak.getBoolean());
      mm.blockFly.sneakDelay.setVisible(mm.blockFly.sneakDelayBool.isVisible() && mm.blockFly.sneakDelayBool.getBoolean());
      mm.blockFly.sneakBlocks.setVisible(!mm.blockFly.sneakDelayBool.getBoolean() && mm.blockFly.sneak.getBoolean());
      mm.blockFly.sneakBlocksDiagonal.setVisible(!mm.blockFly.sneakDelayBool.getBoolean() && mm.blockFly.sneak.getBoolean());
      mm.blockFly.sneakTicks.setVisible(mm.blockFly.sneak.getBoolean());
      mm.blockFly.sneakOnPlace.setVisible(mm.blockFly.sneak.getBoolean());
      mm.blockFly.adStrafe.setVisible(mm.blockFly.playerYaw.getBoolean() && !mm.blockFly.moonWalk.getBoolean());
      mm.blockFly.adStrafeLegit.setVisible(mm.blockFly.adStrafe.getBoolean() && mm.blockFly.playerYaw.getBoolean() && !mm.blockFly.moonWalk.getBoolean());
      mm.blockFly.spamClickDelay.setVisible(mm.blockFly.spamClick.getBoolean() && !mm.blockFly.intaveHit.getBoolean());
      mm.blockFly.intaveHit.setVisible(mm.blockFly.spamClick.getBoolean());
      mm.blockFly.sameY.setVisible(mm.blockFly.rayCast.getBoolean());
      mm.blockFly.blockSafe.setVisible(mm.blockFly.playerYaw.getBoolean());
      mm.backTrack.targets.setVisible(!mm.backTrack.onlyKillAura.getBoolean());
      mm.backTrack.range.setVisible(!mm.backTrack.onlyKillAura.getBoolean());
      mm.customGlint.customColor.setVisible(!mm.customGlint.removeGlint.getBoolean());
      mm.customGlint.glintSpeed.setVisible(!mm.customGlint.removeGlint.getBoolean());
      mm.customGlint.color.setVisible(mm.customGlint.customColor.getBoolean() && mm.customGlint.customColor.isVisible());
      mm.antiFireBall.range.setVisible(!mm.antiFireBall.rotate.getBoolean());
      mm.antiFireBall.yawSpeed.setVisible(mm.antiFireBall.rotate.getBoolean());
      mm.antiFireBall.pitchSpeed.setVisible(mm.antiFireBall.rotate.getBoolean());
      mm.antiFireBall.slowDown.setVisible(mm.antiFireBall.rotate.getBoolean());
      mm.antiFireBall.moveFix.setVisible(mm.antiFireBall.rotate.getBoolean());
   }

   private void split8() {
      mm.line.killAuraColor.setVisible(mm.line.killAura.getBoolean());
      mm.line.killAuraLineWidth.setVisible(mm.line.killAura.getBoolean());
      mm.fly.autoJump.setVisible(mm.fly.mode.getSelected().equals("AirJump"));
      mm.fly.sendOnGroundPacket.setVisible(mm.fly.mode.getSelected().equals("AirJump"));
      mm.fly.speed.setVisible(mm.fly.mode.getSelected().equals("Vanilla") || mm.fly.mode.getSelected().equals("SkycaveBad") || mm.fly.mode.getSelected().equals("PolarTest") || mm.fly.mode.getSelected().equals("Teleport") || mm.fly.mode.getSelected().equals("SilentACAbuse") || mm.fly.mode.getSelected().equals("FurtherTeleport") || mm.fly.mode.getSelected().equals("Motion") || mm.fly.mode.getSelected().equals("FoxAC") || mm.fly.mode.getSelected().equals("Packet"));
      mm.fly.verusspeed.setVisible(mm.fly.mode.getSelected().equals("Verus"));
      mm.arrayList.vegaColor1.setVisible(mm.arrayList.mode.getSelected().equals("Vega"));
      mm.arrayList.vegaColor2.setVisible(mm.arrayList.mode.getSelected().equals("Vega"));
      mm.spider.motion.setVisible(mm.spider.mode.getSelected().equals("Basic") || mm.spider.mode.getSelected().equals("Jump") && mm.spider.customJumpMotion.getBoolean());
      mm.spider.onGroundPacket.setVisible(mm.spider.mode.getSelected().equals("Jump"));
      mm.spider.motionToJump.setVisible(mm.spider.mode.getSelected().equals("Jump"));
      mm.spider.customJumpMotion.setVisible(mm.spider.mode.getSelected().equals("Jump"));
      mm.storageESP.color.setVisible(!mm.storageESP.rainbow.getBoolean());
      mm.storageESP.lineWidth.setVisible(mm.storageESP.mode.getSelected().equals("Box") || mm.storageESP.mode.getSelected().equals("OtherBox"));
      mm.storageESP.rainbowAlpha.setVisible(mm.storageESP.rainbow.getBoolean() && (mm.storageESP.mode.getSelected().equals("Box") || mm.storageESP.mode.getSelected().equals("OtherBox")));
      mm.storageESP.rainbowSpeed.setVisible(mm.storageESP.rainbow.getBoolean());
      mm.blockESP.rainbowAlpha.setVisible(mm.blockESP.rainbow.getBoolean());
      mm.blockESP.rainbowSpeed.setVisible(mm.blockESP.rainbow.getBoolean());
      mm.blockESP.color.setVisible(!mm.blockESP.rainbow.getBoolean());
      mm.tracers.color.setVisible(!mm.tracers.rainbow.getBoolean() && !mm.tracers.staticColor.getBoolean());
      mm.tracers.rainbow.setVisible(!mm.tracers.staticColor.getBoolean());
      mm.tracers.staticColor.setVisible(!mm.tracers.rainbow.getBoolean());
      mm.tracers.rainbowSpeed.setVisible(mm.tracers.rainbow.getBoolean());
      mm.crossHair.color.setVisible(!mm.crossHair.rainbow.getBoolean());
      mm.crossHair.rainbowSpeed.setVisible(mm.crossHair.rainbow.getBoolean());
      mm.blockFly.resetRotation.setVisible(mm.blockFly.snap.getBoolean());
      mm.blockFly.predict.setVisible(mm.blockFly.latestRotate.getBoolean() && mm.blockFly.rayCast.getBoolean() && mm.blockFly.latestRotate.isVisible());
      mm.blockFly.backupTicks.setVisible(mm.blockFly.latestRotate.getBoolean() && mm.blockFly.latestRotate.isVisible());
      mm.blockFly.latestRotate.setVisible(mm.blockFly.rayCast.getBoolean());
   }

   private void split7() {
      mm.esp.rainbowSpeed.setVisible(mm.esp.rainbow.getBoolean());
      mm.esp.rainbowAlpha.setVisible(mm.esp.rainbow.getBoolean() && mm.esp.mode.getSelected().equals("Box"));
      mm.esp.lineWidth.setVisible(mm.esp.mode.getSelected().equals("Box"));
      mm.esp.otherColorOnHit.setVisible(!mm.esp.mode.getSelected().equals("Vanilla"));
      mm.esp.hitColor.setVisible(mm.esp.otherColorOnHit.getBoolean() && !mm.esp.mode.getSelected().equals("Vanilla"));
      mm.scoreboard.yCord.setVisible(!mm.scoreboard.remove.getBoolean() && !mm.scoreboard.stick.getBoolean());
      mm.scoreboard.xCord.setVisible(!mm.scoreboard.remove.getBoolean());
      mm.scoreboard.stick.setVisible(!mm.scoreboard.remove.getBoolean());
      mm.fucker.instant.setVisible(mm.fucker.action.getSelected().equals("Break"));
      mm.antiBot.ticksExisted.setVisible(mm.antiBot.mode.getSelected().equals("Custom"));
      mm.antiBot.checkTab.setVisible(mm.antiBot.mode.getSelected().equals("Custom"));
      mm.autoSoup.autoClose.setVisible(mm.autoSoup.fill.getBoolean());
      mm.noSlow.swordForward.setVisible(mm.noSlow.swordSlowdown.getBoolean());
      mm.noSlow.swordStrafe.setVisible(mm.noSlow.swordSlowdown.getBoolean());
      mm.noSlow.bowForward.setVisible(mm.noSlow.bowSlowdown.getBoolean());
      mm.noSlow.bowStrafe.setVisible(mm.noSlow.bowSlowdown.getBoolean());
      mm.noSlow.restForward.setVisible(mm.noSlow.restSlowdown.getBoolean());
      mm.noSlow.restStrafe.setVisible(mm.noSlow.restSlowdown.getBoolean());
      mm.noSlow.timerSword.setVisible(mm.noSlow.swordTimer.getBoolean());
      mm.noSlow.timerBow.setVisible(mm.noSlow.bowTimer.getBoolean());
      mm.noSlow.timerRest.setVisible(mm.noSlow.restTimer.getBoolean());
      mm.line.lineWidth.setVisible(mm.line.line.getBoolean());
      mm.line.color.setVisible(mm.line.line.getBoolean());
      mm.line.lineTime.setVisible(mm.line.line.getBoolean());
      mm.line.killAuraLineTime.setVisible(mm.line.killAura.getBoolean());
   }

   private void split6() {
      mm.inventoryCleaner.noMove.setVisible(mm.inventoryCleaner.mode.getSelected().equals("SpoofInv"));
      mm.inventoryCleaner.interactionCheck.setVisible(mm.inventoryCleaner.mode.getSelected().equals("SpoofInv"));
      mm.noFall.fallDistance.setVisible(mm.noFall.mode.getSelected().equals("OnGround"));
      mm.noFall.lookRange.setVisible(mm.noFall.mode.getSelected().equals("Legit"));
      mm.noFall.yawSpeed.setVisible(mm.noFall.mode.getSelected().equals("Legit"));
      mm.noFall.pitchSpeed.setVisible(mm.noFall.mode.getSelected().equals("Legit"));
      mm.noFall.delay.setVisible(mm.noFall.mode.getSelected().equals("Legit"));
      mm.noFall.legitFallDistance.setVisible(mm.noFall.mode.getSelected().equals("Legit"));
      mm.jesus.speed.setVisible(mm.jesus.mode.getSelected().equals("Speed"));
      mm.nameTags.height.setVisible(!mm.nameTags.mode.getSelected().equals("None"));
      mm.nameTags.armor.setVisible(!mm.nameTags.mode.getSelected().equals("None"));
      mm.nameTags.scale.setVisible(!mm.nameTags.mode.getSelected().equals("None"));
      mm.tracers.color.setVisible(mm.tracers.staticColor.getBoolean());
      mm.hud.color.setVisible(!mm.hud.mode.getSelected().equals("None"));
      mm.hud.backGround.setVisible(!mm.hud.mode.getSelected().equals("None"));
      mm.hud.backGroundColor.setVisible(!mm.hud.mode.getSelected().equals("None") && mm.hud.backGround.getBoolean());
      mm.hud.size.setVisible(mm.hud.mode.getSelected().equals("Augustus"));
      mm.speed.vanillaSpeed.setVisible(mm.speed.mode.getSelected().equals("Vanilla") || mm.speed.mode.getSelected().equals("VanillaHop") || mm.speed.mode.getSelected().equals("Polar"));
      mm.speed.vanillaHeight.setVisible(mm.speed.mode.getSelected().equals("VanillaHop"));
      mm.speed.damageBoost.setVisible(mm.speed.mode.getSelected().equals("Verus"));
      mm.speed.dmgSpeed.setVisible(mm.speed.mode.getSelected().equals("Verus"));
      if (!mm.esp.rainbow.getBoolean()) {
         mm.esp.color.setVisible(!mm.esp.mode.getSelected().equals("Vanilla"));
         mm.esp.outlineColor.setVisible(mm.esp.mode.getSelected().equals("Vanilla"));
      } else {
         mm.esp.color.setVisible(false);
         mm.esp.outlineColor.setVisible(false);
      }
   }

   public void split1() {
      mm.arrayList.backGroundColor.setVisible(mm.arrayList.backGround.getBoolean());
      mm.arrayList.staticColor.setVisible(!mm.arrayList.randomColor.getBoolean() && !mm.arrayList.rainbow.getBoolean());
      mm.arrayList.randomColor.setVisible(!mm.arrayList.rainbow.getBoolean());
      mm.arrayList.rainbow.setVisible(!mm.arrayList.randomColor.getBoolean());
      mm.arrayList.rainbowSpeed.setVisible(mm.arrayList.rainbow.getBoolean());
      mm.killAura.maxCPS.setValue(Math.max(mm.killAura.minCPS.getValue(), mm.killAura.maxCPS.getValue()));
      mm.killAura.minCPS.setValue(Math.min(mm.killAura.minCPS.getValue(), mm.killAura.maxCPS.getValue()));
   }
   public void split2() {
      mm.killAura.coolDown.setVisible(true);
      mm.killAura.targetRandom.setVisible(true);
      mm.killAura.rangeMode.setVisible(true);
      mm.killAura.blockMode.setVisible(true);
      mm.killAura.attackMode.setVisible(true);
      mm.killAura.yawSpeedMin.setVisible(true);
      mm.killAura.yawSpeedMax.setVisible(true);
      mm.killAura.pitchSpeedMin.setVisible(true);
      mm.killAura.pitchSpeedMax.setVisible(true);
      mm.killAura.randomStrength.setVisible(true);
      mm.killAura.interpolation.setVisible(true);
      mm.killAura.smoothBackRotate.setVisible(true);
      mm.killAura.stopOnTarget.setVisible(true);
      mm.killAura.smartAim.setVisible(true);
      mm.killAura.inInv.setVisible(false);
      mm.killAura.randomize.setVisible(false);
      mm.killAura.multi.setVisible(false);
      mm.killAura.targetRandom.setVisible(true);
      mm.killAura.hitChance.setVisible(true);
      mm.killAura.block.setVisible(false);
      mm.killAura.moveFix.setVisible(true);
      mm.killAura.heuristics.setVisible(true);
      mm.killAura.preHit.setVisible(true);
      mm.killAura.advancedRots.setVisible(true);
   }
   public void split3() {
      mm.killAura.hazeAdd.setVisible(mm.killAura.hazeRange.getBoolean());
      mm.killAura.hazeMax.setVisible(mm.killAura.hazeRange.getBoolean());
      mm.killAura.hazeRange.setVisible(true);
      mm.killAura.perfectHit.setVisible(true);
      mm.killAura.perfectHitGomme.setVisible(mm.killAura.perfectHit.isVisible() && mm.killAura.perfectHit.getBoolean());
      mm.killAura.yawSpeedMax.setValue(Math.max(mm.killAura.yawSpeedMin.getValue(), mm.killAura.yawSpeedMax.getValue()));
      mm.killAura.yawSpeedMin.setValue(Math.min(mm.killAura.yawSpeedMin.getValue(), mm.killAura.yawSpeedMax.getValue()));
      mm.killAura.pitchSpeedMax.setValue(Math.max(mm.killAura.pitchSpeedMin.getValue(), mm.killAura.pitchSpeedMax.getValue()));
      mm.killAura.pitchSpeedMin.setValue(Math.min(mm.killAura.pitchSpeedMin.getValue(), mm.killAura.pitchSpeedMax.getValue()));
      mm.killAura.silentMoveFix.setVisible(mm.killAura.moveFix.getBoolean());
      mm.killAura.intave.setVisible(true);
	  mm.killAura.autoResetTick.setVisible(mm.killAura.smarthitv2.getBoolean());
	  mm.killAura.resetTimer.setVisible(mm.killAura.smarthitv2.getBoolean());
	  mm.killAura.resetDuration.setVisible(mm.killAura.smarthitv2.getBoolean());
      if(!mm.killAura.blockMode.getSelected().equalsIgnoreCase("None")) {
    	  mm.killAura.smartBlock.setVisible(true);
    	  mm.killAura.blockConditions.setVisible(mm.killAura.smartBlock.getBoolean());
    	  mm.killAura.blockHealth.setVisible(mm.killAura.health.getBoolean() && mm.killAura.smartBlock.getBoolean());
    	  mm.killAura.rangeBlockValue.setVisible(mm.killAura.rangeBlock.getBoolean() && mm.killAura.smartBlock.getBoolean());
      }else {
    	  mm.killAura.smartBlock.setVisible(false);
    	  mm.killAura.blockConditions.setVisible(false);
    	  mm.killAura.blockHealth.setVisible(false);
      }
      if (mm.killAura.blockMode.getSelected().equalsIgnoreCase("Custom")) {
         mm.killAura.unblockHit.setVisible(true);
         mm.killAura.unblockHitOnly.setVisible(mm.killAura.unblockHit.getBoolean());
         mm.killAura.startBlock.setVisible(mm.killAura.unblockHit.getBoolean() && !mm.killAura.unblockHitOnly.getBoolean());
         mm.killAura.endBlock.setVisible(mm.killAura.unblockHit.getBoolean() && !mm.killAura.unblockHitOnly.getBoolean());
         mm.killAura.endBlockHitOnly.setVisible(mm.killAura.unblockHitOnly.getBoolean() && mm.killAura.unblockHit.getBoolean());
      } else {
         mm.killAura.unblockHit.setVisible(false);
         mm.killAura.unblockHitOnly.setVisible(false);
         mm.killAura.startBlock.setVisible(false);
         mm.killAura.endBlock.setVisible(false);
         mm.killAura.endBlockHitOnly.setVisible(false);
      }
   }
   public void split4() {
      mm.killAura.hazeAdd.setVisible(false);
      mm.killAura.hazeMax.setVisible(false);
      mm.killAura.hazeRange.setVisible(false);
      mm.killAura.minCPS.setVisible(true);
      mm.killAura.maxCPS.setVisible(true);
      mm.killAura.perfectHit.setVisible(false);
      mm.killAura.perfectHitGomme.setVisible(false);
      mm.killAura.coolDown.setVisible(false);
      mm.killAura.multi.setVisible(true);
      mm.killAura.targetRandom.setVisible(false);
      mm.killAura.rangeMode.setVisible(false);
      mm.killAura.blockMode.setVisible(false);
      mm.killAura.attackMode.setVisible(false);
      mm.killAura.yawSpeedMin.setVisible(false);
      mm.killAura.yawSpeedMax.setVisible(false);
      mm.killAura.pitchSpeedMin.setVisible(false);
      mm.killAura.pitchSpeedMax.setVisible(false);
      mm.killAura.randomStrength.setVisible(false);
      mm.killAura.interpolation.setVisible(false);
      mm.killAura.smoothBackRotate.setVisible(false);
      mm.killAura.stopOnTarget.setVisible(false);
      mm.killAura.moveFix.setVisible(false);
      mm.killAura.silentMoveFix.setVisible(false);
      mm.killAura.smartAim.setVisible(false);
      mm.killAura.inInv.setVisible(true);
      mm.killAura.randomize.setVisible(true);
      mm.killAura.targetRandom.setVisible(false);
      mm.killAura.hitChance.setVisible(false);
      mm.killAura.startBlock.setVisible(false);
      mm.killAura.endBlock.setVisible(false);
      mm.killAura.unblockHit.setVisible(false);
      mm.killAura.unblockHitOnly.setVisible(false);
      mm.killAura.endBlockHitOnly.setVisible(false);
      mm.killAura.block.setVisible(true);
      mm.killAura.heuristics.setVisible(false);
      mm.killAura.preHit.setVisible(false);
      mm.killAura.advancedRots.setVisible(false);
      mm.killAura.minCPS.setVisible(true);
      mm.killAura.maxCPS.setVisible(true);
      mm.killAura.intave.setVisible(false);
   }
   public void split5() {
      mm.velocity.ignoreExplosion.setVisible(mm.velocity.mode.getSelected().equalsIgnoreCase("Basic"));
      mm.velocity.YValue.setVisible(mm.velocity.mode.getSelected().equalsIgnoreCase("Basic"));
      mm.velocity.XZValue.setVisible(mm.velocity.mode.getSelected().equalsIgnoreCase("Basic"));
      mm.velocity.XZValueIntave.setVisible(mm.velocity.mode.getSelected().equalsIgnoreCase("Intave"));
      mm.velocity.jumpIntave.setVisible(mm.velocity.mode.getSelected().equalsIgnoreCase("Intave"));
      mm.velocity.pushStart.setVisible(mm.velocity.mode.getSelected().equals("Push"));
      mm.velocity.pushEnd.setVisible(mm.velocity.mode.getSelected().equals("Push"));
      mm.velocity.pushXZ.setVisible(mm.velocity.mode.getSelected().equals("Push"));
      mm.velocity.pushOnGround.setVisible(mm.velocity.mode.getSelected().equals("Push"));
      mm.velocity.reverseStart.setVisible(mm.velocity.mode.getSelected().equals("Reverse"));
      mm.velocity.karhuStart.setVisible(mm.velocity.mode.getSelected().equals("TickZero"));
      mm.velocity.reverseStrafe.setVisible(mm.velocity.mode.getSelected().equals("Reverse"));
      mm.autoArmor.hotbar.setVisible(mm.autoArmor.mode.getSelected().equals("OpenInv"));
      mm.autoArmor.noMove.setVisible(mm.autoArmor.mode.getSelected().equals("SpoofInv"));
      mm.autoArmor.interactionCheck.setVisible(mm.autoArmor.mode.getSelected().equals("SpoofInv"));
      if (mm.autoArmor.hotbar.isVisible()) {
         mm.autoArmor.gommeQSG.setVisible(mm.autoArmor.hotbar.getBoolean());
         mm.autoArmor.hotbarStartDelay.setVisible(mm.autoArmor.hotbar.getBoolean());
         mm.autoArmor.hotbarDelay.setVisible(mm.autoArmor.hotbar.getBoolean());
      } else {
         mm.autoArmor.gommeQSG.setVisible(mm.autoArmor.mode.getSelected().equals("OpenInv"));
         mm.autoArmor.hotbarStartDelay.setVisible(mm.autoArmor.mode.getSelected().equals("OpenInv"));
         mm.autoArmor.hotbarDelay.setVisible(mm.autoArmor.mode.getSelected().equals("OpenInv"));
      }
   }
   
   public void split10() {
	   mm.oldarmor.color.setVisible(!mm.oldarmor.rainbow.getBoolean());
	   mm.oldarmor.hitColor.setVisible(mm.oldarmor.damageColor.getBoolean());
   }
}
