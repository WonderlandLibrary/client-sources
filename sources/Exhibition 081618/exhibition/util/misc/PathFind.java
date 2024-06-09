package exhibition.util.misc;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Vec3;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathFind {
   public EntityPlayer pos;
   public PathFinder pathFinder = new PathFinder(new WalkNodeProcessor());
   private float yaw;
   public static float fakeYaw;
   public static float fakePitch;

   public PathFind(String name) {
      Iterator var2 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

      while(var2.hasNext()) {
         Object i = var2.next();
         if (i instanceof EntityPlayer && i != null) {
            EntityPlayer player = (EntityPlayer)i;
            if (player.getName().contains(name)) {
               this.pos = player;
            }
         }
      }

      if (this.pos != null) {
         this.move();
         float[] rot = this.getRotationTo(this.pos.getPositionVector());
         fakeYaw = rot[0];
         fakePitch = rot[1];
      }

   }

   public void move() {
      double var10001 = this.pos.posX + 0.5D;
      double var10002 = this.pos.posY + 0.5D;
      if (Minecraft.getMinecraft().thePlayer.getDistance(var10001, var10002, this.pos.posZ + 0.5D) > 0.3D) {
         PathEntity pe = this.pathFinder.func_176188_a(Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer, this.pos, 40.0F);
         if (pe != null && pe.getCurrentPathLength() > 1) {
            PathPoint point = pe.getPathPointFromIndex(1);
            float[] rot = this.getRotationTo(new Vec3((double)point.xCoord + 0.5D, (double)point.yCoord + 0.5D, (double)point.zCoord + 0.5D));
            this.yaw = rot[0];
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            EntityPlayerSP player2 = Minecraft.getMinecraft().thePlayer;
            double n = 0.0D;
            player2.motionZ = 0.0D;
            player.motionX = 0.0D;
            double offset = 0.26D;
            double newx = Math.sin((double)(this.yaw * 3.1415927F / 180.0F)) * 0.26D;
            double newz = Math.cos((double)(this.yaw * 3.1415927F / 180.0F)) * 0.26D;
            EntityPlayerSP player3 = Minecraft.getMinecraft().thePlayer;
            player3.motionX -= newx;
            EntityPlayerSP player4 = Minecraft.getMinecraft().thePlayer;
            player4.motionZ += newz;
            if (Minecraft.getMinecraft().thePlayer.onGround && Minecraft.getMinecraft().thePlayer.isCollidedHorizontally) {
               Minecraft.getMinecraft().thePlayer.jump();
            }
         }
      }

   }

   public static float angleDifference(float to, float from) {
      return ((to - from) % 360.0F + 540.0F) % 360.0F - 180.0F;
   }

   public float[] getRotationTo(Vec3 pos) {
      double xD = Minecraft.getMinecraft().thePlayer.posX - pos.xCoord;
      double yD = Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight() - pos.yCoord;
      double zD = Minecraft.getMinecraft().thePlayer.posZ - pos.zCoord;
      double yaw = Math.atan2(zD, xD);
      double pitch = Math.atan2(yD, Math.sqrt(Math.pow(xD, 2.0D) + Math.pow(zD, 2.0D)));
      return new float[]{(float)Math.toDegrees(yaw) + 90.0F, (float)Math.toDegrees(pitch)};
   }
}
