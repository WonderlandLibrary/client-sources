package net.augustus.modules.movement;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class BugUp extends Module {
   public final DoubleValue maxDistance = new DoubleValue(2, "MaxDistance", this, 15.0, 3.0, 30.0, 0);
   private final TimeHelper timeHelper = new TimeHelper();
   public StringValue mode = new StringValue(1, "Mode", this, "OnGround", new String[]{"Teleport", "OnGround"});
   private double[] xyz = new double[3];
   private final ArrayList<Packet> packets = new ArrayList<>();

   public BugUp() {
      super("BugUp", Color.DARK_GRAY, Categorys.MOVEMENT);
   }

   @Override
   public void onEnable() {
      this.packets.clear();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.packets.clear();
      super.onDisable();
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (mc.thePlayer.onGround) {
         this.xyz = new double[]{mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
      }

      if (this.shouldBugUp()) {
         String var2 = this.mode.getSelected();
         switch(var2) {
            case "Teleport":
               if (this.timeHelper.reached(200L)) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.xyz[0], this.xyz[1], this.xyz[2], true));
                  this.timeHelper.reset();
               }
               break;
            case "OnGround":
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
         }
      }
   }

   private boolean shouldBugUp() {
      if (!mm.longJump.isToggled() && !mm.fly.isToggled() && !(mc.thePlayer.fallDistance < 2.0F)) {
         double posX = mc.thePlayer.posX;
         double posY = mc.thePlayer.posY;
         double posZ = mc.thePlayer.posZ;
         double motionX = mc.thePlayer.motionX;
         double motionY = mc.thePlayer.motionY;
         double motionZ = mc.thePlayer.motionZ;
         boolean isJumping = mc.thePlayer.isJumping;

         for(int i = 0; i < 200; ++i) {
            double[] doubles = PlayerUtil.getPredictedPos(
               mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, motionX, motionY, motionZ, posX, posY, posZ, isJumping
            );
            isJumping = false;
            posX = doubles[0];
            posY = doubles[1];
            posZ = doubles[2];
            motionX = doubles[3];
            motionY = doubles[4];
            motionZ = doubles[5];
            BlockPos b = new BlockPos(posX, posY, posZ);
            Block block = mc.theWorld.getBlockState(b).getBlock();
            if (!(block instanceof BlockAir)) {
               return false;
            }

            if (Math.abs(mc.thePlayer.posY - posY) > this.maxDistance.getValue()) {
               break;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
