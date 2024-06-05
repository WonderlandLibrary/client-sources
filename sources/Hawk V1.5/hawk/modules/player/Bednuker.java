package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventRender3D;
import hawk.modules.Module;
import hawk.modules.combat.Killaura;
import hawk.util.BedInfo;
import hawk.util.RotationUtils;
import net.minecraft.block.BlockBed;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Bednuker extends Module {
   public static BedInfo lastBed = null;

   public void onEvent(Event var1) {
      BedInfo var2;
      BlockPos var3;
      EnumFacing var4;
      if (var1 instanceof EventRender3D && var1.isPre()) {
         var2 = this.findBed();
         var3 = var2.pos;
         var4 = var2.face;
         if (var3 == null || var4 == null) {
            return;
         }
      }

      if (var1 instanceof EventMotion && var1.isPost()) {
         var2 = this.findBed();
         var3 = var2.pos;
         var4 = var2.face;
         if (var3 == null || var4 == null) {
            return;
         }

         EventMotion var5 = (EventMotion)var1;
         if (!Killaura.HasTarget) {
            float[] var6 = RotationUtils.getRotationFromPosition((double)var3.getX(), (double)var3.getZ(), (double)var3.getY());
            var5.setYaw(var6[0]);
            var5.setPitch(var6[1]);
         }

         this.mc.thePlayer.swingItem();
         this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, var3, var4));
         this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, var3, var4));
      }

   }

   public BedInfo findBed() {
      try {
         if (lastBed != null && this.mc.thePlayer.getDistance((double)lastBed.pos.getX(), (double)lastBed.pos.getY(), (double)lastBed.pos.getZ()) <= 4.0D && this.mc.theWorld.getBlockState(lastBed.pos).getBlock() instanceof BlockBed) {
            return lastBed;
         }
      } catch (Exception var28) {
      }

      BlockPos var1 = null;
      EnumFacing var2 = null;
      EnumFacing[] var6;
      int var5 = (var6 = EnumFacing.VALUES).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         EnumFacing var3 = var6[var4];
         BlockPos var7 = this.mc.thePlayer.getPosition();
         if (this.mc.theWorld.getBlockState(var7.offset(var3)).getBlock() instanceof BlockBed) {
            var1 = var7.offset(var3);
            var2 = var3.getOpposite();
            break;
         }

         EnumFacing[] var11;
         int var10 = (var11 = EnumFacing.VALUES).length;

         for(int var9 = 0; var9 < var10; ++var9) {
            EnumFacing var8 = var11[var9];
            BlockPos var12 = var7.offset(var8);
            if (this.mc.theWorld.getBlockState(var12).getBlock() instanceof BlockBed) {
               var1 = var12;
               var2 = var8.getOpposite();
               break;
            }

            EnumFacing[] var16;
            int var15 = (var16 = EnumFacing.VALUES).length;

            for(int var14 = 0; var14 < var15; ++var14) {
               EnumFacing var13 = var16[var14];
               BlockPos var17 = var12.offset(var13);
               if (this.mc.theWorld.getBlockState(var17).getBlock() instanceof BlockBed) {
                  var1 = var17;
                  var2 = var13.getOpposite();
                  break;
               }

               EnumFacing[] var21;
               int var20 = (var21 = EnumFacing.VALUES).length;

               for(int var19 = 0; var19 < var20; ++var19) {
                  EnumFacing var18 = var21[var19];
                  BlockPos var22 = var17.offset(var18);
                  if (this.mc.theWorld.getBlockState(var22).getBlock() instanceof BlockBed) {
                     var1 = var22;
                     var2 = var18.getOpposite();
                     break;
                  }

                  EnumFacing[] var26;
                  int var25 = (var26 = EnumFacing.VALUES).length;

                  for(int var24 = 0; var24 < var25; ++var24) {
                     EnumFacing var23 = var26[var24];
                     BlockPos var27 = var22.offset(var23);
                     if (this.mc.theWorld.getBlockState(var27).getBlock() instanceof BlockBed) {
                        var1 = var27;
                        var2 = var23.getOpposite();
                        break;
                     }
                  }
               }
            }
         }
      }

      lastBed = new BedInfo(var1, var2);
      return lastBed;
   }

   public Bednuker() {
      super("BedBreaker", 0, Module.Category.PLAYER);
   }
}
