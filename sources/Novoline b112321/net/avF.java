package net;

import net.BT;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEu;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.avl;
import net.avu;
import net.axu;

public final class avF extends as0 {
   private int y;
   private boolean x;
   @VN("omni")
   private final aEu z = axu.a(Boolean.valueOf(false));

   public avF(UW var1) {
      super(var1, "Sprint", "Sprint", 0, a2V.MOVEMENT);
      ae9.a(new adZ("OMNI", "Omni", a6d.CHECKBOX, this, this.z));
   }

   private boolean a() {
      String var1 = avu.r();
      return !this.a((Class)avl.class) && this.w.thePlayer.ap().b();
   }

   public void n() {
      this.c(((Boolean)this.z.a()).booleanValue()?"Omni":"");
   }

   @agu
   public void a(WB var1) {
      this.c(((Boolean)this.z.a()).booleanValue()?"Omni":"");
   }

   @agu
   public void a(BT var1) {
      String var2 = avu.r();
      this.y = this.w.thePlayer.onGround?this.y + 1:0;
      if(this.a((Class)avl.class) || !this.w.thePlayer.ap().b()) {
         this.w.thePlayer.setSprinting(this.w.thePlayer.ap().c() > 0.0F);
      }

   }

   @agu
   public void a(aL_ var1) {
      String var2 = avu.r();
      if(!this.a((Class)avu.class) && !this.w.thePlayer.isCollidedHorizontally && ((Boolean)this.z.a()).booleanValue() && this.y > 1 && this.w.thePlayer.p() && !this.w.thePlayer.q() && !this.w.thePlayer.N()) {
         var1.c(this.w.thePlayer.b(this.w.thePlayer.m(true), 0.2D));
      }

   }
}
