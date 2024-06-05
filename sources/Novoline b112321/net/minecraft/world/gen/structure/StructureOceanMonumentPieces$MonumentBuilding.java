package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$1;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$EntryRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$FitSimpleRoomHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$FitSimpleRoomTopHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentCoreRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Penthouse;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$WingRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$XDoubleRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$XYDoubleRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$YDoubleRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$YZDoubleRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$ZDoubleRoomFitHelper;

public class StructureOceanMonumentPieces$MonumentBuilding extends StructureOceanMonumentPieces$Piece {
   private StructureOceanMonumentPieces$RoomDefinition field_175845_o;
   private StructureOceanMonumentPieces$RoomDefinition field_175844_p;
   private List field_175843_q = Lists.newArrayList();

   public StructureOceanMonumentPieces$MonumentBuilding() {
   }

   public StructureOceanMonumentPieces$MonumentBuilding(Random var1, int var2, int var3, EnumFacing var4) {
      super(0);
      this.coordBaseMode = var4;
      switch(StructureOceanMonumentPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
      case 1:
      case 2:
         this.boundingBox = new StructureBoundingBox(var2, 39, var3, var2 + 58 - 1, 61, var3 + 58 - 1);
         break;
      default:
         this.boundingBox = new StructureBoundingBox(var2, 39, var3, var2 + 58 - 1, 61, var3 + 58 - 1);
      }

      List var5 = this.func_175836_a(var1);
      this.field_175845_o.field_175963_d = true;
      this.field_175843_q.add(new StructureOceanMonumentPieces$EntryRoom(this.coordBaseMode, this.field_175845_o));
      this.field_175843_q.add(new StructureOceanMonumentPieces$MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, var1));
      ArrayList var6 = Lists.newArrayList();
      var6.add(new StructureOceanMonumentPieces$XYDoubleRoomFitHelper());
      var6.add(new StructureOceanMonumentPieces$YZDoubleRoomFitHelper());
      var6.add(new StructureOceanMonumentPieces$ZDoubleRoomFitHelper());
      var6.add(new StructureOceanMonumentPieces$XDoubleRoomFitHelper());
      var6.add(new StructureOceanMonumentPieces$YDoubleRoomFitHelper());
      var6.add(new StructureOceanMonumentPieces$FitSimpleRoomTopHelper());
      var6.add(new StructureOceanMonumentPieces$FitSimpleRoomHelper());

      label319:
      for(StructureOceanMonumentPieces$RoomDefinition var8 : var5) {
         if(!var8.field_175963_d && !var8.func_175961_b()) {
            Iterator var9 = var6.iterator();

            StructureOceanMonumentPieces$MonumentRoomFitHelper var10;
            while(true) {
               if(!var9.hasNext()) {
                  continue label319;
               }

               var10 = (StructureOceanMonumentPieces$MonumentRoomFitHelper)var9.next();
               if(var10.func_175969_a(var8)) {
                  break;
               }
            }

            this.field_175843_q.add(var10.func_175968_a(this.coordBaseMode, var8, var1));
         }
      }

      int var14 = this.boundingBox.minY;
      int var15 = this.getXWithOffset(9, 22);
      int var16 = this.getZWithOffset(9, 22);

      for(StructureOceanMonumentPieces$Piece var11 : this.field_175843_q) {
         var11.getBoundingBox().offset(var15, var14, var16);
      }

      StructureBoundingBox var18 = StructureBoundingBox.func_175899_a(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
      StructureBoundingBox var19 = StructureBoundingBox.func_175899_a(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
      StructureBoundingBox var12 = StructureBoundingBox.func_175899_a(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
      int var13 = var1.nextInt();
      this.field_175843_q.add(new StructureOceanMonumentPieces$WingRoom(this.coordBaseMode, var18, var13++));
      this.field_175843_q.add(new StructureOceanMonumentPieces$WingRoom(this.coordBaseMode, var19, var13++));
      this.field_175843_q.add(new StructureOceanMonumentPieces$Penthouse(this.coordBaseMode, var12));
   }

   private List func_175836_a(Random var1) {
      StructureOceanMonumentPieces$RoomDefinition[] var2 = new StructureOceanMonumentPieces$RoomDefinition[75];

      for(int var3 = 0; var3 < 5; ++var3) {
         for(int var4 = 0; var4 < 4; ++var4) {
            byte var5 = 0;
            int var6 = func_175820_a(var3, var5, var4);
            var2[var6] = new StructureOceanMonumentPieces$RoomDefinition(var6);
         }
      }

      for(int var15 = 0; var15 < 5; ++var15) {
         for(int var19 = 0; var19 < 4; ++var19) {
            byte var23 = 1;
            int var27 = func_175820_a(var15, var23, var19);
            var2[var27] = new StructureOceanMonumentPieces$RoomDefinition(var27);
         }
      }

      for(int var16 = 1; var16 < 4; ++var16) {
         for(int var20 = 0; var20 < 2; ++var20) {
            byte var24 = 2;
            int var28 = func_175820_a(var16, var24, var20);
            var2[var28] = new StructureOceanMonumentPieces$RoomDefinition(var28);
         }
      }

      this.field_175845_o = var2[field_175823_g];

      for(int var17 = 0; var17 < 5; ++var17) {
         for(int var21 = 0; var21 < 5; ++var21) {
            for(int var25 = 0; var25 < 3; ++var25) {
               int var29 = func_175820_a(var17, var25, var21);
               if(var2[var29] != null) {
                  for(EnumFacing var10 : EnumFacing.values()) {
                     int var11 = var17 + var10.getFrontOffsetX();
                     int var12 = var25 + var10.getFrontOffsetY();
                     int var13 = var21 + var10.getFrontOffsetZ();
                     if(var11 < 5 && var13 < 5 && var12 < 3) {
                        int var14 = func_175820_a(var11, var12, var13);
                        if(var2[var14] != null) {
                           if(var13 != var21) {
                              var2[var29].func_175957_a(var10.getOpposite(), var2[var14]);
                           } else {
                              var2[var29].func_175957_a(var10, var2[var14]);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      StructureOceanMonumentPieces$RoomDefinition var18;
      var2[field_175831_h].func_175957_a(EnumFacing.UP, var18 = new StructureOceanMonumentPieces$RoomDefinition(1003));
      StructureOceanMonumentPieces$RoomDefinition var22;
      var2[field_175832_i].func_175957_a(EnumFacing.SOUTH, var22 = new StructureOceanMonumentPieces$RoomDefinition(1001));
      StructureOceanMonumentPieces$RoomDefinition var26;
      var2[field_175829_j].func_175957_a(EnumFacing.SOUTH, var26 = new StructureOceanMonumentPieces$RoomDefinition(1002));
      var18.field_175963_d = true;
      var22.field_175963_d = true;
      var26.field_175963_d = true;
      this.field_175845_o.field_175964_e = true;
      this.field_175844_p = var2[func_175820_a(var1.nextInt(4), 0, 2)];
      this.field_175844_p.field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
      this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
      ArrayList var30 = Lists.newArrayList();

      for(StructureOceanMonumentPieces$RoomDefinition var37 : var2) {
         var37.func_175958_a();
         var30.add(var37);
      }

      var18.func_175958_a();
      Collections.shuffle(var30, var1);
      int var32 = 1;

      for(StructureOceanMonumentPieces$RoomDefinition var36 : var30) {
         int var38 = 0;
         int var39 = 0;

         while(var38 < 2 && var39 < 5) {
            ++var39;
            int var40 = var1.nextInt(6);
            if(var36.field_175966_c[var40]) {
               int var41 = EnumFacing.getFront(var40).getOpposite().getIndex();
               var36.field_175966_c[var40] = false;
               var36.field_175965_b[var40].field_175966_c[var41] = false;
               if(var36.func_175959_a(var32++) && var36.field_175965_b[var40].func_175959_a(var32++)) {
                  ++var38;
               } else {
                  var36.field_175966_c[var40] = true;
                  var36.field_175965_b[var40].field_175966_c[var41] = true;
               }
            }
         }
      }

      var30.add(var18);
      var30.add(var22);
      var30.add(var26);
      return var30;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      int var4 = Math.max(var1.func_181545_F(), 64) - this.boundingBox.minY;
      this.func_181655_a(var1, var3, 0, 0, 0, 58, var4, 58, false);
      this.func_175840_a(false, 0, var1, var2, var3);
      this.func_175840_a(true, 33, var1, var2, var3);
      this.func_175839_b(var1, var2, var3);
      this.func_175837_c(var1, var2, var3);
      this.func_175841_d(var1, var2, var3);
      this.func_175835_e(var1, var2, var3);
      this.func_175842_f(var1, var2, var3);
      this.func_175838_g(var1, var2, var3);

      for(int var5 = 0; var5 < 7; ++var5) {
         int var6 = 0;

         while(var6 < 7) {
            if(var5 == 3) {
               var6 = 6;
            }

            int var7 = var5 * 9;
            int var8 = var6 * 9;

            for(int var9 = 0; var9 < 4; ++var9) {
               for(int var10 = 0; var10 < 4; ++var10) {
                  this.setBlockState(var1, field_175826_b, var7 + var9, 0, var8 + var10, var3);
                  this.replaceAirAndLiquidDownwards(var1, field_175826_b, var7 + var9, -1, var8 + var10, var3);
               }
            }

            if(var5 != 6) {
               var6 += 6;
            } else {
               ++var6;
            }
         }
      }

      for(int var11 = 0; var11 < 5; ++var11) {
         this.func_181655_a(var1, var3, -1 - var11, 0 + var11 * 2, -1 - var11, -1 - var11, 23, 58 + var11, false);
         this.func_181655_a(var1, var3, 58 + var11, 0 + var11 * 2, -1 - var11, 58 + var11, 23, 58 + var11, false);
         this.func_181655_a(var1, var3, 0 - var11, 0 + var11 * 2, -1 - var11, 57 + var11, 23, -1 - var11, false);
         this.func_181655_a(var1, var3, 0 - var11, 0 + var11 * 2, 58 + var11, 57 + var11, 23, 58 + var11, false);
      }

      for(StructureOceanMonumentPieces$Piece var13 : this.field_175843_q) {
         if(var13.getBoundingBox().intersectsWith(var3)) {
            var13.addComponentParts(var1, var2, var3);
         }
      }

      return true;
   }

   private void func_175840_a(boolean var1, int var2, World var3, Random var4, StructureBoundingBox var5) {
      boolean var6 = true;
      if(this.func_175818_a(var5, var2, 0, var2 + 23, 20)) {
         this.fillWithBlocks(var3, var5, var2 + 0, 0, 0, var2 + 24, 0, 20, field_175828_a, field_175828_a, false);
         this.func_181655_a(var3, var5, var2 + 0, 1, 0, var2 + 24, 10, 20, false);

         for(int var7 = 0; var7 < 4; ++var7) {
            this.fillWithBlocks(var3, var5, var2 + var7, var7 + 1, var7, var2 + var7, var7 + 1, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var3, var5, var2 + var7 + 7, var7 + 5, var7 + 7, var2 + var7 + 7, var7 + 5, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var3, var5, var2 + 17 - var7, var7 + 5, var7 + 7, var2 + 17 - var7, var7 + 5, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var3, var5, var2 + 24 - var7, var7 + 1, var7, var2 + 24 - var7, var7 + 1, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var3, var5, var2 + var7 + 1, var7 + 1, var7, var2 + 23 - var7, var7 + 1, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var3, var5, var2 + var7 + 8, var7 + 5, var7 + 7, var2 + 16 - var7, var7 + 5, var7 + 7, field_175826_b, field_175826_b, false);
         }

         this.fillWithBlocks(var3, var5, var2 + 4, 4, 4, var2 + 6, 4, 20, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var3, var5, var2 + 7, 4, 4, var2 + 17, 4, 6, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var3, var5, var2 + 18, 4, 4, var2 + 20, 4, 20, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var3, var5, var2 + 11, 8, 11, var2 + 13, 8, 20, field_175828_a, field_175828_a, false);
         this.setBlockState(var3, field_175824_d, var2 + 12, 9, 12, var5);
         this.setBlockState(var3, field_175824_d, var2 + 12, 9, 15, var5);
         this.setBlockState(var3, field_175824_d, var2 + 12, 9, 18, var5);
         int var11 = var2 + 19;
         int var8 = var2 + 5;

         for(int var9 = 20; var9 >= 5; var9 -= 3) {
            this.setBlockState(var3, field_175824_d, var11, 5, var9, var5);
         }

         for(int var12 = 19; var12 >= 7; var12 -= 3) {
            this.setBlockState(var3, field_175824_d, var8, 5, var12, var5);
         }

         for(int var13 = 0; var13 < 4; ++var13) {
            int var10 = var2 + 24 - (17 - var13 * 3);
            this.setBlockState(var3, field_175824_d, var10, 5, 5, var5);
         }

         this.setBlockState(var3, field_175824_d, var8, 5, 5, var5);
         this.fillWithBlocks(var3, var5, var2 + 11, 1, 12, var2 + 13, 7, 12, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var3, var5, var2 + 12, 1, 11, var2 + 12, 7, 13, field_175828_a, field_175828_a, false);
      }

   }

   private void func_175839_b(World var1, Random var2, StructureBoundingBox var3) {
      if(this.func_175818_a(var3, 22, 5, 35, 17)) {
         this.func_181655_a(var1, var3, 25, 0, 0, 32, 8, 20, false);

         for(int var4 = 0; var4 < 4; ++var4) {
            this.fillWithBlocks(var1, var3, 24, 2, 5 + var4 * 4, 24, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 22, 4, 5 + var4 * 4, 23, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
            this.setBlockState(var1, field_175826_b, 25, 5, 5 + var4 * 4, var3);
            this.setBlockState(var1, field_175826_b, 26, 6, 5 + var4 * 4, var3);
            this.setBlockState(var1, field_175825_e, 26, 5, 5 + var4 * 4, var3);
            this.fillWithBlocks(var1, var3, 33, 2, 5 + var4 * 4, 33, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 34, 4, 5 + var4 * 4, 35, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
            this.setBlockState(var1, field_175826_b, 32, 5, 5 + var4 * 4, var3);
            this.setBlockState(var1, field_175826_b, 31, 6, 5 + var4 * 4, var3);
            this.setBlockState(var1, field_175825_e, 31, 5, 5 + var4 * 4, var3);
            this.fillWithBlocks(var1, var3, 27, 6, 5 + var4 * 4, 30, 6, 5 + var4 * 4, field_175828_a, field_175828_a, false);
         }
      }

   }

   private void func_175837_c(World var1, Random var2, StructureBoundingBox var3) {
      if(this.func_175818_a(var3, 15, 20, 42, 21)) {
         this.fillWithBlocks(var1, var3, 15, 0, 21, 42, 0, 21, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 26, 1, 21, 31, 3, 21, false);
         this.fillWithBlocks(var1, var3, 21, 12, 21, 36, 12, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 17, 11, 21, 40, 11, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 16, 10, 21, 41, 10, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 15, 7, 21, 42, 9, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 16, 6, 21, 41, 6, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 17, 5, 21, 40, 5, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 21, 4, 21, 36, 4, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 22, 3, 21, 26, 3, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 31, 3, 21, 35, 3, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 23, 2, 21, 25, 2, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 32, 2, 21, 34, 2, 21, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 28, 4, 20, 29, 4, 21, field_175826_b, field_175826_b, false);
         this.setBlockState(var1, field_175826_b, 27, 3, 21, var3);
         this.setBlockState(var1, field_175826_b, 30, 3, 21, var3);
         this.setBlockState(var1, field_175826_b, 26, 2, 21, var3);
         this.setBlockState(var1, field_175826_b, 31, 2, 21, var3);
         this.setBlockState(var1, field_175826_b, 25, 1, 21, var3);
         this.setBlockState(var1, field_175826_b, 32, 1, 21, var3);

         for(int var4 = 0; var4 < 7; ++var4) {
            this.setBlockState(var1, field_175827_c, 28 - var4, 6 + var4, 21, var3);
            this.setBlockState(var1, field_175827_c, 29 + var4, 6 + var4, 21, var3);
         }

         for(int var5 = 0; var5 < 4; ++var5) {
            this.setBlockState(var1, field_175827_c, 28 - var5, 9 + var5, 21, var3);
            this.setBlockState(var1, field_175827_c, 29 + var5, 9 + var5, 21, var3);
         }

         this.setBlockState(var1, field_175827_c, 28, 12, 21, var3);
         this.setBlockState(var1, field_175827_c, 29, 12, 21, var3);

         for(int var6 = 0; var6 < 3; ++var6) {
            this.setBlockState(var1, field_175827_c, 22 - var6 * 2, 8, 21, var3);
            this.setBlockState(var1, field_175827_c, 22 - var6 * 2, 9, 21, var3);
            this.setBlockState(var1, field_175827_c, 35 + var6 * 2, 8, 21, var3);
            this.setBlockState(var1, field_175827_c, 35 + var6 * 2, 9, 21, var3);
         }

         this.func_181655_a(var1, var3, 15, 13, 21, 42, 15, 21, false);
         this.func_181655_a(var1, var3, 15, 1, 21, 15, 6, 21, false);
         this.func_181655_a(var1, var3, 16, 1, 21, 16, 5, 21, false);
         this.func_181655_a(var1, var3, 17, 1, 21, 20, 4, 21, false);
         this.func_181655_a(var1, var3, 21, 1, 21, 21, 3, 21, false);
         this.func_181655_a(var1, var3, 22, 1, 21, 22, 2, 21, false);
         this.func_181655_a(var1, var3, 23, 1, 21, 24, 1, 21, false);
         this.func_181655_a(var1, var3, 42, 1, 21, 42, 6, 21, false);
         this.func_181655_a(var1, var3, 41, 1, 21, 41, 5, 21, false);
         this.func_181655_a(var1, var3, 37, 1, 21, 40, 4, 21, false);
         this.func_181655_a(var1, var3, 36, 1, 21, 36, 3, 21, false);
         this.func_181655_a(var1, var3, 33, 1, 21, 34, 1, 21, false);
         this.func_181655_a(var1, var3, 35, 1, 21, 35, 2, 21, false);
      }

   }

   private void func_175841_d(World var1, Random var2, StructureBoundingBox var3) {
      if(this.func_175818_a(var3, 21, 21, 36, 36)) {
         this.fillWithBlocks(var1, var3, 21, 0, 22, 36, 0, 36, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 21, 1, 22, 36, 23, 36, false);

         for(int var4 = 0; var4 < 4; ++var4) {
            this.fillWithBlocks(var1, var3, 21 + var4, 13 + var4, 21 + var4, 36 - var4, 13 + var4, 21 + var4, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 21 + var4, 13 + var4, 36 - var4, 36 - var4, 13 + var4, 36 - var4, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 21 + var4, 13 + var4, 22 + var4, 21 + var4, 13 + var4, 35 - var4, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 36 - var4, 13 + var4, 22 + var4, 36 - var4, 13 + var4, 35 - var4, field_175826_b, field_175826_b, false);
         }

         this.fillWithBlocks(var1, var3, 25, 16, 25, 32, 16, 32, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 25, 17, 25, 25, 19, 25, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(var1, var3, 32, 17, 25, 32, 19, 25, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(var1, var3, 25, 17, 32, 25, 19, 32, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(var1, var3, 32, 17, 32, 32, 19, 32, field_175826_b, field_175826_b, false);
         this.setBlockState(var1, field_175826_b, 26, 20, 26, var3);
         this.setBlockState(var1, field_175826_b, 27, 21, 27, var3);
         this.setBlockState(var1, field_175825_e, 27, 20, 27, var3);
         this.setBlockState(var1, field_175826_b, 26, 20, 31, var3);
         this.setBlockState(var1, field_175826_b, 27, 21, 30, var3);
         this.setBlockState(var1, field_175825_e, 27, 20, 30, var3);
         this.setBlockState(var1, field_175826_b, 31, 20, 31, var3);
         this.setBlockState(var1, field_175826_b, 30, 21, 30, var3);
         this.setBlockState(var1, field_175825_e, 30, 20, 30, var3);
         this.setBlockState(var1, field_175826_b, 31, 20, 26, var3);
         this.setBlockState(var1, field_175826_b, 30, 21, 27, var3);
         this.setBlockState(var1, field_175825_e, 30, 20, 27, var3);
         this.fillWithBlocks(var1, var3, 28, 21, 27, 29, 21, 27, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 27, 21, 28, 27, 21, 29, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 28, 21, 30, 29, 21, 30, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 30, 21, 28, 30, 21, 29, field_175828_a, field_175828_a, false);
      }

   }

   private void func_175835_e(World var1, Random var2, StructureBoundingBox var3) {
      if(this.func_175818_a(var3, 0, 21, 6, 58)) {
         this.fillWithBlocks(var1, var3, 0, 0, 21, 6, 0, 57, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 0, 1, 21, 6, 7, 57, false);
         this.fillWithBlocks(var1, var3, 4, 4, 21, 6, 4, 53, field_175828_a, field_175828_a, false);

         for(int var4 = 0; var4 < 4; ++var4) {
            this.fillWithBlocks(var1, var3, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, field_175826_b, field_175826_b, false);
         }

         for(int var5 = 23; var5 < 53; var5 += 3) {
            this.setBlockState(var1, field_175824_d, 5, 5, var5, var3);
         }

         this.setBlockState(var1, field_175824_d, 5, 5, 52, var3);

         for(int var6 = 0; var6 < 4; ++var6) {
            this.fillWithBlocks(var1, var3, var6, var6 + 1, 21, var6, var6 + 1, 57 - var6, field_175826_b, field_175826_b, false);
         }

         this.fillWithBlocks(var1, var3, 4, 1, 52, 6, 3, 52, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 5, 1, 51, 5, 3, 53, field_175828_a, field_175828_a, false);
      }

      if(this.func_175818_a(var3, 51, 21, 58, 58)) {
         this.fillWithBlocks(var1, var3, 51, 0, 21, 57, 0, 57, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 51, 1, 21, 57, 7, 57, false);
         this.fillWithBlocks(var1, var3, 51, 4, 21, 53, 4, 53, field_175828_a, field_175828_a, false);

         for(int var7 = 0; var7 < 4; ++var7) {
            this.fillWithBlocks(var1, var3, 57 - var7, var7 + 1, 21, 57 - var7, var7 + 1, 57 - var7, field_175826_b, field_175826_b, false);
         }

         for(int var8 = 23; var8 < 53; var8 += 3) {
            this.setBlockState(var1, field_175824_d, 52, 5, var8, var3);
         }

         this.setBlockState(var1, field_175824_d, 52, 5, 52, var3);
         this.fillWithBlocks(var1, var3, 51, 1, 52, 53, 3, 52, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 52, 1, 51, 52, 3, 53, field_175828_a, field_175828_a, false);
      }

      if(this.func_175818_a(var3, 0, 51, 57, 57)) {
         this.fillWithBlocks(var1, var3, 7, 0, 51, 50, 0, 57, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 7, 1, 51, 50, 10, 57, false);

         for(int var9 = 0; var9 < 4; ++var9) {
            this.fillWithBlocks(var1, var3, var9 + 1, var9 + 1, 57 - var9, 56 - var9, var9 + 1, 57 - var9, field_175826_b, field_175826_b, false);
         }
      }

   }

   private void func_175842_f(World var1, Random var2, StructureBoundingBox var3) {
      if(this.func_175818_a(var3, 7, 21, 13, 50)) {
         this.fillWithBlocks(var1, var3, 7, 0, 21, 13, 0, 50, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 7, 1, 21, 13, 10, 50, false);
         this.fillWithBlocks(var1, var3, 11, 8, 21, 13, 8, 53, field_175828_a, field_175828_a, false);

         for(int var4 = 0; var4 < 4; ++var4) {
            this.fillWithBlocks(var1, var3, var4 + 7, var4 + 5, 21, var4 + 7, var4 + 5, 54, field_175826_b, field_175826_b, false);
         }

         for(int var5 = 21; var5 <= 45; var5 += 3) {
            this.setBlockState(var1, field_175824_d, 12, 9, var5, var3);
         }
      }

      if(this.func_175818_a(var3, 44, 21, 50, 54)) {
         this.fillWithBlocks(var1, var3, 44, 0, 21, 50, 0, 50, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 44, 1, 21, 50, 10, 50, false);
         this.fillWithBlocks(var1, var3, 44, 8, 21, 46, 8, 53, field_175828_a, field_175828_a, false);

         for(int var6 = 0; var6 < 4; ++var6) {
            this.fillWithBlocks(var1, var3, 50 - var6, var6 + 5, 21, 50 - var6, var6 + 5, 54, field_175826_b, field_175826_b, false);
         }

         for(int var7 = 21; var7 <= 45; var7 += 3) {
            this.setBlockState(var1, field_175824_d, 45, 9, var7, var3);
         }
      }

      if(this.func_175818_a(var3, 8, 44, 49, 54)) {
         this.fillWithBlocks(var1, var3, 14, 0, 44, 43, 0, 50, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 14, 1, 44, 43, 10, 50, false);

         for(int var8 = 12; var8 <= 45; var8 += 3) {
            this.setBlockState(var1, field_175824_d, var8, 9, 45, var3);
            this.setBlockState(var1, field_175824_d, var8, 9, 52, var3);
            if(var8 == 12 || var8 == 18 || var8 == 24 || var8 == 33 || var8 == 39 || var8 == 45) {
               this.setBlockState(var1, field_175824_d, var8, 9, 47, var3);
               this.setBlockState(var1, field_175824_d, var8, 9, 50, var3);
               this.setBlockState(var1, field_175824_d, var8, 10, 45, var3);
               this.setBlockState(var1, field_175824_d, var8, 10, 46, var3);
               this.setBlockState(var1, field_175824_d, var8, 10, 51, var3);
               this.setBlockState(var1, field_175824_d, var8, 10, 52, var3);
               this.setBlockState(var1, field_175824_d, var8, 11, 47, var3);
               this.setBlockState(var1, field_175824_d, var8, 11, 50, var3);
               this.setBlockState(var1, field_175824_d, var8, 12, 48, var3);
               this.setBlockState(var1, field_175824_d, var8, 12, 49, var3);
            }
         }

         for(int var9 = 0; var9 < 3; ++var9) {
            this.fillWithBlocks(var1, var3, 8 + var9, 5 + var9, 54, 49 - var9, 5 + var9, 54, field_175828_a, field_175828_a, false);
         }

         this.fillWithBlocks(var1, var3, 11, 8, 54, 46, 8, 54, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(var1, var3, 14, 8, 44, 43, 8, 53, field_175828_a, field_175828_a, false);
      }

   }

   private void func_175838_g(World var1, Random var2, StructureBoundingBox var3) {
      if(this.func_175818_a(var3, 14, 21, 20, 43)) {
         this.fillWithBlocks(var1, var3, 14, 0, 21, 20, 0, 43, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 14, 1, 22, 20, 14, 43, false);
         this.fillWithBlocks(var1, var3, 18, 12, 22, 20, 12, 39, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 18, 12, 21, 20, 12, 21, field_175826_b, field_175826_b, false);

         for(int var4 = 0; var4 < 4; ++var4) {
            this.fillWithBlocks(var1, var3, var4 + 14, var4 + 9, 21, var4 + 14, var4 + 9, 43 - var4, field_175826_b, field_175826_b, false);
         }

         for(int var5 = 23; var5 <= 39; var5 += 3) {
            this.setBlockState(var1, field_175824_d, 19, 13, var5, var3);
         }
      }

      if(this.func_175818_a(var3, 37, 21, 43, 43)) {
         this.fillWithBlocks(var1, var3, 37, 0, 21, 43, 0, 43, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 37, 1, 22, 43, 14, 43, false);
         this.fillWithBlocks(var1, var3, 37, 12, 22, 39, 12, 39, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(var1, var3, 37, 12, 21, 39, 12, 21, field_175826_b, field_175826_b, false);

         for(int var6 = 0; var6 < 4; ++var6) {
            this.fillWithBlocks(var1, var3, 43 - var6, var6 + 9, 21, 43 - var6, var6 + 9, 43 - var6, field_175826_b, field_175826_b, false);
         }

         for(int var7 = 23; var7 <= 39; var7 += 3) {
            this.setBlockState(var1, field_175824_d, 38, 13, var7, var3);
         }
      }

      if(this.func_175818_a(var3, 15, 37, 42, 43)) {
         this.fillWithBlocks(var1, var3, 21, 0, 37, 36, 0, 43, field_175828_a, field_175828_a, false);
         this.func_181655_a(var1, var3, 21, 1, 37, 36, 14, 43, false);
         this.fillWithBlocks(var1, var3, 21, 12, 37, 36, 12, 39, field_175828_a, field_175828_a, false);

         for(int var8 = 0; var8 < 4; ++var8) {
            this.fillWithBlocks(var1, var3, 15 + var8, var8 + 9, 43 - var8, 42 - var8, var8 + 9, 43 - var8, field_175826_b, field_175826_b, false);
         }

         for(int var9 = 21; var9 <= 36; var9 += 3) {
            this.setBlockState(var1, field_175824_d, var9, 13, 38, var3);
         }
      }

   }
}
