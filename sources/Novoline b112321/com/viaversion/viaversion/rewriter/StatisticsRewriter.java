package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter$2;
import net.Ch;
import net.a76;
import net.aTD;
import net.ayx;
import net.y7;
import org.jetbrains.annotations.Nullable;

public class StatisticsRewriter {
   private final ayx c;
   private final IdRewriteFunction b;
   private final int customStatsCategory = 8;

   public StatisticsRewriter(ayx var1, @Nullable IdRewriteFunction var2) {
      this.c = var1;
      this.b = var2;
   }

   public void a(y7 var1) {
      this.c.a((y7)var1, (PacketRemapper)(new a76(this)));
   }

   @Nullable
   protected IdRewriteFunction a(Ch var1) {
      boolean var2 = aTD.e();
      switch(StatisticsRewriter$2.$SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[var1.ordinal()]) {
      case 1:
         return this.c.d().c() != null?this::lambda$getRewriter$0:null;
      case 2:
         return this.c.d().g() != null?this::lambda$getRewriter$1:null;
      case 3:
         return this.b;
      default:
         throw new IllegalArgumentException("Unknown registry type in statistics packet: " + var1);
      }
   }

   @Nullable
   public Ch a(int var1) {
      switch(var1) {
      case 0:
         return Ch.BLOCK;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
         return Ch.ITEM;
      case 6:
      case 7:
         return Ch.ENTITY;
      default:
         return null;
      }
   }

   private int lambda$getRewriter$1(int var1) {
      return this.c.d().getNewItemId(var1);
   }

   private int lambda$getRewriter$0(int var1) {
      return this.c.d().getNewBlockId(var1);
   }

   static ayx a(StatisticsRewriter var0) {
      return var0.c;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
