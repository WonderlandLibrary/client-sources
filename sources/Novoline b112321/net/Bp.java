package net;

import com.thealtening.api.response.AccountDetails;
import net.Bv;
import net.CI;
import net.JP;
import net.Jb;
import net.a1x;
import net.aHC;
import net.aHc;
import net.azi;
import net.gZ;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

class Bp extends Bv {
   final JP a;
   final aHC c;
   final aHc d;

   Bp(aHc var1, JP var2, aHC var3) {
      this.d = var1;
      this.a = var2;
      this.c = var3;
   }

   public void a(a1x var1, Session var2) {
      CI.b();
      super.a(var1, var2);
      StringBuilder var4 = new StringBuilder("Logged in! Username: " + var2.getUsername());
      if(this.a instanceof Jb) {
         Jb var5 = (Jb)this.a;
         AccountDetails var6 = var5.a();
         String var7 = var6.getHypixelRank();
         var4.append(" | ").append(var6.getHypixelLevel()).append(" Lvl").append(var7 != null?" | " + var7:"");
      }

      gZ.g().t().a(var4.toString(), azi.INFO);
      aHc.a(this.d).displayGuiScreen(this.d);
   }

   public void unload() {
      this.c.c().b(EnumChatFormatting.RED + "Invalid credentials!");
   }
}
