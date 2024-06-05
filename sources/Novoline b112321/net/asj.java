package net;

import cc.novoline.events.events.EventState;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEE;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.as_;
import net.asx;
import net.awR;
import net.axu;
import net.azi;
import net.d3;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

public class asj extends as0 {
   private boolean D;
   private List y = new CopyOnWriteArrayList();
   private d3 x = new d3();
   @VN("mode")
   private aEs z = axu.a("BlocksMC").a(new String[]{"BlocksMC", "NCPNoTimer"});
   @VN("speed")
   private aEs B = axu.a("Instant").a(new String[]{"Instant", "Delayed"});
   @VN("delay")
   private aEE A = (aEE)((aEE)axu.a(Double.valueOf(100.0D)).d(Double.valueOf(50.0D))).c(Double.valueOf(400.0D));
   @VN("damage")
   private aEu C = axu.a(Boolean.valueOf(true));

   public asj(@NotNull UW var1) {
      super(var1, "ClickTeleport", "Click Teleport", a2V.EXPLOITS, "lol");
      ae9.a(new adZ("CT_MODE", "Mode", a6d.COMBOBOX, this, this.z));
      as_.a();
      ae9.a(new adZ("CT_SPEED", "Speed", a6d.COMBOBOX, this, this.B, this::lambda$new$0));
      ae9.a(new adZ("CT_DELAY", "Delay", a6d.SLIDER, this, this.A, 10.0D, this::lambda$new$1));
      ae9.a(new adZ("CT_DAMAGE", "Damage", a6d.CHECKBOX, this, this.C, this::lambda$new$2));
   }

   private Vec3 c() {
      return new Vec3(this.w.thePlayer.posX, this.w.thePlayer.posY + (double)this.w.thePlayer.getEyeHeight(), this.w.thePlayer.posZ);
   }

   public MovingObjectPosition d() {
      float var1 = 999.0F;
      Vec3 var2 = this.c();
      Vec3 var3 = awR.a(this.w.thePlayer.rotationPitch, this.w.thePlayer.rotationYaw);
      Vec3 var4 = var2.addVector(var3.xCoord * (double)var1, var3.yCoord * (double)var1, var3.zCoord * (double)var1);
      return this.w.theWorld.rayTraceBlocks(var2, var4, false, false, false);
   }

   public void n() {
      this.y.clear();
      this.x.b();
      this.b();
   }

   @agu
   public void a(aL_ var1) {
      int var2 = as_.b();
      if(((String)this.z.a()).equals("BlocksMC") && ((String)this.B.a()).equals("Delayed") && !this.y.isEmpty()) {
         var1.a(this.w.thePlayer.motionY = 0.0D);
         var1.c(0.0D);
      }

   }

   @agu
   public void a(aG1 var1) {
      int var2 = as_.b();
      if(var1.h() == EventState.PRE && ((String)this.z.a()).equals("BlocksMC") && ((String)this.B.a()).equals("Delayed") && this.x.a(((Double)this.A.a()).doubleValue())) {
         if(!this.y.isEmpty()) {
            if(this.y.size() == 1) {
               this.w.thePlayer.setPosition((double)((BlockPos)this.y.get(0)).getX(), (double)(((BlockPos)this.y.get(0)).getY() + 1), (double)((BlockPos)this.y.get(0)).getZ());
               this.e();
            }

            this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition((double)((BlockPos)this.y.get(0)).getX(), (double)((BlockPos)this.y.get(0)).getY(), (double)((BlockPos)this.y.get(0)).getZ(), false)));
            this.y.remove(0);
         }

         this.x.b();
      }

   }

   private void b() {
      int var1 = as_.b();
      if(((String)this.z.a()).equals("NCPNoTimer")) {
         double var2 = this.w.thePlayer.as();
         if(this.d() != null) {
            if(this.d().typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
               BlockPos var4 = this.d().getBlockPos();
               double var5 = (double)var4.getX() + 0.5D;
               double var7 = (double)var4.getZ() + 0.5D;
               float var9 = awR.a(var5, var7);
               double var10 = Math.abs(this.w.thePlayer.getDistance(var5, (double)var4.getY(), var7));
               double var12 = this.w.thePlayer.posY;
               if(var2 < var10) {
                  double var16 = -Math.sin(Math.toRadians((double)var9)) * var2;
                  double var18 = Math.cos(Math.toRadians((double)var9)) * var2;
                  double var20 = 0.0D;
                  Vec3 var22 = this.b(this.w.thePlayer.posX + var16, var12, this.w.thePlayer.posZ + var18);
                  Vec3 var23 = awR.a(90.0F, 0.0F);
                  Vec3 var24 = var22.addVector(var23.xCoord * 99.0D, var23.yCoord * 99.0D, var23.zCoord * 99.0D);
                  MovingObjectPosition var25 = this.w.theWorld.rayTraceBlocks(var22, var24, false, false, false);
                  if(var25 == null) {
                     this.j.t().a("Can\'t teleport there!", 2000, azi.ERROR);
                     return;
                  }

                  double var26 = 0.0D;
                  if(var26 < var2) {
                     Vec3 var28 = awR.a(0.0F, var9);
                     Vec3 var29 = var22.addVector(var28.xCoord * var26, var28.yCoord * var26, var28.zCoord * var26);
                     MovingObjectPosition var30 = this.w.theWorld.rayTraceBlocks(var22, var29, false, false, false);
                     if(var30 != null && var30.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
                        var20 = var26;
                     }

                     double var10000 = var26 + var2 / 100.0D;
                  }

                  Block var51 = this.w.theWorld.getBlockState(new BlockPos(this.w.thePlayer.posX - Math.sin(Math.toRadians((double)var9)) * (var2 + var2), var12, this.w.thePlayer.posZ + Math.cos(Math.toRadians((double)var9)) * (var2 + var2))).getBlock();
                  if(var20 > 0.0D && var51 != Blocks.tallgrass) {
                     double var27 = -Math.sin(Math.toRadians((double)var9)) * (var20 - 0.3D);
                     double var53 = Math.cos(Math.toRadians((double)var9)) * (var20 - 0.3D);
                     this.a(this.w.thePlayer.posX + var16 + var27, var12, this.w.thePlayer.posZ + var18 + var53);
                     double var31 = var51 == Blocks.air?0.5D:var51.getBlockBoundsMaxY() - var51.getBlockBoundsMinY();
                     double var33 = var31 - var12 % 1.0D;
                     if(var33 < 0.625D) {
                        this.a(this.w.thePlayer.posX + var16 + var27, var12 + var33, this.w.thePlayer.posZ + var18 + var53);
                     }

                     this.a(this.w.thePlayer.posX + var16 + var27, var12 + 0.41999998688698D, this.w.thePlayer.posZ + var18 + var53);
                     this.a(this.w.thePlayer.posX + var16 + var27, var12 + 0.7531999805212D, this.w.thePlayer.posZ + var18 + var53);
                     var12 += var33;
                  }

                  double var52 = 0.0D;
                  Vec3 var54 = this.b(this.w.thePlayer.posX + var16, var12, this.w.thePlayer.posZ + var18);
                  double var55 = 0.0D;
                  if(var55 < 99.0D) {
                     Vec3 var32 = awR.a(90.0F, 0.0F);
                     Vec3 var59 = var54.addVector(var32.xCoord * var55, var32.yCoord * var55, var32.zCoord * var55);
                     MovingObjectPosition var34 = this.w.theWorld.rayTraceBlocks(var54, var59, false, false, false);
                     if(var34 != null && var34.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
                        Block var35 = this.w.theWorld.getBlockState(var34.getBlockPos()).getBlock();
                        if(var35 != Blocks.tallgrass) {
                           var52 = MathHelper.a(MathHelper.b(var55, 3), 0.125D);
                        }
                     }

                     var55 = var55 + 1.0E-4D;
                  }

                  if(var52 > 0.0D) {
                     var55 = 0.0D;
                     Vec3 var58 = this.b(this.w.thePlayer.posX + var16, var12 - 0.01D, this.w.thePlayer.posZ + var18);
                     double var60 = 0.0D;
                     if(var60 < var2) {
                        Vec3 var63 = awR.a(0.0F, var9);
                        Vec3 var36 = var58.addVector(var63.xCoord * var60, var63.yCoord * var60, var63.zCoord * var60);
                        MovingObjectPosition var37 = this.w.theWorld.rayTraceBlocks(var58, var36, false, false, false);
                        if(var37 != null && var37.typeOfHit != MovingObjectPosition$MovingObjectType.BLOCK) {
                           var55 = var60;
                        }

                        var60 = var60 + 0.001D;
                     }

                     var60 = -Math.sin(Math.toRadians((double)var9)) * (var55 + 0.5D);
                     double var64 = Math.cos(Math.toRadians((double)var9)) * (var55 + 0.5D);
                     this.a(this.w.thePlayer.posX + var16 + var60, var12, this.w.thePlayer.posZ + var18 + var64);
                     double var65 = 0.0D;
                     double var39 = 0.0D;
                     int var43 = 0;
                     if(var43 < 99) {
                        label1198: {
                           if(var65 < var52) {
                              var39 = var39 + 0.08D;
                              var39 = var39 * 0.980000019073486D;
                              var65 = var65 + var39;
                              double var41 = var12 - var65;
                              if(var65 >= var52) {
                                 break label1198;
                              }

                              this.a(this.w.thePlayer.posX + var16 + var60, var41, this.w.thePlayer.posZ + var18 + var64);
                           }

                           var12 -= var52;
                        }

                        ++var43;
                     }
                  }

                  this.a(this.w.thePlayer.posX + var16, var12, this.w.thePlayer.posZ + var18);
                  double var14 = var2 + var2;
               }

               this.w.thePlayer.setPosition(var5, var12, var7);
            }

            this.e();
         }
      }

      if(((String)this.z.a()).equals("BlocksMC")) {
         MovingObjectPosition var44 = this.d();
         if(var44 != null) {
            if(var44.typeOfHit != MovingObjectPosition$MovingObjectType.BLOCK) {
               return;
            }

            if(((Boolean)this.C.a()).booleanValue()) {
               this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(this.w.thePlayer.posX, this.w.thePlayer.posY + 3.0005123413124D, this.w.thePlayer.posZ, false)));
               this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(this.w.thePlayer.posX, this.w.thePlayer.posY, this.w.thePlayer.posZ, false)));
               this.a((Packet)(new C03PacketPlayer(true)));
            }

            BlockPos var3 = var44.getBlockPos();
            double var45 = Math.abs(this.w.thePlayer.posX - (double)var3.getX());
            double var6 = Math.abs(this.w.thePlayer.posZ - (double)var3.getZ());
            double var8 = Math.hypot(var45, var6);
            double var46 = this.w.thePlayer.posY - (double)var3.getY();
            int var47 = 0;
            if((double)var47 < var8) {
               if(var47 % 7 == 0) {
                  double var13 = -Math.sin(Math.toRadians((double)this.w.thePlayer.rotationYaw)) * (double)var47;
                  double var15 = Math.cos(Math.toRadians((double)this.w.thePlayer.rotationYaw)) * (double)var47;
                  this.y.add(new BlockPos(this.w.thePlayer.posX + var13, this.w.thePlayer.posY, this.w.thePlayer.posZ + var15));
               }

               ++var47;
            }

            this.y.add(var3);
            if(((String)this.B.a()).equals("Instant")) {
               Iterator var49 = this.y.iterator();
               if(var49.hasNext()) {
                  BlockPos var50 = (BlockPos)var49.next();
                  if(this.y.indexOf(var50) == this.y.size() - 1) {
                     this.w.thePlayer.setPosition((double)var50.getX(), (double)(var50.getY() + 1), (double)var50.getZ());
                     this.e();
                  }

                  this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition((double)var50.getX(), (double)var50.getY(), (double)var50.getZ(), false)));
               }
            }
         }

         this.e();
      }

   }

   private void a(double var1, double var3, double var5) {
      int var7 = as_.b();
      if(this.a((Class)asx.class) && ((asx)this.b(asx.class)).E()) {
         this.a((Packet)(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
      }

      this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(var1, var3, var5, this.w.thePlayer.c(var3))));
   }

   public Vec3 b(double var1, double var3, double var5) {
      return new Vec3(var1, var3, var5);
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(((String)this.z.a()).equals("BlocksMC"));
   }

   private Boolean lambda$new$1() {
      int var1 = as_.a();
      return Boolean.valueOf(((String)this.z.a()).equals("BlocksMC") && ((String)this.B.a()).equals("Delayed"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(((String)this.z.a()).equals("BlocksMC"));
   }
}
