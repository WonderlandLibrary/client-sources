package net.augustus.modules.combat;

import java.awt.Color;
import java.util.Comparator;
import net.augustus.Augustus;
import net.augustus.events.EventClick;
import net.augustus.events.EventClickKillAura;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventSilentMove;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.EventHandler;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MovingObjectPosition;

public class AntiFireBall extends Module {
   public final RotationUtil rotationUtil = new RotationUtil();
   public final BooleanValue rotate = new BooleanValue(8, "Rotate", this, true);
   public final DoubleValue yawSpeed = new DoubleValue(1, "YawSpeed", this, 60.0, 0.0, 180.0, 0);
   public final DoubleValue pitchSpeed = new DoubleValue(2, "PitchSpeed", this, 60.0, 0.0, 180.0, 0);
   public final DoubleValue preRange = new DoubleValue(3, "PreAimRange", this, 4.0, 0.0, 15.0, 1);
   public final DoubleValue delay = new DoubleValue(9, "Delay", this, 60.0, 0.0, 400.0, 0);
   public final DoubleValue range = new DoubleValue(7, "Range", this, 4.0, 0.0, 6.0, 2);
   public final BooleanValue moveFix = new BooleanValue(5, "MoveFix", this, true);
   public final BooleanValue slowDown = new BooleanValue(6, "SlowDown", this, true);
   public final DoubleValue ticksExisted = new DoubleValue(9, "TicksExisted", this, 10.0, 0.0, 20.0, 0);
   private final TimeHelper delayTimeHelper = new TimeHelper();
   public float[] rots = new float[2];
   public float[] lastRots = new float[2];
   private Object[] listOfTargets;
   private Entity fireball;

   public AntiFireBall() {
      super("AntiFireBall", new Color(232, 147, 0), Categorys.COMBAT);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.fireball = null;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.rots = new float[]{Augustus.getInstance().getYawPitchHelper().realYaw, Augustus.getInstance().getYawPitchHelper().realPitch};
      this.lastRots = new float[]{Augustus.getInstance().getYawPitchHelper().realLastYaw, Augustus.getInstance().getYawPitchHelper().realLastPitch};
   }

   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      this.listOfTargets = null;
      this.fireball = null;
      this.listOfTargets = mc.theWorld
         .loadedEntityList
         .stream()
         .filter(this::canAttacked)
         .sorted(Comparator.comparingDouble(entity -> (double)mc.thePlayer.getDistanceToEntity(entity)))
         .toArray();
      if (this.listOfTargets.length > 0) {
         this.fireball = (Entity)this.listOfTargets[0];
      }

      if (this.rotate.getBoolean()) {
         if (this.shouldFireBall()) {
            float yawSpeed = (float)(this.yawSpeed.getValue() + (double)RandomUtil.nextFloat(0.0F, 15.0F));
            float pitchSpeed = (float)(this.yawSpeed.getValue() + (double)RandomUtil.nextFloat(0.0F, 15.0F));
            float[] floats = this.rotationUtil
               .faceEntityCustom(
                  this.fireball, yawSpeed, pitchSpeed, this.rots[0], this.rots[1], "Basic", false, false, false, 0.95F, null, false, false, false, true, true
               );
            this.lastRots = this.rots;
            this.rots = floats;
            this.setRotation();
         } else {
            this.restRotation();
         }
      } else if (this.fireball != null) {
         double range = mc.thePlayer.canEntityBeSeen(this.fireball) ? this.range.getValue() : Math.min(3.0, this.range.getValue());
         if ((double)mc.thePlayer.getDistanceToEntity(this.fireball) < range
            && this.delayTimeHelper.reached(this.delay.getValue() != 0.0 ? (long)(this.delay.getValue() + RandomUtil.nextSecureInt(0, 60)) : 0L)) {
            mc.thePlayer.swingItem();
            mc.playerController.attackEntity(mc.thePlayer, this.fireball);
            this.delayTimeHelper.reset();
         }
      }
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      if (this.shouldFireBall()
         && this.rotate.getBoolean()
         && mc.objectMouseOver != null
         && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
         && this.delayTimeHelper.reached(this.delay.getValue() != 0.0 ? (long)(this.delay.getValue() + RandomUtil.nextSecureInt(0, 60)) : 0L)) {
         eventClick.setCanceled(true);
         if (this.slowDown.getBoolean()) {
            mc.clickMouse();
         } else {
            mc.thePlayer.swingItem();
            if (mc.objectMouseOver.entityHit != null) {
               EventClickKillAura eventClickKillAura = new EventClickKillAura();
               EventHandler.call(eventClickKillAura);
               mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
            }
         }

         this.delayTimeHelper.reset();
         mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
      }
   }

   @EventTarget
   public void onEventMove(EventMove eventMove) {
      if (this.shouldFireBall() && !this.moveFix.getBoolean() && this.rotate.getBoolean()) {
         eventMove.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
      }
   }

   @EventTarget
   public void onEventJump(EventJump eventJump) {
      if (this.shouldFireBall() && !this.moveFix.getBoolean() && this.rotate.getBoolean()) {
         eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
      }
   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      if (this.shouldFireBall() && this.moveFix.getBoolean() && this.rotate.getBoolean()) {
         eventSilentMove.setSilent(true);
      }
   }

   private boolean shouldFireBall() {
      return this.fireball != null && mm.killAura.target == null && !BlockUtil.isScaffoldToggled() && mc.currentScreen == null;
   }

   private boolean canAttacked(Entity entity) {
      return entity instanceof EntityFireball
         && (double)entity.ticksExisted > this.ticksExisted.getValue()
         && (double)mc.thePlayer.getDistanceToEntity(entity) < this.preRange.getValue()
         && (double)mc.thePlayer.getDistanceToEntity(entity) <= mc.thePlayer.getDistance(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
   }

   private void setRotation() {
      if (!BlockUtil.isScaffoldToggled() && mm.killAura.target == null) {
         mc.thePlayer.rotationYaw = this.rots[0];
         mc.thePlayer.rotationPitch = this.rots[1];
         mc.thePlayer.prevRotationYaw = this.lastRots[0];
         mc.thePlayer.prevRotationPitch = this.lastRots[1];
      } else {
         this.restRotation();
      }
   }

   private void restRotation() {
      this.rots = this.lastRots;
      this.rots[0] = Augustus.getInstance().getYawPitchHelper().realYaw;
      this.rots[1] = Augustus.getInstance().getYawPitchHelper().realPitch;
      this.lastRots[0] = Augustus.getInstance().getYawPitchHelper().realLastYaw;
      this.lastRots[1] = Augustus.getInstance().getYawPitchHelper().realLastPitch;
   }
}
