package net;

import cc.novoline.events.events.EventState;
import io.netty.util.internal.ThreadLocalRandom;
import net.UW;
import net.VN;
import net.WB;
import net.WL;
import net.a2V;
import net.a6d;
import net.aEs;
import net.aG1;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.apo;
import net.as0;
import net.avS;
import net.avu;
import net.axu;
import net.lS;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class avr extends as0 {
   private int y;
   private int A;
   private boolean z;
   @VN("mode")
   private aEs x = axu.a("Solid").a(new String[]{"Solid", "Bounce", "Dolphin"});

   public avr(UW var1) {
      super(var1, "WaterWalk", "Water Walk", a2V.MOVEMENT, "");
      ae9.a(new adZ("WW_MODE", "Mode", a6d.COMBOBOX, this, this.x));
   }

   public void n() {
      this.c((String)this.x.a());
   }

   public void c() {
      this.w.thePlayer.stepHeight = 0.625F;
      this.z = false;
      this.A = 0;
   }

   @agu
   public void a(aG1 var1) {
      String var2 = avu.r();
      if(var1.h().equals(EventState.PRE) && !this.a((Class)avS.class)) {
         if(this.x.a("Solid")) {
            if(!this.w.thePlayer.ap().b() && this.w.thePlayer.N() && !this.w.thePlayer.q()) {
               if(this.w.thePlayer.fallDistance != 0.0F) {
                  return;
               }

               this.w.thePlayer.stepHeight = 0.015625F;
               ++this.y;
               if(lS.c()) {
                  if(this.y == 1) {
                     var1.c(var1.f() - ThreadLocalRandom.current().nextDouble(0.015525D, 0.015625D));
                  }

                  if(this.y == 2) {
                     var1.c(var1.f() + ThreadLocalRandom.current().nextDouble(0.0149D, 0.015D));
                  }

                  if(this.y == 3) {
                     var1.c(var1.f() + ThreadLocalRandom.current().nextDouble(0.0199D, 0.02D));
                  }

                  if(this.y >= 4) {
                     var1.c(var1.f() + 0.015625D);
                     this.y = 0;
                  }

                  var1.c(this.w.thePlayer.c(var1.f()));
               }

               if(this.y % 2 == 0) {
                  var1.c(var1.f() - 1.0E-13D);
               }

               var1.c(var1.f() + 1.0E-13D);
            }

            this.y = 0;
            this.w.thePlayer.stepHeight = 0.625F;
            if(this.w.thePlayer.ap().b() || !this.w.thePlayer.q() || !this.a(this.w.thePlayer.posX, this.w.thePlayer.posY, this.w.thePlayer.posZ) && !this.a(this.w.thePlayer.posY - 1.0D) && !this.a(this.w.thePlayer.posY + 1.0D)) {
               return;
            }

            this.w.thePlayer.motionY = 0.11999998688698D;
         }

         if(this.x.a("Bounce") && this.w.thePlayer.q() && !this.w.thePlayer.ap().b()) {
            this.w.thePlayer.motionY = 0.11999998688698D;
         }

         if(this.x.a("Dolphin") && !this.w.thePlayer.ap().b()) {
            if(this.w.thePlayer.onGround || this.w.thePlayer.isOnLadder()) {
               this.z = false;
            }

            if(this.w.thePlayer.motionY > 0.0D && this.z) {
               if(this.w.thePlayer.motionY <= 0.1127D) {
                  this.w.thePlayer.motionY *= 1.267D;
               }

               this.w.thePlayer.motionY += 0.05172D;
            }

            if(this.w.thePlayer.q()) {
               if(!this.b(var1)) {
                  if(this.A < 3) {
                     this.w.thePlayer.motionY = 0.13D;
                     ++this.A;
                     this.z = false;
                  }

                  this.w.thePlayer.motionY = 0.5D;
                  this.A = 0;
                  this.z = true;
               }

               if(!this.w.thePlayer.ap().d()) {
                  this.w.thePlayer.motionY = 0.09D;
               }
            }
         }
      }

   }

   @agu
   public void a(aL_ var1) {
      String var2 = avu.r();
      if(lS.c() && !this.w.thePlayer.ap().b() && !this.a((Class)avS.class)) {
         if(this.x.a("Solid")) {
            if(!this.w.thePlayer.N() || this.w.thePlayer.q()) {
               return;
            }

            var1.c(!lS.a(WL.UHC) && !lS.a(WL.SG)?this.w.thePlayer.l(true) * 0.98D:(this.w.thePlayer.l(true) + this.w.thePlayer.l(false)) / 2.0D);
         }

         if(this.x.a("Bounce") && this.a(MathHelper.a(this.w.thePlayer.posY, 1.0D) - 0.001D) && !this.a(this.w.thePlayer.posY + 0.5D)) {
            var1.c(!lS.a(WL.UHC) && !lS.a(WL.SG)?this.w.thePlayer.l(true) * 0.98D:(this.w.thePlayer.l(true) + this.w.thePlayer.l(false)) / 2.0D);
         }
      }

   }

   @agu
   public void a(apo var1) {
      String var2 = avu.r();
      if(this.x.a("Solid") && var1.b().getMaterial().isLiquid() && !this.w.thePlayer.q() && !this.w.thePlayer.ap().b() && this.a((double)var1.a().getX(), (double)var1.a().getY(), (double)var1.a().getZ()) && !this.a((Class)avS.class)) {
         var1.a(new AxisAlignedBB(var1.a(), var1.a().a(1, 1, 1)));
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)this.x.a());
   }

   private boolean b(aG1 var1) {
      return this.w.theWorld.getBlockState(new BlockPos(var1.d(), this.w.thePlayer.getEntityBoundingBox().maxY - 1.0D, var1.i())).getBlock() instanceof BlockLiquid;
   }

   private boolean a(double var1, double var3, double var5) {
      String var7 = avu.r();
      return ((Integer)((Comparable)this.w.theWorld.getBlockState(new BlockPos(var1, var3, var5)).getProperties().get(BlockLiquid.LEVEL))).intValue() < 4;
   }

   private boolean a(double var1) {
      return this.w.theWorld.getBlockState(new BlockPos(this.w.thePlayer.posX, var1, this.w.thePlayer.posZ)).getBlock().getMaterial().isLiquid();
   }

   public aEs a() {
      return this.x;
   }
}
