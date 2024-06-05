package cc.novoline.utils.pathfinding;

import cc.novoline.utils.pathfinding.AStarCustomPathfinder$CompareHub;
import cc.novoline.utils.pathfinding.AStarCustomPathfinder$Hub;
import cc.novoline.utils.pathfinding.Vec3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class AStarCustomPathfinder {
   private Vec3 startVec3;
   private Vec3 endVec3;
   private ArrayList path = new ArrayList();
   private ArrayList hubs = new ArrayList();
   private ArrayList hubsToWork = new ArrayList();
   private double minDistanceSquared = 9.0D;
   private boolean nearest = true;
   private static Vec3[] flatCardinalDirections = new Vec3[]{new Vec3(1.0D, 0.0D, 0.0D), new Vec3(-1.0D, 0.0D, 0.0D), new Vec3(0.0D, 0.0D, 1.0D), new Vec3(0.0D, 0.0D, -1.0D)};

   public AStarCustomPathfinder(Vec3 var1, Vec3 var2) {
      this.startVec3 = var1.addVector(0.0D, 0.0D, 0.0D).floor();
      this.endVec3 = var2.addVector(0.0D, 0.0D, 0.0D).floor();
   }

   public ArrayList getPath() {
      return this.path;
   }

   public void compute() {
      this.compute(1000, 4);
   }

   public void compute(int var1, int var2) {
      this.path.clear();
      Vec3.b();
      this.hubsToWork.clear();
      ArrayList var4 = new ArrayList();
      var4.add(this.startVec3);
      this.hubsToWork.add(new AStarCustomPathfinder$Hub(this, this.startVec3, (AStarCustomPathfinder$Hub)null, var4, this.startVec3.squareDistanceTo(this.endVec3), 0.0D, 0.0D));
      int var5 = 0;
      if(var5 < var1) {
         Collections.sort(this.hubsToWork, new AStarCustomPathfinder$CompareHub(this));
         int var6 = 0;
         if(this.hubsToWork.size() == 0) {
            ;
         }

         Iterator var7 = (new ArrayList(this.hubsToWork)).iterator();
         if(var7.hasNext()) {
            AStarCustomPathfinder$Hub var8 = (AStarCustomPathfinder$Hub)var7.next();
            ++var6;
            if(var6 > var2) {
               ;
            }

            this.hubsToWork.remove(var8);
            this.hubs.add(var8);
            Vec3[] var9 = flatCardinalDirections;
            int var10 = var9.length;
            int var11 = 0;
            if(var11 < var10) {
               Vec3 var12 = var9[var11];
               Vec3 var13 = var8.getLoc().add(var12).floor();
               if(checkPositionValidity(var13, false) && this.addHub(var8, var13, 0.0D)) {
                  ;
               }

               ++var11;
            }

            Vec3 var16 = var8.getLoc().addVector(0.0D, 1.0D, 0.0D).floor();
            if(checkPositionValidity(var16, false) && this.addHub(var8, var16, 0.0D)) {
               ;
            }

            Vec3 var17 = var8.getLoc().addVector(0.0D, -1.0D, 0.0D).floor();
            if(checkPositionValidity(var17, false) && this.addHub(var8, var17, 0.0D)) {
               ;
            }
         }

         ++var5;
      }

      if(this.nearest) {
         Collections.sort(this.hubs, new AStarCustomPathfinder$CompareHub(this));
         this.path = ((AStarCustomPathfinder$Hub)this.hubs.get(0)).getPath();
      }

   }

   public static boolean checkPositionValidity(Vec3 var0, boolean var1) {
      return checkPositionValidity((int)var0.getX(), (int)var0.getY(), (int)var0.getZ(), var1);
   }

   public static boolean checkPositionValidity(int var0, int var1, int var2, boolean var3) {
      Vec3.b();
      BlockPos var5 = new BlockPos(var0, var1, var2);
      BlockPos var6 = new BlockPos(var0, var1 + 1, var2);
      BlockPos var7 = new BlockPos(var0, var1 - 1, var2);
      return !isBlockSolid(var5) && !isBlockSolid(var6) && (isBlockSolid(var7) || !var3) && isSafeToWalkOn(var7);
   }

   private static boolean isBlockSolid(BlockPos var0) {
      String var1 = Vec3.b();
      return Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()).isSolidFullCube() || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockSlab || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockStairs || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockCactus || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockChest || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockEnderChest || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockSkull || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockPane || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockFence || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockWall || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockGlass || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockPistonBase || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockPistonExtension || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockPistonMoving || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockStainedGlass || Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockTrapDoor;
   }

   private static boolean isSafeToWalkOn(BlockPos var0) {
      String var1 = Vec3.b();
      return !(Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockFence) && !(Minecraft.getInstance().world.getBlock(var0.getX(), var0.getY(), var0.getZ()) instanceof BlockWall);
   }

   public AStarCustomPathfinder$Hub isHubExisting(Vec3 var1) {
      Vec3.b();
      Iterator var3 = this.hubs.iterator();
      if(var3.hasNext()) {
         AStarCustomPathfinder$Hub var4 = (AStarCustomPathfinder$Hub)var3.next();
         if(var4.getLoc().getX() == var1.getX() && var4.getLoc().getY() == var1.getY() && var4.getLoc().getZ() == var1.getZ()) {
            return var4;
         }
      }

      var3 = this.hubsToWork.iterator();
      if(var3.hasNext()) {
         AStarCustomPathfinder$Hub var6 = (AStarCustomPathfinder$Hub)var3.next();
         if(var6.getLoc().getX() == var1.getX() && var6.getLoc().getY() == var1.getY() && var6.getLoc().getZ() == var1.getZ()) {
            return var6;
         }
      }

      return null;
   }

   public boolean addHub(AStarCustomPathfinder$Hub var1, Vec3 var2, double var3) {
      Vec3.b();
      AStarCustomPathfinder$Hub var6 = this.isHubExisting(var2);
      double var7 = var3;
      if(var1 != null) {
         var7 = var3 + var1.getTotalCost();
      }

      if(var6 == null) {
         if(var2.getX() == this.endVec3.getX() && var2.getY() == this.endVec3.getY() && var2.getZ() == this.endVec3.getZ() || this.minDistanceSquared != 0.0D && var2.squareDistanceTo(this.endVec3) <= this.minDistanceSquared) {
            this.path.clear();
            this.path = var1.getPath();
            this.path.add(var2);
            return true;
         }

         ArrayList var9 = new ArrayList(var1.getPath());
         var9.add(var2);
         this.hubsToWork.add(new AStarCustomPathfinder$Hub(this, var2, var1, var9, var2.squareDistanceTo(this.endVec3), var3, var7));
      }

      if(var6.getCost() > var3) {
         ArrayList var10 = new ArrayList(var1.getPath());
         var10.add(var2);
         var6.setLoc(var2);
         var6.setParent(var1);
         var6.setPath(var10);
         var6.setSquareDistanceToFromTarget(var2.squareDistanceTo(this.endVec3));
         var6.setCost(var3);
         var6.setTotalCost(var7);
      }

      return false;
   }
}
