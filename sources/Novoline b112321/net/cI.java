package net;

import java.util.UUID;
import net.a66;
import net.aRB;
import net.bgR;
import net.cA;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class cI extends cA {
   private a66 c = a66.HANDSHAKE;
   private int h = -1;
   private int i = -1;
   private String d;
   private UUID f;
   private aRB g;
   private static String e;

   public cI(bgR var1) {
      super(var1);
   }

   public a66 e() {
      return this.c;
   }

   public void a(a66 var1) {
      this.c = var1;
   }

   public int f() {
      return this.h;
   }

   public void b(int var1) {
      ProtocolVersion var2 = ProtocolVersion.b(var1);
      this.h = var2.d();
   }

   public int h() {
      return this.i;
   }

   public void a(int var1) {
      ProtocolVersion var2 = ProtocolVersion.b(var1);
      this.i = var2.d();
   }

   public String g() {
      return this.d;
   }

   public void a(String var1) {
      this.d = var1;
   }

   public UUID a() {
      return this.f;
   }

   public void a(UUID var1) {
      this.f = var1;
   }

   public aRB b() {
      return this.g;
   }

   public void a(aRB var1) {
      this.g = var1;
   }

   public String toString() {
      return "ProtocolInfo{state=" + this.c + ", protocolVersion=" + this.h + ", serverProtocolVersion=" + this.i + ", username=\'" + this.d + '\'' + ", uuid=" + this.f + '}';
   }

   public static void b(String var0) {
      e = var0;
   }

   public static String d() {
      return e;
   }

   static {
      b((String)null);
   }
}
