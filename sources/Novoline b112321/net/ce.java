package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.util.Pair;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.aRP;
import net.af_;
import net.bgR;
import net.cA;
import net.cX;

public class ce extends cA {
   private static final short[] d = new short[8582];
   private static Constructor c;
   private final Map e = this.b();

   public ce(bgR var1) {
      super(var1);
   }

   public void a(int var1, int var2, int var3, int var4) {
      cX.a();
      short var6 = d[var4];
      if(var6 != -1) {
         long var7 = this.c(var1, var2, var3);
         Pair var9 = this.a(var7, (var6 & 15) != 0);
         short var10 = this.b(var1, var2, var3);
         ((byte[])var9.getKey())[var10] = (byte)(var6 >> 4);
         NibbleArray var11 = (NibbleArray)var9.getValue();
         if(var11 != null) {
            var11.set(var10, var6);
         }

      }
   }

   public int d(int var1, int var2, int var3) {
      cX.a();
      long var5 = this.c(var1, var2, var3);
      Pair var7 = (Pair)this.e.get(Long.valueOf(var5));
      return 0;
   }

   public void a(int var1, int var2, int var3) {
      cX.a();
      long var5 = this.c(var1, var2, var3);
      Pair var7 = (Pair)this.e.get(Long.valueOf(var5));
   }

   public void a() {
      this.e.clear();
   }

   public void a(int var1, int var2) {
      cX.a();
      int var4 = 0;
      if(var4 < 256) {
         this.e.remove(Long.valueOf(this.c(var1 << 4, var4, var2 << 4)));
         var4 = var4 + 16;
      }

   }

   private Pair a(long var1, boolean var3) {
      cX.a();
      Pair var5 = (Pair)this.e.get(Long.valueOf(var1));
      if(var5 == null) {
         var5 = new Pair(new byte[4096], (Object)null);
         this.e.put(Long.valueOf(var1), var5);
      }

      if(var5.getValue() == null) {
         var5.setValue(new NibbleArray(4096));
      }

      return var5;
   }

   private long c(int var1, int var2, int var3) {
      return ((long)(var1 >> 4) & 67108863L) << 38 | ((long)(var2 >> 4) & 4095L) << 26 | (long)(var3 >> 4) & 67108863L;
   }

   private long a(Position var1) {
      return this.c(var1.getX(), var1.e(), var1.getZ());
   }

   private short b(int var1, int var2, int var3) {
      return (short)((var2 & 15) << 8 | (var1 & 15) << 4 | var3 & 15);
   }

   private short b(Position var1) {
      return this.b(var1.getX(), var1.e(), var1.getZ());
   }

   private Map b() {
      String[] var1 = cX.a();
      if(c != null) {
         try {
            return (Map)c.newInstance(new Object[0]);
         } catch (InstantiationException | InvocationTargetException | IllegalAccessException var3) {
            var3.printStackTrace();
         }
      }

      return new HashMap();
   }

   static {
      try {
         String var8 = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
         c = af_.a(var8).getConstructor(new Class[0]);
         Via.d().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
      } catch (NoSuchMethodException | ClassNotFoundException var10) {
         ;
      }

      Arrays.fill(d, (short)-1);

      for(int var11 = 0; var11 < 4096; ++var11) {
         int var9 = aRP.m.c().a(var11);
         if(var9 != -1) {
            d[var9] = (short)var11;
         }
      }

   }

   private static ReflectiveOperationException a(ReflectiveOperationException var0) {
      return var0;
   }
}
