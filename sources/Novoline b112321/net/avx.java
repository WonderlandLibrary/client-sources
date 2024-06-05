package net;

import java.awt.Color;
import java.util.EnumSet;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEB;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.ava;
import net.axu;

public class avx extends as0 {
   private int x = 0;
   @VN("glint-color")
   private final aEB y = axu.a(Integer.valueOf(-7697665));
   @VN("rainbow")
   private final aEu z = axu.g();

   public avx(UW var1) {
      super(var1, "GlintColorize", "Glint Colorize", 0, a2V.VISUALS);
      ae9.a(new adZ("GLINT_COLOR", "Glint color", a6d.COLOR_PICKER, this, this.y, (EnumSet)null));
      ae9.a(new adZ("GLINT_RAINBOW", "Rainbow", a6d.CHECKBOX, this, this.z));
   }

   @agu
   public void a(WB var1) {
      int var2 = ava.e();
      if(((Boolean)this.z.a()).booleanValue()) {
         ++this.x;
         if(this.x <= 255) {
            return;
         }

         this.x = 0;
      }

      if(this.x != 0) {
         this.x = 0;
      }

   }

   public int b() {
      ava.h();
      float[] var2 = this.y.i();
      float var3 = ((Boolean)this.z.a()).booleanValue()?(float)this.x / 255.0F:var2[0];
      Color var4 = Color.getHSBColor(var3, var2[1], var2[2]);
      return var4.getRGB();
   }

   public aEB c() {
      return this.y;
   }

   public aEu a() {
      return this.z;
   }

   public int d() {
      return this.x;
   }
}
