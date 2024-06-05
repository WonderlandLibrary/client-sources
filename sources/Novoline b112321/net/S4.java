package net;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.S1;
import net.aTC;
import net.agP;
import net.akH;

public class S4 extends S1 implements aTC {
   private String i;
   private final Map h = new LinkedHashMap();
   private static PacketRemapper[] g;

   public static S4 b() {
      return a("root", agP.e());
   }

   public static S4 a(String var0) {
      return a(var0, agP.e());
   }

   public static S4 a(String var0, agP var1) {
      return new S4(var0, (Object)null, (S1)null, var1);
   }

   protected S4(String var1, Object var2, S1 var3, agP var4) {
      super(var2, var3, var4);
      this.b(var1);
   }

   protected S4(String var1, S1 var2, S1 var3) {
      super(var2, var3);
      this.b(var1);
   }

   public String e() {
      return this.i;
   }

   public S4 b(String var1) {
      PacketRemapper[] var2 = c();
      if(Strings.isNullOrEmpty(var1)) {
         throw new IllegalArgumentException("Tag name cannot be null/empty");
      } else {
         this.i = var1;
         return this;
      }
   }

   public S4 a(String var1, String var2) {
      PacketRemapper[] var3 = c();
      if(Strings.isNullOrEmpty(var1)) {
         throw new IllegalArgumentException("Attribute name cannot be null/empty");
      } else {
         this.h.put(var1, var2);
         return this;
      }
   }

   public S4 c(String var1) {
      this.h.remove(var1);
      return this;
   }

   public boolean d() {
      PacketRemapper[] var1 = c();
      return !this.h.isEmpty();
   }

   public String c(String var1) {
      return (String)this.h.get(var1);
   }

   public Map b() {
      return ImmutableMap.copyOf(this.h);
   }

   public S4 a(Map var1) {
      c();
      Iterator var3 = var1.keySet().iterator();
      if(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(Strings.isNullOrEmpty(var4)) {
            throw new IllegalArgumentException("Attribute name cannot be null/empty");
         }
      }

      this.h.clear();
      this.h.putAll(var1);
      return this;
   }

   public S4 e() {
      return (S4)super.c();
   }

   protected S4 b(Object var1) {
      return new S4("element", var1, this, this.i());
   }

   public S4 a(Object var1) {
      PacketRemapper[] var2 = c();
      if(var1 instanceof aTC) {
         aTC var3 = (aTC)var1;
         this.b(var3.e());
         this.a(var3.b());
      }

      return (S4)super.b(var1);
   }

   public S4 a(akH var1) {
      PacketRemapper[] var2 = c();
      if(var1 instanceof aTC) {
         aTC var3 = (aTC)var1;
         this.b(var3.e());
         Iterator var4 = var3.b().entrySet().iterator();
         if(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            this.a((String)var5.getKey(), (String)var5.getValue());
         }
      }

      return (S4)super.a(var1);
   }

   public S4 a(Object... var1) {
      return (S4)super.a(var1);
   }

   public List b() {
      return super.b();
   }

   public Map s() {
      return super.s();
   }

   public S4 d() {
      return (S4)super.a();
   }

   public S4 a() {
      return this.a((S1)null);
   }

   protected S4 a(S1 var1) {
      S4 var2 = new S4(this.i, var1, this);
      var2.h.putAll(this.h);
      return var2;
   }

   public boolean equals(Object var1) {
      PacketRemapper[] var2 = c();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof S4)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         S4 var3 = (S4)var1;
         return this.i.equals(var3.i) && this.h.equals(var3.h);
      }
   }

   public int hashCode() {
      c();
      int var2 = super.hashCode();
      var2 = 31 * var2 + this.i.hashCode();
      var2 = 31 * var2 + this.h.hashCode();
      return var2;
   }

   public String toString() {
      PacketRemapper[] var1 = c();
      String var10000 = "SimpleAttributedConfigurationNode{super=" + super.toString() + ", tagName=" + this.i + ", attributes=" + this.h + '}';
      if(PacketRemapper.b() == null) {
         b(new PacketRemapper[2]);
      }

      return var10000;
   }

   public static void b(PacketRemapper[] var0) {
      g = var0;
   }

   public static PacketRemapper[] c() {
      return g;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }

   static {
      b(new PacketRemapper[1]);
   }
}
