package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.a1C;
import net.a1n;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$1;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$2;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$ChestCorridor;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Crossing;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$LeftTurn;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Library;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$PieceWeight;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$PortalRoom;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Prison;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$RightTurn;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$RoomCrossing;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$StairsStraight;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stones;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Straight;

public class StructureStrongholdPieces {
   private static final StructureStrongholdPieces$PieceWeight[] pieceWeightArray = new StructureStrongholdPieces$PieceWeight[]{new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$Straight.class, 40, 0), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$Prison.class, 5, 5), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$LeftTurn.class, 20, 0), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$RightTurn.class, 20, 0), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$RoomCrossing.class, 10, 6), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$StairsStraight.class, 5, 5), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$Stairs.class, 5, 5), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$Crossing.class, 5, 4), new StructureStrongholdPieces$PieceWeight(StructureStrongholdPieces$ChestCorridor.class, 5, 4), new StructureStrongholdPieces$1(StructureStrongholdPieces$Library.class, 10, 2), new StructureStrongholdPieces$2(StructureStrongholdPieces$PortalRoom.class, 20, 1)};
   private static List structurePieceList;
   private static Class strongComponentType;
   static int totalWeight;
   private static final StructureStrongholdPieces$Stones strongholdStones = new StructureStrongholdPieces$Stones((StructureStrongholdPieces$1)null);

   public static void registerStrongholdPieces() {
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$ChestCorridor.class, "SHCC");
      MapGenStructureIO.registerStructureComponent(a1n.class, "SHFC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$Crossing.class, "SH5C");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$LeftTurn.class, "SHLT");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$Library.class, "SHLi");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$PortalRoom.class, "SHPR");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$Prison.class, "SHPH");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$RightTurn.class, "SHRT");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$RoomCrossing.class, "SHRC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$Stairs.class, "SHSD");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$Stairs2.class, "SHStart");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$Straight.class, "SHS");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces$StairsStraight.class, "SHSSD");
   }

   public static void prepareStructurePieces() {
      structurePieceList = Lists.newArrayList();

      for(StructureStrongholdPieces$PieceWeight var3 : pieceWeightArray) {
         var3.instancesSpawned = 0;
         structurePieceList.add(var3);
      }

      strongComponentType = null;
   }

   private static boolean canAddStructurePieces() {
      boolean var0 = false;
      totalWeight = 0;

      for(StructureStrongholdPieces$PieceWeight var2 : structurePieceList) {
         if(var2.instancesLimit > 0 && var2.instancesSpawned < var2.instancesLimit) {
            var0 = true;
         }

         totalWeight += var2.pieceWeight;
      }

      return var0;
   }

   private static a1C a(Class var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      Object var8 = null;
      if(var0 == StructureStrongholdPieces$Straight.class) {
         var8 = StructureStrongholdPieces$Straight.func_175862_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$Prison.class) {
         var8 = StructureStrongholdPieces$Prison.func_175860_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$LeftTurn.class) {
         var8 = StructureStrongholdPieces$LeftTurn.func_175867_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$RightTurn.class) {
         var8 = StructureStrongholdPieces$RightTurn.func_175867_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$RoomCrossing.class) {
         var8 = StructureStrongholdPieces$RoomCrossing.func_175859_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$StairsStraight.class) {
         var8 = StructureStrongholdPieces$StairsStraight.func_175861_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$Stairs.class) {
         var8 = StructureStrongholdPieces$Stairs.func_175863_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$Crossing.class) {
         var8 = StructureStrongholdPieces$Crossing.func_175866_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$ChestCorridor.class) {
         var8 = StructureStrongholdPieces$ChestCorridor.func_175868_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$Library.class) {
         var8 = StructureStrongholdPieces$Library.func_175864_a(var1, var2, var3, var4, var5, var6, var7);
      } else if(var0 == StructureStrongholdPieces$PortalRoom.class) {
         var8 = StructureStrongholdPieces$PortalRoom.func_175865_a(var1, var2, var3, var4, var5, var6, var7);
      }

      return (a1C)var8;
   }

   private static a1C a(StructureStrongholdPieces$Stairs2 var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      if(!canAddStructurePieces()) {
         return null;
      } else if(strongComponentType != null) {
         a1C var13 = a(strongComponentType, var1, var2, var3, var4, var5, var6, var7);
         strongComponentType = null;
         return var13;
      } else {
         int var8 = 0;

         while(var8 < 5) {
            ++var8;
            int var9 = var2.nextInt(totalWeight);
            Iterator var10 = structurePieceList.iterator();
            if(var10.hasNext()) {
               StructureStrongholdPieces$PieceWeight var11 = (StructureStrongholdPieces$PieceWeight)var10.next();
               var9 = var9 - var11.pieceWeight;
               if(var11.canSpawnMoreStructuresOfType(var7) && var11 != var0.strongholdPieceWeight) {
                  a1C var12 = a(var11.pieceClass, var1, var2, var3, var4, var5, var6, var7);
                  ++var11.instancesSpawned;
                  var0.strongholdPieceWeight = var11;
                  if(!var11.canSpawnMoreStructures()) {
                     structurePieceList.remove(var11);
                  }

                  return var12;
               }
            }
         }

         StructureBoundingBox var15 = a1n.a(var1, var2, var3, var4, var5, var6);
         if(var15.minY > 1) {
            return new a1n(var7, var2, var15, var6);
         } else {
            return null;
         }
      }
   }

   private static StructureComponent func_175953_c(StructureStrongholdPieces$Stairs2 var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      if(var7 > 50) {
         return null;
      } else if(Math.abs(var3 - var0.getBoundingBox().minX) <= 112 && Math.abs(var5 - var0.getBoundingBox().minZ) <= 112) {
         a1C var8 = a(var0, var1, var2, var3, var4, var5, var6, var7 + 1);
         var1.add(var8);
         var0.field_75026_c.add(var8);
         return var8;
      } else {
         return null;
      }
   }

   static StructureStrongholdPieces$Stones access$100() {
      return strongholdStones;
   }

   static Class access$202(Class var0) {
      strongComponentType = var0;
      return var0;
   }

   static StructureComponent access$300(StructureStrongholdPieces$Stairs2 var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      return func_175953_c(var0, var1, var2, var3, var4, var5, var6, var7);
   }
}
