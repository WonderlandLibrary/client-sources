package cc.novoline.utils.pathfinding;

import net.minecraft.util.BlockPos;

public class AStar$Node {
   int x;
   int y;
   int z;
   int gCost;
   int hCost;

   public AStar$Node(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.z = var2;
      this.gCost = var3;
      this.hCost = var4;
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public int getFinalCost() {
      return this.gCost + this.hCost;
   }

   public BlockPos getBlockPos(int var1) {
      return new BlockPos(this.x, var1, this.z);
   }
}
