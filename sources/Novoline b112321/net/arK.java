package net;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.Ju;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEE;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.ava;
import net.axu;
import net.aye;
import net.cz;
import net.d3;
import net.gZ;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public final class arK extends as0 {
   public static int G;
   public static boolean D;
   public static List y = Lists.newArrayList(new Integer[]{Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(46), Integer.valueOf(48), Integer.valueOf(52), Integer.valueOf(56), Integer.valueOf(57), Integer.valueOf(61), Integer.valueOf(62), Integer.valueOf(73), Integer.valueOf(74), Integer.valueOf(84), Integer.valueOf(89), Integer.valueOf(103), Integer.valueOf(116), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(120), Integer.valueOf(129), Integer.valueOf(133), Integer.valueOf(137), Integer.valueOf(145), Integer.valueOf(152), Integer.valueOf(153), Integer.valueOf(154)});
   public static List I = new CopyOnWriteArrayList();
   private d3 F = new d3();
   @VN("opacity")
   private final aE8 A = (aE8)((aE8)axu.b(Integer.valueOf(160)).d(Integer.valueOf(0))).c(Integer.valueOf(255));
   @VN("esp")
   private final aEu B = axu.b();
   @VN("tracers")
   private final aEu z = axu.b();
   @VN("esp-ores")
   private final aE3 C = axu.a((Object)"Diamond").a((Object[])(new String[]{"Redstone", "Diamond", "Emerald", "Lapis", "Iron", "Coal", "Gold"}));
   @VN("distance")
   private final aE8 x = (aE8)((aE8)axu.b(Integer.valueOf(42)).d(Integer.valueOf(16))).c(Integer.valueOf(64));
   @VN("chunk-update")
   private final aEu H = axu.g();
   @VN("delay")
   private aEE E = (aEE)((aEE)axu.a(Double.valueOf(10.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(30.0D));

   public arK(UW var1) {
      super(var1, "XRay", "XRay", 0, a2V.VISUALS);
      ae9.a(new adZ("ESP_ORES", "ESP Ores", a6d.SELECTBOX, this, this.C, this::lambda$new$0));
      ae9.a(new adZ("OPACITY", "Opacity", a6d.SLIDER, this, this.A, 5.0D));
      ae9.a(new adZ("XRAY_DISTANCE", "Distance", a6d.SLIDER, this, this.x, 4.0D));
      ae9.a(new adZ("XR_TRACERS", "Tracers", a6d.CHECKBOX, this, this.z));
      ae9.a(new adZ("XR_ESP", "ESP", a6d.CHECKBOX, this, this.B));
      ae9.a(new adZ("XR_UPDATE", "Chunks Update", a6d.CHECKBOX, this, this.H));
      a6d var10004 = a6d.SLIDER;
      aEE var10006 = this.E;
      aEu var10008 = this.H;
      this.H.getClass();
      ae9.a(new adZ("XR_DELAY", "Update Delay", var10004, this, var10006, 0.5D, var10008::a));
   }

   public void n() {
      this.d(true);
   }

   public void c() {
      this.d(false);
      this.F.b();
   }

   private void d(boolean var1) {
      I.clear();
      this.w.renderGlobal.loadRenderers();
      D = var1;
   }

   @agu
   public void a(WB var1) {
      int var2 = ava.e();
      if(G != ((Integer)this.A.a()).intValue()) {
         this.w.renderGlobal.loadRenderers();
         G = ((Integer)this.A.a()).intValue();
      }

      if(((Boolean)this.H.a()).booleanValue() && this.F.a(1000.0D * ((Double)this.E.a()).doubleValue())) {
         this.w.renderGlobal.loadRenderers();
         this.F.b();
      }

   }

   @agu
   public void a(aye var1) {
      int var2 = ava.h();
      if(((Boolean)this.B.a()).booleanValue()) {
         Iterator var3 = I.iterator();
         if(var3.hasNext()) {
            BlockPos var4 = (BlockPos)var3.next();
            if(this.w.thePlayer.a((double)var4.getX(), (double)var4.getZ()) <= (double)((Integer)this.x.a()).intValue()) {
               Block var5 = this.w.theWorld.getBlockState(var4).getBlock();
               if(var5 == Blocks.diamond_ore && this.C.a((Object)"Diamond")) {
                  this.a(var4, 0, 255, 255);
               }

               if(var5 == Blocks.iron_ore && this.C.a((Object)"Iron")) {
                  this.a(var4, 225, 225, 225);
               }

               if(var5 == Blocks.lapis_ore && this.C.a((Object)"Lapis")) {
                  this.a(var4, 0, 0, 255);
               }

               if(var5 == Blocks.redstone_ore && this.C.a((Object)"Redstone")) {
                  this.a(var4, 255, 0, 0);
               }

               if(var5 == Blocks.coal_ore && this.C.a((Object)"Coal")) {
                  this.a(var4, 0, 30, 30);
               }

               if(var5 == Blocks.emerald_ore && this.C.a((Object)"Emerald")) {
                  this.a(var4, 0, 255, 0);
               }

               if(var5 == Blocks.gold_ore && this.C.a((Object)"Gold")) {
                  this.a(var4, 255, 255, 0);
               }
            }
         }
      }

   }

   private void a(BlockPos var1, int var2, int var3, int var4) {
      int var5 = ava.e();
      if(((Boolean)this.B.a()).booleanValue()) {
         a6_.b(var1, Ju.a(var2, var3, var4));
      }

      if(((Boolean)this.z.a()).booleanValue()) {
         a6_.a(var1, Ju.a(var2, var3, var4));
      }

   }

   @agu
   public void a(cz var1) {
      int var2 = ava.e();
      if(var1.a().equals("XR_ESP") || var1.a().equals("XR_TRACERS")) {
         I.clear();
         this.w.renderGlobal.loadRenderers();
      }

   }

   public static boolean b() {
      return ((Boolean)((arK)gZ.g().d().b(arK.class)).B.a()).booleanValue();
   }

   public static int a() {
      return ((Integer)((arK)gZ.g().d().b(arK.class)).x.a()).intValue();
   }

   private Boolean lambda$new$0() {
      int var1 = ava.h();
      return Boolean.valueOf(((Boolean)this.B.a()).booleanValue() || ((Boolean)this.z.a()).booleanValue());
   }
}
