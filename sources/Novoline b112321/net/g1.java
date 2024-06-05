package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.Ch;
import net.a0l;
import net.a7e;
import net.aTD;
import net.ayx;
import net.n9;
import net.y7;
import org.jetbrains.annotations.Nullable;

public class g1 {
   private static final int[] d = new int[0];
   private final ayx a;
   private final IdRewriteFunction b;
   private final List f = new ArrayList();
   private final List c = new ArrayList();
   private final List e = new ArrayList();

   public g1(ayx var1, @Nullable IdRewriteFunction var2) {
      this.a = var1;
      this.b = var2;
   }

   public void a(Ch var1, String var2) {
      this.a(var1).add(new n9(var2, d, (a7e)null));
   }

   public void a(Ch var1, String... var2) {
      aTD.c();
      List var4 = this.a(var1);
      int var6 = var2.length;
      int var7 = 0;
      if(var7 < var6) {
         String var8 = var2[var7];
         var4.add(new n9(var8, d, (a7e)null));
         ++var7;
      }

   }

   public void a(Ch var1, String var2, int... var3) {
      aTD.e();
      List var5 = this.a(var1);
      IdRewriteFunction var6 = this.b(var1);
      int var7 = 0;
      if(var7 < var3.length) {
         int var8 = var3[var7];
         var3[var7] = var6.rewrite(var8);
         ++var7;
      }

      var5.add(new n9(var2, var3, (a7e)null));
   }

   public void a(y7 var1) {
      this.a.a((y7)var1, (PacketRemapper)(new a7e(this)));
   }

   private void a(PacketWrapperImpl var1, IdRewriteFunction var2, List var3) throws Exception {
      aTD.c();
      int var5 = ((Integer)var1.b((Type)Type.VAR_INT)).intValue();
      var1.a(Type.VAR_INT, Integer.valueOf(var5 + var3.size()));
      int var6 = 0;
      if(var6 < var5) {
         var1.a(Type.STRING);
         int[] var7 = (int[])var1.b(Type.VAR_INT_ARRAY_PRIMITIVE);
         IntArrayList var8 = new IntArrayList(var7.length);
         int var10 = var7.length;
         int var11 = 0;
         if(var11 < var10) {
            int var12 = var7[var11];
            int var13 = var2.rewrite(var12);
            if(var13 != -1) {
               var8.add(var13);
            }

            ++var11;
         }

         var1.a(Type.VAR_INT_ARRAY_PRIMITIVE, var8.toArray(d));
         var1.a(Type.VAR_INT_ARRAY_PRIMITIVE, var7);
         ++var6;
      }

      if(var3 != null && !var3.isEmpty()) {
         Iterator var15 = var3.iterator();
         if(var15.hasNext()) {
            n9 var16 = (n9)var15.next();
            var1.a(Type.STRING, n9.b(var16));
            var1.a(Type.VAR_INT_ARRAY_PRIMITIVE, n9.a(var16));
         }
      }

   }

   private List a(Ch var1) {
      switch(a0l.a[var1.ordinal()]) {
      case 1:
         return this.f;
      case 2:
         return this.c;
      case 3:
         return this.e;
      case 4:
      default:
         return null;
      }
   }

   @Nullable
   private IdRewriteFunction b(Ch var1) {
      boolean var2 = aTD.e();
      switch(a0l.a[var1.ordinal()]) {
      case 1:
         return this.a.d().c() != null?this::lambda$getRewriter$0:null;
      case 2:
         return this.a.d().g() != null?this::lambda$getRewriter$1:null;
      case 3:
         return this.b;
      case 4:
      default:
         return null;
      }
   }

   private int lambda$getRewriter$1(int var1) {
      return this.a.d().getNewItemId(var1);
   }

   private int lambda$getRewriter$0(int var1) {
      return this.a.d().getNewBlockId(var1);
   }

   static ayx e(g1 var0) {
      return var0.a;
   }

   static List c(g1 var0) {
      return var0.f;
   }

   static void a(g1 var0, PacketWrapperImpl var1, IdRewriteFunction var2, List var3) throws Exception {
      var0.a(var1, var2, var3);
   }

   static List b(g1 var0) {
      return var0.c;
   }

   static IdRewriteFunction a(g1 var0) {
      return var0.b;
   }

   static List d(g1 var0) {
      return var0.e;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
