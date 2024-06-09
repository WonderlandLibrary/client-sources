package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventBlockBounds;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventPushBlock;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Phase extends Module {
   private int delay;
   private String PM = "PHASEMODE";
   private Setting distance = new Setting("DIST", 0.5D, "Distance for HCF phase.", 0.1D, 0.1D, 2.0D);

   public Phase(ModuleData data) {
      super(data);
      this.settings.put(this.PM, new Setting(this.PM, new Options("Phase Mode", "Normal", new String[]{"Spider", "Skip", "Normal", "FullBlock", "Silent", "HCF"}), "Phase exploit method."));
      this.settings.put("DIST", this.distance);
   }

   @RegisterEvent(
      events = {EventBlockBounds.class, EventMotionUpdate.class, EventPushBlock.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      String currentPhase = ((Options)((Setting)this.settings.get(this.PM)).getValue()).getSelected();
      this.setSuffix(currentPhase);
      double multiplier;
      double mx;
      double mz;
      double x;
      double z;
      if (event instanceof EventPacket && !currentPhase.equalsIgnoreCase("HCF")) {
         EventPacket ep = (EventPacket)event;
         if (ep.isOutgoing()) {
            if (this.isInsideBlock()) {
               return;
            }

            multiplier = 0.2D;
            mx = Math.cos(Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
            mz = Math.sin(Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
            x = (double)mc.thePlayer.movementInput.moveForward * 0.2D * mx + (double)mc.thePlayer.movementInput.moveStrafe * 0.2D * mz;
            z = (double)mc.thePlayer.movementInput.moveForward * 0.2D * mz - (double)mc.thePlayer.movementInput.moveStrafe * 0.2D * mx;
            if (mc.thePlayer.isCollidedHorizontally && ep.getPacket() instanceof C03PacketPlayer) {
               ++this.delay;
               if (this.delay >= 5) {
                  C03PacketPlayer player = (C03PacketPlayer)ep.getPacket();
                  player.x += x;
                  player.z += z;
                  --player.y;
                  this.delay = 0;
               }
            }
         }
      }

      if (event instanceof EventBlockBounds) {
         EventBlockBounds ebb = (EventBlockBounds)event;
         if (mc.thePlayer == null) {
            return;
         }

         if (ebb.getBounds() != null && mc.thePlayer.boundingBox != null && ebb.getBounds().maxY > mc.thePlayer.boundingBox.minY && mc.thePlayer.isSneaking()) {
            ebb.setBounds((AxisAlignedBB)null);
            return;
         }

         if (currentPhase.equalsIgnoreCase("HCF")) {
            return;
         }

         mc.thePlayer.noClip = true;
         if ((double)ebb.getPos().getY() > mc.thePlayer.posY + (double)(this.isInsideBlock() ? 0 : 1)) {
            ebb.setBounds((AxisAlignedBB)null);
         }

         if (mc.thePlayer.isCollidedHorizontally && (double)ebb.getPos().getY() > mc.thePlayer.boundingBox.minY - 0.4D) {
            ebb.setBounds((AxisAlignedBB)null);
         }
      }

      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre() && currentPhase.equalsIgnoreCase("HCF") && this.isInsideBlock() && mc.thePlayer.isSneaking() && mc.thePlayer.boundingBox != null) {
            float yaw = mc.thePlayer.rotationYaw;
            double dist = ((Number)this.distance.getValue()).doubleValue();
            mc.thePlayer.boundingBox = mc.thePlayer.boundingBox.offset(dist * Math.cos(Math.toRadians((double)(yaw + 90.0F))), 0.0D, dist * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
         }

         if (em.isPost()) {
            multiplier = 0.3D;
            mx = Math.cos(Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
            mz = Math.sin(Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
            if (currentPhase.equals("FullBlock")) {
               multiplier = 0.31D;
            }

            x = (double)mc.thePlayer.movementInput.moveForward * multiplier * mx + (double)mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            z = (double)mc.thePlayer.movementInput.moveForward * multiplier * mz - (double)mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            byte var15 = -1;
            switch(currentPhase.hashCode()) {
            case -1955878649:
               if (currentPhase.equals("Normal")) {
                  var15 = 1;
               }
               break;
            case -1818460043:
               if (currentPhase.equals("Silent")) {
                  var15 = 2;
               }
               break;
            case -1812086011:
               if (currentPhase.equals("Spider")) {
                  var15 = 4;
               }
               break;
            case 2578847:
               if (currentPhase.equals("Skip")) {
                  var15 = 3;
               }
               break;
            case 299963166:
               if (currentPhase.equals("FullBlock")) {
                  var15 = 0;
               }
            }

            double posY;
            int i;
            double posX;
            switch(var15) {
            case 0:
               if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !this.isInsideBlock()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));

                  for(i = 1; i < 11; ++i) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Double.MAX_VALUE * (double)i, mc.thePlayer.posZ, false));
                  }

                  posX = mc.thePlayer.posX;
                  posY = mc.thePlayer.posY;
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY - (PlayerUtil.isOnLiquid() ? 9000.0D : 0.1D), mc.thePlayer.posZ, false));
                  mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
               }
               break;
            case 1:
               if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !this.isInsideBlock()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));
                  posX = mc.thePlayer.posX;
                  posY = mc.thePlayer.posY;
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY - (PlayerUtil.isOnLiquid() ? 9000.0D : 0.09D), mc.thePlayer.posZ, false));
                  mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
               }
               break;
            case 2:
               if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !this.isInsideBlock()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));

                  for(i = 1; i < 10; ++i) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, 8.988465674311579E307D, mc.thePlayer.posZ, false));
                  }

                  mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
               }
               break;
            case 3:
               if (mc.thePlayer.isCollidedHorizontally) {
                  mc.thePlayer.motionX *= 0.5D;
                  mc.thePlayer.motionZ *= 0.5D;
                  double[] OPOP = new double[]{-0.02500000037252903D, -0.028571428997176036D, -0.033333333830038704D, -0.04000000059604645D, -0.05000000074505806D, -0.06666666766007741D, -0.10000000149011612D, 0.0D, -0.20000000298023224D, -0.04000000059604645D, -0.033333333830038704D, -0.028571428997176036D, -0.02500000037252903D};

                  for(int j = 0; j < OPOP.length; ++j) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + OPOP[j], mc.thePlayer.posZ, false));
                     mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x * (double)j, mc.thePlayer.boundingBox.minY, mc.thePlayer.posZ + z * (double)j, false));
                  }

                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                  mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posZ, false));
               }
               break;
            case 4:
               if (this.isInsideBlock()) {
                  mc.thePlayer.posY += 0.1D;
                  mc.thePlayer.motionY = 0.065D;
                  mc.thePlayer.resetHeight();
               }
            }
         }
      }

   }

   private boolean isInsideBlock() {
      for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
         for(int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1; ++y) {
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
               Block block = mc.thePlayer.getEntityWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
               if (block != null && !(block instanceof BlockAir)) {
                  AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z), mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                  if (block instanceof BlockHopper) {
                     boundingBox = new AxisAlignedBB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
                  }

                  if (boundingBox != null && mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }
}
