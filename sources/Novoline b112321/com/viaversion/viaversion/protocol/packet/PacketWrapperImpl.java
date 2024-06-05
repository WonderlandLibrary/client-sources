package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import net.Zv;
import net.a66;
import net.ayx;
import net.bgR;
import net.kH;
import net.xk;

public class PacketWrapperImpl {
   public static final int f = 1000;
   private static final ayx[] b = new ayx[0];
   private final ByteBuf g;
   private final bgR a;
   private boolean send;
   private int id;
   private final LinkedList e;
   private final List d;
   private static boolean h;

   public PacketWrapperImpl(int var1, ByteBuf var2, bgR var3) {
      k();
      super();
      this.send = true;
      this.id = -1;
      this.e = new LinkedList();
      this.d = new ArrayList();
      this.id = var1;
      this.g = var2;
      this.a = var3;
   }

   public Object b(Type var1, int var2) throws Exception {
      f();
      int var4 = 0;
      Iterator var5 = this.d.iterator();
      if(var5.hasNext()) {
         Pair var6 = (Pair)var5.next();
         if(var6.getKey() == var1) {
            if(var4 == var2) {
               return var6.getValue();
            }

            ++var4;
         }
      }

      ArrayIndexOutOfBoundsException var8 = new ArrayIndexOutOfBoundsException("Could not find type " + var1.getTypeName() + " at " + var2);
      throw (new kH(var8)).a("Type", var1.getTypeName()).a("Index", Integer.valueOf(var2)).a("Packet ID", Integer.valueOf(this.l())).a("Data", this.d);
   }

   public boolean c(Type var1, int var2) {
      k();
      int var4 = 0;
      Iterator var5 = this.d.iterator();
      if(var5.hasNext()) {
         Pair var6 = (Pair)var5.next();
         if(var6.getKey() == var1) {
            if(var4 == var2) {
               return true;
            }

            ++var4;
         }
      }

      return false;
   }

   public boolean a(Type var1, int var2) {
      f();
      int var4 = 0;
      Iterator var5 = this.e.iterator();
      if(var5.hasNext()) {
         Pair var6 = (Pair)var5.next();
         if(((Type)var6.getKey()).getBaseClass() == var1.getBaseClass()) {
            if(var4 == var2) {
               return true;
            }

            ++var4;
         }
      }

      return false;
   }

   public void a(Type var1, int var2, Object var3) throws Exception {
      f();
      int var5 = 0;
      Iterator var6 = this.d.iterator();
      if(var6.hasNext()) {
         Pair var7 = (Pair)var6.next();
         if(var7.getKey() == var1) {
            if(var5 == var2) {
               var7.setValue(var3);
               return;
            }

            ++var5;
         }
      }

      ArrayIndexOutOfBoundsException var9 = new ArrayIndexOutOfBoundsException("Could not find type " + var1.getTypeName() + " at " + var2);
      throw (new kH(var9)).a("Type", var1.getTypeName()).a("Index", Integer.valueOf(var2)).a("Packet ID", Integer.valueOf(this.l()));
   }

   public Object b(Type var1) throws Exception {
      boolean var2 = f();
      if(var1 == Type.af) {
         return null;
      } else if(this.e.isEmpty()) {
         Preconditions.checkNotNull(this.g, "This packet does not have an input buffer.");

         try {
            return var1.read(this.g);
         } catch (Exception var6) {
            throw (new kH(var6)).a("Type", var1.getTypeName()).a("Packet ID", Integer.valueOf(this.l())).a("Data", this.d);
         }
      } else {
         Pair var3 = (Pair)this.e.poll();
         Type var4 = (Type)var3.getKey();
         if(!var4.equals(var1) && (!var1.getBaseClass().equals(var4.getBaseClass()) || !var1.getOutputClass().equals(var4.getOutputClass()))) {
            if(var4 == Type.af) {
               return this.b(var1);
            } else {
               IOException var5 = new IOException("Unable to read type " + var1.getTypeName() + ", found " + ((Type)var3.getKey()).getTypeName());
               throw (new kH(var5)).a("Type", var1.getTypeName()).a("Packet ID", Integer.valueOf(this.l())).a("Data", this.d);
            }
         } else {
            return var3.getValue();
         }
      }
   }

   public void a(Type var1, Object var2) {
      boolean var3 = k();
      if(!var1.getOutputClass().isAssignableFrom(var2.getClass())) {
         if(var1 instanceof TypeConverter) {
            var2 = ((TypeConverter)var1).from(var2);
         }

         Via.d().getLogger().warning("Possible type mismatch: " + var2.getClass().getName() + " -> " + var1.getOutputClass());
      }

      this.d.add(new Pair(var1, var2));
   }

   public Object a(Type var1) throws Exception {
      Object var2 = this.b(var1);
      this.a(var1, var2);
      return var2;
   }

   public void h() throws Exception {
      f();
      this.d.addAll(this.e);
      this.e.clear();
      if(this.g.readableBytes() > 0) {
         this.a(Type.REMAINING_BYTES);
      }

   }

   public void a(ByteBuf var1) throws Exception {
      boolean var2 = k();
      if(this.id != -1) {
         Type.VAR_INT.writePrimitive(var1, this.id);
      }

      if(!this.e.isEmpty()) {
         this.d.addAll(this.e);
         this.e.clear();
      }

      int var3 = 0;
      Iterator var4 = this.d.iterator();
      if(var4.hasNext()) {
         Pair var5 = (Pair)var4.next();

         try {
            Object var6 = var5.getValue();
            if(var6 != null && !((Type)var5.getKey()).getOutputClass().isAssignableFrom(var6.getClass())) {
               if(var5.getKey() instanceof TypeConverter) {
                  var6 = ((TypeConverter)var5.getKey()).from(var6);
               }

               Via.d().getLogger().warning("Possible type mismatch: " + var6.getClass().getName() + " -> " + ((Type)var5.getKey()).getOutputClass());
            }

            ((Type)var5.getKey()).write(var1, var6);
         } catch (Exception var7) {
            throw (new kH(var7)).a("Index", Integer.valueOf(var3)).a("Type", ((Type)var5.getKey()).getTypeName()).a("Packet ID", Integer.valueOf(this.l())).a("Data", this.d);
         }

         ++var3;
      }

      this.writeRemaining(var1);
   }

   public void a() {
      boolean var1 = k();
      if(this.g != null) {
         this.g.clear();
      }

      this.e.clear();
   }

   public void c() {
      this.a();
      this.d.clear();
   }

   private void writeRemaining(ByteBuf var1) {
      boolean var2 = k();
      if(this.g != null) {
         var1.writeBytes(this.g, this.g.readableBytes());
      }

   }

   public void b(Class var1, boolean var2) throws Exception {
      this.a(var1, var2, false);
   }

   public void a(Class var1, boolean var2, boolean var3) throws Exception {
      boolean var4 = k();
      if(!this.b()) {
         try {
            ByteBuf var5 = this.a(var1, var2, xk.OUTGOING);
            this.e().d(var5, var3);
         } catch (Exception var6) {
            if(!PipelineUtil.containsCause(var6, CancelException.class)) {
               throw var6;
            }
         }
      }

   }

   private ByteBuf a(Class var1, boolean var2, xk var3) throws Exception {
      k();
      ayx[] var5 = (ayx[])this.e().c().b().a().toArray(b);
      boolean var6 = var3 == xk.OUTGOING;
      int var7 = -1;
      int var8 = 0;
      if(var8 < var5.length) {
         if(var5[var8].getClass() == var1) {
            var7 = var8;
         }

         ++var8;
      }

      if(var7 == -1) {
         throw new NoSuchElementException(var1.getCanonicalName());
      } else {
         if(var2) {
            var7 = var6?var7 - 1:var7 + 1;
         }

         this.d();
         this.a(var3, this.e().c().e(), var7, var5, var6);
         ByteBuf var10 = this.g == null?this.e().p().alloc().buffer():this.g.alloc().buffer();
         this.a(var10);
         return var10;
      }
   }

   public void a(Class var1) throws Exception {
      this.b(var1, true);
   }

   public ChannelFuture b(Class var1) throws Exception {
      boolean var2 = f();
      if(!this.b()) {
         ByteBuf var3 = this.a(var1, true, xk.OUTGOING);
         return this.e().b(var3);
      } else {
         return this.e().p().newFailedFuture(new Exception("Cancelled packet"));
      }
   }

   /** @deprecated */
   @Deprecated
   public void j() throws Exception {
      boolean var1 = f();
      if(!this.b()) {
         ByteBuf var2 = this.g == null?this.e().p().alloc().buffer():this.g.alloc().buffer();
         this.a(var2);
         this.e().c(var2);
      }

   }

   public PacketWrapperImpl a(int var1) {
      return new PacketWrapperImpl(var1, (ByteBuf)null, this.e());
   }

   public PacketWrapperImpl a(int var1, Zv var2) throws Exception {
      PacketWrapperImpl var3 = this.a(var1);
      var2.b(var3);
      return var3;
   }

   public PacketWrapperImpl a(xk var1, a66 var2, int var3, List var4, boolean var5) throws Exception {
      k();
      ayx[] var7 = (ayx[])var4.toArray(b);
      return this.a(var1, var2, var5?var7.length - 1:var3, var7, var5);
   }

   public PacketWrapperImpl a(xk var1, a66 var2, int var3, List var4) throws Exception {
      return this.a(var1, var2, var3, (ayx[])var4.toArray(b), false);
   }

   private PacketWrapperImpl a(xk var1, a66 var2, int var3, ayx[] var4, boolean var5) throws Exception {
      boolean var6 = f();
      if(var5) {
         var4[var3].a(var1, var2, this);
         this.d();
         int var7 = var3 - 1;
      }

      if(var3 < var4.length) {
         var4[var3].a(var1, var2, this);
         this.d();
         int var8 = var3 + 1;
      }

      return this;
   }

   public void i() {
      this.send = false;
   }

   public boolean b() {
      boolean var1 = f();
      return !this.send;
   }

   public bgR e() {
      return this.a;
   }

   public void d() {
      this.d.addAll(this.e);
      this.e.clear();
      this.e.addAll(this.d);
      this.d.clear();
   }

   /** @deprecated */
   @Deprecated
   public void g() throws Exception {
      boolean var1 = f();
      if(!this.b()) {
         ByteBuf var2 = this.g == null?this.e().p().alloc().buffer():this.g.alloc().buffer();
         this.a(var2);
         this.e().a(var2, true);
      }

   }

   public void b(Class var1, boolean var2, boolean var3) throws Exception {
      boolean var4 = k();
      if(!this.b()) {
         try {
            ByteBuf var5 = this.a(var1, var2, xk.INCOMING);
            this.e().a(var5, var3);
         } catch (Exception var6) {
            if(!PipelineUtil.containsCause(var6, CancelException.class)) {
               throw var6;
            }
         }
      }

   }

   public void a(Class var1, boolean var2) throws Exception {
      this.b(var1, var2, false);
   }

   public void c(Class var1) throws Exception {
      this.a(var1, true);
   }

   public int l() {
      return this.id;
   }

   public void b(int var1) {
      this.id = var1;
   }

   public String toString() {
      return "PacketWrapper{packetValues=" + this.d + ", readableObjects=" + this.e + ", id=" + this.id + '}';
   }

   static {
      b(true);
   }

   public static void b(boolean var0) {
      h = var0;
   }

   public static boolean f() {
      return h;
   }

   public static boolean k() {
      boolean var0 = f();
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
