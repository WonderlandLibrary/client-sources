package cc.slack.features.modules.impl.combat;

import cc.slack.events.impl.player.JumpEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.StrafeEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.other.RaycastUtil;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.player.BlinkUtil;
import cc.slack.utils.player.RotationUtil;
import io.github.nevalackin.radbus.Listen;
import java.security.SecureRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

@ModuleInfo(
   name = "KillAura",
   category = Category.COMBAT,
   key = 19
)
public class KillAura extends Module {
   private final NumberValue<Double> aimRange = new NumberValue("Aim Range", 7.0D, 3.0D, 12.0D, 0.01D);
   private final NumberValue<Double> attackRange = new NumberValue("Attack Range", 3.0D, 3.0D, 7.0D, 0.01D);
   private final ModeValue<AttackUtil.AttackPattern> attackPattern = new ModeValue("Pattern", AttackUtil.AttackPattern.values());
   private final NumberValue<Integer> cps = new NumberValue("CPS", 14, 1, 30, 1);
   private final NumberValue<Double> randomization = new NumberValue("Randomization", 1.5D, 0.0D, 4.0D, 0.01D);
   private final ModeValue<String> autoBlock = new ModeValue("Autoblock", new String[]{"None", "Fake", "Blatant", "Vanilla", "Basic", "Blink"});
   private final ModeValue<String> blinkMode = new ModeValue("Blink Autoblock Mode", new String[]{"Legit", "Legit HVH", "Blatant"});
   private final NumberValue<Double> blockRange = new NumberValue("Block Range", 3.0D, 0.0D, 7.0D, 0.01D);
   private final BooleanValue interactAutoblock = new BooleanValue("Interact", false);
   private final BooleanValue renderBlocking = new BooleanValue("Render Blocking", true);
   private final BooleanValue rotationRand = new BooleanValue("Rotation Randomization", false);
   private final NumberValue<Double> minRotationSpeed = new NumberValue("Min Rotation Speed", 65.0D, 0.0D, 180.0D, 5.0D);
   private final NumberValue<Double> maxRotationSpeed = new NumberValue("Max Rotation Speed", 85.0D, 0.0D, 180.0D, 5.0D);
   private final BooleanValue moveFix = new BooleanValue("Move Fix", false);
   private final BooleanValue keepSprint = new BooleanValue("Keep Sprint", true);
   private final BooleanValue rayCast = new BooleanValue("Ray Cast", true);
   private final ModeValue<String> sortMode = new ModeValue("Sort", new String[]{"FOV", "Distance", "Health", "Hurt Ticks"});
   private final TimeUtil timer = new TimeUtil();
   private final TimeUtil rotationCenter = new TimeUtil();
   private double rotationOffset;
   private EntityLivingBase target;
   private EntityLivingBase rayCastedEntity;
   private float[] rotations;
   private long attackDelay;
   private int queuedAttacks;
   public boolean isBlocking;
   private boolean wasBlink;
   public boolean renderBlock;

   public KillAura() {
      this.addSettings(new Value[]{this.aimRange, this.attackRange, this.attackPattern, this.cps, this.randomization, this.autoBlock, this.blinkMode, this.blockRange, this.interactAutoblock, this.renderBlocking, this.rotationRand, this.minRotationSpeed, this.maxRotationSpeed, this.moveFix, this.keepSprint, this.rayCast, this.sortMode});
   }

   public void onEnable() {
      this.wasBlink = false;
      this.rotations = new float[]{mc.getPlayer().rotationYaw, mc.getPlayer().rotationPitch};
      this.attackDelay = AttackUtil.getAttackDelay((Integer)this.cps.getValue(), (Double)this.randomization.getValue(), (AttackUtil.AttackPattern)this.attackPattern.getValue());
      this.queuedAttacks = 0;
      this.timer.reset();
      this.rotationCenter.reset();
   }

   public void onDisable() {
      if (this.isBlocking) {
         this.unblock();
      }

      if (this.wasBlink) {
         BlinkUtil.disable();
      }

   }

   @Listen
   public void onStrafe(StrafeEvent e) {
      if (this.target != null && (Boolean)this.moveFix.getValue()) {
         e.setYaw(this.rotations[0]);
      }

   }

   @Listen
   public void onJump(JumpEvent e) {
      if (this.target != null && (Boolean)this.moveFix.getValue()) {
         e.setYaw(this.rotations[0]);
      }

   }

   @Listen
   public void onMotion(MotionEvent e) {
      if (this.target == null) {
         this.rotations[0] = mc.getPlayer().rotationYaw;
         this.rotations[1] = mc.getPlayer().rotationPitch;
         e.setYaw(this.rotations[0]);
         e.setPitch(this.rotations[1]);
      } else {
         e.setYaw(this.rotations[0]);
         e.setPitch(this.rotations[1]);
      }

   }

   @Listen
   public void onRender(RenderEvent e) {
      if (e.getState() == RenderEvent.State.RENDER_2D) {
         if (this.timer.hasReached(this.attackDelay) && this.target != null) {
            ++this.queuedAttacks;
            this.timer.reset();
            this.attackDelay = AttackUtil.getAttackDelay((Integer)this.cps.getValue(), (Double)this.randomization.getValue(), (AttackUtil.AttackPattern)this.attackPattern.getValue());
         }

      }
   }

   @Listen
   public void onUpdate(UpdateEvent e) {
      this.target = AttackUtil.getTarget((Double)this.aimRange.getValue(), (String)this.sortMode.getValue());
      if (this.target == null) {
         this.attackDelay = 0L;
         if (this.isBlocking) {
            this.unblock();
         }

         if (this.wasBlink) {
            this.wasBlink = false;
            BlinkUtil.disable();
         }

         this.renderBlock = false;
      } else if (!((double)mc.getPlayer().getDistanceToEntity(this.target) > (Double)this.aimRange.getValue())) {
         this.renderBlock = this.canAutoBlock() && ((Boolean)this.renderBlocking.getValue() || this.isBlocking || ((String)this.autoBlock.getValue()).equals("Fake")) && !((String)this.autoBlock.getValue()).equals("None");
         this.rotations = this.calculateRotations(this.target);
         if (!((double)mc.getPlayer().getDistanceToEntity(this.target) < (Double)this.blockRange.getValue()) && !this.isBlocking || !this.preAttack()) {
            while(this.queuedAttacks > 0) {
               this.attack(this.target);
               --this.queuedAttacks;
            }

            if (this.canAutoBlock()) {
               this.postAttack();
            }

         }
      }
   }

   private void attack(EntityLivingBase target) {
      this.rayCastedEntity = null;
      if ((Boolean)this.rayCast.getValue()) {
         this.rayCastedEntity = RaycastUtil.rayCast((Double)this.attackRange.getValue(), this.rotations);
      }

      mc.getPlayer().swingItem();
      if (!((double)mc.getPlayer().getDistanceToEntity(this.rayCastedEntity == null ? target : this.rayCastedEntity) > (Double)this.attackRange.getValue() + 0.3D)) {
         if ((Boolean)this.keepSprint.getValue()) {
            mc.getPlayerController().syncCurrentPlayItem();
            PacketUtil.send(new C02PacketUseEntity(this.rayCastedEntity == null ? target : this.rayCastedEntity, C02PacketUseEntity.Action.ATTACK));
            if (mc.getPlayer().fallDistance > 0.0F && !mc.getPlayer().onGround) {
               mc.getPlayer().onCriticalHit(this.rayCastedEntity == null ? target : this.rayCastedEntity);
            }
         } else {
            mc.getPlayerController().attackEntity(mc.getPlayer(), this.rayCastedEntity == null ? target : this.rayCastedEntity);
         }

      }
   }

   private boolean preAttack() {
      String var1 = ((String)this.autoBlock.getValue()).toLowerCase();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -33870134:
         if (var1.equals("blatant")) {
            var2 = 0;
         }
         break;
      case 93508654:
         if (var1.equals("basic")) {
            var2 = 1;
         }
         break;
      case 93826908:
         if (var1.equals("blink")) {
            var2 = 2;
         }
      }

      switch(var2) {
      case 0:
         this.unblock();
         break;
      case 1:
         switch(mc.getPlayer().ticksExisted % 3) {
         case 0:
            this.unblock();
            return true;
         case 1:
            return false;
         case 2:
            this.block();
            return true;
         default:
            return false;
         }
      case 2:
         String var3 = ((String)this.blinkMode.getValue()).toLowerCase();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -1963524045:
            if (var3.equals("legit hvh")) {
               var4 = 1;
            }
            break;
         case -33870134:
            if (var3.equals("blatant")) {
               var4 = 2;
            }
            break;
         case 102851513:
            if (var3.equals("legit")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            switch(mc.getPlayer().ticksExisted % 3) {
            case 0:
               this.unblock();
               return true;
            case 1:
               return false;
            case 2:
               this.block();
               if (!BlinkUtil.isEnabled) {
                  BlinkUtil.enable(false, true);
               }

               BlinkUtil.setConfig(false, true);
               BlinkUtil.releasePackets();
               this.wasBlink = true;
               return true;
            default:
               return false;
            }
         case 1:
            switch(mc.getPlayer().ticksExisted % 5) {
            case 0:
               this.unblock();
               return true;
            case 4:
               this.block();
               if (!BlinkUtil.isEnabled) {
                  BlinkUtil.enable(false, true);
               }

               BlinkUtil.setConfig(false, true);
               BlinkUtil.releasePackets();
               this.wasBlink = true;
               return true;
            default:
               return false;
            }
         case 2:
            switch(mc.getPlayer().ticksExisted % 2) {
            case 0:
               this.unblock();
               return true;
            case 1:
               return false;
            }
         }
      }

      return false;
   }

   private void postAttack() {
      String var1 = ((String)this.autoBlock.getValue()).toLowerCase();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -33870134:
         if (var1.equals("blatant")) {
            var2 = 0;
         }
         break;
      case 93826908:
         if (var1.equals("blink")) {
            var2 = 2;
         }
         break;
      case 233102203:
         if (var1.equals("vanilla")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         this.block();
         break;
      case 1:
         this.block();
         break;
      case 2:
         if (((String)this.blinkMode.getValue()).equals("Blatant")) {
            this.block();
            if (!BlinkUtil.isEnabled) {
               BlinkUtil.enable(false, true);
            }

            BlinkUtil.setConfig(false, true);
            BlinkUtil.releasePackets();
            this.wasBlink = true;
         }
      }

   }

   private float[] calculateRotations(Entity entity) {
      AxisAlignedBB bb = entity.getEntityBoundingBox();
      if (this.rotationCenter.hasReached(1200) && (Boolean)this.rotationRand.getValue()) {
         this.rotationOffset = (new SecureRandom()).nextDouble();
         this.rotationCenter.reset();
      }

      double distancedYaw = (double)entity.getDistanceToEntity(mc.getPlayer()) > (Double)this.attackRange.getValue() ? (double)entity.getEyeHeight() : 2.0D * ((double)entity.getDistanceToEntity(mc.getPlayer()) / 3.5D);
      float[] newRots = RotationUtil.getRotations(bb.minX + (bb.maxX - bb.minX) / 2.0D + ((Boolean)this.rotationRand.getValue() ? this.rotationOffset / 2.0D : 0.0D), bb.minY + distancedYaw, bb.minZ + (bb.maxZ - bb.minZ) / 2.0D + ((Boolean)this.rotationRand.getValue() ? this.rotationOffset / 2.0D : 0.0D));
      float pitchSpeed = (float)((double)mc.getGameSettings().mouseSensitivity * MathUtil.getRandomInRange((Double)this.minRotationSpeed.getValue(), (Double)this.maxRotationSpeed.getValue()));
      float yawSpeed = (float)((double)mc.getGameSettings().mouseSensitivity * MathUtil.getRandomInRange((Double)this.minRotationSpeed.getValue(), (Double)this.maxRotationSpeed.getValue()));
      newRots[0] = RotationUtil.updateRots(this.rotations[0], (float)MathUtil.getRandomInRange((double)newRots[0] - 2.19782323D, (double)newRots[0] + 2.8972343D), pitchSpeed);
      newRots[1] = RotationUtil.updateRots(this.rotations[1], (float)MathUtil.getRandomInRange((double)newRots[1] - 3.13672842D, (double)newRots[1] + 3.8716793D), yawSpeed);
      newRots[1] = MathHelper.clamp_float(newRots[1], -90.0F, 90.0F);
      return RotationUtil.applyGCD(newRots, this.rotations);
   }

   private void block() {
      this.block((Boolean)this.interactAutoblock.getValue());
   }

   private void block(boolean interact) {
      if (!this.isBlocking) {
         EntityLivingBase targetEntity = this.rayCastedEntity == null ? this.target : this.rayCastedEntity;
         if (interact) {
            PacketUtil.send(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
         }

         PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.getPlayer().getCurrentEquippedItem()));
         this.isBlocking = true;
      }
   }

   private void unblock() {
      if (!mc.getGameSettings().keyBindUseItem.isKeyDown()) {
         PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      } else if (this.canAutoBlock()) {
         mc.getGameSettings().keyBindUseItem.setPressed(false);
      }

      this.isBlocking = false;
   }

   private boolean canAutoBlock() {
      return this.target != null && mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().getItem() instanceof ItemSword && (double)mc.getPlayer().getDistanceToEntity(this.target) < (Double)this.blockRange.getValue();
   }
}
