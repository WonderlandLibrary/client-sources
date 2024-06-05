package net;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import net.FJ;
import net.Gi;
import net.Uj;
import net.aJ1;
import net.a_E;
import net.ar9;
import net.azi;
import net.gZ;

public final class Fl extends FJ {
   public Fl(gZ var1) {
      super(var1, "waypoint", "Manages waypoints", (Iterable)Arrays.asList(new String[]{"waypoints", "wp"}));
   }

   private void b() {
      this.a("Waypoints help:", ".waypoint", new Uj[]{aJ1.a("add (x) (y) (z) (name)", "add waypoint"), aJ1.a("remove (name)", "removes waypoint"), aJ1.a("clear", "remove all waypoints"), aJ1.a("list", "show waypoints list")});
   }

   public void b(String[] var1) {
      a_E.b();
      ar9 var3 = (ar9)gZ.g().d().b(ar9.class);
      if(var1.length == 0) {
         this.b();
      } else {
         String var4 = var1[0];
         String var5 = var4.toLowerCase();
         byte var6 = -1;
         switch(var5.hashCode()) {
         case 96417:
            if(!var5.equals("add")) {
               break;
            }

            var6 = 0;
         case -934610812:
            if(!var5.equals("remove")) {
               break;
            }

            var6 = 1;
         case -1335458389:
            if(!var5.equals("delete")) {
               break;
            }

            var6 = 2;
         case 99339:
            if(!var5.equals("del")) {
               break;
            }

            var6 = 3;
         case 94746189:
            if(!var5.equals("clear")) {
               break;
            }

            var6 = 4;
         case 3322014:
            if(var5.equals("list")) {
               var6 = 5;
            }
         }

         switch(var6) {
         case 0:
            if(var1.length != 5) {
               this.b();
               return;
            }

            var3.a(Gi.a(var1[4], Integer.parseInt(var1[1]), Integer.parseInt(var1[2]), Integer.parseInt(var1[3])));

            try {
               var3.b().a();
               this.c("Waypoint " + var1[4] + " was added successfully!", 5000);
               break;
            } catch (IOException var10) {
               gZ.g().t().a("Can\'t save to file", azi.ERROR);
               var3.v().warn("An error occurred while saving waypoints list", var10);
            }
         case 1:
         case 2:
         case 3:
            if(var1.length != 2) {
               this.b();
               return;
            }

            if(var3.a(var1[1])) {
               this.c("Removed " + var1[1] + " waypoint!", 5000);
               ar9 var10000 = var3;

               try {
                  var10000.b().a();
                  break;
               } catch (IOException var9) {
                  gZ.g().t().a("Can\'t save to file", azi.ERROR);
                  var3.v().warn("An error occurred while saving waypoints list", var9);
               }
            }

            this.b("Waypoint doesn\'t exist!", 5000);
         case 4:
            if(var1.length != 1) {
               this.b();
               return;
            }

            if(var3.c().isEmpty()) {
               break;
            }

            var3.c().clear();
         case 5:
            if(var1.length != 1) {
               this.b();
               return;
            }

            this.c("Waypoints:");
            Iterator var7 = var3.c().iterator();
            if(var7.hasNext()) {
               Gi var8 = (Gi)var7.next();
               this.c(" - name: §3" + var8.g() + "§r, coordinates: §8X:§r " + var8.b() + " §8Y:§r " + var8.f() + " §8Z:§r " + var8.e());
            }
         }

      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
