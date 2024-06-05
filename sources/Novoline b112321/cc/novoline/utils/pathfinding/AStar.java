package cc.novoline.utils.pathfinding;

import cc.novoline.utils.pathfinding.AStar$Node;
import cc.novoline.utils.pathfinding.Vec3;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.acE;
import net.minecraft.util.BlockPos;

public class AStar {
   public static AStar aStar;

   public static AStar getAStar() {
      return aStar;
   }

   public static List getPath(BlockPos var0, BlockPos var1) {
      Vec3.b();
      CopyOnWriteArrayList var3 = new CopyOnWriteArrayList();
      CopyOnWriteArrayList var4 = new CopyOnWriteArrayList();
      AStar$Node var5 = new AStar$Node(var0.getX(), var0.getZ(), 0, 0);
      AStar$Node var6 = new AStar$Node(var0.getX(), var0.getZ(), 0, getCostToATarget(new AStar$Node(var0.getX(), var0.getZ(), 0, 0), var1));
      AStar$Node var7 = new AStar$Node(var1.getX(), var1.getZ(), getCostToATarget(new AStar$Node(var1.getX(), var1.getZ(), 0, 0), var0), 0);
      int var8 = 0;
      if(var8 < 1000) {
         CopyOnWriteArrayList var9 = new CopyOnWriteArrayList();
         int var10 = -1;
         if(var10 < 2) {
            int var11 = 1;
            if(var11 > -2) {
               if(!doesNodeExistHere(var5.x + var10, var5.z + var11, var4)) {
                  var9.add(new AStar$Node(var5.x + var10, var5.z + var11, getCostToATarget(var6, new BlockPos(var5.x + var10, 0, var5.z + var11)), getCostToATarget(var7, new BlockPos(var5.x + var10, 0, var5.z + var11))));
               }

               --var11;
            }

            ++var10;
         }

         var4.addAll(var9);
         var5 = (AStar$Node)((List)var9.stream().sorted(Comparator.comparingDouble(AStar::lambda$getPath$0)).collect(Collectors.toList())).get(0);
         var3.add(var5);
         if(var5.hCost == 0) {
            ;
         }

         ++var8;
      }

      if(acE.b() == null) {
         Vec3.b("OeTCZb");
      }

      return var3;
   }

   private static boolean doesNodeExistHere(int var0, int var1, List var2) {
      Vec3.b();
      Iterator var4 = var2.iterator();
      if(var4.hasNext()) {
         AStar$Node var5 = (AStar$Node)var4.next();
         if(var5.x == var0 && var5.z == var1) {
            return true;
         }
      }

      return false;
   }

   private static int getCostToATarget(AStar$Node var0, BlockPos var1) {
      Vec3.b();
      int var3 = Math.abs(var0.getX() - var1.getX());
      int var4 = Math.abs(var0.getZ() - var1.getZ());
      int var5 = 0;
      if(var3 > 0) {
         var5 = var5 + 14;
         int var11 = var3 - 1;
         if(var4 > 1) {
            --var4;
            var5 += 10 * var4;
         }

         return var5;
      } else if(var4 > 0) {
         var5 = var5 + 14;
         int var6 = var4 - 1;
         if(var3 > 1) {
            --var3;
            var5 += 10 * var3;
         }

         return var5;
      } else {
         return 0;
      }
   }

   private int getCostToATarget(AStar$Node var1, AStar$Node var2) {
      Vec3.b();
      int var4 = Math.abs(var1.getX() - var2.getX());
      int var5 = Math.abs(var1.getZ() - var2.getZ());
      int var6 = 0;
      if(var4 > 0) {
         var6 = var6 + 14;
         int var12 = var4 - 1;
         if(var5 > 1) {
            --var5;
            var6 += 10 * var5;
         }

         return var6;
      } else if(var5 > 0) {
         var6 = var6 + 14;
         int var7 = var5 - 1;
         if(var4 > 1) {
            --var4;
            var6 += 10 * var4;
         }

         return var6;
      } else {
         return 0;
      }
   }

   private static double lambda$getPath$0(AStar$Node var0) {
      return (double)var0.getFinalCost();
   }
}
