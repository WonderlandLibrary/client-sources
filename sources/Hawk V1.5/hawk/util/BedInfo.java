package hawk.util;

import hawk.modules.player.Bednuker;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedInfo {
   public EnumFacing face;
   public BlockPos pos;
   public static Minecraft mc;

   public BedInfo findBed() {
      try {
         if (Bednuker.lastBed != null && mc.thePlayer.getDistance((double)Bednuker.lastBed.pos.getX(), (double)Bednuker.lastBed.pos.getY(), (double)Bednuker.lastBed.pos.getZ()) <= 4.0D && mc.theWorld.getBlockState(Bednuker.lastBed.pos).getBlock() instanceof BlockBed) {
            return Bednuker.lastBed;
         }
      } catch (Exception var28) {
      }

      BlockPos var1 = null;
      EnumFacing var2 = null;
      EnumFacing[] var6;
      int var5 = (var6 = EnumFacing.VALUES).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         EnumFacing var3 = var6[var4];
         BlockPos var7 = mc.thePlayer.getPosition();
         if (mc.theWorld.getBlockState(var7.offset(var3)).getBlock() instanceof BlockBed) {
            var1 = var7.offset(var3);
            var2 = var3.getOpposite();
            break;
         }

         EnumFacing[] var11;
         int var10 = (var11 = EnumFacing.VALUES).length;

         for(int var9 = 0; var9 < var10; ++var9) {
            EnumFacing var8 = var11[var9];
            BlockPos var12 = var7.offset(var8);
            if (mc.theWorld.getBlockState(var12).getBlock() instanceof BlockBed) {
               var1 = var12;
               var2 = var8.getOpposite();
               break;
            }

            EnumFacing[] var16;
            int var15 = (var16 = EnumFacing.VALUES).length;

            for(int var14 = 0; var14 < var15; ++var14) {
               EnumFacing var13 = var16[var14];
               BlockPos var17 = var12.offset(var13);
               if (mc.theWorld.getBlockState(var17).getBlock() instanceof BlockBed) {
                  var1 = var17;
                  var2 = var13.getOpposite();
                  break;
               }

               EnumFacing[] var21;
               int var20 = (var21 = EnumFacing.VALUES).length;

               for(int var19 = 0; var19 < var20; ++var19) {
                  EnumFacing var18 = var21[var19];
                  BlockPos var22 = var17.offset(var18);
                  if (mc.theWorld.getBlockState(var22).getBlock() instanceof BlockBed) {
                     var1 = var22;
                     var2 = var18.getOpposite();
                     break;
                  }

                  EnumFacing[] var26;
                  int var25 = (var26 = EnumFacing.VALUES).length;

                  for(int var24 = 0; var24 < var25; ++var24) {
                     EnumFacing var23 = var26[var24];
                     BlockPos var27 = var22.offset(var23);
                     if (mc.theWorld.getBlockState(var27).getBlock() instanceof BlockBed) {
                        var1 = var27;
                        var2 = var23.getOpposite();
                        break;
                     }
                  }
               }
            }
         }
      }

      Bednuker.lastBed = new BedInfo(var1, var2);
      return Bednuker.lastBed;
   }

   public BedInfo(BlockPos var1, EnumFacing var2) {
      this.pos = var1;
      this.face = var2;
   }
}
