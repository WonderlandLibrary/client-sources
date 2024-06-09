package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.render.Colors;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class BedFucker extends Module {
   private Block nukeBlock;
   private BlockPos blockBreaking;
   exhibition.util.Timer timer = new exhibition.util.Timer();

   public BedFucker(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventRender3D.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            for(int y = 6; y >= -6; --y) {
               for(int x = -6; x <= 6; ++x) {
                  for(int z = -6; z <= 6; ++z) {
                     boolean uwot = x != 0 || z != 0;
                     if (mc.thePlayer.isSneaking()) {
                        uwot = !uwot;
                     }

                     if (uwot) {
                        BlockPos pos = new BlockPos(mc.thePlayer.posX + (double)x, mc.thePlayer.posY + (double)y, mc.thePlayer.posZ + (double)z);
                        if (this.getFacingDirection(pos) != null && this.blockChecks(mc.theWorld.getBlockState(pos).getBlock()) && mc.thePlayer.getDistance(mc.thePlayer.posX + (double)x, mc.thePlayer.posY + (double)y, mc.thePlayer.posZ + (double)z) < (double)mc.playerController.getBlockReachDistance() - 0.5D) {
                           float[] rotations = this.getBlockRotations(mc.thePlayer.posX + (double)x, mc.thePlayer.posY + (double)y, mc.thePlayer.posZ + (double)z);
                           em.setYaw(rotations[0]);
                           em.setPitch(rotations[1]);
                           this.blockBreaking = pos;
                           return;
                        }
                     }
                  }
               }
            }

            this.blockBreaking = null;
         } else if (this.blockBreaking != null) {
            if (mc.playerController.blockHitDelay > 1) {
               mc.playerController.blockHitDelay = 1;
            }

            mc.thePlayer.swingItem();
            EnumFacing direction = this.getFacingDirection(this.blockBreaking);
            if (direction != null) {
               mc.playerController.breakBlock(this.blockBreaking, direction);
            }
         }
      } else if (this.blockBreaking != null) {
         this.drawESP((double)this.blockBreaking.getX(), (double)this.blockBreaking.getY(), (double)this.blockBreaking.getZ(), (double)(this.blockBreaking.getX() + 1), (double)(this.blockBreaking.getY() + 1), (double)(this.blockBreaking.getZ() + 1), 100.0D, 100.0D, 100.0D);
      }

   }

   public void drawESP(double x, double y, double z, double x2, double y2, double z2, double r, double g, double b) {
      double x3 = x - RenderManager.renderPosX;
      double y3 = y - RenderManager.renderPosY;
      double z3 = z - RenderManager.renderPosZ;
      double x4 = x2 - RenderManager.renderPosX;
      double y4 = y2 - RenderManager.renderPosY;
      this.drawFilledBBESP(new AxisAlignedBB(x3, y3, z3, x4, y4, z2 - RenderManager.renderPosZ), Colors.getColor(0, 0, 50, 0));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawFilledBox(AxisAlignedBB boundingBox) {
      if (boundingBox != null) {
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
         GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
         GL11.glEnd();
      }
   }

   public void drawFilledBBESP(AxisAlignedBB axisalignedbb, int color) {
      GL11.glPushMatrix();
      float red = (float)(color >> 24 & 255) / 255.0F;
      float green = (float)(color >> 16 & 255) / 255.0F;
      float blue = (float)(color >> 8 & 255) / 255.0F;
      float alpha = (float)(color & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawFilledBox(axisalignedbb);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2896);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   private boolean blockChecks(Block block) {
      return block == Blocks.bed;
   }

   public float[] getBlockRotations(double x, double y, double z) {
      double var4 = x - mc.thePlayer.posX + 0.5D;
      double var5 = z - mc.thePlayer.posZ + 0.5D;
      double var6 = y - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - 1.0D);
      double var7 = (double)MathHelper.sqrt_double(var4 * var4 + var5 * var5);
      float var8 = (float)(Math.atan2(var5, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      return new float[]{var8, (float)(-(Math.atan2(var6, var7) * 180.0D / 3.141592653589793D))};
   }

   private EnumFacing getFacingDirection(BlockPos pos) {
      EnumFacing direction = null;
      if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.UP;
      } else if (!mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.DOWN;
      } else if (!mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.EAST;
      } else if (!mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.WEST;
      } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.SOUTH;
      } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.NORTH;
      }

      MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D));
      return rayResult != null && rayResult.getBlockPos() == pos ? rayResult.facing : direction;
   }
}
