package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import io.netty.buffer.ByteBuf;
import net.aRP;
import net.bgR;
import net.cA;
import net.cX;

public class cG extends cA {
   private int c;
   private String e;
   private String f;
   private long d;

   public cG(bgR var1) {
      super(var1);
   }

   public void a() {
      String[] var1 = cX.a();
      if(this.f != null && this.d <= System.currentTimeMillis()) {
         PacketWrapperImpl var2 = new PacketWrapperImpl(1, (ByteBuf)null, this.d());
         var2.a(Type.STRING, this.f);
         var2.a(Type.c, Boolean.valueOf(false));
         var2.a(Type.v, (Object)null);
         PacketWrapperImpl var10000 = var2;
         Class var10001 = aRP.class;

         try {
            var10000.c(var10001);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

         this.f = null;
      }
   }

   public int e() {
      return this.c;
   }

   public void a(int var1) {
      this.c = var1;
   }

   public String c() {
      return this.e;
   }

   public void a(String var1) {
      this.e = var1;
   }

   public String b() {
      return this.f;
   }

   public void b(String var1) {
      this.f = var1;
   }

   public long d() {
      return this.d;
   }

   public void a(long var1) {
      this.d = var1;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
