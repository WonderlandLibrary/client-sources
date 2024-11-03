package net.augustus.modules.misc;

import java.awt.Color;
import net.augustus.Augustus;
import net.augustus.events.EventClick;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventPreMotion;
import net.augustus.events.EventSilentMove;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.*;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class SpinBot extends Module {
   public final StringValue mode = new StringValue(613473, "Mode", this, "Spin", new String[]{"Spin", "Random"});
   public final BooleanValue moveFix = new BooleanValue(1, "MoveFix", this, false);
   public final DoubleValue spinSpeed = new DoubleValue(51644, "Speed", this, 50, 1, 360, 0);
   private final TimeHelper timeHelper = new TimeHelper();
   private final TimeHelper timeHelper2 = new TimeHelper();
   private final BooleanValue click = new BooleanValue(3, "Click", this, true);
   private final BooleanValue chest = new BooleanValue(4, "Chest", this, true);
   private final BooleanValue craftingTable = new BooleanValue(5, "Crafter", this, false);
   private final BooleanValue furnace = new BooleanValue(6, "Furnace", this, false);
   private final BooleansSetting stopOn = new BooleansSetting(2, "StopOn", this, new Setting[]{this.click, this.chest, this.craftingTable, this.furnace});
   private float[] rots = new float[2];
   private float[] lastRots = new float[2];
   private boolean spin;

   public SpinBot() {
      super("AntiAim", new Color(50, 168, 82), Categorys.MISC);
   }

   @Override
   public void onEnable() {
      if (mc.thePlayer != null) {
         this.rots = new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
         this.lastRots = new float[]{mc.thePlayer.prevRotationYaw, mc.thePlayer.prevRotationPitch};
      }
   }

   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      if (this.spin) {
         this.uhh();
      }
   }

   private boolean shouldSpin() {
      MovingObjectPosition objectPosition = mc.thePlayer
         .customRayTrace(4.5, 1.0F, Augustus.getInstance().getYawPitchHelper().realYaw, Augustus.getInstance().getYawPitchHelper().realPitch);
      if (!this.click.getBoolean() || !Mouse.isButtonDown(1) && !Mouse.isButtonDown(0)) {
         if (objectPosition != null && objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (this.chest.getBoolean() && mc.theWorld.getBlockState(objectPosition.getBlockPos()).getBlock() instanceof BlockChest) {
               return false;
            }

            if (this.craftingTable.getBoolean() && mc.theWorld.getBlockState(objectPosition.getBlockPos()).getBlock().equals(Block.getBlockById(58))) {
               return false;
            }

            if (this.furnace.getBoolean() && mc.theWorld.getBlockState(objectPosition.getBlockPos()).getBlock().equals(Block.getBlockById(61))) {
               return false;
            }
         }

         return (!mm.killAura.isToggled() || mm.killAura.target == null) && !BlockUtil.isScaffoldToggled() && !mm.teleport.isToggled();
      } else {
         return false;
      }
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      this.spin = false;
      if (this.shouldSpin()) {
         this.spin = true;
      } else {
         this.timeHelper2.reset();
         if (this.spin) {
            mc.thePlayer.rotationYaw = Augustus.getInstance().getYawPitchHelper().realYaw;
            mc.thePlayer.rotationPitch = Augustus.getInstance().getYawPitchHelper().realPitch;
            mc.thePlayer.prevRotationYaw = Augustus.getInstance().getYawPitchHelper().realLastYaw;
            mc.thePlayer.prevRotationPitch = Augustus.getInstance().getYawPitchHelper().realLastPitch;
         }

         this.spin = false;
      }
   }

   @EventTarget
   public void onEventPreMotion(EventPreMotion eventPreMotion) {
      if (!this.moveFix.getBoolean()) {
         if (this.spin) {
            this.uhh();
            if (this.timeHelper2.reached(1000L)) {
               eventPreMotion.setYaw(this.rots[0]);
               eventPreMotion.setPitch(this.rots[1]);
               mc.thePlayer.rotationYawHead = this.rots[0];
               mc.thePlayer.rotationPitchHead = this.rots[1];
               mc.thePlayer.renderYawOffset = this.rots[0];
            }
         }
      }
   }

   @EventTarget
   public void onEventMove(EventMove eventMove) {
      if (this.spin) {
         if (!this.moveFix.getBoolean()) {
            eventMove.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
         }
      }
   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      if (this.spin) {
         if (this.moveFix.getBoolean()) {
            eventSilentMove.setSilent(true);
         }
      }
   }

   @EventTarget
   public void onEventJump(EventJump eventJump) {
      if (this.spin) {
         if (!this.moveFix.getBoolean()) {
            eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
         }
      }
   }

   private void uhh() {
      if (this.timeHelper2.reached(1000L)) {
         if (this.timeHelper.reached(50L)) {
            if(mode.getSelected().equalsIgnoreCase("Spin")) {
               this.rots[0] += spinSpeed.getValue();
               this.rots[1] = 90.0F;
            } else if(mode.getSelected().equalsIgnoreCase("Random")) {
               this.rots[0] = RandomUtil.nextFloat(-180, 180);
               this.rots[1] = RandomUtil.nextFloat(-90, 90);
            }
            this.timeHelper.reset();
         }

         new RotationUtil();
         this.rots = RotationUtil.mouseSens(this.rots[0], this.rots[1], this.lastRots[0], this.lastRots[1]);
         if (this.moveFix.getBoolean()) {
            mc.thePlayer.rotationYaw = this.rots[0];
            mc.thePlayer.rotationPitch = this.rots[1];
            mc.thePlayer.prevRotationYaw = this.lastRots[0];
            mc.thePlayer.prevRotationPitch = this.lastRots[1];
         }
      }
   }
}
