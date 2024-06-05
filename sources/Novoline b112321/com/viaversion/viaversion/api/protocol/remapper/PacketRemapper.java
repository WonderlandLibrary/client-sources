package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper$1;
import com.viaversion.viaversion.api.protocol.remapper.TypeRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.EN;
import net.Zv;
import net.aQd;
import net.kH;

public abstract class PacketRemapper {
   private final List a;
   private static String b;

   public PacketRemapper() {
      ValueTransformer.a();
      super();
      this.a = new ArrayList();
      this.registerMap();
      if(b() == null) {
         ValueTransformer.b(false);
      }

   }

   public void a(Type var1) {
      TypeRemapper var2 = new TypeRemapper(var1);
      this.a(var2, var2);
   }

   public void map(Type var1, Type var2) {
      this.a(new TypeRemapper(var1), new TypeRemapper(var2));
   }

   public void map(Type var1, Type var2, Function var3) {
      ValueTransformer.b();
      this.a(new TypeRemapper(var1), new PacketRemapper$1(this, var2, var3));
   }

   public void map(ValueTransformer var1) {
      boolean var2 = ValueTransformer.a();
      if(var1.getInputType() == null) {
         throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
      } else {
         this.map(var1.getInputType(), var1);
      }
   }

   public void map(Type var1, ValueTransformer var2) {
      this.a(new TypeRemapper(var1), var2);
   }

   public void a(aQd var1, ValueWriter var2) {
      this.a.add(new Pair(var1, var2));
   }

   public void a(Zv var1) {
      this.a(new TypeRemapper(Type.af), var1);
   }

   public void a(EN var1) {
      this.a(new TypeRemapper(Type.af), var1);
   }

   public abstract void registerMap();

   public void a(PacketWrapperImpl var1) throws Exception {
      boolean var2 = ValueTransformer.a();

      try {
         Iterator var3 = this.a.iterator();
         if(var3.hasNext()) {
            Pair var9 = (Pair)var3.next();
            Object var5 = ((aQd)var9.getKey()).a(var1);
            ((ValueWriter)var9.getValue()).a(var1, var5);
         }

      } catch (kH var6) {
         var6.a(this.getClass());
         throw var6;
      } catch (CancelException var7) {
         throw var7;
      } catch (Exception var8) {
         kH var4 = new kH(var8);
         var4.a(this.getClass());
         throw var4;
      }
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   private static Exception b(Exception var0) {
      return var0;
   }

   static {
      if(b() == null) {
         ;
      }

   }
}
