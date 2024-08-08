package com.example.editme.modules.misc;

import com.example.editme.events.EditmeEvent;
import com.example.editme.events.EventPlayerMotionUpdate;
import com.example.editme.events.PacketEvent;
import com.example.editme.events.PlayerMoveEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.client.PlayerUtil;
import com.example.editme.util.client.Timer;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(
   name = "Scaffold",
   category = Module.Category.MISC
)
public class Scaffold extends Module {
   @EventHandler
   private Listener onMotionUpdate = new Listener(this::lambda$new$0, new Predicate[0]);
   private Timer towerPauseTimer = new Timer();
   private Timer timer = new Timer();
   private Timer towerTimer = new Timer();
   @EventHandler
   private Listener onPacketEvent = new Listener(this::lambda$new$1, new Predicate[0]);
   @EventHandler
   private Listener onPlayerMove = new Listener(Scaffold::lambda$new$2, new Predicate[0]);

   private boolean isValidResult(BlockPos var1) {
      BlockInteractionHelper.ValidResult var2 = BlockInteractionHelper.valid(var1);
      if (var2 == BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
         return mc.field_71441_e.func_180495_p(var1).func_185904_a().func_76222_j();
      } else {
         return var2 == BlockInteractionHelper.ValidResult.Ok;
      }
   }

   private void lambda$new$1(PacketEvent var1) {
      if (var1.getPacket() instanceof SPacketPlayerPosLook) {
         this.towerTimer.reset();
      }

   }

   private void lambda$new$0(EventPlayerMotionUpdate var1) {
      if (!var1.isCancelled()) {
         if (var1.getEra() == EditmeEvent.Era.PRE) {
            if (this.timer.passed(100.0D)) {
               ItemStack var2 = mc.field_71439_g.func_184614_ca();
               int var3 = -1;
               if (var2.func_190926_b() || !(var2.func_77973_b() instanceof ItemBlock)) {
                  for(int var4 = 0; var4 < 9; ++var4) {
                     var2 = mc.field_71439_g.field_71071_by.func_70301_a(var4);
                     if (!var2.func_190926_b() && var2.func_77973_b() instanceof ItemBlock) {
                        var3 = mc.field_71439_g.field_71071_by.field_70461_c;
                        mc.field_71439_g.field_71071_by.field_70461_c = var4;
                        mc.field_71442_b.func_78765_e();
                        break;
                     }
                  }
               }

               if (!var2.func_190926_b() && var2.func_77973_b() instanceof ItemBlock) {
                  this.timer.reset();
                  BlockPos var18 = null;
                  BlockPos var5 = PlayerUtil.GetLocalPlayerPosFloored().func_177977_b();
                  boolean var6 = this.isValidResult(var5);
                  if (var6 && mc.field_71439_g.field_71158_b.field_78901_c && this.towerTimer.passed(250.0D)) {
                     if (this.towerPauseTimer.passed(1500.0D)) {
                        this.towerPauseTimer.reset();
                        mc.field_71439_g.field_70181_x = -0.2800000011920929D;
                     } else {
                        float var7 = 0.42F;
                        mc.field_71439_g.func_70016_h(0.0D, 0.41999998688697815D, 0.0D);
                     }
                  }

                  if (var6) {
                     var18 = var5;
                  } else {
                     BlockInteractionHelper.ValidResult var19 = BlockInteractionHelper.valid(var5);
                     if (var19 != BlockInteractionHelper.ValidResult.Ok && var19 != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                        BlockPos[] var8 = new BlockPos[]{var5.func_177978_c(), var5.func_177968_d(), var5.func_177974_f(), var5.func_177976_e()};
                        BlockPos var9 = null;
                        double var10 = 420.0D;
                        BlockPos[] var12 = var8;
                        int var13 = var8.length;

                        for(int var14 = 0; var14 < var13; ++var14) {
                           BlockPos var15 = var12[var14];
                           if (this.isValidResult(var15)) {
                              double var16 = var15.func_185332_f((int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70163_u, (int)mc.field_71439_g.field_70161_v);
                              if (var10 > var16) {
                                 var10 = var16;
                                 var9 = var15;
                              }
                           }
                        }

                        if (var9 != null) {
                           var18 = var9;
                        }
                     }
                  }

                  if (var18 != null) {
                     Vec3d var20 = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
                     EnumFacing[] var21 = EnumFacing.values();
                     int var22 = var21.length;

                     for(int var23 = 0; var23 < var22; ++var23) {
                        EnumFacing var11 = var21[var23];
                        BlockPos var24 = var18.func_177972_a(var11);
                        EnumFacing var25 = var11.func_176734_d();
                        if (mc.field_71441_e.func_180495_p(var24).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(var24), false)) {
                           Vec3d var26 = (new Vec3d(var24)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(var25.func_176730_m())).func_186678_a(0.5D));
                           if (var20.func_72438_d(var26) <= 5.0D) {
                              float[] var27 = BlockInteractionHelper.getFacingRotations(var18.func_177958_n(), var18.func_177956_o(), var18.func_177952_p(), var11);
                              var1.cancel();
                              PlayerUtil.PacketFacePitchAndYaw(var27[1], var27[0]);
                              break;
                           }
                        }
                     }

                     if (BlockInteractionHelper.place(var18, 5.0F, false, false, true) == BlockInteractionHelper.PlaceResult.Placed) {
                     }
                  } else {
                     this.towerPauseTimer.reset();
                  }

                  if (var3 != -1) {
                     mc.field_71439_g.field_71071_by.field_70461_c = var3;
                     mc.field_71442_b.func_78765_e();
                  }

               }
            }
         }
      }
   }

   private static void lambda$new$2(PlayerMoveEvent var0) {
      double var1 = var0.getX();
      double var3 = var0.getY();
      double var5 = var0.getZ();
      if (mc.field_71439_g.field_70122_E && !mc.field_71439_g.field_70145_X) {
         double var7 = 0.05D;

         label58:
         while(true) {
            while(var1 != 0.0D && mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(var1, -1.0D, 0.0D)).isEmpty()) {
               if (var1 < var7 && var1 >= -var7) {
                  var1 = 0.0D;
               } else if (var1 > 0.0D) {
                  var1 -= var7;
               } else {
                  var1 += var7;
               }
            }

            while(true) {
               while(var5 != 0.0D && mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -1.0D, var5)).isEmpty()) {
                  if (var5 < var7 && var5 >= -var7) {
                     var5 = 0.0D;
                  } else if (var5 > 0.0D) {
                     var5 -= var7;
                  } else {
                     var5 += var7;
                  }
               }

               while(true) {
                  while(true) {
                     if (var1 == 0.0D || var5 == 0.0D || !mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(var1, -1.0D, var5)).isEmpty()) {
                        break label58;
                     }

                     if (var1 < var7 && var1 >= -var7) {
                        var1 = 0.0D;
                     } else if (var1 > 0.0D) {
                        var1 -= var7;
                     } else {
                        var1 += var7;
                     }

                     if (var5 < var7 && var5 >= -var7) {
                        var5 = 0.0D;
                     } else if (var5 > 0.0D) {
                        var5 -= var7;
                     } else {
                        var5 += var7;
                     }
                  }
               }
            }
         }
      }

      var0.setX(var1);
      var0.setY(var3);
      var0.setZ(var5);
      var0.cancel();
   }
}
