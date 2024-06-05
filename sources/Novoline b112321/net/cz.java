package net;

import net.Eb;
import net.a6d;
import net.aE3;
import net.aEi;
import net.aEs;
import net.aEu;
import net.as0;

public class cz implements Eb {
   private aEu f;
   private aEs b;
   private aEi c;
   private aE3 a;
   private final as0 h;
   private final String e;
   private String d;
   private final a6d g;

   public cz(as0 var1, String var2, aEu var3) {
      this.h = var1;
      this.e = var2;
      this.g = a6d.CHECKBOX;
      this.f = var3;
   }

   public cz(as0 var1, String var2, String var3, aEu var4) {
      this.h = var1;
      this.e = var2;
      this.d = var3;
      this.g = a6d.CHECKBOX;
      this.f = var4;
   }

   public cz(as0 var1, String var2, aEs var3) {
      this.h = var1;
      this.e = var2;
      this.g = a6d.COMBOBOX;
      this.b = var3;
   }

   public cz(as0 var1, String var2, aEi var3) {
      this.h = var1;
      this.e = var2;
      this.g = a6d.SLIDER;
      this.c = var3;
   }

   public cz(as0 var1, String var2, aE3 var3) {
      this.h = var1;
      this.e = var2;
      this.g = a6d.SELECTBOX;
      this.a = var3;
   }

   public aEu e() {
      return this.f;
   }

   public aEs b() {
      return this.b;
   }

   public aEi f() {
      return this.c;
   }

   public aE3 d() {
      return this.a;
   }

   public String a() {
      return this.e;
   }

   public a6d c() {
      return this.g;
   }

   public as0 h() {
      return this.h;
   }

   public String g() {
      return this.d;
   }
}
