package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.B7;
import net.P5;
import net.RT;
import net.a10;
import net.aAD;
import net.aX3;
import net.aa8;
import net.aag;
import net.acE;
import net.at6;
import net.b3;
import net.minecraft.client.Minecraft;

public class aSm {
   public static File a;
   public static List h = new ArrayList();
   public static int b = 0;
   public String c;
   public String d;
   public String g;
   public String e;
   public static List f = new ArrayList();

   public void a(File var1) throws IOException {
      this.c = var1.getName();
      BufferedReader var3 = new BufferedReader(new FileReader(var1.getAbsolutePath()));
      P5.b();
      String var4 = var3.readLine();
      if(var4.startsWith("name:")) {
         this.d = b("name:", var4);
      }

      if(var4.startsWith("author:")) {
         this.g = b("author:", var4);
      }

      if(var4.startsWith("description:")) {
         this.e = b("description:", var4);
      }

      var4 = var3.readLine();
      var3.close();
   }

   public void c() throws IOException {
      int var1 = P5.c();
      if(this.c == null) {
         f.clear();
      } else {
         File var2 = new File(a, this.c);
         BufferedReader var3 = new BufferedReader(new FileReader(var2.getAbsolutePath()));
         f.clear();
         boolean var4 = false;
         String var5 = "";
         String var6 = var3.readLine();
         if(var6.startsWith("target")) {
            String var7 = b("target", var6);
            f.add(new aX3(var7.toLowerCase()));
            var4 = false;
         }

         if(var6.contains("anim")) {
            var5 = b("anim", var6);
         }

         if(var6.contains("override: true")) {
            var4 = true;
         }

         if(var6.contains("override: false")) {
            var4 = false;
         }

         if(var6.contains("@") && f.size() > 0) {
            ((aX3)f.get(f.size() - 1)).b.add(a(var5, var6));
         }

         var6 = var3.readLine();
         var3.close();
         int var15 = 0;
         if(var15 < f.size()) {
            System.out.println("Target: " + ((aX3)f.get(var15)).a);
            int var8 = 0;
            if(var8 < ((aX3)f.get(var15)).b.size()) {
               System.out.println("    Action: " + ((P5)((aX3)f.get(var15)).b.get(var8)).h + ", " + ((P5)((aX3)f.get(var15)).b.get(var8)).g + ", " + ((P5)((aX3)f.get(var15)).b.get(var8)).b.name() + "-" + (((P5)((aX3)f.get(var15)).b.get(var8)).i != null?((P5)((aX3)f.get(var15)).b.get(var8)).i.name():"null") + (((P5)((aX3)f.get(var15)).b.get(var8)).e != null?((P5)((aX3)f.get(var15)).b.get(var8)).e.name():"null"));
               int var9 = 0;
               if(var9 < ((P5)((aX3)f.get(var15)).b.get(var8)).a.size()) {
                  aa8 var10 = (aa8)((P5)((aX3)f.get(var15)).b.get(var8)).a.get(var9);
                  System.out.println("        Calc: " + var10.b.name() + ", " + (var10.c != null?var10.c:Float.valueOf(var10.a)) + ", ");
                  ++var9;
               }

               ++var8;
            }

            ++var15;
         }

      }
   }

   public static void b() {
      a = new File(Minecraft.getInstance().mcDataDir, "bendspacks");
      P5.c();
      a.mkdir();
      b = 1;

      try {
         a();
         if(f() != null) {
            f().c();
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public static void a() throws IOException {
      P5.c();
      File[] var1 = a.listFiles();
      h.clear();
      h.add(e());
      int var3 = var1.length;
      int var4 = 0;
      if(var4 < var3) {
         File var5 = var1[var4];
         if(var5.getAbsolutePath().endsWith(".bends")) {
            aSm var6 = new aSm();
            var6.a(var5);
            if(var6.c != null) {
               boolean var10000 = true;
            } else {
               boolean var8 = false;
            }

            if(var6.d != null) {
               boolean var10001 = true;
            } else {
               boolean var9 = false;
            }

            boolean var10 = false;
         }

         ++var4;
      }

      if(b > h.size() - 1) {
         b = h.size() - 1;
      }

   }

   public static aSm e() {
      aSm var0 = new aSm();
      var0.c = null;
      var0.d = "Default";
      var0.g = "GoblinBob";
      var0.e = "The default bends-pack suggested and made by GoblinBob, the creator of Mo\' Bends.";
      return var0;
   }

   public static String b(String var0, String var1) {
      P5.b();
      var1 = var1.replaceFirst(var0, "");
      if(var1.contains("\"")) {
         var1 = var1.replaceAll("\"", "");
      }

      if(var1.contains("{")) {
         var1 = var1.replace("{", "");
      }

      var1 = var1.trim();
      return var1;
   }

   public static aSm f() {
      int var0 = P5.b();
      if(b > h.size() - 1) {
         b = h.size() - 1;
      }

      return (aSm)h.get(b);
   }

   public static P5 a(String var0, String var1) {
      P5 var3 = new P5();
      var3.h = var0;
      var3.g = "";
      int var10000 = P5.c();
      ArrayList var4 = new ArrayList();
      var4.add(new at6());
      int var5 = 0;
      int var2 = var10000;
      String var6 = "";
      int var7 = 0;
      int var8 = 0;
      if(var8 < var1.length()) {
         label69: {
            if(var7 == 0) {
               if(var1.charAt(var8) != 64) {
                  break label69;
               }

               var7 = 1;
            }

            if(var7 == 1) {
               if(var1.charAt(var8) == 58) {
                  ++var7;
               }

               var3.g = var3.g + var1.charAt(var8);
            }

            if(var7 == 2) {
               if(var1.charAt(var8) != 32) {
                  break label69;
               }

               ++var7;
            }

            if(var7 == 3) {
               if(var1.charAt(var8) == 32) {
                  ++var7;
               }

               StringBuilder var15 = new StringBuilder();
               at6 var10002 = (at6)var4.get(var5);
               var10002.b = var15.append(var10002.b).append(var1.charAt(var8)).toString();
            }

            if(var7 == 4) {
               if(var1.charAt(var8) == 32) {
                  ++var7;
               }

               if(var1.charAt(var8) == 43 | var1.charAt(var8) == 45 | var1.charAt(var8) == 61 | var1.charAt(var8) == 42 | var1.charAt(var8) == 47) {
                  if(var1.charAt(var8 + 1) == 61) {
                     var4.add(new at6());
                     ++var5;
                     ((at6)var4.get(var5)).b = var1.charAt(var8) + "=";
                     ++var8;
                  }

                  StringBuilder var16 = new StringBuilder();
                  at6 var18 = (at6)var4.get(var5);
                  var18.a = var16.append(var18.a).append(var1.charAt(var8)).toString();
               }

               StringBuilder var17 = new StringBuilder();
               at6 var19 = (at6)var4.get(var5);
               var19.a = var17.append(var19.a).append(var1.charAt(var8)).toString();
            }

            if(var7 == 5) {
               if(var1.charAt(var8) == 32) {
                  ++var7;
               }

               var6 = var6 + (var1.charAt(var8) == 35?"":Character.valueOf(var1.charAt(var8)));
            }
         }

         ++var8;
      }

      var0 = var0.trim();
      var8 = 0;
      if(var8 < var4.size()) {
         ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.trim();
         if(((at6)var4.get(var8)).a.contains(":cos:")) {
            var3.e = aAD.COS;
            ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.replaceAll(":cos:", "");
            ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.trim();
         }

         if(((at6)var4.get(var8)).a.contains(":sin:")) {
            var3.e = aAD.SIN;
            ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.replaceAll(":sin:", "");
            ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.trim();
         }

         if(((at6)var4.get(var8)).a.contains("$")) {
            ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.replace("$", " ");
            ((at6)var4.get(var8)).a = ((at6)var4.get(var8)).a.trim();
            ((at6)var4.get(var8)).c = ((at6)var4.get(var8)).a;
            ((at6)var4.get(var8)).a = "0";
            System.out.println("Global Var Used: " + ((at6)var4.get(var8)).c);
         }

         ((at6)var4.get(var8)).b = ((at6)var4.get(var8)).b.trim();
         System.out.println("Number: " + ((at6)var4.get(var8)).a + ", " + ((at6)var4.get(var8)).b + ";");
         System.out.println("Line: " + var1);
         var3.a.add((new aa8(P5.a(((at6)var4.get(var8)).b), Float.parseFloat(((at6)var4.get(var8)).a))).a(((at6)var4.get(var8)).c));
         ++var8;
      }

      if(var1.contains(":rot:")) {
         var3.b = b3.ROT;
      }

      if(var1.contains(":scale:")) {
         var3.b = b3.SCALE;
      }

      if(var1.contains(":prerot:")) {
         var3.b = b3.PREROT;
      }

      if(var1.contains(":x")) {
         var3.i = a10.X;
      }

      if(var1.contains(":y")) {
         var3.i = a10.Y;
      }

      if(var1.contains(":z")) {
         var3.i = a10.Z;
      }

      var3.c = Float.parseFloat(var6);
      if(acE.b() == null) {
         ++var2;
         P5.b(var2);
      }

      return var3;
   }

   public static aX3 b(String var0) {
      P5.b();
      int var2 = 0;
      if(var2 < f.size()) {
         if(((aX3)f.get(var2)).a.equalsIgnoreCase(var0)) {
            return (aX3)f.get(var2);
         }

         ++var2;
      }

      return null;
   }

   public static void a(RT var0, String var1, String var2) {
      int var3 = P5.c();
      if(b(var1) != null) {
         b(var1).a((aag)var0.bipedBody, var2, "body");
         b(var1).a((aag)var0.bipedHead, var2, "head");
         b(var1).a((aag)var0.bipedLeftArm, var2, "leftArm");
         b(var1).a((aag)var0.bipedRightArm, var2, "rightArm");
         b(var1).a((aag)var0.bipedLeftLeg, var2, "leftLeg");
         b(var1).a((aag)var0.bipedRightLeg, var2, "rightLeg");
         b(var1).a(var0.K, var2, "leftForeArm");
         b(var1).a(var0.D, var2, "rightForeArm");
         b(var1).a(var0.O, var2, "leftForeLeg");
         b(var1).a(var0.G, var2, "rightForeLeg");
         b(var1).a(var0.T, var2, "itemRotation");
         b(var1).a(var0.A, var2, "playerRotation");
      }
   }

   public void d() throws IOException {
      P5.c();
      String var2 = "\t";
      if(this.c == null) {
         this.c = a(this.d);
      }

      int var3 = 0;
      if(var3 < f.size()) {
         ++var3;
      }

      File var13 = new File(a, this.c + "");
      var13.createNewFile();
      BufferedWriter var4 = new BufferedWriter(new FileWriter(var13));
      var4.write("name: \"" + this.d + "\"\n");
      var4.write("author: \"" + this.g + "\"\n");
      var4.write("description: \"" + this.e + "\"\n");
      var4.newLine();
      int var5 = 0;
      if(var5 < f.size()) {
         aX3 var6 = (aX3)f.get(var5);
         var4.write("target " + var6.a + " {\n");
         String var7 = null;
         int var8 = 0;
         if(var8 < var6.b.size()) {
            P5 var9 = (P5)var6.b.get(var8);
            if(var9.a.size() > 0) {
               if(var7 == null || !var7.equalsIgnoreCase(var9.h)) {
                  if(var7 != null) {
                     var4.write(var2 + "}\n");
                  }

                  var4.write(var2 + "anim " + var9.h + " {\n");
                  var7 = var9.h;
               }

               var4.write(var2 + var2 + "@" + var9.g + ":" + (var9.b == b3.ROT?"rot":(var9.b == b3.SCALE?"scale":"prerot")) + ":" + (var9.i == a10.X?"x":(var9.i == a10.Y?"y":(var9.i == a10.Z?"z":""))) + " ");
               int var10 = 0;
               if(var10 < var9.a.size()) {
                  aa8 var11 = (aa8)var9.a.get(var10);
                  var4.write(var11.b == B7.SET?"==":(var11.b == B7.ADD?"+=":(var11.b == B7.SUBSTRACT?"-=":(var11.b == B7.MULTIPLY?"*=":(var11.b == B7.DIVIDE?"/=":"==")))));
                  var4.write(" " + (var9.e == aAD.COS?":cos:":(var9.e == aAD.SIN?":sin:":"")));
                  var4.write(var11.c == null?"" + var11.a:"$" + var11.c);
                  ++var10;
               }

               var4.write(" #" + var9.c);
               var4.newLine();
               if(var8 == var6.b.size() - 1) {
                  var4.write(var2 + "}\n");
               }
            }

            ++var8;
         }

         var4.write("}\n\n");
         ++var5;
      }

      var4.close();
   }

   public static String a(String var0) {
      String var1 = var0.toLowerCase();
      var1 = var1.replace('.', ' ');
      var1 = var1.trim();
      var1 = var1.replace(" ", "_");
      return var1 + ".bends";
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
