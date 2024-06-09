package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.PlayerUtil;
import exhibition.util.RenderingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Teleport extends Module {
   private boolean canTP;
   private int delay;
   private BlockPos endPos;

   public Teleport(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventRender3D.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            if (this.canTP && Mouse.isButtonDown(1) && !mc.thePlayer.isSneaking() && this.delay == 0 && mc.inGameHasFocus && !(mc.objectMouseOver.entityHit instanceof Entity)) {
               event.setCancelled(true);
               this.endPos = mc.objectMouseOver.getBlockPos();
               double[] startPos = new double[]{mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
               PlayerUtil.blinkToPos(startPos, this.endPos, 0.0D, new double[]{0.3D, 0.2D});
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition((double)this.endPos.getX() + 0.5D, (double)this.endPos.getY() - 1.0D, (double)this.endPos.getZ() + 0.5D, false));
               mc.thePlayer.setPosition((double)this.endPos.getX() + 0.5D, (double)(this.endPos.getY() + 1), (double)this.endPos.getZ() + 0.5D);
               this.delay = 5;
               event.setCancelled(false);
            }

            if (this.delay > 0) {
               --this.delay;
            }
         }
      }

      if (event instanceof EventRender3D) {
         EventRender3D er = (EventRender3D)event;

         try {
            int x = mc.objectMouseOver.getBlockPos().getX();
            int y = mc.objectMouseOver.getBlockPos().getY();
            int z = mc.objectMouseOver.getBlockPos().getZ();
            Block block1 = getBlock(x, y, z);
            Block block2 = getBlock(x, y + 1, z);
            Block block3 = getBlock(x, y + 2, z);
            boolean blockBelow = !(block1 instanceof BlockSign) && block1.getMaterial().isSolid();
            boolean blockLevel = !(block2 instanceof BlockSign) && block1.getMaterial().isSolid();
            boolean blockAbove = !(block3 instanceof BlockSign) && block1.getMaterial().isSolid();
            if (getBlock(mc.objectMouseOver.getBlockPos()).getMaterial() != Material.air && blockBelow && blockLevel && blockAbove) {
               this.canTP = true;
               GL11.glPushMatrix();
               RenderingUtil.pre3D();
               mc.entityRenderer.setupCameraTransform(er.renderPartialTicks, 2);
               GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.25D);
               RenderingUtil.drawBoundingBox(new AxisAlignedBB((double)x - RenderManager.renderPosX, (double)y - RenderManager.renderPosY, (double)z - RenderManager.renderPosZ, (double)x - RenderManager.renderPosX + 1.0D, (double)y + 1.1D - RenderManager.renderPosY, (double)z - RenderManager.renderPosZ + 1.0D));
               GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
               RenderingUtil.post3D();
               GL11.glPopMatrix();
            } else {
               this.canTP = false;
            }
         } catch (Exception var12) {
            ;
         }
      }

   }

   public static Block getBlock(int x, int y, int z) {
      return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
   }

   public static Block getBlock(BlockPos pos) {
      return mc.theWorld.getBlockState(pos).getBlock();
   }
}
